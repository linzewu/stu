//刷新性能检验记录单
function refreshXnjyjld(){
	$.post("/veh/checkReport/printJyReport",{lsh:""},function(data){
		if(data.state==1){
			
			$("#printTemplet img").attr("src","../cache/report/"+data.data);
//			var printObj = {};
//			printObj.prview = false;
//			if(params.isView == "true"){
//				printObj.prview = true;
//			}
//			printCYD(printObj);
		}else{
			$.messager.alert("提示",data.message,"error");
		}
		
	},"json").complete(function(){
		$.messager.progress('close');
	});
}
//打印性能检验记录单
function printXnjyjld(){
	$.post("/veh/checkReport/printJyReport",{lsh:""},function(data){
		if(data.state==1){
			
			$("#printTemplet img").attr("src","../cache/report/"+data.data);
			var printObj = {};
			printObj.prview = false;
			printObj.template = "printTemplet";
//			if(params.isView == "true"){
//				printObj.prview = true;
//			}
			printCYD(printObj);
		}else{
			$.messager.alert("提示",data.message,"error");
		}
		
	},"json").complete(function(){
		$.messager.progress('close');
	});
}

//刷新性能检验报告单
function refreshXnjybgd(){
	$.post("/veh/checkReport/printJyBgReport",{lsh:""},function(data){
		if(data.state==1){
			
			$("#printTempletRep img").attr("src","../cache/report/"+data.data);
//			var printObj = {};
//			printObj.prview = false;
//			if(params.isView == "true"){
//				printObj.prview = true;
//			}
//			printCYD(printObj);
		}else{
			$.messager.alert("提示",data.message,"error");
		}
		
	},"json").complete(function(){
		$.messager.progress('close');
	});
}
//打印性能检验记录单
function printXnjybgd(){
	$.post("/veh/checkReport/printJyBgReport",{lsh:""},function(data){
		if(data.state==1){
			
			$("#printTempletRep img").attr("src","../cache/report/"+data.data);
			var printObj = {};
			printObj.prview = false;
			printObj.template = "printTempletRep";
//			if(params.isView == "true"){
//				printObj.prview = true;
//			}
			printCYD(printObj);
		}else{
			$.messager.alert("提示",data.message,"error");
		}
		
	},"json").complete(function(){
		$.messager.progress('close');
	});
}

function printCYD(option) {
	
	var prview=false;
	if(option){
		if(option.prview){
			prview=option.prview
		}
	}
	var LODOP = getLodop(document.getElementById('LODOP_OB'),
			document.getElementById('LODOP_EM'));
	LODOP.ADD_PRINT_HTM(0, 0, 1024, 1200, document
			.getElementById(option.template).innerHTML);
	if(prview){
		LODOP.PREVIEW();
	}else{
		LODOP.PRINT();
	}
}