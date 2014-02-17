CQ.form.PhotoGallerySearch = CQ.Ext.extend(CQ.Ext.form.ComboBox, {

constructor : function(config) {

        var prepositions = ['a','ante','bajo','cabe','con','de','desde','contra','en','entre','hacia','hasta','para','por','según','sin','so','sobre','tras','durante', 'mediante'];
        var articles = ['el','la','los','las','lo','un','una','unos','unas', 'uno','al','del'];
        var articlesConjunctions = ['y','e','ni','mas','pero','sino','que','o','u','porque' ,'pues','puesto que','si','con tal que','siempre que','aunque','si bien','así','por lo tanto','como','tal como','tan','tanto que','así que','cuando','antes que','para que','a fin de que'];

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
            "minChars": 30,
            "typeAhead": false,
            "typeAheadDelay": 100,
            "validationEvent": false,
            "validateOnBlur": false,
            "displayField": "title",
            "queryParam": "query",
            "enableKeyEvents" : true,
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
                        ' style="background-image:url(\'{image}\')!important;"></div>' +

                        '<div class="search-text-wrapper">' +
                            '<div class="search-title">{title}</div>',
                            '<div class="search-excerpt">{excerpt}</div>',
                            '<div style="text-align:right; color:gray; padding-top: 3px;">Publicado el {published}</div>',

                        '</div>',
                    '<div class="search-separator"></div>',
                    '</div>',
                '</tpl>'),
            "itemSelector": "div.search-item",
            "listeners" : {"select" : function(combo,record,index){this.setValue(record.get("path"));}, "keypress" : function(textField, e){
             if(e.button == 12 || e.button == 31){
                   var validSearchValue = true;
				   var searchValue = new String(textField.getValue()).toLowerCase();

                   for (var i=0; i < prepositions.length; i++){
                       if(validSearchValue){
                           if(searchValue == new String(prepositions[i]).toLowerCase()){
						   		validSearchValue = false;
                           }
                       }
                    }

                   for (var i=0; i < articles.length; i++){
                       if(validSearchValue){
                           if(searchValue== new String(articles[i]).toLowerCase()){
						   		validSearchValue = false;
                           }
                       }
                   }

                   for (var i=0; i < articlesConjunctions.length; i++){
                       if(validSearchValue){
                           if(searchValue == new String(articlesConjunctions[i]).toLowerCase()){
						   		validSearchValue = false;
                           }
                       }
                   }

	               if(validSearchValue){
				   		this.doQuery(textField.getValue(), true);
                   }
            	}
        	}}
        });
        var storeConfig = CQ.Util.applyDefaults(config.storeConfig, {
            "proxy":new CQ.Ext.data.HttpProxy( {
                "url" :"/bin/generics/articlesearch.gallery.json",
                "method" :"GET"
            }),
            "baseParams": {
                "_charset_": "utf-8",

            },
            "reader": new CQ.Ext.data.JsonReader({
                "id":'id',
                "root":'hits',
                "totalProperty":'results',
                "fields" : [ "name", "path",CQ.shared.XSS.getXSSPropertyName("path"), "excerpt", "title", "image", "published" ]
            })
        });
        config.store = new CQ.Ext.data.Store(storeConfig);
        CQ.form.SearchField.superclass.constructor.call(this, config);
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

    },
    validator : function(){
		var isValid;
        var validation = searchFieldValidation("/apps/deportes/local/templates/photo/gallery", this.getValue());
        if(!validation){
			isValid = CQ.I18n.getMessage("generics.searchfieldvalidation.photogallery");
        }else{
			isValid = true;
        }
        return isValid;
    }





});

CQ.Ext.reg("photogallerysearch", CQ.form.PhotoGallerySearch); //Register the xtype