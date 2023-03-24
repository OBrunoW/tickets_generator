package com.example.camaleovendas.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.camaleovendas.R;
import com.example.camaleovendas.adapter.MenuTabAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MenuTabCollectionFragment extends Fragment {
    MenuTabAdapter menuTabAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;
    List<Integer> tabIcons;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_tab_collection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        menuTabAdapter = new MenuTabAdapter(getChildFragmentManager());
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(menuTabAdapter);

        tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        tabIcons = new ArrayList<>();
        tabIcons.add(R.drawable.round_add_shopping_cart_24);
        tabIcons.add(R.drawable.round_view_list_24);
        tabIcons.add(R.drawable.round_confirmation_number_24);

        setupTabIcons();
    }

    private void setupTabIcons() {
        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(tabIcons.get(0));
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(tabIcons.get(1));
        Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(tabIcons.get(2));
    }
}

