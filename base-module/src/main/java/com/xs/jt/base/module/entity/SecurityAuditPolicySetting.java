package com.xs.jt.base.module.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component("securityAuditPolicySetting")
@Entity
@Table(name = "TM_SAPS")
public class SecurityAuditPolicySetting extends BaseEntity{
	
	//策略编码
	//一天内访问次数超限
	public static final String 	VISIT_NUMBER_ONEDAY="visit_number_oneday";
	//一小时内访问次数超限
	public static final String 	VISIT_NUMBER_ONEHOUR="visit_number_onehour";
	//一分钟访问次数超限
	public static final String 	VISIT_NUMBER_ONEMINUTE="visit_number_oneminute";
	//特殊时间段访问
	public static final String 	SPECIAL_TIMESLOT_VISIT="special_timeslot_visit";
	//特殊日期访问
	public static final String 	SPECIAL_DATE_VISIT="special_date_visit";
	//特殊日期的特殊时间段访问
	public static final String 	SPECIAL_TIMESLOT_OF_DATE_VISIT="special_timeslot_of_date_visit";
	//账户一周未使用
	public static final String 	ACCOUNT_NOT_USED_AWEEK = "account_not_used_aweek";
	//账户一月未使用
	public static final String 	ACCOUNT_NOT_USED_AMONTH = "account_not_used_amonth";
	//账户一年未使用
	public static final String 	ACCOUNT_NOT_USED_AYEAR = "account_not_used_ayear";
	//账户锁定
	public static final String ACCOUNT_LOCK = "account_lock";
	//IP终端锁定(黑名单)
	public static final String IP_LOCK = "ip_lock";
	
//	@Id
//	@GenericGenerator(name = "idGenerator", strategy = "identity")
//	@GeneratedValue(generator = "idGenerator")
//	@Column(name="id")
//	private Integer id;
	
	@Column(length = 50)
	private String aqsjclbm;
	
	@Column(length = 120)
	private String aqsjcllxmc;
	
	@Column(length = 120)
	private String aqsjclzlxmc;
	
	@Column(length = 120)
	private String clz;
	
	@Column(length = 300)
	private String clzsm;
	
	@Column(length = 1)
	private String sfkq;
	@Transient
	List<SecurityAuditPolicySetting> updateList = new ArrayList<SecurityAuditPolicySetting>();

	public String getAqsjcllxmc() {
		return aqsjcllxmc;
	}

	public String getAqsjclzlxmc() {
		return aqsjclzlxmc;
	}

	public String getClzsm() {
		return clzsm;
	}

	public String getSfkq() {
		return sfkq;
	}

	public void setAqsjcllxmc(String aqsjcllxmc) {
		this.aqsjcllxmc = aqsjcllxmc;
	}

	public void setAqsjclzlxmc(String aqsjclzlxmc) {
		this.aqsjclzlxmc = aqsjclzlxmc;
	}

	public void setClzsm(String clzsm) {
		this.clzsm = clzsm;
	}

	public void setSfkq(String sfkq) {
		this.sfkq = sfkq;
	}

	public List<SecurityAuditPolicySetting> getUpdateList() {
		return updateList;
	}

	public void setUpdateList(List<SecurityAuditPolicySetting> updateList) {
		this.updateList = updateList;
	}

	public String getClz() {
		return clz;
	}

	public void setClz(String clz) {
		this.clz = clz;
	}

	public String getAqsjclbm() {
		return aqsjclbm;
	}

	public void setAqsjclbm(String aqsjclbm) {
		this.aqsjclbm = aqsjclbm;
	}
	

}
