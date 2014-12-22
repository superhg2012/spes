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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.spes.bean.CenterItem;
import org.spes.bean.CenterParam;
import org.spes.bean.CenterScore;
import org.spes.service.center.CenterItemService;
import org.spes.service.center.CenterParamService;
import org.spes.service.center.CenterScoreService;

public class CenterScoreAction {
	private Integer centerId;
	private Integer itemId;
	private Integer sheetId;
	private String jsonData;

	private CenterScoreService centerScoreService = null;
	private CenterItemService centerItemService = null;
	private CenterParamService centerParamService = null;

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
	public Integer getSheetId() {
		return sheetId;
	}

	public void setSheetId(Integer sheetId) {
		this.sheetId = sheetId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public void setCenterScoreService(CenterScoreService centerScoreService) {
		this.centerScoreService = centerScoreService;
	}
	public CenterItemService getCenterItemService() {
		return centerItemService;
	}
	public void setCenterItemService(CenterItemService centerItemService) {
		this.centerItemService = centerItemService;
	}
	public CenterParamService getCenterParamService() {
		return centerParamService;
	}
	public void setCenterParamService(CenterParamService centerParamService) {
		this.centerParamService = centerParamService;
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
	public void doExportExcel() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		Integer sheetId = Integer.valueOf(request.getParameter("sheetId"));
		Integer centerId = Integer.valueOf(request.getParameter("centerId"));
		String fileName = "中心指标考核结果_" + sheetId  + ".xls";
		setResponseHeader(response, fileName);
		HSSFWorkbook workbook = new HSSFWorkbook(); //create excel file
		HSSFSheet worksheet = workbook.createSheet("中心指标考核结果");
		HSSFRow row = worksheet.createRow(0);
	    cteateCell(workbook, row, 0, "指标级别");  
	    cteateCell(workbook, row, 1, "指标名称");
	    cteateCell(workbook, row, 2, "指标得分");
	    int rowIndex = 1;
		List<CenterItem> allCenterItems = centerItemService.getAllParameterOfCenter(centerId);
		for(int i =0, len = allCenterItems.size(); i < len; i++) {
			CenterItem ci = allCenterItems.get(i);
			if(ci.getItemGrade().equals(1)) {
				CenterScore cs1 = centerScoreService.getCheckedCenterItems(ci.getItemId(), centerId, String.valueOf(sheetId));
				row = worksheet.createRow(rowIndex);
			    cteateCell(workbook, row, 0, "1"); 
			    if(cs1 == null){
			    	cteateCell(workbook, row, 1, ci.getItemName());
				    cteateCell(workbook, row, 2, "");
			    } else {
			    	cteateCell(workbook, row, 1, cs1.getItemName());
				    cteateCell(workbook, row, 2, cs1.getItemScore().toString());	
			    }
				List<CenterItem> secondLevelParamList = centerItemService.getSecondLevelParameterOfFirst(ci.getItemId(), centerId);
				for(int j=0, len2 = secondLevelParamList.size();j <len2;j++) {
					CenterItem secondItem = secondLevelParamList.get(j);
					CenterScore cs2 = centerScoreService.getCheckedCenterItems(secondItem.getItemId(), centerId, String.valueOf(sheetId));
					rowIndex++;
					row = worksheet.createRow(rowIndex);
					cteateCell(workbook, row, 0, "1." + (j+1));  
					if(cs2 == null) {
						cteateCell(workbook, row, 1, secondItem.getItemName());
					    cteateCell(workbook, row, 2, "");
					} else {
						cteateCell(workbook, row, 1, cs2.getItemName());
					    cteateCell(workbook, row, 2, cs2.getItemScore().toString());	
					}
					List<CenterItem> thirdLevelParamList = centerItemService.getThirdLevelParameterOfSecond(secondItem.getItemId(), centerId);
					for(int p=0, len3 = thirdLevelParamList.size(); p < len3; p++) {
						CenterItem thirdItem  =  thirdLevelParamList.get(p);
						CenterParam cp = centerParamService.getParamByIds(thirdItem.getItemId(), centerId, sheetId);
						rowIndex++;
						row = worksheet.createRow(rowIndex);
						cteateCell(workbook, row, 0, "1." + (j+1) + "." + (p+1));
						if(cp == null){
						    cteateCell(workbook, row, 1, thirdItem.getItemName());
						    cteateCell(workbook, row, 2, "");	
						} else {
						    cteateCell(workbook, row, 1, cp.getItemName());
						    cteateCell(workbook, row, 2, cp.getItemValue().toString());	
						}
					}
				}
			}
		}
		workbook.write(response.getOutputStream());
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
	private void cteateCell(HSSFWorkbook wb, HSSFRow row, int col, String val) {
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(val);
		HSSFCellStyle cellstyle = wb.createCellStyle();
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);// 居中对齐
		cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直方向居中对齐
		cellstyle.setWrapText(true);// 设置自动换行
		cell.setCellStyle(cellstyle);// 给单元格设置样式
	}
	public void setResponseHeader(HttpServletResponse response, String fileName) throws Exception{
		 response.setContentType("application/octet-stream;charset=iso-8859-1");
	      response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
	      response.addHeader("Pargam", "no-cache");
	      response.addHeader("Cache-Control", "no-cache");
	}
	public void getHisCenterItemScore() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setCharacterEncoding("UTF-8");
		Integer sheetId = Integer.valueOf(request.getParameter("sheetId"));
		Integer itemId = Integer.valueOf(request.getParameter("itemId"));// 1级指标Id
		Integer centerId = Integer.valueOf(request.getParameter("centerId"));
		List<CenterItem> secondLevelParamList = centerItemService.getSecondLevelParameterOfFirst(itemId, centerId);
		JSONObject resultJson = new JSONObject();
		JSONObject allParamJson = new JSONObject();
		for(int i=0, len=secondLevelParamList.size() ;i<len;i++) {
			CenterItem secondItem  =  secondLevelParamList.get(i);
			CenterScore cs = centerScoreService.getCheckedCenterItems(secondItem.getItemId(), centerId, String.valueOf(sheetId));
			JSONObject secondItemParamJson = JSONObject.fromObject(cs);
			List<CenterItem> thirdLevelParamList = centerItemService.getThirdLevelParameterOfSecond(secondItem.getItemId(), centerId);
			JSONObject temp = new JSONObject();
			if(thirdLevelParamList.size() > 0) {
				JSONObject thirdParamJSON = new JSONObject();
				for(CenterItem thirdItem : thirdLevelParamList) {
					CenterParam cp = centerParamService.getParamByIds(thirdItem.getItemId(), centerId, sheetId);
					thirdParamJSON.accumulate(i + "_3", JSONObject.fromObject(cp).toString());
				}
				temp.put("third", thirdParamJSON);
			}
			temp.put("second", secondItemParamJson);
			temp.put("length", thirdLevelParamList.size());
			allParamJson.put(i, temp);
		}
		resultJson.put("all", allParamJson);
		resultJson.put("length", secondLevelParamList.size());
		response.getWriter().write(resultJson.toString());
		resultJson.clear();
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
