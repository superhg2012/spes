package org.spes.action.centerManage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.spes.bean.BdCity;
import org.spes.bean.BdCounty;
import org.spes.bean.BdProvince;
import org.spes.bean.ServiceCenter;
import org.spes.bean.ServiceCenterAudit;
import org.spes.service.centerManage.CenterRegisterService;

import com.opensymphony.xwork2.ActionSupport;

/**
 * �����ƣ�����ע����
 * @author DJ
 */
public class CenterRegisterAction extends ActionSupport implements SessionAware,ServletResponseAware{

	private static final long serialVersionUID = 1L;
	
	/**sessionע��*/
	Map<String, Object> mapSession  = null;
	
	/**ע��ҳ��ͱ༭ҳ��ʹ�õ���ͬһҳ�棬��ȡ���������ֵ*/
	private String edit ;
	
	public String getEdit() {
		return edit;
	}

	public void setEdit(String edit) {
		this.edit = edit;
	}

	private int start ; //��ʼ��ҳ
	
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

	private int limit;	//����ҳ��
	
	private Integer id ;
	
	private Integer centerId;
	
	private String centerName;
	
	private String province;
	
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	private String city;
	
	private String county;
	
	private String organcode;
	
	private String linkman;
	
	private String contact ;
	
	private String email;
	
	private String valid ;
	
	private String legalrepresent ;
	
	private String remarks ;
	
	/**��ɾ��ע���¼ID*/
	private Integer delAuditId;
	
	public Integer getDelAuditId() {
		return delAuditId;
	}

	public void setDelAuditId(Integer delAuditId) {
		this.delAuditId = delAuditId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCenterId() {
		return centerId;
	}

	public void setCenterId(Integer centerId) {
		this.centerId = centerId;
	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getOrgancode() {
		return organcode;
	}

	public void setOrgancode(String organcode) {
		this.organcode = organcode;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public String getLegalrepresent() {
		return legalrepresent;
	}

	public void setLegalrepresent(String legalrepresent) {
		this.legalrepresent = legalrepresent;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	/**���Ĺ�������*/
	private CenterRegisterService centerRegisterService = null;

	/**ע��response*/
	HttpServletResponse response = null;
	
	public CenterRegisterService getCenterRegisterService() {
		return centerRegisterService;
	}

	public void setCenterRegisterService(CenterRegisterService centerManageService) {
		this.centerRegisterService = centerManageService;
	}

	/**responseע��*/
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	/**��ȡ����ʡ��*/
	public void getAllProvince(){
		List<BdProvince> list = centerRegisterService.getAll();
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		JSONArray array = new JSONArray();
		array = JSONArray.fromObject(list);
		JSONObject json = new JSONObject();
		json.put("root",array.toString());
		try {
			json.write(response.getWriter());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(json.toString());
	}
	
	/**ͨ��ʡ�ݱ�Ż�ȡ��������*/
	public void findCitiesByProvince(){
		String provinceCode = centerRegisterService.getProvinceCodeByName(province);
		List<BdCity> list = centerRegisterService.getCitiedByProvince(provinceCode);
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		JSONArray array = new JSONArray();
		array = JSONArray.fromObject(list);
		JSONObject json = new JSONObject();
		json.put("root",array.toString());
		try {
			json.write(response.getWriter());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**ͨ�����л�ȡ��/��*/
	public void findCountiesByCity(){
		List<BdCounty> list = centerRegisterService.getCountiesByCity(city);
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		JSONArray array = new JSONArray();
		array = JSONArray.fromObject(list);
		JSONObject json = new JSONObject();
		json.put("root",array.toString());
		try {
			json.write(response.getWriter());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**����һ������ע����Ϣ*/ 
	public void addCenterServiceRegister(){
		ServiceCenterAudit serviceCenterAudit = new ServiceCenterAudit();
		serviceCenterAudit.setCenterId(centerId);
		serviceCenterAudit.setCenterName(centerName);
		serviceCenterAudit.setProvince(province);
		serviceCenterAudit.setCity(city);
		serviceCenterAudit.setCounty(county);
		serviceCenterAudit.setOrgancode(organcode);
		serviceCenterAudit.setLinkman(linkman);
		serviceCenterAudit.setContact(contact);
		serviceCenterAudit.setEmail(email);
		serviceCenterAudit.setLegalrepresent(legalrepresent);
		serviceCenterAudit.setRemarks(remarks);
		
		/*֮ǰ�ж��û��Ľ�ɫ*/
		String role = (String)mapSession.get("roleId");
		int result = -1;
		if("1".equals(role)){
			serviceCenterAudit.setValid("true");//��������Աע�ᣬ���ͨ��
			int result1 = centerRegisterService.saveCenterRegister(serviceCenterAudit);
			ServiceCenter serviceCenter = new ServiceCenter();
			serviceCenter.setCenterId(centerId);
			serviceCenter.setCenterName(centerName);
			serviceCenter.setProvince(province);
			serviceCenter.setCity(city);
			serviceCenter.setCounty(county);
			serviceCenter.setOrgancode(organcode);
			serviceCenter.setLinkman(linkman);
			serviceCenter.setContact(contact);
			serviceCenter.setEmail(email);
			serviceCenter.setLegalrepresent(legalrepresent);
			serviceCenter.setRemarks(remarks);
			int result2 = centerRegisterService.saveCenterInfo(serviceCenter);
			
			if(result1 != -1 && result2!= -1)
				result = 1;
		} else{
			serviceCenterAudit.setValid("0");//�����û�ע�ᣬ���δͨ��
			result = centerRegisterService.saveCenterRegister(serviceCenterAudit);
		}
		
		System.out.println(result);
		JSONObject json = new JSONObject();
		JSONObject js = new JSONObject();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		if(result!=-1){
			js.put("resptext", "ע��ɹ���");
			json.put("success", true);
			json.put("data", js.toString());
		} else {
			js.put("resptext", "ע��ʧ�ܣ�");
			json.put("success", false);
			json.put("data", js.toString());
		}
		System.out.println(json.toString());
		try {
//			response.getWriter().write(json.toString());
			json.write(response.getWriter());
		} catch (IOException e) {
			e.printStackTrace();
		}
		//return "listRegister";					//����ע����Ϣ
	}
	
	/**��ת�������ҳ��*/
	public String toAuditInfo(){
		return "auditInfo";
	}
	
	/**��ת����ע��ҳ��*/
	public String toRegisterView(){
		return "toRegister";
	}
	
	/**������������ע��ҳ��*/
	public void listAuditInfo(){
		List<ServiceCenterAudit> list = centerRegisterService.findAllAuditInfo();
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		if(list.size() > 0) {
			List<ServiceCenterAudit> subList = null;
			if((start + limit) > list.size()){
				subList = list.subList(start, list.size());
			} else {
				subList = list.subList(start, limit);
			}
			
			JSONArray array = JSONArray.fromObject(subList);
			JSONObject jObj = new JSONObject();
			jObj.put("data", array.toString());
			jObj.put("total",list.size());
			try {
				jObj.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**ɾ��ָ����ע����Ϣ*/
	@Deprecated
	public String deleteAuditInfo(){
		centerRegisterService.deletAuditById(delAuditId);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		JSONObject json = new JSONObject();
		json.put("resptext", "ɾ������ע��ɹ���");
		json.put("success", true);
		return "auditInfo";
	}
	
	/**���ͨ��*/
	public void passAudit(){
		System.out.println("���ͨ����"+delAuditId);
		centerRegisterService.passAudit(delAuditId);
		ServiceCenterAudit sca = centerRegisterService.findServiceAuditById(delAuditId);
		System.out.println("ServiceCenterAudit:"+sca == null ? false : true);
		ServiceCenter sv = new ServiceCenter();
		//sv.setCenterId(sca.getCenterId()); ����ע����е�centerId��s
		sv.setCenterName(sca.getCenterName());
		sv.setProvince(sca.getProvince());
		sv.setCity(sca.getCity());
		sv.setCounty(sca.getCounty());
		sv.setContact(sca.getCounty());
		sv.setLinkman(sca.getLinkman());
		sv.setEmail(sca.getEmail());
		sv.setOrgancode(sca.getOrgancode());
		sv.setLegalrepresent(sca.getLegalrepresent());
		sv.setRemarks(sca.getRemarks());
		System.out.println("ServiceCenter:"+sv == null ? false : true);
		centerRegisterService.saveCenterInfo(sv);//��������
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		JSONObject json = new JSONObject();
		json.put("resptext", "���ͨ����");
		json.put("success", true);
	}
	
	/**
	 * ����û��Ƿ����
	 * @throws IOException 
	 */
	public void checkCenter() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String name = request.getParameter(centerName);
		ServiceCenterAudit sc = centerRegisterService.findByName(centerName);//ͨ��ע�����Ʋ���ע���
		System.out.println(sc == null ? true : true);
		if (null != sc) {
			response.getWriter().write("true");
		} else {
			response.getWriter().write("false");
		}
	}
	
	/**sessionע��*/
	public void setSession(Map<String, Object> map) {
		mapSession = map;
	}

	
}
