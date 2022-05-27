package com.alsebo.ejerciciollamadaentrante;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class ReceptorLlamadas extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Sacamos información de la intención
        String estado = "", numero = "";
        Bundle extras = intent.getExtras();
        if (extras != null) {
            } else {
           // requestPermission(Manifest.permission.READ_PHONE_STATE);
            if (ContextCompat.checkSelfPermission( (Activity) context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat .requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
            }
            estado = extras.getString(TelephonyManager.EXTRA_STATE);
            if (estado.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                numero = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                String info = estado + " " + numero;
                Log.d("ReceptorAnuncio", info + " intent=" + intent);
                // Creamos Notificación
                NotificationCompat.Builder notificacion = new
                        NotificationCompat.Builder(context)
                        .setContentTitle("Llamada entrante ")
                        .setContentText(info)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(PendingIntent.getActivity(context, 0,
                                new Intent(context, MainActivity.class), 0));
                ((NotificationManager) context.getSystemService(Context.
                        NOTIFICATION_SERVICE)).notify(1,notificacion.build());
            }

        }

    }



   /* protected void requestPermission(String readPhoneState, int requestCode) {
        int permission = ContextCompat.checkSelfPermission(this,
                readPhoneState);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{readPhoneState}, requestCode
            );
        }
    }*/


}