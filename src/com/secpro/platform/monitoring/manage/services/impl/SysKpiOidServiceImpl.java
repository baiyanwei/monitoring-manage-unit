package com.secpro.platform.monitoring.manage.services.impl;

import javax.annotation.Resource;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.SysKpiOidDao;
import com.secpro.platform.monitoring.manage.services.SysKpiOidService;


public class SysKpiOidServiceImpl extends BaseService implements SysKpiOidService{
	private SysKpiOidDao dao;

	public SysKpiOidDao getDao() {
		return dao;
	}
	@Resource(name="SysKpiOidDaoImpl")
	public void setDao(SysKpiOidDao dao) {
		this.dao = dao;
	}
}
