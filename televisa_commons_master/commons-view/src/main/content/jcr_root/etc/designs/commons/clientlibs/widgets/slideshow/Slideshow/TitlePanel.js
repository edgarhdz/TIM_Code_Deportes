/*
 * Copyright 1997-2009 Day Management AG
 * Barfuesserplatz 6, 4001 Basel, Switzerland
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Day Management AG, ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Day.
 */

/**
 * @class CQ.form.Slideshow.TitlePanel
 * @extends CQ.Ext.Panel
 * @private
 * The TitlePanel provides the UI to edit the title of each slide.
 * @constructor
 * Creates a new TitlePanel.
 * @param {Object} config The config object
 */
CQ.form.Slideshow.TitlePanel = CQ.Ext.extend(CQ.Ext.Panel, {

    constructor: function(config) {

        config = config || { };
        var defaults = {
            "layout": "table",


            "layoutConfig": {
                "columns": 2
            },
            "defaults": {
                "style": "padding: 4px; font-size: 12px;"

            },
            "minSize": 170,
            "maxSize": 170,
            "height": 100,
            "items": [{
                    "itemId": "titleLabel",
                    "xtype": "label",
                    "text": "Title"
                }, {
                    "itemId": "titlePanel",
                    "xtype": "panel",
                    "layout": "fit",
                    "border": false,
                    "hideBorders": true,
                    "items": [{
                            "itemId": "title",
                        	"boxMaxWidth": 650,
                            "xtype": "textfield"
                        }
                    ]
                },{
                    "itemId": "linkLabel",
                    "xtype": "label",
                    "text": "URL"
                }, {

                    "itemId": "linkPanel",
                    "xtype": "panel",
                    "layout": "fit",
                    "border": false,
                    "hideBorders": true,
                    "items": [{
                            "itemId": "link",
                        	"boxMaxWidth": 650,
                            "xtype": "textfield"
                        }
                    ]
                }, {
                    "itemId": "headlineLabel",
                    "xtype": "label",
                    "text": "Status"
                }, {
                    "itemId": "headlinePanel",
                    "xtype": "panel",
                    "layout": "fit",
                    "border": false,
                    "hideBorders": true,
                    "items": [{
                            "itemId": "headline",
                            "boxMaxWidth": 650,
                            "defaultValue":"Activated",
                            "type": "select",
                            "xtype": "selection",
                            "options":[{
                                    "text":"Activated",
                                    "value":"Activated"
                                },
                                {
                                    "text":"Deactivated",
                                    "value":"Deactivated"
                            }]
                        }
                    ]
                }

            ],
            "listeners": {
                "bodyresize": {
                    "fn": function(comp, w, h) {
                        //this.adjustTitleWidth(w);
                        this.adjustItemsWidth(w);
                    },
                    "scope": this
                }
            }
        };

        CQ.Util.applyDefaults(config, defaults);
        CQ.form.Slideshow.TitlePanel.superclass.constructor.call(this, config);

    },

    initComponent: function() {
        CQ.form.Slideshow.SlidesPanel.superclass.initComponent.call(this);
    },

    afterRender: function() {
        CQ.form.Slideshow.TitlePanel.superclass.afterRender.call(this);
        this.el.setVisibilityMode(CQ.Ext.Element.DISPLAY);
        this.body.setVisibilityMode(CQ.Ext.Element.DISPLAY);
    },



    adjustTitleWidth: function(width) {


        if (width) {
            var titlePanel = this.items.get("titlePanel");
            // var selector = titlePanel.items.get("selector");
            var titleLabel = this.items.get("titleLabel");


            if (titleLabel.rendered) {
				var titleWidth = width - titleLabel.getEl().getWidth();
                titlePanel.setSize(titleWidth, 80);

            } else {
                this._width = width;
            }
        }
    },

    adjustItemsWidth: function(width){
        if(width){

			var titleLabel = this.items.get("titleLabel");
            var titlePanel = this.items.get("titlePanel");

            var headlineLabel = this.items.get("headlineLabel");
            var headlinePanel = this.items.get("headlinePanel");

            //var descriptionLabel = this.items.get("descriptionLabel");
            //var descriptionPanel = this.items.get("descriptionPanel");

			var linkLabel = this.items.get("linkLabel");
            var linkPanel = this.items.get("linkPanel"); 

            if (titleLabel.rendered && headlineLabel.rendered  && linkLabel.rendered) {

                var titleWidth = width - titleLabel.getEl().getWidth();
                var headlineWidth = width - headlineLabel.getEl().getWidth();
                //var descriptionWidth = width - descriptionLabel.getEl().getWidth();
                var linkWidth = width - linkLabel.getEl().getWidth();

                titlePanel.setSize(width, 30);
                headlinePanel.setSize(width, 30);
               //descriptionPanel.setSize(width, 80);
                linkPanel.setSize(width, 30);

            }else {
                this._width = width;
            }

        }
    },

    setTitle: function(title) {
        var titlePanel = this.items.get("titlePanel");
        var titleField = titlePanel.items.get("title");
        titleField.setValue(title ? title : "");
    },

    getTitle: function() {
        var titlePanel = this.items.get("titlePanel");
        var titleField = titlePanel.items.get("title");
        return titleField.getValue();
    },




    setHeadline: function(headline) {
        var headlinePanel = this.items.get("headlinePanel");
        var headlineField = headlinePanel.items.get("headline");
        headlineField.setValue(headline ? headline : "");
    },

    getHeadline: function() {
        var headlinePanel = this.items.get("headlinePanel");
        var headlineField = headlinePanel.items.get("headline");
        return headlineField.getValue();
    },

    /*
    setDescription: function(description) {
        var descriptionPanel = this.items.get("descriptionPanel");
        var descriptionField = descriptionPanel.items.get("description");
        descriptionField.setValue(description ? description : "");
    },

    getDescription: function() {
        var descriptionPanel = this.items.get("descriptionPanel");
        var descriptionField = descriptionPanel.items.get("description");
        return descriptionField.getValue();
    },
*/

    setLink: function(link) {
        var linkPanel = this.items.get("linkPanel");
        var linkField = linkPanel.items.get("link");
        linkField.setValue(link ? link : "");
    },

    getLink: function() {
        var linkPanel = this.items.get("linkPanel");
        var linkField = linkPanel.items.get("link");
        return linkField.getValue();
    },





    disableFormElements: function() {
        var titlePanel = this.items.get("titlePanel");
        var titleField = titlePanel.items.get("title");
        titleField.disable();

		var headlinePanel = this.items.get("headlinePanel");
        var headlineField = headlinePanel.items.get("headline");
        headlineField.disable();

        /*
        var descriptionPanel = this.items.get("descriptionPanel");
        var descriptionField = descriptionPanel.items.get("description");
        descriptionField.disable();
        */

        var linkPanel = this.items.get("linkPanel");
        var linkField = linkPanel.items.get("link");
        linkField.disable();

    },

    enableFormElements: function() {
        var titlePanel = this.items.get("titlePanel");
        var titleField = titlePanel.items.get("title");
        titleField.enable();

		var headlinePanel = this.items.get("headlinePanel");
        var headlineField = headlinePanel.items.get("headline");
        headlineField.enable();

        /*
        var descriptionPanel = this.items.get("descriptionPanel");
        var descriptionField = descriptionPanel.items.get("description");
        descriptionField.enable();
        */

        var linkPanel = this.items.get("linkPanel");
        var linkField = linkPanel.items.get("link");
        linkField.enable();


    }

});