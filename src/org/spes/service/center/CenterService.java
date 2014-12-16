package org.spes.service.center;

import java.util.ArrayList;

import org.spes.bean.ServiceCenter;

public interface CenterService {
	
	/**
	 * ͨ��center������centerId���в�������
	 * @param centerId
	 * @return
	 */
    public ServiceCenter findCenterServiceById(int centerId);
    
    /**
     * ͨ��center������centerId���в������ģ�����Back1��Ϣ
     */
    public boolean updateServiceCenterBack1Para(int centerId,String backPara);
    
    /**
     * ͨ�����ĵ����Ʋ�ѯ���ĵ�Id����֤����Ϊ1��
     * -1  ��ʾ���ʧ��
     * ����  ��ʾ����ɹ�
     */
    public int  findCenterIdByName(String name); 
    /**
     * ��ȡ���е�������Ϣ
     * hegang
     * @return
     */
    public ArrayList<ServiceCenter> getAllCenterService();
}
