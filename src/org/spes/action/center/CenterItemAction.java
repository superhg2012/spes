package org.spes.action.center;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.spes.bean.CenterItem;
import org.spes.bean.CenterParam;
import org.spes.bean.CenterScore;
import org.spes.common.StringUtil;
import org.spes.service.center.CenterItemService;
import org.spes.service.center.CenterParamService;
import org.spes.service.center.CenterScoreService;

import com.opensymphony.xwork2.ActionContext;

/**
 * ����ָ��
 * 
 * @author Hegang
 * 
 */
public class CenterItemAction {

	private Integer itemId = null;
	private Integer centerId = null;
	private CenterItemService centerItemService = null;
	private CenterScoreService centerScoreService = null;
	private CenterParamService centerParamService = null;

	public void setCenterParamService(CenterParamService centerParamService) {
		this.centerParamService = centerParamService;
	}

	public CenterScoreService getCenterScoreService() {
		return centerScoreService;
	}

	public void setCenterScoreService(CenterScoreService centerScoreService) {
		this.centerScoreService = centerScoreService;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public CenterItemService getCenterItemService() {
		return centerItemService;
	}

	public void setCenterItemService(CenterItemService centerItemService) {
		this.centerItemService = centerItemService;
	}

	public Integer getCenterId() {
		return centerId;
	}

	public void setCenterId(Integer centerId) {
		this.centerId = centerId;
	}

	/**
	 * ��������Id,��ȡ���ĵ�����ָ��
	 * 
	 * @throws IOException
	 */
	public void getCenterItemByCenterId() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("tetx/html");
		List<CenterItem> clist = centerItemService.getCenterItemById(centerId);
		if (null != clist && clist.size() > 0) {
			JSONArray jsonArray = JSONArray.fromObject(clist);
			response.getWriter().write(jsonArray.toString());
		} else {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("result", "��������ָ��δ���ã�");
			jsonObject.write(response.getWriter());
		}
		response.getWriter().flush();
	}

	/**
	 * ��������Id,��ȡ���ĵ�һ������ָ��
	 * hegang
	 * @throws IOException
	 */
	public void getCenterFirstItemByCenterId() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();

		response.setCharacterEncoding("UTF-8");
		response.setContentType("tetx/html");
		
		String sheetName = request.getParameter("sheetName");
		String sheetId = request.getParameter("sheetId");
		
		boolean existChecked = false;
		if (StringUtil.isNotNull(sheetId) && StringUtil.isNotNull(sheetName)) {
			existChecked = true;
		}
		//һ��ָ���б�
		List<CenterItem> clist = centerItemService.getItemByItemGradeAndId(centerId, new Integer(1));
		
		if (null != clist && clist.size() > 0) {
			JSONArray jsonArray = new JSONArray();
	
			for(int index = 0, size = clist.size(); index < size; index++){
				CenterItem ci = clist.get(index);
				JSONObject json = new JSONObject();
				json.put("itemId", ci.getItemId());
				json.put("itemName", ci.getItemName());
				json.put("itemType", ci.getItemType());
				json.put("itemGrade", ci.getItemGrade());
				json.put("parentId", ci.getParentId());
				json.put("itemWeight", ci.getItemWeight());
				
				if (existChecked) {
					CenterScore cs = centerScoreService.getCheckedCenterItems(ci.getItemId(), centerId, sheetId);

					if (null != cs) {
						json.put("checked", true);
					} else {
						json.put("checked", false);
					}
				} else {
					json.put("checked", false);
				}
				
				jsonArray.add(json);
			}
			response.getWriter().write(jsonArray.toString());
			jsonArray.clear();
		} else {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("result", "��������ָ��δ���ã�");
			jsonObject.write(response.getWriter());
		}
		response.getWriter().flush();
	}
	
	/**
	 * ����һ��ָ��Id��ָ�����ʻ�ȡ����ָ���µ�����ָ��
	 * @throws IOException
	 */
	public void getCenterItemByItemId2() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json");
		response.setCharacterEncoding("utf-8");
		List<CenterItem> item2list = centerItemService.getSonItemByParentId(itemId);//����ָ���б�
		JSONArray totalArray = new JSONArray();
		if (null != item2list && item2list.size() > 0) {
			for(CenterItem centerItem : item2list){
				Integer item2Id = centerItem.getItemId();
				String itemType = centerItem.getItemType();
				List<CenterItem> targetlist = null;
				if(!itemType.equals("����")) {
//					targetlist = centerItemService.getCenterItemsByFormula(item2Id, centerId);	//����ָ���б�
					targetlist = centerItemService.getCenterItemsByFormula(item2Id, centerId);	//����ָ���б�
				} else {
					targetlist = new ArrayList<CenterItem>();
					targetlist.add(centerItem);//���ԣ�����Ҫ���㹫ʽ
				}
				if (null !=targetlist) {
					//ָ����㹫ʽ��Ϊ��
					for(CenterItem ci : targetlist){
						JSONObject jsonObject = JSONObject.fromObject(ci);
						jsonObject.put("pItemName", centerItem.getItemName());
						jsonObject.put("pItemId", centerItem.getItemId());
						jsonObject.put("itemType", centerItem.getItemType());
						jsonObject.put("itemWeight", centerItem.getItemWeight());
						totalArray.add(jsonObject);
					}
				}
			}//end for
			response.getWriter().write(totalArray.toString());
			response.getWriter().flush();
			totalArray.clear();
		} else {
			response.getWriter().write("");
		}
	}
	
	/**
	 * ��ȡ�ѿ���ָ���ֵ
	 * @throws IOException
	 */
	public void getHisCenterItemByItemId() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setContentType("text/json");
		response.setCharacterEncoding("utf-8");
		Map<String, Object> session = ActionContext.getContext().getSession();
		String userName = "";
		if(session.containsKey("userName")) {
			userName = (String)session.get("userName");
		}
		String  checkType = request.getParameter("checkType"); //��������
		String  sheetName = request.getParameter("sheetName");//������
		//����һ��ָ���ȡ����ָ��
		List<CenterItem> item2list = centerItemService.getSonItemByParentId(itemId);//����ָ���б�
		JSONArray totalArray = new JSONArray();
		if (item2list != null && item2list.size() > 0) {
			
			for(CenterItem cItem : item2list){
				Integer itemId = cItem.getItemId();
				Integer centerId = cItem.getCenterId();
				String itemType = cItem.getItemType();
				List<CenterItem> targetlist = null; //�������ָ��
				if(!itemType.equals("����")) {//����ָ��
					targetlist = centerItemService.getCenterItemsByFormula(itemId, centerId);	//����ָ���б�
				} else {
					targetlist = new ArrayList<CenterItem>();
					targetlist.add(cItem);//���ԣ�����Ҫ���㹫ʽ
				}
				
				if(targetlist != null) {
					for(CenterItem item : targetlist ) {
						JSONObject jsonObject = JSONObject.fromObject(item);
						if(item.getItemType().equals("����")) {
	                      CenterScore cs = centerScoreService.getCenterScoreByIds(itemId, centerId);   					  	
	                  	  jsonObject.put("pItemValue", cs.getItemScore());
						} else {
							CenterParam cp = centerParamService.queryCenterParams(item.getItemId(), centerId, checkType, sheetName, userName);	
							jsonObject.put("pItemValue", cp.getItemValue());
						}
					
						jsonObject.put("pItemId", cItem.getItemId());
						jsonObject.put("pItemName", cItem.getItemName());
						jsonObject.put("itemType", cItem.getItemType());
						jsonObject.put("itemWeight", item.getItemWeight());
						totalArray.add(jsonObject);
					}	
				}
			}
			response.getWriter().write(totalArray.toString());
			totalArray.clear();
		} else {
			response.getWriter().write("");
		}
	}
	
	/**
	 * ���ݶ���ָ��Id,��ȡ������ָ��
	 * @throws IOException
	 */
	public void getCenterItemByItemId() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		List<CenterItem> clist = centerItemService.getCenterItemsByFormula(itemId, centerId);
		if (null != clist && clist.size() > 0) {
			JSONArray jsonArray = JSONArray.fromObject(clist);
			response.getWriter().write(jsonArray.toString());
		} else {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("result", "��ָ����δ�������۹�ʽ��");
			response.getWriter().write(jsonObject.toString());
		}
		response.getWriter().flush();
	}

}
