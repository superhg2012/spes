package org.spes.dao.item;

import java.util.List;

import org.spes.bean.WindowScore;

public interface WindowScoreDAO {

	public void save(WindowScore transientInstance);

	public void delete(WindowScore persistentInstance);

	public void deleteCenterScore(String sheetId, String sheetName);
	
	public List findByProperty(String propertyName, Object value);

	public List findAll();
	
	public List FindByWindowIdAndTime(Integer windowId,String from,String to, String type);
	
	public WindowScore findWindowScoreByIds(Integer centerId, Integer windowId, Integer itemId, String sheetId);

}