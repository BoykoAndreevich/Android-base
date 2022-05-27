package com.example.ev02as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ComentarioActivity extends AppCompatActivity {

    private static String FICHERO = "comentario.txt";
    private Context context;

    SharedPreferences pref;
    String tex;
    EditText text;
    String usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentario);

        text = (EditText) findViewById(R.id.editTextcomentario);
        tex = text.getText().toString();

        pref = PreferenceManager.getDefaultSharedPreferences(this);

        usuario = "usuario: " + pref.getBoolean("editUruario",false);

    }

    public void guardarComentario(String usuario, String tex, long fecha){
        try {
            FileOutputStream f = context.openFileOutput(FICHERO, Context.MODE_APPEND);
            String texto = usuario + " " + tex + "\n";
            f.write(texto.getBytes());
            f.close();
        } catch (Exception e) {

        }
    }
    public List<String> listaComentarios(int cantidad) {
        List<String> result = new ArrayList<String>();
        try {
            FileInputStream f = context.openFileInput(FICHERO);
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(f));
            int n = 0;
            String linea;
            do {
                linea = entrada.readLine();
                if (linea != null) {
                    result.add(linea);
                    n++;
                }
            } while (n < cantidad && linea != null);
            f.close();
        } catch (Exception e) {

        }
        return result;
    }
}