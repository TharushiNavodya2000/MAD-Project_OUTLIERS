package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GuidesAdapter extends RecyclerView.Adapter<GuidesAdapter.MyViewHolder> {
    String place[];
    String description[];
    int images[];
    Context context;

    public GuidesAdapter(Context context,String place[],String description[],int images[])
    {
        this.place = place;
        this.description = description;
        this.images = images;
        this.context = context;
    }
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.guides_list,parent,false);
        return new MyViewHolder(view);
    }


    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.places.setText(place[position]);
        holder.descriptions.setText(description[position]);
        holder.images.setImageResource(images[position]);
    }

    public int getItemCount() {
        return place.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView places;
        TextView descriptions;
        ImageView images;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            places = itemView.findViewById(R.id.user);
            descriptions = itemView.findViewById(R.id.guideDescription);
            images = itemView.findViewById(R.id.image);
        }
    }
}