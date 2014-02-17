/**
 * 'Global' module
 */
var Televisa = (function ($, Modernizr) {

    var T = {},
        isReady = false,
        isLogged = true,
        modules = {},
        loadedModules = {},

        isTouchDevice = Modernizr.touch,
        lastWidthCheck = window.lastWidthCheck = null,

        DeviceSizes = {
            SMALL: 'small',
            MEDIUM: 'medium',
            LARGE: 'large'
        },
        deviceSize = DeviceSizes.SMALL;

    /**
     * Given the event returned by $(window).resize(), fills the 'deviceSize' variable
     * with one of the device sizes specified in 'DeviceSizes' and triggers an event
     * called 'resize.Televisa' that other objects or methods can subscribe to (if necessary)
     *
     * @param Event object
     * @return void
     * @visibility <private>
     */
    function calculateSize (data) {
        var w = T.getClientWidth();

        // 624 = 39em
        // 960 = 60em
        if (w < 624) {
            deviceSize = DeviceSizes.SMALL;
        } else if (w >= 624 && w < 960) {
            deviceSize = DeviceSizes.MEDIUM;
        } else {
            deviceSize = DeviceSizes.LARGE;
        }

        // Emit an event notifying that the window has been resized
        $(Televisa).trigger('resize.Televisa', data);

        return;
    }

    /**
     * Helper to create throttle functions
     *
     * @param function handler Method to proxy by
     * @param integer time Time to throttle by. Default: 500 (optional)
     * @return function
     */
    T.throttle = function(handler, time) {
        var throttle;
        time = time || 500;
        return function() {
            var args = arguments,
                context = this;
            clearTimeout(throttle);
            throttle = setTimeout(function() {
                handler.apply(context, args);
            }, time);
        };
    };

    /**
     * This is the main entry point for the class, it works as a sort of weird 'constructor'
     * that gets called when $(document).ready() is triggered.
     *
     * @return void
     * @visibility <public>
     */
    T.init = function () {

        // Calculate the window size
        calculateSize();
        $(window).resize(function() {
            if (lastWidthCheck !== T.getClientWidth()) {
                lastWidthCheck = T.getClientWidth();
                calculateSize();
            }
        });

        // Set myself as ready
        isReady = true;

        // Load the other modules
        for (var m in modules) {
             modules[m].run();
        }

        if (window.ieVersion !== undefined && window.ieVersion === 7) {
            // Stop IE7 from showing mobile first, then medium and then large
            // We should wait to show the site until all stylesheets are properly loaded.
            setTimeout(function() {
                $('body').show();
            }, 1500);
        }

        // Load FB asynchronously to stop IE7 from BLOWING UP.
        window.fbAsyncInit = function() {
            if (FB) {
                FB.init({
                    appId      : '126915557457520', // App ID
                    channelUrl : 'http://rga.com/channel.html', // Channel File
                    status     : true, // check login status
                    cookie     : true, // enable cookies to allow the server to access the session
                    xfbml      : true  // parse XFBML
                });

                // Additional initialization code here
            }
        };

        // Load the SDK Asynchronously
        (function(d){
            var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
            if (d.getElementById(id)) {return;}
            js = d.createElement('script'); js.id = id; js.async = true;
            js.src = "//connect.facebook.net/en_US/all.js";
            ref.parentNode.insertBefore(js, ref);
        }(document));

        // Disable zooming in on input elements
        if (T.isIOS()) {
            $('input[type="text"], input[type="number"], textarea, select').bind('focus blur', function(e) {
                if (window.orientation === 0) {
                    $('meta[name="viewport"]').attr('content', 'width=device-width, initial-scale=1, maximum-scale=' + (e.type === 'blur' ? 10 : 1));
                }
            });
        }

        return;

    };

    /**
     * Registers a new module. This method is usually called by submodules, and when they do
     * they will automatically execute their pre-initialization functions.
     *
     * @param Module object
     * @return void
     * @visibility <public>
     */
    T.registerModule = function (m) {
        modules[m.getName()] = m;

        return;
    };

    /**
     * Marks a module as ready to be ran when $(document).ready() is reached, which deletes it
     * from the list of preloaded modules, and moves it to the list of loaded modules.
     *
     * @param Module object
     * @return void
     * @visibility <public>
     */
    T.moduleReady = function (m) {
        // Delete the module from the 'preload' list and add it to the 'loaded' list
        loadedModules[m.getName()] = m;
        delete modules[m.getName()];

        return;
    };

    /**
     * Returns the device size in the form of a DeviceSize value
     *
     * @return DeviceSize
     * @visibility <public>
     */
    T.getDeviceSize = function () {
        return deviceSize;
    };

    /**
     * Returns the device size in the form of a DeviceSize value
     *
     * @return DeviceSize
     * @visibility <public>
     */
    T.getClientWidth = function () {
        return (document.documentElement || document.body).clientWidth;
    };

    /**
     * Returns a boolean indicating if the device supports touch events or clicks
     *
     * @return boolean
     * @visibility <public>
     */
    T.getIsTouchDevice = function () {
        return isTouchDevice;
    };

    /**
     * Is IE7
     *
     */
    T.isIE7 = function (m) {
        return ($.browser.msie  && parseInt($.browser.version, 10) === 7);
    };

    /**
     * Is Kindle
     */
    T.isKindle = function() {
        return navigator.userAgent.toLowerCase().indexOf('silk') > -1;
    };

    /**
     * Is Android
     */
    T.isAndroid = function() {
        return !!navigator.userAgent.match(/Android/i);
    };

    /**
     * Is iOS device
     */
    T.isIOS = function() {
        return !!navigator.userAgent.match(/(iPad|iPhone|iPod)/i);
    };

    /**
     * Is BlackBerry Curve
     */
    T.isBlackBerryCurve = function() {
        var ua = navigator.userAgent;

        if (ua.indexOf("BlackBerry") >= 0) {
            if (ua.indexOf("Version/") >= 0) {
                // BlackBerry 6 and 7
                var model = ua.match(/BlackBerry\s[0-9]*/);
                if (model) {
                    var model_number = model[0].match(/[0-9]+/);

                    if (model_number) model_number = model_number[0];

                    pos = ua.indexOf("Version/") + 8;
                    os_version = ua.substring(pos, pos + 3);

                    if (os_version === '6.0' && model_number === '9300') {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Less than IE9
     *
     */
    T.isLessThanIE9 = function (m) {
        return ($.browser.msie  && parseInt($.browser.version, 10) < 9);
    };

    $(document).ready(T.init);

    return T;

})(jQuery, Modernizr || null);
