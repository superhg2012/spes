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
	
	//用户服务层
	public UserServiceImpl	      userService    = null;
	//当前中心Id，通过当前登录用户名称或者用户角色，在数据库中查找
	int centerId = 0;
   
	//中心指标显示行数
	public String  displayRowsW = "";
	//编辑后是否及时刷新
	public String  instanceRepaintW = ""; 
	
	/**
	 * 获取当前中心 back1&&back2参数（当前显示行数）
	 * 通过Ext Ajax传递
	 * @return
	 */
	public void getCurCenterBack1Para(){
		
		ServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("GB2312");
		response.setContentType("text/html");
		
		//========session获取centerId Start========//
		int userId = SessionUtils.getUserId();
		if(-1 == userId){
			JSONObject json = new JSONObject();
			json.put("success","false");
			json.put("data", "当前session失效，获取中心显示参数！");
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
			json.put("data", "当前session失效，获取中心显示参数！");
			try {
				json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("get center back1 to front error!");
			}
			return;
		}
		//========session获取centerId End=========//
		//往前台传送结果数据
		//back1;back2 合并传递，前台通过";"进行分割
		JSONObject json = new JSONObject();
		if(null == user || null == user.getBackup1()|| user.getBackup1().isEmpty()){
			json.put("success","false");
			json.put("data", "获取当前中心数据错误！");
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
	 * 存贮当前中心 back1&&back2参数（当前显示行数&&即时刷新参数）
	 * 通过Form表单使用
	 * @return
	 */
	public void storeCurCenterBack1Para(){
		
		ServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("GB2312");
		response.setContentType("text/html");
	
		//【1】获取参数不为空才可继续,直接返回失败结果
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
        
		//========session获取centerId Start========//
		int userId = SessionUtils.getUserId();
		if(-1 == userId){
			JSONObject json = new JSONObject();
			json.put("success","false");
			json.put("data", "当前session失效，保存中心显示参数！");
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
			json.put("data", "当前session失效，保存中心显示参数！");
			try {
				json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("get center back1 to front error!");
			}
			return;
		}
		//========session获取centerId End=========//
		
		//【3】判断当前显示行数与数据库中显示行数是否一致
		displayRowsW = displayRowsW.trim();
		instanceRepaintW = instanceRepaintW.trim();
		String dataString = "保存中心显示参数成功！";
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
			json.put("data", "获取当前中心数据错误！");
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
