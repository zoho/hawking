package com.zoho.hawking.datetimeparser.utils;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for DateUtil class.
 */
public class DateUtilTest {

  /**
   * Initialise test suite - no-op.
   */
  @Before
  public void setUp() {
  }

  /**
   * Clean up test suite - no-op.
   */
  @After
  public void tearDown() {
  }

  /**
   * Test stringToDate().
   */
  @Test
  public void testStringToDate() throws Exception {
    assertEquals(new Date(Long.valueOf("1601596800000")), DateUtil
        .stringToDate("02/10/2020", new DateTime(Long.valueOf("1607952668000")), "dd/MM/YYYY"));
  }

}
