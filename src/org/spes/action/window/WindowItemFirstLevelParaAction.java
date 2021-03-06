package org.spes.action.window;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.spes.action.center.SessionUtils;
import org.spes.bean.ServiceCenter;
import org.spes.bean.User;
import org.spes.bean.WindowItem;
import org.spes.service.center.impl.CenterServiceImpl;
import org.spes.service.user.impl.UserServiceImpl;
import org.spes.service.window.impl.WindowItemServiceImpl;

import com.opensymphony.xwork2.ActionSupport;

public class WindowItemFirstLevelParaAction extends ActionSupport{

	//窗口服务层链接
	public WindowItemServiceImpl  windowItemService = null;
    //中心服务层
	public CenterServiceImpl      centerService    = null;
	//用户服务层
	public UserServiceImpl	      userService    = null;
	private int centerId = 0;
	/**
	 * 获取 一级与二级指标数据
	 * Ext Ajax
	 * @return
	 */

	public void getFirstSecondParaData(){
		

		ServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("GB2312");
		response.setContentType("text/html");
		//当前中心Id，通过当前登录用户名称或者用户角色，在数据库中查找
		//========session获取centerId Start========//
		int userId = SessionUtils.getUserId();
		if(-1 == userId){
			JSONObject json = new JSONObject();
			json.put("success","false");
			json.put("data", "当前session失效，获取中心指标参数！");
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
			json.put("data", "当前session失效，获取中心指标参数！");
			try {
				json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("get center back1 to front error!");
			}
			return;
		}
		centerId = user.getServiceCenter().getCenterId();
		//========session获取centerId End=========//
		
		List<WindowItem> item    = windowItemService.getAllParameterData(centerId);
		ServiceCenter  serCenter = centerService.findCenterServiceById(centerId);

		
		//[0.1]判断获取参数结果函数是否为空
		if(null == item || item.size() ==0 ){
			JSONObject obj = new JSONObject();
			obj.put("success","false");
			obj.put("data","无法从数据库加载指标信息！");
			try {
				obj.write(response.getWriter());
				return;
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("transfer centerpara1 to front error!");
				return;
			}
		}
		//[0.2]判断中心实例是否为空
		else if(null == serCenter){
			JSONObject obj = new JSONObject();
			obj.put("success","false");
			obj.put("data","无法从数据库加载中心信息！");
			try {
				obj.write(response.getWriter());
				return;
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("transfer centerpara2 to front error!");
				return;
			}
		}
		else{
			
			int firstGrade = 1;
			int selfItemId = 0;
			//json数组
			JSONArray jsonArray = new JSONArray();
			for (WindowItem centerItem : item) {

				if(null!= centerItem.getItemGrade() && firstGrade == centerItem.getItemGrade()){
					JSONObject json = new JSONObject();
					selfItemId = centerItem.getItemId();
					//添加一级Title信息
					json.put("title", centerItem.getItemName());
					json.put("titleId",String.valueOf(centerItem.getItemId()));
					//在结果数据中查询 ，父id为selfItemId
					int idIndex = 1;
					String content = "";
					for (WindowItem centerItemInner : item) {
						if(selfItemId == centerItemInner.getParentId()){
							//添加二级指标信息
							content += "["+String.valueOf(idIndex++)+"] "+centerItemInner.getItemName()+"<br>";
						}
					}//for end
					content+="【权重】 "+centerItem.getItemWeight();
	                json.put("content",content);
	                jsonArray.add(json);
	                
				}else{
					// do nothing
				}
			}//for end
			
		    JSONObject resultJson = new JSONObject();
		    resultJson.put("success", "true");
		    resultJson.put("data",jsonArray);
		    resultJson.put("centerId", serCenter.getCenterId());
		    resultJson.put("centerName",serCenter.getCenterName());
			
		    try {
		    	resultJson.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("transfer centerpara to front error!");
			}//catch end
		}//else end
		
	}
	
	/**
	 * 通过一级指标Id，获取指标信息,(用于编辑一级指标信息窗口获取指标信息)
	 */
	public void getFirstLevelParaById(){
			
			ServletRequest request = ServletActionContext.getRequest();
			ServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("GB2312");
			response.setContentType("text/html");
			
			String  centerItemIdStr = request.getParameter("centerItemId");
			WindowItem item = windowItemService.getFirstLevelparameter(Integer.valueOf(centerItemIdStr));
			//[0]判断指标实体是否为空
			if(null == item){
				JSONObject json = new JSONObject();
				json.put("success","false");
				try {
				    json.write(response.getWriter());
				} catch (IOException e) {
					e.printStackTrace();
					System.err.println("get centerItem error!");
				}//catch end
				return;
			}
			ServiceCenter serviceCenter = centerService.findCenterServiceById(item.getCenterId());
			//[1]判断中心实体是否为空
			if(null == serviceCenter){
				JSONObject json = new JSONObject();
				json.put("success","false");
				try {
				    json.write(response.getWriter());
				} catch (IOException e) {
					e.printStackTrace();
					System.err.println("get centerservice error!");
				}//catch end
				return;
			}
			
			JSONObject json = new JSONObject();
			json.put("success","true");
			json.put("name",item.getItemName());
			json.put("weight",String.valueOf(item.getItemWeight()));
			json.put("grade",String.valueOf(item.getItemGrade()));
			json.put("center",serviceCenter.getCenterName());
			json.put("cstyle",item.getItemType());
			json.put("estyle",String.valueOf(item.getEnabled()));
			try {
			    json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("get centerservice error!");
			}//catch end
	}
	/**
	 * 通过一级指标Id获取指标信息，(用于更新update当前Panel的内容 )
	 * 其格式需要特定的形式
	 * Ext Ajax
	 */
	public void getFirstLevelAndBelongSecondLevelPara(){
		
		ServletRequest request = ServletActionContext.getRequest();
		ServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("GB2312");
		response.setContentType("text/html");
		
		//========session获取centerId Start========//
		int userId = SessionUtils.getUserId();
		if(-1 == userId){
			JSONObject json = new JSONObject();
			json.put("success","false");
			json.put("data", "当前session失效，获取窗口一级指标参数！");
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
			json.put("data", "当前session失效，获取窗口一级指标参数！");
			try {
				json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("get center back1 to front error!");
			}
			return;
		}
		centerId = user.getServiceCenter().getCenterId();
		//========session获取centerId End=========//
		
		String centerItemId = request.getParameter("centerItemId");
	    List<WindowItem> result = windowItemService.getFirstLevelAndSecondLevelPara(Integer.valueOf(centerItemId),centerId);
	    WindowItem  firstLevelItem = windowItemService.getFirstLevelparameter(Integer.valueOf(centerItemId));
	    //【0】判断结果信息是否正确
	    if(null == result|| null ==firstLevelItem ){
	    	JSONObject json = new JSONObject();
			json.put("success","false");
			json.put("data","无法获取此Panel的指标信息！");
			try {
			    json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("get centerservice error!");
			}//catch end
			return;
	    }
	    //处理数据，返回前台
	    int idIndex = 1;
		String content = "";
	    for (WindowItem item : result) {
			//添加二级指标信息
			content += "["+String.valueOf(idIndex++)+"] "+item.getItemName()+"<br>";
		}
		content+="【权重】 "+firstLevelItem.getItemWeight();
		JSONObject json = new JSONObject();
		json.put("success","true");
		json.put("data",content);
		json.put("title",firstLevelItem.getItemName());
		try {
		    json.write(response.getWriter());
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("get centerservice error!");
		}//catch end
		
	}
	/**
	 * 更新一级指标数据，（通过一级指标编辑窗口上传）
	 * formPanel update
	 */
	public void updateFirstLevelParameters(){
		
		ServletRequest request = ServletActionContext.getRequest();
		ServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("GB2312");
		response.setContentType("text/html");
		
		//========session获取centerId Start========//
		int userId = SessionUtils.getUserId();
		if(-1 == userId){
			JSONObject json = new JSONObject();
			json.put("success","false");
			json.put("data", "当前session失效，更新窗口一级指标参数！");
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
			json.put("data", "当前session失效，更新窗口一级指标参数！");
			try {
				json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("get center back1 to front error!");
			}
			return;
		}
		centerId = user.getServiceCenter().getCenterId();
		//========session获取centerId End=========//
		
		String centerItemIdStr = request.getParameter("centerItemId");
		//【0】判断指标参数是否为空
		if(null == centerItemIdStr || centerItemIdStr.isEmpty()){
			JSONObject json = new JSONObject();
			json.put("success",false);
			json.put("data","获取指标Id信息错误！");
			try {
			    json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("get centerItemId error!");
			}//catch end
			return;
		}
		String paraname = request.getParameter("efnameW").trim();
		String paraweigth =  request.getParameter("efweightW").trim();
		String paraStyle = request.getParameter("efstyleW").trim();
		String parauseStyle = request.getParameter("efusestyleW").trim();
		
		//【1】判断获取的参数是否为空
		if(null == paraname || null == paraweigth ||null == paraStyle ||null ==parauseStyle){
			JSONObject json = new JSONObject();
			json.put("success",false);
			json.put("data","获取用户编辑指标信息错误！");
			try {
			    json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("get centerItemId error!");
			}//catch end
			return;
		}
		//【2】判断当前更新指标内容是否在数据库中存在(不与自身指标判断自己)
		if(!windowItemService.otherSameNameParaInDatabase(Integer.valueOf(centerItemIdStr),paraname,centerId)){
			JSONObject json = new JSONObject();
		    json.put("success",false);
		    json.put("data","数据库中存在相同名称的指标，不能更新！");
			try {
				json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("store centerpara to front error!");
			}
			return;
		}
		
		//【3】判断获取指标实体类是否为空
		WindowItem item = windowItemService.getFirstLevelparameter(Integer.valueOf(centerItemIdStr));
		if(null == item){
			JSONObject json = new JSONObject();
			json.put("success",false);
			json.put("data","获取编辑实体类错误！");
			try {
			    json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("get centerItemId error!");
			}//catch end
			return;
		}
		//【4】判断指标信息是否发生改变
		boolean paraEnable = false;
		if(parauseStyle.equals("使用"))
			paraEnable = true;
		if(item.getItemName().equals(paraname) && item.getItemWeight().equals(Double.valueOf(paraweigth)) &&
				item.getEnabled() == paraEnable && item.getItemType().equals(paraStyle)){
			JSONObject json = new JSONObject();
			json.put("success",false);
			json.put("data","指标内容并未编辑！");
			try {
			    json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("get centerItemId error!");
			}//catch end
			return;
		}
		
		//【5】判断当前指标内容是否改变,指标内容为panel的id ,所以指标内容改变，其Id相应作出改变
		JSONObject array = new JSONObject();
		if(item.getItemName().equals(paraname)){
			array.put("alter","false");
			array.put("title",paraname);
		}else{
			array.put("alter","true");
			array.put("oldTitle",item.getItemName());
			array.put("newTitle",paraname);
		}
	    //更新指标信息
		boolean result = windowItemService.updateCenterItemById(Integer.valueOf(centerItemIdStr), paraname, Double.valueOf(paraweigth), paraEnable, paraStyle);
		JSONObject json = new JSONObject();
		json.put("success",result);
		json.put("title",array);
		try {
		    json.write(response.getWriter());
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("get centerItemId error!");
		}//catch end
		return;
		
		
		
	}
	
	/**
	 * 存储一级指标数据
	 * FormPanel submit
	 * @return
	 */
	public void storeFirstLevelParaData(){
		
		HttpServletRequest resquest= ServletActionContext.getRequest();
		ServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("GB2312");
		response.setContentType("text/html");
		
		//========session获取centerId Start========//
		int userId = SessionUtils.getUserId();
		if(-1 == userId){
			JSONObject json = new JSONObject();
			json.put("success","false");
			json.put("data", "当前session失效，获取窗口一级指标参数！");
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
			json.put("data", "当前session失效，获取窗口一级指标参数！");
			try {
				json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("get center back1 to front error!");
			}
			return;
		}
		centerId = user.getServiceCenter().getCenterId();
		//========session获取centerId End=========//
		
		String paraName = resquest.getParameter("pnameInputW").trim();
		Double paraWeight = Double.valueOf(resquest.getParameter("pweightInputW").trim());
		String paraStyle = resquest.getParameter("pstyleInputW").trim();
		//[0]判断当前输入指标 在数据库中是否已经存在
		if(!windowItemService.curParaIsInDatabase(paraName,centerId)){
			JSONObject json = new JSONObject();
		    json.put("success",false);
		    json.put("data","数据库中已经存在此指标！");
			try {
				json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("store centerpara to front error!");
			}
			return;
		}

		int   parentId = 0;
		boolean enable = true;
		int paraGrade = 1;
		//centerId此参数需要通过数据库判断 当前中心的Id
		WindowItem item = new WindowItem(paraName, parentId, paraGrade, paraWeight, paraStyle, enable, centerId,null,null,null,null,null);
		int result = windowItemService.storeFirstLevelPara(item);
		
		//往前台传送结果数据,格式为(返回标题paraName;内容'二级指标';id信息result)
		JSONObject json = new JSONObject();
		if(result != -1){
			json.put("success",true);
			json.put("data", paraName+";"+"编辑二级指标"+";"+result);
		}else{
			json.put("success",false);
			json.put("data","指标保存失败！");
		}
		
		try {
			json.write(response.getWriter());
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("store centerpara to front error!");
		}
	}
	
	
	/**
	 * 根据一级指标Id，删除一级指标（连带二级&&三级指标）
	 * @return
	 * ExtJs Ajax
	 */
	public void deleteFirstLevelParams(){
		
		HttpServletRequest request= ServletActionContext.getRequest();
		ServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("GB2312");
		response.setContentType("text/html");
		
		String centerItemIdStr = request.getParameter("centerItemId");
		boolean result = windowItemService.deleteAllFirstLevelPara(Integer.valueOf(centerItemIdStr));
		//往前台传送结果数据,格式为(返回标题paraName;内容'二级指标';id信息result)
		JSONObject json = new JSONObject();
		if(result ){
			json.put("success","true");
			json.put("data", "删除成功！");
		}else{
			json.put("success","false");
			json.put("data","删除失败！");
		}
		
		try {
			json.write(response.getWriter());
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("delete centerpara to front error!");
		}
		
		
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

	public UserServiceImpl getUserService() {
		return userService;
	}

	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}

}
