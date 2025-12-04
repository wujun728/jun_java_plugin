jQuery.extend({
	deparam : function(str) {
		var obj = {};
		var params = str.split('&');
		$.each(params, function(i, value) {
			var p = value.split('=');
			obj[p[0]] = p[1];
		});
		return obj;
	}
});

var r20 = /%20/g, rbracket = /\[\]$/;
/**
 */
var param = function(a, traditional) {
	var s = [], add = function(key, value) {
		value = jQuery.isFunction(value) ? value() : value;
		s[s.length] = encodeURIComponent(key) + "=" + encodeURIComponent(value);
	}, buildParams = function(prefix, obj, traditional, add) {
		if (jQuery.isArray(obj)) {
			// Serialize array item.
			jQuery.each(obj, function(i, v) {
				if (traditional || rbracket.test(prefix)) {
					// Treat each array item as a scalar.
					add(prefix, v);

				} else {
					// If array item is non-scalar (array or object), encode its
					// numeric index to resolve deserialization ambiguity
					// issues.
					// Note that rack (as of 1.0.0) can't currently deserialize
					// nested arrays properly, and attempting to do so may cause
					// a server error. Possible fixes are to modify rack's
					// deserialization algorithm or to provide an option or flag
					// to force array serialization to be shallow.
					buildParams(prefix + "["
							+ (typeof v === "object" || jQuery.isArray(v) ? i : i) + "]",
							v, traditional, add);
				}
			});

		} else if (!traditional && obj != null && typeof obj === "object") {
			// Serialize object item.
			for ( var name in obj) {
				buildParams(prefix + "." + name + "", obj[name], traditional, add);
			}

		} else {
			// Serialize scalar item.
			add(prefix, obj);
		}
	};
	if (traditional === undefined) {
		traditional = jQuery.ajaxSettings.traditional;
	}
	if (jQuery.isArray(a) || (a.jquery && !jQuery.isPlainObject(a))) {
		// Serialize the form elements
		jQuery.each(a, function() {
			add(this.name, this.value);
		});

	} else {
		for ( var prefix in a) {
			buildParams(prefix, a[prefix], traditional, add);
		}
	}
	return s.join("&").replace(r20, "+");
};

function stopEvent(event, _allowDefault) {
	var allowDefault = _allowDefault ? _allowDefault : false;
	if (!allowDefault) {
		if (event.preventDefault) {
			event.preventDefault();
		} else {
			event.returnValue = false;
		}
	}

	if (event.stopPropagation) {
		event.stopPropagation();
	} else {
		event.cancelBubble = true;
	}
}
// 打印日志
function log(obj) {
	try {
		if ((typeof console) != 'undefined') {
			console.log(obj);
		}
	} catch (e) {

	}
}