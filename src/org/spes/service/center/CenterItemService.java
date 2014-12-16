package org.spes.service.center;

import java.util.ArrayList;
import java.util.List;

import org.spes.bean.CenterItem;

public interface CenterItemService {
	
	
	public List<CenterItem> getAllParameterOfCenter(int centerId);
	/**
	 * 获取所有指标数据
	 */
	public List<CenterItem> getAllParameterData();

	/**
	 * 查询一级指标数据隶属其的二级指标信息
	 */
	public List<CenterItem> getFirstLevelAndSecondLevelPara(int centerItemId);

	/**
	 * /** 获取一级指标数据，通过主键Id
	 */
	public CenterItem getFirstLevelparameter(int centerId);

	/**
	 * 查询当前一级指标在数据库中是否存在
	 */
	public boolean curParaIsInDatabase(String paraname);

	/**
	 * 判断 在数据库中是否存在与其名称相同的指标 True 不存在 false 存在
	 */
	public boolean otherSameNameParaInDatabase(int centerItemId, String paraname);

	/**
	 * (一级指标更新)更新CenterItem内容，根据centerItemid
	 */
	public boolean updateCenterItemById(int centerItemId, String paraname,
			double weight, boolean enable, String paraType);

	/**
	 * （二级&三级指标内容更新）更新CenterItem内容，根据centerItem实体类
	 *  -1 更新失败
	 *  -2 父亲指标下存在名称相同的指标内容
	 *  1 更新成功
	 *  
	 */
	public int updateCenterItemByConcreteItem(CenterItem item);

	/**
	 * 存储一级指标数据（） 非-1 保存成功 -1 保存失败 存储一级指标数据 非-1 保存成功 -1 保存失败、
	 * 配合 otherSameNameParaInDatabase 方法检测数据库中是否存在同名指标
	 */
	public int storeFirstLevelPara(CenterItem firstPara);

	/**
	 * 存储二级&三级指标 均可使用此方法
	 * -1 保存失败
	 * -2 当前父亲指标下，存在名字相同的指标而无法保存
	 * 1 保存成功
	 */
	public int storeSecondAndThirdPara(CenterItem item);

	/**
	 * 删除一级指标，连带二级，三级指标一其删除
	 */
	public boolean deleteAllFirstLevelPara(int centerId);

	/**
	 * 删除二级指标信息（连带三级指标一起删除）
	 */
	public boolean deleteSecondLevelPara(int itemId);
	/**
	 * 删除三级指标信息（仅当前三级指标信息）
	 *  -1 表示删除错误
	 * -2 表示其父类二级指标公式中 存在此指标，无法删除
	 * 1 表示删除成功
	 */
	public int deleteCurParaInfo(int itemId);
	/**
	 * 获取当前一级指标下的二级&三级指标信息（用于二级&三级指标信息编辑）
	 */
	public ArrayList<CenterItem> getSecondAndThridParameters(int firstItemId);

	  /**
	   * 获取二级指标下面的三级指标内容
	   */
	public List<CenterItem> getCurThirdParas(int itemId);

	/**
	 * 根据中心Id获取中心指标
	 * 
	 * @param centerId
	 *            中心ID
	 * @return 中心列表
	 */
	public List<CenterItem> getCenterItemById(Integer centerId);

	/**
	 * 根据中心Id与中心指标等级获取中心指标
	 * 
	 * @param centerId
	 *            中心Id
	 * @param itemGrade
	 *            中心指标等级
	 * @return
	 */
	public List<CenterItem> getItemByItemGradeAndId(Integer centerId,
			Integer itemGrade);

	/**
	 * 通过解析公式获取三级指标
	 * 
	 * @param itemId
	 *            二级指标Id
	 * @param centerId
	 *            中心Id
	 * @return 三级指标列表
	 */
	public List<CenterItem> getCenterItemsByFormula(Integer itemId,
			Integer centerId);
	
	public List<CenterItem> getSonItemByParentId(Integer parentItemId);
}
