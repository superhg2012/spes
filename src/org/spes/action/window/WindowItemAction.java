package org.spes.action.window;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.spes.bean.WindowItem;
import org.spes.bean.WindowScore;
import org.spes.constants.SessionConstants;
import org.spes.service.window.WindowItemService;
import org.spes.service.window.WindowScoreService;

import com.opensymphony.xwork2.ActionContext;

/**
 * 窗口指标Action
 * 
 * @author HeGang
 * 
 */
public class WindowItemAction {
	private Integer itemId;
	private Integer parentId;
	private Integer centerId;
	private Integer windowId;
	private WindowItemService windowItemService = null;
	private WindowScoreService windowScoreService = null;

	public WindowScoreService getWindowScoreService() {
		return windowScoreService;
	}

	public void setWindowScoreService(WindowScoreService windowScoreService) {
		this.windowScoreService = windowScoreService;
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

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getWindowId() {
		return windowId;
	}

	public void setWindowId(Integer windowId) {
		this.windowId = windowId;
	}

	public WindowItemService getWindowItemService() {
		return windowItemService;
	}

	public void setWindowItemService(WindowItemService windowItemService) {
		this.windowItemService = windowItemService;
	}

	/**
	 * 获取窗口指标,生成窗口评价指标树
	 * 
	 * @throws IOException
	 */
	public void getFirstAndSecondWindowItem() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		Map<String, Object> session = ActionContext.getContext().getSession();
		Integer centerId = Integer.valueOf((String) session.get(SessionConstants.CENTER_ID));
		List<WindowItem> wlist = windowItemService.getWindowItems(centerId);

		if (null != wlist && wlist.size() > 0) {
			JSONArray jsonArr = JSONArray.fromObject(wlist);
			response.getWriter().write(jsonArr.toString());
			response.getWriter().flush();
		}

	}

	/**
	 * 获取窗口二级指标的子指标
	 * 
	 * @throws IOException
	 */
	public void getWindowThirdParam() throws IOException {
		// HttpServletResponse response = ServletActionContext.getResponse();
		// response.setCharacterEncoding("UTF-8");
		// response.setContentType("text/json");
		//		
		// List<WindowItem> wlist =
		// windowItemService.getWindowItemsOfGradeTwo(itemId);
		// if (null != wlist && wlist.size() > 0) {
		// JSONArray jsonArr = JSONArray.fromObject(wlist);
		// response.getWriter().write(jsonArr.toString());
		// response.getWriter().flush();
		// }
	}

	/**
	 * 根据指标评价公式解析获取三级指标参数
	 * 
	 * @throws IOException
	 */
	public void getWindowItemByItemId() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");

		List<WindowItem> wlist = windowItemService.getWindowItemsByFormula(itemId, centerId);

		if (null != wlist && wlist.size() > 0) {
			JSONArray jsonArr = JSONArray.fromObject(wlist);
			response.getWriter().write(jsonArr.toString());
			response.getWriter().flush();
		}
	}

	/**
	 * 获取待评价指标
	 * @throws IOException 
	 */
	public void getWindowItemByItemId2() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		List<WindowItem> wlist = windowItemService.getSonItemByParentId(itemId, centerId);//二级指标
//		System.out.println("[2]" + wlist.toString());
		JSONArray totalArray = new JSONArray();
		if (wlist != null && wlist.size() > 0) {
			for(WindowItem windowItem : wlist){
				Integer itemId = windowItem.getItemId();
				String itemType = windowItem.getItemType();
				List<WindowItem> targetlist = null;
				if(!itemType.equals("定性")){
					targetlist = windowItemService.getWindowItemsByFormula(itemId, centerId);	//三级指标列表
				} else {
					targetlist = new ArrayList<WindowItem>();
					targetlist.add(windowItem);//定性，不需要计算公式
				}
				if (null !=targetlist) {
					//指标计算公式不为空
					for(WindowItem wi : targetlist){
						JSONObject jsonObject = JSONObject.fromObject(wi);
						jsonObject.put("pItemName", windowItem.getItemName());
						jsonObject.put("pItemId", windowItem.getItemId());
						jsonObject.put("itemType", windowItem.getItemType());
						jsonObject.put("itemWeight", windowItem.getItemWeight());
						totalArray.add(jsonObject);
					}
				}
			}//end for
			response.getWriter().write(totalArray.toString());
			response.getWriter().flush();
			totalArray.clear();
		} else {
			response.getWriter().write("");
		}
	}
	
	/**
	 * 获取窗口一级指标
	 * hegang
	 * @throws IOException
	 */
	public void getFirstWindowItem() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/json");
		
		String sheetId = request.getParameter("sheetId");
		String sheetName = request.getParameter("sheetName");

		boolean existChecked = false;

		if (sheetId != null && sheetName != null) {
			existChecked = true;
		}
		
		List<WindowItem> wlist = windowItemService.getWindowItemsOfFirstGrade(centerId, new Integer(1));

		if (null != wlist && wlist.size() > 0) {
			JSONArray jsonArray = new JSONArray();
			for (int index = 0; index < wlist.size(); index++) {
				WindowItem wi = wlist.get(index);
				JSONObject json = new JSONObject();
				json.put("itemId", wi.getItemId());
				json.put("itemName", wi.getItemName());
				json.put("itemType", wi.getItemType());
				json.put("itemGrade", wi.getItemGrade());
				json.put("parentId", wi.getParentId());
				json.put("itemWeight", wi.getItemWeight());
				
				if(existChecked){
					WindowScore ws = windowScoreService.getCheckedWindowItems(wi.getItemId(), windowId, centerId, sheetId);
					if (null != ws) {
						json.put("checked", true);
					} else {
						json.put("checked", false);
					}
				} else {
					json.put("checked", false);
				}
				
				jsonArray.add(json);
			}
			response.getWriter().write(jsonArray.toString());
			response.getWriter().flush();
			jsonArray.clear();
		} else {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("result", "窗口评价指标未设置！");
			jsonObject.write(response.getWriter());
		}
	}

}
