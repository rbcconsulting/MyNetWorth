package com.rbcconsulting.mobile.mynetworth.databaseHelper;

import com.rbcconsulting.mobile.mynetworth.BuildConfig;
import com.rbcconsulting.mobile.mynetworth.model.Debt;

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
public class DebtHelperTest {

    private DatabaseHelper dbHelper;

    @Before
    public void setUp() throws Exception {
        dbHelper = new DatabaseHelper(RuntimeEnvironment.application);
    }

    @Test
    public void testAddDebt() {
        DebtHelper debtHelper = new DebtHelper(dbHelper);
        Debt createdDebt;

        createdDebt = debtHelper.addDebt(new Debt("bank loan", 10.0, "remarks", "2017-08-01"));

        //check if the inserted record has the correct values
        assertEquals("bank loan", createdDebt.getType());
        assertEquals(10.0, createdDebt.getAmount());
        assertEquals("remarks", createdDebt.getRemarks());
        assertEquals("2017-08-01", createdDebt.getDate());
    }

    @Test
    public void testDeleteDebt() {
        DebtHelper debtHelper = new DebtHelper(dbHelper);
        Debt createdDebt;

        createdDebt = debtHelper.addDebt(new Debt("loan", 20.0, "remarks", "2017-08-01"));
        debtHelper.addDebt(new Debt("loan", 20.0, "remarks", "2011-08-01"));
        debtHelper.addDebt(new Debt("loan", 20.0, "remarks", "2013-08-01"));

        //check if only 1 record was deleted (record type='savings' and date='2017-08-01')
        assertEquals(1, debtHelper.deleteDebt(createdDebt));
    }

    @Test
    public void testCountAllDebts() {
        Debt debt = new Debt("loan", 20.0, "remarks", "2017-08-01");
        DebtHelper debtHelper = new DebtHelper(dbHelper);

        debtHelper.addDebt(debt);
        debtHelper.addDebt(debt);
        debtHelper.addDebt(debt);
        debtHelper.addDebt(debt);

        //check if the table contains exactly 4 records
        assertEquals(4, debtHelper.countAllDebts());
    }

    @Test
    public void testCountAllDebtsWhere() {
        Debt debt = new Debt("loan", 20.0, "remarks", "2017-08-01");
        DebtHelper debtHelper = new DebtHelper(dbHelper);

        debtHelper.addDebt(debt);
        debtHelper.addDebt(debt);
        debtHelper.addDebt(debt);
        debtHelper.addDebt(debt);
        debtHelper.addDebt(new Debt("friend loan", 20.0, "remarks", "2016-08-01"));
        debtHelper.addDebt(new Debt("friend loan", 20.0, "remarks", "2016-08-01"));
        debtHelper.addDebt(new Debt("friend loan", 20.0, "remarks", "2016-08-01"));
        debtHelper.addDebt(new Debt("friend loan", 20.0, "remarks", "2016-08-01"));

        //check if table contains only 4 records (record type = 'uitf')
        assertEquals(4,
                debtHelper.countAllDebtsWhere(
                        new String[]{DATABASE_CONSTANTS._DEBTS_TYPE},
                        new String[]{"friend loan"}));
    }

    @Test
    public void testUpdateDebt() {
        Debt debt = new Debt("loan", 20.0, "remarks", "2017-08-01");
        DebtHelper debtHelper = new DebtHelper(dbHelper);
        Debt updateDebt;

        updateDebt = debtHelper.addDebt(debt);
        debtHelper.addDebt(new Debt("friend loan", 20.0, "remarks", "2016-08-01"));
        debtHelper.addDebt(new Debt("friend loan", 20.0, "remarks", "2016-08-01"));
        debtHelper.addDebt(new Debt("friend loan", 20.0, "remarks", "2016-08-01"));
        debtHelper.addDebt(new Debt("friend loan", 20.0, "remarks", "2016-08-01"));

        updateDebt.setType("company loan");
        updateDebt.setRemarks("No remarks");
        updateDebt.setDate("1990-01-01");
        updateDebt.setAmount(100.0);

        //check if only 1 record was updated (previous record type='savings')
        assertEquals(1, debtHelper.updateDebt(updateDebt));

        //check if the record was properly updated and has new values
        assertEquals(updateDebt.getType(), debtHelper.getDebt(updateDebt).getType());
        assertEquals(updateDebt.getAmount(), debtHelper.getDebt(updateDebt).getAmount());
        assertEquals(updateDebt.getRemarks(), debtHelper.getDebt(updateDebt).getRemarks());
        assertEquals(updateDebt.getDate(), debtHelper.getDebt(updateDebt).getDate());

        //check if the record does not have the old values
        assertNotSame(debt.getType(), debtHelper.getDebt(updateDebt).getType());
        assertNotSame(debt.getAmount(), debtHelper.getDebt(updateDebt).getAmount());
        assertNotSame(debt.getRemarks(), debtHelper.getDebt(updateDebt).getRemarks());
        assertNotSame(debt.getDate(), debtHelper.getDebt(updateDebt).getDate());
    }

    @Test
    public void testGetDebt() {
        DebtHelper debtHelper = new DebtHelper(dbHelper);
        int id = debtHelper.addDebt(new Debt("loan", 20.0, "remarks", "2016-08-01"))
                .getDebtId();
        debtHelper.addDebt(new Debt("friend loan", 20.0, "remarks", "2016-08-01"));

        //check if the record exists in the table (type='loan')
        assertEquals(id, debtHelper.getDebt(new Debt(id, null, 0.0, null, null)).getDebtId());
    }

    @Test
    public void testGetAllDebts() {
        DebtHelper debtHelper = new DebtHelper(dbHelper);
        List<Debt> createdDebts = new ArrayList<>();

        createdDebts.add(new Debt("loan", 20.0, "remarks", "2016-08-01"));
        createdDebts.add(new Debt("bank loan", 20.0, "remarks", "2016-08-01"));
        createdDebts.add(new Debt("friend loan", 20.0, "remarks", "2016-08-01"));
        createdDebts.add(new Debt("emergency loan", 20.0, "remarks", "2016-08-01"));

        for (Debt createdDebt : createdDebts) {
            debtHelper.addDebt(createdDebt);
        }

        //check if all records inserted were all selected
        assertEquals(debtHelper.getAllDebts().size(), createdDebts.size());
    }
}
