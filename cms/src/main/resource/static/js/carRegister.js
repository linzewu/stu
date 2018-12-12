function changeBT(value) {
	if (value == "A") {
		$("#query_hphm").linkbutton("disable");
	} else {
		$("#query_hphm").linkbutton("enable");
	}
}

function setPring() {
	LODOP.SELECT_PRINTER();
}

function getGgbh(value) {
	if (value != null && $.trim(value) != "") {
		$.messager.progress({
			title : '加载公告日期',
			msg : '数据加载中...'
		});
		var param = {};

		//param.mType = "trafficDBManager";
		//param.method = "getGongGaoListbyCLXH";
		param.clxh = value;
		$.post("../../preCarRegister/getGongGaoListbyCLXH", param, function(data) {
			if (data != null && data.length > 0) {
				$("#ggbh").combobox("loadData", data);

				var index = 0;
				for (var i = 0; i < data.length; i++) {
					if (data[i]['CLXH '] == value) {
						index = i;
						break;
					}
				}
				// $("#ggbh").combobox("select", data[index]['BH']);
			} else {
				$("#ggbh").combobox("loadData", []);
				$("#ggbh").combobox("setValue", '');
				$("#ggbh").combobox("setText", '');
			}
			$.messager.progress('close');

		}, "json");
	} else {
		$("#ggbh").combobox("loadData", []);
		$("#ggbh").combobox("setValue", '');
		$("#ggbh").combobox("setText", '');
	}
}

function save() {
	$("#isPrint").val("false");
	$('#myform').submit();
}

function saveAndPring() {
	$('#myform').form({
		url: "../../preCarRegister/savePreCarRegister"
	});
	var isValid = $('#myform').form('validate');
	if(isValid){
		var params = $("#myform").serializeJson();
		$.post("../../preCarRegister/savePreCarRegister",params,function(data){
			if(data.state==1){
				$.messager.alert("提示","保存成功！","info",function(){
					$('#myform').form('clear');
					$("#ywlx").combobox("setValue","A");
				});
				//$("#printTemplet img").attr("src","../cache/report/template_ptc_01_"+data.data+".jpg");
				var printObj = {};
				printObj.prview = false;
				if(params.isView == "true"){
					printObj.prview = true;
				}
				printObj.src="../cache/report/template_ptc_01_"+data.data+".jpg";
				printImgCYD(printObj);
				//printCYD(printObj);
			}else{
				$.messager.alert("提示",data.message,"error");
			}
			
		},"json").complete(function(){
			$.messager.progress('close');
		});
	}
	
	
	
}

function implSaveAndPring() {	
	
	$('#myformImpl').form({
		url: "../../preCarRegister/savePreCarRegister"
	});
	var isValid = $('#myformImpl').form('validate');
	if(isValid){
		var params = $("#myformImpl").serializeJson();
		$.post("../../preCarRegister/savePreCarRegister",params,function(data){
			if(data.state==1){
				$.messager.alert("提示","保存成功！","info",function(){
					$('#myformImpl').form('clear');
					$("#ywlx").combobox("setValue","A");
				});
				//$("#printTemplet img").attr("src","../cache/report/template_ptc_01_"+data.data+".jpg");
				var printObj = {};
				printObj.prview = false;
				if(params.isView == "true"){
					printObj.prview = true;
				}
				printObj.src="../cache/report/template_ptc_01_"+data.data+".jpg";
				printImgCYD(printObj);
				//printCYD(printObj);
			}else{
				$.messager.alert("提示",data.message,"error");
			}
			
		},"json").complete(function(){
			$.messager.progress('close');
		});
	}
}

function clearData(formId){
	$('#'+formId).form('clear');
}

function updateAndPring() {
	$("#isPrint").val("true");
	$('#myformEdit').submit();
	
	$('#myformEdit').form({
		url: "../../preCarRegister/updateRegister"
	});
	var isValid = $('#myformEdit').form('validate');
	if(isValid){
		var params = $("#myformEdit").serializeJson();
		$.post("../../preCarRegister/updateRegister",params,function(data){
			if(data.state==1){
				$.messager.alert("提示","保存成功！","info",function(){
					$('#myformEdit').form("clear");
				});
				//$("#printTemplet img").attr("src","../cache/report/template_ptc_01_"+data.data+".jpg");
				
				var cydata = {};
				cydata.src="../cache/report/template_ptc_01_"+data.data+".jpg";
				cydata.prview = false;
				if(params.isView == "true"){
					cydata.prview = true;
				}
				cydata['ywlx'] = $("#ywlx").combobox("getValue");
				cydata['hpzl'] = $("#hpzl").combobox("getValue");
				cydata['id'] = datajson['sid'];
				cydata['clsbdh'] = $("#clsbdh").val();
				cydata['hphm'] = $("#hphm").textbox("getValue");
			
				printImgCYD(cydata);
				//printCYD(printObj);
			}else{
				$.messager.alert("提示",data.message,"error");
			}
			
		},"json").complete(function(){
			$.messager.progress('close');
		});
	}
	/////////
}

function CheckIsInstall() {
	try {
		var LODOP = getLodop(document.getElementById('LODOP_OB'), document
				.getElementById('LODOP_EM'));
	} catch (err) {
		// alert("Error:本机未安装或需要升级!");
	}
}

CheckIsInstall();

function scan(obj) {
	var newValue = $(obj).val();
	var dataArray = newValue.split("|");
	if (dataArray.length == 22
			&& newValue.substring(newValue.length - 1, newValue.length) == "|") {
		$("#clxh").textbox("setValue", dataArray[6]);
		$("#clsbdh").textbox("setValue", dataArray[7]);
		$("#fdjh").val(dataArray[9]);
		$("#hdzk").textbox("setValue", dataArray[15]);
		$("#csys").textbox("setValue", dataArray[17]);
		$("#ywlx").combobox("select", "A");
		$("#ewm").val($(obj).val());
		$("#codes").val('');
	}
}

function checkFormNumber() {
	var qlj = $("#qlj").val();
	var hlj = $("#hlj").val();
	var zj = $("#zj").val();
	var cwkc = $("#cwkc").val();
	var cwkk = $("#cwkk").val();
	var cwkg = $("#cwkg").val();

	var ltgg = $("#ltgg").val();

	if (isNaN(qlj)) {
		$.messager.alert("提示", "前轮距只能输入数字！")
		return false;
	}
	if (isNaN(hlj)) {
		$.messager.alert("提示", "后轮距只能输入数字！")
		return false;
	}
	if (isNaN(zj)) {
		$.messager.alert("提示", "轴距只能输入数字！")
		return false;
	}
	if (isNaN(cwkc)) {
		$.messager.alert("提示", "车外廓长只能输入数字！")
		return false;
	}
	if (isNaN(cwkk)) {
		$.messager.alert("提示", "车外廓宽只能输入数字！")
		return false;
	}
	if (isNaN(cwkg)) {
		$.messager.alert("提示", "车外廓高只能输入数字！")
		return false;
	}

	if (ltgg.split(",").length > 1) {
		$.messager.alert("提示", "请根据车辆合格证填写唯一的轮胎规格！")
		return false;
	}

	return true;

}

function checkData(param) {
	var isValid = $(this).form('validate');
	if (isValid) {
		var param = {};
		param.mType = "preCarRegisterManager";
		param.method = "checkPower";
		
		var ywlx = $("#ywlx").combobox("getValue");
		if(ywlx=='A'){
			param.hphm = $("#clsbdh").textbox("getValue");
		}else{
			param.hphm = $("#hphm").textbox("getValue");
		}
		var r = $.ajax({
			url : "baseManager!!multipleManager.action?timestamp="
					+ new Date().getTime(),
			async : false,
			data : param
		}).responseText;
		if (isNaN(r)) {
			$.messager.alert('警告', "验证车辆合格信息错误", 'error');
			return false;
		}
		if (Number(r) == 0) {
			return true;
		} else {
			$.messager.alert('警告', "该车辆60天内检测不合格，需要授权才能登记", 'info');
			return false;
		}
	}
	return isValid;
}

// 进口车
$('#myformImpl').form({
	success : function(data) {
		var datajson = $.parseJSON(data);
		if (datajson['state'] == 200) {
			var isprint = $("#isPrint").val();
			if (isprint == "true") {
				var cydata = {};
				cydata['ywlx'] = $("#ywlx").combobox("getValue");
				cydata['hpzl'] = $("#hpzl").combobox("getValue");
				cydata['id'] = datajson['sid'];
				cydata['clsbdh'] = $("#clsbdh").val();
				//cydata['syxz'] = $("#syxz").combobox("getValue");
				printCYD(cydata);
			}
			$.messager.alert('提示', "保存成功", 'info', function() {
				$('#myform').form("clear");
				$("#bType").val("preCarRegister");
				$("#method").val("saveRegister");
			});
		} else {
			$.messager.alert('提示', "保存出错", 'error');
		}
	}
});

// 国产车信息修改
$('#myformEdit').form({
	onSubmit : function(param) {
		var isValid = $(this).form('validate');
		return isValid;
	},
	success : function(data) {
		var datajson = $.parseJSON(data);
		if (datajson['state'] == 200) {
			var isprint = $("#isPrint").val();
			if (isprint == "true") {
				var cydata = {};
				cydata['ywlx'] = $("#ywlx").combobox("getValue");
				cydata['hpzl'] = $("#hpzl").combobox("getValue");
				cydata['id'] = datajson['sid'];
				cydata['clsbdh'] = $("#clsbdh").val();
				cydata['hphm'] = $("#hphm").textbox("getValue");
			
				printCYD(cydata);


			}
			$.messager.alert('提示', "保存成功", 'info', function() {
				$('#myformEdit').form("clear");
				$("#bType").val("preCarRegister");
				$("#method").val("saveRegister");
			});
		} else {
			$.messager.alert('提示', "保存出错", 'error');
		}
	}
});
/**
$('#myform').form({
	onSubmit : checkData,
	success : function(data) {
		var datajson = $.parseJSON(data);
		if (datajson['state'] == 200) {
			var isprint = $("#isPrint").val();
			if (isprint == "true") {

				var cydata = {};
				cydata['ywlx'] = $("#ywlx").combobox("getValue");
				cydata['hpzl'] = $("#hpzl").combobox("getValue");
				cydata['id'] = datajson['sid'];
				cydata['clsbdh'] = $("#clsbdh").val();
				cydata['hphm'] = $("#hphm").textbox("getValue");
				//cydata['syxz'] = $("#syxz").combobox("getValue");
				printCYD(cydata);

			}
			$.messager.alert('提示', "保存成功", 'info', function() {
				$('#myform').form("clear");
				$("#bType").val("preCarRegister");
				$("#method").val("saveRegister");
			});
		} else {
			$.messager.alert('提示', "保存出错", 'error');
		}
	}
});

$.post("a.html", null, function(data) {
	$("#printTemplet").html(data);
});**/

function loadCarInfo() {
	var hpzl = $("#hpzl").combobox("getValue");
	var hphm = $("#hphm").textbox("getValue");
	if (hpzl == "") {
		$.messager.alert("提示", "请选择号牌种类", "error");
		return;
	}
	if (hphm == "") {
		$.messager.alert("提示", "请输入位号牌号码", "error");
		return;
	}

	var param = {};
	//param.mType = "preCarRegisterManager";
	//param.method = "getCarInfoByCarNumber";
	param.hpzl = hpzl;
	param.hphm = hphm;

	$.messager.progress({
		title : '加载机动车信息',
		msg : '数据加载中...'
	});

	$.post("../../preCarRegister/getCarInfoByCarNumber", param, function(data) {
		if (data != null) {
			$('#myform').form('load', data);
		} else {
			$.messager.alert("提示", "该号牌号码不存在！");
		}
		$.messager.progress('close');
	}, "json");
}

function gongGaoChange(value) {
	if (value != null && value != "" && value.length == 14) {
		var param = {};
		//param.mType = "trafficDBManager";
		//param.method = "getGongGaoInfoByGgbh";
		param.ggbh = value;
		loadInfo("../../preCarRegister/getGongGaoInfoByGgbh", param, "gonggaoForm",
				function(data) {
					$("#cllx").combobox("select", data.cllx);
				});
	}

}

function loadGonggao() {
	var ggbh = $("#ggbh").combobox("getValue");

	if (ggbh == "") {
		$.messager.alert("提示", "公告编号不能为空");
		return;
	} else {
		$("#win").window("open");
	}
}

function colorChange(value) {
	$("#csys").textbox("setValue", getLabelByid("csys", value.toUpperCase()));
}

function getHlj(m_SourceValue, m_zs) {
	// 是否为数字
	var newgbthps;
	var i_lj = 0;

	if (m_SourceValue.length == 0) {
		return "";
	}
	var i_zs = parseInt(m_zs);
	if (m_SourceValue.indexOf("/") > 0) {
		var paraArray = m_SourceValue.split("/"); // 根据","分割成字符串数组
		if (i_zs == 3) {
			i_lj = parseInt(paraArray[0]);
		} else {
			i_lj = parseInt(paraArray[paraArray.length - 2]);
		}
	} else {
		if (m_SourceValue.indexOf("+") > 0) {
			var paraArray = m_SourceValue.split("+"); // 根据","分割成字符串数组
			var i;
			for (i = 0; i < paraArray.length; i++) {
				if (!isNaN(paraArray[i])) {
					// 如为数字，直接判断
					i_lj += parseInt(paraArray[i]);
				}
			}
		} else {
			i_lj = parseInt(m_SourceValue);
		}
	}
	return i_lj;
}

function getZj(m_SourceValue) {

	// 是否为数字
	var newgbthps;
	var i_zj = 0;
	if (m_SourceValue.length == 0) {
		i_zj = 0;
	}
	var paraArray = m_SourceValue.split("+"); // 根据","分割成字符串数组
	var i;
	for (i = 0; i < paraArray.length; i++) {
		if (!isNaN(paraArray[i])) {
			// 如为数字，直接判断
			i_zj += parseInt(paraArray[i]);
		}
	}
	return i_zj;
}

function readQrtext() {
	var barcodetemp = "";
	try{
		var strbarcode = vehPrinter.GetQrText();
		if (strbarcode == "") {
			return 0;
		}
		if (strbarcode == "-1") {
			$.messager.alert("提示", "条码信息有误!");
			return 0;
		}
		var barArray = strbarcode.split("|");
	
		var strBarCodeType = barArray[0];
		if (strBarCodeType.split("_")[0] == "ZCCCHGZ") {
			loadScanInfo(barArray, strbarcode);
			return;
		}
	
		if (strBarCodeType == "SFZXX_2.0") {
			loadSFZXX(barArray);
			return;
		}
	}catch (e) {
		return ;
	}

}

function loadSFZXX(barArray) {
	$("#syr").textbox("setValue", barArray[1]);
	$("#sfz").textbox("setValue", barArray[6]);
	$("#dz").textbox("setValue", barArray[5]);
}

function loadScanInfo(barArray, strbarcode) {
	var dataInfo = {};

	dataInfo["hgzbh"] = barArray[2];
	dataInfo["ccrq"] = barArray[3];

	dataInfo["zzcmc"] = barArray[4];
	// dataInfo["cllx"]=barArray[6];

	var clpps = barArray[7].split("/");
	if (clpps.length > 1) {
		dataInfo["clpp1"] = clpps[0];
		dataInfo["clpp2"] = clpps[1];
	} else {
		dataInfo["clpp1"] = clpps[0];
	}
	dataInfo["clxh"] = barArray[8];

	dataInfo["csys"] = barArray[9];

	dataInfo["clsbdh"] = barArray[13];
	dataInfo["fdjh"] = barArray[15];
	dataInfo["fdjxh"] = barArray[16];

	dataInfo["rlzl"] = barArray[17];
	dataInfo["pl"] = barArray[19];
	dataInfo["gl"] = barArray[20];
	dataInfo["zxxs"] = barArray[21];

	var qljs = barArray[22].split("/");

	dataInfo["qlj"] = qljs[0];

	// 计算后轮距 29是轴数
	var hlj = getHlj(barArray[23], barArray[29]);

	dataInfo["hlj"] = hlj;

	dataInfo["lts"] = barArray[24];
	dataInfo["ltgg"] = barArray[25];
	dataInfo['hbdbqk'] = barArray[18];
	dataInfo['dpid'] = barArray[11];

	var zxzs = 1;
	if (qljs.length > 0) {
		zxzs = qljs.length;
	}

	var gbthps = "";
	if (barArray[6] == "挂车") {
		gbthps = getGbthps("-/" + barArray[26], 1);
	} else {
		gbthps = getGbthps(barArray[26], zxzs);
	}

	dataInfo["gbthps"] = gbthps;
	var zj = getZj(barArray[27]);
	dataInfo["zj"] = zj;

	dataInfo["zs"] = barArray[29];
	dataInfo["cwkc"] = barArray[30];
	dataInfo["cwkk"] = barArray[31];

	dataInfo["cwkg"] = barArray[32];
	dataInfo["hxnbcd"] = barArray[33];
	dataInfo["hxnbkd"] = barArray[34];
	dataInfo["hxnbgd"] = barArray[35];
	dataInfo["zzl"] = barArray[36];

	dataInfo["hdzzl"] = barArray[37];
	dataInfo["zbzl"] = barArray[38];
	dataInfo["zqyzl"] = barArray[40];

	var hdzks = barArray[41].split("/");
	dataInfo["hdzk"] = hdzks[0];

	var qpzks = barArray[43].split("+");

	dataInfo["qpzk"] = qpzks[0];
	if (qpzks.length > 1) {
		dataInfo["hpzk"] = qpzks[1];
	}
	dataInfo["bz"] = barArray[50];
	$("#ewm").val(strbarcode);
	$("#ywlx").combobox("select", "A");
	$('#myform').form('load', dataInfo);
}

function getGbthps(m_SourceValue, m_zxzs) {

	// 是否为数字
	var newgbthps;
	var i_gbthps = 0;
	if (m_SourceValue.length == 0) {
		return "";
	}
	// m_SourceValue="-/"+m_SourceValue;
	var paraArray = m_SourceValue.split(","); // 根据","分割成字符串数组
	var i;
	var j;
	var k
	for (i = 0; i < paraArray.length; i++) {
		if (!isNaN(paraArray[i])) {
			// 如为数字，直接判断
			i_gbthps = parseInt(paraArray[i]);
		} else {
			i_gbthps = 0;
			if (paraArray[i].indexOf("/") > -1) {

				for (k = 0; k < m_zxzs; k++) {
					paraArray[i] = paraArray[i].substr(paraArray[i]
							.indexOf("/") + 1)
				}
				paraArray[i] += "/";

				while (paraArray[i].indexOf("/") > -1) {
					newgbthps = paraArray[i].substring(0, paraArray[i]
							.indexOf("/"));
					if (!isNaN(newgbthps)) {
						i_gbthps += parseInt(newgbthps);
					} else {
						if (newgbthps.indexOf("+") > 0) {
							var valueSplit = newgbthps.split("+");
							for (j = 0; j < valueSplit.length; j++) {
								// 为了应对钢板弹簧片数形式为‘-/8+-’的情况，所以增加valueSplit[j]是否为数字的判断
								if (!isNaN(valueSplit[j])) {
									i_gbthps += parseInt(valueSplit[j]);
								}
							}
							// i_gbthps = i_gbthps+(parseInt(valueSplit[0]) +
							// parseInt(valueSplit[1]));
						}
					}
					paraArray[i] = paraArray[i].substr(paraArray[i]
							.indexOf("/") + 1);
				}
			} else {
				if (paraArray[i].indexOf("+") > 0) {
					var valueSplit = paraArray[i].split("+");
					for (j = 0; j < valueSplit.length; j++) {
						if (!isNaN(valueSplit[j])) {
							i_gbthps += parseInt(valueSplit[j]);
						}
					}
					// i_gbthps=parseInt(valueSplit[0]) +
					// parseInt(valueSplit[1]);
				}
			}
		}
	}
	if (i_gbthps == 0) {
		return "";
	} else {
		return i_gbthps;
	}
}

// /////////////// 列表页面js ///////////////////////////

function formatterCllx(value) {
	return getLabelByid("cllx", value);
}

function formatterCsys(value) {
	return getLabelByid("csys", value);
}

function formatterHpzl(value) {
	return comm.getParamNameByValue("hpzl", value);
}

function formatterYwlx(value) {
	return getLabelByid("ywlx", value);
}

function formatterSyxz(value) {
	return getLabelByid("syxz", value);
}

function print() {
	var row = $("#preCarRegisterDG").datagrid("getSelected");
	if (row != null) {
		//
		$.post("../../preCarRegister/printCarInfo",{"lsh":row.lsh},function(data){
			if(data.state==1){
				//$("#printTemplet img").attr("src","../cache/report/template_ptc_01_"+data.data+".jpg");
				var isView = $("#isView").checkbox("options").checked;
				var printObj = {};
				printObj.prview = isView;
				printObj.src="../cache/report/template_ptc_01_"+data.data+".jpg";
				printImgCYD(printObj)
				//printCYD(printObj);
			}else{
				$.messager.alert("提示",data.message,"error");
			}
			
		},"json").complete(function(){
			$.messager.progress('close');
		});
		//printCYD(row);
	} else {
		$.messager.alert("提示", "请选择需要打印的数据", "warning");
	}
}

function printCYD(option) {
	var prview=false;
	var setup=false;
	if(option){
		if(option.prview){
			prview=option.prview
		}
		if(option.setup){
			setup=option.setup
		}
	}
	var LODOP = getLodop(document.getElementById('LODOP_OB'),
			document.getElementById('LODOP_EM'));
	LODOP.ADD_PRINT_HTM(-35, 0, 1024, 1200, document
			.getElementById("printTemplet").innerHTML);
	
	if(setup){
		LODOP.PRINT_SETUP();
	}else{
		if(prview){
			LODOP.PREVIEW();
		}else{
			LODOP.PRINT();
		}
	}
}


function printImgCYD(option) {
	var prview=false;
	var setup=false;
	if(option){
		if(option.prview){
			prview=option.prview
		}
		if(option.setup){
			setup=option.setup
		}
	}
	var LODOP = getLodop(document.getElementById('LODOP_OB'),
			document.getElementById('LODOP_EM'));
	LODOP.ADD_PRINT_IMAGE(-35,0,1024,1200,"<img border='0' src='"+option.src+"' />");
	LODOP.SET_PRINT_STYLEA(0,"Stretch",2);//按原图比例(不变形)缩放模式
	if(setup){
		LODOP.PRINT_SETUP();
	}else{
		if(prview){
			LODOP.PREVIEW();
		}else{
			LODOP.PRINT();
		}
	}
}

function getLodopObj() {
	var LODOP = getLodop(document.getElementById('LODOP_OB'),
			document.getElementById('LODOP_EM'));
	return LODOP;
}



function deving(){
	$.messager.alert("提示","正在开发中....")
}

function loadParam(){
	var clxh = $("#clxh").val();
	if(clxh==""){
		$.messager.alert("提示","请输入的车辆型号！");
		return
	}
	
	if(clxh.length<7){
		$.messager.alert("提示","请输入7到8位车辆型号！");
		return
	}
	
	var queryParams={"clxh":clxh};
	
	var isParse = $("#implCarParamListIsParse").val();
	if(isParse=="false"){
		$("#implCarParamList").datagrid({queryParams:queryParams});
	}else{
		$("#implCarParamList").datagrid("reload",queryParams);
	}
	$("#win").window("open");
}

function loadImplCarParam(index,row){
	$("#myformImpl").form("load",row);
	$("#win").window("close");
}

function shSaveAndPring() {
	if($("#syrs").text() == "" || $("#sfzs").text() == ""){
		$.messager.alert("提示","请将二代身份证放置在身份证识别区","info");
		return;
	}
	if($("#clsbdh_text").text() == "" || $("#clxh_text").text() == ""){
		$.messager.alert("提示","请扫描车辆合格证","info");
		return;
	}
	var sjhm = $("#sjhm").val();
	if(sjhm == ""){
		$.messager.alert("提示","请输入手机号码","info");
		$("#sjhm").click();
		return;
	}
	if (!(/^(13|15|18)\d{9}$/i.test(sjhm))){
		$.messager.alert("提示","手机号码格式不正确","info");
		$("#sjhm").click();
		return;
	}
	
	$('#shForm').form({
		url: "../../preCarRegister/savePreCarRegister"
	});
	var isValid = $('#shForm').form('validate');
	if(isValid){
		var params = $("#shForm").serializeJson();
		$.post("../../preCarRegister/savePreCarRegister",params,function(data){
			if(data.state==1){
				$.messager.alert("提示","保存成功！","info",function(){
					$('#shForm').form('clear');
					$("#syr").text("");
					$("#sfz").text("");
					$("#dz").text("");
					$("#clsbdh").text("");
					$("#clxh").text("");
					$("#fdjh").text("");
					$("#csys").text("");
					vehSfxc = "";
					window.location.href="shIndex.html";
				});
				$("#printTemplet img").attr("src","../cache/report/template_ptc_01_"+data.data+".jpg");
				printCYD();
			}else{
				$.messager.alert("提示",data.message,"error");
			}
			
		},"json").complete(function(){
			$.messager.progress('close');
			window.location.href="shIndex.html";
		});
	}
	
	
	
}


$.extend($.fn.validatebox.defaults.rules, {
    cc: {
        validator: function(value, param){
        	
        	if(value.indexOf(",")>-1){
        		return false
        	}else{
        		return true;
        	}
        },
        message: '请确认该项数据的唯一性！'
    }
});
