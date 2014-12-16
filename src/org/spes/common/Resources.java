package org.spes.common;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;

import com.alcatel.omc.fwk.utilities.URLInputStream;
import com.alcatel.omc.fwk.utilities.trace.TraceManager;

public class Resources extends Properties {

	private static Resources instance = null;

	public static Resources getInstance() {

		if (instance == null) {
			instance = new Resources();
		}
		return instance;
	}

	public Vector getVectorProperty(String key, char separator) {
		return getVectorProperty(key, separator, 1, true);
	}

	public Vector getVectorProperty(String key, char separator, int width) {
		return getVectorProperty(key, separator, width, true);
	}

	public Vector getVectorProperty(String key, char separator, int width,
			boolean vertical) {
		key = key.trim();
		if (width < 1) {
			System.out.println("Invalid vector width");
			return new Vector();
		}
		Vector res = new Vector(width);
		Vector lineRes = new Vector();
		String rawProperty = getProperty(key);
		if (rawProperty == null)
			return res;
		if (rawProperty.length() < 1)
			return res;
		Vector columns[] = new Vector[width];
		for (int i = 0; i < width; i++)
			columns[i] = new Vector();

		Vector line = new Vector(width);
		int beginIndex = 0;
		int endIndex = 1;
		int col = 0;
		for (; endIndex != -1; beginIndex = endIndex + 1) {
			endIndex = rawProperty.indexOf(separator, beginIndex);
			String tempRes;
			if (endIndex == -1) {
				if (rawProperty.length() - beginIndex < 1)
					break;
				tempRes = rawProperty.substring(beginIndex,
						rawProperty.length());
				tempRes = tempRes.replace('\t', ' ');
				tempRes = tempRes.trim();
				columns[col].add(tempRes);
				line.add(tempRes);
				if (col == width - 1)
					lineRes.add(line);
				break;
			}
			if (endIndex - beginIndex < 1)
				continue;
			tempRes = rawProperty.substring(beginIndex, endIndex);
			tempRes = tempRes.replace('\t', ' ');
			tempRes = tempRes.trim();
			columns[col].add(tempRes);
			line.add(tempRes);
			if (++col == width) {
				col = 0;
				lineRes.add(line);
				line = new Vector(width);
			}
		}

		if (width == 1)
			return columns[0];
		for (int i = 0; i < width - 1; i++)
			if (columns[i].size() != columns[i + 1].size()) {
				System.out.println((new StringBuilder())
						.append("Cardinality Error in resource ").append(key)
						.append(" on line : ").append(i).toString());
				System.out.println((new StringBuilder()).append("===> ")
						.append(columns[i]).toString());
				return res;
			}

		for (int i = 0; i < width; i++)
			res.add(columns[i]);

		if (vertical)
			return res;
		else
			return lineRes;
	}

	public Vector getHandleProperty(String key, char separator) {
		Vector _Vmenu = new Vector();
		boolean b = true;
		for (int i = 0; b; i++) {
			String s = (new StringBuilder()).append(key).append(i).toString();
			Vector v = getVectorProperty(s, ',');
			if (v.size() != 0) {
				_Vmenu.add(v);
				continue;
			}
			if (i > 0)
				b = false;
		}

		return _Vmenu;
	}

	public String getStringProperty(String key, String defaultVal) {
		String rawProperty = defaultVal;
		if (key != null) {
			key = key.trim();
			rawProperty = getProperty(key);
			if (rawProperty != null) {
				rawProperty = rawProperty.trim();
				rawProperty = rawProperty.replace('\t', ' ');
			} else {
				rawProperty = defaultVal;
			}
		} else {
			TraceManager
					.TrError("(Resources::378) Resources.getStringProperty : unable to retrieve the ressource with key : null");
		}
		return rawProperty;
	}

	public String getStringProperty(String key) {
		return getStringProperty(key, null);
	}

	public int getIntProperty(String key, int defaut) {
		Integer i = new Integer(defaut);
		String rawProperty = getStringProperty(key, i.toString());
		i = new Integer(rawProperty);
		return i.intValue();
	}

	public int getIntProperty(String key) {
		String rawProperty = getStringProperty(key);
		Integer i = new Integer(rawProperty);
		return i.intValue();
	}

	public float getFloatProperty(String key, float defaut) {
		Float f = new Float(defaut);
		String rawProperty = getStringProperty(key, f.toString());
		f = new Float(rawProperty);
		return f.floatValue();
	}

	public float getFloatProperty(String key) {
		String rawProperty = getStringProperty(key);
		Float f = new Float(rawProperty);
		return f.floatValue();
	}

	public boolean getBooleanProperty(String key, boolean defaut) {
		Boolean B = new Boolean(defaut);
		String rawProperty = getStringProperty(key, B.toString());
		rawProperty = rawProperty.trim();
		rawProperty = rawProperty.toLowerCase();
		return (new Boolean(rawProperty)).booleanValue();
	}

	public boolean getBooleanProperty(String key) {
		return getBooleanProperty(key, false);
	}

	public String computeProperty(String propertyName, String type,
			String release) {
		String property = (new StringBuilder()).append(propertyName)
				.append(".").append(type).append(".").append(release)
				.toString();
		if (containsKey(property))
			return property;
		property = (new StringBuilder()).append(propertyName).append(".")
				.append(type).toString();
		if (containsKey(property))
			return property;
		else
			return propertyName;
	}

	public void initResources(String resFileName) {

		if (resFileName == null) {
			return;
		}

		if (instance == null) {
			instance = this;
		}

		InputStream input = null;
		try {
			input = new FileInputStream(resFileName);
			load(input);
		} catch (Exception e1) {
			System.err.println((new StringBuilder())
					.append("initResources: caught exception <")
					.append(e1.getMessage()).append("> when loading local <")
					.append(resFileName)
					.append("> => try to load resources as URL...").toString());
			try {
				input = new URLInputStream(resFileName);
				load(input);
			} catch (Exception e2) {
				System.err.println((new StringBuilder())
						.append("initResources: caught exception <")
						.append(e2.getMessage()).append("> when loading URL <")
						.append(resFileName)
						.append("> => cannot load resources!").toString());
				return;
			}
		}

		try {
			input.close();
		} catch (Exception e3) {
			System.err.println((new StringBuilder())
					.append("Cannot close resource file ").append(resFileName)
					.toString());
		}

	}
}
