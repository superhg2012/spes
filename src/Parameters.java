import java.lang.reflect.Field;


public class Parameters extends GenParameters {

	private static Parameters instance;
	
	private Parameters() {
		try {
			final Field [] fields = Parameters.class.getDeclaredFields();
			for(Field field : fields){
				String fieldName = field.getName();
				if(fieldName.startsWith("PARAM_")){
					fieldName = fieldName.substring(6);
						final Object defValue =   Parameters.class.getField("DEF_" + fieldName);
						defaultValuesMap_.put(fieldName, defValue);
				}
			}
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized static Parameters getInstance() {
		if (instance == null) {
			instance = new Parameters();
		}
		return instance;
	}
}
