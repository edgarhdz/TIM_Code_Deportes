CQ.form.GalleryItem = CQ.Ext.extend(CQ.form.CompositeField, {

    hideLabel: true,
    descriptionConfig: null,
    imageAltConfig: null,
    imgPathConfig: null,
    imgPath: null,
    imageAlt: null,
    description: null,
    imageAlt: null,
    index: null,
    path: null,
    imagePath: null,

    constructor: function (config) {
        config = config || {};
        var defaults = {
            border: true,
            layout: "form",
        };
        var descriptionConfigDefaults = {
            fieldDescription: CQ.I18n.getMessage("deportes.gallerydetail.description.message"),
            anchor: "100%",
        };
        descriptionConfig = config.descriptionConfig || {};
        descriptionConfig = CQ.Util.applyDefaults(descriptionConfig, descriptionConfigDefaults);

        var imageAltConfigDefaults = {
            fieldDescription: CQ.I18n.getMessage("commons.imagegallery.item.imagealt.description"),
            anchor: "100%",
        };

        imageAltConfig = config.imageAltConfig || {};
        imageAltConfig = CQ.Util.applyDefaults(imageAltConfig, imageAltConfigDefaults);

        var imgPathConfigDefaults = {
            fieldDescription: CQ.I18n.getMessage("deportes.gallerydetail.imagepath.message"),
            anchor: "85%",
        };

        imgPathConfig = config.imgPathConfig || {};
        imgPathConfig = CQ.Util.applyDefaults(imgPathConfig, imgPathConfigDefaults);

        config = CQ.Util.applyDefaults(config, defaults);
        CQ.form.GalleryItem.superclass.constructor.call(this, config);

    },

    initComponent: function () {
        CQ.form.GalleryItem.superclass.initComponent.call(this);

        // Description text field
        this.description = new CQ.Ext.form.TextField(descriptionConfig);
        this.add(this.description);

        // Image Alt text field
        this.imageAlt = new CQ.Ext.form.TextField(imageAltConfig);
        this.add(this.imageAlt);

        //function to change related image of an item DialogSelect listener
        function changeItemImageDialogSelect(widget) {
            var img = ImageGallery.Gallery.buildDialogImageHTML(widget.lastSelectionText);
            var itemChangeImage = widget.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt.items.items[0].items.itemAt(widget.ownerCt.index);
            itemChangeImage.field.update(img);
            itemChangeImage.field.doLayout();
        }

        //function to change related image of an item Select listener
        function changeItemImageSelect(combo,record,index) {
            var img = ImageGallery.Gallery.buildDialogImageHTML(record.id);
            var itemChangeImage = combo.ownerCt.ownerCt.ownerCt.ownerCt.items.itemAt(combo.ownerCt.index);
            itemChangeImage.field.update(img);
            itemChangeImage.field.doLayout();
            combo.setValue(record.id);
        }

        this.imgPath = new CQ.form.SearchPathField({
            width: "85%",
            fieldDescription: CQ.I18n.getMessage("deportes.gallerydetail.imagepath.message"),
            listeners: {
                dialogselect: changeItemImageDialogSelect,
                select: changeItemImageSelect
            }
        });
        this.add(this.imgPath);

    },
    /*
     Loads each item's data and sets the image to display on each item.
     */
    processPath: function (galleryPath, ignoreData) {

        var galleryPropertiesResponse = CQ.utils.HTTP.get(galleryPath + ".json"); //we get a json with all the component's properties
        eval('var galleryProperties=' + galleryPropertiesResponse.responseText); //parse the response
        var galleryitems = "";
        if (galleryProperties.galleryitems) {
            if ($.isArray(galleryProperties.galleryitems)) {
                galleryItemPath = galleryProperties.galleryitems[this.index];
            }
            else {
                galleryItemPath = galleryProperties.galleryitems;
            }
        }

        if (galleryItemPath !== undefined) {
            var galleryItemPropertiesResponse = CQ.utils.HTTP.get(galleryItemPath + ".json"); //we get a json with all the component's properties
            eval('var galleryItemProperties =' + galleryItemPropertiesResponse.responseText);
            this.imagePath = galleryItemProperties.fileReference;
            this.path = galleryItemPath;

            var imageHTML = ImageGallery.Gallery.buildDialogImageHTML(this.imagePath);
            this.update(imageHTML);

            var description = galleryItemProperties.imgDescription;
            this.description.setValue(description);

            var imageAlt = galleryItemProperties.imagealt;
            this.imageAlt.setValue(imageAlt);

            var imgPath = galleryItemProperties.fileReference;
            this.imgPath.setValue(imgPath);
        }

        CQ.form.GalleryItem.superclass.processPath.call(galleryPath, ignoreData);
    }

});

CQ.Ext.reg("galleryitem", CQ.form.GalleryItem);
