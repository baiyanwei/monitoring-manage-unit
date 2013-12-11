package com.secpro.platform.monitoring.manage.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;

import com.secpro.platform.monitoring.manage.common.dao.impl.BaseDao;
import com.secpro.platform.monitoring.manage.dao.SysDevTypeDao;
import com.secpro.platform.monitoring.manage.util.JdbcUtil;

@Repository("SysDevTypeDaoImpl")
public class SysDevTypeDaoImpl extends BaseDao implements SysDevTypeDao{
	private DataSource dataSource;
	public DataSource getDataSource() {
		return dataSource;
	}
	@Resource(name="dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public String deleteDevtypeByCompanyId(final String companyId){
		final StringBuilder sb=new StringBuilder();
		getSessionFactory().getCurrentSession().doWork(new Work(){
			public void execute(Connection connection) {
				Statement s = null;
				ResultSet rs = null;
				try {
					s=connection.createStatement();
					s.execute("delete from sys_dev_type s where s.company_code in (select company_code from sys_dev_company where id="+companyId+")");
					sb.append("0");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					sb.append("1");
				}finally{
					if(s!=null){
						try {
							s.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
			}
		});
		return sb.toString();
	}
	public String createTypeCode(String companyCode){	
		Connection con=null;
		Statement sta=null;
		ResultSet rs=null;
		long typeCode=0;
		try {
			con=dataSource.getConnection();
			sta=con.createStatement();
			rs=sta.executeQuery("SELECT max(type_code) from sys_dev_type d where d.company_code='"+companyCode+"'");
			if(rs.next()){
				if(rs.getString(1)!=null){
					typeCode=Long.parseLong(rs.getString(1))+1;
				}else{
					typeCode=Long.parseLong(companyCode)+1;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JdbcUtil.close(con,sta,rs);
		}
		return typeCode+"";
	}
}
