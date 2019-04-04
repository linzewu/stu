var jcxdh_combox=[{
	jcxdh:'3200000195',
	name:'滨海县明迪服务有限公司'
}];


var jyxm_combox=[{
	jyxm:'F1',
	name:'车辆外观检验'
},{
	jyxm:'C1',
	name:'底盘检验'
},{
	jyxm:'DC',
	name:'底盘动态检验'
},{
	jyxm:'B1',
	name:'一轴制动'
},{
	jyxm:'B2',
	name:'二轴制动'
},{
	jyxm:'B3',
	name:'三轴制动'
},{
	jyxm:'B4',
	name:'四轴制动'
},{
	jyxm:'B5',
	name:'五轴制动'
},{
	jyxm:'B6',
	name:'六轴制动'
},{
	jyxm:'B0',
	name:'驻车制动'
},{
	jyxm:'H1',
	name:'左外灯或二三轮机动车的左灯'
},{
	jyxm:'H2',
	name:'左内灯'
},{
	jyxm:'H3',
	name:'右内灯'
},{
	jyxm:'H4',
	name:'右外灯或二三轮机动车的右灯'
},{
	jyxm:'S1',
	name:'车速表'
},{
	jyxm:'A1',
	name:'侧滑'
},{
	jyxm:'R1',
	name:'路试制动'
},{
	jyxm:'R2',
	name:'路试坡道驻车'
},{
	jyxm:'R3',
	name:'路试车速表'
},{
	jyxm:'M1',
	name:'外廓尺寸自动测量'
},{
	jyxm:'R',
	name:'路试制动'
}];

function getjyxm(jyxm){
	var name="";
	$.each(jyxm_combox,function(i,n){
		if(n.jyxm==jyxm){
			name=n.name;
		}
	})
	return name;
}


function getjczmc(jcxdh){
	var name="";
	$.each(jcxdh_combox,function(i,n){
		if(n.jcxdh==jcxdh){
			name=n.name;
		}
	})
	return name;
}


function StringToDate(s) { 
	var d = new Date(); 
	d.setYear(parseInt(s.substring(0,4),10)); 
	d.setMonth(parseInt(s.substring(5,7)-1,10)); 
	d.setDate(parseInt(s.substring(8,10),10)); 
	d.setHours(parseInt(s.substring(11,13),10)); 
	d.setMinutes(parseInt(s.substring(14,16),10)); 
	d.setSeconds(parseInt(s.substring(17,19),10)); 
	return d; 
}

Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "H+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
