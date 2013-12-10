package com.secpro.platform.monitoring.manage.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * @author baiyanwei 应用配置
 */

public class ApplicationConfiguration {
	final static public String LOGGING_CONFIG_NAME = "logging_cfg.xml";
	public static String APPLCATION_NAME = "inventory";
	//
	final static public String SAMPLE_DATE_FORMAT = "yyyy-MM-DD HH:mm:ss";

	public static String ULTRA_CMDB_IP = "10.129.240.195";
	public static String ULTRA_CMDB_PORT = "31088";
	public static String SYNCHRONIZED_ACCOUNT = "root";
	public static String SYSLOGRULEPATH;
	public static String SYSLOGWSPATH;
	public static String CONFIGRULEPATH;
	public static String WATCHDOGPORT;
	public static String WATCHDOGSERVERPATH;
	public static String DEFAULT_ENCODING="UTF-8";
	public static String HMAC_SHA1_ALGORITHM="HmacSHA1";
	public static Charset DEFAULT_CHARSET;
	public static String HMAC_SHA256_ALGORITHM="HmacSHA256";
	static{
		
		Properties configuration = new Properties();
		InputStream in=null;
		try {
            in=ApplicationConfiguration.class.getResourceAsStream("/config/conf.properties");
            configuration.load(in);
            SYSLOGWSPATH = configuration.getProperty("syslogwspath");
            SYSLOGRULEPATH=configuration.getProperty("syslogrulepath");
            CONFIGRULEPATH=configuration.getProperty("configrulepath");
            WATCHDOGPORT=configuration.getProperty("watchdogport");
            WATCHDOGSERVERPATH=configuration.getProperty("watchdogserverpath");
            DEFAULT_CHARSET = Charset.forName(DEFAULT_ENCODING);
        } catch (Exception e) {
            e.printStackTrace();
           
        }finally{
        	if(in!=null){
        		try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }
		
	}
	public ApplicationConfiguration(){
		
	}
}
