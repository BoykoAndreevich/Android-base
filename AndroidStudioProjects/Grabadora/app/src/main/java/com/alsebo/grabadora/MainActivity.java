package com.alsebo.grabadora;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Button bGrabar, bDetener, bReproducir;

    private static final String LOG_TAG = "Grabadora";
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private static String fichero ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bGrabar = findViewById(R.id.bGrabar);
        bDetener = findViewById(R.id.bDetener);
        bReproducir = findViewById(R.id.bReproducir);
        bDetener.setEnabled(false);
        bReproducir.setEnabled(false);

        fichero = this.getExternalFilesDir(Environment.DIRECTORY_MUSIC).
                getAbsolutePath() + "/myaudio.3gp";

        audioSetup();

    }

    private void audioSetup()
    {


        if (!hasMicrophone())
        {
            bDetener.setEnabled(false);
           bReproducir.setEnabled(false);
            bGrabar.setEnabled(false);
        } else {
            bReproducir.setEnabled(false);
            bDetener.setEnabled(false);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 12345: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }
        }
    }
    public void grabar(View view) {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat .requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 12345);
        } else {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setOutputFile(fichero);
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            bGrabar.setEnabled(false);
            bDetener.setEnabled(true);
            bReproducir.setEnabled(false);

            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Fallo en grabación");
            }

       }
    }

    public void detenerGrabacion(View view) {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            bGrabar.setEnabled(true);
            bDetener.setEnabled(false);
            bReproducir.setEnabled(true);
        }
    }
    public void reproducir(View view) {
        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(fichero);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Fallo en reproducción");
        }
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Fallo en reproducción");
        }
        mediaPlayer.start();
        Toast.makeText(getApplicationContext(), "reproducción de audio", Toast.LENGTH_LONG).show();
    }

    protected boolean hasMicrophone() {
        PackageManager pmanager = this.getPackageManager();
        return pmanager.hasSystemFeature(
                PackageManager.FEATURE_MICROPHONE);
    }
}
