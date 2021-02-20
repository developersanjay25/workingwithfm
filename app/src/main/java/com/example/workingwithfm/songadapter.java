package com.example.workingwithfm;

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

public class songadapter extends RecyclerView.Adapter<songadapter.exampleviewholder> {
    ArrayList<uploadsong> arrayList;
    public onclicklistener monclicklistener;

     songadapter()
     {

     }

    public interface onclicklistener {
        void onclick(int position);
        void ondeleteclick(int position);
    }

    public void setonclicklistener(onclicklistener onclicklistener) {
        this.monclicklistener = onclicklistener;
    }


    public class exampleviewholder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        ImageView img;
        TextView albu, artist;

        public exampleviewholder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgsong);
            albu = itemView.findViewById(R.id.album);
            artist = itemView.findViewById(R.id.artist);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View view) {
            if (monclicklistener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    monclicklistener.onclick(position);
                }
            }

        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu = contextMenu.setHeaderTitle("Menu");
            MenuItem delete = contextMenu.add(Menu.NONE, 1, 1, "Delete");
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (monclicklistener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    monclicklistener.ondeleteclick(position);
                }
            }
            return true;
        }
    }
    songadapter(ArrayList<uploadsong> marrayList) {
        arrayList = marrayList;
    }

    @NonNull
    @Override
    public exampleviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.songcardview, null, false);
        exampleviewholder evh = new exampleviewholder(v);
        return evh;

    }

    @Override
    public void onBindViewHolder(@NonNull exampleviewholder holder, int position) {
        uploadsong currentposition = arrayList.get(position);

        Picasso.get().load(currentposition.getImg()).fit().into(holder.img);
        holder.albu.setText(currentposition.getAlbum());
        holder.artist.setText(currentposition.getArtist());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
