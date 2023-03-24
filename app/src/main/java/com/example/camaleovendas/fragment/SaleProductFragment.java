package com.example.camaleovendas.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
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
import com.example.camaleovendas.printer.ElginI9Settings;
import com.example.camaleovendas.printer.SettingPrinterE1;
import com.example.camaleovendas.util.Util;
import com.example.camaleovendas.view.MainActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Objects;

public class SaleProductFragment extends Fragment {

    private MainActivity mainActivity;
    private ProductAdapterList productAdapterList;
    private ConsolidatedAdapterList consolidatedAdapterList;
    private List<Product> products;
    private List<Consolidated> consolidateds;
    private RecyclerView recyclerView;
    private final boolean buttonState = true;

    private static final String TAG_FRAGMENT = "fragment_main_menu";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sale_product, container, false);

        mainActivity = (MainActivity) getActivity();

        assert mainActivity != null;

        Objects.requireNonNull(mainActivity.getSupportActionBar()).setTitle("Camaleões vendas");
        mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mainActivity.getSupportActionBar().setElevation(0);

        ProductController productController = new ProductController(getContext());
        products = productController.getAll();

        ConsolidatedController consolidatedController = new ConsolidatedController(getContext());
        consolidateds = consolidatedController.getAll();

        FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();

        productAdapterList = new ProductAdapterList(mainActivity, products);
        consolidatedAdapterList = new ConsolidatedAdapterList(mainActivity, consolidateds);

        recyclerView = view.findViewById(R.id.list_products);
        recyclerView.setAdapter(productAdapterList);

        ExtendedFloatingActionButton cardNewProduct = view.findViewById(R.id.card_new_product);

        cardNewProduct.setOnClickListener(view1 ->{
            newProduct();
        });

        recyclerView.setAdapter(productAdapterList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));


        productAdapterList.setOnClickListener(new ProductAdapterList.OnClickListener() {
            @Override
            public void onItemClick(View view, Product obj, int pos) {
                newSill(obj);
            }

            @Override
            public void onItemLongClick(View view, Product obj, int pos) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Deletar")
                        .setMessage("Deseja remover esse produto ?")
                        .setPositiveButton("Sim", (dialogInterface, i) -> {
                            productAdapterList.deleteItem(pos);
                        })
                        .setNegativeButton("Não", (dialogInterface, i) -> dialogInterface.dismiss())
                        .show();

            }
        });

        return view;
    }

    private void newProduct() {

        View customLayout = LayoutInflater
                .from(getContext()).inflate(R.layout.dialog_custom_layout_form_new_product, null);

        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(customLayout);

        Toolbar toolbar = customLayout.findViewById(R.id.toolbar_dialog);
        toolbar.setBackgroundColor(Color.WHITE);
        toolbar.setTitle("Novo Produto");

        TextInputEditText name = customLayout.findViewById(R.id.dialog_custom_layout_name_text);
        EditText price = customLayout.findViewById(R.id.edit_price_new_product);
        TextInputLayout layout = customLayout.findViewById(R.id.dialog_custom_layout_name_input_text);
        Button save = customLayout.findViewById(R.id.save_new_product);

        save.setOnClickListener(view -> {

            boolean stateName;
            boolean statePrice;

            if(TextUtils.isEmpty(name.getText())) {
                layout.setError("Obrigatório");
                layout.setErrorEnabled(true);
                stateName = false;

            } else {
                layout.setErrorEnabled(false);
                stateName = true;
            }

            for(Product obj : new ProductController(getContext()).getAll()) {
                if (Objects.equals(obj.getName(), name.getText().toString())) {
                    layout.setError("Nome já existe");
                    layout.setErrorEnabled(true);
                    stateName = false;
                } else {
                    layout.setErrorEnabled(false);
                    stateName = true;
                }
            }

            statePrice = !TextUtils.isEmpty(price.getText());

            if(stateName && statePrice) {
                Product product = new Product();
                product.setName(name.getText().toString());
                product.setPrice(Double.parseDouble(price.getText().toString()));
                product.setAmount(0);

                new ProductController(getContext()).save(product);

                products.add(product);
                productAdapterList.notifyDataSetChanged();

                dialog.dismiss();
            }
        });


        dialog.show();
        dialog.getWindow().setLayout
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    private void newSill(Product obj) {

        View customLayout = LayoutInflater
                .from(getContext()).inflate(R.layout.fragment_new_sill, null);

        ElginI9Settings settings = new ElginI9Settings(getContext());
        SettingPrinterE1 e1 = new SettingPrinterE1(getContext());

        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(customLayout);
        dialog.setCancelable(false);

        Toolbar toolbar = customLayout.findViewById(R.id.toolbar_dialog);
        toolbar.setBackgroundColor(Color.WHITE);
        toolbar.setTitle(obj.getName());

        final String[] pg = {null};

        ImageButton increment, decrement;
        LinearLayout pix, credit, debit, money;
        TextView result;

        final int[] incrementValue = {1};

        increment = customLayout.findViewById(R.id.increment);
        decrement = customLayout.findViewById(R.id.decrement);
        pix = customLayout.findViewById(R.id.card_payment_pix);
        credit = customLayout.findViewById(R.id.card_payment_card);
        debit = customLayout.findViewById(R.id.card_payment_card_debit);
        money = customLayout.findViewById(R.id.card_payment_money);
        result = customLayout.findViewById(R.id.result);
        MaterialButton buttonPrint = customLayout.findViewById(R.id.button_print_ticket);
        MaterialButton buttonCancel = customLayout.findViewById(R.id.button_cancell_print);

        increment.setOnClickListener(view -> result.setText(String.valueOf(1 + incrementValue[0]++)));
        decrement.setOnClickListener(view -> {
            if(incrementValue[0] >= 1) result.setText(String.valueOf(incrementValue[0]-- -1));
        });

        pix.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_corner_radious));
        pg[0] = Util.NAME_PIX;

        pix.setOnClickListener(view -> {
            pix.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_corner_radious));
            credit.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_corner_radious_selected));
            debit.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_corner_radious_selected));
            money.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_corner_radious_selected));
            pg[0] = Util.NAME_PIX;
        });
        credit.setOnClickListener(view -> {
            pix.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_corner_radious_selected));
            credit.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_corner_radious));
            debit.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_corner_radious_selected));
            money.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_corner_radious_selected));
            pg[0] = Util.NAME_CREDIT;
        });
        debit.setOnClickListener(view -> {
            pix.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_corner_radious_selected));
            credit.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_corner_radious_selected));
            debit.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_corner_radious));
            money.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_corner_radious_selected));
            pg[0] = Util.NAME_DEBIT;
        });
        money.setOnClickListener(view -> {
            pix.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_corner_radious_selected));
            credit.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_corner_radious_selected));
            debit.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_corner_radious_selected));
            money.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_corner_radious));
            pg[0] = Util.NAME_MONEY;
        });

        buttonPrint.setOnClickListener(view -> {

            for(int i = 0; i < incrementValue[0]; i++) {

                e1.connectPrinter();

                Consolidated consolidated = new Consolidated();

                consolidated.setName(obj.getName());
                consolidated.setPg(pg[0]);
                consolidated.setAmount(i);
                consolidated.setPrice(obj.getPrice());
                consolidated.setDateTime(Util.getDateTimeNow());

                new ConsolidatedController(getContext()).save(consolidated);

                consolidateds.add(consolidated);

                consolidatedAdapterList.notifyDataSetChanged();

                /*
                String[] date = Util.getDateTimeNow().split(" ");

                settings.printText("Valido até " + date[0] + "\n", true, 12, "Esquerda");
                settings.printText(obj.getName(), true, 18, "Esquerda");
                settings.jumpLine();
                settings.cutPaper();
                 */

                Bitmap mBitmapDraw = Bitmap.createBitmap(800, 400, Bitmap.Config.ARGB_8888);

                Canvas mCanvasDraw = new Canvas(mBitmapDraw);

                Paint paint = new Paint();
                paint.setColor(getContext().getColor(R.color.color_coil_termic_printer));
                mCanvasDraw.drawPaint(paint);

                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Color.BLACK);
                paint.setTextSize(36);

                int xPos = 0;
                int yPos = 36;

                String[] date = Util.getDateTimeNow().split(" ");
                mCanvasDraw.drawText("Válido até " + date[0], xPos, yPos, paint);

                paint.setTextSize(96);
                yPos += 125;

                mCanvasDraw.drawText(obj.getName(), xPos, yPos, paint);

                //xPos = getRight() - 250;
                //yPos = 0;

                /*if(bitmap != null) {
                    Bitmap mBitmap = Util.setImageSizeColor(bitmap, 250, 250);
                    mCanvasDraw.drawBitmap(Util.replaceColor(mBitmap), xPos, yPos, paint);
                }

                 */

                e1.printImageElgin(mBitmapDraw);
                settings.cutPaper();
            }

            //e1.printerStopElgin();

            dialog.dismiss();

        });

        buttonCancel.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
        dialog.getWindow().setLayout
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
}
