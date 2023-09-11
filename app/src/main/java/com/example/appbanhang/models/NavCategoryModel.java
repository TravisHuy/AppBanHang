package com.example.appbanhang.models;

public class NavCategoryModel {
    private  String name,type,discount,description,img_url;

    public NavCategoryModel() {
    }

    public NavCategoryModel(String name, String type, String discount, String description, String img_url) {
        this.name = name;
        this.type = type;
        this.discount = discount;
        this.description = description;
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
