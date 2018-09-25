var yue = [ {
	"id" : 1,
	"text" : "一月",
	"selected" : true
}, {
	"id" : 2,
	"text" : "二月"
}, {
	"id" : 3,
	"text" : "三月"
}, {
	"id" : 4,
	"text" : "四月"
}, {
	"id" : 5,
	"text" : "五月"
}, {
	"id" : 6,
	"text" : "六月"
}, {
	"id" : 7,
	"text" : "七月"
}, {
	"id" : 8,
	"text" : "八月"
}, {
	"id" : 9,
	"text" : "九月"
}, {
	"id" : 10,
	"text" : "十月"
}, {
	"id" : 11,
	"text" : "十一月"
}, {
	"id" : 12,
	"text" : "十二月"
} ]

var jidu = [ {
	"id" : 1,
	"text" : "第一季度",
	"selected" : true
}, {
	"id" : 2,
	"text" : "第二季度"
}, {
	"id" : 3,
	"text" : "第三季度"
}, {
	"id" : 4,
	"text" : "第四季度"
} ]

var years = [ {
	"id" : 2005,
	"text" : "2005"
}, {
	"id" : 2006,
	"text" : "2006"
}, {
	"id" : 2007,
	"text" : "2007"
}, {
	"id" : 2008,
	"text" : "2008"
}, {
	"id" : 2009,
	"text" : "2009"
}, {
	"id" : 2010,
	"text" : "2010"
}, {
	"id" : 2011,
	"text" : "2011"
}, {
	"id" : 2012,
	"text" : "2012"
}, {
	"id" : 2013,
	"text" : "2013"
}, {
	"id" : 2014,
	"text" : "2014",
	"selected" : true
}, {
	"id" : 2015,
	"text" : "2015"
} ];

var ywlx = [ {
	"id" : 1,
	"text" : "注册登记"
}, {
	"id" : 2,
	"text" : "转入"
}, {
	"id" : 3,
	"text" : "转移登记"
}, {
	"id" : 4,
	"text" : "转出"
}, {
	"id" : 5,
	"text" : "变更车身颜色"
}, {
	"id" : 6,
	"text" : "更换车身或车架"
}, {
	"id" : 7,
	"text" : "更换发动机"
} , {
	"id" : 9,
	"text" : "变更使用性质"
} , {
	"id" : 10,
	"text" : "重新打刻VIN"
} , {
	"id" : 11,
	"text" : "重新打刻发动机号"
} , {
	"id" : 12,
	"text" : "更换整车"
} , {
	"id" : 13,
	"text" : "加装/拆除操纵辅助装置"
} , {
	"id" : 14,
	"text" : "申领登记证书"
} , {
	"id" : 15,
	"text" : "补领登记证书"
} , {
	"id" : 16,
	"text" : "监销"
} , {
	"id" : 17,
	"text" : "申领登记证书"
} , {
	"id" : 18,
	"text" : "补领登记证书"
} , {
	"id" : 19,
	"text" : "监销"
} , {
	"id" : 20,
	"text" : "其他/年检"
} , {
	"id" : 21,
	"text" : "领证"
}, {
	"id" : 22,
	"text" : "换证"
}]

var zjlx=[{
	"id" : 1,
	"text" : "身份证",
	"selected" : true
}, {
	"id" : 2,
	"text" : "居住证"
},{
	"id" : 3,
	"text" : "签证"
},{
	"id" : 4,
	"text" : "护照"
},{
	"id" : 5,
	"text" : "户口本"
},{
	"id" : 6,
	"text" : "军人证"
},{
	"id" : 7,
	"text" : "团员证"
},{
	"id" : 8,
	"text" : "党员证"
},{
	"id" : 9,
	"text" : "港澳通行证等"
}]
function getBaseParames(type) {
	var array = [];
	for ( var i in bps) {
		var bp = bps[i];
		if (bp.type == type) {
			var map = {};
			map['value'] = bp.paramName;
			map['id'] = bp.paramValue;
			array.push(map);
		}
	}
	return array;
}

var jdcdata=[{
	"dzpdh":"01234",
	"ywlsh":"12345678901234567",
	"ywlx":"转移登记",
	"hpzl":"小型轿车",
	"hphm":"苏JAA2AA",
	"clsbdh":"11XASDERYX11920976"
}]

var jszdata=[{
	"dzpdh":"01234",
	"ywlsh":"12345678901234567",
	"ywlx":"领证",
	"xm":"张三",
	"zjlx":"证件类型",
	"zjhm":"445121198807104899"
}]

function openScan(){
	$.messager.progress({"title":"提示","msg":"高速扫描中。。。",interval:5});
	window.setTimeout(function(){
		$.messager.progress("close");
		$.messager.alert("提示","扫描完成");
	}, 5*1000)
}
