package org.spes.action.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.spes.bean.Post;
import org.spes.service.common.PostService;

/**
 * ¸ÚÎ»Action
 * 
 * @author Administrator
 * 
 */
public class PostAction {

	private PostService postService = null;

	public PostService getPostService() {
		return postService;
	}

	public void setPostService(PostService postService) {
		this.postService = postService;
	}

	public void getPosts() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		List<Post> list = postService.getAllPosts();

		if (null != list) {
			JSONArray jsonArray = JSONArray.fromObject(list);
			response.getWriter().write(jsonArray.toString());
			jsonArray.clear();
		}
	}
}
