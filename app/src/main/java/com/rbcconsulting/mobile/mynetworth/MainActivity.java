package com.rbcconsulting.mobile.mynetworth;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.rbcconsulting.mobile.mynetworth.databaseHelper.AssetHelper;
import com.rbcconsulting.mobile.mynetworth.databaseHelper.DatabaseHelper;
import com.rbcconsulting.mobile.mynetworth.databaseHelper.DebtHelper;
import com.rbcconsulting.mobile.mynetworth.model.Asset;
import com.rbcconsulting.mobile.mynetworth.model.Debt;
import com.rbcconsulting.mobile.mynetworth.utility.AppUtility;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(this);

        //Load the total Asset amount
        loadAssetAmount();
        loadDebtAmount();
        loadNetWorthAmount();
    }

    private void loadAssetAmount() {
        AssetHelper assetHelper = new AssetHelper(this.databaseHelper);

        //TODO remove this snippet when deploying
        assetHelper.addAsset(new Asset("savings", 120.0, "remarks", "2017-08-01"));
        assetHelper.addAsset(new Asset("uitf", 320.0, "remarks", "2017-08-01"));
        assetHelper.addAsset(new Asset("uitf", 420.0, "remarks", "2016-08-01"));
        assetHelper.addAsset(new Asset("uitf", 2320.42, "remarks", "2017-08-01"));
        assetHelper.addAsset(new Asset("uitf", 25230.3232, "remarks", "2017-08-01"));

        setContentView(R.layout.activity_main);

        //TODO change the hardcoded date in the parameter
        double assetAmount = assetHelper.getTotalAmountByDate(true, "2017-08-01").getDouble(0);

        String str_AssetAmount = String.valueOf(AppUtility.roundNearest2(assetAmount));
        TextView tv_lbl_AssetAmount = (TextView) findViewById(R.id.tv_lbl_AssetAmount);
        tv_lbl_AssetAmount.setText(str_AssetAmount);
    }

    private void loadDebtAmount() {
        DebtHelper debtHelper = new DebtHelper(this.databaseHelper);

        //TODO remove this snippet when deploying
        debtHelper.addDebt(new Debt("loan", 999.32, "remarks", "2017-08-01"));
        debtHelper.addDebt(new Debt("emergency", 123.22, "remarks", "2017-08-01"));
        debtHelper.addDebt(new Debt("emergency", 7799.23, "remarks", "2017-08-01"));

        //TODO change the hardcoded date in the parameter
        double debtAmount = debtHelper.getTotalAmountByDate(true, "2017-08-01").getDouble(0);

        String str_DebtAmount = String.valueOf(AppUtility.roundNearest2(debtAmount));
        TextView tv_lbl_DebtAmount = (TextView) findViewById(R.id.tv_lbl_DebtAmount);
        tv_lbl_DebtAmount.setText(str_DebtAmount);
    }

    private void loadNetWorthAmount() {
        String str_DebtAmount = String.valueOf(((TextView)
                findViewById(R.id.tv_lbl_DebtAmount)).getText());
        String str_AssetAmount = String.valueOf(((TextView)
                findViewById(R.id.tv_lbl_AssetAmount)).getText());

        double netWorthAmount = Double.valueOf(str_AssetAmount) - Double.valueOf(str_DebtAmount);

        String str_NetWorthAmount = String.valueOf(netWorthAmount);
        TextView tv_lbl_NetWorthAmount = (TextView) findViewById(R.id.tv_lbl_NetWorthAmount);
        tv_lbl_NetWorthAmount.setText(str_NetWorthAmount);
    }
}
