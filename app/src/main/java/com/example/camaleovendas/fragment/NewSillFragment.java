package com.example.camaleovendas.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.camaleovendas.R;
import com.example.camaleovendas.view.MainActivity;

import java.util.Objects;

public class NewSillFragment extends Fragment {

    private MainActivity mainActivity;

    private static final String TAG_FRAGMENT = "fragment_new_sill";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_sill, container, false);

        mainActivity = (MainActivity) getActivity();

        assert mainActivity != null;

        Objects.requireNonNull(mainActivity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mainActivity.getSupportActionBar().setDisplayShowTitleEnabled(true);
        mainActivity.getSupportActionBar().setTitle("Nova venda");


        return view;
    }
}
