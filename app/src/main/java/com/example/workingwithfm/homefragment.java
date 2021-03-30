package com.example.workingwithfm;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class homefragment extends Fragment {
    int IMAGE_REQUEST = 1;
    Button select_image, upload,settofire;
    TextView Show_name;
    ImageView imageView;
    Uri imageuri;
    DatabaseReference databaseReference, show_name;
    StorageReference storageReference;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.homefragment,null,false);


        select_image = v.findViewById(R.id.select_image);
        upload = v.findViewById(R.id.upload_home_img);
        settofire = v.findViewById(R.id.settofire);
        Show_name = v.findViewById(R.id.Show_name);
        imageView = v.findViewById(R.id.home_image);
        progressBar = v.findViewById(R.id.progress_bar);


        databaseReference = FirebaseDatabase.getInstance().getReference("home");
        show_name = FirebaseDatabase.getInstance().getReference("show_name");
        storageReference = FirebaseStorage.getInstance().getReference("home");

        settofire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_to_fire();
            }
        });
        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openimages();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tofirebase();
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == -1 && data != null) {
            imageuri = data.getData();
            Picasso.get().load(imageuri).centerCrop().fit().into(imageView);
        }
    }

    public void openimages() {
        Intent intent = new Intent();

        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(intent, IMAGE_REQUEST);

    }

    public void tofirebase() {

        if (imageuri != null){
            storageReference.child("homeimg").putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful());
                    Uri downloadUrl = urlTask.getResult();

                    if (imageuri != null) {
                        String uri = downloadUrl.toString();
                        databaseReference.setValue(uri);
                        Toast.makeText(getContext(), "Uploaded Succesfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "select something", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "" + e, Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                   double prog = 100.00*(snapshot.getTotalByteCount()/snapshot.getBytesTransferred());
//                   progressBar.setProgress((int) prog);
                }
            });
        }
    }
    public void set_to_fire() {
        show_name.setValue(Show_name.getText().toString());
    }
}