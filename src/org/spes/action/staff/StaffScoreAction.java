package org.spes.action.staff;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.spes.bean.StaffParam;
import org.spes.service.staff.StaffScoreService;

public class StaffScoreAction {

	private StaffScoreService staffScoreService = null;
	private String jsonData = null;

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public void setStaffScoreService(StaffScoreService staffScoreService) {
		this.staffScoreService = staffScoreService;
	}

	public void saveStaffItemScore() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONObject jsonObject = JSONObject.fromObject(jsonData);

		JSONArray jsonArray = jsonObject.getJSONArray("staffItem");
		
		JSONObject jsonObj = jsonObject.getJSONObject("secondItemId");
		Integer itemId = (Integer) jsonObj.get("itemId");
		Integer userId = (Integer) jsonObj.get("userId");

		Set<StaffParam> set = new HashSet<StaffParam>();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject json = (JSONObject) jsonArray.get(i);
			StaffParam sp = (StaffParam) JSONObject.toBean(json, StaffParam.class);
			sp.setUserId(userId);
			if (null != sp) {
				set.add(sp);
			}
		}

		staffScoreService.saveStaffParamScore(set);
		try {
			staffScoreService.evaluateStaffItemScore(set, itemId);
		} catch (Exception e) {
			response.getWriter().write(e.getMessage());
		}
		response.getWriter().write("考核成功！");
		response.getWriter().flush();
	}
	
	
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
	/**
	 * save staffitem score results
	 * @throws IOException
	 */
	public void saveStaffItemScore2() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONObject jsonObject = JSONObject.fromObject(jsonData);
		Map<String,List<JSONObject>> staffScoreMap = new HashMap<String,List<JSONObject>>();
		JSONArray jsonArray = jsonObject.getJSONArray("staffItem");//全部一级指标下的指标
		Integer itemId = Integer.valueOf(request.getParameter("itemId"));//1级指标Id
		Integer userId = Integer.valueOf(request.getParameter("userId"));
		String sheetId = request.getParameter("sheetId");
		String sheetType = request.getParameter("sheetType");
		// get username from session
		Map<String, Object> session = ServletActionContext.getContext().getSession();
		String username = "";
		if (session.containsKey("userName")) {
			username = (String) session.get("userName");
		}
		String sheetTYpe = request.getParameter("checkType");
		String sheetName = request.getParameter("sheetName");
		sheetName = username + "&" + getDate() + "&" + sheetName;
		
		
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObj = (JSONObject) jsonArray.get(i);
			String itemType = jsonObj.getString("itemType");
			
			if (staffScoreMap.containsKey(itemType)) {
				List<JSONObject> jlist = staffScoreMap.get(itemType);
				jlist.add(jsonObj);
				staffScoreMap.put(itemType, jlist);
			} else {
				List<JSONObject> jlist = new ArrayList<JSONObject>();
				jlist.add(jsonObj);
				staffScoreMap.put(itemType, jlist);
			}
		}
		
		staffScoreService.evaluateStaffItemScore(staffScoreMap,itemId,userId,sheetType,sheetId);
		response.getWriter().flush();
	}
}
