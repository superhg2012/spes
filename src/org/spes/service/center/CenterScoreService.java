package org.spes.service.center;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.spes.bean.CenterParam;
import org.spes.bean.CenterScore;

/**
 * 中心指标评价
 * 
 * @author Administrator
 * 
 */
public interface CenterScoreService {
	/**
	 * 评估中心二级指标
	 * 
	 * @param itemId
	 *            中心指标Id
	 * @param centerId
	 *            中心Id
	 */
	public String evaluate(Integer itemId, Integer centerId);

	/**
	 * 评估中心一级指标
	 * 
	 * @param itemId
	 *            中心指标Id
	 * @param centerId
	 *            中心Id
	 */
	public void evaluateFirstLevel(Integer itemId, Integer centerId);

	/**
	 * 评估中心二级指标
	 * 
	 * @param set
	 *            三级指标参数及得分
	 * @param itemId
	 *            二级指标Id
	 */
	public Integer evaluateCenterItemScore(Set<CenterParam> set, Integer itemId);
	
	
	public Integer evaluateCenterItemScore2(CenterParam cp ,Integer itemId);
	
	
	public Integer evaluateCenterItemScore(List<JSONObject> cplist, String flag);
	
	@Deprecated
	public Integer evaluateCenterItemScore(Map<String,List<JSONObject>> itemscoreMap,Integer itemId);

	public Integer evaluateCenterItemScore(Map<String,List<JSONObject>> itemscoreMap,Integer itemId, String checkType, String sheetName);
	
	public Integer evaluateCenterItemScore(Map<String,List<JSONObject>> itemscoreMap,Integer itemId, String checkType, Integer sheetId);
	
	public CenterScore getCenterScoreByIds(Integer itemId, Integer centerId);
	
	public CenterScore getCheckedCenterItems(final Integer itemId, final Integer centerId, final String sheetId);
	
	//public CenterScore getSecondLevelItemScores(final Integer itemId, final Integer centerId, final String sheetId);
	
	public CenterScore getCenterScoreByParams(Integer itemId, Integer centerId, String checkType, String sheetName, String userName);
}
