package com.aman.foodapi;

import android.graphics.drawable.Drawable;

public class FoodCategories {
    Drawable food_picture;
    String food_name;
    boolean expand;

    public FoodCategories(Drawable food_picture, String food_name, boolean expand) {
        this.food_picture = food_picture;
        this.food_name = food_name;
        this.expand = expand;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public Drawable getFood_picture() {
        return food_picture;
    }

    public void setFood_picture(Drawable food_picture) {
        this.food_picture = food_picture;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }
}
