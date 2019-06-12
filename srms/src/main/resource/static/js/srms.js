var qrtNumber=0;
function hpzlFormat(value,row,index){
	return comm.getParamNameByValue("hpzl", value)
}
function ywlxFormat(value,row,index){
	return comm.getParamNameByValue("ywlx", value)
}

function ztFormat(value,row,index){
	return comm.getParamNameByValue("dazt", value)
}

function readQrLabel() {
	
	//var strbarcode = "123";
	try{
		var strbarcode = srmBarCode.GetQrText();
		if (strbarcode == "") {
			return 0;
		}
		if (strbarcode == "-1") {
			$.messager.alert("提示", "条码信息有误!");
			return 0;
		}
		findCarByLsh(strbarcode);
				
	}catch (e) {
		alert(e);
		return ;
	}

}

function findCarByLsh(lsh){
	$.post("../../archivalCase/findCarInfoByBarCode",{"barCode":lsh},function(data){
		if (data.state == "1"){
			$("#rk_hphm").textbox("setValue",data.data['HPHM']);
			$("#rk_hpzl").combobox("setValue",data.data['HPZL']);	
			$("#rk_clsbdh").textbox("setValue",data.data['CLSBDH']);
			$("#rk_ywlx").combobox("setValue",data.data['YWLX']);	
		}
		
	},"json").complete(function(){
	});
}

function printBQ(printData){
	
	$("#l_hphm").text(printData.hphm);
	$("#l_hpzl").text(comm.getParamNameByValue("hpzl", printData.hpzl));
	$("#l_clsbdh").text(printData.clsbdh);
	$.post("../../archivalFiling/createLabel",{"barCode":printData.barCode},function(data){
		$("#barImg").attr("src","../cache/barcode/"+printData.barCode+".jpg")
		var obj = {};
		obj.prview= false;
		printCYD(obj);
	},"json").complete(function(){
		$.messager.progress('close');
	});
	//$("#bcTarget").empty().barcode("B-16-002-3-1-001", "code39",{barWidth:1, barHeight:12,showHRI:true,moduleSize: 2});
	
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
	LODOP.SET_LICENSES("上海翔尚信息技术有限公司","E61FDF8932E17DEFD1AFD1E7F683CA26","","");
	var strBodyStyle="<style>"+document.getElementById("style1").innerHTML+"</style>";
	var strFormHtml=strBodyStyle+"<body>"+document.getElementById("printTemplet").innerHTML+"</body>";
	LODOP.SET_PRINT_PAGESIZE(0,580,400,"");
	LODOP.ADD_PRINT_HTM(0, 0, 215, 220, strFormHtml);

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

function exportExcel(){
	var quer_createTime=$("#quer_createTime").datebox("getValue");	
	if(quer_createTime == ""){
		$.messager.alert("提示","请选择入库时间!","warning");
		return;
	}
	window.location = "../../archivalFiling/export?rksj="+quer_createTime;	
}

$.extend(
		$.fn.validatebox.defaults.rules,
		{
			
			rackNoVad : {
				validator : function(value, param) {
					
					var bflag=false;
					//param[1]='';
					
					//if(aflag){
						var data={};
						data.rackNo=value;
						var r = $.ajax({url:'../../storeRoom/validateRackNo',dataType:"json",data:data,async:false,cache:false,type:"post"}).responseText;
						bflag=r=="true";
						if(!bflag){
							param[1]='档案架编号已存在';
						}
					//}
					
					
					
					return bflag;
				},
				message : '{1}'
			}
		});
		