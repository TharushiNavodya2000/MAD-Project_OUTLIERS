package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GuideAdapter extends RecyclerView.Adapter<GuideAdapter.MyViewHolder> {
    String guideDescription[];
    String user[];
    int images[];
    Context context;

    public GuideAdapter (Context context, String guideDescription[], String user[], int images[])
    {
        this.guideDescription = guideDescription;
        this.images = images;
        this.user = user;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.guide_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuideAdapter.MyViewHolder holder, int position) {
        holder.user.setText(user[position]);
        holder.guideDescription.setText(guideDescription[position]);
        holder.images.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return guideDescription.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView guideDescription;
        TextView user;
        ImageView images;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            guideDescription = itemView.findViewById(R.id.place);
            user = itemView.findViewById(R.id.description);
            images = itemView.findViewById(R.id.image);
        }
    }
}
