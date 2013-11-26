package com.secpro.platform.monitoring.manage.dao;

import java.util.List;

import com.secpro.platform.monitoring.manage.common.dao.IBaseDao;

public interface BaselineTemplateDao extends IBaseDao {
	public List getAllBaseLineTemplate(List templateList);
}
