package org.spes.dao.common.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spes.bean.ServiceCenter;
import org.spes.bean.ServiceCenterAudit;
import org.spes.dao.common.ServiceCenterDAO;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Dodify by DJ
 */
public class ServiceCenterDAOImpl extends HibernateDaoSupport implements ServiceCenterDAO{
	
	private static final Logger log = LoggerFactory
		.getLogger(ServiceCenterDAOImpl.class);
	
	  public ServiceCenter findCenterById(int centerId) {

	     try {
	    	 Object result = this.getHibernateTemplate().get("org.spes.bean.ServiceCenter", centerId);
	    	 if(null == result) return null;
	    	 else{
	    		 return (ServiceCenter)result;
	    	 }
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	  }
	  public int findCenterIdByName(String name) {
		  try {
			  List<ServiceCenter> results = this.getHibernateTemplate().find("from ServiceCenter u where u.centerName = ?", name);
			  if(null == results || results.size() != 1) return -1;
			  return results.get(0).getCenterId();
			  
		} catch (Exception e) {
			return -1;
		}
		  
	  }
	
	  public boolean updateCenterBackPara(int centerId, String backPara) {
		     try {
				
		    	 Object result = this.getHibernateTemplate().get(ServiceCenter.class, centerId);
		    	 if(null == result) return false;
		    	 else{
		    		 ServiceCenter center = (ServiceCenter)result;
		    		 center.setBackup1(backPara);
		    		 this.getHibernateTemplate().update(result);
		    		 return true;
		    	 }
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
	  }
	  
	 /**删除中心信息*/
	public void delete(ServiceCenter serviceCenter) {
		log.debug("deleting ServiceCenter instance");
		try {
			getHibernateTemplate().delete(serviceCenter);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	/**列出所有中心信息*/
	public List findAll() {
		log.debug("finding all ServiceCenter instances");
		try {
			String queryString = "from ServiceCenter";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	/**通过某个属性查找中心信息*/
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding ServiceCenter instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ServiceCenter as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	/**保存某个中心信息*/
	public int save(ServiceCenter serviceCenter) {
		int result = -1;
		log.debug("saving ServiceCenterAudit instance");
		try {
			result = (Integer)getHibernateTemplate().save(serviceCenter);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
		
		return result;
	}
	
	public ServiceCenter merge(ServiceCenter detachedInstance) {
		log.debug("merging ServiceCenterAudit instance");
		try {
			ServiceCenter result = (ServiceCenter) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}
	
	/**更新某个中心的信息*/
	public int updateServiceCenter(ServiceCenter serviceCenter) {
		ServiceCenter sv = findCenterById(serviceCenter.getCenterId());
		
		sv.setCenterName(serviceCenter.getCenterName());
		sv.setCity(serviceCenter.getCity());
		sv.setProvince(serviceCenter.getProvince());
		sv.setCounty(serviceCenter.getCounty());
		sv.setContact(serviceCenter.getContact());
		sv.setLinkman(serviceCenter.getLinkman());
		sv.setEmail(serviceCenter.getEmail());
		sv.setLegalrepresent(serviceCenter.getLegalrepresent());
		sv.setRemarks(serviceCenter.getRemarks());
		System.out.println("centerName:"+sv.getCenterName());
		merge(sv);
		return serviceCenter.getCenterId();
		
	}
}