// 年选择
;(function ($) {
  $.fn.spinner = function (opts) {
    return this.each(function () {
      var defaults = {value:2018, min:0}
      var options = $.extend(defaults, opts)
      var keyCodes = {up:38, down:40}
      var container = $('<div></div>')
      container.addClass('spinner')
      var textField = $(this).addClass('value').attr('maxlength', '2').val(options.value)
        .bind('keyup paste change', function (e) {
          var field = $(this)
          if (e.keyCode == keyCodes.up) changeValue(1)
          else if (e.keyCode == keyCodes.down) changeValue(-1)
          else if (getValue(field) != container.data('lastValidValue')) validateAndTrigger(field)
        })
      textField.wrap(container)

      var increaseButton = $('<a class="increase"></a>').click(function () { changeValue(1) })
      var decreaseButton = $('<a class="decrease"></a>').click(function () { changeValue(-1) })

      validate(textField)
      container.data('lastValidValue', options.value)
      textField.before(decreaseButton)
      textField.after(increaseButton)

      function changeValue(delta) {
        textField.val(getValue() + delta)
        validateAndTrigger(textField)
      }

      function validateAndTrigger(field) {
        clearTimeout(container.data('timeout'))
        var value = validate(field)
        if (!isInvalid(value)) {
          textField.trigger('update', [field, value])
        }
      }

      function validate(field) {
        var value = getValue()
        if (value <= options.min) decreaseButton.attr('disabled', 'disabled')
        else decreaseButton.removeAttr('disabled')
        field.toggleClass('invalid', isInvalid(value)).toggleClass('passive', value === 0)

        if (isInvalid(value)) {
          var timeout = setTimeout(function () {
            textField.val(container.data('lastValidValue'))
            validate(field)
          }, 500)
          container.data('timeout', timeout)
        } else {
          container.data('lastValidValue', value)
        }
        return value
      }

      function isInvalid(value) { return isNaN(+value) || value < options.min; }

      function getValue(field) {
        field = field || textField;
        return parseInt(field.val() || 0, 10)
      }
    })
  }
})(jQuery)

// select下拉
;jQuery.fn.selectFilter = function (options){
  var defaults = {
    callBack : function (res){}
  };
  var ops = $.extend({}, defaults, options);
  var selectList = $(this).find('select option');
  var that = this;
  var html = '';
  
  // 读取select 标签的值
  html += '<ul class="filter-list">';
  
  $(selectList).each(function (idx, item){
    var val = $(item).val();
    var valText = $(item).html();
    var selected = $(item).attr('selected');
    var disabled = $(item).attr('disabled');
    var isSelected = selected ? 'filter-selected' : '';
    var isDisabled = disabled ? 'filter-disabled' : '';
    if(selected) {
      html += '<li class="'+ isSelected +'" data-value="'+val+'"><a title="'+valText+'">'+valText+'</a></li>';
      $(that).find('.filter-title').val(valText);
    }else if (disabled){
      html += '<li class="'+ isDisabled +'" data-value="'+val+'"><a>'+valText+'</a></li>';
    }else {
      html += '<li data-value="'+val+'"><a title="'+valText+'">'+valText+'</a></li>';
    };
  });
  
  html += '</ul>';
  $(that).append(html);
  $(that).find('select').hide();
  
  //点击选择
  $(that).on('click', '.filter-text', function (){
    $(that).find('.filter-list').slideToggle(100);
    $(that).find('.filter-list').toggleClass('filter-open');
    $(that).find('.icon-filter-arrow').toggleClass('filter-show');
  });
  
  //点击选择列表
  $(that).find('.filter-list li').not('.filter-disabled').on('click', function (){
    var val = $(this).data('value');
    var valText =  $(this).find('a').html();
    $(that).find('.filter-title').val(valText);
    $(that).find('.icon-filter-arrow').toggleClass('filter-show');
    $(this).addClass('filter-selected').siblings().removeClass('filter-selected');
    $(this).parent().slideToggle(50);
    for(var i=0; i<selectList.length; i++){
      var selectVal = selectList.eq(i).val();
      if(val == selectVal) {
        $(that).find('select').val(val);
      };
    };
    ops.callBack(val); //返回值
  });
  
  //其他元素被点击则收起选择
  $(document).on('mousedown', function(e){
    closeSelect(that, e);
  });
  $(document).on('touchstart', function(e){
    closeSelect(that, e);
  });
  
  function closeSelect(that, e) {
    var filter = $(that).find('.filter-list'),
      filterEl = $(that).find('.filter-list')[0];
    var filterBoxEl = $(that)[0];
    var target = e.target;
    if(filterEl !== target && !$.contains(filterEl, target) && !$.contains(filterBoxEl, target)) {
      filter.slideUp(50);
      $(that).find('.filter-list').removeClass('filter-open');
      $(that).find('.icon-filter-arrow').removeClass('filter-show');
    };
  }
};
