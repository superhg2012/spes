package org.spes.service.centerManage;

import java.util.List;

import org.spes.bean.*;
/**������Ϣ��������ӿ�*/
public interface CenterInfoManageService {
	
	/**����������Ϣ*/
	List<ServiceCenter> getServiceCenterInfo();

	/**ͨ��idɾ������*/
	void deleteServiceCenter(Integer delServicecId);
	
	/**ͨ��id�޸�������Ϣ*/
	int updateSeraviceCenter(ServiceCenter serviceCenter);
	
	/**ͨ��id��ȡ������Ϣ*/
	ServiceCenter getServiceCenterById(Integer editServiceId);

	/**�������Ĺ�����Ա�޸ĵ�����*/
	void saveModified(List<ServiceCenter> services);
}
