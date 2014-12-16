package org.spes.service.staff;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.spes.bean.StaffParam;
import org.spes.bean.StaffScore;

public interface StaffScoreService {

	/**
	 * 保存窗口普通人员三级指标得分集合
	 * 
	 * @param set
	 *            指标得分集合
	 */
	public void saveStaffParamScore(Set<StaffParam> set);

	/**
	 * 评估二级指标，根据三级指标得分
	 * 
	 * @param set
	 *            三级指标得分集合
	 * @param itemId
	 *            二级指标Id
	 */
	public void evaluateStaffItemScore(Set<StaffParam> set, Integer itemId)
			throws Exception;

	
	/**
	 * 
	 * @param staffScoreMap
	 * @param itemId
	 * @param userId
	 * @param sheetType
	 * @param sheetId
	 */
	public void evaluateStaffItemScore(
			Map<String, List<JSONObject>> staffScoreMap, Integer itemId,
			Integer userId, String sheetType, String sheetId);

	/**
	 * 查找指标是否考核
	 * 
	 * @param itemId
	 *            指标ID
	 * @param userId
	 *            用户Id
	 * @param windowId
	 *            窗口Id
	 * @param centerId
	 *            中心Id
	 * @return 考核对象
	 */
//	public StaffScore getStaffScoreByIds(Integer itemId, Integer userId,
//			Integer windowId, Integer centerId);
	
	public StaffScore getCheckedStaffItem(Integer itemId, Integer userId,
			Integer windowId, Integer centerId, String sheetId);
	

}
