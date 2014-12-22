package org.spes.dao.item.impl;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spes.bean.CenterParam;
import org.spes.dao.item.CenterParamDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * CenterParam entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see org.spes.bean.CenterParam
 * @author MyEclipse Persistence Tools
 */

public class CenterParamDAOImpl extends HibernateDaoSupport implements
		CenterParamDAO {
	private static final Logger log = LoggerFactory
			.getLogger(CenterParamDAOImpl.class);
	// property constants
	public static final String ITEM_NAME = "itemName";
	public static final String ITEM_VALUE = "itemValue";
	public static final String ITEM_ID = "itemId";
	public static final String CENTER_ID = "centerId";
	public static final String BACKUP1 = "backup1";
	public static final String BACKUP2 = "backup2";
	public static final String BACKUP3 = "backup3";
	public static final String BACKUP4 = "backup4";
	public static final String BACKUP5 = "backup5";

	protected void initDao() {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.spes.bean.CenterParamDAO#save(org.spes.bean.CenterParam)
	 */
	public void save(CenterParam transientInstance) {
		log.debug("saving CenterParam instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.spes.bean.CenterParamDAO#delete(org.spes.bean.CenterParam)
	 */
	public void delete(CenterParam persistentInstance) {
		log.debug("deleting CenterParam instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CenterParam findById(java.lang.Integer id) {
		log.debug("getting CenterParam instance with id: " + id);
		try {
			CenterParam instance = (CenterParam) getHibernateTemplate().get(
					"org.spes.bean.CenterParam", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(CenterParam instance) {
		log.debug("finding CenterParam instance by example");
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.spes.bean.CenterParamDAO#findByProperty(java.lang.String,
	 * java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding CenterParam instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from CenterParam as model where model."
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.spes.bean.CenterParamDAO#findAll()
	 */
	public List findAll() {
		log.debug("finding all CenterParam instances");
		try {
			String queryString = "from CenterParam";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CenterParam merge(CenterParam detachedInstance) {
		log.debug("merging CenterParam instance");
		try {
			CenterParam result = (CenterParam) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CenterParam instance) {
		log.debug("attaching dirty CenterParam instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CenterParam instance) {
		log.debug("attaching clean CenterParam instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CenterParamDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (CenterParamDAO) ctx.getBean("CenterParamDAO");
	}

	public CenterParam findByCenterIdAndItemId(Integer itemId, Integer centerId) {
		Session session = null;
		String hql = "from CenterParam as c where c.itemId=? and c.centerId=?";
		try {
			session = getSession();
			return (CenterParam) session.createQuery(hql).setParameter(0,
					itemId).setParameter(1, centerId).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			this.releaseSession(session);
		}
	}

	public List<CenterParam> findByItemNameAndCenterId(final String itemName,
			final Integer centerId) {
		String hql = "from CenterParam as c where c.itemName=? and c.centerId=? order by c.evaluateDate desc";
		try {
			List<CenterParam> list =  this.getHibernateTemplate().find(hql, itemName, centerId);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}

	public CenterParam findCenterParamByParams(Integer itemId,
			Integer centerId, String checkType, String sheetName,
			String userName) {
		return null;
	}

	@Override
	public CenterParam findThirdLevelParamByIds(Integer itemId,
			Integer centerId, Integer sheetId) {
		String hql = "From CenterParam where itemId=? and centerId=? and sheetId=?";
		List<CenterParam> list = getHibernateTemplate().find(hql, itemId, centerId, String.valueOf(sheetId)); 
		return list.size() > 0 ? list.get(0) : null;
	}
}