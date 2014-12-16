package org.spes.dao.item.impl;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spes.bean.StaffParam;
import org.spes.dao.item.StaffParamDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * StaffParam entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.spes.bean.StaffParam
 * @author MyEclipse Persistence Tools
 */

public class StaffParamDAOImpl extends HibernateDaoSupport implements StaffParamDAO {
	private static final Logger log = LoggerFactory
			.getLogger(StaffParamDAOImpl.class);
	// property constants
	public static final String ITEM_NAME = "itemName";
	public static final String ITEM_VALUE = "itemValue";
	public static final String ITEM_ID = "itemId";
	public static final String USER_ID = "userId";
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
	 * @see org.spes.bean.StaffParamDAO#save(org.spes.bean.StaffParam)
	 */
	public void save(StaffParam transientInstance) {
		log.debug("saving StaffParam instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.StaffParamDAO#delete(org.spes.bean.StaffParam)
	 */
	public void delete(StaffParam persistentInstance) {
		log.debug("deleting StaffParam instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public StaffParam findById(java.lang.Integer id) {
		log.debug("getting StaffParam instance with id: " + id);
		try {
			StaffParam instance = (StaffParam) getHibernateTemplate().get(
					"org.spes.bean.StaffParam", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(StaffParam instance) {
		log.debug("finding StaffParam instance by example");
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
	 * @see org.spes.bean.StaffParamDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding StaffParam instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from StaffParam as model where model."
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

	public List findByItemValue(Object itemValue) {
		return findByProperty(ITEM_VALUE, itemValue);
	}

	public List findByItemId(Object itemId) {
		return findByProperty(ITEM_ID, itemId);
	}

	public List findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
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
	 * @see org.spes.bean.StaffParamDAO#findAll()
	 */
	public List findAll() {
		log.debug("finding all StaffParam instances");
		try {
			String queryString = "from StaffParam";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public StaffParam merge(StaffParam detachedInstance) {
		log.debug("merging StaffParam instance");
		try {
			StaffParam result = (StaffParam) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(StaffParam instance) {
		log.debug("attaching dirty StaffParam instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(StaffParam instance) {
		log.debug("attaching clean StaffParam instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static StaffParamDAO getFromApplicationContext(ApplicationContext ctx) {
		return (StaffParamDAO) ctx.getBean("StaffParamDAO");
	}
}