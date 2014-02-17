
function importButtonClick(t, eventObject){


    var brightcoveServletPath = '/bin/brightcoveVideo.json';
    var idParameter = '?id=';
    var brightcoveId = CQ.Ext.getCmp("brightcoveId").getValue();

    var response = CQ.utils.HTTP.get(brightcoveServletPath + idParameter + brightcoveId); // ajax request to the externalizer service
    eval('var data =' + response.responseText); //parse the response text
    var releaseDate = CQ.Ext.getCmp("releaseDate");
    var chapter = CQ.Ext.getCmp("chapter");
    var referenceId = CQ.Ext.getCmp("referenceId");
    var duration = CQ.Ext.getCmp("duration");
    var program = CQ.Ext.getCmp("program");
    var programName = CQ.Ext.getCmp("programName");
    var summary = CQ.Ext.getCmp("summary");
    var season = CQ.Ext.getCmp("season");
    var title = CQ.Ext.getCmp("title");
    var assetPath = CQ.Ext.getCmp("assetPath");
    var tooltip = CQ.Ext.getCmp("tooltip");
    var geoFilter = CQ.Ext.getCmp("geoFilter");
    var thumbnailUrl = CQ.Ext.getCmp("thumbnailUrl");
    var imageUrl = CQ.Ext.getCmp("imageUrl");
    var videoLow = CQ.Ext.getCmp("videoUrl");
    var videoMedium = CQ.Ext.getCmp("videoMedium");
    var videoHigh = CQ.Ext.getCmp("videoHigh");



    releaseDate.setValue(data.releaseDate);
    chapter.setValue(data.chapter);
    referenceId.setValue(data.referenceId);
    duration.setValue(data.duration);
    program.setValue(data.program);
    programName.setValue(data.program);
    summary.setValue(data.summary);
    season.setValue(data.season);
    title.setValue(data.title);
    assetPath.setValue(data.assetPath);
    tooltip.setValue(data.tooltip);
    geoFilter.setValue(data.geoFilter);
    thumbnailUrl.setValue(data.thumbnailUrl);
    imageUrl.setValue(data.imageUrl);
    videoLow.setValue(data.videoUrl);
    videoMedium.setValue(data.videoMedium);
    videoHigh.setValue(data.videoHigh);


}
