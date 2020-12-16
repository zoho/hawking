package com.zoho.hawking.utils;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for CoreNlpUtils class.
 */

public class CoreNlpUtilsTest {

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
   * Test sentenceTokenize().
   */
  @Test
  public void testSentenceTokenize() {
    List<String> sentList = new ArrayList<>();
    sentList.add("The Hohenzollern Bridge crossing the Rhine in Cologne, Germany, with the Cologne Cathedral in the background."); //No I18N
    sentList.add("The bridge is a tied-arch railway bridge, as well as a pedestrian bridge."); //No I18N
    assertEquals(sentList, CoreNlpUtils.sentenceTokenize("The Hohenzollern Bridge crossing the Rhine in Cologne, Germany, with the Cologne Cathedral in the background. The bridge is a tied-arch railway bridge, as well as a pedestrian bridge."));//No I18N
  }

}
