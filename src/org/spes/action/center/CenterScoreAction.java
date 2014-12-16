package org.spes.action.center;

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
import org.spes.service.center.CenterScoreService;

public class CenterScoreAction {
	private Integer centerId;
	private Integer itemId;
	private String jsonData;

	private CenterScoreService centerScoreService = null;

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public Integer getCenterId() {
		return centerId;
	}

	public void setCenterId(Integer centerId) {
		this.centerId = centerId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public void setCenterScoreService(CenterScoreService centerScoreService) {
		this.centerScoreService = centerScoreService;
	}

	public void evaluate() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		centerScoreService.evaluate(itemId, centerId);
		response.getWriter().write("true");
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

	public void saveCenterItemScore() throws IOException {
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
		}

		JSONObject jsonObject = JSONObject.fromObject(jsonData);
		JSONArray jsonArray = jsonObject.getJSONArray("centerItem");// 全部一级指标下的指标

		Integer itemId = Integer.valueOf(request.getParameter("itemId"));// 1级指标Id

		String sheetType = request.getParameter("sheetType");
		String sheetName = request.getParameter("sheetName");
		String sheetId = request.getParameter("sheetId");
		Integer intSheetId = null;
		if(sheetId != null){
			intSheetId = Integer.valueOf(sheetId);
		}
		
		
		//new sheet name
		sheetName = username + "&" + getDate() + "&" + sheetName;
		
		Map<String, List<JSONObject>> itemScoreMap = new HashMap<String, List<JSONObject>>();

		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObj = (JSONObject) jsonArray.get(i);
			String itemType = jsonObj.getString("itemType");

			if (itemScoreMap.containsKey(itemType)) {
				List<JSONObject> jlist = itemScoreMap.get(itemType);
				jlist.add(jsonObj);
				itemScoreMap.put(itemType, jlist);
			} else {
				List<JSONObject> jlist = new ArrayList<JSONObject>();
				jlist.add(jsonObj);
				itemScoreMap.put(itemType, jlist);
			}

		}
		// 二级定性指标考核
		centerScoreService.evaluateCenterItemScore(itemScoreMap, itemId, sheetType, intSheetId);
		response.getWriter().flush();
		itemScoreMap.clear();
	}
}
