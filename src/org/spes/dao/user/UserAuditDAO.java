package org.spes.dao.user;

import java.util.List;

import org.spes.bean.UserAudit;

public interface UserAuditDAO {

	public void save(UserAudit transientInstance);

	public void delete(UserAudit persistentInstance);

	public List findByProperty(String propertyName, Object value);

	public List findAll();

}