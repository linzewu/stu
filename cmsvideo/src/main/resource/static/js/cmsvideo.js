$.extend(
	$.fn.validatebox.defaults.rules,
	{
		serialCodeVad : {
			validator : function(value, param) {				
				var bflag=false;				
				
				var data={};
				data.serialCode=value;
				var id =$(param[0]).val();
				if(id!=null&&$.trim(id)!=""){
					return true;
				}
				var r = $.ajax({url:'../../pdaInfo/validateSerialCode',dataType:"json",data:data,async:false,cache:false,type:"post"}).responseText;
				bflag=r=="true";
				if(!bflag){
					param[1]='串码已存在';
				}				
				
				return bflag;
			},
			message : '{1}'
		}
	});

function formatterSize(value,row,index){
	return (value/1024/1024).toFixed(2);
}

function formatterHpzl(value) {
	return comm.getParamNameByValue("hpzl", value);
}

function mergeCellsByField(tableId,colList){
    var ColArray = colList.split(",");
    var tTable = $('#'+tableId);
    var TableRowCnts=tTable.datagrid("getRows").length;
    var tmpA;
    var tmpB;
    var PerTxt = "";
    var CurTxt = "";
    var alertStr = "";
    for (j=ColArray.length-1;j>=0 ;j-- )
    {
        PerTxt="";
        tmpA=1;
        tmpB=0;
         
        for (i=0;i<=TableRowCnts ;i++ )
        {
            if (i==TableRowCnts)
            {
                CurTxt="";
            }
            else
            {
                CurTxt=tTable.datagrid("getRows")[i][ColArray[j]];
            }
            if (PerTxt==CurTxt)
            {
                tmpA+=1;
            }
            else
            {
                tmpB+=tmpA;
                tTable.datagrid('mergeCells',{
                    index:i-tmpA,
                    field:ColArray[j],
                    rowspan:tmpA,
                    colspan:null
                });
                tmpA=1;
            }
            PerTxt=CurTxt;
        }
    }
}