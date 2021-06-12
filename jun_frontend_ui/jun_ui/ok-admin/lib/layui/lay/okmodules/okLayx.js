!function (t, e) {
    // "object" == typeof exports && "object" == typeof module ? module.exports = e() : "function" == typeof define && define.amd ? define([], e) : "object" == typeof exports ? exports.layx = e() : t.layx = e()
    // 加入layui模块规范
    layui.define(function (exports) {
        exports("okLayx", e());
    }).addcss("okmodules/layx.min.css");
}(window, function () {
    return (d = [function (t, e, n) {
        (function (d) {
            t.exports = function () {
                "use strict";
                var t = function (t) {
                    var e = t.id, n = t.viewBox, i = t.content;
                    this.id = e, this.viewBox = n, this.content = i
                };
                t.prototype.stringify = function () {
                    return this.content
                }, t.prototype.toString = function () {
                    return this.stringify()
                }, t.prototype.destroy = function () {
                    var e = this;
                    ["id", "viewBox", "content"].forEach(function (t) {
                        return delete e[t]
                    })
                };
                "undefined" != typeof window ? window : void 0 !== d || "undefined" != typeof self && self;

                function e(t, e) {
                    return t(e = {exports: {}}, e.exports), e.exports
                }

                var o = e(function (t, e) {
                    t.exports = function () {
                        function r(t) {
                            var e = t && "object" == typeof t;
                            return e && "[object RegExp]" !== Object.prototype.toString.call(t) && "[object Date]" !== Object.prototype.toString.call(t)
                        }

                        function s(t, e) {
                            var n, i = e && !0 === e.clone;
                            return i && r(t) ? u((n = t, Array.isArray(n) ? [] : {}), t, e) : t
                        }

                        function a(n, t, i) {
                            var o = n.slice();
                            return t.forEach(function (t, e) {
                                void 0 === o[e] ? o[e] = s(t, i) : r(t) ? o[e] = u(n[e], t, i) : -1 === n.indexOf(t) && o.push(s(t, i))
                            }), o
                        }

                        function d(e, n, i) {
                            var o = {};
                            return r(e) && Object.keys(e).forEach(function (t) {
                                o[t] = s(e[t], i)
                            }), Object.keys(n).forEach(function (t) {
                                r(n[t]) && e[t] ? o[t] = u(e[t], n[t], i) : o[t] = s(n[t], i)
                            }), o
                        }

                        function u(t, e, n) {
                            var i = Array.isArray(e), o = n || {arrayMerge: a}, r = o.arrayMerge || a;
                            return i ? Array.isArray(t) ? r(t, e, n) : s(e, n) : d(t, e, n)
                        }

                        return u.all = function (t, n) {
                            if (!Array.isArray(t) || t.length < 2) throw new Error("first argument should be an array with at least two elements");
                            return t.reduce(function (t, e) {
                                return u(t, e, n)
                            })
                        }, u
                    }()
                }), n = e(function (t, e) {
                    e.default = {
                        svg: {name: "xmlns", uri: "http://www.w3.org/2000/svg"},
                        xlink: {name: "xmlns:xlink", uri: "http://www.w3.org/1999/xlink"}
                    }, t.exports = e.default
                }), i = n.svg, r = n.xlink, s = {};
                s[i.name] = i.uri, s[r.name] = r.uri;
                var a = function (t, e) {
                    void 0 === t && (t = "");
                    var n = o(s, e || {}), i = function (n) {
                        return Object.keys(n).map(function (t) {
                            var e = n[t].toString().replace(/"/g, "&quot;");
                            return t + '="' + e + '"'
                        }).join(" ")
                    }(n);
                    return "<svg " + i + ">" + t + "</svg>"
                };
                return function (t) {
                    function e() {
                        t.apply(this, arguments)
                    }

                    t && (e.__proto__ = t), (e.prototype = Object.create(t && t.prototype)).constructor = e;
                    var n = {isMounted: {}};
                    return n.isMounted.get = function () {
                        return !!this.node
                    }, e.createFromExistingNode = function (t) {
                        return new e({
                            id: t.getAttribute("id"),
                            viewBox: t.getAttribute("viewBox"),
                            content: t.outerHTML
                        })
                    }, e.prototype.destroy = function () {
                        this.isMounted && this.unmount(), t.prototype.destroy.call(this)
                    }, e.prototype.mount = function (t) {
                        if (this.isMounted) return this.node;
                        var e = "string" == typeof t ? document.querySelector(t) : t, n = this.render();
                        return this.node = n, e.appendChild(n), n
                    }, e.prototype.render = function () {
                        var t = this.stringify();
                        return function (t) {
                            var e = !!document.importNode,
                                n = (new DOMParser).parseFromString(t, "image/svg+xml").documentElement;
                            if (e) {
                                return document.importNode(n, true)
                            }
                            return n
                        }(a(t)).childNodes[0]
                    }, e.prototype.unmount = function () {
                        this.node.parentNode.removeChild(this.node)
                    }, Object.defineProperties(e.prototype, n), e
                }(t)
            }()
        }).call(this, n(14))
    }, function (t, e, n) {
        (function (H) {
            t.exports = function () {
                "use strict";
                "undefined" != typeof window ? window : void 0 !== H || "undefined" != typeof self && self;

                function t(t, e) {
                    return t(e = {exports: {}}, e.exports), e.exports
                }

                var a = t(function (t, e) {
                    t.exports = function () {
                        function r(t) {
                            var e = t && "object" == typeof t;
                            return e && "[object RegExp]" !== Object.prototype.toString.call(t) && "[object Date]" !== Object.prototype.toString.call(t)
                        }

                        function s(t, e) {
                            var n, i = e && !0 === e.clone;
                            return i && r(t) ? u((n = t, Array.isArray(n) ? [] : {}), t, e) : t
                        }

                        function a(n, t, i) {
                            var o = n.slice();
                            return t.forEach(function (t, e) {
                                void 0 === o[e] ? o[e] = s(t, i) : r(t) ? o[e] = u(n[e], t, i) : -1 === n.indexOf(t) && o.push(s(t, i))
                            }), o
                        }

                        function d(e, n, i) {
                            var o = {};
                            return r(e) && Object.keys(e).forEach(function (t) {
                                o[t] = s(e[t], i)
                            }), Object.keys(n).forEach(function (t) {
                                r(n[t]) && e[t] ? o[t] = u(e[t], n[t], i) : o[t] = s(n[t], i)
                            }), o
                        }

                        function u(t, e, n) {
                            var i = Array.isArray(e), o = n || {arrayMerge: a}, r = o.arrayMerge || a;
                            return i ? Array.isArray(t) ? r(t, e, n) : s(e, n) : d(t, e, n)
                        }

                        return u.all = function (t, n) {
                            if (!Array.isArray(t) || t.length < 2) throw new Error("first argument should be an array with at least two elements");
                            return t.reduce(function (t, e) {
                                return u(t, e, n)
                            })
                        }, u
                    }()
                });
                var e = t(function (t, e) {
                    e.default = {
                        svg: {name: "xmlns", uri: "http://www.w3.org/2000/svg"},
                        xlink: {name: "xmlns:xlink", uri: "http://www.w3.org/1999/xlink"}
                    }, t.exports = e.default
                }), n = e.svg, i = e.xlink, o = {};
                o[n.name] = n.uri, o[i.name] = i.uri;
                var r, s = function (t, e) {
                        void 0 === t && (t = "");
                        var n = a(o, e || {}), i = function (n) {
                            return Object.keys(n).map(function (t) {
                                var e = n[t].toString().replace(/"/g, "&quot;");
                                return t + '="' + e + '"'
                            }).join(" ")
                        }(n);
                        return "<svg " + i + ">" + t + "</svg>"
                    }, d = e.svg, u = e.xlink,
                    p = {attrs: (r = {style: ["position: absolute", "width: 0", "height: 0"].join("; ")}, r[d.name] = d.uri, r[u.name] = u.uri, r)},
                    l = function (t) {
                        this.config = a(p, t || {}), this.symbols = []
                    };
                l.prototype.add = function (t) {
                    var e = this.symbols, n = this.find(t.id);
                    return n ? (e[e.indexOf(n)] = t, !1) : (e.push(t), !0)
                }, l.prototype.remove = function (t) {
                    var e = this.symbols, n = this.find(t);
                    return !!n && (e.splice(e.indexOf(n), 1), n.destroy(), !0)
                }, l.prototype.find = function (e) {
                    return this.symbols.filter(function (t) {
                        return t.id === e
                    })[0] || null
                }, l.prototype.has = function (t) {
                    return null !== this.find(t)
                }, l.prototype.stringify = function () {
                    var t = this.config, e = t.attrs, n = this.symbols.map(function (t) {
                        return t.stringify()
                    }).join("");
                    return s(n, e)
                }, l.prototype.toString = function () {
                    return this.stringify()
                }, l.prototype.destroy = function () {
                    this.symbols.forEach(function (t) {
                        return t.destroy()
                    })
                };
                var c = function (t) {
                    var e = t.id, n = t.viewBox, i = t.content;
                    this.id = e, this.viewBox = n, this.content = i
                };
                c.prototype.stringify = function () {
                    return this.content
                }, c.prototype.toString = function () {
                    return this.stringify()
                }, c.prototype.destroy = function () {
                    var e = this;
                    ["id", "viewBox", "content"].forEach(function (t) {
                        return delete e[t]
                    })
                };
                var h = function (t) {
                    var e = !!document.importNode,
                        n = (new DOMParser).parseFromString(t, "image/svg+xml").documentElement;
                    return e ? document.importNode(n, !0) : n
                }, f = function (t) {
                    function e() {
                        t.apply(this, arguments)
                    }

                    t && (e.__proto__ = t), (e.prototype = Object.create(t && t.prototype)).constructor = e;
                    var n = {isMounted: {}};
                    return n.isMounted.get = function () {
                        return !!this.node
                    }, e.createFromExistingNode = function (t) {
                        return new e({
                            id: t.getAttribute("id"),
                            viewBox: t.getAttribute("viewBox"),
                            content: t.outerHTML
                        })
                    }, e.prototype.destroy = function () {
                        this.isMounted && this.unmount(), t.prototype.destroy.call(this)
                    }, e.prototype.mount = function (t) {
                        if (this.isMounted) return this.node;
                        var e = "string" == typeof t ? document.querySelector(t) : t, n = this.render();
                        return this.node = n, e.appendChild(n), n
                    }, e.prototype.render = function () {
                        var t = this.stringify();
                        return h(s(t)).childNodes[0]
                    }, e.prototype.unmount = function () {
                        this.node.parentNode.removeChild(this.node)
                    }, Object.defineProperties(e.prototype, n), e
                }(c), m = {
                    autoConfigure: !0,
                    mountTo: "body",
                    syncUrlsWithBaseTag: !1,
                    listenLocationChangeEvent: !0,
                    locationChangeEvent: "locationChange",
                    locationChangeAngularEmitter: !1,
                    usagesToUpdate: "use[*|href]",
                    moveGradientsOutsideSymbol: !1
                }, w = function (t) {
                    return Array.prototype.slice.call(t, 0)
                }, v = {
                    isChrome: function () {
                        return /chrome/i.test(navigator.userAgent)
                    }, isFirefox: function () {
                        return /firefox/i.test(navigator.userAgent)
                    }, isIE: function () {
                        return /msie/i.test(navigator.userAgent) || /trident/i.test(navigator.userAgent)
                    }, isEdge: function () {
                        return /edge/i.test(navigator.userAgent)
                    }
                }, y = function (t) {
                    return (t || window.location.href).split("#")[0]
                }, g = function (i) {
                    angular.module("ng").run(["$rootScope", function (t) {
                        t.$on("$locationChangeSuccess", function (t, e, n) {
                            !function (t, e) {
                                var n = document.createEvent("CustomEvent");
                                n.initCustomEvent(t, false, false, e), window.dispatchEvent(n)
                            }(i, {oldUrl: n, newUrl: e})
                        })
                    }])
                }, b = function (t, n) {
                    return void 0 === n && (n = "linearGradient, radialGradient, pattern"), w(t.querySelectorAll("symbol")).forEach(function (e) {
                        w(e.querySelectorAll(n)).forEach(function (t) {
                            e.parentNode.insertBefore(t, e)
                        })
                    }), t
                };
                var x = e.xlink.uri, _ = "xlink:href", C = /[{}|\\\^\[\]`"<>]/g;

                function E(t) {
                    return t.replace(C, function (t) {
                        return "%" + t[0].charCodeAt(0).toString(16).toUpperCase()
                    })
                }

                var O,
                    M = ["clipPath", "colorProfile", "src", "cursor", "fill", "filter", "marker", "markerStart", "markerMid", "markerEnd", "mask", "stroke", "style"],
                    B = M.map(function (t) {
                        return "[" + t + "]"
                    }).join(","), I = function (t, e, n, i) {
                        var o = E(n), r = E(i), s = t.querySelectorAll(B), a = function (t, o) {
                            return w(t).reduce(function (t, e) {
                                if (!e.attributes) return t;
                                var n = w(e.attributes), i = o ? n.filter(o) : n;
                                return t.concat(i)
                            }, [])
                        }(s, function (t) {
                            var e = t.localName, n = t.value;
                            return -1 !== M.indexOf(e) && -1 !== n.indexOf("url(" + o)
                        });
                        a.forEach(function (t) {
                            return t.value = t.value.replace(new RegExp(function (t) {
                                return t.replace(/[.*+?^${}()|[\]\\]/g, "\\$&")
                            }(o), "g"), r)
                        }), function (t, i, o) {
                            w(t).forEach(function (t) {
                                var e = t.getAttribute(_);
                                if (e && 0 === e.indexOf(i)) {
                                    var n = e.replace(i, o);
                                    t.setAttributeNS(x, _, n)
                                }
                            })
                        }(e, o, r)
                    }, S = {MOUNT: "mount", SYMBOL_MOUNT: "symbol_mount"}, j = function (s) {
                        function t(t) {
                            var e = this;
                            void 0 === t && (t = {}), s.call(this, a(m, t));
                            var n = function (i) {
                                return i = i || Object.create(null), {
                                    on: function (t, e) {
                                        (i[t] || (i[t] = [])).push(e)
                                    }, off: function (t, e) {
                                        i[t] && i[t].splice(i[t].indexOf(e) >>> 0, 1)
                                    }, emit: function (e, n) {
                                        (i[e] || []).map(function (t) {
                                            t(n)
                                        }), (i["*"] || []).map(function (t) {
                                            t(e, n)
                                        })
                                    }
                                }
                            }();
                            this._emitter = n, this.node = null;
                            var i = this.config;
                            if (i.autoConfigure && this._autoConfigure(t), i.syncUrlsWithBaseTag) {
                                var o = document.getElementsByTagName("base")[0].getAttribute("href");
                                n.on(S.MOUNT, function () {
                                    return e.updateUrls("#", o)
                                })
                            }
                            var r = this._handleLocationChange.bind(this);
                            this._handleLocationChange = r, i.listenLocationChangeEvent && window.addEventListener(i.locationChangeEvent, r), i.locationChangeAngularEmitter && g(i.locationChangeEvent), n.on(S.MOUNT, function (t) {
                                i.moveGradientsOutsideSymbol && b(t)
                            }), n.on(S.SYMBOL_MOUNT, function (t) {
                                i.moveGradientsOutsideSymbol && b(t.parentNode), (v.isIE() || v.isEdge()) && function (t) {
                                    var e = [];
                                    w(t.querySelectorAll("style")).forEach(function (t) {
                                        t.textContent += "", e.push(t)
                                    })
                                }(t)
                            })
                        }

                        s && (t.__proto__ = s), (t.prototype = Object.create(s && s.prototype)).constructor = t;
                        var e = {isMounted: {}};
                        return e.isMounted.get = function () {
                            return !!this.node
                        }, t.prototype._autoConfigure = function (t) {
                            var e = this.config;
                            void 0 === t.syncUrlsWithBaseTag && (e.syncUrlsWithBaseTag = void 0 !== document.getElementsByTagName("base")[0]), void 0 === t.locationChangeAngularEmitter && (e.locationChangeAngularEmitter = "angular" in window), void 0 === t.moveGradientsOutsideSymbol && (e.moveGradientsOutsideSymbol = v.isFirefox())
                        }, t.prototype._handleLocationChange = function (t) {
                            var e = t.detail, n = e.oldUrl, i = e.newUrl;
                            this.updateUrls(n, i)
                        }, t.prototype.add = function (t) {
                            var e = s.prototype.add.call(this, t);
                            return this.isMounted && e && (t.mount(this.node), this._emitter.emit(S.SYMBOL_MOUNT, t.node)), e
                        }, t.prototype.attach = function (t) {
                            var e = this, n = this;
                            if (n.isMounted) return n.node;
                            var i = "string" == typeof t ? document.querySelector(t) : t;
                            return n.node = i, this.symbols.forEach(function (t) {
                                t.mount(n.node), e._emitter.emit(S.SYMBOL_MOUNT, t.node)
                            }), w(i.querySelectorAll("symbol")).forEach(function (t) {
                                var e = f.createFromExistingNode(t);
                                e.node = t, n.add(e)
                            }), this._emitter.emit(S.MOUNT, i), i
                        }, t.prototype.destroy = function () {
                            var t = this.config, e = this.symbols, n = this._emitter;
                            e.forEach(function (t) {
                                return t.destroy()
                            }), n.off("*"), window.removeEventListener(t.locationChangeEvent, this._handleLocationChange), this.isMounted && this.unmount()
                        }, t.prototype.mount = function (t, e) {
                            void 0 === t && (t = this.config.mountTo), void 0 === e && (e = !1);
                            if (this.isMounted) return this.node;
                            var n = "string" == typeof t ? document.querySelector(t) : t, i = this.render();
                            return this.node = i, e && n.childNodes[0] ? n.insertBefore(i, n.childNodes[0]) : n.appendChild(i), this._emitter.emit(S.MOUNT, i), i
                        }, t.prototype.render = function () {
                            return h(this.stringify())
                        }, t.prototype.unmount = function () {
                            this.node.parentNode.removeChild(this.node)
                        }, t.prototype.updateUrls = function (t, e) {
                            if (!this.isMounted) return !1;
                            var n = document.querySelectorAll(this.config.usagesToUpdate);
                            return I(this.node, n, y(t) + "#", y(e) + "#"), !0
                        }, Object.defineProperties(t.prototype, e), t
                    }(l), A = t(function (t) {
                        /*!
      * domready (c) Dustin Diaz 2014 - License MIT
      */
                        t.exports = function () {
                            var t, e = [], n = document, i = n.documentElement.doScroll, o = "DOMContentLoaded",
                                r = (i ? /^loaded|^c/ : /^loaded|^i|^c/).test(n.readyState);
                            return r || n.addEventListener(o, t = function () {
                                for (n.removeEventListener(o, t), r = 1; t = e.shift();) t()
                            }), function (t) {
                                r ? setTimeout(t, 0) : e.push(t)
                            }
                        }()
                    }), P = "__SVG_SPRITE_NODE__", k = "__SVG_SPRITE__";
                window[k] ? O = window[k] : (O = new j({attrs: {id: P}}), window[k] = O);
                var T = function () {
                    var t = document.getElementById(P);
                    t ? O.attach(t) : O.mount(document.body, !0)
                };
                return document.body ? T() : A(T), O
            }()
        }).call(this, n(14))
    }, function (t, e, n) {
        "use strict";

        function o(t, i, o) {
            void 0 === o && (o = "layx-");
            for (var e = [], n = 3; n < arguments.length; n++) e[n - 3] = arguments[n];
            var r = t.className.split(/\s+/g);
            return e.forEach(function (t) {
                if (t) {
                    var e = o + t, n = r.indexOf(e);
                    i(r, n, e)
                }
            }), t.className = r.join(" ").trim(), t
        }

        Object.defineProperty(e, "__esModule", {value: !0}), e.createFragment = function () {
            return document.createDocumentFragment()
        }, e.createElement = function (t) {
            return document.createElement(t)
        }, e.createElementNS = function (t) {
            return document.createElementNS("http://www.w3.org/2000/svg", t)
        }, e.addStyles = function (t, e) {
            if (null === t) return t;
            for (var n = 0, i = Object.keys(e); n < i.length; n++) {
                var o = i[n];
                t.style[o] = e[o]
            }
            return t
        }, e.updateClasses = o, e.addClasses = function (t, e) {
            void 0 === e && (e = "layx-");
            for (var n = [], i = 2; i < arguments.length; i++) n[i - 2] = arguments[i];
            return null === t ? t : o.apply(void 0, [t, function (t, e, n) {
                ~e || t.push(n)
            }, e].concat(n))
        }, e.removeClasses = function (t, e) {
            void 0 === e && (e = "layx-");
            for (var n = [], i = 2; i < arguments.length; i++) n[i - 2] = arguments[i];
            return null === t ? t : o.apply(void 0, [t, function (t, e) {
                ~e && t.splice(e, 1)
            }, e].concat(n))
        }, e.containClass = function (t, e, n) {
            return void 0 === e && (e = "layx-"), null !== t && !!~t.className.split(/\s+/g).indexOf(e + n)
        }, e.removeElement = function (t) {
            t && t.parentElement && t.parentElement.removeChild(t)
        }
    }, function (t, e, n) {
        "use strict";
        Object.defineProperty(e, "__esModule", {value: !0});
        var r = n(5), s = n(8), o = n(18);
        e.numberCast = function (t, e) {
            return void 0 === t ? e : "number" == typeof t ? t : /^(([1-9][0-9]*(\.\d{1,2})?)|(0\.\d{1,2}))$/.test(t) ? Number(t) : s.assertNumber(t)
        }, e.stringCast = function (t) {
            return r.isStringWithNotEmpty(t) ? t : s.assertString(t)
        }, e.booleanCast = function (t, e) {
            return void 0 === t ? e : ("boolean" != typeof t && s.assertBoolean(t), t)
        }, e.stringOrBooleanStyleCast = function (t, e, n) {
            return void 0 === t ? e : !0 === t ? void 0 === n ? e : n : !1 === t ? null : t
        }, e.typeOrBooleanCast = function (t, e, n, i) {
            return void 0 === t ? e : !0 === t ? void 0 === n ? e : n : !1 === t ? void 0 !== i && i : t
        }, e.undefinedCast = function (t, e) {
            return void 0 === t ? e : t
        }, e.stringOrUndefinedCast = function (t) {
            if (void 0 !== t) return r.isStringWithNotEmpty(t) ? t : s.assertString(t)
        }, e.windowModeCast = function (t, e) {
            return void 0 === t ? e : (r.isWindowMode(t), t)
        }, e.borderCast = function (t, e) {
            if ("string" == typeof t) return [t, null];
            if (!1 === t) return [null, null];
            var n = [null, null], i = {};
            return "number" == typeof (i = void 0 === t || !0 === t ? o.merge({}, e) : o.merge(e, t)).width && "string" == typeof i.color && "string" == typeof i.style && -1 < ["none", "hidden", "dotted", "dashed", "solid", "double", "groove", "ridge", "inset", "outset", "inherit"].indexOf(i.style) && (n[0] = i.width + "px " + i.style + " " + i.color), "number" == typeof i.radius && (n[1] = i.radius + "px"), n
        }, e.offsetCast = function (t, e, n) {
            if (void 0 === t) return [(innerWidth - e) / 2, (innerHeight - n) / 2];
            if (r.isWindowCoord(t)) {
                var i = t;
                return [i[0], i[1]]
            }
            var o = [0, 0];
            switch (t) {
                case"leftTop":
                    break;
                case"leftCenter":
                    o[0] = 0, o[1] = (innerHeight - n) / 2;
                    break;
                case"leftBottom":
                    o[0] = 0, o[1] = innerHeight - n;
                    break;
                case"topCenter":
                    o[0] = (innerWidth - e) / 2, o[1] = 0;
                case"center":
                    o[0] = (innerWidth - e) / 2, o[1] = (innerHeight - n) / 2;
                    break;
                case"bottomCenter":
                    o[0] = (innerWidth - e) / 2, o[1] = innerHeight - n;
                    break;
                case"rightTop":
                    o[0] = innerWidth - e, o[1] = 0;
                    break;
                case"rightCenter":
                    o[0] = innerWidth - e, o[1] = (innerHeight - n) / 2;
                    break;
                case"rightBottom":
                    o[0] = innerWidth - e, o[1] = innerHeight - n;
                    break;
                default:
                    return s.assertNever(t)
            }
            return o
        }, e.windowAnimateCast = function (t, e, n, i) {
            return void 0 === t ? e : !0 === t ? void 0 === n ? "zoom" : n : !1 === t ? void 0 === i ? "none" : i : (r.isWindowAnimate(t), t)
        }, e.jsonOrBooleanCast = function (t, e, n, i) {
            return void 0 === t ? e : !0 === t ? void 0 === n ? e : n : !1 === t ? void 0 !== i && i : !1 === e ? t : o.merge(e, t)
        }, e.contextMenuButtonsCast = function (t) {
            return void 0 !== t && !1 !== t && (r.isContextMenuButtons(t), t)
        }, e.actionButtonsCast = function (t, e) {
            return void 0 === t || !0 === t ? e : !1 !== t && (r.isActionButtons(t), t)
        }, e.contentTypeCast = function (t, e) {
            return void 0 === t ? e : (r.isContentType(t), t)
        }, e.stringOrElementCast = function (t) {
            return r.isStringOrElement(t) ? t : s.assertNever(t)
        }, e.noticeTypeCast = function (t, e) {
            return void 0 === t ? e : (r.isNoticeType(t), t)
        }
    }, function (t, e, n) {
        "use strict";
        var i, o = this && this.__extends || (i = function (t, e) {
            return (i = Object.setPrototypeOf || {__proto__: []} instanceof Array && function (t, e) {
                t.__proto__ = e
            } || function (t, e) {
                for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
            })(t, e)
        }, function (t, e) {
            function n() {
                this.constructor = t
            }

            i(t, e), t.prototype = null === e ? Object.create(e) : (n.prototype = e.prototype, new n)
        });
        Object.defineProperty(e, "__esModule", {value: !0});
        var r, s = n(6), a = (r = s.default, o(d, r), d);

        function d(t, e) {
            var n = r.call(this, t) || this;
            return n.window = e, n
        }

        e.default = a
    }, function (t, e, n) {
        "use strict";
        Object.defineProperty(e, "__esModule", {value: !0});
        var i = n(8);

        function o(t) {
            return "object" == typeof t && void 0 !== t.constructor && t.constructor === Object
        }

        function r(t) {
            return o(t) && void 0 !== t.id && void 0 !== t.label
        }

        function s(t) {
            return o(t) && void 0 !== t.label && void 0 !== t.id
        }

        function a(t) {
            return "object" == typeof t && t instanceof Element
        }

        e.isWindowCoord = function (t) {
            return void 0 !== t.length && 2 <= t.length && t.constructor === Array && "number" == typeof t[0] && "number" == typeof t[1]
        }, e.isWindowMode = function (t) {
            switch (t) {
                case"layer":
                case"embed":
                    return !0;
                default:
                    return i.assertNever(t)
            }
        }, e.isWindowAnimate = function (t) {
            switch (t) {
                case"none":
                case"zoom":
                    return !0;
                default:
                    return i.assertNever(t)
            }
        }, e.isJsonObject = o, e.isContextMenuButton = r, e.isContextMenuButtons = function (t) {
            for (var e = !0, n = 0, i = t; n < i.length; n++) {
                if (!r(i[n])) {
                    e = !1;
                    break
                }
            }
            return e
        }, e.isResizeOptions = function (t) {
            return "boolean" == typeof t || o(t) && (void 0 !== t.left || void 0 !== t.right || void 0 !== t.top || void 0 !== t.bottom || void 0 !== t.leftTop || void 0 !== t.rightTop || void 0 !== t.leftBottom || void 0 !== t.rightBottom)
        }, e.isActionButton = s, e.isActionButtons = function (t) {
            for (var e = !0, n = 0, i = t; n < i.length; n++) {
                if (!s(i[n])) {
                    e = !1;
                    break
                }
            }
            return e
        }, e.isStringWithNotEmpty = function (t) {
            return "string" == typeof t && 0 < t.trim().length
        }, e.isElement = a, e.isContentType = function (t) {
            switch (t) {
                case"html":
                case"local-url":
                case"non-local-url":
                    return !0;
                default:
                    return i.assertNever(t)
            }
        }, e.isStringOrElement = function (t) {
            return "string" == typeof t || a(t)
        }, e.isNoticeType = function (t) {
            switch (t) {
                case"info":
                case"success":
                case"warning":
                case"error":
                    return !0;
                default:
                    return i.assertNever(t)
            }
        }, e.isMoveEvent = function (t) {
            return void 0 !== t.button && void 0 === t.touches
        }
    }, function (t, e, n) {
        "use strict";
        Object.defineProperty(e, "__esModule", {value: !0});
        var r = n(3), s = n(9), i = (o.prototype.setComponent = function (t, e) {
            t = r.stringCast(t), this.components[t] || (this.components[t] = e)
        }, o.prototype.getComponent = function (t, e) {
            if (void 0 === e && (e = this), t = r.stringCast(t), -1 < (t = s.removeValidSymbol(t)).indexOf("/")) {
                var n = t.split("/"), i = e.getComponent(n[0]);
                if (null === i) return null;
                for (var o = 0; o < n.length; o++) {
                    if (!(o + 1 < n.length)) return i;
                    i = i.getComponent(n[o + 1], i)
                }
                return i
            }
            return e.components[t] ? e.components[t] : null
        }, o.prototype.removeComponent = function (t) {
            t = r.stringCast(t), this.components[t] && delete this.components[t]
        }, o);

        function o(t) {
            this.app = t, this.components = {}
        }

        e.default = i
    }, function (t, e, n) {
        "use strict";
        var i, o = this && this.__extends || (i = function (t, e) {
            return (i = Object.setPrototypeOf || {__proto__: []} instanceof Array && function (t, e) {
                t.__proto__ = e
            } || function (t, e) {
                for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
            })(t, e)
        }, function (t, e) {
            function n() {
                this.constructor = t
            }

            i(t, e), t.prototype = null === e ? Object.create(e) : (n.prototype = e.prototype, new n)
        });
        Object.defineProperty(e, "__esModule", {value: !0});
        var r, s = n(6), a = n(2), d = n(3), u = (r = s.default, o(p, r), p.prototype.present = function () {
            var t = a.createFragment(), e = a.createElementNS("svg");
            e.setAttribute("class", this.className);
            var n = a.createElementNS("use");
            return n.setAttributeNS("http://www.w3.org/1999/xlink", "xlink:href", "#" + this.name), e.appendChild(n), t.appendChild(e), t
        }, p);

        function p(t, e) {
            var n = r.call(this, t) || this;
            return n.className = n.app.prefix + "icon", n.name = d.stringCast(e), n
        }

        e.default = u
    }, function (t, e, n) {
        "use strict";
        Object.defineProperty(e, "__esModule", {value: !0}), e.assertId = function () {
            throw new Error("`id` is required.")
        }, e.assertString = function (t) {
            throw new Error("Unexpected string: " + t)
        }, e.assertNumber = function (t) {
            throw new Error("Unexpected number: " + t)
        }, e.assertNever = function (t) {
            throw new Error("Unexpected object: " + t)
        }, e.assertUnique = function (t) {
            throw new Error("The element contains '" + t + "' is exists.")
        }, e.assertBoolean = function (t) {
            throw new Error("Unexpected boolean: " + t)
        }
    }, function (t, e, n) {
        "use strict";
        Object.defineProperty(e, "__esModule", {value: !0}), e.getKebabCase = function (t) {
            return t.replace(/[A-Z]/g, function (t) {
                return "-" + t.toLowerCase()
            })
        }, e.getCamelCase = function (t) {
            return t.replace(/-([a-z])/g, function (t, e) {
                return e.toUpperCase()
            })
        }, e.exchangeValue = function (t, e) {
            var n = t;
            return [t = e, e = n]
        }, e.removeValidSymbol = function (t) {
            var e = t.trim();
            return 0 === e.length ? e : e = t.replace(/[\r\n\t\s]/g, "")
        }, e.mendZero = function (t, e) {
            if (t.length === e) return t;
            if (t.length < e) {
                for (var n = "", i = 0; i < e - t.length; i++) n += "0";
                return n + t
            }
            return t.substr(0, e)
        }
    }, function (t, e, n) {
        "use strict";
        var i, o = this && this.__extends || (i = function (t, e) {
            return (i = Object.setPrototypeOf || {__proto__: []} instanceof Array && function (t, e) {
                t.__proto__ = e
            } || function (t, e) {
                for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
            })(t, e)
        }, function (t, e) {
            function n() {
                this.constructor = t
            }

            i(t, e), t.prototype = null === e ? Object.create(e) : (n.prototype = e.prototype, new n)
        });
        Object.defineProperty(e, "__esModule", {value: !0});
        var s, r = n(4), f = n(25), m = n(2), a = n(3),
            d = (s = r.default, o(w, s), Object.defineProperty(w.prototype, "element", {
                get: function () {
                    return document.getElementById("" + this.elementId)
                }, enumerable: !0, configurable: !0
            }), w.prototype.present = function () {
                var t = m.createFragment();
                if (!1 !== this.contextMenuButtons && 0 < this.contextMenuButtons.length) {
                    var e = m.createElement("div");
                    e.setAttribute("data-window-id", this.window.id), this.isTopMenu && (e.id = this.elementId), m.addClasses(e, this.app.prefix, "context-menu-bar"), e.addEventListener("contextmenu", function (t) {
                        return t.preventDefault(), t.returnValue = !1
                    }, !0), this.createContextMenuButtons(e), t.appendChild(e)
                }
                return t
            }, w.prototype.hide = function () {
                this.isTopMenu && m.removeClasses(this.element, this.app.prefix, "context-menu-bar-active")
            }, w.prototype.updateOffset = function (t, e, n, i) {
                if (this.isTopMenu && this.element && !1 !== this.contextMenuButtons && 0 !== this.contextMenuButtons.length) {
                    var o = getComputedStyle(this.element), r = Number(o.width.replace("px", "")),
                        s = this.contextMenuButtons.length * f.default.height, a = t.pageX, d = t.pageY, u = a, p = d;
                    void 0 !== n ? u = n : r + a > innerWidth && (u = a - r), void 0 !== i ? p = i : s + d > innerHeight && (p = d - s), m.addClasses(this.element, this.app.prefix, "context-menu-bar-active"), m.addStyles(this.element, {
                        zIndex: "" + e,
                        top: p + "px",
                        left: u + "px"
                    })
                }
            }, w.prototype.updateChildrenOffset = function (t, e, n) {
                if (!this.isTopMenu && e && e.parentElement && e.parentElement.parentElement) {
                    var i = e.parentElement.parentElement, o = getComputedStyle(i),
                        r = Number(o.width.replace("px", "")), s = Number(o.left.replace("px", "")),
                        a = Number(o.top.replace("px", "")), d = getComputedStyle(e),
                        u = Number(d.width.replace("px", "")), p = e.childElementCount * f.default.height,
                        l = f.default.height * n, c = r + s - w.offset, h = a + l - w.offset;
                    r + s + u > innerWidth && (c = s - u + w.offset), a + l + p > innerHeight && (h = innerHeight - p - w.offset), m.addClasses(e, this.app.prefix, "context-menu-bar-active"), m.addStyles(e, {
                        top: h + "px",
                        left: c + "px"
                    })
                }
            }, w.prototype.hideChildren = function (t) {
                this.isTopMenu || m.removeClasses(t, this.app.prefix, "context-menu-bar-active")
            }, w.prototype.createContextMenuButtons = function (t) {
                if (!1 !== this.contextMenuButtons) {
                    for (var e = Array(), n = 0, i = 0, o = this.contextMenuButtons; i < o.length; i++) {
                        var r = o[i], s = new f.default(this.app, this.window, r, n), a = s.present();
                        t.appendChild(a), e.push(s), n++
                    }
                    this.setComponent("context-menu-buttons", e)
                }
            }, w.offset = 4, w);

        function w(t, e, n, i, o) {
            void 0 === o && (o = !0);
            var r = s.call(this, t, e) || this;
            return r.isTopMenu = o, r.contextMenuButtons = !1, r._element = null, r.contextMenuButtons = a.contextMenuButtonsCast(i), r.uniqueId = a.stringCast(n), r.elementId = r.window.elementId + "-" + r.uniqueId + "-context-menu-bar", r
        }

        e.default = d
    }, function (t, e, n) {
        "use strict";
        Object.defineProperty(e, "__esModule", {value: !0});
        var i = n(12), s = n(5), o = (r.prototype.draggingFirst = function (t, e, n, i, o) {
        }, r);

        function r(t) {
            var r = this;
            this.isDragging = !1, this.isFirstDragging = !0, this.startX = 0, this.startY = 0, this.touchStartTime = null, this.mousedown = function (t) {
                r.mouseStar(t), r.touchStartTime = new Date, (s.isMoveEvent(t) && 0 === t.button || !s.isMoveEvent(t) && 0 < t.touches.length) && (r.startX = s.isMoveEvent(t) ? t.pageX : t.touches[0].pageX, r.startY = s.isMoveEvent(t) ? t.pageY : t.touches[0].pageY, !1 !== r.dragStart(t, r.startX, r.startY) && (i.addTouchMoveEvent(document, r.mousemove), i.addTouchEndEvent(document, r.mouseup)))
            }, this.mousemove = function (t) {
                r.mouseMove(t);
                var e = s.isMoveEvent(t) ? t.pageX : t.touches[0].pageX,
                    n = s.isMoveEvent(t) ? t.pageY : t.touches[0].pageY, i = e - r.startX, o = n - r.startY;
                (s.isMoveEvent(t) && (0 != i || 0 != o) || !s.isMoveEvent(t) && 30 < (new Date).getTime() - r.touchStartTime.getTime()) && ((r.isDragging = !0) === r.isFirstDragging && (r.isFirstDragging = !1, r.draggingFirst(t, e, n, i, o)), r.dragging(t, e, n, i, o))
            }, this.mouseup = function (t) {
                r.mouseEnd(t), i.removeTouchMoveEvent(document, r.mousemove), i.removeTouchEndEvent(document, r.mouseup), r.dragEnd(t), r.isFirstDragging = !0, r.isDragging = !1
            }, i.addTouchStartEvent(t, this.mousedown)
        }

        e.default = o
    }, function (t, e, n) {
        "use strict";
        Object.defineProperty(e, "__esModule", {value: !0}), e.addTouchStartEvent = function (t, e, n) {
            void 0 === n && (n = !1), Document, t.addEventListener("mousedown", e, n), t.addEventListener("touchstart", e, n)
        }, e.addTouchMoveEvent = function (t, e, n) {
            void 0 === n && (n = !1), Document, t.addEventListener("mousemove", e, n), t.addEventListener("touchmove", e, n)
        }, e.addTouchEndEvent = function (t, e, n) {
            void 0 === n && (n = !1), Document, t.addEventListener("mouseup", e, n), t.addEventListener("touchend", e, n)
        }, e.removeTouchStartEvent = function (t, e) {
            Document, t.removeEventListener("mousedown", e), t.removeEventListener("touchstart", e)
        }, e.removeTouchMoveEvent = function (t, e) {
            Document, t.removeEventListener("mousemove", e), t.removeEventListener("touchmove", e)
        }, e.removeTouchEndEvent = function (t, e) {
            Document, t.removeEventListener("mouseup", e), t.removeEventListener("touchend", e)
        }
    }, function (t, e, n) {
        "use strict";
        var i, o = this && this.__extends || (i = function (t, e) {
            return (i = Object.setPrototypeOf || {__proto__: []} instanceof Array && function (t, e) {
                t.__proto__ = e
            } || function (t, e) {
                for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
            })(t, e)
        }, function (t, e) {
            function n() {
                this.constructor = t
            }

            i(t, e), t.prototype = null === e ? Object.create(e) : (n.prototype = e.prototype, new n)
        });
        Object.defineProperty(e, "__esModule", {value: !0});
        var r, s = n(4), a = n(7), d = n(2), u = n(3),
            p = (r = s.default, o(l, r), Object.defineProperty(l.prototype, "element", {
                get: function () {
                    return document.getElementById("" + this.elementId)
                }, enumerable: !0, configurable: !0
            }), l.prototype.present = function () {
                var e = this, t = d.createFragment(), n = d.createElement("div");
                n.setAttribute("data-window-id", this.window.id), n.id = this.elementId, n.setAttribute("title", this.label), d.addClasses(n, this.app.prefix, "action-button", "destroy" === this.id ? "action-button-destroy" : "", "flexbox", "flex-center"), d.addStyles(n, {width: l.width + "px"}), n.addEventListener("mousedown", function (t) {
                    0 === t.button && "function" == typeof e.handler && e.handler(t, e.window)
                }, !0), n.addEventListener("dblclick", function (t) {
                    t.stopPropagation()
                });
                var i = new a.default(this.app, this.id), o = i.present();
                return n.appendChild(o), this.setComponent("action-button", i), t.appendChild(n), t
            }, l.width = 45, l.destroy = {
                id: "destroy", label: "关闭", handler: function (t, e) {
                    e.destroy()
                }
            }, l.max = {
                id: "max", label: "最大化", handler: function (t, e) {
                    e.max()
                }
            }, l.restore = {
                id: "restore", label: "恢复", handler: function (t, e) {
                    e.normal()
                }
            }, l.min = {
                id: "min", label: "最小化", handler: function (t, e) {
                    e.min()
                }
            }, l.about = {
                id: "about", label: "关于", handler: function (t, e) {
                }
            }, l.refresh = {
                id: "refresh", label: "刷新内容", handler: function (t, e) {
                    var n = e.getComponent("content-container");
                    n && n.refreshContent()
                }
            }, l.more = {
                id: "more", label: "更多操作", handler: function (t, e) {
                }
            }, l);

        function l(t, e, n) {
            var i = r.call(this, t, e) || this;
            return i._element = null, i.id = u.stringCast(n.id), i.label = u.stringCast(n.label), i.handler = n.handler, i.elementId = i.window.elementId + "-action-button-" + i.id, i
        }

        e.default = p
    }, function (t, e) {
        var n;
        n = function () {
            return this
        }();
        try {
            n = n || new Function("return this")()
        } catch (t) {
            "object" == typeof window && (n = window)
        }
        t.exports = n
    }, function (t, e, n) {
        "use strict";
        Object.defineProperty(e, "__esModule", {value: !0});
        var i, o = n(16);

        function r(t) {
        }

        n(39), n(55), e.default = (i = new o.default(r), r.v = i.version, r.open = function (t) {
            i.open(t)
        }, r.windows = i.windows, r.window = i.window, r.lastWindow = i.lastWindow, r.getWindow = function (t) {
            return i.getWindow(t)
        }, r.destroy = function (t) {
            i.destroy(t)
        }, r.notice = function (t) {
            i.notice(t)
        }, r.notices = i.notices, r)
    }, function (t, e, n) {
        "use strict";
        Object.defineProperty(e, "__esModule", {value: !0});
        var o = n(17), r = n(36), s = n(8), a = n(5), i = n(2), d = n(38),
            u = (Object.defineProperty(p.prototype, "window", {
                get: function () {
                    return this._window
                }, set: function (t) {
                    this.layx.window = this._window = t
                }, enumerable: !0, configurable: !0
            }), Object.defineProperty(p.prototype, "lastWindow", {
                get: function () {
                    return this._lastWindow
                }, set: function (t) {
                    this.layx.lastWindow = this._lastWindow = t
                }, enumerable: !0, configurable: !0
            }), Object.defineProperty(p.prototype, "salver", {
                get: function () {
                    return this._salver
                }, set: function (t) {
                    this.layx.salver = this._salver = t
                }, enumerable: !0, configurable: !0
            }), Object.defineProperty(p.prototype, "zIndex", {
                get: function () {
                    return this._zIndex = this._zIndex + 3
                }, enumerable: !0, configurable: !0
            }), Object.defineProperty(p.prototype, "aboveZIndex", {
                get: function () {
                    return this._aboveZIndex = this._aboveZIndex + 3
                }, enumerable: !0, configurable: !0
            }), Object.defineProperty(p.prototype, "salverZIndex", {
                get: function () {
                    return this._salverZIndex
                }, enumerable: !0, configurable: !0
            }), Object.defineProperty(p.prototype, "noticeZIndex", {
                get: function () {
                    return this._noticeZIndex = this._noticeZIndex + 1
                }, enumerable: !0, configurable: !0
            }), Object.defineProperty(p.prototype, "windows", {
                get: function () {
                    return this._windows
                }, enumerable: !0, configurable: !0
            }), Object.defineProperty(p.prototype, "notices", {
                get: function () {
                    return this._notices
                }, enumerable: !0, configurable: !0
            }), Object.defineProperty(p.prototype, "drayLayer", {
                get: function () {
                    return this._drayLayer
                }, set: function (t) {
                    this._drayLayer = t
                }, enumerable: !0, configurable: !0
            }), p.prototype.open = function (t) {
                var e = this, n = this.getWindow(t.id);
                if (n) n.updateZIndex(), n.flicker(); else {
                    var i = (n = new o.default(this, t)).present();
                    document.body.appendChild(i), this.lastWindow = this.window, this.window = n, this.windows.push(n), this.salver && (0 === this.salver.items.length && (this.salver.show(), setTimeout(function () {
                        e.salver.show(!1)
                    }, 300)), this.salver.addOrUpdateItem())
                }
                n.zoomActionButtons(n.width)
            }, p.prototype.destroy = function (t) {
                a.isStringWithNotEmpty(t) || s.assertId();
                var e = this.getWindow(t);
                e && e.destroy()
            }, p.prototype.getWindow = function (t) {
                a.isStringWithNotEmpty(t) || s.assertId();
                for (var e = 0, n = this.windows; e < n.length; e++) {
                    var i = n[e];
                    if (i.id === t) {
                        if (i.element) return i;
                        var o = this.windows.indexOf(i);
                        return this.windows.splice(o, 1), null
                    }
                }
                return null
            }, p.prototype.notice = function (t) {
                var e = new r.default(this, t), n = e.present();
                document.body.append(n), this.notices.push(e);
                var i = this.notices.indexOf(e);
                e.updateOffset(i, !0)
            }, p.prototype.init = function () {
                this.bindEvent()
            }, p.prototype.createDragLayer = function () {
                if (!this.drayLayer) {
                    var t = new d.default(this), e = t.present();
                    document.body.appendChild(e), this.drayLayer = t
                }
            }, p.prototype.bindEvent = function () {
                var t = this;
                document.addEventListener("DOMContentLoaded", function () {
                    document.body.id || (document.body.id = t.prefix + "body"), t.createDragLayer()
                }), document.addEventListener("mousedown", this.mousedown, !0), document.addEventListener("mousemove", this.mousemove, !0)
            }, p);

        function p(t) {
            var o = this;
            this.layx = t, this.version = "3.0.0", this.prefix = "layx-", this._window = null, this._lastWindow = null, this._salver = null, this._zIndex = 1e7, this._aboveZIndex = 2e7, this._salverZIndex = 3e7, this._noticeZIndex = 4e7, this._windows = [], this._notices = [], this._drayLayer = null, this.mousedown = function (t) {
                if (o.window) {
                    var e = o.window.getComponent("context-menu-bar");
                    e && e.hide(), o.window.hideMoreActionContextMenu(), (i = o.window.getComponent("top-menu-bar")) && i.hide(t);
                    var n = o.window.getComponent("\n            tool-bar\n            /title-bar\n            /window-icon-context-menu-bar");
                    n && n.hide()
                }
                var i;
                o.lastWindow && (i = o.lastWindow.getComponent("top-menu-bar")) && i.hide(t)
            }, this.mousemove = function (t) {
                if (o.salver && o.salver.element) if (t.pageY >= innerHeight - 50) {
                    if (i.containClass(o.salver.element, o.prefix, "salver-bar-keep")) return;
                    o.salver.show()
                } else {
                    if (!i.containClass(o.salver.element, o.prefix, "salver-bar-keep")) return;
                    o.salver.show(!1)
                }
            }, this.init()
        }

        e.default = u
    }, function (module, exports, __webpack_require__) {
        "use strict";
        var __extends = this && this.__extends || (bp = function (t, e) {
            return (bp = Object.setPrototypeOf || {__proto__: []} instanceof Array && function (t, e) {
                t.__proto__ = e
            } || function (t, e) {
                for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
            })(t, e)
        }, function (t, e) {
            function n() {
                this.constructor = t
            }

            bp(t, e), t.prototype = null === e ? Object.create(e) : (n.prototype = e.prototype, new n)
        }), bp;
        Object.defineProperty(exports, "__esModule", {value: !0});
        var UIComponent_1 = __webpack_require__(6), UIParclose_1 = __webpack_require__(19),
            UIResizeBar_1 = __webpack_require__(20), UIToolBar_1 = __webpack_require__(22),
            UIActionButton_1 = __webpack_require__(13), UIContextMenuBar_1 = __webpack_require__(10),
            UITopMenuBar_1 = __webpack_require__(28), UISalverBar_1 = __webpack_require__(30),
            UIContent_1 = __webpack_require__(32), UIStatuBar_1 = __webpack_require__(35),
            ElementHelper = __webpack_require__(2), CastHelper = __webpack_require__(3),
            TypeHelper = __webpack_require__(5), ExceptionHelper = __webpack_require__(8),
            StringHelper = __webpack_require__(9), UIWindow = function (_super) {
                function UIWindow(t, e) {
                    var n, i, o = _super.call(this, t) || this;
                    return o.zIndex = o.app.zIndex, o.enableAnimated = !1, o.status = "normal", o.lastStatus = "none", o.flickering = !1, o.width = 800, o.height = 600, o.maxWidth = innerWidth, o.maxHeight = innerHeight, o.minWidth = 200, o.minHeight = 200, o.background = "#ffffff", o.shadow = "rgba(0, 0, 0, 0.3) 1px 1px 24px", o.parclose = !1, o.mode = "layer", o.border = null, o.borderRadius = null, o.animate = "zoom", o.resizeBar = {}, o.toolBar = {}, o.contextMenu = !1, o.topMenu = !1, o.content = {}, o.statuBar = !1, o.storeStatus = !0, o._element = null, o._flickerShadow = null, o._lastStoreStatus = null, TypeHelper.isStringWithNotEmpty(e.id) || ExceptionHelper.assertId(), o.id = e.id, o.elementId = o.app.prefix + o.id, o.mode = CastHelper.windowModeCast(e.mode, o.mode), o.width = CastHelper.numberCast(e.width, o.width), o.height = CastHelper.numberCast(e.height, o.height), o.maxWidth = Math.min(CastHelper.numberCast(e.maxWidth, o.maxWidth), o.maxWidth), o.maxHeight = Math.min(CastHelper.numberCast(e.maxHeight, o.maxHeight), o.maxHeight), o.minWidth = Math.max(CastHelper.numberCast(e.minWidth, o.minWidth), o.minWidth), o.minHeight = Math.max(CastHelper.numberCast(e.minHeight, o.minHeight), o.minHeight), o.width = Math.max(o.minWidth, o.width), o.width = Math.min(o.maxWidth, o.width), o.height = Math.max(o.minHeight, o.height), o.height = Math.min(o.maxHeight, o.height), n = CastHelper.offsetCast(e.offset, o.width, o.height), o.left = n[0], o.top = n[1], o.background = CastHelper.stringOrBooleanStyleCast(e.background, o.background), o.shadow = CastHelper.stringOrBooleanStyleCast(e.shadow, o.shadow), o.parclose = CastHelper.typeOrBooleanCast(e.parclose, o.parclose, 0), i = CastHelper.borderCast(e.border, {
                        width: 1,
                        style: "solid",
                        color: "#3baced",
                        radius: 4
                    }), o.border = i[0], o.borderRadius = i[1], o.animate = CastHelper.windowAnimateCast(e.animate, o.animate), o.enableAnimated = "none" !== o.animate, o.resizeBar = CastHelper.jsonOrBooleanCast(e.resizeBar, o.resizeBar), o.toolBar = CastHelper.jsonOrBooleanCast(e.toolBar, o.toolBar), o.contextMenu = CastHelper.contextMenuButtonsCast(e.contextMenu), o.topMenu = CastHelper.contextMenuButtonsCast(e.topMenu), o.content = CastHelper.jsonOrBooleanCast(e.content, o.content), o.statuBar = CastHelper.jsonOrBooleanCast(e.statuBar, o.statuBar), o.storeStatus = CastHelper.booleanCast(e.storeStatus, o.storeStatus), o
                }

                return __extends(UIWindow, _super), Object.defineProperty(UIWindow.prototype, "element", {
                    get: function () {
                        return document.getElementById(this.elementId)
                    }, enumerable: !0, configurable: !0
                }), Object.defineProperty(UIWindow.prototype, "flickerShadow", {
                    get: function () {
                        return this.getFlickerShadow()
                    }, enumerable: !0, configurable: !0
                }), Object.defineProperty(UIWindow.prototype, "lastStoreStatus", {
                    get: function () {
                        var storeStatus = localStorage.getItem(this.app.prefix + this.id);
                        return storeStatus ? eval("(" + storeStatus + ")") : null
                    }, set: function (t) {
                        localStorage.setItem(this.app.prefix + this.id, JSON.stringify(t))
                    }, enumerable: !0, configurable: !0
                }), UIWindow.prototype.present = function () {
                    var e = this, t = ElementHelper.createFragment(), n = ElementHelper.createElement("div");
                    if (n.id = this.elementId, n.setAttribute("data-window-id", this.id), ElementHelper.addClasses(n, this.app.prefix, "window", "window-" + this.mode, "flexbox", "flex-column", this.enableAnimated ? "animate" : "", this.enableAnimated ? "animate-" + this.animate + "-show" : ""), this.readStoreStatus(), ElementHelper.addStyles(n, {
                        zIndex: "layer" === this.mode ? "" + this.zIndex : null,
                        maxWidth: this.maxWidth + "px",
                        maxHeight: this.maxHeight + "px",
                        minWidth: this.minWidth + "px",
                        minHeight: this.minHeight + "px",
                        width: this.width + "px",
                        height: this.height + "px",
                        top: "layer" === this.mode ? this.top + "px" : null,
                        left: "layer" === this.mode ? this.left + "px" : null,
                        background: this.background,
                        border: this.border,
                        borderRadius: this.borderRadius,
                        webkitBorderRadius: this.borderRadius,
                        boxShadow: this.shadow,
                        webkitBoxShadow: this.shadow
                    }), n.addEventListener("mousedown", function (t) {
                        e.updateZIndex()
                    }, !0), !1 !== this.toolBar) {
                        var i = new UIToolBar_1.default(this.app, this, this.toolBar), o = i.present();
                        n.appendChild(o), this.setComponent("tool-bar", i)
                    }
                    if (!1 !== this.topMenu) {
                        var r = new UITopMenuBar_1.default(this.app, this, this.topMenu), s = r.present();
                        n.appendChild(s), this.setComponent("top-menu-bar", r)
                    }
                    if (!1 !== this.content) {
                        var a = new UIContent_1.default(this.app, this, this.content), d = a.present();
                        n.appendChild(d), this.setComponent("content-container", a)
                    }
                    if (!1 !== this.resizeBar) {
                        var u = new UIResizeBar_1.default(this.app, this, this.resizeBar), p = u.present();
                        n.appendChild(p), this.setComponent("resize-bar", u)
                    }
                    if (!1 !== this.statuBar) {
                        var l = new UIStatuBar_1.default(this.app, this, this.statuBar), c = l.present();
                        n.appendChild(c), this.setComponent("statu-bar", l)
                    }
                    if (!1 !== this.parclose) {
                        var h = new UIParclose_1.default(this.app, this, {opacity: this.parclose}), f = h.present();
                        t.appendChild(f), this.setComponent("parclose", h), this.app.salver && this.app.salver.parsecloseCount++
                    }
                    if (!1 !== this.contextMenu) {
                        var m = new UIContextMenuBar_1.default(this.app, this, "window", this.contextMenu), w = m.present();
                        t.appendChild(w), this.setComponent("context-menu-bar", m)
                    }
                    if (this.bindEvent(n), !this.app.salver) {
                        var v = new UISalverBar_1.default(this.app), y = v.present();
                        t.appendChild(y), this.app.salver = v
                    }
                    return t.appendChild(n), t
                }, UIWindow.prototype.readStoreStatus = function () {
                    var t = this.lastStoreStatus;
                    this.storeStatus && (t ? (this.width = t.width, this.height = t.height, this.top = t.top, this.left = t.left) : this.lastStoreStatus = {
                        top: this.top,
                        left: this.left,
                        width: this.width,
                        height: this.height
                    })
                }, UIWindow.prototype.handlerContentByAnimate = function (t) {
                    void 0 === t && (t = !0);
                    var e = this.getComponent("content-container");
                    e && (t ? ElementHelper.addClasses(e.element, this.app.prefix, "content-container-fade-out") : ElementHelper.removeClasses(e.element, this.app.prefix, "content-container-fade-out"))
                }, UIWindow.prototype.bindEvent = function (t) {
                    var n = this;
                    !1 !== this.contextMenu && t.addEventListener("contextmenu", function (t) {
                        t.preventDefault(), t.returnValue = !1;
                        var e = n.getComponent("context-menu-bar");
                        return e && e.updateOffset(t, n.zIndex + 1), !1
                    }), t.addEventListener("animationstart", function (t) {
                        n.handlerContentByAnimate()
                    }), t.addEventListener("animationend", function (t) {
                        var e = n.element;
                        ElementHelper.removeClasses(e, n.app.prefix, "animate-" + n.animate + "-show", "animate-" + n.animate + "-drag-to-normal"), ElementHelper.containClass(e, n.app.prefix, "animate-" + n.animate + "-destroy") && n.remove(), ElementHelper.containClass(e, n.app.prefix, "animate-" + n.animate + "-to-min") && n.minimize(), n.handlerContentByAnimate(!1)
                    }), t.addEventListener("transitionend", function (t) {
                        ElementHelper.removeClasses(n.element, n.app.prefix, "animate-" + n.animate + "-to-max", "animate-" + n.animate + "-to-normal"), n.handlerContentByAnimate(!1)
                    })
                }, UIWindow.prototype.destroy = function () {
                    this.enableAnimated ? ElementHelper.addClasses(this.element, this.app.prefix, "animate-" + this.animate + "-destroy") : this.remove()
                }, UIWindow.prototype.remove = function () {
                    "max" === this.status && ElementHelper.removeClasses(document.body, "z" + this.app.prefix, "body-noscroll");
                    var t = this.getComponent("parclose");
                    t && (ElementHelper.removeElement(t.element), this.app.salver && this.app.salver.parsecloseCount--), this.app.salver && this.app.salver.removeItem();
                    var e = this.app.windows.indexOf(this);
                    this.app.windows.splice(e, 1), this.app.window = null, ElementHelper.removeElement(this.element)
                }, UIWindow.prototype.normal = function (t) {
                    void 0 === t && (t = !1);
                    var e = this.element;
                    if (e && e.parentElement && "normal" !== this.status) {
                        this.lastStatus = this.status, this.status = "normal", this.handlerContentByAnimate(), ElementHelper.removeClasses(document.body, "z" + this.app.prefix, "body-noscroll"), ElementHelper.addClasses(e, this.app.prefix, this.enableAnimated ? !1 === t ? "animate-" + this.animate + "-to-normal" : "animate-" + this.animate + "-drag-to-normal" : ""), ElementHelper.addStyles(e, {
                            top: this.top + "px",
                            left: this.left + "px",
                            width: this.width + "px",
                            height: this.height + "px",
                            borderRadius: this.borderRadius
                        });
                        var n = this.getComponent("resize-bar");
                        n && ElementHelper.removeClasses(n.element, this.app.prefix, "resize-bar-disabled");
                        var i = this.getComponent("\n        tool-bar\n        /action-bar\n        /action-buttons");
                        if (i && 0 !== i.length) {
                            for (var o = 0, r = i; o < r.length; o++) {
                                var s = r[o];
                                if ("max" === s.id) {
                                    var a = new UIActionButton_1.default(this.app, this, UIActionButton_1.default.restore).element;
                                    if (!a || !a.parentElement) return;
                                    var d = new UIActionButton_1.default(this.app, this, s).present();
                                    a.parentElement.replaceChild(d, a);
                                    break
                                }
                            }
                            this.zoomActionButtons(this.width)
                        }
                    }
                }, UIWindow.prototype.max = function () {
                    var t = this.element;
                    if (t && t.parentElement && "max" !== this.status) {
                        this.handlerContentByAnimate(), this.lastStatus = this.status, this.status = "max", ElementHelper.addClasses(document.body, "z" + this.app.prefix, "body-noscroll"), ElementHelper.addClasses(t, this.app.prefix, this.enableAnimated ? "animate-" + this.animate + "-to-max" : ""), ElementHelper.addStyles(t, {
                            top: "0",
                            left: "0",
                            width: innerWidth + "px",
                            height: innerHeight + "px",
                            borderRadius: "0"
                        });
                        var e = this.getComponent("resize-bar");
                        e && ElementHelper.addClasses(e.element, this.app.prefix, "resize-bar-disabled");
                        var n = this.getComponent("\n        tool-bar\n        /action-bar\n        /action-buttons");
                        if (n && 0 !== n.length) {
                            for (var i = 0, o = n; i < o.length; i++) {
                                var r = o[i];
                                if ("max" === r.id) {
                                    var s = r.element;
                                    if (!s || !s.parentElement) return;
                                    var a = new UIActionButton_1.default(this.app, this, UIActionButton_1.default.restore).present();
                                    s.parentElement.replaceChild(a, s);
                                    break
                                }
                            }
                            this.zoomActionButtons(innerWidth)
                        }
                    }
                }, UIWindow.prototype.min = function () {
                    this.element && "min" !== this.status && (!1 !== this.parclose ? this.flicker() : this.enableAnimated ? ElementHelper.addClasses(this.element, this.app.prefix, "animate-" + this.animate + "-to-min") : this.minimize())
                }, UIWindow.prototype.minimize = function () {
                    var t = this.element;
                    ElementHelper.addClasses(t, this.app.prefix, "window-min"), this.enableAnimated && ElementHelper.removeClasses(t, this.app.prefix, "animate-" + this.animate + "-to-min"), this.app.salver && this.app.salver.addOrUpdateItem(), this.lastStatus = this.status, this.status = "min"
                }, UIWindow.prototype.flicker = function () {
                    var t = this;
                    if (this.element && "layer" === this.mode && !1 === this.flickering) {
                        var e = 0;
                        this.flickering = !0;
                        for (var n = 0; n < 12; n++) n % 2 == 0 ? setTimeout(function () {
                            ElementHelper.addStyles(t.element, {
                                boxShadow: t.flickerShadow,
                                webkitBoxShadow: t.flickerShadow
                            }), e++
                        }, 60 * n) : setTimeout(function () {
                            ElementHelper.addStyles(t.element, {boxShadow: t.shadow, webkitBoxShadow: t.shadow}), e++
                        }, 60 * n);
                        var i = setInterval(function () {
                            12 <= e && (clearInterval(i), t.flickering = !1)
                        }, 60)
                    }
                }, UIWindow.prototype.showThis = function (t) {
                    var e;
                    "min" === this.status && (this.handlerContentByAnimate(!0), t = t || this.element, ElementHelper.removeClasses(t, this.app.prefix, "window-min"), ElementHelper.addClasses(t, this.app.prefix, this.enableAnimated ? "animate-" + this.animate + "-show" : ""), this.enableAnimated && ElementHelper.addClasses(t, this.app.prefix, "animate-" + this.animate + "-show"), e = StringHelper.exchangeValue(this.status, this.lastStatus), this.status = e[0], this.lastStatus = e[1])
                }, UIWindow.prototype.updateZIndex = function () {
                    if (this !== this.app.window) {
                        var t = this.element;
                        if ("layer" === this.mode) {
                            this.showThis(t), this.zIndex = this.app.zIndex, ElementHelper.addStyles(t, {zIndex: "" + this.zIndex});
                            var e = this.getComponent("parclose");
                            e && e.updateZIndex(this.zIndex - 1), this.app.lastWindow = this.app.window, (this.app.window = this).app.salver && this.app.salver.addOrUpdateItem()
                        }
                    } else this.showThis()
                }, UIWindow.prototype.hideMoreActionContextMenu = function () {
                    var t = this.getComponent("more-action-context-menu-bar");
                    t && ElementHelper.removeClasses(t.element, this.app.prefix, "context-menu-bar-active")
                }, UIWindow.prototype.removeMoreActionContextMenu = function () {
                    var t = new UIActionButton_1.default(this.app, this, UIActionButton_1.default.more).element;
                    ElementHelper.removeElement(t)
                }, UIWindow.prototype.zoomActionButtons = function (t) {
                    var e = this.getComponent("\n        tool-bar\n        /action-bar");
                    e && e.zoomActionButtons(t)
                }, UIWindow.prototype.getFlickerShadow = function () {
                    if (!this.shadow) return this.shadow;
                    var t = this.shadow.split(" ");
                    return t[t.length - 1] = Number(t[t.length - 1].replace("px", "")) / 2 + "px", t.join(" ")
                }, UIWindow
            }(UIComponent_1.default);
        exports.default = UIWindow
    }, function (t, e, n) {
        "use strict";
        Object.defineProperty(e, "__esModule", {value: !0});
        var a = n(5);

        function d(t) {
            for (var e = {}, n = 0, i = Object.keys(t); n < i.length; n++) {
                var o = i[n];
                e[o] = a.isJsonObject(t[o]) ? d(t[o]) : t[o]
            }
            return e
        }

        e.clone = d, e.merge = function t(e, n) {
            for (var i = d(e), o = 0, r = Object.keys(n); o < r.length; o++) {
                var s = r[o];
                void 0 !== i[s] && a.isJsonObject(n[s]) ? i[s] = t(i[s], n[s]) : i[s] = n[s]
            }
            return i
        }
    }, function (t, e, n) {
        "use strict";
        var i, o = this && this.__extends || (i = function (t, e) {
            return (i = Object.setPrototypeOf || {__proto__: []} instanceof Array && function (t, e) {
                t.__proto__ = e
            } || function (t, e) {
                for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
            })(t, e)
        }, function (t, e) {
            function n() {
                this.constructor = t
            }

            i(t, e), t.prototype = null === e ? Object.create(e) : (n.prototype = e.prototype, new n)
        });
        Object.defineProperty(e, "__esModule", {value: !0});
        var r, s = n(4), a = n(2), d = n(3),
            u = (r = s.default, o(p, r), Object.defineProperty(p.prototype, "element", {
                get: function () {
                    return document.getElementById("" + this.elementId)
                }, enumerable: !0, configurable: !0
            }), p.prototype.present = function () {
                var t = a.createFragment(), e = a.createElement("div");
                return e.setAttribute("data-window-id", this.window.id), e.id = this.elementId, a.addClasses(e, this.app.prefix, "parclose"), a.addStyles(e, {
                    backgroundColor: "rgba(0,0,0," + this.opacity + ")",
                    zIndex: "" + (this.window.zIndex - 2)
                }), this.bindEvent(e), t.appendChild(e), t
            }, p.prototype.updateZIndex = function (t) {
                a.addStyles(this.element, {zIndex: "" + t})
            }, p.prototype.bindEvent = function (t) {
                var e = this;
                t.addEventListener("mousedown", function (t) {
                    e.window.flicker()
                }, !0), t.addEventListener("contextmenu", function (t) {
                    return t.preventDefault(), t.returnValue = !1
                })
            }, p);

        function p(t, e, n) {
            var i = r.call(this, t, e) || this;
            return i.elementId = i.window.elementId + "-parclose", i.opacity = 0, i._element = null, i.opacity = d.numberCast(n.opacity, i.opacity), i
        }

        e.default = u
    }, function (t, e, n) {
        "use strict";
        var i, o = this && this.__extends || (i = function (t, e) {
            return (i = Object.setPrototypeOf || {__proto__: []} instanceof Array && function (t, e) {
                t.__proto__ = e
            } || function (t, e) {
                for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
            })(t, e)
        }, function (t, e) {
            function n() {
                this.constructor = t
            }

            i(t, e), t.prototype = null === e ? Object.create(e) : (n.prototype = e.prototype, new n)
        });
        Object.defineProperty(e, "__esModule", {value: !0});
        var r, s = n(4), a = n(21), d = n(9), u = n(2), p = n(3),
            l = (r = s.default, o(c, r), Object.defineProperty(c.prototype, "element", {
                get: function () {
                    return document.getElementById("" + this.elementId)
                }, enumerable: !0, configurable: !0
            }), c.prototype.present = function () {
                var t = u.createFragment();
                if (this.leastOneTrue()) {
                    var e = u.createElement("div");
                    e.setAttribute("data-window-id", this.window.id), e.id = this.elementId, u.addClasses(e, this.app.prefix, "resize-bar"), this.bindEvent(e);
                    for (var n = 0, i = this.directions; n < i.length; n++) {
                        var o = i[n];
                        !0 === this[d.getCamelCase(o)] && e.appendChild(this.presentItem(o))
                    }
                    t.appendChild(e)
                }
                return t
            }, c.prototype.presentItem = function (t) {
                var e = document.createElement("div");
                return e.setAttribute("data-window-id", this.window.id), u.addClasses(e, this.app.prefix, "resize-item-" + t), new a.default(this.app, this.window, e, t), e
            }, c.prototype.bindEvent = function (t) {
                t.addEventListener("contextmenu", function (t) {
                    return t.preventDefault(), t.stopPropagation(), t.returnValue = !1
                })
            }, c.prototype.leastOneTrue = function () {
                for (var t = !1, e = 0, n = this.directions; e < n.length; e++) {
                    var i = n[e];
                    if (!0 === this[d.getCamelCase(i)]) {
                        t = !0;
                        break
                    }
                }
                return t
            }, c);

        function c(t, e, n) {
            var i = r.call(this, t, e) || this;
            return i.elementId = i.window.elementId + "-resize-bar", i.left = !0, i.right = !0, i.top = !0, i.bottom = !0, i.leftTop = !0, i.rightTop = !0, i.leftBottom = !0, i.rightBottom = !0, i._element = null, i.directions = ["left", "right", "top", "bottom", "left-top", "right-top", "left-bottom", "right-bottom"], i.left = p.booleanCast(n.left, i.left), i.right = p.booleanCast(n.right, i.right), i.top = p.booleanCast(n.top, i.top), i.bottom = p.booleanCast(n.bottom, i.bottom), i.leftTop = p.booleanCast(n.leftTop, i.leftTop), i.rightTop = p.booleanCast(n.rightTop, i.rightTop), i.leftBottom = p.booleanCast(n.leftBottom, i.leftBottom), i.rightBottom = p.booleanCast(n.rightBottom, i.rightBottom), i
        }

        e.default = l
    }, function (t, e, n) {
        "use strict";
        var i, o = this && this.__extends || (i = function (t, e) {
            return (i = Object.setPrototypeOf || {__proto__: []} instanceof Array && function (t, e) {
                t.__proto__ = e
            } || function (t, e) {
                for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
            })(t, e)
        }, function (t, e) {
            function n() {
                this.constructor = t
            }

            i(t, e), t.prototype = null === e ? Object.create(e) : (n.prototype = e.prototype, new n)
        });
        Object.defineProperty(e, "__esModule", {value: !0});
        var r, s = n(11), p = n(2), a = (r = s.default, o(d, r), d.prototype.dragStart = function (t, e, n) {
            if ("normal" !== this.window.status) return !1;
            this._top = this.window.top, this._left = this.window.left, this._width = this.window.width, this._height = this.window.height
        }, d.prototype.dragging = function (t, e, n, i, o) {
            switch (this.direction) {
                case"left":
                    this.resizeHandler(i, o, !1, !0, !1, !0);
                    break;
                case"right":
                    this.resizeHandler(i, o, !1, !1, !1, !0);
                    break;
                case"top":
                    this.resizeHandler(i, o, !0, !1, !0, !1);
                    break;
                case"bottom":
                    this.resizeHandler(i, o, !1, !1, !0, !1);
                    break;
                case"left-top":
                    this.resizeHandler(i, o, !0, !0, !1, !1);
                    break;
                case"right-top":
                    this.resizeHandler(i, o, !0, !1, !1, !1);
                    break;
                case"left-bottom":
                    this.resizeHandler(i, o, !1, !0, !1, !1);
                    break;
                case"right-bottom":
                    this.resizeHandler(i, o, !1, !1, !1, !1)
            }
        }, d.prototype.dragEnd = function (t) {
            this.window.top = this._top, this.window.left = this._left, this.window.width = this._width, this.window.height = this._height, this.window.storeStatus && (this.window.lastStoreStatus = {
                top: this.window.top,
                left: this.window.left,
                width: this.window.width,
                height: this.window.height
            }), this.app.drayLayer.hide(), this.content && this.content.showPenetrate(!1)
        }, d.prototype.resizeHandler = function (t, e, n, i, o, r) {
            var s = this.window.top + e, a = this.window.left + t,
                d = i ? this.window.width - t : this.window.width + t,
                u = n ? this.window.height - e : this.window.height + e;
            d = Math.max(d, this.window.minWidth), d = i ? (a = Math.min(a, this.window.left + this.window.width - this.window.minWidth), a = Math.max(0, a), Math.min(d, this.window.left + this.window.width)) : (a = Math.min(a, this.window.left), a = Math.max(this.window.left, a), Math.min(d, innerWidth - this.window.left)), d = Math.min(d, this.window.maxWidth), u = Math.max(u, this.window.minHeight), u = n ? (s = Math.min(s, this.window.top + this.window.height - this.window.minHeight), s = Math.max(0, s), Math.min(u, this.window.top + this.window.height)) : (s = Math.min(s, this.window.top), s = Math.max(this.window.top, s), Math.min(u, innerHeight - this.window.top)), u = Math.min(u, this.window.maxHeight), o && (this._top = s, this._height = u, p.addStyles(this.window.element, {
                top: s + "px",
                height: u + "px"
            })), r && (this._width = d, this._left = a, p.addStyles(this.window.element, {
                width: d + "px",
                left: a + "px"
            }), this.updateActionButton(d)), !1 === r && !1 === o && (this._top = s, this._left = a, this._width = d, this._height = u, p.addStyles(this.window.element, {
                top: s + "px",
                left: a + "px",
                height: u + "px",
                width: d + "px"
            })), this.updateActionButton(o ? this.window.width : d)
        }, d.prototype.updateActionButton = function (t) {
            if (t <= 300) {
                if (!1 !== this.isShowMoreActionButton) return;
                this.isShowMoreActionButton = !0, this.window.zoomActionButtons(t)
            } else {
                if (!0 !== this.isShowMoreActionButton) return;
                this.isShowMoreActionButton = !1, this.window.zoomActionButtons(t)
            }
        }, d.prototype.mouseStar = function (t) {
            this.app.drayLayer.updateZIndex(this.window.zIndex - 1), this.content = this.window.getComponent("content-container"), this.content && this.content.showPenetrate()
        }, d.prototype.mouseMove = function (t) {
            t.preventDefault()
        }, d.prototype.mouseEnd = function (t) {
        }, d);

        function d(t, e, n, i) {
            var o = r.call(this, n) || this;
            return o.app = t, o.window = e, o.direction = i, o.isShowMoreActionButton = !1, o._top = 0, o._left = 0, o._width = 0, o._height = 0, o.content = null, o
        }

        e.default = a
    }, function (t, e, n) {
        "use strict";
        var i, o = this && this.__extends || (i = function (t, e) {
            return (i = Object.setPrototypeOf || {__proto__: []} instanceof Array && function (t, e) {
                t.__proto__ = e
            } || function (t, e) {
                for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
            })(t, e)
        }, function (t, e) {
            function n() {
                this.constructor = t
            }

            i(t, e), t.prototype = null === e ? Object.create(e) : (n.prototype = e.prototype, new n)
        });
        Object.defineProperty(e, "__esModule", {value: !0});
        var r, s = n(4), u = n(23), p = n(24), l = n(26), c = n(27), h = n(2), a = n(3),
            d = (r = s.default, o(f, r), Object.defineProperty(f.prototype, "element", {
                get: function () {
                    return document.getElementById("" + this.elementId)
                }, enumerable: !0, configurable: !0
            }), f.prototype.present = function () {
                var e = this, t = h.createFragment(), n = h.createElement("div");
                if (n.setAttribute("data-window-id", this.window.id), n.id = this.elementId, h.addClasses(n, this.app.prefix, "tool-bar", "flexbox", "flex-row"), h.addStyles(n, {
                    height: this.height + "px",
                    background: this.background
                }), n.addEventListener("dblclick", function (t) {
                    "max" !== e.window.status ? "normal" !== e.window.status || e.window.max() : e.window.normal()
                }), !1 !== this.titleBar) {
                    var i = new l.default(this.app, this.window, this.titleBar), o = i.present();
                    n.appendChild(o), this.setComponent("title-bar", i)
                }
                if (!1 !== this.tabBar) {
                    var r = new c.default(this.app, this.window, this.tabBar), s = r.present();
                    n.appendChild(s), this.setComponent("tab-bar", r)
                }
                if (!1 !== this.actionBar) {
                    var a = new p.default(this.app, this.window, this.actionBar), d = a.present();
                    d && n.appendChild(d), this.setComponent("action-bar", a)
                }
                return !this.drag || !0 !== this.drag.vertical && !0 !== this.drag.horizontal || new u.default(this.app, this.window, n, this.drag), t.appendChild(n), t
            }, f);

        function f(t, e, n) {
            var i = r.call(this, t, e) || this;
            return i.elementId = i.window.elementId + "-tool-bar", i.height = 30, i.drag = {
                vertical: !0,
                horizontal: !0,
                breakLeft: !0,
                breakRight: !0,
                breakTop: !0,
                breakBottom: !0
            }, i.titleBar = {}, i.tabBar = {}, i.actionBar = {}, i.background = "#ffffff", i._element = null, i.height = a.numberCast(n.height, i.height), i.drag = a.jsonOrBooleanCast(n.drag, i.drag), i.titleBar = a.jsonOrBooleanCast(n.titleBar, i.titleBar), i.tabBar = a.jsonOrBooleanCast(n.tabBar, i.tabBar), i.actionBar = a.jsonOrBooleanCast(n.actionBar, i.actionBar), i.background = a.stringOrBooleanStyleCast(n.background, i.background), i
        }

        e.default = d
    }, function (t, e, n) {
        "use strict";
        var i, o = this && this.__extends || (i = function (t, e) {
            return (i = Object.setPrototypeOf || {__proto__: []} instanceof Array && function (t, e) {
                t.__proto__ = e
            } || function (t, e) {
                for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
            })(t, e)
        }, function (t, e) {
            function n() {
                this.constructor = t
            }

            i(t, e), t.prototype = null === e ? Object.create(e) : (n.prototype = e.prototype, new n)
        });
        Object.defineProperty(e, "__esModule", {value: !0});
        var r, s = n(11), a = n(2), d = (r = s.default, o(u, r), u.prototype.dragStart = function (t, e, n) {
            this._top = this.window.top, this._left = this.window.left
        }, u.prototype.dragging = function (t, e, n, i, o) {
            this.moveHandler(i, o)
        }, u.prototype.dragEnd = function (t) {
            if (1 == this.isDragging && 0 === this._top) return this.window.max(), this.window.top = this._originTop, void (this.window.left = this._originLeft);
            this.window.top = this._top, this.window.left = this._left, this.window.storeStatus && (this.window.lastStoreStatus = {
                top: this.window.top,
                left: this.window.left,
                width: this.window.width,
                height: this.window.height
            }), this.app.drayLayer.hide(), this.content && this.content.showPenetrate(!1)
        }, u.prototype.moveHandler = function (t, e) {
            var n = this.window.top, i = this.window.left;
            this.dragMoveOptions.vertical && (n += e, n = Math.max(0, n), n = this.dragMoveOptions.breakBottom ? Math.min(innerHeight - this.emerge, n) : Math.min(innerHeight - this.window.height, n), this._top = n), this.dragMoveOptions.horizontal && (i += t, i = this.dragMoveOptions.breakLeft ? Math.max(this.emerge - this.window.width, i) : Math.max(0, i), i = this.dragMoveOptions.breakRight ? Math.min(innerWidth - this.emerge, i) : Math.min(innerWidth - this.window.width, i), this._left = i), a.addStyles(this.window.element, {
                top: n + "px",
                left: i + "px"
            })
        }, u.prototype.draggingFirst = function (t, e, n, i, o) {
            this._originTop = this.window.top, this._originLeft = this.window.left, "max" === this.window.status && (e < this.window.width / 2 ? this._left = 0 : e > this.window.width / 2 && e < innerWidth - this.window.width ? this._left = e - this.window.width / 2 : innerWidth - e < this.window.width / 2 ? this._left = innerWidth - this.window.width : innerWidth - e > this.window.width / 2 && e >= innerWidth - this.window.width && (this._left = e - this.window.width / 2), this.window.top = o, this.window.left = this._left, this.window.normal(!0))
        }, u.prototype.mouseStar = function (t) {
            this.app.drayLayer.updateZIndex(this.window.zIndex - 1), this.content = this.window.getComponent("content-container"), this.content && this.content.showPenetrate()
        }, u.prototype.mouseMove = function (t) {
            t.preventDefault()
        }, u.prototype.mouseEnd = function (t) {
        }, u);

        function u(t, e, n, i) {
            var o = r.call(this, n) || this;
            return o.app = t, o.window = e, o.dragMoveOptions = i, o.emerge = 10, o._top = 0, o._left = 0, o._originTop = 0, o._originLeft = 0, o._lastTime = null, o.content = null, o
        }

        e.default = d
    }, function (t, e, n) {
        "use strict";
        var i, o = this && this.__extends || (i = function (t, e) {
            return (i = Object.setPrototypeOf || {__proto__: []} instanceof Array && function (t, e) {
                t.__proto__ = e
            } || function (t, e) {
                for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
            })(t, e)
        }, function (t, e) {
            function n() {
                this.constructor = t
            }

            i(t, e), t.prototype = null === e ? Object.create(e) : (n.prototype = e.prototype, new n)
        });
        Object.defineProperty(e, "__esModule", {value: !0});
        var r, s = n(4), f = n(10), m = n(13), w = n(2), a = n(3),
            d = (r = s.default, o(v, r), Object.defineProperty(v.prototype, "element", {
                get: function () {
                    return document.getElementById("" + this.elementId)
                }, enumerable: !0, configurable: !0
            }), v.prototype.present = function () {
                var t = w.createFragment();
                if (!0 === this.enable) {
                    var e = w.createElement("div");
                    e.setAttribute("data-window-id", this.window.id), e.id = this.elementId, w.addClasses(e, this.app.prefix, "action-bar", "flexbox", "flex-row"), e.addEventListener("mousedown", function (t) {
                        t.preventDefault(), t.stopPropagation()
                    }), e.addEventListener("contextmenu", function (t) {
                        return t.preventDefault(), t.stopPropagation(), t.returnValue = !1
                    }, !0), this.createActionButtons(e), t.appendChild(e)
                }
                return t
            }, v.prototype.zoomActionButtons = function (t) {
                if (!1 !== this.items) {
                    var e = t <= v.actionButtonZoomWidth, n = this.getComponent("action-buttons");
                    if (n) {
                        for (var i = n.slice().reverse(), o = i[0], r = i.slice(1), s = Array(), a = 0, d = r = r.reverse(); a < d.length; a++) {
                            var u = d[a];
                            e ? w.addClasses(u.element, this.app.prefix, "action-button-hidden") : w.removeClasses(u.element, this.app.prefix, "action-button-hidden"), s.push(u)
                        }
                        var p = new m.default(this.app, this.window, m.default.more);
                        if (e) {
                            if (!p.element) {
                                var l = new f.default(this.app, this.window, "more-action", s), c = l.present();
                                document.body.appendChild(c), p.handler = function (t, e) {
                                    l.updateOffset(t, this.window.zIndex + 1)
                                };
                                var h = p.present();
                                h.firstElementChild && o.element.insertAdjacentElement("beforebegin", h.firstElementChild), this.window.setComponent("more-action-context-menu-bar", l)
                            }
                        } else p.element && (this.window.removeMoreActionContextMenu(), p.element && p.element.parentElement.removeChild(p.element), this.window.removeComponent("more-action-context-menu-bar"))
                    }
                }
            }, v.prototype.createActionButtons = function (t) {
                if (!1 !== this.items) {
                    for (var e = Array(), n = 0, i = this.items; n < i.length; n++) {
                        var o = i[n], r = new m.default(this.app, this.window, o), s = r.present();
                        t.appendChild(s), e.push(r)
                    }
                    this.setComponent("action-buttons", e)
                }
            }, v.actionButtonZoomWidth = 300, v);

        function v(t, e, n) {
            var i = r.call(this, t, e) || this;
            return i.elementId = i.window.elementId + "-action-bar", i.enable = !0, i.items = [m.default.refresh, m.default.min, m.default.max, m.default.destroy], i._element = null, i.enable = a.booleanCast(n.enable, i.enable), i.items = a.actionButtonsCast(n.actionButtons, i.items), i
        }

        e.default = d
    }, function (t, e, n) {
        "use strict";
        var i, o = this && this.__extends || (i = function (t, e) {
            return (i = Object.setPrototypeOf || {__proto__: []} instanceof Array && function (t, e) {
                t.__proto__ = e
            } || function (t, e) {
                for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
            })(t, e)
        }, function (t, e) {
            function n() {
                this.constructor = t
            }

            i(t, e), t.prototype = null === e ? Object.create(e) : (n.prototype = e.prototype, new n)
        });
        Object.defineProperty(e, "__esModule", {value: !0});
        var r, s = n(4), p = n(10), l = n(7), c = n(2), a = n(3),
            d = (r = s.default, o(h, r), h.prototype.present = function () {
                var e = this, t = c.createFragment(), n = c.createElement("div");
                n.setAttribute("data-window-id", this.window.id), n.setAttribute("data-index", "" + this.index), c.addClasses(n, this.app.prefix, "context-menu-button", "flexbox", "flex-row"), c.addStyles(n, {
                    height: h.height + "px",
                    lineHeight: h.height + "px"
                }), n.addEventListener("mousedown", function (t) {
                    t.stopPropagation(), 0 === t.button && "function" == typeof e.handler && e.handler(t, e.window)
                });
                var i = c.createElement("label");
                if (i.setAttribute("data-window-id", this.window.id), i.innerText = this.label, c.addClasses(i, this.app.prefix, "context-menu-button-label", "flex-item"), n.appendChild(i), !1 !== this.items) {
                    var o = new p.default(this.app, this.window, "" + this.id, this.items, !1), r = o.present();
                    n.appendChild(r);
                    var s, a = i.nextElementSibling;
                    n.addEventListener("mouseenter", function (t) {
                        s = setTimeout(function () {
                            o.updateChildrenOffset(t, a, e.index)
                        }, 200)
                    }), n.addEventListener("mouseleave", function (t) {
                        clearTimeout(s), o.hideChildren(a)
                    }), this.setComponent("context-menu-bar", o);
                    var d = c.createElement("div");
                    d.setAttribute("data-window-id", this.window.id), c.addClasses(d, this.app.prefix, "context-menu-more", "flexbox", "flex-center");
                    var u = new l.default(this.app, "right").present();
                    d.appendChild(u), n.appendChild(d)
                }
                return t.appendChild(n), t
            }, h.height = 30, h);

        function h(t, e, n, i) {
            void 0 === i && (i = 0);
            var o = r.call(this, t, e) || this;
            return o.index = i, o.id = a.stringCast(n.id), o.label = a.stringCast(n.label), o.handler = n.handler, o.items = a.contextMenuButtonsCast(n.items), o
        }

        e.default = d
    }, function (t, e, n) {
        "use strict";
        var i, o = this && this.__extends || (i = function (t, e) {
            return (i = Object.setPrototypeOf || {__proto__: []} instanceof Array && function (t, e) {
                t.__proto__ = e
            } || function (t, e) {
                for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
            })(t, e)
        }, function (t, e) {
            function n() {
                this.constructor = t
            }

            i(t, e), t.prototype = null === e ? Object.create(e) : (n.prototype = e.prototype, new n)
        });
        Object.defineProperty(e, "__esModule", {value: !0});
        var r, s = n(4), d = n(7), u = n(2), a = n(3),
            p = (r = s.default, o(l, r), Object.defineProperty(l.prototype, "element", {
                get: function () {
                    return document.getElementById("" + this.elementId)
                }, enumerable: !0, configurable: !0
            }), l.prototype.present = function () {
                var e = this, t = u.createFragment(), n = u.createElement("div");
                if (n.setAttribute("data-window-id", this.window.id), n.id = this.elementId, u.addClasses(n, this.app.prefix, "title-bar", "flexbox", "flex-row", "flex-vertical-center"), this.icon) {
                    var i = u.createElement("div");
                    i.setAttribute("data-window-id", this.window.id), u.addClasses(i, this.app.prefix, "window-icon", "flexbox", "flex-center"), i.addEventListener("dblclick", function (t) {
                        t.stopPropagation(), e.window.destroy()
                    }), n.appendChild(i);
                    var o = new d.default(this.app, this.icon), r = o.present();
                    i.appendChild(r), this.setComponent("window-icon", o)
                }
                if (this.title || this.useSubTitle) {
                    var s = u.createElement("div");
                    s.setAttribute("data-window-id", this.window.id), u.addClasses(s, this.app.prefix, "window-title");
                    var a = document.createElement("label");
                    a.setAttribute("data-window-id", this.window.id), u.addClasses(a, this.app.prefix, "window-title-label"), this.title && (a.innerText = this.title, a.setAttribute("title", this.title)), s.appendChild(a), n.appendChild(s)
                }
                return t.appendChild(n), t
            }, l.prototype.updateTitle = function (t) {
                var e = this.element;
                if (e) {
                    var n = e.querySelector("." + this.app.prefix + "window-title-label");
                    if (n) {
                        if (n.innerText = t, n.setAttribute("title", t), this.app.salver && this.app.salver.element) {
                            var i = this.app.salver.element.querySelector("." + this.app.prefix + "salver-button[data-window-id='" + this.window.id + "']");
                            i && i.setAttribute("title", t)
                        }
                        this.title = t
                    }
                }
            }, l);

        function l(t, e, n) {
            var i = r.call(this, t, e) || this;
            return i.elementId = i.window.elementId + "-title-bar", i.icon = "icon", i.title = void 0, i.useSubTitle = !1, i._element = null, i.icon = a.typeOrBooleanCast(n.icon, i.icon), i.title = n.title, i.useSubTitle = a.booleanCast(n.useSubTitle, i.useSubTitle), i
        }

        e.default = p
    }, function (t, e, n) {
        "use strict";
        var i, o = this && this.__extends || (i = function (t, e) {
            return (i = Object.setPrototypeOf || {__proto__: []} instanceof Array && function (t, e) {
                t.__proto__ = e
            } || function (t, e) {
                for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
            })(t, e)
        }, function (t, e) {
            function n() {
                this.constructor = t
            }

            i(t, e), t.prototype = null === e ? Object.create(e) : (n.prototype = e.prototype, new n)
        });
        Object.defineProperty(e, "__esModule", {value: !0});
        var r, s = n(4), a = n(2), d = (r = s.default, o(u, r), Object.defineProperty(u.prototype, "element", {
            get: function () {
                return document.getElementById("" + this.elementId)
            }, enumerable: !0, configurable: !0
        }), u.prototype.present = function () {
            var t = a.createFragment(), e = a.createElement("div");
            return e.setAttribute("data-window-id", this.window.id), e.id = this.elementId, a.addClasses(e, this.app.prefix, "tab-bar", "flexbox", "flex-item", "flex-row"), t.appendChild(e), t
        }, u);

        function u(t, e, n) {
            var i = r.call(this, t, e) || this;
            return i.elementId = i.window.elementId + "-tab-bar", i._element = null, i
        }

        e.default = d
    }, function (t, e, n) {
        "use strict";
        var i, o = this && this.__extends || (i = function (t, e) {
            return (i = Object.setPrototypeOf || {__proto__: []} instanceof Array && function (t, e) {
                t.__proto__ = e
            } || function (t, e) {
                for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
            })(t, e)
        }, function (t, e) {
            function n() {
                this.constructor = t
            }

            i(t, e), t.prototype = null === e ? Object.create(e) : (n.prototype = e.prototype, new n)
        });
        Object.defineProperty(e, "__esModule", {value: !0});
        var r, s = n(4), a = n(29), d = n(2), u = n(3),
            p = (r = s.default, o(l, r), Object.defineProperty(l.prototype, "element", {
                get: function () {
                    return document.getElementById("" + this.elementId)
                }, enumerable: !0, configurable: !0
            }), l.prototype.present = function () {
                var t = d.createFragment();
                if (!1 !== this.contextMenuButtons && 0 < this.contextMenuButtons.length) {
                    var e = d.createElement("div");
                    e.setAttribute("data-window-id", this.window.id), e.id = this.elementId, d.addClasses(e, this.app.prefix, "top-menu-bar"), e.addEventListener("contextmenu", function (t) {
                        return t.preventDefault(), t.stopPropagation(), t.returnValue = !1
                    }, !0), this.createTopMenuButtons(e), t.appendChild(e)
                }
                return t
            }, l.prototype.hide = function (t) {
                this.currentTopMenuButtonElement && d.removeClasses(this.currentTopMenuButtonElement, this.app.prefix, "top-menu-button-active"), this.currentTopMenuContextBar && this.currentTopMenuContextBar.hide();
                var e = t.target;
                this.isActive = -1 < e.className.indexOf(this.app.prefix + "top-menu-button") && e.getAttribute("data-window-id") === this.window.id && !this.isActive, this.currentTopMenuContextBar = null, this.currentTopMenuButtonElement = null
            }, l.prototype.createTopMenuButtons = function (t) {
                if (!1 !== this.contextMenuButtons) {
                    for (var e = Array(), n = 0, i = this.contextMenuButtons; n < i.length; n++) {
                        var o = i[n], r = new a.default(this.app, this.window, this, o), s = r.present();
                        t.appendChild(s), e.push(r)
                    }
                    this.setComponent("top-menu-buttons", e)
                }
            }, l);

        function l(t, e, n) {
            var i = r.call(this, t, e) || this;
            return i.elementId = i.window.elementId + "-top-menu-bar", i.isActive = !1, i.currentTopMenuContextBar = null, i.currentTopMenuButtonElement = null, i.contextMenuButtons = !1, i._element = null, i.contextMenuButtons = u.contextMenuButtonsCast(n), i
        }

        e.default = p
    }, function (t, e, n) {
        "use strict";
        var i, o = this && this.__extends || (i = function (t, e) {
            return (i = Object.setPrototypeOf || {__proto__: []} instanceof Array && function (t, e) {
                t.__proto__ = e
            } || function (t, e) {
                for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
            })(t, e)
        }, function (t, e) {
            function n() {
                this.constructor = t
            }

            i(t, e), t.prototype = null === e ? Object.create(e) : (n.prototype = e.prototype, new n)
        });
        Object.defineProperty(e, "__esModule", {value: !0});
        var r, s = n(4), a = n(10), d = n(2), u = n(3), p = (r = s.default, o(l, r), l.prototype.present = function () {
            var t = d.createFragment(), e = d.createElement("div");
            e.setAttribute("data-window-id", this.window.id), d.addClasses(e, this.app.prefix, "top-menu-button");
            var n = d.createElement("label");
            if (n.setAttribute("data-window-id", this.window.id), n.innerText = this.label, d.addClasses(n, this.app.prefix, "top-menu-button-label"), e.appendChild(n), !1 !== this.items) {
                var i = new a.default(this.app, this.window, "top-menu-" + this.id, this.items), o = i.present();
                document.body.appendChild(o), this.setComponent("context-menu-bar", i)
            }
            return this.bindEvent(e), t.appendChild(e), t
        }, l.prototype.bindEvent = function (i) {
            var o = this;
            i.addEventListener("mousedown", function (t) {
                o.topMenuBar.currentTopMenuButtonElement && d.removeClasses(o.topMenuBar.currentTopMenuButtonElement, o.app.prefix, "top-menu-button-active"), o.topMenuBar.currentTopMenuContextBar && o.topMenuBar.currentTopMenuContextBar.hide();
                var e = o.getComponent("context-menu-bar");
                if (o.topMenuBar.isActive) {
                    if (d.addClasses(i, o.app.prefix, "top-menu-button-active"), e && e.element) {
                        var n = i.getBoundingClientRect();
                        e.updateOffset(t, o.window.zIndex + 1, n.left, n.top + 25)
                    }
                } else d.removeClasses(i, o.app.prefix, "top-menu-button-active"), e && e.hide();
                o.topMenuBar.currentTopMenuContextBar = e, o.topMenuBar.currentTopMenuButtonElement = i
            }), i.addEventListener("mouseenter", function (t) {
                if (o.topMenuBar.isActive) {
                    o.topMenuBar.currentTopMenuButtonElement && d.removeClasses(o.topMenuBar.currentTopMenuButtonElement, o.app.prefix, "top-menu-button-active"), o.topMenuBar.currentTopMenuContextBar && o.topMenuBar.currentTopMenuContextBar.hide(), d.addClasses(i, o.app.prefix, "top-menu-button-active");
                    var e = o.getComponent("context-menu-bar");
                    if (e && e.element) {
                        var n = i.getBoundingClientRect();
                        e.updateOffset(t, o.window.zIndex + 1, n.left, n.top + 25)
                    }
                    o.topMenuBar.currentTopMenuContextBar = e, o.topMenuBar.currentTopMenuButtonElement = i
                }
            })
        }, l);

        function l(t, e, n, i) {
            var o = r.call(this, t, e) || this;
            return o.topMenuBar = n, o.id = u.stringCast(i.id), o.label = u.stringCast(i.label), o.handler = i.handler, o.items = u.contextMenuButtonsCast(i.items), o
        }

        e.default = p
    }, function (t, e, n) {
        "use strict";
        var i, o = this && this.__extends || (i = function (t, e) {
            return (i = Object.setPrototypeOf || {__proto__: []} instanceof Array && function (t, e) {
                t.__proto__ = e
            } || function (t, e) {
                for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
            })(t, e)
        }, function (t, e) {
            function n() {
                this.constructor = t
            }

            i(t, e), t.prototype = null === e ? Object.create(e) : (n.prototype = e.prototype, new n)
        });
        Object.defineProperty(e, "__esModule", {value: !0});
        var r, s = n(6), a = n(31), d = n(2),
            u = (r = s.default, o(p, r), Object.defineProperty(p.prototype, "element", {
                get: function () {
                    return document.getElementById("" + this.elementId)
                }, enumerable: !0, configurable: !0
            }), Object.defineProperty(p.prototype, "items", {
                get: function () {
                    return this._items
                }, set: function (t) {
                    this._items = t
                }, enumerable: !0, configurable: !0
            }), p.prototype.present = function () {
                var t = d.createFragment(), e = d.createElement("div");
                return e.id = this.elementId, d.addClasses(e, this.app.prefix, "salver-bar", "animate-d3s", "flexbox", "flex-row"), d.addStyles(e, {
                    zIndex: "" + this.app.salverZIndex,
                    height: a.default.size + "px",
                    bottom: "-" + (a.default.size - a.default.talentHeight) + "px"
                }), t.appendChild(e), t
            }, p.prototype.addOrUpdateItem = function () {
                var t = this.element;
                if (this.app.window && t) {
                    var e = this.app.lastWindow;
                    if (e) {
                        var n = new a.default(this.app, e.id);
                        d.removeClasses(n.element, this.app.prefix, "salver-button-active")
                    }
                    var i = this.app.window.id, o = new a.default(this.app, i);
                    if (-1 === this.items.indexOf(i)) {
                        var r = o.present();
                        t.appendChild(r), this.items.push(i), this.updateOffset()
                    } else d.addClasses(o.element, this.app.prefix, "salver-button-active")
                }
            }, p.prototype.removeItem = function () {
                var t = this.element;
                if (this.app.window && t) {
                    var e = this.app.window.id, n = new a.default(this.app, e);
                    d.removeElement(n.element);
                    var i = this.items.indexOf(e);
                    this.items.splice(i, 1), this.updateOffset()
                }
            }, p.prototype.updateOffset = function () {
                var t = this.element;
                if (t) {
                    var e = this.items.length * a.default.size;
                    d.addStyles(t, {left: (innerWidth - e) / 2 + "px"})
                }
            }, p.prototype.show = function (t) {
                void 0 === t && (t = !0), t ? (d.removeClasses(this.element, this.app.prefix, "animate-salver-slide-down"), d.addClasses(this.element, this.app.prefix, "animate-salver-slide-up", "salver-bar-keep")) : (d.removeClasses(this.element, this.app.prefix, "animate-salver-slide-up", "salver-bar-keep"), d.addClasses(this.element, this.app.prefix, "animate-salver-slide-down"))
            }, p);

        function p(t) {
            var e = r.call(this, t) || this;
            return e.elementId = e.app.prefix + "salver-bar", e.parsecloseCount = 0, e._element = null, e._items = [], e
        }

        e.default = u
    }, function (t, e, n) {
        "use strict";
        var i, o = this && this.__extends || (i = function (t, e) {
            return (i = Object.setPrototypeOf || {__proto__: []} instanceof Array && function (t, e) {
                t.__proto__ = e
            } || function (t, e) {
                for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
            })(t, e)
        }, function (t, e) {
            function n() {
                this.constructor = t
            }

            i(t, e), t.prototype = null === e ? Object.create(e) : (n.prototype = e.prototype, new n)
        });
        Object.defineProperty(e, "__esModule", {value: !0});
        var r, s = n(6), d = n(7), u = n(2), a = n(3),
            p = (r = s.default, o(l, r), Object.defineProperty(l.prototype, "element", {
                get: function () {
                    return document.getElementById("" + this.elementId)
                }, enumerable: !0, configurable: !0
            }), l.prototype.present = function () {
                var i = this, t = u.createFragment(), e = u.createElement("div");
                e.id = this.elementId, e.setAttribute("data-window-id", this.windowId), u.addClasses(e, this.app.prefix, "salver-button", "flexbox", "flex-center", "salver-button-active"), u.addStyles(e, {
                    width: l.size + "px",
                    height: l.size + "px"
                });
                var o = this.app.getWindow(this.windowId);
                e.addEventListener("mousedown", function (t) {
                    if (o && i.app.salver && i.app.salver.element) if (0 < i.app.salver.parsecloseCount) {
                        var e = i.app.salver.element.querySelector("." + i.app.prefix + "salver-button." + i.app.prefix + "salver-button-active");
                        if (e) {
                            var n = i.app.getWindow(e.getAttribute("data-window-id"));
                            n && n.flicker()
                        }
                    } else o === i.app.window && "min" !== o.status ? o.min() : o.updateZIndex()
                });
                var n = o.getComponent("\n            tool-bar\n            /title-bar"), r = "未命名标题",
                    s = new d.default(this.app, "icon");
                if (n) {
                    r = n.title || r;
                    var a = n.getComponent("window-icon");
                    a && (s = a)
                }
                return e.setAttribute("title", r), e.appendChild(s.present()), t.appendChild(e), t
            }, l.size = 50, l.talentHeight = 5, l);

        function l(t, e) {
            var n = r.call(this, t) || this;
            return n._element = null, n.windowId = a.stringCast(e), n.elementId = n.app.prefix + e + "-salver-button", n
        }

        e.default = p
    }, function (t, e, n) {
        "use strict";
        var i, o = this && this.__extends || (i = function (t, e) {
            return (i = Object.setPrototypeOf || {__proto__: []} instanceof Array && function (t, e) {
                t.__proto__ = e
            } || function (t, e) {
                for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
            })(t, e)
        }, function (t, e) {
            function n() {
                this.constructor = t
            }

            i(t, e), t.prototype = null === e ? Object.create(e) : (n.prototype = e.prototype, new n)
        });
        Object.defineProperty(e, "__esModule", {value: !0});
        var r, s = n(4), a = n(33), d = n(34), u = n(2), p = n(3),
            l = (r = s.default, o(c, r), Object.defineProperty(c.prototype, "element", {
                get: function () {
                    return document.getElementById("" + this.elementId)
                }, enumerable: !0, configurable: !0
            }), Object.defineProperty(c.prototype, "penetrateElement", {
                get: function () {
                    var t = this.element;
                    return t ? t.querySelector("." + this.app.prefix + "content-penetrate") : null
                }, enumerable: !0, configurable: !0
            }), c.prototype.present = function () {
                var t = u.createFragment(), e = u.createElement("div");
                e.setAttribute("data-window-id", this.window.id), e.id = this.elementId, u.addClasses(e, this.app.prefix, "content-container", "content-container-fade-out", "flex-item"), u.addClasses(e, this.app.prefix, "content-container");
                var n = u.createElement("div");
                switch (u.addClasses(n, this.app.prefix, "content-penetrate"), e.appendChild(n), this.type) {
                    case"html":
                        var i = new a.default(this.app, this.window, this.value), o = i.present();
                        e.appendChild(o), this.setComponent("content", i);
                        break;
                    case"local-url":
                        var r = new d.default(this.app, this.window, this.value), s = r.present();
                        e.appendChild(s), this.setComponent("content", r)
                }
                return t.appendChild(e), t
            }, c.prototype.showPenetrate = function (t) {
                void 0 === t && (t = !0), t ? u.addClasses(this.penetrateElement, this.app.prefix, "content-penetrate-active") : u.removeClasses(this.penetrateElement, this.app.prefix, "content-penetrate-active")
            }, c.prototype.refreshContent = function () {
                if ("local-url" === this.type) {
                    var t = this.window.getComponent("\n            content-container\n            /content");
                    t && t.contentWindow && t.contentWindow.location.reload()
                }
            }, c);

        function c(t, e, n) {
            var i = r.call(this, t, e) || this;
            return i.elementId = i.window.elementId + "-content-container", i.type = "html", i.value = "", i._element = null, i._penetrateElement = null, i.type = p.contentTypeCast(n.type, i.type), i.value = p.stringOrElementCast(n.value), i
        }

        e.default = l
    }, function (t, e, n) {
        "use strict";
        var i, o = this && this.__extends || (i = function (t, e) {
            return (i = Object.setPrototypeOf || {__proto__: []} instanceof Array && function (t, e) {
                t.__proto__ = e
            } || function (t, e) {
                for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
            })(t, e)
        }, function (t, e) {
            function n() {
                this.constructor = t
            }

            i(t, e), t.prototype = null === e ? Object.create(e) : (n.prototype = e.prototype, new n)
        });
        Object.defineProperty(e, "__esModule", {value: !0});
        var r, s = n(4), a = n(2), d = n(5), u = (r = s.default, o(p, r), p.prototype.present = function () {
            var t = a.createFragment(), e = a.createElement("div");
            if (a.addClasses(e, this.app.prefix, "html-content"), d.isElement(this.content)) {
                var n = this.content.cloneNode(!0);
                a.addStyles(n, {display: "inherit"}), e.appendChild(n)
            } else e.innerHTML = this.content;
            return t.appendChild(e), t
        }, p);

        function p(t, e, n) {
            var i = r.call(this, t, e) || this;
            return i.content = n, i
        }

        e.default = u
    }, function (t, e, n) {
        "use strict";
        var i, o = this && this.__extends || (i = function (t, e) {
            return (i = Object.setPrototypeOf || {__proto__: []} instanceof Array && function (t, e) {
                t.__proto__ = e
            } || function (t, e) {
                for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
            })(t, e)
        }, function (t, e) {
            function n() {
                this.constructor = t
            }

            i(t, e), t.prototype = null === e ? Object.create(e) : (n.prototype = e.prototype, new n)
        });
        Object.defineProperty(e, "__esModule", {value: !0});
        var r, s = n(4), a = n(2), d = n(3), u = n(5), p = n(12),
            l = (r = s.default, o(c, r), Object.defineProperty(c.prototype, "element", {
                get: function () {
                    return document.getElementById("" + this.elementId)
                }, enumerable: !0, configurable: !0
            }), Object.defineProperty(c.prototype, "contentWindow", {
                get: function () {
                    var t = document.getElementById("" + this.elementId);
                    return t ? t.contentWindow : null
                }, enumerable: !0, configurable: !0
            }), c.prototype.present = function () {
                var t = a.createFragment(), e = a.createElement("iframe");
                return e.setAttribute("data-window-id", this.window.id), e.id = this.elementId, e.setAttribute("frameborder", "0"), e.setAttribute("scrolling", "no"), e.setAttribute("allowtransparency", "true"), e.src = this.url, a.addClasses(e, this.app.prefix, "url-content"), this.bindEvent(e), t.appendChild(e), t
            }, c.prototype.bindEvent = function (o) {
                var r = this;
                o.addEventListener("load", function (t) {
                    var e = o.contentWindow;
                    if (e) {
                        var n = r.window.getComponent("\n            tool-bar\n            /title-bar");
                        if (n && n.useSubTitle) {
                            var i = e.document.querySelector("title");
                            i && n.updateTitle(i.innerText || "未命名标题")
                        }
                        !1 !== r.window.contextMenu && e.document.addEventListener("contextmenu", function (t) {
                            t.preventDefault(), t.returnValue = !1;
                            var e = document.createEvent("Event");
                            e.initEvent("contextmenu", !0), r.window.element.dispatchEvent(e);
                            var n = r.element.getBoundingClientRect(), i = r.window.getComponent("context-menu-bar");
                            return i && i.updateOffset(t, r.window.zIndex + 1, t.pageX + n.left, t.pageY + n.top), !1
                        }), e.document.addEventListener("mousedown", r.mousedown), p.addTouchMoveEvent(e.document, r.mousemove)
                    }
                })
            }, c);

        function c(t, e, n) {
            var i = r.call(this, t, e) || this;
            return i.elementId = i.window.elementId + "-url-content", i._element = null, i._contentWindow = null, i.mousedown = function (t) {
                var e = document.createEvent("Event");
                e.initEvent("mousedown", !0), i.window.element.dispatchEvent(e)
            }, i.mousemove = function (t) {
                var e = i.element.getBoundingClientRect(),
                    n = (u.isMoveEvent(t) ? t.pageY : t.touches[0].pageY) + e.top;
                if (i.app.salver && i.app.salver.element) if (n >= parent.innerHeight - 50) {
                    if (a.containClass(i.app.salver.element, i.app.prefix, "salver-bar-keep")) return;
                    i.app.salver.show()
                } else {
                    if (!a.containClass(i.app.salver.element, i.app.prefix, "salver-bar-keep")) return;
                    i.app.salver.show(!1)
                }
            }, i.url = d.stringCast(n), i
        }

        e.default = l
    }, function (t, e, n) {
        "use strict";
        var i, o = this && this.__extends || (i = function (t, e) {
            return (i = Object.setPrototypeOf || {__proto__: []} instanceof Array && function (t, e) {
                t.__proto__ = e
            } || function (t, e) {
                for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
            })(t, e)
        }, function (t, e) {
            function n() {
                this.constructor = t
            }

            i(t, e), t.prototype = null === e ? Object.create(e) : (n.prototype = e.prototype, new n)
        });
        Object.defineProperty(e, "__esModule", {value: !0});
        var r, s = n(4), a = n(2), d = n(3), u = n(5),
            p = (r = s.default, o(l, r), Object.defineProperty(l.prototype, "element", {
                get: function () {
                    return document.getElementById("" + this.elementId)
                }, enumerable: !0, configurable: !0
            }), l.prototype.present = function () {
                var t = a.createFragment(), e = a.createElement("div");
                if (e.setAttribute("data-window-id", this.window.id), e.id = this.elementId, a.addClasses(e, this.app.prefix, "statu-bar"), a.addStyles(e, {
                    height: this.height + "px",
                    background: "" + this.background
                }), u.isElement(this.content)) {
                    var n = this.content.cloneNode(!0);
                    a.addStyles(n, {display: "inherit"}), e.appendChild(n)
                } else e.innerHTML = this.content;
                return t.appendChild(e), t
            }, l);

        function l(t, e, n) {
            var i = r.call(this, t, e) || this;
            return i.elementId = i.window.elementId + "-statu-bar", i.height = 30, i.background = "#e5e5e5", i.content = "", i._element = null, i.height = d.numberCast(n.height, i.height), i.background = d.stringOrBooleanStyleCast(n.background, i.background), i.content = d.stringOrElementCast(n.content), i
        }

        e.default = p
    }, function (t, e, n) {
        "use strict";
        var i, o = this && this.__extends || (i = function (t, e) {
            return (i = Object.setPrototypeOf || {__proto__: []} instanceof Array && function (t, e) {
                t.__proto__ = e
            } || function (t, e) {
                for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
            })(t, e)
        }, function (t, e) {
            function n() {
                this.constructor = t
            }

            i(t, e), t.prototype = null === e ? Object.create(e) : (n.prototype = e.prototype, new n)
        });
        Object.defineProperty(e, "__esModule", {value: !0});
        var r, s = n(6), a = n(7), d = n(2), u = n(3), p = n(37),
            l = (r = s.default, o(c, r), Object.defineProperty(c.prototype, "element", {
                get: function () {
                    return document.getElementById(this.elementId)
                }, enumerable: !0, configurable: !0
            }), Object.defineProperty(c.prototype, "processElement", {
                get: function () {
                    return this.element ? this.element.querySelector("." + this.app.prefix + "notice-process") : null
                }, enumerable: !0, configurable: !0
            }), c.prototype.present = function () {
                var t = d.createFragment(), e = d.createElement("div");
                e.id = this.elementId, d.addClasses(e, this.app.prefix, "notice", "animate-d3s", "animate-fade-in-right"), d.addStyles(e, {zIndex: "" + this.id}), this.bindEvent(e), this.createClose(e);
                var n = d.createElement("div");
                d.addClasses(n, this.app.prefix, "notice-container", "flexbox", "flex-row");
                var i = d.createElement("div");
                d.addClasses(i, this.app.prefix, "notice-icon", "notice-" + this.type);
                var o = new a.default(this.app, "" + this.type).present();
                i.appendChild(o), n.appendChild(i);
                var r = d.createElement("div");
                return d.addClasses(r, this.app.prefix, "notice-message", "flex-item"), this.createTitle(r), this.createContent(r), n.appendChild(r), e.appendChild(n), this.createNoticeTime(e), this.createProcess(e), t.appendChild(e), t
            }, c.prototype.createClose = function (t) {
                var e = this, n = d.createElement("div");
                d.addClasses(n, this.app.prefix, "notice-close-button", "flexbox", "flex-center");
                var i = new a.default(this.app, "destroy").present();
                n.appendChild(i), n.addEventListener("mousedown", function (t) {
                    e.destroy()
                }), t.appendChild(n)
            }, c.prototype.createTitle = function (t) {
                if (void 0 !== this.title) {
                    var e = d.createElement("div");
                    d.addClasses(e, this.app.prefix, "notice-title", "notice-" + this.type), e.innerText = this.title, t.appendChild(e)
                }
            }, c.prototype.createContent = function (t) {
                var e = d.createElement("div");
                d.addClasses(e, this.app.prefix, "notice-content"), e.innerText = this.message, t.appendChild(e)
            }, c.prototype.createNoticeTime = function (t) {
                var e = d.createElement("div");
                d.addClasses(e, this.app.prefix, "notice-time"), e.innerText = "" + p.getDatetime(), t.appendChild(e)
            }, c.prototype.createProcess = function (t) {
                var e = d.createElement("div");
                d.addClasses(e, this.app.prefix, "notice-process"), t.appendChild(e)
            }, c.prototype.calcTopOffset = function (t) {
                void 0 === t && (t = void 0);
                var e = c.topOffset;
                if (0 < this.app.notices.length) {
                    var n = (void 0 === t ? this.app.notices.length : t) - 1,
                        i = this.app.notices[n].element.getBoundingClientRect();
                    e = i.top + i.height + c.space
                }
                return e
            }, c.prototype.bindEvent = function (t) {
                var n = this;
                t.addEventListener("animationend", function (t) {
                    var e = n.element;
                    d.containClass(e, n.app.prefix, "animate-fade-in-right-reverse") && n.remove(), 0 !== n.timeout && d.containClass(e, n.app.prefix, "animate-fade-in-right") && n.processAnimate(), d.removeClasses(e, n.app.prefix, "animate-fade-in-right-reverse", "animate-fade-in-right", "animate-slide-to-top")
                }), 0 !== this.timeout && (t.addEventListener("mouseenter", function (t) {
                    clearInterval(n.timer)
                }), t.addEventListener("mouseleave", function (t) {
                    n.processAnimate()
                }))
            }, c.prototype.destroy = function () {
                d.addClasses(this.element, this.app.prefix, "animate-fade-in-right-reverse")
            }, c.prototype.remove = function () {
                var t = this.app.notices.indexOf(this);
                this.app.notices.splice(t, 1), d.removeElement(this.element), this.updateOffset(t)
            }, c.prototype.processAnimate = function () {
                var t = this;
                this.timer = setInterval(function () {
                    t.fps <= t.timeout ? d.addStyles(t.processElement, {width: t.fps / t.timeout * 100 + "%"}) : (clearInterval(t.timer), t.destroy()), t.fps += 10
                }, 10)
            }, c.prototype.updateOffset = function (t, e) {
                void 0 === e && (e = !1);
                for (var n = this.app.notices, i = t; i < n.length; i++) d.addStyles(n[i].element, {top: (0 === i ? c.topOffset : this.calcTopOffset(i)) + "px"}), e || d.addClasses(this.element, this.app.prefix, "animate-slide-to-top")
            }, c.topOffset = 24, c.space = 10, c);

        function c(t, e) {
            var n = r.call(this, t) || this;
            return n.timer = 0, n.fps = 10, n.id = n.app.noticeZIndex, n.elementId = n.app.prefix + "notice-" + n.id, n.type = "info", n.timeout = 3e3, n._element = null, n._processElement = null, n.type = u.noticeTypeCast(e.type, n.type), n.title = u.stringOrUndefinedCast(e.title), n.message = u.stringCast(e.message), n.timeout = u.numberCast(e.timeout, n.timeout), n
        }

        e.default = l
    }, function (t, e, n) {
        "use strict";
        Object.defineProperty(e, "__esModule", {value: !0});
        var i = n(9);
        e.getDatetime = function () {
            var t = new Date;
            return t.getFullYear() + "-" + i.mendZero("" + (t.getMonth() + 1), 2) + "-" + i.mendZero("" + t.getDate(), 2) + " " + i.mendZero("" + t.getHours(), 2) + ":" + i.mendZero("" + t.getMinutes(), 2) + ":" + i.mendZero("" + t.getSeconds(), 2)
        }
    }, function (t, e, n) {
        "use strict";
        var i, o = this && this.__extends || (i = function (t, e) {
            return (i = Object.setPrototypeOf || {__proto__: []} instanceof Array && function (t, e) {
                t.__proto__ = e
            } || function (t, e) {
                for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
            })(t, e)
        }, function (t, e) {
            function n() {
                this.constructor = t
            }

            i(t, e), t.prototype = null === e ? Object.create(e) : (n.prototype = e.prototype, new n)
        });
        Object.defineProperty(e, "__esModule", {value: !0});
        var r, s = n(6), a = n(2), d = (r = s.default, o(u, r), Object.defineProperty(u.prototype, "element", {
            get: function () {
                return document.getElementById("" + this.elementId)
            }, enumerable: !0, configurable: !0
        }), u.prototype.present = function () {
            var t = a.createFragment(), e = a.createElement("div");
            return e.id = this.elementId, a.addClasses(e, this.app.prefix, "drag-layer"), t.appendChild(e), t
        }, u.prototype.updateZIndex = function (t) {
            var e = this.element;
            a.addClasses(e, this.app.prefix, "drag-layer-active"), a.addStyles(e, {zIndex: "" + t})
        }, u.prototype.hide = function () {
            var t = this.element;
            a.removeClasses(t, this.app.prefix, "drag-layer-active")
        }, u);

        function u(t) {
            var e = r.call(this, t) || this;
            return e.elementId = e.app.prefix + "drag-layer", e._element = null, e
        }

        e.default = d
    }, function (t, e, n) {
        "use strict";
        Object.defineProperty(e, "__esModule", {value: !0}), n(40), n(41), n(42), n(43), n(44), n(45), n(46), n(47), n(48), n(49), n(50), n(51), n(52), n(53), n(54)
    }, function (t, e, n) {
    }, function (t, e, n) {
    }, function (t, e, n) {
    }, function (t, e, n) {
    }, function (t, e, n) {
    }, function (t, e, n) {
    }, function (t, e, n) {
    }, function (t, e, n) {
    }, function (t, e, n) {
    }, function (t, e, n) {
    }, function (t, e, n) {
    }, function (t, e, n) {
    }, function (t, e, n) {
    }, function (t, e, n) {
    }, function (t, e, n) {
    }, function (t, e, n) {
        "use strict";
        Object.defineProperty(e, "__esModule", {value: !0}), n(56), n(57), n(58), n(59), n(60), n(61), n(62), n(63), n(64), n(65), n(66), n(67), n(68), n(69)
    }, function (t, e, n) {
        "use strict";
        n.r(e);
        var i = n(0), o = n.n(i), r = n(1), s = n.n(r), a = new o.a({
            id: "destroy",
            use: "destroy-usage",
            viewBox: "0 0 1024 1024",
            content: '<symbol class="icon" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" id="destroy"><defs><style type="text/css"></style></defs><path d="M933.89254819 139.71606348L884.23129279 90.08990363 511.96490363 462.39138834 140.40044113 90.82692583 90.84447403 140.34779656 462.40893653 511.91225907 90.10745181 884.2137446 139.73361166 933.875 512.03509637 561.53841892 883.59955887 933.10288141 933.15552597 883.58201068 561.59106347 512.01754819Z" p-id="4833" /></symbol>'
        });
        s.a.add(a);
        e.default = a
    }, function (t, e, n) {
        "use strict";
        n.r(e);
        var i = n(0), o = n.n(i), r = n(1), s = n.n(r), a = new o.a({
            id: "min",
            use: "min-usage",
            viewBox: "0 0 1024 1024",
            content: '<symbol class="icon" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" id="min"><defs><style type="text/css"></style></defs><path d="M65.23884 456.152041 958.760137 456.152041l0 111.695918L65.23884 567.847959 65.23884 456.152041z" p-id="4222" /></symbol>'
        });
        s.a.add(a);
        e.default = a
    }, function (t, e, n) {
        "use strict";
        n.r(e);
        var i = n(0), o = n.n(i), r = n(1), s = n.n(r), a = new o.a({
            id: "max",
            use: "max-usage",
            viewBox: "0 0 1024 1024",
            content: '<symbol class="icon" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" id="max"><defs><style type="text/css"></style></defs><path d="M75.74912227 948.24738475L75.74912227 75.75145131l872.50059037 0 0 872.49593344L75.74912227 948.24738475zM839.18786674 184.81446115L184.81213326 184.81446115l0 654.37573462 654.37573461 0L839.18786674 184.81446115z" p-id="4102" /></symbol>'
        });
        s.a.add(a);
        e.default = a
    }, function (t, e, n) {
        "use strict";
        n.r(e);
        var i = n(0), o = n.n(i), r = n(1), s = n.n(r), a = new o.a({
            id: "info",
            use: "info-usage",
            viewBox: "0 0 1024 1024",
            content: '<symbol class="icon" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" id="info"><defs><style type="text/css"></style></defs><path d="M512 1012.62222187C235.52 1012.62222187 11.37777813 788.48 11.37777813 512S235.52 11.37777813 512 11.37777813s500.62222187 224.14222187 500.62222187 500.62222187-224.14222187 500.62222187-500.62222187 500.62222187z m0-91.02222187c226.21297813 0 409.6-183.38702187 409.6-409.6S738.21297813 102.4 512 102.4 102.4 285.78702187 102.4 512s183.38702187 409.6 409.6 409.6z m0-91.02222187a45.51111147 45.51111147 0 1 1 0-91.02222293 45.51111147 45.51111147 0 0 1 0 91.02222293z m45.4656-202.9340448a45.51111147 45.51111147 0 1 1-90.9312-3.73191146 180.224 180.224 0 0 1 17.11217813-67.8115552c13.5395552-28.8768 34.3836448-51.97368853 63.80657707-65.24017814C594.69368853 469.53813333 625.77777813 427.87271147 625.77777813 398.22222187a113.77777813 113.77777813 0 0 0-227.55555626 0 45.51111147 45.51111147 0 1 1-91.02222187 0 204.8 204.8 0 0 1 409.6 0c0 68.08462187-53.97617813 140.44728853-131.93671147 175.60462293-7.62311147 3.43608853-13.83537813 10.33102187-18.81884373 20.9351104a97.62133333 97.62133333 0 0 0-7.28177813 23.5064896 81.96551147 81.96551147 0 0 0-1.29706667 9.37528853z" p-id="4713" /></symbol>'
        });
        s.a.add(a);
        e.default = a
    }, function (t, e, n) {
        "use strict";
        n.r(e);
        var i = n(0), o = n.n(i), r = n(1), s = n.n(r), a = new o.a({
            id: "restore",
            use: "restore-usage",
            viewBox: "0 0 1024 1024",
            content: '<symbol class="icon" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" id="restore"><defs><style type="text/css"></style></defs><path d="M256 256V149.333333c0-58.88 47.829333-106.666667 106.666667-106.666666h512c58.88 0 106.666667 47.829333 106.666666 106.666666v512c0 58.88-47.829333 106.666667-106.666666 106.666667h-106.666667v106.666667c0 58.88-47.829333 106.666667-106.666667 106.666666H149.333333c-58.88 0-106.666667-47.829333-106.666666-106.666666V362.666667c0-58.88 47.829333-106.666667 106.666666-106.666667h106.666667z m0 85.333333H149.333333c-11.733333 0-21.333333 9.6-21.333333 21.333334v512c0 11.733333 9.6 21.333333 21.333333 21.333333h512c11.733333 0 21.333333-9.6 21.333334-21.333333v-106.666667H362.666667c-58.88 0-106.666667-47.829333-106.666667-106.666667V341.333333z m85.333333-192v512c0 11.733333 9.6 21.333333 21.333334 21.333334h512c11.733333 0 21.333333-9.6 21.333333-21.333334V149.333333c0-11.733333-9.6-21.333333-21.333333-21.333333H362.666667c-11.733333 0-21.333333 9.6-21.333334 21.333333z" p-id="4342" /></symbol>'
        });
        s.a.add(a);
        e.default = a
    }, function (t, e, n) {
        "use strict";
        n.r(e);
        var i = n(0), o = n.n(i), r = n(1), s = n.n(r), a = new o.a({
            id: "above",
            use: "above-usage",
            viewBox: "0 0 1024 1024",
            content: '<symbol class="icon" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" id="above"><defs><style type="text/css"></style></defs><path d="M863.92416068 184.3484319H160.07583932a50.27488011 50.27488011 0 0 1 0-100.5497602h703.84832136a50.27488011 50.27488011 0 0 1 0 100.5497602z m-50.27488007 804.39808157a50.22460522 50.22460522 0 0 1-35.69516489-14.57971521L512 708.21268254l-265.95411572 265.95411572A50.27488011 50.27488011 0 0 1 160.07583932 938.47163339V335.1730722a50.27488011 50.27488011 0 0 1 50.27488007-50.27488013h603.29856122a50.27488011 50.27488011 0 0 1 50.27488007 50.27488013v603.29856119a50.27488011 50.27488011 0 0 1-50.27488007 50.27488008z m-301.64928061-402.19904078a50.22460522 50.22460522 0 0 1 35.69516487 14.57971522L763.37440051 816.80642355V385.44795228H260.62559949v431.86122007l215.67923564-215.67923564A50.27488011 50.27488011 0 0 1 512 586.54747269z" p-id="4947" /></symbol>'
        });
        s.a.add(a);
        e.default = a
    }, function (t, e, n) {
        "use strict";
        n.r(e);
        var i = n(0), o = n.n(i), r = n(1), s = n.n(r), a = new o.a({
            id: "more",
            use: "more-usage",
            viewBox: "0 0 1024 1024",
            content: '<symbol class="icon" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" id="more"><defs><style type="text/css"></style></defs><path d="M512 269.254656a97.10978845 97.10978845 0 1 0 0-194.16132267 97.10978845 97.10978845 0 0 0 0 194.16132267z" p-id="4591" /><path d="M512 512m-97.10978845 0a97.10978845 97.10978845 0 1 0 194.2195769 0 97.10978845 97.10978845 0 1 0-194.2195769 0Z" p-id="4592" /><path d="M512 948.90666667a97.10978845 97.10978845 0 1 0 0-194.21957689 97.10978845 97.10978845 0 0 0 0 194.21957689z" p-id="4593" /></symbol>'
        });
        s.a.add(a);
        e.default = a
    }, function (t, e, n) {
        "use strict";
        n.r(e);
        var i = n(0), o = n.n(i), r = n(1), s = n.n(r), a = new o.a({
            id: "icon",
            use: "icon-usage",
            viewBox: "0 0 1024 1024",
            content: '<symbol class="icon" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" id="icon"><defs><style type="text/css"></style></defs><path d="M853.333333 1024L170.666667 1024c-93.866667 0-170.666667-76.8-170.666667-170.666667L0 170.666667c0-93.866667 76.8-170.666667 170.666667-170.666667l682.66666699 0c93.866667 0 170.666667 76.8 170.66666701 170.666667l0 682.66666699C1024 947.2 947.2 1024 853.333333 1024zM170.666667 85.333333C123.733333 85.333333 85.333333 123.733333 85.333333 170.666667l0 682.66666699c0 46.933333 38.4 85.333333 85.33333301 85.33333301l682.66666699 0c46.933333 0 85.333333-38.4 85.33333301-85.33333301L938.666667 170.666667c0-46.933333-38.4-85.333333-85.33333301-85.33333301L170.666667 85.333333z" p-id="1156" /><path d="M981.333333 341.333333L42.666667 341.333333C17.066667 341.333333 0 324.266667 0 298.666667s17.066667-42.666667 42.666667-42.666667l938.66666699 0c25.6 0 42.666667 17.066667 42.66666701 42.666667S1006.933333 341.333333 981.333333 341.333333z" p-id="1157" /><path d="M170.666667 170.666667m-42.666667 0a1 1 0 1 0 85.333333 0 1 1 0 1 0-85.333333 0Z" p-id="1158" /><path d="M170.666667 234.666667C136.533333 234.666667 106.666667 204.8 106.666667 170.666667S136.533333 106.666667 170.666667 106.666667 234.666667 136.533333 234.666667 170.666667 204.8 234.666667 170.666667 234.666667zM170.666667 149.333333C157.866667 149.333333 149.333333 157.866667 149.333333 170.666667S157.866667 192 170.666667 192 192 183.466667 192 170.666667 183.466667 149.333333 170.666667 149.333333z" p-id="1159" /><path d="M298.666667 170.666667m-42.666667 0a1 1 0 1 0 85.333333 0 1 1 0 1 0-85.333333 0Z" p-id="1160" /><path d="M298.666667 234.666667C264.533333 234.666667 234.666667 204.8 234.666667 170.666667S264.533333 106.666667 298.666667 106.666667 362.666667 136.533333 362.666667 170.666667 332.8 234.666667 298.666667 234.666667zM298.666667 149.333333C285.866667 149.333333 277.333333 157.866667 277.333333 170.666667S285.866667 192 298.666667 192 320 183.466667 320 170.666667 311.466667 149.333333 298.666667 149.333333z" p-id="1161" /><path d="M426.666667 170.666667m-42.666667 0a1 1 0 1 0 85.333333 0 1 1 0 1 0-85.333333 0Z" p-id="1162" /><path d="M426.666667 234.666667C392.533333 234.666667 362.666667 204.8 362.666667 170.666667S392.533333 106.666667 426.666667 106.666667s64 29.866667 64 64S460.8 234.666667 426.666667 234.666667zM426.666667 149.333333C413.866667 149.333333 405.333333 157.866667 405.333333 170.666667S413.866667 192 426.666667 192s21.333333-8.533333 21.333333-21.333333S439.466667 149.333333 426.666667 149.333333z" p-id="1163" /></symbol>'
        });
        s.a.add(a);
        e.default = a
    }, function (t, e, n) {
        "use strict";
        n.r(e);
        var i = n(0), o = n.n(i), r = n(1), s = n.n(r), a = new o.a({
            id: "right",
            use: "right-usage",
            viewBox: "0 0 1024 1024",
            content: '<symbol class="icon" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" id="right"><defs><style type="text/css"></style></defs><path d="M737.95577083 554.36670718a42.64915156 42.64915156 0 0 1-29.93913978-12.4275674l-451.91154165-451.91154165a42.36670718 42.36670718 0 0 1 59.87827955-59.87827955l451.91154165 451.91154164a42.08426279 42.08426279 0 0 1 0 59.87827956 42.64915156 42.64915156 0 0 1-29.93913977 12.4275674z" p-id="1702" /><path d="M286.04422917 1006.27824882a42.64915156 42.64915156 0 0 1-29.93913977-12.4275674 42.08426279 42.08426279 0 0 1 0-59.87827955l451.91154165-451.91154165a42.36670718 42.36670718 0 0 1 59.87827955 59.87827956l-451.91154165 451.91154164a42.64915156 42.64915156 0 0 1-29.93913978 12.4275674z" p-id="1703" /></symbol>'
        });
        s.a.add(a);
        e.default = a
    }, function (t, e, n) {
        "use strict";
        n.r(e);
        var i = n(0), o = n.n(i), r = n(1), s = n.n(r), a = new o.a({
            id: "about",
            use: "about-usage",
            viewBox: "0 0 1024 1024",
            content: '<symbol class="icon" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" id="about"><defs><style type="text/css"></style></defs><path d="M512 1012.62222187C235.52 1012.62222187 11.37777813 788.48 11.37777813 512S235.52 11.37777813 512 11.37777813s500.62222187 224.14222187 500.62222187 500.62222187-224.14222187 500.62222187-500.62222187 500.62222187z m0-91.02222187c226.21297813 0 409.6-183.38702187 409.6-409.6S738.21297813 102.4 512 102.4 102.4 285.78702187 102.4 512s183.38702187 409.6 409.6 409.6z m0-91.02222187a45.51111147 45.51111147 0 1 1 0-91.02222293 45.51111147 45.51111147 0 0 1 0 91.02222293z m45.4656-202.9340448a45.51111147 45.51111147 0 1 1-90.9312-3.73191146 180.224 180.224 0 0 1 17.11217813-67.8115552c13.5395552-28.8768 34.3836448-51.97368853 63.80657707-65.24017814C594.69368853 469.53813333 625.77777813 427.87271147 625.77777813 398.22222187a113.77777813 113.77777813 0 0 0-227.55555626 0 45.51111147 45.51111147 0 1 1-91.02222187 0 204.8 204.8 0 0 1 409.6 0c0 68.08462187-53.97617813 140.44728853-131.93671147 175.60462293-7.62311147 3.43608853-13.83537813 10.33102187-18.81884373 20.9351104a97.62133333 97.62133333 0 0 0-7.28177813 23.5064896 81.96551147 81.96551147 0 0 0-1.29706667 9.37528853z" p-id="1690" /></symbol>'
        });
        s.a.add(a);
        e.default = a
    }, function (t, e, n) {
        "use strict";
        n.r(e);
        var i = n(0), o = n.n(i), r = n(1), s = n.n(r), a = new o.a({
            id: "success",
            use: "success-usage",
            viewBox: "0 0 1025 1024",
            content: '<symbol class="icon" viewBox="0 0 1025 1024" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" id="success"><defs><style type="text/css"></style></defs><path d="M763.61272889 290.06165333L450.04344889 598.21169778c-1.96721778 1.92853333-5.00849778 2.22435555-7.32615111 0.71566222L267.07854222 484.59320889c-0.36636445-0.24917333-0.73955555-0.49152-1.09795555-0.72362667l-0.72362667-0.45056c-0.45056-0.27989333-1.00352-0.48355555-1.48593778-0.75548444-4.99256889-2.72952889-10.64618667-4.41799111-16.74467555-4.41799111-19.41276445 0-35.12433778 15.72636445-35.12433778 35.12433778 0 7.62197333 2.49628445 14.62272 6.61162666 20.3776 1.54624 2.39502222 3.31320889 4.66716445 5.46816 6.71971555l199.25674667 188.65948445c17.25326222 13.81376 42.26616889 11.69749333 56.93326223-4.82190223l329.62446222-391.46609777c10.62456889-12.61568 9.72231111-31.2832-2.06848-42.8088889C795.47050667 278.02965333 775.85408 278.03761778 763.61272889 290.06165333z" p-id="1805" /><path d="M513.99224889-2.72839111c-281.51011555 0-509.72444445 228.21774222-509.72444444 509.72103111 0 281.52718222 228.21432889 509.72899555 509.72444444 509.72899555 281.52263111 0 509.72444445-228.20181333 509.72444444-509.72899555C1023.71669333 225.48935111 795.51488-2.72839111 513.99224889-2.72839111zM513.99224889 953.00608c-246.3232 0-446.00888889-199.67317333-446.00888889-446.01344 0-246.31637333 199.68568889-446.00547555 446.00888889-446.00547555 246.32206222 0 446.00888889 199.68910222 446.00888889 446.00547555C960.00113778 753.33176889 760.31431111 953.00608 513.99224889 953.00608z" p-id="1806" /></symbol>'
        });
        s.a.add(a);
        e.default = a
    }, function (t, e, n) {
        "use strict";
        n.r(e);
        var i = n(0), o = n.n(i), r = n(1), s = n.n(r), a = new o.a({
            id: "warning",
            use: "warning-usage",
            viewBox: "0 0 1024 1024",
            content: '<symbol class="icon" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" id="warning"><defs><style type="text/css"></style></defs><path d="M897.53493333 990.29333333L128.6304 990.29333333c-51.56266667 0-92.9856-20.37013333-113.67253333-55.89866666-20.69226667-35.52853333-18.17493333-81.93706667 6.93226666-127.2864L409.84533333 106.42346667C435.09866667 60.74346667 472.65066667 34.56 512.82666667 34.56c40.18133333 0 77.696 26.15146667 103.01973333 71.79093333l388.36266667 700.8288c25.1008 45.3504 27.69706667 91.7248 6.9664 127.24906667C990.5216 969.95626667 949.06453333 990.29333333 897.53493333 990.29333333L897.53493333 990.29333333zM512.86613333 108.11306667c-12.7744 0-27.40586667 12.7616-39.2672 34.1312L85.64373333 843.00053333c-12.15573333 21.99253333-15.00053333 41.7472-7.73973333 54.20906667 7.2672 12.43093333 25.76533333 19.57013333 50.72746667 19.57013333l768.90453333 0c25.0016 0 43.46666667-7.09973333 50.72853333-19.57013333 7.2224-12.42986667 4.416-32.17706667-7.7728-54.17066667L552.1344 142.24426667C540.3072 120.87466667 525.60106667 108.11306667 512.86613333 108.11306667L512.86613333 108.11306667zM513.0848 658.4896c-20.14186667 0-36.49066667-16.4416-36.49066667-36.7776L476.59413333 290.71466667c0-20.30186667 16.3488-36.77866667 36.49066667-36.77866667 20.14506667 0 36.49386667 16.4768 36.49386667 36.77866667l0 330.99626666C549.5776 642.048 533.2288 658.4896 513.0848 658.4896zM512.8224 838.2624c31.40373333 0 56.86293333-25.7024 56.86293333-57.40693333s-25.45813333-57.40693333-56.86293333-57.40693334-56.86186667 25.7024-56.86186667 57.40693334S481.41866667 838.2624 512.8224 838.2624z" p-id="2160" /></symbol>'
        });
        s.a.add(a);
        e.default = a
    }, function (t, e, n) {
        "use strict";
        n.r(e);
        var i = n(0), o = n.n(i), r = n(1), s = n.n(r), a = new o.a({
            id: "error",
            use: "error-usage",
            viewBox: "0 0 1024 1024",
            content: '<symbol class="icon" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" id="error"><defs><style type="text/css"></style></defs><path d="M422.4 750.933333 422.4 750.933333 422.4 750.933333zM512 0C230.4 0 0 230.4 0 512s230.4 512 512 512 512-230.4 512-512S793.6 0 512 0zM512 960C264.533333 960 64 759.466667 64 512S264.533333 64 512 64 960 264.533333 960 512 759.466667 960 512 960zM693.0176 282.702507l51.28832 51.28832-410.30656 410.30656-51.28832-51.28832 410.30656-410.30656ZM282.702507 330.9824l51.28832-51.28832 410.30656 410.30656-51.28832 51.28832-410.30656-410.30656Z" p-id="1921" /></symbol>'
        });
        s.a.add(a);
        e.default = a
    }, function (t, e, n) {
        "use strict";
        n.r(e);
        var i = n(0), o = n.n(i), r = n(1), s = n.n(r), a = new o.a({
            id: "refresh",
            use: "refresh-usage",
            viewBox: "0 0 1024 1024",
            content: '<symbol class="icon" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" id="refresh"><defs><style type="text/css"></style></defs><path d="M1021.72444445 402.77333333V147.91111111l-83.12149334 83.12149334a509.39676445 509.39676445 0 0 0-425.07377778-228.79345778C232.01564445 2.23914667 3.76832 230.45006222 3.76832 512S232.01564445 1021.76085333 513.52917333 1021.76085333a509.79726222 509.79726222 0 0 0 472.44174222-317.99523555 43.65425778 43.65425778 0 1 0-80.93696-32.87722667 422.52515555 422.52515555 0 0 1-391.50478222 263.49112889C280.25742222 934.37952 91.14965333 745.27175111 91.14965333 512S280.25742222 89.62048 513.52917333 89.62048c150.69639111 0 286.64718222 79.73546667 361.83153778 204.61795555L766.86222222 402.77333333h254.86222223z" p-id="1747" /></symbol>'
        });
        s.a.add(a);
        e.default = a
    }], e = {}, f.m = d, f.c = e, f.d = function (t, e, n) {
        f.o(t, e) || Object.defineProperty(t, e, {enumerable: !0, get: n})
    }, f.r = function (t) {
        "undefined" != typeof Symbol && Symbol.toStringTag && Object.defineProperty(t, Symbol.toStringTag, {value: "Module"}), Object.defineProperty(t, "__esModule", {value: !0})
    }, f.t = function (e, t) {
        if (1 & t && (e = f(e)), 8 & t) return e;
        if (4 & t && "object" == typeof e && e && e.__esModule) return e;
        var n = Object.create(null);
        if (f.r(n), Object.defineProperty(n, "default", {
            enumerable: !0,
            value: e
        }), 2 & t && "string" != typeof e) for (var i in e) f.d(n, i, function (t) {
            return e[t]
        }.bind(null, i));
        return n
    }, f.n = function (t) {
        var e = t && t.__esModule ? function () {
            return t.default
        } : function () {
            return t
        };
        return f.d(e, "a", e), e
    }, f.o = function (t, e) {
        return Object.prototype.hasOwnProperty.call(t, e)
    }, f.p = "", f(f.s = 15)).default;

    function f(t) {
        if (e[t]) return e[t].exports;
        var n = e[t] = {i: t, l: !1, exports: {}};
        return d[t].call(n.exports, n, n.exports, f), n.l = !0, n.exports
    }

    var d, e
});
