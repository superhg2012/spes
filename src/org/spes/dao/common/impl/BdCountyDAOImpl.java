package org.spes.dao.common.impl;

import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spes.bean.BdCounty;
import org.spes.dao.common.BdCountyDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * BdCounty entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.spes.bean.BdCounty
 * @author MyEclipse Persistence Tools
 */

public class BdCountyDAOImpl extends HibernateDaoSupport implements BdCountyDAO {
	private static final Logger log = LoggerFactory
			.getLogger(BdCountyDAOImpl.class);
	// property constants
	public static final String NAME = "name";
	public static final String CODE = "code";
	public static final String SHRT_MM = "shrtMm";
	public static final String ENABLED = "enabled";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.BdCountyDAO#save(org.spes.bean.BdCounty)
	 */
	public void save(BdCounty transientInstance) {
		log.debug("saving BdCounty instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.BdCountyDAO#delete(org.spes.bean.BdCounty)
	 */
	public void delete(BdCounty persistentInstance) {
		log.debug("deleting BdCounty instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BdCounty findById(java.lang.Integer id) {
		log.debug("getting BdCounty instance with id: " + id);
		try {
			BdCounty instance = (BdCounty) getHibernateTemplate().get(
					"org.spes.bean.BdCounty", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BdCounty instance) {
		log.debug("finding BdCounty instance by example");
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
	 * @see org.spes.bean.BdCountyDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding BdCounty instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from BdCounty as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findByCode(Object code) {
		return findByProperty(CODE, code);
	}

	public List findByShrtMm(Object shrtMm) {
		return findByProperty(SHRT_MM, shrtMm);
	}

	public List findByEnabled(Object enabled) {
		return findByProperty(ENABLED, enabled);
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.BdCountyDAO#findAll()
	 */
	public List findAll() {
		log.debug("finding all BdCounty instances");
		try {
			String queryString = "from BdCounty";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BdCounty merge(BdCounty detachedInstance) {
		log.debug("merging BdCounty instance");
		try {
			BdCounty result = (BdCounty) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BdCounty instance) {
		log.debug("attaching dirty BdCounty instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BdCounty instance) {
		log.debug("attaching clean BdCounty instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static BdCountyDAO getFromApplicationContext(ApplicationContext ctx) {
		return (BdCountyDAO) ctx.getBean("BdCountyDAO");
	}
}