package org.spes.service.window;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.spes.bean.WindowParam;
import org.spes.bean.WindowScore;

/**
 * ����ָ��÷�ҵ��ӿ�
 * 
 * @author HeGang
 * 
 */
public interface WindowScoreService {

	/**
	 * ��������ָ��ĵ÷�ֵ
	 * 
	 * @param set
	 *            ����ָ�꼯��
	 */
	public void saveWindowParamScore(Set<WindowParam> set);

	/**
	 * ����ָ��
	 * 
	 * @param set
	 *            ָ�����
	 * @param itemId
	 *            ������ָ��Id
	 */
	public void evaluateWindowItemScore(Set<WindowParam> set, Integer itemId);

	/**
	 * ����ָ��
	 * 
	 * @param itemId
	 * @param windowId
	 * @param centerId
	 * @return
	 */
	///public WindowScore getWindowScoreByIds(Integer itemId, Integer windowId, Integer centerId);

	/**
	 * @param itemId
	 * @param windowId
	 * @param centerId
	 * @param sheetId
	 * @return
	 */
	public WindowScore getCheckedWindowItems(Integer itemId, Integer windowId, Integer centerId, String sheetId);
	
	/**
	 * ����ָ��
	 * 
	 * @param windowScoreMap
	 *            ָ��÷�ӳ��
	 * @param itemId
	 *            һ��ָ��ID
	 */
	public void evaluateWindowItemScore(
			Map<String, List<JSONObject>> windowScoreMap, Integer itemId, String sheetType, String sheetName, String sheetId);
}
