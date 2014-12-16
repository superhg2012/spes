package org.spes.service.sys;

import java.util.List;

import org.spes.bean.Reply;

public interface ReplyService {
	
	/**
	 * get the reply list from the database
	 * 
	 * @return all the reply list
	 */
	public List getReplyList();
	
	/**
	 * add a reply to the database
	 * 
	 * @param title
	 * @param content
	 * @return the id of the new reply
	 */
	public int addReply(String content, String consultId);
	
	/**
	 *  edit the reply for the verified id and set the content title and content to a new value
	 *  
	 * @param replyID
	 * @param title
	 * @param content
	 * @return the new reply id
	 */
	public int editReply(Integer replyID, String content);
	
	/**
	 * delete a reply from the database
	 * 
	 * @param replyId
	 * @return failure: not success; success: delete success
	 */
	public String deleteReply(Integer replyId);
	
	/**
	 * get the unsolved consult in the Consult table
	 * @return
	 */
	public List getUnsolvedConsult();
	
	/**
	 * get userName by userId
	 * @param userId
	 * @return
	 */
	public String getUserNameById(Integer userId);
	
	/**
	 * get the reply by the consultId
	 * if the consultid has the reply then return its reply
	 * if the consultid does not have the reply return null
	 * @param consultId
	 * @return
	 */
	public Reply getReplybyConsultId(Integer consultId);
	
	/**
	 * get all the consult list order by the backup1 which means whether it is replied
	 * 
	 * @return the sorted consult list 
	 */
	public List getAllConsultOrder();
}
