// Image Gallery object init
var ImageGallery = (function (v) {
    return v;
}(ImageGallery || {}));

ImageGallery.Gallery = {

    DIALOG_PATH: "/apps/commons/components/content/image/imageFotoGalleryFull/dialog", // path to the fotogallery dialog
    ITEM_RESOURCE_TYPE: "/apps/commons/components/content/image/imageFotoGalleryFullItem", //gallery item resource type
    GALLERY_ITEMS: "galleryitems",

    /**
     * fires when the component has been edited (or an image has been dropped).
     * Handles the dropping of an image.
     * @param the gallery container
     */
    afteredit: function (container) {
        var galleryContentPath = container.path;
        var response = CQ.utils.HTTP.get(galleryContentPath + ".json"); //we get a json with all the component's properties
        eval('var galleryProperties =' + response.responseText);
        var droppedImageProperty = galleryProperties["droppedImage"]; //the droppedImage property stores the path of the image dropped 

        //prepare json object with the required data to create a new gallery item component via the Sling POST Servlet
        var createNodeData = {
            "sling:resourceType": this.ITEM_RESOURCE_TYPE, //the gallery item resource type
            ":nameHint": "imageFotoGalleryFullItem", //the name of the item (the Sling POST Servlet appends a number after to tell one item from the other)
            "fileReference": droppedImageProperty //the path to the image just dropped
        };
        if (!galleryProperties.fileReference) {
            ImageGallery.Util.synchronousPost(galleryContentPath + ".json", {"fileReference": droppedImageProperty});
        }

        var createNodeResponse = ImageGallery.Util.synchronousPost(galleryContentPath + "/", createNodeData); // Perform the POST

        var galleryItemPath = createNodeResponse.headers.Path; //from the POST response, we obtain the path of the newly created item
        var galleryItemsPostData = {};
        //the galleryitems property holds an array of the child component's paths e.g. 
        // ["/content/televisa/deportes/photo-gallery/1311/gallery/jcr:content/imagegallerycontainer/imagefotogalleryfull",
        // "/content/televisa/deportes/photo-gallery/1311/gallery/jcr:content/imagegallerycontainer/imagefotogalleryfull_0"]
        if (galleryProperties.galleryitems) {//if the property exists
            var galleryItems = galleryProperties.galleryitems;
            if ($.isArray(galleryItems)) {
                galleryItems.push(galleryItemPath); //add the newly created item path
            } else {
                galleryItems = [galleryItems, galleryItemPath];
            }
            galleryItemsPostData[this.GALLERY_ITEMS] = galleryItems;
        } else {
            var galleryItems = [galleryItemPath];
            galleryItemsPostData[this.GALLERY_ITEMS] = galleryItems;
        }
        var galleryItemsPostResponse = ImageGallery.Util.synchronousPost(galleryContentPath, galleryItemsPostData);

        container.refreshSelf(); //refresh the component so it displays the recently created gallery item 
        this.beginSort(); //re-initialize the sorting functionality
        ImageGallery.FotoGalFull.init(); //re-initialize the gallery js
    },

    /**
     * fires when the dialog 'OK' button has been clicked
     * retrieves the fields' values and stores them on the CRX
     * @param the gallery container
     */
    beforesubmit: function (container) {

        var dataToBeSaved = {};
        var path = container.path;

        var galleryItemsMultifield = CQ.Ext.getCmp(this.GALLERY_ITEMS); //get the multifield widget
        if (galleryItemsMultifield.getValue().length == 0) {
            dataToBeSaved[this.GALLERY_ITEMS + "@Delete"] = "delete"; //if the multifield is empty, delete the galleryitems property
        } else {
            dataToBeSaved[this.GALLERY_ITEMS] = galleryItemsMultifield.getValue();
        }
        var items = galleryItemsMultifield.items.items;
        var updateItems = [];
        var its = [];
        for (i in items) { //loop through the multifield items obtaining the values of the textfields and store them in an object.
            var item = items[i];
            if (item.field) {
                if (item.getValue() === "") {
                    updateItems.push(item);
                    var galleryContentPath = container.path;
                    var response = CQ.utils.HTTP.get(galleryContentPath + ".json"); //we get a json with all the component's properties
                    eval('var galleryProperties =' + response.responseText);
                    //prepare json object with the required data to create a new gallery item component via the Sling POST Servlet
                    var createNodeData = {
                        "sling:resourceType": this.ITEM_RESOURCE_TYPE, //the gallery item resource type
                        ":nameHint": "imageFotoGalleryFullItem", //the name of the item (the Sling POST Servlet appends a number after to tell one item from the other)
                        "fileReference": item.field.imgPath.getValue(), //the path to the image just dropped
                        "imagealt": item.field.imageAlt.getValue(),
                        "imgDescription": item.field.description.getValue()
                    };
                    if (!galleryProperties.fileReference) {
                        ImageGallery.Util.synchronousPost(galleryContentPath + ".json", {"fileReference": item.field.imgPath.lastSelectionText});
                    }
                    var createNodeResponse = ImageGallery.Util.synchronousPost(galleryContentPath + "/", createNodeData); // Perform the POST

                    var galleryItemPath = createNodeResponse.headers.Path; //from the POST response, we obtain the path of the newly created item
                    var galleryItemsPostData = {};
                    //the galleryitems property holds an array of the child component's paths e.g. 
                    // ["/content/televisa/deportes/photo-gallery/1311/gallery/jcr:content/imagegallerycontainer/imagefotogalleryfull",
                    // "/content/televisa/deportes/photo-gallery/1311/gallery/jcr:content/imagegallerycontainer/imagefotogalleryfull_0"]
                    if (galleryProperties.galleryitems) {//if the property exists
                        var galleryItems = galleryProperties.galleryitems;
                        if ($.isArray(galleryItems)) {
                            galleryItems.push(galleryItemPath); //add the newly created item path
                        } else {
                            galleryItems = [galleryItems, galleryItemPath];
                        }

                        galleryItemsPostData[this.GALLERY_ITEMS] = galleryItems;
                    } else {
                        var galleryItems = [galleryItemPath];
                        galleryItemsPostData[this.GALLERY_ITEMS] = galleryItems;
                    }
                    for (var i = 0; i < galleryItemsPostData.galleryitems.length; i++) {
                        its.push(galleryItemsPostData.galleryitems[i]);
                    }
                    //var galleryItemsPostResponse = ImageGallery.Util.synchronousPost(galleryContentPath, galleryItemsPostData);
                    ImageGallery.Util.saveProperty(galleryContentPath, "galleryitems", its);

                    //container.refresh(); //refresh the component so it displays the recently created gallery item 
                    this.beginSort(); //re-initialize the sorting functionality
                    ImageGallery.FotoGalFull.init(); //re-initialize the gallery js
                } else {
                    var nodeName = ImageGallery.Util.getNodeName(item.getValue());
                    var descriptionPropertyName = nodeName + "/imgDescription";
                    var description = item.field.description.getValue();
                    dataToBeSaved[descriptionPropertyName] = description;

                    var imageAltPropertyName = nodeName + "/imagealt";
                    var imageAlt = item.field.imageAlt.getValue();
                    dataToBeSaved[imageAltPropertyName] = imageAlt;

                    var imgPathPropertyName = nodeName + "/fileReference";
                    var imgPath = item.field.imgPath.getValue();
                    dataToBeSaved[imgPathPropertyName] = imgPath;
                }
            }
        }

        galleryItemsMultifield = CQ.Ext.getCmp(this.GALLERY_ITEMS);
        if (galleryItemsMultifield.getValue().length == 0) {
            dataToBeSaved[this.GALLERY_ITEMS + "@Delete"] = "delete"; //if the multifield is empty, delete the galleryitems property
        } else {
            if (its.length > 0) {
                dataToBeSaved[this.GALLERY_ITEMS] = its;
            } else {
                dataToBeSaved[this.GALLERY_ITEMS] = galleryItemsMultifield.getValue();
            }
        }
        dataToBeSaved["shortDescription"] = CQ.Ext.getCmp("shortDescription").getValue();
        var topic = CQ.Ext.getCmp("topic").getValue();
        if (topic.length == 0) {
            dataToBeSaved["topic@Delete"] = "delete";

        } else {
            dataToBeSaved["topic"] = CQ.Ext.getCmp("topic").getValue();

        }
        dataToBeSaved["hideDevice"] = CQ.Ext.getCmp("hideDevice").getValue();
        ImageGallery.Util.synchronousPost(path, dataToBeSaved); //Store the data in the CRX via the Sling POST Servlet

        //remove the items that no longer exist
        var originalValue = galleryItemsMultifield.originalValue; //the value of the galleryitems array before
        var newValue = galleryItemsMultifield.getValue(); //the value of the galleryitems array after editing
        var valueDifference = $(originalValue).not(newValue).get(); //the galleryitems that were on the original array but aren't in the new one (they have to be removed)
        var deleteNodePostData = {":operation": "delete"};
        $.each(valueDifference, function (index, value) {
            ImageGallery.Util.synchronousPost(value, deleteNodePostData);
        });

        //the galleryitems property contains a list of the paths to the gallery items components. Here, we update it, as items could have been
        // removed or its position could have been changed.
        var reorderData = {};
        $.each(newValue, function (index, value) {
            reorderData[":order"] = index;
            ImageGallery.Util.synchronousPost(value, reorderData);
        });

        //Update firstImage
        if (newValue.length > 0 && newValue[0] != "") {
            var firstElementPath = newValue[0];
            eval("var firstElementProperties=" + CQ.HTTP.get(firstElementPath + ".json").responseText);
            var firstImage = firstElementProperties["fileReference"];
            ImageGallery.Util.synchronousPost(path, {"fileReference": firstImage});
        }
        container.hide(); //hide the dialog
        CQ.Ext.getCmp(container.params.editBarID).refreshSelf(); //refresh the component so it shows the changes made
        this.beginSort(); //sort init

        return false; //we return false to disable the dialog's default actions. For some reason, instead of saving the values it deletes them.
    },

    /**
     * Fires whe the dialog is loading the content. It gets the properties from the CRX ands sets them in their respective fields
     **/
    loadcontent: function (container) {
        var propertiesResponse = CQ.HTTP.get(container.path + ".json");
        eval("var properties=" + propertiesResponse.responseText);
        if (properties.topic) {
            var topic = $.isArray(properties.topic) ? properties.topic : [properties.topic];
            CQ.Ext.getCmp("topic").setValue(topic);
        }
        var galleryItemsMultifield = CQ.Ext.getCmp(this.GALLERY_ITEMS);
        var originalValue = galleryItemsMultifield.getValue();
        if (originalValue.length == 1 && originalValue[0] == "") {
            originalValue = [];
        }
        galleryItemsMultifield.originalValue = originalValue;

    },

    /**
     * Fires when the edit button on the editbar is clicked. loads the dialog and sets the 'params' property  with the ID of the container, so we can reference it on the
     * beforesubmit event to refresh the component.
     **/
    edit: function (container) {
        var dialog = CQ.WCM.getDialog(ImageGallery.Gallery.DIALOG_PATH);
        dialog.loadContent(container.path);
        dialog.params = {editBarID: container.getId()}; //we save the id of the component so we can refresh it when the dialog is closed.
        dialog.show(); //show the dialog
    },
    /**
     * Builds a simple HTML layout to display the image on the widget
     **/
    buildDialogImageHTML: function (imagePath) {
        return '<div><img class="gallery-item-image" src="' + imagePath + '" /></div>';
    },

    /**
     * Initializes the sorting functionality
     **/
    beginSort: function () {
        var gallerySortableDiv = $("#sortable");
        if (ImageGallery.Util.elementExists(gallerySortableDiv)) {
            gallerySortableDiv.sortable({
                update: function (event, ui) { //fires each time an item has been reordered
                    var item = ui.item.find(".gallery-item");
                    var itemPath = item.data("path");
                    var prev = ui.item.prev().find(".gallery-item");
                    if (prev.length != 0) {
                        var prevPath = prev.data("path");
                        var prevName = ImageGallery.Util.getNodeName(prevPath);
                        ImageGallery.Util.synchronousPost(itemPath, {":order": "after " + prevName});

                    } else {
                        ImageGallery.Util.synchronousPost(itemPath, {":order": "first"});

                    }
                    var parentNode = ImageGallery.Util.getParentNode(itemPath);
                    var newGalleryItems = new Array();
                    ui.item.parent().children().each(function (index) {
                        var item = $(this).find(".gallery-item");
                        if (item) {
                            var path = item.data("path");
                            if (path && path.length > 0) {
                                newGalleryItems.push(path);
                            }
                        }

                    });

                    //update first image property (fileReference)
                    var firstElement = ui.item.parent().find("li:first");
                    var firstElementPath = firstElement.find(".gallery-item").data("path");
                    eval("var firstElementProperties=" + CQ.HTTP.get(firstElementPath + ".json").responseText);
                    var imagePath = firstElementProperties["fileReference"];
                    ImageGallery.Util.synchronousPost(parentNode, {"fileReference": imagePath});

                    ImageGallery.Util.saveProperty(parentNode, "galleryitems", newGalleryItems);
                    var id = $(".cq-element-imagegallerycontainer").attr("id");
                    CQ.Ext.getCmp(id).refresh();

                }
            });
        }
    }
};

ImageGallery.Gallery.Item = {

    DELETE_TITLE: CQ.I18n.getMessage("deportes.gallerydetail.component.delete"),
    DELETE_MESSAGE: CQ.I18n.getMessage("commons.imagegallery.item.delete.message"),
    container: null,
    /**
     * Fires when a Gallery Item 'delete' button is clicked
     **/
    delete: function (container) {

        this.container = container;
        CQ.Ext.Msg.show({ //show confirmation message
            title: this.TITLE,
            msg: this.DELETE_MESSAGE,
            buttons: CQ.Ext.Msg.YESNO,
            fn: function (option, text, opt) {
                this.processResult(option, text, opt);
            },
            animEl: 'elId',
            icon: CQ.Ext.MessageBox.QUESTION,
            scope: this,
        });

    },

    /**
     * Processes the result of the confirmation message
     **/
    processResult: function (option, text, opt) {
        if (option == "yes") {

            var galleryItemPath = this.container.path;
            var parentPath = galleryItemPath.substring(0, galleryItemPath.lastIndexOf("/"));
            var response = CQ.utils.HTTP.get(parentPath + ".json"); //we get a json with all the component's properties
            eval('var data =' + response.responseText);
            var galleryItems = data.galleryitems;
            if ($.isArray(galleryItems)) {
                if (galleryItems.length > 1) {
                    var pathIndex = $.inArray(galleryItemPath, galleryItems);
                    if (pathIndex >= 0) {
                        galleryItems.splice(pathIndex, 1);
                    }
                    ImageGallery.Util.synchronousPost(parentPath, {"galleryitems": galleryItems});
                } else {
                    ImageGallery.Util.synchronousPost(parentPath, {"galleryitems@Delete": galleryItems});
                }
            } else {
                ImageGallery.Util.synchronousPost(parentPath, {"galleryitems@Delete": galleryItems});

            }
            ImageGallery.Util.synchronousPost(galleryItemPath, {":operation": "delete"});
            this.container.refreshParent();
        }
    },

    /**
     * Fires after the gallery item has been edited. Refreshes the sorting, the gallery js and the component
     **/
    afteredit: function (container) {
        $("#sortable").sortable("refresh");
        ImageGallery.FotoGalFull.init();
        container.refreshParent();
    }
};


ImageGallery.Gallery.Multifield = {
    /**
     * Fires before the content is loaded, checks if the multifield is empty. If so, it displays a message telling the author to add more items.
     **/
    beforeloadcontent: function (field, record, path) {
        var galleryPropertiesResponse = CQ.HTTP.get(path + ".json").responseText;
        eval("var galleryResponse=" + galleryPropertiesResponse);
        if (!galleryResponse.galleryitems || galleryResponse.length == 0) {
            field.update(this.getEmptyMessage());
        } else {
            field.update("");
        }

    },

    getEmptyMessage: function () {
        return '<div style="margin-right:5px" class="x-form-item-description">' + CQ.I18n.getMessage("deportes.gallerydetail.empty.message") + '</div>';
    }
};