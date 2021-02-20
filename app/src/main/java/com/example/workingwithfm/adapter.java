package com.example.workingwithfm;

import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<adapter.viewholder> {
    private ArrayList<upload2> arrayList;

    setonclicklistener setonclicklistener;

    public interface setonclicklistener {
        void onclick(int position);
        void ondelete(int position);
    }

    public void onclicklistener(setonclicklistener setonclicklistener) {
        this.setonclicklistener = setonclicklistener;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, null, false);
        viewholder vh = new viewholder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        upload2 currentpodition = arrayList.get(position);
        Picasso.get().load(currentpodition.getImg()).fit().placeholder(R.drawable.loadinggif).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    adapter(ArrayList<upload2> arrayList) {
        this.arrayList = arrayList;
    }

    public class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener , View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        ImageView imageView;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_for_card);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }


        @Override
        public void onClick(View v) {
            if (setonclicklistener != null) {
                int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION) {
                    setonclicklistener.onclick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu = menu.setHeaderTitle("Menu");
            MenuItem delete = menu.add(Menu.NONE,1,1,"Delete");
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (setonclicklistener != null) {
                int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION) {
                    setonclicklistener.ondelete(position);
                }
            }return true;
        }
    }
}
