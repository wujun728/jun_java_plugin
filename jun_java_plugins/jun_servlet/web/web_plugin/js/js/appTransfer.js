var yesNoJson;
$.getJSON("<c:url value='/cust/outParamJsonByDomainCustInfo.tg?nickName=YESORNO'/>", function(json){
	yesNoJson=json;
});
function yesNoFormatter(value){
	for(var i=0; i<yesNoJson.length; i++){		
		if (yesNoJson[i].value == value) return yesNoJson[i].name;
	}
	return value;
}
var memberFromJson;
$.getJSON("<c:url value='/cust/outParamJsonByDomainCustInfo.tg?nickName=MEMBERFROM'/>", function(json){
	memberFromJson=json;
});
function memberFromFormatter(value){
	for(var i=0; i<memberFromJson.length; i++){		
		if (memberFromJson[i].value == value) return memberFromJson[i].name;
	}
	return value;
}
var memberLevelJson;
$.getJSON("<c:url value='/cust/outParamJsonByDomainCustInfo.tg?nickName=MEMBERFROM'/>", function(json){
	memberLevelJson=json;
});
function memberLevelFormatter(value){
	for(var i=0; i<memberLevelJson.length; i++){		
		if (memberLevelJson[i].value == value) return memberLevelJson[i].name;
	}
	return value;
}
var sexJson;
$.getJSON("<c:url value='/cust/outParamJsonByDomainCustInfo.tg?nickName=SEX'/>", function(json){
	sexJson=json;
});
function sexFormatter(value){
	for(var i=0; i<sexJson.length; i++){		
		if (sexJson[i].value == value) return sexJson[i].name;
	}
	return value;
}
var natureJson;
$.getJSON("<c:url value='/cust/outParamJsonByDomainCustInfo.tg?nickName=NATURE'/>", function(json){
	natureJson=json;
});
function natureFormatter(value){
	for(var i=0; i<natureJson.length; i++){		
		if (natureJson[i].value == value) return natureJson[i].name;
	}
	return value;
}
var insurancecomanyJson;
$.getJSON("<c:url value='/cust/outParamJsonByDomainCustInfo.tg?nickName=INSURANCE_COMANY'/>", function(json){
	insurancecomanyJson=json;
});
function insurancecomanyFormatter(value){
	for(var i=0; i<insurancecomanyJson.length; i++){		
		if (insurancecomanyJson[i].value == value) return insurancecomanyJson[i].name;
	}
	return value;
}
var areaJson;
$.getJSON("<c:url value='/cust/outParamJsonByDomainCustInfo.tg?nickName=CITY_AREA'/>", function(json){
	areaJson=json;
});
function areaFormatter(value){
	for(var i=0; i<areaJson.length; i++){		
		if (areaJson[i].value == value) return areaJson[i].name;
	}
	return value;
}
var payMethodJson;
$.getJSON("<c:url value='/cust/outParamJsonByDomainCustInfo.tg?nickName=PAY_METHOD'/>", function(json){
	payMethodJson=json;
});
function payMethodFormatter(value){
	for(var i=0; i<payMethodJson.length; i++){		
		if (payMethodJson[i].value == value) return payMethodJson[i].name;
	}
	return value;
}
var purchaseMethodJson;
$.getJSON("<c:url value='/cust/outParamJsonByDomainCustInfo.tg?nickName=PURCHASE_METHOD'/>", function(json){
	purchaseMethodJson=json;
});
function purchaseMethodFormatter(value){
	for(var i=0; i<purchaseMethodJson.length; i++){		
		if (purchaseMethodJson[i].value == value) return purchaseMethodJson[i].name;
	}
	return value;
}
var problemTypeJson;
$.getJSON("<c:url value='/cust/outParamJsonByDomainCustInfo.tg?nickName=PROBLEM_TYPE'/>", function(json){
	problemTypeJson=json;
});
function problemTypeFormatter(value){
	for(var i=0; i<problemTypeJson.length; i++){		
		if (problemTypeJson[i].value == value) return problemTypeJson[i].name;
	}
	return value;
}
var emergencyDegreeJson;
$.getJSON("<c:url value='/cust/outParamJsonByDomainCustInfo.tg?nickName=EMERGENCY_DEGREE'/>", function(json){
	emergencyDegreeJson=json;
});
function emergencyDegreeFormatter(value){
	for(var i=0; i<emergencyDegreeJson.length; i++){		
		if (emergencyDegreeJson[i].value == value) return emergencyDegreeJson[i].name;
	}
	return value;
}
var dealStatusJson;
$.getJSON("<c:url value='/cust/outParamJsonByDomainCustInfo.tg?nickName=DEAL_STATUS'/>", function(json){
	dealStatusJson=json;
});
function dealStatusFormatter(value){
	for(var i=0; i<dealStatusJson.length; i++){		
		if (dealStatusJson[i].value == value) return dealStatusJson[i].name;
	}
	return value;
}
var companyJson;
$.getJSON("<c:url value='/permissions/outDicJsonByNicknameActions.tg?nickName=COMPANY_DIC'/>", function(json){
	companyJson=json;
});
function companyFormatter(value){
	for(var i=0; i<companyJson.length; i++){		
		if (companyJson[i].value == value) return companyJson[i].name;
	}
	return value;
}