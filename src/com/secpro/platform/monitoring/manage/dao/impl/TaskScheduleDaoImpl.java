package com.secpro.platform.monitoring.manage.dao.impl;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.secpro.platform.monitoring.manage.common.dao.impl.BaseDao;
import com.secpro.platform.monitoring.manage.dao.TaskScheduleDao;

@Repository("TaskScheduleDaoImpl")
public class TaskScheduleDaoImpl extends BaseDao implements TaskScheduleDao {
	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
