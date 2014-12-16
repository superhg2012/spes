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

	//���ڷ��������
	public WindowItemServiceImpl  windowItemService = null;
    //���ķ����
	public CenterServiceImpl      centerService    = null;
	//�û������
	public UserServiceImpl	      userService    = null;
	private int centerId = 0;
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
		User user = userService.getUserById(userId);
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
		//========session��ȡcenterId End=========//
		
		List<WindowItem> item    = windowItemService.getAllParameterData(centerId);
		ServiceCenter  serCenter = centerService.findCenterServiceById(centerId);

		
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
		else if(null == serCenter){
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
			for (WindowItem centerItem : item) {

				if(null!= centerItem.getItemGrade() && firstGrade == centerItem.getItemGrade()){
					JSONObject json = new JSONObject();
					selfItemId = centerItem.getItemId();
					//���һ��Title��Ϣ
					json.put("title", centerItem.getItemName());
					json.put("titleId",String.valueOf(centerItem.getItemId()));
					//�ڽ�������в�ѯ ����idΪselfItemId
					int idIndex = 1;
					String content = "";
					for (WindowItem centerItemInner : item) {
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
	 * ͨ��һ��ָ��Id����ȡָ����Ϣ,(���ڱ༭һ��ָ����Ϣ���ڻ�ȡָ����Ϣ)
	 */
	public void getFirstLevelParaById(){
			
			ServletRequest request = ServletActionContext.getRequest();
			ServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("GB2312");
			response.setContentType("text/html");
			
			String  centerItemIdStr = request.getParameter("centerItemId");
			WindowItem item = windowItemService.getFirstLevelparameter(Integer.valueOf(centerItemIdStr));
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
		User user = userService.getUserById(userId);
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
		//========session��ȡcenterId End=========//
		
		String centerItemId = request.getParameter("centerItemId");
	    List<WindowItem> result = windowItemService.getFirstLevelAndSecondLevelPara(Integer.valueOf(centerItemId),centerId);
	    WindowItem  firstLevelItem = windowItemService.getFirstLevelparameter(Integer.valueOf(centerItemId));
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
	    for (WindowItem item : result) {
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
		User user = userService.getUserById(userId);
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
		String paraname = request.getParameter("efnameW").trim();
		String paraweigth =  request.getParameter("efweightW").trim();
		String paraStyle = request.getParameter("efstyleW").trim();
		String parauseStyle = request.getParameter("efusestyleW").trim();
		
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
		if(!windowItemService.otherSameNameParaInDatabase(Integer.valueOf(centerItemIdStr),paraname,centerId)){
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
		WindowItem item = windowItemService.getFirstLevelparameter(Integer.valueOf(centerItemIdStr));
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
		User user = userService.getUserById(userId);
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
		//========session��ȡcenterId End=========//
		
		String paraName = resquest.getParameter("pnameInputW").trim();
		Double paraWeight = Double.valueOf(resquest.getParameter("pweightInputW").trim());
		String paraStyle = resquest.getParameter("pstyleInputW").trim();
		//[0]�жϵ�ǰ����ָ�� �����ݿ����Ƿ��Ѿ�����
		if(!windowItemService.curParaIsInDatabase(paraName,centerId)){
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
		WindowItem item = new WindowItem(paraName, parentId, paraGrade, paraWeight, paraStyle, enable, centerId,null,null,null,null,null);
		int result = windowItemService.storeFirstLevelPara(item);
		
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
		boolean result = windowItemService.deleteAllFirstLevelPara(Integer.valueOf(centerItemIdStr));
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
