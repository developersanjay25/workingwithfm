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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class songs extends Fragment {
    RecyclerView recyclerView;
    songadapter mmmadapter;
    ArrayList<uploadsong> marrayList;
    FirebaseDatabase getsongs;
    DatabaseReference reference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.songs, null, false);


        recyclerView = v.findViewById(R.id.songrecyclerview);
        marrayList = new ArrayList<>();
        mmmadapter = new songadapter(marrayList);
        reference = FirebaseDatabase.getInstance().getReference("playsongs");
        fromfirebase();
        recyclerView.setAdapter(mmmadapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }

    public void fromfirebase() {
        getsongs.getInstance().getReference("songs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                marrayList.clear();
                for (DataSnapshot msnapshot : snapshot.getChildren()) {
                       uploadsong muploadsongg = msnapshot.getValue(uploadsong.class);
                       marrayList.add(muploadsongg);
                }
                mmmadapter.notifyDataSetChanged();

                mmmadapter.setonclicklistener(new songadapter.onclicklistener() {
                    @Override
                    public void onclick(int position) {
                        uploadsong playsong =  marrayList.get(position);
                        String ref = reference.push().getKey();
                        reference.child(ref).setValue(marrayList.get(position));
                        Toast.makeText(getContext(), "uploaded successfully", Toast.LENGTH_SHORT).show();
                    }


                    @Override
                    public void ondeleteclick(int position) {
                        Toast.makeText(getContext(), "you delete" + position, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "" + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onclickliseners()
    {

    }
}