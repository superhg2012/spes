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
 * �����ƣ����Ĺ�����
 * @author DJ
 */
public class CenterManageAction extends ActionSupport implements SessionAware, ServletResponseAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**����ɾ����ID��*/
	private Integer delIds;
	
	public Integer getDelIds() {
		return delIds;
	}

	public void setDelIds(Integer delIds) {
		this.delIds = delIds;
	}

	/**ɾ������id*/
	private Integer delServiceId ;
	
	/**�޸�����id*/
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

	/**ע��session*/
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

	/**ע��resonse*/
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	/**��ȡ���еķ���������Ϣ
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

	/**ͨ��idɾ��������Ϣ*/
	public void deleteServiceCenterbyId(){
		System.out.println("deleteServiceCenterbyId:"+delIds);
		
		centerInfoManageService.deleteServiceCenter(delIds);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		JSONObject json = new JSONObject();
		json.put("resptext", "ɾ��������Ϣ�ɹ���");
		json.put("success", true);
//		return "serviceInfo";
	}
	
	/**��ת���޸�ҳ��
	 * @throws IOException */
	public void toEditView() throws IOException{
		System.out.println("toEditView..");
		System.out.println("editServiceId:"+editServiceId);
		ServiceCenter sc = centerInfoManageService.getServiceCenterById(editServiceId);//��ȡָ����id������Ϣ
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		JSONObject json = new JSONObject();
		/*json = JSONObject.fromObject(sc);
		json.put("data", json.toString());
		json.write(response.getWriter());*/
		System.out.println(json.toString());
	}
	
	/**�޸�������Ϣ*/
	@Deprecated
	public String editServiceCenter(){
		//ͨ��editCenterId����������Ϣ����json���ص��༭ҳ��
		response.reset();
		System.out.println("editServiceCenter...");
		ServiceCenter cerviceCenter = centerInfoManageService.getServiceCenterById(editServiceId);
		System.out.println("editServiceId:"+editServiceId);
		System.out.println("cerviceCenter:"+cerviceCenter);
		
		centerInfoManageService.updateSeraviceCenter(cerviceCenter);
		System.out.println("����");
		//Ȼ���ȡҳ����Ϣ�����޸�
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
