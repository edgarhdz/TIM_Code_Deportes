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
(function(CQ, $CQ) {
	"use strict";
	CQ.soco = CQ.soco || {};
	CQ.soco.comments = CQ.soco.comments || {};
	CQ.soco.comments.events = CQ.soco.comments.events || {};
	CQ.soco.comments.strings = CQ.soco.comments.strings || {};
	CQ.soco.comments.events.ADDED = "CQ.soco.comments.events.ADDED";
	CQ.soco.comments.events.OPEN_REPLY = "CQ.soco.comments.events.OPEN_REPLY";
	CQ.soco.comments.events.CLOSE_REPLY = "CQ.soco.comments.events.CLOSE_REPLY";
	CQ.soco.comments.events.DELETE = "CQ.soco.comments.events.DELETE";

	CQ.soco.comments.toggleReplyForm = function(target, formURL, isRTEenabled) {
		// From the Reply link that was clicked find the closest parent
		// comment-replies then get that div's
		// reply-form holder
		var replyFormDiv = $CQ(target).closest(".comment-replies").find(
				".reply-form").first(), numForms;
		if (formURL && replyFormDiv.children().length === 0) {
			try {
				numForms = $CQ(".comment-replies .reply-form > form").length;
				formURL = CQ.shared.HTTP.noCaching(formURL + "&composerCount="
						+ numForms);
				CQ.shared.HTTP.get(formURL,
						function(o, ok, response) {
							var result = response.responseText;
							replyFormDiv.html(result);
							var appendTarget = replyFormDiv
									.closest(".comment-replies");
							if (appendTarget.length === 0) {
								appendTarget = replyFormDiv.parent();
							}
							CQ.soco.comments.attachToComposer(replyFormDiv
									.find("form").first(), appendTarget,
									"comment");
							if (isRTEenabled) {
								CQ.soco.commons.activateRTE(replyFormDiv.find(
										"form").first());
							}
							// evaluate the first form element's id and remove
							// the '-form' ending to use it as the idPrefix for
							// updating the form
							// Disabling this till I can get the client context
							// running again. This appears to be a way to of
							// taking the changes from the client context into
							// the form.
							// var formElementId =
							// $CQ(form).children("form").attr("id");
							// if (formElementId) {
							// var tokens = formElementId.split("-");
							// tokens.length = tokens.length - 1;
							// var idPrefix = tokens.join("-");
							// if (CQ_Analytics && CQ_Analytics.CCM) {
							// var store =
							// CQ_Analytics.CCM.getRegisteredStore(CQ_Analytics.ProfileDataMgr.STORENAME);
							// if (store) {
							// CQ_collab_comments_formStateChanged(idPrefix,
							// store)
							// }
							// }
							// }
						});
			} catch (e) {
				throw e;
			}

		}
		replyFormDiv.toggle();
	};
	CQ.soco.comments.showError = function(targetForm, errorMessage) {
		var errorElem = $CQ(targetForm).find("div.comment-error");
		if (!errorElem) {
			alert(errorMessage);
		} else {
			errorElem.text(errorMessage);
		}
	};

	CQ.soco.comments.validateCommentForm = function(targetForm, defaultMessage,
			enterCommentError) {
		var form = $CQ(targetForm), idPrefix = "#" + form.attr("id");
		var message = form.find("textarea").first().val();
		if (message === undefined || message === "" || message === defaultMessage) {
			CQ.soco.comments.showError(targetForm, enterCommentError);
			return false;
		}
		try {
			var check = form.find(idPrefix + "-id");
			if (check.length === 0) {
				check = document.createElement("input");
				check.id = form.attr("id") + "-id";
				check.type = "hidden";
				check.name = "id";
				check.value = "nobot";
				form.append(check);
			}
		} catch (e) {
			return false;
		}
		return true;
	};

	var refreshReplyCount = function(jqComment) {
		var numReplies = +(jqComment.data("numreplies") || 0);
		if (numReplies === 1) {
			jqComment.find("span.numReplies").filter(":first").text(
					CQ.I18n.getMessage("{0} Reply", numReplies));
		} else if (numReplies === 0) {
			jqComment.find("span.numReplies").filter(":first").text(
					CQ.I18n.getMessage("0 Replies"));
		} else {
			jqComment.find("span.numReplies").filter(":first").text(
					CQ.I18n.getMessage("{0} Replies", (numReplies + '')));
		}

	};
	CQ.soco.comments.removeHandler = function(event) {
		var targetComment = $CQ(event.target).closest(".comment").parent();
		if (targetComment.length === 0) {
			return;
		}
		event.stopPropagation();
		$CQ.post($CQ(event.target).closest("form").attr("action"), function(
				data, textStatus, jqXHR) {
			var parentComment = targetComment;
			var numReplies = +(parentComment.data("numreplies") || 0);
			parentComment.data("numreplies", (numReplies - 1));
			refreshReplyCount(parentComment);
			$CQ(event.target).closest(".comment").remove();
		});
	};
	CQ.soco.comments.addHandler = function(event) {
		var parentComment = $CQ(event.target).parent().closest(
				".comment-replies");
		if (parentComment.length === 0) {
			return;
		}
		event.stopPropagation();
		var numReplies = +(parentComment.data("numreplies") || 0);
		parentComment.data("numreplies", (numReplies + 1));
		refreshReplyCount(parentComment);
		CQ.soco.comments.toggleReplyForm(event.target);
	};

	CQ.soco.comments.bindOnAdded = function(targetComment) {
		targetComment.bind(CQ.soco.comments.events.ADDED,
				CQ.soco.comments.addHandler);
	};

	CQ.soco.comments.bindOnRemove = function(targetComment) {
		targetComment.bind(CQ.soco.comments.events.DELETE,
				CQ.soco.comments.removeHandler);
	};

	CQ.soco.comments.attachToComposer = function(targetForm, appendTarget,
			templateName) {
		var success = function(data, status, jqxhr) {
			CQ.soco.filterHTMLFragment(data, function(node) {
				var newNode = node.appendTo(appendTarget);
				newNode.bind(CQ.soco.comments.events.DELETE,
						CQ.soco.comments.removeHandler);
				newNode.bind(CQ.soco.comments.events.ADDED,
						CQ.soco.comments.addHandler);
			});
			targetForm.trigger(CQ.soco.comments.events.ADDED);
			targetForm.find("textarea").blur();

		};
		var failure = function(jqXHR, textStatus) {
			throw new Error(textStatus);
		};
		CQ.soco.commons.clientSideComposer(targetForm, templateName, success,
				failure, {});
	};
})(CQ, $CQ);