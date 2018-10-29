package com.SelfTourGuide.bangkok.model;

import java.io.Serializable;


public class ShoppingModel implements Serializable {


	private static final long serialVersionUID = 6027703483847860900L;
	private int imageUrl;
    private String shoppingid;
	private String language;
	private String shopping_name;
	private String categories;
	private String instroduction;
	private String address;
	private String telephone;
	private String subway;
	private String website;
	private String opentime;
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

	public String getShoppingid() {
		return shoppingid;
	}

	public void setShoppingid(String shoppingid) {
		this.shoppingid = shoppingid;
	}

	public String getShopping_name() {
		return shopping_name;
	}

	public void setShopping_name(String shopping_name) {
		this.shopping_name = shopping_name;
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
		return "ShoppingModel{" +
				"shoppingid='" + shoppingid + '\'' +
				", language='" + language + '\'' +
				", categories='" + categories + '\'' +
				", shopping_name='" + shopping_name + '\'' +
				", address='" + address + '\'' +
				'}';
	}
}
