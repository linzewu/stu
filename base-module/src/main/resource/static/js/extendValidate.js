$.extend(
	$.fn.validatebox.defaults.rules,
	{
		ipVad:{
			validator:function(value, param){
				
				var ips=value.split(",");
				var reg =  /^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])$/;
				if(!reg.test(value)){
					return false;
				}
				return true;
			},
			message:"您输入的IP地址不正确！"
		},
		ipsVad:{
			validator:function(value, param){
				
				var ips=value.split(",");
				var reg =  /^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])$/;
				for(var i in ips){
					var ip = ips[i];
					if(!reg.test(ip)){
						return false;
					}
				}
				return true;
				
			},
			message:"您输入的IP地址不正确！"
		},
		userVad : {
			validator : function(value, param) {
				var reg = /^[a-zA-Z\d]\w{3,11}[a-zA-Z\d]$/;
				
				var aflag=reg.test(value);
				
				var bflag=false;
				param[1]='用户名包含5到12位数字、字母';
				
				if(aflag){
					var data={};
					data.yhm=value;
					var id =$(param[0]).val();
					if(id!=null&&$.trim(id)!=""){
						data.id=id;
					}
					var r = $.ajax({url:'../../user/validateUserName',dataType:"json",data:data,async:false,cache:false,type:"post"}).responseText;
					bflag=r=="true";
					if(!bflag){
						param[1]='用户名已存在';
					}
				}
				
				
				
				return bflag&&aflag;
			},
			message : '{1}'
		},
		idCardVad : {
			validator : function(value, param) {
				// 15位和18位身份证号码的正则表达式
				var regIdCard = /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
				// 如果通过该验证，说明身份证格式正确，但准确性还需计算
				if (regIdCard.test(value)) {
					if (value.length == 18) {
						var idCardWi = new Array(7, 9, 10, 5, 8, 4,
								2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); // 将前17位加权因子保存在数组里
						var idCardY = new Array(1, 0, 10, 9, 8, 7,
								6, 5, 4, 3, 2); // 这是除以11后，可能产生的11位余数、验证码，也保存成数组
						var idCardWiSum = 0; // 用来保存前17位各自乖以加权因子后的总和
						for (var i = 0; i < 17; i++) {
							idCardWiSum += value
									.substring(i, i + 1)
									* idCardWi[i];
						}
						var idCardMod = idCardWiSum % 11;// 计算出校验码所在数组的位置
						var idCardLast = value.substring(17);// 得到最后一位身份证号码
						// 如果等于2，则说明校验码是10，身份证号码最后一位应该是X
						if (idCardMod == 2) {
							if (idCardLast == "X"
									|| idCardLast == "x") {
								return true;
							} else {
								return false;
							}
						} else {
							// 用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
							if (idCardLast == idCardY[idCardMod]) {
								return true;
							} else {
								return false;
							}
						}
					}
				} else {
					return false;
				}
			},
			message : '身份证号码错误'
		},
		passwordValide: {			
	        validator: function(value, param){
	        	var paramValue = "/^(?![a-zA-Z0-9]+$)(?![^a-zA-Z\/D]+$)(?![^0-9\/D]+$).{8,16}$/";
	        	var memo = "不能使用默认密码，只能输入8-16个字母、数字、特殊符号";
	        	var bpObj = comm.getBaseParamObjs("mmjygz");
	        	if (bpObj.length > 0){
	        		paramValue = bpObj[0].paramValue;
	        		memo = bpObj[0].memo;
	        	}
	        	param[0] = memo;
	        	var patrn=eval(paramValue);// /^(\w){8,16}$/;
	        	if (!patrn.exec(value)||value=='888888'){
	        		 return false
	        	} else{
	        		return true 
	        	}
	        },
	        message: '{0}'
	    },
	    passwordEquals: {
	        validator: function(value,param){
	            return value == $(param[0]).val();
	        },
	        message: '两次密码不匹配！'
	    },
	    mobile : {// 验证手机号码
	        validator : function(value) {
	            return /^(13|15|18|17|14|19)\d{9}$/i.test(value);
	        },
	        message : '手机号码格式不正确'
	    },
    	ghValide : {
			validator : function(value, param) {
				console.log(param)
				var bflag=false;
				
					var data={};
					data.gh=value;
					var id =$(param[0]).val();
					if(id!=null&&$.trim(id)!=""){
						data.id=id;
					}
					var r = $.ajax({url:'../../user/validateGH',dataType:"json",data:data,async:false,cache:false,type:"post"}).responseText;
					bflag=r=="true";
					if(!bflag){
						param[1]='警号已存在';
					}
				
				
				
				return bflag;
			},
			message : '{1}'
		}
	});