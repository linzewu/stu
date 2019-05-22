package com.xs.jt.hwvideo.manager.decode;

import com.xs.jt.hwvideo.manager.WeightDecodeAbstract;

public class DemoWeightDecode extends WeightDecodeAbstract {
	

	@Override
	public Float decode(byte[] data) {
		this.weightContainer.setWeight(1433f);
		return 1433f;
	}

}
