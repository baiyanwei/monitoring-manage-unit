package com.secpro.platform.monitoring.manage.services.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.BaselineTemplateDao;
import com.secpro.platform.monitoring.manage.services.BaselineTemplateService;
@Service("BaselineTemplateServiceImpl")
public class BaselineTemplateServiceImpl extends BaseService implements BaselineTemplateService{
	private BaselineTemplateDao dao;

	public BaselineTemplateDao getDao() {
		return dao;
	}
	@Resource(name="BaselineTemplateDaoImpl")
	public void setDao(BaselineTemplateDao dao) {
		this.dao = dao;
	}
	public List getAllBaseLineTemplate(List templateList){
		return dao.getAllBaseLineTemplate(templateList);
	}
}
