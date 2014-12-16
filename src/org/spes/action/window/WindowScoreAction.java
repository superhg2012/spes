package org.spes.action.window;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.spes.service.window.WindowScoreService;

/**
 * 窗口考核得分
 * 
 * @author Hegang
 * 
 */
public class WindowScoreAction {

	private String jsonData = null;
	private WindowScoreService windowScoreService = null;

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public WindowScoreService getWindowScoreService() {
		return windowScoreService;
	}

	public void setWindowScoreService(WindowScoreService windowScoreService) {
		this.windowScoreService = windowScoreService;
	}

	/*public void saveWindowItemScore() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");

		JSONObject json = JSONObject.fromObject(jsonData);
		JSONArray array = json.getJSONArray("windowItem");
		JSONObject jsonObj = json.getJSONObject("secondItemId");
		Integer itemId = (Integer)jsonObj.get("itemId"); //get itemId
		
		Set<WindowParam> set = new HashSet<WindowParam>();
		
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = (JSONObject) array.get(i);
			WindowParam wp = (WindowParam) JSONObject.toBean(jsonObject, WindowParam.class);
			if (null !=wp) {
				set.add(wp);
			}
		}
		windowScoreService.saveWindowParamScore(set);
		windowScoreService.evaluateWindoItemScore(set, itemId);
		json = new JSONObject();
		json.put("result", "success");
		response.getWriter().write(json.toString());
		response.getWriter().flush();
	}*/
	
	
	/**
	 * get current date
	 * 
	 * @return
	 */
	public String getDate() {
		Calendar calender = Calendar.getInstance();
		SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-DD");
		return sformat.format(calender.getTime());
	}
	
	public void saveWindowItemScore() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setCharacterEncoding("GBK");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		// get username from session
		Map<String, Object> session = ServletActionContext.getContext().getSession();
		String username = "";
		if (session.containsKey("userName")) {
			username = (String) session.get("userName");
		} else {
			return;
		}
		
		JSONObject jsonObject = JSONObject.fromObject(jsonData);
		JSONArray jsonArray = jsonObject.getJSONArray("windowItem");//全部一级指标下的指标
		Integer itemId = Integer.valueOf(request.getParameter("itemId"));//1级指标Id
		String checkType = request.getParameter("checkType");
		String sheetName = request.getParameter("sheetName");
		String sheetId = request.getParameter("sheetId");
		String sheetType = request.getParameter("sheetType");
		
		sheetName = username + "&" + getDate() + "&" + sheetName;
		
		Map<String, List<JSONObject>> map = new HashMap<String, List<JSONObject>>();
		
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObj = (JSONObject) jsonArray.get(i);
			String itemType = jsonObj.getString("itemType");
			
			if (map.containsKey(itemType)) {
				List<JSONObject> jlist = map.get(itemType);
				jlist.add(jsonObj);
				map.put(itemType, jlist);
			} else {
				List<JSONObject> jlist = new ArrayList<JSONObject>();
				jlist.add(jsonObj);
				map.put(itemType, jlist);
			}
		}
		//二级定性指标考核
		windowScoreService.evaluateWindowItemScore(map,itemId,sheetType,sheetName, sheetId);
		response.getWriter().flush();
	}
}
