$(function() {
	initSwich();
	$("[name=camera]").bind("change", function(e) {
		var x = $(e.target).val();
		ScanCtrl.SetCurDev(x);
		initDeivce();
	});
	initDeivce();
});

function initDeivce() {
	try {
		addResolution();
		var opts = $("#resolution").get(0).options;
		if (opts.length > 2) {
			$("#resolution").val(1);
			ScanCtrl.SetResolution(1);
		}
		ScanCtrl.StartPreviewEx();
	} catch (e) {
	}
}

function resolutionChange(value) {
	try {
		ScanCtrl.SetResolution(value);
		ScanCtrl.StartPreviewEx();
	} catch (e) {
	}

}

function addResolution() {
	try {
		var count = ScanCtrl.GetResolutionCount();
		$("#resolution").empty();
		for (i = 0; i < count; i++) {
			var w = ScanCtrl.GetResolutionWidth(i);
			var h = ScanCtrl.GetResolutionHeight(i);
			var str = w.toString() + "x" + h.toString();
			var opt = new Option(str, i);
			$("#resolution").append(opt);
		}
	} catch (e) {
	}

}

function takePic(url) {
	try {
		var date = new Date();
		var yy = date.getFullYear().toString();
		var mm = (date.getMonth() + 1).toString();
		var dd = date.getDate().toString();
		var hh = date.getHours().toString();
		var nn = date.getMinutes().toString();
		var ss = date.getSeconds().toString();
		var mi = date.getMilliseconds().toString();
		var picName = yy + mm + dd + hh + nn + ss + mi;

		var path = "D:\\" + picName + ".jpg";
		ScanCtrl.EnableDateRecord(1);
		var flag = ScanCtrl.Scan(path);
		if (flag) {
			alert(path)
		}
	} catch (e) {
	}
}

function initSwich() {
	var page = 1;
	var mb=3;
	// 向右滚动
	var offset=$(".scroll_list").offset()
	var origin = offset.left;
	
	
	
	
	$(".next").click(function() { // 点击事件
		var v_wrap = $(this).parents(".scroll"); // 根据当前点击的元素获取到父元素
		var v_show = v_wrap.find(".scroll_list"); // 找到视频展示的区域
		var v_li_width = v_show.find("li").width();
		var v_width =  (v_li_width+10)*mb;
		var len = v_show.find("li").length; // 我的视频图片个数
		var newOeigin = v_show.offset().left;
		var countLen=len*(v_li_width+10);
		var flag =  newOeigin-origin+countLen-(v_li_width+10)*4>=0?true:false;
		
		if (!v_show.is(":animated")) {
			if (flag) {
				v_show.animate({
					left : '-=' + v_width
				}, "slow");
			}
		}
	});
	// 向左滚动
	$(".prev").click(function() { // 点击事件
		var v_wrap = $(this).parents(".scroll"); // 根据当前点击的元素获取到父元素
		var v_show = v_wrap.find(".scroll_list"); // 找到视频展示的区域
		var v_li_width = v_show.find("li").width();
		var v_width =  (v_li_width+10)*mb;
		var len = v_show.find("li").length; // 我的视频图片个数
		
		var newOeigin = v_show.offset().left;
		var countLen=len*(v_li_width+10);
		var flag =  newOeigin<origin?true:false;
		
		if (!v_show.is(":animated")) {
			if(flag) {
				v_show.animate({
					left : '+=' + v_width
				}, "slow");
			}else{
				v_show.animate({
					left : 0
				}, "slow");
			}
		}
	});
	
	$(".scroll_list").find("[name=img-zplx]").change(function(e){
		var index  = $(e.target).attr("index");
		var v_wrap = $(this).parents(".scroll"); // 根据当前点击的元素获取到父元素
		var v_show = v_wrap.find(".scroll_list"); // 找到视频展示的区域
		var v_li_width = v_show.find("li").width();
		index=index-4<=0?0:index-4;
		var v_width = index*v_li_width;
		v_show.offset(offset);
		if (!v_show.is(":animated")) {
			v_show.animate({
				left : '-='+ v_width
			}, "slow");
		}
	});
}