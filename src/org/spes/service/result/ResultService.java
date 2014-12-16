package org.spes.service.result;

import java.util.List;

public interface ResultService {
	public List GetCenterResult(String from,String to,String type);
	
	public List GetWindowScore(Integer windowId,String from,String to,String type);
	
	public List GetStaffAndScoreByWindowId(Integer windowId,String from,String to,String type);
	
	public List GetStaffAndScoreByUserIdAndTime(Integer userId,String from,String to,String type);
	
	public List GetStaffAndScoreBUIdAndTime(Integer userId,String from,String to,String type);
	
	public String GenerateDownloadFile(String from, String to, String type);
}
