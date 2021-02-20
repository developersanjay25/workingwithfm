package com.example.workingwithfm;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class infouploader extends Fragment  {
    Button selectimage, upload;
    EditText text;
    int REQUEST_CODE = 0;
    Uri imguri;
    ImageView imgview;
    DatabaseReference urllink;
    RecyclerView recyclerView;
    upload mupload;
    StorageReference imgg;
    adapter madapter;
    FirebaseStorage mstorage;
    ArrayList<upload2> arrayList;
    ProgressBar progressBar;
    private ValueEventListener dblisener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.info_uploader, null, false);

        selectimage = v.findViewById(R.id.upload_info_img);
        upload = v.findViewById(R.id.upload_details);
        text = v.findViewById(R.id.text);
        imgview = v.findViewById(R.id.info_img);
        recyclerView = v.findViewById(R.id.recyclerviw);
        progressBar = v.findViewById(R.id.progress_bar);
        mstorage = FirebaseStorage.getInstance();
        mupload = new upload();

        imgg = FirebaseStorage.getInstance().getReference("info");
        urllink = FirebaseDatabase.getInstance().getReference("info");

        arrayList = new ArrayList<>();
        madapter = new adapter(arrayList);
        recyclerView.setAdapter(madapter);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        selectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mselectimage();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tofirebase();
            }
        });

        recyclerview();


        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == -1 && data != null && data.getData() != null) {
            imguri = data.getData();
            Picasso.get().load(imguri).fit().into(imgview);
        }
    }

    public void mselectimage() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        startActivityForResult(intent, REQUEST_CODE);
    }

    public void tofirebase() {
        if (imguri != null) {
            imgg.child(System.currentTimeMillis() + ".jpg").putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    String imgId = urllink.push().getKey();
                    while (!uriTask.isSuccessful()) ;
                    Uri downloadurl = uriTask.getResult();
                    if (downloadurl != null) {
                        String imglinkkk = downloadurl.toString();
                        upload2 upload2 = new upload2(imglinkkk, "");
                        urllink.child(imgId).setValue(upload2);

                        Toast.makeText(getContext(), "Successfully uploaded", Toast.LENGTH_SHORT).show();
                    }


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "" + e, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void recyclerview() {
       dblisener = FirebaseDatabase.getInstance().getReference("info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot msnapshot : snapshot.getChildren()) {

                    upload2 upload2 = msnapshot.getValue(upload2.class);

                    upload2.setKey(msnapshot.getKey());
                    arrayList.add(upload2);
                    progressBar.setVisibility(View.INVISIBLE);


                }
                madapter.notifyDataSetChanged();
            madapter.onclicklistener(new adapter.setonclicklistener() {
                @Override
                public void onclick(int position) {
                    Toast.makeText(getContext(), "you clicked"+ position, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void ondelete(int position) {
//                    Toast.makeText(getContext(), "you deleted"+ position, Toast.LENGTH_SHORT).show();
                       upload2 selecteditem = arrayList.get(position);
                       String item = selecteditem.getKey();
                       StorageReference imgdel = mstorage.getReferenceFromUrl(selecteditem.getImg());

                       imgdel.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void aVoid) {
                                    urllink.child(item).removeValue();
                               Toast.makeText(getContext(), "Image deleted", Toast.LENGTH_SHORT).show();
                           }
                       })
                       .addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {

                           }
                       });
                }
            });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "" + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        urllink.removeEventListener(dblisener);
    }
}
