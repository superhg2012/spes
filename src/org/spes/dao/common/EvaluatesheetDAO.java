package org.spes.dao.common;

import java.util.List;

import org.spes.bean.Evaluatesheet;

public interface EvaluatesheetDAO {

	public abstract Integer save(Evaluatesheet transientInstance);

	public abstract void delete(Evaluatesheet persistentInstance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findBySheetName(Object sheetName);

	public abstract List findAll();

	public abstract List findEvaluateSheet(String sheetType, String sheetState);
	
	public abstract boolean delete(String sheetId);
	
}