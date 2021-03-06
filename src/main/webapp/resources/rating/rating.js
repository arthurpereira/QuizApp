PrimeFaces.widget.Rating = PrimeFaces.widget.BaseWidget.extend({
    init: function (a) {
        this._super(a);
        this.jqInput = $(this.jqId + "_input");
        this.value = this.getValue();
        this.stars = this.jq.children(".ui-rating-star");
        this.cancel = this.jq.children(".ui-rating-cancel");
        if (!this.cfg.disabled && !this.cfg.readonly) {
            this.bindEvents()
        }
        if (this.cfg.readonly) {
            this.jq.children().css("cursor", "default")
        }
    }, bindEvents: function () {
        var a = this;
        this.stars.click(function () {
            var b = a.stars.index(this) + 1;
            a.setValue(b)
        });
        this.cancel.hover(function () {
            $(this).toggleClass("ui-rating-cancel-hover")
        }).click(function () {
            a.reset()
        })
    }, unbindEvents: function () {
        this.stars.unbind("click");
        this.cancel.unbind("hover click")
    }, getValue: function () {
        var a = this.jqInput.val();
        return a == "" ? null : parseInt(a)
    }, setValue: function (c) {
        this.jqInput.val(c);
        this.stars.removeClass("ui-rating-star-on");
        for (var b = 0; b < c; b++) {
            this.stars.eq(b).addClass("ui-rating-star-on")
        }
        if (this.cfg.onRate) {
            this.cfg.onRate.call(this, c)
        }
        if (this.cfg.behaviors) {
            var a = this.cfg.behaviors.rate;
            if (a) {
                a.call(this)
            }
        }
    }, enable: function () {
        this.cfg.disabled = false;
        this.bindEvents();
        this.jq.removeClass("ui-state-disabled")
    }, disable: function () {
        this.cfg.disabled = true;
        this.unbindEvents();
        this.jq.addClass("ui-state-disabled")
    }, reset: function () {
        this.jqInput.val("");
        this.stars.filter(".ui-rating-star-on").removeClass("ui-rating-star-on");
        if (this.cfg.behaviors) {
            var a = this.cfg.behaviors.cancel;
            if (a) {
                a.call(this)
            }
        }
    }
});