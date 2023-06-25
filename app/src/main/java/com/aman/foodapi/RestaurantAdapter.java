package com.aman.foodapi;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RecyclerviewHolder> {
    private ArrayList<Restaurant> restaurants = new ArrayList<>();
    Context context;


    public RestaurantAdapter(Context context, ArrayList<Restaurant> restaurants) {
        this.context           = context;
        this.restaurants = restaurants;
    }


    @NonNull
    @Override
    public RecyclerviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mRootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_card, parent, false);
        return new RecyclerviewHolder(mRootView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerviewHolder holder, int position) {
        Restaurant restaurant = restaurants.get(position);

        holder.name.setText(restaurant.getName());
        holder.rating.setText(restaurant.getRating());
        holder.discount.setText(restaurant.discount+"% Flat Off");
        holder.time.setText(restaurant.getDistance());
        holder.image.setImageDrawable(restaurant.getImage());
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }


    public static final class RecyclerviewHolder extends RecyclerView.ViewHolder {
        TextView name, discount, distance, rating, time;
        ImageView image;

        public RecyclerviewHolder(@NonNull View itemView) {
            super(itemView);

            name                   =   itemView.findViewById(R.id.restaurant_name);
            discount               =   itemView.findViewById(R.id.discount);
            rating                 =   itemView.findViewById(R.id.rating);
            time                   =   itemView.findViewById(R.id.restaurant_time);
            image                  =   itemView.findViewById(R.id.res_image);

        }

    }

}
