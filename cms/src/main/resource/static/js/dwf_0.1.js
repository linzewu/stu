



var qrtNumber=0;

function testOnMove(left,top){
	//console.log(left+"\t"+top);
}
/**
$(function() {
	menuInit();
});**/

function contexDestroy(){
	alert("contexDestroy")
}

function menuInit() {
	var action = $(".dwf-nav1").attr("action");
	
	$.post(action,{},function(data) {
		var menus = $.parseJSON(data);
		var ul=$("<ul></ul>");
		$.each(menus,function(i,n){
			var li=$("<li></li>");
			var a=$("<a></a>");
			a.attr("href","javaScript:void(0);");
			a.bind("click", function(){
				createMenuPanel(n.menus);
				loadCenter(n);
				var wpanel =$('body').layout('panel','west');
				wpanel.panel("setTitle",n.mTitle);
			});
			a.text(n.mTitle);
			li.append(a);
			ul.append(li);
		});
		$(".dwf-nav1").append(ul);
		ul.find("li a").eq(0).click();
	});
}

function createMenuPanel(menus){
	
	var ul=$("<ul></ul>");
	$.each(menus,function(i,n){
		var li=$("<li></li>");
		var a=$("<a></a>");
		var img=$("<img></img>");
		img.attr("src",n.icon);
		img.attr("alt",n.title);
		a.attr("href","javaScript:void(0);");
		a.bind("click", function(){
			loadCenter(n);
		});
		a.append(img);
		a.append(n.title);
		li.append(a);
		ul.append(li);
	});
	$(".dwf-menu1").html('');
	$(".dwf-menu1").append(ul);
}

function loadCenter(n,callback){
	
	//$("#win").window("close");
	window.clearInterval(qrtNumber);
	
	//$("#tabAll").tabs("getSelected").find(".menus li:contains('车辆预登记')").click();
	
	var currentPanel = $("#tabAll").tabs("getTab","车辆预登记");
	var center = currentPanel.find(".easyui-layout").layout('panel','center');
	//var center =$('body').layout('panel','center');
	
	center.panel("clear");
	center.panel("setTitle",n.title);
	center.panel("refresh",n.action);
	
	if(callback){
		callback(n);
	}
}

function toUtf8(str) {    
    var out, i, len, c;    
    out = "";    
    len = str.length;    
    for(i = 0; i < len; i++) {    
        c = str.charCodeAt(i);    
        if ((c >= 0x0001) && (c <= 0x007F)) {    
            out += str.charAt(i);    
        } else if (c > 0x07FF) {    
            out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));    
            out += String.fromCharCode(0x80 | ((c >>  6) & 0x3F));    
            out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));    
        } else {    
            out += String.fromCharCode(0xC0 | ((c >>  6) & 0x1F));    
            out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));    
        }    
    }    
    return out;    
}


function baseEntityManager(id,beanType){
	this.id=id;
	this.beanType=beanType;
	this.editIndex=undefined;
	this.onClickRow=function(index) {
		if (this.editIndex != index) {
			if (this.endEditing()) {
				$("#"+this.id).datagrid('selectRow', index).datagrid(
						'beginEdit', index);
				this.editIndex = index;
			} else {
				$("#"+this.id).datagrid('selectRow', this.editIndex);
			}
		}
	};
	
	this.endEditing= function() {
			if (this.editIndex == undefined) {
				return true;
			}
			if ($('#'+this.id).datagrid('validateRow', this.editIndex)) {
				$('#'+this.id).datagrid('endEdit', this.editIndex);
				var rowData = $('#'+this.id).datagrid('getRows')[this.editIndex];
				rowData['bType'] = this.beanType;
				var dataId = saveData(rowData);
				if(dataId!=null){
					rowData['id'] = dataId;
					this.editIndex = undefined;
					return true;
				}else{
					$.messager.alert("提示","保存出错，请联刷新页面重试","error");
					$('#'+this.id).datagrid('selectRow', this.editIndex)
					.datagrid('beginEdit', this.editIndex);
					return false;
				}
			} else {
				return false;
			}
		};
		
		this.append = function(data) {
			if (this.endEditing()) {
				$('#'+this.id).datagrid('appendRow', data);
				this.editIndex = $('#'+this.id).datagrid('getRows').length - 1;
				$('#'+this.id).datagrid('selectRow', this.editIndex)
						.datagrid('beginEdit', this.editIndex);
			}
		};
		
		this.remove = function() {
			if (this.editIndex == undefined) {
				return
			}
			var rowData = $('#'+this.id).datagrid('getRows')[this.editIndex];
			if(rowData['id']!=null&&rowData['id']!=undefined&&rowData['id']!=""){
				var  data = deleteData(this.beanType,rowData['id']);
				if(data.state!=200){
					$.messager.alert("删除出错",data.errorInfo,"error")
				}
			}
			$('#'+this.id).datagrid('cancelEdit', this.editIndex)
					.datagrid('deleteRow', this.editIndex);
			this.editIndex = undefined;
		};
		
		this.accept = function() {
			if (this.endEditing()) {
				$('#'+this.id).datagrid('acceptChanges');
			}
		};
		this.reject = function() {
			$('#'+this.id).datagrid('rejectChanges');
			this.editIndex = undefined;
		}
}
/**
// 保存数据的方法
function saveData(data) {
	if(data['id']==null){
		data['id']=undefined;
	}
	var responseText = $.ajax({
		type: "POST",
		url : "baseManager!!saveBaseEntity.action",
		async : false,
		data : data
	}).responseText;
	
	var rdata = $.parseJSON(responseText);
	if(rdata.state==200){
		return rdata.sid;
	}else{
		$.messager.alert("错误","数据保存错误","error");
	}
	return null;
}

// 删除数据的方法
function deleteData(beanType,id) {
	var data={'bType':beanType,'id':id};
	var text= $.ajax({
		type: "POST",
		url : "baseManager!!deleteBaseEntity.action",
		async : false,
		data : data
	}).responseText;
	
	return $.parseJSON(text);
}**/

function toedit(tid,url){
	var row = $('#'+tid).datagrid("getSelected");
	if(null!=row){
		window.location.href = url+"?id="+row['id'];
	}else{
		$.messager.alert("提示","请选择需要修改的数据","warning");
	}
}
/**
function del(tid,beanType){
	var row = $('#'+tid).datagrid("getSelected");
	if(row!=null){
		delBaseEntity(beanType, row['id'], function(){
			$('#'+tid).datagrid("reload");
		});
	}else{
		$.messager.alert("提示","请选择需要删除的数据","warning");
	}
}

function delBaseEntity(beanType,id,callback){
	$.messager.confirm("提示","你确认删除该数据",function(r){
		if(r){
			$.post("baseManager!!deleteBean.action",{'bType':beanType,'id':id},function(data){
				if(data.state==200){
					$.messager.alert("提示","数据删除成功","info",callback);
				}else{
					$.messager.alert("提示","数据删除失败，请刷新页面尝试。","error");
				}
		   },"json");
		}
	});
}**/

function savaDataByFrom(formId){
	var isOK = $('#'+formId).form("validate");
	if(isOK){
		$.messager.confirm("提示","请检查数据是否正确!<br>您确认提交该数据吗？", function(r){
    		if(r){
    			 $('#'+formId).submit();
    		}
    	});
	}
}

function dateTimeFormater(formData){
	$(".dateTimeFormater").each(function(i,n){
		var dateJson = formData[$(n).attr("name")];
		if(dateJson==null){
			return;
		}
		var fd =getDateStringByTime(dateJson.time) ;
		$(n).text(fd);
	});
}

function getDateStringByTime(time){
	var date=new Date(time);
	return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+date.getHours()
	+":"+date.getMinutes()+":"+date.getSeconds();
}

function dataFormater(){
	$(".dataFormater").each(function(i,n){
		
		var key = $(n).attr("dataKey");
		var value= $(n).text();
		var data = datacode[key];
		var strs=value.split(",");
		var temp="";
		for(var s in strs){
			$.each(data,function(j,k){
				if(k.value==strs[s]){
					temp+=k.label+",";
				}
			});
		}
		if(temp!=""){
			temp=temp.substring(0, temp.length-1);
		}else{
			temp=value;
		}
		$(n).text(temp);
	});
}


function loadInfo(url,param,formId,callback){
	
	$.messager.progress({
		title : '提示',
		msg : '数据加载中...'
	});
	
	$.post(url,param,function(data){
		try{
			
			if(data.state==1&&data.data!=null){
				var formData=data.data;
				$("#"+formId).form("load",formData);
				$(".readField").each(function(i,n){
					var value = formData[$(n).attr("name")];
					if(value==null||value==""){
						value="&nbsp;"
					}
					$(n).html(value);
				});
				dateTimeFormater(formData);
				dataFormater();
				if(callback){
					callback(formData);
				}
			}else{
				$.messager.alert("错误","数据加载错误，请刷新页面重试", "error");
			}
		}finally{
			$.messager.progress("close");
		}
	},"json");
}
