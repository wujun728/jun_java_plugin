    function create () 
    { 
        if (window.XMLHttpRequest) 
        { 
            var objXMLHttp = new XMLHttpRequest(); 
     
        } 
        else 
        { 
            var MSXML = ['MSXML2.XMLHTTP.5.0', 'MSXML2.XMLHTTP.4.0', 'MSXML2.XMLHTTP.3.0', 'MSXML2.XMLHTTP', 'Microsoft.XMLHTTP']; 
            for(var n = 0; n < MSXML.length; n ++) 
            { 
                try 
                { 
                    var objXMLHttp = new ActiveXObject(MSXML[n]); 
                    break; 
                } 
                catch(e) 
                { 
                } 
            } 
         }           
     
        // mozilla某些版本没有readyState属性 
        if (objXMLHttp.readyState == null) 
        { 
            objXMLHttp.readyState = 0; 
     
            objXMLHttp.addEventListener("load", function () 
                { 
                    objXMLHttp.readyState = 4; 
     
                    if (typeof objXMLHttp.onreadystatechange == "function") 
                    { 
                        objXMLHttp.onreadystatechange(); 
                    } 
                },  false); 
        } 
     
        return objXMLHttp; 
    }
    
    function  sendReq(method, url, data, callback) 
    { 
    	
        var objXMLHttp = create () ;
        with(objXMLHttp) 
        { 
            try 
            { 
                // 加随机数防止缓存 
                if (url.indexOf("?") > 0) 
                { 
                    url += "&randnum=" + Math.random(); 
                } 
                else 
                { 
                    url += "?randnum=" + Math.random(); 
                } 
     
                open(method, url, true); 
     
                // 设定请求编码方式 
                var encode =  'application/x-www-form-urlencoded; charset=UTF-8';
                setRequestHeader('Content-Type',encode); 
                send(data); 
                onreadystatechange = function () 
                { 
                    if (objXMLHttp.readyState == 4 && (objXMLHttp.status == 200 || objXMLHttp.status == 304)) 
                    { 
                        callback(objXMLHttp);
                    } 
                } 
            } 
            catch(e) 
            { 
                alert(e); 
            } 
        } 
    }
    
    