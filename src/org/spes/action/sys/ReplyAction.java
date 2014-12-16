package org.spes.action.sys;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.spes.bean.Consult;
import org.spes.bean.Notice;
import org.spes.bean.Reply;
import org.spes.common.DateUtil;
import org.spes.service.sys.ReplyService;

import com.opensymphony.xwork2.ActionSupport;

/**
 * The action of reply
 * 
 * @author WuRuihong
 * @version 1.0 2013.07.11
 *
 */
public class ReplyAction extends ActionSupport{
	
	private String replyContent;// reply内容
	private Integer start;//起始下标
	private Integer limit; //页面显示条数
	private String consultId;//咨询 id
	private Integer userId; //咨询者ID
	private ReplyService replyService;
	
	
	/**
	 * @return the replyContent
	 */
	public String getReplyContent() {
		return replyContent;
	}
	/**
	 * @param replyContent the replyContent to set
	 */
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	/**
	 * @return the start
	 */
	public Integer getStart() {
		return start;
	}
	/**
	 * @param start the start to set
	 */
	public void setStart(Integer start) {
		this.start = start;
	}
	/**
	 * @return the limit
	 */
	public Integer getLimit() {
		return limit;
	}
	/**
	 * @param limit the limit to set
	 */
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	/**
	 * @return the consultId
	 */
	public String getConsultId() {
		return consultId;
	}
	/**
	 * @param consultId the consultId to set
	 */
	public void setConsultId(String consultId) {
		this.consultId = consultId;
	}
	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * @return the replyService
	 */
	public ReplyService getReplyService() {
		return replyService;
	}
	/**
	 * @param replyService the replyService to set
	 */
	public void setReplyService(ReplyService replyService) {
		this.replyService = replyService;
	}
	
	/**
	 * get all the consult and order by backup1
	 * @throws IOException 
	 */
	public void getConsultListOrder() throws IOException {
		List<Consult> consultOrderList = replyService.getAllConsultOrder();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		if (consultOrderList.size() > 0) {
			List<Consult> sublist = null;
			if ((limit + start) > consultOrderList.size()) {
				sublist = consultOrderList.subList(start, consultOrderList.size());
			} else {
				sublist = consultOrderList.subList(start, limit + start);
			}
			
			JSONArray array = new JSONArray();
			for(int i=0; i<sublist.size(); i++){
				Consult consult = sublist.get(i);
				String userName = replyService.getUserNameById(consult.getUserId());
				JSONObject json = new JSONObject();
				

				json.put("consultId", consult.getConsultId());
				json.put("consultTitle", consult.getConsultTitle());
				json.put("consultContent", consult.getConsultContent());

				json.put("userId", consult.getUserId());
				json.put("userName", userName);
				if (consult.getBackup1().equals("0")) {
					json.put("isReply", "未回复");
				} else {
					json.put("isReply", "已回复");
				}
				json.put("consultTime", DateUtil.TimeStamp2Date(consult.getConsultTime()));
				array.add(json);
			}
			
			JSONObject json = new JSONObject();
			json.put("total", consultOrderList.size());
			json.put("data", array.toString());
			json.write(response.getWriter());
		}
	}
	/**
	 * 获取未解答的咨询列表
	 * @throws IOException 
	 */
	public void getConsultUnsolvedList() throws IOException {
		List<Consult> replyUnsolvedList = replyService.getUnsolvedConsult();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		if (replyUnsolvedList.size() > 0) {
			List<Consult> sublist = null;
			if ((limit + start) > replyUnsolvedList.size()) {
				sublist = replyUnsolvedList.subList(start, replyUnsolvedList.size());
			} else {
				sublist = replyUnsolvedList.subList(start, limit + start);
			}
			
			JSONArray array = new JSONArray();
			for(int i=0;i<sublist.size();i++){
				Consult consult = sublist.get(i);
				String userName = replyService.getUserNameById(consult.getUserId());
				JSONObject json = new JSONObject();

				json.put("consultId", consult.getConsultId());
				json.put("consultTitle", consult.getConsultTitle());
				json.put("consultContent", consult.getConsultContent());

				json.put("userId", consult.getUserId());
				json.put("userName", userName);
				if (consult.getBackup1().equals("0"))
				json.put("consultTime", DateUtil.TimeStamp2Date(consult.getConsultTime()));
				array.add(json);
			}
			
			JSONObject json = new JSONObject();
			json.put("total", replyUnsolvedList.size());
			json.put("data", array.toString());
			json.write(response.getWriter());
		}
	}
	
	/**
	 * 根据未解决的consultId进行回复
	 * @throws IOException 
	 *  
	 */
	public void relpyByConsultId() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		//Integer consultId = Integer.parseInt(request.getParameter("consultId"));
	//	Integer userId = Integer.parseInt(request.getParameter("userId"));
		int result = replyService.addReply(replyContent, consultId);
		
		JSONObject json = new JSONObject();
		if (result > 0) {
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		
		
		json.write(response.getWriter());
	}
	
	/**
	 * get the reply by consultId
	 * if it has the reply then get the reply
	 * if it does not have the reply, then return have not been solved.
	 * @throws IOException 
	 */
	public void getReplyByConsultId() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		int cid = Integer.parseInt(request.getParameter("consultId"));
		
		JSONObject json = new JSONObject();
		Reply reply = replyService.getReplybyConsultId(cid);
		if (reply != null) {
			json.put("replyContent", reply.getReplyContent());
			DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String dateStr = sdf.format(reply.getReplyTime());
			json.put("replyTime", dateStr);
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		json.write(response.getWriter());
	}
}
