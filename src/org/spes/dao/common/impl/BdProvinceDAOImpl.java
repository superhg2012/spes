package org.spes.dao.common.impl;

import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spes.bean.BdProvince;
import org.spes.dao.common.BdProvinceDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * BdProvince entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.spes.bean.BdProvince
 * @author MyEclipse Persistence Tools
 */

public class BdProvinceDAOImpl extends HibernateDaoSupport implements BdProvinceDAO {
	private static final Logger log = LoggerFactory
			.getLogger(BdProvinceDAOImpl.class);
	// property constants
	public static final String NAME = "name";
	public static final String CODE = "code";
	public static final String SHRT_NM = "shrtNm";
	public static final String ENABLED = "enabled";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.BdProvinceDAO#save(org.spes.bean.BdProvince)
	 */
	public void save(BdProvince transientInstance) {
		log.debug("saving BdProvince instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.BdProvinceDAO#delete(org.spes.bean.BdProvince)
	 */
	public void delete(BdProvince persistentInstance) {
		log.debug("deleting BdProvince instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BdProvince findById(java.lang.Integer id) {
		log.debug("getting BdProvince instance with id: " + id);
		try {
			BdProvince instance = (BdProvince) getHibernateTemplate().get(
					"org.spes.bean.BdProvince", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BdProvince instance) {
		log.debug("finding BdProvince instance by example");
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
	 * @see org.spes.bean.BdProvinceDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding BdProvince instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from BdProvince as model where model."
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

	public List findByShrtNm(Object shrtNm) {
		return findByProperty(SHRT_NM, shrtNm);
	}

	public List findByEnabled(Object enabled) {
		return findByProperty(ENABLED, enabled);
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.BdProvinceDAO#findAll()
	 */
	public List findAll() {
		log.debug("finding all BdProvince instances");
		try {
			String queryString = "from BdProvince";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BdProvince merge(BdProvince detachedInstance) {
		log.debug("merging BdProvince instance");
		try {
			BdProvince result = (BdProvince) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BdProvince instance) {
		log.debug("attaching dirty BdProvince instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BdProvince instance) {
		log.debug("attaching clean BdProvince instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static BdProvinceDAO getFromApplicationContext(ApplicationContext ctx) {
		return (BdProvinceDAO) ctx.getBean("BdProvinceDAO");
	}
}