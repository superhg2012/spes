package org.spes.dao.sys;

import java.util.List;

import org.spes.bean.Consult;
/**
 * The DAO Interface of the Consult
 * @author WuRuihong 
 * @version 1.0 2013.07.11
 *
 */
public interface ConsultDAO {

	/**
	 * save the consult instance to the database
	 * 
	 * @param transientInstance the saved instance
	 * @return the ID of the saved instance
	 */
	public int save(Consult transientInstance);

	/**
	 * delete the consult instance from the database
	 * 
	 * @param persistentInstance the deleted instance
	 */
	public void  delete(Consult persistentInstance);

	/**
	 * find the consult by id
	 * @param id
	 * @return
	 */
	public Consult findById(Integer id);
	
	/**
	 * find the Consult instance by the property Name and its value
	 *  
	 * @param propertyName the property name
	 * @param value the property value
	 * @return the List of the consult instance
	 */
	public List findByProperty(String propertyName, Object value);
	
	/**
	 * find the Consult instance by userId and sort them by the property direction (desc or asc)
	 * 
	 * @param userId the userId
	 * @param property the sorted column name 
	 * @param direction the sorted desc or asc
	 * @return the list of the userId sorted by property direction
	 */
	public List findByUserIdOrderBy(Integer userId, String property, String direction);

	/**
	 * update the consult use the new instance if exists ,or save it to the datatbase
	 * 
	 * @param detachedInstance the new consult instance
	 * @return the updated consult instance
	 */
	public Consult update(Consult detachedInstance);
	
	/**
	 * find all the List of the consult table
	 * 
	 * @return all the consult list in the table 
	 */
	public List findAll();
	
	/**
	 * get all the consult list order by backup1
	 * @return
	 */
	public List findAllOrderByBackup1();

}