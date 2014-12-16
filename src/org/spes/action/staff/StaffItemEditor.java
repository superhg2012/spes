package org.spes.action.staff;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.spes.action.center.SessionUtils;
import org.spes.bean.ServiceCenter;
import org.spes.bean.StaffItem;
import org.spes.bean.User;
import org.spes.bean.Window;
import org.spes.service.center.impl.CenterServiceImpl;
import org.spes.service.result.impl.WindowServiceImpl;
import org.spes.service.staff.impl.StaffItemServiceImpl;
import org.spes.service.user.impl.UserServiceImpl;

public class StaffItemEditor {
	
	//���ڷ��������
	public StaffItemServiceImpl    staffItemServie   = null;
    //���ķ����
	private CenterServiceImpl      centerService     = null;
	//���ڷ����
	public WindowServiceImpl      windowService      = null;
	//�û������
	public UserServiceImpl	      userService    = null;
    private int centerId = 0;
    private int windowId = 0;
	//����&���� ����&����ָ����Ϣ
	public void storeEditorSencondOrThirdParameter(){
		
		ServletRequest request = ServletActionContext.getRequest();
		ServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("GB2312");
		response.setContentType("text/html");
		
		//========session��ȡcenterId Start==========================================//
		int userId = SessionUtils.getUserId();
		if(-1 == userId){
			JSONObject json = new JSONObject();
			json.put("success","false");
			try {
			    json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("��ȡsession���û�����ϢΪ�գ�");
			}//catch end
			return;
		}
		User user = userService.findbyUserId(userId);
		if(null == user){
			JSONObject json = new JSONObject();
			json.put("success","false");
			try {
			    json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("��ȡsession���û�����ϢΪ�գ�");
			}//catch end
			return;
		}
		centerId = user.getServiceCenter().getCenterId();
		windowId = user.getWindow().getWindowId();
		
		ServiceCenter center = centerService.findCenterServiceById(centerId);
		//[1]�ж�����ʵ���Ƿ�Ϊ��
		if(null == center){
			JSONObject json = new JSONObject();
			json.put("success","false");
			try {
			    json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("��ȡsession������ʵ��Ϊ�գ�");
			}//catch end
			return;
		}
		Window window = windowService.findWindowById(windowId);
		if(null == window){	
			JSONObject json = new JSONObject();
			json.put("success","false");
			try {
			    json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("��ȡsession�д���ʵ��Ϊ�գ�");
			}//catch end
			return;
		}
		//========session��ȡcenterId End========================================//
		
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
				
				StaffItem item = new StaffItem();
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
               
    			item.setCenterId(centerId);
    			item.setWindowId(windowId);
    			
    			//���浽���ݿ�.,-1Ϊ���治�ɹ���-2Ϊ����ָ���´���������ָͬ��
    			int result = staffItemServie.storeSecondAndThirdPara(item);
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
			StaffItem  item = staffItemServie.getFirstLevelparameter(obj.getInt("sqlId"));
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
			
			//����ָ����Ϣ
			item.setItemName(name);
			item.setItemWeight(weight);
			item.setEnabled(enable);
			item.setItemType(style);
			
			
			int result = staffItemServie.updateCenterItemByConcreteItem(item);
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
			boolean result = staffItemServie.deleteSecondLevelPara(Integer.valueOf(itemId));
			if(!result) {
				info ="����ɾ��ʧ�ܣ�";
				resultText = "false";
			}else{
				info ="����ɾ���ɹ���";
				resultText = "true";
			}
		}else if(itemType.equals("����ָ��")){
			int resultInner = staffItemServie.deleteCurParaInfo(Integer.valueOf(Integer.valueOf(itemId)));
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
    


	public StaffItemServiceImpl getStaffItemServie() {
		return staffItemServie;
	}

	public void setStaffItemServie(StaffItemServiceImpl staffItemServie) {
		this.staffItemServie = staffItemServie;
	}

	public WindowServiceImpl getWindowService() {
		return windowService;
	}

	public void setWindowService(WindowServiceImpl windowService) {
		this.windowService = windowService;
	}

	public CenterServiceImpl getCenterService() {
		return centerService;
	}
	public void setCenterService(CenterServiceImpl centerService) {
		this.centerService = centerService;
	}

	public UserServiceImpl getUserService() {
		return userService;
	}

	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}
	
}
