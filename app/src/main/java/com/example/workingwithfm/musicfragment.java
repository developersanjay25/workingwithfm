package com.example.workingwithfm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class musicfragment extends Fragment {
    private Button musicslector, uploadsong;
    private int REQUESTED_SONG = 1;
    private Uri songuri;
    TextView album, artist, name;
    ProgressBar progressBar;
    private ImageView songimg;
    private String uriforimg;
    private FirebaseDatabase songurl;
    private DatabaseReference songurlref;
    private FirebaseStorage songs;
    private StorageReference songref;
    private  String songname;
    String malbum;
    Bitmap songimggg;
    String martist;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.musicupload, null, false);
        musicslector = v.findViewById(R.id.musicselector);
        album = v.findViewById(R.id.album);
        artist = v.findViewById(R.id.artist);
        songimg = v.findViewById(R.id.songimg);
        name = v.findViewById(R.id.name);
        uploadsong = v.findViewById(R.id.upload_song);
        progressBar = v.findViewById(R.id.progress_songs);

        musicslector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(intent.ACTION_GET_CONTENT);
                intent.setType("audio/*");
                startActivityForResult(intent, REQUESTED_SONG);
            }

        });

        uploadsong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (songuri != null)
            {
                tofirebase();
                progressBar.setVisibility(View.VISIBLE);
            }
            else
            {
                Toast.makeText(getContext(), "select audio file", Toast.LENGTH_SHORT).show();
            }
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTED_SONG && resultCode == -1 && data.getData() != null) {
            songuri = data.getData();
            MediaMetadataRetriever metareciver = new MediaMetadataRetriever();
            metareciver.setDataSource(getContext(), songuri);

          songname = metareciver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            String malbum = metareciver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
            String martist = metareciver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            int img = MediaMetadataRetriever.METADATA_KEY_HAS_IMAGE;

            byte[] art = metareciver.getEmbeddedPicture();

            if( art != null ){
                songimggg = BitmapFactory.decodeByteArray(art, 0, art.length);
                songimg.setImageBitmap(songimggg);
            }
            else{
                songimg.setImageResource(R.drawable.musicimg);
                songimggg = BitmapFactory.decodeResource(getResources(),R.drawable.musicimg);
            }

//            if (img != null)
//            {
//                songimg.setImageResource(MediaMetadataRetriever.METADATA_KEY_IMAGE_PRIMARY);
//            }
//            Picasso.get().load(img).placeholder(R.drawable.musicimg).fit().into(songimg);
            name.setText(songname);

            album.setText(malbum);
            artist.setText(martist);

        }
    }

    void tofirebase() {
        songref = songs.getInstance().getReference("songs").child(songname);

        songref.putFile(songuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                songurlref = songurl.getInstance().getReference("songs");


                Task<Uri> urlforsongs = taskSnapshot.getStorage().getDownloadUrl();
                while (!urlforsongs.isSuccessful()) ;
                Uri downloadurl = urlforsongs.getResult();

                FirebaseStorage.getInstance().getReference("song  images").child(System.currentTimeMillis() + ".jpg").putFile(getImageUri(getContext(), songimggg)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> urll = taskSnapshot.getStorage().getDownloadUrl();
                        while (!urll.isSuccessful()) ;
                        Uri imgurii = urll.getResult();
                        if (imgurii != null) {
                            giveurl(imgurii.toString());
                            String push = songurlref.push().getKey();
                            if (uriforimg != null && album.getText().toString() != null && artist.getText().toString() != null) {
                                uploadsong uploadsong = new uploadsong(uriforimg, name.getText().toString(), artist.getText().toString(), downloadurl.toString());

                                songurlref.child(push).setValue(uploadsong);
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(getContext(), "Succesfully uploaded", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getContext(), "unable to set details", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
                            }

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "" + e, Toast.LENGTH_SHORT).show();
                    }
                })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                double prog = 100.00 * (snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                                progressBar.setProgress((int) prog);
                            }
                        });
            }

        });
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public void giveurl(String url) {
        uriforimg = url;
    }

}