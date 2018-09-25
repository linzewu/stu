//获取用户所有功能点
var allRolePower;
//当前登录用户
var userInfo;


function Map() {
	function KeyIndex(key, index) {
		this.key = key;
		this.index = index;
	}
	/** 存放键的数组(遍历用到) */
	this.keys = new Array();
	/** 存放数据 */
	this.data = new Object();
	var objectList = new Array();
	/**
	 * 放入一个键值对
	 * 
	 * @param {String}
	 *            key
	 * @param {Object}
	 *            value
	 * @param {Object}
	 *            index 顺序，不传默认为0
	 */
	this.put = function(key, value, index) {
		if (!index) {
			index = 0;
		}
		if (this.data[key] == null) {
			this.keys.push(key);
			objectList.push(new KeyIndex(key, index));
		}
		this.data[key] = value;
	};

	/**
	 * 获取某键对应的值
	 * 
	 * @param {String}
	 *            key
	 * @return {Object} value
	 */
	this.get = function(key) {
		return this.data[key];
	};

	/**
	 * 删除一个键值对
	 * 
	 * @param {String}
	 *            key
	 */
	this.remove = function(key) {
		this.keys.remove(key);
		this.data[key] = null;
	};

	/**
	 * 遍历Map,执行处理函数
	 * 
	 * @param {Function}
	 *            回调函数 function(key,value,index){..}
	 */
	this.each = function(fn) {
		if (typeof fn != 'function') {
			return;
		}
		objectList.sort(function(a, b) {
			return a.index - b.index
		});
		var len = objectList.length;
		for (var i = 0; i < len; i++) {
			var k = objectList[i].key;
			fn(k, this.data[k], i);
		}
	};

	/**
	 * 获取键值数组(类似Java的entrySet())
	 * 
	 * @return 键值对象{key,value}的数组
	 */
	this.entrys = function() {
		var len = this.keys.length;
		var entrys = new Array(len);
		for (var i = 0; i < len; i++) {
			entrys[i] = {
				key : this.keys[i],
				value : this.data[i]
			};
		}
		return entrys;
	};

	/**
	 * 判断Map是否为空
	 */
	this.isEmpty = function() {
		return this.keys.length == 0;
	};

	/**
	 * 获取键值对数量
	 */
	this.size = function() {
		return this.keys.length;
	};

	/**
	 * 重写toString
	 */
	this.toString = function() {
		var s = "{";
		for (var i = 0; i < this.keys.length; i++, s += ',') {
			var k = this.keys[i];
			s += k + "=" + this.data[k];
		}
		s += "}";
		return s;
	};
}
// 解析菜单
$(function() {
	
	userInfo = $.ajax({
		url : "../../user/getCurrentUser",
		async : false,
		type:'POST'
	}).responseText;

	userInfo=$.parseJSON(userInfo);
	
	allRolePower=$.ajax({
		url : "../../role/getRolePower",
		async : false,
		type:'POST'
	}).responseText;
	
	function createTab(data){
		$.each(data,function(i,n){
			$("#tabAll").tabs("add",{
				title:n.module,
				selected:i==0?true:false,
				href:n.url==null?"defaultTemplate.html":n.url,
				onLoad:function(){
					if(n.url==null){
						createDefaultMenu(n.module,n.group);
					}
				}
			});
		});
	};
	
	function createDefaultMenu(title,group){
		var currentPanel = $("#tabAll").tabs("getTab",title);
		var accordion =$(currentPanel).find(".easyui-accordion");
		var centerPanel = currentPanel.find(".easyui-layout").layout('panel','center');
		$.each(group,function(i,item){
			
			var menus = [];
			$.each(item.menus,function(i,n){
				if(allRolePower.indexOf(n.permission) != -1){
					menus.push(n);
				}
			});
			accordion.accordion('add', {
				title: item.groupName,
				content: createItemMume(centerPanel,menus),//授权后修改  //----createItemMume(centerPanel,item.menus),所有权限
				selected: i==0?true:false
			});
			
		});
		
		
	}
	
	function createItemMume(centerPanel, data) {
		var ul = $("<ul class='menus'></ul>");
		$.each(data,function(i,n){
			var li = $("<li><a href=\"javascript:void(0)\"><img></a></li>");
			li.find("img").attr("src", n.icon);
			li.find("a").append(n.title);
			li.click(function(){
				centerPanel.panel("refresh",n.href);
			});
			ul.append(li);
			if(i==0&&centerPanel.html()==""){
				li.click();
			}
		});
		return ul;
	}
	
	
	
	$.get("sysMenu.json",function(data){
		$("#tabAll").tabs();
		createTab(data);
	},"json");
	
	if(userInfo.pwOverdue=="Y"){
		$('#win_password').window('open');
	}
	
	
})