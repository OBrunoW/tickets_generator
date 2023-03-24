package com.example.camaleovendas.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.camaleovendas.R;
import com.example.camaleovendas.fragment.MenuTabCollectionFragment;
import com.example.camaleovendas.fragment.SettingsFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_fragment);

        fragmentManager = getSupportFragmentManager();

        setSupportActionBar(toolbar);

        fragmentManager.beginTransaction().replace
                (R.id.content_fragment, new MenuTabCollectionFragment()).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public void onBackPressed() {
            super.onBackPressed();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) super.onBackPressed();

        if (item.getItemId() == R.id.menu_main_setting) fragmentManager.beginTransaction().replace
                (R.id.content_fragment, new SettingsFragment()).addToBackStack(null).commit();

        return true;
    }
}