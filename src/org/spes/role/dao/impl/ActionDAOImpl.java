package org.spes.role.dao.impl;

import java.util.List;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spes.bean.Action;
import org.spes.role.dao.ActionDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Action entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.spes.bean.Action
 * @author MyEclipse Persistence Tools
 */

public class ActionDAOImpl extends HibernateDaoSupport implements ActionDAO {
	private static final Logger log = LoggerFactory.getLogger(ActionDAOImpl.class);
	// property constants
	public static final String ACTION_NAME = "actionName";
	public static final String PARENT_ID = "parentId";
	public static final String ACTION_TYPE = "actionType";
	public static final String URL = "url";
	public static final String TARGET = "target";
	public static final String ACTION_DESC = "actionDesc";
	public static final String BACKUP1 = "backup1";
	public static final String BACKUP2 = "backup2";
	public static final String BACKUP3 = "backup3";
	public static final String BACKUP4 = "backup4";
	public static final String BACKUP5 = "backup5";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.ActionDAO#save(org.spes.bean.Action)
	 */
	public void save(Action transientInstance) {
		log.debug("saving Action instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.ActionDAO#delete(org.spes.bean.Action)
	 */
	public void delete(Action persistentInstance) {
		log.debug("deleting Action instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Action findById(java.lang.Integer id) {
		log.debug("getting Action instance with id: " + id);
		try {
			Action instance = (Action) getHibernateTemplate().get(
					"org.spes.bean.Action", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Action instance) {
		log.debug("finding Action instance by example");
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
	 * @see org.spes.bean.ActionDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Action instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Action as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByActionName(Object actionName) {
		return findByProperty(ACTION_NAME, actionName);
	}

	public List findByParentId(Object parentId) {
		return findByProperty(PARENT_ID, parentId);
	}

	public List findByActionType(Object actionType) {
		return findByProperty(ACTION_TYPE, actionType);
	}

	public List findByUrl(Object url) {
		return findByProperty(URL, url);
	}

	public List findByTarget(Object target) {
		return findByProperty(TARGET, target);
	}

	public List findByActionDesc(Object actionDesc) {
		return findByProperty(ACTION_DESC, actionDesc);
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
	 * @see org.spes.bean.ActionDAO#findAll()
	 */
	public List findAll() {
		log.debug("finding all Action instances");
		try {
			String queryString = "from Action";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Action merge(Action detachedInstance) {
		log.debug("merging Action instance");
		try {
			Action result = (Action) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Action instance) {
		log.debug("attaching dirty Action instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Action instance) {
		log.debug("attaching clean Action instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ActionDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ActionDAO) ctx.getBean("ActionDAO");
	}
	
	public List findActionsByRoleId(Integer roleId){
		String hql = "select new list(ar.backup1) from Action as ac,ActionRole as ar where ar.roleId=? and ar.actionId=ac.actionId";
		return getHibernateTemplate().find(hql,roleId);
	}
}