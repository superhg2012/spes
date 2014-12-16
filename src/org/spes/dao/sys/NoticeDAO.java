package org.spes.dao.sys;

import java.util.List;

import org.spes.bean.Notice;

public interface NoticeDAO {

	public int save(Notice transientInstance);

	public void delete(Notice persistentInstance);

	public List findByProperty(String propertyName, Object value);

	public List findAll();
	
	public int update(Notice notice);
	
	public Notice findById(Integer noticeId);
	
	/**
	 * find all the sort by property and direction
	 * @param property
	 * @param direction
	 * @return
	 */
	public List<Notice> findAllSortByProperty(String property, String direction, Integer userId);
	
	/**
	 * find the notice where the notice where userId is not the userId.
	 * 
	 * @param userId
	 * @return
	 */
	public List findCheckNoticeOrderBy(Integer userId, List userIds, String property, String value);
}