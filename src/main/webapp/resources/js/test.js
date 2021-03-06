(function (e, t, n, r) {
    function o(t, n) {
        this.element = t;
        this.$element = e(t);
        this.settings = e.extend({}, s, n);
        this._defaults = s;
        this._name = i;
        if (this[n]) {
            this[n](this.element)
        } else {
            this.isInit = true;
            this.init()
        }
    }
    var i = "magnet", s = {animationType: "scale", columns: {items: 8}, containerSelector: ".magnet", containerStyle: {position: "relative", overflow: "hidden"}, duration: 800, filter: "*", filterSelector: ".magnet-filter", gutter: null, hiddenClass: "magnet-hidden", hiddenStyle: {opacity: 0}, itemSelector: ".magnet-item", itemStyle: {position: "absolute"}, layoutMode: "masonry", rows: {items: 8}, visibleStyle: {opacity: "1"}};
    o.prototype = {init: function () {
            var e = this;
            e.$container = e.$element.find(e.settings.containerSelector);
            e.$item = e.$element.find(e.settings.itemSelector);
            e.$filter = e.$element.find(e.settings.filterSelector);
            e.containerWidth;
            e.items = {x: [], y: []};
            e.onInit();
            e.onResize();
            e.onClick()
        }, getContainerWidth: function () {
            var e = this, t = e.$container;
            e.containerWidth = t.width()
        }, setGlobalStyle: function () {
            var e = this, t = e.$container, n = e.$item;
            t.css(e.settings.containerStyle);
            n.css(e.settings.itemStyle);
            n.css({"margin-right": e.settings.gutter + "px", "margin-bottom": e.settings.gutter + "px"})
        }, fetchItems: function () {
            var t = this, n = t.$item, r = [], i = [], s = [], o = [], u = 0;
            n.each(function () {
                var n = e(this), a = n[0], f = n.outerWidth(true);
                h = n.outerHeight(true);
                r.push(a);
                i.push(u);
                s.push(f);
                o.push(h);
                t.items.element = r;
                t.items.index = i;
                t.items.width = s;
                t.items.height = o;
                u++
            })
        }, filterItems: function () {
            var t = this, n = t.$item, r = [], i = 0;
            n.each(function () {
                var n = e(this), s = true;
                if (t.settings.filter === "*" || n.hasClass(t.settings.filter)) {
                    n.removeClass(t.settings.hiddenClass)
                } else {
                    s = false;
                    n.addClass(t.settings.hiddenClass)
                }
                r.push(s);
                i++
            });
            t.items.visibility = r
        }, layoutItems: function () {
            var t = this, n = t.settings.layoutMode, r = t.items, i = t.$container, s = t.$item, o = 0, u = 1, a = 1, f, l = 0, c = 0, h = 0, p = 0, d = 0, v = 0;
            switch (t.settings.layoutMode) {
                case"tiled":
                    s.each(function () {
                        var n = e(this);
                        if (r.visibility[o]) {
                            r.x[o] = d;
                            r.y[o] = l;
                            for (j = 0; j < o && r.visibility[o]; j++) {
                                if (r.x[o] == 0 && r.x[j] == 0) {
                                    if (r.visibility[j]) {
                                        l = r.y[j];
                                        r.y[o] = l
                                    }
                                }
                            }
                            for (j = 0; j < o; j++) {
                                if (r.visibility[j]) {
                                    var i = r.x[o] + r.width[o] > r.x[j] && r.x[o] + r.width[o] < r.x[j] + r.width[j];
                                    var s = r.x[j] + r.width[j] > r.x[o] && r.x[j] + r.width[j] < r.x[o] + r.width[o];
                                    var u = r.x[o] == r.x[j];
                                    var a = r.x[o] + r.width[o] == r.x[j] + r.width[j];
                                    var c = r.y[j] + r.height[j] > r.y[o];
                                    var h = (i || s || u || a) && c;
                                    if (h) {
                                        r.y[o] = r.height[j] + r.y[j]
                                    }
                                }
                            }
                            f = o + 1;
                            while (r.visibility[f] == false) {
                                f++
                            }
                            if (d + r.width[o] + r.width[f] <= t.containerWidth) {
                                d += r.width[o]
                            } else {
                                d = 0
                            }
                        }
                        o++
                    });
                    break;
                case"rows":
                    s.each(function () {
                        var n = e(this);
                        if (r.visibility[o]) {
                            r.x[o] = d;
                            r.y[o] = l;
                            if (r.height[o] > h) {
                                h = r.height[o]
                            }
                            f = o + 1;
                            while (r.visibility[f] == false) {
                                f++
                            }
                            if (d + r.width[o] + r.width[f] <= t.containerWidth && u < t.settings.rows.items) {
                                d += r.width[o]
                            } else {
                                l += h;
                                u = 0;
                                d = 0;
                                h = 0
                            }
                            u++
                        }
                        o++
                    });
                    break;
                case"columns":
                    s.each(function () {
                        var n = e(this);
                        if (r.visibility[o]) {
                            r.x[o] = d;
                            r.y[o] = l;
                            if (r.width[o] > v) {
                                v = r.width[o]
                            }
                            f = o + 1;
                            while (r.visibility[f] == false) {
                                f++
                            }
                            if (a < t.settings.columns.items) {
                                l += r.height[o]
                            } else {
                                d += v;
                                a = 0;
                                l = 0;
                                v = 0
                            }
                            a++
                        }
                        o++
                    });
                    break;
                case"vertical":
                    s.each(function () {
                        var t = e(this);
                        if (r.visibility[o]) {
                            r.x[o] = d;
                            r.y[o] = l;
                            l += r.height[o]
                        }
                        o++
                    });
                    break;
                case"horizontal":
                    s.each(function () {
                        var t = e(this);
                        if (r.visibility[o]) {
                            r.x[o] = d;
                            r.y[o] = l;
                            d += r.width[o]
                        }
                        o++
                    });
                    break;
                case"static":
                    s.each(function () {
                        var n = e(this);
                        r.x[o] = d;
                        r.y[o] = l;
                        if (c == 0 || c > r.height[o]) {
                            c = r.height[o]
                        }
                        do {
                            var i = false;
                            for (j = 0; j < o; j++) {
                                var s = r.x[o] + r.width[o] > r.x[j] && r.x[o] + r.width[o] < r.x[j] + r.width[j];
                                var u = r.x[j] + r.width[j] > r.x[o] && r.x[j] + r.width[j] < r.x[o] + r.width[o];
                                var a = r.x[o] == r.x[j];
                                var h = r.x[o] + r.width[o] == r.x[j] + r.width[j];
                                var v = r.y[j] + r.height[j] > r.y[o];
                                var m = (s || u || a || h) && v;
                                if (m) {
                                    i = true;
                                    if (r.x[j] + r.width[j] + r.width[o] <= t.containerWidth) {
                                        d = r.x[j] + r.width[j]
                                    } else {
                                        d = 0;
                                        l += p;
                                        r.y[o] = l
                                    }
                                    r.x[o] = d
                                }
                            }
                        } while (i);
                        f = o + 1;
                        if (d + r.width[o] + r.width[f] <= t.containerWidth) {
                            d += r.width[o]
                        } else {
                            p = c;
                            d = 0;
                            c = 0
                        }
                        o++
                    });
                    break;
                default:
                    s.each(function () {
                        var n = e(this);
                        if (r.visibility[o]) {
                            r.x[o] = d;
                            r.y[o] = l;
                            if (c == 0 || c > r.height[o]) {
                                c = r.height[o]
                            }
                            do {
                                var i = false;
                                for (j = 0; j < o; j++) {
                                    if (r.visibility[j]) {
                                        var s = r.x[o] + r.width[o] > r.x[j] && r.x[o] + r.width[o] < r.x[j] + r.width[j];
                                        var u = r.x[j] + r.width[j] > r.x[o] && r.x[j] + r.width[j] < r.x[o] + r.width[o];
                                        var a = r.x[o] == r.x[j];
                                        var h = r.x[o] + r.width[o] == r.x[j] + r.width[j];
                                        var v = r.y[j] + r.height[j] > r.y[o];
                                        var m = (s || u || a || h) && v;
                                        if (m) {
                                            i = true;
                                            if (r.x[j] + r.width[j] + r.width[o] <= t.containerWidth) {
                                                d = r.x[j] + r.width[j]
                                            } else {
                                                d = 0;
                                                l += p;
                                                r.y[o] = l
                                            }
                                            r.x[o] = d
                                        }
                                    }
                                }
                            } while (i);
                            f = o + 1;
                            while (r.visibility[f] == false) {
                                f++
                            }
                            if (d + r.width[o] + r.width[f] <= t.containerWidth) {
                                d += r.width[o]
                            } else {
                                p = c;
                                d = 0;
                                c = 0
                            }
                        }
                        o++
                    })
                }
        }, animateItems: function () {
            var t = this, n = t.$item, r, i, s = 0;
            n.each(function () {
                var n = e(this);
                if (t.items.visibility[s]) {
                    i = t.settings.visibleStyle.opacity;
                    switch (t.settings.animationType) {
                        case"fade":
                            r = "";
                            break;
                        case"flip":
                            r = "rotateX(0deg)";
                            break;
                        case"turn":
                            r = "rotateY(0deg)";
                            break;
                        case"rotate":
                            r = "rotateZ(0deg)";
                            break;
                        default:
                            r = "scale3d(1, 1, 1)"
                        }
                } else {
                    i = t.settings.hiddenStyle.opacity;
                    switch (t.settings.animationType) {
                        case"fade":
                            r = "";
                            break;
                        case"flip":
                            r = "rotateX(180deg)";
                            break;
                        case"turn":
                            r = "rotateY(180deg)";
                            break;
                        case"rotate":
                            r = "rotateZ(180deg)";
                            break;
                        default:
                            r = "scale3d(0, 0, 0)"
                        }
                }
                if (t.isInit) {
                    n.css({transform: "translate3d(" + t.items.x[s] + "px," + t.items.y[s] + "px, 0px)" + r, opacity: i})
                } else {
                    n.css({transform: "translate3d(" + t.items.x[s] + "px," + t.items.y[s] + "px, 0px)" + r, opacity: i, transition: t.settings.duration + "ms"})
                }
                s++
            });
            t.isInit = false
        }, resizeContainer: function () {
            var e = this, t = e.settings.layoutMode, n = e.items, r = e.$container, i = e.$item, s = 0, o = 0, u = 0;
            if (t == "columns" || t == "horizontal") {
                for (s = 0; s < n.visibility.length; s++) {
                    if (n.visibility[s]) {
                        if (n.width[s] + n.x[s] > o) {
                            o = n.width[s] + n.x[s]
                        }
                    }
                }
                o += r.outerWidth() - e.containerWidth;
                r.css("width", o)
            }
            for (s = 0; s < n.visibility.length; s++) {
                if (n.visibility[s]) {
                    if (n.height[s] + n.y[s] > u) {
                        u = n.height[s] + n.y[s]
                    }
                }
            }
            u += r.outerHeight() - r.height();
            r.css({height: u, transition: e.settings.duration + "ms"})
        }, onInit: function () {
            var e = this;
            e.getContainerWidth();
            e.setGlobalStyle();
            e.fetchItems();
            e.filterItems();
            e.layoutItems();
            e.animateItems();
            e.resizeContainer()
        }, onClick: function () {
            var t = this, n = t.$filter;
            n.on("click", "[data-filter]", function () {
                var r = e(this);
                if (r.parent().is(n)) {
                    r.addClass("active").siblings().removeClass("active")
                } else {
                    r.parent().addClass("active").siblings().removeClass("active")
                }
                t.settings.filter = r.attr("data-filter");
                t.getContainerWidth();
                t.filterItems();
                t.layoutItems();
                t.animateItems();
                t.resizeContainer()
            })
        }, onResize: function () {
            var n = this;
            e(t).resize(function () {
                n.getContainerWidth();
                n.fetchItems();
                n.layoutItems();
                n.animateItems();
                n.resizeContainer()
            })
        }};
    o.prototype.append = function (e) {
        var t = this;
        t.$container.append(e);
        t.refresh()
    };
    o.prototype.destroy = function () {
        var n = this, r = n.$item, i = n.$container;
        $filter = n.$filter;
        r.each(function () {
            var t = e(this);
            t.removeClass(n.settings.hiddenClass);
            t.removeAttr("style")
        });
        i.removeAttr("style");
        $filter.off("click", "[data-filter]");
        e(t).off("resize")
    };
    o.prototype.get = function (e) {
        var t = this, n = t.$container.find(e);
        for (var r in t.items.element) {
            var e = t.items.element[r];
            if (n.is(e)) {
                console.log(e)
            }
        }
    };
    o.prototype.getAll = function () {
        var e = this;
        for (var t in e.items.element) {
            console.log(e.items.element[t])
        }
    };
    o.prototype.getAllData = function () {
        var e = this;
        console.log(e.items)
    };
    o.prototype.hide = function (t) {
        var n = this, r = n.$container.find(t), i = 0;
        n.$item.each(function () {
            var t = e(this);
            if (r.is(t)) {
                r.addClass(n.settings.hiddenClass);
                n.items.visibility[i] = false
            }
            i++
        });
        n.layoutItems();
        n.animateItems();
        n.resizeContainer()
    };
    o.prototype.prepend = function (e) {
        var t = this;
        t.$container.prepend(e);
        t.refresh()
    };
    o.prototype.refresh = function () {
        var e = this;
        e.init()
    };
    o.prototype.remove = function (e) {
        var t = this;
        t.$container.find(e).remove();
        t.refresh()
    };
    o.prototype.show = function (t) {
        var n = this, r = n.$container.find(t), i = 0;
        n.$item.each(function () {
            var t = e(this);
            if (r.is(t)) {
                r.removeClass(n.settings.hiddenClass);
                n.items.visibility[i] = true
            }
            i++
        });
        n.layoutItems();
        n.animateItems();
        n.resizeContainer()
    };
    e.fn[i] = function (t) {
        var n = arguments;
        if (t === r || typeof t === "object") {
            return this.each(function () {
                if (!e.data(this, "plugin_" + i)) {
                    e.data(this, "plugin_" + i, new o(this, t))
                }
            })
        } else if (typeof t === "string" && t[0] !== "_" && t !== "init") {
            var s;
            this.each(function () {
                var r = e.data(this, "plugin_" + i);
                if (r instanceof o && typeof r[t] === "function") {
                    s = r[t].apply(r, Array.prototype.slice.call(n, 1))
                }
                if (t === "destroy") {
                    e.data(this, "plugin_" + i, null)
                }
            });
            return s !== r ? s : this
        }
    }
})(jQuery, window, document)