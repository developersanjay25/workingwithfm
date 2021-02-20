package com.example.workingwithfm;

import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URL;
import java.util.ArrayList;

public class songs extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.Adapter madapter;
    ArrayList<uploadsong> marrayList;
    FirebaseDatabase getsongs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.songs,null,false);
        recyclerView = v.findViewById(R.id.songrecyclerview);
        marrayList = new ArrayList<>();

        fromfirebase();
        madapter = new songadapter(marrayList);
        recyclerView.setAdapter(madapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }
    public void fromfirebase()
    {
           getsongs.getInstance().getReference("songs").addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   for (DataSnapshot msnapshot : snapshot.getChildren()) {

//                       uploadsong muploadsong = msnapshot.getValue(uploadsong.class);
//                       marrayList.add(muploadsong);

                       String urll = msnapshot.getValue().toString();
                       MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
                        metadataRetriever.setDataSource(getContext(),Uri.parse(urll));
                        uploadsong muploadsong = new uploadsong();
                        muploadsong.setAlbum(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
                        muploadsong.setArtist(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
                        }
               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {
                   Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();
               }
           });
    }
}
