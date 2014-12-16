package org.spes.action.center;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.spes.service.center.CenterItemFormulaService;
import org.spes.service.center.impl.CenterItemServiceImpl;
/**
 * ����ָ�깫ʽAction
 * @author HeGang
 *
 */
public class CenterFormulaHandler {


	// ����ָ�깫ʽ�ӿ�
	private CenterItemFormulaService centerFormulaService = null;
	//centerItem���������
	private CenterItemServiceImpl  centerItemService = null;
	

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
			
			result =  centerFormulaService.storeFormulaByItemId(itemName, formula, Integer.valueOf(itemId));
		}else{
			result = centerFormulaService.updateFormulaByItemId(Integer.valueOf(formulaId), itemName, formula, Integer.valueOf(itemId));
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
	
	public CenterItemFormulaService getCenterFormulaService() {
		return centerFormulaService;
	}

	public void setCenterFormulaService(
			CenterItemFormulaService centerFormulaService) {
		this.centerFormulaService = centerFormulaService;
	}


	public CenterItemServiceImpl getCenterItemService() {
		return centerItemService;
	}

	public void setCenterItemService(CenterItemServiceImpl centerItemService) {
		this.centerItemService = centerItemService;
	}
    
}
