package com.asiainfo.billing.drquery.uniformStructure;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class UniformStructureReader implements InitializingBean{
	
	private final static Log log = LogFactory.getLog(UniformStructureReader.class);
	private static final Map<String,Integer> uniformStructureMap = new HashMap<String,Integer>();
	private static final Map<String,Map<String,Integer>> table2uniformStructureMapping = new HashMap<String,Map<String,Integer>>();
	
	public static Map<String, Integer> getUniformStructureMap() {
		return uniformStructureMap;
	}
	
	public static Map<String, Map<String, Integer>> getTable2uniformstructuremapping() {
		return table2uniformStructureMapping;
	}

	private void initUniformStructure(){
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource resource = resolver.getResource("classpath:drquery.model/uniformStructure/uniformStructure.xml");;
		InputStream inputStream = null;
		try {
			inputStream = resource.getInputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (log.isDebugEnabled()) {
			log.debug("mappring model>>>" + resource.getFilename());
		}
		Document document = null;
		SAXReader reader = new SAXReader();
		try {
			document = reader.read(inputStream);
		} 
		catch (DocumentException e) {
			if (log.isErrorEnabled()) {
				log.error("loader uniformstructure model exception", e);
			}
		}
		Element root = document.getRootElement();
		List<Element> nodeList = root.elements();
		int index = 0;
		for (Element element : nodeList) {
			List<Attribute> attrs = element.attributes();
			//uniformStructureMap.put(attrs.get(0).getValue(), Integer.parseInt(attrs.get(1).getValue())-2);
			
			uniformStructureMap.put(attrs.get(0).getValue(), index ++);
			//uniformStructureMap.put(attrs.get(1).getValue(), index ++);
		}
	}
	private void initTable2UniformStructure(){
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = null;
		try {
			resources = resolver.getResources("classpath:drquery.model/uniformStructure/uniformStructure-*.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(Resource resource:resources){
			readuniformStructureMapping(resource);
		}
	}
	private void readuniformStructureMapping(Resource resource){
		InputStream inputStream = null;
		try {
			inputStream = resource.getInputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (log.isInfoEnabled()) {
			log.info("uniformstructure model>>>" + resource.getFilename());
		}
		Document document = null;
		SAXReader reader = new SAXReader();
		try {
			document = reader.read(inputStream);
		} 
		catch (DocumentException e) {
			if (log.isErrorEnabled()) {
				log.error("loader uniformstructure model exception", e);
			}
		}
		Element root = document.getRootElement();
		List<Element> nodeList = root.elements();
		try {
			Map<String,Integer> uniformStructureModel = new HashMap<String,Integer>();
			for (Element element : nodeList) {
				List<Attribute> attrs = element.attributes();
				UniformStructure uniformStructure = new UniformStructure();
				for(Attribute attr:attrs){
					BeanUtils.setProperty(uniformStructure, attr.getName(), attr.getValue());
				}
				uniformStructureModel.put(uniformStructure.getName(), uniformStructure.getUniformindex()-1);
			}
			Attribute tablename = root.attribute("name");
			table2uniformStructureMapping.put(tablename.getValue(), uniformStructureModel);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		initUniformStructure();
		initTable2UniformStructure();
	}
}
