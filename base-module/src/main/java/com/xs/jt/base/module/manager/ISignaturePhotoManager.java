package com.xs.jt.base.module.manager;

import com.xs.jt.base.module.entity.SignaturePhoto;

public interface ISignaturePhotoManager {
	
	public SignaturePhoto findByYhm(String yhm);
	
	public void saveSignaturePhoto(SignaturePhoto signaturePhoto);
	
	public String findSignaturePhotoByYhm(String yhm) ;

}
