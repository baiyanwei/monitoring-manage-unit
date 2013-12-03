package com.secpro.platform.monitoring.manage.dao;

import com.secpro.platform.monitoring.manage.common.dao.IBaseDao;

public interface SysBaselineDao extends IBaseDao{
	public boolean deleteBaseLine(String[] baseLineIds);
	public boolean createBaseLineRule(String typeCode ,String baseLineId,String rule);
	public String getRule(String baselineId,String typeCode);
}
