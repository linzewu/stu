var CreatedOKLodop7766=null;

function getLodop(oOBJECT,oEMBED){
/**************************
  本函数根据浏览器类型决定采用哪个页面元素作为Lodop对象：
  IE系列、IE内核系列的浏览器采用oOBJECT，
  其它浏览器(Firefox系列、Chrome系列、Opera系列、Safari系列等)采用oEMBED,
  如果页面没有相关对象元素，则新建一个或使用上次那个,避免重复生成。
  64位浏览器指向64位的安装程序install_lodop64.exe。
**************************/
	
	var strHtml1 = "打印控件未安装!请点击确定按钮安装,安装后请刷新页面或重新进入。</font>";
	var strHtml2 = "打印控件需要升级!请点击确定按钮执行升级,升级后请重新进入。</font>";
	var strHtml3 = "<font color='#FF00FF'>注意：<br>1：如曾安装过Lodop旧版附件npActiveXPLugin,请在【工具】->【附加组件】->【扩展】中先卸它;<br>2：如果浏览器表现出停滞不动等异常，建议关闭其“plugin-container”(网上搜关闭方法)功能;</font>";
	var LODOP;		
	try{	
	     //=====判断浏览器类型:===============
	     var isIE	 = (navigator.userAgent.indexOf('MSIE')>=0) || (navigator.userAgent.indexOf('Trident')>=0);
	     var is64IE  = isIE && (navigator.userAgent.indexOf('x64')>=0);
	     //=====如果页面有Lodop就直接使用，没有则新建:==========
	     if (oOBJECT!=undefined || oEMBED!=undefined) { 
               	 if (isIE) 
	             LODOP=oOBJECT; 
	         else 
	             LODOP=oEMBED;
	     } else { 
	    	 if (CreatedOKLodop7766==null){
          	     LODOP=document.createElement("object"); 
		     LODOP.setAttribute("width",0); 
                     LODOP.setAttribute("height",0); 
		     LODOP.setAttribute("style","position:absolute;left:0px;top:-100px;width:0px;height:0px;");  		     
                     if (isIE) LODOP.setAttribute("classid","clsid:2105C259-1E0C-4534-8141-A753534CB4CA"); 
		     else LODOP.setAttribute("type","application/x-print-lodop");
		     document.documentElement.appendChild(LODOP); 
	             CreatedOKLodop7766=LODOP;		     
 	         } else 
                LODOP=CreatedOKLodop7766;
	     };
	     //=====判断Lodop插件是否安装过，没有安装或版本过低就提示下载安装:==========
	     if ((LODOP==null)||(typeof(LODOP.VERSION)=="undefined")) {
	             if (navigator.userAgent.indexOf('Chrome')>=0){
	            	 $.messager.alert("警告", strHtml1, 'warn', function() {
	 					window.open('../print/install_lodop.exe');
	 				});
	             }else if(navigator.userAgent.indexOf('Firefox')>=0){
	            	 $.messager.alert("警告", strHtml1, 'warn', function() {
		 					window.open('../print/install_lodop.exe');
		 				});
	             }else if (is64IE){
	            	 $.messager.alert("警告", strHtml1, 'warn', function() {
		 					window.open('../print/install_lodop64.exe');
		 				});
	             }else if (isIE){
	            	 $.messager.alert("警告", strHtml1, 'warn', function() {
		 					window.open('../print/install_lodop.exe');
		 				});
	             }else{
	            	 $.messager.alert("警告", strHtml1, 'warn', function() {
		 					window.open('../print/install_lodop.exe');
		 				});
	             }
	                 
	             return LODOP;
	     } else if (LODOP.VERSION<"6.1.9.8") {
	    	 console.log("LODOP.VERSION " + LODOP.VERSION)
	             if (is64IE){
	            	 $.messager.alert("警告", strHtml2, 'warn', function() {
		 					window.open('../print/install_lodop64.exe');
		 				});
	             } else
	             if (isIE){
	            	 $.messager.alert("警告", strHtml2, 'warn', function() {
		 					window.open('../print/install_lodop.exe');
		 				});
	             } else{
	            	 $.messager.alert("警告", strHtml2, 'warn', function() {
		 					window.open('../print/install_lodop.exe');
		 				});
	             }
	           
	    	     return LODOP;
	     };
	     return LODOP; 
	} catch(err) {
		 if (is64IE){
			 $.messager.alert("错误", strHtml1, 'error', function() {
					window.open('../print/install_lodop64.exe');
				});
		 }else{
			 $.messager.alert("错误", strHtml1, 'error', function() {
					window.open('../print/install_lodop.exe');
				});
		 }
	     return LODOP; 
	};
}
