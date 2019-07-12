package com.xs.jt.hwvideo.manager.decode;

import com.xs.jt.hwvideo.manager.WeightDecodeAbstract;

public class DemoWeightDecode extends WeightDecodeAbstract {
	

	@Override
	public Float decode(byte[] data) {
		
		if(data.length>=8) {
			String strWeight =  new String(new byte[]{data[2],data[3],data[4],data[5],data[6],data[7]});
			this.weightContainer.setWeight(Float.valueOf(strWeight));
		}
		
		//System.out.println(new String(new byte[]{data[2],data[3],data[4],data[5],data[6],data[7],data[8],data[9]}));
		
		return 0f;
	}

}
