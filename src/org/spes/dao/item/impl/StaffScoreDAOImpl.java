package org.spes.dao.item.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spes.bean.StaffScore;
import org.spes.dao.item.StaffScoreDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * StaffScore entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.spes.bean.StaffScore
 * @author MyEclipse Persistence Tools
 */

public class StaffScoreDAOImpl extends HibernateDaoSupport implements StaffScoreDAO {
	private static final Logger log = LoggerFactory.getLogger(StaffScoreDAOImpl.class);
	// property constants
	public static final String ITEM_NAME = "itemName";
	public static final String ITEM_SCORE = "itemScore";
	public static final String CALCULATOR = "calculator";
	public static final String ITEM_ID = "itemId";
	public static final String USER_ID = "userId";
	public static final String WINDOW_ID = "windowId";
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
	 * @see org.spes.bean.StaffScoreDAO#save(org.spes.bean.StaffScore)
	 */
	public void save(StaffScore transientInstance) {
		log.debug("saving StaffScore instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.StaffScoreDAO#delete(org.spes.bean.StaffScore)
	 */
	public void delete(StaffScore persistentInstance) {
		log.debug("deleting StaffScore instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public StaffScore findById(java.lang.Integer id) {
		log.debug("getting StaffScore instance with id: " + id);
		try {
			StaffScore instance = (StaffScore) getHibernateTemplate().get(
					"org.spes.bean.StaffScore", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(StaffScore instance) {
		log.debug("finding StaffScore instance by example");
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
	 * @see org.spes.bean.StaffScoreDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding StaffScore instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from StaffScore as model where model."
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

	public List findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}

	public List findByWindowId(Object windowId) {
		return findByProperty(WINDOW_ID, windowId);
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
	 * @see org.spes.bean.StaffScoreDAO#findAll()
	 */
	public List findAll() {
		log.debug("finding all StaffScore instances");
		try {
			String queryString = "from StaffScore";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public StaffScore merge(StaffScore detachedInstance) {
		log.debug("merging StaffScore instance");
		try {
			StaffScore result = (StaffScore) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(StaffScore instance) {
		log.debug("attaching dirty StaffScore instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(StaffScore instance) {
		log.debug("attaching clean StaffScore instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static StaffScoreDAO getFromApplicationContext(ApplicationContext ctx) {
		return (StaffScoreDAO) ctx.getBean("StaffScoreDAO");
	}
	
	public List getStaffAndScoreByWindowIdAndTime(Integer windowId,String from,String to,String type){
		return getHibernateTemplate().find("select new list(ss,u.userId,u.userName) from StaffScore as ss,User as u where ss.sheetType=? and ss.windowId=? and ss.userId=u.userId and ss.evaluateDate between ? and ? order by ss.evaluateDate",type,windowId,java.sql.Date.valueOf(from),java.sql.Date.valueOf(to));
	}
	
	public List getStaffAndScoreByUserIdAndTime(Integer userId,String from ,String to, String type){
		return getHibernateTemplate().find("select new list(ss,u.userId,u.userName) from StaffScore as ss,User as u where ss.sheetType=? and ss.userId=? and ss.userId=u.userId and ss.evaluateDate between ? and ? order by ss.evaluateDate",type,userId,java.sql.Date.valueOf(from),java.sql.Date.valueOf(to));
	}
	
	public List getStaffAndScoreByIdAndTime(Integer userId,String from,String to, String type){
		Session session = this.getSession();
		String hql = "select new list(ss,u.userName,si.itemGrade,(select itemName from StaffItem where itemId=si.parentId)) from StaffScore as ss,User as u, StaffItem as si where ss.sheetType=:queryType and ss.itemId=si.itemId and ss.userId=:userID and ss.userId=u.userId and ss.evaluateDate between :from and :to order by si.itemGrade,ss.evaluateDate";
		List result = session.createQuery(hql).setString("queryType", type).setInteger("userID", userId).setDate("from", java.sql.Date.valueOf(from)).setDate("to", java.sql.Date.valueOf(to)).list();
		this.releaseSession(session);
		return result;
	}

	public StaffScore findByIds(Integer itemId, Integer userId,
			Integer windowId, Integer centerId, String sheetId) {
		String hql = "from StaffScore as s where s.itemId=? and s.userId=? and s.windowId=? and s.centerId=? and s.sheetId=?";
		List<StaffScore> list = getHibernateTemplate().find(hql, itemId, userId, windowId, centerId, sheetId);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public void deleteStaffScore(String sheetId, String sheetName) {
		String sql = "delete from staff_score where sheetId='" + sheetId + "'";
		Session session = null;
		try {
			session = getSession();
			Transaction tx = session.beginTransaction();
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(StaffScore.class);
			query.executeUpdate();
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}