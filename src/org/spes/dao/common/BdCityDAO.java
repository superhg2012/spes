package org.spes.dao.common;

import java.util.List;

import org.spes.bean.BdCity;

public interface BdCityDAO {

	public void save(BdCity transientInstance);

	public void delete(BdCity persistentInstance);

	public List findByProperty(String propertyName, Object value);

	public List findAll();

}