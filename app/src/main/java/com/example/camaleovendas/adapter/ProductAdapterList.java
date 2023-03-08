package com.example.camaleovendas.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.camaleovendas.R;
import com.example.camaleovendas.controller.ProductController;
import com.example.camaleovendas.model.Product;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class ProductAdapterList extends RecyclerView.Adapter<ProductAdapterList.VewHolder> {

    public List<Product> list;
    private final SparseBooleanArray selected;
    private Context context;
    private OnClickListener onClickListener = null;


    public ProductAdapterList(Context context, List<Product> list) {
        this.context = context;
        this.list = list;
        selected = new SparseBooleanArray();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    @NonNull
    @Override
    public ProductAdapterList.VewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.adapter_product, parent, false);

        return new VewHolder(contactView);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull VewHolder holder, int position) {

        Locale ptBr = new Locale("pt", "BR");

        Product product= list.get(position);

        holder.name.setText(product.getName());

        holder.price.setText(NumberFormat.getCurrencyInstance(ptBr).format(product.getPrice()));

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
        Product item = list.get(position);

        if (new ProductController(context).delete(item)) {
            this.list.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addItem(int position) {
        new Product();
        Product item = list.get(position);

        this.list.add(item);
        notifyItemInserted(position);
    }

    public interface OnClickListener {
        void onItemClick(View view, Product obj, int pos);

        void onItemLongClick(View view, Product obj, int pos);
    }

    public static class VewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout layout;
        private final TextView name;
        private final TextView price;

        public VewHolder(@NonNull View itemView) {
            super(itemView);


            name = itemView.findViewById(R.id.adapter_product_name);
            price = itemView.findViewById(R.id.adapter_product_price);
            layout = itemView.findViewById(R.id.adapter_product_layout);
        }
    }
}
