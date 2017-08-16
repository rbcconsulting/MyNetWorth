package com.rbcconsulting.mobile.mynetworth.utility;

import com.rbcconsulting.mobile.mynetworth.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Ralph on 13/08/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class AppUtilityTest {

    @Test
    public void testRoundNearest2() {
        double testNumber_1 = 123.12782136;
        double testNumber_2 = 33.1249;
        double testNumber_3 = 103.194;
        double testNumber_4 = 13.198;

        assertEquals(123.13, AppUtility.roundNearest2(testNumber_1));
        assertEquals(33.12, AppUtility.roundNearest2(testNumber_2));
        assertEquals(103.19, AppUtility.roundNearest2(testNumber_3));
        assertEquals(13.20, AppUtility.roundNearest2(testNumber_4));

    }
}
