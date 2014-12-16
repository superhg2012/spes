package org.spes.service.sys;

import java.util.List;

import org.spes.bean.Consult;
import org.spes.bean.Notice;

/**
 * The interface service of consult 
 * 
 * @author WuRuihong
 * @version 1.0 2013.07.11
 *asd
 */
public interface ConsultService {
	
	/**
	 * get the consult list from the database
	 * 
	 * @param property the sorted colum
	 * @param direction desc or asc
	 * @return a user's consult list
	 */
	public List getConsultListByUserOrderBy(String property, String direction);
	
	/**
	 * add a consult to the database
	 * 
	 * @param title
	 * @param content
	 * @return the id of the new consult
	 */
	public int addConsult(String title, String content);
	
	/**
	 *  edit the consult for the verified id and set the content title and content to a new value
	 *  
	 * @param consultID
	 * @param title
	 * @param content
	 * @return the new consult id
	 */
	public int editConsult(Integer consultID, String title, String content);
	
	/**
	 * delete a consult from the database
	 * @param consultId
	 * @return failure: not success; success: delete success
	 */
	public String deleteConsult(Integer consultId);
	
	/**
	 * get the consult list by userId
	 * @param userId
	 * @return
	 */
	public List getConsultListByUser(Integer userId);
	
	/**
	 * get the Consult by consultId
	 * @param consultId
	 * @return
	 */
	public Consult getConsultById(Integer consultId);
	
	/**
	 * get the userName by userId
	 * @param userId
	 * @return the userName
	 */
	public String getUserNameById(Integer userId);
	
}
