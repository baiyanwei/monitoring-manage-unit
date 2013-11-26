package com.secpro.platform.monitoring.manage.services;

import com.secpro.platform.monitoring.manage.common.services.IBaseService;

public interface SysDevTypeService extends IBaseService {
	public String deleteDevtypeByCompanyId(final String companyId);
}
