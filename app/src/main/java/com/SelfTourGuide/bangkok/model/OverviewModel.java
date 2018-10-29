package com.SelfTourGuide.bangkok.model;

import java.io.Serializable;

/**
 * 
 * @author 张志珍
 * @ClassName:RegionModel
 * @Description:个人信息页面地址model
 * @date2014-6-19
 *
 */
public class OverviewModel implements Serializable {


	private static final long serialVersionUID = 6027703483847860900L;

	private String language;
	private String description;

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "OverviewModel{" +
				"language='" + language + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}
