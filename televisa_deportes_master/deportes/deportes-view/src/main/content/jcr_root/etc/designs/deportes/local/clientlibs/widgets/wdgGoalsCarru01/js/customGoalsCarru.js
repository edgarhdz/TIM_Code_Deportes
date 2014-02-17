(function(window) {
    var GoalCarru = GoalCarru || {};
    var tagPath = "";
    var tagMatch = "";
    var parentPath = "";
    var jornadaTag = "";
    var tagSelectors = "";
    window.GoalCarru = GoalCarru;

    GoalCarru.initialize=function(tagPathInit,parentPagePath) {
        tagPath = tagPathInit;
        parentPath = parentPagePath;
        tag = $('div.wdg_goals_carru_012_dropdowncontent.content1').find('p').attr('name');
        console.log("****************");
        console.log(tagPath);
        console.log(tag);
        $.ajax({
            type: "GET",
            url: "/bin/video/carousel." + tagPath.replace(/\//g,"__") + "." + tag + ".json",
            dataType: "json"
        }).done(function (jsonData) {
                if (jsonData !== undefined) {
                    $("ul.wdg_goals_carru_012_dropdownlist.list2").empty();
                    $.each(jsonData, function(k,v) {
                        if (k=="last") {
                            $('div.wdg_goals_carru_012_dropdowncontent.content2').html($('<p></p>').attr("name",v.name).html(v.title).after(' <span class="tvsa-caret-down dropdown-gray"></span>'));
                            tagMatch = tagPath + "/" + tag;
                            tagSelectors = "." + tagPath.replace(/\//g,"__") + "__" + tag.replace(/\//g,"__") + ".";
                            loadNotes(parentPath, tagSelectors, 1);
                        }
                        $("ul.wdg_goals_carru_012_dropdownlist.list2").append($('<li></li>').html($('<p></p>').attr("name",v.name).html(v.title)));
                    });
                }
            });
        attachEvents();

    }

    function attachEvents(){
        $(document).ready(function() {

            $('.wdg_goals_carru_012_listcontainer.list1').bind("click",function(evt) {
                tag = evt.target.getAttribute("name");
                tagMatch = tagPath + "/" + tag;
                $.ajax({
                    type: "GET",
                    url: "/bin/video/carousel." + tagPath.replace(/\//g,"__") + "." + tag  + ".json",
                    dataType: "json"
                }).done(function (jsonData) {
                        if (jsonData !== undefined) {
                            $("ul.wdg_goals_carru_012_dropdownlist.list2").empty();
                            $('div.wdg_goals_carru_012_dropdowncontent.content2').empty();
                            $.each(jsonData, function(k,v) {
                                if (k=="last") {
                                    $('div.wdg_goals_carru_012_dropdowncontent.content2').html($('<p></p>').attr("name",tag).html(v.title).after(' <span class="tvsa-caret-down dropdown-gray"></span>'));
                                    console.log("first selector0: " + tagMatch);
                                    console.log("second selector0: " + tagMatch + "/" + v.name);
                                    tagMatch = tagPath + "/" + tag;
                                    var temp = tagMatch + "/" + v.name;
                                    // aqui va loadnotes
                                    tagSelectors = "." + tagMatch.replace(/\//g,"__")  + /*"." + temp.replace(/\//g,"__")  + */".";
                                    loadNotes(parentPath,tagSelectors,1);
                                }
                                $("ul.wdg_goals_carru_012_dropdownlist.list2").append($('<li></li>').html($('<p></p>').attr("name",v.name).html(v.title)));
                            });
                        }
                    });

            });

            $('.wdg_goals_carru_012_listcontainer.list2').bind("click",function(evt) {
                tag = evt.target.getAttribute("name");
                console.log("first selector: " + tagMatch);
                console.log("second selector: " + tagMatch + "/" + tag);
                jornadaTag = tagMatch + "/" + tag;
                tagSelectors =  "." + tagMatch.replace(/\//g,"__")  +  "." + jornadaTag.replace(/\//g,"__") + ".";
                loadNotes(parentPath,tagSelectors,1);
            });

            $('.wdg_goals_carru_012_listcontainer.list3').bind("click",function(evt) {
                tag = evt.target.getAttribute("name");
                console.log("first selector: " + tagMatch);
                console.log("second selector: " + tagMatch + "/" + tag);
                if(tag != null) {
                    tagSelectors =  "." + tagMatch.replace(/\//g,"__")  +  "." + jornadaTag.replace(/\//g,"__") + "." + tag.replace(/\//g,"__")  + ".";
                } else {
                    tagSelectors =  "." + tagMatch.replace(/\//g,"__")  +  "." + jornadaTag.replace(/\//g,"__") + ".";
                }
                loadNotes(parentPath,tagSelectors,1);
            });

        });
    }

    function loadNotes(path, infoTags, numberOfPage){
        if(numberOfPage == undefined || numberOfPage <= 0){
            numberOfPage = 1;
        }
        var htmlBody = '';
        var htmlCombo = '';
        var htmlResult = [];
        var teamsCombo = false;
        $.ajax({
            dataType: "json",
            type: "GET",
            url: "/bin/generics/goalscarrousel/query." + path + "." + numberOfPage + infoTags + "json",
            success: function(result){
                htmlResult = result;
                $.each(htmlResult, function(key, val) {
                    if(key == 'body'){
                        htmlBody = val;
                    } else if(key == 'teams') {
                        htmlCombo = val;
                        teamsCombo = true;
                    }
                });
                $('.goalscontent').html(htmlBody);
                if(teamsCombo) {
                    $('.wdg_goals_carru_012_dropdownlist.list3').html(htmlCombo);
                }
            },
            error: function(result){
                console.log("Unable to get data in the Goals Carrousel");
            }
        });


    }
})(window);
