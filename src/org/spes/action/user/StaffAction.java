package org.spes.action.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.spes.bean.User;
import org.spes.service.user.UserService;

public class StaffAction {
	private Integer windowId;
	private Integer centerId;
	private Integer start;// 起始下标
	private Integer limit; // 页面显示条数
	private UserService userService = null;

	
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

	
	public Integer getWindowId() {
		return windowId;
	}

	public void setWindowId(Integer windowId) {
		this.windowId = windowId;
	}

	public Integer getCenterId() {
		return centerId;
	}

	public void setCenterId(Integer centerId) {
		this.centerId = centerId;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void getUserByWindowIdAndCenterId() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		List<User> userlist = userService.getUserOfWindow(windowId, centerId);
		if (null != userlist && userlist.size() > 0) {
//			List<User> sublist = null;
//			if ((limit + start) > userlist.size()) {
//				sublist = userlist.subList(start, userlist.size());
//			} else {
//				sublist = userlist.subList(start, limit + start);
//			}
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = null;
			for (User user : userlist) {
				jsonObject = new JSONObject();
				jsonObject.put("userId", user.getUserId());
				jsonObject.put("userName", user.getUserName());
				jsonObject.put("realName", user.getBackup2());
				jsonObject.put("windowId", user.getWindow().getWindowId());
				jsonObject.put("centerId", user.getServiceCenter().getCenterId());
				jsonObject.put("windowName", user.getWindow().getWindowName());
				jsonArray.add(jsonObject);
			}
			
//			JSONObject json = new JSONObject();
//			json.put("total", userlist.size());
//			json.put("root", jsonArray.toString());
			jsonArray.write(response.getWriter());
			jsonArray.clear();
		} 
		response.getWriter().flush();
	}

}
