package org.spes.action.center;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.spes.bean.CenterParam;
import org.spes.service.center.CenterParamService;
/**
 * 中心三級指標
 * 
 * @author Hegang
 *
 */
public class CenterParamAction {

	private Integer itemId;
	private Integer centerId;
	private CenterParamService centerParamService;

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getCenterId() {
		return centerId;
	}

	public void setCenterId(Integer centerId) {
		this.centerId = centerId;
	}

	public CenterParamService getCenterParamService() {
		return centerParamService;
	}

	public void setCenterParamService(CenterParamService centerParamService) {
		this.centerParamService = centerParamService;
	}

	/**
	 * 依据指标Id与中心Id获取中心三级指标
	 * @throws IOException
	 */
	public void getCenterParams() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
        
		CenterParam cp = centerParamService.getParamById(itemId, centerId);
		
		if (null != cp) {
			JSONObject json = JSONObject.fromObject(cp);
			response.getWriter().write(json.toString());
			json.clear();
		}
		
	}
}
