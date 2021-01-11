package com.zoho.hawking.datetimeparser.utils;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for DateTimeManipulation class.
 */

public class DateTimeManipulationTest {

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
   * Test getStartOrEndTime().
   */
  @Test
  public void testGetStartOrEndTime() {
    DateTime dateTime = new DateTime(Long.valueOf("1608457980000"));
    assertEquals(new DateTime(Long.valueOf("1608422400000")),
        DateTimeManipulation.getStartOrEndTime(dateTime, 1, 3));
  }

  /**
   * Test addYears().
   */
  @Test
  public void testAddYears() {
    DateTime dateTime = new DateTime(Long.valueOf("1607945107000"));
    assertEquals(new DateTime(Long.valueOf("1671017107000")),
        DateTimeManipulation.addYears(dateTime, 2));
  }

  /**
   * Test setYear().
   */
  @Test
  public void testSetYear() {
    DateTime dateTime = new DateTime(Long.valueOf("1607945394000"));
    assertEquals(new DateTime(Long.valueOf("2554630194000")),
        DateTimeManipulation.setYear(dateTime, 2050));
  }

  /**
   * Test addMonths().
   */
  @Test
  public void testAddMonths() {
    DateTime dateTime = new DateTime(Long.valueOf("1607945622000"));
    assertEquals(new DateTime(Long.valueOf("1613302422000")),
        DateTimeManipulation.addMonths(dateTime, 0, 2));
  }

  /**
   * Test setMonth().
   */
  @Test
  public void testSetMonth() {
    DateTime dateTime = new DateTime(Long.valueOf("1607945719000"));
    assertEquals(new DateTime(Long.valueOf("1623670519000")),
        DateTimeManipulation.setMonth(dateTime, 1, 6));
  }

  /**
   * Test recentPastMonth().
   */
  @Test
  public void testRecentPastMonth() {
    DateTime dateTime = new DateTime(Long.valueOf("1607952668000"));
    assertEquals(new DateTime(Long.valueOf("1592141468000")),
        DateTimeManipulation.recentPastMonth(dateTime, 6));
  }

  /**
   * Test recentFutureMonth().
   */
  @Test
  public void testRecentFutureMonth() {
    DateTime dateTime = new DateTime(Long.valueOf("1607952668000"));
    assertEquals(new DateTime(Long.valueOf("1623677468000")),
        DateTimeManipulation.recentFutureMonth(dateTime, 6));
  }

  /**
   * Test addWeeks().
   */
  @Test
  public void testAddWeeks() {
    DateTime dateTime = new DateTime(Long.valueOf("1607952668000"));
    assertEquals(new DateTime(Long.valueOf("1611581468000")),
        DateTimeManipulation.addWeeks(dateTime, 6, 0));
  }

  /**
   * Test setWeek().
   */
  @Test
  public void testSetWeek() {
    DateTime dateTime = new DateTime(Long.valueOf("1607952668000"));
    assertEquals(new DateTime(Long.valueOf("1579354268000")),
        DateTimeManipulation.setWeek(dateTime, 6, 0));
  }

  /**
   * Test nthWeekOfMonth().
   */
  @Test
  public void testNthWeekOfMonth() {
    DateTime dateTime = new DateTime(Long.valueOf("1607952668000"));
    assertEquals(new DateTime(Long.valueOf("1608557468000")),
        DateTimeManipulation.nthWeekOfMonth(dateTime, 3, 1));
  }

  /**
   * Test nthDayofWeek().
   */
  @Test
  public void testNthDayofWeek() {
    DateTime dateTime = new DateTime(Long.valueOf("1607952668000"));
    assertEquals(new DateTime(Long.valueOf("1608211868000")),
        DateTimeManipulation.nthDayofWeek(dateTime, 4, 1));
  }

  /**
   * Test nthWeekOfYear().
   */
  @Test
  public void testNthWeekOfYear() {
    DateTime dateTime = new DateTime(Long.valueOf("1607952668000"));
    assertEquals(new DateTime(Long.valueOf("1579699868000")),
        DateTimeManipulation.nthWeekOfYear(dateTime, 4, 3));
  }

  /**
   * Test addDays().
   */
  @Test
  public void testAddDays() {
    DateTime dateTime = new DateTime(Long.valueOf("1607952668000"));
    assertEquals(new DateTime(Long.valueOf("1609162268000")),
        DateTimeManipulation.addDays(dateTime, 2, 0));
  }

  /**
   * Test setDayOfWeek().
   */
  @Test
  public void testSetDayOfWeek() {
    DateTime dateTime = new DateTime(Long.valueOf("1607952668000"));
    assertEquals(new DateTime(Long.valueOf("1609335068000")),
        DateTimeManipulation.setDayOfWeek(dateTime, 2, 3));
  }

  /**
   * Test nthDayOfMonth().
   */
  @Test
  public void testNthDayOfMonth() {
    DateTime dateTime = new DateTime(Long.valueOf("1607952668000"));
    assertEquals(new DateTime(Long.valueOf("1606915868000")),
        DateTimeManipulation.nthDayOfMonth(dateTime, 2));
  }


  /**
   * Test nthDayOfYear().
   */
  @Test
  public void testNthDayOfYear() {
    DateTime dateTime = new DateTime(Long.valueOf("1607952668000"));
    assertEquals(new DateTime(Long.valueOf("1577971868000")),
        DateTimeManipulation.nthDayOfYear(dateTime, 2));
  }


  /**
   * Test recentPastDay().
   */
  @Test
  public void testRecentPastDay() {
    DateTime dateTime = new DateTime(Long.valueOf("1607952668000"));
    assertEquals(new DateTime(Long.valueOf("1607434268000")),
        DateTimeManipulation.recentPastDay(dateTime, 2));
  }


  /**
   * Test recentFutureDay().
   */
  @Test
  public void testRecentFutureDay() {
    DateTime dateTime = new DateTime(Long.valueOf("1607952668000"));
    assertEquals(new DateTime(Long.valueOf("1608039068000")),
        DateTimeManipulation.recentFutureDay(dateTime, 2));
  }


  /**
   * Test addHours().
   */
  @Test
  public void testAddHours() {
    DateTime dateTime = new DateTime(Long.valueOf("1607952668000"));
    assertEquals(new DateTime(Long.valueOf("1608024668000")),
        DateTimeManipulation.addHours(dateTime, 20));
  }

  /**
   * Test nthHourDay().
   */
  @Test
  public void testNthHourDay() {
    DateTime dateTime = new DateTime(Long.valueOf("1607952668000"));
    assertEquals(new DateTime(Long.valueOf("1607905868000")),
        DateTimeManipulation.nthHourDay(dateTime, 2, 0));
  }

  /**
   * Test recentPastHour().
   */
  @Test
  public void testRecentPastHour() {
    DateTime dateTime = new DateTime(Long.valueOf("1607952668000"));
    assertEquals(new DateTime(Long.valueOf("1607941868000")),
        DateTimeManipulation.recentPastHour(dateTime, 10));
  }

  /**
   * Test recentFutureHour().
   */
  @Test
  public void testRecentFutureHour() {
    DateTime dateTime = new DateTime(Long.valueOf("1607952668000"));
    assertEquals(new DateTime(Long.valueOf("1608028268000")),
        DateTimeManipulation.recentFutureHour(dateTime, 10));
  }


  /**
   * Test exactPastTime().
   */
  @Test
  public void testExactPastTime() {
    DateTime dateTime = new DateTime(Long.valueOf("1607952668000"));
    assertEquals(new DateTime(Long.valueOf("1607945468000")),
        DateTimeManipulation.exactPastTime(dateTime.minusHours(2), dateTime));
  }


  /**
   * Test exactFutureTime().
   */
  @Test
  public void testExactFutureTime() {
    DateTime dateTime = new DateTime(Long.valueOf("1607952668000"));
    assertEquals(new DateTime(Long.valueOf("1607959868000")),
        DateTimeManipulation.exactFutureTime(dateTime.plusHours(2), dateTime));
  }


  /**
   * Test addMinutes().
   */
  @Test
  public void testAddMinutes() {
    DateTime dateTime = new DateTime(Long.valueOf("1607952668000"));
    assertEquals(new DateTime(Long.valueOf("1607953868000")),
        DateTimeManipulation.addMinutes(dateTime, 20));
  }


  /**
   * Test setMinute().
   */
  @Test
  public void testSetMinute() {
    DateTime dateTime = new DateTime(Long.valueOf("1607952668000"));
    assertEquals(new DateTime(Long.valueOf("1607952008000")),
        DateTimeManipulation.setMinute(dateTime, 20));
  }

  /**
   * Test addSeconds().
   */
  @Test
  public void testAddSeconds() {
    DateTime dateTime = new DateTime(Long.valueOf("1607952668000"));
    assertEquals(new DateTime(Long.valueOf("1607952688000")),
        DateTimeManipulation.addSeconds(dateTime, 20));
  }


  /**
   * Test setSecond().
   */
  @Test
  public void testSetSecond() {
    DateTime dateTime = new DateTime(Long.valueOf("1607952668000"));
    assertEquals(new DateTime(Long.valueOf("1607952680000")),
        DateTimeManipulation.setSecond(dateTime, 20));
  }

}
