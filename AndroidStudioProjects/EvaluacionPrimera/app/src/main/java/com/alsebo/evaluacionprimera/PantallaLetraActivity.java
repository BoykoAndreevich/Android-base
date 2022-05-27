package com.alsebo.evaluacionprimera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class PantallaLetraActivity extends AppCompatActivity {

    TextView texto;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_letra);

        texto = (TextView)findViewById(R.id.textView3);

        Bundle extras = getIntent().getExtras();
        String s = extras.getString("palabra");


        texto.setText("Palabra: "+s);
    }


    public void cuenta(View view) {
        editText= (EditText) findViewById(R.id.editText2);
        String letra = editText.getText().toString();
        char caracter = letra.charAt(0);
        Bundle extras = getIntent().getExtras();
        String s = extras.getString("palabra");
        int contador = 0;
        for (int x = 0; x < s.length(); x++) {
            if ((s.charAt(x) == caracter)) {
                contador++;
            }
        }
        String importe = Integer.toString(contador);
        Intent data = new Intent();
        data.putExtra("contador",importe);
        data.putExtra("letra", letra);
        setResult(RESULT_OK, data);
        finish();
    }
}
