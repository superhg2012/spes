package org.spes.action.result;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import org.spes.bean.CenterScore;
import org.spes.service.result.ResultService;
import org.spes.service.util.ResultDataModifier;

import com.opensymphony.xwork2.ActionSupport;

public class CenterResultDisplay extends ActionSupport implements
		ServletRequestAware, ServletResponseAware {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private ResultService resultService;
	private org.spes.service.auth.ActionService actionService;
	private String type;
	private String from;
	private String to;
	private String[] removeList;
	private boolean displayLev2 = false;
	private Integer start = 0;
	private Integer limit = 0;
	
	private String fileName = null;
	private final String BASE_DIR = "ResultDetail" + File.separator;
	
	public void setInputStream(String fileName){
		this.fileName = fileName;
	}

	public boolean isDisplayLev2() {
		return displayLev2;
	}

	public void setDisplayLev2(boolean displayLev2) {
		this.displayLev2 = displayLev2;
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

	public String[] getRemoveList() {
		return removeList;
	}

	public void setRemoveList(String[] removeList) {
		this.removeList = removeList;
	}

	public org.spes.service.auth.ActionService getActionService() {
		return actionService;
	}

	public void setActionService(org.spes.service.auth.ActionService actionService) {
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

	public ResultService getResultService() {
		return resultService;
	}

	public void setResultService(ResultService resultService) {
		this.resultService = resultService;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	public void setServletResponse(HttpServletResponse arg0) {
		this.response = arg0;
	}
	
	/**
	 * 返回中心结果
	 * @throws IOException
	 * @throws ParseException 
	 */
	public void GetCenterResult() throws IOException, ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calFrom = Calendar.getInstance();
		Calendar calTo = Calendar.getInstance();
		//to be replaced with StringUtil.isNull();
		if((this.from == null || this.from.equals(""))&&(this.to == null || this.to.equals(""))){
			calFrom.add(Calendar.YEAR, -1);
			if(type==null){
				type = "month";// default to month
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

		List scores = resultService.GetCenterResult(from, to, type);
		
		ArrayList<TreeMap> itemAndScore = new ArrayList<TreeMap>();//root
		Integer totalProperty=0;
		ArrayList<TreeMap> chartAll = new ArrayList<TreeMap>();
		ArrayList<TreeMap> chartDetail = new ArrayList<TreeMap>();
		ArrayList dateList = new ArrayList();
		ArrayList detailChartField = new ArrayList();
		detailChartField.add("name");
		
		for(int i = 0, size = scores.size(); i < size; i++) {
			Object[] obj = (Object[])scores.get(i);
			CenterScore cs = (CenterScore)obj[0];
			String parentItemName = (String)obj[1];
			Integer itemGrade = (Integer)obj[2];
			/*Long time = cs.getEvaluateDate().getTime();
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(sdf.format(time)));
			Integer year = cal.get(Calendar.YEAR);
			Integer month = cal.get(Calendar.MONTH)+1;
			String date = "";
			if("month".equals(type)){
				date = year+"年"+month+"月";
			}
			if("quarter".equals(type)){
				date = year + "年第";
				date += month%3 == 0?month/3:month/3+1;
				date += "季度";
			}
			if("year".equals(type)){
				date = year + "年";

			}
			if(!dateList.contains(date)){
				dateList.add(date);
			}*/
//			String date = cs.getBackup2();
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
			if (!flag) {
				tm = new TreeMap();
				tm.put("itemName", cs.getItemName());
				tm.put(date, cs.getItemScore());
				tm.put("itemGrade", itemGrade);
				tm.put("parentItemName", parentItemName);
				itemAndScore.add(tm);
			} else {
				if (tm.keySet().contains(date)) {
					Double former = (Double) tm.get(date);
					tm.put(date, former + cs.getItemScore());
				} else {
					tm.put(date, cs.getItemScore());
				}
			}
			//生成chartAll
			boolean chartAllFlag = false;
			TreeMap tmChartAll = new TreeMap();
			for(int j = 0; j<chartAll.size(); j++){
				if(!chartAll.isEmpty() && chartAll.get(j).get("name").equals(date)){
					tmChartAll = chartAll.get(j);
					chartAllFlag = true;
				}
			}
			if(!chartAllFlag){
				tmChartAll = new TreeMap();
				tmChartAll.put("name", date);
				tmChartAll.put("score", cs.getItemScore());
				chartAll.add(tmChartAll);
			}else{
				Double former = (Double)tmChartAll.get("score");
				tmChartAll.put("score",former+cs.getItemScore());
			}
			//成成chartDetail
			if(itemGrade!=1 && !displayLev2){
				continue;
			}
			detailChartField.add(cs.getItemName());
			if(this.removeList != null){
				boolean chartflag = false;
				for(String str : this.removeList){
					if(str.equals(cs.getItemName())){
						chartflag = true;
						break;
					}
				}
				if(!chartflag){
					continue;
				}
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
				tmChartD.put(cs.getItemName(), cs.getItemScore());
				chartDetail.add(tmChartD);
			}else{
				tmChartD.put(cs.getItemName(),cs.getItemScore());
			}
		}
		totalProperty = itemAndScore.size();
		Comparator comp = Collator.getInstance();
		Collections.sort(dateList, comp);
		ArrayList<TreeMap> series = ResultDataModifier.GenerateSeriesForChart(itemAndScore, "itemName");
		ResultDataModifier.modifiyJSONResultData(itemAndScore, type,"itemName");
		JSONArray jsonArr = new JSONArray();
		response.setCharacterEncoding("utf-8");
		JSONArray root = new JSONArray();
		if(start == 0 && limit == 0){
			root = JSONArray.fromObject(itemAndScore);
			JSONObject json = new JSONObject();
			json.put("totalProperty", itemAndScore.size());
			json.put("root", root);
			json.put("dateList", dateList);
			json.put("chartAll", chartAll);
			json.put("chartDetail", chartDetail);
			json.put("series", series);
			json.put("detailChartField", detailChartField);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			json.write(response.getWriter());
			json.clear();
			itemAndScore.clear();
			return;
		}
		int sIndex = start;
		int tIndex = start+limit;
		if(tIndex<0){
			tIndex = 0;
		}
		if(tIndex >= itemAndScore.size()){
			tIndex = itemAndScore.size();
		}
		//System.out.println(windowTotalScores);
		List<TreeMap> sublist = itemAndScore.subList(sIndex, tIndex);
		//System.out.println(sublist);
		root = JSONArray.fromObject(sublist);
		JSONObject json = new JSONObject();
		json.put("totalProperty", itemAndScore.size());
		json.put("root", root);
		json.put("chartAll", chartAll);
		json.put("chartDetail", chartDetail);
		json.put("dateList", dateList);
		json.put("series", series);
		json.put("detailChartField", detailChartField);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/json");
		json.write(response.getWriter());
		json.clear();
		itemAndScore.clear();
	}
	
	
	//未完成
	public InputStream getInputStream() throws IOException{
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
			return null;
		}
		calFrom = null;
		calTo = null;
		//this.fileName = resultService.GenerateDownloadFile(this.from, this.to, this.type, "center");
		return null;
		
	}
	
}
