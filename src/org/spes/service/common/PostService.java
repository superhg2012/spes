package org.spes.service.common;

import java.util.List;

import org.spes.bean.Post;
/**
 * ������λҵ��ӿ�
 * 
 * @author HeGang
 *
 */
public interface PostService {

	/**
	 * ��ȡȫ����λ��Ϣ
	 * 
	 * @return ��λ�����б�
	 */
	public List<Post> getAllPosts();
}
