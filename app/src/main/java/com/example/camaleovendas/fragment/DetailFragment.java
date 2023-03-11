package com.example.camaleovendas.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.camaleovendas.R;
import com.example.camaleovendas.adapter.ConsolidatedDetailsAdapterList;
import com.example.camaleovendas.adapter.ProductAdapterList;
import com.example.camaleovendas.controller.ProductController;
import com.example.camaleovendas.model.Product;
import com.example.camaleovendas.view.MainActivity;

import java.util.List;
import java.util.Objects;

public class DetailFragment extends Fragment {

    private MainActivity mainActivity;

    private static final String TAG_FRAGMENT = "fragment_details";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_custom_layout_form_consolidated_details, container, false);

        mainActivity = (MainActivity) getActivity();

        assert mainActivity != null;

        Objects.requireNonNull(mainActivity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mainActivity.getSupportActionBar().setDisplayShowTitleEnabled(true);
        mainActivity.getSupportActionBar().setTitle("Detalhes Relatório");

        List<Product> products = new ProductController(getContext()).getProduct();

        ConsolidatedDetailsAdapterList consolidatedDetails = new ConsolidatedDetailsAdapterList(mainActivity, products);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_details);
        recyclerView.setAdapter(consolidatedDetails);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));


        return view;
    }
}
