package org.spes.dao.user.impl;

import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spes.bean.UserAudit;
import org.spes.dao.user.UserAuditDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * UserAudit entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.spes.bean.UserAudit
 * @author MyEclipse Persistence Tools
 */

public class UserAuditDAOImpl extends HibernateDaoSupport implements UserAuditDAO {
	private static final Logger log = LoggerFactory
			.getLogger(UserAuditDAOImpl.class);
	// property constants
	public static final String USER_ID = "userId";
	public static final String USER_NAME = "userName";
	public static final String USER_PASS = "userPass";
	public static final String ID_CARD_NUM = "idCardNum";
	public static final String EMAIL = "email";
	public static final String CONTACT = "contact";
	public static final String GENDER = "gender";
	public static final String ADDRESS = "address";
	public static final String ROLE_ID = "roleId";
	public static final String POST_ID = "postId";
	public static final String WINDOW_ID = "windowId";
	public static final String CENTER_ID = "centerId";
	public static final String VALID = "valid";
	public static final String REMARKS = "remarks";
	public static final String BACKUP1 = "backup1";
	public static final String BACKUP2 = "backup2";
	public static final String BACKUP3 = "backup3";
	public static final String BACKUP4 = "backup4";
	public static final String BACKUP5 = "backup5";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.UserAuditDAO#save(org.spes.bean.UserAudit)
	 */
	public void save(UserAudit transientInstance) {
		log.debug("saving UserAudit instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.UserAuditDAO#delete(org.spes.bean.UserAudit)
	 */
	public void delete(UserAudit persistentInstance) {
		log.debug("deleting UserAudit instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UserAudit findById(java.lang.Integer id) {
		log.debug("getting UserAudit instance with id: " + id);
		try {
			UserAudit instance = (UserAudit) getHibernateTemplate().get(
					"org.spes.bean.UserAudit", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(UserAudit instance) {
		log.debug("finding UserAudit instance by example");
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
	 * @see org.spes.bean.UserAuditDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding UserAudit instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from UserAudit as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}

	public List findByUserName(Object userName) {
		return findByProperty(USER_NAME, userName);
	}

	public List findByUserPass(Object userPass) {
		return findByProperty(USER_PASS, userPass);
	}

	public List findByIdCardNum(Object idCardNum) {
		return findByProperty(ID_CARD_NUM, idCardNum);
	}

	public List findByEmail(Object email) {
		return findByProperty(EMAIL, email);
	}

	public List findByContact(Object contact) {
		return findByProperty(CONTACT, contact);
	}

	public List findByGender(Object gender) {
		return findByProperty(GENDER, gender);
	}

	public List findByAddress(Object address) {
		return findByProperty(ADDRESS, address);
	}

	public List findByRoleId(Object roleId) {
		return findByProperty(ROLE_ID, roleId);
	}

	public List findByPostId(Object postId) {
		return findByProperty(POST_ID, postId);
	}

	public List findByWindowId(Object windowId) {
		return findByProperty(WINDOW_ID, windowId);
	}

	public List findByCenterId(Object centerId) {
		return findByProperty(CENTER_ID, centerId);
	}

	public List findByValid(Object valid) {
		return findByProperty(VALID, valid);
	}

	public List findByRemarks(Object remarks) {
		return findByProperty(REMARKS, remarks);
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
	 * @see org.spes.bean.UserAuditDAO#findAll()
	 */
	public List findAll() {
		log.debug("finding all UserAudit instances");
		try {
			String queryString = "from UserAudit";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public UserAudit merge(UserAudit detachedInstance) {
		log.debug("merging UserAudit instance");
		try {
			UserAudit result = (UserAudit) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(UserAudit instance) {
		log.debug("attaching dirty UserAudit instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(UserAudit instance) {
		log.debug("attaching clean UserAudit instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static UserAuditDAO getFromApplicationContext(ApplicationContext ctx) {
		return (UserAuditDAO) ctx.getBean("UserAuditDAO");
	}
}