package org.spes.action.staff;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.omg.CORBA.Request;
import org.spes.action.center.SessionUtils;
import org.spes.bean.CenterItem;
import org.spes.bean.ServiceCenter;
import org.spes.bean.User;
import org.spes.bean.StaffItem;
import org.spes.bean.Window;
import org.spes.service.center.impl.CenterItemServiceImpl;
import org.spes.service.center.impl.CenterServiceImpl;
import org.spes.service.result.impl.WindowServiceImpl;
import org.spes.service.staff.impl.StaffItemServiceImpl;
import org.spes.service.user.impl.UserServiceImpl;

import com.opensymphony.xwork2.ActionSupport;

public class StaffFirstLevelParaAction extends ActionSupport{

	//���ڷ��������
	public StaffItemServiceImpl  staffItemServie = null;
    //���ķ����
	public CenterServiceImpl      centerService    = null;
	//���ڷ����
	public WindowServiceImpl      windowService    = null;
	//�û������
	public UserServiceImpl	      userService    = null;
	//���ģ�����Id��Ϣ
	private int centerId = 0;
	private int windowId = 0;
	/**
	 * ��ȡ һ�������ָ������
	 * Ext Ajax
	 * @return
	 */

	public void getFirstSecondParaData(){
		

		ServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("GB2312");
		response.setContentType("text/html");
		//��ǰ����Id��ͨ����ǰ��¼�û����ƻ����û���ɫ�������ݿ��в���
		//========session��ȡcenterId Start========//
		int userId = SessionUtils.getUserId();
		if(-1 == userId){
			JSONObject json = new JSONObject();
			json.put("success","false");
			json.put("data", "��ǰsessionʧЧ����ȡ����ָ�������");
			try {
				json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("get center back1 to front error!");
			}
			return;
			
		}
		User user = userService.findbyUserId(userId);
		if(null == user){
			JSONObject json = new JSONObject();
			json.put("success","false");
			json.put("data", "��ǰsessionʧЧ����ȡ����ָ�������");
			try {
				json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("get center back1 to front error!");
			}
			return;
		}
		centerId = user.getServiceCenter().getCenterId();
		windowId = user.getWindow().getWindowId();
		//========session��ȡcenterId End=========//
		
		List<StaffItem> item    = staffItemServie.getAllParameterData(centerId,windowId);
		ServiceCenter  serCenter = centerService.findCenterServiceById(centerId);
        Window         window    = windowService.findWindowById(windowId);
		
		//[0.1]�жϻ�ȡ������������Ƿ�Ϊ��
		if(null == item || item.size() ==0 ){
			JSONObject obj = new JSONObject();
			obj.put("success","false");
			obj.put("data","�޷������ݿ����ָ����Ϣ��");
			try {
				obj.write(response.getWriter());
				return;
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("transfer centerpara1 to front error!");
				return;
			}
		}
		//[0.2]�ж�����ʵ���Ƿ�Ϊ��
		else if(null == serCenter || null == window){
			JSONObject obj = new JSONObject();
			obj.put("success","false");
			obj.put("data","�޷������ݿ����������Ϣ��");
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
			//json����
			JSONArray jsonArray = new JSONArray();
			for (StaffItem centerItem : item) {

				if(null!= centerItem.getItemGrade() && firstGrade == centerItem.getItemGrade()){
					JSONObject json = new JSONObject();
					selfItemId = centerItem.getItemId();
					//���һ��Title��Ϣ
					json.put("title", centerItem.getItemName());
					json.put("titleId",String.valueOf(centerItem.getItemId()));
					//�ڽ�������в�ѯ ����idΪselfItemId
					int idIndex = 1;
					String content = "";
					for (StaffItem centerItemInner : item) {
						if(selfItemId == centerItemInner.getParentId()){
							//��Ӷ���ָ����Ϣ
							content += "["+String.valueOf(idIndex++)+"] "+centerItemInner.getItemName()+"<br>";
						}
					}//for end
					content+="��Ȩ�ء� "+centerItem.getItemWeight();
	                json.put("content",content);
	                jsonArray.add(json);
	                
				}else{
					// do nothing
				}
			}//for end
			
		    JSONObject resultJson = new JSONObject();
		    resultJson.put("success", "true");
		    resultJson.put("data",jsonArray);
		    resultJson.put("centerName",serCenter.getCenterName());
		    resultJson.put("windowName",window.getWindowName());
			
		    try {
		    	resultJson.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("transfer centerpara to front error!");
			}//catch end
		}//else end
		
	}
	
	/**
	 * ͨ��һ��ָ��Id����ȡָ����Ϣ,(���ڱ༭һ��ָ����Ϣ���ڻ�ȡָ����Ϣ)
	 */
	public void getFirstLevelParaById(){
			
			ServletRequest request = ServletActionContext.getRequest();
			ServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("GB2312");
			response.setContentType("text/html");
			
			
			String  centerItemIdStr = request.getParameter("centerItemId");
			StaffItem item = staffItemServie.getFirstLevelparameter(Integer.valueOf(centerItemIdStr));
			//[0]�ж�ָ��ʵ���Ƿ�Ϊ��
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
			//[1]�ж�����ʵ���Ƿ�Ϊ��
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
			Window window = windowService.findWindowById(item.getWindowId());
			//[2]�жϴ���ʵ���Ƿ�Ϊ��
			if(null == window){
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
			json.put("window",window.getWindowName());
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
	 * ͨ��һ��ָ��Id��ȡָ����Ϣ��(���ڸ���update��ǰPanel������ )
	 * ���ʽ��Ҫ�ض�����ʽ
	 * Ext Ajax
	 */
	public void getFirstLevelAndBelongSecondLevelPara(){
		
		ServletRequest request = ServletActionContext.getRequest();
		ServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("GB2312");
		response.setContentType("text/html");
		
		//========session��ȡcenterId Start========//
		int userId = SessionUtils.getUserId();
		if(-1 == userId){
			JSONObject json = new JSONObject();
			json.put("success","false");
			json.put("data", "��ǰsessionʧЧ����ȡ����һ��ָ�������");
			try {
				json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("get center back1 to front error!");
			}
			return;
			
		}
		User user = userService.findbyUserId(userId);
		if(null == user){
			JSONObject json = new JSONObject();
			json.put("success","false");
			json.put("data", "��ǰsessionʧЧ����ȡ����һ��ָ�������");
			try {
				json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("get center back1 to front error!");
			}
			return;
		}
		centerId = user.getServiceCenter().getCenterId();
		windowId = user.getWindow().getWindowId();
		//========session��ȡcenterId End=========//
		
		String centerItemId = request.getParameter("centerItemId");
	    List<StaffItem> result = staffItemServie.getFirstLevelAndSecondLevelPara(Integer.valueOf(centerItemId),centerId,windowId);
	    StaffItem  firstLevelItem = staffItemServie.getFirstLevelparameter(Integer.valueOf(centerItemId));
	    //��0���жϽ����Ϣ�Ƿ���ȷ
	    if(null == result|| null ==firstLevelItem ){
	    	JSONObject json = new JSONObject();
			json.put("success","false");
			json.put("data","�޷���ȡ��Panel��ָ����Ϣ��");
			try {
			    json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("get centerservice error!");
			}//catch end
			return;
	    }
	    //�������ݣ�����ǰ̨
	    int idIndex = 1;
		String content = "";
	    for (StaffItem item : result) {
			//��Ӷ���ָ����Ϣ
			content += "["+String.valueOf(idIndex++)+"] "+item.getItemName()+"<br>";
		}
		content+="��Ȩ�ء� "+firstLevelItem.getItemWeight();
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
	 * ����һ��ָ�����ݣ���ͨ��һ��ָ��༭�����ϴ���
	 * formPanel update
	 */
	public void updateFirstLevelParameters(){
		
		ServletRequest request = ServletActionContext.getRequest();
		ServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("GB2312");
		response.setContentType("text/html");
		
		//========session��ȡcenterId Start========//
		int userId = SessionUtils.getUserId();
		if(-1 == userId){
			JSONObject json = new JSONObject();
			json.put("success","false");
			json.put("data", "��ǰsessionʧЧ�����´���һ��ָ�������");
			try {
				json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("get center back1 to front error!");
			}
			return;
			
		}
		User user = userService.findbyUserId(userId);
		if(null == user){
			JSONObject json = new JSONObject();
			json.put("success","false");
			json.put("data", "��ǰsessionʧЧ�����´���һ��ָ�������");
			try {
				json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("get center back1 to front error!");
			}
			return;
		}
		centerId = user.getServiceCenter().getCenterId();
		windowId = user.getWindow().getWindowId();
		//========session��ȡcenterId End=========//
		
		String centerItemIdStr = request.getParameter("centerItemId");
		//��0���ж�ָ������Ƿ�Ϊ��
		if(null == centerItemIdStr || centerItemIdStr.isEmpty()){
			JSONObject json = new JSONObject();
			json.put("success",false);
			json.put("data","��ȡָ��Id��Ϣ����");
			try {
			    json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("get centerItemId error!");
			}//catch end
			return;
		}
		String paraname = request.getParameter("efnameS").trim();
		String paraweigth =  request.getParameter("efweightS").trim();
		String paraStyle = request.getParameter("efstyleS").trim();
		String parauseStyle = request.getParameter("efusestyleS").trim();
		
		//��1���жϻ�ȡ�Ĳ����Ƿ�Ϊ��
		if(null == paraname || null == paraweigth ||null == paraStyle ||null ==parauseStyle){
			JSONObject json = new JSONObject();
			json.put("success",false);
			json.put("data","��ȡ�û��༭ָ����Ϣ����");
			try {
			    json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("get centerItemId error!");
			}//catch end
			return;
		}
		//��2���жϵ�ǰ����ָ�������Ƿ������ݿ��д���(��������ָ���ж��Լ�)
		if(!staffItemServie.otherSameNameParaInDatabase(Integer.valueOf(centerItemIdStr),paraname,centerId,windowId)){
			JSONObject json = new JSONObject();
		    json.put("success",false);
		    json.put("data","���ݿ��д�����ͬ���Ƶ�ָ�꣬���ܸ��£�");
			try {
				json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("store centerpara to front error!");
			}
			return;
		}
		
		//��3���жϻ�ȡָ��ʵ�����Ƿ�Ϊ��
		StaffItem item = staffItemServie.getFirstLevelparameter(Integer.valueOf(centerItemIdStr));
		if(null == item){
			JSONObject json = new JSONObject();
			json.put("success",false);
			json.put("data","��ȡ�༭ʵ�������");
			try {
			    json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("get centerItemId error!");
			}//catch end
			return;
		}
		//��4���ж�ָ����Ϣ�Ƿ����ı�
		boolean paraEnable = false;
		if(parauseStyle.equals("ʹ��"))
			paraEnable = true;
		if(item.getItemName().equals(paraname) && item.getItemWeight().equals(Double.valueOf(paraweigth)) &&
				item.getEnabled() == paraEnable && item.getItemType().equals(paraStyle)){
			JSONObject json = new JSONObject();
			json.put("success",false);
			json.put("data","ָ�����ݲ�δ�༭��");
			try {
			    json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("get centerItemId error!");
			}//catch end
			return;
		}
		
		//��5���жϵ�ǰָ�������Ƿ�ı�,ָ������Ϊpanel��id ,����ָ�����ݸı䣬��Id��Ӧ�����ı�
		JSONObject array = new JSONObject();
		if(item.getItemName().equals(paraname)){
			array.put("alter","false");
			array.put("title",paraname);
		}else{
			array.put("alter","true");
			array.put("oldTitle",item.getItemName());
			array.put("newTitle",paraname);
		}
	    //����ָ����Ϣ
		boolean result = staffItemServie.updateCenterItemById(Integer.valueOf(centerItemIdStr), paraname, Double.valueOf(paraweigth), paraEnable, paraStyle);
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
	 * �洢һ��ָ������
	 * FormPanel submit
	 * @return
	 */
	public void storeFirstLevelParaData(){
		
		HttpServletRequest resquest= ServletActionContext.getRequest();
		ServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("GB2312");
		response.setContentType("text/html");
		
		//========session��ȡcenterId Start========//
		int userId = SessionUtils.getUserId();
		if(-1 == userId){
			JSONObject json = new JSONObject();
			json.put("success","false");
			json.put("data", "��ǰsessionʧЧ����ȡ����һ��ָ�������");
			try {
				json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("get center back1 to front error!");
			}
			return;
			
		}
		User user = userService.findbyUserId(userId);
		if(null == user){
			JSONObject json = new JSONObject();
			json.put("success","false");
			json.put("data", "��ǰsessionʧЧ����ȡ����һ��ָ�������");
			try {
				json.write(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("get center back1 to front error!");
			}
			return;
		}
		
		centerId = user.getServiceCenter().getCenterId();
		windowId = user.getWindow().getWindowId();
		//========session��ȡcenterId End=========//
		String paraName = resquest.getParameter("pnameInputS").trim();
		Double paraWeight = Double.valueOf(resquest.getParameter("pweightInputS").trim());
		String paraStyle = resquest.getParameter("pstyleInputS").trim();
		//[0]�жϵ�ǰ����ָ�� �����ݿ����Ƿ��Ѿ�����
		if(!staffItemServie.curParaIsInDatabase(paraName,centerId,windowId)){
			JSONObject json = new JSONObject();
		    json.put("success",false);
		    json.put("data","���ݿ����Ѿ����ڴ�ָ�꣡");
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
		//centerId�˲�����Ҫͨ�����ݿ��ж� ��ǰ���ĵ�Id
		StaffItem item = new StaffItem(paraName, parentId, paraGrade, paraWeight, paraStyle, enable, centerId,windowId,null,null,null,null,null);
		int result = staffItemServie.storeFirstLevelPara(item);
		
		//��ǰ̨���ͽ������,��ʽΪ(���ر���paraName;����'����ָ��';id��Ϣresult)
		JSONObject json = new JSONObject();
		if(result != -1){
			json.put("success",true);
			json.put("data", paraName+";"+"�༭����ָ��"+";"+result);
		}else{
			json.put("success",false);
			json.put("data","ָ�걣��ʧ�ܣ�");
		}
		
		try {
			json.write(response.getWriter());
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("store centerpara to front error!");
		}
	}
	
	
	/**
	 * ����һ��ָ��Id��ɾ��һ��ָ�꣨��������&&����ָ�꣩
	 * @return
	 * ExtJs Ajax
	 */
	public void deleteFirstLevelParams(){
		
		HttpServletRequest request= ServletActionContext.getRequest();
		ServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("GB2312");
		response.setContentType("text/html");
		
		String centerItemIdStr = request.getParameter("centerItemId");
		boolean result = staffItemServie.deleteAllFirstLevelPara(Integer.valueOf(centerItemIdStr));
		//��ǰ̨���ͽ������,��ʽΪ(���ر���paraName;����'����ָ��';id��Ϣresult)
		JSONObject json = new JSONObject();
		if(result ){
			json.put("success","true");
			json.put("data", "ɾ���ɹ���");
		}else{
			json.put("success","false");
			json.put("data","ɾ��ʧ�ܣ�");
		}
		
		try {
			json.write(response.getWriter());
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("delete centerpara to front error!");
		}
		
		
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
   
}
