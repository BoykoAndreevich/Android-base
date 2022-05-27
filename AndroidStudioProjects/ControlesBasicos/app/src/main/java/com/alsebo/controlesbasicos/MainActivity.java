package com.alsebo.controlesbasicos;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        

        TextView textView1 = (TextView) findViewById(R.id.textView1);
        textView1.setText("Texto Construido desde Java \n Tamaño 20dp, Italic y color Blue");
        textView1.setTextColor(Color.BLUE);
        textView1.setTextSize(20);
        textView1.setTypeface(null, Typeface.ITALIC);
        textView1.setGravity(Gravity.CENTER);

        Animation miAnimacion = AnimationUtils.loadAnimation(this, R.anim.alfa1);
        miAnimacion.setRepeatMode(Animation.RESTART);
        miAnimacion.setRepeatCount(20);
        textView1.startAnimation(miAnimacion);

        
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setGravity(Gravity.CENTER);
        String texto = "\n"+"<font color=#0A37B8><br/>Texto añadido con Append desde Java</font> ";
        textView2.append(Html.fromHtml(texto));


        TextView textView3 = (TextView) findViewById(R.id.textView3);
        textView3.setText("TEXTO ESCRITO FUENTE UMBRELLA");
        textView3.setGravity(Gravity.CENTER);
        Typeface miFuente = Typeface.createFromAsset(getAssets(),"fonts/umbrella.ttf");
        Typeface fuente = ResourcesCompat.getFont(this, R.font.umbrella);
        textView3.setTypeface(fuente);

        Animation miAnim = AnimationUtils.loadAnimation(this, R.anim.rotar2);
        miAnim.setRepeatMode(Animation.RESTART);
        miAnim.setRepeatCount(20);
        textView3.startAnimation(miAnim);
    }


}
