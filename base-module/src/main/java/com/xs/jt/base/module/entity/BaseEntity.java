package com.xs.jt.base.module.entity;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@MappedSuperclass
@JsonIgnoreProperties(value ={"hibernateLazyInitializer","handler","fieldHandler"})
public abstract class BaseEntity {
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	
	@Column(length=20)
	private String createUser;
	
	@Column(length=20)
	private String updateUser;
	
	@Column(length=20)
	private String createTime;
	
	@Column(length=20)
	private String updateTime;
	
	@Column
	private Integer seq;
	

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getCreateUser() {
		return createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public String getCreateTime() {
		return createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public static String md5(String str) throws UnsupportedEncodingException{  
	    String pwd = null;  
	    try {  
	        // 生成一个MD5加密计算摘要  
	        MessageDigest md = MessageDigest.getInstance("MD5");  
	        // 计算md5函数  
	        md.update(str.getBytes("GB2312"));  
	        // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符  
	        // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值  
	        pwd = new BigInteger(1, md.digest()).toString(16);  
	    } catch (NoSuchAlgorithmException e) {  
	        e.printStackTrace();
	    }  
	    return pwd;  
	}
}
