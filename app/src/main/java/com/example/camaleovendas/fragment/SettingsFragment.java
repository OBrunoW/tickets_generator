package com.example.camaleovendas.fragment;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.camaleovendas.R;
import com.example.camaleovendas.view.MainActivity;

import java.util.Objects;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        MainActivity mainActivity = (MainActivity) getActivity();

        assert mainActivity != null;

        Objects.requireNonNull(mainActivity.getSupportActionBar()).setTitle("Configurações");
        mainActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.round_arrow_back_ios_24);
        mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mainActivity.getSupportActionBar().setElevation(0);
        mainActivity.getSupportActionBar().show();
    }
}