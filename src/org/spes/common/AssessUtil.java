package org.spes.common;

import java.util.HashMap;
import java.util.Map;

public class AssessUtil {

	public static String getSheetState(String key) {
		Map<String, String> stateMap = new HashMap<String, String>();
		stateMap.put("unfinished", Constants.SHEETSTATE_UNFINISHED);
		stateMap.put("finished", Constants.SHEETSTATE_FINISHED);
		stateMap.put("notstarted", Constants.SHEETSTATE_NOT_START);
		return stateMap.get(key);
	}
}
