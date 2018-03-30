var tool={
	selectClass:function (obj,val) {
		if(val === '请选择'){
			obj.removeClass('selected')
		}else{
			obj.addClass('selected')
		}
	},
	inputVerify:function(obj,key,empyt,max,type){//验证对象，正则则关键字，是否值为空验证，最大值
		var val = obj.val(),len = val.length ,msg = obj.parent().find('.form-msg');
		empyt = empyt || false;
        var regular = tool.regular(key);
        if(len==0&&type==='1'){ //手机必填
        	obj.removeClass('error');
        	msg.text('');
        }else if(len==0&&type==='3'){  //手机邮箱非必填
        	obj.removeClass('error');
        	msg.text('');
        }else if(type==='2'){    //手机邮箱二选一
          var phoneVal = $('#phone').val();
          var emailVal = $('#email').val();
          var id = obj.attr('id');
          if(len == 0 && emailVal.length==0){
              msg.addClass('input-msg');
              obj.removeClass('error');
              var txt = msg.attr('defaultTxt') || '';
              msg.removeClass('error').html(txt);
              msg.addClass('input-msg');
              $('#email').siblings().removeClass('input-msg');
          }
          if(id==='phone'){
          	if(regular.test(val)){
          		var txt = msg.attr('defaultTxt') || '';
				obj.removeClass('error');
				msg.removeClass('error').html(txt);
				msg.addClass('input-msg');
          	}
          }
        }
        
        if(!empyt &&  len == 0) return true;
        
		if(max && len > max){
            tool.creatMsg(obj,'请控制在'+max+'字符以内');
            msg.removeClass('input-msg');
            return false;
        }
        if(empyt &&  len == 0){
            var txt = msg.attr('emptyTxt') || '必填项不可为空';
            tool.creatMsg(obj,txt);
            return false;
        }
        if(regular.test(val)){
        	var txt = msg.attr('defaultTxt') || '';
			obj.removeClass('error');
			msg.removeClass('error').html(txt);
        }else{
			tool.creatMsg(obj,'请输入正确格式');
			msg.removeClass('input-msg');
			return false;
        }
        return true;
	},
	creatMsg:function(obj,info){
		var msg = obj.parents('.form-group').find('.form-msg');
		obj.addClass('error');
		msg.addClass('error').html(info);

	},
    empytInfo:function(name){
        var regular = "必填项不可为空";
        switch (name) {
            case 'email':
                regular = '该员工原有信息中已填写邮箱，请勿清除';
                break;
            case 'phone':
                regular = '该员工原有信息中已填写手机号码，请勿清除';
                break;
            default:
                break;
        }
        return regular;
    },
    regular:function(name){
        var regular;
        switch (name) {
            case 'workNumber':
                regular = /^[A-Za-z0-9]{1,20}$/;
                break;
            case 'name':
                regular = /^[\u4e00-\u9fa5A-Za-z0-9（）·、.]{1,20}$/;
                break;
            case 'personId':
                regular = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
                break;
            case 'phone':
                regular = /^\d{11,20}$/;
                break;
            case 'email':
                regular = /^([a-zA-Z0-9]+[_|\.|\-]*)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\.|\-]?)*[a-zA-Z0-9]+((\.[a-zA-Z]{2,10}){1,2})$/;
                break;
            case 'passwordNum':
                regular = /^[\x20-\x7e]*$/;
                break;
            default:
                break;
        }
        return regular;
    }
}

$.fn.extend({
    realTime: function () {
        var agrs = arguments;
        this.each(function () {
            if (!this.realTime) {
                this.realTime = new RealTime($(this),agrs[0]);
                return;
            }
        })

        return this;
    }
});
function RealTime(ele,num) {
	this.ele = ele;
	this.timer = null;
	this.max = num || 100;
	this.num = this.ele.parent().find('.num');
	this.init();
}
RealTime.prototype.init = function(){
	var self = this;
	self.ele.keyup(function() {
		self.num.html(self.ele.val().length)
        clearTimeout(self.timer)
        self.timer = setTimeout(function(){
            var val = self.ele.val()
            if (self.max < val.length) {
                val = val.substr(0, self.max);
                self.ele.val(val);
				self.num.html(val.length)
            }
        },500)
    });
}

















