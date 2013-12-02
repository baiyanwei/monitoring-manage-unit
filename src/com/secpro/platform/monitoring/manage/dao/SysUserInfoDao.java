package com.secpro.platform.monitoring.manage.dao;

import java.util.List;
import java.util.Map;

import com.secpro.platform.monitoring.manage.common.dao.IBaseDao;
import com.secpro.platform.monitoring.manage.entity.SysUserInfo;

public interface SysUserInfoDao extends IBaseDao{
	public boolean createUserRoleMapping(String[] userIds,String[] roleIds);
	public List getRoleByUser(Long userId);
	public Map getAllApp(SysUserInfo user);
}
