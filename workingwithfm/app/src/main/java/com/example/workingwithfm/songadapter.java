package com.example.workingwithfm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class songadapter extends RecyclerView.Adapter<songadapter.exampleviewholder> {
ArrayList<uploadsong> arrayList;
    songadapter(ArrayList<uploadsong> marrayList)
{
    arrayList = marrayList;
}
public static class exampleviewholder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView albu,artist;
        public exampleviewholder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgsong);
            albu = itemView.findViewById(R.id.album);
            artist = itemView.findViewById(R.id.artist);
        }
    }

    @NonNull
    @Override
    public exampleviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.songcardview,null,false);
        exampleviewholder evh = new exampleviewholder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull exampleviewholder holder, int position) {
             uploadsong currentposition = arrayList.get(position);

             holder.img.setImageBitmap(currentposition.getImg());
             holder.albu.setText(currentposition.getAlbum());
             holder.artist.setText(currentposition.getArtist());
    }

    @Override
    public int getItemCount()
    {
        return arrayList.size();
    }
}
