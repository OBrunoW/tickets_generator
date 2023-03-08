package com.example.camaleovendas;

import android.util.Log;

import com.example.camaleovendas.datasource.DataSource;

public class VendasApplication extends android.app.Application{

    @Override
    public void onCreate() {
        super.onCreate();

            new DataSource(this);

            Log.i("DB", "DB Criado");

    }
}
