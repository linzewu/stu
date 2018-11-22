$(function(){
	var nAgt = navigator.userAgent;
	if(window.navigator.userAgent.indexOf("Windows NT 6.1") != -1) { //window 7
		if ( (nAgt.indexOf("Trident"))!=-1 ) {   // IE ( ver >= ie7) In MSIE, the true version is after "MSIE" in userAgent
		    if((verOffset=nAgt.indexOf("MSIE"))!=-1){
		    	 $.messager.confirm('警告', '当前IE版本较低，为保证页面正常使用，请重新下载IE11进行使用', function(r){
		                if (r){
		                	window.open("../cache/download/IE11_Windows6.1_x86_zh_cn.exe");
		                }
		            });
		    }
		}
	}
})
