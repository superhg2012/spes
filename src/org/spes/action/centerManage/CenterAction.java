package org.spes.action.centerManage;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.spes.bean.ServiceCenter;
import org.spes.service.center.CenterService;

public class CenterAction {
	
	private CenterService centerService;
	private Integer start;
	private Integer limit;

	
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


	public CenterService getCenterService() {
		return centerService;
	}

	public void setCenterService(CenterService centerService) {
		this.centerService = centerService;
	}

	/**
	 * 获取所有中心列表
	 * 
	 * @throws IOException
	 */
	public void getServiceCenter() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		List<ServiceCenter> list = centerService.getAllCenterService();
		if (null != list && list.size() > 0) {
			JSONArray jsonArray = new JSONArray();
			for (int index = 0; index < list.size(); index++) {
				ServiceCenter sc = list.get(index);
				JSONObject json = new JSONObject();
				json.put("centerId", sc.getCenterId());
				json.put("centerName", sc.getCenterName());
				jsonArray.add(json);
			}
			JSONObject json = new JSONObject();
			json.put("root", jsonArray.toString());
			response.getWriter().write(json.toString());
			response.getWriter().flush();
		}
	}
	
	/**
	 * 分页获取
	 * @throws IOException 
	 */
	public void getServiceCenter2() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		List<ServiceCenter> list = centerService.getAllCenterService();
		if (null != list && list.size() > 0) {
			List<ServiceCenter> sublist = null;
			if ((limit + start) > list.size()) {
				sublist = list.subList(start, list.size());
			} else {
				sublist = list.subList(start, limit + start);
			}
			
			JSONArray jsonArray = new JSONArray();
			for (int index = 0; index < sublist.size(); index++) {
				ServiceCenter sc = sublist.get(index);
				JSONObject json = new JSONObject();
				json.put("centerId", sc.getCenterId());
				json.put("centerName", sc.getCenterName());
				json.put("province", sc.getProvince());
				json.put("city", sc.getCity());
				jsonArray.add(json);
			}
			JSONObject json = new JSONObject();
			json.put("root", jsonArray.toString());
			json.put("total", list.size());
			response.getWriter().write(json.toString());
			response.getWriter().flush();
		}
	}
}
