package org.spes.dao.common;

import java.util.List;

import org.spes.bean.BdProvince;

public interface BdProvinceDAO {

	public void save(BdProvince transientInstance);

	public void delete(BdProvince persistentInstance);

	public List findByProperty(String propertyName, Object value);

	public List findAll();

}