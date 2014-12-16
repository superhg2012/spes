package org.spes.action.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.spes.bean.CenterItem;
import org.spes.bean.CenterScore;
import org.spes.bean.Evaluatesheet;
import org.spes.common.ParameterData;
import org.spes.common.StringUtil;
import org.spes.service.center.CenterItemService;
import org.spes.service.center.CenterScoreService;
import org.spes.service.common.EvaluateSheetService;


public class ExportParameterAction {

	private String sheetId;
	private String flag;
	private Integer centerId;
	private HttpServletRequest request;
	private HttpServletResponse	response;
	private EvaluateSheetService evaluateSheetService = null;
	private CenterItemService centerItemService = null;
	private CenterScoreService centerScoreService = null;

	public String getSheetId() {
		return sheetId;
	}

	public void setSheetId(String sheetId) {
		this.sheetId = sheetId;
	}
	
	public Integer getCenterId() {
		return centerId;
	}

	public void setCenterId(Integer centerId) {
		this.centerId = centerId;
	}
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	

	public void setEvaluateSheetService(EvaluateSheetService evaluateSheetService) {
		this.evaluateSheetService = evaluateSheetService;
	}

	public void getExportedParameter() throws IOException {
		
		final JSONObject paramJson = new JSONObject();
		if(StringUtil.isNull(sheetId)) {
			 response.getWriter().write("error");
			 return;
		}
		
		final Evaluatesheet es = evaluateSheetService.getEvaluateSheet(Integer.valueOf(sheetId)).get(0);
		
		if (es == null) {
			response.getWriter().write("internal error");
			return;
		}
		
		List<CenterItem> cIlist = centerItemService.getAllParameterOfCenter(centerId);
		if(!cIlist.isEmpty()) {
			for(CenterItem ceItem : cIlist) {
				Integer paramId = ceItem.getItemId();
				String paramName = ceItem.getItemName();
				String paramScore = "N/A";
				int grade = ceItem.getItemGrade();
				if(grade == 1) {
					
				}
				CenterScore cs =  centerScoreService.getCheckedCenterItems(paramId, centerId, sheetId);
				if (cs != null) {
					paramScore = cs.getItemScore().toString();
				}
				
				
				
				ParameterData pd = new ParameterData(paramId, paramName, paramScore);
			}
			
		}
		//crate the sheet values grid to display
		
		if(StringUtil.isNotNull(flag) && "download".equals(flag)) {
			
		}
		response.getWriter().write(paramJson.toString());
		
	}
}
