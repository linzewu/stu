package com.xs.jt.base.module.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@MappedSuperclass
@JsonIgnoreProperties(value ={"hibernateLazyInitializer","handler","fieldHandler"})
public abstract class BaseEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
//	@GenericGenerator(name = "idGenerator", strategy = "identity")
//	@GeneratedValue(generator = "idGenerator")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	@Column(length=255)
	private String vehjyw;
	
	
	@Transient
	private boolean checkBitOk=true;
	

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
	
	public String getVehjyw() {
		return vehjyw;
	}

	public void setVehjyw(String vehjyw) {
		this.vehjyw = vehjyw;
	}

	public boolean isCheckBitOk() {
		return checkBitOk;
	}


	public void checkBit() {
		try {
			String bit1 = md5(this.toString());
			System.out.println(this.toString());
			System.out.println("bit1:"+bit1+"\t vehjyw:" +vehjyw);
			System.out.println((!StringUtils.isEmpty(vehjyw)) && !vehjyw.equals(bit1));

			if((!StringUtils.isEmpty(vehjyw))&& !vehjyw.equals(bit1)) {
				checkBitOk=false;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
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
