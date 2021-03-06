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
	
	//窗口服务层链接
	public StaffItemServiceImpl    staffItemServie   = null;
    //中心服务层
	private CenterServiceImpl      centerService     = null;
	//窗口服务层
	public WindowServiceImpl      windowService      = null;
	//用户服务层
	public UserServiceImpl	      userService    = null;
    private int centerId = 0;
    private int windowId = 0;
	//保存&更新 二级&三级指标信息
	public void storeEditorSencondOrThirdParameter(){
		
		ServletRequest request = ServletActionContext.getRequest();
		ServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("GB2312");
		response.setContentType("text/html");
		
		//========session获取centerId Start==========================================//
		int userId = SessionUtils.getUserId();
		if(-1 == userId){
			JSONObject json = new JSONObject();
			json.put("success","false");
			try {
			    json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("获取session中用户的信息为空！");
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
				System.out.println("获取session中用户的信息为空！");
			}//catch end
			return;
		}
		centerId = user.getServiceCenter().getCenterId();
		windowId = user.getWindow().getWindowId();
		
		ServiceCenter center = centerService.findCenterServiceById(centerId);
		//[1]判断中心实体是否为空
		if(null == center){
			JSONObject json = new JSONObject();
			json.put("success","false");
			try {
			    json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("获取session中中心实例为空！");
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
				System.out.println("获取session中窗口实例为空！");
			}//catch end
			return;
		}
		//========session获取centerId End========================================//
		
		String content = request.getParameter("data");
		//【0】判断获取内容是否为空
		if(null == content || content.isEmpty()){
			
			JSONObject json = new JSONObject();
			json.put("success","false");
			try {
			    json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("获取前台的信息为空！");
			}//catch end
			return;
		}
		JSONArray array = JSONArray.fromObject(content);
        //【1】判断json的数组大小
		if(0 == array.size()){
			
			JSONObject json = new JSONObject();
			json.put("success","false");
			try {
			    json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("获取前台Json信息大小为0！");
			}//catch end
			return;
		}
		for(int i=0;i<array.size();i++){
			JSONObject obj = array.getJSONObject(i);
		    //【2】判断此指标信息是否为新添加的
			if(obj.getString("sqlId").trim().equals("+1")){
				
				StaffItem item = new StaffItem();
				item.setItemName(obj.getString("name").trim());
				item.setParentId(obj.getInt("parentId"));
				if(obj.getString("grade").trim().equals("二级指标")){
					item.setItemGrade(2);
				}
				if(obj.getString("grade").trim().equals("三级指标")){
					item.setItemGrade(3);
				}
				item.setItemWeight(obj.getDouble("weight"));
				
				if(obj.getString("enable").trim().equals("使用")){
					item.setEnabled(true);
				}
				if(obj.getString("enable").trim().equals("禁用")){
					item.setEnabled(false);
				}
                item.setItemType(obj.getString("type").trim());
               
    			item.setCenterId(centerId);
    			item.setWindowId(windowId);
    			
    			//保存到数据库.,-1为保存不成功，-2为父亲指标下存在名称相同指标
    			int result = staffItemServie.storeSecondAndThirdPara(item);
    			if(-1 == result ){
    				JSONObject json = new JSONObject();
    				json.put("success","false");
    				json.put("info", "保存 "+item.getItemName()+" 发生错误！");
    				try {
    				    json.write(response.getWriter());
    				} catch (IOException e) {
    					e.printStackTrace();
    					System.out.println("保存Item信息发生错误！");
    				}//catch end
    				return;
    			}
    			if(-2 == result ){
    				JSONObject json = new JSONObject();
    				json.put("success","false");
    				json.put("info", "同级指标中存在相同内容的指标，无法保存！");
    				try {
    				    json.write(response.getWriter());
    				} catch (IOException e) {
    					e.printStackTrace();
    					System.out.println("保存Item信息发生错误！");
    				}//catch end
    				return;
    			}
    			
    			//继续循环
    			continue;
    		
			}// if end
			else{
			//【3】更新指标信息
			StaffItem  item = staffItemServie.getFirstLevelparameter(obj.getInt("sqlId"));
			if(null == item){
				System.out.println("获取指标信息为空！");
				return;
			}
			String name = obj.getString("name").trim();
			double weight = obj.getDouble("weight");
			//是否可用
			boolean enable = false;
			if(obj.getString("enable").trim().equals("使用")){
				enable = true;
			}else{
				enable = false;
			}
			String style = obj.getString("type").trim();
			
			//更新指标信息
			item.setItemName(name);
			item.setItemWeight(weight);
			item.setEnabled(enable);
			item.setItemType(style);
			
			
			int result = staffItemServie.updateCenterItemByConcreteItem(item);
			//更新指标数据库.,-1为保存不成功，-2为父亲指标下存在名称相同指标
			if( -1 == result){
				JSONObject json = new JSONObject();
				json.put("success","false");
				json.put("info", "更新 "+item.getItemName()+" 发生错误！");
				try {
				    json.write(response.getWriter());
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("更新Item信息发生错误！");
				}//catch end
				return;
			}//if end
			if( -2 == result){
				JSONObject json = new JSONObject();
				json.put("success","false");
				json.put("info", "同级指标中存在相同内容的指标，无法更新！");
				try {
				    json.write(response.getWriter());
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("更新Item信息发生错误！");
				}//catch end
				return;
			}//if end
			
		  }//总 else end
		}// for end
 
		JSONObject json = new JSONObject();
		json.put("success","true");
		try {
		    json.write(response.getWriter());
		} catch (IOException e) {
			e.printStackTrace();
		}//catch end
	}
	
	//删除二级or三级指标信息
	public void deleteEditorSecOrThirdPara(){
		
		ServletRequest request = ServletActionContext.getRequest();
		ServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("GB2312");
		response.setContentType("text/html");
		
		String itemId = request.getParameter("itemId");
		String itemType = request.getParameter("itemType");
		//[0]判断前台传递参数是否为空
		if(null == itemId || null == itemType || itemId.isEmpty() || itemType.isEmpty()){
			
			JSONObject json = new JSONObject();
			json.put("success","false");
			try {
			    json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("获取前台Json信息大小为0！");
			}//catch end
			return;
		}
		//[1]判断当前指标 为 二级指标or三级指标
		//返回前台结果
		String info = "";
		String resultText = "";
		if(itemType.equals("二级指标")){
			boolean result = staffItemServie.deleteSecondLevelPara(Integer.valueOf(itemId));
			if(!result) {
				info ="二级删除失败！";
				resultText = "false";
			}else{
				info ="二级删除成功！";
				resultText = "true";
			}
		}else if(itemType.equals("三级指标")){
			int resultInner = staffItemServie.deleteCurParaInfo(Integer.valueOf(Integer.valueOf(itemId)));
			if(-1 == resultInner) { 
				info = "三级指标删除失败！";
				resultText = "false";
			}else if( -2 == resultInner){
				info = "公式中存在此三级指标内容，请先修改公式！";
				resultText = "false";
			}else if(1 == resultInner){
				info = "三级指标删除成功！";
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
