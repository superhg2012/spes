package org.spes.action.window;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.spes.bean.CenterItem;
import org.spes.bean.WindowItem;
import org.spes.service.center.impl.CenterItemServiceImpl;
import org.spes.service.center.impl.CenterServiceImpl;
import org.spes.service.window.impl.WindowItemServiceImpl;

public class WindowItemEditor {
	
	//����ָ����������
	private WindowItemServiceImpl  windowItemService = null;
    //���ķ����
	private CenterServiceImpl      centerService     = null;
	
	//����&���� ����&����ָ����Ϣ
	public void storeEditorSencondOrThirdParameter(){
		
		ServletRequest request = ServletActionContext.getRequest();
		ServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("GB2312");
		response.setContentType("text/html");
		
		String content = request.getParameter("data");
		//��0���жϻ�ȡ�����Ƿ�Ϊ��
		if(null == content || content.isEmpty()){
			
			JSONObject json = new JSONObject();
			json.put("success","false");
			try {
			    json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("��ȡǰ̨����ϢΪ�գ�");
			}//catch end
			return;
		}
		JSONArray array = JSONArray.fromObject(content);
        //��1���ж�json�������С
		if(0 == array.size()){
			
			JSONObject json = new JSONObject();
			json.put("success","false");
			try {
			    json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("��ȡǰ̨Json��Ϣ��СΪ0��");
			}//catch end
			return;
		}
		for(int i=0;i<array.size();i++){
			JSONObject obj = array.getJSONObject(i);
		    //��2���жϴ�ָ����Ϣ�Ƿ�Ϊ����ӵ�
			if(obj.getString("sqlId").trim().equals("+1")){
				
				WindowItem item = new WindowItem();
				item.setItemName(obj.getString("name").trim());
				item.setParentId(obj.getInt("parentId"));
				if(obj.getString("grade").trim().equals("����ָ��")){
					item.setItemGrade(2);
				}
				if(obj.getString("grade").trim().equals("����ָ��")){
					item.setItemGrade(3);
				}
				item.setItemWeight(obj.getDouble("weight"));
				
				if(obj.getString("enable").trim().equals("ʹ��")){
					item.setEnabled(true);
				}
				if(obj.getString("enable").trim().equals("����")){
					item.setEnabled(false);
				}
                item.setItemType(obj.getString("type").trim());
                //��ȡ����Id
    			int centerId = centerService.findCenterIdByName(obj.getString("center").trim());
    			if(-1 == centerId){
    				System.out.println("��ȡ����Id����");
    				return;
    			}
    			item.setCenterId(centerId);
    			
    			//���浽���ݿ�.,-1Ϊ���治�ɹ���-2Ϊ����ָ���´���������ָͬ��
    			int result = windowItemService.storeSecondAndThirdPara(item);
    			if(-1 == result ){
    				JSONObject json = new JSONObject();
    				json.put("success","false");
    				json.put("info", "���� "+item.getItemName()+" ��������");
    				try {
    				    json.write(response.getWriter());
    				} catch (IOException e) {
    					e.printStackTrace();
    					System.out.println("����Item��Ϣ��������");
    				}//catch end
    				return;
    			}
    			if(-2 == result ){
    				JSONObject json = new JSONObject();
    				json.put("success","false");
    				json.put("info", "ͬ��ָ���д�����ͬ���ݵ�ָ�꣬�޷����棡");
    				try {
    				    json.write(response.getWriter());
    				} catch (IOException e) {
    					e.printStackTrace();
    					System.out.println("����Item��Ϣ��������");
    				}//catch end
    				return;
    			}
    			
    			//����ѭ��
    			continue;
    		
			}// if end
			else{
			//��3������ָ����Ϣ
			WindowItem  item = windowItemService.getFirstLevelparameter(obj.getInt("sqlId"));
			if(null == item){
				System.out.println("��ȡָ����ϢΪ�գ�");
				return;
			}
			String name = obj.getString("name").trim();
			double weight = obj.getDouble("weight");
			//�Ƿ����
			boolean enable = false;
			if(obj.getString("enable").trim().equals("ʹ��")){
				enable = true;
			}else{
				enable = false;
			}
			String style = obj.getString("type").trim();
			//��ȡ����Id
			int centerId = centerService.findCenterIdByName(obj.getString("center").trim());
			if(-1 == centerId){
				System.out.println("��ȡ����Id����");
				return;
			}
			
			//����ָ����Ϣ
			item.setItemName(name);
			item.setItemWeight(weight);
			item.setEnabled(enable);
			item.setItemType(style);
			item.setCenterId(centerId);
			
			int result = windowItemService.updateCenterItemByConcreteItem(item);
			//����ָ�����ݿ�.,-1Ϊ���治�ɹ���-2Ϊ����ָ���´���������ָͬ��
			if( -1 == result){
				JSONObject json = new JSONObject();
				json.put("success","false");
				json.put("info", "���� "+item.getItemName()+" ��������");
				try {
				    json.write(response.getWriter());
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("����Item��Ϣ��������");
				}//catch end
				return;
			}//if end
			if( -2 == result){
				JSONObject json = new JSONObject();
				json.put("success","false");
				json.put("info", "ͬ��ָ���д�����ͬ���ݵ�ָ�꣬�޷����£�");
				try {
				    json.write(response.getWriter());
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("����Item��Ϣ��������");
				}//catch end
				return;
			}//if end
			
		  }//�� else end
		}// for end
 
		JSONObject json = new JSONObject();
		json.put("success","true");
		try {
		    json.write(response.getWriter());
		} catch (IOException e) {
			e.printStackTrace();
		}//catch end
	}
	
	//ɾ������or����ָ����Ϣ
	public void deleteEditorSecOrThirdPara(){
		
		ServletRequest request = ServletActionContext.getRequest();
		ServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("GB2312");
		response.setContentType("text/html");
		
		String itemId = request.getParameter("itemId");
		String itemType = request.getParameter("itemType");
		//[0]�ж�ǰ̨���ݲ����Ƿ�Ϊ��
		if(null == itemId || null == itemType || itemId.isEmpty() || itemType.isEmpty()){
			
			JSONObject json = new JSONObject();
			json.put("success","false");
			try {
			    json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("��ȡǰ̨Json��Ϣ��СΪ0��");
			}//catch end
			return;
		}
		//[1]�жϵ�ǰָ�� Ϊ ����ָ��or����ָ��
		//����ǰ̨���
		String info = "";
		String resultText = "";
		if(itemType.equals("����ָ��")){
			boolean result = windowItemService.deleteSecondLevelPara(Integer.valueOf(itemId));
			if(!result) {
				info ="����ɾ��ʧ�ܣ�";
				resultText = "false";
			}else{
				info ="����ɾ���ɹ���";
				resultText = "true";
			}
		}else if(itemType.equals("����ָ��")){
			int resultInner = windowItemService.deleteCurParaInfo(Integer.valueOf(Integer.valueOf(itemId)));
			if(-1 == resultInner) { 
				info = "����ָ��ɾ��ʧ�ܣ�";
				resultText = "false";
			}else if( -2 == resultInner){
				info = "��ʽ�д��ڴ�����ָ�����ݣ������޸Ĺ�ʽ��";
				resultText = "false";
			}else if(1 == resultInner){
				info = "����ָ��ɾ���ɹ���";
				resultText = "true";
			}
		}
		
		JSONObject json = new JSONObject();
		json.put("success",resultText);
		json.put("info", info);
		try {
		    json.write(response.getWriter());
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
}
