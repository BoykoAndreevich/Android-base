package com.alsebo.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_list_item_1;

public class MainActivity extends AppCompatActivity {
    private ListView listapral;
    private TextView textopral;
    private RadioButton radiobutton_pulsado=null; //inicializado a null

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listapral = findViewById(R.id.listapral);
        textopral = findViewById(R.id.textpral);

        ArrayList<Encapsulador> datos = new ArrayList<Encapsulador>();


        datos = new ArrayList<>();
        datos.add(new Encapsulador(R.drawable.ima1, "DONUTS", "El 15 de septiembre de 2009, fue lanzado el SDK de Android 1.6 Donut, basado en el núcleo Linux 2.6.29. En la actualización se incluyen numerosas características nuevas.", true));
        datos.add(new Encapsulador(R.drawable.ima2, "FROYO", "El 20 de mayo de 2010, El SDK de Android 2.2 Froyo (Yogur helado) fue lanzado, basado en el núcleo Linux 2.6.32.", false));
        datos.add(new Encapsulador(R.drawable.ima3, "GINGERBREAD", "El 6 de diciembre de 2010, el SDK de Android 2.3 Gingerbread (Pan de Jengibre) fue lanzado, basado en el núcleo Linux 2.6.35.", false));
        datos.add(new Encapsulador(R.drawable.ima4, "HONEYCOMB", "El 22 de febrero de 2011, sale el SDK de Android 3.0 Honeycomb (Panal de Miel). Fue la primera actualización exclusiva para TV y tableta, lo que quiere decir que sólo es apta para TV y tabletas y no para teléfonos Android.", false));
        datos.add(new Encapsulador(R.drawable.ima5, "ICE CREAM", "El SDK para Android 4.0.0 Ice Cream Sandwich (Sándwich de Helado), basado en el núcleo de Linux 3.0.1, fue lanzado públicamente el 12 de octubre de 2011.", false));
        datos.add(new Encapsulador(R.drawable.ima6, "JELLY BEAN", "Google anunció Android 4.1 Jelly Bean (Gomita Confitada o Gominola) en la conferencia del 30 de junio de 2012. Basado en el núcleo de linux 3.0.31, Bean fue una actualización incremental con el enfoque primario de mejorar la funcionalidad y el rendimiento de la interfaz de usuario.", false));
        datos.add(new Encapsulador(R.drawable.ima7, "KITKAT", "Su nombre se debe a la chocolatina KitKat, de la empresa internacional Nestlé. Posibilidad de impresión mediante WIFI. WebViews basadas en el motor de Chromium.", false));
        datos.add(new Encapsulador(R.drawable.ima8, "LOLLIPOP", "Incluye Material Design, un diseño intrépido, colorido, y sensible interfaz de usuario para las experiencias coherentes e intuitivos en todos los dispositivos. Movimiento de respuesta natural, iluminación y sombras realistas y familiares elementos visuales hacen que sea más fácil de navegar su dispositivo.", false));

        listapral.setAdapter(new Adaptador(this, R.layout.entrada,datos){
            @Override
            public void onEntrada(Object entrada, View view){
                TextView texto_superior_entrada = (TextView) view.findViewById(R.id.text_titulo);
                TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.text_datos);
                ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imagen);
                RadioButton miRadio = (RadioButton) view.findViewById(R.id.radioButton);
                texto_superior_entrada.setText(((Encapsulador) entrada).get_textotitulo());
                texto_inferior_entrada.setText(((Encapsulador) entrada).get_textocontenido());
                imagen_entrada.setImageResource(((Encapsulador) entrada).get_idimagen());
                miRadio.setChecked(((Encapsulador) entrada).get_checkson());
                if (((Encapsulador) entrada).get_checkson() == true) {
                    radiobutton_pulsado = (RadioButton) view.findViewById(R.id.radioButton);
                };
                miRadio.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        if (radiobutton_pulsado != null) radiobutton_pulsado.setChecked(false);
                        radiobutton_pulsado = (RadioButton) v;
                        textopral.setText("MARCADA UNA OPCIÓN");
                    }
                });
            }
        });



        listapral.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id){
                Encapsulador elegido = (Encapsulador) pariente.getItemAtPosition(posicion);
                String textoelegido = "seleccionado: " + elegido.get_textotitulo();
                textopral.setText(textoelegido);
            }
        });

    }

    public class Encapsulador{

        private int imagen;
        private String titulo;
        private String texto;
        private boolean dato1;

        public Encapsulador(int idimagen, String textotitulo, String textocontenido, boolean
                favorito){
            this.imagen = idimagen;
            this.titulo = textotitulo;
            this.texto = textocontenido;
            this.dato1 = favorito;
        }

        public String get_textotitulo(){
            return titulo;
        }

        public String get_textocontenido(){
            return texto;}

        public int get_idimagen(){
            return imagen;
        }

        public boolean get_checkson(){
            return dato1;
        }
    }
}