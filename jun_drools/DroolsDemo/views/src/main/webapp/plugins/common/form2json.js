/**
 * ---form properties to json ($scope.Model) --- -- eachCheckBox (return array
 * or string) --- ---Directions: (Version 1.0) <input required
 * model="Model.object...property" /> $(id or class).getValue(); return
 * :Model{object:{objct:{},prop},prop}
 * 
 * by lga 2015-10-31
 * 
 * ---Directions: (Version 1.1) $(id or class).setValue( $scope.Model);
 * 
 * by lga 2015-11-4
 */
var $scope = {};
var $function = {};

var $month = [];
var $times = [];
var $minutes = [];

var configProjectId;
var INTERVALID = 0; // setInterval ID

$(function() {

	var attr;
	var prop;
	var model;
	var id;

	var del = [];

	var strIsTrue = function(str) {
		if (str != "" && typeof (str) != "undefined" && str != null
				&& str != $.t("common.choose"))
			return true;
		return false;
	}

	var filterJSON = function(json) {
		for ( var key in json) {
			for (var i = 0; i < del.length; i++) {
				if (key == del[i]) {
					delete json[key];
					break;
				}
			}
		}
	}

	var filterID = function(json) {
		for ( var key in json) {
			if (typeof (json[key]) == "object") {
				filterID(json[key]);
			} else {
				if (key == "id" && !strIsTrue(json[key])) {
					delete json[key];
				}
			}
		}
	}

	/** !important */
	var filterStr = function(str) {
		var S = str.split(".");
		var L = S.length;
		var tmpLevel = [];
		var tmpObj = [];
		var model = "";
		for (var i = L - 1; i >= 0; i--) {
			var tmpS = S[i].split("@");
			var level = tmpS[0];
			var obj = tmpS[1];
			if (i == L - 1) {
				tmpLevel.push(level);
				tmpObj.push(obj);
			} else {
				for (var j = 0; j < tmpLevel.length; j++) {
					if (level >= tmpLevel[j]) {
						break;
					} else if (j == tmpLevel.length - 1) {
						tmpObj.push(obj);
						tmpLevel.push(level);
					}
				}
			}
		}
		for (var i = tmpObj.length - 1; i >= 0; i--) {
			model += tmpObj[i] + ".";
		}
		return model.substring(0, model.length - 1);
	}

	/** !important */
	var tmp = [];
	var base = "";
	var level = 0;
	function json2Str(json) {
		level++;
		for ( var key in json) {
			var js = {
				name : base + level + "@" + key,
				value : json[key]
			};
			if (typeof (json[key]) == "object") {
				base += level + "@" + key + ".";
				json2Str(json[key]);
				level--;
			} else {
				js.name = filterStr(js.name);
				tmp.push(js);
			}
		}
		return tmp;
	}
	/** !important */
	function str2Json(model, value) {

		var strs = model.split(".");
		var L = strs.length;

		var T = $scope[strs[0]];
		var tmp = T ? T : {};

		for (var i = L - 1; i >= 0; i--) {
			if (i == L - 1) {
				tmp[strs[i]] = value;
			}
			if (i < L - 1 && i > 0) {
				tmp[strs[i]] = tmp[strs[i]] ? tmp[strs[i]] : {};
				tmp[strs[i]][strs[i + 1]] = tmp[strs[i + 1]];
				del.push(strs[i + 1]);
			}
			if (i == 0) {
				$scope[strs[i]] = tmp;
			}
		}
	}

	$function.startScanValue = function(_id) {
		$scope = {};
		var tmp = {};
		$(_id)
				.find("input[model],select[model],textarea[model],div[model]")
				.each(
						function(i) {

							var model = $(this).attr("model");
							var PROP = $(this).val();
							var ATTR = $(this).attr("name");
							var ID = $(this).attr("id");

							if ($(this).attr("type") == "radio") {
								if (typeof ($(this).attr("checked")) == "undefined") {
									return true;
								}
							}

							if ($(this).is("div")
									&& $(this).attr("type") == "checkBox") {
								var value = "";
								$.each($(this).find(
										"input[type='checkBox']:checked"),
										function(i, checkbox) {
											value += $(this).attr("value")
													+ ",";
										});
								PROP = value.substring(0, value.length - 1);
							}

							str2Json(model, PROP);
						});
		filterJSON($scope["Model"]);
		filterID($scope["Model"]);
	};

	$.fn.getValue = function() {
		$function.startScanValue(this);
	}

	$.fn.setValue = function(data) {
		tmp = [];
		base = "";
		level = 0;

		var models = json2Str(data);
		for (var i = 0; i < models.length; i++) {
			var el = $(this).find('[model="' + models[i].name + '"]');
			if ($(el).is("input, select, textarea")) {
				$(el).val(models[i].value);
			} else if ($(el).is("div") && "checkBox" == $(el).attr("type")) {
				var strs = models[i].value.split(",");
				$.each(strs, function(i, check) {
					$(el).find("input[value='" + check + "']").attr("checked",
							true);
				});
			} else {
				$(el).text(models[i].value);
			}
		}
	}

	$.fn.eachArray = function() {
		var array = [];
		$(this).find("tr").each(function(i) {
			var tmp = {};
			$(this).find("td  input[type='text']").each(function(j) {
				var name = $(this).attr("name");
				var value = $(this).val();
				tmp[name] = value;
			});
			array.push(tmp);
		});
		return array;
	};

	$.fn.eachArrayPlus = function() {
		var array = [];
		$(this).find("tr[class!='filterTypeTitle']").each(function(i) {
			var tmp = {};
			$(this).find("td div input").each(function(j) {
				var name = $(this).parent().attr("name");
				var value = $(this).val();
				tmp[name] = value;
			});
			$(this).find("td  input").each(function(j) {
				var name = $(this).attr("name");
				if (name == "") {
					return true;
				}
				var value = $(this).val();
				tmp[name] = value;
			});
			$(this).find("td  select").each(function(j) {
				var name = $(this).attr("name");
				var value = $(this).val();
				tmp[name] = value;
			});
			array.push(tmp);
		});
		return array;
	};
	$function.filterID = function(json) {
		filterID(json);
	};

	$function.clear = function() {
		$scope = {};
	}

	$function.back = function() {
		history.go(-1);
	}

	$function.clone = function(jsonObj) {
		return JSON.parse(JSON.stringify(jsonObj));
	}	

	// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
	// (new Date()).Format("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
	Date.prototype.Format = function(fmt) { // author: meizz
		var o = {
			"M+" : this.getMonth() + 1,
			"d+" : this.getDate(),
			"h+" : this.getHours(),
			"m+" : this.getMinutes(),
			"s+" : this.getSeconds(),
			"q+" : Math.floor((this.getMonth() + 3) / 3),
			"S" : this.getMilliseconds()
		};
		if (/(y+)/.test(fmt))
			fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
					.substr(4 - RegExp.$1.length));
		for ( var k in o)
			if (new RegExp("(" + k + ")").test(fmt))
				fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
						: (("00" + o[k]).substr(("" + o[k]).length)));
		return fmt;
	}

	String.prototype.parseDate = function() {
		var str = this.replace(/-/g, "/");
		var date = new Date(str);
		return date;
	}

	$function.generateYear = function(year, n) {
		var $years = [];
		for (var i = 0; i < n; i++) {
			$years.push(year - i);
		}
		return $years;
	}

	function generateMonth() {
		for (var i = 1; i <= 12; i++) {
			if (i < 10)
				i = "0" + i;
			$month.push(i);
		}
	}

	generateMonth();

	function generateTimes() {
		for (var i = 0; i < 24; i++) {
			if (i < 10)
				i = "0" + i;
			$times.push(i);
		}
	}

	generateTimes();

	function generateMinutes() {
		for (var i = 0; i < 60; i++) {
			if (i < 10)
				i = "0" + i;
			$minutes.push(i);
		}
	}

	generateMinutes();

		

});
