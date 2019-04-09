package com.xs.jt.srms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xs.jt.base.module.entity.BaseEntity;
@Scope("prototype")
@Component("storeRoom")
@Entity
@Table(name = "tm_Store_Room")
public class StoreRoom extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//档案室编号
	@Column
	private String archivesNo;
	
	//档案架编号
	@Column
	private String rackNo;
	
	//档案架列数
	@Column
	private Integer rackCols;
	
	//档案架行数
	@Column
	private Integer rackRows;
	
	//存放类别
	@Column(length=1)
	private String storageType;
	
	//档案格容量
	@Column
	private Integer cellCapacity;

	public String getArchivesNo() {
		return archivesNo;
	}

	public void setArchivesNo(String archivesNo) {
		this.archivesNo = archivesNo;
	}

	public String getRackNo() {
		return rackNo;
	}

	public void setRackNo(String rackNo) {
		this.rackNo = rackNo;
	}

	public Integer getRackCols() {
		return rackCols;
	}

	public void setRackCols(Integer rackCols) {
		this.rackCols = rackCols;
	}

	public Integer getRackRows() {
		return rackRows;
	}

	public void setRackRows(Integer rackRows) {
		this.rackRows = rackRows;
	}

	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public Integer getCellCapacity() {
		return cellCapacity;
	}

	public void setCellCapacity(Integer cellCapacity) {
		this.cellCapacity = cellCapacity;
	}
	
	

}
