package com.secpro.platform.monitoring.manage.services.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.TaskScheduleDao;
import com.secpro.platform.monitoring.manage.entity.SysOperation;
import com.secpro.platform.monitoring.manage.services.TaskScheduleService;

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

	public List<SysOperation> getSystemOperation(String typeCode) {
		if (typeCode == null) {
			return findAll(SysOperation.class);
		} else {

		}
		return null;
	}
}
