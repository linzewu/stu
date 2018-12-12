$(function(){
	var DEFAULT_VERSION = 8.0;
	var nAgt = window.navigator.userAgent;
	if(nAgt.indexOf("Windows NT 6.1") != -1 || nAgt.indexOf("Windows 7") != -1) { //window 7
		if ( (nAgt.indexOf("Trident"))!=-1 ) {   // IE ( ver >= ie7) In MSIE, the true version is after "MSIE" in userAgent
		    if((verOffset=nAgt.indexOf("MSIE"))>-1){   //判断是否是ie
		    	 var ua = nAgt.toLowerCase();  
		    	 var safariVersion =  ua.match(/msie ([\d.]+)/)[1];
		    	 if(safariVersion <= DEFAULT_VERSION ){  
		    	      $.messager.confirm('警告', '系统检测到您正在使用ie8以下内核的浏览器，不能实现完美体验，请更换或升级为浏览器IE9访问！', function(r){
			                if (r){			                	
			                	if(ua.indexOf("win64")>=0||ua.indexOf("wow64")>=0){
			                		window.open("../cache/download/ie9_64bit.exe");
			                	}
			                	
			                	if (ua.indexOf("win32") >= 0 || ua.indexOf("wow32") >= 0) {
			                		window.open("../cache/download/ie9_32bit.exe");
			        	    	}			                	
			                }
			            });
		    	 }; 		    	 
		    }
		}
	}
})
