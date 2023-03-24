package com.example.camaleovendas.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.camaleovendas.datamodel.ConsolidatedDataModel;
import com.example.camaleovendas.datamodel.EventDataModel;
import com.example.camaleovendas.datamodel.ProductDataModel;
import com.example.camaleovendas.model.Consolidated;
import com.example.camaleovendas.model.Event;
import com.example.camaleovendas.model.Product;

import java.util.ArrayList;
import java.util.List;

public class DataSource extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    private static final String DB_NAME = "vendas.sqlite";
    Cursor cursor;
    SQLiteDatabase db;


    public DataSource(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        db = getWritableDatabase();

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(EventDataModel.createTable());
        } catch (Exception ignored) {}

        try {
            db.execSQL(ProductDataModel.createTable());
        } catch (Exception ignored) {}

        try {
            db.execSQL(ConsolidatedDataModel.createTable());
        } catch (Exception ignored) {}

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    /*Table Create Products*/


    /**
     * CRUD Insert
     **/

    public boolean insert(String table, ContentValues data) {

        db = getWritableDatabase();

        boolean success;

        try {
            success = db.insert(table, null,
                    data) > 0;
        } catch (Exception e) {

            success = false;
        }

        db.close();

        return success;
    }

    /**
     * CRUD Reset Table
     **/

    public void resetTable(String table) {

        db.execSQL("DELETE FROM " + table);


    }

    /**
     * CRUD Remove Table
     **/

    public boolean delete(String table, int id) {

        boolean success;

        success = db.delete(table, "id=?",
                new String[]{Integer.toString(id)}) > 0;

        return success;
    }

    /**
     * CRUD Update table
     **/

    public boolean update(String table, ContentValues data) {

        db = getWritableDatabase();

        boolean success;

        int id = data.getAsInteger("id");

        success = db.update(table, data, "id=?",
                new String[]{Integer.toString(id)}) > 0;

        return success;
    }

    /**
     * CRUD Delete Table
     **/

    public void deleteTable(String table) {

        try {

            db.execSQL("DROP TABLE IF EXISTS " + table);

        } catch (Exception ignored) {

        }
    }

    /**
     * CRUD Create Table
     **/

    public void createTable(String queryCreateTable) {

        try {

            db.execSQL(queryCreateTable);

        } catch (SQLiteCantOpenDatabaseException ignored) {

        }
    }

    public List<Product> getProduct() {

        Product objProduct;

        db = this.getReadableDatabase();

        List<Product> retorno = new ArrayList<>();

        String sql = "SELECT * FROM " + ProductDataModel.getTable()
                + " ORDER BY "
                + ProductDataModel.getId()
                + " COLLATE NOCASE ASC";

        cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            do {

                objProduct = new Product();

                objProduct.setId(cursor.getInt(Integer.parseInt(String.valueOf
                        (cursor.getColumnIndex(ProductDataModel.getId())))));

                objProduct.setAmount(cursor.getInt(Integer.parseInt(String.valueOf
                        (cursor.getColumnIndex(ProductDataModel.getAmount())))));

                objProduct.setName(cursor.getString(Integer.parseInt(String.valueOf
                        (cursor.getColumnIndex(ProductDataModel.getName())))));

                objProduct.setPrice(cursor.getDouble(Integer.parseInt(String.valueOf
                        (cursor.getColumnIndex(ProductDataModel.getPrice())))));

                retorno.add(objProduct);

            } while (cursor.moveToNext());

        }

        cursor.close();

        db.close();

        return retorno;
    }

    public Product getProductName(String name) {

        Product objProduct = new Product();

        db = this.getReadableDatabase();

        String sql = "SELECT * FROM " + ProductDataModel.getTable()
                + " WHERE name = ?";

        cursor = db.rawQuery(sql, new String[]{name});

        if (cursor.moveToFirst()) {

            do {

                objProduct.setId(cursor.getInt(Integer.parseInt(String.valueOf
                        (cursor.getColumnIndex(ProductDataModel.getId())))));

                objProduct.setAmount(cursor.getInt(Integer.parseInt(String.valueOf
                        (cursor.getColumnIndex(ProductDataModel.getAmount())))));

                objProduct.setName(cursor.getString(Integer.parseInt(String.valueOf
                        (cursor.getColumnIndex(ProductDataModel.getName())))));

                objProduct.setPrice(cursor.getDouble(Integer.parseInt(String.valueOf
                        (cursor.getColumnIndex(ProductDataModel.getPrice())))));

            } while (cursor.moveToNext());

        }

        cursor.close();

        db.close();

        return objProduct;
    }

    public Event getEvent(int id) {

        Event objEvent = new Event();

        db = this.getReadableDatabase();

        String sql = "SELECT * FROM " + EventDataModel.getTable()
                + " WHERE id = ?";

        cursor = db.rawQuery(sql, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {

            do {

                objEvent.setId(cursor.getInt(Integer.parseInt(String.valueOf
                        (cursor.getColumnIndex(ProductDataModel.getId())))));

                objEvent.setName(cursor.getString(Integer.parseInt(String.valueOf
                        (cursor.getColumnIndex(EventDataModel.getName())))));

                objEvent.setImage(cursor.getBlob(Integer.parseInt(String.valueOf
                        (cursor.getColumnIndex(EventDataModel.getImage())))));

            } while (cursor.moveToNext());

        }

        cursor.close();

        db.close();

        return objEvent;
    }

    public List<Event> getEvent() {

        List<Event> values = new ArrayList<>();
        Event objEvent;

        db = this.getReadableDatabase();

        String sql = "SELECT * FROM " + EventDataModel.getTable()
                + " Order by "
                + EventDataModel.getId();

        cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            do {

                objEvent = new Event();

                objEvent.setId(cursor.getInt(Integer.parseInt(String.valueOf
                        (cursor.getColumnIndex(ProductDataModel.getId())))));

                objEvent.setName(cursor.getString(Integer.parseInt(String.valueOf
                        (cursor.getColumnIndex(EventDataModel.getName())))));

                objEvent.setImage(cursor.getBlob(Integer.parseInt(String.valueOf
                        (cursor.getColumnIndex(EventDataModel.getImage())))));

                values.add(objEvent);

            } while (cursor.moveToNext());

        }

        cursor.close();

        db.close();

        return values;
    }

    public List<Consolidated> getConsolidateds() {

        Consolidated objProduct;

        db = this.getReadableDatabase();

        List<Consolidated> retorno = new ArrayList<>();

        String sql = "SELECT * FROM " + ConsolidatedDataModel.getTable()
                + " ORDER BY "
                + ConsolidatedDataModel.getId()
                + " COLLATE NOCASE DESC";

        cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            do {

                objProduct = new Consolidated();

                objProduct.setId(cursor.getInt(Integer.parseInt(String.valueOf
                        (cursor.getColumnIndex(ConsolidatedDataModel.getId())))));

                objProduct.setAmount(cursor.getInt(Integer.parseInt(String.valueOf
                        (cursor.getColumnIndex(ConsolidatedDataModel.getAmount())))));

                objProduct.setName(cursor.getString(Integer.parseInt(String.valueOf
                        (cursor.getColumnIndex(ConsolidatedDataModel.getName())))));

                objProduct.setPrice(cursor.getDouble(Integer.parseInt(String.valueOf
                        (cursor.getColumnIndex(ConsolidatedDataModel.getPrice())))));

                objProduct.setPg(cursor.getString(Integer.parseInt(String.valueOf
                        (cursor.getColumnIndex(ConsolidatedDataModel.getPg())))));

                objProduct.setDateTime(cursor.getString(Integer.parseInt(String.valueOf
                        (cursor.getColumnIndex(ConsolidatedDataModel.getDateTime())))));

                retorno.add(objProduct);

            } while (cursor.moveToNext());

        }

        cursor.close();

        db.close();

        return retorno;
    }

}
