package com.rbcconsulting.mobile.mynetworth.databaseHelper;

/**
 * Created by Ralph on 11/08/2017.
 */

public class DATABASE_CONSTANTS {

    public static final String DATABASE_NAME = "mynetworth.db";

    //Table names
    public static final String TBL_ASSETS = "assets";
    public static final String TBL_DEBTS = "debts";

    //Column names
    public static final String _ASSETS_ID = "asset_id";
    public static final String _ASSETS_TYPE = "type";
    public static final String _ASSETS_AMOUNT = "amount";
    public static final String _ASSETS_REMARKS = "remarks";
    public static final String _ASSETS_DATE = "date";


    //Create Table SQL
    public static final String SQL_CREATE_TBL_ASSETS = "CREATE TABLE " +
            TBL_ASSETS + " ( " + _ASSETS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            _ASSETS_TYPE + " TEXT, " +
            _ASSETS_AMOUNT + " REAL, " +
            _ASSETS_REMARKS + " TEXT, " +
            _ASSETS_DATE + " TEXT) ";

    //Drop Table SQL
    public static final String SQL_DROP_TBL_ASSETS = " DROP TABLE IF EXISTS " +
            TBL_ASSETS;

    public static final int DATABASE_VERSION = 1;
}
