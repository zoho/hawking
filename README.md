# Hawking

Hawking is a natural language date parser written in Java. Given a date expression, hawking will apply standard language recognition and parser techniques to produce a list of corresponding dates with optional parse and syntax information. 

## Sample Code:

Keep your hands dirty

Hawking parser is simple to use:

Clone the repository and use

```
mvn compile
```
Then run the following code.

``` java
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zoho.hawking.*;
import com.zoho.hawking.datetimeparser.configuration.HawkingConfiguration;
import com.zoho.hawking.model.DatesFound;

class HawkingDemo {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(HawkingDemo.class);

	 public static void main(String[] args) throws Exception {
           HawkingTimeParser parser = new HawkingTimeParser();
           String inputText = "Good morning, Have a nice day. Shall we meet tomorrow ?";
           HawkingConfiguration hawkingConfiguration = new HawkingConfiguration();
           hawkingConfiguration.setFiscalYearStart(2);
           hawkingConfiguration.setFiscalYearEnd(1);
           hawkingConfiguration.setTimeZone("IST");
           Date referenceDate = new Date();
           DatesFound datesFound = null;
           try {
                datesFound = parser.parse(inputText, referenceDate, hawkingConfiguration, "eng"); //No I18N
           } catch (Exception e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           }
           LOGGER.info("DATES FOUND ::  "+ datesFound.toString());
       }

}
```

To understand the Hawking configuration input check:
```
com.zoho.hawking.datetimeparser.configuration.HawkingConfiguration.java
```