package org.spes.service.center.impl;

import java.util.ArrayList;

import org.spes.bean.ServiceCenter;
import org.spes.dao.common.impl.ServiceCenterDAOImpl;
import org.spes.service.center.CenterService;

public class CenterServiceImpl implements CenterService {

	// centerService µÄÊý¾Ý¿âDAO²ã
	public ServiceCenterDAOImpl centerServiceDAO = null;

	public ServiceCenter findCenterServiceById(int centerId) {
		return centerServiceDAO.findCenterById(centerId);
	}

	public int findCenterIdByName(String name) {
		return centerServiceDAO.findCenterIdByName(name);
	}

	public boolean updateServiceCenterBack1Para(int centerId, String backPara) {
		return centerServiceDAO.updateCenterBackPara(centerId, backPara);
	}

	public ServiceCenterDAOImpl getCenterServiceDAO() {
		return centerServiceDAO;
	}

	public void setCenterServiceDAO(ServiceCenterDAOImpl centerServiceDAO) {
		this.centerServiceDAO = centerServiceDAO;
	}

	public ArrayList<ServiceCenter> getAllCenterService() {
		return (ArrayList<ServiceCenter>) this.centerServiceDAO.findAll();
	}

}
