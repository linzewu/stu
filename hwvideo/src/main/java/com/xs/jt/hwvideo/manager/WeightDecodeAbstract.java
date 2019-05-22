package com.xs.jt.hwvideo.manager;

import com.xs.jt.hwvideo.util.WeightContainer;

public abstract class WeightDecodeAbstract {
	
	protected WeightContainer weightContainer;
	
	public abstract Float decode(byte[] data);

	public WeightContainer getWeightContainer() {
		return weightContainer;
	}

	public void setWeightContainer(WeightContainer weightContainer) {
		this.weightContainer = weightContainer;
	}
	
	

}
