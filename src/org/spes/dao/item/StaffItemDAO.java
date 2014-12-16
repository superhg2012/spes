package org.spes.dao.item;

import java.util.ArrayList;
import java.util.List;

import org.spes.bean.StaffItem;

public interface StaffItemDAO {

	public void save(StaffItem transientInstance);

	public void delete(StaffItem persistentInstance);

	public List findByProperty(String propertyName, Object value);

	public List findAll();

	/**
	 * 通过窗口Id与中心ID属性，获取窗口人员评价指标
	 * hegang
	 * @param windowId
	 *            窗口ID
	 * @param centerId
	 *            中心ID
	 * @return 窗口普通人员评价指标列表
	 */
	public List<StaffItem> findStaffItemByProperty(Integer windowId,
			Integer centerId);

	/**
	 * 通过窗口Id与中心ID,指标等级属性，获取窗口人员评价指标
	 * hegang
	 * @param windowId
	 * @param centerId
	 * @param itemGRade
	 * @return 窗口普通人员评价指标列表
	 */
	public List<StaffItem> findStaffItemByProperty(Integer windowId,
			Integer centerId, Integer itemGrade);

	/**
	 * 通过指标名称等参数获取人员评价指标
	 * hegang
	 * @param itemName
	 * @param windowId
	 * @param centerId
	 * @return
	 */
	public List<StaffItem> findStaffByItemName(String itemName,
			Integer windowId, Integer centerId);

	/**
	 * 通过指标名称等参数获取人员评价指标
	 * hegang
	 * @param itemName
	 * @param parentId
	 * @param windowId
	 * @param centerId
	 * @return
	 */
	public List<StaffItem> findByItemNameAndIds(String itemName,
			Integer parentId, Integer windowId, Integer centerId);	
	

	/**
	 * 通过指标Id 窗口Id 中心Id获取人员指标
	 * hegang
	 * @param itemId
	 * @param windowId
	 * @param centerId
	 * @return
	 */
	public List<StaffItem> findStaffItemByIds(Integer itemId, Integer windowId,
			Integer centerId);
	
	// //////////////////////zhoushaojun start///////////////////////////////

	/**
	 * 查询所有指标数据
	 */
	public List<StaffItem> getAllParaData(int centerId, int windowId);

	/**
	 * 查询一级指标数据隶属其的二级指标信息
	 */
	public List<StaffItem> getFirstLevelAndSecondLevelPara(int centerItemId,
			int centerId, int windowId);

	/**
	 * 查询一级指标数据，通过主键Id
	 * 
	 */
	public StaffItem getFirstLevelPara(int staffItemId);

	/**
	 * 查询当前一级指标在数据库中是否存在 True 不存在 false 存在
	 */
	public boolean curParaInDatabase(String paraname, int centerId, int windowId);

	/**
	 * 判断 在数据库中是否存在与其名称相同的指标 True 不存在 false 存在
	 */
	public boolean otherSameNameParaInDatabase(int centerItemId,
			String paraname, int centerId, int windowId);

	/**
	 * （一级指标内容更新）更新CenterItem内容，根据centerItemid()
	 */
	public boolean updateCenterItemById(int staffItemId, String paraname,
			double weight, boolean enable, String paraType);

	/**
	 * （二级&三级指标内容更新）更新CenterItem内容，根据centerItem实体类 -1 更新失败 -2 父亲指标下存在名称相同的指标内容 1
	 * 更新成功
	 * 
	 */
	public int updateCenterItemByConcreteItem(StaffItem item);

	/**
	 * 存储一级指标数据（） 非-1 保存成功 -1 保存失败 存储一级指标数据 非-1 保存成功 -1 保存失败、 配合
	 * otherSameNameParaInDatabase 方法检测数据库中是否存在同名指标
	 */
	public int storeFirstLevelPara(StaffItem firstPara);

	/**
	 * 存储二级&三级指标 均可使用此方法 -1 保存失败 -2 当前父亲指标下，存在名字相同的指标而无法保存 1 保存成功
	 */
	public int storeSecondAndThirdPara(StaffItem item);

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
	public ArrayList<StaffItem> getCurSecondAndThridParameters(int firstItemId);

	/**
	 * 获取二级指标下面的三级指标内容
	 */
	public List<StaffItem> getCurThirdParas(int itemId);
	// //////////////////////zhoushaojun end////////////////////////////////

}