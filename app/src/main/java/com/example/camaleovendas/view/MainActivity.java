package com.example.camaleovendas.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.camaleovendas.R;
import com.example.camaleovendas.fragment.MainMenuFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_fragment);

        FragmentManager fragmentManager = getSupportFragmentManager();

        setSupportActionBar(toolbar);

        fragmentManager.beginTransaction().replace
                (R.id.content_fragment, new MainMenuFragment()).commit();

    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) super.onBackPressed();

        return true;
    }
}