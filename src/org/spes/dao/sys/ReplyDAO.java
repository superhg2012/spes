package org.spes.dao.sys;

import java.util.List;

import org.spes.bean.Reply;
/**
 * The interface DAO of the reply
 * @author WuRuihong
 * @version 1.0 2013.07.11
 */
public interface ReplyDAO {

	/**
	 * find the reply by id
	 * @param id
	 * @return reply
	 */
	public Reply findById(Integer id);
	
	/**
	 * save the reply to the database
	 * 
	 * @param transientInstance the saved instance
	 * @return the id of the saved instance
	 */
	public int save(Reply transientInstance);

	/**
	 * delete the reply instance from the database
	 * 
	 * @param persistentInstance
	 */
	public void delete(Reply persistentInstance);
	
	/**
	 * update the reply 
	 * @param reply
	 * @return
	 */
	public Reply update(Reply reply);

	/**
	 * find the reply instance by propername and value
	 * 
	 * @param propertyName
	 * @param value
	 * @return the reply list
	 */
	public List findByProperty(String propertyName, Object value);

	/**
	 * find all the instance from the reply list
	 * 
	 * @return all the instance
	 */
	public List findAll();

}