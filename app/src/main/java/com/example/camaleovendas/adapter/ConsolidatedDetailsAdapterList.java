package com.example.camaleovendas.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.camaleovendas.R;
import com.example.camaleovendas.controller.ConsolidatedController;
import com.example.camaleovendas.model.Consolidated;
import com.example.camaleovendas.model.Product;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class ConsolidatedDetailsAdapterList extends RecyclerView.Adapter<ConsolidatedDetailsAdapterList.VewHolder> {

    public List<Product> list;
    private Context context;
    private ConsolidatedController controller;


    public ConsolidatedDetailsAdapterList(Context context, List<Product> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ConsolidatedDetailsAdapterList.VewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.adapter_consolidated_detail, parent, false);
        controller = new ConsolidatedController(context);

        return new VewHolder(contactView);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull VewHolder holder, int position) {

        Locale ptBr = new Locale("pt", "BR");

        int countPix = 0;
        int countCredit = 0;
        int countDebit = 0;
        int countMoney = 0;

        double pix = 0;
        double credit = 0;
        double debit = 0;
        double money = 0;


        Product product = list.get(position);

        List<Consolidated> consolidateds =
                controller.getConsolidatedsByProdCode(product.getName());

        holder.prodName.setText(product.getName());

        for(Consolidated obj : consolidateds) {

            if(Objects.equals(obj.getPg(), "Pix")){
                countPix++;
                pix += obj.getPrice();
            }
            if(Objects.equals(obj.getPg(), "Crédito")){
                countCredit++;
                credit += obj.getPrice();
            }
            if(Objects.equals(obj.getPg(), "Débito")){
                countDebit++;
                debit += obj.getPrice();
            }
            if(Objects.equals(obj.getPg(), "Dinheiro")){
                countMoney++;
                money += obj.getPrice();
            }
        }

        holder.pixUn.setText(countPix + " un.");
        holder.creditUn.setText( countCredit + " un.");
        holder.debitUn.setText( countDebit + " un.");
        holder.moneyUn.setText(countMoney + " un.");

        int resultUn = countPix + countCredit + countDebit + countMoney;
        holder.unAll.setText(resultUn + " un.");

        holder.pixPrice.setText(NumberFormat.getCurrencyInstance(ptBr).format(pix));
        holder.creditPrice.setText(NumberFormat.getCurrencyInstance(ptBr).format(credit));
        holder.debitPrice.setText(NumberFormat.getCurrencyInstance(ptBr).format(debit));
        holder.moneyPrice.setText(NumberFormat.getCurrencyInstance(ptBr).format(money));
        holder.priceAll.setText(NumberFormat
                .getCurrencyInstance(ptBr)
                .format(money + debit + credit + pix));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class VewHolder extends RecyclerView.ViewHolder {

        private final TextView prodName;
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
