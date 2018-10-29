package com.SelfTourGuide.bangkok.model;

import java.io.Serializable;

/**
 * 
 * @author 张志珍
 * @ClassName:AttractionModel
 * @Description:个人信息页面地址model
 * @date2014-6-19
 *
 */
public class AttractionModel implements Serializable {


	private static final long serialVersionUID = 6027703483847860900L;
	private int imageUrl;
    private String attractionid;
	private String attraction_name;
	private String opentime;
	private String howlong;
	private String ticket_price;
	private String address;
	private String telephone;
	private String instroduction;
	private String subwag;
	private String website;
	private String logoid;
	private String language;
	private String type;

	public int getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(int imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getAttractionid() {
		return attractionid;
	}

	public void setAttractionid(String attractionid) {
		this.attractionid = attractionid;
	}

	public String getAttraction_name() {
		return attraction_name;
	}

	public void setAttraction_name(String attraction_name) {
		this.attraction_name = attraction_name;
	}

	public String getOpentime() {
		return opentime;
	}

	public void setOpentime(String opentime) {
		this.opentime = opentime;
	}

	public String getHowlong() {
		return howlong;
	}

	public void setHowlong(String howlong) {
		this.howlong = howlong;
	}

	public String getTicket_price() {
		return ticket_price;
	}

	public void setTicket_price(String ticket_price) {
		this.ticket_price = ticket_price;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getInstroduction() {
		return instroduction;
	}

	public void setInstroduction(String instroduction) {
		this.instroduction = instroduction;
	}

	public String getSubwag() {
		return subwag;
	}

	public void setSubwag(String subwag) {
		this.subwag = subwag;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getLogoid() {
		return logoid;
	}

	public void setLogoid(String logoid) {
		this.logoid = logoid;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "AttractionModel{" +
				"attractionid='" + attractionid + '\'' +
				", attraction_name='" + attraction_name + '\'' +
				", opentime='" + opentime + '\'' +
				", howlong='" + howlong + '\'' +
				", ticket_price='" + ticket_price + '\'' +
				", address='" + address + '\'' +
				", telephone='" + telephone + '\'' +
				", instroduction='" + instroduction + '\'' +
				", subwag='" + subwag + '\'' +
				", website='" + website + '\'' +
				", logoid='" + logoid + '\'' +
				", language='" + language + '\'' +
				'}';
	}
}
