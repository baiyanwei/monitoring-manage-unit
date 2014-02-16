package com.secpro.platform.monitoring.manage.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PasswdRuleUtil {
	public static long passwdLong=6l;
	public static long passwdTimeout=1l;
	public static long WrongTimes=5l;
	public static String isPasswdTimeout="1";
	public static long maxPwdLong=16l;
	static {
		Connection conn=null;
		Statement sta=null;
		ResultSet rs=null;
		try{	
			Class.forName(DBConfig.DRIVER);
			conn=DriverManager.getConnection(DBConfig.URL,DBConfig.USERNAME,DBConfig.PASSWORD);
			sta=conn.createStatement();
			rs=sta.executeQuery("select passwd_Long,passwd_Timeout,is_Passwd_Timeout,wrong_Times from sys_passwd_rule");
			if(rs.next()){
				passwdLong=rs.getLong(1);
				passwdTimeout=rs.getLong(2);
				isPasswdTimeout=rs.getString(3);
				WrongTimes=rs.getLong(4);				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
		} finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(sta!=null){
				try {
					sta.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public PasswdRuleUtil(){
		
	}
}
