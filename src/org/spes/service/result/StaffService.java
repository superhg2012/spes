package org.spes.service.result;

import java.util.List;

public interface StaffService {
	
	public List GetStaffAndScoreByWindowId(Integer windowId,String from,String to,String type);
}
