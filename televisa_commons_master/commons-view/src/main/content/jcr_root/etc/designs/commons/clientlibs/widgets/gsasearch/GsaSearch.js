CQ.form.GsaSearch = CQ.Ext.extend(CQ.Ext.form.ComboBox, {

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
            "displayField": "title", //property to be displayed in the dialog.
            //"queryParam": "statement",
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
                    '<div class="search-item" qtip="<nobr>{[CQ.shared.Util.htmlEncode(CQ.shared.XSS.getXSSTablePropertyValue(values, \"link\"))]}</nobr>">',
                        '<div class="search-thumb"',
                        ' style="background-image:url({[CQ.HTTP.externalize(CQ.shared.XSS.getXSSValue(CQ.HTTP.encodePath(values.thumbnail)), true)]});"></div>' ,
                        '<div class="search-text-wrapper">' ,
                            '<div class="search-title">{title}</div>',
                        '</div>',
                    '<div class="search-separator"></div>',
                    '</div>',
                '</tpl>'),
            "itemSelector": "div.search-item",
            "listeners" : {"select" : function(combo,record,index){  

                                                this.findParentByType("panel").previousSibling().getComponent( this.name.substring(2) + "_image_url").setValue(record.get("thumbnail"));
                                                this.findParentByType("panel").previousSibling().getComponent( this.name.substring(2) + "_url").setValue(record.get("link"));
                                                this.findParentByType("panel").previousSibling().getComponent( this.name.substring(2) + "_title").setValue(record.get("title"));
            }}  // used to set hidden variables
        });
        var storeConfig = CQ.Util.applyDefaults(config.storeConfig, {
            "proxy":new CQ.Ext.data.HttpProxy ( { //ScriptTagProxy
                "url" :"/bin/gsaproxy.json",
                "method" :"GET"
            }),
            "baseParams": {
                "_charset_": "utf-8", 
            },
            "reader":new CQ.Ext.data.JsonReader({
                "id":"link",
                "root":"items",
                "fields" : [ "title", "link", "thumbnail" ]
            })
        });
        config.store = new CQ.Ext.data.Store(storeConfig);
        CQ.form.SearchField.superclass.constructor.call(this, config);
    },
    onKeyUp : function(e){
      //do nothing =)
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
            if(this.lastQuery != q){
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

                    this.store.baseParams[this.queryParam] = q;
                    this.store.load({
                        params: this.getParams(q)
                    });

                    this.expand();
                    
                 //   debugger;
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
     if(this.readOnly || this.disabled){
            return;
        }
        if(this.isExpanded()){
            this.collapse();
            this.el.focus();
        }else {
            this.onFocus({});
            if(this.triggerAction == 'all') {
                this.doQuery(this.allQuery, true);
            } else {
                this.doQuery(this.getRawValue());
            }
            this.el.focus();
        }    
    }
});

CQ.Ext.reg("gsasearch", CQ.form.GsaSearch); //Register the xtype 
