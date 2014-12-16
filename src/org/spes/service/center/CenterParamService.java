package org.spes.service.center;

import java.util.List;
import java.util.Set;

import org.spes.bean.CenterParam;

public interface CenterParamService {
	
	public CenterParam getParamById(Integer itemId, Integer centerId);
	
	/**
	 * ��������ָ��÷�
	 * @param set
	 */
	public void saveCenterParamScore(Set<CenterParam> set);
	
	public void saveCenterParamScore(List<CenterParam> list);

	public CenterParam queryCenterParams(Integer itemId, Integer centerId,
			String checkType, String sheetName, String userName);
}
