package com.secpro.platform.monitoring.manage.services.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.TaskScheduleDao;
import com.secpro.platform.monitoring.manage.entity.SysCity;
import com.secpro.platform.monitoring.manage.entity.SysCommand;
import com.secpro.platform.monitoring.manage.entity.SysOperation;
import com.secpro.platform.monitoring.manage.entity.SysResAuth;
import com.secpro.platform.monitoring.manage.services.TaskScheduleService;
import com.secpro.platform.monitoring.manage.util.Assert;

@Service("TaskScheduleServiceImpl")
public class TaskScheduleServiceImpl extends BaseService implements TaskScheduleService {
	private TaskScheduleDao dao;

	public TaskScheduleDao getDao() {
		return dao;
	}

	@Resource(name = "TaskScheduleDaoImpl")
	public void setDao(TaskScheduleDao dao) {
		this.dao = dao;
	}

	@SuppressWarnings("unchecked")
	public <T extends Object> List<T> findAll(Class<?> clazz) {
		Session session = null;
		try {
			session = this.dao.getSessionFactory().openSession();
			String queryString = "from " + clazz.getSimpleName();
			Query queryObject = session.createQuery(queryString);
			return (List<T>) queryObject.list();
		} catch (RuntimeException re) {
			re.printStackTrace();
			throw re;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public List<SysOperation> getSysOperationByTypeCode(String typeCode) {
		String whereSql = null;
		if (Assert.isEmptyString(typeCode) == false) {
			whereSql = "TYPE_CODE='" + typeCode + "'";
		}
		return this.dao.findAll(SysOperation.class, whereSql, 1, 1000, null);
	}

	public List<SysCommand> getSystCommandByTypeCode(String typeCode) {
		String whereSql = null;
		if (Assert.isEmptyString(typeCode) == false) {
			whereSql = "TYPE_CODE='" + typeCode + "'";
		}
		return this.dao.findAll(SysCommand.class, whereSql, 1, 1000, null);
	}

	public List<SysResAuth> getSysResAuthByResId(long resId) {
		return this.dao.findAll(SysResAuth.class, "RES_ID=" + resId, 1, 1000, null);
	}

	public SysCity getSysCityBycityCode(String cityCode) {
		if (Assert.isEmptyString(cityCode) == true) {
			return null;
		}
		List<SysCity> cityList = this.dao.findAll(SysCity.class, "CITY_CODE='" + cityCode + "'", 1, 1000, null);
		if (cityList == null || cityList.isEmpty()) {
			return null;
		}
		return cityList.get(0);
	}

	public TaskScheduleDao getTaskScheduleDao() {
		return this.dao;
	}

}
