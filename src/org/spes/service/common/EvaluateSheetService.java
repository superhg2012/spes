package org.spes.service.common;

import java.util.List;

import org.spes.bean.Evaluatesheet;

public interface EvaluateSheetService {

	/**
	 * 
	 * @param sheetType øº∫À¿‡–Õ
	 * @return
	 */
	public List<Evaluatesheet> getEvaluateSheetList(String sheetType, String sheetState);
	
	public List<Evaluatesheet> getEvaluateSheet(Integer sheetId);
	
	public boolean deleteEvaluateSheet(String sheetId);
	
	public boolean createNewEvaluateSheet(String sheetName, String userName, String sheetState, String sheetType, String checkType);
}
