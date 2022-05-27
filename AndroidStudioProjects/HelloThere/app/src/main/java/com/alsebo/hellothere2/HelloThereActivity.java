package com.alsebo.hellothere2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;


public class HelloThereActivity extends AppCompatActivity {

    TextView mtexto;
    Button mpulsador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hellothere);

        mtexto = (TextView) findViewById(R.id.texto);
        mpulsador = (Button) findViewById(R.id.pulsador);
        mpulsador.setOnClickListener(new View.OnClickListener() {


            public void onClick(View arg0) {
                vibrar();

                if (mtexto.getText() == getString(R.string.texto2_textview)) {
                    mtexto.setText(getString(R.string.texto1_textview));
                } else {
                    mtexto.setText(getString(R.string.texto2_textview));
                }

                }
        });
    }

    public void vibrar() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(550, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(550);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast toast = Toast.makeText(getApplicationContext(),R.string.hint_hi, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP,0,200);
        toast.show();
    }
    @Override
    protected void onStop() {
        super.onStop();
        Toast toast = Toast.makeText(getApplicationContext(),R.string.hint_bye, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM,0,400);
        toast.show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast toast = Toast.makeText(getApplicationContext(),R.string.hint_bye, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM,0,400);
        toast.show();
    }

}
