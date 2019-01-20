drag = 0
move = 0


var ie = document.all;
var nn6 = document.getElementById && !document.all;
var isdrag = false;
var y, x, imgWidth = 0, imgHeight = 0, imgLeft = 0, imgTop = 0;
var oDragObj;

function moveMouse(e) {
	if (isdrag) {
		oDragObj.style.top = (nn6 ? nTY + e.clientY - y : nTY + event.clientY
				- y)
				+ "px";
		oDragObj.style.left = (nn6 ? nTX + e.clientX - x : nTX + event.clientX
				- x)
				+ "px";
		return false;
	}
}

function initDrag(e) {
	var oDragHandle = nn6 ? e.target : event.srcElement;
	var topElement = "HTML";
	while (oDragHandle.tagName != topElement
			&& oDragHandle.className != "dragAble") {
		oDragHandle = nn6 ? oDragHandle.parentNode : oDragHandle.parentElement;
	}
	if (oDragHandle.className == "dragAble") {
		isdrag = true;
		oDragObj = oDragHandle;
		nTY = parseInt(oDragObj.style.top + 0);
		y = nn6 ? e.clientY : event.clientY;
		nTX = parseInt(oDragObj.style.left + 0);
		x = nn6 ? e.clientX : event.clientX;
		document.onmousemove = moveMouse;
		return false;
	}
}
document.onmousedown = initDrag;
document.onmouseup = new Function("isdrag=false");

function clickMove1(s) {

	if (s == "up") {
		dragObj.style.top = parseInt(dragObj.style.top) + 100 + "px";
	} else if (s == "down") {
		dragObj.style.top = parseInt(dragObj.style.top) - 100 + "px";
	} else if (s == "left") {
		dragObj.style.left = parseInt(dragObj.style.left) + 100 + "px";
	} else if (s == "right") {
		dragObj.style.left = parseInt(dragObj.style.left) - 100 + "px";
	}

}

function smallit() {
	var height1 = images1.height;
	var width1 = images1.width;
	images1.height = height1 / 1.2;
	images1.width = width1 / 1.2;
}

function bigit() {
	var height1 = images1.height;
	var width1 = images1.width;
	images1.height = height1 * 1.2;
	images1.width = width1 * 1.2;
}

function featsize() {
	var width1 = images2.width;
	var height1 = images2.height;
	var width2 = 701;
	var height2 = 576;
	var h = height1 / height2;
	var w = width1 / width2;
	if (height1 < height2 && width1 < width2) {
		images1.height = height1;
		images1.width = width1;
	} else {
		if (h > w) {
			images1.height = height2;
			images1.width = width1 * height2 / height1;
		} else {
			images1.width = width2;
			images1.height = height1 * width2 / width1;
		}
	}
	block1.style.left = 0;
	block1.style.top = 0;
}

function onWheelZoom(obj) { // ��������
	zoom = parseFloat(obj.style.zoom);
	tZoom = zoom + (event.wheelDelta > 0 ? 0.05 : -0.05);
	if (tZoom < 0.1)
		return true;
	obj.style.zoom = tZoom;
	return false;
}
function dowImg() {
	window.open($(".bigImg").attr("src"));
}
function pringImg(){
	$("#printImg").attr("href","apply/printImg?url="+$(".bigImg").attr("src"));
	$("#printImg").click();
}
function onClickPic(src){
	$(".bigImg").attr("src",src)
	.load(
			function() {
				imgWidth = this.width;
				imgHeight = this.height;
				imgLeft = block1.style.left;
				imgTop = block1.style.top;
				//图片加载完成后预览
				openMask();
	});
}
function closeMask() {
	var oMask = document.getElementById('mask');
	var oImg = document.getElementById('img');
	var layer1 = document.getElementById('Layer1');
	oMask.style.display = 'none';
	oImg.style.display = 'none';
	layer1.style.display = 'none';
	realsize();
	
}
//图片预览
function openMask() {
	var oMask = document.getElementById('mask');
	var oImg = document.getElementById('img');
	var layer1 = document.getElementById('Layer1');
	oMask.style.display = 'block';
	oImg.style.display = 'block';
	layer1.style.display = 'block';
	oMask.style.width = document.documentElement.clientWidth + 'px';
	oMask.style.height = document.documentElement.clientHeight + 'px';
	//图片居中
	oImg.style.left = (document.documentElement.clientWidth - imgWidth) / 2
			+ 'px';
	oImg.style.top = (document.documentElement.clientHeight - imgHeight)
			/ 2 + 'px';
}
/**
 * 重置图片位置大小
 * @returns
 */
function realsize() {
/*	images1.height = imgHeight;
	images1.width = imgWidth;*/
	images1.style.zoom = 1;
	block1.style.left = imgLeft;
	block1.style.top = imgTop;

}
(function ($) {
	$(document).ready(function () {
		/** Coding Here */
	}).keydown(function (e) {
		if (e.which === 27) {
			closeMask();
		}
	});
})(jQuery);