package com.aman.foodapi;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.RecyclerviewHolder> {
    private ArrayList<FoodCategories> foodCategoriesArrayList = new ArrayList<>();
    Context context;


    public CategoriesAdapter(Context context, ArrayList<FoodCategories> foodCategoriesArrayList) {
        this.context           = context;
        this.foodCategoriesArrayList = foodCategoriesArrayList;
    }


    @NonNull
    @Override
    public RecyclerviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mRootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_categories_item, parent, false);
        return new RecyclerviewHolder(mRootView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerviewHolder holder, int position) {
        FoodCategories food = foodCategoriesArrayList.get(position);
        if(food.food_name.equals("All"))
        {
            holder.foodPicture.setVisibility(View.GONE);
            holder.name.setVisibility(View.VISIBLE);
            holder.name.setText(food.getFood_name());
            if(food.isExpand())
            {
                holder.container.setCardBackgroundColor(context.getColor(R.color.light_red_categories));
                holder.name.setTextColor(context.getColor(R.color.white));
            }else
            {
                holder.container.setCardBackgroundColor(context.getColor(R.color.white));
                holder.name.setTextColor(context.getColor(R.color.black));
            }
        }else if(food.expand){
            holder.container.setCardBackgroundColor(context.getColor(R.color.light_red_categories));
            holder.name.setText(food.getFood_name());
            holder.name.setTextColor(context.getColor(R.color.white));
            holder.name.setVisibility(View.VISIBLE);
            if(null!= holder.foodPicture.getDrawable())holder.foodPicture.setImageDrawable(food.getFood_picture());
            else holder.foodPicture.setVisibility(View.GONE);
        }
        else
        {
            holder.container.setCardBackgroundColor(context.getColor(R.color.white));
            holder.name.setTextColor(context.getColor(R.color.black));
            if(null== holder.foodPicture.getDrawable()) holder.name.setVisibility(View.VISIBLE);
            else holder.name.setVisibility(View.GONE);
            holder.name.setText(food.getFood_name());
            if(null!= holder.foodPicture.getDrawable()) holder.foodPicture.setImageDrawable(food.getFood_picture());
            else holder.foodPicture.setVisibility(View.GONE);
        }

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                food.expand = !food.expand;
                notifyItemChanged(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return foodCategoriesArrayList.size();
    }


    public static final class RecyclerviewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView foodPicture;
        CardView container;


        public RecyclerviewHolder(@NonNull View itemView) {
            super(itemView);

            name                   =   itemView.findViewById(R.id.food_name);
            foodPicture            =   itemView.findViewById(R.id.food_img);
            container              =  itemView.findViewById(R.id.container_card);

        }

    }

}
