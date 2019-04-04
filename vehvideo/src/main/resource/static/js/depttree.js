var deptArr = null;
$(function() {
	
	deptArr = $.ajax({
		url : "../../dept/getDepts",
		async : false,
		type:'POST'
	}).responseText;
	
	deptArr=$.parseJSON(deptArr);
});

function depts2Node(data){
	var roots=[];
	for(var i in data){
		if(data[i].sjbmdm==null||data[i].sjbmdm==""){
			var node={};
			node.id=data[i].bmdm;
			node.text=data[i].bmmc;
			//node.data=data[i];
			setChildrenNode(data,node);
			roots.push(node);
		}
	}
	return roots;
}

function setChildrenNode(data,node){
	var children=[];
	for(var i in data){
		if(node.id==data[i].sjbmdm){
			var child={};
			child.id=data[i].bmdm;
			child.text=data[i].bmmc;
			//child.data=data[i];
			setChildrenNode(data,child);
			children.push(child);
			node.children=children;
		}
	}
}

function loadComboboxTree(id){

		var nodes = depts2Node(deptArr);
		
		$("#"+id).combotree('loadData', nodes);
}

function jyjgbhFormatter(value){
	var text=value;
	$.each(deptArr,function(i,n){
		if(n.bmdm==value){
			text=n.bmmc;
			return false;
		}
	})
	return text;
}