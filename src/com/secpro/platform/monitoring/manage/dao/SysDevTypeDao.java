package com.secpro.platform.monitoring.manage.dao;

import com.secpro.platform.monitoring.manage.common.dao.IBaseDao;

public interface SysDevTypeDao extends IBaseDao {
	public String deleteDevtypeByCompanyId(final String companyId);
}
