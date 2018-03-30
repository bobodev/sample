(function($){
  $.fn.extend({
    checkAll:function(opt){
      this.each(function(){
        var $this=$(this);
        var oInput=$this.find('input[type=checkbox]')
        var aCheckbox = $this.parents('.table').find('tbody input[type=checkbox]');
        oInput.change(function(){
          aCheckbox.each(function(){
            if($(this).is(':disabled'))return;
            if(oInput.is(':checked')){
              $(this).prop('checked','true').parent().addClass('checked');
            }else{
              $(this).removeProp('checked').parent().removeClass('checked');
            }
          });
          opt && opt.Allchange && opt.Allchange.call($this);
        });
        aCheckbox.change(function(){
          var isAll=true;
          aCheckbox.each(function(){
            if(!$(this).is(':checked')){
              isAll=false;
              return;
            }
          })
          if(isAll){
            oInput.prop('checked','true').parent().addClass('checked');
          }else{
            oInput.removeProp('checked').parent().removeClass('checked');
          }
          opt && opt.change && opt.change.call($this);
        });
      });
    }

  })
})(jQuery)