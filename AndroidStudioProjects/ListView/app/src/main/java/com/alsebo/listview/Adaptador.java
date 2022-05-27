package com.alsebo.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public abstract class Adaptador extends BaseAdapter {

    private ArrayList<?> entradas;
    private int x_layout_zdview;
    private Context contexto;

    public Adaptador(Context context, int x_layout_zdview, ArrayList<?> entradas){
        super();
        this.contexto = context;
        this.entradas = entradas;
        this.x_layout_zdview = x_layout_zdview;
    }
    public abstract void onEntrada(Object entrada, View view);

    public int getCount(){
        return entradas.size();
    }
    public Object getItem(int posicion){
        return entradas.get(posicion);
    }
    public long getItemId(int posicion) {
        return posicion;
    }

    public View getView(int posicion, View view, ViewGroup pariente){
        if (view == null) {
            LayoutInflater vi = (LayoutInflater)
                    contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(x_layout_zdview,null);
        }
        onEntrada(entradas.get(posicion),view);
        return view;
    }

}
