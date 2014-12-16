package org.spes.dao.common.impl;

import java.util.List;
import java.util.Set;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spes.bean.Post;
import org.spes.dao.common.PostDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 	* A data access object (DAO) providing persistence and search support for Post entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see org.spes.bean.Post
  * @author MyEclipse Persistence Tools 
 */

public class PostDAOImpl extends HibernateDaoSupport implements PostDAO  {
	     private static final Logger log = LoggerFactory.getLogger(PostDAOImpl.class);
		//property constants
	public static final String POST_NAME = "postName";
	public static final String ENABLED = "enabled";
	public static final String POST_DESC = "postDesc";
	public static final String BACKUP1 = "backup1";
	public static final String BACKUP2 = "backup2";
	public static final String BACKUP3 = "backup3";
	public static final String BACKUP4 = "backup4";
	public static final String BACKUP5 = "backup5";



	protected void initDao() {
		//do nothing
	}
    
    /* (non-Javadoc)
	 * @see org.spes.bean.PostDAO#save(org.spes.bean.Post)
	 */
    public void save(Post transientInstance) {
        log.debug("saving Post instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	/* (non-Javadoc)
	 * @see org.spes.bean.PostDAO#delete(org.spes.bean.Post)
	 */
	public void delete(Post persistentInstance) {
        log.debug("deleting Post instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Post findById( java.lang.Integer id) {
        log.debug("getting Post instance with id: " + id);
        try {
            Post instance = (Post) getHibernateTemplate()
                    .get("org.spes.bean.Post", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(Post instance) {
        log.debug("finding Post instance by example");
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
	 * @see org.spes.bean.PostDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding Post instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Post as model where model." 
         						+ propertyName + "= ?";
		 return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByPostName(Object postName
	) {
		return findByProperty(POST_NAME, postName
		);
	}
	
	public List findByEnabled(Object enabled
	) {
		return findByProperty(ENABLED, enabled
		);
	}
	
	public List findByPostDesc(Object postDesc
	) {
		return findByProperty(POST_DESC, postDesc
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
	 * @see org.spes.bean.PostDAO#findAll()
	 */
	public List findAll() {
		log.debug("finding all Post instances");
		try {
			String queryString = "from Post";
		 	return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Post merge(Post detachedInstance) {
        log.debug("merging Post instance");
        try {
            Post result = (Post) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Post instance) {
        log.debug("attaching dirty Post instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Post instance) {
        log.debug("attaching clean Post instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static PostDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (PostDAO) ctx.getBean("PostDAO");
	}
}