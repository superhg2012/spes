package org.spes.action.center;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

public class SessionUtils {
   
	//��ȡ��ǰsession�е�userId��Ϣ
	public static  int  getUserId(){
		
		HttpSession session = ServletActionContext.getRequest().getSession(false);
		
		if(null == session){
			return -1;
		}
		Integer userId =(Integer)session.getAttribute("userId"); 
		if(userId == null ){
			return -1;
			
		}
		return userId;
		
	}
}
