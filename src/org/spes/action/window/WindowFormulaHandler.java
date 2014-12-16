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
 * ����ָ�깫ʽAction
 * @author HeGang
 *
 */
public class WindowFormulaHandler {


	// ����ָ�깫ʽ�ӿ�
	private WindowFormulaServiceImpl windowFormulaService = null;
	//���ڷ��������
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
		//��0���жϲ����Ƿ�Ϊ��
		if(null == itemId || null== itemId || null == formula || itemId.isEmpty() ||itemName.isEmpty() || formula.isEmpty()){

			JSONObject total = new JSONObject();
			total.put("success", "false");
			total.put("info", "�޷���ȡ��ʽ������");
			try {
			    total.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
			}//catch end
			return;
		}
		//��1���жϸ���Or���湫ʽ
		boolean result = false;
		if(formulaId.equals("-1")){
			
			result =  windowFormulaService.storeFormulaByItemId(itemName, formula, Integer.valueOf(itemId));
		}else{
			result = windowFormulaService.updateFormulaByItemId(Integer.valueOf(formulaId), itemName, formula, Integer.valueOf(itemId));
		}
		
		if(!result){
			
			JSONObject total = new JSONObject();
			total.put("success", "false");
			total.put("info", "���湫ʽ����ʧ�ܣ�");
			try {
			    total.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
			}//catch end
			return;
		}
		
			JSONObject total = new JSONObject();
			total.put("success", "true");
			total.put("info", "���湫ʽ�ɹ���");
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
