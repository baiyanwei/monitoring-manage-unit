package com.secpro.platform.monitoring.manage.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.SysBaselineDao;
import com.secpro.platform.monitoring.manage.services.SysBaselineService;

@Service("SysBaselineServiceImpl")
public class SysBaselineServiceImpl extends BaseService implements SysBaselineService{
	private SysBaselineDao dao;

	public SysBaselineDao getDao() {
		return dao;
	}
	@Resource(name="SysBaselineDaoImpl")
	public void setDao(SysBaselineDao dao) {
		this.dao = dao;
	}
	public boolean deleteBaseLine(String[] baseLineIds){
		return dao.deleteBaseLine(baseLineIds);
	}
	public boolean createBaseLineRule(String typeCode ,String baseLineId,String rule){
		return dao.createBaseLineRule(typeCode, baseLineId, rule);
	}
	public String getRule(String baselineId,String typeCode){
		return dao.getRule(baselineId, typeCode);
	}
}
