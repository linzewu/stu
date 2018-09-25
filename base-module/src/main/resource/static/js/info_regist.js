
$(function(){
	$("input[name=buttonOK]").parent().append("<input type=button class=button z_idx value='查看档案照片' onClick=openWinOfyx()>");
});

function openWinOfyx(){
	var dzyxh_win_ip="10.39.147.126";
	var dzyxh_win_port="8080";
	var hphm=$("form[name=tmri] input[name=hphm]").val();
	var hpzl=$("form[name=tmri] input[name=hpzl]").val();
	var clsbdh=$("form[name=tmri] input[name=clsbdh]").val();
	var ywlx=$("form[name=tmri] input[name=ywlx]").val();
	var lsh=$("form[name=agentInfoForm] input[name=lsh]").val();
	
	console.log("hphm:"+hphm);
	console.log("hpzl:"+hpzl);
	console.log("clsbdh:"+clsbdh);
	console.log("ywlx:"+ywlx);
	console.log("lsh:"+lsh);
	if(clsbdh==""){
		alert("车辆识别代号不能为空！");
		return false;
	}
	
	var param="hphm="+hphm+"&hpzl="+hpzl+"&ywlx="+ywlx+"&clsbdh="+clsbdh+"&lsh="+lsh;
	
	window.open("http://"+dzyxh_win_ip+":"+dzyxh_win_port+"/dzyxh/page/jdccj.html?"+param)
}