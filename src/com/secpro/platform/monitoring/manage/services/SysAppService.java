package com.secpro.platform.monitoring.manage.services;

import com.secpro.platform.monitoring.manage.common.services.IBaseService;

public interface SysAppService extends IBaseService{
	public String queryAppByRoleid(String roleid);
}
