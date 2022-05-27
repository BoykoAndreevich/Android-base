package com.example.ev02as;


import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

public class PreferenciasFragment extends PreferenceFragment {
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);

        //para verificar número de fragmentos de preferencias entre 1 y 9
        final EditTextPreference fragmentos = (EditTextPreference)
            findPreference("fragmentos");
        fragmentos.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
          @Override
          public boolean onPreferenceChange(Preference preference, Object newValue) {
            int valor;
            try {
              valor = Integer.parseInt((String) newValue);
            } catch (Exception e) {
              Toast.makeText(getActivity(), "Ha de ser un número", Toast.LENGTH_SHORT).show();
              return false;
            }
            if (valor >= 0 && valor <= 9) {
              fragmentos.setSummary("En cuantos trozos se divide un asteroide (" + valor + ")");
              return true;
            } else {
              Toast.makeText(getActivity(), "Máximo de fragmentos 9", Toast.LENGTH_SHORT).show();
              return false;
            }
          }
        });
    }
}
