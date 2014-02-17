$(document).ready(function(){ 
    (function($, T){

        function initVerticalHeroEach(verticalHero) {

            var elementDataUpdaters = {},
                gallerySwipe = null, 
                galleryThumbs = verticalHero.find('.galleryThumbs'), 
                galleryImages = verticalHero.find('.galleryImages>ul');

            if (!galleryThumbs.length || !galleryImages.length) {
                // If the basic required DOM structure is not found, return immediately (and silently)
                return; 
            }

            // Construct the query string based on the assets to load
            var assetIdsToLoad = galleryThumbs.find('li').map(function(index, element) {
                return $(element).attr('data-assetid');
            }).get();

            // Get the information for all of the gallery assets
            loadGalleryInfo(assetIdsToLoad).done(function(data) {

                // Initialize the 1st picture (that comes in the markup)
                var firstDomElement = galleryImages.find('li:first-child'), 
                    firstDomElementID = firstDomElement.attr('data-assetid'), 
                    firstElementData = $.grep(data, function(galleryElement, index) {
                        return firstDomElementID == galleryElement.id;
                    });

                setupGalleryElement(firstDomElement, firstElementData[0], true);

                var stillNotInitializedElementsData = $.grep(data, function(galleryElement, index) {
                    return firstDomElementID != galleryElement.id;
                });

                $.each(stillNotInitializedElementsData, function(index, galleryElement) {

                    // Create the big image in the slideshow
                    var newLi = $('<li></li>');
                    newLi.data('galleryElement', galleryElement);
                    galleryImages.append(newLi);
                    setupGalleryElement(newLi, galleryElement);

                });

                //For mobile devices, initialize swipe
                if ( Modernizr.cssanimations && Modernizr.touch ) { // Should test for csstransforms instead, but I am assuming that if a browser supports annimations, it will support transforms 
                    var sliderContainer = verticalHero.find('.galleryImages').get(0);
                    gallerySwipe = new Swipe(sliderContainer,{callback:afterSwipe});
                }

                function afterSwipe(){
                    var currentElementIndex = gallerySwipe.getPos();
                    elementDataUpdaters[currentElementIndex]();
                }

                setupNextPrevArrows();

            });

            function showPic(direction){


                var theThumb = galleryThumbs.find('>li.active'),
                    allTheThumbs = galleryThumbs.find('>li'),
                    elementIndex = allTheThumbs.index(theThumb);

                //Check boudaries
                if( (elementIndex > 0 && direction == -1) || (elementIndex < allTheThumbs.length -1 && direction == 1 ) ){
                    elementIndex += direction;
                    $(galleryThumbs.find('>li').get(elementIndex)).find('>a').click();
                } 

            } 

            function setupNextPrevArrows(){

                if (/iPhone|iPod|iPad/.test(navigator.userAgent) && // If it's an iPad, iPod or iPhone
                        !(/OS [2-4]_\d(_\d)? like Mac OS X/i.test(navigator.userAgent))) { // And the version of the OS is bigger than 4.x...
                } else {
                    // Touch devices (non-iOS or iOS < 5)
                    if (T.getIsTouchDevice() && !T.isBlackBerryCurve()) {
                    } else {
                        verticalHero.addClass('showArrows');
                    }
                }

                verticalHero.find('.title ul .left').click(function(event){
                    event.preventDefault();

                    if(gallerySwipe){
                        gallerySwipe.prev();
                    } else {
                        showPic(-1);
                    }

                });

                verticalHero.find('.title ul .right').click(function(event){
                    event.preventDefault();

                    if(gallerySwipe){
                        gallerySwipe.next();
                    } else {
                        showPic(1);
                    }

                });

            }

            function setupGalleryElement(galleryDomElement, elementData, avoidAddingImage) {
                var initialized = false,
                    theThumb = galleryThumbs.find('>li[data-assetid="' + elementData.id + '"]'),
                    theThumbAnchor = theThumb.find('>a'), 
                    allTheThumbs = galleryThumbs.find('>li'),
                    elementIndex = allTheThumbs.index(theThumb),

                    updateElementData = function(){

                        verticalHero.find('.itemSection a')
                            .attr('href', elementData.urlSection)
                            .html(elementData.section);

                        verticalHero.find('.itemCaption a')
                            .attr('href', theThumbAnchor.attr('href'))
                            .html(elementData.caption);

                        verticalHero.find('.galleryCaption h3')
                            .html(elementData.age);

                        //Put the border around the current thumbnail
                        allTheThumbs.removeClass('active');
                        theThumb.addClass('active');

                        if(!avoidAddingImage && !initialized){
                            var theImageLink = $('<a href="'+ theThumbAnchor.attr('href') +'"></a>');
                            var theImage = '<img src="' + elementData.imgUrl + '" />';
                            theImageLink.append(theImage);
                            galleryDomElement.append(theImageLink);
                            initialized=true;
                        }
                    } ;

                // Save the update data to call it when swiping
                elementDataUpdaters[elementIndex] = updateElementData;

                // Assign the even handlers and data to the thumbnail
                theThumbAnchor
                    .data('galleryElement', elementData)
                    .click(function(event) {
                        event.preventDefault();

                        if(gallerySwipe){
                            gallerySwipe.slide(elementIndex,0); 
                        }else{
                            galleryImages.find('li.active').removeClass('active'); // Hide all pictures
                            galleryDomElement.addClass('active'); // Show current one
                        }

                        updateElementData();

                    });

            }

        }

        function loadGalleryInfo(assetIDs) {

            var result = $.Deferred();

            $.getJSON('../global/json/articlesdata.json?assets=' + encodeURIComponent(assetIDs.join(',')))
                .done(function(data){
                    result.resolve(data);
                });

            return result;
        }


        $('.verticalHero').each(function(index, verticalHero) {
            initVerticalHeroEach( $(verticalHero) );
        });

    })(jQuery, Televisa);

});

