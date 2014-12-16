package org.spes.service.centerManage;

import java.util.List;

import org.spes.bean.BdCity;
import org.spes.bean.BdCounty;
import org.spes.bean.BdProvince;
import org.spes.bean.ServiceCenter;
import org.spes.bean.ServiceCenterAudit;

/**
 * 中心管理接口
 * @author DJ
 */
public interface CenterRegisterService {
	
	/**获取所有服务中心*/
	public List<BdProvince> getAll();
	
	/**增加一个中心注册信息*/
	public int saveCenterRegister(ServiceCenterAudit serviceCenter);
	
	/**通过省名称获取所有城市*/
	public List<BdCity> getCitiedByProvince(String provinceCodea);

	/**通过省名称获取省代号*/
	public String getProvinceCodeByName(String province);
	
	/**通过城市名获取所有区县*/
	public List<BdCounty> getCountiesByCity(String cityCode);

	/**通过城市名获取城市对象*/
	public BdCounty getCityByName(String city);
	
	/**添加中心信息表*/
	public int saveCenterInfo(ServiceCenter serviceCenter);
	
	/**获取所有注册信息*/
	public List<ServiceCenterAudit> findAllAuditInfo();

	/**通过id删除审核信息*/
	public void deletAuditById(Integer delAuditId);

	/**审核通过*/
	public void passAudit(Integer delAuditId);

	public ServiceCenterAudit findServiceAuditById(Integer delAuditId);

	public ServiceCenterAudit findByName(String name);
	
}
