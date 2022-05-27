package com.alsebo.menus2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int MnOpA = 1;
    private static final int MnOpA_1 = 4;
    private static final int MnOpA_2 = 5;
    private static final int MnOpA_3 = 6;
    private static final int MnOpA_4 = 7;
    private static final int MnOpB = 2;
    private static final int MnOpB_1 = 8;
    private static final int MnOpB_2 = 9;
    private static final int MnOpB_3 = 10;
    private static final int MnOpB_4 = 11;

    TextView texto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texto = (TextView)findViewById(R.id.textView);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        SubMenu submenuA = menu.addSubMenu(Menu.NONE,MnOpA,Menu.NONE,"DIAS DE SEMANA");
                submenuA.add(Menu.NONE,MnOpA_1,Menu.NONE,"LUNES");
                submenuA.add(Menu.NONE,MnOpA_2,Menu.NONE,"MARTES");
                submenuA.add(Menu.NONE,MnOpA_3,Menu.NONE,"MIERCOLES");
                submenuA.add(Menu.NONE,MnOpA_4,Menu.NONE,"JUEVES");
        SubMenu submenuB = menu.addSubMenu(Menu.NONE,MnOpB,Menu.NONE,"MESES DEL AÑO");
                submenuB.add(Menu.NONE,MnOpB_1,Menu.NONE,"ENERO");
                submenuB.add(Menu.NONE,MnOpB_2,Menu.NONE,"FEBRERO");
                submenuB.add(Menu.NONE,MnOpB_3,Menu.NONE,"MARZO");
                submenuB.add(Menu.NONE,MnOpB_4,Menu.NONE,"ABRIL");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case MnOpA_1:
                texto.setText("Pulsado el dia Lunes");
                return true;
            case MnOpA_2:
                texto.setText("Pulsado el dia Martes");
                return true;
            case MnOpA_3:
                texto.setText("Pulsado el dia Miercoles");
                return true;
            case MnOpA_4:
                texto.setText("Pulsado el dia Jueves");
                return true;
            case MnOpB_1:
                texto.setText("Pulsado el mes de Enero ");
                return true;
            case MnOpB_2:
                texto.setText("Pulsado el mes de Febrero");
                return true;
            case MnOpB_3:
                texto.setText("Pulsado el mes de Marzo");
                return true;
            case MnOpB_4:
                texto.setText("Pulsado el mes de Abril");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}