package org.spes.action.staff;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.spes.bean.StaffItem;
import org.spes.bean.StaffScore;
import org.spes.service.staff.StaffItemService;
import org.spes.service.staff.StaffScoreService;

/**
 * Staff Item Action
 * @author gbhe
 *
 */
public class StaffItemAction {

	private Integer windowId;
	private Integer centerId;
	private Integer itemId;
	private StaffItemService staffItemService = null;
	private StaffScoreService staffScoreService = null;

	public void setStaffScoreService(StaffScoreService staffScoreService) {
		this.staffScoreService = staffScoreService;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
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

	public void setStaffItemService(StaffItemService staffItemService) {
		this.staffItemService = staffItemService;
	}

	public void getStaffItem() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		List<StaffItem> slist = staffItemService.getStaffItemByWinIdAndCenId(windowId, centerId);
		if (null != slist && slist.size() > 0) {
			JSONArray jsonArray = JSONArray.fromObject(slist);
			response.getWriter().write(jsonArray.toString());
		} else {
			response.getWriter().write("指标不存在！");
		}
		response.getWriter().flush();
	}

	/**
	 * get Staff Item of Level One
	 * @throws IOException
	 */
	public void getFirstStaffItem() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		Integer userId = Integer.valueOf(request.getParameter("userId"));
		String sheetId = request.getParameter("sheetId");
		
		boolean existChecked = false;
		if (sheetId != null) {
			existChecked = true;
		}

		List<StaffItem> slist = staffItemService.getStaffItemByIdsAndItemGrade(windowId, centerId, new Integer(1));
		
		if (null != slist && slist.size() > 0) {
			JSONArray jsonArray = new JSONArray();
			for(int index = 0;index <slist.size();index++){
				StaffItem si = slist.get(index);
				JSONObject json = new JSONObject();
				json.put("itemId", si.getItemId());
				json.put("itemName", si.getItemName());
				json.put("itemType", si.getItemType());
				json.put("itemGrade", si.getItemGrade());
				json.put("parentId", si.getParentId());
				json.put("itemWeight", si.getItemWeight());
				if (existChecked) {
					StaffScore ss = staffScoreService.getCheckedStaffItem(si.getItemId(), userId, windowId, centerId, sheetId);
					if (null != ss) {
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
		}
	}
	
	
/*	public void getStaffItemByItemId() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		List<StaffItem> list = staffItemService.getStaffItemByFormula(itemId,
				windowId, centerId);
		if (list != null && list.size() > 0) {
			JSONArray jsonArray = JSONArray.fromObject(list);
			response.getWriter().write(jsonArray.toString());
		} else {
			response.getWriter().write("请求失败！");
		}
		response.getWriter().flush();
	}
	*/
	
	public void getStaffItemByItemId() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		List<StaffItem> item2list = staffItemService.getSonItemByParentId(itemId);//二级指标列表
		JSONArray totalArray = new JSONArray();
		if (null != item2list && item2list.size() > 0) {
			for(StaffItem staffItem : item2list){
				Integer item2Id = staffItem.getItemId();
				String itemType = staffItem.getItemType();
				List<StaffItem> targetlist = null;
				if (!itemType.equals("定性")) {
					targetlist = staffItemService.getStaffItemByFormula(item2Id,
							windowId, centerId); // 三级指标列表
				} else {
					targetlist = new ArrayList<StaffItem>();
					targetlist.add(staffItem);// 定性，不需要计算公式
				}
				if (null != targetlist) {
					// 指标计算公式不为空
					for (StaffItem ci : targetlist) {
						JSONObject jsonObject = JSONObject.fromObject(ci);
						jsonObject.put("pItemName", staffItem.getItemName());
						jsonObject.put("pItemId", staffItem.getItemId());
						jsonObject.put("itemType", staffItem.getItemType());
						jsonObject.put("itemWeight", staffItem.getItemWeight());
						totalArray.add(jsonObject);
					}
				}
			}//end for
			response.getWriter().write(totalArray.toString());
			response.getWriter().flush();
			totalArray.clear();
		} else {
			response.getWriter().write("");//无二级指标
		}
		
	}
	
}
