package org.spes.dao.common.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spes.bean.Window;
import org.spes.dao.common.WindowDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.opensymphony.xwork2.ActionContext;

/**
 * A data access object (DAO) providing persistence and search support for
 * Window entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.spes.bean.Window
 * @author MyEclipse Persistence Tools
 */

public class WindowDAOImpl extends HibernateDaoSupport implements WindowDAO {
	private static final Logger log = LoggerFactory.getLogger(WindowDAOImpl.class);
	// property constants
	public static final String WINDOW_NAME = "windowName";
	public static final String WINDOW_BUSSINESS = "windowBussiness";
	public static final String WINDOW_DESC = "windowDesc";
	public static final String BACKUP1 = "backup1";
	public static final String BACKUP2 = "backup2";
	public static final String BACKUP3 = "backup3";
	public static final String BACKUP4 = "backup4";
	public static final String BACKUP5 = "backup5";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.WindowDAO#save(org.spes.bean.Window)
	 */
	public void save(Window transientInstance) {
		log.debug("saving Window instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.WindowDAO#delete(org.spes.bean.Window)
	 */
	public void delete(Window persistentInstance) {
		log.debug("deleting Window instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Window findById(java.lang.Integer id) {
		log.debug("getting Window instance with id: " + id);
		try {
			Window instance = (Window) getHibernateTemplate().get(
					"org.spes.bean.Window", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Window instance) {
		log.debug("finding Window instance by example");
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
	 * @see org.spes.bean.WindowDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Window instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Window as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByWindowName(Object windowName) {
		return findByProperty(WINDOW_NAME, windowName);
	}

	public List findByWindowBussiness(Object windowBussiness) {
		return findByProperty(WINDOW_BUSSINESS, windowBussiness);
	}

	public List findByWindowDesc(Object windowDesc) {
		return findByProperty(WINDOW_DESC, windowDesc);
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
	 * @see org.spes.bean.WindowDAO#findAll()
	 */
	public List findAll() {
		log.debug("finding all Window instances");
		try {
			String queryString = "from Window";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Window merge(Window detachedInstance) {
		log.debug("merging Window instance");
		try {
			Window result = (Window) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Window instance) {
		log.debug("attaching dirty Window instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Window instance) {
		log.debug("attaching clean Window instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static WindowDAO getFromApplicationContext(ApplicationContext ctx) {
		return (WindowDAO) ctx.getBean("WindowDAO");
	}

	public List findByUserId(Integer userId) {
		Map session = ActionContext.getContext().getSession();
		Integer roleId = Integer.valueOf(session.get("roleId").toString());
		String sql = "";
		if(roleId<4){
			sql = "from Window as w where w.serviceCenter.centerId=(select u.serviceCenter.centerId from User as u where u.userId=?)";
		}else {
			sql = "from Window as w where w.windowId=(select u.window.windowId from User as u where u.userId=?)";
		}
		return getHibernateTemplate().find(sql,userId);
	}

	@SuppressWarnings("unchecked")
	public List<Window> findByCenterId(Integer centerId) {
		String hql = "from Window as w where w.serviceCenter.centerId=?";
		Session session = null;
		List<Window> wlist = null;
		try {
			session = getSession();
			wlist = session.createQuery(hql).setParameter(0, centerId).list();
			return wlist;
		}catch(RuntimeException re){
			throw re;
		} finally {
			this.releaseSession(session);
		}
	}
	
	//////////////////////zhoushaojun start/////////////////////
	public Window findWindowById(int windowId) {
		try {

			Object result = this.getHibernateTemplate().get(
					"org.spes.bean.Window", windowId);
			if (null == result)
				return null;
			else {
				return (Window) result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int findWindowIdByName(String name, int centerId) {
		try {
			List<Window> results = this.getHibernateTemplate().find(
					"from Window u where u.windowName = ? and u.centerId = ?",
					new Object[] { name, centerId });

			if (null == results || results.size() != 1)
				return -1;
			return results.get(0).getWindowId();

		} catch (Exception e) {
			return -1;
		}

	}
	//////////////////////zhousahojun end///////////////////////

	public boolean updateWindow(Window window) {
		try {
			getHibernateTemplate().update(window);
			return true;
		} catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}
}