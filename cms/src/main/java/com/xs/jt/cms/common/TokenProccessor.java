package com.xs.jt.cms.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.springframework.util.Base64Utils;

/**  
 * 生成Token的工具类  
 *  
 */  
public class TokenProccessor {

	private TokenProccessor(){};  
    private static final TokenProccessor instance = new TokenProccessor();  
      
   public static TokenProccessor getInstance() {  
       return instance;  
   }  
 
   /**  
    * 生成Token  
    * @return  
    */  
   public String makeToken() {  
       String token = (System.currentTimeMillis() + new Random().nextInt(999999999)) + "";  
        try {  
           MessageDigest md = MessageDigest.getInstance("md5");  
           byte md5[] =  md.digest(token.getBytes());  
           return Base64Utils.encodeToString(md5); //BASE64Encoder encoder = new BASE64Encoder();  
           //return encoder.encode(md5);  
       } catch (NoSuchAlgorithmException e) {  
           // TODO Auto-generated catch block  
           e.printStackTrace();  
       }  
        return null;  
   }
   
}
