var camplayer;
var playerzt;    //0空闲 1忙碌
var serverDir = "http://192.168.51.10:8088/video"
var selectUrl;

//新建Ajax对象
function newAjaxObject()
{
    var xhe;
    if(window.ActiveXObject)
    {
        xhe = new ActiveXObject("Microsoft.XMLHTTP");
    }
    else
    {   
        xhe = new XMLHttpRequest();
    }
    return xhe;
}


function initPlay()
{ 
   camplayer =  document.getElementById("camplayer");
    initHPZL();
    playerzt=0;
    selectUrl="";
    var tmpinsid=""
    tmpinsid = document.getElementById("hiddeJylsh").value;
    //tmpinsid = document.getElementById('<%=hiddeJylsh.ClientID %>').value;
    document.getElementById("insid").value = tmpinsid;
    if(tmpinsid!="")
    {
    //   searchVideo();
    }
}

//查询车辆
function searchVideo()
{
    //alert(document.getElementById("hphm").value+document.getElementById("hpzl").value+document.getElementById("hiddeJylsh").value);
    var xhr = newAjaxObject();
    url = "Play.aspx?";
    var postData = document.getElementById("insid").value;
    url = url +"insid="+escape(postData);
    postData = document.getElementById("hphm").value;
    url = url +"&hphm="+escape(postData);
    postData = document.getElementById("hpzl").value;
    url = url +"&hpzl="+escape(postData);
    //confirm(url);
    xhr.onreadystatechange = function()
    {
        if(xhr.readyState == 4)
            {
                var str = xhr.responseText;
                //data[0] 最终列表 data[1]详细列表 data[2]检验流水号
                var data = str.split('||');  
                var videotab = data[1];
                
                document.getElementById("videoList").innerHTML = videotab;
                videotab ="";
                document.getElementById("videoListall").innerHTML = videotab;
                
                document.getElementById("insid").value = data[2];
                document.getElementById("hphm").value = data[3];
                document.getElementById("hpzl").value = data[4];
            }
    }
    xhr.open("Get",url,true);
    xhr.send(null);
}

//初始化号牌种类
function initHPZL()
{
    initFpxmmc("黄牌大型","01","hpzl");
    initFpxmmc("蓝牌小型","02","hpzl");
    initFpxmmc("普通摩托","07","hpzl");
    initFpxmmc("轻型摩托","08","hpzl");
    initFpxmmc("低速汽车","13","hpzl");
    initFpxmmc("拖拉机","14","hpzl");
    initFpxmmc("警用汽车","23","hpzl");
    initFpxmmc("黑牌外籍","06","hpzl");
    initFpxmmc("挂车号牌","15","hpzl");
    initFpxmmc("黄牌教练","16","hpzl");
    initFpxmmc("警用摩托","24","hpzl");
    initFpxmmc("其他号牌","99","hpzl");
}

function initFpxmmc(mc,dm,i)
{
    var newOption = document.createElement("option");
    newOption.text = mc;
    newOption.value = dm;
    document.getElementById(i).options.add(newOption);
}
//播放视频
function playVideo(i) 
{
    stopVideoHC();
	// 等待释放资源
	var delayTime = 0;
	if (selectUrl != "") {
		delayTime = 1500;
	}
	// 设置播放路径、并延时播放
	selectUrl =  serverDir+document.getElementById("URL"+i).value;
	setTimeout("playVideoHC('')", delayTime);
}
//海康播放
function playVideoHC(purl)
{
     if (purl != "") {
		selectUrl = purl;
	 }
      playerzt=1;
      camplayer.Open('http://localhost:8082/cmsvideo/static/ck/11_1_1_123456.mp4');
      camplayer.Play();
}
//海康暂停
function pauseVideoHC()
{
     camplayer.Pause();
}
//海康停止
function stopVideoHC()
{
    camplayer.Stop();
    playerzt=0;
}

