package org.spes.dao.item;

import java.util.List;

import org.spes.bean.StaffParam;

public interface StaffParamDAO {

	public void save(StaffParam transientInstance);

	public void delete(StaffParam persistentInstance);

	public List findByProperty(String propertyName, Object value);

	public List findAll();

}