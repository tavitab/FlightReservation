package services;

import java.util.ArrayList;
import java.util.List;

import domain.PlaneTemplate;

public class PlaneTemplateService {
	
	private static PlaneTemplateService instance = null;
	
	List<PlaneTemplate> planeTemplateList = new ArrayList<PlaneTemplate>();
	
	public void addPlaneTemplate(PlaneTemplate planeTemplate) {
		planeTemplateList.add(planeTemplate);
	}
	
	public List<PlaneTemplate> getAllPlaneTemplates() {
		return planeTemplateList;
	}
	
	public static PlaneTemplateService getInstance() {
		if(instance==null) {
			instance = new PlaneTemplateService();
		}
		return instance;
	}
}
