CQ.form.BrightcoveVideoGallerySearch = CQ.Ext.extend(CQ.Ext.form.ComboBox, {

constructor : function(config) {
        
        //CQ.form.PathField functionality
        if (typeof config.rootTitle === "undefined") {
            config.rootTitle = config.rootPath || CQ.I18n.getMessage("Websites");
        }
        if (typeof config.rootPath === "undefined") {
            config.rootPath = "/content";
        }
        var rootName = config.rootPath;
        // the root path must not include a leading slash for the root tree node
        // (it's added automatically in CQ.Ext.data.Node.getPath())
        if (rootName.charAt(0) === "/") {
            rootName = rootName.substring(1);
        }
        
        //CQ.form.SearchField functionality
        config = CQ.Util.applyDefaults(config, {
            "width": 300,
            "pageSize": 6,
            "minChars": 1,
            "typeAhead": false,
            "typeAheadDelay": 100,
            "validationEvent": false,
            "validateOnBlur": false,
            "displayField": "title",
            "queryParam": "fulltext",
            "treeRoot": {
                name: rootName,
                // label for the root
                text: config.rootTitle
            },
            "triggerClass": "x-form-search-trigger",
            "emptyText": CQ.I18n.getMessage("Enter search query"),
            "loadingText": CQ.I18n.getMessage("Searching..."),
            "tpl": new CQ.Ext.XTemplate(
                '<tpl for=".">',
                    '<div class="search-item" qtip="<nobr>{[CQ.shared.Util.htmlEncode(CQ.shared.XSS.getXSSTablePropertyValue(values, \"path\"))]}</nobr>">',
                        '<div class="search-thumb"',
                        ' style="background-image:url({[CQ.HTTP.externalize(CQ.shared.XSS.getXSSValue(CQ.HTTP.encodePath(values.path)), true)]}.thumb.48.48.png);"></div>' +
                        '<div class="search-text-wrapper">' +
                            '<div class="search-title">{title}</div>',
                            '<div class="search-excerpt">{excerpt}</div>',
                        '</div>',
                    '<div class="search-separator"></div>',
                    '</div>',
                '</tpl>'),
            "itemSelector": "div.search-item",
            "listeners" : {"select" : function(combo,record,index){this.setValue(record.get("path"));}}
        });
        var storeConfig = CQ.Util.applyDefaults(config.storeConfig, {
            "proxy":new CQ.Ext.data.HttpProxy( {
                "url" :"/bin/querybuilder.json",
                "method" :"GET"
            }),
            "baseParams": {
                "_charset_": "utf-8",
                "fulltext.relPath": "jcr:content/@jcr:title",
                "group.p.and": "true",
                "group.1_property": "jcr:content/cq:template",
                "group.1_.value": "/apps/televisa/templates/noticieros/video/video",
                "group.2_property": "jcr:content/video/videoPlayer",
                "group.2_.value": "8",
                "path": "/content/televisa",
                "type": "cq:Page"
            },
            "reader": new CQ.Ext.data.JsonReader({
                "id":'id',
                "root":'hits',
                "totalProperty":'results',
                "fields" : [ "name", "path",CQ.shared.XSS.getXSSPropertyName("path"), "excerpt", "title" ]
            })
        });
        config.store = new CQ.Ext.data.Store(storeConfig);
        CQ.form.SearchField.superclass.constructor.call(this, config);
    },
    doQuery : function(q, forceAll){
        q = CQ.Ext.isEmpty(q) ? '' : q;
        var qe = {
            query: q,
            forceAll: forceAll,
            combo: this,
            cancel:false
        };
        if(this.fireEvent('beforequery', qe)===false || qe.cancel){
            return false;
        }
        q = qe.query;
        forceAll = qe.forceAll;
        if(forceAll === true || (q.length >= this.minChars)){
            if(this.lastQuery !== q){
                this.lastQuery = q;
                if(this.mode == 'local'){
                    this.selectedIndex = -1;
                    if(forceAll){
                        this.store.clearFilter();
                    }else{
                        this.store.filter(this.displayField, q);
                    }
                    this.onLoad();
                }else{

                    this.store.baseParams[this.queryParam] = "*"+q+"*";
                    this.store.load({
                        params: this.getParams(q)
                    });
                    
                    this.expand();
                }
            }else{
                this.selectedIndex = -1;
                this.onLoad();
            }
        }
    },

    // private
    getParams : function(q){

        var params = {},
            paramNames = this.store.paramNames;

        if(this.pageSize){
            params[paramNames.start] = 0;
            params[paramNames.limit] = this.pageSize;
        }

        return params;
    },
    onTriggerClick : function(){//fires when the 'search' button is pressed
        //CQ.form.PathField functionality
        if (this.disabled) {
            return;
        }
        // lazy creation of browse dialog
        if (this.browseDialog == null) {
            function okHandler() {
                var path = this.getSelectedPath();
                var anchor = this.parBrowse ? this.getSelectedAnchor() : null;


                this.pathField.setValue(path);

                this.pathField.fireEvent("dialogselect", this.pathField, path, anchor);
                this.hide();
            }
            var browseDialogConfig = CQ.Util.applyDefaults(this.browseDialogCfg, {
                ok: okHandler,
                // pass this to the BrowseDialog to make in configurable from 'outside'
                parBrowse: this.parBrowse,
                treeRoot: this.treeRoot,
                treeLoader: this.treeLoader,
                listeners: {
                    hide: function() {
                        if (this.pathField) {
                            this.pathField.fireEvent("dialogclose");
                        }
                    }
                },
                loadAndShowPath: function(path) {
                    this.path = path;
                    // if the root node is the real root, we need an additional slash
                    // at the begining for selectPath() to work properly
                    if (this.pathField.rootPath == "" || this.pathField.rootPath == "/") {
                        path = "/" + path;
                    }

                    var browseDialog = this;
                    var treePanel = this.treePanel;
                    // what to do when selectPath worked
                    function successHandler(node) {
                        // ensureVisible fails on root, ie. getParentNode() == null
                        if (node.parentNode) {
                            node.ensureVisible();
                        }
                        if (browseDialog.parBrowse) {
                            browseDialog.onSelectPage(node);
                        }
                    }

                    // string split helper function
                    function substringBeforeLast(str, delim) {
                        var pos = str.lastIndexOf(delim);
                        if (pos >= 0) {
                            return str.substring(0, pos);
                        } else {
                            return str;
                        }
                    }

                    // try to handle links created by linkPattern/parLinkPattern,
                    // such as "/content/foo/bar.html#par_sys"; needs to try various
                    // cut-offs until selectPath works (eg. /content/foo/bar)
                    // 1) try full link (path)
                    treePanel.selectPath(path, null, function(success, node) {
                        if (success && node) {
                            successHandler(node);
                        } else {
                            // 2) try and split typical anchor from (par)linkPattern
                            path = substringBeforeLast(path, "#");

                            treePanel.selectPath(path, null, function(success, node) {
                                if (success && node) {
                                    successHandler(node);
                                } else {
                                    // 3) try and split typical extension from (par)linkPattern
                                    path = substringBeforeLast(path, ".");

                                    treePanel.selectPath(path, null, function(success, node) {
                                        if (success && node) {
                                            successHandler(node);
                                        }
                                    });
                                }
                            });
                        }
                    });
                },
                pathField: this
            });

            // fix dialog width for par browse to include 3 cols of pars
            if (this.parBrowse) {
                browseDialogConfig.width = 570;
            }

            // build the dialog and load its contents
            this.browseDialog = new CQ.BrowseDialog(browseDialogConfig);
        }

        this.browseDialog.loadAndShowPath(this.getValue());

        this.browseDialog.show();
        this.fireEvent("dialogopen");

    }
});

CQ.Ext.reg("brightcovevideogallerysearch", CQ.form.BrightcoveVideoGallerySearch); //Register the xtype