package org.spes.action.window;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.spes.bean.CenterFormula;
import org.spes.service.center.CenterItemFormulaService;
import org.spes.service.center.impl.CenterItemServiceImpl;
import org.spes.service.window.impl.WindowFormulaServiceImpl;
import org.spes.service.window.impl.WindowItemServiceImpl;
/**
 * 中心指标公式Action
 * @author HeGang
 *
 */
public class WindowFormulaHandler {


	// 窗口指标公式接口
	private WindowFormulaServiceImpl windowFormulaService = null;
	//窗口服务层链接
	private WindowItemServiceImpl  windowItemService = null;
	

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
			
			result =  windowFormulaService.storeFormulaByItemId(itemName, formula, Integer.valueOf(itemId));
		}else{
			result = windowFormulaService.updateFormulaByItemId(Integer.valueOf(formulaId), itemName, formula, Integer.valueOf(itemId));
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


	public WindowFormulaServiceImpl getWindowFormulaService() {
		return windowFormulaService;
	}


	public void setWindowFormulaService(
			WindowFormulaServiceImpl windowFormulaService) {
		this.windowFormulaService = windowFormulaService;
	}


	public WindowItemServiceImpl getWindowItemService() {
		return windowItemService;
	}


	public void setWindowItemService(WindowItemServiceImpl windowItemService) {
		this.windowItemService = windowItemService;
	}
	
    
}
