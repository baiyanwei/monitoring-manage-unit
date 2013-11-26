package com.secpro.platform.monitoring.manage.services;

import java.util.List;
import java.util.Map;

import com.secpro.platform.monitoring.manage.common.services.IBaseService;

public interface BaselineTemplateService extends IBaseService{
	public List getAllBaseLineTemplate(List templateList);
	public boolean saveBaseLineTemplete(Long BaselineTemplateId,String[] baselineIds,Map<String,String> scores);
}
