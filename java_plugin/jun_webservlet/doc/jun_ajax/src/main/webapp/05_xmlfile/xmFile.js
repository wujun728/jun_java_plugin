window.onload = function(){
	var xhr = ajaxFunction();
	
	xhr.onreadystatechange = function(){
		if(xhr.readyState==4){
			if(xhr.status==200){
				var docXml = xhr.responseXML;		//获取xml格式的数据
				//alert(docXml);		//打印的是object类型，说明已经不是字符串
				
				var provinceXmlElements = docXml.getElementsByTagName("province");
				
				for(var i=0;i<provinceXmlElements.length;i++){
					var provinceXmlElement = provinceXmlElements[i];
					
					var provinceXmlValue = provinceXmlElement.getAttribute("name");
					
					/*
					 * <select id="province" name="province">
					       <option value="">请选择....</option>
					     </select>
					 */
					var optionElement = document.createElement("option");
					optionElement.setAttribute("value",provinceXmlValue);
					var provinceText = document.createTextNode(provinceXmlValue);
					optionElement.appendChild(provinceText);
					
					var provinceElement = document.getElementById("province");
					provinceElement.appendChild(optionElement);
					
				}
				
				//处理页面中第二个下拉选与处理页面中第一个下拉选的内容是没有关系的,所以不能放置在遍历省份里面
				
				document.getElementById("province").onchange = function(){
					//1 获取页面中选中的省份
					var provinceValue = this.value;
					
					//清空
					/*
					 * <select id="city" name="city">
						 	<option value="">请选择.....</option>
						 </select>
					 */
					var cityElement = document.getElementById("city");
					var optionsOld = cityElement.getElementsByTagName("option");
					for(var z=1;z<optionsOld.length;){
						cityElement.removeChild(optionsOld[z]);
					}
					
					//2 遍历解析后的省份
					for(var i=0;i<provinceXmlElements.length;i++){
						var provinceXmlElement = provinceXmlElements[i];
						
						var provinceXmlValue = provinceXmlElement.getAttribute("name");
						
						//3 作对比
						if(provinceValue==provinceXmlValue){
							//4 获取对应省份里的所有城市信息
							var cityXmlElements = provinceXmlElement.getElementsByTagName("city");
							
							for(var j=0;j<cityXmlElements.length;j++){
								var cityXmlElement = cityXmlElements[j];
								
								var cityXmlValue = cityXmlElement.firstChild.nodeValue;
								
								//5 放置到页面的第二个下拉选中
								/*
								 * <select id="city" name="city">
									 	<option value="">请选择.....</option>
									 </select>
								 */
								var optionElement = document.createElement("option");
								optionElement.setAttribute("value",cityXmlValue);
								var cityText = document.createTextNode(cityXmlValue);
								optionElement.appendChild(cityText);
								
								cityElement.appendChild(optionElement);
								
							}
						}
					}
				}
			}
		}
	}
	
	xhr.open("post","../xmlFileServlet?timeStamp="+new Date().getTime(),true);
	
	xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	
	xhr.send(null);
}

function ajaxFunction(){
   var xmlHttp;
   try{ // Firefox, Opera 8.0+, Safari
        xmlHttp=new XMLHttpRequest();
    }
    catch (e){
	   try{// Internet Explorer
	         xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
	      }
	    catch (e){
	      try{
	         xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
	      }
	      catch (e){}
	      }
    }

	return xmlHttp;
 }