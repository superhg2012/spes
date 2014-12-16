package org.spes.dao.common.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spes.bean.Evaluatesheet;
import org.spes.common.StringUtil;
import org.spes.dao.common.EvaluatesheetDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.alcatel.omc.fwk.utilities.trace.TraceManager;
/**
 * A data access object (DAO) providing persistence and search support for
 * Evaluatesheet entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see org.spes.bean.Evaluatesheet
 * @author MyEclipse Persistence Tools
 */

public class EvaluatesheetDAOImpl extends HibernateDaoSupport implements EvaluatesheetDAO {
	private static final Logger log = LoggerFactory
			.getLogger(EvaluatesheetDAOImpl.class);
	// property constants
	public static final String SHEET_ID = "sheetId";
	public static final String SHEET_NAME = "sheetName";
	public static final String SHEET_STATE = "sheetState";
	public static final String USER_NAME = "userName";
	public static final String CREATE_TIME = "createTime";
	public static final String LAST_UPDATE = "lastUpdate";
	public static final String BACKUP1 = "backup1";
	public static final String BACKUP2 = "backup2";
	public static final String BACKUP3 = "backup3";
	public static final String BACKUP4 = "backup4";
	public static final String BACKUP5 = "backup5";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see org.spes.dao.common.impl.EvaluatesheetDAO#save(org.spes.bean.Evaluatesheet)
	 */
	public Integer save(Evaluatesheet transientInstance) {
		log.debug("saving Evaluatesheet instance");
		try {
			Serializable id = getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
			return (Integer) id;
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see org.spes.dao.common.impl.EvaluatesheetDAO#delete(org.spes.bean.Evaluatesheet)
	 */
	public void delete(Evaluatesheet persistentInstance) {
		log.debug("deleting Evaluatesheet instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Evaluatesheet findById(java.lang.Integer id) {
		log.debug("getting Evaluatesheet instance with id: " + id);
		try {
			Evaluatesheet instance = (Evaluatesheet) getHibernateTemplate()
					.get("org.spes.bean.Evaluatesheet", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Evaluatesheet instance) {
		log.debug("finding Evaluatesheet instance by example");
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
	 * @see org.spes.dao.common.impl.EvaluatesheetDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Evaluatesheet instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Evaluatesheet as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see org.spes.dao.common.impl.EvaluatesheetDAO#findBySheetName(java.lang.Object)
	 */
	public List findBySheetName(Object sheetName) {
		return findByProperty(SHEET_NAME, sheetName);
	}

	public List findBySheetState(Object sheetState) {
		return findByProperty(SHEET_STATE, sheetState);
	}

	public List findByUserName(Object userName) {
		return findByProperty(USER_NAME, userName);
	}

	public List findByCreateTime(Object createTime) {
		return findByProperty(CREATE_TIME, createTime);
	}

	public List findByLastUpdate(Object lastUpdate) {
		return findByProperty(LAST_UPDATE, lastUpdate);
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
	 * @see org.spes.dao.common.impl.EvaluatesheetDAO#findAll()
	 */
	public List findAll() {
		log.debug("finding all Evaluatesheet instances");
		try {
			String queryString = "from Evaluatesheet";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Evaluatesheet merge(Evaluatesheet detachedInstance) {
		log.debug("merging Evaluatesheet instance");
		try {
			Evaluatesheet result = (Evaluatesheet) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Evaluatesheet instance) {
		log.debug("attaching dirty Evaluatesheet instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Evaluatesheet instance) {
		log.debug("attaching clean Evaluatesheet instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static EvaluatesheetDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (EvaluatesheetDAO) ctx.getBean("EvaluatesheetDAO");
	}

	public List findEvaluateSheet(String checkType, String sheetState) {
		TraceManager.TrDebug("finding  Evaluatesheet instances according to sheetType and sheetState");
		try {
			String hql = "from Evaluatesheet as es where ";
			if(StringUtil.isNotNull(checkType) && StringUtil.isNotNull(sheetState)) {
				hql += "es.checkType='" + checkType +"' and es.sheetState='" + sheetState + "'";
			}
			
			if(StringUtil.isNotNull(checkType) && StringUtil.isNull(sheetState)) {
				hql += "es.checkType='" + checkType + "'";
			}
			
			if (StringUtil.isNotNull(sheetState) && StringUtil.isNull(checkType)) {
				hql += "es.sheetState='" + sheetState + "'";
			}
			//String hql = "from Evaluatesheet as es where es.checkType='" + checkType +"' order by createTime desc";
			hql += " order by createTime desc";
			return getHibernateTemplate().find(hql);
		} catch(RuntimeException e) {
			TraceManager.TrException("find Evaluatesheet instances failed", e);
			return null;
		}

	}

	public boolean delete(String sheetId) {
		List<Evaluatesheet> list = findByProperty(SHEET_ID, Integer.valueOf(sheetId));
		try {
			if (list != null) {
				getHibernateTemplate().delete(list.get(0));
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}