package com.example.ev02as;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.RectShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.content.res.AppCompatResources;

import java.util.ArrayList;
import java.util.List;

public class VistaJuego extends View implements SensorEventListener {
    // //// ASTEROIDES //////
    private List<Grafico> asteroides; // Lista con los Asteroides
    private int numAsteroides = 5; // Número inicial de asteroides
    private int numFragmentos = 3; // Fragmentos en que se divide
    // //// NAVE //////
    private Grafico nave; // Gráfico de la nave
    private int giroNave; // Incremento de dirección
    private double aceleracionNave; // aumento de velocidad
    private static final int MAX_VELOCIDAD_NAVE = 20;
    // //// MISIL //////
    private Grafico misil; //Gráfico del misil
    private static int PASO_VELOCIDAD_MISIL = 12;
    private boolean misilActivo = false;
    private int tiempoMisil;

    // Incremento estándar de giro y aceleración
    private static final int PASO_GIRO_NAVE = 5;
    private static final float PASO_ACELERACION_NAVE = 0.5f;
    // //// THREAD Y TIEMPO //////
    // Thread encargado de procesar el juego
    private ThreadJuego thread = new ThreadJuego();
    // Cada cuanto queremos procesar cambios (ms)
    private static int PERIODO_PROCESO = 50;
    // Cuando se realizó el último proceso
    private long ultimoProceso = 0;

    // /// Para el manejo de la nave con pantalla táctil
    private float mX=0, mY=0;
    private boolean disparo=false;

    /*Para activar y desactivar sensores */
    private SensorManager mSensorManager;
    /*Para activar y desactivar sensores */

    /*Para incluir audio con MediaPlayer ANTES DE USAR SOUNDPOOL */
    //MediaPlayer mpDisparo, mpExplosion;
    /*Para incluir audio con MediaPlayer ANTES DE USAR SOUNDPOOL */

    // //// MULTIMEDIA //////
    SoundPool soundPool;
    int idDisparo, idExplosion;

    ///PREFERENCIAS
    public static SharedPreferences pref;

    //FRAGMENTACIÓN DE ASTEROIDES
    private Drawable drawableAsteroide[]= new Drawable[3];

    //PUNTUACIONES
    private int puntuacion = 0;
    private Activity padre;
    public void setPadre(Activity padre) {
        this.padre = padre;
    }

    public VistaJuego(Context context, AttributeSet attrs) {
        super(context, attrs);
        Drawable drawableNave, drawableMisil;


        /* cambiada para permitir la representaciíon vectorial de los asteroides
        drawableAsteroide = AppCompatResources.getDrawable(context,
                R.drawable.asteroide1);
         */
        pref = PreferenceManager.
            getDefaultSharedPreferences(getContext());
        if (pref.getString("graficos", "1").equals("0")) {
            Path pathAsteroide = new Path();
            pathAsteroide.moveTo((float) 0.3, (float) 0.0);
            pathAsteroide.lineTo((float) 0.6, (float) 0.0);
            pathAsteroide.lineTo((float) 0.6, (float) 0.3);
            pathAsteroide.lineTo((float) 0.8, (float) 0.2);
            pathAsteroide.lineTo((float) 1.0, (float) 0.4);
            pathAsteroide.lineTo((float) 0.8, (float) 0.6);
            pathAsteroide.lineTo((float) 0.9, (float) 0.9);
            pathAsteroide.lineTo((float) 0.8, (float) 1.0);
            pathAsteroide.lineTo((float) 0.4, (float) 1.0);
            pathAsteroide.lineTo((float) 0.0, (float) 0.6);
            pathAsteroide.lineTo((float) 0.0, (float) 0.2);
            pathAsteroide.lineTo((float) 0.3, (float) 0.0);
            /*
            ShapeDrawable dAsteroide = new ShapeDrawable(
                new PathShape(pathAsteroide, (float) 0.25, (float) 0.25));
            dAsteroide.getPaint().setColor(Color.WHITE);
            dAsteroide.getPaint().setStyle(Paint.Style.STROKE);
            dAsteroide.setIntrinsicWidth(50);
            dAsteroide.setIntrinsicHeight(50);
            drawableAsteroide = dAsteroide;
            */
            //Fragmentación asteroides
            for (int i=0; i<3; i++) {
                ShapeDrawable dAsteroide = new ShapeDrawable(new PathShape(pathAsteroide, 1, 1));
                dAsteroide.getPaint().setColor(Color.WHITE);
                dAsteroide.getPaint().setStyle(Paint.Style.STROKE);
                dAsteroide.setIntrinsicWidth(50 - i * 14);
                dAsteroide.setIntrinsicHeight(50 - i * 14);
                drawableAsteroide[i] = dAsteroide;
            }


            // Nave como gráfico vectorial
            Path pathNave = new Path();
            pathNave.moveTo((float) 0.0, (float) 0.0);
            pathNave.lineTo((float) 0.0, (float) 1.0);
            pathNave.lineTo((float) 1.0, (float) 0.5);
            pathNave.lineTo((float) 0.0, (float) 0.0);
            ShapeDrawable dNave = new ShapeDrawable(
                new PathShape(pathNave, (float) 0.25, (float) 0.25));
            dNave.getPaint().setColor(Color.WHITE);
            dNave.getPaint().setStyle(Paint.Style.STROKE);
            dNave.setIntrinsicWidth(20);
            dNave.setIntrinsicHeight(15);
            drawableNave = dNave;

            // Misil como gráfico vectorial
            ShapeDrawable dMisil = new ShapeDrawable(new RectShape());
            dMisil.getPaint().setColor(Color.WHITE);
            dMisil.getPaint().setStyle(Paint.Style.STROKE);
            dMisil.setIntrinsicWidth(15);
            dMisil.setIntrinsicHeight(3);
            drawableMisil = dMisil;


            setBackgroundColor(Color.BLACK);
            //desactivar la aceleración gráfica cuando se trabaja con gráficos vectoriales
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        } else {
            //Fragmentación asteroides
            drawableAsteroide[0] = AppCompatResources.getDrawable(context, R.drawable.asteroide1);
            drawableAsteroide[1] = AppCompatResources.getDrawable(context, R.drawable.asteroide2);
            drawableAsteroide[2] = AppCompatResources.getDrawable(context, R.drawable.asteroide3);
            /*
            drawableAsteroide =
                AppCompatResources.getDrawable(context, R.drawable.asteroide1);
            */
            drawableNave =
                AppCompatResources.getDrawable(context, R.drawable.nave);
            //Para incluir misil
            drawableMisil =
                AppCompatResources.getDrawable(context, R.drawable.misil1);
            //activar la aceleración gráfica cuando se trabaja con bitmap
            setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }

        asteroides = new ArrayList<Grafico>();
        for (int i = 0; i < numAsteroides; i++) {
            Grafico asteroide = new Grafico(this, drawableAsteroide[1]);
            asteroide.setIncY(Math.random() * 4 - 2);
            asteroide.setIncX(Math.random() * 4 - 2);
            asteroide.setAngulo((int) (Math.random() * 360));
            asteroide.setRotacion((int) (Math.random() * 8 - 4));
            asteroides.add(asteroide);
        }
        nave = new Grafico(this, drawableNave);
        //Para inicializar variable misil
        misil= new Grafico(this,drawableMisil);

        /*
        //Sensor de orientación
        SensorManager mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> listSensors = mSensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
        if (!listSensors.isEmpty()) {
            Sensor orientationSensor = listSensors.get(0);
            mSensorManager.registerListener(this,orientationSensor,SensorManager.SENSOR_DELAY_GAME);
        }
         */
        /*
        //Sensor de aceleración
        SensorManager mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> listSensors = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (!listSensors.isEmpty()) {
            Sensor acelerometerSensor = listSensors.get(0);
            mSensorManager.registerListener(this,acelerometerSensor,SensorManager.SENSOR_DELAY_GAME);
        }
        */
        /* INICIALIZAR GESTOR DE SENSORES */
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        /* INICIALIZAR GESTOR DE SENSORES */

        /* Para leer de las preferencias la activación inicial de sensores o no */
        if (pref.getBoolean("sensores",true)) {
            activarSensores();
        }else{
            desactivarSensores();
        }
        /* Para leer de las preferencias la activación inicial de sensores o no */
        /*Inicializar Audio MediaPlayer ANTES DE USAR SOUNDPOOL */
        //mpDisparo = MediaPlayer.create(context, R.raw.disparo);
        //mpExplosion = MediaPlayer.create(context, R.raw.explosion);
        /*Inicializar Audio MediaPlayer ANTES DE USAR SOUNDPOOL */

        /* Inicializar variables para SOUNDPOOL */
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC , 0);
        idDisparo = soundPool.load(context, R.raw.disparo, 0);
        idExplosion = soundPool.load(context, R.raw.explosion, 0);
        /* Inicializar variables para SOUNDPOOL */


    }
    @Override
    protected void onSizeChanged(int ancho, int alto,
                                           int ancho_anter, int alto_anter) {
        super.onSizeChanged(ancho, alto, ancho_anter, alto_anter);
        // Una vez que conocemos nuestro ancho y alto.
        // Antes, código sustituido por el do while de después
        /*
        for (Grafico asteroide: asteroides) {
            asteroide.setCenX((int) (Math.random()*ancho));
            asteroide.setCenY((int) (Math.random()*alto));
        }
         */
        // Situamos la nave en el centro
        nave.setCenX((int) (ancho/2));
        nave.setCenY((int) (alto/2));
        // Para que los asteroides no se sitúen encima de la nave
        for (Grafico asteroide: asteroides) {
            do {
                asteroide.setCenX((int) (Math.random() * ancho));
                asteroide.setCenY((int) (Math.random() * alto));
            } while (asteroide.distancia(nave) < (ancho + alto) / 5);
        }

        ultimoProceso = System.currentTimeMillis();
        thread.start();

    }
    @Override
    // ANTES
    // synchronized protected void onDraw(Canvas canvas) {
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Reducimos el tamaño de la sección crítica
        //Antes estaba en onDraw
        synchronized (asteroides) {
            // Dibujamos los gráficos
            for (Grafico asteroide : asteroides) {
                asteroide.dibujaGrafico(canvas);
            }
        }
        // Dibujamos la nave
        nave.dibujaGrafico(canvas);
        // Dibujamos el misil sólo si lo indica la variable misilActivo
        if (misilActivo) misil.dibujaGrafico(canvas);

    }
    // ANTES
    // synchronized protected void actualizaFisica() {
    protected void actualizaFisica() {
        long ahora = System.currentTimeMillis();
        // Salir si el período de proceso no se ha cumplido.
        if (ultimoProceso + PERIODO_PROCESO > ahora) {
            return;
        }
        // Para una ejecución en tiempo real calculamos el factor de movimiento
        double factorMov = (ahora - ultimoProceso) / PERIODO_PROCESO;
        ultimoProceso = ahora; // Para la próxima vez
        // Actualizamos velocidad y dirección de la nave a partir de
        // giroNave y aceleracionNave (según la entrada del jugador)
        nave.setAngulo((int) (nave.getAngulo() + giroNave * factorMov));
        double nIncX = nave.getIncX() + aceleracionNave *
            Math.cos(Math.toRadians(nave.getAngulo())) * factorMov;
        double nIncY = nave.getIncY() + aceleracionNave *
            Math.sin(Math.toRadians(nave.getAngulo())) * factorMov;
        // Actualizamos si el módulo de la velocidad no excede el máximo
        if (Math.hypot(nIncX, nIncY) <= MAX_VELOCIDAD_NAVE) {
            nave.setIncX(nIncX);
            nave.setIncY(nIncY);
        }
        nave.incrementaPos(factorMov); // Actualizamos posición
        for (Grafico asteroide : asteroides) {
            asteroide.incrementaPos(factorMov);
        }

        //Para tener en cuenta las puntuaciones
        for (Grafico asteroide : asteroides) {
            if (asteroide.verificaColision(nave)) {
                salir();
            }
        }
        // Actualizamos posición de misil
        if (misilActivo) {
            misil.incrementaPos(factorMov);
            tiempoMisil -= factorMov;
            if (tiempoMisil < 0) {
                misilActivo = false;
            } else {
                for (int i = 0; i < asteroides.size(); i++)
                    if (misil.verificaColision(asteroides.get(i))) {
                        destruyeAsteroide(i);
                        break;
                    }
            }
        }

    }
    //Métodos para implementar funcionalidad del MISIL
    private void destruyeAsteroide (int i) {
        //Reducción de la sección crítica del hilo secundario
        //Antes el synchronized estaba en ActualizaFisica
        synchronized (asteroides) {
            /* Activar sonido de explosión de asteroide ANTES DE USAR SOUNDPOOL */
            //mpExplosion.start();

            //Fragmentación asteroides
            int tam;
            if(asteroides.get(i).getDrawable()!=drawableAsteroide[2]){
                if(asteroides.get(i).getDrawable()==drawableAsteroide[1]){
                    tam=2;
                } else {
                    tam=1;
                }
                for(int n=0;n<numFragmentos;n++){
                    Grafico asteroide = new Grafico(this,drawableAsteroide[tam]);
                    asteroide.setCenX(asteroides.get(i).getCenX());
                    asteroide.setCenY(asteroides.get(i).getCenY());
                    asteroide.setIncX(Math.random()*7-2-tam);
                    asteroide.setIncY(Math.random()*7-2-tam);
                    asteroide.setAngulo((int)(Math.random()*360));
                    asteroide.setRotacion((int)(Math.random()*8-4));
                    asteroides.add(asteroide);
                }
            }

            /* Activar sonido de explosión de asteroide USANDO SOUNDPOOL */
            soundPool.play(idExplosion, 1, 1, 0, 0, 1);
            /* Activar sonido de explosión de asteroide USANDO SOUNDPOOL */

            asteroides.remove(i);
            puntuacion += 1000;
            misilActivo = false;
            this.postInvalidate();
            //Para tener en cuenta las puntuaciones
            if (asteroides.isEmpty()) {
                salir();
            }
        }
    }
    private void activaMisil() {
        misil.setCenX(nave.getCenX());
        misil.setCenY(nave.getCenY());
        misil.setAngulo(nave.getAngulo());
        misil.setIncX(Math.cos(Math.toRadians(misil.getAngulo())) * PASO_VELOCIDAD_MISIL);
        misil.setIncY(Math.sin(Math.toRadians(misil.getAngulo())) * PASO_VELOCIDAD_MISIL);
        tiempoMisil=(int)Math.min(this.getWidth()/Math.abs(misil.getIncX()),
            this.getHeight()/Math.abs(misil.getIncY())) - 2;
        misilActivo = true;
        /* Activar sonido de disparo de misil ANTES DE USAR SOUNDPOOL */
        //mpDisparo.start();

        /* Activar sonido de disparo de misil USANDO SOUNDPOOL */
        soundPool.play(idDisparo, 1, 1, 1, 0, 2);
        /* Activar sonido de disparo de misil USANDO SOUNDPOOL */
    }

    /*Redefinición de la clase ThreadJuego para que cuando la aplicación no esté visible
    no consuma recursos */
    class ThreadJuego extends Thread {
        private boolean pausa,corriendo;
        public synchronized void pausar() {
            pausa = true;
        }
        public synchronized void reanudar() {
            pausa = false;
            notify();
        }
        public void detener() {
            corriendo = false;
            if (pausa) reanudar();
        }
        @Override
        public void run() {
            corriendo = true;
            while (corriendo) {
                actualizaFisica();
                synchronized (this) {
                    while (pausa) {
                        try {
                            wait();
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
    }
    /*Redefinición de la clase ThreadJuego para que cuando la aplicación no esté visible
    no consuma recursos*/

    /*Inclusión del get (Generate... > Getter) para acceder al hilo ThreadJuego */
    public ThreadJuego getThread() {
        return thread;
    }
    /* inclusión del get para acceder al hilo ThreadJuego */

    //Manejo de la nave con el teclado
    @Override
    public boolean onKeyDown(int codigoTecla, KeyEvent evento) {
        super.onKeyDown(codigoTecla, evento);
        // Suponemos que vamos a procesar la pulsación
        boolean procesada = true;
        switch (codigoTecla) {
            case KeyEvent.KEYCODE_DPAD_UP:
                aceleracionNave = +PASO_ACELERACION_NAVE;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                giroNave = -PASO_GIRO_NAVE;
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                giroNave = +PASO_GIRO_NAVE;
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_ENTER:
                activaMisil();
                break;
            case KeyEvent.KEYCODE_SPACE:
                aceleracionNave = +1000;
                break;
            default:
                // Si estamos aquí, no hay pulsación que nos interese
                procesada = false;
                break;
        }
        return procesada;
    }
    @Override
    public boolean onKeyUp(int codigoTecla, KeyEvent evento) {
        super.onKeyUp(codigoTecla, evento);
        // Suponemos que vamos a procesar la pulsación
        boolean procesada = true;
        switch (codigoTecla) {
            case KeyEvent.KEYCODE_DPAD_UP:
                aceleracionNave = 0;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                giroNave = 0;
                break;
            default:
                // Si estamos aquí, no hay pulsación que nos interese
                procesada = false;
                break;
        }
        return procesada;
    }

    //Manejo de la nave con la pantalla táctil
    @Override
    public boolean onTouchEvent (MotionEvent event) {
        super.onTouchEvent(event);
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                disparo=true;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - mX);
                float dy = Math.abs(y - mY);
                //if (dy<6 && dx>6){ //HORIZONTAL
                if (dy<6 && dx>6){
                    giroNave = Math.round((x - mX) / 2);
                    disparo = false;
                } else if (dx<6 && dy>6){
                //} else if (dx<6 && dy>6){ //VERTICAL
                    aceleracionNave = Math.round((mY - y) / 25);
                    disparo = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                giroNave = 0;
                aceleracionNave = 0;
                if (disparo){
                    //Se implementa más adelante
                    activaMisil();
                }
                break;
        }
        mX=x; mY=y;
        return true;
    }
    //Manejo de la nave con el sensor de aceleración
    //Implementación de los dos métodos que incluye la interfaz SensorEventListener
    // Variables utilizadas
    private boolean hayValorInicial = false;
    private float valorInicial;
    // Implementación de los métodos
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {};
    @Override
    public void onSensorChanged(SensorEvent event) {
        float valor = event.values[1];
        if (!hayValorInicial){
            valorInicial = valor;
            hayValorInicial = true;
        }
        giroNave=(int) (valor-valorInicial)/3 ;
    }
    public void activarSensores(){
        //Sensor de orientación
        List<Sensor> listSensors = mSensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
        if (!listSensors.isEmpty()) {
            Sensor orientationSensor = listSensors.get(0);
            mSensorManager.registerListener(this,orientationSensor,SensorManager.SENSOR_DELAY_GAME);
        }
        //Sensor de aceleración
        //SensorManager mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> listSensors2 = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (!listSensors2.isEmpty()) {
            Sensor acelerometerSensor = listSensors2.get(0);
            mSensorManager.registerListener(this,acelerometerSensor,SensorManager.SENSOR_DELAY_GAME);
        }

    }
    public void desactivarSensores(){
        mSensorManager.unregisterListener(this);
    }
    //Puntuaciones
    private void salir() {
        Bundle bundle = new Bundle();
        bundle.putInt("puntuacion", puntuacion);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        padre.setResult(Activity.RESULT_OK, intent);
        padre.finish();
    }
}