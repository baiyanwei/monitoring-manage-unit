package com.secpro.platform.monitoring.manage.dao;

import com.secpro.platform.monitoring.manage.common.dao.IBaseDao;
import com.secpro.platform.monitoring.manage.entity.SyslogBean;

public interface SyslogRuleDao extends IBaseDao{
	public boolean syslogRuleSave(SyslogBean syslogB);
	public boolean syslogRuleDelete(String typeCode);
}
