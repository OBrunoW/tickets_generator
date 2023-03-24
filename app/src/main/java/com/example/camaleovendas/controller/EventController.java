package com.example.camaleovendas.controller;

import android.content.ContentValues;
import android.content.Context;

import com.example.camaleovendas.datamodel.EventDataModel;
import com.example.camaleovendas.datamodel.ProductDataModel;
import com.example.camaleovendas.datasource.DataSource;
import com.example.camaleovendas.model.Event;
import com.example.camaleovendas.model.Product;

import java.util.List;

public class EventController extends DataSource implements CrudController<Event>{

    ContentValues values;

    public EventController(Context context) {
        super(context);
    }

    @Override
    public boolean save(Event obj) {
        values = new ContentValues();
        values.put(EventDataModel.getName(), obj.getName());
        values.put(EventDataModel.getImage(), obj.getImage());

        return insert(EventDataModel.getTable(), values);
    }

    @Override
    public boolean update(Event obj) {
        values = new ContentValues();
        values.put(EventDataModel.getId(), obj.getId());
        values.put(EventDataModel.getName(), obj.getName());
        values.put(EventDataModel.getImage(), obj.getImage());

        return update(EventDataModel.getTable(), values);
    }

    @Override
    public boolean delete(Event obj) {
        return delete(EventDataModel.getTable(), obj.getId());
    }

    @Override
    public List<Event> getAll() {
        return null;
    }

    @Override
    public Event getByID(int id) {
        return null;
    }
}
