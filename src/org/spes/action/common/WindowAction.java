package org.spes.action.common;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.spes.bean.User;
import org.spes.bean.Window;
import org.spes.service.result.WindowService;
import org.spes.service.user.UserService;

import com.opensymphony.xwork2.ActionContext;

/**
 * 窗口Action
 * 
 * @author Hegang
 * 
 */
public class WindowAction {

	private Integer centerId;
	private Integer start;
	private Integer limit;
	private UserService userService;
	private WindowService windowService = null;
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

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

	public Integer getCenterId() {
		return centerId;
	}

	public void setCenterId(Integer centerId) {
		this.centerId = centerId;
	}

	public WindowService getWindowService() {
		return windowService;
	}

	public void setWindowService(WindowService windowService) {
		this.windowService = windowService;
	}

	/**
	 * 根据中心ID获取窗口信息
	 * hegang 未分页
	 * @throws IOException
	 */
	public void getWindowByCenterId() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		List<Window> wlist = windowService.getWindowsOfCenter(centerId);

		if (wlist != null && wlist.size() > 0) {
			JSONArray array = new JSONArray();
			JSONObject json = null;
			for (Window window : wlist) {
				json = new JSONObject();
				json.put("windowId", window.getWindowId());
				json.put("windowName", window.getWindowName());
				json.put("windowState", window.getBackup1());
				json.put("centerId", window.getServiceCenter().getCenterId());
				json.put("windowBussiness", window.getWindowBussiness());
				array.add(json);
			}
			json = new JSONObject();
			json.put("root", array.toString());
			json.write(response.getWriter());
		} else {
			response.getWriter().write("尚不存在窗口数据！");
		}
		response.getWriter().flush();
	}
	
	/**
	 * 获取单个窗口Obejct
	 * @throws IOException 
	 */
	public void getWindowOfCenter() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		
		HttpServletRequest request = ServletActionContext.getRequest();
		Integer centerId = Integer.valueOf(request.getParameter("centerId"));
		Map<String, Object>  session = ActionContext.getContext().getSession();
		Integer userId = (Integer)session.get("userId");
		User user = userService.getUserById(userId);
		Window window = user.getWindow();
		JSONArray jsonArray = new JSONArray();
		JSONObject json = new JSONObject();
		json.put("windowId", window.getWindowId());
		json.put("windowName", window.getWindowName());
		json.put("windowState", window.getBackup1());
		json.put("centerId", window.getServiceCenter().getCenterId());
		json.put("windowBussiness", window.getWindowBussiness());
		jsonArray.add(json);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("root",jsonArray.toString());
		response.getWriter().write(jsonObj.toString());
		response.getWriter().flush();
	}
	
	
	/**
	 * 分页
	 * hegang
	 * @throws IOException
	 */
	public void getWindowListByCenterId() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		List<Window> wlist = windowService.getWindowsOfCenter(centerId);
		
		if(wlist.size()>0){
			List<Window> sublist = null;
			if ((limit + start) > wlist.size()) {
				sublist = wlist.subList(start, wlist.size());
			} else {
				sublist = wlist.subList(start, limit + start);
			}
			JSONArray array = new JSONArray();
			JSONObject json = null;
			for (Window window : sublist) {
				json = new JSONObject();
				json.put("windowId", window.getWindowId());
				json.put("windowName", window.getWindowName());
				json.put("windowState", window.getBackup1());
				json.put("centerId", window.getServiceCenter().getCenterId());
				json.put("windowBussiness", window.getWindowBussiness());
				json.put("windowDesc", window.getWindowDesc());
				array.add(json);
			}
			json = new JSONObject();
			json.put("total", wlist.size());
			json.put("data", array.toString());
			json.write(response.getWriter());
		}
	}

	/**
	 * 增加或修改窗口信息
	 */
	public void addOrUpdateWindow(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json");
		response.setCharacterEncoding("Utf-8");
		String jsonData = request.getParameter("jsonData");
		JSONObject json = JSONObject.fromObject(jsonData);
		JSONArray array = json.getJSONArray("window");
		System.out.println(jsonData);
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		String centerId = (String)session.get("centerId");
		System.out.println("tt");
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObj = array.getJSONObject(i);
			String windowId = jsonObj.getString("windowId");
			String windowName = jsonObj.getString("windowName");
			String windowBussiness = jsonObj.getString("windowBussiness");
			String windowDesc = jsonObj.getString("windowDesc");
			
			if(windowId.equals("")){
				//create
				windowService.addWindow(windowName, windowBussiness, windowDesc, Integer.valueOf(centerId));
			} else{
				//update
				Window window = windowService.findWindowById(Integer.parseInt(windowId));
				window.setWindowName(windowName);
				window.setWindowBussiness(windowBussiness);
				window.setWindowDesc(windowDesc);
				windowService.updateWindow(window);
			}
		}
	}

}
