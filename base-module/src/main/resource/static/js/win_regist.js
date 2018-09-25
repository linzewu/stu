
$(function(){
	$("input[name=buttonOK]").parent().append("<input type=button class=button z_idx value='拍照' onClick=openWinOfyx()>");
});

function openWinOfyx(){
	var dzyxh_win_ip="10.39.147.126";
	var dzyxh_win_port="8080";
	var hphm=$("form[name=tmri] input[name=hphm]").val();
	var hpzl=$("form[name=tmri] input[name=hpzl]").val();
	var clsbdh=$("form[name=tmri] input[name=clsbdh]").val();
	var ywlx=$("form[name=tmri] input[name=ywlx]").val();
	var lsh=$("form[name=agentInfoForm] input[name=lsh]").val();
	
	if(lsh==""){
		alert("无法获取流水号，请先确定办理该业务！");
		return false;
	}
	
	var param="hphm="+hphm+"&hpzl="+hpzl+"&ywlx="+ywlx+"&clsbdh="+clsbdh+"&lsh="+lsh;
	
	window.open("http://"+dzyxh_win_ip+":"+dzyxh_win_port+"/dzyxh/page/jdccj.html?"+param)
}