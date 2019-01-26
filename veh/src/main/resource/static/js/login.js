document.onkeydown = function(e) {
	var e = event || window.event || arguments.callee.caller.arguments[0];
	
	var element = e.srcElement || e.target;

	if (e.keyCode == 13 && element.type != "submit" && element.type != "button"
			&& element.type != "textarea" && element.type != "reset") {
		if (document.all) {
			e.keyCode = 9;
		} else {
			var nexElement = getNextInput(element);
			if(nexElement!=null){
				nexElement.focus();
				e.preventDefault();
			}
		}
		if(element.type=="password"){
			login();
		}
	}
}

function getNextInput(input) {

	var form = input.form;
	if(!form){
		return null;
	}
	for (var i = 0; i < form.elements.length; i++) {
		if (form.elements[i] == input) {
			break;
		}
	}
	while (true) {
		if (i++ < form.elements.length) {
			
			if (form.elements[i]!=null && form.elements[i].type != "hidden"
					&& form.elements[i].type != 'checkbox'&&
					$(form.elements[i]).css('display')!="none"
			) {
				return form.elements[i];
			}
		} else {
			return null;
		}
	}
}

function login() {
	
	var userName=$("#userName").textbox("getValue");
	var password=$("#password").textbox("getValue");
	
	if($.trim(userName)==""){
		$("#userName").textbox("validate");
		$("#userName").textbox('textbox').focus();
		return;
	}else if($.trim(password)==""){
		$("#password").textbox("validate");
		$("#password").textbox('textbox').focus();
		return;
	}
	
	$.post("../../user/login",{userName:userName,password:password}, function(data){
		console.log(data);
		if(data.state==1){
			var userData  = data.data;
			console.log(userData)
			window.location.href="index.html";
		}else{
			$.messager.alert("登陆失败",data.errorMsg,"info");
		}
	},"json");
}
/**
$(document).ajaxStart(function(){
	$.messager.progress({
		title:"请等待",
		msg:"请求发送中。。。"
	}); 
});

$(document).ajaxComplete(function(){
	$.messager.progress('close');
});**/
