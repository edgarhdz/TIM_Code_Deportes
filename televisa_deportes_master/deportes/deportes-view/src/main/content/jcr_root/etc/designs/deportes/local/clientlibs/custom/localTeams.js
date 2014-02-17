(function(window){

	var comboLoaded=false;
	var LocalTeamsTemplate={};
	
	window.LocalTeamsTemplate=LocalTeamsTemplate;
	
	LocalTeamsTemplate.beforeThemeContainerLoadContent=function(container,record,path){
	
		var cssThemeValue=record.data.cssTheme;
		
			if(!comboLoaded){
				loadComboBox(container,function(combo){
					if(cssThemeValue)
						combo.setValue(cssThemeValue);
				});
			}
			else{
				var combo=container.findByType("combo")[0];
				if(cssThemeValue)
					combo.setValue(cssThemeValue);
			}
		
		
	}
	
	function loadComboBox(container,callback){
		CQ.Ext.Ajax.request({
			url:"/etc/designs/deportes/local/_jcr_content/localTeams/localTeamsThemeMapping.json",
			success:function(response){
				 var jsonData = CQ.Ext.util.JSON.decode(response.responseText);
				 var comboData=[];
				 CQ.Ext.each(jsonData.themeMappingOptions,function(item){
					 var tokens=item.split("||");
					 var dataItem=[tokens[0],tokens[1]];
					 comboData.push(dataItem);
				 });
				 var store=new CQ.Ext.data.ArrayStore({
					 fields:['team','theme'],
					 data:comboData,
					 id:0
				 });

				var combo = new CQ.Ext.form.ComboBox({
				    typeAhead: true,
				    triggerAction: 'all',
				    lazyRender:true,
				    mode: 'local',
				    store:store,
				    valueField: 'theme',
				    displayField: 'team',
				    hiddenName:"./cssTheme"
				});
				container.add(combo);
				container.findParentByType('panel').doLayout();	
				
				if(callback){
					callback(combo);
				}
				comboLoaded=true;
				
			}
		});
	}
	
	
})(window);
