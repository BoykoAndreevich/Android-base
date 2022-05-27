package com.alsebo.mydinero2;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private EditText mInput;
    private TextView mTextEuro, mTextDolar, mTextLibra;
    private Button mBotonEuro, mBotonDolar, mBotonLibra;
    private ProgressBar mBar;
    private ImageView mImagen;
    private Button port;

    static final String MARCADOR = "";
    int myMarcador;

    private EditText mInputDolar;
    private Button mBotonChDolar;
    private EditText mInputLibra;
    private Button mBotonChLibra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInput = (EditText) findViewById(R.id.editDinero);
        mTextEuro = (TextView) findViewById(R.id.textEuro);
        mTextDolar = (TextView) findViewById(R.id.textDolar);
        mTextLibra = (TextView) findViewById(R.id.textLibra);
        mBotonDolar = (Button) findViewById(R.id.botonDolar);
        mImagen = (ImageView) findViewById(R.id.imagen);
        port = (Button) findViewById(R.id.port);
        mInputDolar = (EditText) findViewById(R.id.editDolar);
        mBotonChDolar = (Button) findViewById(R.id.botonCambioDolar);
        mInputLibra = (EditText) findViewById(R.id.editLibra);
        mBotonChLibra = (Button) findViewById(R.id.botonCambioLibra);

        String[] archivos = fileList();
        if (existe_archivo(archivos, "dólar.dat")) {
            try {
                InputStreamReader archivo = new InputStreamReader(openFileInput("dólar.dat"));
                BufferedReader br = new BufferedReader(archivo);
                String ínea = br.readLine();
                String todas = "";
                while (ínea != null) {
                    todas = todas + ínea + "\n";
                    ínea = br.readLine();
                }
                br.close();
                archivo.close();
                mInputDolar.setText(todas);
            } catch (IOException e) {
                Toast.makeText(this, "Error leyendo dólar", Toast.LENGTH_SHORT).show();
            }
        }
        if (existe_archivo(archivos, "libra.dat")) {
            try {
                InputStreamReader archivo = new InputStreamReader(openFileInput("libra.dat"));
                BufferedReader br = new BufferedReader(archivo);
                String ínea = br.readLine();
                String todas = "";
                while (ínea != null) {
                    todas = todas + ínea + "\n";
                    ínea = br.readLine();
                }
                br.close();
                archivo.close();
                mInputLibra.setText(todas);
            } catch (IOException e) {
                Toast.makeText(this, "Error leyendo libra", Toast.LENGTH_SHORT).show();
            }
        }

        port.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                port();
                vibrar();
            }
        });
        mBotonDolar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                vibrar();

                if (mInput.getText().length() == 0) {
                    Snackbar.make(arg0, "ERROR : Introduzca dinero", Snackbar.LENGTH_LONG).show();
                    //mostrarBrindis("ERROR : Introduzca dinero");
                } else {
                    mTextEuro.setText(String.format("%1$,.2f", Double.parseDouble(String.valueOf(mInput.getText())) /
                            166.386) + "€");
                    mTextDolar.setText(String.format("%1$,.2f",Double.parseDouble(String.valueOf(mInput.getText())) /
                            166.386 / Double.parseDouble(String.valueOf(mInputDolar.getText()))) + "$");
                    mTextLibra.setText(String.format("%1$,.2f", Double.parseDouble(String.valueOf(mInput.getText())) /
                            185.700/ Double.parseDouble(String.valueOf(mInputLibra.getText()))) + "£");

                    ocultarTeclado();
                }

            }
        });
        mBotonChDolar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    guardar_dolar();
            }
        });
        mBotonChLibra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar_libra();
            }
        });


        mImagen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                Toast toast = Toast.makeText(getApplicationContext(), "Horizontal", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                vibrar();

                toast.show();

                /*mInput.setText("");
                mTextEuro.setText("");
                mTextDolar.setText("");
                mTextLibra.setText("");
                mBar.setVisibility(View.INVISIBLE);
                ocultarTeclado();*/
            }
        });
        if (savedInstanceState != null) {
            myMarcador = savedInstanceState.getInt(MARCADOR);
        } else {
            //Acciones como iniciar con valor por defecto, no hacer nada…
        }
    }//Fin de onCreate
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mimenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.accion_salir:
                Toast.makeText(getApplicationContext(), "Salir", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void port(){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toast toast = Toast.makeText(getApplicationContext(), "Vertical", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    public void vibrar() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(200);
        }
    }

    /*private void mostrarBrindis(String msg) {
        ocultarTeclado();
        Toast toast = Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }*/

    private void ocultarTeclado() {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mInput.getWindowToken(), 0);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(MARCADOR, myMarcador);
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("EURO", mTextEuro.getText().toString());
        savedInstanceState.putString("DOLAR", mTextDolar.getText().toString());
        savedInstanceState.putString("LIBRA", mTextLibra.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        myMarcador = savedInstanceState.getInt(MARCADOR);
        mTextEuro.setText(savedInstanceState.getString("EURO"));
        mTextDolar.setText(savedInstanceState.getString("DOLAR"));
        mTextLibra.setText(savedInstanceState.getString("LIBRA"));
    }

    public void guardar_dolar() {
        try {
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput("dólar.dat",
                    Activity.MODE_PRIVATE));
            archivo.write(mInputDolar.getText().toString());
            archivo.flush();
            archivo.close();
        } catch (IOException e) {
            Toast.makeText(this, "Error guardando dólar", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "Dólar guardado", Toast.LENGTH_SHORT).show();
    }
    public void guardar_libra() {
        try {
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput("libra.dat",
                    Activity.MODE_PRIVATE));
            archivo.write(mInputDolar.getText().toString());
            archivo.flush();
            archivo.close();
        } catch (IOException e) {
            Toast.makeText(this, "Error guardando libra", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "Libra guardado", Toast.LENGTH_SHORT).show();
    }

    private boolean existe_archivo(String[] archivos, String archbusca) {
        for (int f = 0; f < archivos.length; f++)
            if (archbusca.equals(archivos[f]))
                return true;
        return false;
    }


}