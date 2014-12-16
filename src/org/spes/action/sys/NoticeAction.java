package org.spes.action.sys;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.spes.bean.Notice;
import org.spes.common.DateUtil;
import org.spes.service.sys.NoticeService;

import com.opensymphony.xwork2.ActionSupport;
/**
 * 
 * 系统公告
 * 
 * @author HeGang
 * 
 */
public class NoticeAction extends ActionSupport {

	/**
	 * generated serialVersion
	 */
	private static final long serialVersionUID = 684469262153932019L;
	private String title;// 标题
	private String content;// 内容
	private Integer start;//起始下标
	private Integer limit; //页面显示条数
	private String noticeID;//notice id

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	private NoticeService noticeService = null;

	public NoticeService getNoticeService() {
		return noticeService;
	}

	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the noticeID
	 */
	public String getNoticeID() {
		return noticeID;
	}

	/**
	 * @param noticeID the noticeID to set
	 */
	public void setNoticeID(String noticeID) {
		this.noticeID = noticeID;
	}

	/**
	 * check the notice send by others not himself in his center
	 * @throws IOException 
	 */
	public void checkNotice() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		String sort= request.getParameter("sort");
		String dir= request.getParameter("dir");
		List<Notice> noticeList = noticeService.checkNoticeOrderBy(sort, dir);
		if (noticeList.size() > 0) {
			List<Notice> sublist = null;
			if ((limit + start) > noticeList.size()) {
				sublist = noticeList.subList(start, noticeList.size());
			} else {
				sublist = noticeList.subList(start, limit + start);
			}
			JSONArray array = new JSONArray();
			
			for(int i=0;i<sublist.size();i++){
				Notice notice = sublist.get(i);
				String userName = noticeService.getUserNameByUserId(notice.getUserId());
				JSONObject json = new JSONObject();

				json.put("noticeId", notice.getNoticeId());
				json.put("noticeTitle", notice.getNoticeTitle());
				json.put("noticeContent", notice.getNoticeContent());
				json.put("noticeID", notice.getNoticeId());
				json.put("title", notice.getNoticeTitle());
				json.put("content", notice.getNoticeContent());
				json.put("userId", notice.getUserId());
				json.put("userName", userName);
				json.put("noticeTime", DateUtil.TimeStamp2Date(notice.getNoticeTime()));
				array.add(json);
			}
	
			JSONObject json = new JSONObject();
			json.put("total", noticeList.size());
			json.put("data", array.toString());
			json.write(response.getWriter());
		}
	}
	
	
	
	/**
	 * get all the notice by user and sort by noticeTime 
	 * and A user can see his own published notice
	 * 
	 * @throws IOException
	 */
	public void getNoticeList() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		String sort= request.getParameter("sort");
		String dir= request.getParameter("dir");
		List<Notice> noticeList = noticeService.getNoticeOrderBy(sort, dir);
		if (noticeList.size() > 0) {
			List<Notice> sublist = null;
			if ((limit + start) > noticeList.size()) {
				sublist = noticeList.subList(start, noticeList.size());
			} else {
				sublist = noticeList.subList(start, limit + start);
			}
			JSONArray array = new JSONArray();
			
			for(int i=0;i<sublist.size();i++){
				Notice notice = sublist.get(i);
				String userName = noticeService.getUserNameByUserId(notice.getUserId());
				JSONObject json = new JSONObject();

				json.put("noticeId", notice.getNoticeId());
				json.put("noticeTitle", notice.getNoticeTitle());
				json.put("noticeContent", notice.getNoticeContent());
				json.put("noticeID", notice.getNoticeId());
				json.put("title", notice.getNoticeTitle());
				json.put("content", notice.getNoticeContent());
				json.put("userId", notice.getUserId());
				json.put("userName", userName);
				json.put("noticeTime", DateUtil.TimeStamp2Date(notice.getNoticeTime()));
				array.add(json);
			}
			
			JSONObject json = new JSONObject();
			json.put("total", noticeList.size());
			json.put("data", array.toString());
			json.write(response.getWriter());
		}
	}

	/**
	 * add a notice 
	 * 
	 * @throws IOException
	 */
	public void pubNotice() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		int result = noticeService.addNotice(title, content);
		JSONObject json = new JSONObject();

		if (result > 0) {
			json.put("success", true);
			json.put("respText", "公告发布成功！");
		} else {
			json.put("success", false);
			json.put("respText", "公告发布失败！");
		}

		json.write(response.getWriter());
	}
	
	/**
	 * edit the notice
	 * 
	 * @throws IOException
	 */
	public void editNotice() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		String id = request.getParameter("noticeId");
		
		JSONObject json = new JSONObject();
		Boolean isOk = true;
		int result = noticeService.editNotice(Integer.parseInt(id), title, content);
		
		if (result < 0) {
				isOk = false;
		}
	
		if (isOk ) {
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		json.write(response.getWriter());
	}
	
	/**
	 * delete the notice
	 * 
	 * @throws IOException
	 */
	public void deleteNotice() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		HttpServletRequest request = ServletActionContext.getRequest();
		String noticeID = request.getParameter("noticeId");
		String result = noticeService.deleteNotice(Integer.parseInt(noticeID));
		JSONObject json = new JSONObject();
		
		if (result.equals("failure")) {
			json.put("success", false);
			json.put("respText", "delete failure!");
		} else {
			json.put("success", true);
			json.put("respText", "delete success!");
		}
		json.write(response.getWriter());
	}
	
	/**
	 * get the notice by noticeId
	 * 
	 * @throws IOException
	 */
	public void getNoticeById() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		HttpServletRequest request = ServletActionContext.getRequest();
		String noticeID = request.getParameter("noticeId");
		System.out.println(noticeID);
		Notice notice = noticeService.getNoticeById(Integer.parseInt(noticeID));
		System.out.println(notice.getNoticeId());
		JSONObject json = new JSONObject();
		
		if (notice != null) {
			String userName = noticeService.getUserNameByUserId(notice.getUserId());
			json.put("noticeId", notice.getNoticeId());
			json.put("noticeTitle", notice.getNoticeTitle());
			json.put("noticeContent", notice.getNoticeContent());
			json.put("userId", notice.getUserId());
			json.put("userName", userName);
			json.put("noticeTime", DateUtil.TimeStamp2Date(notice.getNoticeTime()));
			json.put("success", true);
		} else {
			json.put("success", false);
			json.put("respText", "none notice");
		}
		
		json.write(response.getWriter());
	}
}
