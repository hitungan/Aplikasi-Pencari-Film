package com.example.kevin_2201729386;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterSearchMov extends RecyclerView.Adapter<AdapterSearchMov.searchHolder> {

    private OnItemClickListener Listener;
    private OnItemLongClickListener Longlistener;

    public interface  OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(int position, View view);

    }

    public void setOnItemLongClickListener(OnItemLongClickListener longlistener){
        Longlistener = longlistener;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        Listener = listener;

    }

    ArrayList<ItemMovie> itemMovies = new ArrayList<>();
    Context context;

    public AdapterSearchMov(ArrayList<ItemMovie> itemMovies, Context context) {
        this.itemMovies = itemMovies;
        this.context = context;
    }

    @NonNull
    @Override
    public searchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.filem,parent,false);
        return new searchHolder(view, Listener,Longlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull searchHolder holder, int position) {
        ItemMovie item = itemMovies.get(position);
        Glide.with(context).load(item.LinkGambar).into(holder.imgMovie);
        holder.judulMovie.setText(item.Judul);
    }

    @Override
    public int getItemCount() {
        return itemMovies.size();
    }

    public static class searchHolder extends RecyclerView.ViewHolder {

        ImageView imgMovie;
        TextView judulMovie;

        public  searchHolder(@NonNull View itemView, final OnItemClickListener listener, final OnItemLongClickListener longListener) {
            super(itemView);
            imgMovie = itemView.findViewById(R.id.gmbrMovie);
            judulMovie = itemView.findViewById(R.id.jdlMovie);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (longListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            longListener.onItemLongClick(position,v);
                        }
                    }
                    return true;
                }
            });

        }

    }
}
