package com.rbcconsulting.mobile.mynetworth.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ralph on 11/08/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase dbHelper;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_CONSTANTS.DATABASE_NAME, null, DATABASE_CONSTANTS.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CONSTANTS.SQL_CREATE_TBL_ASSETS);
        db.execSQL(DATABASE_CONSTANTS.SQL_CREATE_TBL_DEBTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DATABASE_CONSTANTS.SQL_DROP_TBL_ASSETS);
        db.execSQL(DATABASE_CONSTANTS.SQL_DROP_TBL_DEBTS);
        onCreate(db);
    }

    public long createRecord(String tableName, ContentValues contentValues) {
        dbHelper = this.getWritableDatabase();
        return dbHelper.insert(tableName, null, contentValues);
    }

    public Cursor getRecord(String tableName, String[] columns, String whereClause,
                            String[] whereValues) {
        return dbHelper.query(tableName, columns, whereClause, whereValues, null, null, null);
    }

    public Cursor getAggregatedRecords(String tableName, String[] columns, String whereClause,
                                       String[] whereValues, String groupBy, String having) {
        return dbHelper.query(tableName, columns, whereClause, whereValues, groupBy, having, null);
    }

    public int updateRecord(String tableName, String whereClause, String[] whereValues,
                            ContentValues contentValues) {
        return dbHelper.update(tableName, contentValues, whereClause, whereValues);
    }

    public int deleteRecord(String tableName, String whereClause, String[] whereValues) {
        dbHelper = this.getWritableDatabase();
        return dbHelper.delete(tableName, whereClause, whereValues);
    }

    public int countRecords(String tableName, String whereClause, String[] whereValues) {
        dbHelper = this.getReadableDatabase();
        return dbHelper.query(tableName,
                new String[]{"1"},
                whereClause,
                whereValues,
                null, null, null).getCount();
    }

}