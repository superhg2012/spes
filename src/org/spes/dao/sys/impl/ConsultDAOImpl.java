package org.spes.dao.sys.impl;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spes.bean.Consult;
import org.spes.dao.sys.ConsultDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Consult entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.spes.bean.Consult
 * @author MyEclipse Persistence Tools
 */

public class ConsultDAOImpl extends HibernateDaoSupport implements ConsultDAO {
	private static final Logger log = LoggerFactory.getLogger(ConsultDAOImpl.class);
	// property constants
	public static final String CONSULT_TITLE = "consultTitle";
	public static final String CONSULT_CONTENT = "consultContent";
	public static final String USER_ID = "userId";
	public static final String BACKUP1 = "backup1";
	public static final String BACKUP2 = "backup2";
	public static final String BACKUP3 = "backup3";
	public static final String BACKUP4 = "backup4";
	public static final String BACKUP5 = "backup5";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.ConsultDAO#save(org.spes.bean.Consult)
	 */
	public int save(Consult transientInstance) {
		log.debug("saving Consult instance");
		try {
			int id = (Integer)getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
			return id;
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.ConsultDAO#delete(org.spes.bean.Consult)
	 */
	public void delete(Consult persistentInstance) {
		log.debug("deleting Consult instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Consult findById(java.lang.Integer id) {
		log.debug("getting Consult instance with id: " + id);
		try {
			Consult instance = (Consult) getHibernateTemplate().get(
					"org.spes.bean.Consult", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Consult instance) {
		log.debug("finding Consult instance by example");
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
	 * @see org.spes.bean.ConsultDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Consult instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Consult as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByConsultTitle(Object consultTitle) {
		return findByProperty(CONSULT_TITLE, consultTitle);
	}

	public List findByConsultContent(Object consultContent) {
		return findByProperty(CONSULT_CONTENT, consultContent);
	}

	public List findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
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
	 * @see org.spes.bean.ConsultDAO#findAll()
	 */
	public List findAll() {
		log.debug("finding all Consult instances");
		try {
			String queryString = "from Consult";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Consult merge(Consult detachedInstance) {
		log.debug("merging Consult instance");
		try {
			Consult result = (Consult) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Consult instance) {
		log.debug("attaching dirty Consult instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Consult instance) {
		log.debug("attaching clean Consult instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ConsultDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ConsultDAO) ctx.getBean("ConsultDAO");
	}

	public Consult update(Consult detachedInstance) {
		log.debug("updating the consult");
		return merge(detachedInstance);
	}

	public List findByUserIdOrderBy(Integer userId, String property,
			String direction) {
		
		log.debug("finding consult where userId is  " + userId +"  order by " + property + " " + direction);
		try {
			String queryString = "from Consult where userId =  " + userId + " order by " + property + " " + direction;
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAllOrderByBackup1() {
		log.debug("finding consult oreder by backup1");
		try {
			String queryString = "from Consult order by backup1 asc";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
}