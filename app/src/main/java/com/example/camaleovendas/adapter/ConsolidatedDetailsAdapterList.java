package com.example.camaleovendas.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.camaleovendas.R;
import com.example.camaleovendas.controller.ConsolidatedController;
import com.example.camaleovendas.controller.ProductController;
import com.example.camaleovendas.model.Consolidated;
import com.example.camaleovendas.model.Product;
import com.example.camaleovendas.util.Util;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class ConsolidatedDetailsAdapterList extends RecyclerView.Adapter<ConsolidatedDetailsAdapterList.VewHolder> {

    private final List<Consolidated> consolidateds;
    private final List<String> names;
    private Context context;


    public ConsolidatedDetailsAdapterList(Context context) {
        this.context = context;
        this.consolidateds = new ConsolidatedController(context).getAll();
        this.names = new ArrayList<>();

        for(Consolidated obj : consolidateds)
            if(!names.contains(obj.getName())) names.add(obj.getName());
    }

    @NonNull
    @Override
    public ConsolidatedDetailsAdapterList.VewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.adapter_consolidated_detail, parent, false);

        return new VewHolder(contactView);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull VewHolder holder, int position) {

        int countPix = 0;
        int countCredit = 0;
        int countDebit = 0;
        int countMoney = 0;

        double pix = 0;
        double credit = 0;
        double debit = 0;
        double money = 0;

        String name = names.get(position);
        Product product = new ProductController(context).getProductName(name);

        if(product.getName() != null) holder.nameStatus.setVisibility(View.GONE);
        else holder.nameStatus.setVisibility(View.VISIBLE);

        holder.prodName.setText(name);

        for(Consolidated obj : consolidateds) {

            if(Objects.equals(obj.getName(), name)) {

                if (Objects.equals(obj.getPg(), Util.NAME_PIX)) {
                    countPix++;
                    pix += obj.getPrice();
                }
                if (Objects.equals(obj.getPg(), Util.NAME_CREDIT)) {
                    countCredit++;
                    credit += obj.getPrice();
                }
                if (Objects.equals(obj.getPg(), Util.NAME_DEBIT)) {
                    countDebit++;
                    debit += obj.getPrice();
                }
                if (Objects.equals(obj.getPg(), Util.NAME_MONEY)) {
                    countMoney++;
                    money += obj.getPrice();
                }
            }
        }

        holder.pixUn.setText(countPix + " un.");
        holder.creditUn.setText( countCredit + " un.");
        holder.debitUn.setText( countDebit + " un.");
        holder.moneyUn.setText(countMoney + " un.");

        int resultUn = countPix + countCredit + countDebit + countMoney;
        holder.unAll.setText(resultUn + " un.");

        holder.pixPrice.setText(NumberFormat.getCurrencyInstance(Util.ptBr()).format(pix));
        holder.creditPrice.setText(NumberFormat.getCurrencyInstance(Util.ptBr()).format(credit));
        holder.debitPrice.setText(NumberFormat.getCurrencyInstance(Util.ptBr()).format(debit));
        holder.moneyPrice.setText(NumberFormat.getCurrencyInstance(Util.ptBr()).format(money));

        double resultPrice = money + debit + credit + pix;
        holder.priceAll.setText(NumberFormat.getCurrencyInstance(Util.ptBr()).format(resultPrice));

    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public static class VewHolder extends RecyclerView.ViewHolder {

        private final TextView prodName;
        private final TextView nameStatus;
        private final TextView pixUn;
        private final TextView creditUn;
        private final TextView debitUn;
        private final TextView moneyUn;
        private final TextView pixPrice;
        private final TextView creditPrice;
        private final TextView debitPrice;
        private final TextView moneyPrice;
        private final TextView priceAll;
        private final TextView unAll;

        public VewHolder(@NonNull View itemView) {
            super(itemView);


            prodName = itemView.findViewById(R.id.adapter_detail_name);
            nameStatus = itemView.findViewById(R.id.adapter_detail_name_status);
            pixUn = itemView.findViewById(R.id.detail_un_pix);
            creditUn = itemView.findViewById(R.id.detail_un_credit);
            debitUn = itemView.findViewById(R.id.detail_un_debit);
            moneyUn = itemView.findViewById(R.id.detail_un_money);
            pixPrice = itemView.findViewById(R.id.detail_price_pix);
            creditPrice = itemView.findViewById(R.id.detail_price_credit);
            debitPrice = itemView.findViewById(R.id.detail_price_debit);
            moneyPrice = itemView.findViewById(R.id.detail_price_money);
            priceAll = itemView.findViewById(R.id.detail_price_all);
            unAll = itemView.findViewById(R.id.detail_un_all);
        }
    }
}
