package com.asiainfo.billing.drquery.process.operation.fieldEscape.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultAttribute;
import org.dom4j.tree.DefaultElement;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.asiainfo.billing.drquery.model.MetaModel;
import com.asiainfo.billing.drquery.process.core.request.DRProcessRequest;
import com.asiainfo.billing.drquery.process.operation.fieldEscape.FieldEscapeOperation;

@Service
public class FieldEscapeLoader implements FieldEscapeOperation,InitializingBean {
	private final static Log log = LogFactory.getLog(FieldEscapeLoader.class);
	private static SAXReader reader = new SAXReader();
	private static Map<String, FieldEscapeModel> fieldEscapeBeans = null;
	private static boolean isStart = true;

	@SuppressWarnings("unchecked")
	private void loadFieldEscape() {
		boolean infoEnable = log.isInfoEnabled();
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();  
	    Resource resource=resolver.getResource("classpath:drquery.process/fieldescape_mapping.xml");  
		if (infoEnable) {
			log.info("load fieldescape mapping file>>>" + resource.getFilename());
		}
		Document document = null;
		try {
			document = reader.read(resource.getInputStream());
			
		}catch (IOException e) {
			log.error("loading reource file of fieldescape found error", e);
		}
		catch (DocumentException e) {
			if (log.isErrorEnabled()) {
				log.error("loading reource file of fieldescape found error", e);
			}
		}
		Element root = document.getRootElement();
		List<Element> nodeList = root.elements();
		if (!CollectionUtils.isEmpty(nodeList)) {
			fieldEscapeBeans = new HashMap<String, FieldEscapeModel>();
			for (Element element : nodeList) {
				buildFieldEscapeBeans(element);
			}
		}
	}
    private void buildFieldEscapeBeans(Element element){
    	FieldEscapeModel fieldEscape = new FieldEscapeModel();
		Attribute escapeFieldCode = element.attribute("escapeFieldCode");
		if(log.isInfoEnabled()){
			log.info("build escapeField model[code:"+escapeFieldCode.getValue()+"]");
		}
		fieldEscape.setEscapeFieldCode(escapeFieldCode.getValue());
		Attribute type = element.attribute("type");
		fieldEscape.setType(type.getValue());
		Attribute localMethodAttr = element.attribute("localMethod");
		if(localMethodAttr!=null){
			String localMethodStr = localMethodAttr.getValue();
			fieldEscape.setLocalMethod(localMethodStr);
		}
		Attribute cacheKeyAttr = element.attribute("cacheKey");
		if(cacheKeyAttr!=null){
			String cacheKeyStr = cacheKeyAttr.getValue();
			fieldEscape.setCacheKey(cacheKeyStr);
		}
		if (fieldEscape.getType().equals("enum")) {
			Map<String, String> enumeration = new HashMap<String, String>();
			Map<String, String> mapping = new HashMap<String, String>();
			List<DefaultElement> entryList = element.elements();

			if (entryList.size() == 1) {
				List<DefaultAttribute> list = entryList.get(0).attributes();
				fieldEscape.setDefaultEmun(list.get(0).getValue());
			} 
			else {
				for (DefaultElement entry : entryList) {
					List<DefaultAttribute> list = entry.attributes();
					if (entry.getName().equals("enumeration")) {
						enumeration.put(list.get(0).getValue(), list.get(1).getValue());
					} 
					else if (entry.getName().equals("mapping")) {
						mapping.put(list.get(0).getValue(), list.get(1).getValue());
					}
				}
				fieldEscape.setEnumeration(enumeration);
				fieldEscape.setMapping(mapping);
			}
		}
		fieldEscapeBeans.put(fieldEscape.generateCacheKey(),fieldEscape);//
    }
	public static Map<String, FieldEscapeModel> getFieldEscapeBeans() {
		return fieldEscapeBeans;
	}
	
	public void afterPropertiesSet() throws Exception {
		log.info("invoke afterPropertiesSet:" + isStart);
		if (isStart) {
			loadFieldEscape();
			isStart = false;
		}

	}

	public List<Map<String, String>> execute(List<Map<String, String>> models, MetaModel meta, DRProcessRequest request)
			throws Exception {
		return null;
	}

}
