package org.spes.common;

public class ParameterData implements Comparable<ParameterData> {

	private Integer paramId;
	private String paramName;
	private String paramScore;

	public ParameterData(Integer paramId, String paramName, String paramScore) {
		this.paramId = paramId;
		this.paramName = paramName;
		this.paramScore = paramScore;
	}

	public Integer getParamId() {
		return paramId;
	}

	public void setParamId(Integer paramId) {
		this.paramId = paramId;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamScore() {
		return paramScore;
	}

	public void setParamScore(String paramScore) {
		this.paramScore = paramScore;
	}

	@Override
	public int compareTo(ParameterData o) {
		return this.paramName.compareTo(o.paramName);
	}

}
