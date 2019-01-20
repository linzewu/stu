package com.xs.jt.veh.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "MYTABLE")
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(name = "getJYLSH", procedureName = "getJYLSH", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "Tabel_Name", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "prefix", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "MaxSeq", type = String.class)})  })
public class MyTable implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id")
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}

