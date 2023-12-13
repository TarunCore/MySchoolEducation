package com.innocraze.myse;
public class Upload {
    private String imageUrl;

    public Upload() {
        // Empty constructor needed for Firebase
    }

    public Upload(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
