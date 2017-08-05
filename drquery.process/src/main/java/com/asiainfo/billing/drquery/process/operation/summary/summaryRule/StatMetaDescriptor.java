package com.asiainfo.billing.drquery.process.operation.summary.summaryRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class StatMetaDescriptor<K, V> {
	
    private static final Log log = LogFactory.getLog(StatMetaDescriptor.class);
    
	private String sumType;
	private K desc;
	private V value;
	private String code;
	private String bigType;
	private int sortIndex;
	private List<StatMetaDescriptor<K, V>> parts= new ArrayList<StatMetaDescriptor<K, V>>();
	
	private Map<String, Map> hisCodeProperties = new LinkedHashMap<String, Map>();
	private Map<String, Map> curCodeProperties = new LinkedHashMap<String, Map>();
    
	public StatMetaDescriptor(String code, K desc, V value, String sumType, String bigType, int sortIndex) {
		this.desc = desc;
		this.value = value;
		this.sumType = sumType;
		this.code = code;
		this.bigType = bigType;
		this.sortIndex = sortIndex;
	}
	public StatMetaDescriptor() {
	}
	public K getDesc() {
		return desc;
	}

	public V getValue() {
		return value;
	}
	

    
    public String getSumType() {
		return sumType;
	}
	public void setSumType(String sumType) {
		this.sumType = sumType;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addHisItem(String code, String resourceType, String bigType, int sortIndex){
    	if(code != null && !code.equals("") && !hisCodeProperties.containsKey(code)){
    		Map map = new LinkedHashMap();
    		map.put("code", code);
    		map.put("resourceType", resourceType);
    		map.put("bigType", bigType);
    		map.put("sortIndex", sortIndex);
    		hisCodeProperties.put(code, map);
    	}
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addCurItem(StatMetaDescriptor<K, V> statMetaDescriptor){
		if(statMetaDescriptor == null) return;
		if(statMetaDescriptor.getCode() == null || statMetaDescriptor.getCode().equals("")) return;
//		if(this.value==null){
//			this.value=statMetaDescriptor.getValue();
//			this.type=statMetaDescriptor.getType();
//			this.desc=statMetaDescriptor.getDesc();
//			this.parts=statMetaDescriptor.getParts();
//		}
//		else{
//			parts.add(statMetaDescriptor);
//		}
		
//		String key = statMetaDescriptor.getSumType() + "|" + statMetaDescriptor.getDesc() + "|" + statMetaDescriptor.getCode();
		String key = statMetaDescriptor.getBigType()+ "|" + statMetaDescriptor.getDesc() + "|" + statMetaDescriptor.getCode();
		if(curCodeProperties.containsKey(key)){
			Map map = curCodeProperties.get(key);
			V _val = statMetaDescriptor.getValue();
			if(_val instanceof Integer){
				int val = ((Number)map.get("value")).intValue();
				map.put("value", ((Number)_val).intValue() + val);
			}else{
				double val = ((Number)map.get("value")).doubleValue();
				map.put("value", ((Number)_val).doubleValue() + val);
			}
		}else{
			Map map = new LinkedHashMap();
			map.put("code", statMetaDescriptor.getCode());
			map.put("bigType", statMetaDescriptor.getBigType());
			map.put("desc", statMetaDescriptor.getDesc());
			map.put("sumType", statMetaDescriptor.getSumType());
			map.put("value", statMetaDescriptor.getValue());
			map.put("sortIndex", statMetaDescriptor.getSortIndex());
			curCodeProperties.put(key, map);
		}
		
    }
	
	
	
	public List<StatMetaDescriptor<K, V>> getParts() {
		return parts;
	}

	
	
	public String getBigType() {
		return bigType;
	}
	public void setBigType(String bigType) {
		this.bigType = bigType;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Map<String, Map> getHisCodeProperties() {
		return hisCodeProperties;
	}
	public void setHisCodeProperties(Map<String, Map> hisCodeProperties) {
		this.hisCodeProperties = hisCodeProperties;
	}
	public Map<String, Map> getCurCodeProperties() {
		return curCodeProperties;
	}
	public void setCurCodeProperties(Map<String, Map> curCodeProperties) {
		this.curCodeProperties = curCodeProperties;
	}
	
	public int getSortIndex() {
		return sortIndex;
	}
	public void setSortIndex(int sortIndex) {
		this.sortIndex = sortIndex;
	}
	
	
//	@SuppressWarnings("unchecked")
//	public void sum(StatMetaDescriptor<K, V> statMetaDescriptor){
//		boolean isPartsEmpty = false;
//		if(StringUtils.isEmpty(this.getType())){
//			isPartsEmpty=true;
//			this.type=statMetaDescriptor.getType();
//			this.desc=statMetaDescriptor.getDesc();
//		}
//		else if(!statMetaDescriptor.getType().equals(this.type)){
//			log.error("the org type ["+this.type+"] not equal dest type["+statMetaDescriptor.getType()+"]");
//			return;
//		}
//		if(statMetaDescriptor.getValue() instanceof Integer){
//			Integer intvlaue = (Integer) (this.value==null?0:this.value);
//			Integer newintvlaue = (Integer) statMetaDescriptor.getValue();
//			intvlaue = intvlaue+newintvlaue;
//			this.value = (V) intvlaue;
//		}
//		if(!statMetaDescriptor.getParts().isEmpty()){
//			if(isPartsEmpty){
//				this.parts=statMetaDescriptor.getParts();
//			}
//			else{
//				StatMetaDescriptor<K,V> dest = null;
//				StatMetaDescriptor<K,V> org = null;
//				List<StatMetaDescriptor<K, V>> newParts = new ArrayList<StatMetaDescriptor<K, V>>();
//				for(int i = 0 ; i<statMetaDescriptor.getParts().size();i++){
//					dest = statMetaDescriptor.getParts().get(i);
//					org = this.getParts().get(i);
//					if(dest.getValue() instanceof Integer){
//						Integer intvlaue = (Integer) (org.getValue()==null?0:org.getValue());
//						Integer newintvlaue =(Integer) dest.getValue();
//						intvlaue = intvlaue+newintvlaue;
//						org.value=(V) intvlaue;
//					}
//					newParts.add(org);
//				}
//				this.parts=newParts;
//			}
//			
//		}
//	}
//    @SuppressWarnings("unchecked")
//	public void count(StatMetaDescriptor<K, V> statMetaDescriptor){
//		boolean isPartsEmpty = false;
//		if(StringUtils.isEmpty(this.getType())){
//			isPartsEmpty=true;
//			this.type=statMetaDescriptor.getType();
//			this.desc=statMetaDescriptor.getDesc();
//		}
//		else if(!statMetaDescriptor.getType().equals(this.type)){
//			log.error("the org type ["+this.type+"] not equal dest type["+statMetaDescriptor.getType()+"]");
//			return;
//		}
//		if(statMetaDescriptor.getValue() instanceof Integer){
//			Integer intvlaue = (Integer) (this.value==null?0:this.value);
//			intvlaue = intvlaue+1;
//			this.value = (V) intvlaue;
//		}
//		if(!statMetaDescriptor.getParts().isEmpty()){
//			if(isPartsEmpty){
//				this.parts=statMetaDescriptor.getParts();
//			}
//			else{
//				StatMetaDescriptor<K,V> dest = null;
//				StatMetaDescriptor<K,V> org = null;
//				List<StatMetaDescriptor<K, V>> newParts = new ArrayList<StatMetaDescriptor<K, V>>();
//				for(int i = 0 ; i<statMetaDescriptor.getParts().size();i++){
//					dest = statMetaDescriptor.getParts().get(i);
//					org = this.getParts().get(i);
//					if(dest.getValue() instanceof Integer){
//						Integer intvlaue = (Integer) (org.getValue()==null?0:org.getValue());
//						intvlaue = intvlaue+1;
//						org.value=(V) intvlaue;
//					}
//					newParts.add(org);
//				}
//				this.parts=newParts;
//			}
//			
//		}
//	}
//	public final String toString() {
//		StringBuilder sb = new StringBuilder();
//		sb.append(getType()).append(":").append(getDesc()).append("=").append(getValue());
//		if(!this.getParts().isEmpty()){
//			for(StatMetaDescriptor<K,V> dest:this.getParts()){
//				sb.append("|").append(dest.getType()).append(":").append(dest.getDesc()).append("=").append(dest.getValue());
//			}
//		}
//		return sb.toString();
//	}
	public Map<K,V> toMap(){
		Map<K,V> map  =  new HashMap<K,V>();
		map.put(desc,value);
		if(!this.parts.isEmpty()){
			for(StatMetaDescriptor<K, V> part:parts){
				map.put(part.getDesc(),part.getValue());
			}
		}
		return map;
	}
	
	public List toCurList(Map extendParams){
		List items = new ArrayList();
		for(Iterator it = curCodeProperties.keySet().iterator(); it.hasNext();){
			String code = (String) it.next();
			Map properties = (Map) curCodeProperties.get(code);
			properties.remove("sumType");
			properties.putAll(extendParams);
			items.add(properties);
		}
		return items;
	}
	
	
}