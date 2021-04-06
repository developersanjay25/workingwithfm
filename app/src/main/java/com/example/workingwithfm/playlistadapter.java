package com.example.workingwithfm;

import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class playlistadapter extends RecyclerView.Adapter<playlistadapter.ViewHolder> {
    ArrayList<uploadsong> linkk;
    onclicklistener onClickListener;

    interface onclicklistener
    {
       void onclick(int position);
       void ondelete(int postion);
    }

    void setOnClickListener(onclicklistener onClickListener)
    {
        this.onClickListener = onClickListener;
    }

    playlistadapter(ArrayList<uploadsong> arr)
    {
        linkk = arr;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardplaylist,null,false);
         ViewHolder vh = new ViewHolder(v);
         return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        uploadsong currentposition = linkk.get(position);
        holder.link.setText(currentposition.getAlbum());
        Picasso.get().load(currentposition.getImg()).into(holder.playlistimg);
    }

    @Override
    public int getItemCount() {
        return linkk.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener
    {
        TextView link;
        ImageView playlistimg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           link = itemView.findViewById(R.id.linkk);
           playlistimg = itemView.findViewById(R.id.playlistimg);
           itemView.setOnClickListener(this);
           itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View view) {
           if (onClickListener != null) {
               int position = getAdapterPosition();
               if (position != RecyclerView.NO_POSITION)
               {
                   onClickListener.onclick(position);
               }
           }
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
               contextMenu = contextMenu.setHeaderTitle("Menu");
               MenuItem delete = contextMenu.add(Menu.NONE,1,1,"Delete");
               delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (onClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION)
                {
                    onClickListener.ondelete(position);
                }
            }
            return true;
        }
    }
}
