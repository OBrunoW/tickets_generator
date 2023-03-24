package com.example.camaleovendas.datamodel;

public class EventDataModel {

    public static final String TABLE = "Events";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String IMAGE = "image";

    private static String query;

    public static String createTable() {

        query = "CREATE TABLE " + TABLE;
        query += " (";
        query += ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        query += NAME + " TEXT, ";
        query += IMAGE + " BLOB ";
        query += ") ";

        return query;
    }

    public static String getTable() {return TABLE;}
    public static String getId() {return ID;}
    public static String getName() {return NAME;}
    public static String getImage() {return IMAGE;}

}
