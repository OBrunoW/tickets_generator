package com.example.camaleovendas.datamodel;

public class ConsolidatedDataModel {

    public static final String TABLE = "Consolidated";
    private static final String ID = "id";
    private static final String AMOUNT = "amount";
    private static final String PRICE = "price";
    private static final String NAME = "name";
    private static final String PG = "pg";
    private static final String DATE_TIME = "date_time";

    private static String query;

    public static String createTable() {

        query = "CREATE TABLE " + TABLE;
        query += " (";
        query += ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        query += AMOUNT + " INTEGER, ";
        query += PRICE + " REAL, ";
        query += PG + " TEXT, ";
        query += NAME + " TEXT, ";
        query += DATE_TIME + " TEXT ";
        query += ") ";

        return query;
    }

    public static String getTable() {return TABLE;}
    public static String getId() {return ID;}
    public static String getAmount() {return AMOUNT;}
    public static String getDateTime() {return DATE_TIME;}
    public static String getPg() {return PG;}
    public static String getPrice() {return PRICE;}
    public static String getName() {return NAME;}

}
