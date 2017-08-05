package com.asiainfo.billing.drquery.process.compile;

import com.asiainfo.billing.drquery.cache.ICache;
import com.asiainfo.billing.drquery.cache.local.LocalCache;
import com.asiainfo.billing.drquery.process.compile.model.BeanConfig;
import com.asiainfo.billing.drquery.process.compile.model.Template;
import com.asiainfo.billing.drquery.utils.FileUtil;
import com.asiainfo.billing.drquery.utils.PropertiesUtil;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.InitializingBean;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Iterator;

public class BeanConfigerReader implements InitializingBean{
	private final static Log log = LogFactory.getLog(BeanConfigerReader.class);

	public static final String CONFIG_PATH = PropertiesUtil.getProperty("drquery.service/runtime.properties", "dynamic.compile.path");
	public static final String CLASSPATH = PropertiesUtil.getProperty("drquery.service/runtime.properties", "dynamic.compile.classpath");
	private static BeanHolder holder = new BeanHolder();
	private ICache cache = new LocalCache();

    BeanAppClassLoader classLoader = new BeanAppClassLoader(this.getClass().getClassLoader());

	public void afterPropertiesSet() throws Exception {
		loadModel(CONFIG_PATH + File.separator + "bean.xml");
	}
	
	public void loadModel(String path){
		log.info("******************* init bean.xml start ********************************************************************");
		log.info("javaSource.config.path --> " + path);
		File file = new File(path);
		if(file.exists()){
			SAXReader reader = new SAXReader();
			try{
				Document document = reader.read(file);
				readConfig(document);
			}catch(Exception e){
				throw new RuntimeException("init bean.xml failture", e);
			}
		}else{
			throw new RuntimeException(path + " is not found!");
		}
		log.info("******************* init bean.xml end ***********************************************************************");
	}
	
	
	
	@SuppressWarnings({ "unchecked", "static-access", "rawtypes" })
	public void readConfig(Document document){
		Element root = document.getRootElement();
		for(Iterator<Element> it = root.elementIterator("bean"); it.hasNext();){
			Element beanElement = it.next();
			BeanConfig config = new BeanConfig();
			config.setId((String)beanElement.attribute("id").getData());
			config.setClassName((String)beanElement.attributeValue("class"));
			config.setSourcePath((String)beanElement.attributeValue("sourcePath"));
			config.setTargetPath((String)beanElement.attributeValue("targetPath"));
			
			config.setPackageName(config.getClassName().substring(0, config.getClassName().lastIndexOf(".")));
			config.setClassSimpleName(config.getClassName().substring(config.getClassName().lastIndexOf(".")+1));
			
//			Element templateElement = beanElement.element("template");
//			Template template = new Template();
//			template.setLocation(templateElement.attributeValue("location"));
//			List<Element> tempModel = templateElement.elements();
//			for(Element e : tempModel){
//				Template.Model model = template.new Model();
//				model.setId(e.attributeValue("id"));
//				model.setRefModel(e.attributeValue("ref-model"));
//				template.addModel(model);
//			}
//			config.setTemplate(template);
			holder.add(config);
//			compactSourceFile(config);
			compileSource(config);
			Class clz = loadClass(config.getClassName(), config.getTargetPath());
			cacheMethod(clz);
			holder.putClass(config.getId(), clz);
		}
	}
	
	
	
	/**
	 * 合并文件
	 * @param config
	 */
	public void compactSourceFile(BeanConfig config){
		String sourcePath = config.getSourcePath().replace("/", File.separator);
		String template = config.getTemplate().getLocation();
		String basePath = BeanConfigerReader.CONFIG_PATH  + sourcePath + File.separator;
		String templateFilePath = basePath + "template" + File.separator + template;
		String javaCode = FileUtil.readFile(templateFilePath); 
		
		javaCode = javaCode.replace("${packageName}", config.getPackageName()).replace("${className}", config.getClassSimpleName());
		
		String flag = "/*************************************{REPLACEMENT}*************************************************/";
		
		for(Template.Model model : config.getTemplate().getModelList()){
			String modelPath = basePath + "original" + File.separator + model.getRefModel();
			String modelSource = FileUtil.readFile(modelPath);
			javaCode = javaCode.replace(model.getId(), flag.replace("{REPLACEMENT}", model.getId()) + "\r" + modelSource);
		}
        FileUtil.writeFile(basePath + "final" + File.separator + config.getClassSimpleName() + ".java", javaCode);
	}
	

	
	/**
	 * 编译JAVA源文件
	 * @param config
	 */
	public void compileSource(BeanConfig config) {
		String sourcePath = config.getSourcePath().replace("/", File.separator);
		String targetPath = config.getTargetPath().replace("/", File.separator);
		String className = config.getClassName();
		String basePath = BeanConfigerReader.CONFIG_PATH;
		String sourceCode = "";
		String classSimpleName = className.substring(className.lastIndexOf(".") + 1);
//		String javaSourcePath = basePath +  sourcePath + File.separator + "final" + File.separator + config.getClassSimpleName() + ".java";
        String javaSourcePath = basePath +  sourcePath;
        try {
			sourceCode = FileUtil.readFile(javaSourcePath);
			log.info("Read JAVA source file success! --> " + javaSourcePath );
		} catch (Exception e) {
			throw new RuntimeException("Read JAVA source file failture! --> " + javaSourcePath, e);
		}
				
		CompileJava.compile(classSimpleName, sourceCode, basePath + targetPath, CLASSPATH);
	}
	
	
	/**
	 * 加载CLASS
	 * @param className
	 * @param targetPath
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Class loadClass(String className, String targetPath){
		Class clz = null;
		String classPath = CONFIG_PATH + targetPath.replace("/", File.separator) + File.separator;
		try {
			
			classLoader.setClassPath(classPath);
			clz = classLoader.loadClass(className, false);
			log.info("Load [" + className +".class] success!");
		} catch (Exception e){
			throw new RuntimeException("Load [" + className +".class] failture!", e);
		}
		
		return clz;
	}
	
	
	@SuppressWarnings("rawtypes")
	public void cacheMethod(Class clz){
		Method[] methods = clz.getDeclaredMethods();
		for(Method method : methods){
			String methodKey = clz.getName() + "." + method.getName();

			FastClass cglibBeanClass = FastClass.create(clz);
			FastMethod fastMethod = cglibBeanClass.getMethod(method);
			
			cache.putData2Cache(methodKey, fastMethod, -1);
			//log.info("cache method: [" + clz.getSimpleName() + "." + methodName + "], methodKey: [" + methodKey + "]");
		}
		log.info("cache methods of class["+ clz.getSimpleName() + "] success.");
	}
}
