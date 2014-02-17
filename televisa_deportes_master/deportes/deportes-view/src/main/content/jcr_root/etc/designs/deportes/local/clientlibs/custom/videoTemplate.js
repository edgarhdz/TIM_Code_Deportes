CQ.tagging.VideoTagInputField= CQ.Ext.extend(CQ.tagging.TagInputField, {
	constructor : function(config) {
		
		config = config || {};    
		this.config=config;
		
        config = CQ.Util.applyDefaults(config, {
        	listeners:{
        		afterrender:function(container){
        			
        		}
        	}
        });       
   
        CQ.tagging.VideoTagInputField.superclass.constructor.call(this, config);
    },
    prepareSubmit:function(){
    	
    	// check the text field for contents
        if (!this.readTextField(true)) {
            return false;
        }
        
        //0 Custom tags merge logic
        var dialog=this.findParentByType("dialog");
    	var that =this;
    	/*We find the teams tag field from wich we are going to include his  tags*/
    	var teamsTagsField=dialog.findBy(function(object){
    		if(object.name==that.teamsTagsName&&object.xtype=="tags"){
    			return true;
    		}
    	})[0];
    	
    	//we add the added tags from the mirrowed input if it doesn't exists
    	CQ.Ext.each(teamsTagsField.addedTags,function(tagObj){
    		if(!that.containsTag(that.addedTags,tagObj)){
    			that.addedTags.push(tagObj);
    		}    		
    	});
    	
    	/*If the current tags contains tags from the mirrowed tags field that will be removed, we add them
    	 * to the removedTags also
    	 * */
    	CQ.Ext.each(teamsTagsField.removedTags,function(tagObj){
    		if(that.containsTag(that.tags,tagObj)){
    			that.removedTags.push(tagObj);
    		}  
    	});
    	
    	/*
    	 * If a tag that will be removed we have to check if exists in the mirrowed field removedTags, and if it will be removed 
    	 * if not, we should remove that tag from the removedTags array
    	 * */
    	var newTagsToRemove=[];
    	CQ.Ext.each(this.removedTags,function(tagObj){
    		if(that.containsTag(teamsTagsField.tags,tagObj)){
    			if(that.containsTag(teamsTagsField.removedTags, tagObj)){
    				newTagsToRemove.push(tagObj);
    			}
    		}
    		else{
    			//this tag don't exists in the mirrowed tags field we should just add it as it is
    			newTagsToRemove.push(tagObj);
    		}
    	});
    	//We have a new Tags to remove 
        this.removedTags=newTagsToRemove;
        // 1. inspect added tags; "new" => create via ajax or "denied" => stop and display
        
        // arrays of tag ids
        var newTags = [];
        var denied = [];
        
        CQ.Ext.each(this.addedTags, function(tagObj) {
            if (tagObj.type == "new") {
                newTags.push(tagObj.tag);
            } else if (tagObj.type == "denied") {
                denied.push(tagObj.tag);
            }
        });
        
        // first check if there are denied new tags and stop the submit in this case
        if (denied.length > 0) {
            CQ.Ext.Msg.alert(
                CQ.I18n.getMessage("Error"),
                CQ.I18n.getMessage("You are not allowed to create these new tag(s):<br><br>{0}<br><br>Please remove before submitting the form.", [ denied.join("<br>") ])
            );
            return false;
        }
        
        // 2. (try to) create the new tags on the server
        
        if (newTags.length > 0) {
            var result = this.createTags(newTags);
            if (result.failed.length > 0) {
                CQ.Ext.Msg.alert(
                    CQ.I18n.getMessage("Error from Server"),
                    CQ.I18n.getMessage("Could not create tag(s):<br><br>{0}<br><br>The form was not saved.", [ result.failed.join("<br>") ])
                );
                // stop form submit, don't set the tags on the current content
                return false;
            }
            // update tag (type="new") with the full data for the newly created tag
            for (var i=0; i < newTags.length; i++) {
                var tagObj = this.getTag(newTags[i]);
                tagObj.setType("added");
                tagObj.tag = this.getTagDefinition(result.created[i]);
            }
            
            // please reload the tag tree next time
            this.tagNamespacesLoaded = false;
        }

        // 4. update the hidden fields with the added/removed patch operations
        
        this.updateHiddenFields();
        
        return true;
    },
    containsTag:function(tagsArray,tagObj){
    	var result=false;
    	CQ.Ext.each(tagsArray,function(addedTag){
    		if(addedTag.tag.path==tagObj.tag.path){
    			result= true;
    		}
    	});
    	return result;
    }
});
CQ.Ext.reg("videotagsinputfield",CQ.tagging.VideoTagInputField);