/*
 * Copyright 1997-2008 Day Management AG
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
 * The CQ.form.TwoColumnMultiField class implements editable list
 * component with a name and value pair
 *
 * @class CQ.form.TwoColumnMultiField
 * @extends CQ.form.MultiField
 */
CQ.form.TwoColumnMultiField = CQ.Ext.extend(CQ.form.MultiField, {
    
    
    /**
     * @cfg {Object} fieldConfig
     * The configuration options for the fields (optional).
     */

    maxItems: 0,

    /**
     * Creates a new CQ.form.MultiField.
     * @constructor
     * @param {Object} config The config object
     */
    constructor: function(config) {
        var list = this;
        maxItems = 0;
        config.saveFieldConfig = {};
        config.saveFieldConfig.name = config.name;

        if (!config.fieldConfig_0) {
            config.fieldConfig_0 = {};
        }
        if (!config.fieldConfig_0.xtype) {
            config.fieldConfig_0.xtype = "textfield";
        }
        config.fieldConfig_0.name = "";
        config.fieldConfig_0.style = "width:95%;";

        if (!config.fieldConfig_1) {
            config.fieldConfig_1 = {};
        }
        if (!config.fieldConfig_1.xtype) {
            config.fieldConfig_1.xtype = "textfield";
        }
        config.fieldConfig_1.name = "";
        config.fieldConfig_1.style = "width:95%;";

        if (config.orderable) {
            config.fieldConfig_1.orderable = config.orderable;
            config.fieldConfig_0.orderable = config.orderable;
        }
        if (!config.addItemLabel) {
            config.addItemLabel = CQ.I18n.getMessage("Add Item");
        }
        if (config.maxItems) {
            maxItems = config.maxItems;
        }

        var items = new Array();

        if(config.readOnly) {
            //if component is defined as readOnly, apply this to all items
            config.fieldConfig_0.readOnly = true;
            config.fieldConfig_1.readOnly = true;
        } else {
            items.push({
                xtype: "toolbar",
                cls: "cq-multifield-toolbar",
              
                items: [
                    "->",
                  
                    {
                        xtype: "textbutton",
                        text: config.addItemLabel,
                        style: "padding-right:6px",
                        handler:function() {
                            list.addItem();
                        }
                    },
                    {
                        xtype: "button",
                        iconCls: "cq-multifield-add",
                        template: new CQ.Ext.Template('<span><button class="x-btn" type="{0}"></button></span>'),
                        handler: function() {
                            list.addItem();
                        }
                    }
                ]
            });
        }

        items.push({
            "xtype":"hidden",
            "name":config.name + CQ.Sling.DELETE_SUFFIX
        });

        config = CQ.Util.applyDefaults(config, {
            "defaults":{
                "xtype":"multifielditem2",
                "fieldConfig_0":config.fieldConfig_0,
                "fieldConfig_1":config.fieldConfig_1,
                "saveFieldConfig":config.saveFieldConfig
            },
            "items":[
                {
                    "xtype":"panel",
                    "border":false,
                    "bodyStyle":"padding:2px",
                    "items":items
                }
            ]
        });
        CQ.form.TwoColumnMultiField.superclass.constructor.call(this,config);
        if (this.defaults.fieldConfig_0.regex) {
            // somehow regex get broken in this.defaults, so fix it
            this.defaults.fieldConfig_0.regex = config.fieldConfig_0.regex;
        }
        if (this.defaults.fieldConfig_1.regex) {
            // somehow regex get broken in this.defaults, so fix it
            this.defaults.fieldConfig_1.regex = config.fieldConfig_1.regex;
        }
        this.addEvents(
            /**
             * @event change
             * Fires when the value is changed.
             * @param {CQ.form.MultiField} this
             * @param {Mixed} newValue The new value
             * @param {Mixed} oldValue The original value
             */
            "change"
        );
        this.findParentByType("panel").addListener("show", function() {
            var tmp = this.findByType("twocolumnmultifield");
            var multifield = null;
            if ( tmp.length > 0 ) multifield = tmp[0];
            if (multifield != null) {
                multifield.doLayout();
                var itemsTmp = multifield.findByType("multifielditem2");
                for ( var i = 0; i < itemsTmp.length; i ++ ) {
                    var item = itemsTmp[i];
                    if (item.field.isXType("trigger")) {
                        if (item.field2.getWidth() > 15) {
                            item.field.setWidth(item.field2.getWidth());
                        } else {
                            item.field.setWidth(134);
                        }
                        item.field.wrap.setWidth(item.field.getWidth()+"px");
                    }
                    if (item.field2.isXType("trigger")) {
                        if (item.field.getWidth() > 15) {
                            item.field2.setWidth(item.field.getWidth());
                        } else {
                            item.field2.setWidth(134);
                        }

                        item.field2.wrap.setWidth(item.field2.getWidth()+"px");
                    }
                }
            }
            /**/
        });
    },


    /**
     * Adds a new field to the widget.
     * @param value The value of the field
     */
    addItem: function(value,value2) {

        if (maxItems > 0 && this.items.getCount() > maxItems) {
            var msg = CQ.I18n.getMessage("You have reached the maximum configured limit of ") + maxItems + CQ.I18n.getMessage(" items.");
            CQ.Ext.Msg.show({
                title:CQ.I18n.getMessage('Maximum Limit Reached'),
                msg: msg,
                buttons: CQ.Ext.Msg.OK,
                fn: null,
                icon: CQ.Ext.MessageBox.WARNING
            });
            return;
        }

        var item = this.insert(this.items.getCount() - 1, {});

        var parent = item.ownerCt;
        var index = parent.items.indexOf(item);
        if(index > 0){
            var previousItem = parent.items.itemAt(index - 1)
        }

        this.doLayout();
        if (value) {
            item.setValue(value);
        }
        if (value2) {
            item.setValue2(value2);
        }
        if (item.field.isXType("trigger")) {
            item.field.wrap.setWidth(item.field2.getWidth()+"px");
        }
        if (item.field2.isXType("trigger")) {
            item.field2.wrap.setWidth(item.field2.getWidth()+"px");
        }
    },

    /**
     * Returns the data value.
     * @return {String[]} value The field value
     */
    getValue: function() {
        var value = new Array();
        this.items.each(function(item, index/*, length*/) {
            if (item instanceof CQ.form.MultiField.Item2) {
                value[index] = item.getSavedValue();
                index++;
            }
        }, this);
        return value;
    },

    /**
     * Sets a data value into the field and validates it.
     * @param {Mixed} value The value to set
     */
    setValue: function(value) {
        this.fireEvent("change", this, value, this.getValue());
        var oldItems = this.items;
        oldItems.each(function(item/*, index, length*/) {
            if (item instanceof CQ.form.MultiField.Item2) {
                this.remove(item, true);
                this.findParentByType("form").getForm().remove(item);
            }
        }, this);
        this.doLayout();
        if ((value != null) && (value != "")) {
            if (value instanceof Array || CQ.Ext.isArray(value)) {
                //window.alert("I'm an array - value : " + value.toString());
                for (var i = 0; i < value.length; i++) {
                    var str = value[i].toString();
                    if(str.indexOf("||") > -1){
                        var tmp = str.split("||");
                        this.addItem(tmp[0],tmp[1]);
                    }else{
                        this.addItem(str);
                    }
                }
            } else {
                var str = value;
                if(str.indexOf("||") > -1){
                    var tmp = str.split("||");
                    this.addItem(tmp[0],tmp[1]);
                }else{
                    this.addItem(str);
                }
            }
        }
    }

});

CQ.Ext.reg("twocolumnmultifield", CQ.form.TwoColumnMultiField);

/**
 * The CQ.form.MultiField.Item class represents an item in a
 * CQ.form.MultiField. This class is not intended for direct use.
 *
 * @private
 * @class CQ.form.MultiField.Item
 * @extends CQ.Ext.Panel
 */
CQ.form.MultiField.Item2 = CQ.Ext.extend(CQ.Ext.Panel, {

    /**
     * Creates a new CQ.form.MultiField.Item.
     * @constructor
     * @param {Object} config The config object
     */
    constructor: function(config) {
        var item = this;
        this.field = CQ.Util.build(config.fieldConfig_0, true);
        this.saveField = CQ.Util.build(config.fieldConfig_0, true);
        this.saveField.name = config.saveFieldConfig.name;

        if (!config.fieldConfig_1){
            this.field2 = CQ.Util.build(config.fieldConfig_0, true);
        }else{
            this.field2 = CQ.Util.build(config.fieldConfig_1, true);
        }

        this.field.addListener("valid", function(){
            var value = item.field.getValue();
            var value2 = item.field2.getValue();
            item.saveField.setValue( new String(value + "||" + value2) );
        }, this, {});

        this.field2.addListener("valid", function(){
            var value = item.field.getValue();
            var value2 = item.field2.getValue();
            item.saveField.setValue( new String(value + "||" + value2) );
        }, this, {});

        var columns = 3;
        var items = new Array();

        items.push({
            "xtype":"panel",
            "border":false,
            "items":item.field
        });

        items.push({
            "xtype":"panel",
            "border":false,
            "items":item.field2
        });

        if(!config.fieldConfig_0.readOnly) {
            items.push({
                "xtype": "panel",
                "border": false,
                "width": "25px",
                "items": {
                    "xtype": "button",

                    "iconCls": "cq-multifield-up",
                    "template": new CQ.Ext.Template('<span><button class="x-btn" type="{0}"></button></span>'),
                    "handler": function(){
                        var parent = item.ownerCt;
                        var index = parent.items.indexOf(item);

                        if (index > 0) {
                            item.reorder(parent.items.itemAt(index - 1));
                        }
                    }
                }
            });
            items.push({
                "xtype": "panel",
                "border": false,
                "width": "25px",
                "items": {
                    "xtype": "button",
                    "iconCls": "cq-multifield-down",
                    "template": new CQ.Ext.Template('<span><button class="x-btn" type="{0}"></button></span>'),
                    "handler": function(){
                        var parent = item.ownerCt;
                        var index = parent.items.indexOf(item);

                        if (index < parent.items.getCount() - 1) {
                            item.reorder(parent.items.itemAt(index + 1));
                        }
                    }
                }
            });
            columns += 2;

            items.push({
                "xtype":"panel",
                "border":false,
                "width": "25px",
                "items":{
                    "xtype":"button",
                    "iconCls": "cq-multifield-remove",
                    "template": new CQ.Ext.Template('<span><button class="x-btn" type="{0}"></button></span>'),
                    "handler":function() {
                        item.ownerCt.remove(item);
                    }
                }
            });

            columns += 1;
        }

        items.push({
            "xtype":"panel",
            "border":false,
            "style":"border:0px;padding:0px;",
            "items":item.saveField,
            "width":0,
            "height":0
        });

        config = CQ.Util.applyDefaults(config, {
            "layout":"table",
            "border":false,
            
            "anchor":"100%",
            "layoutConfig":{
                "columns":columns
            },
            "defaults":{
                "bodyStyle":"padding:3px",
                "width":"190px",
            },
            "items":items
        });
        CQ.form.MultiField.Item2.superclass.constructor.call(this, config);

        if (config.value) {
            this.field.setValue(config.value);
        }
    },

    /**
     * Reorders the item above the specified item.
     * @param item The item to reorder above
     */
    reorder: function(item) {
        var value = item.field.getValue();
        var value2 = item.field2.getValue();
        item.field.setValue(this.field.getValue());
        item.field2.setValue(this.field2.getValue());
        this.field.setValue(value);
        this.field2.setValue(value2);
        this.saveField.setValue(new String(value + "||" + value2));
    },

    /**
     * Returns the data value.
     * @return {String} value The field value
     */
    getValue: function() {
        var value = item.field.getValue();
        var value2 = item.field2.getValue();
        this.saveField.setValue(new String(value + "||" + value2));
        return this.field.getValue();
    },

    /**
     * Returns the data value.
     * @return {String} value The field value
     */
    getValue2: function() {
        var value = item.field.getValue();
        var value2 = item.field2.getValue();
        this.saveField.setValue(new String(value + "||" + value2));
        return this.field2.getValue();
    },

    getSavedValue: function() {
        return this.saveField.getValue();
    },

    /**
     * Sets a data value into the field and validates it.
     * @param {String} value The value to set
     */
    setValue: function(value) {
        this.field.setValue(value);
        var value = (this.field.getValue()) ? this.field.getValue() : "";
        var value2 = (this.field2.getValue()) ? this.field2.getValue() : "";
        this.saveField.setValue(new String(value + "||" + value2));
        this.doLayout();
    },

    /**
     * Sets a data value into the field and validates it.
     * @param {String} value The value to set
     */
    setValue2: function(value) {
        this.field2.setValue(value);
        var value = (this.field.getValue()) ? this.field.getValue() : "";
        var value2 = (this.field2.getValue()) ? this.field2.getValue() : "";
        this.saveField.setValue(new String(value + "||" + value2));
        this.doLayout();
    }

});

CQ.Ext.reg("multifielditem2", CQ.form.MultiField.Item2);