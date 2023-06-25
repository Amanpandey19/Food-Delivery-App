package com.aman.foodapi;

import android.graphics.drawable.Drawable;

public class Restaurant {
    String name;
    String distance;
    Drawable image;
    String rating;
    String discount;

    public Restaurant(String name, String distance, Drawable image, String rating, String discount) {
        this.name = name;
        this.distance = distance;
        this.image = image;
        this.rating = rating;
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
