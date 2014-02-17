
/********************************************************************************************************************
 * Objeto para administrar un HTML5 Player
 * M3U8 / MP4
 * Thu Nov 22 17:15:14 CST 2012 @10 /Internet Time/
 * Tue Sep 04 11:17:47 CDT 2012 @720 /Internet Time/
 * Mon Sep 03 19:56:05 CDT 2012 @80 /Internet Time/
 * 24/05/11	IS	Linea:0		Se creó el script
 * 26/07/12	IS	Linea:0		not-i for-o tv
 * 30/10/12	IS	Linea:0		valen
 ********************************************************************************************************************/

//that one
function HTML5Player( userProperties ) {
    this.properties = userProperties;
    this.values = {
        width: "612",
        height: "344"
    }
    if( this.properties.width ) {
        this.values.width = this.properties.width;
        this.values.height = this.properties.height;
    }
    this.isIpad = false;
    this.DetectIpad = function() {
        if( navigator.userAgent.indexOf('iPad') != -1 ) {
            this.isIpad = true;
        }
        else if( navigator.userAgent.indexOf('iPod') != -1)
        {
            this.isIpad = true;
        }
        else if( navigator.userAgent.indexOf('iPhone') != -1 ){
            this.isIpad = true;
        }

    }
    this.DetectIpad();
    this.GetThumbnail = function() {
        return this.properties.thumbnail;
    }
    // GB Code
    this.IsValidRegion = function() {
        var result = false;
        switch( this.properties.gbs ) {
            case '49010dd1ebf31e2005d4c624eef19176':
                if( MN_geo.country.toLowerCase() == 'gtm' )
                    result = true
                break;
            case '716ee5cf447289f814a8ef5f9ad86bb5':
                var cString = 'usa,pri,vir,umi,asm,plw';
                if( cString.indexOf( MN_geo.country.toLowerCase() ) == -1 )
                    result = true;
                break;
            case '991d48815c3a68294305aaf78e0bdeeb':
                var cString = 'ant,arg,brb,bmu,bol,bra,bra,bhs,blz,can,chl,col,cri,cri,cub,dma,ecu,dom,guf,glp,sgs,gtm,gum,guy,hnd,hti,jam,kna,cym,lca,mtq,mex,nic,pan,per,pry,sur,stp,slv,tto,ury,vct,ven';
                if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 )
                    result = true;
                break;
            case 'c1c734120e257f118c92aa2293bed0bf':
                var cString = 'ant,brb,bmu,bol,bhs,blz,can,chl,col,cri,cri,cub,dma,ecu,dom,guf,glp,sgs,gtm,gum,guy,hnd,hti,jam,kna,cym,lca,mtq,mex,nic,pan,per,pry,sur,stp,slv,tto,ury,vct,ven';
                if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 )
                    result = true;
                break;
            case '48bfa84d06702d2185b4b0a47eb4d01d':
                var cString = 'mex,gtm';
                if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 )
                    result = true;
                break;
            case '71791115dd7384c59214ad4697e6d15b':
                var cString = 'bol,chl,col,cri,ecu,gtm,hnd,nic,pan,per,pry,slv,ury,ven,mex';
                if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 )
                    result = true;
                break;
            case 'ce91be1cd60cdc434d7e5604182030bf':
                var cString = 'mex,gtm,slv,nic,hnd,pan,cri';
                if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 )
                    result = true;
                break;
            case '5115ee5df30264131b7a9c41b8fd752e':
                var cString = 'bol,chl,col,cri,ecu,slv,gtm,hnd,mex,nic,pan,per,pry,ury,ven';
                if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 )
                    result = true;
                break;
            case 'd7491456701ad4c98d1fe0f389a1225e':
                var cString = 'bol,chl,col,cri,ecu,slv,gtm,hnd,nic,pan,per,pry,ury,ven';
                if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 )
                    result = true;
                break;
            case '11a344833988376463d9fecda369580b':
                if( MN_geo.country.toLowerCase() == 'usa' )
                    result = true;
                break;
            case '5fb1f955b45e38e31789286a1790398d':
                result = true;
                break;
            // ESTADOS
            case '70d696597ff47b9b71ee3c9384c62f9e':
                var cString = 'usa,pri,vir,umi,asm,plw';
                if( cString.indexOf( MN_geo.country.toLowerCase() ) == -1 && MN_geo.state.toLowerCase() != 'que' )
                    result = true;
                break;
            case 'e673e0d8ec9ab6188f5551fffdb8dd22':
                var cString = 'usa,pri,vir,umi,asm,plw';
                if( cString.indexOf( MN_geo.country.toLowerCase() ) == -1 && MN_geo.state.toLowerCase() != 'jal' )
                    result = true;
                break;
            case '69239913289a8cb723a7fa071a52625c':
                var cString = 'usa,pri,vir,umi,asm,plw';
                if( cString.indexOf( MN_geo.country.toLowerCase() ) == -1 && MN_geo.state.toLowerCase() != 'slp' )
                    result = true;
                break;
            case '0064dc1902181149302bbf47aa921928':
                var cString = 'usa,pri,vir,umi,asm,plw';
                if( cString.indexOf( MN_geo.country.toLowerCase() ) == -1 && MN_geo.state.toLowerCase() != 'nle' )
                    result = true;
                break;
            case '54afac809a26220a351763e3734c8e93':
                var cString = 'usa,pri,vir,umi,asm,plw';
                if( cString.indexOf( MN_geo.country.toLowerCase() ) == -1 && MN_geo.state.toLowerCase() != 'roo' )
                    result = true;
                break;
            // LATAM - NOT SINGLE ESTATE
            case '1e9d517c7b36eb14ec294f39f4189583':
                var cString = 'ant,arg,brb,bmu,bol,bra,bra,bhs,blz,can,chl,col,cri,cri,cub,dma,ecu,dom,guf,glp,sgs,gtm,gum,guy,hnd,hti,jam,kna,cym,lca,mtq,mex,nic,pan,per,pry,sur,stp,slv,tto,ury,vct,ven';
                if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 && MN_geo.state.toLowerCase() != 'que' )
                    result = true;
                break;
            case '57d994124e8845f6315041d77e9d295e':
                var cString = 'ant,arg,brb,bmu,bol,bra,bra,bhs,blz,can,chl,col,cri,cri,cub,dma,ecu,dom,guf,glp,sgs,gtm,gum,guy,hnd,hti,jam,kna,cym,lca,mtq,mex,nic,pan,per,pry,sur,stp,slv,tto,ury,vct,ven';
                if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 && MN_geo.state.toLowerCase() != 'jal' )
                    result = true;
                break;
            case '5cc46744223866f912ff282557caadda':
                var cString = 'ant,arg,brb,bmu,bol,bra,bra,bhs,blz,can,chl,col,cri,cri,cub,dma,ecu,dom,guf,glp,sgs,gtm,gum,guy,hnd,hti,jam,kna,cym,lca,mtq,mex,nic,pan,per,pry,sur,stp,slv,tto,ury,vct,ven';
                if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 && MN_geo.state.toLowerCase() != 'slp' )
                    result = true;
                break;
            case 'fb5361cce87bdb60261f0b41335f3771':
                var cString = 'ant,arg,brb,bmu,bol,bra,bra,bhs,blz,can,chl,col,cri,cri,cub,dma,ecu,dom,guf,glp,sgs,gtm,gum,guy,hnd,hti,jam,kna,cym,lca,mtq,mex,nic,pan,per,pry,sur,stp,slv,tto,ury,vct,ven';
                if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 && MN_geo.state.toLowerCase() != 'nle' )
                    result = true;
                break;
            case '163b5b0f07d61ae8d1e33f6a9d379631':
                var cString = 'ant,arg,brb,bmu,bol,bra,bra,bhs,blz,can,chl,col,cri,cri,cub,dma,ecu,dom,guf,glp,sgs,gtm,gum,guy,hnd,hti,jam,kna,cym,lca,mtq,mex,nic,pan,per,pry,sur,stp,slv,tto,ury,vct,ven';
                if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 && MN_geo.state.toLowerCase() != 'roo' )
                    result = true;
                break;
            case 'e4a15899c397ca5ba2b77563b90eb811':
                var cString = 'ant,arg,brb,bmu,bol,bra,bra,bhs,blz,can,chl,col,cri,cri,cub,dma,ecu,dom,guf,glp,sgs,gtm,gum,guy,hnd,hti,jam,kna,cym,lca,mtq,mex,nic,pan,per,pry,sur,stp,slv,tto,ury,vct,ven';
                if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 && MN_geo.state.toLowerCase() != 'ver' )
                    result = true;
                break;
            case '9a3ae7c45fd231b425519f979ba20112':
                var cString = 'ant,arg,brb,bmu,bol,bra,bra,bhs,blz,can,chl,col,cri,cri,cub,dma,ecu,dom,guf,glp,sgs,gtm,gum,guy,hnd,hti,jam,kna,cym,lca,mtq,mex,nic,pan,per,pry,sur,stp,slv,tto,ury,vct,ven';
                if( cString.indexOf( MN_geo.country.toLowerCase() ) != -1 && MN_geo.state.toLowerCase() != 'chi' )
                    result = true;
                break;
        }
        return result;
    }
    this.GetIpadUrl = function() {
        //var dominio 		= "http://gbs01.esmas.com";
        //var dominio 		= "http://m4v.tvolucion.com";
        //var dominio 		= "http://gbs02.esmas.com";
        var dominio 		= "http://gbs04.esmas.com";
        var formatFolder  	= 'm4v';
        if(this.properties.storage != undefined){
            if(this.properties.storage == 'ext'){
                dominio = "http://extvod.esmas.com";
                formatFolder = 'mp4';
            }
        }
        if(this.properties.profile == 'bctp'){
            dominio= 'http://gbs04.esmas.com';
        }

        var foro = false;
        var val1 = false;
        if(this.properties.idgal != undefined){
            if(this.properties.idgal == '163226'){
                foro=true;
            }
        }
        if(this.properties.id =="ebefd20ad3d404113fa5d7660ea1ba73"){
            val1 = true;
        }

        if(foro){
            // return "http://hdidevices-i.akamaihd.net/hls/live/202990/ios01/index.m3u8";
            return "http://185417-f.akamaihd.net/i/n0tf0r0_1@81283/master.m3u8";
            //return "http://tvsawpdvr-lh.akamaihd.net/i/stch04wp_1@119661/master.m3u8";
        }else if(val1){
            return "http://m4vhds.tvolucion.com/i/m4v/boh/valen/ebefd20ad3d404113fa5d7660ea1ba73/d404113fa5-,150,235,480,600,970,.mp4.csmil/master.m3u8";
        }else{
            if ( this.properties.profile == 'hls2' )
                return dominio + '/' + formatFolder + '/' + this.properties.cat + '/' + this.properties.id + '/' + ( ( this.properties.id.split('/').length > 1 ) ? this.properties.id.split('/')[1] : this.properties.id ) + '/' + ( ( this.properties.id.split('/').length > 1 ) ? this.properties.id.split('/')[1] : this.properties.id ) + '.m3u8';
            else if ( this.properties.profile == 'hls1' )
                return dominio + '/' + formatFolder + '/' + this.properties.cat + '/' + this.properties.id + '/' + ( ( this.properties.id.split('/').length > 1 ) ? this.properties.id.split('/')[1] : this.properties.id ) + '.m3u8';
            else if ( this.properties.profile == 'tp2' ){
                return dominio + '/' + formatFolder + '/' + this.properties.cat + '/' + this.properties.id + '/' + ( ( this.properties.id.split('/').length > 1 ) ? this.properties.id.split('/')[1] : this.properties.id ) + '-480.mp4';
            }
            else if ( this.properties.profile == 'tp1' ){
                tempId = this.properties.id;
                if(this.hexadecimal(this.properties.id)){
                    if(this.properties.id.length == 32){
                        tempId=this.properties.id.substr(10,10);
                    }
                }
                if(this.properties.idgal == '197525'){
                    tempId= this.GetThumbnail();
                    atmpId= tempId.split('/')
                    numElems= atmpId.length;
                    tempId = atmpId[numElems-1];
                    tempId = tempId.substr(0,10);
                }

                return dominio + '/' + formatFolder + '/' + this.properties.cat + '/' + tempId + '-480.mp4';
            }
            else if( this.properties.profile == 'bcs' )
            //return 'http://flash.tvolucion.com/bright/files/' + this.properties.cat + '/' + this.properties.id + '.mp4';
                return 'http://apps.tvolucion.com/bright/files/' + this.properties.cat + '/' + this.properties.id + '.mp4';
            //else if( this.properties.profile == 'bcm4' || this.properties.profile == 'bca' )
            else if( this.properties.profile == 'bcm4')
                return 'http://apps.tvolucion.com/' + this.properties.cat + '/' + this.properties.id + '.mp4';
            else if( this.properties.profile == 'bctp')
                return dominio + '/m4v/' + this.properties.cat + '/' + this.properties.id + '.mp4';
            else if ( this.properties.profile == 'hls2b' ){
                tempId = this.properties.id;
                if(this.hexadecimal(this.properties.id)){
                    if(this.properties.id.length == 32){
                        tempId=this.properties.id.substr(10,10);
                    }
                    return dominio + '/' + formatFolder + '/' + this.properties.cat + '/' + tempId + '-480.mp4';
                }else{
                    return 	dominio + '/' +
                        formatFolder + '/' +
                        this.properties.cat + '/' +
                        ( ( this.properties.id.split('/').length > 1 ) ?
                            this.properties.id.split('/')[1] :
                            this.properties.id ) + '/' +
                        ( ( this.properties.id.split('/').length > 1 ) ?
                            this.properties.id.split('/')[1] :
                            this.properties.id ) +
                        '.m3u8';
                }
            }else{
                return 'http://media.esmas.com/criticalmedia/files/' + this.properties.cat + '/' + this.properties.id + '.mp4';
            }
        }
    }
    this.VideoNotAvailable = function( divId ) {
        document.getElementById(divId).innerHTML = '<div style="padding: 20px; text-align:center; font:bold 18px/19px Arial; color: #FFF; background: #000;">Lo sentimos, el video no está disponible para este dispositivo...</div>';
        document.getElementById(divId).style.height='344px';
        document.getElementById(divId).style.display='block';
    }
    this.CreatePlayer = function( divId ) {
        if( this.IsValidRegion() ) {
            if(this.properties.idgal != undefined){
                if(this.properties.idgal == '163226'){
                    document.getElementById(divId).innerHTML = '' +
                        '<video autoplay="true" controls="controls"  width="' + this.values.width + '" height="' + this.values.height +  '" poster="' + this.GetThumbnail() + '">' +
                        '<source src="http://185417-f.akamaihd.net/i/n0tf0r0_1@81283/master.m3u8" type="video/mp4; codecs=\'avc1,mp4a\'">'; +
                        '</video>';
                }else{
                    document.getElementById(divId).innerHTML = '' +
                        '<video src="' + this.GetIpadUrl() + '" controls="controls" width="' + this.values.width + '" height="' + this.values.height + '" autoplay="autoplay" poster="' + this.GetThumbnail() + '"></video>';
                }
            }else{
                document.getElementById(divId).innerHTML = '' +
                    '<video src="' + this.GetIpadUrl() + '" controls="controls" width="' + this.values.width + '" height="' + this.values.height + '" autoplay="autoplay" poster="' + this.GetThumbnail() + '"></video>';
            }
        }
        else {
            document.getElementById(divId).innerHTML = '<div style="padding: 20px; text-align:center; font:bold 18px/19px Arial; color: #FFF;">Lo sentimos, el video no está disponible para tu país...</div>';
        }
    }
    this.CreatePlayerM3 = function( divId,relation ) {
        if( this.IsValidRegion() ) {
            if(relation != undefined){
                document.getElementById(divId).innerHTML = '' +
                    '<video autoplay="true" controls="controls"  width="' + this.values.width + '" height="' + this.values.height +  '" poster="' + this.GetThumbnail() + '">' +
                    '<source src="'+ relation +'" type="video/mp4; codecs=\'avc1,mp4a\'">'; +
                    '</video>';
            }
        }
        else {
            document.getElementById(divId).innerHTML = '<div style="padding: 20px; text-align:center; font:bold 18px/19px Arial; color: #FFF;">Lo sentimos, el video no está disponible para tu país...</div>';
        }
    }
    this.hexadecimal = function(inputtxt)
    {
        var letterNumber = /^[0-9a-fA-F]+$/;
        if(inputtxt.match(letterNumber))
        {
            return true;
        }
        else
        {
            //alert("message");
            return false;
        }
    }
}