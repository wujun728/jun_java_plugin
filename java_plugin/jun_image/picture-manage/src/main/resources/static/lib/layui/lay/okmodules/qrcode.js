layui.define("jquery", function (t) {
  var e = layui.jquery;
  "use strict";
  var r;
  
  function i(t) {
    this.mode = n.MODE_8BIT_BYTE;
    this.data = t;
    this.parsedData = [];
    for (var e = 0, r = this.data.length; e < r; e++) {
      var i = [];
      var a = this.data.charCodeAt(e);
      if (a > 65536) {
        i[0] = 240 | (a & 1835008) >>> 18;
        i[1] = 128 | (a & 258048) >>> 12;
        i[2] = 128 | (a & 4032) >>> 6;
        i[3] = 128 | a & 63
      } else if (a > 2048) {
        i[0] = 224 | (a & 61440) >>> 12;
        i[1] = 128 | (a & 4032) >>> 6;
        i[2] = 128 | a & 63
      } else if (a > 128) {
        i[0] = 192 | (a & 1984) >>> 6;
        i[1] = 128 | a & 63
      } else {
        i[0] = a
      }
      this.parsedData.push(i)
    }
    this.parsedData = Array.prototype.concat.apply([], this.parsedData);
    if (this.parsedData.length != this.data.length) {
      this.parsedData.unshift(191);
      this.parsedData.unshift(187);
      this.parsedData.unshift(239)
    }
  }
  
  i.prototype = {
    getLength: function (t) {
      return this.parsedData.length
    }, write: function (t) {
      for (var e = 0, r = this.parsedData.length; e < r; e++) {
        t.put(this.parsedData[e], 8)
      }
    }
  };
  
  function h(t, e) {
    this.typeNumber = t;
    this.errorCorrectLevel = e;
    this.modules = null;
    this.moduleCount = 0;
    this.dataCache = null;
    this.dataList = []
  }
  
  h.prototype = {
    addData: function (t) {
      var e = new i(t);
      this.dataList.push(e);
      this.dataCache = null
    }, isDark: function (t, e) {
      if (t < 0 || this.moduleCount <= t || e < 0 || this.moduleCount <= e) {
        throw new Error(t + "," + e)
      }
      return this.modules[t][e]
    }, getModuleCount: function () {
      return this.moduleCount
    }, make: function () {
      this.makeImpl(false, this.getBestMaskPattern())
    }, makeImpl: function (t, e) {
      this.moduleCount = this.typeNumber * 4 + 17;
      this.modules = new Array(this.moduleCount);
      for (var r = 0; r < this.moduleCount; r++) {
        this.modules[r] = new Array(this.moduleCount);
        for (var i = 0; i < this.moduleCount; i++) {
          this.modules[r][i] = null
        }
      }
      this.setupPositionProbePattern(0, 0);
      this.setupPositionProbePattern(this.moduleCount - 7, 0);
      this.setupPositionProbePattern(0, this.moduleCount - 7);
      this.setupPositionAdjustPattern();
      this.setupTimingPattern();
      this.setupTypeInfo(t, e);
      if (this.typeNumber >= 7) {
        this.setupTypeNumber(t)
      }
      if (this.dataCache == null) {
        this.dataCache = h.createData(this.typeNumber, this.errorCorrectLevel, this.dataList)
      }
      this.mapData(this.dataCache, e)
    }, setupPositionProbePattern: function (t, e) {
      for (var r = -1; r <= 7; r++) {
        if (t + r <= -1 || this.moduleCount <= t + r) continue;
        for (var i = -1; i <= 7; i++) {
          if (e + i <= -1 || this.moduleCount <= e + i) continue;
          if (0 <= r && r <= 6 && (i == 0 || i == 6) || 0 <= i && i <= 6 && (r == 0 || r == 6) || 2 <= r && r <= 4 && 2 <= i && i <= 4) {
            this.modules[t + r][e + i] = true
          } else {
            this.modules[t + r][e + i] = false
          }
        }
      }
    }, getBestMaskPattern: function () {
      var t = 0;
      var e = 0;
      for (var r = 0; r < 8; r++) {
        this.makeImpl(true, r);
        var i = _.getLostPoint(this);
        if (r == 0 || t > i) {
          t = i;
          e = r
        }
      }
      return e
    }, createMovieClip: function (t, e, r) {
      var i = t.createEmptyMovieClip(e, r);
      var a = 1;
      this.make();
      for (var n = 0; n < this.modules.length; n++) {
        var o = n * a;
        for (var s = 0; s < this.modules[n].length; s++) {
          var h = s * a;
          var l = this.modules[n][s];
          if (l) {
            i.beginFill(0, 100);
            i.moveTo(h, o);
            i.lineTo(h + a, o);
            i.lineTo(h + a, o + a);
            i.lineTo(h, o + a);
            i.endFill()
          }
        }
      }
      return i
    }, setupTimingPattern: function () {
      for (var t = 8; t < this.moduleCount - 8; t++) {
        if (this.modules[t][6] != null) {
          continue
        }
        this.modules[t][6] = t % 2 == 0
      }
      for (var e = 8; e < this.moduleCount - 8; e++) {
        if (this.modules[6][e] != null) {
          continue
        }
        this.modules[6][e] = e % 2 == 0
      }
    }, setupPositionAdjustPattern: function () {
      var t = _.getPatternPosition(this.typeNumber);
      for (var e = 0; e < t.length; e++) {
        for (var r = 0; r < t.length; r++) {
          var i = t[e];
          var a = t[r];
          if (this.modules[i][a] != null) {
            continue
          }
          for (var n = -2; n <= 2; n++) {
            for (var o = -2; o <= 2; o++) {
              if (n == -2 || n == 2 || o == -2 || o == 2 || n == 0 && o == 0) {
                this.modules[i + n][a + o] = true
              } else {
                this.modules[i + n][a + o] = false
              }
            }
          }
        }
      }
    }, setupTypeNumber: function (t) {
      var e = _.getBCHTypeNumber(this.typeNumber);
      for (var r = 0; r < 18; r++) {
        var i = !t && (e >> r & 1) == 1;
        this.modules[Math.floor(r / 3)][r % 3 + this.moduleCount - 8 - 3] = i
      }
      for (var r = 0; r < 18; r++) {
        var i = !t && (e >> r & 1) == 1;
        this.modules[r % 3 + this.moduleCount - 8 - 3][Math.floor(r / 3)] = i
      }
    }, setupTypeInfo: function (t, e) {
      var r = this.errorCorrectLevel << 3 | e;
      var i = _.getBCHTypeInfo(r);
      for (var a = 0; a < 15; a++) {
        var n = !t && (i >> a & 1) == 1;
        if (a < 6) {
          this.modules[a][8] = n
        } else if (a < 8) {
          this.modules[a + 1][8] = n
        } else {
          this.modules[this.moduleCount - 15 + a][8] = n
        }
      }
      for (var a = 0; a < 15; a++) {
        var n = !t && (i >> a & 1) == 1;
        if (a < 8) {
          this.modules[8][this.moduleCount - a - 1] = n
        } else if (a < 9) {
          this.modules[8][15 - a - 1 + 1] = n
        } else {
          this.modules[8][15 - a - 1] = n
        }
      }
      this.modules[this.moduleCount - 8][8] = !t
    }, mapData: function (t, e) {
      var r = -1;
      var i = this.moduleCount - 1;
      var a = 7;
      var n = 0;
      for (var o = this.moduleCount - 1; o > 0; o -= 2) {
        if (o == 6) o--;
        while (true) {
          for (var s = 0; s < 2; s++) {
            if (this.modules[i][o - s] == null) {
              var h = false;
              if (n < t.length) {
                h = (t[n] >>> a & 1) == 1
              }
              var l = _.getMask(e, i, o - s);
              if (l) {
                h = !h
              }
              this.modules[i][o - s] = h;
              a--;
              if (a == -1) {
                n++;
                a = 7
              }
            }
          }
          i += r;
          if (i < 0 || this.moduleCount <= i) {
            i -= r;
            r = -r;
            break
          }
        }
      }
    }
  };
  h.PAD0 = 236;
  h.PAD1 = 17;
  h.createData = function (t, e, r) {
    var i = u.getRSBlocks(t, e);
    var a = new f;
    for (var n = 0; n < r.length; n++) {
      var o = r[n];
      a.put(o.mode, 4);
      a.put(o.getLength(), _.getLengthInBits(o.mode, t));
      o.write(a)
    }
    var s = 0;
    for (var n = 0; n < i.length; n++) {
      s += i[n].dataCount
    }
    if (a.getLengthInBits() > s * 8) {
      throw new Error("code length overflow. (" + a.getLengthInBits() + ">" + s * 8 + ")")
    }
    if (a.getLengthInBits() + 4 <= s * 8) {
      a.put(0, 4)
    }
    while (a.getLengthInBits() % 8 != 0) {
      a.putBit(false)
    }
    while (true) {
      if (a.getLengthInBits() >= s * 8) {
        break
      }
      a.put(h.PAD0, 8);
      if (a.getLengthInBits() >= s * 8) {
        break
      }
      a.put(h.PAD1, 8)
    }
    return h.createBytes(a, i)
  };
  h.createBytes = function (t, e) {
    var r = 0;
    var i = 0;
    var a = 0;
    var n = new Array(e.length);
    var o = new Array(e.length);
    for (var s = 0; s < e.length; s++) {
      var h = e[s].dataCount;
      var l = e[s].totalCount - h;
      i = Math.max(i, h);
      a = Math.max(a, l);
      n[s] = new Array(h);
      for (var u = 0; u < n[s].length; u++) {
        n[s][u] = 255 & t.buffer[u + r]
      }
      r += h;
      var f = _.getErrorCorrectPolynomial(l);
      var g = new w(n[s], f.getLength() - 1);
      var d = g.mod(f);
      o[s] = new Array(f.getLength() - 1);
      for (var u = 0; u < o[s].length; u++) {
        var v = u + d.getLength() - o[s].length;
        o[s][u] = v >= 0 ? d.get(v) : 0
      }
    }
    var c = 0;
    for (var u = 0; u < e.length; u++) {
      c += e[u].totalCount
    }
    var p = new Array(c);
    var m = 0;
    for (var u = 0; u < i; u++) {
      for (var s = 0; s < e.length; s++) {
        if (u < n[s].length) {
          p[m++] = n[s][u]
        }
      }
    }
    for (var u = 0; u < a; u++) {
      for (var s = 0; s < e.length; s++) {
        if (u < o[s].length) {
          p[m++] = o[s][u]
        }
      }
    }
    return p
  };
  var n = {MODE_NUMBER: 1 << 0, MODE_ALPHA_NUM: 1 << 1, MODE_8BIT_BYTE: 1 << 2, MODE_KANJI: 1 << 3};
  var s = {L: 1, M: 0, Q: 3, H: 2};
  var a = {
    PATTERN000: 0,
    PATTERN001: 1,
    PATTERN010: 2,
    PATTERN011: 3,
    PATTERN100: 4,
    PATTERN101: 5,
    PATTERN110: 6,
    PATTERN111: 7
  };
  var _ = {
    PATTERN_POSITION_TABLE: [[], [6, 18], [6, 22], [6, 26], [6, 30], [6, 34], [6, 22, 38], [6, 24, 42], [6, 26, 46], [6, 28, 50], [6, 30, 54], [6, 32, 58], [6, 34, 62], [6, 26, 46, 66], [6, 26, 48, 70], [6, 26, 50, 74], [6, 30, 54, 78], [6, 30, 56, 82], [6, 30, 58, 86], [6, 34, 62, 90], [6, 28, 50, 72, 94], [6, 26, 50, 74, 98], [6, 30, 54, 78, 102], [6, 28, 54, 80, 106], [6, 32, 58, 84, 110], [6, 30, 58, 86, 114], [6, 34, 62, 90, 118], [6, 26, 50, 74, 98, 122], [6, 30, 54, 78, 102, 126], [6, 26, 52, 78, 104, 130], [6, 30, 56, 82, 108, 134], [6, 34, 60, 86, 112, 138], [6, 30, 58, 86, 114, 142], [6, 34, 62, 90, 118, 146], [6, 30, 54, 78, 102, 126, 150], [6, 24, 50, 76, 102, 128, 154], [6, 28, 54, 80, 106, 132, 158], [6, 32, 58, 84, 110, 136, 162], [6, 26, 54, 82, 110, 138, 166], [6, 30, 58, 86, 114, 142, 170]],
    G15: 1 << 10 | 1 << 8 | 1 << 5 | 1 << 4 | 1 << 2 | 1 << 1 | 1 << 0,
    G18: 1 << 12 | 1 << 11 | 1 << 10 | 1 << 9 | 1 << 8 | 1 << 5 | 1 << 2 | 1 << 0,
    G15_MASK: 1 << 14 | 1 << 12 | 1 << 10 | 1 << 4 | 1 << 1,
    getBCHTypeInfo: function (t) {
      var e = t << 10;
      while (_.getBCHDigit(e) - _.getBCHDigit(_.G15) >= 0) {
        e ^= _.G15 << _.getBCHDigit(e) - _.getBCHDigit(_.G15)
      }
      return (t << 10 | e) ^ _.G15_MASK
    },
    getBCHTypeNumber: function (t) {
      var e = t << 12;
      while (_.getBCHDigit(e) - _.getBCHDigit(_.G18) >= 0) {
        e ^= _.G18 << _.getBCHDigit(e) - _.getBCHDigit(_.G18)
      }
      return t << 12 | e
    },
    getBCHDigit: function (t) {
      var e = 0;
      while (t != 0) {
        e++;
        t >>>= 1
      }
      return e
    },
    getPatternPosition: function (t) {
      return _.PATTERN_POSITION_TABLE[t - 1]
    },
    getMask: function (t, e, r) {
      switch (t) {
        case a.PATTERN000:
          return (e + r) % 2 == 0;
        case a.PATTERN001:
          return e % 2 == 0;
        case a.PATTERN010:
          return r % 3 == 0;
        case a.PATTERN011:
          return (e + r) % 3 == 0;
        case a.PATTERN100:
          return (Math.floor(e / 2) + Math.floor(r / 3)) % 2 == 0;
        case a.PATTERN101:
          return e * r % 2 + e * r % 3 == 0;
        case a.PATTERN110:
          return (e * r % 2 + e * r % 3) % 2 == 0;
        case a.PATTERN111:
          return (e * r % 3 + (e + r) % 2) % 2 == 0;
        default:
          throw new Error("bad maskPattern:" + t)
      }
    },
    getErrorCorrectPolynomial: function (t) {
      var e = new w([1], 0);
      for (var r = 0; r < t; r++) {
        e = e.multiply(new w([1, o.gexp(r)], 0))
      }
      return e
    },
    getLengthInBits: function (t, e) {
      if (1 <= e && e < 10) {
        switch (t) {
          case n.MODE_NUMBER:
            return 10;
          case n.MODE_ALPHA_NUM:
            return 9;
          case n.MODE_8BIT_BYTE:
            return 8;
          case n.MODE_KANJI:
            return 8;
          default:
            throw new Error("mode:" + t)
        }
      } else if (e < 27) {
        switch (t) {
          case n.MODE_NUMBER:
            return 12;
          case n.MODE_ALPHA_NUM:
            return 11;
          case n.MODE_8BIT_BYTE:
            return 16;
          case n.MODE_KANJI:
            return 10;
          default:
            throw new Error("mode:" + t)
        }
      } else if (e < 41) {
        switch (t) {
          case n.MODE_NUMBER:
            return 14;
          case n.MODE_ALPHA_NUM:
            return 13;
          case n.MODE_8BIT_BYTE:
            return 16;
          case n.MODE_KANJI:
            return 12;
          default:
            throw new Error("mode:" + t)
        }
      } else {
        throw new Error("type:" + e)
      }
    },
    getLostPoint: function (t) {
      var e = t.getModuleCount();
      var r = 0;
      for (var i = 0; i < e; i++) {
        for (var a = 0; a < e; a++) {
          var n = 0;
          var o = t.isDark(i, a);
          for (var s = -1; s <= 1; s++) {
            if (i + s < 0 || e <= i + s) {
              continue
            }
            for (var h = -1; h <= 1; h++) {
              if (a + h < 0 || e <= a + h) {
                continue
              }
              if (s == 0 && h == 0) {
                continue
              }
              if (o == t.isDark(i + s, a + h)) {
                n++
              }
            }
          }
          if (n > 5) {
            r += 3 + n - 5
          }
        }
      }
      for (var i = 0; i < e - 1; i++) {
        for (var a = 0; a < e - 1; a++) {
          var l = 0;
          if (t.isDark(i, a)) l++;
          if (t.isDark(i + 1, a)) l++;
          if (t.isDark(i, a + 1)) l++;
          if (t.isDark(i + 1, a + 1)) l++;
          if (l == 0 || l == 4) {
            r += 3
          }
        }
      }
      for (var i = 0; i < e; i++) {
        for (var a = 0; a < e - 6; a++) {
          if (t.isDark(i, a) && !t.isDark(i, a + 1) && t.isDark(i, a + 2) && t.isDark(i, a + 3) && t.isDark(i, a + 4) && !t.isDark(i, a + 5) && t.isDark(i, a + 6)) {
            r += 40
          }
        }
      }
      for (var a = 0; a < e; a++) {
        for (var i = 0; i < e - 6; i++) {
          if (t.isDark(i, a) && !t.isDark(i + 1, a) && t.isDark(i + 2, a) && t.isDark(i + 3, a) && t.isDark(i + 4, a) && !t.isDark(i + 5, a) && t.isDark(i + 6, a)) {
            r += 40
          }
        }
      }
      var u = 0;
      for (var a = 0; a < e; a++) {
        for (var i = 0; i < e; i++) {
          if (t.isDark(i, a)) {
            u++
          }
        }
      }
      var f = Math.abs(100 * u / e / e - 50) / 5;
      r += f * 10;
      return r
    }
  };
  var o = {
    glog: function (t) {
      if (t < 1) {
        throw new Error("glog(" + t + ")")
      }
      return o.LOG_TABLE[t]
    }, gexp: function (t) {
      while (t < 0) {
        t += 255
      }
      while (t >= 256) {
        t -= 255
      }
      return o.EXP_TABLE[t]
    }, EXP_TABLE: new Array(256), LOG_TABLE: new Array(256)
  };
  for (var l = 0; l < 8; l++) {
    o.EXP_TABLE[l] = 1 << l
  }
  for (var l = 8; l < 256; l++) {
    o.EXP_TABLE[l] = o.EXP_TABLE[l - 4] ^ o.EXP_TABLE[l - 5] ^ o.EXP_TABLE[l - 6] ^ o.EXP_TABLE[l - 8]
  }
  for (var l = 0; l < 255; l++) {
    o.LOG_TABLE[o.EXP_TABLE[l]] = l
  }
  
  function w(t, e) {
    if (t.length == undefined) {
      throw new Error(t.length + "/" + e)
    }
    var r = 0;
    while (r < t.length && t[r] == 0) {
      r++
    }
    this.num = new Array(t.length - r + e);
    for (var i = 0; i < t.length - r; i++) {
      this.num[i] = t[i + r]
    }
  }
  
  w.prototype = {
    get: function (t) {
      return this.num[t]
    }, getLength: function () {
      return this.num.length
    }, multiply: function (t) {
      var e = new Array(this.getLength() + t.getLength() - 1);
      for (var r = 0; r < this.getLength(); r++) {
        for (var i = 0; i < t.getLength(); i++) {
          e[r + i] ^= o.gexp(o.glog(this.get(r)) + o.glog(t.get(i)))
        }
      }
      return new w(e, 0)
    }, mod: function (t) {
      if (this.getLength() - t.getLength() < 0) {
        return this
      }
      var e = o.glog(this.get(0)) - o.glog(t.get(0));
      var r = new Array(this.getLength());
      for (var i = 0; i < this.getLength(); i++) {
        r[i] = this.get(i)
      }
      for (var i = 0; i < t.getLength(); i++) {
        r[i] ^= o.gexp(o.glog(t.get(i)) + e)
      }
      return new w(r, 0).mod(t)
    }
  };
  
  function u(t, e) {
    this.totalCount = t;
    this.dataCount = e
  }
  
  u.RS_BLOCK_TABLE = [[1, 26, 19], [1, 26, 16], [1, 26, 13], [1, 26, 9], [1, 44, 34], [1, 44, 28], [1, 44, 22], [1, 44, 16], [1, 70, 55], [1, 70, 44], [2, 35, 17], [2, 35, 13], [1, 100, 80], [2, 50, 32], [2, 50, 24], [4, 25, 9], [1, 134, 108], [2, 67, 43], [2, 33, 15, 2, 34, 16], [2, 33, 11, 2, 34, 12], [2, 86, 68], [4, 43, 27], [4, 43, 19], [4, 43, 15], [2, 98, 78], [4, 49, 31], [2, 32, 14, 4, 33, 15], [4, 39, 13, 1, 40, 14], [2, 121, 97], [2, 60, 38, 2, 61, 39], [4, 40, 18, 2, 41, 19], [4, 40, 14, 2, 41, 15], [2, 146, 116], [3, 58, 36, 2, 59, 37], [4, 36, 16, 4, 37, 17], [4, 36, 12, 4, 37, 13], [2, 86, 68, 2, 87, 69], [4, 69, 43, 1, 70, 44], [6, 43, 19, 2, 44, 20], [6, 43, 15, 2, 44, 16], [4, 101, 81], [1, 80, 50, 4, 81, 51], [4, 50, 22, 4, 51, 23], [3, 36, 12, 8, 37, 13], [2, 116, 92, 2, 117, 93], [6, 58, 36, 2, 59, 37], [4, 46, 20, 6, 47, 21], [7, 42, 14, 4, 43, 15], [4, 133, 107], [8, 59, 37, 1, 60, 38], [8, 44, 20, 4, 45, 21], [12, 33, 11, 4, 34, 12], [3, 145, 115, 1, 146, 116], [4, 64, 40, 5, 65, 41], [11, 36, 16, 5, 37, 17], [11, 36, 12, 5, 37, 13], [5, 109, 87, 1, 110, 88], [5, 65, 41, 5, 66, 42], [5, 54, 24, 7, 55, 25], [11, 36, 12], [5, 122, 98, 1, 123, 99], [7, 73, 45, 3, 74, 46], [15, 43, 19, 2, 44, 20], [3, 45, 15, 13, 46, 16], [1, 135, 107, 5, 136, 108], [10, 74, 46, 1, 75, 47], [1, 50, 22, 15, 51, 23], [2, 42, 14, 17, 43, 15], [5, 150, 120, 1, 151, 121], [9, 69, 43, 4, 70, 44], [17, 50, 22, 1, 51, 23], [2, 42, 14, 19, 43, 15], [3, 141, 113, 4, 142, 114], [3, 70, 44, 11, 71, 45], [17, 47, 21, 4, 48, 22], [9, 39, 13, 16, 40, 14], [3, 135, 107, 5, 136, 108], [3, 67, 41, 13, 68, 42], [15, 54, 24, 5, 55, 25], [15, 43, 15, 10, 44, 16], [4, 144, 116, 4, 145, 117], [17, 68, 42], [17, 50, 22, 6, 51, 23], [19, 46, 16, 6, 47, 17], [2, 139, 111, 7, 140, 112], [17, 74, 46], [7, 54, 24, 16, 55, 25], [34, 37, 13], [4, 151, 121, 5, 152, 122], [4, 75, 47, 14, 76, 48], [11, 54, 24, 14, 55, 25], [16, 45, 15, 14, 46, 16], [6, 147, 117, 4, 148, 118], [6, 73, 45, 14, 74, 46], [11, 54, 24, 16, 55, 25], [30, 46, 16, 2, 47, 17], [8, 132, 106, 4, 133, 107], [8, 75, 47, 13, 76, 48], [7, 54, 24, 22, 55, 25], [22, 45, 15, 13, 46, 16], [10, 142, 114, 2, 143, 115], [19, 74, 46, 4, 75, 47], [28, 50, 22, 6, 51, 23], [33, 46, 16, 4, 47, 17], [8, 152, 122, 4, 153, 123], [22, 73, 45, 3, 74, 46], [8, 53, 23, 26, 54, 24], [12, 45, 15, 28, 46, 16], [3, 147, 117, 10, 148, 118], [3, 73, 45, 23, 74, 46], [4, 54, 24, 31, 55, 25], [11, 45, 15, 31, 46, 16], [7, 146, 116, 7, 147, 117], [21, 73, 45, 7, 74, 46], [1, 53, 23, 37, 54, 24], [19, 45, 15, 26, 46, 16], [5, 145, 115, 10, 146, 116], [19, 75, 47, 10, 76, 48], [15, 54, 24, 25, 55, 25], [23, 45, 15, 25, 46, 16], [13, 145, 115, 3, 146, 116], [2, 74, 46, 29, 75, 47], [42, 54, 24, 1, 55, 25], [23, 45, 15, 28, 46, 16], [17, 145, 115], [10, 74, 46, 23, 75, 47], [10, 54, 24, 35, 55, 25], [19, 45, 15, 35, 46, 16], [17, 145, 115, 1, 146, 116], [14, 74, 46, 21, 75, 47], [29, 54, 24, 19, 55, 25], [11, 45, 15, 46, 46, 16], [13, 145, 115, 6, 146, 116], [14, 74, 46, 23, 75, 47], [44, 54, 24, 7, 55, 25], [59, 46, 16, 1, 47, 17], [12, 151, 121, 7, 152, 122], [12, 75, 47, 26, 76, 48], [39, 54, 24, 14, 55, 25], [22, 45, 15, 41, 46, 16], [6, 151, 121, 14, 152, 122], [6, 75, 47, 34, 76, 48], [46, 54, 24, 10, 55, 25], [2, 45, 15, 64, 46, 16], [17, 152, 122, 4, 153, 123], [29, 74, 46, 14, 75, 47], [49, 54, 24, 10, 55, 25], [24, 45, 15, 46, 46, 16], [4, 152, 122, 18, 153, 123], [13, 74, 46, 32, 75, 47], [48, 54, 24, 14, 55, 25], [42, 45, 15, 32, 46, 16], [20, 147, 117, 4, 148, 118], [40, 75, 47, 7, 76, 48], [43, 54, 24, 22, 55, 25], [10, 45, 15, 67, 46, 16], [19, 148, 118, 6, 149, 119], [18, 75, 47, 31, 76, 48], [34, 54, 24, 34, 55, 25], [20, 45, 15, 61, 46, 16]];
  u.getRSBlocks = function (t, e) {
    var r = u.getRsBlockTable(t, e);
    if (r == undefined) {
      throw new Error("bad rs block @ typeNumber:" + t + "/errorCorrectLevel:" + e)
    }
    var i = r.length / 3;
    var a = [];
    for (var n = 0; n < i; n++) {
      var o = r[n * 3 + 0];
      var s = r[n * 3 + 1];
      var h = r[n * 3 + 2];
      for (var l = 0; l < o; l++) {
        a.push(new u(s, h))
      }
    }
    return a
  };
  u.getRsBlockTable = function (t, e) {
    switch (e) {
      case s.L:
        return u.RS_BLOCK_TABLE[(t - 1) * 4 + 0];
      case s.M:
        return u.RS_BLOCK_TABLE[(t - 1) * 4 + 1];
      case s.Q:
        return u.RS_BLOCK_TABLE[(t - 1) * 4 + 2];
      case s.H:
        return u.RS_BLOCK_TABLE[(t - 1) * 4 + 3];
      default:
        return undefined
    }
  };
  
  function f() {
    this.buffer = [];
    this.length = 0
  }
  
  f.prototype = {
    get: function (t) {
      var e = Math.floor(t / 8);
      return (this.buffer[e] >>> 7 - t % 8 & 1) == 1
    }, put: function (t, e) {
      for (var r = 0; r < e; r++) {
        this.putBit((t >>> e - r - 1 & 1) == 1)
      }
    }, getLengthInBits: function () {
      return this.length
    }, putBit: function (t) {
      var e = Math.floor(this.length / 8);
      if (this.buffer.length <= e) {
        this.buffer.push(0)
      }
      if (t) {
        this.buffer[e] |= 128 >>> this.length % 8
      }
      this.length++
    }
  };
  var g = [[17, 14, 11, 7], [32, 26, 20, 14], [53, 42, 32, 24], [78, 62, 46, 34], [106, 84, 60, 44], [134, 106, 74, 58], [154, 122, 86, 64], [192, 152, 108, 84], [230, 180, 130, 98], [271, 213, 151, 119], [321, 251, 177, 137], [367, 287, 203, 155], [425, 331, 241, 177], [458, 362, 258, 194], [520, 412, 292, 220], [586, 450, 322, 250], [644, 504, 364, 280], [718, 560, 394, 310], [792, 624, 442, 338], [858, 666, 482, 382], [929, 711, 509, 403], [1003, 779, 565, 439], [1091, 857, 611, 461], [1171, 911, 661, 511], [1273, 997, 715, 535], [1367, 1059, 751, 593], [1465, 1125, 805, 625], [1528, 1190, 868, 658], [1628, 1264, 908, 698], [1732, 1370, 982, 742], [1840, 1452, 1030, 790], [1952, 1538, 1112, 842], [2068, 1628, 1168, 898], [2188, 1722, 1228, 958], [2303, 1809, 1283, 983], [2431, 1911, 1351, 1051], [2563, 1989, 1423, 1093], [2699, 2099, 1499, 1139], [2809, 2213, 1579, 1219], [2953, 2331, 1663, 1273]];
  
  function d() {
    return typeof CanvasRenderingContext2D != "undefined"
  }
  
  function v() {
    var t = false;
    var e = navigator.userAgent;
    if (/android/i.test(e)) {
      t = true;
      var r = e.toString().match(/android ([0-9]\.[0-9])/i);
      if (r && r[1]) {
        t = parseFloat(r[1])
      }
    }
    return t
  }
  
  var c = function () {
    var t = function (t, e) {
      this._el = t;
      this._htOption = e
    };
    t.prototype.draw = function (t) {
      var e = this._htOption;
      var r = this._el;
      var i = t.getModuleCount();
      var a = Math.floor(e.width / i);
      var n = Math.floor(e.height / i);
      this.clear();
      
      function o(t, e) {
        var r = document.createElementNS("http://www.w3.org/2000/svg", t);
        for (var i in e) if (e.hasOwnProperty(i)) r.setAttribute(i, e[i]);
        return r
      }
      
      var s = o("svg", {
        viewBox: "0 0 " + String(i) + " " + String(i),
        width: "100%",
        height: "100%",
        fill: e.colorLight
      });
      s.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xlink", "http://www.w3.org/1999/xlink");
      r.appendChild(s);
      s.appendChild(o("rect", {fill: e.colorLight, width: "100%", height: "100%"}));
      s.appendChild(o("rect", {fill: e.colorDark, width: "1", height: "1", id: "template"}));
      for (var h = 0; h < i; h++) {
        for (var l = 0; l < i; l++) {
          if (t.isDark(h, l)) {
            var u = o("use", {x: String(l), y: String(h)});
            u.setAttributeNS("http://www.w3.org/1999/xlink", "href", "#template");
            s.appendChild(u)
          }
        }
      }
    };
    t.prototype.clear = function () {
      while (this._el.hasChildNodes()) this._el.removeChild(this._el.lastChild)
    };
    return t
  }();
  var p = document.documentElement.tagName.toLowerCase() === "svg";
  var m = p ? c : !d() ? function () {
    var t = function (t, e) {
      this._el = t;
      this._htOption = e
    };
    t.prototype.draw = function (t) {
      var e = this._htOption;
      var r = this._el;
      var i = t.getModuleCount();
      var a = Math.floor(e.width / i);
      var n = Math.floor(e.height / i);
      var o = ['<table style="border:0;border-collapse:collapse;">'];
      for (var s = 0; s < i; s++) {
        o.push("<tr>");
        for (var h = 0; h < i; h++) {
          o.push('<td style="border:0;border-collapse:collapse;padding:0;margin:0;width:' + a + "px;height:" + n + "px;background-color:" + (t.isDark(s, h) ? e.colorDark : e.colorLight) + ';"></td>')
        }
        o.push("</tr>")
      }
      o.push("</table>");
      r.innerHTML = o.join("");
      var l = r.childNodes[0];
      var u = (e.width - l.offsetWidth) / 2;
      var f = (e.height - l.offsetHeight) / 2;
      if (u > 0 && f > 0) {
        l.style.margin = f + "px " + u + "px"
      }
    };
    t.prototype.clear = function () {
      this._el.innerHTML = ""
    };
    return t
  }() : function () {
    function t() {
      this._elImage.src = this._elCanvas.toDataURL("image/png");
      this._elImage.style.display = "block";
      this._elCanvas.style.display = "none"
    }
    
    if (this._android && this._android <= 2.1) {
      var u = 1 / window.devicePixelRatio;
      var f = CanvasRenderingContext2D.prototype.drawImage;
      CanvasRenderingContext2D.prototype.drawImage = function (t, e, r, i, a, n, o, s, h) {
        if ("nodeName" in t && /img/i.test(t.nodeName)) {
          for (var l = arguments.length - 1; l >= 1; l--) {
            arguments[l] = arguments[l] * u
          }
        } else if (typeof s == "undefined") {
          arguments[1] *= u;
          arguments[2] *= u;
          arguments[3] *= u;
          arguments[4] *= u
        }
        f.apply(this, arguments)
      }
    }
    
    function e(t, e) {
      var r = this;
      r._fFail = e;
      r._fSuccess = t;
      if (r._bSupportDataURI === null) {
        var i = document.createElement("img");
        var a = function () {
          r._bSupportDataURI = false;
          if (r._fFail) {
            r._fFail.call(r)
          }
        };
        var n = function () {
          r._bSupportDataURI = true;
          if (r._fSuccess) {
            r._fSuccess.call(r)
          }
        };
        i.onabort = a;
        i.onerror = a;
        i.onload = n;
        i.src = "data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==";
        return
      } else if (r._bSupportDataURI === true && r._fSuccess) {
        r._fSuccess.call(r)
      } else if (r._bSupportDataURI === false && r._fFail) {
        r._fFail.call(r)
      }
    }
    
    var r = function (t, e) {
      this._bIsPainted = false;
      this._android = v();
      this._htOption = e;
      this._elCanvas = document.createElement("canvas");
      this._elCanvas.width = e.width;
      this._elCanvas.height = e.height;
      t.appendChild(this._elCanvas);
      this._el = t;
      this._oContext = this._elCanvas.getContext("2d");
      this._bIsPainted = false;
      this._elImage = document.createElement("img");
      this._elImage.alt = "Scan me!";
      this._elImage.style.display = "none";
      this._el.appendChild(this._elImage);
      this._bSupportDataURI = null
      
    };
    r.prototype.draw = function (t) {
      var e = this._elImage;
      var r = this._oContext;
      var i = this._htOption;
      var a = t.getModuleCount();
      var n = i.width / a;
      var o = i.height / a;
      var s = Math.round(n);
      var h = Math.round(o);
      e.style.display = "none";
      this.clear();
      for (var l = 0; l < a; l++) {
        for (var u = 0; u < a; u++) {
          var f = t.isDark(l, u);
          var g = u * n;
          var d = l * o;
          r.strokeStyle = f ? i.colorDark : i.colorLight;
          r.lineWidth = 1;
          r.fillStyle = f ? i.colorDark : i.colorLight;
          r.fillRect(g, d, n, o);
          r.strokeRect(Math.floor(g) + .5, Math.floor(d) + .5, s, h);
          r.strokeRect(Math.ceil(g) - .5, Math.ceil(d) - .5, s, h)
        }
      }
      this._bIsPainted = true
    };
    r.prototype.makeImage = function () {
      if (this._bIsPainted) {
        e.call(this, t)
      }
    };
    r.prototype.isPainted = function () {
      return this._bIsPainted
    };
    r.prototype.clear = function () {
      this._oContext.clearRect(0, 0, this._elCanvas.width, this._elCanvas.height);
      this._bIsPainted = false
    };
    r.prototype.round = function (t) {
      if (!t) {
        return t
      }
      return Math.floor(t * 1e3) / 1e3
    };
    return r
  }();
  
  function C(t, e) {
    var r = 1;
    var i = A(t);
    for (var a = 0, n = g.length; a <= n; a++) {
      var o = 0;
      switch (e) {
        case s.L:
          o = g[a][0];
          break;
        case s.M:
          o = g[a][1];
          break;
        case s.Q:
          o = g[a][2];
          break;
        case s.H:
          o = g[a][3];
          break
      }
      if (i <= o) {
        break
      } else {
        r++
      }
    }
    if (r > g.length) {
      throw new Error("Too long data")
    }
    return r
  }
  
  function A(t) {
    var e = encodeURI(t).toString().replace(/\%[0-9a-fA-F]{2}/g, "a");
    return e.length + (e.length != t ? 3 : 0)
  }
  
  r = function (t, e) {
    this._htOption = {
      width: 256,
      height: 256,
      typeNumber: 4,
      colorDark: "#000000",
      colorLight: "#ffffff",
      correctLevel: s.H
    };
    if (typeof e === "string") {
      e = {text: e}
    }
    if (e) {
      for (var r in e) {
        this._htOption[r] = e[r]
      }
    }
    if (typeof t == "string") {
      t = document.getElementById(t)
    }
    if (this._htOption.useSVG) {
      m = c
    }
    this._android = v();
    this._el = t;
    this._oQRCode = null;
    this._oDrawing = new m(this._el, this._htOption);
    if (this._htOption.text) {
      this.makeCode(this._htOption.text)
    }
  };
  r.prototype.makeCode = function (t) {
    this._oQRCode = new h(C(t, this._htOption.correctLevel), this._htOption.correctLevel);
    this._oQRCode.addData(t);
    this._oQRCode.make();
    this._el.title = t;
    this._oDrawing.draw(this._oQRCode);
    this.makeImage()
  };
  r.prototype.makeImage = function () {
    if (typeof this._oDrawing.makeImage == "function" && (!this._android || this._android >= 3)) {
      this._oDrawing.makeImage()
    }
  };
  r.prototype.clear = function () {
    this._oDrawing.clear()
  };
  r.CorrectLevel = s;
  t("qrcode", r)
});
