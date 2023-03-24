package com.example.camaleovendas.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

public class PinFragment extends Fragment implements View.OnClickListener{

    private ImageView circle1, circle2, circle3, circle4;
    private String strPin = "";
    private FragmentManager fragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pin, container, false);

        MainActivity mainActivity = (MainActivity) getActivity();

        assert mainActivity != null;

        Objects.requireNonNull(mainActivity.getSupportActionBar()).hide();

        Button btn0 = view.findViewById(R.id.pin_button_0);
        Button btn1 = view.findViewById(R.id.pin_button_1);
        Button btn2 = view.findViewById(R.id.pin_button_2);
        Button btn3 = view.findViewById(R.id.pin_button_3);
        Button btn4 = view.findViewById(R.id.pin_button_4);
        Button btn5 = view.findViewById(R.id.pin_button_5);
        Button btn6 = view.findViewById(R.id.pin_button_6);
        Button btn7 = view.findViewById(R.id.pin_button_7);
        Button btn8 = view.findViewById(R.id.pin_button_8);
        Button btn9 = view.findViewById(R.id.pin_button_9);

        circle1 = view.findViewById(R.id.pin_circle_1);
        circle2 = view.findViewById(R.id.pin_circle_2);
        circle3 = view.findViewById(R.id.pin_circle_3);
        circle4 = view.findViewById(R.id.pin_circle_4);

        Button btnBackSpace = view.findViewById(R.id.pin_button_back_space);

        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);

        btnBackSpace.setOnClickListener(v -> {

            if (strPin.length() > 0) {
                StringBuilder buffer = new StringBuilder(strPin);

                buffer.deleteCharAt(strPin.length() - 1);

                strPin = String.valueOf(buffer);

                setCircle();
            }
        });

        fragmentManager = mainActivity.getSupportFragmentManager();
        return view;
    }

    public void addExpression(String data) {

        if (strPin.length() < 4) {
            if (strPin.length() == 0) {
                strPin = data;
            } else {
                strPin += data;
            }
            setCircle();
        }
        if (strPin.length() == 4) {
            verify();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.pin_button_0:
                addExpression("0");
                break;

            case R.id.pin_button_1:
                addExpression("1");
                break;

            case R.id.pin_button_2:
                addExpression("2");
                break;

            case R.id.pin_button_3:
                addExpression("3");
                break;

            case R.id.pin_button_4:
                addExpression("4");
                break;

            case R.id.pin_button_5:
                addExpression("5");
                break;

            case R.id.pin_button_6:
                addExpression("6");
                break;

            case R.id.pin_button_7:
                addExpression("7");
                break;

            case R.id.pin_button_8:
                addExpression("8");
                break;

            case R.id.pin_button_9:
                addExpression("9");
                break;
        }

    }

    private void setCircle() {
        switch (strPin.length()) {
            case 4:
                circle4.setImageResource(R.drawable.pin_circle);
                break;
            case 3:
                circle4.setImageResource(R.drawable.pin_circle_void);
                circle3.setImageResource(R.drawable.pin_circle);
                break;
            case 2:
                circle3.setImageResource(R.drawable.pin_circle_void);
                circle2.setImageResource(R.drawable.pin_circle);
                break;
            case 1:
                circle2.setImageResource(R.drawable.pin_circle_void);
                circle1.setImageResource(R.drawable.pin_circle);
                break;
            default:
                circle1.setImageResource(R.drawable.pin_circle_void);
                circle2.setImageResource(R.drawable.pin_circle_void);
                circle3.setImageResource(R.drawable.pin_circle_void);
                circle4.setImageResource(R.drawable.pin_circle_void);
        }
    }

    private void verify() {

        int pin = Integer.parseInt(strPin);

        if(pin == 1234) fragmentManager.beginTransaction().replace
                (R.id.content_fragment, new MenuTabCollectionFragment()).commit();

        strPin = "";
        setCircle();
    }
}
