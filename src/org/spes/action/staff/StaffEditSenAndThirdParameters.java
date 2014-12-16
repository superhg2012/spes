package org.spes.action.staff;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.spes.action.center.SessionUtils;
import org.spes.bean.ServiceCenter;
import org.spes.bean.StaffFormula;
import org.spes.bean.StaffItem;
import org.spes.bean.User;
import org.spes.bean.Window;
import org.spes.service.center.impl.CenterServiceImpl;
import org.spes.service.result.impl.WindowServiceImpl;
import org.spes.service.staff.impl.StaffFormulaServiceImpl;
import org.spes.service.staff.impl.StaffItemServiceImpl;
import org.spes.service.user.impl.UserServiceImpl;

import com.opensymphony.xwork2.ActionSupport;

public class StaffEditSenAndThirdParameters extends ActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//人员服务层链接
	private StaffItemServiceImpl    staffItemService = null;
    //中心服务层
	private CenterServiceImpl        centerService    = null;
	//窗口服务层
	private WindowServiceImpl        windowService    = null;
	// 中心公式接口
	private StaffFormulaServiceImpl staffFormulaService = null;
	//用户服务层
	public UserServiceImpl	      userService    = null;
	//当前一级指标的Id信息（用户选择不同的一级指标时候更新）
	private String     curFirstLevelParaId = null;
    private String     start = null;
    private String     end   = null;
	
    private int centerId = 0;
    private int windowId = 0;
    //获取二级&三级指标信息
    public void getSecAndThirdItemsInfo(){
	
		ServletRequest request = ServletActionContext.getRequest();
		ServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("GB2312");
		response.setContentType("text/html");
		
		//========session获取centerId Start==========================================//
		int userId = SessionUtils.getUserId();
		if(-1 == userId){
			//获取总数据量空间大小
			JSONObject total = new JSONObject();
			JSONArray aray = new JSONArray();
			total.put("totalProperty",0);
			total.put("root", aray);
			try {
			    total.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("当前session失效,获取二级&&三级指标!");
			}//catch end
			return;
		}
		User user = userService.findbyUserId(userId);
		if(null == user){
			//获取总数据量空间大小
			JSONObject total = new JSONObject();
			JSONArray aray = new JSONArray();
			total.put("totalProperty",0);
			total.put("root", aray);
			try {
			    total.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("当前session失效,获取二级&&三级指标!");
			}//catch end
			return;
		}
		centerId = user.getServiceCenter().getCenterId();
		windowId = user.getWindow().getWindowId();
		
		ServiceCenter center = centerService.findCenterServiceById(centerId);
		//[1]判断中心实体是否为空
		if(null == center){
			//获取总数据量空间大小
			JSONObject total = new JSONObject();
			JSONArray aray = new JSONArray();
			total.put("totalProperty",0);
			total.put("root", aray);
			try {
			    total.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("当前session失效,获取二级&&三级指标!");
			}//catch end
			return;
		}
		Window window = windowService.findWindowById(windowId);
		if(null == window){	
		//获取总数据量空间大小
			JSONObject total = new JSONObject();
			JSONArray aray = new JSONArray();
			total.put("totalProperty",0);
			total.put("root", aray);
			try {
			    total.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("当前session失效,获取二级&&三级指标!");
			}//catch end
			return;
		}
		//========session获取centerId End========================================//

		//【0.0】获取当前一级指标Id信息（仅在用户第一次点击一级指标面板时候，赋值）
		String paraId = request.getParameter("paraId");
		if(null != paraId){
			curFirstLevelParaId = paraId;
		}
		
		String frontStart = request.getParameter("start");
		if(null != frontStart)start = frontStart;
		String frontEnd = request.getParameter("limit");
		if(null != frontEnd) end =frontEnd;
		
		
		//【0】判断前台传递参数、start、end是否为空
		if( null == curFirstLevelParaId || null == start || null == end || start.isEmpty()||end.isEmpty()|| curFirstLevelParaId.isEmpty()){
			//获取总数据量空间大小
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
		
		//【1】获取指标数据
		ArrayList<StaffItem> itemData = this.staffItemService.getSecondAndThridParameters(Integer.valueOf(curFirstLevelParaId));
		if(null == itemData || itemData.size() ==0){
			//获取总数据量空间大小
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
		
		//获取总数据量空间大小
		JSONObject total = new JSONObject();
		total.put("totalProperty",itemData.size());
		//获取总数据（Backup1 用于存放当前二级指标的顺序Id，在Dao层读取数据时已经添加）
		JSONArray aray = new JSONArray();
		int itemStart = Integer.valueOf(start);
		int itemEnd   = Integer.valueOf(end);
		for (int i = itemStart; i < itemStart+itemEnd;  i++) {
			//超过itemData数据总量，就返回不做循环处理
			if(i >= itemData.size() )continue;
			StaffItem item = itemData.get(i);
			JSONObject obj = new JSONObject();
			//全部返回前台指标信息 均加入数据库Id,以及父类Id
			obj.put("itemDBId",item.getItemId());
			obj.put("itemParentId", item.getParentId());
			
			//二级指标均有Id信息，三级指标无Id信息
			if(item.getItemGrade().equals(Integer.valueOf(2))){
				obj.put("itemId",item.getBackup1());
				//（二级指标添加公式信息）
				List<StaffFormula> formulas = staffFormulaService.getFormulaByItemId(item.getItemId());
				if(null != formulas && formulas.size() == 1){
                   obj.put("itemFormula",formulas.get(0).getCaclulator());
                   obj.put("itemFormulaId",String.valueOf(formulas.get(0).getFormulaId()));
				}else{
					
					obj.put("itemFormula","无公式");
					obj.put("itemFormulaId","-1");
				}
			}else{
				obj.put("itemId","");
				obj.put("itemFormula","");
				obj.put("itemFormulaId","");
			}
			//指标名称
			obj.put("itemName",item.getItemName());
			//指标等级信息
			if(item.getItemGrade().equals(Integer.valueOf(2))){
				obj.put("itemGrade","<b>"+"二级指标"+"</b>");	
			}else{
				obj.put("itemGrade","三级指标");
			}
			//指标权重
			obj.put("itemWeight", item.getItemWeight());
			//指标是否使用
			if(item.getEnabled()){
				obj.put("itemEnable","使用");
			}else{
				obj.put("itemEnable","禁用");
			}
			obj.put("itemType", item.getItemType());
			obj.put("itemCenter",center.getCenterName());
			obj.put("itemWindow",window.getWindowName());
			//添加指标信息
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
	//获取三级指标信息
    public void getCurThirdPara(){
    	
    	ServletRequest request = ServletActionContext.getRequest();
		ServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("GB2312");
		response.setContentType("text/html");
		
		//【0.0】获取当前二级指标Id信息
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
    	List<StaffItem> result = staffItemService.getCurThirdParas(Integer.valueOf(paraId));
    	//【0.2】判断获取二级指标是否为空
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
    	//返回前台数据
    	JSONArray array = new JSONArray();
    	for (StaffItem centerItem : result) {
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
    
    

	public CenterServiceImpl getCenterService() {
		return centerService;
	}
	public void setCenterService(CenterServiceImpl centerService) {
		this.centerService = centerService;
	}
	public StaffItemServiceImpl getStaffItemService() {
		return staffItemService;
	}
	public void setStaffItemService(StaffItemServiceImpl staffItemService) {
		this.staffItemService = staffItemService;
	}
	public WindowServiceImpl getWindowService() {
		return windowService;
	}
	public void setWindowService(WindowServiceImpl windowService) {
		this.windowService = windowService;
	}
	public StaffFormulaServiceImpl getStaffFormulaService() {
		return staffFormulaService;
	}
	public void setStaffFormulaService(StaffFormulaServiceImpl staffFormulaService) {
		this.staffFormulaService = staffFormulaService;
	}
	public UserServiceImpl getUserService() {
		return userService;
	}
	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}

    
	
}
