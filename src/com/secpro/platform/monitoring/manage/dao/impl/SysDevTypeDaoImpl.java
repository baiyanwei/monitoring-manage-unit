package com.secpro.platform.monitoring.manage.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;

import com.secpro.platform.monitoring.manage.common.dao.impl.BaseDao;
import com.secpro.platform.monitoring.manage.dao.SysDevTypeDao;

@Repository("SysDevTypeDaoImpl")
public class SysDevTypeDaoImpl extends BaseDao implements SysDevTypeDao{
	public String deleteDevtypeByCompanyId(final String companyId){
		final StringBuilder sb=new StringBuilder();
		getSessionFactory().getCurrentSession().doWork(new Work(){
			public void execute(Connection connection) {
				System.out.println("------------111111111111");
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
}
