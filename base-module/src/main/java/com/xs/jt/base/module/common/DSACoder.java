package com.xs.jt.base.module.common;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

/**
 * DSA安全编码组件
 * 
 * @author lzw
 * @version 1.0
 * @since 1.0
 */
public abstract class DSACoder extends Coder {

	public static final String ALGORITHM = "DSA";


	/**
	 * 校验数字签名
	 * 
	 * @param data
	 *            加密数据
	 * @param publicKey
	 *            公钥
	 * @param sign
	 *            数字签名
	 * 
	 * @return 校验成功返回true 失败返回false
	 * @throws Exception
	 * 
	 */
	public static boolean verify(byte[] data, String publicKey, String sign)
			throws Exception {

		// 解密由base64编码的公钥
		byte[] keyBytes = decryptBASE64(publicKey);

		// 构造X509EncodedKeySpec对象
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

		// ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		// 取公钥匙对象
		PublicKey pubKey = keyFactory.generatePublic(keySpec);

		Signature signature = Signature.getInstance(keyFactory.getAlgorithm());
		signature.initVerify(pubKey);
		signature.update(data);
		
		// 验证签名是否正常
		return signature.verify(decryptBASE64(sign));
	}

}
