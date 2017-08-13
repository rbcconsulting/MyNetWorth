package com.rbcconsulting.mobile.mynetworth.model;

/**
 * Created by Ralph on 13/08/2017.
 */

public class Debt {

    private int debtId;
    private String type;
    private double amount;
    private String remarks;
    private String date;

    public Debt(int debtId, String type, double amount, String remarks, String date) {
        this.debtId = debtId;
        this.type = type;
        this.amount = amount;
        this.remarks = remarks;
        this.date = date;
    }

    public Debt(String type, double amount, String remarks, String date) {
        this.type = type;
        this.amount = amount;
        this.remarks = remarks;
        this.date = date;
    }

    public int getDebtId() {
        return debtId;
    }

    public void setDebtId(int debtId) {
        this.debtId = debtId;
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
        return "Debt: debtId=" + debtId
                + ", type=" + type
                + ", amount=" + amount
                + ", remarks=" + remarks
                + ", date=" + date;
    }
}
