package org.spes.dao.user.impl;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spes.bean.User;
import org.spes.dao.user.UserDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for User
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see org.spes.bean.User
 * @author MyEclipse Persistence Tools
 */

public class UserDAOImpl extends HibernateDaoSupport implements UserDAO {
	private static final Logger log = LoggerFactory.getLogger(UserDAOImpl.class);
	// property constants
	public static final String USER_NAME = "userName";
	public static final String USER_PASS = "userPass";
	public static final String ID_CARD_NUM = "idCardNum";
	public static final String EMAIL = "email";
	public static final String CONTACT = "contact";
	public static final String GENDER = "gender";
	public static final String ADDRESS = "address";
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
	 * @see org.spes.bean.UserDAO#save(org.spes.bean.User)
	 */
	public Integer save(User transientInstance) {
		int result = -1;
		log.debug("saving User instance");
		try {
			result = (Integer)getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
			return result;
		} catch (RuntimeException re) {
			log.error("save failed", re);
			re.printStackTrace();
			return -1;
		}
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.UserDAO#delete(org.spes.bean.User)
	 */
	public void delete(User persistentInstance) {
		log.debug("deleting User instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public User findById(java.lang.Integer id) {
		log.debug("getting User instance with id: " + id);
		try {
			User instance = (User) getHibernateTemplate().get(
					"org.spes.bean.User", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(User instance) {
		log.debug("finding User instance by example");
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
	 * @see org.spes.bean.UserDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding User instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from User as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
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
	 * @see org.spes.bean.UserDAO#findAll()
	 */
	public List findAll() {
		log.debug("finding all User instances");
		try {
			String queryString = "from User";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public User merge(User detachedInstance) {
		log.debug("merging User instance");
		try {
			User result = (User) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(User instance) {
		log.debug("attaching dirty User instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(User instance) {
		log.debug("attaching clean User instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static UserDAO getFromApplicationContext(ApplicationContext ctx) {
		return (UserDAO) ctx.getBean("UserDAO");
	}

	public User findUserByNameAndPwd(String userName, String password) {
		User user = null;
		Session session = null;
		String hql = "from User where userName=? and userPass=?";
		try {
			session = this.getSession();
			user = (User)session.createQuery(hql).setParameter(0, userName).setParameter(1, password).uniqueResult();
			return user;
		} finally {
			this.releaseSession(session);
		}
	}

	public List<User> findByNameAndPass(String username, String password) {
		return  getHibernateTemplate().find("from User where userName=? and userPass=?", username, password);
	}

	

	public List<User> findUserByWindowId(Integer windowId, Integer centerId, Integer roleId) {
		String hql = "from User as u where u.window.windowId=? and u.serviceCenter.centerId=? and u.role.roleId=?";
		return  getHibernateTemplate().find(hql, windowId, centerId,roleId);
	}

	public int changeUserPwd(String password, Integer userId) {
		String hql = "update User as u set u.userPass=? where u.userId=?";
		Session session = getSession();
		session.beginTransaction().begin();
		Query query = session.createQuery(hql).setParameter(0, password).setParameter(1, userId);
		int result = query.executeUpdate();
		session.getTransaction().commit();
		return result;
	}

	public boolean updateUser(User persistentInstance) {
		try {
			this.getHibernateTemplate().update(persistentInstance);
			return true;
			
		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}
	}
	
	public List<User> findUserByCenterId(Integer centerId) {
		String hql = "from User as u where u.serviceCenter.centerId=? and backup3=1";
		return getHibernateTemplate().find(hql, centerId);
	}

	public List<User> findUnValidUserOfCenter(Integer centerId) {
		String hql = "from User as u where u.serviceCenter.centerId=? and u.backup3=0";
		return getHibernateTemplate().find(hql,centerId);
	}

	public void saveOrUpdate(User u) {
		
	}

}