function CheckLodop(){
   var oldVersion=LODOP.Version;
       newVerion="4.0.0.2";	
   if (oldVersion==null){
	document.write("<h3><font color='#FF00FF'>打印控件未安装!点击这里<a href='install_lodop.exe'>执行安装</a>,安装后请刷新页面。</font></h3>");
	if (navigator.appName=="Netscape")
	document.write("<h3><font color='#FF00FF'>（Firefox浏览器用户需先点击这里<a href='npActiveX0712SFx31.xpi'>安装运行环境</a>）</font></h3>");
   } else if (oldVersion<newVerion)
	document.write("<h3><font color='#FF00FF'>打印控件需要升级!点击这里<a href='install_lodop.exe'>执行升级</a>,升级后请重新进入。</font></h3>");
}

