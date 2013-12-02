package com.secpro.platform.monitoring.manage.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.secpro.platform.monitoring.manage.common.dao.impl.BaseDao;
import com.secpro.platform.monitoring.manage.dao.SysAppDao;
import com.secpro.platform.monitoring.manage.util.JdbcUtil;

@Repository("SysAppDaoImpl")
public class SysAppDaoImpl extends BaseDao implements SysAppDao{
	private DataSource dataSource;
	public DataSource getDataSource() {
		return dataSource;
	}
	@Resource(name="dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public String queryAppByRoleid(String roleid){
		Connection con=null;
		Statement sta=null;
		ResultSet rs=null;
		String appids="";
		try {
			con=dataSource.getConnection();
			sta=con.createStatement();
			rs=sta.executeQuery("select id from sys_app a,sys_role_app rs where a.id=rs.app_id and rs.role_id="+roleid);
			int start=1;//如果start为1时，拼接appids时不加,号
			while(rs.next()){
				if(start==1){
					appids+=rs.getString(1);
					start++;
				}else{
					appids+=",";
					appids+=rs.getString(1);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JdbcUtil.close(con, sta, rs);
		}
		return appids;
	}
}
