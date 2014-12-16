package org.spes.service.result;

import java.util.List;

import org.spes.bean.Window;

/**
 * ����ҵ�񷽷�
 * @author Administrator
 *
 */
public interface WindowService {
	
	public List GetAvailableWindows(Integer userId);
	/**
	 * ��������ID��ȡ����ĳ���ĵĴ���
	 * @param centerId ����Id
	 * @return �����б�
	 */
	public List<Window> getWindowsOfCenter(Integer centerId);
	

	/**
	 * ͨ��center������centerId���в�������
	 * @param centerId
	 * @return
	 */
    public Window findWindowById(int windowId);
    /**
     * ͨ�����ĵ����Ʋ�ѯ���ĵ�Id����֤����Ϊ1��
     * -1  ��ʾ���ʧ��
     * ����  ��ʾ����ɹ�
     */
    public int  findWindowIdByName(String name,int centerId); 
    
    /**
     * ��Ӵ���
     * @param windowName
     * @param windowBussiness
     * @param windowDesc
     * @param centerId
     */
    public void addWindow(String windowName, String windowBussiness, String windowDesc,Integer centerId);
    
    /**
     * ���´���
     * @param window
     */
    public void updateWindow(Window window);
}
