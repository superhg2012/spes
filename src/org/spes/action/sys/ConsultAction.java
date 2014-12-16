package org.spes.action.sys;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.spes.bean.Consult;
import org.spes.bean.Notice;
import org.spes.common.DateUtil;
import org.spes.service.sys.ConsultService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * The action of consultAction
 * 
 * @author WuRuihong
 * @version 1.0 2013.0711
 * 
 */
public class ConsultAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer consultId; // 咨询id
	private String consultTitle; // 咨询时间
	private String consultContent; // 咨询内容
	private Integer userId; // 咨询者
	private Integer start;// 起始下标
	private Integer limit; // 页面显示条数

	private ConsultService consultService; // 咨询服务

	/**
	 * @return the consultId
	 */
	public Integer getConsultId() {
		return consultId;
	}

	/**
	 * @param consultId
	 *            the consultId to set
	 */
	public void setConsultId(Integer consultId) {
		this.consultId = consultId;
	}

	/**
	 * @return the consultTitle
	 */
	public String getConsultTitle() {
		return consultTitle;
	}

	/**
	 * @param consultTitle
	 *            the consultTitle to set
	 */
	public void setConsultTitle(String consultTitle) {
		this.consultTitle = consultTitle;
	}

	/**
	 * @return the consultContent
	 */
	public String getConsultContent() {
		return consultContent;
	}

	/**
	 * @param consultContent
	 *            the consultContent to set
	 */
	public void setConsultContent(String consultContent) {
		this.consultContent = consultContent;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the start
	 */
	public Integer getStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set
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
	 * @param limit
	 *            the limit to set
	 */
	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	/**
	 * @return the consultService
	 */
	public ConsultService getConsultService() {
		return consultService;
	}

	/**
	 * @param consultService
	 *            the consultService to set
	 */
	public void setConsultService(ConsultService consultService) {
		this.consultService = consultService;
	}

	/**
	 * add a consult
	 * 
	 * @throws IOException
	 */
	public void pubConsult() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		System.out.println(consultTitle);
		System.out.println(consultContent);
		int result = consultService.addConsult(consultTitle, consultContent);
		JSONObject json = new JSONObject();

		if (result > 0) {
			json.put("success", true);
			json.put("resptext", "咨询成功！");
		} else {
			json.put("success", false);
			json.put("resptext", "咨询失败！");
		}

		json.write(response.getWriter());
	}

	/**
	 * get the consult list
	 * 
	 * @throws IOException
	 */
	public void getConsultList() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		String sort = request.getParameter("sort");
		String dir = request.getParameter("dir");
		List<Consult> consultList = consultService.getConsultListByUserOrderBy(
				sort, dir);
		if (consultList.size() > 0) {
			List<Consult> sublist = null;
			if ((limit + start) > consultList.size()) {
				sublist = consultList.subList(start, consultList.size());
			} else {
				sublist = consultList.subList(start, limit + start);
			}

			JSONArray array = new JSONArray();
			for (int i = 0; i < sublist.size(); i++) {
				Consult consult = sublist.get(i);
				String userName = consultService.getUserNameById(consult
						.getUserId());
				JSONObject json = new JSONObject();

				json.put("consultId", consult.getConsultId());
				// json.put(notice.);
				json.put("consultTitle", consult.getConsultTitle());
				json.put("consultContent", consult.getConsultContent());

				json.put("userId", consult.getUserId());

				json.put("userName", userName);
				if (consult.getBackup1().equals("0")) {
					json.put("isReply", "未回复");
				} else {
					json.put("isReply", "已回复");
				}
				json.put("consultTime", DateUtil.TimeStamp2Date(consult
						.getConsultTime()));
				array.add(json);
			}

			JSONObject json = new JSONObject();
			json.put("total", consultList.size());
			json.put("data", array.toString());
			json.write(response.getWriter());
		}
	}

	/**
	 * 按照登陆用户获取咨询列表
	 * 
	 * @throws IOException
	 * 
	 */
	public void getConsultListbyUser() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		Map<String, Object> session = ActionContext.getContext().getSession();
		List<Consult> consultList = consultService
				.getConsultListByUser((Integer) session.get("userId"));
		if (consultList.size() > 0) {
			List<Consult> sublist = null;
			if ((limit + start) > consultList.size()) {
				sublist = consultList.subList(start, consultList.size());
			} else {
				sublist = consultList.subList(start, limit + start);
			}
			// JSONArray array = JSONArray.fromObject(sublist);
			JSONArray array = new JSONArray();
			for (int i = 0; i < sublist.size(); i++) {
				Consult consult = sublist.get(i);
				String userName = consultService.getUserNameById(consult
						.getUserId());

				JSONObject json = new JSONObject();

				json.put("consultId", consult.getConsultId());
				// json.put(notice.);
				json.put("consultTitle", consult.getConsultTitle());
				json.put("consultContent", consult.getConsultContent());

				json.put("userId", consult.getUserId());
				json.put("userName", userName);

				json.put("consultTime", DateUtil.TimeStamp2Date(consult
						.getConsultTime()));
				array.add(json);
			}

			JSONObject json = new JSONObject();
			json.put("total", consultList.size());
			json.put("data", array.toString());
			json.write(response.getWriter());
		}
	}

	public void editConsult() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");

		String id = request.getParameter("consultId");

		JSONObject json = new JSONObject();
		Boolean isOk = true;
		int result = consultService.editConsult(Integer.parseInt(id),
				consultTitle, consultContent);

		if (result < 0) {
			isOk = false;
		}

		if (isOk) {
			json.put("success", true);
		} else {
			json.put("success", false);
		}

		json.write(response.getWriter());
	}

	public void deleteConsult() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		HttpServletRequest request = ServletActionContext.getRequest();
		String consultID = request.getParameter("consultId");
		String result = consultService.deleteConsult(Integer
				.parseInt(consultID));
		JSONObject json = new JSONObject();
		if (result.equals("failure")) {
			json.put("success", false);
			json.put("consultText", "delete failure!");
		} else {
			json.put("success", true);
			json.put("consultText", "delete success!");
		}

		json.write(response.getWriter());
	}

	/**
	 * get the consult by consultId
	 * 
	 * @throws IOException
	 */
	public void getConsultById() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		HttpServletRequest request = ServletActionContext.getRequest();
		String noticeID = request.getParameter("consultId");
		Consult consult = consultService.getConsultById(Integer.parseInt(noticeID));
		JSONObject json = new JSONObject();

		if (consult != null) {
			json.put("consultId", consult.getConsultId());
			json.put("consultTitle", consult.getConsultTitle());
			json.put("consultContent", consult.getConsultContent());
			json.put("userId", consult.getUserId());
			json.put("consultTime",
					DateUtil.TimeStamp2Date(consult.getConsultTime()));
			json.put("success", true);
		} else {
			json.put("success", false);
			json.put("respText", "none notice");
		}

		json.write(response.getWriter());
	}
}
