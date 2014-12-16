package org.spes.dao.common.impl;

import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spes.bean.ServiceCenterAudit;
import org.spes.dao.common.ServiceCenterAuditDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * ServiceCenterAudit entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see org.spes.bean.ServiceCenterAudit
 * @author MyEclipse Persistence Tools
 */

public class ServiceCenterAuditDAOImpl extends HibernateDaoSupport implements ServiceCenterAuditDAO {
	private static final Logger log = LoggerFactory
			.getLogger(ServiceCenterAuditDAOImpl.class);
	// property constants
	public static final String CENTER_ID = "centerId";
	public static final String CENTER_NAME = "centerName";
	public static final String PROVINCE = "province";
	public static final String CITY = "city";
	public static final String COUNTY = "county";
	public static final String ORGANCODE = "organcode";
	public static final String LINKMAN = "linkman";
	public static final String CONTACT = "contact";
	public static final String EMAIL = "email";
	public static final String VALID = "valid";
	public static final String LEGALREPRESENT = "legalrepresent";
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
	 * @see org.spes.bean.ServiceCenterAuditDAO#save(org.spes.bean.ServiceCenterAudit)
	 */
	public int save(ServiceCenterAudit transientInstance) {
		int result = -1;
		log.debug("saving ServiceCenterAudit instance");
		try {
			result = (Integer)getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.spes.bean.ServiceCenterAuditDAO#delete(org.spes.bean.ServiceCenterAudit)
	 */
	public void delete(ServiceCenterAudit persistentInstance) {
		log.debug("deleting ServiceCenterAudit instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ServiceCenterAudit findById(java.lang.Integer id) {
		log.debug("getting ServiceCenterAudit instance with id: " + id);
		try {
			ServiceCenterAudit instance = (ServiceCenterAudit) getHibernateTemplate()
					.get("org.spes.bean.ServiceCenterAudit", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ServiceCenterAudit instance) {
		log.debug("finding ServiceCenterAudit instance by example");
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
	 * @see org.spes.bean.ServiceCenterAuditDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding ServiceCenterAudit instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ServiceCenterAudit as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCenterId(Object centerId) {
		return findByProperty(CENTER_ID, centerId);
	}

	public List findByCenterName(Object centerName) {
		return findByProperty(CENTER_NAME, centerName);
	}

	public List findByProvince(Object province) {
		return findByProperty(PROVINCE, province);
	}

	public List findByCity(Object city) {
		return findByProperty(CITY, city);
	}

	public List findByCounty(Object county) {
		return findByProperty(COUNTY, county);
	}

	public List findByOrgancode(Object organcode) {
		return findByProperty(ORGANCODE, organcode);
	}

	public List findByLinkman(Object linkman) {
		return findByProperty(LINKMAN, linkman);
	}

	public List findByContact(Object contact) {
		return findByProperty(CONTACT, contact);
	}

	public List findByEmail(Object email) {
		return findByProperty(EMAIL, email);
	}

	public List findByValid(Object valid) {
		return findByProperty(VALID, valid);
	}

	public List findByLegalrepresent(Object legalrepresent) {
		return findByProperty(LEGALREPRESENT, legalrepresent);
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
	 * @see org.spes.bean.ServiceCenterAuditDAO#findAll()
	 */
	public List findAll() {
		log.debug("finding all ServiceCenterAudit instances");
		try {
			String queryString = "from ServiceCenterAudit";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ServiceCenterAudit merge(ServiceCenterAudit detachedInstance) {
		log.debug("merging ServiceCenterAudit instance");
		try {
			ServiceCenterAudit result = (ServiceCenterAudit) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ServiceCenterAudit instance) {
		log.debug("attaching dirty ServiceCenterAudit instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ServiceCenterAudit instance) {
		log.debug("attaching clean ServiceCenterAudit instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ServiceCenterAuditDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (ServiceCenterAuditDAO) ctx.getBean("ServiceCenterAuditDAO");
	}

	public void update(ServiceCenterAudit sca) {
		// TODO Auto-generated method stub
		merge(sca);
	}
}