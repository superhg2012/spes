package org.spes.service.common;

import java.util.List;

import org.spes.bean.Post;
/**
 * 工作岗位业务接口
 * 
 * @author HeGang
 *
 */
public interface PostService {

	/**
	 * 获取全部岗位信息
	 * 
	 * @return 岗位对象列表
	 */
	public List<Post> getAllPosts();
}
