/**
 * @private
 * @class CQ.form.GalleryMultiField.Item
 * @extends CQ.form.MultiField.Item
 * The GalleryMultiField.Item is an item in the CQ.form.GalleryMultiField
 * This class is not intended for direct use.
 * @constructor
 * Creates a new GalleryMultiField.Item.
 * @param {Object} config The config object
 */
CQ.form.GalleryMultiField.Item = CQ.Ext.extend(CQ.form.MultiField.Item, {

    constructButtonConfig: function (items, fieldConfig) {
        var item = this;
        this.field = CQ.Util.build(fieldConfig, true);
        items.push({
            "xtype": "panel",
            "border": false,
            "cellCls": "cq-multifield-itemct",
            "items": item.field
        });

        if (!fieldConfig.readOnly) {
            if (fieldConfig.orderable) {
                items.push({
                    "xtype": "panel",
                    "border": false,
                    "items": {
                        "xtype": "button",
                        "iconCls": "cq-multifield-up",
                        "template": new CQ.Ext.Template('<span><button class="x-btn" type="{0}"></button></span>'),
                        "handler": function () {
                            var parent = item.ownerCt;
                            var index = parent.items.indexOf(item);

                            if (index > 0) {
                                var previousItem = parent.items.itemAt(index - 1);
                                if (previousItem) {
                                    var tempImagePath = previousItem.field.imagePath;
                                    var itemImagePath = item.field.imagePath;
                                    previousItemImagePath = itemImagePath;
                                    itemImagePath = tempImagePath;
                                    previousItem.field.update(ImageGallery.Gallery.buildDialogImageHTML(previousItemImagePath));
                                    previousItem.field.imagePath = previousItemImagePath;

                                    item.field.update(ImageGallery.Gallery.buildDialogImageHTML(itemImagePath));
                                    item.field.imagePath = itemImagePath;

                                    var previousDescription = previousItem.field.description;
                                    var itemDescription = item.field.description;
                                    var tempDescriptionValue = previousDescription.getValue();
                                    var itemDescriptionValue = itemDescription.getValue();
                                    previousDescription.setValue(itemDescriptionValue);
                                    itemDescription.setValue(tempDescriptionValue);

                                    var previousImageAlt = previousItem.field.imageAlt;
                                    var itemImageAlt = item.field.imageAlt;
                                    var tempImageAltValue = previousImageAlt.getValue();
                                    var itemImageAltValue = itemImageAlt.getValue();
                                    previousImageAlt.setValue(itemImageAltValue);
                                    itemImageAlt.setValue(tempImageAltValue);

                                    var previousImgPath = previousItem.field.imgPath;
                                    var itemImgPath = item.field.imgPath;
                                    var tempImgPathValue = previousImgPath.getValue();
                                    var itemImgPathValue = itemImgPath.getValue();
                                    previousImgPath.setValue(itemImgPathValue);
                                    itemImgPath.setValue(tempImgPathValue);

                                    previousItem.field.doLayout();
                                    item.field.doLayout();
                                    item.reorder(previousItem);
                                }
                            }
                        }
                    }
                });
                items.push({
                    "xtype": "panel",
                    "border": false,
                    "items": {
                        "xtype": "button",
                        "iconCls": "cq-multifield-down",
                        "template": new CQ.Ext.Template('<span><button class="x-btn" type="{0}"></button></span>'),
                        "handler": function () {
                            var parent = item.ownerCt;
                            var index = parent.items.indexOf(item);
                            if (index < parent.items.getCount() - 1) {
                                var nextItem = parent.items.itemAt(index + 1);

                                if (nextItem && nextItem.field) {
                                    var tempImagePath = nextItem.field.imagePath;
                                    var itemImagePath = item.field.imagePath;
                                    nextItemImagePath = itemImagePath;
                                    itemImagePath = tempImagePath;

                                    nextItem.field.update(ImageGallery.Gallery.buildDialogImageHTML(nextItemImagePath));
                                    nextItem.field.imagePath = nextItemImagePath;

                                    item.field.update(ImageGallery.Gallery.buildDialogImageHTML(itemImagePath));
                                    item.field.imagePath = itemImagePath;

                                    var nextDescription = nextItem.field.description;
                                    var itemDescription = item.field.description;
                                    var tempDescriptionValue = nextDescription.getValue();
                                    var itemDescriptionValue = itemDescription.getValue();

                                    nextDescription.setValue(itemDescriptionValue);
                                    itemDescription.setValue(tempDescriptionValue);

                                    var nextImageAlt = nextItem.field.imageAlt;
                                    var itemImageAlt = item.field.imageAlt;
                                    var tempImageAltValue = nextImageAlt.getValue();
                                    var itemImageAltValue = itemImageAlt.getValue();

                                    nextImageAlt.setValue(itemImageAltValue);
                                    itemImageAlt.setValue(tempImageAltValue);

                                    var nextImgPath = nextItem.field.imgPath;
                                    var itemImgPath = item.field.imgPath;
                                    var tempImgPathValue = nextImgPath.getValue();
                                    var itemImgPathValue = itemImgPath.getValue();

                                    nextImgPath.setValue(itemImgPathValue);
                                    itemImgPath.setValue(tempImgPathValue);

                                    nextItem.field.doLayout();
                                    item.field.doLayout();

                                    item.reorder(nextItem);
                                }
                            }
                        }
                    }
                });
            }

            items.push({
                "xtype": "panel",
                "border": false,
                "items": {
                    "xtype": "button",
                    "iconCls": "cq-multifield-remove",
                    "template": new CQ.Ext.Template('<span><button class="x-btn" type="{0}"></button></span>'),
                    "handler": function () {
                        var parent = item.ownerCt;
                        parent.remove(item);
                        parent.fireEvent("removeditem", parent);
                        if (parent.getValue().length == 0) {
                            parent.update(ImageGallery.Gallery.Multifield.getEmptyMessage());
                        }

                    }
                }
            });
        }
    }
});

CQ.Ext.reg("gallerymultifielditem", CQ.form.GalleryMultiField.Item);