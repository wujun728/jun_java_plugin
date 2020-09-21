
function getStationData() {
    var stations = station_names.split("@");
    var array= [];
    for(var i=0;i<stations.length;i++){
        var json = {};
        var station_arry = stations[i].split("|");
        if(station_arry[1]==null || station_arry[1]=='')
            continue;
        json.serial = station_arry[5];
        json.station_name =  station_arry[1];
        json.station_code =  station_arry[2];
        json.station_py =  station_arry[0]+"," +station_arry[3]+","+station_arry[4];
        array.push(json);
    }
    var json2 = {message:"",value:array,code:200,redirect:""}
    var result_data = JSON.stringify(json2);
    console.log(result_data)
    return result_data;
}