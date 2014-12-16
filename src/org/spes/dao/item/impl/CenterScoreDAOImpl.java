package org.spes.dao.item.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spes.bean.CenterScore;
import org.spes.dao.item.CenterScoreDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.alcatel.omc.fwk.utilities.trace.TraceManager;

/**
 * A data access object (DAO) providing persistence and search support for
 * CenterScore entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see org.spes.bean.CenterScore
 * @author MyEclipse Persistence Tools
 */

public class CenterScoreDAOImpl extends HibernateDaoSupport implements CenterScoreDAO {
	private static final Logger log = LoggerFactory
			.getLogger(CenterScoreDAOImpl.class);
	// property constants
	public static final String ITEM_NAME = "itemName";
	public static final String ITEM_SCORE = "itemScore";
	public static final String CALCULATOR = "calculator";
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

	/* (non-Javadoc)
	 * @see org.spes.bean.CenterScoreDAO#save(org.spes.bean.CenterScore)
	 */
	public Integer save(CenterScore transientInstance) {
		log.debug("saving CenterScore instance");
		Integer result = -1;
		try {
			result = (Integer)getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
			return result;
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.CenterScoreDAO#delete(org.spes.bean.CenterScore)
	 */
	public void delete(CenterScore persistentInstance) {
		log.debug("deleting CenterScore instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CenterScore findById(java.lang.Integer id) {
		log.debug("getting CenterScore instance with id: " + id);
		try {
			CenterScore instance = (CenterScore) getHibernateTemplate().get("org.spes.bean.CenterScore", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(CenterScore instance) {
		log.debug("finding CenterScore instance by example");
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
	 * @see org.spes.bean.CenterScoreDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding CenterScore instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from CenterScore as model where model."
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

	public List findByItemScore(Object itemScore) {
		return findByProperty(ITEM_SCORE, itemScore);
	}

	public List findByCalculator(Object calculator) {
		return findByProperty(CALCULATOR, calculator);
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

	/* (non-Javadoc)
	 * @see org.spes.bean.CenterScoreDAO#findAll()
	 */
	public List findAll() {
		log.debug("finding all CenterScore instances");
		try {
			String queryString = "from CenterScore";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CenterScore merge(CenterScore detachedInstance) {
		log.debug("merging CenterScore instance");
		try {
			CenterScore result = (CenterScore) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CenterScore instance) {
		log.debug("attaching dirty CenterScore instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CenterScore instance) {
		log.debug("attaching clean CenterScore instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CenterScoreDAO getFromApplicationContext(ApplicationContext ctx) {
		return (CenterScoreDAO) ctx.getBean("CenterScoreDAO");
	}

	public List findByCenterIdAndDates(String from, String to, Integer centerId, String type) {
		String sql = "select cs,(select itemName from CenterItem where itemId=ci.parentId),ci.itemGrade as itemGrade from CenterScore as cs, CenterItem as ci where cs.sheetType=? and cs.centerId=? and cs.itemId=ci.itemId and cs.evaluateDate between ? and ? order by itemGrade,cs.evaluateDate asc";
		try {
			return getHibernateTemplate().find(sql,type,centerId,java.sql.Date.valueOf(from),java.sql.Date.valueOf(to));
		} catch (Exception e){
			TraceManager.TrException("[Hibernate Exception] :", e);
		}
		
		return null;
	}

	public CenterScore findByCenterIdAndItemId(Integer centerId, Integer itemId, String sheetId) {
		String hql = "from CenterScore where centerId=? and itemId=? and sheetId=? order by evaluateDate desc";
		List<CenterScore> list = getHibernateTemplate().find(hql, centerId,	itemId, sheetId);
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
	
	public CenterScore findByCenterIdAndItemId(Integer centerId, Integer itemId) {
		String hql = "from CenterScore where centerId=? and itemId=? order by evaluateDate desc";
		List<CenterScore> list = getHibernateTemplate().find(hql, centerId,	itemId);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public CenterScore findByParams(Integer centerId, Integer itemId,
		String checkType, String sheetName, String userName) {
		String hql = "from CenterScore where centerId=? and itemId=? and checkType=? and sheetName like '" + userName +  "%" + sheetName + "'" ;
		Session session = getSession();
		Query query = session.createQuery(hql).setParameter(1, centerId)
				.setParameter(2, itemId).setParameter(3, checkType)
				.setParameter(4, sheetName);
		return (CenterScore) query.uniqueResult();
	}

	public void deleteCenterScore(String sheetId, String sheetName) {
		String sql = "delete from center_score where sheetId='" + sheetId + "'";
		Session session = null;
		try {
			session = getSession();
			Transaction tx =  session.beginTransaction();
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(CenterScore.class);
			query.executeUpdate();
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			TraceManager.TrException("[Hibernate Exception]", e);
		} catch (Exception e){
			TraceManager.TrException("[Exception]", e);
		}
	}

}