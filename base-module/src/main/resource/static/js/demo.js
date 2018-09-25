

function initData(){
	var datas=[];
	for(var i=0;i<4;i++){
	   var data={};
	   data["name"]="窗口"+(i+1);
	   data["data"]=[];
	   for(var j=0;j<12;j++){
		   data["data"].push(Math.ceil(Math.random()*85+440));
	   }
	   datas.push(data);
	}
	return datas;
}
var data1,data2;
$(function(){
	data1 = initData();
	data2 = initData();
})

function loadReport(title,index){
	 createYue('#yue'+(index+1),"2014年"+title+'月度报表',index==0?data1:data2);
}

function loadReport2(title,index){
	createJd('#jidu'+(index+1),"2014年"+title+'季度报表');
}

function loadReporte3(title,index){
	createYear('year'+(index+1),title+'历年统计报表');
}

function createYear(id,title,datas){
	 // Set up the chart
    var chart = new Highcharts.Chart({
        chart: {
            renderTo: id,
            type: 'column',
            margin: 75,
            options3d: {
                enabled: true,
                alpha: 15,
                beta: 15,
                depth: 50,
                viewDistance: 25
            }
        },
        title: {
            text: title
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            categories: [
                '2005年',
                '2006年',
                '2007年',
                '2008年',
                '2009年',
                '2010年',
                '2011年',
                '2012年',
                '2013年',
                '2014年'
            ]
        },
        plotOptions: {
            column: {
                depth: 25
            }
        },
        series: [{
        	name:'年份',
            data: [11911, 13998, 14551, 14911, 16012, 17112, 17512, 17475, 18675, 18375]
        }]
    });
}

function createJd(id,title,datas){
	 $(id).highcharts({
	        chart: {
	            type: 'pie',
	            options3d: {
	                enabled: true,
	                alpha: 45,
	                beta: 0
	            }
	        },
	        title: {
	            text: title
	        },
	        tooltip: {
	            pointFormat: '{series.name}:{point.y}</b>'
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                depth: 35,
	                dataLabels: {
	                    enabled: true,
	                    format: '{point.name}'
	                }
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: '业务量',
	            data: [
	                ['第一季度',  1764],
	                ['第二季度',    1811],
	                {
	                    name: '第三季度',
	                    y: 1920,
	                    sliced: true,
	                    selected: true
	                },
	                ['第四季度',    876],
	            ]
	        }]
	    });
}

function createYue(id,title,datas){
	console.log(datas)
	 $(id).highcharts({
	        title: {
	            text: title
	        },
	        subtitle: {
	            text: ''
	        },
	        xAxis: {
	            categories: ['1月', '2月', '3月', '4月', '5月', '6月','7月', '8月', '9月', '10月', '11月', '12月']
	        },
	        yAxis: {
	            title: {
	                text: '窗口'
	            },
	            plotLines: [{
	                value: 0,
	                width: 1,
	                color: '#808080'
	            }]
	        },
	        tooltip: {
	            valueSuffix: '个'
	        },
	        legend: {
	            layout: 'vertical',
	            align: 'right',
	            verticalAlign: 'middle',
	            borderWidth: 0
	        },
	        series: datas
	    });
}

Highcharts.theme = {
		colors: ['#058DC7', '#50B432', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4'],
		chart: {
			backgroundColor: {
				linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
				stops: [
					[0, 'rgb(255, 255, 255)'],
					[1, 'rgb(240, 240, 255)']
				]
			},
			borderWidth: 2,
			plotBackgroundColor: 'rgba(255, 255, 255, .9)',
			plotShadow: true,
			plotBorderWidth: 1
		},
		title: {
			style: {
				color: '#000',
				font: 'bold 16px "Trebuchet MS", Verdana, sans-serif'
			}
		},
		subtitle: {
			style: {
				color: '#666666',
				font: 'bold 12px "Trebuchet MS", Verdana, sans-serif'
			}
		},
		xAxis: {
			gridLineWidth: 1,
			lineColor: '#000',
			tickColor: '#000',
			labels: {
				style: {
					color: '#000',
					font: '11px Trebuchet MS, Verdana, sans-serif'
				}
			},
			title: {
				style: {
					color: '#333',
					fontWeight: 'bold',
					fontSize: '12px',
					fontFamily: 'Trebuchet MS, Verdana, sans-serif'

				}
			}
		},
		yAxis: {
			minorTickInterval: 'auto',
			lineColor: '#000',
			lineWidth: 1,
			tickWidth: 1,
			tickColor: '#000',
			labels: {
				style: {
					color: '#000',
					font: '11px Trebuchet MS, Verdana, sans-serif'
				}
			},
			title: {
				style: {
					color: '#333',
					fontWeight: 'bold',
					fontSize: '12px',
					fontFamily: 'Trebuchet MS, Verdana, sans-serif'
				}
			}
		},
		legend: {
			itemStyle: {
				font: '9pt Trebuchet MS, Verdana, sans-serif',
				color: 'black'

			},
			itemHoverStyle: {
				color: '#039'
			},
			itemHiddenStyle: {
				color: 'gray'
			}
		},
		labels: {
			style: {
				color: '#99b'
			}
		},

		navigation: {
			buttonOptions: {
				theme: {
					stroke: '#CCCCCC'
				}
			}
		}
	};
var highchartsOptions = Highcharts.setOptions(Highcharts.theme);
