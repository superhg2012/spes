package org.spes.dao.item;

import java.util.List;

import org.spes.bean.StaffScore;

public interface StaffScoreDAO {

	public void save(StaffScore transientInstance);

	public void delete(StaffScore persistentInstance);

	public void deleteStaffScore(String sheetId, String sheetName);
	
	public List findByProperty(String propertyName, Object value);

	public List findAll();

	public StaffScore findByIds(Integer itemId, Integer userId,
			Integer windowId, Integer centerId, String sheetId);

	public List getStaffAndScoreByWindowIdAndTime(Integer windowId,
			String from, String to, String type);

	public List getStaffAndScoreByIdAndTime(Integer userId, String from,
			String to, String type);
	
	public List getStaffAndScoreByUserIdAndTime(Integer userId,String from ,String to, String type);
}