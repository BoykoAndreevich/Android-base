package com.alsebo.dialoglistado;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CharSequence[] elementos = {"LUNES", "MARTES", "MIERCOLES", "JUEVES", "VIERNES", "SABADO", "DOMINGO"};

        AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
        builder3.setTitle("ACTIVIDAD DE DIALOGO")
                .setIcon(R.mipmap.ic_launcher)
                .setItems(elementos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast toast4 = Toast.makeText(getApplicationContext(), "TOCADO EL "+ elementos[i],
                        Toast.LENGTH_SHORT);
                toast4.setGravity(Gravity.CENTER, 0,0);
                toast4.show();
            }
        });
        AlertDialog dialogo3 = builder3.create();
        dialogo3.show();
    }
}