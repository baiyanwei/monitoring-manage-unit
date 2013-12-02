package com.secpro.platform.monitoring.manage.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity(name="SysUserInfo")
@Table(name="sys_user_info")
public class SysUserInfo {
	@Id
	@Column  
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="generator")   
	@SequenceGenerator( name ="generator",allocationSize = 1,sequenceName="sys_user_info_seq")   
	private Long id;
	@Column(name="account")
	private String account;
	@Column(name="USER_NAME")
	private String userName;
	@Column(name="password")
	private String password;
	@Column(name="cdate")
	private String cdate;
	@Column(name="email")
	private String email;
	@Column(name="mobel_tel")
	private String mobelTel;
	@Column(name="office_tel")
	private String officeTel;
	@Column(name="enable_account")
	private String enableAccount;
	@Column(name="user_desc")
	private String userDesc;
	@Column(name="mdate")
	private String mdate;
	@Column(name="deleted")
	private String deleted;
	@Column(name="delete_date")
	private String deleteDate;
	@Column(name="org_id")
	private Long orgId;
	@Transient
	private String orgName;
	@Transient
	private Map app=new HashMap();
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Map getApp() {
		return app;
	}
	public void setApp(Map app) {
		this.app = app;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	public String getMobelTel() {
		return mobelTel;
	}
	public void setMobelTel(String mobelTel) {
		this.mobelTel = mobelTel;
	}
	public String getOfficeTel() {
		return officeTel;
	}
	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}
	public String getEnableAccount() {
		return enableAccount;
	}
	public void setEnableAccount(String enableAccount) {
		this.enableAccount = enableAccount;
	}
	public String getUserDesc() {
		return userDesc;
	}
	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}
	public String getMdate() {
		return mdate;
	}
	public void setMdate(String mdate) {
		this.mdate = mdate;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getDeleteDate() {
		return deleteDate;
	}
	public void setDeleteDate(String deleteDate) {
		this.deleteDate = deleteDate;
	}
	
}
