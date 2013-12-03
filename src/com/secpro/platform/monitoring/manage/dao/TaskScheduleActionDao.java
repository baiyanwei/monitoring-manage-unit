package com.secpro.platform.monitoring.manage.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.mapping.PersistentClass;

import com.secpro.platform.monitoring.manage.entity.MSUTask;
import com.secpro.platform.monitoring.manage.util.db.session.HibernateSessionFactory;

/**
 * @author Martin
 * 
 */
public class TaskScheduleActionDao {

	/**
	 * @param resourceBean
	 * @return 插入或更新一条记录
	 */
	public static MSUTask save(MSUTask task) {
		if (task == null) {
			return task;
		}
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession();
			tx = session.getTransaction();
			tx.begin();
			session.saveOrUpdate(task);
			tx.commit();
			return task;
		} catch (RuntimeException re) {
			if (tx != null) {
				tx.rollback();
			}
			re.printStackTrace();
			throw re;
		} finally {
			if (session != null) {
				session.close();
			}

		}
	}

	/**
	 * @param beanList
	 *            批量更新记录
	 */
	public static void saveList(List<MSUTask> beanList) {
		if (beanList == null || beanList.isEmpty()) {
			return;
		}
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession();
			tx = session.getTransaction();
			tx.begin();
			for (int i = 0; i < beanList.size(); i++) {
				session.saveOrUpdate(beanList.get(i));
			}
			tx.commit();
		} catch (RuntimeException re) {
			if (tx != null) {
				tx.rollback();
			}
			re.printStackTrace();
			throw re;
		} finally {
			if (session != null) {
				session.close();
			}

		}
	}

	/**
	 * @param beanList
	 *            批量删除记录
	 */
	public static void delete(MSUTask task) {
		if (task == null) {
			return;
		}
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession();
			tx = session.getTransaction();
			tx.begin();
			session.delete(task);
			tx.commit();
		} catch (RuntimeException re) {
			if (tx != null) {
				tx.rollback();
			}
			re.printStackTrace();
			throw re;
		} finally {
			if (session != null) {
				session.close();
			}

		}
	}

	/**
	 * @param beanList
	 *            批量删除记录
	 */
	public static void delete(List<MSUTask> beanList) {
		if (beanList == null || beanList.isEmpty()) {
			return;
		}
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession();
			tx = session.getTransaction();
			tx.begin();
			for (int i = 0; i < beanList.size(); i++) {
				session.delete(beanList.get(i));
			}
			tx.commit();
		} catch (RuntimeException re) {
			if (tx != null) {
				tx.rollback();
			}
			re.printStackTrace();
			throw re;
		} finally {
			if (session != null) {
				session.close();
			}

		}
	}

	/**
	 * @param resourceBean
	 *            更新一条记录
	 */
	public static void update(MSUTask task) {
		if (task == null) {
			return;
		}
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession();
			tx = session.getTransaction();
			tx.begin();
			session.update(task);
			tx.commit();
		} catch (RuntimeException re) {
			if (tx != null) {
				tx.rollback();
			}
			re.printStackTrace();
			throw re;
		} finally {
			if (session != null) {
				session.close();
			}

		}
	}

	/**
	 * @param clazz
	 * @param id
	 * @return 根据类别与ＩＤ返回对应的实例
	 */
	public static MSUTask findById(String id) {
		if (id == null || id.length() == 0) {
			return null;
		}
		Session session = null;
		try {
			String entityName = getEntityName(MSUTask.class);
			if (entityName == null || entityName.length() == 0) {
				throw new RuntimeException("无效的资源BEAN,没有Mapping.");
			}
			session = getSession();
			Object obj = session.get(entityName, id);
			return (MSUTask) obj;
		} catch (RuntimeException re) {
			re.printStackTrace();
			throw re;
		} finally {
			if (session != null) {
				session.close();
			}

		}
	}

	/**
	 * @param clazz
	 * @param pageNo
	 * @param pageSize
	 * @return 反回对应的查询集合
	 */
	public static List<Object> findAll(Class<?> clazz, int pageNo, int pageSize) {
		Session session = null;
		try {
			int firstRow = pageSize * (pageNo - 1);
			session = getSession();
			Criteria crit = session.createCriteria(clazz);
			crit.setFirstResult(firstRow);
			crit.setMaxResults(pageSize);
			List<Object> reList = crit.list();
			return reList;
		} catch (RuntimeException re) {
			re.printStackTrace();
			throw re;
		} finally {
			if (session != null) {
				session.close();
			}

		}
	}

	public static List<Object> findAll(Class<?> clazz, String whereSql, int pageNo, int pageSize, String orderKey) {
		Session session = null;
		try {
			int firstRow = pageSize * (pageNo - 1);
			session = getSession();
			Criteria crit = session.createCriteria(clazz);
			crit.addOrder(Order.desc(orderKey));
			crit.add(Restrictions.sqlRestriction(whereSql));
			crit.setFirstResult(firstRow);
			crit.setMaxResults(pageSize);
			List<Object> reList = crit.list();
			return reList;
		} catch (RuntimeException re) {
			re.printStackTrace();
			throw re;
		} finally {
			if (session != null) {
				session.close();
			}

		}
	}

	public static List<Object> findByHSql(String HSql) {
		Session session = null;
		try {
			session = getSession();
			List<Object> reList = session.createQuery(HSql).list();
			return reList;
		} catch (RuntimeException re) {
			re.printStackTrace();
			throw re;
		} finally {
			if (session != null) {
				session.close();
			}

		}
	}

	public static List<Object> findBySql(String sql) {
		Session session = null;
		try {
			session = getSession();
			List<Object> reList = session.createSQLQuery(sql).list();
			return reList;
		} catch (RuntimeException re) {
			re.printStackTrace();
			throw re;
		} finally {
			if (session != null) {
				session.close();
			}

		}
	}

	public static List<Object> findBySql(String sql, int pageSize, int pageNo) {
		Session session = null;
		int firstRow = pageSize * (pageNo - 1);
		try {
			session = getSession();
			SQLQuery query = session.createSQLQuery(sql);
			query.setFirstResult(firstRow);
			query.setMaxResults(pageSize);
			return query.list();
		} catch (RuntimeException re) {
			re.printStackTrace();
			throw re;
		} finally {
			if (session != null) {
				session.close();
			}

		}
	}

	public static int removeByHSql(String HSql) {
		if (HSql == null || HSql.trim().length() == 0) {
			return -1;
		}
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession();
			tx = session.getTransaction();
			tx.begin();
			int reValue = session.createQuery(HSql).executeUpdate();
			tx.commit();
			return reValue;
		} catch (RuntimeException re) {
			if (tx != null) {
				tx.rollback();
			}
			re.printStackTrace();
			throw re;
		} finally {
			if (session != null) {
				session.close();
			}

		}
	}

	public static List<Object> findAll(Class<?> clazz) {
		Session session = null;
		try {
			session = getSession();
			String queryString = "from " + clazz.getSimpleName();
			Query queryObject = session.createQuery(queryString);
			List<Object> reList = queryObject.list();
			return reList;
		} catch (RuntimeException re) {
			re.printStackTrace();
			throw re;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public static List<Object> findAllBySQL(Class<?> clazz, String sql) {
		Session session = null;
		try {
			session = getSession();
			SQLQuery queryObject = session.createSQLQuery(sql);
			queryObject.addEntity(clazz);
			List<Object> reList = queryObject.list();
			return reList;
		} catch (RuntimeException re) {
			re.printStackTrace();
			throw re;
		} finally {
			if (session != null) {
				session.close();
			}

		}
	}

	public static List<Object> findAll(Class<?> clazz, long[] ids) {
		if (clazz == null || ids == null || ids.length == 0) {
			return null;
		}
		Session session = null;
		try {
			String queryString = "from " + clazz.getSimpleName() + " where id in (";
			for (int i = 0; i < ids.length; i++) {
				if (i == 0) {
					queryString = queryString + ids[i];
				} else {
					queryString = queryString + "," + ids[i];
				}

			}
			queryString = queryString + ")";
			session = getSession();
			Query queryObject = session.createQuery(queryString);
			List<Object> reList = queryObject.list();
			return reList;
		} catch (RuntimeException re) {
			re.printStackTrace();
			throw re;
		} finally {
			if (session != null) {
				session.close();
			}

		}
	}

	public static int getCounterNo(Class<?> clazz) {
		return getCounterNo(clazz, null);
	}

	public static int getCounterNo(Class<?> clazz, String whereSql) {
		if (clazz == null) {
			throw new RuntimeException("无效的资源Class");
		}
		Session session = null;
		try {
			String hqlString = "select count(*) from " + getTableNameByClass(clazz);
			if (whereSql != null && whereSql.length() != 0) {
				hqlString = hqlString + " where " + whereSql;
			}
			session = getSession();
			Query query = session.createSQLQuery(hqlString);
			int reValue = ((Number) query.uniqueResult()).intValue();
			return reValue;
		} catch (RuntimeException re) {
			re.printStackTrace();
			throw re;
		} finally {
			if (session != null) {
				session.close();
			}

		}

	}

	public static Session getSession() {
		return HibernateSessionFactory.getSession();
	}

	private static PersistentClass getPersistentClass(Class<?> clazz) {
		if (clazz == null) {
			return null;
		}
		return HibernateSessionFactory.getConfiguration().getClassMapping(clazz.getName());
	}

	public static String getTableNameByClass(Class<?> clazz) {
		PersistentClass pClass = getPersistentClass(clazz);
		if (pClass == null) {
			return null;
		}
		return pClass.getTable().getName();
	}

	public static String getEntityName(Class<?> clazz) {
		PersistentClass pClass = getPersistentClass(clazz);
		if (pClass == null) {
			return null;
		}
		return pClass.getEntityName();
	}
}
