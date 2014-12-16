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
	
	//WindowItem服务层链接
	private WindowItemServiceImpl    windowItemService = null;
    //centerService服务层
	private CenterServiceImpl        centerService    = null;
	// 中心公式接口
	private WindowFormulaServiceImpl windowFormulaService = null;
	//当前一级指标的Id信息（用户选择不同的一级指标时候更新）
	private String     curFirstLevelParaId = null;
    private String     start = null;
    private String     end   = null;
	
    //获取二级&三级指标信息
    public void getSecAndThirdItemsInfo(){
	
		ServletRequest request = ServletActionContext.getRequest();
		ServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("GB2312");
		response.setContentType("text/html");
		
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
		ArrayList<WindowItem> itemData = this.windowItemService.getSecondAndThridParameters(Integer.valueOf(curFirstLevelParaId));
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
		
		//【2】获取当前中心信息
		ServiceCenter service = this.centerService.findCenterServiceById(itemData.get(0).getCenterId());
		if(null == service){
			
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
			WindowItem item = itemData.get(i);
			JSONObject obj = new JSONObject();
			//全部返回前台指标信息 均加入数据库Id,以及父类Id
			obj.put("itemDBId",item.getItemId());
			obj.put("itemParentId", item.getParentId());
			
			//二级指标均有Id信息，三级指标无Id信息
			if(item.getItemGrade().equals(Integer.valueOf(2))){
				obj.put("itemId",item.getBackup1());
				//（二级指标添加公式信息）
				List<WindowFormula> formulas = windowFormulaService.getFormulaByItemId(item.getItemId());
				if(null != formulas && formulas.size() == 1){
                   obj.put("itemFormula",formulas.get(0).getCalculator());
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
			obj.put("itemCenter",service.getCenterName());
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
    	List<WindowItem> result = windowItemService.getCurThirdParas(Integer.valueOf(paraId));
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
