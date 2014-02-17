function DateFmt() {
    this.dateMarkers = {
        d:['getDate',function(v) { return ("0"+v).substr(-2,2)}],
        m:['getMonth',function(v) { return ("0"+(v+1)).substr(-2,2)}],
        n:['getMonth',function(v) {
            var mthNames = ["Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dic"];
            return mthNames[v];
        }],
        w:['getDay',function(v) {
            var dayNames = ["Domingo","Lunes","Martes","Miércoles","Jueves","Viernes","Sabado"];
            return dayNames[v];
        }],
        y:['getFullYear'],
        H:['getHours',function(v) { return ("0"+v).substr(-2,2)}],
        M:['getMinutes',function(v) { return ("0"+v).substr(-2,2)}],
        S:['getSeconds',function(v) { return ("0"+v).substr(-2,2)}],
        i:['toISOString',null]
    };

    this.format = function(date, fmt) {
        var dateMarkers = this.dateMarkers
        var dateTxt = fmt.replace(/%(.)/g, function(m, p){
            var rv = date[(dateMarkers[p])[0]]()

            if ( dateMarkers[p][1] != null ) rv = dateMarkers[p][1](rv)

            return rv
        });

        return dateTxt
    }
}
function setTime(){
    var now = new Date();
    fmt = new DateFmt();
    var dateContainer = document.getElementById('date');
    if(dateContainer != null){
        dateContainer.innerHTML=fmt.format(new Date(),"%w %d %n, %H:%M");
    }
}

x=setInterval(setTime,60000);

