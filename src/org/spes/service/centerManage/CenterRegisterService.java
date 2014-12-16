package org.spes.service.centerManage;

import java.util.List;

import org.spes.bean.BdCity;
import org.spes.bean.BdCounty;
import org.spes.bean.BdProvince;
import org.spes.bean.ServiceCenter;
import org.spes.bean.ServiceCenterAudit;

/**
 * ���Ĺ���ӿ�
 * @author DJ
 */
public interface CenterRegisterService {
	
	/**��ȡ���з�������*/
	public List<BdProvince> getAll();
	
	/**����һ������ע����Ϣ*/
	public int saveCenterRegister(ServiceCenterAudit serviceCenter);
	
	/**ͨ��ʡ���ƻ�ȡ���г���*/
	public List<BdCity> getCitiedByProvince(String provinceCodea);

	/**ͨ��ʡ���ƻ�ȡʡ����*/
	public String getProvinceCodeByName(String province);
	
	/**ͨ����������ȡ��������*/
	public List<BdCounty> getCountiesByCity(String cityCode);

	/**ͨ����������ȡ���ж���*/
	public BdCounty getCityByName(String city);
	
	/**���������Ϣ��*/
	public int saveCenterInfo(ServiceCenter serviceCenter);
	
	/**��ȡ����ע����Ϣ*/
	public List<ServiceCenterAudit> findAllAuditInfo();

	/**ͨ��idɾ�������Ϣ*/
	public void deletAuditById(Integer delAuditId);

	/**���ͨ��*/
	public void passAudit(Integer delAuditId);

	public ServiceCenterAudit findServiceAuditById(Integer delAuditId);

	public ServiceCenterAudit findByName(String name);
	
}
