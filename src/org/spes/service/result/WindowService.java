package org.spes.service.result;

import java.util.List;

import org.spes.bean.Window;

/**
 * 窗口业务方法
 * @author Administrator
 *
 */
public interface WindowService {
	
	public List GetAvailableWindows(Integer userId);
	/**
	 * 根据中心ID获取属于某中心的窗口
	 * @param centerId 中心Id
	 * @return 窗口列表
	 */
	public List<Window> getWindowsOfCenter(Integer centerId);
	

	/**
	 * 通过center的主键centerId进行查找中心
	 * @param centerId
	 * @return
	 */
    public Window findWindowById(int windowId);
    /**
     * 通过中心的名称查询中心的Id（保证数量为1）
     * -1  表示结果失败
     * 其余  表示结果成功
     */
    public int  findWindowIdByName(String name,int centerId); 
    
    /**
     * 添加窗口
     * @param windowName
     * @param windowBussiness
     * @param windowDesc
     * @param centerId
     */
    public void addWindow(String windowName, String windowBussiness, String windowDesc,Integer centerId);
    
    /**
     * 更新窗口
     * @param window
     */
    public void updateWindow(Window window);
}
