package org.spes.service.staff;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.spes.bean.StaffParam;
import org.spes.bean.StaffScore;

public interface StaffScoreService {

	/**
	 * ���洰����ͨ��Ա����ָ��÷ּ���
	 * 
	 * @param set
	 *            ָ��÷ּ���
	 */
	public void saveStaffParamScore(Set<StaffParam> set);

	/**
	 * ��������ָ�꣬��������ָ��÷�
	 * 
	 * @param set
	 *            ����ָ��÷ּ���
	 * @param itemId
	 *            ����ָ��Id
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
	 * ����ָ���Ƿ񿼺�
	 * 
	 * @param itemId
	 *            ָ��ID
	 * @param userId
	 *            �û�Id
	 * @param windowId
	 *            ����Id
	 * @param centerId
	 *            ����Id
	 * @return ���˶���
	 */
//	public StaffScore getStaffScoreByIds(Integer itemId, Integer userId,
//			Integer windowId, Integer centerId);
	
	public StaffScore getCheckedStaffItem(Integer itemId, Integer userId,
			Integer windowId, Integer centerId, String sheetId);
	

}
