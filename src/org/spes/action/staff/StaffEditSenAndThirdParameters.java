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
	//��Ա���������
	private StaffItemServiceImpl    staffItemService = null;
    //���ķ����
	private CenterServiceImpl        centerService    = null;
	//���ڷ����
	private WindowServiceImpl        windowService    = null;
	// ���Ĺ�ʽ�ӿ�
	private StaffFormulaServiceImpl staffFormulaService = null;
	//�û������
	public UserServiceImpl	      userService    = null;
	//��ǰһ��ָ���Id��Ϣ���û�ѡ��ͬ��һ��ָ��ʱ����£�
	private String     curFirstLevelParaId = null;
    private String     start = null;
    private String     end   = null;
	
    private int centerId = 0;
    private int windowId = 0;
    //��ȡ����&����ָ����Ϣ
    public void getSecAndThirdItemsInfo(){
	
		ServletRequest request = ServletActionContext.getRequest();
		ServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("GB2312");
		response.setContentType("text/html");
		
		//========session��ȡcenterId Start==========================================//
		int userId = SessionUtils.getUserId();
		if(-1 == userId){
			//��ȡ���������ռ��С
			JSONObject total = new JSONObject();
			JSONArray aray = new JSONArray();
			total.put("totalProperty",0);
			total.put("root", aray);
			try {
			    total.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("��ǰsessionʧЧ,��ȡ����&&����ָ��!");
			}//catch end
			return;
		}
		User user = userService.findbyUserId(userId);
		if(null == user){
			//��ȡ���������ռ��С
			JSONObject total = new JSONObject();
			JSONArray aray = new JSONArray();
			total.put("totalProperty",0);
			total.put("root", aray);
			try {
			    total.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("��ǰsessionʧЧ,��ȡ����&&����ָ��!");
			}//catch end
			return;
		}
		centerId = user.getServiceCenter().getCenterId();
		windowId = user.getWindow().getWindowId();
		
		ServiceCenter center = centerService.findCenterServiceById(centerId);
		//[1]�ж�����ʵ���Ƿ�Ϊ��
		if(null == center){
			//��ȡ���������ռ��С
			JSONObject total = new JSONObject();
			JSONArray aray = new JSONArray();
			total.put("totalProperty",0);
			total.put("root", aray);
			try {
			    total.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("��ǰsessionʧЧ,��ȡ����&&����ָ��!");
			}//catch end
			return;
		}
		Window window = windowService.findWindowById(windowId);
		if(null == window){	
		//��ȡ���������ռ��С
			JSONObject total = new JSONObject();
			JSONArray aray = new JSONArray();
			total.put("totalProperty",0);
			total.put("root", aray);
			try {
			    total.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("��ǰsessionʧЧ,��ȡ����&&����ָ��!");
			}//catch end
			return;
		}
		//========session��ȡcenterId End========================================//

		//��0.0����ȡ��ǰһ��ָ��Id��Ϣ�������û���һ�ε��һ��ָ�����ʱ�򣬸�ֵ��
		String paraId = request.getParameter("paraId");
		if(null != paraId){
			curFirstLevelParaId = paraId;
		}
		
		String frontStart = request.getParameter("start");
		if(null != frontStart)start = frontStart;
		String frontEnd = request.getParameter("limit");
		if(null != frontEnd) end =frontEnd;
		
		
		//��0���ж�ǰ̨���ݲ�����start��end�Ƿ�Ϊ��
		if( null == curFirstLevelParaId || null == start || null == end || start.isEmpty()||end.isEmpty()|| curFirstLevelParaId.isEmpty()){
			//��ȡ���������ռ��С
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
		
		//��1����ȡָ������
		ArrayList<StaffItem> itemData = this.staffItemService.getSecondAndThridParameters(Integer.valueOf(curFirstLevelParaId));
		if(null == itemData || itemData.size() ==0){
			//��ȡ���������ռ��С
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
		
		//��ȡ���������ռ��С
		JSONObject total = new JSONObject();
		total.put("totalProperty",itemData.size());
		//��ȡ�����ݣ�Backup1 ���ڴ�ŵ�ǰ����ָ���˳��Id����Dao���ȡ����ʱ�Ѿ���ӣ�
		JSONArray aray = new JSONArray();
		int itemStart = Integer.valueOf(start);
		int itemEnd   = Integer.valueOf(end);
		for (int i = itemStart; i < itemStart+itemEnd;  i++) {
			//����itemData�����������ͷ��ز���ѭ������
			if(i >= itemData.size() )continue;
			StaffItem item = itemData.get(i);
			JSONObject obj = new JSONObject();
			//ȫ������ǰָ̨����Ϣ ���������ݿ�Id,�Լ�����Id
			obj.put("itemDBId",item.getItemId());
			obj.put("itemParentId", item.getParentId());
			
			//����ָ�����Id��Ϣ������ָ����Id��Ϣ
			if(item.getItemGrade().equals(Integer.valueOf(2))){
				obj.put("itemId",item.getBackup1());
				//������ָ����ӹ�ʽ��Ϣ��
				List<StaffFormula> formulas = staffFormulaService.getFormulaByItemId(item.getItemId());
				if(null != formulas && formulas.size() == 1){
                   obj.put("itemFormula",formulas.get(0).getCaclulator());
                   obj.put("itemFormulaId",String.valueOf(formulas.get(0).getFormulaId()));
				}else{
					
					obj.put("itemFormula","�޹�ʽ");
					obj.put("itemFormulaId","-1");
				}
			}else{
				obj.put("itemId","");
				obj.put("itemFormula","");
				obj.put("itemFormulaId","");
			}
			//ָ������
			obj.put("itemName",item.getItemName());
			//ָ��ȼ���Ϣ
			if(item.getItemGrade().equals(Integer.valueOf(2))){
				obj.put("itemGrade","<b>"+"����ָ��"+"</b>");	
			}else{
				obj.put("itemGrade","����ָ��");
			}
			//ָ��Ȩ��
			obj.put("itemWeight", item.getItemWeight());
			//ָ���Ƿ�ʹ��
			if(item.getEnabled()){
				obj.put("itemEnable","ʹ��");
			}else{
				obj.put("itemEnable","����");
			}
			obj.put("itemType", item.getItemType());
			obj.put("itemCenter",center.getCenterName());
			obj.put("itemWindow",window.getWindowName());
			//���ָ����Ϣ
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
	//��ȡ����ָ����Ϣ
    public void getCurThirdPara(){
    	
    	ServletRequest request = ServletActionContext.getRequest();
		ServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("GB2312");
		response.setContentType("text/html");
		
		//��0.0����ȡ��ǰ����ָ��Id��Ϣ
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
    	//��0.2���жϻ�ȡ����ָ���Ƿ�Ϊ��
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
    	//����ǰ̨����
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
