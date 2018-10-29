package com.SelfTourGuide.bangkok.model;

import java.io.Serializable;


public class HotelModel implements Serializable {


	private static final long serialVersionUID = 6027703483847860900L;
	private int imageUrl;
    private String hotelid;
	private String language;
	private String hotel_name;
	private String star_rating;
	private String instroduction;
	private String price_range;
	private String address;
	private String telephone;
	private String subway;
	private String website;
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

	public String getHotelid() {
		return hotelid;
	}

	public void setHotelid(String hotelid) {
		this.hotelid = hotelid;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getHotel_name() {
		return hotel_name;
	}

	public void setHotel_name(String hotel_name) {
		this.hotel_name = hotel_name;
	}

	public String getStar_rating() {
		return star_rating;
	}

	public void setStar_rating(String star_rating) {
		this.star_rating = star_rating;
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


	@Override
	public String toString() {
		return "HotelModel{" +
				"hotelid='" + hotelid + '\'' +
				", imageUrl=" + imageUrl +
				", hotel_name='" + hotel_name + '\'' +
				", star_rating='" + star_rating + '\'' +
				", address='" + address + '\'' +
				'}';
	}
}
