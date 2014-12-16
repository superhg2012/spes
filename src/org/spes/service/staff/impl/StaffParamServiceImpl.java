package org.spes.service.staff.impl;

import org.spes.dao.item.StaffParamDAO;
import org.spes.service.staff.StaffParamService;

public class StaffParamServiceImpl implements StaffParamService {
	private StaffParamDAO staffParamDao = null;

	public void setStaffParamDao(StaffParamDAO staffParamDao) {
		this.staffParamDao = staffParamDao;
	}

}
