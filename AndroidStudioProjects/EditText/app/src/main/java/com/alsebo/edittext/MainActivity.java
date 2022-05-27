package com.alsebo.edittext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText edit = (EditText) findViewById(R.id.editText);
        EditText edit2 = (EditText) findViewById(R.id.editText2);
        EditText edit3 = (EditText) findViewById(R.id.editText3);
        EditText edit4 = (EditText) findViewById(R.id.editText4);

        String cadena = edit.getText().toString();
        String cadena2 = edit2.getText().toString();
        String cadena3 = edit3.getText().toString();
        String cadena4 = edit4.getText().toString();


    }
}
