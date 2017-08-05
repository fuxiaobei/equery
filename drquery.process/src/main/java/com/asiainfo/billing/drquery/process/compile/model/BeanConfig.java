package com.asiainfo.billing.drquery.process.compile.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanConfig {

	private String id;
	private String className;
	private String sourcePath;
	private String targetPath;
	private List<Model> modelList = new ArrayList<Model>();
	private Map<String, Model> modelMap = new HashMap<String, Model>();
	private Template template;
	
	private String packageName;
	private String classSimpleName;

	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	public String getTargetPath() {
		return targetPath;
	}

	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}

	public List<Model> getModelList() {
		return modelList;
	}

	public void setModelList(List<Model> modelList) {
		this.modelList = modelList;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}
	
	public void addModel(Model model){
		this.modelList.add(model);
		modelMap.put(model.getId(), model);
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getClassSimpleName() {
		return classSimpleName;
	}

	public void setClassSimpleName(String classSimpleName) {
		this.classSimpleName = classSimpleName;
	}
	
	public Model getModel(String modelId){
		return modelMap.get(modelId);
	}
	
	
}
