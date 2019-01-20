var search = {
	view : "form",
	scroll : false,
	elements : [ {
		cols : [ {
			view : "select",
			width : 130,
			options : veh.getBaseParames("sf"),
			name : "sf",
			label : "号牌号码:"
		}, {
			view : "text",
			width : 80,
			name : "hphm",
			value : 'J'
		}, {
			view : "select",
			value : "02",
			options : veh.getBaseParames("hpzl"),
			width : 200,
			name : "hpzl",
			label : "号牌种类:"
		}, {
			view : "text",
			name : "clsbdh",
			label : "车架号："
		}, {
			view : "button",
			value : "查询",
			width : 100,
			align : "center"
		} ]
	} ]

}

var vehLginRigth = {
	view : "datatable",
	width : 300,
	columns : [ {
		id : "rank",
		header : "流水号",
		width : 120
	}, {
		id : "title",
		header : "号牌号码",
		width : 120
	}, {
		id : "year",
		header : "号牌种类",
		width : 120
	}, {
		id : "votes",
		header : "状态",
		width : 100
	} ],
	autoheight : true,
	autowidth : true
};

var row2 = {
	id : "vehInfo",
	scroll : true,
	height : 250,
	header : "车辆基本信息",
	body : "http->html/vehInfo.html",
	data : {
		hphm : 'J1235'
	}
}
var row3 = {
	view : "form",
	header : "查验项目",
	elements : [ {
		cols : [ {
			view : "checkbox",
			label : "车辆外观检验",
			labelWidth : 120,
			value : 'F1'
		}, {
			view : "checkbox",
			label : "底盘检验",
			value : 'C1'
		}, {
			view : "checkbox",
			label : "动态底盘检验",
			labelWidth : 120,
			value : 'DC'
		}, {
			view : "checkbox",
			label : "一轴制动",
			value : 'B1'
		}, {
			view : "checkbox",
			label : "二轴制动",
			value : 'B2'
		} ]
	}, {
		cols : [ {
			view : "checkbox",
			label : "三轴制动",
			labelWidth : 120,
			value : 'B3'
		}, {
			view : "checkbox",
			label : "四轴制动",
			value : 'B4'
		}, {
			view : "checkbox",
			label : "五轴制动",
			labelWidth : 120,
			value : 'B5'
		}, {
			view : "checkbox",
			label : "六轴制动",
			value : 'B6'
		}, {
			view : "checkbox",
			label : "驻车轴制动",
			labelWidth : 100,
			value : 'B0'
		} ]
	}, {
		cols : [ {
			view : "checkbox",
			label : "左外灯",
			labelWidth : 120,
			value : 'H1'
		}, {
			view : "checkbox",
			label : "左内灯",
			value : 'H2'
		}, {
			view : "checkbox",
			label : "右内灯",
			labelWidth : 120,
			value : 'H3'
		}, {
			view : "checkbox",
			label : "右外灯",
			value : 'H4'
		}, {
			view : "checkbox",
			label : "车速表",
			value : 'S1'
		} ]
	}, {
		cols : [ {
			view : "checkbox",
			label : "侧滑",
			labelWidth : 120,
			value : 'A1'
		}, {
			view : "select",
			width : 230,
			value : "01",
			options : veh.getBaseParames("jylb"),
			name : "sf",
			label : "检验类别:"
		}, {
			view : "select",
			width : 230,
			value : "01",
			options : veh.getBaseParames("clsslb"),
			name : "sf",
			label : "所属类别:"
		}, {
			view : "button",
			value : "登陆",
			width : 100,
			align : "center"
		} ]
	} ]
};

var vehLoginLeft = {
	type : "clear",
	view : "accordion",
	rows : [ search, row2, row3 ]
};

var vheLogin = {
	cols : [ vehLoginLeft, {
		view : "resizer"
	}, vehLginRigth ]
};
