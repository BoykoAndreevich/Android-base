package com.alsebo.evaluacionprimera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView1, textView2, textView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1= (TextView)findViewById(R.id.textView1);
        textView2= (TextView)findViewById(R.id.textView2);
        textView3= (TextView)findViewById(R.id.textView3);
    }


    public void onClickLetras(View view) {

        editText = (EditText) findViewById(R.id.editText);
        String letras = editText.getText().toString();
        int tamaño = letras.length();

        textView1.append(" " + tamaño);
    }

    public void onClickVocales(View view) {
        editText = (EditText) findViewById(R.id.editText);
        String letras = editText.getText().toString();
        int contador = 0;
        for (int x = 0; x < letras.length(); x++) {
            if ((letras.charAt(x) == 'a') || (letras.charAt(x) == 'e') || (letras.charAt(x) == 'i') || (letras.charAt(x) == 'o') || (letras.charAt(x) == 'u')) {
                contador++;
            }
        }
        textView2.append(" " + contador);

    }

    public void onClickAgenda(View view) {
        Intent intent = new Intent(this, Agenda.class);
        startActivity(intent);
    }

    public void cuentaLetra(View view){
        Intent intent = new Intent(this, PantallaLetraActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra("palabra", message);
        startActivityForResult(intent, 1234);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        editText = (EditText) findViewById(R.id.editText);
        String letras = editText.getText().toString();
        if (requestCode == 1234 && resultCode == RESULT_OK) {
            String res1 = data.getExtras().getString("letra");
            String res2 = data.getExtras().getString("contador");
            textView3.setText("El usuario ha elegido la letra " + res1 + " y en la palabra " +
                    letras + " aparece " + res2 + " veces");
        }
    }
}