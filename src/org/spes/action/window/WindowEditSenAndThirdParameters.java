package org.spes.action.window;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.spes.bean.CenterFormula;
import org.spes.bean.CenterItem;
import org.spes.bean.ServiceCenter;
import org.spes.bean.WindowFormula;
import org.spes.bean.WindowItem;
import org.spes.service.center.CenterItemFormulaService;
import org.spes.service.center.impl.CenterItemServiceImpl;
import org.spes.service.center.impl.CenterServiceImpl;
import org.spes.service.window.impl.WindowFormulaServiceImpl;
import org.spes.service.window.impl.WindowItemServiceImpl;

import com.opensymphony.xwork2.ActionSupport;

public class WindowEditSenAndThirdParameters extends ActionSupport {
	
	//WindowItem���������
	private WindowItemServiceImpl    windowItemService = null;
    //centerService�����
	private CenterServiceImpl        centerService    = null;
	// ���Ĺ�ʽ�ӿ�
	private WindowFormulaServiceImpl windowFormulaService = null;
	//��ǰһ��ָ���Id��Ϣ���û�ѡ��ͬ��һ��ָ��ʱ����£�
	private String     curFirstLevelParaId = null;
    private String     start = null;
    private String     end   = null;
	
    //��ȡ����&����ָ����Ϣ
    public void getSecAndThirdItemsInfo(){
	
		ServletRequest request = ServletActionContext.getRequest();
		ServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("GB2312");
		response.setContentType("text/html");
		
		//��0.0����ȡ��ǰһ��ָ��Id��Ϣ�������û���һ�ε��һ��ָ�����ʱ�򣬸�ֵ��
		String paraId = request.getParameter("paraId");
		if(null != paraId){
			curFirstLevelParaId = paraId;
		}
		
		String frontStart = request.getParameter("start");
		if(null != frontStart)start = frontStart;
		String frontEnd = request.getParameter("limit");
		if(null != frontEnd) end =frontEnd;
		
		
		//��0���ж�ǰ̨���ݲ�����start��end�Ƿ�Ϊ��
		if( null == curFirstLevelParaId || null == start || null == end || start.isEmpty()||end.isEmpty()|| curFirstLevelParaId.isEmpty()){
			//��ȡ���������ռ��С
			JSONObject total = new JSONObject();
			JSONArray aray = new JSONArray();
			total.put("totalProperty",0);
			total.put("root", aray);
			try {
			    total.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("get centerservice error!");
			}//catch end
			return;
		}
		
		//��1����ȡָ������
		ArrayList<WindowItem> itemData = this.windowItemService.getSecondAndThridParameters(Integer.valueOf(curFirstLevelParaId));
		if(null == itemData || itemData.size() ==0){
			//��ȡ���������ռ��С
			JSONObject total = new JSONObject();
			JSONArray aray = new JSONArray();
			total.put("totalProperty",0);
			total.put("root", aray);
			try {
			    total.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("get centerservice error!");
			}//catch end
			
			return;
		}
		
		//��2����ȡ��ǰ������Ϣ
		ServiceCenter service = this.centerService.findCenterServiceById(itemData.get(0).getCenterId());
		if(null == service){
			
			//��ȡ���������ռ��С
			JSONObject total = new JSONObject();
			JSONArray aray = new JSONArray();
			total.put("totalProperty",0);
			total.put("root", aray);
			try {
			    total.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("get centerservice error!");
			}//catch end
			return;
		}
		
		//��ȡ���������ռ��С
		JSONObject total = new JSONObject();
		total.put("totalProperty",itemData.size());
		//��ȡ�����ݣ�Backup1 ���ڴ�ŵ�ǰ����ָ���˳��Id����Dao���ȡ����ʱ�Ѿ���ӣ�
		JSONArray aray = new JSONArray();
		int itemStart = Integer.valueOf(start);
		int itemEnd   = Integer.valueOf(end);
		for (int i = itemStart; i < itemStart+itemEnd;  i++) {
			//����itemData�����������ͷ��ز���ѭ������
			if(i >= itemData.size() )continue;
			WindowItem item = itemData.get(i);
			JSONObject obj = new JSONObject();
			//ȫ������ǰָ̨����Ϣ ���������ݿ�Id,�Լ�����Id
			obj.put("itemDBId",item.getItemId());
			obj.put("itemParentId", item.getParentId());
			
			//����ָ�����Id��Ϣ������ָ����Id��Ϣ
			if(item.getItemGrade().equals(Integer.valueOf(2))){
				obj.put("itemId",item.getBackup1());
				//������ָ����ӹ�ʽ��Ϣ��
				List<WindowFormula> formulas = windowFormulaService.getFormulaByItemId(item.getItemId());
				if(null != formulas && formulas.size() == 1){
                   obj.put("itemFormula",formulas.get(0).getCalculator());
                   obj.put("itemFormulaId",String.valueOf(formulas.get(0).getFormulaId()));
				}else{
					
					obj.put("itemFormula","�޹�ʽ");
					obj.put("itemFormulaId","-1");
				}
			}else{
				obj.put("itemId","");
				obj.put("itemFormula","");
				obj.put("itemFormulaId","");
			}
			//ָ������
			obj.put("itemName",item.getItemName());
			//ָ��ȼ���Ϣ
			if(item.getItemGrade().equals(Integer.valueOf(2))){
				obj.put("itemGrade","<b>"+"����ָ��"+"</b>");	
			}else{
				obj.put("itemGrade","����ָ��");
			}
			//ָ��Ȩ��
			obj.put("itemWeight", item.getItemWeight());
			//ָ���Ƿ�ʹ��
			if(item.getEnabled()){
				obj.put("itemEnable","ʹ��");
			}else{
				obj.put("itemEnable","����");
			}
			obj.put("itemType", item.getItemType());
			obj.put("itemCenter",service.getCenterName());
			//���ָ����Ϣ
			aray.add(obj);
		}
   		total.put("root", aray);
   		try {
		    total.write(response.getWriter());
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("get centerservice error!");
		}//catch end
		
	}
	//��ȡ����ָ����Ϣ
    public void getCurThirdPara(){
    	
    	ServletRequest request = ServletActionContext.getRequest();
		ServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("GB2312");
		response.setContentType("text/html");
		
		//��0.0����ȡ��ǰ����ָ��Id��Ϣ
		String paraId = request.getParameter("paraId");
		if(null == paraId){
			
			JSONObject total = new JSONObject();
			total.put("success","false");
			try {
			    total.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
			}//catch end
			return;
		}
    	List<WindowItem> result = windowItemService.getCurThirdParas(Integer.valueOf(paraId));
    	//��0.2���жϻ�ȡ����ָ���Ƿ�Ϊ��
    	if(null == result){
    		
    		JSONObject total = new JSONObject();
			total.put("success","false");
			try {
			    total.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
			}//catch end
			return;
    	}
    	//����ǰ̨����
    	JSONArray array = new JSONArray();
    	for (WindowItem centerItem : result) {
			JSONObject obj = new JSONObject();
			obj.put("name",centerItem.getItemName());
			array.add(obj);
		}
    	JSONObject returnData = new JSONObject();
    	returnData.put("success","true");
    	returnData.put("data",array);
    	try {
		    returnData.write(response.getWriter());
		} catch (IOException e) {
			e.printStackTrace();
		}//catch end
    }
    
    
	public WindowItemServiceImpl getWindowItemService() {
		return windowItemService;
	}
	public void setWindowItemService(WindowItemServiceImpl windowItemService) {
		this.windowItemService = windowItemService;
	}
	public CenterServiceImpl getCenterService() {
		return centerService;
	}
	public void setCenterService(CenterServiceImpl centerService) {
		this.centerService = centerService;
	}
	public WindowFormulaServiceImpl getWindowFormulaService() {
		return windowFormulaService;
	}
	public void setWindowFormulaService(
			WindowFormulaServiceImpl windowFormulaService) {
		this.windowFormulaService = windowFormulaService;
	}
    
	
}
