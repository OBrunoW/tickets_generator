package com.example.camaleovendas.controller;

import android.content.ContentValues;
import android.content.Context;

import com.example.camaleovendas.datamodel.ConsolidatedDataModel;
import com.example.camaleovendas.datamodel.ProductDataModel;
import com.example.camaleovendas.datasource.DataSource;
import com.example.camaleovendas.model.Consolidated;

import java.util.List;

public class ConsolidatedController extends DataSource implements CrudController<Consolidated>{

    ContentValues values;

    public ConsolidatedController(Context context) {
        super(context);
    }

    @Override
    public boolean save(Consolidated obj) {
        values = new ContentValues();
        values.put(ConsolidatedDataModel.getPg(), obj.getPg());
        values.put(ConsolidatedDataModel.getDateTime(), obj.getDateTime());
        values.put(ConsolidatedDataModel.getPrice(), obj.getPrice());
        values.put(ConsolidatedDataModel.getAmount(), obj.getAmount());
        values.put(ConsolidatedDataModel.getName(), obj.getName());

        return insert(ConsolidatedDataModel.getTable(), values);
    }

    @Override
    public boolean update(Consolidated obj) {
        values = new ContentValues();
        values.put(ConsolidatedDataModel.getId(), obj.getId());
        values.put(ConsolidatedDataModel.getPg(), obj.getPg());
        values.put(ConsolidatedDataModel.getDateTime(), obj.getDateTime());
        values.put(ConsolidatedDataModel.getPrice(), obj.getPrice());
        values.put(ConsolidatedDataModel.getAmount(), obj.getAmount());
        values.put(ConsolidatedDataModel.getName(), obj.getName());

        return update(ConsolidatedDataModel.getTable(), values);
    }

    @Override
    public boolean delete(Consolidated obj) {
        return delete(obj);
    }

    @Override
    public List<Consolidated> getAll() {
        return getConsolidateds();
    }

    @Override
    public Consolidated getByID(int id) {
        return null;
    }
}
