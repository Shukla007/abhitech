package com.smart.teach.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.smart.teach.R;
import com.smart.teach.model.livebatchModel;

import java.io.File;
import java.util.ArrayList;

public class recycleAdapter extends RecyclerView.Adapter <recycleAdapter.ViewHolder> {

    Context context;

    public recycleAdapter(Context context, ArrayList<livebatchModel> list) {
        this.context = context;
        this.list = list;
    }

    ArrayList<livebatchModel> list;


    @NonNull
    @Override
    public recycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycleview_layout,parent,false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull recycleAdapter.ViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.discription.setText(list.get(position).getDiscription());
        holder.price.setText(list.get(position).getPrice());
        holder.duration.setText(list.get(position).getDuration());
        holder.price.setText(list.get(position).getPrice());
        Glide.with(holder.courseImage).load(list.get(position).getThumbnail())
                .into(holder.courseImage);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView  title,discription,price,duration,roomid;
        ImageView courseImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.courseTitle);
            discription = itemView.findViewById(R.id.batchDis);
            price = itemView.findViewById(R.id.coursePrice);
            duration = itemView.findViewById(R.id.courseDur);
            courseImage = itemView.findViewById(R.id.course_thumb);

        }
    }
}


