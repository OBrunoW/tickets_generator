package com.example.camaleovendas.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.camaleovendas.R;
import com.example.camaleovendas.fragment.ConsolidatedFragment;
import com.example.camaleovendas.fragment.SaleProductFragment;
import com.example.camaleovendas.fragment.TicketFragment;

import java.util.ArrayList;
import java.util.List;

public class MenuTabAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> mFragments;
    private final List<String> mFragmentsTitle;
    public MenuTabAdapter(FragmentManager fm) {
        super(fm);
        mFragments = new ArrayList<>();
        mFragmentsTitle = new ArrayList<>();

        addFrag(new SaleProductFragment(), "Vender");
        addFrag(new ConsolidatedFragment(), "Gerir");
        addFrag(new TicketFragment(), "Ficha");
    }

    private void addFrag(Fragment fragment, String title) {
        mFragments.add(fragment);
        mFragmentsTitle.add(title);
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentsTitle.get(position);
    }
}
