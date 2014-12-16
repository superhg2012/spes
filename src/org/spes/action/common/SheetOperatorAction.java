package org.spes.action.common;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.spes.bean.Evaluatesheet;
import org.spes.common.AssessUtil;
import org.spes.common.Constants;
import org.spes.service.common.EvaluateSheetService;
/**
 * 考核表单操作类
 * 
 * @author gbhe
 *
 */
public class SheetOperatorAction {

	private Integer start;
	private Integer limit;
	private EvaluateSheetService evaluateSheetService = null;

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

	public void setEvaluateSheetService(
			EvaluateSheetService evaluateSheetService) {
		this.evaluateSheetService = evaluateSheetService;
	}

	/**
	 * get Evaluated sheets according to sheetType
	 * @throws IOException
	 */
	public void getHisEvaluateSheets() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("GBK");
		
		String checkType = request.getParameter("checkType");
		String sheetState = request.getParameter("sheetState");
		
		List<Evaluatesheet> list = evaluateSheetService.getEvaluateSheetList(checkType, AssessUtil.getSheetState(sheetState));
		if (list == null || list.isEmpty()) {
			JSONObject json = new JSONObject();
			json.put("total", 0);
			response.getWriter().write("");
			return;
		}
		
		List<Evaluatesheet> sublist = null;
		if ((limit + start) > list.size()) {
			sublist = list.subList(start, list.size());
		} else {
			sublist = list.subList(start, limit + start);
		}
		JSONArray jsonArray = JSONArray.fromObject(sublist);

		JSONObject json = new JSONObject();
		json.put("total", list.size());
		json.put("data", jsonArray.toString());
		response.getWriter().write(json.toString());
		jsonArray.clear();
	}
	
	/**
	 * query sheet info according to sheetId
	 * @throws IOException
	 */
	public void getEvaluateSheets() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");

		String sheetId = request.getParameter("sheetId");

		List<Evaluatesheet> list = evaluateSheetService.getEvaluateSheet(Integer.valueOf(sheetId));
		JSONObject json = new JSONObject();

		if (list != null && !list.isEmpty()) {
			Evaluatesheet es = list.get(0);
			json = JSONObject.fromObject(es);
			response.getWriter().write(json.toString());
		} else {
			response.getWriter().write("单号为[" + sheetId + "]的考核表单不存在！");
		}
	}
	
	/**
	 * crate new evaluate sheet
	 * @throws IOException 
	 * 
	 */
	public void createEvaluateSheet() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String,Object> session = ServletActionContext.getContext().getSession();
		String userName = (String)session.get("userName");
		if(userName == null || userName.equals("")) {
			return;
		}
		String sheetName = request.getParameter("sheetName");
		String sheetType = request.getParameter("sheetType"); //center
		String checkType = request.getParameter("checkType"); //month,quarter,year
		String sheetState = Constants.SHEETSTATE_NOT_START;//default to not start evaluate
		boolean error =  evaluateSheetService.createNewEvaluateSheet(sheetName, userName, sheetState, sheetType, checkType);
		JSONObject json = new JSONObject();
		if (!error) {
			json.put("result", true);
		} else {
			json.put("result", false);
		}
		response.getWriter().write(json.toString());
	}
	
	/**
	 * 1 delete sheet according to specific sheetId
	 * 2 delete all parameters associated with the sheet to be deleted
	 * @throws IOException
	 */
	public void deleteEvaluateSheet() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		
		final String sheetId = request.getParameter("sheetId");
		
		boolean error = evaluateSheetService.deleteEvaluateSheet(sheetId);
		JSONObject json = new JSONObject();
		if(!error) {
			json.put("result", true);
		} else {
			json.put("result", false);
		}
		response.getWriter().write(json.toString());
	}
}
