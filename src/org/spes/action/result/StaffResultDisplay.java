package org.spes.action.result;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.spes.bean.StaffScore;
import org.spes.service.auth.ActionService;
import org.spes.service.result.ResultService;
import org.spes.service.result.StaffService;
import org.spes.service.util.ResultDataModifier;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class StaffResultDisplay extends ActionSupport implements
		ServletRequestAware, ServletResponseAware {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private String from;
	private String to;
	private String type;
	private Integer start = 0;
	private Integer limit = 0;
	private Integer windowId;
	private Integer userId;
	private Integer[] removeList;
	private Integer[] displayList;
	private Integer[] userIdArray; 
	private StaffService staffService;
	private ActionService actionService;
	private ResultService resultService;
	
	
	public Integer[] getUserIdArray() {
		return userIdArray;
	}

	public void setUserIdArray(Integer[] userIdArray) {
		this.userIdArray = userIdArray;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer[] getRemoveList() {
		return removeList;
	}

	public void setRemoveList(Integer[] removeList) {
		this.removeList = removeList;
	}

	public Integer[] getDisplayList() {
		return displayList;
	}

	public void setDisplayList(Integer[] displayList) {
		this.displayList = displayList;
	}

	public ResultService getResultService() {
		return resultService;
	}

	public void setResultService(ResultService resultService) {
		this.resultService = resultService;
	}

	public ActionService getActionService() {
		return actionService;
	}

	public void setActionService(ActionService actionService) {
		this.actionService = actionService;
	}

	public StaffService getStaffService() {
		return staffService;
	}

	public void setStaffService(StaffService staffService) {
		this.staffService = staffService;
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

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getWindowId() {
		return windowId;
	}

	public void setWindowId(Integer windowId) {
		this.windowId = windowId;
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

	public void GetStaffAndScore() throws IOException, ParseException{
		
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		Integer roleId = Integer.valueOf(session.get("roleId").toString());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calFrom = Calendar.getInstance();
		Calendar calTo = Calendar.getInstance();
		if((this.from == null || this.from.equals(""))&&(this.to == null || this.to.equals(""))){
			calFrom.add(Calendar.YEAR, -1);
			if(type==null){
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
		List result = new ArrayList();
		if(roleId < 5){
			result = resultService.GetStaffAndScoreByWindowId(windowId, from, to, type);
		}else{
			result = resultService.GetStaffAndScoreBUIdAndTime(Integer.valueOf(session.get("userId").toString()), from, to, type);
		}
		ArrayList<TreeMap> StaffTotalScore = new ArrayList<TreeMap>();//root
		Integer totalProperty=0;
		ArrayList<TreeMap> chartAll = new ArrayList<TreeMap>();
		ArrayList<TreeMap> chartDetail = new ArrayList<TreeMap>();
		ArrayList dateList = new ArrayList();
		ArrayList detailChartField = new ArrayList();
		ArrayList fields = new ArrayList();
		fields.add("name");
		detailChartField.add("name");
		for(int i = 0 ; i < result.size() ; i++){
			// 生成表格数据
			TreeMap staffEach = new TreeMap();
			List resultEach = (List) result.get(i);
			StaffScore staffScore = (StaffScore) resultEach.get(0);
			Integer userId = (Integer) resultEach.get(1);
			String userName = (String) resultEach.get(2);
			if (this.removeList != null) {
				boolean flag1 = false;
				for (Integer id : removeList) {
					if (id.equals(userId)) {
						flag1 = true;
						break;
					}
				}
				if (!flag1) {
					continue;
				}
			}
			/*Long time = staffScore.getEvaluateDate().getTime();
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(sdf.format(time)));
			Integer year = cal.get(Calendar.YEAR);
			Integer month = cal.get(Calendar.MONTH) +1;
			String date = "";
			if("month".equals(type)){
				date = year + "年" + month + "月";
			}
			if("quarter".equals(type)){
				date = year + "年第";
				date += month%3==0?month/3:month/3+1;
				date += "季度";
			}
			if("year".equals(type)){
				date = year +"年";
			}*/
			String date = staffScore.getSheetType();
			if(!dateList.contains(date)){
				dateList.add(date);
			}
			boolean flag = false;
			TreeMap tm = new TreeMap();
			for(int j = 0; j<StaffTotalScore.size(); j++){
				if(StaffTotalScore.size()>0 && StaffTotalScore.get(j).get("userName").equals(userName)){
					flag = true;
					tm = StaffTotalScore.get(j);
					break;
				}
			}
			if(!flag){
				tm = new TreeMap();
				tm.put("userName", userName);
				tm.put(date, staffScore.getItemScore());
				tm.put("userId", userId);
				StaffTotalScore.add(tm);
			}else{
				if(tm.keySet().contains(date)){
					Double former = (Double)tm.get(date);
					tm.put(date, former + staffScore.getItemScore());
				}else{
					tm.put(date, staffScore.getItemScore());
				}
			}
			fields.add(userName);
			
		}
		for(int i = 0 ; i<StaffTotalScore.size(); i ++){
			TreeMap tm = StaffTotalScore.get(i);
			Set kSet = tm.keySet();
			Iterator itSet = kSet.iterator();
			Double totalScore = 0.0;
			while(itSet.hasNext()){
				String key = itSet.next().toString();
				if(key.equals("userName") || key.equals("userId")){
					continue;
				}else{
					Double score = (Double)tm.get(key);
					totalScore += score;
				}
			}
			tm.put("totalScore", totalScore);
		}
		Collections.sort(StaffTotalScore, new Comparator<TreeMap>(){

			public int compare(TreeMap t1, TreeMap t2) {
				if((Double)t1.get("totalScore")<(Double)t2.get("totalScore")){
					return 1;
				}else{
					return -1;
				}
			}});
		ArrayList<TreeMap> series = ResultDataModifier.GenerateSeriesForChart(StaffTotalScore, "userName");
		ResultDataModifier.modifiyJSONResultData(StaffTotalScore, type,"userName");
		ArrayList<TreeMap> resultForChart = ResultDataModifier.ModifyStaffJSONResultForChart(StaffTotalScore, dateList);
		totalProperty = StaffTotalScore.size();
		JSONObject json = new JSONObject();
		json.put("root", StaffTotalScore);
		json.put("totalProperty", totalProperty);
		json.put("dateList",dateList);
		json.put("chart", resultForChart);
		json.put("series", series);
		json.put("fields", fields);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/json");
		if(start == 0 && limit == 0){
			json.put("root", StaffTotalScore);
			json.write(response.getWriter());
			return;
		}
		int sIndex = start;
		int tIndex = start+limit;
		if(tIndex<0){
			tIndex = 0;
		}
		if(tIndex >= StaffTotalScore.size()){
			tIndex = StaffTotalScore.size();
		}
		json.put("root", StaffTotalScore.subList(sIndex, tIndex));
		json.write(response.getWriter());
	}
	
	public void getStaffScore() throws IOException, ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calFrom = Calendar.getInstance();
		Calendar calTo = Calendar.getInstance();
		if((this.from == null || this.from.equals(""))&&(this.to == null || this.to.equals(""))){
			calFrom.add(Calendar.YEAR, -1);
			if(type==null){
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
		response.setContentType("text/json");
		ArrayList<TreeMap> StaffScore = new ArrayList<TreeMap>();//root
		ArrayList<TreeMap> StaffScoreForSeries = new ArrayList<TreeMap>();
		Integer totalProperty=0;
		ArrayList<TreeMap> chartDetail = new ArrayList<TreeMap>();
		ArrayList dateList = new ArrayList();
		ArrayList fields = new ArrayList();
		List result = new ArrayList();
		if(this.userIdArray == null){
			result = resultService.GetStaffAndScoreByUserIdAndTime(userId, from, to, type);
			fields.add("name");
			for(int i = 0; i<result.size(); i++){
				//生成表格数据
				List list = (List)result.get(i);
				StaffScore ss = (StaffScore)list.get(0);
				String userName = (String)list.get(1);
				Integer itemGrade = (Integer)list.get(2);
				String parentName = (String)list.get(3);
				String date = ss.getSheetType();
				if(!dateList.contains(date)){
					dateList.add(date);
				}
				boolean flag = false;
				TreeMap tm = new TreeMap();
				for(int j = 0; j<StaffScore.size(); j++){
					if(StaffScore.size()>0 && StaffScore.get(j).get("itemName").equals(userName + "|" +ss.getItemName())){
						flag = true;
						tm = StaffScore.get(j);
						break;
					}
				}
				if(!flag){
					tm = new TreeMap();
					tm.put("itemName", userName + "|" + ss.getItemName());
					tm.put("itemGrade", itemGrade);
					tm.put("parentName", parentName);
					tm.put(date, ss.getItemScore());
					tm.put("itemId", ss.getItemId());
					StaffScore.add(tm);
				}else{
					tm.put(date, ss.getItemScore());
				}
				//生成chart
				if(this.displayList != null){
					boolean flag1 = false;
					for(Integer id : displayList){
						if(!id.equals(ss.getItemId())){
							flag1 = true;
							break;
						}
					}
					if(flag1){
						continue;
					}
				}else{
					continue;
				}
				if(!fields.contains(userName + "|" +ss.getItemName())){
					fields.add(userName + "|" +ss.getItemName());
				}
				boolean chartDFlag = false;
				TreeMap tmChartD = new TreeMap();
				for(int j = 0; j<chartDetail.size(); j++){
					if(!chartDetail.isEmpty() && chartDetail.get(j).get("name").equals(date)){
						tmChartD = chartDetail.get(j);
						chartDFlag = true;
					}
				}
				if(!chartDFlag){
					tmChartD = new TreeMap();
					tmChartD.put("name", date);
					tmChartD.put(userName + "|" + ss.getItemName(), ss.getItemScore());
					chartDetail.add(tmChartD);
				}else{
					tmChartD.put(userName + "|" +ss.getItemName(),ss.getItemScore());
				}
				
			}
		}else{
			result=resultService.GetStaffAndScoreByUserIdAndTime(userId, from, to, type);
			fields.add("name");
			for(int i = 0; i<result.size(); i++){
				//生成表格数据
				List list = (List)result.get(i);
				StaffScore ss = (StaffScore)list.get(0);
				String userName = (String)list.get(1);
				Integer itemGrade = (Integer)list.get(2);
				String parentName = (String)list.get(3);
				String date = ss.getSheetType();
				if(!dateList.contains(date)){
					dateList.add(date);
				}
				boolean flag = false;
				TreeMap tm = new TreeMap();
				for(int j = 0; j<StaffScore.size(); j++){
					if(StaffScore.size()>0 && StaffScore.get(j).get("itemName").equals(userName + "|" +ss.getItemName())){
						flag = true;
						tm = StaffScore.get(j);
						break;
					}
				}
				if(!flag){
					tm = new TreeMap();
					tm.put("itemName", userName + "|" + ss.getItemName());
					tm.put("itemGrade", itemGrade);
					tm.put("parentName", parentName);
					tm.put(date, ss.getItemScore());
					tm.put("itemId", ss.getItemId());
					StaffScore.add(tm);
				}else{
					tm.put(date, ss.getItemScore());
				}
			}
			result.clear();
			for(int i = 0 ; i<this.userIdArray.length ; i++){
				result.add(resultService.GetStaffAndScoreByUserIdAndTime(userIdArray[i], from, to, type));
			}
			for(int i = 0 ; i<result.size(); i++){
				List temp = ((List)result.get(i));
					//生成chart
				for(int k = 0; k<temp.size(); k++){
					List list = (List)temp.get(k);
					StaffScore ss = (StaffScore)list.get(0);
					String userName = (String)list.get(1);
					Integer itemGrade = (Integer)list.get(2);
					String parentName = (String)list.get(3);
					String date = ss.getSheetType();
					TreeMap tm = new TreeMap();
					boolean flagForSeries = false;
					for(int j = 0; j<StaffScoreForSeries.size(); j++){
						if(StaffScoreForSeries.size()>0 && StaffScoreForSeries.get(j).get("itemName").equals(userName + "|" +ss.getItemName())){
							flagForSeries = true;
							tm = StaffScoreForSeries.get(j);
							break;
						}
					}
					if(!flagForSeries){
						tm = new TreeMap();
						tm.put("itemName", userName + "|" + ss.getItemName());
						tm.put("itemGrade", itemGrade);
						tm.put("parentName", parentName);
						tm.put(date, ss.getItemScore());
						tm.put("itemId", ss.getItemId());
						StaffScoreForSeries.add(tm);
					}else{
						tm.put(date, ss.getItemScore());
					}
					if(this.displayList != null){
						boolean flag1 = false;
						for(Integer id : displayList){
							if(id.equals(ss.getItemId())){
								flag1 = true;
								break;
							}
						}
						if(!flag1){
							continue;
						}else{
							if(!fields.contains(userName + "|" +ss.getItemName())){
								fields.add(userName + "|" + ss.getItemName());
							}
							boolean chartDFlag = false;
							TreeMap tmChartD = new TreeMap();
							for(int j = 0; j<chartDetail.size(); j++){
								if(!chartDetail.isEmpty() && chartDetail.get(j).get("name").equals(date)){
									tmChartD = chartDetail.get(j);
									chartDFlag = true;
								}
							}
							if(!chartDFlag){
								tmChartD = new TreeMap();
								tmChartD.put("name", date);
								tmChartD.put(userName + "|" + ss.getItemName(), ss.getItemScore());
								chartDetail.add(tmChartD);
							}else{
								tmChartD.put(userName + "|" +ss.getItemName(),ss.getItemScore());
							}
						}
					}
					
				}
			}
		}
		
		ResultDataModifier.modifiyJSONResultData(StaffScore, type,"itemName");
		ArrayList<TreeMap> series = ResultDataModifier.GenerateSeriesForChart(StaffScoreForSeries,"itemName");
		StaffScoreForSeries = null;
		JSONObject json = new JSONObject();
		json.put("fields", fields);
		json.put("chartDetail", chartDetail);
		json.put("dateList", dateList);
		json.put("series", series);
		json.put("totalProperty", StaffScore.size());
		if(start == 0 && limit == 0){
			json.put("root", StaffScore);
			json.write(response.getWriter());
			return;
		}
		int sIndex = start;
		int tIndex = start+limit;
		if(tIndex<0){
			tIndex = 0;
		}
		if(tIndex >= StaffScore.size()){
			tIndex = StaffScore.size();
		}
		//System.out.println(windowTotalScores);
		List<TreeMap> sublist = StaffScore.subList(sIndex, tIndex);
		json.put("root", sublist);
		json.write(response.getWriter());
	}
}
