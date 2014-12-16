package org.spes.action.result;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.spes.bean.Window;
import org.spes.bean.WindowScore;
import org.spes.service.auth.ActionService;
import org.spes.service.result.ResultService;
import org.spes.service.result.WindowService;
import org.spes.service.util.ResultDataModifier;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class WindowResultDisplay extends ActionSupport implements
		ServletRequestAware, ServletResponseAware {
	
	private static final long serialVersionUID = 1L;
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String from;
	private String to;
	private String type;
	private String[] removeList;
	private String[] displayList;
	private Integer[] windowIdArray;
	private ActionService actionService;
	private WindowService windowService;
	private ResultService resultService;
	private Integer windowId;
	private int start;
	private int limit;
	
	public Integer[] getWindowIdArray() {
		return windowIdArray;
	}

	public void setWindowIdArray(Integer[] windowIdArray) {
		this.windowIdArray = windowIdArray;
	}
	
	public String[] getDisplayList() {
		return displayList;
	}

	public void setDisplayList(String[] displayList) {
		this.displayList = displayList;
	}

	public String[] getRemoveList() {
		return removeList;
	}

	public void setRemoveList(String[] removeList) {
		this.removeList = removeList;
	}

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

	public ResultService getResultService() {
		return resultService;
	}

	public void setResultService(ResultService resultService) {
		this.resultService = resultService;
	}

	
	public WindowService getWindowService() {
		return windowService;
	}

	public Integer getWindowId() {
		return windowId;
	}

	public void setWindowId(Integer windowId) {
		this.windowId = windowId;
	}

	public void setWindowService(WindowService windowService) {
		this.windowService = windowService;
	}

	public ActionService getActionService() {
		return actionService;
	}

	public void setActionService(ActionService actionService) {
		this.actionService = actionService;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	public void setServletResponse(HttpServletResponse arg0) {
		this.response = arg0;
	}
	/**
	 * 返回所有该用户所在中心的窗口
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public void GetAllAvailableWindow() throws IOException, ParseException{
		ActionContext context = ActionContext.getContext();
		Integer userId = Integer.valueOf(context.getSession().get("userId").toString());
		List windows = windowService.GetAvailableWindows(userId);//to be modified to username
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calFrom = Calendar.getInstance();
		Calendar calTo = Calendar.getInstance();
		if((this.from == null || this.from.equals(""))&&(this.to == null || this.to.equals(""))){
			calFrom.add(Calendar.YEAR, -1);
			if(type == null){
				type = "month";
			}
			from = sdf.format(calFrom.getTime());
			to = sdf.format(calTo.getTime());
		}
		if((this.from == null || this.from.equals(""))^(this.to == null || this.to.equals(""))){
			PrintWriter pw = response.getWriter();
			pw.write("Date error");
			pw.flush();
			pw.close();
			return ;
		}
		
		calFrom = null;
		calTo = null;
		ArrayList<TreeMap> windowTotalScores = new ArrayList<TreeMap>();
		ArrayList<TreeMap> windowScoreForChart = new ArrayList<TreeMap>();
		for(int i = 0; i<windows.size(); i++){
			if(this.removeList != null){
				boolean flag = false;
				for(String str : this.removeList){
					if(str.equals(((Window)windows.get(i)).getWindowName())){
						flag = true;
						break;
					}
				}
				if(!flag){
					continue;
				}
				
			}
			Integer windowId = ((Window)windows.get(i)).getWindowId();
			List windowScore = resultService.GetWindowScore(windowId, from, to, type);
			TreeMap dateAndScore = new TreeMap();
			if("month".equals(type)){
				for(int j = 0; j<windowScore.size();j++){
					Object[] item = (Object[])windowScore.get(j);
					WindowScore score = (WindowScore)item[0];
					Double sc = score.getItemScore();
					String date = score.getSheetType();
					if(!dateAndScore.keySet().contains(date)){
						dateAndScore.put(date, sc);
					}else{
						Double formerScore = (Double)dateAndScore.get(date);
						dateAndScore.put(date, formerScore+sc);
					}
				}
				dateAndScore.put("windowName", ((Window)windows.get(i)).getWindowName().toString());
				dateAndScore.put("windowId", ((Window)windows.get(i)).getWindowId());
				windowTotalScores.add(dateAndScore);
			}
			if("quarter".equals(type)){
				for(int j = 0; j<windowScore.size();j++){
					Object[] item = (Object[])windowScore.get(j);
					WindowScore score = (WindowScore)item[0];
					String date = score.getSheetType();
					Double sc = score.getItemScore();
					if(!dateAndScore.keySet().contains(date)){
						dateAndScore.put(date, sc);
					}else{
						Double formerScore = (Double)dateAndScore.get(date);
						dateAndScore.put(date,formerScore+sc);
					}
				}
				dateAndScore.put("windowName", ((Window)windows.get(i)).getWindowName().toString());
				dateAndScore.put("windowId", ((Window)windows.get(i)).getWindowId());
				windowTotalScores.add(dateAndScore);
			}
			if("year".equals(type)){
				for(int j = 0; j<windowScore.size();j++){
					Object[] item = (Object[])windowScore.get(j);
					WindowScore score = (WindowScore)item[0];
					String date = score.getSheetType();
					Double sc = score.getItemScore();
					if(!dateAndScore.keySet().contains(date)){
						dateAndScore.put(date, sc);
					}else{
						Double formerScore = (Double)dateAndScore.get(date);
						dateAndScore.put(date,formerScore+sc);
					}
				}
				dateAndScore.put("windowName", ((Window)windows.get(i)).getWindowName().toString());
				dateAndScore.put("windowId", ((Window)windows.get(i)).getWindowId());
				windowTotalScores.add(dateAndScore);
			}
			windowScore = null;
			dateAndScore = null;
		}
		ArrayList dateList = ResultDataModifier.modifiyJSONResultData(windowTotalScores, type,"windowName");
		ArrayList<TreeMap> resultForChart = ResultDataModifier.ModifyWindowJSONResultForChart(windowTotalScores, dateList);
		ArrayList<TreeMap> series = ResultDataModifier.GenerateSeriesForChart(windowTotalScores,"windowName");
		JSONArray chart = JSONArray.fromObject(resultForChart);
		JSONArray root = new JSONArray();
		if(start == 0 && limit == 0){
			root = JSONArray.fromObject(windowTotalScores);
			JSONObject json = new JSONObject();
			json.put("totalProperty", windowTotalScores.size());
			json.put("root", root);
			json.put("dateList", dateList);
			json.put("chart", chart);
			json.put("series", series);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			json.write(response.getWriter());
			json.clear();
			windowTotalScores.clear();
			return;
		}
		int sIndex = start;
		int tIndex = start+limit;
		if(tIndex<0){
			tIndex = 0;
		}
		if(tIndex >= windowTotalScores.size()){
			tIndex = windowTotalScores.size();
		}
		List<TreeMap> sublist = windowTotalScores.subList(sIndex, tIndex);
		root = JSONArray.fromObject(sublist);
		JSONObject json = new JSONObject();
		json.put("totalProperty", windowTotalScores.size());
		json.put("root", root);
		json.put("chart", chart);
		json.put("dateList", dateList);
		json.put("series", series);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/json");
		json.write(response.getWriter());
		json.clear();
		windowTotalScores.clear();
	}
	
	public void GetWindowResult() throws IOException, ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calFrom = Calendar.getInstance();
		Calendar calTo = Calendar.getInstance();
		if((this.from == null || this.from.equals(""))&&(this.to == null || this.to.equals(""))){
			calFrom.add(Calendar.YEAR, -1);
			if(type == null){
				type = "month";
			}
			from = sdf.format(calFrom.getTime());
			to = sdf.format(calTo.getTime());
		}
		if((this.from == null || this.from.equals(""))^(this.to == null || this.to.equals(""))){
			PrintWriter pw = response.getWriter();
			pw.write("Date error");
			pw.flush();
			pw.close();
			return ;
		}
		calFrom = null;
		calTo = null;
		response.setCharacterEncoding("utf-8");
		List scores = new ArrayList();
		ArrayList<TreeMap> itemAndScore = new ArrayList<TreeMap>();//root
		Integer totalProperty=0;
		ArrayList<TreeMap> chartAll = new ArrayList<TreeMap>();
		ArrayList<TreeMap> chartDetail = new ArrayList<TreeMap>();
		ArrayList dateList = new ArrayList();
		ArrayList detailChartField = new ArrayList();
		ArrayList fields = new ArrayList();
		fields.add("name");
		if(this.windowIdArray == null){
			scores = resultService.GetWindowScore(this.windowId, this.from, this.to, this.type);
			detailChartField.add("name");
			for(int i = 0; i<scores.size(); i++){
				Object[] obj = (Object[])scores.get(i);
				WindowScore cs = (WindowScore)obj[0];
				String parentItemName = (String)obj[1];
				Integer itemGrade = (Integer)obj[2];
				String windowName = (String)obj[3];
				String date = cs.getSheetType();
				if(!dateList.contains(date)){
					dateList.add(date);
				}
				boolean flag = false;
				TreeMap tm = new TreeMap();
				for(int j = 0; j<itemAndScore.size(); j++){
					if(itemAndScore.size()>0 && itemAndScore.get(j).get("itemName").equals(cs.getItemName())){
						flag = true;
						tm = itemAndScore.get(j);
						break;
					}
				}
				if(!flag){
					tm = new TreeMap();
					tm.put("itemName", windowName+" "+cs.getItemName());
					tm.put(date, cs.getItemScore());
					tm.put("itemGrade", itemGrade);
					tm.put("parentItemName",parentItemName);
					itemAndScore.add(tm);
				}else{
					if(tm.keySet().contains(date)){
						Double former = (Double)tm.get(date);
						tm.put(date, former + cs.getItemScore());
					}else{
						tm.put(date, cs.getItemScore());
					}
				}
			
			}
		} else {
			
			for(Integer winId : windowIdArray){
				scores.add(resultService.GetWindowScore(winId, this.from, this.to, this.type));
			}
			
			detailChartField.add("name");
			for(int l = 0; l<scores.size(); l++){
				ArrayList temp = (ArrayList)scores.get(l);
				for(int i=0;i<temp.size();i++){
					Object[] obj = (Object[])temp.get(i);
					WindowScore cs = (WindowScore)obj[0];
					String parentItemName = (String)obj[1];
					Integer itemGrade = (Integer)obj[2];
					String windowName = (String)obj[3];
					String date = cs.getSheetType();
					if(!dateList.contains(date)){
						dateList.add(date);
					}
					boolean flag = false;
					TreeMap tm = new TreeMap();
					for(int j = 0; j<itemAndScore.size(); j++){
						if(itemAndScore.size()>0 && itemAndScore.get(j).get("itemName").equals(cs.getItemName())){
							flag = true;
							tm = itemAndScore.get(j);
							break;
						}
					}
					if(!flag){
						tm = new TreeMap();
						tm.put("itemName", windowName+" "+cs.getItemName());
						tm.put(date, Float.parseFloat(cs.getItemScore().toString()));
						tm.put("itemGrade", itemGrade);
						tm.put("parentItemName",parentItemName);
						itemAndScore.add(tm);
					}else{
						if(tm.keySet().contains(date)){
							Double former = (Double)tm.get(date);
							tm.put(date, former + Float.parseFloat(cs.getItemScore().toString()));
						}else{
							tm.put(date, Float.parseFloat(cs.getItemScore().toString()));
						}
					}
				}
			}
		}
		totalProperty = itemAndScore.size();
		Comparator cmp = Collator.getInstance();
		ResultDataModifier.modifiyJSONResultData(itemAndScore, type,"itemName");
		ArrayList<TreeMap> resultForChart = ResultDataModifier.ModifyJSONWindowItemResultForChart(itemAndScore, dateList,this.displayList,fields);
		ArrayList<TreeMap> series = ResultDataModifier.GenerateSeriesForChart(itemAndScore,"itemName");
		Collections.sort(dateList, cmp);
		JSONObject json = new JSONObject();
		json.put("totalProperty", totalProperty);
		json.put("dateList", dateList);
		json.put("chart", resultForChart);
		json.put("series", series);
		json.put("fields", fields);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/json");
		if(start ==0 && limit ==0){
			json.put("root", itemAndScore);
		}else{
			int sIndex = start;
			int tIndex = start+limit;
			if(tIndex<0){
				tIndex = 0;
			}
			if(tIndex >= itemAndScore.size()){
				tIndex = itemAndScore.size();
			}
			json.put("root", itemAndScore.subList(sIndex, tIndex));
		}
		json.write(response.getWriter());
	}
}
