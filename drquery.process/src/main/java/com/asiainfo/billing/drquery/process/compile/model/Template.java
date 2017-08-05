package com.asiainfo.billing.drquery.process.compile.model;

import java.util.ArrayList;
import java.util.List;

public class Template {

	private String location;
	private List<Template.Model> modelList = new ArrayList<Template.Model>();
	
	public class Model{
		private String id;
		private String refModel;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getRefModel() {
			return refModel;
		}
		public void setRefModel(String refModel) {
			this.refModel = refModel;
		}
	}
	
	
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<Template.Model> getModelList() {
		return modelList;
	}

	public void setModelList(List<Template.Model> modelList) {
		this.modelList = modelList;
	}
	
	public void addModel(Template.Model model){
		this.modelList.add(model);
	}
}
