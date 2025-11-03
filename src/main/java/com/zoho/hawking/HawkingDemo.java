//$Id$
package com.zoho.hawking;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.zoho.hawking.datetimeparser.configuration.HawkingConfiguration;
import com.zoho.hawking.language.english.model.DatesFound;


class HawkingDemo {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(HawkingDemo.class);

  public static void main(String[] args) throws Exception {
    HawkingTimeParser parser = new HawkingTimeParser();
    String inputText = "Good morning, Have a nice day. Shall we meet on December 20 ?";
    HawkingConfiguration hawkingConfiguration = new HawkingConfiguration();
    hawkingConfiguration.setFiscalYearStart(4);
    hawkingConfiguration.setFiscalYearEnd(3);
    //hawkingConfiguration.setDateComponentOnly(true);
    // Strictly Set to false when the input includes general text with embedded date information (e.g., "Meet me at the airport tomorrow 11:20 am")
    // Set to true when the input contains only date-related text (e.g., "Nov-2024", "12-Dec-2022")

    hawkingConfiguration.setTimeZone("IST");
    Date referenceDate = new Date();
    DatesFound datesFound = null;
    try {
      datesFound = parser.parse(inputText, referenceDate, hawkingConfiguration, "eng"); //No I18N
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    assert datesFound != null;
    LOGGER.info("DATES FOUND ::  "+ datesFound.toString());
  }

}
