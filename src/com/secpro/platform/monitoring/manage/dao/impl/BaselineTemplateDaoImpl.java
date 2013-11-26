package com.secpro.platform.monitoring.manage.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.secpro.platform.monitoring.manage.common.dao.impl.BaseDao;
import com.secpro.platform.monitoring.manage.dao.BaselineTemplateDao;
import com.secpro.platform.monitoring.manage.entity.BaselineTemplate;
import com.secpro.platform.monitoring.manage.entity.SysBaseline;

@Repository("BaselineTemplateDaoImpl")
public class BaselineTemplateDaoImpl extends BaseDao implements BaselineTemplateDao{
	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}
	@Resource(name="dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public List getAllBaseLineTemplate(List templateList){
		try {
			Connection con=dataSource.getConnection();
			PreparedStatement sta=null;
			if(templateList==null){
				return null;
			}
			for(int i=0;i<templateList.size();i++){
				BaselineTemplate bt=(BaselineTemplate)templateList.get(i);
				sta=con.prepareStatement("select b.id,b.baseline_desc,b.baseline_type,b.baselin_black_white,s.res_ip, s.res_name ,d.company_name from  baseline_template t, sys_baseline b ,baseline_template_mapping btm,sys_res_obj s,sys_dev_company d  where btm.template_id=? and t.id=btm.template_id and b.id=btm.baseline_id and b.id=s.template_id and t.company_code=d.company_code");
				sta.setLong(1, bt.getId());
				ResultSet rs=sta.executeQuery();
				while(rs.next()){
					SysBaseline sbl=new SysBaseline();
					sbl.setId(rs.getLong(1));
					sbl.setBaselineDesc(rs.getString(2));
					sbl.setBaselineType(rs.getString(3));
					sbl.setBaselinBlackWhite(rs.getString(4));
					bt.setResip(rs.getString(5));
					bt.setResname(rs.getString(6));
					bt.setCompanyName(rs.getString(7));
					bt.getBaseLineList().add(sbl);
					
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return templateList;
	}
}
