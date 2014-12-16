package org.spes.service.centerManage;

import java.util.List;

import org.spes.bean.*;
/**中心信息管理服务层接口*/
public interface CenterInfoManageService {
	
	/**所有中心信息*/
	List<ServiceCenter> getServiceCenterInfo();

	/**通过id删除中心*/
	void deleteServiceCenter(Integer delServicecId);
	
	/**通过id修改中心信息*/
	int updateSeraviceCenter(ServiceCenter serviceCenter);
	
	/**通过id获取中心信息*/
	ServiceCenter getServiceCenterById(Integer editServiceId);

	/**保存中心管理人员修改的中心*/
	void saveModified(List<ServiceCenter> services);
}
