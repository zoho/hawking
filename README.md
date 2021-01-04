<h1 align="center">HAWKING</h1>
<p align="center"><strong>A natural language date parser written in Java</strong></p>
<p align="center">
        <a href="#about">About</a> •
        <a href="#build-instructions">Build Instructions</a> •
        <a href="#key-features">Key Features</a> •
        <a href="#configurations">Configurations</a>
          <br> <br>
</p>

## About

Given any date expression in a sentence, Hawking will apply standard language recognition and parser techniques to produce a list of corresponding dates with optional parse and syntax information.

It supports for almost every existing date format: absolute dates, relative dates(on Sunday, at 12 PM,
at 04/11/2021, etc).

###### Here is sample input:
``` java
HawkingTimeParser parser = new HawkingTimeParser();
String inputText = "Good morning, Have a nice day. Shall we meet on December 20 ?";
DatesFound datesFound = parser.parse(inputText, referenceDate, hawkingConfiguration, "eng");

//"eng" refers to English language
//referenceDate, hawkingConfiguration are explained in detail below
```
###### Output object "datesFound" contains following information and more:

```
Text : on December 20
Start : 2021-12-20T00:00:00.000+05:30
End : 2021-12-20T23:59:59.000+05:30
```
Check out the Demo file:
[```HawkingDemo```](https://github.com/zohocatalyst/hawking/tree/master/src/main/java/com/zoho/hawking/HawkingDemo.java)

## Build Instructions
Here are some helpful instructions to use the latest code:

#### Provided Build

We will provide updated models/jars here which have the latest version of the code.

At present, you can always build the very latest from GitHub HEAD yourself from [the current released version of the code](https://github.com/zohocatalyst/hawking)

#### Build with Maven

1. Make sure you have Maven installed, details here: [https://maven.apache.org/](https://maven.apache.org/)
2. If you run this command in the Hawking directory: `mvn package` , it should run the tests and build this jar file: `hawking/target/hawking-time-parser.jar`
3. Make sure all the models, configuration files from latest version of code is updated.

#### Models
The models used in Hawking Time Parser in the latest code are listed below.

*```Parser Model : Custom NER model to parse all dates in a sentence ```
> src/main/resources/parser/parser.crf.ser.gz

*```Recognizer Model : Custom NER model to parse all dates components in a date text ```
> src/main/resources/parser/recognizer.crf.ser.gz

*```Stanford NLP POS Tagger, Dependency Parser : Used for Tense Prediction```
> src/main/resources/tense/english-left3words-distsim.tagger, src/main/resources/tense/english_SD.gz

## Key Features
Here are some key features of Hawking Time Parser,

#### *Tense Consideration :*
Tense of the sentence is considered while parsing date.
```
I met you on Monday
I will meet you on Monday
```

#### *Context Understanding :*
Hawking Time Parser can understand and detect the date components based on context.
```
Good morning, I am so happy to see you(No dates)
Sun rises in the east(No dates) -- On sun, John met Lisa(Date: on sun)
I watched Tomorrow Never Dies(No dates) -- Can we go to the movie tomorrow ?(Date: tomorrow)
```

#### *Multiple Dates :*
It can detect multiple dates for a given input.
```
I am going for a trade show at evening. From December 2nd, most of the companies will be open in Tamil Nadu. (Dates: at evening, from December 2nd)
```

#### *Date Time Relation and Duration :*
Hawking time parser can parse duration or span cases .
```
Next 2 weeks, I am going to London for a conference.
We have been working on this project for 3 months.
Call me in 2 hours.
```
#### *Prefix & Postfix Importance :*
Prefix and postfix plays a vital role in parsing datetime text. Each prefix/postfix refers different date and time
```
Prefix -> Since Monday, Next Monday, within Monday, for Monday.
Postfix -> 2 weeks back, 10 months ago, 5 hours later.
```
Hawking time Parser can account 30+ prefix and suffix while parsing and returns date as input referred to(since, from, till, until, within, for, at, on, in, next, last, past and so on).
#### *Complex cases :*
Hawking can detect and parse date time more than 5 words.
```
next year 1st weekend Sunday morning 9 am
```
#### *Reference time :*
A reference time is the time from which date text has to be parsed
```
  *Let's say reference time = 01/11/2020 9:00 AM
    Input : "I will call at 2 PM"
    output = 01/11/2020 2:00 PM

  *Let's say reference time = 01/11/2020 8:00 PM
    Input : "I will call at 2 PM"
    Output = 02/11/2020 2:00 PM (As input is in future tense at night
    8 PM, it's obvious that it refers to tomorrow)
```
  So parsing dates based on input reference time is a base for Hawking time parser(if reference time is not passed, current system time will be default reference time)
#### *Time Zone Handling :*
Hawking time parser can parse dates and time along with timezone. Currently it supports 500+ Time zones
```
  Call me at 9 AM Singapore time
  I will schedule demo at 13:30 PST
  Meeting will start at 10:00 AM (Asia/Calcutta)
```
## Configurations
Hawking time Parser's versatility allows to the user to configure everything in the way they need the date text to parsed.

#### *Needs for configuration :*
Even though date and time is globally referred by same common words in English, the context of date text can differs from person to person, locale to locale, country to country, business to business.
```
When someone says "we will go out on weekend "
  -> In India it's Saturday and Sunday
  -> In UAE it's Friday and Saturday

Date formats :
  In Europe  = DD/MM/YYYY
  In United States = MM/DD/YYYY
```
So Hawking Configurations enable user to control the date and time output based on their perspective and requirements.

#### *Cases for configurations :*
Hawking Time Parser allows user set configuration to 30+ date and time components based on the needs.


*```Show me meeting schedules for next 2 days```

> User can expect next 48 hours or next 2 days (from tomorrow))

*```Show me meeting schedules for next week```

> WeekStart and WeekEnd can be given by user like Sunday to Saturday. For Business model user can set week to represent only weekdays-Monday to Friday

*```I will call you at 12 am```

> User can set their time zone as default timezone instead of mentioning timezone in input every time

*```Schedule meeting on 07/12/2020```

> User can set default dateformats like (DD/MM/YYYY or MM/DD/YYYY) more than 20 DateFormats is supported.


#### *Business Custom Cases:*
Hawking time parser supports business date cases like Fiscal year, Financial year, Annual year, quarterly, Q3, etc.
```
* Revenue generated this quarter
* In the last fiscal year, our company reached more than 20 million users
```
Users can set configuration for the Fiscal year start and end too based on their business model (February to January or April to March...)

***To understand the Hawking configuration input, check:*** [HawkingConfiguration](https://github.com/zohocatalyst/hawking/blob/master/src/main/java/com/zoho/hawking/datetimeparser/configuration/HawkingConfiguration.java)
