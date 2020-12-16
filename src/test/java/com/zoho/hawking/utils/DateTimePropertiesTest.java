package com.zoho.hawking.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for DateTimeProperties class.
 */

public class DateTimePropertiesTest {

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
   * Test isDateContain().
   */
  @Test
  public void testIsDateContain() {
    assertEquals(true, DateTimeProperties.isDateContain("sunday")); //No I18N
  }

  /**
   * Test cardinalNumberFinder().
   */
  @Test
  public void testCardinalNumberFinder() {
    assertEquals("on 2nd june", DateTimeProperties.cardinalNumberFinder("on second june")); //No I18N

  }

}
