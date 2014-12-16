package org.spes.action.staff;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.spes.service.staff.impl.StaffFormulaServiceImpl;
/**
 * 中心指标公式Action
 * @author HeGang
 *
 */
public class StaffFormulaHandler {


	// 人员指标公式接口
	private StaffFormulaServiceImpl staffFormulaService = null;
	

	public void storeCenterFormula() throws IOException {
		ServletRequest request = ServletActionContext.getRequest();
		ServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("GB2312");
		response.setContentType("text/html");

		String itemName = request.getParameter("itemName");
		String itemId = request.getParameter("itemId");
		String formula = request.getParameter("formula");
		String formulaId = request.getParameter("formulaId");
		//【0】判断参数是否为空
		if(null == itemId || null== itemId || null == formula || itemId.isEmpty() ||itemName.isEmpty() || formula.isEmpty()){

			JSONObject total = new JSONObject();
			total.put("success", "false");
			total.put("info", "无法获取公式参数！");
			try {
			    total.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
			}//catch end
			return;
		}
		//【1】判断更新Or保存公式
		boolean result = false;
		if(formulaId.equals("-1")){
			
			result =  staffFormulaService.storeFormulaByItemId(itemName, formula, Integer.valueOf(itemId));
		}else{
			result = staffFormulaService.updateFormulaByItemId(Integer.valueOf(formulaId), itemName, formula, Integer.valueOf(itemId));
		}
		
		if(!result){
			
			JSONObject total = new JSONObject();
			total.put("success", "false");
			total.put("info", "保存公式内容失败！");
			try {
			    total.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
			}//catch end
			return;
		}
		
			JSONObject total = new JSONObject();
			total.put("success", "true");
			total.put("info", "保存公式成功！");
			try {
			    total.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
			}//catch end
	}


	public StaffFormulaServiceImpl getStaffFormulaService() {
		return staffFormulaService;
	}


	public void setStaffFormulaService(StaffFormulaServiceImpl staffFormulaService) {
		this.staffFormulaService = staffFormulaService;
	}



    
}
