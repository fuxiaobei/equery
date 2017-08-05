package com.asiainfo.billing.drquery.model;

import java.io.Serializable;

/**
 * 字段模型，包含类似的字段名称，中文名的属性信息
 * 
 * @author Rex Wong
 *
 * @version
 */
public class Field implements Serializable , Comparable<Field> {
	
	private static final long serialVersionUID = -3884879199227489962L;
	private String name;
	private String cname;//中文
	private String escapeField;//是否是转义字段
	private boolean commonField;//是否是帐前历史清单字段
	private Integer index;
    private String backName;
	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	private String type;//字段数据类型
	private boolean summaryField;//是否是统计字段
	private boolean mergeField;//是否是合并字段
	private boolean mergeKey;//确定两条话单是否合并
	private boolean distinct;//是否是去除字段
    private boolean defaultField;
    private String mappingField;//是否是科目映射字段
    public Field(){}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getEscapeField() {
		return escapeField;
	}

	public void setEscapeField(String escapeField) {
		this.escapeField = escapeField;
	}

	public boolean isCommonField() {
		return commonField;
	}

	public void setCommonField(boolean commonField) {
		this.commonField = commonField;
	}

	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isSummaryField() {
		return summaryField;
	}

	public void setSummaryField(boolean summaryField) {
		this.summaryField = summaryField;
	}

	public boolean isMergeField() {
		return mergeField;
	}

	public void setMergeField(boolean mergeField) {
		this.mergeField = mergeField;
	}

	public boolean isMergeKey() {
		return mergeKey;
	}

	public void setMergeKey(boolean mergeKey) {
		this.mergeKey = mergeKey;
	}

	public boolean isDistinct() {
		return distinct;
	}

	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	public boolean isDefaultField() {
		return defaultField;
	}

	public void setDefaultField(boolean defaultField) {
		this.defaultField = defaultField;
	}

	public int compareTo(Field o) {
		return this.getIndex().compareTo(o.getIndex());
	}

	public String getMappingField() {
		return mappingField;
	}

	public void setMappingField(String mappingField) {
		this.mappingField = mappingField;
	}

    public String getBackName() {
        return backName;
    }

    public void setBackName(String backName) {
        this.backName = backName;
    }
}