package com.asiainfo.billing.drquery.process.operation.fieldEscape.model;

import java.util.Map;

/**
 * 转义模型
 * 
 * @author Rex Wong
 *
 * @version
 */
public class FieldEscapeModel {
	
	private String escapeFieldCode;
	private String type;
	private Map<String, String> enumeration;//枚举值映射
	private Map<String, String> mapping;
	private String defaultEmun;
	private String localDefault;
	private String localMethod;
	private String cacheKey;
	public String getEscapeFieldCode() {
		return escapeFieldCode;
	}
	public void setEscapeFieldCode(String escapeFieldCode) {
		this.escapeFieldCode = escapeFieldCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String generateCacheKey(){
		return this.escapeFieldCode;
	}
	public String getDefaultEmun() {
		return defaultEmun;
	}
	public void setDefaultEmun(String defaultEmun) {
		this.defaultEmun = defaultEmun;
	}
	public Map<String, String> getEnumeration() {
		return enumeration;
	}
	public void setEnumeration(Map<String, String> enumeration) {
		this.enumeration = enumeration;
	}
	public Map<String, String> getMapping() {
		return mapping;
	}
	public void setMapping(Map<String, String> mapping) {
		this.mapping = mapping;
	}
	public String getLocalDefault() {
		return localDefault;
	}
	public void setLocalDefault(String localDefault) {
		this.localDefault = localDefault;
	}
	public String getLocalMethod() {
		return localMethod;
	}
	public void setLocalMethod(String localMethod) {
		this.localMethod = localMethod;
	}
	public String getCacheKey() {
		return cacheKey;
	}
	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}
}
