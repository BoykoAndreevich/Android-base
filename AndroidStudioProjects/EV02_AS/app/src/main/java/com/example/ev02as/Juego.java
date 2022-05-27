package com.example.ev02as;

import android.app.Activity;
import android.os.Bundle;

public class Juego extends Activity {

    //Variable para conseguir que la app no consuma recursos si no está visible
    private VistaJuego vistaJuego;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.juego);

        //Inicialización para conseguir que la app no consuma recursos si no está visible
        vistaJuego = findViewById(R.id.VistaJuego);

        //Para tener en cuenta las puntuaciones
        vistaJuego.setPadre(this);
    }

    /* MÉTODOS PARA Conseguir que la app no consuma recursos si no está visible */
    @Override
    protected void onPause() {
        vistaJuego.desactivarSensores();
        vistaJuego.getThread().pausar();
        super.onPause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (VistaJuego.pref.getBoolean("sensores",true)) {
            vistaJuego.activarSensores();
        }
        vistaJuego.getThread().reanudar();
    }
    @Override
    protected void onDestroy() {
        vistaJuego.getThread().detener();
        super.onDestroy();
    }
    /* MÉTODOS PARA Conseguir que la app no consuma recursos si no está visible */
}