package com.secpro.platform.monitoring.manage.util;

import javax.persistence.Entity;

/**
 * @author baiyanwei 应用配置
 */
@Entity
public class ApplicationConfiguration {
	final static public String LOGGING_CONFIG_NAME = "logging_cfg.xml";
	public static String APPLCATION_NAME = "inventory";
	//
	final static public String SAMPLE_DATE_FORMAT = "yyyy-MM-DD HH:mm:ss";

	public static String ULTRA_CMDB_IP = "10.129.240.195";
	public static String ULTRA_CMDB_PORT = "31088";
	public static String SYNCHRONIZED_ACCOUNT = "root";
}
