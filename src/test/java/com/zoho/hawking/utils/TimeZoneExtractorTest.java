package com.zoho.hawking.utils;

import com.zoho.hawking.datetimeparser.configuration.HawkingConfiguration;
import com.zoho.hawking.language.english.model.DateTimeOffsetReturn;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for TimeZoneExtractor class.
 */

public class TimeZoneExtractorTest {

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
   * Test timeZoneDetector().
   */
  @Test
  public void testTimeZoneDetector() {
    assertEquals("+05:30", TimeZoneExtractor.timeZoneDetector("4 am IST", new Date())); //No I18N

  }

  /**
   * Test referenceDateExtractor().
   */
  @Test
  public void testReferenceDateExtractor() {
    Date date = new Date(Long.valueOf("1607970068198"));
    DateTimeOffsetReturn dateTimeOffsetReturn = new DateTimeOffsetReturn(new Date(Long.valueOf("1607959268000")), "-03:30"); //No I18N
    assertEquals(dateTimeOffsetReturn.getReferenceDate(),
        TimeZoneExtractor.referenceDateExtractor(date, new HawkingConfiguration(), "5 PM Uruguay Time").getReferenceDate()); //No I18N
  }

  /**
   * Test offsetDateConverter().
   */
  @Test
  public void testOffsetDateConverter() {
    assertEquals(new Date(Long.valueOf("1607980868000")), TimeZoneExtractor.offsetDateConverter(Long.valueOf("1607970068198"), "-03:00"));
  }

  /**
   * Test dateFormatter().
   */
  @Test
  public void testDateFormatter() {
    assertEquals("2020-12-15T02:51:08", TimeZoneExtractor.dateFormatter(Long.valueOf("1608000668000"))); //No I18N

  }
}
