package org.spes.dao.item.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spes.bean.WindowScore;
import org.spes.dao.item.WindowScoreDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * WindowScore entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see org.spes.bean.WindowScore
 * @author MyEclipse Persistence Tools
 */

public class WindowScoreDAOImpl extends HibernateDaoSupport implements WindowScoreDAO {
	private static final Logger log = LoggerFactory
			.getLogger(WindowScoreDAOImpl.class);
	// property constants
	public static final String ITEM_NAME = "itemName";
	public static final String ITEM_SCORE = "itemScore";
	public static final String CALCULATOR = "calculator";
	public static final String ITEM_ID = "itemId";
	public static final String WINDOW_ID = "windowId";
	public static final String CENTER_ID = "centerId";
	public static final String BACKUP1 = "backup1";
	public static final String BACKUP2 = "backup2";
	public static final String BACKUP3 = "backup3";
	public static final String BACKUP4 = "backup4";
	public static final String BACKUP5 = "backup5";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.WindowScoreDAO#save(org.spes.bean.WindowScore)
	 */
	public void save(WindowScore transientInstance) {
		log.debug("saving WindowScore instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.WindowScoreDAO#delete(org.spes.bean.WindowScore)
	 */
	public void delete(WindowScore persistentInstance) {
		log.debug("deleting WindowScore instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public WindowScore findById(java.lang.Integer id) {
		log.debug("getting WindowScore instance with id: " + id);
		try {
			WindowScore instance = (WindowScore) getHibernateTemplate().get(
					"org.spes.bean.WindowScore", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(WindowScore instance) {
		log.debug("finding WindowScore instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.WindowScoreDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding WindowScore instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from WindowScore as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByItemName(Object itemName) {
		return findByProperty(ITEM_NAME, itemName);
	}

	public List findByItemScore(Object itemScore) {
		return findByProperty(ITEM_SCORE, itemScore);
	}

	public List findByCalculator(Object calculator) {
		return findByProperty(CALCULATOR, calculator);
	}

	public List findByItemId(Object itemId) {
		return findByProperty(ITEM_ID, itemId);
	}

	public List findByWindowId(Object windowId) {
		return findByProperty(WINDOW_ID, windowId);
	}

	public List findByCenterId(Object centerId) {
		return findByProperty(CENTER_ID, centerId);
	}

	public List findByBackup1(Object backup1) {
		return findByProperty(BACKUP1, backup1);
	}

	public List findByBackup2(Object backup2) {
		return findByProperty(BACKUP2, backup2);
	}

	public List findByBackup3(Object backup3) {
		return findByProperty(BACKUP3, backup3);
	}

	public List findByBackup4(Object backup4) {
		return findByProperty(BACKUP4, backup4);
	}

	public List findByBackup5(Object backup5) {
		return findByProperty(BACKUP5, backup5);
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.WindowScoreDAO#findAll()
	 */
	public List findAll() {
		log.debug("finding all WindowScore instances");
		try {
			String queryString = "from WindowScore";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public WindowScore merge(WindowScore detachedInstance) {
		log.debug("merging WindowScore instance");
		try {
			WindowScore result = (WindowScore) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(WindowScore instance) {
		log.debug("attaching dirty WindowScore instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(WindowScore instance) {
		log.debug("attaching clean WindowScore instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static WindowScoreDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (WindowScoreDAO) ctx.getBean("WindowScoreDAO");
	}

	public List FindByWindowIdAndTime(Integer windowId, String from, String to, String type) {
		String hql = "select ws,(select itemName from WindowItem where itemId=wi.parentId),wi.itemGrade as itemGrade,w.windowName as windowName " +
				"from WindowScore as ws,WindowItem as wi,Window as w where ws.sheetType=:sheetType and ws.windowId=w.windowId " +
				"and ws.windowId=:windowid and wi.itemId=ws.itemId and ws.evaluateDate between :from and :to " +
				"order by itemGrade,evaluateDate";
		Session session = this.getSession();
		List list = session.createQuery(hql).setString("sheetType", type).setInteger("windowid", windowId).setDate("from", java.sql.Date.valueOf(from)).setDate("to", java.sql.Date.valueOf(to)).list();
		this.releaseSession(session);
		return list;
	}

	public WindowScore findWindowScoreByIds(Integer centerId, Integer windowId,
			Integer itemId, String sheetId) {
		String hql = "from WindowScore where centerId=? and windowId=? and itemId=? and sheetId=? order by evaluateDate desc";
		List<WindowScore> list = getHibernateTemplate().find(hql, centerId, windowId, itemId, sheetId);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public void deleteCenterScore(String sheetId, String sheetName) {
		try {
			String sql = "delete from window_score where sheetId='" + sheetId + "'";
			Session session = getSession();
			Transaction tx = session.beginTransaction();
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(WindowScore.class);
			query.executeUpdate();
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}