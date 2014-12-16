package org.spes.service.centerManage.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.spes.bean.BdCity;
import org.spes.bean.BdCounty;
import org.spes.bean.BdProvince;
import org.spes.bean.ServiceCenter;
import org.spes.bean.ServiceCenterAudit;
import org.spes.dao.common.BdCityDAO;
import org.spes.dao.common.BdCountyDAO;
import org.spes.dao.common.BdProvinceDAO;
import org.spes.dao.common.ServiceCenterAuditDAO;
import org.spes.dao.common.ServiceCenterDAO;
import org.spes.service.centerManage.CenterRegisterService;

/**
 * 中心管理服务层
 * @author DJ
 */
public class CenterRegisterServiceImpl implements CenterRegisterService {
	
	/**省份Dao*/
	private BdProvinceDAO dbProvinceDao  = null;
	
	/**城市Dao*/
	private BdCityDAO dbCityDao = null;
	
	/**区/县Dao*/
	private BdCountyDAO dbcountyDao = null;
	
	/**服务中心注册Dao*/
	private ServiceCenterAuditDAO serviceCenterAuditDao = null;
	
	/**中心信息Dao*/
	private ServiceCenterDAO serviceCenterDao = null;
	
	public ServiceCenterDAO getServiceCenterDao() {
		return serviceCenterDao;
	}

	public void setServiceCenterDao(ServiceCenterDAO serviceCenterDao) {
		this.serviceCenterDao = serviceCenterDao;
	}

	public ServiceCenterAuditDAO getServiceCenterAuditDao() {
		return serviceCenterAuditDao;
	}

	public void setServiceCenterAuditDao(ServiceCenterAuditDAO serviceCenterAuditDAO) {
		this.serviceCenterAuditDao = serviceCenterAuditDAO;
	}

	public BdCityDAO getDbCityDao() {
		return dbCityDao;
	}

	public void setDbCityDao(BdCityDAO dbCityDao) {
		this.dbCityDao = dbCityDao;
	}

	public BdCountyDAO getDbcountyDao() {
		return dbcountyDao;
	}

	public void setDbcountyDao(BdCountyDAO dbcountyDAO) {
		this.dbcountyDao = dbcountyDAO;
	}

	public BdProvinceDAO getDbProvinceDao() {
		return dbProvinceDao;
	}
	
	public void setDbProvinceDao(BdProvinceDAO dbProvinceDao) {
		this.dbProvinceDao = dbProvinceDao;
	}

	/**获取所有省*/
	public List<BdProvince> getAll() {
		return dbProvinceDao.findAll();
	}

	/**通过省名获取城市名*/
	public List<BdCity> getCitiedByProvince(String provinceCode) {
		List<BdCity> citiesList = dbCityDao.findAll();
		if(citiesList == null || citiesList.size() == 0){
			return new ArrayList<BdCity>();
		}
		Iterator<BdCity> it = citiesList.iterator();//不允许使用快速迭代器删除元素
		while(it.hasNext()){
			BdCity city = it.next();
			if(!city.getCode().startsWith(provinceCode)){
				it.remove();
			}
		}
		if(citiesList == null)
			return new ArrayList<BdCity>();
		
		return citiesList;
	}

	/**通过省名称获取省代号*/
	public String getProvinceCodeByName(String provinceName) {
		List<BdProvince> pros =  dbProvinceDao.findByProperty("name", provinceName);
		if(pros == null || pros.size() == 0){
			System.out.println("没有符合名称的省份...");
			return "";
		}
		return pros.get(0).getCode();
	}

	/**通过城市名获取所有区县*/
	public List<BdCounty> getCountiesByCity(String cityCode) {
		List<BdCounty> list = dbcountyDao.findAll();
		
		if(list == null || list.size()==0){
			System.out.println("");
			return new ArrayList<BdCounty>();
		}
		
		Iterator<BdCounty> it = list.iterator();
		while(it.hasNext()){
			BdCounty county = it.next();
			if(!county.getCode().startsWith(cityCode)){
				it.remove();
			}
		}
		return list;
	}
	
	/**通过城市名获取城市对象*/
	public BdCounty getCityByName(String city) {
		List<BdCounty> list = dbCityDao.findByProperty("name", city);
		if(list.size()!=0)
			return (BdCounty) list.get(0);
		else return new BdCounty();
	}

	/**中心注册信息保存*/
	public int saveCenterRegister(ServiceCenterAudit serviceCenter) {
		return serviceCenterAuditDao.save(serviceCenter);
		
	}
	
	/**中心信息保存*/
	public int saveCenterInfo(ServiceCenter serviceCenter) {
		return serviceCenterDao.save(serviceCenter);
	}

	/**获取所有中心注册信息*/
	public List<ServiceCenterAudit> findAllAuditInfo() {
		return serviceCenterAuditDao.findAll();
	}
	
	/**通过id删除注册信息*/
	public void deletAuditById(Integer delAuditId) {
		List<ServiceCenterAudit> sva =  serviceCenterAuditDao.findByProperty("id", delAuditId);
		if(sva != null && sva.size() != 0){
			serviceCenterAuditDao.delete(sva.get(0));
		} else{
			serviceCenterAuditDao.delete(new ServiceCenterAudit());
		}
	}

	/**审核通过*/
	public void passAudit(Integer delAuditId) {
		List<ServiceCenterAudit> sva = serviceCenterAuditDao.findByProperty("id", delAuditId);
		ServiceCenterAudit sca = null;
		if(sva != null && sva.size() != 0){
			sca = sva.get(0);
		}
		sca.setValid("true");
		serviceCenterAuditDao.update(sca);
	}

	public ServiceCenterAudit findServiceAuditById(Integer delAuditId) {
		List list = serviceCenterAuditDao.findByProperty("id", delAuditId);
		if(list != null)
			return (ServiceCenterAudit) list.get(0);
		else return new ServiceCenterAudit();
	}

	public ServiceCenterAudit findByName(String name) {
		List list = serviceCenterAuditDao.findByProperty("centerName", name);
		if(list != null && list.size() > 0){
			return (ServiceCenterAudit)list.get(0);
		}
		return null;
	}

	
	

}
