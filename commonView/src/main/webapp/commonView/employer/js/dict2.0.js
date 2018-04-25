// JavaScript Document
//省市区选择
			function Dict(provinceId, cityId, areaId, desc){
					this.province = document.getElementById(provinceId);
					this.city = document.getElementById(cityId);
					this.area = document.getElementById(areaId);
					this.desc = desc;
					this.initSelect();
			}
			Dict.prototype.initSelect = function(){//初始化自定义select
					var _this = this;
					this.provinceParent = $(this.province).parent();
					this.cityParent = $(this.city).parent();
					this.areaParent = $(this.area).parent();
					this.provinceParent.select({
						change:function(){
							_this.provinceVal = $(_this.province).parent().find('select').find('option').eq(0).prop('selected');
							_this.pName = $(this).val();
							_this.cName = '';
							_this.aName = '';
							_this.renderOptions(2);
							_this.renderOptions(3);
							if(_this.provinceVal){
                               $(_this.city).parent().find('.select-text').removeClass('error');
                               $(_this.city).parents('.address').find('.form-msg').hide().text('');
							}
						}
				 });
					this.cityParent.select({
						change:function(){
							_this.provinceVal = $(_this.province).parent().find('select').find('option').eq(0).prop('selected');
							_this.cName = $(this).val();
							_this.renderOptions(3);
							_this.callback0();
							if(!_this.provinceVal){
                               $(_this.city).parent().find('.select-text').removeClass('error');
                               $(_this.city).parents('.address').find('.form-msg').hide().text('');
							}
						}
				  });
					this.areaParent.select({
						change:function(){
							_this.aName = $(this).val();
							_this.callback();
						}
				  });
			}
			Dict.prototype.init = function(opt){//初始化设置
					this.provinceIds = opt.provinceIds || "";
					this.url = opt.url || "";
					this.pName =  opt.province || '';
					this.cName =  opt.city || '';
					this.aName =  opt.area || '';
					this.cb0 = opt.callback0 || null;   //返回选中的二级地址
					this.cb = opt.callback || null;     //返回选中的三级地址
					this.renderOptions(1);
					this.renderOptions(2);
					this.renderOptions(3);
			}
			Dict.prototype.renderOptions = function(level){//渲染option
				var name,options,dom,$dom,proUrl;
				var _this = this;
				var data;
				proUrl = _this.url;
				if(level == 1){
					name = this.pName;
					dom = this.province;
					$dom = $(dom);
			    ajax(proUrl+'&provinceIds='+this.provinceIds,function(data){
							this.provinceResult = data;
			        options = '<option>'+ _this.desc +'</option>' + _this.creatOptions(data);
							$dom.html(options)
							if(name)$dom.val(name);
							$dom.parent().selectUpdate();
							_this.renderOptions(2);
			    });
				}
				else if(level == 2){
					dom = this.city;
					$dom = $(dom);
					if(this.pName && this.pName != this.desc){//判断省是否值并且不等于默认值
						data = getId(provinceResult, this.pName);
						name = this.cName;
			      ajax(proUrl+'&provinceId='+data,function(city){
						  this.cityResult = city;
		          options = '<option>'+ _this.desc +'</option>' + _this.creatOptions(city);
							$dom.html(options)
							if(name)$dom.val(name);
							$dom.parent().selectUpdate();
							_this.renderOptions(3);
							
			      })
					}else{
						  options = '<option>'+ _this.desc +'</option>';
							$dom.html(options)
							if(name)$dom.val(name);
							$dom.parent().selectUpdate();
					}
				}
				else if(level == 3){
					dom = this.area;
					$dom = $(dom);
					if($dom.is(':disabled')){
						$dom.prop('disabled',false).parent().removeClass('disabled');
					}
					if(this.cName && this.cName != this.desc){
						dataIds = getId(cityResult, this.cName);
						name = this.aName;
			      ajax(proUrl+'&cityId='+dataIds,function(area){
							  if(!area.success){
									  area.errmsg = '';
									  area.data = [];
										if(area.errmsg.length ==0){
												_this.callback();
										 		$dom.prop('disabled',true).parent().addClass('disabled');
										 }
								}
								options = '<option>'+ _this.desc +'</option>' + _this.creatOptions(area);
								$dom.html(options)
								if(name)$dom.val(name);
								$dom.parent().selectUpdate();
			      })
					}else{
						  options = '<option>'+ _this.desc +'</option>';
							$dom.html(options)
							if(name)$dom.val(name);
							$dom.parent().selectUpdate();
					}
				}
			}

			Dict.prototype.creatOptions = function(obj){//创建options
					var options = '';
						for(var i = 0; i < obj.data.length; i++){
							  options += '<option value="' + obj.data[i].name + '">' + obj.data[i].name + '</option>';
					  }
					return options;
			}
            
            Dict.prototype.callback0 = function(){
					var _this = this;
					_this.cb0 && _this.cb0([_this.pName||'', _this.cName||'']);
			}
            
			Dict.prototype.callback = function(){
					var _this = this;
					_this.cb && _this.cb([_this.pName||'', _this.cName||'', _this.aName||'']);
			}
			
			

			function getId(obj, name){//根据上级地址筛选
					if(obj.data){
						for(var i=0, len=obj.data.length; i<len; i++){
							if(obj.data[i].name == name){
								return obj.data[i].id;
							}
						}
					}
			}

			function ajax(sUrl,successfn){
			    	$.ajax({
			    		url:sUrl,
			    		type: "get",
			        async: false,
							dataType:"json",
			    		success: function(resp){
					    			successfn&&successfn(resp);
			    		}
			    	})
			  }
