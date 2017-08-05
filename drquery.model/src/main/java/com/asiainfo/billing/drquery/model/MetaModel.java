package com.asiainfo.billing.drquery.model;

import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;

import org.apache.commons.beanutils.*;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Rex Wong
 * 
 *         18 Apr 2012
 * @version
 */
public class MetaModel implements Serializable{
	private static final long serialVersionUID = 5476537497209662498L;
	private Map<String, Field> fields;
	private String modelId;
	private String table;
	private String name;
	private String process;
	private String statType;
	private String statRule;
	private  String[] mergeFields;
	private  String[] mergeKeys;
	private  String[] summaryFields;
	private  String[] distinctFields;
	private  Map<String, Field> commonFields;
	private  Map<String, Field> escapeFields;
	private  Map<String, Field> queryFields;
	
	private String billType;
    private boolean useCache;
    private String invoke;

    //每个接口对应的sql 键值对如：<F11,"select * from student">
    private Map<String,String> sql;

    private  Map<String,String> backNameToDBName;
    private  Map<String,String> dbNameToBackName;

	/**
	 * 得到当前model中需要合并的字段
	 * 
	 * @return
	 */
	public String[] getMergeFields(){
		if(ArrayUtils.isEmpty(mergeFields)){
			List<String> tempmergeFields = new ArrayList<String>();
			for(Entry<String,Field> entry:fields.entrySet()){
				Field field = entry.getValue();
				if(field.isMergeField()){
					tempmergeFields.add(field.getName());
				}
			}
			mergeFields = new String[tempmergeFields.size()];
			for(int i =0;i<tempmergeFields.size();i++){
				mergeFields[i]=tempmergeFields.get(i);
			}
		}
		return mergeFields;
	}
	
	/**
	 * 得到判断是否进行合并的判定字段
	 * @return
	 */
	public String[] getMergeKeys(){
		if(ArrayUtils.isEmpty(mergeKeys)){
			List<String> tempmergeKeys = new ArrayList<String>(); 
			for(Entry<String,Field> entry:fields.entrySet()){
				Field field = entry.getValue();
				if(field.isMergeKey()){
					tempmergeKeys.add(field.getName());
				}
			}
			mergeKeys = new String[tempmergeKeys.size()];
			for(int i =0;i<tempmergeKeys.size();i++){
				mergeKeys[i]=tempmergeKeys.get(i);
			}
		}
		return mergeKeys;
	}
	
	/**
	 * 得到需要进行统计的字段
	 * @return
	 */
	public String[] getSummaryFields(){
		if(ArrayUtils.isEmpty(summaryFields)){
			List<String> tempsummaryFields = new ArrayList<String>(); 
			for(Entry<String,Field> entry:fields.entrySet()){
				Field field = entry.getValue();
				if(field.isSummaryField()){
					tempsummaryFields.add(field.getName());
				}
			}
			summaryFields = new String[tempsummaryFields.size()];
			for(int i =0;i<tempsummaryFields.size();i++){
				summaryFields[i]=tempsummaryFields.get(i);
			}
		}
		return summaryFields;
	}
	/**
	 * 得到去除的标识字段，即根据这些字段的值进行判重
	 * @return
	 */
	public String[] getDistinctFields(){
		if(ArrayUtils.isEmpty(distinctFields)){
			List<String> tempsdistinctFields = new ArrayList<String>(); 
			for(Entry<String,Field> entry:fields.entrySet()){
				Field field = entry.getValue();
				if(field.isDistinct()){
					tempsdistinctFields.add(field.getName());
				}
			}
			distinctFields = new String[tempsdistinctFields.size()];
			for(int i =0;i<tempsdistinctFields.size();i++){
				distinctFields[i]=tempsdistinctFields.get(i);
			}
		}
		return distinctFields;
	}
	/**
	 * 返回常用字段
	 * @return return Map<String,Field>:key[字段名称] value[字段<code>Field</code>]
	 */
	public Map<String, Field> getCommonFields(){
		if(MapUtils.isEmpty(commonFields)){
			commonFields = new HashMap<String,Field>(); 
			for(Entry<String,Field> entry:fields.entrySet()){
				Field field = entry.getValue();
				if(field.isCommonField()&&StringUtils.isNotEmpty(field.getCname())){
					commonFields.put(entry.getKey(), entry.getValue());
				}
			}
		}
		return commonFields;
	}
	/**
	 * 返回需要转义的字段（field）
	 * 
	 * @return Map<String,Field>:key[字段名称] value[字段<code>Field</code>]
	 */
	public Map<String, Field> getEscapeFields(){
        if(fields == null) {
            return null;
        }
		if(MapUtils.isEmpty(escapeFields)){
			escapeFields = new HashMap<String,Field>(); 
			for(Entry<String,Field> entry:fields.entrySet()){
				Field field = entry.getValue();
				if(StringUtils.isNotEmpty(field.getEscapeField())){
					escapeFields.put(entry.getKey(), entry.getValue());
				}
			}
		}
		return escapeFields;
	}
	
	/**
	 * 返回查询字段
	 * @return
	 */
	public Map<String, Field> getQueryFields() {
		if(MapUtils.isEmpty(queryFields)){
			queryFields = new HashMap<String,Field>(); 
			for(Entry<String,Field> entry:fields.entrySet()){
				Field field = entry.getValue();
				if(field.isMergeField()||field.isMergeKey()
						||field.isCommonField()
						||field.isDistinct()
						||field.isSummaryField()
						||StringUtils.isNotEmpty(field.getEscapeField())
						||field.isDefaultField()){
					queryFields.put(field.getName(),field);
				}
			}
		}
		return queryFields;
	}
	
	public Map<String, Field> getFields() {
		return fields;
	}

	public void setFields(Map<String, Field> fields) {
		this.fields = fields;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getStatType() {
		return statType;
	}

	public void setStatType(String statType) {
		this.statType = statType;
	}

	public String getStatRule() {
		return statRule;
	}

	public void setStatRule(String statRule) {
		this.statRule = statRule;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

    public Map<String, String> getSql() {
        return sql;
    }

    public void setSql(Map<String, String> sql) {
        this.sql = sql;
    }

    public Map<String, String> getBackNameToDBName() {
        return backNameToDBName;
    }

    public void setBackNameToDBName(Map<String, String> backNameToDBName) {
        this.backNameToDBName = backNameToDBName;
    }

    public Map<String, String> getDbNameToBackName() {
        return dbNameToBackName;
    }

    public void setDbNameToBackName(Map<String, String> dbNameToBackName) {
        this.dbNameToBackName = dbNameToBackName;
    }

    public boolean isUseCache() {
        return useCache;
    }

    public void setUseCache(boolean useCache) {
        this.useCache = useCache;
    }

    public String getInvoke() {
        return invoke;
    }

    public void setInvoke(String invoke) {
        this.invoke = invoke;
    }
}
