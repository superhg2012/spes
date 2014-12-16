import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Set;

import com.alcatel.omc.fwk.utilities.Resources;
import com.alcatel.omc.fwk.utilities.trace.LocalTraceParameters;
import com.alcatel.omc.fwk.utilities.trace.TraceManager;


public class GenParameters {

	protected static HashMap<String, Object> defaultValuesMap_ = new HashMap<String, Object>();
	
	
	public static final String PARAM_TRACE_CFG_PATH = "TRACE_CFG_PATH";
	public static final String DEF_TRACE_CFG_PATH = "/tmp";

	public static final String PARAM_TRACE_POLLING_PERIOD = "TRACE_POLLING_PERIOD";
	public static final Integer DEF_TRACE_POLLING_PERIOD = -1;
	
	
	public void loadConfiguration(String configUrl, String componentName) {
		Resources.getInstance().initResources(configUrl);
		String trace_ComponentName = componentName; //getStringParam(PARAM_TRACE_COMPONENT_NAME);
		String trace_cfg_path = String.valueOf(getStringParam(PARAM_TRACE_CFG_PATH));
		
		String soncn_home = null;
		if (trace_cfg_path.startsWith("$SONCN_HOME")) {
			soncn_home = System.getenv("SONCN_HOME");
			if(soncn_home!=null) {
				soncn_home = soncn_home.replaceAll("\\\\","/");
				trace_cfg_path = trace_cfg_path.replace("$SONCN_HOME",soncn_home);
			}
		}
		//check if it starts with $PROPERTY_SONCN_HOME to get SONCN_HOME system property
		else if (trace_cfg_path.startsWith("$PROPERTY_SONCN_HOME")) {
			soncn_home = System.getProperty("SONCN_HOME");
			if(soncn_home!=null) {
				soncn_home = soncn_home.replaceAll("\\\\","/");
				trace_cfg_path = trace_cfg_path.replace("$PROPERTY_SONCN_HOME",soncn_home);
			}
		}
		System.out.println("trace configuration path: " + trace_cfg_path);
 		int trace_period_pooling = getIntParam(PARAM_TRACE_POLLING_PERIOD);

		//generate the trace.cfg with default values
		int BUF_SZ = 1024;
		File ConfigFile = new File(trace_cfg_path + "/trace.cfg");
		if (!ConfigFile.exists()) {
			try {
				Writer fcsvWriter = new BufferedWriter(new FileWriter(ConfigFile),BUF_SZ);
				fcsvWriter.write("TRACE_OUTPUT_PATH=" + soncn_home + "/data/trace\n");
				fcsvWriter.write("TRACE_MAX_SIZE = 50000\n");
				fcsvWriter.write("TRACE_ACTIVE_LEVELS =1,2,7\n");
				fcsvWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	    //TraceManager.init(new Log4jTraceParameters(traceFile, tracePath));
		TraceManager.init(new LocalTraceParameters(trace_ComponentName,
				System.getProperty("user.name"),
				trace_cfg_path,
				trace_period_pooling), true
		);
		TraceManager.TrProcess(trace_ComponentName+" was started with config file: "+configUrl);
	}



	public Set<String> getParameterNames() {
		return defaultValuesMap_.keySet();
	}
	
	public Resources getProperties() {
		return Resources.getInstance();
	}

	public String getStringParam(String key) {
		String result = key;
		Object defValue = defaultValuesMap_.get(key);
		if (defValue!=null && defValue instanceof String){
			result = Resources.getInstance().getStringProperty(key,(String)defValue);
			// Notes: Remove the quotes around the value if they are present.
			// The case is required so ";" can be defined.
			if(result.length()>1 && result.charAt(0)=='\"' && result.charAt(result.length()-1)=='\"') {
				result = result.substring(1,result.length()-1);
			}
		}

		else
			result = Resources.getInstance().getStringProperty(key);
		TraceManager.TrDebug("getStringParam("+key+") = "+result);
		return result; 
	}
	
	
	
	public float getFloatParam(String key) {
		float result = 0;
		Object defValue = defaultValuesMap_.get(key);
		if (defValue!=null && defValue instanceof Float)
			result = Resources.getInstance().getFloatProperty(key, (Float)defValue);
		else
			result = Resources.getInstance().getFloatProperty(key);
		TraceManager.TrDebug("getFloatParam("+key+") = "+result);
		return result;		
	}

	public boolean getBooleanParam(String key) {
		boolean result = false;
		Object defValue = defaultValuesMap_.get(key);
		if (defValue!=null && defValue instanceof Boolean)
			result = Resources.getInstance().getBooleanProperty(key, (Boolean)defValue);
		else
			result = Resources.getInstance().getBooleanProperty(key);
		TraceManager.TrDebug("getBooleanProperty("+key+") = "+result);
		return result;		
	}
	
	public int getIntParam(String key) {
		int result = 0;
		Object defValue = defaultValuesMap_.get(key);
		if (defValue!=null && defValue instanceof Integer)
			result = Resources.getInstance().getIntProperty(key, (Integer)defValue);
		else
			result = Resources.getInstance().getIntProperty(key);

		TraceManager.TrDebug("getIntParam("+key+") = "+result);
		return result;
	}
	
}
