/*
 *
 * ADOBE CONFIDENTIAL
 * __________________
 *
 *  Copyright 2012 Adobe Systems Incorporated
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 */
/*
 * location: libs/social/commons/components/ugcparbase/clientlibs/commons.js
 * category: [cq.collab.comments,cq.social.commons]
 */
(function(CQ, $CQ) {
    "use strict";
    CQ.soco = CQ.soco || {};
    CQ.soco.commons = CQ.soco.commons || {};
    CQ.soco.TEMPLATE_PARAMNAME = ":templatename";
    CQ.soco.filterHTMLFragment = CQ.soco.filterHTMLFragment ||
    function(fragment, targetFunction) {
        try {
            targetFunction.call(null, $CQ(fragment));
        } catch(e) {
            throw e;
        }
    };
    var localEvents = {};
    localEvents.CLEAR = "lcl.cq.soco.events.clear";
    CQ.soco.commons.handleOnBlur = function(el, message) {
        //Apparently the RTE reports a <br/> as it's empty text
        if(($CQ(el).val() === "") || ($CQ(el).val() === "<br/>")) {
            $CQ(el).val(message);
        }
    };
    CQ.soco.commons.handleOnFocus = function(el, message) {
        if($CQ(el).val() === message) {
            $CQ(el).val("");
        }
    };
    CQ.soco.commons.validateFieldNotEmptyOrDefaultMessage = function(field, message) {
        var textValue = $CQ(field).val();
        var divTextValue = '<div>' + textValue + '</div>';
        if($CQ.trim(textValue).length != 0) {
            if (($CQ.trim($CQ(divTextValue).text()).length === 0) || ($CQ.trim(textValue) === message)) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    };

    CQ.soco.commons.clientSideComposer = function(targetForm, templateName, success, failure, addedData, action, verb) {
        var formAction = action || targetForm.attr('action'),
            formVerb = verb || targetForm.attr('method') || "POST";
        targetForm.find(":submit").click(function(event) {
            // If the frm has a file upload field then we can't do client side rendering, without using a jquery ui
            //  plugin or HTML5 to handle the upload.
            if((targetForm.find(":input[type='file']").val() != undefined) && targetForm.find(":input[type='file']").val() != "") {
                return;
            }
            event.preventDefault();
            // A submit button should only submit it's closest parent form and there is only one of those.
            var form = $CQ(event.target).closest("form")[0],
                formData;
            // Check if the form has an onsubmit function, which is used for validation
            if($CQ.isFunction(form.onsubmit)) {
                // If it returns false, then do not make the request because that signifies
                // validation failed.
                if(!form.onsubmit.call(form,event)) {
                    // Need to figure out a way to communicate this failure back to the caller,
                    // invoking "failure" breaks some of the symmetry.
                    return;

                }
            }

            formData = $CQ(form).serialize();
            if(templateName) {
                formData += "&" + encodeURIComponent(CQ.soco.TEMPLATE_PARAMNAME) + "=" + encodeURIComponent(templateName);
            }
            for(var key in addedData) {
                formData += "&" + encodeURIComponent(key) + "=" + encodeURIComponent(addedData[key]);
            }

            $CQ(form).find(":input:visible").each(function() {
                $CQ(this).attr('disabled', 'disabled');
            });

            var localSuccess = function(data, status, jqxhr) {
                if(jqxhr.status === 201) {
                    $CQ(form).find(":input:visible").each(function() {
                        switch(this.type) {
                        case "password":
                        case "select-multiple":
                        case "select-one":
                        case "text":
                        case "textarea":
                            $CQ(this).val("");
                            break;
                        case "checkbox":
                        case "radio":
                            this.checked = false;
                        }
                        $CQ(this).removeAttr('disabled');
                    });
                    // This like the RTE hide form elements that are still
                    // used so notify them to clear.
                    $CQ(form).find(":input:hidden").each(function() {
                        $CQ(this).trigger(localEvents.CLEAR);
                    });
                    success.call(null, data, status, jqxhr);
                } else {
                    $CQ(form).find(":input:visible").each(function() {
                        $CQ(this).removeAttr('disabled');
                    });
                    failure.call(null, jqxhr, status);
                }
            };
            var localFail = function(jqxhr, status) {
                $CQ(form).find(":input:visible").each(function() {
                    $CQ(this).removeAttr('disabled');
                });
                failure.call(null, jqxhr, status);
            };
            $CQ.ajax(formAction, {
                data: formData,
                success: localSuccess,
                fail: localFail,
                type: formVerb
            });
        });
    };
    CQ.soco.commons.fillInputFromClientContext = function(jqFields, clientcontextProperty) {
        if(window.CQ_Analytics && CQ_Analytics.CCM) {
            $CQ(function() {
                var store = CQ_Analytics.CCM.getRegisteredStore(CQ_Analytics.ProfileDataMgr.STORENAME);
                if(store) {
                    var name = store.getProperty(clientcontextProperty, true) || '';
                    jqFields.val(name);
                }

                CQ_Analytics.CCM.addListener('storesloaded', function() {
                    var store = CQ_Analytics.CCM.getRegisteredStore(CQ_Analytics.ProfileDataMgr.STORENAME);
                    if(store && store.addListener) {
                        var name = store.getProperty(clientcontextProperty, true) || '';
                        jqFields.val(name);
                        store.addListener('update', function() {
                            var name = store.getProperty(clientcontextProperty, true) || '';
                            jqFields.val(name);
                        });
                    }
                });
            });
        }
    };

    CQ.soco.commons.activateRTE = function(targetForm, handlers) {
        var targetTextArea = targetForm.find("textarea"),
            width = targetTextArea.width() + 4,
            height = targetTextArea.height() + 60,
            controls = "bold italic underline",
            listeners = {},
            targetElement = targetTextArea[0],
            key, i, handlers = handlers || ["onfocus", "onblur"];
        // For some reason the RTE jquery plugin doesn't remap
        // handlers that are attached to the editor, so map the
        // handlers we are using.
        for(i = 0; i < handlers.length; i++) {
            key = handlers[i];
            if(null !=  targetElement[key]) {
                listeners[key.substring(2)] = targetElement[key];
            }
        }

        key = null;
        $CQ(targetTextArea).height(targetTextArea.height() + 60);
        var editor = targetTextArea.cleditor({
            width: width,
            height: height,
            controls: controls
        })[0];

        //Crazy hack to get height before its displayed
        var clonedToolbar = editor.$toolbar.clone().attr("id", false).css({
            visibility: "hidden",
            display: "block",
            position: "absolute"
        });
        $CQ('body').append(clonedToolbar);
        var toolBarHeight = $CQ(clonedToolbar).height();
        clonedToolbar.remove();
        //Hack Ends
        $CQ(editor.$frame[0]).ready(function() {
            $CQ(editor.$frame[0]).height(editor.options.height - toolBarHeight);
        });
        for(key in listeners) {
            $CQ(editor.$frame[0].contentWindow).bind(
            key, (function(func) {
                return function(event) {
                    var before = $CQ(targetElement).val();
                    func.call(targetElement, event);
                    if(before !== $CQ(targetElement).val()) {
                        editor.updateFrame();
                    }
                };
            }(listeners[key])));
        }
        targetTextArea.on(localEvents.CLEAR, function(event) {
            $CQ(targetElement).val("");
            $CQ(editor.$frame[0].contentWindow).blur();
        });
    };

    CQ.soco.commons.openModeration = function() {
        CQ.shared.Util.open(CQ.shared.HTTP.externalize('/communities.html/content/usergenerated' + CQ.WCM.getPagePath()));
    };
})(CQ, $CQ);
