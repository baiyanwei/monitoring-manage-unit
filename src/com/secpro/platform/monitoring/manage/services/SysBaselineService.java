package com.secpro.platform.monitoring.manage.services;

import com.secpro.platform.monitoring.manage.common.services.IBaseService;

public interface SysBaselineService extends IBaseService{
	public boolean deleteBaseLine(String[] baseLineIds);
	public boolean createBaseLineRule(String typeCode ,String baseLineId,String rule);
	public String getRule(String baselineId,String typeCode);
}
