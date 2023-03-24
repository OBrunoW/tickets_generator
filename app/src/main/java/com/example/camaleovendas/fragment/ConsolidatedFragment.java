package com.example.camaleovendas.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.camaleovendas.R;
import com.example.camaleovendas.adapter.ConsolidatedAdapterList;
import com.example.camaleovendas.adapter.ProductAdapterList;
import com.example.camaleovendas.controller.ConsolidatedController;
import com.example.camaleovendas.controller.ProductController;
import com.example.camaleovendas.model.Consolidated;
import com.example.camaleovendas.model.Product;
import com.example.camaleovendas.view.MainActivity;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ConsolidatedFragment extends Fragment {

    private MainActivity mainActivity;
    private ProductAdapterList productAdapterList;
    private ConsolidatedAdapterList consolidatedAdapterList;
    private List<Product> products;
    private List<Consolidated> consolidateds;
    private RecyclerView recyclerView;
    private final boolean buttonState = true;
    private TextView performance;

    private static final String TAG_FRAGMENT = "fragment_main_menu";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_consolidated, container, false);

        mainActivity = (MainActivity) getActivity();

        assert mainActivity != null;

        Objects.requireNonNull(mainActivity.getSupportActionBar()).setTitle("Camaleões vendas");
        mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mainActivity.getSupportActionBar().setDisplayShowTitleEnabled(true);
        mainActivity.getSupportActionBar().setElevation(0);

        ProductController productController = new ProductController(getContext());
        products = productController.getAll();

        ConsolidatedController consolidatedController = new ConsolidatedController(getContext());
        consolidateds = consolidatedController.getAll();

        FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();

        productAdapterList = new ProductAdapterList(mainActivity, products);
        consolidatedAdapterList = new ConsolidatedAdapterList(mainActivity, consolidateds);

        recyclerView = view.findViewById(R.id.list_consolidated);
        recyclerView.setAdapter(productAdapterList);

        performance = view.findViewById(R.id.result_consolidated);


        Locale ptBr = new Locale("pt", "BR");
        double val = 0.0;

        for(Consolidated obj : consolidateds) {

            String value = NumberFormat.getCurrencyInstance(ptBr).format(val += obj.getPrice());
            performance.setText(value);
        }

        recyclerView.setAdapter(consolidatedAdapterList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        performance.setOnClickListener(view13 -> fragmentManager.beginTransaction().replace
                (R.id.content_fragment, new DetailFragment()).addToBackStack(null).commit());


        return view;
    }
}
