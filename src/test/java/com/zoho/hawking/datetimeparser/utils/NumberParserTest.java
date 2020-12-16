package com.zoho.hawking.datetimeparser.utils;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for NumberParser class.
 */

public class NumberParserTest {

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
   * Test numberParser().
   */
  @Test
  public void testNumberParser() {
    assertEquals(Pair.of(6, true), NumberParser.numberParser("sixth")); //No I18N
  }
}
