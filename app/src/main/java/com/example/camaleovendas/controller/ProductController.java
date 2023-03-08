package com.example.camaleovendas.controller;

import android.content.ContentValues;
import android.content.Context;

import com.example.camaleovendas.datamodel.ProductDataModel;
import com.example.camaleovendas.datasource.DataSource;
import com.example.camaleovendas.model.Product;

import java.util.List;

public class ProductController extends DataSource implements CrudController<Product>{

    ContentValues values;

    public ProductController(Context context) {
        super(context);
    }

    @Override
    public boolean save(Product obj) {
        values = new ContentValues();
        values.put(ProductDataModel.getName(), obj.getName());
        values.put(ProductDataModel.getPrice(), obj.getPrice());
        values.put(ProductDataModel.getAmount(), obj.getAmount());

        return insert(ProductDataModel.getTable(), values);
    }

    @Override
    public boolean update(Product obj) {
        values = new ContentValues();
        values.put(ProductDataModel.getId(), obj.getId());
        values.put(ProductDataModel.getName(), obj.getName());
        values.put(ProductDataModel.getPrice(), obj.getPrice());
        values.put(ProductDataModel.getAmount(), obj.getAmount());

        return update(ProductDataModel.getTable(), values);
    }

    @Override
    public boolean delete(Product obj) {
        return delete(ProductDataModel.getTable(), obj.getId());
    }

    @Override
    public List<Product> getAll() {
        return getProduct();
    }

    @Override
    public Product getByID(int id) {
        return null;
    }
}
