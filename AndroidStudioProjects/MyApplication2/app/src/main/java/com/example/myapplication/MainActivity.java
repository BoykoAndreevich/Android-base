package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("LOGCAT","entra en el metodo onCreate");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i("LOGCAT","entra en el metodo onStart");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i("LOGCAT","entra en el metodo onResumen");
    }

    @Override
    protected  void onPause(){
        super.onPause();
        Log.i("LOGCAT","entra en el metodo onPausa");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i("LOGCAT","entra en el metodo onDestroy");
    }
}
