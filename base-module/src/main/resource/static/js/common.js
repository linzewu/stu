



var comm = {
	getBaseParames : function(type) {
		var array = [];
		for ( var i in bps) {
			var bp = bps[i];
			if (bp.type == type) {
				var map = {};
				map['value'] = bp.paramName;
				map['id'] = bp.paramValue;
				array.push(map);
			}
		}
		return array;
	},
	
	getParamNameByValue:function(type,value){
		for ( var i in bps) {
			var bp = bps[i];
			if (bp.type == type && bp.paramValue==value) {
				 return bp.paramName;
			}
		}
		return value;
	},
	toPage : function(target, title, url, param) {
		
		$(target).panel({
			"title" : title,
			href:url,
			"queryParams" : param
		});
	},
	createMume : function(id, data) {
		var ul = $("#" + id);	
		ul.empty();
		//此处的data为自定义Map，详见createMenus.js
		data.each(function(key,n,i){
			var li = $("<li><a id='_menu"+i+"' href=\"javascript:void(0)\"><img></a></li>");
			li.find("img").attr("src", n.icon);
			li.find("a").append(n.title);
			if (n.callbak) {
				li.find("a").bind("click", n.callbak)
			} else {
				li.find("a").bind(
						"click",
						function() {
							comm.toPage(n.target, n.title,
									n.href, n.param);
						});
			}
			ul.append(li);
			
			if (i == 0) {
				li.find("a").click();
			}
		});
	},
	openComWindow:function(url,param){
		if(param){
			$("#comm_window").window(param);
		}
		$("#comm_window").window("open").window('refresh', url);
	},
	createBaseSelect:function(target,data,title){
		
		if(title){
			$(target).append("<option value=''>--"+title+"--</option>");
		}
		if(typeof data=="string"){
			data = getBaseParames(data);
		}
		for(var i in data){
			$(target).append("<option value='"+data[i].id+"'>"+data[i].value+"</option>");
		}
	},
	creaTemplate:function(param){
		var id=param.id;
		var title=param.title;
		var menu=param.menus;
		var temp = $("<div class=\"easyui-layout\" data-options=\"fit:true\">"+
		"<div data-options=\"region:'west',title:'"+title+"',split:true\" style=\"width: 200px;\" ><ul class=menus></ul></div>"+
		"<div data-options=\"region:'center',href:'"+menu[0].href+"',title:'"+menu[0].title+"',queryParams:"+param.param+"\" id="+id+"Contex></div></div>");
		comm.createMumeOfTemp(temp,menu,id+"Contex");
		return temp;
	},
	createMumeOfTemp : function(temp, data,context) {
		var ul = temp.find(".menus" );
		ul.empty();
		console.log(ul);
		$.each(data,function(i,n){
			var li = $("<li><a id='_menu"+i+"' href=\"javascript:void(0)\"><img></a></li>");
			li.find("img").attr("src", n.icon);
			li.find("a").append(n.title);
			if (n.callbak) {
				li.find("a").bind("click", n.callbak)
			} else {
				li.find("a").bind(
						"click",
						function() {
							if(n.target){
								comm.toPage(n.target, n.title,
										n.href, n.param);
							}else{
								comm.toPage("#"+context, n.title,
										n.href, n.param);
							}
						});
			}
			ul.append(li);
		});
	},
	createTab:function(id,title,href){
		
		if($(id).tabs("getTab",title)){
			$(id).tabs("select",title);
		}else{
			var cp = $(id).tabs("getTab",1);
			var optiosn={
					title: title,
					selected: true,
					fit:true,
					closable:true,
					href:href
				};
			
			if(cp){
				$.messager.confirm("提示",cp.panel("options").title+" 面板正在编辑，是否关闭？",function(r){
					if(r){
						$(id).tabs("close",1);
						$(id).tabs('add',optiosn);
					}
				});
			}else{
				$(id).tabs('add',optiosn);
			}
		
		}
	}
}

var gridUtil = {
	createNew : function(grid,options) {
		var g = {};
		g.editIndex = null;
		g.endEditing = function(callback) {
			if (g.editIndex == null) {
				if(callback){
					callback.call();
				}
				return
			}
			
			if ($(grid).datagrid('validateRow', g.editIndex)) {
				
				$.messager.progress({"title":"数据保存中！"});
				
				$(grid).datagrid('endEdit', g.editIndex);
				
				var rows=$(grid).datagrid("getRows");
				
				
				$.post(options["url"]+"/save",rows[g.editIndex],function(rd){
					if(rd.state==1){
						g.editIndex = null;
						rows[g.editIndex]=rd.data;
						if(callback){
							callback.call();
						}
					}else{
						var errors="";
						$.each(rd.errors,function(i,n){
							errors+=(i+1)+"、"+n.defaultMessage+"<br>";
						});
						
						$.messager.alert("保存错误",errors,"error");
						$(grid).datagrid('beginEdit', g.editIndex);
					}
				}).error(function(e){
					$.messager.alert("错误",e.responseText,"error");
					$(grid).datagrid('beginEdit', g.editIndex);
				}).complete(function(e){
					$.messager.progress("close");
				});
				
			}
		};
		g.append = function() {
			
			g.endEditing(function(){
				$(grid).datagrid('appendRow', {});
				g.editIndex = $(grid).datagrid('getRows').length - 1;
				$(grid).datagrid('selectRow', g.editIndex).datagrid(
						'beginEdit', g.editIndex);
			});
			
			
		};
		g.remove = function() {
			var row = $(grid).datagrid("getSelected");
			if (!row) {
				$.messager.alert("提示", "请选择要删除的数据！");
				return;
			}
			if (g.editIndex!= null) {
				$.messager.confirm("请确认","您目前正在编辑数据，是否先取消编辑",function(r){
					if(r){
						g.reject();
					}
				});
				return;
			}
			$.messager.confirm("请确认","您确认删除该数据？",function(r){
				if (r) {
					var rowIndex = $(grid).datagrid("getRowIndex", row);
					
					$.messager.progress({"title":"处理删除中。。。"});
					
					$.post(options["url"]+"/delete",row,function(rd){
						$.messager.alert("提示","删除成功");
						$(grid).datagrid('deleteRow', rowIndex);
						g.editIndex = null;
					}).error(function(e){
						$.messager.alert("错误",e.responseText,"error");
					}).complete(function(){
						$.messager.progress("close");
					});
				}
			});

		};
		g.accept = function() {
			g.endEditing(function(){
				$(grid).datagrid('acceptChanges');
			})
		};
		g.reject = function() {
			if(g.editIndex!=null){
				
				$(grid).datagrid('cancelEdit', g.editIndex);
				if($(grid).datagrid("getRows")[g.editIndex][options["idField"]]==null){
					$(grid).datagrid(
							'deleteRow', g.editIndex);
				}
				g.editIndex = null;
			}
		};
		g.editData = function() {
			
			var row = $(grid).datagrid("getSelected");
			if (!row) {
				$.messager.alert("提示", "请选择要编辑的数据！")
				return;
			}
			var index = $(grid).datagrid("getRowIndex", row);
			
			if(g.editIndex != index) {
				g.endEditing(function(){
					$(grid).datagrid('beginEdit',index);
					g.editIndex = index;
				});
			}
		}

		return g;
	}

};



(function($){  
    $.fn.serializeJson=function(){  
        var serializeObj={};  
        var array=this.serializeArray();  
        var str=this.serialize();  
        $(array).each(function(){  
            if(serializeObj[this.name]){  
                if($.isArray(serializeObj[this.name])){  
                    serializeObj[this.name].push(this.value);  
                }else{  
                    serializeObj[this.name]=[serializeObj[this.name],this.value];  
                }  
            }else{  
                serializeObj[this.name]=this.value;   
            }  
        });  
        return serializeObj;  
    };  
})(jQuery);

$(function($){  
    //备份jquery的ajax方法  
    var _ajax=$.ajax;  
      
    //重写jquery的ajax方法  
    $.ajax=function(opt){  
        //备份opt中error和success方法  
        var fn = {  
            error:function(XMLHttpRequest, textStatus, errorThrown){},  
            success:function(data, textStatus){}  
        }  
        if(opt.error){  
            fn.error=opt.error;  
        }  
        if(opt.success){  
            fn.success=opt.success;  
        }  
          
        //扩展增强处理  
        var _opt = $.extend(opt,{  
            error:function(XMLHttpRequest, textStatus, errorThrown){  
                fn.error(XMLHttpRequest, textStatus, errorThrown);  
            },  
            success:function(data, textStatus){
            	var temp={};
            	if(typeof(data) == "string"){
            		try{
            			 if (data.match("^\{(.+:.+,*){1,}\}$"))
                         {
            				temp=$.parseJSON(data);
                         }
            		}catch (e) {
            			//console.log("返回非JSON对象");
        			}
            	}else if(typeof(data) == "object"&&!$.isArray(data)){
            		temp=data;
            	}
                if(temp['state'] == 600) {
                    window.location.href="login.html";
                    return;
                }
                fn.success(data, textStatus);
            }
        });  
        return _ajax(_opt);  
    };  
});

$(document).ajaxStart(function(){
	$.messager.progress({
		title:"请等待",
		msg:"数据请求中。。。"
	}); 
});
/**/
$(document).ajaxComplete(function(){
	$.messager.progress('close');

});
