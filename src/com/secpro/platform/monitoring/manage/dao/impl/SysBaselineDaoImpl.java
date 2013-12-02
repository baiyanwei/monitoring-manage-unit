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
import com.secpro.platform.monitoring.manage.dao.SysBaselineDao;
import com.secpro.platform.monitoring.manage.util.JdbcUtil;

@Repository("SysBaselineDaoImpl")
public class SysBaselineDaoImpl extends BaseDao implements SysBaselineDao{
	private DataSource dataSource;
	public DataSource getDataSource() {
		return dataSource;
	}
	@Resource(name="dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public boolean deleteBaseLine(String[] baseLineIds){
		Connection con=null;
		PreparedStatement sta=null;
		PreparedStatement sta1=null;
		PreparedStatement sta2=null;
		boolean flag=true;
		try {
			con=dataSource.getConnection();
			con.setAutoCommit(false);
			sta=con.prepareStatement("delete baseline_template_mapping  where baseline_id=? ");
			sta1=con.prepareStatement("delete baseline_rule where baseline_id=?");
			sta2=con.prepareStatement("delete sys_baseline where id =?");
			for(int i=0;i<baseLineIds.length;i++){
				sta.setLong(1, Long.parseLong(baseLineIds[i]));
				sta.execute();
				sta1.setLong(1, Long.parseLong(baseLineIds[i]));
				sta1.execute();
				sta2.setLong(1, Long.parseLong(baseLineIds[i]));
				sta2.execute();
			}
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag=false;
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally{
			JdbcUtil.close( sta);
			JdbcUtil.close( sta1);
			JdbcUtil.close( sta2);
			JdbcUtil.close( con);
		}
		return flag;
	}
	public boolean createBaseLineRule(String typeCode ,String baseLineId,String rule){
		Connection con=null;
		Statement sta=null;
		boolean flag=true;
		try {
			con=dataSource.getConnection();
			con.setAutoCommit(false);
			sta=con.createStatement();
			sta.execute("delete from baseline_rule where baseline_id="+baseLineId+" and type_code='"+typeCode+"'");
			sta.close();
			sta=con.createStatement();
			sta.execute("insert into baseline_rule(baseline_id,type_code,rule) values("+baseLineId+",'"+typeCode+"','"+rule+"')");
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag=false;
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally{
			JdbcUtil.close( sta);
			JdbcUtil.close( con);
		}
		return flag;
	}
	public String getRule(String baselineId,String typeCode){
		Connection con=null;
		Statement sta=null;
		ResultSet rs=null;
		String rule="";
		try {
			con=dataSource.getConnection();
			sta=con.createStatement();
			rs=sta.executeQuery("select rule from baseline_rule where baseline_id="+baselineId+" and type_code='"+typeCode+"'");
			if(rs.next()){
				rule=rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JdbcUtil.close( con,sta,rs);
		}
		return rule;
	}
}
