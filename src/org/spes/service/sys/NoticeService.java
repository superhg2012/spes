package org.spes.service.sys;

import java.util.List;

import org.spes.bean.Notice;

public interface NoticeService {
	/**
	 * 发布一条系统通知
	 * @param title
	 * @param content
	 * @return
	 */
	public int addNotice(String title, String content);
	/**
	 * 获取所有系统公告
	 * @return 公告列表
	 */
	public List<Notice> getNoticeList();
	
	/**
	 * get notice list by property direction 
	 * @return
	 */
	public List<Notice> getNoticeOrderBy(String property, String dir);
	
	public int editNotice(Integer noticeID, String title, String content);
	
	public String deleteNotice(Integer noticeID);
	
	/**
	 * get the notice by noticeId
	 * @param noticeId
	 * @return
	 */
	public Notice getNoticeById(Integer noticeId);
	
	/**
	 * get the userName by userId.
	 * @param userId
	 * @return
	 */
	public String getUserNameByUserId(Integer userId);
	
	/**
	 * check the notice published by others in his center and the notice published by the super manager
	 * 
	 * @return the notice list
	 */
	public List checkNoticeOrderBy(String property, String value);
}
