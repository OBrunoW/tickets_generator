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
import com.example.camaleovendas.controller.ProductController;
import com.example.camaleovendas.model.Consolidated;
import com.example.camaleovendas.model.Product;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class ConsolidatedAdapterList extends RecyclerView.Adapter<ConsolidatedAdapterList.VewHolder> {

    public List<Consolidated> list;
    private final SparseBooleanArray selected;
    private Context context;
    private OnClickListener onClickListener = null;
    private static final String namePgPix = "Pix";
    private static final String namePgMoney = "Dinheiro";
    private static final String namePgCard = "Cartão";


    public ConsolidatedAdapterList(Context context, List<Consolidated> list) {
        this.context = context;
        this.list = list;
        selected = new SparseBooleanArray();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    @NonNull
    @Override
    public ConsolidatedAdapterList.VewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.adapter_consolidated, parent, false);

        return new VewHolder(contactView);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull VewHolder holder, int position) {

        Locale ptBr = new Locale("pt", "BR");

        Consolidated product= list.get(position);

        holder.name.setText(product.getName());

        holder.price.setText(NumberFormat.getCurrencyInstance(ptBr).format(product.getPrice()));

        switch (product.getPg()) {
            case namePgPix :
                holder.icon.setImageDrawable(context.getDrawable(R.drawable.round_pix_24));
                break;

            case namePgCard :
                holder.icon.setImageDrawable(context.getDrawable(R.drawable.round_payment_24));
                break;

            case namePgMoney :
                holder.icon.setImageDrawable(context.getDrawable(R.drawable.round_attach_money_24));
                break;

        }

        holder.dateTime.setText(product.getDateTime());

        holder.layout.setActivated(selected.get(position, false));

        holder.layout.setOnClickListener(v -> {
            if (onClickListener == null) return;
            onClickListener.onItemClick(v, product, position);
        });


        holder.layout.setOnLongClickListener(v -> {
            if (onClickListener == null) return false;
            onClickListener.onItemLongClick(v, product, position);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void deleteItem(int position) {
        new Product();
        Consolidated item = list.get(position);

        if (new ConsolidatedController(context).delete(item)) {
            this.list.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addItem(int position) {
        new Product();
        Consolidated item = list.get(position);

        this.list.add(item);
        notifyItemInserted(position);
    }

    public interface OnClickListener {
        void onItemClick(View view, Consolidated obj, int pos);

        void onItemLongClick(View view, Consolidated obj, int pos);
    }

    public static class VewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout layout;
        private final TextView name;
        private final TextView price;
        private final TextView dateTime;
        private final ImageView icon;

        public VewHolder(@NonNull View itemView) {
            super(itemView);


            name = itemView.findViewById(R.id.adapter_consolidated_name);
            price = itemView.findViewById(R.id.adapter_consolidated_price);
            dateTime = itemView.findViewById(R.id.adapter_consolidated_date_time);
            icon = itemView.findViewById(R.id.consolidated_icon);
            layout = itemView.findViewById(R.id.adapter_consolidated_layout);
        }
    }
}
