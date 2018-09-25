package com.xs.jt.base.module.util;

public class PageInfo {
	
	private Integer page;
	
	private Integer rows;

	public Integer getPage() {
		
		return (page-1)*rows;
	}

	public Integer getRows() {
		if(rows>500){
			return 500;
		}
		return rows;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}
	

}
