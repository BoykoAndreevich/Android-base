package com.example.ev02as;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bAcercaDe;
    public static AlmacenPuntuaciones almacen;
    /*Para añadir sonido */
    public static MediaPlayer mp;
    /*Para añadir sonido */
    public static SharedPreferences prefmusica;
    /* Para puntuaciones */
    static final int ACTIV_JUEGO = 0;

    Button btnComentarios;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle estadoGuardado) {
        super.onSaveInstanceState(estadoGuardado);
        if (mp != null) {
            int pos = mp.getCurrentPosition();
            estadoGuardado.putInt("posicion", pos);
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle estadoGuardado) {
        super.onRestoreInstanceState(estadoGuardado);
        if (estadoGuardado != null && mp != null) {
            int pos = estadoGuardado.getInt("posicion");
            mp.seekTo(pos);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bAcercaDe = findViewById(R.id.button03);
        bAcercaDe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                lanzarAcercaDe(null);
            }
        });
        /* PARA VISUALIZAR EL CICLO DE VIDA DE LAS ACTIVIDADES */
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
        /* PARA VISUALIZAR EL CICLO DE VIDA DE LAS ACTIVIDADES */

        //Almacenamiento
        almacen= new AlmacenPuntuacionesFicheroExterno(this);

        /*Para añadir sonido */
        prefmusica = PreferenceManager.
            getDefaultSharedPreferences(this);
        //if (prefmusica.getBoolean("musica", true)) {
        mp = MediaPlayer.create(this, R.raw.audio);

        btnComentarios = findViewById(R.id.btnComentarios);
        btnComentarios.setOnClickListener(this);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true; /** true -> el menú ya está visible */
    }
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            lanzarPreferencias(null);
            return true;
        }
        if (id == R.id.acercaDe) {
            lanzarAcercaDe(null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void lanzarAcercaDe(View view){
        Intent i = new Intent(this, AcercaDeActivity.class);
        startActivity(i);
    }
    public void lanzarPreferencias(View view){
        Intent i = new Intent(this, PreferenciasActivity.class);
        startActivity(i);
    }
    /*
    public void mostrarPreferencias(View view){
        SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(this);
        String s = "música: " + pref.getBoolean("musica",true)
                +", gráficos: " + pref.getString("graficos","0");
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
    */
    public void lanzarPuntuaciones(View view) {
        Intent i = new Intent(this, Puntuaciones.class);
        startActivity(i);
    }
    public void lanzarJuego(View view) {
        //Para tener en cuenta las puntuaciones
        Intent i = new Intent(this, Juego.class);
        startActivityForResult(i, ACTIV_JUEGO);
    }

    /* PARA VISUALIZAR EL CICLO DE VIDA DE LAS ACTIVIDADES */
    @Override protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
    }
    @Override protected void onResume() {
        if (mp != null && prefmusica.getBoolean("musica", true)){
            mp.start();
        }
        super.onResume();
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onPause() {
        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
        if (mp != null && prefmusica.getBoolean("musica", true)){
            mp.pause();
        }
        super.onPause();
    }
    /* PARA VISUALIZAR EL CICLO DE VIDA DE LAS ACTIVIDADES */

  //Para recoger la puntuación que nos devuelve el juego
  @Override
  protected void onActivityResult (int requestCode, int resultCode, Intent data){
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode== ACTIV_JUEGO && resultCode==RESULT_OK && data!=null) {
      int puntuacion = data.getExtras().getInt("puntuacion");
      String nombre = "Yo";
// Mejor leer nombre desde un AlertDialog.Builder o preferencias
      almacen.guardarPuntuacion(puntuacion, nombre,
          System.currentTimeMillis());
      lanzarPuntuaciones(null);
    }
  }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, ComentarioActivity.class);
        startActivity(i);
    }
}