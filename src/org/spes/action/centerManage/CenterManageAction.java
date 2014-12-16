package org.spes.action.centerManage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.spes.bean.ServiceCenter;
import org.spes.service.centerManage.CenterInfoManageService;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 类名称：中心管理类
 * @author DJ
 */
public class CenterManageAction extends ActionSupport implements SessionAware, ServletResponseAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**所有删除的ID号*/
	private Integer delIds;
	
	public Integer getDelIds() {
		return delIds;
	}

	public void setDelIds(Integer delIds) {
		this.delIds = delIds;
	}

	/**删除中心id*/
	private Integer delServiceId ;
	
	/**修改中心id*/
	private Integer editServiceId;
	
	public Integer getDelServiceId() {
		return delServiceId;
	}

	public void setDelServiceId(Integer delServiceId) {
		this.delServiceId = delServiceId;
	}

	public Integer getEditServiceId() {
		return editServiceId;
	}

	public void setEditServiceId(Integer editServiceId) {
		this.editServiceId = editServiceId;
	}

	private int start;

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	private int limit; 
	
	private Map<String, Object> mapSession  = null;
	
	private HttpServletResponse response = null;
	
	private String changes  = null;
	
	public String getChanges() {
		return changes;
	}

	public void setChanges(String changes) {
		this.changes = changes;
	}

	/**注入session*/
	public void setSession(Map<String, Object> sessionMap) {
		this.mapSession = sessionMap;
	}
	
	private CenterInfoManageService centerInfoManageService = null;
	
	public CenterInfoManageService getCenterInfoManageService() {
		return centerInfoManageService;
	}

	public void setCenterInfoManageService(
			CenterInfoManageService centerInfoManageService) {
		this.centerInfoManageService = centerInfoManageService;
	}

	/**注入resonse*/
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	/**获取所有的服务中心信息
	 * @throws IOException */
	public void listServiceCenter() throws IOException{
		System.out.println("listServiceCenter");
		System.out.println(start+":"+limit);
		List<ServiceCenter> list = centerInfoManageService.getServiceCenterInfo();
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		if(list.size() > 0) {
			List<ServiceCenter> subList = null;
			if((start + limit) > list.size()){
				subList = list.subList(start, list.size());
			} else {
				subList = list.subList(start, limit);
			}
			JSONArray array = new JSONArray();
			for(int i = 0; i< subList.size(); i++){
				ServiceCenter sv = subList.get(i);
				JSONObject json = new JSONObject();
				json.put("centerId", sv.getCenterId());
				json.put("centerName", sv.getCenterName());
				json.put("organcode", sv.getOrgancode());
				json.put("province", sv.getProvince());
				json.put("city", sv.getCity());
				json.put("county", sv.getCounty());
				json.put("contact", sv.getContact());
				json.put("email", sv.getEmail());
				json.put("linkman", sv.getLinkman());
				json.put("legalrepresent", sv.getLegalrepresent());
				array.add(json);
			}
			
			JSONObject json = new JSONObject();
			json.put("total", list.size());
			json.put("data", array.toString());
			System.out.println("---json"+array.toString());
			json.write(response.getWriter());
			/*JSONArray array = JSONArray.fromObject(subList);
			System.out.println("json:"+array.toString());
			JSONObject jObj = new JSONObject();
			jObj.put("data", array.toString());
			jObj.put("total",list.size());
			try {
				jObj.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
			}*/
		}
	}

	/**通过id删除中心信息*/
	public void deleteServiceCenterbyId(){
		System.out.println("deleteServiceCenterbyId:"+delIds);
		
		centerInfoManageService.deleteServiceCenter(delIds);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		JSONObject json = new JSONObject();
		json.put("resptext", "删除中心信息成功！");
		json.put("success", true);
//		return "serviceInfo";
	}
	
	/**跳转到修改页面
	 * @throws IOException */
	public void toEditView() throws IOException{
		System.out.println("toEditView..");
		System.out.println("editServiceId:"+editServiceId);
		ServiceCenter sc = centerInfoManageService.getServiceCenterById(editServiceId);//获取指定的id中心信息
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		JSONObject json = new JSONObject();
		/*json = JSONObject.fromObject(sc);
		json.put("data", json.toString());
		json.write(response.getWriter());*/
		System.out.println(json.toString());
	}
	
	/**修改中心信息*/
	@Deprecated
	public String editServiceCenter(){
		//通过editCenterId查找中心信息，以json返回到编辑页面
		response.reset();
		System.out.println("editServiceCenter...");
		ServiceCenter cerviceCenter = centerInfoManageService.getServiceCenterById(editServiceId);
		System.out.println("editServiceId:"+editServiceId);
		System.out.println("cerviceCenter:"+cerviceCenter);
		
		centerInfoManageService.updateSeraviceCenter(cerviceCenter);
		System.out.println("更新");
		//然后获取页面信息进行修改
		return "serviceInfo";
	}
	
	
	public void saveCenterModify(){
		System.out.println("-------");
		System.out.println(changes);
		if(changes != null){
			JSONArray jsonArray = new JSONArray();
			List<ServiceCenter> services = new ArrayList<ServiceCenter>();
			jsonArray = JSONArray.fromObject(changes);
			for(int i = 0; i < jsonArray.size(); i++){
				JSONObject object = (JSONObject)jsonArray.get(i);  
				ServiceCenter sc = (ServiceCenter)JSONObject.toBean(object,ServiceCenter.class);
	            if(sc != null){
	            	services.add(sc);
			    }
			}
			System.out.println(services.size());
			centerInfoManageService.saveModified(services);
		} else{
			System.out.println(changes == null);
		}
		
	}
	
}
