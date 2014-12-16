package org.spes.dao.common;

import java.util.List;

import org.spes.bean.BdCounty;

public interface BdCountyDAO {

	public void save(BdCounty transientInstance);

	public void delete(BdCounty persistentInstance);

	public List findByProperty(String propertyName, Object value);

	public List findAll();

}