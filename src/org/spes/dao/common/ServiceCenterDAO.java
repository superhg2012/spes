package org.spes.dao.common;

import java.util.List;

import org.spes.bean.BdCounty;
import org.spes.bean.ServiceCenter;

public interface ServiceCenterDAO {


	/**
	 * ͨ��center������centerId���в�������
	 * @param centerId
	 * @return
	 */
    public ServiceCenter findCenterById(int centerId);
    /**
     * ͨ�����ĵ����Ʋ�ѯ���ĵ�Id����֤����Ϊ1��
     * -1  ��ʾ���ʧ��
     * ����  ��ʾ����ɹ�
     */
    public int  findCenterIdByName(String name);    

    /**
	 * ͨ��center������centerId���в�������,����back1����
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