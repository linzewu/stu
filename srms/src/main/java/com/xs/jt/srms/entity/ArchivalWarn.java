package com.xs.jt.srms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xs.jt.base.module.entity.BaseEntity;

/**
 * 业务预警
 * @author admin
 *
 */
@Scope("prototype")
@Component("archivalWarn")
@Entity
@Table(name = "tm_Archival_Warn")
public class ArchivalWarn extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public final static String YJLX_DCBL = "多次办理业务预警";

		// 档案室编号
		@Column
		private String archivesNo;

		// 档案架编号
		@Column
		private String rackNo;

		// 档案架内行号
		@Column
		private Integer rackRow;

		// 档案架内列号
		@Column
		private Integer rackCol;
		
		//档案序号
		@Column
		private String fileNo;
		
		//预警类型
		@Column(length=150)
		private String warnType;
		
		//描述
		@Column(length=500)
		private String describe;
		
		@Column
		@DateTimeFormat(pattern = "yyyy-MM-dd")  
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
		private Date warnDate;
		
		@Transient
		@DateTimeFormat(pattern = "yyyy-MM-dd")  
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
		private Date Begin;
		
		@Transient
		@DateTimeFormat(pattern = "yyyy-MM-dd")  
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
		private Date end;

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

		public Integer getRackRow() {
			return rackRow;
		}

		public void setRackRow(Integer rackRow) {
			this.rackRow = rackRow;
		}

		public Integer getRackCol() {
			return rackCol;
		}

		public void setRackCol(Integer rackCol) {
			this.rackCol = rackCol;
		}

		public String getFileNo() {
			return fileNo;
		}

		public void setFileNo(String fileNo) {
			this.fileNo = fileNo;
		}

		public String getWarnType() {
			return warnType;
		}

		public void setWarnType(String warnType) {
			this.warnType = warnType;
		}

		public String getDescribe() {
			return describe;
		}

		public void setDescribe(String describe) {
			this.describe = describe;
		}

		public Date getWarnDate() {
			return warnDate;
		}

		public void setWarnDate(Date warnDate) {
			this.warnDate = warnDate;
		}

		public Date getBegin() {
			return Begin;
		}

		public void setBegin(Date begin) {
			Begin = begin;
		}

		public Date getEnd() {
			return end;
		}

		public void setEnd(Date end) {
			this.end = end;
		}
		
		

}
