package com.example.harisrafiq.myapplication;

public class Upload {
    private String mName;
    private String description;
    private String mImageUrl;

    public Upload() {
        //empty constructor needed
    }

    public Upload(String name, String imageUrl,String des) {
        if (name.trim().equals("")) {
            name = "No Name";
        }

        mName = name;
        mImageUrl = imageUrl;
        description = des;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getdes() {
        return description;
    }

    public void setdes(String des) {
        description = des;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}

