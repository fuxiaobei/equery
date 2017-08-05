package com.asiainfo.billing.drquery.process.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.asiainfo.billing.drquery.model.ViewModelReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import com.asiainfo.billing.drquery.model.MetaModel;
import com.asiainfo.billing.drquery.process.ProcessException;
import com.asiainfo.billing.drquery.process.core.request.ProcessRequest;
import com.asiainfo.billing.drquery.utils.ServiceLocator;

/**
 * 
 * @author tianyi
 */
@SuppressWarnings("unchecked")
public class ProcessFactory<T extends ProcessRequest> implements InitializingBean{
	private static final Log log = LogFactory.getLog(ProcessFactory.class);
	private Map<String,BaseProcess<T>> processEntries;
	private Map<String,BaseProcess<T>> commProcessEntries;
	private Map<String,BaseProcess<T>> allProcessEntries;
	private boolean isStart=true;
	
	public <E extends BaseProcess<T>> E getProcessByBusiType(String busiType) throws ProcessException {
		return (E) processEntries.get(busiType);
	}
	
	public <E extends BaseProcess<T>> E getCommProcessByBusiType(String busiType)
			throws ProcessException {
		return (E) commProcessEntries.get(busiType);
	}
	
	public <E extends BaseProcess<T>> E getAllProcessByBusiType(String busiType)
			throws ProcessException {
		return (E) allProcessEntries.get(busiType);
	}
	
	public Map<String, BaseProcess<T>> getProcessEntries() {
		return processEntries;
	}
	
	public void setProcessEntries(Map<String, BaseProcess<T>> processEntries) {
		this.processEntries = processEntries;
	}
	
	public Map<String, BaseProcess<T>> getCommProcessEntries() {
		return commProcessEntries;
	}
	
	public void setCommProcessEntries(Map<String, BaseProcess<T>> commProcessEntries) {
		this.commProcessEntries = commProcessEntries;
	}
	
	public Map<String, BaseProcess<T>> getAllProcessEntries() {
		return allProcessEntries;
	}
	
	public void setAllProcessEntries(Map<String, BaseProcess<T>> allProcessEntries) {
		this.allProcessEntries = allProcessEntries;
	}
	
	
	@SuppressWarnings("rawtypes")
	public void afterPropertiesSet() throws Exception {
		if(!isStart){
			return;
		}
		isStart=false;
		processEntries=new HashMap<String,BaseProcess<T>>();
		ViewModelReader reader = new ViewModelReader();
		reader.afterPropertiesSet();
		Map<String, MetaModel> models = ViewModelReader.getMetaModels();
		for(Entry<String, MetaModel> meta:models.entrySet()){
			String process = meta.getValue().getProcess();
			BaseProcess bean = ServiceLocator.getInstance().getService(process,BaseProcess.class);
			processEntries.put(meta.getKey(), bean);
            log.info("meta.getKey()"+meta.getKey()+"===" + (bean ==null) + "processEntries.size="+ processEntries.size());
			if(log.isInfoEnabled()){
				log.info("load process bean["+process+"] in factory for model["+meta.getKey()+"]");
			}
		}
	}
}
