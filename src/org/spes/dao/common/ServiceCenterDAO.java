package org.spes.dao.common;

import java.util.List;

import org.spes.bean.BdCounty;
import org.spes.bean.ServiceCenter;

public interface ServiceCenterDAO {


	/**
	 * 通过center的主键centerId进行查找中心
	 * @param centerId
	 * @return
	 */
    public ServiceCenter findCenterById(int centerId);
    /**
     * 通过中心的名称查询中心的Id（保证数量为1）
     * -1  表示结果失败
     * 其余  表示结果成功
     */
    public int  findCenterIdByName(String name);    

    /**
	 * 通过center的主键centerId进行查找中心,更新back1参数
	 * @param centerId
	 * @return
	 */
    public boolean updateCenterBackPara(int centerId,String backPara);


    
    public int save(ServiceCenter serviceCenter);

	public void delete(ServiceCenter serviceCenter);

	public List findByProperty(String propertyName, Object value);

	public List findAll();
	
	public int updateServiceCenter(ServiceCenter serviceCenter);
	
    
}