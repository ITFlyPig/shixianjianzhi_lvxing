package com.SelfTourGuide.singapore.model;

import java.io.Serializable;


public class RestaurantModel implements Serializable {


	private static final long serialVersionUID = 6027703483847860900L;
	private int imageUrl;
    private String restaurantid;
	private String language;
	private String restaurant_name;
	private String categories;
	private String instroduction;
	private String price_range;
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

	public String getPrice_range() {
		return price_range;
	}

	public void setPrice_range(String price_range) {
		this.price_range = price_range;
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

	public String getRestaurantid() {
		return restaurantid;
	}

	public void setRestaurantid(String restaurantid) {
		this.restaurantid = restaurantid;
	}

	public String getRestaurant_name() {
		return restaurant_name;
	}

	public void setRestaurant_name(String restaurant_name) {
		this.restaurant_name = restaurant_name;
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


}
