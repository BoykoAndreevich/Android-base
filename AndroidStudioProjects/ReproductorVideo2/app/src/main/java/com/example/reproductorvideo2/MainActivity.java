package com.example.reproductorvideo2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;


public class MainActivity extends AppCompatActivity {
    private VideoView mVideoView;
    private MediaController mMediaController;
    private static final int STORAGE_REQUEST_CODE = 102;

    File extStore = Environment.getExternalStorageDirectory();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVideoView = findViewById(R.id.surface_view);

        //MÉTODO 1: FICHERO INCRUSTADO EN LA APLICACIÓN (EN DIRECTORIO RAW)
        String videoUrl = "android.resource://" + getPackageName() + "/" + R.raw.martian_wrinkle;

        //MÉTODO 2: DESDE DIRECCIÓN DE INTERNET
        //String videoUrl="https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
        //String videoUrl= "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
        //String videoUrl= "https://youtu.be/L0Y6hawPB-E?list=PLU8oAlHdN5BktAXdEVCLUYzvDyqRQJ2lk";

        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(this);
            mediacontroller.setAnchorView(mVideoView);
            // Get the URL from String videoUrl
            Uri video = Uri.parse(videoUrl);
            mVideoView.setMediaController(mediacontroller);
            mVideoView.setVideoURI(video);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                mVideoView.start();
            }
        });

    }
}

