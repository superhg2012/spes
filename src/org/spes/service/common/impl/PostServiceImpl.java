package org.spes.service.common.impl;

import java.util.List;

import org.spes.bean.Post;
import org.spes.dao.common.PostDAO;
import org.spes.service.common.PostService;

public class PostServiceImpl implements PostService {

	private PostDAO postDao = null;

	//“¿¿µ◊¢»Î
	public void setPostDao(PostDAO postDao) {
		this.postDao = postDao;
	}
	
	public List<Post> getAllPosts() {
		return postDao.findAll();
	}


}
