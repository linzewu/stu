function formatterSize(value,row,index){
	return (value/1024/1024).toFixed(2);
}

function formatterHpzl(value) {
	return comm.getParamNameByValue("hpzl", value);
}