package com.secpro.platform.monitoring.manage.services;

import java.util.List;
import java.util.Map;

import com.secpro.platform.monitoring.manage.common.services.IBaseService;
import com.secpro.platform.monitoring.manage.entity.SysUserInfo;

public interface SysUserInfoService extends IBaseService{
	public boolean createUserRoleMapping(String[] userIds,String[] roleIds);
	public List getRoleByUser(Long userId);
	public Map getAllApp(SysUserInfo user);
}
