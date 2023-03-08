package com.example.camaleovendas.datamodel;

public class ProductDataModel {

    public static final String TABLE = "Products";
    private static final String ID = "id";
    private static final String AMOUNT = "amount";
    private static final String NAME = "name";
    private static final String PRICE = "price";

    private static String query;

    public static String createTable() {

        query = "CREATE TABLE " + TABLE;
        query += " (";
        query += ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        query += AMOUNT + " INTEGER, ";
        query += NAME + " TEXT, ";
        query += PRICE + " REAL ";
        query += ") ";

        return query;
    }

    public static String getTable() {return TABLE;}
    public static String getId() {return ID;}
    public static String getAmount() {return AMOUNT;}
    public static String getName() {return NAME;}
    public static String getPrice() {return PRICE;}

}
