package org.spes.service.center;

import java.util.ArrayList;

import org.spes.bean.ServiceCenter;

public interface CenterService {
	
	/**
	 * 通过center的主键centerId进行查找中心
	 * @param centerId
	 * @return
	 */
    public ServiceCenter findCenterServiceById(int centerId);
    
    /**
     * 通过center的主键centerId进行查找中心，更新Back1信息
     */
    public boolean updateServiceCenterBack1Para(int centerId,String backPara);
    
    /**
     * 通过中心的名称查询中心的Id（保证数量为1）
     * -1  表示结果失败
     * 其余  表示结果成功
     */
    public int  findCenterIdByName(String name); 
    /**
     * 获取所有的中心信息
     * hegang
     * @return
     */
    public ArrayList<ServiceCenter> getAllCenterService();
}
