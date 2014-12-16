package org.spes.dao.item;

import java.util.ArrayList;
import java.util.List;

import org.spes.bean.WindowItem;

public interface WindowItemDAO {

	/**
	 * 查询所有指标数据
	 */
	public List<WindowItem> getAllParaData(int centerId);

	/**
	 * 查询一级指标数据隶属其的二级指标信息
	 */
	public List<WindowItem> getFirstLevelAndSecondLevelPara(int centerItemId,
			int centerId);

	/**
	 * 查询一级指标数据，通过主键Id
	 * 
	 */
	public WindowItem getFirstLevelPara(int centerItemId);

	/**
	 * 查询当前一级指标在数据库中是否存在 True 不存在 false 存在
	 */
	public boolean curParaInDatabase(String paraname, int centerId);

	/**
	 * 判断 在数据库中是否存在与其名称相同的指标 True 不存在 false 存在
	 */
	public boolean otherSameNameParaInDatabase(int centerItemId,
			String paraname, int centerId);

	/**
	 * （一级指标内容更新）更新CenterItem内容，根据centerItemid()
	 */
	public boolean updateCenterItemById(int centerItemId, String paraname,
			double weight, boolean enable, String paraType);

	/**
	 * （二级&三级指标内容更新）更新CenterItem内容，根据centerItem实体类 -1 更新失败 -2 父亲指标下存在名称相同的指标内容 1
	 * 更新成功
	 * 
	 */
	public int updateCenterItemByConcreteItem(WindowItem item);

	/**
	 * 存储一级指标数据（） 非-1 保存成功 -1 保存失败 存储一级指标数据 非-1 保存成功 -1 保存失败、 配合
	 * otherSameNameParaInDatabase 方法检测数据库中是否存在同名指标
	 */
	public int storeFirstLevelPara(WindowItem firstPara);

	/**
	 * 存储二级&三级指标 均可使用此方法 -1 保存失败 -2 当前父亲指标下，存在名字相同的指标而无法保存 1 保存成功
	 */
	public int storeSecondAndThirdPara(WindowItem item);

	/**
	 * 删除一级指标信息(连带二级与三级指标 一起删除)
	 * 
	 */
	public boolean deleteFirstLevelPara(int itemId);

	/**
	 * 删除二级指标信息（连带三级指标一起删除）
	 */
	public boolean deleteSecondLevelPara(int itemId);

	/**
	 * 删除三级指标信息（仅当前三级指标信息） -1 表示删除错误 -2 表示其父类二级指标公式中 存在此指标，无法删除 1 表示删除成功
	 */
	public int deleteCurParaInfo(int itemId);

	/**
	 * 获取当前一级指标下的二级&三级指标信息（用于二级&三级指标信息编辑）
	 */
	public ArrayList<WindowItem> getCurSecondAndThridParameters(int firstItemId);

	/**
	 * 获取二级指标下面的三级指标内容
	 */
	public List<WindowItem> getCurThirdParas(int itemId);


	/**
	 * 依据指标名称与窗口Id获取窗口评价指标
	 * 
	 * @param itemName
	 *            窗口指标名称
	 * @param centerId
	 *            窗口Id
	 * @return 窗口评价指标列表
	 */
	public List<WindowItem> findByItemNameAndCenterId(String itemName,
			Integer centerId);

	/**
	 * 依据指标名称，父指标Id，中心Id获取指标
	 * 
	 * @param itemName
	 *            窗口指标名称
	 * @param parentId
	 *            父指标Id
	 * @param centerId
	 *            中心Id
	 * @return
	 */
	public List<WindowItem> findByItemNameAndIds(String itemName,
			Integer parentId, Integer centerId);

	public List<WindowItem> findByItemGradeAndCenterId(Integer centerId,
			Integer itemGrade);

	public List findByProperty(String propertyName, Object value);

	public List<WindowItem> fingByParentIdAndCenterId(Integer parentId,
			Integer centerId);
	
	public List<WindowItem> findByItemIdAndCenterId(Integer itemId, Integer centerId);
}