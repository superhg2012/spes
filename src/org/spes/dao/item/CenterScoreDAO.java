package org.spes.dao.item;

import java.util.List;

import org.spes.bean.CenterScore;

public interface CenterScoreDAO {

	public Integer save(CenterScore transientInstance);

	public void delete(CenterScore persistentInstance);

	public void deleteCenterScore(String sheetId, String sheetName);
	
	public List findByProperty(String propertyName, Object value);

	public List findAll();

	public List findByCenterId(Object centerId);
	
	public List findByCenterIdAndDates(String from,String to,Integer centerId, String type);
	
	public CenterScore findByCenterIdAndItemId(final Integer centerId, final Integer itemId);
	
	public CenterScore findByCenterIdAndItemId(final Integer centerId, final Integer itemId, final String sheetId);
	
	public CenterScore findByParams(Integer centerId, Integer itemId, String checkType, String sheetName, String userName);
}