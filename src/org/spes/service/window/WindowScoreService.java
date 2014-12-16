package org.spes.service.window;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.spes.bean.WindowParam;
import org.spes.bean.WindowScore;

/**
 * 窗口指标得分业务接口
 * 
 * @author HeGang
 * 
 */
public interface WindowScoreService {

	/**
	 * 保存三级指标的得分值
	 * 
	 * @param set
	 *            三级指标集合
	 */
	public void saveWindowParamScore(Set<WindowParam> set);

	/**
	 * 考核指标
	 * 
	 * @param set
	 *            指标参数
	 * @param itemId
	 *            被考核指标Id
	 */
	public void evaluateWindowItemScore(Set<WindowParam> set, Integer itemId);

	/**
	 * 查找指标
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
	 * 考核指标
	 * 
	 * @param windowScoreMap
	 *            指标得分映射
	 * @param itemId
	 *            一级指标ID
	 */
	public void evaluateWindowItemScore(
			Map<String, List<JSONObject>> windowScoreMap, Integer itemId, String sheetType, String sheetName, String sheetId);
}
