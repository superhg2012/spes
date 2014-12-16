package org.spes.service.staff;

import java.util.ArrayList;
import java.util.List;

import org.spes.bean.StaffItem;

public interface StaffItemService {

	/**
	 * 根据窗口Id与中心Id获取窗口普通人员的评价指标
	 * 
	 * @param windowId
	 *            窗口Id
	 * @param centerId
	 *            中心Id
	 * @return 窗口普通人员评价指标
	 */
	public List<StaffItem> getStaffItemByWinIdAndCenId(Integer windowId,
			Integer centerId);

	/**
	 * 根据窗口Id，指标Id，中心Id获取窗口工作人员三级评价指标
	 * 
	 * @param itemId
	 *            指标Id
	 * @param windowId
	 *            窗口Id
	 * @param centerId
	 *            中心Id
	 * @return 窗口工作人员评价指标
	 */
	public List<StaffItem> getStaffItemByFormula(Integer itemId,
			Integer windowId, Integer centerId);
	//////////////////////zhoushaojun start////////////////////////
	/**
	 * 获取所有指标数据
	 */
	public List<StaffItem> getAllParameterData(int centerId,int windowId);

	/**
	 * 查询一级指标数据隶属其的二级指标信息
	 */
	public List<StaffItem> getFirstLevelAndSecondLevelPara(int centerItemId,int centerId,int windowId);

	/**
	 * /** 获取一级指标数据，通过主键Id
	 */
	public StaffItem getFirstLevelparameter(int staffItemId);

	/**
	 * 查询当前一级指标在数据库中是否存在
	 */
	public boolean curParaIsInDatabase(String paraname,int centerId,int windowId);

	/**
	 * 判断 在数据库中是否存在与其名称相同的指标 True 不存在 false 存在
	 */
	public boolean otherSameNameParaInDatabase(int centerItemId, String paraname,int centerId,int windowId);

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
	public int updateCenterItemByConcreteItem(StaffItem item);

	/**
	 * 存储一级指标数据（） 非-1 保存成功 -1 保存失败 存储一级指标数据 非-1 保存成功 -1 保存失败、
	 * 配合 otherSameNameParaInDatabase 方法检测数据库中是否存在同名指标
	 */
	public int storeFirstLevelPara(StaffItem firstPara);

	/**
	 * 存储二级&三级指标 均可使用此方法
	 * -1 保存失败
	 * -2 当前父亲指标下，存在名字相同的指标而无法保存
	 * 1 保存成功
	 */
	public int storeSecondAndThirdPara(StaffItem item);

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
	public ArrayList<StaffItem> getSecondAndThridParameters(int firstItemId);

	  /**
	   * 获取二级指标下面的三级指标内容
	   */
	public List<StaffItem> getCurThirdParas(int itemId);
	
	//////////////////////zhoushaojun end/////////////////////////
	
	/**
	 * 根据人员父指标Id，获取人员指标
	 * 
	 * @param parentItemId
	 *            父指标Id
	 * @return 窗口工作人员评价指标
	 */
	public List<StaffItem> getSonItemByParentId(Integer parentItemId);
	
	public List<StaffItem> getStaffItemByIdsAndItemGrade(Integer windowId, Integer centerId, Integer itemGrade );
}
