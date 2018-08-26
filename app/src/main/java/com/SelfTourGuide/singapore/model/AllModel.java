package com.SelfTourGuide.singapore.model;

import java.io.Serializable;

/**
 * Created by a on 2017/5/12.
 */

public class AllModel implements Serializable {

    private String allname;
    private String type1;
    private String type2;
    private String typeid;
    private String type;
    private String language;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAllname() {
        return allname;
    }

    public void setAllname(String allname) {
        this.allname = allname;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AllModel{" +
                "type='" + type + '\'' +
                '}';
    }
}
