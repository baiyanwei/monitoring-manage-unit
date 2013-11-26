package com.secpro.platform.monitoring.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="RawKpi")
@Table(name="raw_kpi")
public class RawKpi {
	@Id
	@Column(name="res_id") 
	private Long resId;
	@Column(name="kpi_id")
	private Long kpiId;
	@Column(name="cdate")
	private String cdate;
	@Column(name="value_str")
	private String valueStr;
	@Column(name="value_num")
	private Double valueNum;
	public Long getResId() {
		return resId;
	}
	public void setResId(Long resId) {
		this.resId = resId;
	}
	
	public Long getKpiId() {
		return kpiId;
	}
	public void setKpiId(Long kpiId) {
		this.kpiId = kpiId;
	}
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	public String getValueStr() {
		return valueStr;
	}
	public void setValueStr(String valueStr) {
		this.valueStr = valueStr;
	}
	public Double getValueNum() {
		return valueNum;
	}
	public void setValueNum(Double valueNum) {
		this.valueNum = valueNum;
	}
	
}