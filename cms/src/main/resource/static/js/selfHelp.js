function openPage(param){
	var url = "checkAndRegister.html?vehSfxc="+param;
	url = encodeURI(url);
	window.location.href = url;
}