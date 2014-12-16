package org.spes.service.util;

import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import net.sf.json.JSONObject;
import antlr.collections.List;

public class ResultDataModifier {

	public static ArrayList modifiyJSONResultData(ArrayList<TreeMap> jsonList,String type,String titles){
		String appendix = "当月未统计";
		if("quarter".equals(type)){
			appendix = "该季度未统计";
		}
		if("year".equals(type)){
			appendix = "当年未统计";
		}
		ArrayList dateList = new ArrayList();
		ArrayList<ArrayList<String>> dateListEach = new ArrayList<ArrayList<String>>();
		Comparator cmp = Collator.getInstance(java.util.Locale.CHINA);
		for(int i = 0; i<jsonList.size(); i++){
			ArrayList<String> listEach = new ArrayList<String>();
			Set keySet = jsonList.get(i).keySet();
			Iterator it = keySet.iterator();
			while(it.hasNext()){
				String title = it.next().toString();
				if(!dateList.contains(title) && !titles.equals(title) && !title.equals("windowId")){
					dateList.add(title);
				}
				listEach.add(title);
			}
			Collections.sort(listEach,cmp);
			dateListEach.add(listEach);
			listEach = null;
		}
		
		Collections.sort(dateList,cmp);
		for(int i = 0; i<dateListEach.size(); i++){
			ArrayList listEach = dateListEach.get(i);
			for(int j = 0; j < listEach.size();j++){
				if(dateList.indexOf(listEach.get(j)) != j){
					int appendCount = dateList.indexOf(listEach.get(j)) - j;
					for(int k = 0; k<appendCount; k++){
						if(!jsonList.get(i).containsKey(dateList.get(j+k))){
							jsonList.get(i).put(dateList.get(j+k), appendix);
						}
					}
				}
			}
		}
		for(int i = 0; i<jsonList.size(); i++){
			TreeMap mapEach = jsonList.get(i);
			int mapSize = mapEach.size();
			int appendCount = dateList.size()-mapEach.size()+1;
			for(int j = 0; j<appendCount; j++){
				jsonList.get(i).put(dateList.get(mapSize-1+j), appendix);
			}
		}
		return dateList;
	}
	
	public static ArrayList<TreeMap> ModifyWindowJSONResultForChart(ArrayList<TreeMap> jsonList,ArrayList dateList){
		ArrayList<TreeMap> resultForChart = new ArrayList<TreeMap>();
		for(int i = 0; i<dateList.size(); i++){
			String date = dateList.get(i).toString();
			TreeMap eachForChart = new TreeMap();
			eachForChart.put("name", date.split("&")[2]);
			for(TreeMap tm : jsonList){
				Object score = tm.get(date);
				Object windowName = tm.get("windowName");
				eachForChart.put(windowName, score);
			}
			resultForChart.add(eachForChart);
		}
		return resultForChart;
	}
	
	public static ArrayList<TreeMap> ModifyStaffJSONResultForChart(ArrayList<TreeMap> jsonList,ArrayList dateList){
		ArrayList<TreeMap> resultForChart = new ArrayList<TreeMap>();
		for(int i = 0; i<dateList.size(); i++){
			String date = dateList.get(i).toString();
			TreeMap eachForChart = new TreeMap();
			eachForChart.put("name", date);
			for(TreeMap tm : jsonList){
				Object score = tm.get(date);
				Object windowName = tm.get("userName");
				eachForChart.put(windowName, score);
			}
			resultForChart.add(eachForChart);
		}
		return resultForChart;
	}
	
	public static ArrayList<TreeMap> ModifyJSONWindowItemResultForChart(ArrayList<TreeMap> jsonList,ArrayList dateList,String[] displayList,ArrayList fields){
		if(displayList == null){
			return new ArrayList<TreeMap>();
		}
		ArrayList<TreeMap> resultForChart = new ArrayList<TreeMap>();
		for(int i = 0; i<dateList.size(); i++){
			String date = dateList.get(i).toString();
			System.out.println("this "+date);
			TreeMap eachForChart = new TreeMap();
			eachForChart.put("name", date);
			for(TreeMap tm : jsonList){
				if(tm.containsKey(date)){
					Object itemName = tm.get("itemName");
					for(int j = 0; j<displayList.length; j++){
						if(displayList[j].equals(itemName.toString())){
							Object score = tm.get(date);
							eachForChart.put(itemName, Float.parseFloat(score.toString()));
							if(!fields.contains(itemName)){
								fields.add(itemName);
							}
						}
					}
				}
			}
			resultForChart.add(eachForChart);
		}
		Collections.sort(resultForChart, new Comparator<TreeMap>(){
				public int compare(TreeMap o1, TreeMap o2) {
					String date1 = o1.get("name").toString();
					String date2 = o2.get("name").toString();
					String dString1 = date1.split("&")[1];
					String dString2 = date2.split("&")[1];
					String[] dateArr1 = dString1.split("-");
					String[] dateArr2 = dString2.split("-");
					
					int result = -1;
					for(int i = 0 ; i<dateArr1.length; i++){
						if(dateArr1[i].trim()!=" " && dateArr2[i].trim() != " "){
							if(Integer.valueOf(dateArr1[i].trim()) == Integer.valueOf(dateArr2[i].trim())){
								continue;
							}else{
								if(Integer.valueOf(dateArr1[i]) < Integer.valueOf(dateArr2[i])){
									result =  -1;
								}else{
									result =  1;
								}
							}
						}
					}
					return result;
				}
		});
		return resultForChart;
	}
	
	public static ArrayList<TreeMap> ModifyJSONStaffItemResultForChart(ArrayList<TreeMap> jsonList,ArrayList dateList,String[] displayList,ArrayList fields){
		if(displayList == null){
			return new ArrayList<TreeMap>();
		}
		ArrayList<TreeMap> resultForChart = new ArrayList<TreeMap>();
		for(int i = 0; i<dateList.size(); i++){
			String date = dateList.get(i).toString();
			TreeMap eachForChart = new TreeMap();
			eachForChart.put("name", date);
			for(TreeMap tm : jsonList){
					Object itemName = tm.get("userName");
					for(int j = 0; j<displayList.length; j++){
						if(displayList[j].equals(itemName.toString())){
							Object score = tm.get(date);
							eachForChart.put(itemName, score);
							if(!fields.contains(itemName)){
								fields.add(itemName);
							}
						}
					}
				
			}
			resultForChart.add(eachForChart);
		}
		Collections.sort(resultForChart, new Comparator<TreeMap>(){
				public int compare(TreeMap o1, TreeMap o2) {
					String date1 = o1.get("name").toString();
					String date2 = o2.get("name").toString();
					String[] dateArr1 = date1.split("年第|年|月|季度");
					String[] dateArr2 = date2.split("年第|年|月|季度");
					int result = -1;
					for(int i = 0 ; i<dateArr1.length; i++){
						if(dateArr1[i].trim()!=" " && dateArr2[i].trim() != " "){
							if(Integer.valueOf(dateArr1[i].trim()) == Integer.valueOf(dateArr2[i].trim())){
								continue;
							}else{
								if(Integer.valueOf(dateArr1[i]) < Integer.valueOf(dateArr2[i])){
									result =  -1;
								}else{
									result =  1;
								}
							}
						}
					}
					return result;
				}
		});
		return resultForChart;
	}
	
	public static ArrayList<TreeMap> GenerateSeriesForChart(ArrayList<TreeMap> jsonList,String title){
		ArrayList<TreeMap> series = new ArrayList<TreeMap>();
		for(TreeMap tm : jsonList){
			TreeMap serie = new TreeMap();
			String itemName =tm.get(title).toString();
			serie.put("displayName", tm.get(title));
			serie.put("type", "line");
			serie.put("yField", tm.get(title));
			String col = getRandColorCode();
			JSONObject json = new JSONObject();
			json.put("color", col);
			serie.put("style", json);
			series.add(serie);
			
		}
		return series;
	}
	
	public static ArrayList<TreeMap> GenerateSeriesForChartAll(ArrayList<TreeMap> jsonList,String title,String[] displayList){
		if(displayList == null){
			return new ArrayList<TreeMap>();
		}
		ArrayList<TreeMap> series = new ArrayList<TreeMap>();
		for(TreeMap tm : jsonList){
			TreeMap serie = new TreeMap();
			String itemName =tm.get(title).toString();
			boolean display = false;
			for(String dis : displayList){
				if(dis.equals(itemName)){
					display = true;
				}
			}
			if(display){
				serie.put("displayName", tm.get(title));
				serie.put("type", "line");
				serie.put("yField", tm.get(title));
				String col = getRandColorCode();
				JSONObject json = new JSONObject();
				json.put("color", col);
				serie.put("style", json);
				series.add(serie);
			}
		}
		return series;
	}
	
	 public static String getRandColorCode() {   
	        String r, g, b;   
	        Random random = new Random();   
	        r = Integer.toHexString(random.nextInt(256)).toUpperCase();   
	        g = Integer.toHexString(random.nextInt(256)).toUpperCase();   
	        b = Integer.toHexString(random.nextInt(256)).toUpperCase();   
	        
	        r = r.length() == 1 ? "0" + r : r;   
	        g = g.length() == 1 ? "0" + g : g;   
	        b = b.length() == 1 ? "0" + b : b;   
	        
	        return r + g + b;   
	}
}
