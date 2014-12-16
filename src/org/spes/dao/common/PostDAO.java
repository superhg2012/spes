package org.spes.dao.common;

import java.util.List;

import org.spes.bean.Post;

public interface PostDAO {

	public void save(Post transientInstance);

	public void delete(Post persistentInstance);

	public List findByProperty(String propertyName, Object value);

	public List findAll();

}