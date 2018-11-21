function openPage(param){
	var url = "checkAndRegisterCar.html?vehSfxc="+param;
	url = encodeURI(url);
	window.location.href = url;
}