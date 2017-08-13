package com.rbcconsulting.mobile.mynetworth.databaseHelper;

import android.content.ContentValues;
import android.database.Cursor;

import com.rbcconsulting.mobile.mynetworth.model.Asset;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ralph on 11/08/2017.
 */

public class AssetHelper {

    private DatabaseHelper dbHelper;

    public AssetHelper(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public Asset addAsset(Asset asset) {
        ContentValues contentValues = new ContentValues();
        Cursor assetCursor;
        Asset createdAsset;

        contentValues.put(DATABASE_CONSTANTS._ASSETS_TYPE, asset.getType());
        contentValues.put(DATABASE_CONSTANTS._ASSETS_AMOUNT, asset.getAmount());
        contentValues.put(DATABASE_CONSTANTS._ASSETS_REMARKS, asset.getRemarks());
        contentValues.put(DATABASE_CONSTANTS._ASSETS_DATE, asset.getDate());

        long id = dbHelper.createRecord(DATABASE_CONSTANTS.TBL_ASSETS, contentValues);

        assetCursor = dbHelper.getRecord(DATABASE_CONSTANTS.TBL_ASSETS, null,
                DATABASE_CONSTANTS._ASSETS_ID + "=?",
                new String[]{String.valueOf(id)});

        assetCursor.moveToFirst();

        createdAsset = new Asset(assetCursor.getInt(0),
                assetCursor.getString(1),
                assetCursor.getDouble(2),
                assetCursor.getString(3),
                assetCursor.getString(4));

        assetCursor.close();

        return createdAsset;
    }

    public int deleteAsset(Asset asset) {
        return dbHelper.deleteRecord(DATABASE_CONSTANTS.TBL_ASSETS,
                DATABASE_CONSTANTS._ASSETS_ID + "=?",
                new String[]{String.valueOf(asset.getAssetId())});
    }

    public Asset getAsset(Asset asset) {
        Cursor assetCursor;
        Asset createdAsset;

        assetCursor = dbHelper.getRecord(DATABASE_CONSTANTS.TBL_ASSETS, null,
                DATABASE_CONSTANTS._ASSETS_ID + "=?",
                new String[]{String.valueOf(asset.getAssetId())});

        assetCursor.moveToFirst();
        createdAsset = new Asset(assetCursor.getInt(0),
                assetCursor.getString(1),
                assetCursor.getDouble(2),
                assetCursor.getString(3),
                assetCursor.getString(4));

        assetCursor.close();

        return createdAsset;
    }

    public List<Asset> getAllAssets() {
        Cursor assetCursor;
        List<Asset> assets = new ArrayList<Asset>();

        assetCursor = dbHelper.getRecord(DATABASE_CONSTANTS.TBL_ASSETS, null, null, null);

        try {
            while (assetCursor.moveToNext()) {
                assets.add(new Asset(assetCursor.getInt(0),
                        assetCursor.getString(1),
                        assetCursor.getDouble(2),
                        assetCursor.getString(3),
                        assetCursor.getString(4)));
            }
        } finally {
            assetCursor.close();
        }

        return assets;
    }

    public int countAllAssets() {
        return dbHelper.countRecords(DATABASE_CONSTANTS.TBL_ASSETS, null, null);
    }

    public int countAllAssetsWhere(String[] columns, String[] whereValues) {
        StringBuilder whereClause = new StringBuilder("");
        for (int ctr = 0; ctr < columns.length; ctr++) {
            whereClause.append(whereClause);
            whereClause.append(columns[ctr]);
            whereClause.append("=?");
            if (columns.length - 1 != ctr) {
                whereClause.append(" AND ");
            }
        }
        return dbHelper.countRecords(DATABASE_CONSTANTS.TBL_ASSETS, whereClause.toString(), whereValues);
    }

    public int updateAsset(Asset asset) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DATABASE_CONSTANTS._ASSETS_TYPE, asset.getType());
        contentValues.put(DATABASE_CONSTANTS._ASSETS_AMOUNT, asset.getAmount());
        contentValues.put(DATABASE_CONSTANTS._ASSETS_DATE, asset.getDate());
        contentValues.put(DATABASE_CONSTANTS._ASSETS_REMARKS, asset.getRemarks());

        return dbHelper.updateRecord(DATABASE_CONSTANTS.TBL_ASSETS,
                DATABASE_CONSTANTS._ASSETS_ID + "=?",
                new String[]{String.valueOf(asset.getAssetId())},
                contentValues
        );
    }
}
