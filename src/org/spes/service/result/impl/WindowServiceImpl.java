package org.spes.service.result.impl;

import java.util.List;

import org.spes.bean.ServiceCenter;
import org.spes.bean.Window;
import org.spes.dao.common.ServiceCenterDAO;
import org.spes.dao.common.WindowDAO;
import org.spes.service.result.WindowService;

public class WindowServiceImpl implements WindowService {

	private WindowDAO windowDao;
	private ServiceCenterDAO centerDao;

	public ServiceCenterDAO getCenterDao() {
		return centerDao;
	}

	public void setCenterDao(ServiceCenterDAO centerDao) {
		this.centerDao = centerDao;
	}

	public WindowDAO getWindowDao() {
		return windowDao;
	}

	public void setWindowDao(WindowDAO windowDao) {
		this.windowDao = windowDao;
	}

	public List GetAvailableWindows(Integer userId) {
		return windowDao.findByUserId(userId);
	}

	public Window findWindowById(int windowId) {
		return windowDao.findWindowById(windowId);
	}

	public int findWindowIdByName(String name, int centerId) {
		return windowDao.findWindowIdByName(name, centerId);
	}

	public List<Window> getWindowsOfCenter(Integer centerId) {
		return windowDao.findByCenterId(centerId);
	}

	public void addWindow(String windowName, String windowBussiness,
			String windowDesc, Integer centerId) {
		Window window = new Window();
		window.setWindowName(windowName);
		window.setWindowBussiness(windowBussiness);
		window.setWindowDesc(windowDesc);
		ServiceCenter sc = centerDao.findCenterById(centerId);
		window.setServiceCenter(sc);
		windowDao.save(window);
	}

	public void updateWindow(Window window) {
		windowDao.updateWindow(window);
	}

}
