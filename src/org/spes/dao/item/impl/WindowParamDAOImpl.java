package org.spes.dao.item.impl;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spes.bean.WindowParam;
import org.spes.dao.item.WindowParamDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 	* A data access object (DAO) providing persistence and search support for WindowParam entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see org.spes.bean.WindowParam
  * @author MyEclipse Persistence Tools 
 */

public class WindowParamDAOImpl extends HibernateDaoSupport implements WindowParamDAO  {
	     private static final Logger log = LoggerFactory.getLogger(WindowParamDAOImpl.class);
		//property constants
	public static final String ITEM_NAME = "itemName";
	public static final String ITEM_VALUE = "itemValue";
	public static final String ITEM_ID = "itemId";
	public static final String WINDOW_ID = "windowId";
	public static final String CENTER_ID = "centerId";
	public static final String BACKUP1 = "backup1";
	public static final String BACKUP2 = "backup2";
	public static final String BACKUP3 = "backup3";
	public static final String BACKUP4 = "backup4";
	public static final String BACKUP5 = "backup5";



	protected void initDao() {
		//do nothing
	}
    
    /* (non-Javadoc)
	 * @see org.spes.bean.WindowParamDAO#save(org.spes.bean.WindowParam)
	 */
    public void save(WindowParam transientInstance) {
        log.debug("saving WindowParam instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	/* (non-Javadoc)
	 * @see org.spes.bean.WindowParamDAO#delete(org.spes.bean.WindowParam)
	 */
	public void delete(WindowParam persistentInstance) {
        log.debug("deleting WindowParam instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public WindowParam findById( java.lang.Integer id) {
        log.debug("getting WindowParam instance with id: " + id);
        try {
            WindowParam instance = (WindowParam) getHibernateTemplate()
                    .get("org.spes.bean.WindowParam", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(WindowParam instance) {
        log.debug("finding WindowParam instance by example");
        try {
            List results = getHibernateTemplate().findByExample(instance);
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    /* (non-Javadoc)
	 * @see org.spes.bean.WindowParamDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding WindowParam instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from WindowParam as model where model." 
         						+ propertyName + "= ?";
		 return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByItemName(Object itemName
	) {
		return findByProperty(ITEM_NAME, itemName
		);
	}
	
	public List findByItemValue(Object itemValue
	) {
		return findByProperty(ITEM_VALUE, itemValue
		);
	}
	
	public List findByItemId(Object itemId
	) {
		return findByProperty(ITEM_ID, itemId
		);
	}
	
	public List findByWindowId(Object windowId
	) {
		return findByProperty(WINDOW_ID, windowId
		);
	}
	
	public List findByCenterId(Object centerId
	) {
		return findByProperty(CENTER_ID, centerId
		);
	}
	
	public List findByBackup1(Object backup1
	) {
		return findByProperty(BACKUP1, backup1
		);
	}
	
	public List findByBackup2(Object backup2
	) {
		return findByProperty(BACKUP2, backup2
		);
	}
	
	public List findByBackup3(Object backup3
	) {
		return findByProperty(BACKUP3, backup3
		);
	}
	
	public List findByBackup4(Object backup4
	) {
		return findByProperty(BACKUP4, backup4
		);
	}
	
	public List findByBackup5(Object backup5
	) {
		return findByProperty(BACKUP5, backup5
		);
	}
	

	/* (non-Javadoc)
	 * @see org.spes.bean.WindowParamDAO#findAll()
	 */
	public List findAll() {
		log.debug("finding all WindowParam instances");
		try {
			String queryString = "from WindowParam";
		 	return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public WindowParam merge(WindowParam detachedInstance) {
        log.debug("merging WindowParam instance");
        try {
            WindowParam result = (WindowParam) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(WindowParam instance) {
        log.debug("attaching dirty WindowParam instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(WindowParam instance) {
        log.debug("attaching clean WindowParam instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static WindowParamDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (WindowParamDAO) ctx.getBean("WindowParamDAO");
	}
}