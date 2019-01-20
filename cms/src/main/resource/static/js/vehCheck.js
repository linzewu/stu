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
	var hhtoPx=$("#divMMHeight").height();
	var wwtoPx=$("#divMMHeight").width();
	$("#imageCorpp").attr("src","../cache/vehimage/"+id+".jpg")
	$("#imageWin").window('open');
	$("#imageCorpp").cropper('reset', true).cropper('replace', "../cache/vehimage/"+id+".jpg");
	var mrcjcc = comm.getBaseParames("mrcjcc");
	if (mrcjcc.length > 0){
		$("#cjcc_c").combobox("setValue",mrcjcc[0].id);
		var ccArr = mrcjcc[0].id.split("*");
		
		$("#divMMHeight").css("width",ccArr[0]+"mm");
		$("#divMMHeight").css("height",ccArr[1]+"mm");
	}
	
	
}

function closeImageWin(){
	$("#imageWin").window('close');
}

function printCropImg(){
	var hhtoPx=$("#divMMHeight").height();
	var wwtoPx=$("#divMMHeight").width();
	var canvasImg = $("#imageCorpp").cropper('getCroppedCanvas');
	LODOP=getLodopObj();
	LODOP.PRINT_INIT("打印车辆裁剪图片");
	pichc(canvasImg,wwtoPx,hhtoPx,LODOP,"N");
	//LODOP.ADD_PRINT_IMAGE(0,0,wwtoPx,hhtoPx,"<img border='0' src='"+canvasImg.toDataURL()+"'width='"+wwtoPx+"' height='"+hhtoPx+"'/>");
	//LODOP.SET_PRINT_STYLEA(0,"Stretch",1);//(可变形)扩展缩放模式
	//LODOP.PRINT_SETUP();
	
}
function printImg(){
	
	var hhtoPx=$("#divMMHeight").height();
	var wwtoPx=$("#divMMHeight").width();
	
	var canvasImg = $("#imageCorpp").cropper('getCroppedCanvas');
	LODOP=getLodopObj();
	
	pichc(canvasImg,wwtoPx,hhtoPx,LODOP,"Y");
	//LODOP.ADD_PRINT_IMAGE(0,0,wwtoPx,hhtoPx,"<img border='0' src='"+canvasImg.toDataURL()+"'width='"+wwtoPx+"' height='"+hhtoPx+"'/>");
	
	//LODOP.PREVIEW();
	
}


function rotate(i){
	if (i == '0'){
		$("#imageCorpp").cropper('rotate',-90);
	}else{
		$("#imageCorpp").cropper('rotate',90);
	}
}

function uploadPlat(){
	var row = $("#vehCheckInfoDG").datagrid("getSelected");
	if(row!=null){
		
		$.post("../../vheCheckInfo/uploadPlat",{"lsh":row.lsh,"cycs":row.cycs},function(data){
			$.messager.alert("提示",data.message,"info");
		},"json").complete(function(){
			$.messager.progress('close');
		});
	}else{
		$.messager.alert("提示","请选择一行数据","warning");
	}
}

function pichc(canvasImg,picW,picH,LODOP,preview){
	//debugger;
	//需要合成的图片张数
	var picCou = 1;
	var tphe = comm.getBaseParames("tphc");
	if (tphe.length > 0){
		picCou = tphe[0].id;
	}
	
    var data1= new Array();
    for(var i=0;i<picCou;i++){
        data1[i]=canvasImg.toDataURL();
    }
    var c=document.createElement('canvas'),
        ctx=c.getContext('2d'),
        len=data1.length;
    c.width=picW;
    c.height=(picH*picCou)+((picCou-1)*20);
    ctx.rect(0,0,c.width,c.height);
    ctx.fillStyle='transparent';//画布填充颜色
    ctx.fill();
    function drawing(n){
        if(n<len){
            var img=new Image;
            //img.crossOrigin = 'Anonymous'; //解决跨域
            img.src=data1[n];
            img.onload=function(){
            	//console.log((picH*n)+20+"  "+picW+" "+picH);
                ctx.drawImage(img,0,(picH*n)+(n*20),picW,picH);
                drawing(n+1);//递归
            }
        }else{
            //保存生成作品图片
        	LODOP.ADD_PRINT_IMAGE(0,0,picW,c.height,"<img border='0' src='"+c.toDataURL()+"'width='"+picW+"' height='"+c.height+"'/>");
            //convertCanvasToImage(c);
            //Canvas2Image.saveAsJPEG("D:\\111.jpg"); //保存到电脑
        	if(preview == "Y"){
        		LODOP.PREVIEW();
        	}else{
        		LODOP.SET_PRINT_STYLEA(0,"Stretch",1);//(可变形)扩展缩放模式
        		LODOP.PRINT_SETUP();
        	}
        }
    }
    drawing(0);
}
function convertCanvasToImage(canvas) {
    var hc_image = new Image();
    hc_image.src = canvas.toDataURL("image/png");
    $('#imgBox').html(hc_image);
}