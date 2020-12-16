package com.zoho.hawking.datetimeparser.utils;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for TagParser class.
 */

public class TagParserTest {

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
   * Test tagParser().
   */
  @Test
  public void testTagParser() {
    HashMap<String, String> mapOfTags = new HashMap<>();
    mapOfTags.put("day_of_week", "sunday");
    mapOfTags.put("implict_prefix", "on next");
    assertEquals(mapOfTags, TagParser.tagParser("<implict_prefix>on next</implict_prefix> <day_of_week>sunday</day_of_week>")); //No I18N
  }
}
