package com.rbcconsulting.mobile.mynetworth.model;

/**
 * Created by Ralph on 11/08/2017.
 */

public class Asset {

    private int assetId;
    private String type;
    private double amount;
    private String remarks;
    private String date;

    public Asset(int assetId, String type, double amount, String remarks, String date) {
        this.assetId = assetId;
        this.type = type;
        this.amount = amount;
        this.remarks = remarks;
        this.date = date;
    }

    public Asset(String type, double amount, String remarks, String date) {
        this.type = type;
        this.amount = amount;
        this.remarks = remarks;
        this.date = date;
    }

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Asset: assetId=" + assetId
                + ", type=" + type
                + ", amount=" + amount
                + ", remarks=" + remarks
                + ", date=" + date;
    }
}
