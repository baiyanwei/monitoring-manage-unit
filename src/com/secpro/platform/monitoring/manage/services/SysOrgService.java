package com.secpro.platform.monitoring.manage.services;

import java.util.List;

import com.secpro.platform.monitoring.manage.common.services.IBaseService;

public interface SysOrgService  extends IBaseService{
	public List getOrgTreeByOrgId(String orgId);
}