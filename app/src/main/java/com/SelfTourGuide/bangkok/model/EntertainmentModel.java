package com.SelfTourGuide.bangkok.model;

import java.io.Serializable;


public class EntertainmentModel implements Serializable {


	private static final long serialVersionUID = 6027703483847860900L;
	private int imageUrl;
    private String entertainmentid;
	private String language;
	private String entertainment_name;
	private String categories;
	private String instroduction;
	private String address;
	private String telephone;
	private String subway;
	private String website;
	private String opentime;
	private String tricket_price;
	private String type;


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(int imageUrl) {
		this.imageUrl = imageUrl;
	}



	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}



	public String getInstroduction() {
		return instroduction;
	}

	public void setInstroduction(String instroduction) {
		this.instroduction = instroduction;
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

	public String getSubway() {
		return subway;
	}

	public void setSubway(String subway) {
		this.subway = subway;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getEntertainmentid() {
		return entertainmentid;
	}

	public void setEntertainmentid(String entertainmentid) {
		this.entertainmentid = entertainmentid;
	}

	public String getEntertainment_name() {
		return entertainment_name;
	}

	public void setEntertainment_name(String entertainment_name) {
		this.entertainment_name = entertainment_name;
	}

	public String getTricket_price() {
		return tricket_price;
	}

	public void setTricket_price(String tricket_price) {
		this.tricket_price = tricket_price;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public String getOpentime() {
		return opentime;
	}

	public void setOpentime(String opentime) {
		this.opentime = opentime;
	}

	@Override
	public String toString() {
		return "EntertainmentModel{" +
				"imageUrl=" + imageUrl +
				", entertainmentid='" + entertainmentid + '\'' +
				", language='" + language + '\'' +
				", entertainment_name='" + entertainment_name + '\'' +
				", categories='" + categories + '\'' +
				", instroduction='" + instroduction + '\'' +
				", address='" + address + '\'' +
				", telephone='" + telephone + '\'' +
				", subway='" + subway + '\'' +
				", website='" + website + '\'' +
				", opentime='" + opentime + '\'' +
				", tricket_price='" + tricket_price + '\'' +
				", type='" + type + '\'' +
				'}';
	}
}
