var components = components || {};
(function($) {

    /**
     **
     ** TWITTER LIST
     **
     **/
    components.twitterListFeed = {

        initTwitterFeed: function(dataObj, feedObj) {
            var sf = components.twitterListFeed;
            var feedStatus = {};
            var tweetsPerFeed = dataObj.rpp;
            dataObj.rpp = Math.max(0, dataObj.rpp * feedObj.length);

            var loadAndProcessTweets = function() {
                sf.getTweets(dataObj, function(d) {
                    dataObj.rpp = tweetsPerFeed;
                    var newTweets = sf.processTweets(d, feedStatus);
                    sf.populateTwitterFeed(newTweets, feedObj, dataObj.rpp, feedStatus);
                    feedStatus = {};
                });
            };
            loadAndProcessTweets();

            setInterval(function(){
                loadAndProcessTweets();
            }, 60000);
        },

        getTweets: function(dataObj, callback) {
            //var url = "https://search.twitter.com/search.json?result_type=recent";
            //$.getJSON(url, dataObj, callback);
            $.getJSON("/bin/twitter/search."+encodeURIComponent(dataObj.q)+"."+dataObj.rpp+".json",callback);

        },

        processTweets: function(jsonData, feedStatus) {
            var tweets = jsonData.statuses;
            var index;
            var entries = [];
            if (tweets) {
                var newLastTweet = null;
                for(index in tweets) {
                    if(index == "remove") break;
                    var tweet = tweets[index];
                    newLastTweet = newLastTweet || tweet;
                    if(feedStatus.lastTweet && tweet.id_str == feedStatus.lastTweet.id_str) break;

                    if(!tweet.text || !tweet.created_at) continue;

                    var fieldData = {
                        timestamp: components.twitterListFeed.getTimestamp(tweet.created_at),
                        fromUser: tweet.user.screen_name,
                        fromName: tweet.user.name,
                        fromId: tweet.id_str,
                        text: tweet.text
                    };
                    fieldData.imageUrl = tweet.user.profile_image_url;
                    entries.push(components.twitterListFeed.renderTemplate(components.twitterListFeed.twitterTemplate, fieldData));
                }
                feedStatus.lastTweet = newLastTweet;
            }
            return entries;
        },

        populateTwitterFeed: function(processedTweets, feedObj, numTweets, feedStatus) {
            if(!feedStatus.queue) {
                feedStatus.queue = [];
                feedStatus.feeds = [];
                feedStatus.maxTweets = 0;
                feedStatus.feedIndex = 0;

                setInterval(function() {
                    var queue = feedStatus.queue;
                    var feeds = feedStatus.feeds;
                    var maxTweets = feedStatus.maxTweets;
                    try{
                        if(queue.length && feeds.length) {
                            feedObj.find("*").remove();
                            var thisFeed = $(feedStatus.feeds[feedStatus.feedIndex]);
                            var numToPost = Math.max(Math.max(1, queue.length - 60),
                                maxTweets - thisFeed.children().length);
                            if(numToPost > queue.length) numToPost = queue.length;
                            var toPost = queue.splice(queue.length - numToPost, numToPost);
                            feedStatus.feedIndex = (feedStatus.feedIndex + 1) % feeds.length;
                            components.twitterListFeed.populateFeed(toPost, thisFeed, maxTweets);
                            feedStatus = {};
                            if(feedObj.find("li").length == 2){
                                var ulElement = thisFeed.find('ul');
                                ulElement.context.style.width = "600px";
                            }
                        }
                    }catch(error){
                        //console.log(error);
                    }
                }, 1000);
            }

            feedObj.find("*").remove();
            feedStatus.maxTweets = numTweets;
            feedStatus.feeds = feedObj;
            feedStatus.queue = processedTweets.concat(feedStatus.queue);
        },

        populateFeed: function(entries, feedObj, maxObjs) {
            maxObjs = maxObjs || 10;
            feedObj.context.innerHTML = '';
            for(var i = entries.length - 1; i >= 0; i--) {
                var index = (i + 1);

                if((index > 0) && (index <= (entries.length - 1)) && ((index % 2) == 0)){
                    feedObj.context.innerHTML = '</li><li class="showArrows">' + feedObj.context.innerHTML;
                }
                $(entries[i]).hide().prependTo(feedObj).fadeIn(100);
            }

            var replacedText = components.twitterListFeed.replaceAll(feedObj.context.innerHTML, 'style="opacity: 0;"','');
            replacedText = components.twitterListFeed.replaceAll(replacedText, 'opacity: 0','opacity: 100');

            feedObj.context.innerHTML = '<li class="showArrows">'+ replacedText + '</li>';

            var items = feedObj.find('li');
            var padding = (feedObj.parent().parent().hasClass('wdg_carousa')) ? 41 : 24;
            feedObj.width((items.outerWidth(true) + padding) * items.length - 160);

            var currObjs = $(feedObj).find('wdg_twitt_02_block one');
            for(i = maxObjs; i < currObjs.length; i++) {
                $(currObjs[i]).fadeOut(100, (function(o) {
                    return function() {
                        $(o).remove();
                    };
                })(currObjs[i]));
            }
        },

        renderTemplate: function(template, fieldData) {
            var ret = template;
            for(var field in fieldData) {
                ret = ret.replace(new RegExp("{{" + field + "}}", "g"), fieldData[field]);
            }
            return ret;
        },

        twitterTemplate:
            '<div  class="wdg_twitt_02_block one showArrows">' +
                '<div class="wdg_twitt_02_img">' +
                '<a href="http://www.twitter.com/{{fromUser}}" target="_blank" class="ui-link">' +
                '<img src="{{imageUrl}}" />' +
                '</a>' +
                '</div>' +
                '<div class="wdg_twitt_02_txt">' +
                '<a href="http://www.twitter.com/{{fromUser}}" target="_blank">' +
                '<span class="title textcolor-title2"> {{fromName}} <span class="cta_twitter textcolor-title4">@{{fromUser}}</span></span>' +
                '<p>{{text}}</p>' +
                '</a>' +
                '<span class="wdg_twitt_02_blue">{{timestamp}}' +
                '<a href="https://twitter.com/intent/tweet?in_reply_to={{fromId}}"> - Reply&nbsp;</a>' +
                '<a href="https://twitter.com/intent/retweet?tweet_id={{fromId}}"> - Retweet&nbsp;</a>' +
                '<a href="https://twitter.com/intent/favorite?tweet_id={{fromId}}"> - Favorito</a>' +
                '</span>' +
                '</div>' +
                '</div>',

        getTimestamp: function(time) {
            var eventTime = new Date(time);
            var currentTime = new Date();
            var diff = Math.floor((currentTime - eventTime) / 1000);
            if (diff <= 1) return "justo ahora";
            if (diff < 30) return diff + " segundos atras";
            if (diff <= 90) return "1 minuto atras";
            if (diff <= 3540) return Math.round(diff / 60) + " minutos atras";
            if (diff <= 5400) return "1 hour ago";
            if (diff <= 86400) return Math.round(diff / 3600) + " horas atras";
            if (diff <= 129600) return "1 day ago";
            if (diff < 604800) return Math.round(diff / 86400) + " dias atras";
            if (diff <= 777600) return "1 semana atras";
            return time;
        },

        replaceAll: function( text, busca, reemplaza ){
            var idx = text.toString().indexOf(busca);
            while (idx != -1) {
                text = text.toString().replace(busca,reemplaza);
                idx = text.toString().indexOf(busca, idx);
            }
            return text;
        },

        sleep: function(milliseconds) {
            var start = new Date().getTime();
            for (var i = 0; i < 1e7; i++) {
                if ((new Date().getTime() - start) > milliseconds){
                    break;
                }
            }
        }
    }

}
    )(jQuery);

var components = components || {};
(function($) {

    /**
     **
     ** TWITTER ACCOUNT
     **
     **/
    components.twitterAccountFeed = {
        initTwitterFeed: function(listTweets, feedObj) {
            var sf = components.twitterAccountFeed;
            var feedStatus = {};

            var loadAndProcessTweets = function() {
                var tweets = [];
                var listLength = listTweets.length - 1;

                for(countTweetUser = 0; countTweetUser < listLength; countTweetUser++){
                    var q = listTweets[countTweetUser].q;
                    var rpp = listTweets[countTweetUser].rpp;

                    rpp = Math.max(0, rpp * feedObj.length);
                    sf.getTweets(listTweets[countTweetUser], function(d) {
                        var tweetLength = Math.floor(6 / listLength); // d.statuses.length;

                        for(countTweets = 0; countTweets < tweetLength; countTweets++){
                            var itemTwt = d.statuses[countTweets];
                            tweets.push(itemTwt);
                            if((countTweets == (tweetLength - 1)) && ((countTweetUser - (countTweetUser - listLength)) == listLength)){
                                rpp = tweets.length;
                                var newTweets = sf.processTweets(tweets, feedStatus);
                                sf.populateTwitterFeed(newTweets, feedObj, rpp, feedStatus);
                                feedStatus = {};
                            }
                        }
                        countTweetUser++;
                    });
                }
            };
            loadAndProcessTweets();
            setInterval(function(){
                loadAndProcessTweets();
            }, 60000);
        },

        getTweets: function(dataObj, callback) {
            //var url = "https://search.twitter.com/search.json?callback=?&result_type=recent";
            //$.getJSON(url, dataObj, callback);
            $.getJSON("/bin/twitter/search."+encodeURIComponent(dataObj.q)+"."+dataObj.rpp+".json",callback);


        },

        processTweets: function(results, feedStatus) {

            var tweets = results;
            var index;
            var entries = [];

            if (tweets) {
                var newLastTweet = null;
                for(index in tweets) {


                    var tweet = tweets[index];
                    newLastTweet = newLastTweet || tweet;
                    if(feedStatus.lastTweet && tweet.id_str == feedStatus.lastTweet.id_str) break;

                    if(!tweet.text || !tweet.created_at) continue;
                    var fieldData = {
                        timestamp: components.twitterListFeed.getTimestamp(tweet.created_at),
                        fromUser: tweet.user.screen_name,
                        fromName: tweet.user.name,
                        fromId: tweet.id_str,
                        text: tweet.text
                    };
                    fieldData.imageUrl = tweet.user.profile_image_url;
                    entries.push(components.twitterAccountFeed.renderTemplate(components.twitterAccountFeed.twitterTemplate, fieldData));
                }
                feedStatus.lastTweet = newLastTweet;
            }
            return entries;
        },

        populateTwitterFeed: function(processedTweets, feedObj, numTweets, feedStatus) {
            if(!feedStatus.queue) {
                feedStatus.queue = [];
                feedStatus.feeds = [];
                feedStatus.maxTweets = 0;
                feedStatus.feedIndex = 0;

                setInterval(function() {
                    var queue = feedStatus.queue;
                    var feeds = feedStatus.feeds;
                    var maxTweets = feedStatus.maxTweets;
                    try{
                        if(queue.length && feeds.length) {
                            feedObj.find("*").remove();
                            var thisFeed = $(feedStatus.feeds[feedStatus.feedIndex]);
                            var numToPost = Math.max(Math.max(1, queue.length - 60),
                                maxTweets - thisFeed.children().length);
                            if(numToPost > queue.length) numToPost = queue.length;
                            var toPost = queue.splice(queue.length - numToPost, numToPost);
                            feedStatus.feedIndex = (feedStatus.feedIndex + 1) % feeds.length;
                            components.twitterAccountFeed.populateFeed(toPost, thisFeed, maxTweets);
                            feedStatus = {};
                            if(feedObj.find("li").length == 2){
                                var ulElement = thisFeed.find('ul');
                                ulElement.context.style.width = "600px";
                            }
                        }
                    }catch(error){
                        //console.log(error);
                    }
                }, 1000);
            }

            feedObj.find("*").remove();
            feedStatus.maxTweets = numTweets;
            feedStatus.feeds = feedObj;
            feedStatus.queue = processedTweets.concat(feedStatus.queue);
        },

        populateFeed: function(entries, feedObj, maxObjs) {
            maxObjs = maxObjs || 10;

            feedObj.context.innerHTML = '';
            for(var i = entries.length - 1; i >= 0; i--) {
                var index = (i + 1);
                if((index > 0) && (index <= (entries.length - 1)) && ((index % 2) == 0)){
                    feedObj.context.innerHTML = '</li><li class="showArrows">' + feedObj.context.innerHTML;
                }
                $(entries[i]).hide().prependTo(feedObj).fadeIn(100);
            }
            var replacedText = components.twitterAccountFeed.replaceAll(feedObj.context.innerHTML, 'style="opacity: 0;"','');
            replacedText = components.twitterAccountFeed.replaceAll(replacedText, 'opacity: 0','opacity: 100');

            feedObj.context.innerHTML = '<li class="showArrows">'+ replacedText + '</li>';

            var items = feedObj.find('li');
            var padding = (feedObj.parent().parent().hasClass('wdg_carousa')) ? 41 : 24;
            feedObj.width((items.outerWidth(true) + padding) * items.length - 160);

            var currObjs = $(feedObj).find('wdg_twitt_02_block one');
            for(i = maxObjs; i < currObjs.length; i++) {
                $(currObjs[i]).fadeOut(100, (function(o) {
                    return function() {
                        $(o).remove();
                    };
                })(currObjs[i]));
            }
        },

        renderTemplate: function(template, fieldData) {
            var ret = template;
            for(var field in fieldData) {
                ret = ret.replace(new RegExp("{{" + field + "}}", "g"), fieldData[field]);
            }
            return ret;
        },

        twitterTemplate:
            '<div  class="wdg_twitt_02_block one showArrows">' +
                '<div class="wdg_twitt_02_img">' +
                '<a href="http://www.twitter.com/{{fromUser}}" target="_blank" class="ui-link">' +
                '<img src="{{imageUrl}}" />' +
                '</a>' +
                '</div>' +
                '<div class="wdg_twitt_02_txt">' +
                '<a href="http://www.twitter.com/{{fromUser}}" target="_blank">' +
                '<span class="title textcolor-title2"> {{fromName}} <span class="cta_twitter textcolor-title4">@{{fromUser}}</span></span>' +
                '<p>{{text}}</p>' +
                '</a>' +
                '<span class="wdg_twitt_02_blue">{{timestamp}}' +
                '<a href="https://twitter.com/intent/tweet?in_reply_to={{fromId}}"> - Reply&nbsp;</a>' +
                '<a href="https://twitter.com/intent/retweet?tweet_id={{fromId}}"> - Retweet&nbsp;</a>' +
                '<a href="https://twitter.com/intent/favorite?tweet_id={{fromId}}"> - Favorito</a>' +
                '</span>' +
                '</div>' +
                '</div>',

        getTimestamp: function(time) {
            var eventTime = new Date(time);
            var currentTime = new Date();
            var diff = Math.floor((currentTime - eventTime) / 1000);
            if (diff <= 1) return "justo ahora";
            if (diff < 30) return diff + " segundos atras";
            if (diff <= 90) return "1 minuto atras";
            if (diff <= 3540) return Math.round(diff / 60) + " minutos atras";
            if (diff <= 5400) return "1 hour ago";
            if (diff <= 86400) return Math.round(diff / 3600) + " horas atras";
            if (diff <= 129600) return "1 day ago";
            if (diff < 604800) return Math.round(diff / 86400) + " dias atras";
            if (diff <= 777600) return "1 semana atras";
            return time;
        },

        replaceAll: function( text, busca, reemplaza ){
            var idx = text.toString().indexOf(busca);
            while (idx != -1) {
                text = text.toString().replace(busca,reemplaza);
                idx = text.toString().indexOf(busca, idx);
            }
            return text;
        },

        sleep: function(milliseconds) {
            var start = new Date().getTime();
            for (var i = 0; i < 1e7; i++) {
                if ((new Date().getTime() - start) > milliseconds){
                    break;
                }
            }
        }
    }
}
    )(jQuery);
