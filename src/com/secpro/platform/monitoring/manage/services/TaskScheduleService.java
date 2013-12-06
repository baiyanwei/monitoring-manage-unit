package com.secpro.platform.monitoring.manage.services;

import java.util.List;

import com.secpro.platform.monitoring.manage.common.services.IBaseService;
import com.secpro.platform.monitoring.manage.entity.SysOperation;

public interface TaskScheduleService extends IBaseService {
	public List<SysOperation> getSystemOperation(String typeCode);
}
