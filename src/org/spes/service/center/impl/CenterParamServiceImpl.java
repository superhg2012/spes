package org.spes.service.center.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.spes.bean.CenterParam;
import org.spes.dao.item.CenterParamDAO;
import org.spes.service.center.CenterParamService;

public class CenterParamServiceImpl implements CenterParamService {

	private CenterParamDAO centerParamDao = null;

	public void setCenterParamDao(CenterParamDAO centerParamDao) {
		this.centerParamDao = centerParamDao;
	}

	public CenterParamDAO getCenterParamDao() {
		return centerParamDao;
	}

	public CenterParam getParamById(Integer itemId, Integer centerId) {
		return centerParamDao.findByCenterIdAndItemId(itemId, centerId);
	}

	public void saveCenterParamScore(Set<CenterParam> set) {
		if (null != set && set.size() > 0) {
			for (CenterParam cp : set) {
				cp.setEvaluateDate(new Timestamp(new Date().getTime()));
				centerParamDao.save(cp);
			}
		}
	}

	public void saveCenterParamScore(List<CenterParam> list) {
		if (null != list && list.size() > 0) {
			for (CenterParam cp : list) {
				cp.setEvaluateDate(new Timestamp(new Date().getTime()));
				centerParamDao.save(cp);
			}
		}
	}

	public CenterParam queryCenterParams(Integer itemId, Integer centerId,
			String checkType, String sheetName, String userName) {
		return centerParamDao.findCenterParamByParams(itemId, centerId, checkType, sheetName, userName);
	}

}
