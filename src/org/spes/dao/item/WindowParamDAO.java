package org.spes.dao.item;

import java.util.List;

import org.spes.bean.WindowParam;

public interface WindowParamDAO {

	public void save(WindowParam transientInstance);

	public void delete(WindowParam persistentInstance);

	public List findByProperty(String propertyName, Object value);

	public List findAll();

}