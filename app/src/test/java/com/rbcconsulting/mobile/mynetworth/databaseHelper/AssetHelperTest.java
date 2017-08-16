package com.rbcconsulting.mobile.mynetworth.databaseHelper;

import com.rbcconsulting.mobile.mynetworth.BuildConfig;
import com.rbcconsulting.mobile.mynetworth.model.Asset;
import com.rbcconsulting.mobile.mynetworth.utility.AppUtility;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;

/**
 * Created by Ralph on 11/08/2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class AssetHelperTest {

    private DatabaseHelper dbHelper;

    @Before
    public void setUp() throws Exception {
        dbHelper = new DatabaseHelper(RuntimeEnvironment.application);
    }

    @Test
    public void testAddAsset() {
        AssetHelper assetHelper = new AssetHelper(dbHelper);
        Asset createdAsset;

        createdAsset = assetHelper.addAsset(new Asset("savings", 10.0, "remarks", "2017-08-01"));

        //check if the inserted record has the correct values
        assertEquals("savings", createdAsset.getType());
        assertEquals(10.0, createdAsset.getAmount());
        assertEquals("remarks", createdAsset.getRemarks());
        assertEquals("2017-08-01", createdAsset.getDate());
    }

    @Test
    public void testDeleteAsset() {
        AssetHelper assetHelper = new AssetHelper(dbHelper);
        Asset createdAsset;

        createdAsset = assetHelper.addAsset(new Asset("savings", 20.0, "remarks", "2017-08-01"));
        assetHelper.addAsset(new Asset("savings", 20.0, "remarks", "2011-08-01"));
        assetHelper.addAsset(new Asset("savings", 20.0, "remarks", "2013-08-01"));

        //check if only 1 record was deleted (record type='savings' and date='2017-08-01')
        assertEquals(1, assetHelper.deleteAsset(createdAsset));
    }

    @Test
    public void testCountAllAssets() {
        Asset asset = new Asset("savings", 20.0, "remarks", "2017-08-01");
        AssetHelper assetHelper = new AssetHelper(dbHelper);

        assetHelper.addAsset(asset);
        assetHelper.addAsset(asset);
        assetHelper.addAsset(asset);
        assetHelper.addAsset(asset);

        //check if the table contains exactly 4 records
        assertEquals(4, assetHelper.countAllAssets());
    }

    @Test
    public void testCountAllAssetsWhere() {
        Asset asset = new Asset("savings", 20.0, "remarks", "2017-08-01");
        AssetHelper assetHelper = new AssetHelper(dbHelper);

        assetHelper.addAsset(asset);
        assetHelper.addAsset(asset);
        assetHelper.addAsset(asset);
        assetHelper.addAsset(asset);
        assetHelper.addAsset(new Asset("uitf", 20.0, "remarks", "2016-08-01"));
        assetHelper.addAsset(new Asset("uitf", 20.0, "remarks", "2016-08-01"));
        assetHelper.addAsset(new Asset("uitf", 20.0, "remarks", "2016-08-01"));
        assetHelper.addAsset(new Asset("uitf", 20.0, "remarks", "2016-08-01"));

        //check if table contains only 4 records (record type = 'uitf')
        assertEquals(4,
                assetHelper.countAllAssetsWhere(
                        new String[]{DATABASE_CONSTANTS._ASSETS_TYPE},
                        new String[]{"uitf"}));
    }

    @Test
    public void testUpdateAsset() {
        Asset asset = new Asset("savings", 20.0, "remarks", "2017-08-01");
        AssetHelper assetHelper = new AssetHelper(dbHelper);
        Asset updateAsset;

        updateAsset = assetHelper.addAsset(asset);
        assetHelper.addAsset(new Asset("uitf", 20.0, "remarks", "2016-08-01"));
        assetHelper.addAsset(new Asset("uitf", 20.0, "remarks", "2016-08-01"));
        assetHelper.addAsset(new Asset("uitf", 20.0, "remarks", "2016-08-01"));
        assetHelper.addAsset(new Asset("uitf", 20.0, "remarks", "2016-08-01"));

        updateAsset.setType("mutual funds");
        updateAsset.setRemarks("No remarks");
        updateAsset.setDate("1990-01-01");
        updateAsset.setAmount(100.0);

        //check if only 1 record was updated (previous record type='savings')
        assertEquals(1, assetHelper.updateAsset(updateAsset));

        //check if the record was properly updated and has new values
        assertEquals(updateAsset.getType(), assetHelper.getAsset(updateAsset).getType());
        assertEquals(updateAsset.getAmount(), assetHelper.getAsset(updateAsset).getAmount());
        assertEquals(updateAsset.getRemarks(), assetHelper.getAsset(updateAsset).getRemarks());
        assertEquals(updateAsset.getDate(), assetHelper.getAsset(updateAsset).getDate());

        //check if the record does not have the old values
        assertNotSame(asset.getType(), assetHelper.getAsset(updateAsset).getType());
        assertNotSame(asset.getAmount(), assetHelper.getAsset(updateAsset).getAmount());
        assertNotSame(asset.getRemarks(), assetHelper.getAsset(updateAsset).getRemarks());
        assertNotSame(asset.getDate(), assetHelper.getAsset(updateAsset).getDate());
    }

    @Test
    public void testGetAsset() {
        AssetHelper assetHelper = new AssetHelper(dbHelper);
        int id = assetHelper.addAsset(new Asset("savings", 20.0, "remarks", "2016-08-01"))
                .getAssetId();
        assetHelper.addAsset(new Asset("uitf", 20.0, "remarks", "2016-08-01"));

        //check if the record exists in the table (type='savings')
        assertEquals(id, assetHelper.getAsset(new Asset(id, null, 0.0, null, null)).getAssetId());
    }

    @Test
    public void testGetAllAssets() {
        AssetHelper assetHelper = new AssetHelper(dbHelper);
        List<Asset> createdAssets = new ArrayList<Asset>();

        createdAssets.add(new Asset("uitf", 20.0, "remarks", "2016-08-01"));
        createdAssets.add(new Asset("savings", 20.0, "remarks", "2016-08-01"));
        createdAssets.add(new Asset("mutual fund", 20.0, "remarks", "2016-08-01"));
        createdAssets.add(new Asset("stocks", 20.0, "remarks", "2016-08-01"));

        for (Asset createdAsset : createdAssets) {
            assetHelper.addAsset(createdAsset);
        }

        //check if all records inserted were all selected
        assertEquals(assetHelper.getAllAssets().size(), createdAssets.size());
    }

    @Test
    public void testGetTotalAssetsAmount() {
        AssetHelper assetHelper = new AssetHelper(dbHelper);

        assetHelper.addAsset(new Asset("savings", 120.0, "remarks", "2017-08-01"));
        assetHelper.addAsset(new Asset("uitf", 320.0, "remarks", "2016-08-01"));
        assetHelper.addAsset(new Asset("uitf", 420.0, "remarks", "2016-08-01"));
        assetHelper.addAsset(new Asset("uitf", 2320.42, "remarks", "2016-08-01"));
        assetHelper.addAsset(new Asset("uitf", 25230.3232, "remarks", "2016-08-01"));

        //check if total amount of assets are correct
        assertEquals(28410.74, AppUtility.roundNearest2(assetHelper.getTotalAssetsAmount()));
    }

    @Test
    public void testGetTotalAmount() {
        AssetHelper assetHelper = new AssetHelper(dbHelper);

        assetHelper.addAsset(new Asset("savings", 120.0, "remarks", "2017-08-01"));
        assetHelper.addAsset(new Asset("uitf", 320.0, "remarks", "2016-08-01"));
        assetHelper.addAsset(new Asset("uitf", 420.0, "remarks", "2016-08-01"));
        assetHelper.addAsset(new Asset("uitf", 2320.42, "remarks", "2016-08-01"));
        assetHelper.addAsset(new Asset("uitf", 25230.3232, "remarks", "2016-08-01"));

        //check if total amount of assets are correct
        assertEquals(28410.74, AppUtility.roundNearest2(assetHelper.getTotalAmount().getDouble(0)));
    }

    @Test
    public void testGetTotalAmountByDate() {
        AssetHelper assetHelper = new AssetHelper(dbHelper);

        assetHelper.addAsset(new Asset("savings", 120.0, "remarks", "2017-08-01"));
        assetHelper.addAsset(new Asset("uitf", 320.0, "remarks", "2016-08-01"));
        assetHelper.addAsset(new Asset("uitf", 420.0, "remarks", "2016-08-01"));
        assetHelper.addAsset(new Asset("uitf", 2320.42, "remarks", "2016-08-01"));
        assetHelper.addAsset(new Asset("uitf", 25230.3232, "remarks", "2016-08-01"));

        double totalAmount = assetHelper.getTotalAmountByDate(true, "2017-08-01").getDouble(0);
        //check if total amount of assets are correct
        assertEquals(120.0, AppUtility.roundNearest2(totalAmount));
        //double totalAmount;
        totalAmount = assetHelper.getTotalAmountByDate(false, "2017-08-01").getDouble(0);
        //check if total amount of assets are correct
        assertEquals(120.0, AppUtility.roundNearest2(totalAmount));

        totalAmount = assetHelper.getTotalAmountByDate(true, "2016-08-01").getDouble(0);
        //check if total amount of assets are correct
        assertEquals(28290.74, AppUtility.roundNearest2(totalAmount));

        totalAmount = assetHelper.getTotalAmountByDate(false, "2016-08-01").getDouble(0);
        //check if total amount of assets are correct
        assertEquals(28290.74, AppUtility.roundNearest2(totalAmount));
    }
}
