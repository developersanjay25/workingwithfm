package com.example.workingwithfm;

import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.HttpUrl;

public class playlist extends Fragment {
    RecyclerView playlistt;
    DatabaseReference songs;
    playlistadapter playlistadapter;
    MediaMetadataRetriever mediaMetadataRetriever;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.playlist,null,false);
        playlistt = v.findViewById(R.id.plsylistid);
        ArrayList<uploadsong> mmmarrayList = new ArrayList<>();
        mediaMetadataRetriever = new MediaMetadataRetriever();
        progressBar = v.findViewById(R.id.progress_circular);


        songs = FirebaseDatabase.getInstance().getReference("playsongs");
        playlistadapter = new playlistadapter(mmmarrayList);
        playlistt.setLayoutManager(new LinearLayoutManager(getContext()));

        System.out.println("Arraylist is empty or not : "+mmmarrayList.isEmpty());
        songs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mmmarrayList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    uploadsong li = snapshot1.getValue(uploadsong.class);
                    li.setKey(snapshot1.getKey());
                     if (li == null)
                    {
                          Toast.makeText(getContext(), "Null", Toast.LENGTH_SHORT).show();
                          System.out.println("null");
                    }
                     else
                         {
                             mmmarrayList.add(li);
                               }
                }
                playlistt.setAdapter(playlistadapter);
                playlistadapter.setOnClickListener(new playlistadapter.onclicklistener() {
                    @Override
                    public void onclick(int position) {
                        Toast.makeText(getContext(), "you clicked "+position, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void ondelete(int postion) {
                        uploadsong currentitem = mmmarrayList.get(postion);
                        String item = currentitem.getKey();
                        FirebaseDatabase.getInstance().getReference("playsongs").child(item).removeValue();
                        playlistadapter.notifyItemChanged(postion);
                    }
                });
                playlistadapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        System.out.println("array list is empty or not : " + mmmarrayList + " Size "+ mmmarrayList.size());
        Toast.makeText(getContext(), "array list is empty or not : " + mmmarrayList + " Size "+ mmmarrayList.size() , Toast.LENGTH_SHORT).show();
        return v;
    }
}