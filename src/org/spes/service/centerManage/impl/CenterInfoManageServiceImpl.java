package org.spes.service.centerManage.impl;

import java.util.List;

import org.spes.bean.ServiceCenter;
import org.spes.dao.common.ServiceCenterDAO;
import org.spes.service.centerManage.CenterInfoManageService;

/**
 * 中心信息管理服务层
 * @author DJ
 */
public class CenterInfoManageServiceImpl implements CenterInfoManageService {

	/**服务中心信息dao*/
	private ServiceCenterDAO serviceCenterDao = null;
	
	public ServiceCenterDAO getServiceCenterDao() {
		return serviceCenterDao;
	}

	public void setServiceCenterDao(ServiceCenterDAO serviceCenterDao) {
		this.serviceCenterDao = serviceCenterDao;
	}

	/**列出所有中心信息*/
	public List<ServiceCenter> getServiceCenterInfo() {
		return serviceCenterDao.findAll();
	}

	/**通过id删除中心*/
	public void deleteServiceCenter(Integer delServicecId) {
		List<ServiceCenter> list = serviceCenterDao.findByProperty("centerId", delServicecId);
		ServiceCenter serviceCenter = null;
		if(list != null && list.size() != 0){
			 serviceCenter = list.get(0);
		}
		serviceCenterDao.delete(serviceCenter);
	}

	/**通过中心id修改中心信息*/
	public int updateSeraviceCenter(ServiceCenter serviceCenter) {
		
		return  serviceCenterDao.updateServiceCenter(serviceCenter);
	}

	/**通过中心id获取中心信息*/
	public ServiceCenter getServiceCenterById(Integer editServiceId) {
		List<ServiceCenter> list = serviceCenterDao.findByProperty("centerId", editServiceId);
		if(list.size() > 0){
			return list.get(0);
		}
		else return new ServiceCenter(); 
	}

	public void saveModified(List<ServiceCenter> services) {
		// TODO Auto-generated method stub
		if(services == null || services.size() == 0){
			System.out.println("未修改任何中心信息!");
			return;
		}
		for(int i = 0 ; i < services.size(); i++){
			ServiceCenter sc = services.get(i);
			System.out.println("修改serviceCenter:----");
			updateSeraviceCenter(sc);
		}
		
	}

}
