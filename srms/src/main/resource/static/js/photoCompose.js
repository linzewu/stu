$(function(){


	$("input[name=buttonPrint_sqb]").parent().append("<input type=button  class=button z_idx value='电子申请表' onClick=getData()>");
});

function getData(){

    var disabled = $("input[name=buttonPrint_sqb]").prop("disabled");
    if(disabled){
		alert('暂不能打印申请表!');
		return;
	}

	var meth = $("input[value='申请表']").attr("onclick");
	var methodName = meth.substring(0,meth.indexOf("(")).toString();
	var clickMethodObj=eval(methodName);
	var methString = clickMethodObj.toString();
	
	methString = methString.replace(methodName+"(",methodName+"_img(");
	methString = methString.replace("PrintSqbOne","PrintSqbOne_image");
	methString = methString.replace("PrintSqbTow","PrintSqbTow_image");
	methString = methString.replace("PrintSqbThree","PrintSqbThree_image");
	methString = methString.replace("PrintSqbFour","PrintSqbFour_image");
	methString = methString.replace("PrintSqbFour_new","PrintSqbFour_new_image");
	methString = methString.replace("PrintSqbFive","PrintSqbFive_image");
	
	eval(methString);
	var newMeth = meth.replace(methodName,methodName+"_img");
	eval(newMeth);
	
	
	//var methString = print_sqb_one.toString();
//	var methSub = methString.substring(methString.indexOf("{")+1,methString.lastIndexOf("}"));
	
	
	/**
	
	var evalTxt = "";
	var arrDm = methSub.split(";");
	$.each(arrDm,function(i,n){
		if(n.trim().indexOf("PrintSqb") != -1){
			var printTxt = n.trim();
			var replaceTxt = printTxt.substring(printTxt.indexOf("PrintSqb"),printTxt.indexOf("("));
			evalTxt+=printTxt.replace(replaceTxt,replaceTxt+"_image")+";";
		}else if(n.trim() != ""){
			evalTxt+=n.trim()+";";
		}
		
	});
	eval(evalTxt);
	
	**/
	
}
//机动车变更登记/备案申请表
function PrintSqbTwo_image(vehPrinter, m_hpzl, m_hphm,m_ywlx,m_ywyy, m_zsxxdz, m_bghxx,
			m_syxz, m_syr, m_zzxxdz, m_yzbm1, m_lxdh, m_sjhm, m_dzyx,
			m_zcd_province, m_zcd_fzjg, 
 			m_veh_agentdwmc, m_veh_agentzsdz,
 			m_veh_agentyzbm, m_veh_agentdwlxdh, m_veh_agentdzyx, m_veh_agentxm,
 			m_veh_agentlxdh,m_print_td){
			var PARAMS = [];
			PARAMS.hpzl = m_hpzl;
			PARAMS.hphm = m_hphm;
			PARAMS.ywlx = m_ywlx;
			PARAMS.ywyy = m_ywyy;
			PARAMS.zsxxdz = m_zsxxdz;
			PARAMS.bghxx = "";
			PARAMS.syxz = m_syxz;
			PARAMS.syr = m_syr;
			PARAMS.zzxxdz = m_zzxxdz;
			PARAMS.yzbm1 = m_yzbm1;
			PARAMS.lxdh = m_lxdh;
			PARAMS.sjhm = m_sjhm;
			PARAMS.dzyx = m_dzyx;
			PARAMS.province = m_zcd_province;
			PARAMS.fzjg = m_zcd_fzjg;
			PARAMS.agentdwmc = m_veh_agentdwmc;
			PARAMS.agentzsdz = m_veh_agentzsdz;
			PARAMS.agentyzbm = m_veh_agentyzbm;
			PARAMS.agentdwlxdh = m_veh_agentdwlxdh;
			PARAMS.agentdzyx = m_veh_agentdzyx;
			PARAMS.agentxm = m_veh_agentxm;
			PARAMS.agentlxdh = m_veh_agentlxdh;
			PARAMS.mbmc = "机动车变更登记备案申请表";
			PARAMS.tempName = "机动车变更登记备案申请表";
			PARAMS.syr1 = "";
			PARAMS.syr2 = "";
			
			if(PARAMS.ywlx=='D'){
				if (PARAMS.ywyy.indexOf("N") > -1) {
					PARAMS.sqsx = "DN";
					PARAMS.syr1 = PARAMS.syr;
				}
				if (PARAMS.ywyy.indexOf("A") > -1) {
					PARAMS.sqsx = "DA";
					PARAMS.syr2 = PARAMS.syr;
				}
				if (PARAMS.ywyy.indexOf("O") > -1) {
					PARAMS.sqsx = "DO";
				}
				if (PARAMS.ywyy.indexOf("P") > -1) {
					PARAMS.sqsx = "DP";
				}
				if (PARAMS.ywyy.indexOf("J") > -1) {
					PARAMS.sqsx = "DJ";
				}
				if (PARAMS.ywyy.indexOf("I") > -1) {
					PARAMS.sqsx = "DI";
				}
				if (PARAMS.ywyy.indexOf("F") > -1) {
					PARAMS.sqsx = "DF";
				}
				if (PARAMS.ywyy.indexOf("G") > -1) {
					PARAMS.sqsx = "DG";
				}
				if (PARAMS.ywyy.indexOf("D") > -1) {
					PARAMS.sqsx = "DD";
				}
				if (m_ywyy.indexOf("H") > -1) {
					PARAMS.sqsx = "DH";
				}
				if (m_ywyy.indexOf("L") > -1) {
					PARAMS.sqsx = "DL";
				}
				if (m_ywyy.indexOf("K") > -1) {
					PARAMS.sqsx = "DK";
				}
				if (m_ywyy.indexOf("M") > -1) {
					PARAMS.sqsx = "DM";
				}
				if (m_ywyy.indexOf("F") > -1 || m_ywyy.indexOf("G") > -1
						|| m_ywyy.indexOf("D") > -1 || m_ywyy.indexOf("H") > -1
						|| m_ywyy.indexOf("L") > -1 || m_ywyy.indexOf("K") > -1
						|| m_ywyy.indexOf("M") > -1) {
					PARAMS.bghxx = m_bghxx;
				}
			}else if(m_ywlx=='V'){
				PARAMS.sqsx = "V";
				PARAMS.bghxx = m_bghxx;
			}
			//PARAMS.bghxx = m_bghxx;
			createImg(PARAMS);
}
//机动车抵押登记质押备案申请表
function PrintSqbThree_image(vehPrinter, 
	    m_hpzl, 
	    m_hphm,
	    m_ywyy, 
	    m_syr, 
	    m_veh_agentdwmc,
		m_veh_agentzsdz, 
		m_veh_agentyzbm, 
		m_veh_agentdwlxdh, 
		m_veh_agentdzyx,
		m_veh_agentxm, 
		m_veh_agentlxdh, 
		
		m_dyqr_xm, 
		m_dyqr_xxdz, 
		m_dyqr_yzbm,
		m_dyqr_lxdh, 
		
		m_dyqr_dzyx,
		m_impawn_agentdwmc, 
		m_impawn_agentzsdz,
		m_impawn_agentyzbm, 
		m_impawn_agentdwlxdh, 
		m_impawn_agentdzyx,
		m_impawn_agentxm, 
		m_impawn_agentlxdh,m_print_td){
		var PARAMS = [];
		PARAMS.hpzl = m_hpzl;
		PARAMS.hphm = m_hphm;
		//PARAMS.ywlx = m_ywlx;
		PARAMS.ywyy = m_ywyy;
		PARAMS.syr = m_syr;
		PARAMS.agentdwmc = m_veh_agentdwmc;
		PARAMS.agentzsdz = m_veh_agentzsdz;
		PARAMS.agentyzbm = m_veh_agentyzbm;
		PARAMS.agentdwlxdh = m_veh_agentdwlxdh;
		PARAMS.agentdzyx = m_veh_agentdzyx;
		PARAMS.agentxm = m_veh_agentxm;
		PARAMS.agentlxdh = m_veh_agentlxdh;
		
		PARAMS.dyqr_xm = m_dyqr_xm;
		PARAMS.dyqr_xxdz = m_dyqr_xxdz;
		PARAMS.dyqr_yzbm = m_dyqr_yzbm;
		PARAMS.dyqr_lxdh = m_dyqr_lxdh;
		
		PARAMS.dyqr_dzyx = m_dyqr_dzyx;
		PARAMS.impawn_agentdwmc = m_impawn_agentdwmc; 
		PARAMS.impawn_agentzsdz = m_impawn_agentzsdz;
		PARAMS.impawn_agentyzbm = m_impawn_agentyzbm; 
		PARAMS.impawn_agentdwlxdh = m_impawn_agentdwlxdh; 
		PARAMS.impawn_agentdzyx = m_impawn_agentdzyx;
		PARAMS.impawn_agentxm = m_impawn_agentxm; 
		PARAMS.impawn_agentlxdh = m_impawn_agentlxdh;
		
		
		
		PARAMS.mbmc = "机动车抵押登记质押备案申请表";
		
		PARAMS.tempName="机动车抵押登记质押备案申请表";
		if (m_ywyy.indexOf("A")>-1) {
			PARAMS.sqsx = "A";
		} else if (m_ywyy.indexOf("B")>-1) {
			PARAMS.sqsx = "B";
		} else if (m_ywyy.indexOf("C")>-1) {
			PARAMS.sqsx = "C";
		} else if (m_ywyy.indexOf("D")>-1) {
			PARAMS.sqsx = "D";
		}
		//PARAMS.bghxx = m_bghxx;
		createImg(PARAMS);
}
//注册登记
function PrintSqbOne_image(vehPrinter, m_hpzl, m_hphm, m_ywlx, m_ywyy, m_zxyy, m_clpp1,
		m_clxh, m_clsbdh, m_hdfs, m_syxz, m_syr, m_zzxxdz, m_yzbm1, m_lxdh,
		m_sjhm, m_dzyx, m_zcd_province, m_zcd_fzjg, m_veh_agentdwmc,
		m_veh_agentzsdz, m_veh_agentyzbm, m_veh_agentdwlxdh, m_veh_agentdzyx,
		m_veh_agentxm, m_veh_agentlxdh ,m_print_td){
	
		var PARAMS = [];
		PARAMS.hpzl = m_hpzl;
		PARAMS.hphm = m_hphm;
		PARAMS.ywlx = m_ywlx;
		PARAMS.ywyy = m_ywyy;
		
		PARAMS.zxyy = m_zxyy; 
		PARAMS.clpp1 = m_clpp1;
		PARAMS.clxh = m_clxh; 
		PARAMS.clsbdh = m_clsbdh; 
		PARAMS.hdfs = m_hdfs; 
		PARAMS.syxz = m_syxz;
		PARAMS.syr = m_syr;
		
		PARAMS.zzxxdz = m_zzxxdz;
		PARAMS.yzbm1 = m_yzbm1;
		PARAMS.lxdh = m_lxdh;
		PARAMS.sjhm = m_sjhm;
		PARAMS.dzyx = m_dzyx;
		PARAMS.province = m_zcd_province;
		PARAMS.fzjg = m_zcd_fzjg;
		
		PARAMS.agentdwmc = m_veh_agentdwmc;
		PARAMS.agentzsdz = m_veh_agentzsdz;
		PARAMS.agentyzbm = m_veh_agentyzbm;
		PARAMS.agentdwlxdh = m_veh_agentdwlxdh;
		PARAMS.agentdzyx = m_veh_agentdzyx;
		PARAMS.agentxm = m_veh_agentxm;
		PARAMS.agentlxdh = m_veh_agentlxdh;
		
		PARAMS.mbmc = "注册申请表";
		PARAMS.tempName="注册申请表";
		createImg(PARAMS);
} 

function PrintSqbFour_image(printer, m_hpzl, m_hphm, m_ywlx, m_ywyy, m_sqyy, m_syr, m_veh_zsxxdz,
		m_veh_yzbm, m_veh_lxdh, m_veh_dzyx, m_veh_sjhm, m_agentsyr,
		m_agentzsdz, m_agentyzbm, m_agentdwlxdh,
		m_agentdzyx, m_agentxm, m_agentlxdh,m_print_td) {
	
		var PARAMS = [];
		PARAMS.hpzl = m_hpzl;
		PARAMS.hphm = m_hphm;
		PARAMS.ywlx = m_ywlx;
		PARAMS.ywyy = m_ywyy;
		
		PARAMS.sqyy = m_sqyy; 
		PARAMS.syr = m_syr;
		
		PARAMS.zsxxdz = m_veh_zsxxdz;
		PARAMS.yzbm = m_veh_yzbm; 
		PARAMS.lxdh = m_veh_lxdh; 
		PARAMS.dzyx = m_veh_dzyx; 
		PARAMS.sjhm = m_veh_sjhm;
		
		
		PARAMS.agentsyr = m_agentsyr;
		PARAMS.agentzsdz = m_agentzsdz;
		PARAMS.agentyzbm = m_agentyzbm;
		PARAMS.agentdwlxdh = m_agentdwlxdh;
		PARAMS.agentdzyx = m_agentdzyx;
		PARAMS.agentxm = m_agentxm;
		PARAMS.agentlxdh = m_agentlxdh;
		PARAMS.newhphm = "";
		PARAMS = setXX(PARAMS);
		PARAMS.mbmc = "机动车牌证申请表";
		PARAMS.tempName="机动车牌证申请表";
		createImg(PARAMS);
	
}

function PrintSqbFour_new_image(printer, m_hpzl, m_hphm, m_ywlx, m_ywyy, m_sqyy, m_syr, m_veh_zsxxdz,
		m_veh_yzbm, m_veh_lxdh, m_veh_dzyx, m_veh_sjhm, m_agentsyr,
		m_agentzsdz, m_agentyzbm, m_agentdwlxdh,
		m_agentdzyx, m_agentxm, m_agentlxdh,m_print_td,m_newhphm){
		var PARAMS = [];
		PARAMS.hpzl = m_hpzl;
		PARAMS.hphm = m_hphm;
		PARAMS.ywlx = m_ywlx;
		PARAMS.ywyy = m_ywyy;
		
		PARAMS.sqyy = m_sqyy; 
		PARAMS.syr = m_syr;
		
		PARAMS.zsxxdz = m_veh_zsxxdz;
		PARAMS.yzbm = m_veh_yzbm; 
		PARAMS.lxdh = m_veh_lxdh; 
		PARAMS.dzyx = m_veh_dzyx; 
		PARAMS.sjhm = m_veh_sjhm;
		
		
		PARAMS.agentsyr = m_agentsyr;
		PARAMS.agentzsdz = m_agentzsdz;
		PARAMS.agentyzbm = m_agentyzbm;
		PARAMS.agentdwlxdh = m_agentdwlxdh;
		PARAMS.agentdzyx = m_agentdzyx;
		PARAMS.agentxm = m_agentxm;
		PARAMS.agentlxdh = m_agentlxdh;
		PARAMS.newhphm = m_newhphm
		
		PARAMS = setXX(PARAMS);
		PARAMS.mbmc = "机动车牌证申请表";
		PARAMS.tempName="机动车牌证申请表";
		createImg(PARAMS);
}

function setXX(PARAMS){
	if (PARAMS.ywlx=="K") {

		if (PARAMS.ywyy.indexOf("A")>-1) {//补领号牌
			//申请事项
			PARAMS.sqsx = "KA";

			if (PARAMS.sqyy=="A") {//灭失
				//申请明细
				PARAMS.sqmx = "KAA";
			}else if (m_sqyy=="B") {//丢失
				PARAMS.sqmx = "KAB";
			}else if (m_sqyy=="C") {//损坏
				PARAMS.sqmx = "KAC";
			}
		}
		else if (PARAMS.ywyy.indexOf("D")>-1) {//换领号牌
			PARAMS.sqsx = "KD";
		}
		else if (PARAMS.ywyy.indexOf("B")>-1) {//补领行驶证
			PARAMS.sqsx = "KB";
			if (PARAMS.sqyy=="A") {//灭失
				PARAMS.sqmx = "KBA";
			}else if (PARAMS.sqyy=="B") {//丢失
				PARAMS.sqmx = "KBB";
			}
		}
		else if (PARAMS.ywyy.indexOf("E")>-1) {//换领行驶证
			PARAMS.sqsx = "KE";
		}
		else if (PARAMS.ywyy.indexOf("G")>-1) {//换领检验合格证
			PARAMS.sqsx = "KG";

		}else if (PARAMS.ywyy.indexOf("H")>-1) {//补领检验合格证
			PARAMS.sqsx = "KH";
			if (PARAMS.sqyy=="A") {//灭失
				PARAMS.sqmx = "KHA";
			}else if (PARAMS.sqyy=="B") {//丢失
				PARAMS.sqmx = "KHB";
			}
		}

	}else if (PARAMS.ywlx.indexOf("P")>-1) {//核发检验合格标志
		PARAMS.sqsx = "PR";
		if (PARAMS.ywyy.indexOf("A")>-1) {//本地
			PARAMS.sqmx = "PRA";
          }

	}else if (PARAMS.ywlx.indexOf("R")>-1) {//受托核发检验合格标志
		PARAMS.sqsx = "PR";
		if (PARAMS.ywyy.indexOf("A")>-1) {//受托检验
			PARAMS.sqmx = "PRB";
		}
		if (PARAMS.ywyy.indexOf("B")>-1) {//异地营运货车核发合格标志
			PARAMS.sqmx = "PRB";
		}

	}else if (PARAMS.ywlx.indexOf("L")>-1) {//登记证书

		if (PARAMS.ywyy.indexOf("I")>-1) {//补领登记证书(首次申领)
			PARAMS.sqsx = "LI";

		}else if (PARAMS.ywyy.indexOf("C")>-1) {//补领登记证书

			PARAMS.sqsx = "LC";

			if (PARAMS.sqyy=="A") {//灭失

				PARAMS.sqmx = "LCA";

			}else if (PARAMS.sqyy=="B") {////丢失

				PARAMS.sqmx = "LCB";

			}
			else if (PARAMS.sqyy=="D") {//未得到

				PARAMS.sqmx = "LCD";

			}
		}else if (PARAMS.ywyy.indexOf("F")>-1) {//换领登记证书
			PARAMS.sqsx = "LF";
		}
	}else if (PARAMS.ywlx.indexOf("D")>-1&&PARAMS.ywyy.indexOf("Y")>-1) {//变更号牌号码-号牌互换	
		PARAMS.sqsx = "DY";
	}
	
	return PARAMS;
}

function PrintSqbFive_image(printer, m_hpzl, m_hphm, m_ywlx, m_ywyy,m_sch_syxz,m_sch_lb,
		m_sch_wgbz,m_veh_ccdjrq,m_sch_crrs,m_sch_xsrs,m_veh_syr,m_sch_name,m_sch_kxsj,
		m_sch_xsxl,m_sch_tkzd,m_sch_drv1_name,m_sch_drv1_sfzmhm,m_sch_drv1_lxdh,
		m_sch_drv2_name,m_sch_drv2_sfzmhm,m_sch_drv2_lxdh,
		m_sch_drv3_name,m_sch_drv3_sfzmhm,m_sch_drv3_lxdh,
		m_lqr_xm,m_lqr_yjdz,m_lqr_lxdh,m_lqr_sjhm,
		m_dlr_xm,m_dlr_yjdz,m_dlr_lxdh,m_dlr_sjhm,
		m_print_td){
		var PARAMS = [];
		PARAMS.hpzl = m_hpzl;
		PARAMS.hphm = m_hphm;
		PARAMS.ywlx = m_ywlx;
		PARAMS.ywyy = m_ywyy;
		
		
		PARAMS.syxz = m_sch_syxz;
		PARAMS.lb = m_sch_lb;
		PARAMS.wgbz = m_sch_wgbz;
		PARAMS.ccdjrq = m_veh_ccdjrq;
		PARAMS.crrs = m_sch_crrs;
		PARAMS.xsrs = m_sch_xsrs;
		PARAMS.syr = m_veh_syr;
		PARAMS.name = m_sch_name;
		PARAMS.kxsj = m_sch_kxsj;
		PARAMS.xsxl = m_sch_xsxl;
		PARAMS.tkzd = m_sch_tkzd;
		PARAMS.drv1_name = m_sch_drv1_name;
		PARAMS.drv1_sfzmhm = m_sch_drv1_sfzmhm;
		PARAMS.drv1_lxdh = m_sch_drv1_lxdh;
		PARAMS.drv2_name = m_sch_drv2_name;
		PARAMS.drv2_sfzmhm = m_sch_drv2_sfzmhm;
		PARAMS.drv2_lxdh = m_sch_drv2_lxdh;
		PARAMS.drv3_name = m_sch_drv3_name;
		PARAMS.drv3_sfzmhm = m_sch_drv3_sfzmhm;
		PARAMS.drv3_lxdh = m_sch_drv3_lxdh;
		PARAMS.lqr_xm = m_lqr_xm;
		PARAMS.lqr_yjdz = m_lqr_yjdz;
		PARAMS.lqr_lxdh = m_lqr_lxdh;
		PARAMS.lqr_sjhm = m_lqr_sjhm;
		PARAMS.dlr_xm = m_dlr_xm;
		PARAMS.dlr_yjdz = m_dlr_yjdz;
		PARAMS.dlr_lxdh = m_dlr_lxdh;
		PARAMS.dlr_sjhm = m_dlr_sjhm;
		
		PARAMS.ywyypd = PARAMS.ywyy;
		if(m_ywyy == "D"){
			PARAMS.ywyypd = "C";
		}
		
		PARAMS.mbmc = "校车标牌领取表";
		PARAMS.tempName="校车标牌领取表";
		createImg(PARAMS);
}
/**function b(){

	var sa =a.toString();

	 var startVar = sa.indexOf("var");
	var txtsa=sa.substring(startVar);
	var textsc="";
	var arraysa=txtsa.split(";")
	$.each(arraysa,function(i,n){

	if(n.trim().indexOf("var")==0){
	textsc+=n.trim()+";"

	}
	});

	eval(textsc);
	console.log(t1);
	console.log(t2);
	console.log(textsc)

	}
function b1(){
	var methString = print_sqb_two.toString();
	var methSub = methString.substring(methString.indexOf("{")+1,methString.lastIndexOf("}"));
	var evalTxt = "";
	var arrDm = methSub.split(";");
	$.each(arrDm,function(i,n){
		if(n.trim().indexOf("PrintSqb") != -1){
			var printTxt = n.trim();
			var replaceTxt = printTxt.substring(printTxt.indexOf("PrintSqb"),printTxt.indexOf("("));
			evalTxt+=printTxt.replace(replaceTxt,replaceTxt+"_image")+";";
		}else if(n.trim() != ""){
			evalTxt+=n.trim()+";";
		}
		
	});
	console.log(evalTxt);
}**/

function createImg(PARAMS){//(URL, PARAMS){
	var dzyxh_win_ip="10.39.147.1";
	var dzyxh_win_port="8080";
	var URL = "http://"+dzyxh_win_ip+":"+dzyxh_win_port+"/srms/photoCompose/toHctp";
	//var PARAMS = [];
	
	//var hphm=$("input[name=hphm]").val();
	//var hpzl=$("input[name=hpzl]").val();
	//PARAMS.clsbdh = "123";
	//PARAMS.ywlx = "A";
	 var hpzl=$("input[name=hpzl]").val();
	 var jylsh=$("form[name=formain] input[name=lsh]").val();
	 if(!jylsh){
		jylsh=$("form[name=agentInfoForm] input[name=lsh]").val();
	 }
	 PARAMS.hpzl_bm = hpzl;
	 PARAMS.lsh = jylsh;
	 PARAMS.ywlx_bm = $("select[name=ywlx]").val();
	 var temp = document.createElement("form");
     temp.action = URL;
     temp.target="_blank"
     temp.method = "post";
     //temp["accept-charset"] = "UTF-8";
     temp.style.display = "none";
     for (var x in PARAMS) {
         var opt = document.createElement("input");
         opt.name = x;
         opt.value = encodeURIComponent(PARAMS[x]);
         // alert(opt.name)
         temp.appendChild(opt);
     }
	 
    /** 
	 var opt1 = document.createElement("input");
     opt1.name = "tempName";
     var meth = $("input[value='申请表']").attr("onclick");
 	 var methodName = meth.substring(0,meth.indexOf("(")).toString();
 	 if(methodName == "print_sqb_one"){
 		opt1.value = "注册申请表";
 	 }else if(methodName == "print_sqb_two"){
 		opt1.value = "机动车变更登记备案申请表";
 	 }else if(methodName == "print_sqb_three"){
 		opt1.value = "机动车抵押登记质押备案申请表";
 	 }else if(methodName == "print_sqb_four"){
 		opt1.value = "机动车牌证申请表";
 	 }else if(methodName == "print_sqb_four_new"){
 		opt1.value = "机动车牌证申请表";
 	 }else if(methodName == "print_sqb_five"){
 		opt1.value = "校车标牌领取表";
 	 }
	 opt1.value=encodeURIComponent(opt1.value);
 	 temp.appendChild(opt1);
 	 **/
	 
     document.body.appendChild(temp);
     temp.submit();
     return temp;
	
	/**$.post("photoCompose/composeImage",{clsbdh:"123"}, function(data){
		if(data.state==1){
			window.location.href="index.html";
		}else{
			$.messager.alert("登陆失败",data.message,"info");
			
		}
	},"json").error(function(e){
		$.messager.alert("提示",e.responseText);
	}).complete(function(){
	})**/
}