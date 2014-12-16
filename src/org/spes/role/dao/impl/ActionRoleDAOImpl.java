package org.spes.role.dao.impl;

import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spes.bean.ActionRole;
import org.spes.role.dao.ActionRoleDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * ActionRole entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.spes.bean.ActionRole
 * @author MyEclipse Persistence Tools
 */

public class ActionRoleDAOImpl extends HibernateDaoSupport implements ActionRoleDAO {
	private static final Logger log = LoggerFactory
			.getLogger(ActionRoleDAOImpl.class);
	// property constants
	public static final String ACTION_ID = "actionId";
	public static final String ROLE_ID = "roleId";
	public static final String BACKUP1 = "backup1";
	public static final String BACKUP2 = "backup2";
	public static final String BACKUP3 = "backup3";
	public static final String BACKUP4 = "backup4";
	public static final String BACKUP5 = "backup5";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.ActionRoleDAO#save(org.spes.bean.ActionRole)
	 */
	public void save(ActionRole transientInstance) {
		log.debug("saving ActionRole instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.ActionRoleDAO#delete(org.spes.bean.ActionRole)
	 */
	public void delete(ActionRole persistentInstance) {
		log.debug("deleting ActionRole instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ActionRole findById(java.lang.Integer id) {
		log.debug("getting ActionRole instance with id: " + id);
		try {
			ActionRole instance = (ActionRole) getHibernateTemplate().get(
					"org.spes.bean.ActionRole", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ActionRole instance) {
		log.debug("finding ActionRole instance by example");
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
	 * @see org.spes.bean.ActionRoleDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding ActionRole instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from ActionRole as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByActionId(Object actionId) {
		return findByProperty(ACTION_ID, actionId);
	}

	public List findByRoleId(Object roleId) {
		return findByProperty(ROLE_ID, roleId);
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
	 * @see org.spes.bean.ActionRoleDAO#findAll()
	 */
	public List findAll() {
		log.debug("finding all ActionRole instances");
		try {
			String queryString = "from ActionRole";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ActionRole merge(ActionRole detachedInstance) {
		log.debug("merging ActionRole instance");
		try {
			ActionRole result = (ActionRole) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ActionRole instance) {
		log.debug("attaching dirty ActionRole instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ActionRole instance) {
		log.debug("attaching clean ActionRole instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ActionRoleDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ActionRoleDAO) ctx.getBean("ActionRoleDAO");
	}

	public List<ActionRole> findByIds(String parentId, Integer roleId) {
		String hql = "from ActionRole where backup2=? and roleId=?";
		return getHibernateTemplate().find(hql, parentId, roleId);
	}
}