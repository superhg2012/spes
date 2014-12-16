package org.spes.action.window;

import java.io.IOException;

import javax.servlet.ServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.spes.action.center.SessionUtils;
import org.spes.bean.User;
import org.spes.service.user.impl.UserServiceImpl;

import com.opensymphony.xwork2.ActionSupport;

public class WindowServiceDisplayRows extends ActionSupport {
	
	//�û������
	public UserServiceImpl	      userService    = null;
	//��ǰ����Id��ͨ����ǰ��¼�û����ƻ����û���ɫ�������ݿ��в���
	int centerId = 0;
   
	//����ָ����ʾ����
	public String  displayRowsW = "";
	//�༭���Ƿ�ʱˢ��
	public String  instanceRepaintW = ""; 
	
	/**
	 * ��ȡ��ǰ���� back1&&back2��������ǰ��ʾ������
	 * ͨ��Ext Ajax����
	 * @return
	 */
	public void getCurCenterBack1Para(){
		
		ServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("GB2312");
		response.setContentType("text/html");
		
		//========session��ȡcenterId Start========//
		int userId = SessionUtils.getUserId();
		if(-1 == userId){
			JSONObject json = new JSONObject();
			json.put("success","false");
			json.put("data", "��ǰsessionʧЧ����ȡ������ʾ������");
			try {
				json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("get center back1 to front error!");
			}
			return;
			
		}
		User user = userService.getUserById(userId);
		if(null == user){
			JSONObject json = new JSONObject();
			json.put("success","false");
			json.put("data", "��ǰsessionʧЧ����ȡ������ʾ������");
			try {
				json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("get center back1 to front error!");
			}
			return;
		}
		//========session��ȡcenterId End=========//
		//��ǰ̨���ͽ������
		//back1;back2 �ϲ����ݣ�ǰ̨ͨ��";"���зָ�
		JSONObject json = new JSONObject();
		if(null == user || null == user.getBackup1()|| user.getBackup1().isEmpty()){
			json.put("success","false");
			json.put("data", "��ȡ��ǰ�������ݴ���");
		}else{
			json.put("success","true");
			json.put("data",user.getBackup1());
		}
		try {
			json.write(response.getWriter());
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("get center back1 to front error!");
		}
	}
	
	
	/**
	 * ������ǰ���� back1&&back2��������ǰ��ʾ����&&��ʱˢ�²�����
	 * ͨ��Form��ʹ��
	 * @return
	 */
	public void storeCurCenterBack1Para(){
		
		ServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("GB2312");
		response.setContentType("text/html");
	
		//��1����ȡ������Ϊ�ղſɼ���,ֱ�ӷ���ʧ�ܽ��
		if(null == displayRowsW || null == instanceRepaintW ){
			JSONObject json = new JSONObject();
			json.put("success",false);
			try {
				json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("store center back1 to front error!");
			}
		}	
        
		//========session��ȡcenterId Start========//
		int userId = SessionUtils.getUserId();
		if(-1 == userId){
			JSONObject json = new JSONObject();
			json.put("success","false");
			json.put("data", "��ǰsessionʧЧ������������ʾ������");
			try {
				json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("get center back1 to front error!");
			}
			return;
			
		}
		User user = userService.getUserById(userId);
		if(null == user){
			JSONObject json = new JSONObject();
			json.put("success","false");
			json.put("data", "��ǰsessionʧЧ������������ʾ������");
			try {
				json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("get center back1 to front error!");
			}
			return;
		}
		//========session��ȡcenterId End=========//
		
		//��3���жϵ�ǰ��ʾ���������ݿ�����ʾ�����Ƿ�һ��
		displayRowsW = displayRowsW.trim();
		instanceRepaintW = instanceRepaintW.trim();
		String dataString = "����������ʾ�����ɹ���";
		if(user.getBackup1().split(";").length == 2){
			if(user.getBackup1().split(";")[0].equals(displayRowsW)){
				dataString = "norepaint"+";"+instanceRepaintW;
			}
		}
		
		user.setBackup1(displayRowsW+";"+instanceRepaintW);
		boolean result = userService.updateUser(user);
		JSONObject json = new JSONObject();
		if(result){
			
			json.put("success",true);
			json.put("data", dataString);
		}else{
			
			json.put("success",false);
			json.put("data", "��ȡ��ǰ�������ݴ���");
		}	

		try {
			json.write(response.getWriter());
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("store center back1 to front error!");
		}
	}
	
	

	public String getDisplayRowsW() {
		return displayRowsW;
	}


	public void setDisplayRowsW(String displayRowsW) {
		this.displayRowsW = displayRowsW;
	}


	public String getInstanceRepaintW() {
		return instanceRepaintW;
	}


	public void setInstanceRepaintW(String instanceRepaintW) {
		this.instanceRepaintW = instanceRepaintW;
	}


	public UserServiceImpl getUserService() {
		return userService;
	}


	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}

	
	
}
