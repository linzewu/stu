function getImgCount(){
	var scanObj = document.getElementById('scaner1');
	if(!scanObj){
		return;
	}
	var imgCount = scanObj.imagesCount;
	var index = currIndex;
	var lsh = $("#sm_lsh").textbox("getValue");
	if($.trim(lsh)!="" && imgCount > currIndex){
		setTimeout(function(){
			uploadFile(index);
			
		}, 500);
		currIndex++;
	}
}
window.setInterval(getImgCount,200);