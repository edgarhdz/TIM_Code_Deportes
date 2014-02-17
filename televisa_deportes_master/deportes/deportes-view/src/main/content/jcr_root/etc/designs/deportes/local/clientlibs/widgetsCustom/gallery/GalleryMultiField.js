CQ.form.GalleryMultiField = CQ.Ext.extend(CQ.form.MultiField, {

    originalItems: null,

    numberOfItems: null,

    constructor: function (config) {
        var list = this;
        if (typeof config.orderable === "undefined") {
            config.orderable = true;
        }

        if (!config.fieldConfig) {
            config.fieldConfig = {};
        }
        if (!config.fieldConfig.xtype) {
            config.fieldConfig.xtype = "textfield";
        }
        config.fieldConfig.name = config.name;
        config.fieldConfig.ownerCt = this;
        config.fieldConfig.orderable = config.orderable;


        if (!config.value) {
            config.value = 0;
        }

        var items = new Array();
        config.fieldConfig.dragDropMode = config.dragDropMode;

        this.hiddenDeleteField = new CQ.Ext.form.Hidden({
            "name": config.name + CQ.Sling.DELETE_SUFFIX
        });
        items.push(this.hiddenDeleteField);

        if (config.typeHint) {
            this.typeHintField = new CQ.Ext.form.Hidden({
                name: config.name + CQ.Sling.TYPEHINT_SUFFIX,
                value: config.typeHint + "[]"
            });
            items.push(this.typeHintField);
        }

        if (!config.addItemLabel) {
            config.addItemLabel = CQ.I18n.getMessage("Add Item");
        }

        this.numberOfItems = new CQ.Ext.form.Hidden({
            "name": config.galleryMultiFieldName,
            "value": config.value
        });
        items.push(this.numberOfItems);

        if (config.readOnly) {
            config.fieldConfig.readOnly = true;
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
                        handler: function () {
                            list.addItem();
                        }
                    },
                    {
                        xtype: "button",
                        iconCls: "cq-multifield-add",
                        template: new CQ.Ext.Template('<span><button class="x-btn" type="{0}"></button></span>'),
                        handler: function () {
                            list.addItem();
                        }
                    }
                ]
            });
        }

        config = CQ.Util.applyDefaults(config, {
            "defaults": {
                "xtype": "gallerymultifielditem",
                "fieldConfig": config.fieldConfig
            },
            "items": [
                {
                    "xtype": "panel",
                    "border": false,
                    "bodyStyle": "padding:" + this.bodyPadding + "px",
                    "items": items
                }
            ]
        });

        CQ.form.MultiField.superclass.constructor.call(this, config);
        if (this.defaults.fieldConfig.regex) {
            // somehow regex get broken in this.defaults, so fix it
            this.defaults.fieldConfig.regex = config.fieldConfig.regex;
        }

        this.addEvents(
            /**
             * @event change
             * Fires when the value is changed.
             * @param {CQ.form.MultiField} this
             * @param {Mixed} newValue The new value
             * @param {Mixed} oldValue The original value
             */
            "change",
            /**
             * @event removeditem
             * Fires when an item is removed.
             * @param {CQ.form.MultiField} this
             */
            "removeditem"
        );
    },

    /**
     * Adds a new field with the specified value to the list.
     * @param {String} value The value of the field
     */
    addItem: function (value) {
        var index = this.items.getCount() - 1;
        var item = this.insert(this.items.getCount() - 1, {});
        var form = this.findParentByType("form");
        if (form)
            item.field.index = index;
        form.getForm().add(item.field);
        this.doLayout();
        if (item.field.processPath) item.field.processPath(this.path);
        if (value) {
            item.setValue(value);
        }

        if (this.fieldWidth < 0) {
            // fieldWidth is < 0 when e.g. the MultiField is on a hidden tab page;
            // do not set width but wait for resize event triggered when the tab page is shown
            return;
        }
        if (!this.fieldWidth) {
            this.calculateFieldWidth(item);
        }
        try {
            item.field.setWidth(this.fieldWidth);
        }
        catch (e) {
            CQ.Log.debug("CQ.form.MultiField#addItem: " + e.message);
        }
    },

});

CQ.Ext.reg("gallerymultifield", CQ.form.GalleryMultiField);
