$.fn.extend({
    address:function(){
        var agrs = arguments;
        this.each(function () {
            if (!this.address) {
                this.address = new Address($(this),agrs[0]);
                return;
            }
        })
        return this;
    }
});


function Address(ele,opt) {
    this.ele = ele;
    this.province = ele.find('.u-select').eq(0);
    this.city = ele.find('.u-select').eq(1);
    this.area = ele.find('.u-select').eq(2);
    var opts = {
        url:'',
        val:[],
        change:null
    }
    this.opts = $.extend({}, opts, opt);
    this.init();
}

Address.prototype.init = function(){
    var self = this,
        opt = self.opts;
    self.province.select({
        change:function(){
            tool.selectClass(self.province,$(this).val());
            self.upOption(self.area,[]);
            ajax(opt.url+'getCityByProvinceId?provinceId='+$(this).val()+'',function(result){
                if(result.code == 0){
                    self.upOption(self.city,result.data);
                }
            });
            opt.val = [];
            opt.val.push($(this).val());
            opt.change && opt.change.call(this,opt.val);
        }
    });
    self.city.select({
        change:function(){
            tool.selectClass(self.city,$(this).val())
            ajax(opt.url+'getDistrictByCityId?cityId='+$(this).val()+'',function(result){
                if(result.code == 0){
                    self.upOption(self.area,result.data)
                }
            });
            if(opt.val.length > 1){
                opt.val = opt.val.slice(0,1)
                opt.val.push($(this).val());
            }else{
                opt.val.push($(this).val());
            }
            self.ele.find('.form-msg').hide();
            self.city.find('.select-text').removeClass('error')
            opt.change && opt.change.call(this,opt.val);
        }
    });
    self.area.select({
        change:function(){
            tool.selectClass(self.area,$(this).val());
            if(opt.val.length > 2){
                opt.val = opt.val.slice(0,2)
                opt.val.push($(this).val());
            }else{
                opt.val.push($(this).val());
            }
            opt.change && opt.change.call(this,opt.val);
        }
    });

    ajax(opt.url+'getAddressProvinces',function(result){
        if(result.code == 0){
            self.upOption(self.province,result.data,self.opts.val[0]);
            if(self.opts.val[0]){
                tool.selectClass(self.province,self.opts.val[0]);
            }
        }
    });
    if(opt.val[0]){
        ajax(opt.url+'getCityByProvinceId?provinceId='+opt.val[0]+'',function(result){
            if(result.code == 0){
                self.upOption(self.city,result.data,self.opts.val[1]);
                tool.selectClass(self.city,self.opts.val[1]);
            }
        });
    }
    if(opt.val[1]){
        ajax(opt.url+'getDistrictByCityId?cityId='+opt.val[1]+'',function(result){
            if(result.code == 0){
                self.upOption(self.area,result.data,self.opts.val[2]);
                tool.selectClass(self.area,self.opts.val[2]);
            }
        });
    }
}

Address.prototype.upOption = function(obj,data,selected){
    var select = obj.find('select'),str = "<option>请选择</option>";
    obj.removeClass('selected')
    select.empty();
    for (var key in data){
        var strs = selected ? (selected == key ? 'selected' : ''): '';
        str += '<option value="'+key+'" '+strs+'>'+data[key]+'</option>';
    }
    select.html(str);
    obj.selectUpdate()
}

Address.prototype.setOption = function(obj,data){
    var option = obj.find('option');
    for (var i = 0; i < option.length; i++) {
        if(option.eq(i).attr('value') === data){
            option.eq(i).attr('selected', true);
        }
    }
    obj.selectUpdate()
}

function ajax(sUrl,successfn){
    $.ajax({
        url:sUrl,
        type: "get",
        async: true,
        jsonp: "callback",
        success: function(result){
            successfn && successfn(result);
        }
    })      
}















