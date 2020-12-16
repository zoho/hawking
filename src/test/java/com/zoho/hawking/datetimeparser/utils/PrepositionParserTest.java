package com.zoho.hawking.datetimeparser.utils;


    import static org.junit.Assert.assertEquals;

    import com.zoho.hawking.language.LanguageFactory;
    import org.apache.commons.lang3.tuple.Triple;
    import org.junit.After;
    import org.junit.Before;
    import org.junit.Test;

/**
 * Test class for PrepositionParser class.
 */
public class PrepositionParserTest {

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
   * Test prePositionParsing().
   */
  @Test
  public void testPrePositionParsing() {
    assertEquals(Triple.of("", "", "next"),
        PrepositionParser.prePositionParsing("next", "", LanguageFactory.getLanguageImpl("eng"))); //No I18N
  }
}
