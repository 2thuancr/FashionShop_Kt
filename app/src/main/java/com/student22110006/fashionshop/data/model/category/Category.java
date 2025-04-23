package com.student22110006.fashionshop.data.model.category;

public class Category {
    private String name;
    private String imageResUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageResUrl() {
        return imageResUrl;
    }

    public void setImageResUrl(String imageResUrl) {
        this.imageResUrl = imageResUrl;
    }

    public Category(String name, String imageResUrl) {
        this.name = name;
        this.imageResUrl = imageResUrl;
    }
}
