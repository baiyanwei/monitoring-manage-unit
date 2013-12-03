package com.secpro.platform.monitoring.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "MSUTask")
@Table(name = "MSU_TASK")
public class MSUTask {

	// final public static String ID_TITLE = "tid";
	// final public static String REGION_TITLE = "reg";
	// final public static String OPERATION_TITLE = "ope";
	// final public static String CREATE_AT_TITLE = "cat";
	// final public static String SCHEDULE_TITLE = "sch";
	// final public static String CONTENT_TITLE = "con";
	// final public static String META_DATA_TITLE = "mda";
	// final public static String RES_ID_TITLE = "rid";
	// final public static String IS_REALTIME_TITLE = "isrt";
	//
	// ID VARCHAR2(50) NOT NULL,
	// REGION VARCHAR2(50) NOT NULL,
	// CREATE_AT NUMBER(20) NOT NULL,
	// SCHEDULE VARCHAR2(255) NOT NULL,
	// OPERATION VARCHAR2(50) NOT NULL,
	// TARGET_IP VARCHAR2(50) NOT NULL,
	// TARGET_PORT NUMBER(5) NOT NULL,
	// META_DATA VARCHAR2(500) NOT NULL,
	// CONTENT VARCHAR2(1000) NOT NULL,
	// RES_ID NUMBER(20) NOT NULL,
	// IS_REALTIME NUMBER(1) DEFAULT 1
	@Id
	@Column(name = "ID", nullable = false)
	private String id = "";

	@Column(name = "REGION", nullable = false)
	private String region = "";

	@Column(name = "OPERATION", nullable = false)
	private String operation = "";

	@Column(name = "SCHEDULE", nullable = false)
	private String schedule = "";

	@Column(name = "CREATE_AT", nullable = false)
	private long createAt = 0;

	@Column(name = "TARGET_IP", nullable = false)
	private String targetIp = null;

	@Column(name = "TARGET_PORT", nullable = false)
	private int targetPort = 0;

	@Column(name = "META_DATA", nullable = false)
	private String metaData = null;

	@Column(name = "CONTENT", nullable = false)
	private String content = null;

	@Column(name = "RES_ID", nullable = false)
	private long resId = 0;

	@Column(name = "IS_REALTIME", nullable = false)
	private boolean isRealtime = false;

	public MSUTask() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public long getCreateAt() {
		return createAt;
	}

	public void setCreateAt(long createAt) {
		this.createAt = createAt;
	}

	public String getMetaData() {
		return metaData;
	}

	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}

	public boolean isRealtime() {
		return isRealtime;
	}

	public void setRealtime(boolean isRealtime) {
		this.isRealtime = isRealtime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getResId() {
		return resId;
	}

	public void setResId(long resId) {
		this.resId = resId;
	}

	public String getTargetIp() {
		return targetIp;
	}

	public void setTargetIp(String targetIp) {
		this.targetIp = targetIp;
	}

	public int getTargetPort() {
		return targetPort;
	}

	public void setTargetPort(int targetPort) {
		this.targetPort = targetPort;
	}
}
