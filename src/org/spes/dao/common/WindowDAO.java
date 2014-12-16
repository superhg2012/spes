package org.spes.dao.common;

import java.util.List;

import org.spes.bean.Window;

public interface WindowDAO {

	public void save(Window transientInstance);

	public void delete(Window persistentInstance);

	public List findByProperty(String propertyName, Object value);

	public List findAll();
	
	public boolean updateWindow(Window window);

	/**
	 * 通过用户名，查找可以查看的窗口
	 * 
	 * @param userId
	 *            用户ID
	 * @return 窗口List
	 */
	public List findByUserId(Integer userId);

	/**
	 * 通过中心Id，查找属于该中心的窗口
	 * 
	 * @param centerId
	 *            中心Id
	 * @return 窗口列表
	 */
	public List<Window> findByCenterId(Integer centerId);

	// /////////////////////zhoushaojun start/////////////////////
	/**
	 * 通过center的主键centerId进行查找中心
	 * 
	 * @param centerId
	 * @return
	 */
	public Window findWindowById(int centerId);

	/**
	 * 通过中心的名称查询中心的Id（保证数量为1） -1 表示结果失败 其余 表示结果成功
	 */
	public int findWindowIdByName(String name, int centerId);

	// /////////////////////zhoushaojun end/////////////////////

}