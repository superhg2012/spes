package org.spes.dao.sys.impl;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spes.bean.Notice;
import org.spes.dao.sys.NoticeDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Notice entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.spes.bean.Notice
 * @author MyEclipse Persistence Tools
 */

public class NoticeDAOImpl extends HibernateDaoSupport implements NoticeDAO {
	private static final Logger log = LoggerFactory.getLogger(NoticeDAOImpl.class);
	// property constants
	public static final String NOTICE_TITLE = "noticeTitle";
	public static final String NOTICE_CONTENT = "noticeContent";
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
	 * @see org.spes.bean.NoticeDAO#save(org.spes.bean.Notice)
	 */
	public int save(Notice transientInstance) {
		int result = -1;
		try {
			result = (Integer)getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
			return result;
		} catch (RuntimeException re) {
			log.error("save failed", re);
			re.printStackTrace();
			return result;
		}
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.NoticeDAO#delete(org.spes.bean.Notice)
	 */
	public void delete(Notice persistentInstance) {
		log.debug("deleting Notice instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Notice findById(java.lang.Integer id) {
		log.debug("getting Notice instance with id: " + id);
		try {
			Notice instance = (Notice) getHibernateTemplate().get(
					"org.spes.bean.Notice", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Notice instance) {
		log.debug("finding Notice instance by example");
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
	 * @see org.spes.bean.NoticeDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Notice instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Notice as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByNoticeTitle(Object noticeTitle) {
		return findByProperty(NOTICE_TITLE, noticeTitle);
	}

	public List findByNoticeContent(Object noticeContent) {
		return findByProperty(NOTICE_CONTENT, noticeContent);
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
	 * @see org.spes.bean.NoticeDAO#findAll()
	 */
	public List findAll() {
		log.debug("finding all Notice instances");
		try {
			String queryString = "from Notice";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Notice merge(Notice detachedInstance) {
		log.debug("merging Notice instance");
		try {
			Notice result = (Notice) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Notice instance) {
		log.debug("attaching dirty Notice instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Notice instance) {
		log.debug("attaching clean Notice instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static NoticeDAO getFromApplicationContext(ApplicationContext ctx) {
		return (NoticeDAO) ctx.getBean("NoticeDAO");
	}

	public int update(Notice notice) {
		return merge(notice).getNoticeId();
	}

	public List<Notice> findAllSortByProperty(String property, String direction, Integer userId) {
		log.debug("finding Notice order by property: " + property +" "
			 + direction);
		try {
			String queryString = "from Notice where userId = " + userId + "order by "
					+ property + " " + direction;
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findCheckNoticeOrderBy(Integer userId, List userIds, String property, String value) {
		// TODO Auto-generated method stub
		log.debug("finding Notice where userId is not " + userId);
			try {
				String queryString = "from Notice where userId !=  " + userId;
				if (userIds.size()> 0) {
					queryString += " and ( ";
					for (int i = 0; i < userIds.size(); i++) {
						if (i == 0) {
							queryString +=  "  userId = " + userIds.get(i) ;
						} else {
							queryString +=  " or userId = " + userIds.get(i) ;
						}
					}
				}
				
				queryString += " ) order by " + property + " " + value;
				return getHibernateTemplate().find(queryString);
			} catch (RuntimeException re) {
				log.error("find by property name failed", re);
				throw re;
			}
	}

}