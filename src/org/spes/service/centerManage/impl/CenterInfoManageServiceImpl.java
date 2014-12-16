package org.spes.service.centerManage.impl;

import java.util.List;

import org.spes.bean.ServiceCenter;
import org.spes.dao.common.ServiceCenterDAO;
import org.spes.service.centerManage.CenterInfoManageService;

/**
 * ������Ϣ��������
 * @author DJ
 */
public class CenterInfoManageServiceImpl implements CenterInfoManageService {

	/**����������Ϣdao*/
	private ServiceCenterDAO serviceCenterDao = null;
	
	public ServiceCenterDAO getServiceCenterDao() {
		return serviceCenterDao;
	}

	public void setServiceCenterDao(ServiceCenterDAO serviceCenterDao) {
		this.serviceCenterDao = serviceCenterDao;
	}

	/**�г�����������Ϣ*/
	public List<ServiceCenter> getServiceCenterInfo() {
		return serviceCenterDao.findAll();
	}

	/**ͨ��idɾ������*/
	public void deleteServiceCenter(Integer delServicecId) {
		List<ServiceCenter> list = serviceCenterDao.findByProperty("centerId", delServicecId);
		ServiceCenter serviceCenter = null;
		if(list != null && list.size() != 0){
			 serviceCenter = list.get(0);
		}
		serviceCenterDao.delete(serviceCenter);
	}

	/**ͨ������id�޸�������Ϣ*/
	public int updateSeraviceCenter(ServiceCenter serviceCenter) {
		
		return  serviceCenterDao.updateServiceCenter(serviceCenter);
	}

	/**ͨ������id��ȡ������Ϣ*/
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
			System.out.println("δ�޸��κ�������Ϣ!");
			return;
		}
		for(int i = 0 ; i < services.size(); i++){
			ServiceCenter sc = services.get(i);
			System.out.println("�޸�serviceCenter:----");
			updateSeraviceCenter(sc);
		}
		
	}

}
