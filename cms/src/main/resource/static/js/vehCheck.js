function viewImage(){
	var row = $("#vehCheckInfoDG").datagrid("getSelected");
	if (row != null) {
		//
		$.post("../../vheCheckInfo/getPhotosByLsh",{"lsh":row.lsh},function(data){
			if(data.state==1){
				var photos = data.data;
				if(photos.length > 0 ){
					var div = $("<div class='content-b'></div>")
					var ul=$("<ul id='photoView' class='content-gif'></ul>");
					$.each(photos,function(i,n){
						var li=$("<li></li>");
						var span = $("<span></span>");
						var img = $("<label>检验次数"+n.jccs+"<br>"+comm.getParamNameByValue("zpzl",n.zpzl)+"</label><br/><img id='"+n.id+"Img' width='80px' height='80px'/><br/><input type='button' value='裁剪及打印' onclick='cropperImg("+n.id+")'>");
						img.attr("src","../images/loading.gif")
						span.append(img);
						li.append(span);
						ul.append(li);
					});
					div.append(ul);
					if($('#vehCheckTab').tabs("exists","照片列表")){
						var tab = $('#vehCheckTab').tabs('getTab',"照片列表");  // get selected panel
						$('#vehCheckTab').tabs('update', {
							tab: tab,
							options: {
								title:'照片列表',
							    content:div,
							    closable:true
							}
						});
						$('#vehCheckTab').tabs('select',"照片列表"); 
					}else{
						$('#vehCheckTab').tabs('add',{
						    title:'照片列表',
						    content:div,
						    closable:true
						});
					}
					$("#photoView").viewer({});
					//请求刷新图片
					$.each(photos,function(i,n){
						$.post("../../vheCheckInfo/buildVehPhoto",{"id":n.id},function(data){
							if(data.state==1){
								$("#"+n.id+"Img").attr("src","../cache/vehimage/"+n.id+".jpg");
							}else{
								$.messager.alert("提示",data.message,"error");
							}
							
						},"json").complete(function(){
							$.messager.progress('close');
						});
					});
				}else{
					$.messager.alert("提示", "没有照片可进行查看", "warning");
				}
			}else{
				$.messager.alert("提示",data.message,"error");
			}
			
		},"json").complete(function(){
			$.messager.progress('close');
		});
	} else {
		$.messager.alert("提示", "请选择需要查看照片的数据", "warning");
	}
}


function imgPrint(id){
	$.post("../../vheCheckInfo/buildVehPhoto",{"id":id},function(data){
		if(data.state==1){
			var printObj = {};
			printObj.prview = true;
			$("#printTemplet img").attr("src","../cache/vehimage/"+id+".jpg").load(function() {
				//放在这里，确保一定是加载完成后才执行打印
				printCYD(printObj);
			});
			
		}else{
			$.messager.alert("提示",data.message,"error");
		}
		
	},"json").complete(function(){
		$.messager.progress('close');
	});
}

function cropperImg(id){
	$("#imageCorpp").attr("src","../cache/vehimage/"+id+".jpg")
	$("#imageWin").window('open');
	$("#imageCorpp").cropper('reset', true).cropper('replace', "../cache/vehimage/"+id+".jpg");
	
}

function closeImageWin(){
	$("#imageWin").window('close');
}

function printCropImg(){
	var canvasImg = $("#imageCorpp").cropper('getCroppedCanvas');
	LODOP=getLodopObj();
	LODOP.PRINT_INIT("打印车辆裁剪图片");
	LODOP.ADD_PRINT_IMAGE(30,20,600,300,"<img border='0' src='"+canvasImg.toDataURL()+"'width='100%' height='300'/>");
	LODOP.SET_PRINT_STYLEA(0,"Stretch",1);//(可变形)扩展缩放模式
	LODOP.PRINT_SETUP();
	
}