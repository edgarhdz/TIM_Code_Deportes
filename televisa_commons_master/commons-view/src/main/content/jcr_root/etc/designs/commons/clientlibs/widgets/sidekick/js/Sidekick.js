// Override the sidekick to add the publish button
CQ.Ext.override(CQ.wcm.Sidekick, {
    
     initButtons: function(path, config) {
        this.reloadButton = new CQ.Ext.Button({
            "iconCls": "cq-sidekick-reload",
            "tooltip": {
                "title": config.reloadText,
                "text": CQ.I18n.getMessage("Reload the page"),
                "autoHide": true
            },
            "handler": function() {
                CQ.Util.reload(CQ.WCM.getContentWindow());
            }
        });
        // custom button
        this.publishButton = new CQ.Ext.Button({
            "iconCls": "cq-sidekick-publish",
            "tooltip": {
                "title": CQ.I18n.getMessage("generics.sidekick.publish.title"),
                "text": CQ.I18n.getMessage("generics.sidekick.publish.text"),
                "autoHide": true
            },
            "handler": function() {
                
                var path = CQ.WCM.getPagePath(); //the page path e.g. /content/televisa/noticieros/mundo/1304/example.html
                var response = CQ.utils.HTTP.get('/bin/externalizer.json?path=' + path); // ajax request to the externalizer service
                //the response will be a json object containing the externalized url e.g. http://localhost:4503/content/televisa/noticieros/mundo/1304/example.html
                //the host is defined in the Day CQ Link Externalizer service. It can be changed.
                eval('var data =' + response.responseText); //parse the response text
                window.open(data.url); //open the link
            }
        });
        
        this.previewButton = new CQ.Ext.Button({
            "iconCls": "cq-sidekick-preview",
            "tooltip": {
                "title": config.previewText,
                "text": CQ.I18n.getMessage("Switch to preview mode"),
                "autoHide": true
            },
            "pressed": CQ.WCM.isPreviewMode(),
            "toggleGroup": "wcmMode",
            "enableToggle": true,
            "handler": function() {
                if (!CQ.WCM.isPreviewMode()) {
                    this.wcmMode = CQ.WCM.setMode(CQ.WCM.MODE_PREVIEW);
                    if (this.previewReload || CQ.utils.Form.requiresReloadForPreview()) {
                        CQ.Util.reload(CQ.WCM.getContentWindow());
                    } else {
                        CQ.WCM.getContentWindow().CQ.WCM.hide();
                    }
                    this.collapse();
                } else {
                    // make sure the button stays pressed
                    this.previewButton.toggle(true);
                }
            },
            "scope": this
        });
        this.scaffoldingButton = new CQ.Ext.Button({
            "iconCls": "cq-sidekick-scaffolding",
            "tooltip": {
                "title": config.scaffoldingText,
                "text": CQ.I18n.getMessage("Switch to scaffolding mode"),
                "autoHide": true
            },
            "pressed": CQ.WCM.getContentUrl().indexOf(".scaffolding.") > 0,
            "handler": function() {
                var scMode = CQ.WCM.getContentUrl().indexOf(".scaffolding.") > 0;
                if (!scMode) {
                    //this.wcmMode = CQ.WCM.setMode(CQ.WCM.MODE_PREVIEW);
                    CQ.Util.reload(CQ.WCM.getContentWindow(),
                        CQ.HTTP.externalize(this.path + ".scaffolding.html"));
                    this.collapse();
//                } else {
//                    // make sure the button stays pressed
//                    this.scaffoldingButton.toggle(true);
                }
            },
            "scope": this
        });

        var editButtonDefault = {
            "iconCls": "cq-sidekick-edit",
            "tooltip": {
                "title": config.editText,
                "text": CQ.I18n.getMessage("Switch to edit mode"),
                "autoHide": true
            },
            "pressed": CQ.WCM.isEditMode(),
            "toggleGroup": "wcmMode",
            "enableToggle": true,
            "handler": function() {
                if (!CQ.WCM.isEditMode()) {
                    this.wcmMode = CQ.WCM.setMode(CQ.WCM.MODE_EDIT);
                    CQ.WCM.getContentWindow().CQ.WCM.show();
                } else {
                    this.editButton.toggle(true);
                }
            },
            "scope": this
        };

        if( path ) {
            var pageInfo = CQ.WCM.getPageInfo(path);

            if( pageInfo && pageInfo["emulators"]) {

                var cfg = pageInfo["emulators"];
                var items = [];

                //convert emulator info into proper combo button config

                if (cfg.groups) {
                    for (var groupName in cfg.groups) {
                        var group = cfg.groups[groupName];

                        items.push({
                            "xtype": "titleseparator",
                            "title": CQ.I18n.getVarMessage(group.title)
                        });

                        var emulators = [];
                        for (var emulatorName in group) {

                            var emulator = group[emulatorName];

                            // skip non-emulators
                            if (null == emulator || !emulator.path) {
                                continue;
                            }

                            items.push({
                                "text": CQ.I18n.getVarMessage(emulator.text),
                                "icon": emulator.path
                                        ? CQ.HTTP.externalize(emulator.path + "/resources/sidekick-icon-" + emulatorName + ".png")
                                        : CQ.HTTP.externalize("/libs/cq/ui/resources/0.gif"),
                                "checked": emulator.isDefault === true || emulator.isDefault == "true",
                                "group": "emulators",
                                "value": emulatorName,
                                "action": emulator.action,
                                "checkHandler": function(item, checked) {
                                    var emulatorMgr = CQ.WCM.getEmulatorManager();
                                    if( emulatorMgr ) {
                                        if(checked) {
                                            if( this.action == "start") {
                                                emulatorMgr.switchEmulator(this.value);
                                            } else {
                                                emulatorMgr.stopEmulator();
                                            }
                                        }
                                    }
                                }
                            });
                        }
                    }
                } else {
                    for(var name in cfg) {
                        var c = cfg[name];
                        items.push({
                            "text": c.text,
                            "icon": c.path ?
                                    c.path + "/resources/sidekick-icon-" + name + ".png" :
                                    "/libs/cq/ui/resources/0.gif",
                            "checked": c.isDefault === true || c.isDefault == "true",
                            "group": "emulators",
                            "value": name,
                            "action": c.action,
                            "checkHandler": function(item, checked) {
                                var emulatorMgr = CQ.WCM.getEmulatorManager();
                                if( emulatorMgr ) {
                                    if(checked) {
                                        if( this.action == "start") {
                                            emulatorMgr.switchEmulator(this.value);
                                        } else {
                                            emulatorMgr.stopEmulator();
                                        }
                                    }
                                }
                            }
                        });
                    }
                }

                if (items.length > 0) {
                    editButtonDefault = CQ.Util.applyDefaults({
                        "menu": {
                            "items": items
                        }
                    }, editButtonDefault);
                }
            }
        }

        this.editButton = new CQ.Ext.Button(editButtonDefault);
        this.designButton = new CQ.Ext.Button({
            "iconCls": "cq-sidekick-design",
            "tooltip": {
                "title": config.designText,
                "text": CQ.I18n.getMessage("Switch to design mode"),
                "autoHide": true
            },
            "pressed": CQ.WCM.isDesignMode(),
            "toggleGroup": "wcmMode",
            "enableToggle": true,
            "handler": function() {
                if (!CQ.WCM.isDesignMode()) {
                    this.wcmMode = CQ.WCM.setMode(CQ.WCM.MODE_DESIGN);
                    CQ.Util.reload(CQ.WCM.getContentWindow());
                    this.collapse();
                } else {
                    this.designButton.toggle(true);
                }
            },
            "scope": this
        });
        var isMisc = path.indexOf("/etc/") == 0;
        this.adminButton = new CQ.Ext.Button({
            "iconCls": isMisc
                    ? "cq-sidekick-miscadmin"
                    : "cq-sidekick-siteadmin",
            "tooltip": {
                "title": isMisc
                        ? config.miscText
                        : config.adminText,
                "text": isMisc
                        ? CQ.I18n.getMessage("Go to Tools")
                        : CQ.I18n.getMessage("Go to Websites"),
                "autoHide": true
            },
            "handler": function(cmp, evt) {
                // check for multi window mode
                if (this.multiWinMode == undefined) {
                    var wm = CQ.User.getCurrentUser().getPreference("winMode");
                    this.multiWinMode = (wm != "single");
                }
                var adminUrl = CQ.HTTP.externalize(config.adminUrl);
                var path = this.getPath();
                if (path.indexOf("/etc/") == 0) {
                    adminUrl = CQ.HTTP.externalize(config.miscUrl);
                }
                path = path.substring(0, path.lastIndexOf("/"));
                adminUrl = CQ.HTTP.setAnchor(adminUrl, path);
                if (evt.shiftKey || this.multiWinMode) {
                    window.open(adminUrl);
                } else {
                    window.location.href = adminUrl;
                }
            },
            "scope": this
        });
        this.clientContextButton = new CQ.Ext.Button({
            "iconCls": "cq-sidekick-clientcontext",
            "hidden": true,
            "tooltip": {
                "title": config.clientContextText,
                "text": CQ.I18n.getMessage("Show the Client Context"),
                "autoHide": true
            },
            "handler": function(cmp, evt) {
                var win = CQ.WCM.getContentWindow();
                var analytics = win ? win["CQ_Analytics"] : null;
                var isClientContext = analytics && analytics.ClientContextUI && analytics.ClientContextUI.isAvailable();
                if( isClientContext ) {
                    analytics.ClientContextUI.toggle();
                }
            },
            "scope": this
        });

        this.liveCopyStatusButton = new CQ.Ext.Button({
            "disabled": false,
            "iconCls": "cq-sidekick-layer-livecopy",
            "tooltip": {
                "title": config.liveCopyStatusText,
                "text": CQ.I18n.getMessage("Display Live Copy status"),
                "autoHide": true
            },
            "handler": function() {
                if (CQ.WCM.isShownLayer(CQ.utils.WCM.LAYER_LCSTATUS)) {
                    CQ.WCM.hideLayer(CQ.utils.WCM.LAYER_LCSTATUS);
                } else {
                    CQ.WCM.showLayer(CQ.utils.WCM.LAYER_LCSTATUS);
                }
                this.liveCopyStatusButton.toggle(CQ.WCM.isShownLayer(CQ.utils.WCM.LAYER_LCSTATUS));
            },
            "scope": this
        });
        
        this.analyticsButton = new CQ.Ext.Button({
            "disabled": false,
            "hidden": !(CQ.WCM.getContentWindow()["CQ_Analytics"] && CQ.WCM.getContentWindow()["CQ_Analytics"]["Sitecatalyst"]),
            "iconCls": "cq-sidekick-sitecatalyst",
            "tooltip": {
                "title": config.sitecatalystText,
                "text": CQ.I18n.getMessage("Open SiteCatalyst"),
                "autoHide": true
            },
            "handler": function() {
                var win = CQ.WCM.getContentWindow();
                var analytics = win ? win["CQ_Analytics"] : null;
                var isSiteCatalyst = analytics && analytics.Sitecatalyst;
                if(isSiteCatalyst) {
                    analytics.Sitecatalyst.openServiceUrl();
                }
            }
        });

    },
    addButtons: function() {
        var bbar = this.getBottomToolbar();
        bbar.removeAll(true);
        bbar.add([
            this.editButton,
            this.previewButton,
            this.designButton,
            this.scaffoldingButton,
            this.liveCopyStatusButton,
            this.clientContextButton,
            "-",
            this.adminButton,
            this.analyticsButton,
            this.publishButton,
            this.reloadButton
        ]);
    }
});