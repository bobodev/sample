(function($){
	$.fn.extend({
      checkAll:function(opt){
          this.each(function(){
            var $this=$(this);
            var oInput=$this.find('input[type=checkbox]')
            var aCheckbox = $this.parents('.table').find('tbody input[type=checkbox]');
            oInput.change(function(){
              aCheckbox.each(function(){
                if(oInput.is(':checked')){
									$('.inviteAll').addClass('disabled');
                  $(this).prop('checked','true').parent().addClass('checked');
                }else{
									$('.inviteAll').removeClass('disabled');
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
      },
		//Tab切换
		TabSwitch: function(opt){
  		this.each(function(index,el){
  			var _this = this;
  			var aItem = $(_this).find('.item'),
  				oCurItem = $(_this).find('.item.active'),
  				aTabBd = $(_this).siblings('.m-tab-bd');
  			aItem.on('click',function(){
    			if($(this).hasClass('active') || $(this).hasClass('disabled')) return false;
  				oCurItem = $(this);
  				var i = oCurItem.index();
  				oCurItem.addClass('active').siblings('.item').removeClass('active');
  				if(aTabBd.length>1){
  					aTabBd.eq(i).show().siblings('.m-tab-bd').hide();
  				}
  				opt && opt.change && opt.change.call(this)
  			})
    		})
		}
	})
})(jQuery)
