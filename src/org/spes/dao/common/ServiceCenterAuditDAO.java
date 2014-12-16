package org.spes.dao.common;

import java.util.List;

import org.spes.bean.ServiceCenterAudit;

public interface ServiceCenterAuditDAO {

	public int save(ServiceCenterAudit transientInstance);

	public void delete(ServiceCenterAudit persistentInstance);

	public List findByProperty(String propertyName, Object value);

	public List findAll();

	public void update(ServiceCenterAudit sca);

}