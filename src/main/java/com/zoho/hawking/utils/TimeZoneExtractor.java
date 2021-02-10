package com.zoho.hawking.utils;


import com.zoho.hawking.datetimeparser.configuration.HawkingConfiguration;
import com.zoho.hawking.language.english.model.DateTimeOffsetReturn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeZoneExtractor {
    private static final Logger LOGGER = Logger.getLogger(TimeZoneExtractor.class.getName());
    public static List<String> timeZoneList = Arrays.asList("GB", "NZ", "HST", "AST", "PST", "MST", "PNT", "CST", "CDT", "EST", "EDT", "IET", "PRT", "AGT", "BET", "GMT", "UCT", "UTC",  //No I18N
            "WET", "CET", "ECT", "MET", "MDT", "ART", "CAT", "EET", "EAT", "NET", "PLT", "PDT", "BST", "VST", "CTT", "PRC", "JST", "ROK", "AET", "SST", "NST", "MIT", "CNT", "IST", "ACT", "AEST", "Cuba",  //No I18N
            "GMT0", "Zulu", "Eire", "W-SU", "Iran", "Egypt", "Libya", "Japan", "Navajo", "Poland", "Israel", "Turkey", "PST8PDT", "MST7MDT", "CST6CDT", "Jamaica", "EST5EDT",  //No I18N
            "Etc/GMT", "Etc/UCT", "Etc/UTC", "Iceland", "GB-Eire", "NZ-CHAT", "NiueTime", "US/Samoa", "AcreTime", "PeruTime", "Etc/GMT0", "Etc/Zulu", "Portugal", "OralTime",  //No I18N
            "OmskTime", "HovdTime", "Hongkong", "Tasmania", "Victoria", "FijiTime", "WakeTime", "GMT-12:00", "GMT-11:00", "Niue Time", "GMT-10:00", "US/Hawaii", "GMT-09:00",  //No I18N
            "Etc/GMT+9", "GMT-08:00", "Etc/GMT+8", "US/Alaska", "GMT-07:00", "Etc/GMT+7", "GMT-06:00", "Etc/GMT+6", "GMT-05:00", "Etc/GMT+5", "Acre Time", "Peru Time",  //No I18N
            "GMT-04:00", "Etc/GMT+4", "GMT-03:00", "Etc/GMT+3", "ChileTime", "GMT-02:00", "Etc/GMT+2", "GMT-01:00", "Etc/GMT+1", "Etc/GMT+0", "Etc/GMT-0", "Greenwich",  //No I18N
            "Universal", "GMT+01:00", "Etc/GMT-1", "GMT+02:00", "Etc/GMT-2", "GMT+03:00", "Etc/GMT-3", "Asia/Gaza", "SyowaTime", "Asia/Aden", "Asia/Baku", "GMT+04:00",  //No I18N
            "Etc/GMT-4", "AqtauTime", "GMT+05:00", "Asia/Oral", "Etc/GMT-5", "Asia/Omsk", "GMT+06:00", "Etc/GMT-6", "DavisTime", "GMT+07:00", "Hovd Time", "Asia/Hovd",  //No I18N
            "Etc/GMT-7", "GMT+08:00", "Etc/GMT-8", "Singapore", "Asia/Dili", "GMT+09:00", "Etc/GMT-9", "PalauTime", "GMT+10:00", "ChuukTime", "GMT+11:00", "GMT+12:00",  //No I18N
            "Kwajalein", "Fiji Time", "NauruTime", "Wake Time", "GMT+13:00", "TongaTime", "GMT+14:00", "NepalTime", "Etc/GMT+12", "Etc/GMT+11", "Etc/GMT+10", "TahitiTime",  //No I18N
            "US/Pacific", "US/Arizona", "US/Central", "US/Eastern", "AmazonTime", "GuyanaTime", "Chile Time", "Asia/Amman", "Syowa Time", "Asia/Qatar", "Asia/Dubai",  //No I18N
            "SamaraTime", "MawsonTime", "Aqtau Time", "Asia/Aqtau", "AqtobeTime", "VostokTime", "Asia/Dacca", "Asia/Dhaka", "BhutanTime", "Davis Time", "Asia/Tomsk",  //No I18N
            "BruneiTime", "Asia/Macao", "Asia/Macau", "Asia/Chita", "Asia/Seoul", "Asia/Tokyo", "Palau Time", "Queensland", "Etc/GMT-10", "Chuuk Time", "Etc/GMT-11",  //No I18N
            "KosraeTime", "AnadyrTime", "Etc/GMT-12", "TuvaluTime", "Nauru Time", "FutunaTime", "Etc/GMT-13", "Tonga Time", "Etc/GMT-14", "Asia/Kabul", "Nepal Time",  //No I18N
            "CookIs.Time", "Tahiti Time", "US/Aleutian", "GambierTime", "US/Mountain", "EcuadorTime", "Brazil/Acre", "US/Michigan", "Amazon Time", "Guyana Time",  //No I18N
            "BoliviaTime", "Brazil/West", "UruguayTime", "RotheraTime", "Brazil/East", "Africa/Lome", "Europe/Oslo", "Europe/Rome", "Asia/Beirut", "Asia/Hebron",  //No I18N
            "Europe/Kiev", "Europe/Riga", "Africa/Juba", "Asia/Kuwait", "Asia/Riyadh", "Asia/Muscat", "GeorgiaTime", "ArmeniaTime", "Samara Time", "Indian/Mahe",  //No I18N
            "ReunionTime", "Mawson Time", "Aqtobe Time", "Asia/Aqtobe", "Asia/Atyrau", "Vostok Time", "Asia/Almaty", "Bhutan Time", "Asia/Thimbu", "Asia/Urumqi",  //No I18N
            "Asia/Saigon", "Brunei Time", "Asia/Brunei", "Asia/Harbin", "IrkutskTime", "Asia/Manila", "Asia/Taipei", "YakutskTime", "Pacific/Yap", "MagadanTime",  //No I18N
            "VanuatuTime", "Kosrae Time", "NorfolkTime", "PohnpeiTime", "Anadyr Time", "Asia/Anadyr", "Tuvalu Time", "Futuna Time", "TokelauTime", "LineIs.Time",  //No I18N
            "Asia/Tehran", "MyanmarTime", "Asia/Yangon", "Pacific/Niue", "America/Adak", "America/Atka", "Gambier Time", "SystemV/YST9", "America/Nome",  //No I18N
            "SystemV/PST8", "Canada/Yukon", "SystemV/MST7", "SystemV/CST6", "Central Time", "ColombiaTime", "Ecuador Time", "America/Lima", "SystemV/EST5", "ParaguayTime",  //No I18N
            "Bolivia Time", "SystemV/AST4", "BrasiliaTime", "Uruguay Time", "SurinameTime", "Rothera Time", "Africa/Accra", "Africa/Dakar", "Africa/Lagos",  //No I18N
            "Africa/Tunis", "Africa/Ceuta", "Europe/Malta", "Europe/Paris", "Europe/Vaduz", "Africa/Cairo", "Asia/Nicosia", "Europe/Sofia", "Asia/Baghdad",  //No I18N
            "Asia/Bahrain", "Europe/Kirov", "Europe/Minsk", "Georgia Time", "Asia/Tbilisi", "Armenia Time", "Asia/Yerevan", "Reunion Time", "PakistanTime",  //No I18N
            "Asia/Karachi", "MaldivesTime", "Alma-AtaTime", "Asia/Bishkek", "Asia/Kashgar", "Asia/Thimphu", "Asia/Bangkok", "Asia/Barnaul", "Asia/Jakarta",  //No I18N
            "HongKongTime", "Irkutsk Time", "Asia/Irkutsk", "MalaysiaTime", "Asia/Kuching", "Yakutsk Time", "Asia/Yakutsk", "Ust-NeraTime", "Pacific/Guam",  //No I18N
            "Pacific/Truk", "Magadan Time", "Asia/Magadan", "SakhalinTime", "Vanuatu Time", "Norfolk Time", "Pohnpei Time", "Pacific/Fiji", "Pacific/Wake",  //No I18N
            "Pacific/Apia", "Tokelau Time", "Asia/Colombo", "Asia/Kolkata", "Myanmar Time", "Asia/Rangoon", "Indian/Cocos", "Pacific/Samoa", "SystemV/HST10",  //No I18N
            "America/Sitka", "America/Boise", "GalapagosTime", "Colombia Time", "America/Aruba", "Paraguay Time", "VenezuelaTime", "America/Thule", "ArgentineTime",  //No I18N
            "Brasilia Time", "America/Bahia", "America/Belem", "America/Jujuy", "Suriname Time", "CapeVerdeTime", "GhanaMeanTime", "Africa/Bamako", "Africa/Banjul",  //No I18N
            "Africa/Bissau", "Etc/Greenwich", "Etc/Universal", "Europe/Jersey", "Europe/Lisbon", "Europe/London", "Africa/Bangui", "Africa/Douala", "Africa/Luanda",  //No I18N
            "Africa/Malabo", "Africa/Niamey", "Europe/Dublin", "Europe/Berlin", "Europe/Madrid", "Europe/Monaco", "Europe/Prague", "Europe/Skopje", "Europe/Tirane",  //No I18N
            "Europe/Vienna", "Europe/Warsaw", "Europe/Zagreb", "Europe/Zurich", "Africa/Harare", "Africa/Kigali", "Africa/Lusaka", "Africa/Maputo", "Africa/Maseru",  //No I18N
            "Asia/Damascus", "Asia/Tel_Aviv", "Europe/Athens", "Africa/Asmara", "Africa/Asmera", "Asia/Istanbul", "Europe/Moscow", "Indian/Comoro", "Europe/Samara",  //No I18N
            "MauritiusTime", "Asia/Ashgabat", "Asia/Dushanbe", "Pakistan Time", "Asia/Tashkent", "Maldives Time", "QyzylordaTime", "Indian/Chagos", "IndochinaTime",  //No I18N
            "HongKong Time", "Malaysia Time", "Asia/Makassar", "Asia/Shanghai", "SingaporeTime", "Asia/Jayapura", "Asia/Khandyga", "Pacific/Palau", "NewSouthWales",  //No I18N
            "Ust-Nera Time", "Asia/Ust-Nera", "Australia/ACT", "Australia/NSW", "Pacific/Chuuk", "Sakhalin Time", "Asia/Sakhalin", "Pacific/Efate", "Pacific/Nauru",  //No I18N
            "MarquesasTime", "Asia/Calcutta", "Asia/Katmandu", "Australia/LHI", "Pacific/Midway", "Pacific/Tahiti", "America/Juneau", "America/Dawson", "Canada/Pacific",  //No I18N
            "US/Pacific-New", "America/Denver", "America/Inuvik", "Mexico/BajaSur", "America/Belize", "America/Regina", "Galapagos Time", "America/Merida",  //No I18N
            "Canada/Central", "Mexico/General", "Pacific/Easter", "America/Bogota", "America/Cancun", "America/Cayman", "America/Panama", "America/Havana",  //No I18N
            "America/Nassau", "Canada/Eastern", "Venezuela Time", "America/Cuiaba", "America/Guyana", "America/La_Paz", "America/Manaus", "America/Virgin",  //No I18N
            "America/Maceio", "America/Recife", "Africa/Abidjan", "Africa/Conakry", "Atlantic/Faroe", "Europe/Belfast", "Africa/Algiers", "Europe/Andorra",  //No I18N
            "Europe/Vatican", "Africa/Mbabane", "Africa/Tripoli", "Asia/Famagusta", "Asia/Jerusalem", "Europe/Nicosia", "Europe/Tallinn", "Europe/Vilnius",  //No I18N
            "Africa/Kampala", "Africa/Nairobi", "Indian/Mayotte", "AzerbaijanTime", "Europe/Saratov", "SeychellesTime", "Mauritius Time", "Indian/Reunion",  //No I18N
            "Asia/Ashkhabad", "TajikistanTime", "UzbekistanTime", "Asia/Samarkand", "KirgizstanTime", "BangladeshTime", "Qyzylorda Time", "Asia/Qyzylorda",  //No I18N
            "Indochina Time", "Asia/Pontianak", "Asia/Vientiane", "ChoibalsanTime", "Asia/Chongqing", "Asia/Chungking", "Asia/Hong_Kong", "Singapore Time",  //No I18N
            "Asia/Singapore", "Australia/West", "Asia/Pyongyang", "Pacific/Saipan", "SolomonIs.Time", "Pacific/Kosrae", "Pacific/Noumea", "Pacific/Ponape",  //No I18N
            "Asia/Kamchatka", "Pacific/Majuro", "GilbertIs.Time", "Pacific/Tarawa", "Pacific/Wallis", "PhoenixIs.Time", "Marquesas Time", "Asia/Kathmandu",  //No I18N
            "SouthAustralia", "Pacific/Gambier", "America/Yakutat", "SystemV/YST9YDT", "America/Tijuana", "SystemV/PST8PDT", "America/Creston", "America/Phoenix",  //No I18N
            "America/Ojinaga", "Canada/Mountain", "SystemV/MST7MDT", "America/Managua", "America/Chicago", "America/Knox_IN", "SystemV/CST6CDT", "America/Jamaica",  //No I18N
            "America/Detroit", "America/Iqaluit", "America/Nipigon", "America/Toronto", "SystemV/EST5EDT", "US/East-Indiana", "America/Antigua", "America/Caracas",  //No I18N
            "America/Curacao", "America/Grenada", "America/Marigot", "America/Tortola", "America/Halifax", "America/Moncton", "ChileSummerTime", "Canada/Atlantic",  //No I18N
            "SystemV/AST4ADT", "America/Cayenne", "America/Cordoba", "America/Mendoza", "America/Rosario", "FalklandIs.Time", "America/Godthab", "America/Noronha",  //No I18N
            "Cape Verde Time", "Atlantic/Azores", "Ghana Mean Time", "Africa/Freetown", "Africa/Monrovia", "Africa/Timbuktu", "Africa/El_Aaiun", "Atlantic/Canary",  //No I18N
            "Atlantic/Faeroe", "Europe/Guernsey", "Africa/Kinshasa", "Africa/Ndjamena", "Africa/Sao_Tome", "Europe/Belgrade", "Europe/Brussels", "Europe/Budapest",  //No I18N
            "Europe/Busingen", "Europe/Sarajevo", "Africa/Blantyre", "Africa/Gaborone", "Africa/Khartoum", "Africa/Windhoek", "Europe/Chisinau", "Europe/Helsinki",  //No I18N
            "Europe/Tiraspol", "Europe/Uzhgorod", "Africa/Djibouti", "Europe/Istanbul", "Azerbaijan Time", "Seychelles Time", "Tajikistan Time", "Uzbekistan Time",  //No I18N
            "Indian/Maldives", "Kirgizstan Time", "Bangladesh Time", "KrasnoyarskTime", "NovosibirskTime", "Asia/Phnom_Penh", "Choibalsan Time", "Asia/Choibalsan",  //No I18N
            "UlaanbaatarTime", "Asia/Ulan_Bator", "Australia/Perth", "Timor-LesteTime", "VladivostokTime", "Pacific/Norfolk", "Pacific/Pohnpei", "Pacific/Fakaofo",  //No I18N
            "AfghanistanTime", "Australia/Eucla", "Australia/North", "Australia/South", "Pacific/Chatham", "Pacific/Honolulu", "Pacific/Johnston", "Pacific/Pitcairn",  //No I18N
            "America/Ensenada", "Mexico/BajaNorte", "America/Edmonton", "America/Mazatlan", "America/Shiprock", "America/Resolute", "America/Winnipeg",  //No I18N
            "America/Atikokan", "America/Eirunepe", "CubaDaylightTime", "America/Montreal", "America/New_York", "America/Anguilla", "America/Asuncion",  //No I18N
            "America/Barbados", "America/Dominica", "America/St_Kitts", "America/St_Lucia", "America/Santiago", "Atlantic/Bermuda", "FrenchGuianaTime",  //No I18N
            "America/Santarem", "Atlantic/Stanley", "America/Miquelon", "Brazil/DeNoronha", "AzoresSummerTime", "Atlantic/Madeira", "Antarctica/Troll",  //No I18N
            "Europe/Amsterdam", "Europe/Gibraltar", "Europe/Ljubljana", "Europe/Podgorica", "Europe/Stockholm", "Africa/Bujumbura", "Europe/Bucharest",  //No I18N
            "Europe/Mariehamn", "Africa/Mogadishu", "Antarctica/Syowa", "Europe/Volgograd", "GulfStandardTime", "Europe/Astrakhan", "Europe/Ulyanovsk",  //No I18N
            "Indian/Mauritius", "TurkmenistanTime", "Indian/Kerguelen", "Antarctica/Davis", "Asia/Ho_Chi_Minh", "Krasnoyarsk Time", "Asia/Krasnoyarsk",  //No I18N
            "Novosibirsk Time", "Asia/Novosibirsk", "Indian/Christmas", "Antarctica/Casey", "Ulaanbaatar Time", "Asia/Ulaanbaatar", "Timor-Leste Time",  //No I18N
            "Vladivostok Time", "Asia/Vladivostok", "Australia/Currie", "Australia/Hobart", "Australia/Sydney", "NewCaledoniaTime", "Pacific/Auckland",  //No I18N
            "Pacific/Funafuti", "America/St_Johns", "Afghanistan Time", "CocosIslandsTime", "Australia/Darwin", "SamoaStandardTime", "Pacific/Pago_Pago",  //No I18N
            "Pacific/Rarotonga", "America/Anchorage", "America/Vancouver", "America/Chihuahua", "America/Guatemala", "Pacific/Galapagos", "America/Matamoros",  //No I18N
            "America/Menominee", "America/Monterrey", "US/Indiana-Starke", "America/Guayaquil", "America/Boa_Vista", "America/St_Thomas", "America/Glace_Bay",  //No I18N
            "America/Goose_Bay", "Chile Summer Time", "Chile/Continental", "America/Araguaina", "America/Catamarca", "America/Fortaleza", "America/Sao_Paulo",  //No I18N
            "Antarctica/Palmer", "GreenwichMeanTime", "Africa/Nouakchott", "Africa/Casablanca", "BritishSummerTime", "Africa/Libreville", "Africa/Porto-Novo",  //No I18N
            "Europe/Bratislava", "Europe/Copenhagen", "Europe/Luxembourg", "Europe/San_Marino", "Africa/Lubumbashi", "Europe/Zaporozhye", "Europe/Simferopol",  //No I18N
            "Antarctica/Mawson", "Turkmenistan Time", "YekaterinburgTime", "Antarctica/Vostok", "WestIndonesiaTime", "Asia/Novokuznetsk", "ChinaStandardTime",  //No I18N
            "Asia/Kuala_Lumpur", "EastIndonesiaTime", "KoreaStandardTime", "JapanStandardTime", "SrednekolymskTime", "Pacific/Kwajalein", "Pacific/Enderbury",  //No I18N
            "Pacific/Tongatapu", "Pacific/Marquesas", "IndiaStandardTime", "NorthernTerritory", "HawaiiStandardTime", "HawaiiDaylightTime", "AlaskaStandardTime",  //No I18N
            "AlaskaDaylightTime", "America/Metlakatla", "America/Whitehorse", "America/Hermosillo", "America/Costa_Rica", "Chile/EasterIsland", "America/Porto_Acre",  //No I18N
            "America/Rio_Branco", "America/Fort_Wayne", "America/Grand_Turk", "Cuba Daylight Time", "America/Louisville", "America/Guadeloupe", "America/Kralendijk",  //No I18N
            "America/Martinique", "America/Montserrat", "America/St_Vincent", "French Guiana Time", "America/Montevideo", "America/Paramaribo", "Antarctica/Rothera",  //No I18N
            "Azores Summer Time", "Africa/Ouagadougou", "Atlantic/Reykjavik", "Atlantic/St_Helena", "Europe/Isle_of_Man", "WesternAfricanTime", "Africa/Brazzaville",  //No I18N
            "Atlantic/Jan_Mayen", "CentralAfricanTime", "Europe/Kaliningrad", "IsraelDaylightTime", "EasternAfricanTime", "Africa/Addis_Ababa", "ArabiaStandardTime",  //No I18N
            "MoscowStandardTime", "Gulf Standard Time", "Yekaterinburg Time", "Asia/Yekaterinburg", "Asia/Ujung_Pandang", "Australia/Brisbane", "Australia/Canberra",  //No I18N
            "Australia/Lindeman", "Australia/Tasmania", "Australia/Victoria", "PapuaNewGuineaTime", "Srednekolymsk Time", "Asia/Srednekolymsk", "New Caledonia Time",  //No I18N
            "Antarctica/McMurdo", "Pacific/Kiritimati", "Iran Daylight Time", "Cocos Islands Time", "Australia/Adelaide", "Samoa Standard Time", "PacificStandardTime",  //No I18N
            "PacificDaylightTime", "America/Los_Angeles", "America/Fort_Nelson", "America/Yellowknife", "CentralStandardTime", "America/El_Salvador",  //No I18N
            "America/Tegucigalpa", "Canada/Saskatchewan", "CentralDaylightTime", "America/Mexico_City", "America/Rainy_River", "EasterIs.SummerTime",  //No I18N
            "EasternStandardTime", "EasternDaylightTime", "America/Pangnirtung", "America/Thunder_Bay", "America/Porto_Velho", "America/Puerto_Rico",  //No I18N
            "Atlantic/Cape_Verde", "Greenwich Mean Time", "British Summer Time", "CentralEuropeanTime", "Arctic/Longyearbyen", "EasternEuropeanTime",  //No I18N
            "Africa/Johannesburg", "Indian/Antananarivo", "West Indonesia Time", "ChristmasIslandTime", "China Standard Time", "East Indonesia Time",  //No I18N
            "Korea Standard Time", "Japan Standard Time", "Australia/Melbourne", "Pacific/Guadalcanal", "MarshallIslandsTime", "Canada/Newfoundland",  //No I18N
            "India Standard Time", "Indian Standard Time", "Indian Time", "Australia/Lord_Howe", "ChathamStandardTime", "Hawaii Standard Time", "Hawaii Daylight Time", "Alaska Standard Time",  //No I18N
            "Alaska Daylight Time", "PitcairnStandardTime", "America/Santa_Isabel", "MountainStandardTime", "America/Dawson_Creek", "MountainDaylightTime",  //No I18N
            "America/Indiana/Knox", "America/Rankin_Inlet", "America/Indianapolis", "AtlanticStandardTime", "America/Blanc-Sablon", "America/Campo_Grande",  //No I18N
            "AtlanticDaylightTime", "America/Buenos_Aires", "America/Punta_Arenas", "MiquelonDaylightTime", "America/Scoresbysund", "America/Danmarkshavn",  //No I18N
            "Western African Time", "Central African Time", "Israel Daylight Time", "Eastern African Time", "Africa/Dar_es_Salaam", "Arabia Standard Time",  //No I18N
            "Moscow Standard Time", "XinjiangStandardTime", "CentralIndonesiaTime", "Dumont-d'UrvilleTime", "Australia/Queensland", "ChamorroStandardTime",  //No I18N
            "Pacific/Port_Moresby", "Antarctica/Macquarie", "Pacific/Bougainville", "Australia/Yancowinna", "LordHoweStandardTime", "Pacific Standard Time",  //No I18N
            "Pacific Daylight Time", "America/Cambridge_Bay", "Central Standard Time", "America/Swift_Current", "Central Daylight Time", "Eastern Standard Time",  //No I18N
            "America/Coral_Harbour", "Eastern Daylight Time", "America/Indiana/Vevay", "America/Lower_Princes", "America/Port_of_Spain", "America/Santo_Domingo",  //No I18N
            "America/St_Barthelemy", "FernandodeNoronhaTime", "Central European Time", "Eastern European Time", "Papua New Guinea Time", "Antarctica/South_Pole",  //No I18N
            "Marshall Islands Time", "WestSamoaStandardTime", "Australia/Broken_Hill", "Chatham Standard Time", "Pitcairn Standard Time", "Mountain Standard Time",  //No I18N
            "Mountain Daylight Time", "America/Bahia_Banderas", "America/Port-au-Prince", "Atlantic Standard Time", "Atlantic Daylight Time", "Miquelon Daylight Time",  //No I18N
            "Atlantic/South_Georgia", "MiddleEuropeSummerTime", "Xinjiang Standard Time", "Central Indonesia Time", "Chamorro Standard Time", "NewZealandStandardTime",  //No I18N
            "America/Indiana/Marengo", "America/Indiana/Winamac", "America/Argentina/Jujuy", "America/Argentina/Salta", "Fernandode Noronha Time", "SouthAfricaStandardTime",  //No I18N
            "PhilippinesStandardTime", "Lord Howe Standard Time", "SouthGeorgiaStandardTime", "CoordinatedUniversalTime", "IndianOceanTerritoryTime", "BougainvilleStandardTime",  //No I18N
            "West Samoa Standard Time", "America/Indiana/Tell_City", "America/Indiana/Vincennes", "America/Argentina/Cordoba", "America/Argentina/Mendoza",  //No I18N
            "America/Argentina/Tucuman", "America/Argentina/Ushuaia", "WesternEuropeanSummerTime", "CentralEuropeanSummerTime", "Middle Europe Summer Time",  //No I18N
            "EasternEuropeanSummerTime", "Philippines Standard Time", "Antarctica/DumontDUrville", "New Zealand Standard Time", "America/Indiana/Petersburg",  //No I18N
            "America/Argentina/La_Rioja", "America/Argentina/San_Juan", "America/Argentina/San_Luis", "WesternGreenlandSummerTime", "EasternGreenlandSummerTime",  //No I18N
            "Coordinated Universal Time", "South Africa Standard Time", "Bougainville Standard Time", "Newfoundland Daylight Time", "America/North_Dakota/Beulah",  //No I18N
            "America/North_Dakota/Center", "America/Kentucky/Louisville", "America/Kentucky/Monticello", "America/Argentina/Catamarca", "South Georgia Standard Time",  //No I18N
            "Indian Ocean Territory Time", "MacquarieIslandStandardTime", "America/Indiana/Indianapolis", "Eastern Greenland Summer Time", "Western European Summer Time",  //No I18N
            "Central European Summer Time", "Eastern European Summer Time", "Petropavlovsk-KamchatskiTime", "SouthAustralia/NewSouthWales", "Western Greenland Summer Time",  //No I18N
            "AustralianWesternStandardTime", "AustralianEasternStandardTime", "AustralianCentralStandardTime", "America/North_Dakota/New_Salem", "America/Argentina/Buenos_Aires",  //No I18N
            "America/Argentina/Rio_Gallegos", "Macquarie Island Standard Time", "America/Argentina/ComodRivadavia", "Australian Eastern Standard Time",   //No I18N
            "Australian Central Standard Time", "AustralianCentralWesternStandardTime", "Australian Central Western Standard Time"); //No I18N
    public static Boolean isTimeZonePresent;
    private static Map<String, String> timeZoneOffsetReturn;
    private static Map<String, String> dayLightOffsetReturn;
    private static SimpleDateFormat zonedDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss z");  //No I18N
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); //No I18N
    private static SimpleDateFormat zonedDateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");  //No I18N

    static {
        timeZoneOffsetReturn = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        dayLightOffsetReturn = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        timeZoneOffsetReturn.put("GMT-12:00", "-12:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/GMT+12", "-12:00"); //No I18N
        timeZoneOffsetReturn.put("GMT-11:00", "-11:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/GMT+11", "-11:00"); //No I18N
        timeZoneOffsetReturn.put("SamoaStandardTime", "-11:00"); //No I18N
        timeZoneOffsetReturn.put("Samoa Standard Time", "-11:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Midway", "-11:00"); //No I18N
        timeZoneOffsetReturn.put("NiueTime", "-11:00"); //No I18N
        timeZoneOffsetReturn.put("Niue Time", "-11:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Niue", "-11:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Pago_Pago", "-11:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Samoa", "-11:00"); //No I18N
        timeZoneOffsetReturn.put("US/Samoa", "-11:00"); //No I18N
        timeZoneOffsetReturn.put("GMT-10:00", "-10:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/GMT+10", "-10:00"); //No I18N
        timeZoneOffsetReturn.put("HawaiiStandardTime", "-10:00"); //No I18N
        timeZoneOffsetReturn.put("Hawaii Standard Time", "-10:00"); //No I18N
        timeZoneOffsetReturn.put("HST", "-10:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Honolulu", "-10:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Johnston", "-10:00"); //No I18N
        timeZoneOffsetReturn.put("CookIs.Time", "-10:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Rarotonga", "-10:00"); //No I18N
        timeZoneOffsetReturn.put("TahitiTime", "-10:00"); //No I18N
        timeZoneOffsetReturn.put("Tahiti Time", "-10:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Tahiti", "-10:00"); //No I18N
        timeZoneOffsetReturn.put("SystemV/HST10", "-10:00"); //No I18N
        timeZoneOffsetReturn.put("US/Hawaii", "-10:00"); //No I18N
        timeZoneOffsetReturn.put("GMT-09:00", "-09:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/GMT+9", "-09:00"); //No I18N
        timeZoneOffsetReturn.put("HawaiiDaylightTime", "-09:00"); //No I18N
        timeZoneOffsetReturn.put("Hawaii Daylight Time", "-09:00"); //No I18N
        timeZoneOffsetReturn.put("America/Adak", "-10:00"); //No I18N
        timeZoneOffsetReturn.put("America/Atka", "-10:00"); //No I18N
        timeZoneOffsetReturn.put("US/Aleutian", "-10:00"); //No I18N
        timeZoneOffsetReturn.put("GambierTime", "-09:00"); //No I18N
        timeZoneOffsetReturn.put("Gambier Time", "-09:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Gambier", "-09:00"); //No I18N
        timeZoneOffsetReturn.put("AlaskaStandardTime", "-09:00"); //No I18N
        timeZoneOffsetReturn.put("Alaska Standard Time", "-09:00"); //No I18N
        timeZoneOffsetReturn.put("SystemV/YST9", "-09:00"); //No I18N
        timeZoneOffsetReturn.put("GMT-08:00", "-08:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/GMT+8", "-08:00"); //No I18N
        timeZoneOffsetReturn.put("AlaskaDaylightTime", "-08:00"); //No I18N
        timeZoneOffsetReturn.put("Alaska Daylight Time", "-08:00"); //No I18N
        timeZoneOffsetReturn.put("AST", "-09:00"); //No I18N
        timeZoneOffsetReturn.put("America/Juneau", "-09:00"); //No I18N
        timeZoneOffsetReturn.put("America/Metlakatla", "-09:00"); //No I18N
        timeZoneOffsetReturn.put("America/Nome", "-09:00"); //No I18N
        timeZoneOffsetReturn.put("America/Sitka", "-09:00"); //No I18N
        timeZoneOffsetReturn.put("America/Yakutat", "-09:00"); //No I18N
        timeZoneOffsetReturn.put("SystemV/YST9YDT", "-09:00"); //No I18N
        timeZoneOffsetReturn.put("US/Alaska", "-09:00"); //No I18N
        timeZoneOffsetReturn.put("PitcairnStandardTime", "-08:00"); //No I18N
        timeZoneOffsetReturn.put("Pitcairn Standard Time", "-08:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Pitcairn", "-08:00"); //No I18N
        timeZoneOffsetReturn.put("PacificStandardTime", "-08:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific Standard Time", "-08:00"); //No I18N
        timeZoneOffsetReturn.put("SystemV/PST8", "-08:00"); //No I18N
        timeZoneOffsetReturn.put("GMT-07:00", "-07:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/GMT+7", "-07:00"); //No I18N
        timeZoneOffsetReturn.put("PacificDaylightTime", "-07:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific Daylight Time", "-07:00"); //No I18N
        timeZoneOffsetReturn.put("America/Dawson", "-08:00"); //No I18N
        timeZoneOffsetReturn.put("America/Ensenada", "-08:00"); //No I18N
        timeZoneOffsetReturn.put("America/Los_Angeles", "-08:00"); //No I18N
        timeZoneOffsetReturn.put("America/Santa_Isabel", "-08:00"); //No I18N
        timeZoneOffsetReturn.put("America/Tijuana", "-08:00"); //No I18N
        timeZoneOffsetReturn.put("America/Vancouver", "-08:00"); //No I18N
        timeZoneOffsetReturn.put("America/Whitehorse", "-08:00"); //No I18N
        timeZoneOffsetReturn.put("Canada/Pacific", "-08:00"); //No I18N
        timeZoneOffsetReturn.put("Canada/Yukon", "-08:00"); //No I18N
        timeZoneOffsetReturn.put("Mexico/BajaNorte", "-08:00"); //No I18N
        timeZoneOffsetReturn.put("PST", "-08:00"); //No I18N
        timeZoneOffsetReturn.put("PST8PDT", "-08:00"); //No I18N
        timeZoneOffsetReturn.put("PDT", "-07:00"); //No I18N
        timeZoneOffsetReturn.put("SystemV/PST8PDT", "-08:00"); //No I18N
        timeZoneOffsetReturn.put("US/Pacific", "-08:00"); //No I18N
        timeZoneOffsetReturn.put("US/Pacific-New", "-08:00"); //No I18N
        timeZoneOffsetReturn.put("MountainStandardTime", "-07:00"); //No I18N
        timeZoneOffsetReturn.put("Mountain Standard Time", "-07:00"); //No I18N
        timeZoneOffsetReturn.put("America/Creston", "-07:00"); //No I18N
        timeZoneOffsetReturn.put("America/Dawson_Creek", "-07:00"); //No I18N
        timeZoneOffsetReturn.put("America/Fort_Nelson", "-07:00"); //No I18N
        timeZoneOffsetReturn.put("America/Hermosillo", "-07:00"); //No I18N
        timeZoneOffsetReturn.put("America/Phoenix", "-07:00"); //No I18N
        timeZoneOffsetReturn.put("MST", "-07:00"); //No I18N
        timeZoneOffsetReturn.put("MDT", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("PNT", "-07:00"); //No I18N
        timeZoneOffsetReturn.put("SystemV/MST7", "-07:00"); //No I18N
        timeZoneOffsetReturn.put("US/Arizona", "-07:00"); //No I18N
        timeZoneOffsetReturn.put("GMT-06:00", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/GMT+6", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("MountainDaylightTime", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("Mountain Daylight Time", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("America/Boise", "-07:00"); //No I18N
        timeZoneOffsetReturn.put("America/Cambridge_Bay", "-07:00"); //No I18N
        timeZoneOffsetReturn.put("America/Chihuahua", "-07:00"); //No I18N
        timeZoneOffsetReturn.put("America/Denver", "-07:00"); //No I18N
        timeZoneOffsetReturn.put("America/Edmonton", "-07:00"); //No I18N
        timeZoneOffsetReturn.put("America/Inuvik", "-07:00"); //No I18N
        timeZoneOffsetReturn.put("America/Mazatlan", "-07:00"); //No I18N
        timeZoneOffsetReturn.put("America/Ojinaga", "-07:00"); //No I18N
        timeZoneOffsetReturn.put("America/Shiprock", "-07:00"); //No I18N
        timeZoneOffsetReturn.put("America/Yellowknife", "-07:00"); //No I18N
        timeZoneOffsetReturn.put("Canada/Mountain", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("MST7MDT", "-07:00"); //No I18N
        timeZoneOffsetReturn.put("Mexico/BajaSur", "-07:00"); //No I18N
        timeZoneOffsetReturn.put("Navajo", "-07:00"); //No I18N
        timeZoneOffsetReturn.put("SystemV/MST7MDT", "-07:00"); //No I18N
        timeZoneOffsetReturn.put("US/Mountain", "-07:00"); //No I18N
        timeZoneOffsetReturn.put("Central Standard Time", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("CentralStandardTime", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("America/Belize", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("America/Costa_Rica", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("America/El_Salvador", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("America/Guatemala", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("America/Managua", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("America/Regina", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("America/Swift_Current", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("America/Tegucigalpa", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("Canada/Saskatchewan", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("GalapagosTime", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("Galapagos Time", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Galapagos", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("SystemV/CST6", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("GMT-05:00", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/GMT+5", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("CentralDaylightTime", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("Central Daylight Time", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Bahia_Banderas", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("America/Chicago", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("America/Indiana/Knox", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("America/Indiana/Tell_City", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("America/Knox_IN", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("America/Matamoros", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("America/Menominee", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("America/Merida", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("America/Mexico_City", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("America/Monterrey", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("America/North_Dakota/Beulah", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("America/North_Dakota/Center", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("America/North_Dakota/New_Salem", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("America/Rainy_River", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("America/Rankin_Inlet", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("America/Resolute", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("America/Winnipeg", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("CST", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("CDT", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("Central Time", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("CST6CDT", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("Canada/Central", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("EasterIs.SummerTime", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("Chile/EasterIsland", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("Mexico/General", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Easter", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("SystemV/CST6CDT", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("US/Central", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("US/Indiana-Starke", "-06:00"); //No I18N
        timeZoneOffsetReturn.put("Eastern Standard Time", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("EasternStandardTime", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Atikokan", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("ColombiaTime", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("Colombia Time", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Bogota", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Cancun", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Cayman", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Coral_Harbour", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("Acre Time", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("AcreTime", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Eirunepe", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("EcuadorTime", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("Ecuador Time", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Guayaquil", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Jamaica", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("PeruTime", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("Peru Time", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Lima", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Panama", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Porto_Acre", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Rio_Branco", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("Brazil/Acre", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("EST", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("EDT", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("Jamaica", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("SystemV/EST5", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("GMT-04:00", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/GMT+4", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("EasternDaylightTime", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("Eastern Daylight Time", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/Detroit", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Fort_Wayne", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Grand_Turk", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("CubaDaylightTime", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("Cuba Daylight Time", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/Havana", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Indiana/Indianapolis", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Indiana/Marengo", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Indiana/Petersburg", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Indiana/Vevay", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Indiana/Vincennes", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Indiana/Winamac", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Indianapolis", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Iqaluit", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Kentucky/Louisville", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Kentucky/Monticello", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Louisville", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Montreal", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Nassau", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/New_York", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Nipigon", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Pangnirtung", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Port-au-Prince", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Thunder_Bay", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("America/Toronto", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("Canada/Eastern", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("Cuba", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("EST5EDT", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("IET", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("SystemV/EST5EDT", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("US/East-Indiana", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("US/Eastern", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("US/Michigan", "-05:00"); //No I18N
        timeZoneOffsetReturn.put("AtlanticStandardTime", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("Atlantic Standard Time", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/Anguilla", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/Antigua", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/Aruba", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("ParaguayTime", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("Paraguay Time", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/Asuncion", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/Barbados", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/Blanc-Sablon", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("AmazonTime", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("Amazon Time", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/Boa_Vista", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/Campo_Grande", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("VenezuelaTime", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("Venezuela Time", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/Caracas", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/Cuiaba", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/Curacao", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/Dominica", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/Grenada", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/Guadeloupe", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("GuyanaTime", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("Guyana Time", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/Guyana", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/Kralendijk", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("BoliviaTime", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("Bolivia Time", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/La_Paz", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/Lower_Princes", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/Manaus", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/Marigot", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/Martinique", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/Montserrat", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/Port_of_Spain", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/Porto_Velho", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/Puerto_Rico", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/Santo_Domingo", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/St_Barthelemy", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/St_Kitts", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/St_Lucia", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/St_Thomas", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/St_Vincent", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/Tortola", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/Virgin", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("Brazil/West", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("PRT", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("SystemV/AST4", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("GMT-03:00", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/GMT+3", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("AtlanticDaylightTime", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("Atlantic Daylight Time", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Glace_Bay", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/Goose_Bay", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/Halifax", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("America/Moncton", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("ChileSummerTime", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("Chile Summer Time", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Santiago", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Thule", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("Atlantic/Bermuda", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("Canada/Atlantic", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("Chile/Continental", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("SystemV/AST4ADT", "-04:00"); //No I18N
        timeZoneOffsetReturn.put("ArgentineTime", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("AGT", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("BrasiliaTime", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("Brasilia Time", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Araguaina", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Argentina/Buenos_Aires", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Argentina/Catamarca", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Argentina/ComodRivadavia", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Argentina/Cordoba", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Argentina/Jujuy", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Argentina/La_Rioja", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Argentina/Mendoza", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Argentina/Rio_Gallegos", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Argentina/Salta", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Argentina/San_Juan", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Argentina/San_Luis", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Argentina/Tucuman", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Argentina/Ushuaia", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Bahia", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Belem", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Buenos_Aires", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Catamarca", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("FrenchGuianaTime", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("French Guiana Time", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Cayenne", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Cordoba", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Fortaleza", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Jujuy", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Maceio", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Mendoza", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("UruguayTime", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("Uruguay Time", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Montevideo", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("SurinameTime", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("Suriname Time", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Paramaribo", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Punta_Arenas", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Recife", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Rosario", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Santarem", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("America/Sao_Paulo", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("ChileTime", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("Chile Time", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("Antarctica/Palmer", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("RotheraTime", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("Rothera Time", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("Antarctica/Rothera", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("FalklandIs.Time", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("Atlantic/Stanley", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("BET", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("Brazil/East", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("GMT-02:00", "-02:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/GMT+2", "-02:00"); //No I18N
        timeZoneOffsetReturn.put("WesternGreenlandSummerTime", "-02:00"); //No I18N
        timeZoneOffsetReturn.put("Western Greenland Summer Time", "-02:00"); //No I18N
        timeZoneOffsetReturn.put("America/Godthab", "-02:00"); //No I18N
        timeZoneOffsetReturn.put("MiquelonDaylightTime", "-02:00"); //No I18N
        timeZoneOffsetReturn.put("Miquelon Daylight Time", "-02:00"); //No I18N
        timeZoneOffsetReturn.put("America/Miquelon", "-03:00"); //No I18N
        timeZoneOffsetReturn.put("FernandodeNoronhaTime", "-02:00"); //No I18N
        timeZoneOffsetReturn.put("Fernandode Noronha Time", "-02:00"); //No I18N
        timeZoneOffsetReturn.put("America/Noronha", "-02:00"); //No I18N
        timeZoneOffsetReturn.put("SouthGeorgiaStandardTime", "-02:00"); //No I18N
        timeZoneOffsetReturn.put("South Georgia Standard Time", "-02:00"); //No I18N
        timeZoneOffsetReturn.put("Atlantic/South_Georgia", "-02:00"); //No I18N
        timeZoneOffsetReturn.put("Brazil/DeNoronha", "-02:00"); //No I18N
        timeZoneOffsetReturn.put("CapeVerdeTime", "-01:00"); //No I18N
        timeZoneOffsetReturn.put("Cape Verde Time", "-01:00"); //No I18N
        timeZoneOffsetReturn.put("Atlantic/Cape_Verde", "-01:00"); //No I18N
        timeZoneOffsetReturn.put("GMT-01:00", "-01:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/GMT+1", "-01:00"); //No I18N
        timeZoneOffsetReturn.put("EasternGreenlandSummerTime", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Eastern Greenland SummerTime", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("America/Scoresbysund", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("AzoresSummerTime", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Azores Summer Time", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Atlantic/Azores", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("GreenwichMeanTime", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Greenwich Mean Time", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Abidjan", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("GhanaMeanTime", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Ghana Mean Time", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Accra", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Bamako", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Banjul", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Bissau", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Conakry", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Dakar", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Freetown", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Lome", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Monrovia", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Nouakchott", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Ouagadougou", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Timbuktu", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("America/Danmarkshavn", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Atlantic/Reykjavik", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Atlantic/St_Helena", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/GMT", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/GMT+0", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/GMT-0", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/GMT0", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/Greenwich", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("CoordinatedUniversalTime", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Coordinated Universal Time", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/UCT", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/UTC", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/Universal", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/Zulu", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("GMT", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("GMT0", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Greenwich", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Iceland", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("UCT", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("UTC", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Universal", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("Zulu", "+00:00"); //No I18N
        timeZoneOffsetReturn.put("GMT+01:00", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/GMT-1", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("WesternEuropeanSummerTime", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Western European Summer Time", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Casablanca", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/El_Aaiun", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Atlantic/Canary", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Atlantic/Faeroe", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Atlantic/Faroe", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Atlantic/Madeira", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("BritishSummerTime", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("British Summer Time", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Belfast", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Guernsey", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Isle_of_Man", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Jersey", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Lisbon", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/London", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("GB", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("GB-Eire", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Portugal", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("WET", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("CET", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("CentralEuropeanTime", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Central European Time", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Algiers", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("WesternAfricanTime", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Western African Time", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Bangui", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Brazzaville", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Douala", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Kinshasa", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Lagos", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Libreville", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Luanda", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Malabo", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Ndjamena", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Niamey", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Porto-Novo", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Sao_Tome", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Tunis", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Eire", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Dublin", "+01:00"); //No I18N
        timeZoneOffsetReturn.put("GMT+02:00", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/GMT-2", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("CentralEuropeanSummerTime", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Central European Summer Time", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Antarctica/Troll", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Ceuta", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Arctic/Longyearbyen", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Atlantic/Jan_Mayen", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("ECT", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Amsterdam", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Andorra", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Belgrade", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Berlin", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Bratislava", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Brussels", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Budapest", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Busingen", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Copenhagen", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Gibraltar", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Ljubljana", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Luxembourg", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Madrid", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Malta", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Monaco", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Oslo", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Paris", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Podgorica", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Prague", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Rome", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/San_Marino", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Sarajevo", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Skopje", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Stockholm", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Tirane", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Vaduz", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Vatican", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Vienna", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Warsaw", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Zagreb", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Zurich", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("MiddleEuropeSummerTime", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Middle Europe Summer Time", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("MET", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Poland", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("EasternEuropeanTime", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Eastern European Time", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("ART", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("CentralAfricanTime", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Central African Time", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Blantyre", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Bujumbura", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Cairo", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Gaborone", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Harare", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("SouthAfricaStandardTime", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("South Africa Standard Time", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Johannesburg", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Khartoum", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Kigali", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Lubumbashi", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Lusaka", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Maputo", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Maseru", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Mbabane", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Tripoli", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Windhoek", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("CAT", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Egypt", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Kaliningrad", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("Libya", "+02:00"); //No I18N
        timeZoneOffsetReturn.put("GMT+03:00", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/GMT-3", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("EasternEuropeanSummerTime", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Eastern European Summer Time", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Amman", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Beirut", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Damascus", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Famagusta", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Gaza", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Hebron", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("IsraelDaylightTime", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Israel Daylight Time", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Jerusalem", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Nicosia", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Tel_Aviv", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("EET", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Athens", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Bucharest", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Chisinau", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Helsinki", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Kiev", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Mariehamn", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Nicosia", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Riga", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Sofia", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Tallinn", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Tiraspol", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Uzhgorod", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Vilnius", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Zaporozhye", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Israel", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("EasternAfricanTime", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Eastern African Time", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Addis_Ababa", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Asmara", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Asmera", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Dar_es_Salaam", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Djibouti", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Juba", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Kampala", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Mogadishu", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Africa/Nairobi", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("SyowaTime", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Syowa Time", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Antarctica/Syowa", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("ArabiaStandardTime", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Arabia Standard Time", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Aden", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Baghdad", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Bahrain", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Istanbul", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Kuwait", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Qatar", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Riyadh", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("EAT", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Kirov", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Istanbul", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("MoscowStandardTime", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Moscow Standard Time", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Minsk", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Moscow", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Simferopol", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Volgograd", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Indian/Antananarivo", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Indian/Comoro", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Indian/Mayotte", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("Turkey", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("W-SU", "+03:00"); //No I18N
        timeZoneOffsetReturn.put("AzerbaijanTime", "+04:00"); //No I18N
        timeZoneOffsetReturn.put("Azerbaijan Time", "+04:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Baku", "+04:00"); //No I18N
        timeZoneOffsetReturn.put("GulfStandardTime", "+04:00"); //No I18N
        timeZoneOffsetReturn.put("Gulf Standard Time", "+04:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Dubai", "+04:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Muscat", "+04:00"); //No I18N
        timeZoneOffsetReturn.put("GeorgiaTime", "+04:00"); //No I18N
        timeZoneOffsetReturn.put("Georgia Time", "+04:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Tbilisi", "+04:00"); //No I18N
        timeZoneOffsetReturn.put("ArmeniaTime", "+04:00"); //No I18N
        timeZoneOffsetReturn.put("Armenia Time", "+04:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Yerevan", "+04:00"); //No I18N
        timeZoneOffsetReturn.put("GMT+04:00", "+04:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/GMT-4", "+04:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Astrakhan", "+04:00"); //No I18N
        timeZoneOffsetReturn.put("SamaraTime", "+04:00"); //No I18N
        timeZoneOffsetReturn.put("Samara Time", "+04:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Samara", "+04:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Saratov", "+04:00"); //No I18N
        timeZoneOffsetReturn.put("Europe/Ulyanovsk", "+04:00"); //No I18N
        timeZoneOffsetReturn.put("SeychellesTime", "+04:00"); //No I18N
        timeZoneOffsetReturn.put("Seychelles Time", "+04:00"); //No I18N
        timeZoneOffsetReturn.put("Indian/Mahe", "+04:00"); //No I18N
        timeZoneOffsetReturn.put("MauritiusTime", "+04:00"); //No I18N
        timeZoneOffsetReturn.put("Mauritius Time", "+04:00"); //No I18N
        timeZoneOffsetReturn.put("Indian/Mauritius", "+04:00"); //No I18N
        timeZoneOffsetReturn.put("ReunionTime", "+04:00"); //No I18N
        timeZoneOffsetReturn.put("Reunion Time", "+04:00"); //No I18N
        timeZoneOffsetReturn.put("Indian/Reunion", "+04:00"); //No I18N
        timeZoneOffsetReturn.put("NET", "+04:00"); //No I18N
        timeZoneOffsetReturn.put("MawsonTime", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("Mawson Time", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("Antarctica/Mawson", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("AqtauTime", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("Aqtau Time", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Aqtau", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("AqtobeTime", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("Aqtobe Time", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Aqtobe", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("TurkmenistanTime", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("Turkmenistan Time", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Ashgabat", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Ashkhabad", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("GMT+05:00", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Atyrau", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("TajikistanTime", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("Tajikistan Time", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Dushanbe", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("PakistanTime", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("Pakistan Time", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Karachi", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("OralTime", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Oral", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("UzbekistanTime", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("Uzbekistan Time", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Samarkand", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Tashkent", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("YekaterinburgTime", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("Yekaterinburg Time", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Yekaterinburg", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/GMT-5", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("Indian/Kerguelen", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("MaldivesTime", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("Maldives Time", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("Indian/Maldives", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("PLT", "+05:00"); //No I18N
        timeZoneOffsetReturn.put("VostokTime", "+06:00"); //No I18N
        timeZoneOffsetReturn.put("Vostok Time", "+06:00"); //No I18N
        timeZoneOffsetReturn.put("Antarctica/Vostok", "+06:00"); //No I18N
        timeZoneOffsetReturn.put("Alma-AtaTime", "+06:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Almaty", "+06:00"); //No I18N
        timeZoneOffsetReturn.put("KirgizstanTime", "+06:00"); //No I18N
        timeZoneOffsetReturn.put("Kirgizstan Time", "+06:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Bishkek", "+06:00"); //No I18N
        timeZoneOffsetReturn.put("BangladeshTime", "+06:00"); //No I18N
        timeZoneOffsetReturn.put("Bangladesh Time", "+06:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Dacca", "+06:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Dhaka", "+06:00"); //No I18N
        timeZoneOffsetReturn.put("XinjiangStandardTime", "+06:00"); //No I18N
        timeZoneOffsetReturn.put("Xinjiang Standard Time", "+06:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Kashgar", "+06:00"); //No I18N
        timeZoneOffsetReturn.put("OmskTime", "+06:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Omsk", "+06:00"); //No I18N
        timeZoneOffsetReturn.put("QyzylordaTime", "+06:00"); //No I18N
        timeZoneOffsetReturn.put("Qyzylorda Time", "+06:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Qyzylorda", "+06:00"); //No I18N
        timeZoneOffsetReturn.put("BhutanTime", "+06:00"); //No I18N
        timeZoneOffsetReturn.put("Bhutan Time", "+06:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Thimbu", "+06:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Thimphu", "+06:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Urumqi", "+06:00"); //No I18N
        timeZoneOffsetReturn.put("BST", "+06:00"); //No I18N
        timeZoneOffsetReturn.put("GMT+06:00", "+06:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/GMT-6", "+06:00"); //No I18N
        timeZoneOffsetReturn.put("IndianOceanTerritoryTime", "+06:00"); //No I18N
        timeZoneOffsetReturn.put("Indian Ocean Territory Time", "+06:00"); //No I18N
        timeZoneOffsetReturn.put("Indian/Chagos", "+06:00"); //No I18N
        timeZoneOffsetReturn.put("DavisTime", "+07:00"); //No I18N
        timeZoneOffsetReturn.put("Davis Time", "+07:00"); //No I18N
        timeZoneOffsetReturn.put("Antarctica/Davis", "+07:00"); //No I18N
        timeZoneOffsetReturn.put("IndochinaTime", "+07:00"); //No I18N
        timeZoneOffsetReturn.put("Indochina Time", "+07:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Bangkok", "+07:00"); //No I18N
        timeZoneOffsetReturn.put("GMT+07:00", "+07:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Barnaul", "+07:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Ho_Chi_Minh", "+07:00"); //No I18N
        timeZoneOffsetReturn.put("HovdTime", "+07:00"); //No I18N
        timeZoneOffsetReturn.put("Hovd Time", "+07:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Hovd", "+07:00"); //No I18N
        timeZoneOffsetReturn.put("WestIndonesiaTime", "+07:00"); //No I18N
        timeZoneOffsetReturn.put("West Indonesia Time", "+07:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Jakarta", "+07:00"); //No I18N
        timeZoneOffsetReturn.put("KrasnoyarskTime", "+07:00"); //No I18N
        timeZoneOffsetReturn.put("Krasnoyarsk Time", "+07:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Krasnoyarsk", "+07:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Novokuznetsk", "+07:00"); //No I18N
        timeZoneOffsetReturn.put("NovosibirskTime", "+07:00"); //No I18N
        timeZoneOffsetReturn.put("Novosibirsk Time", "+07:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Novosibirsk", "+07:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Phnom_Penh", "+07:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Pontianak", "+07:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Saigon", "+07:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Tomsk", "+07:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Vientiane", "+07:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/GMT-7", "+07:00"); //No I18N
        timeZoneOffsetReturn.put("ChristmasIslandTime", "+07:00"); //No I18N
        timeZoneOffsetReturn.put("Indian/Christmas", "+07:00"); //No I18N
        timeZoneOffsetReturn.put("VST", "+07:00"); //No I18N
        timeZoneOffsetReturn.put("AustralianWesternStandardTime", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Antarctica/Casey", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("BruneiTime", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Brunei Time", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Brunei", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("ChoibalsanTime", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Choibalsan Time", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Choibalsan", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("ChinaStandardTime", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("China Standard Time", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Chongqing", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Chungking", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Harbin", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("HongKongTime", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("HongKong Time", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Hong_Kong", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("IrkutskTime", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Irkutsk Time", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Irkutsk", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("MalaysiaTime", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Malaysia Time", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Kuala_Lumpur", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Kuching", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Macao", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Macau", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("CentralIndonesiaTime", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Central Indonesia Time", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Makassar", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("PhilippinesStandardTime", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Philippines Standard Time", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Manila", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Shanghai", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("SingaporeTime", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Singapore Time", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Singapore", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Taipei", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Ujung_Pandang", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("UlaanbaatarTime", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Ulaanbaatar Time", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Ulaanbaatar", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Ulan_Bator", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Australia/Perth", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Australia/West", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("CTT", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("GMT+08:00", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/GMT-8", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Hongkong", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("PRC", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("Singapore", "+08:00"); //No I18N
        timeZoneOffsetReturn.put("YakutskTime", "+09:00"); //No I18N
        timeZoneOffsetReturn.put("Yakutsk Time", "+09:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Chita", "+09:00"); //No I18N
        timeZoneOffsetReturn.put("Timor-LesteTime", "+09:00"); //No I18N
        timeZoneOffsetReturn.put("Timor-Leste Time", "+09:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Dili", "+09:00"); //No I18N
        timeZoneOffsetReturn.put("EastIndonesiaTime", "+09:00"); //No I18N
        timeZoneOffsetReturn.put("East Indonesia Time", "+09:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Jayapura", "+09:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Khandyga", "+09:00"); //No I18N
        timeZoneOffsetReturn.put("KoreaStandardTime", "+09:00"); //No I18N
        timeZoneOffsetReturn.put("Korea Standard Time", "+09:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Pyongyang", "+09:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Seoul", "+09:00"); //No I18N
        timeZoneOffsetReturn.put("JapanStandardTime", "+09:00"); //No I18N
        timeZoneOffsetReturn.put("Japan Standard Time", "+09:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Tokyo", "+09:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Yakutsk", "+09:00"); //No I18N
        timeZoneOffsetReturn.put("GMT+09:00", "+09:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/GMT-9", "+09:00"); //No I18N
        timeZoneOffsetReturn.put("JST", "+09:00"); //No I18N
        timeZoneOffsetReturn.put("Japan", "+09:00"); //No I18N
        timeZoneOffsetReturn.put("PalauTime", "+09:00"); //No I18N
        timeZoneOffsetReturn.put("Palau Time", "+09:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Palau", "+09:00"); //No I18N
        timeZoneOffsetReturn.put("ROK", "+09:00"); //No I18N
        timeZoneOffsetReturn.put("AustralianEasternStandardTime", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Australian Eastern Standard Time", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("NewSouthWales", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("AET", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Dumont-d'UrvilleTime", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Antarctica/DumontDUrville", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Ust-NeraTime", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Ust-Nera Time", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Ust-Nera", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("VladivostokTime", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Vladivostok Time", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Vladivostok", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Australia/ACT", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Queensland", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Australia/Brisbane", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Australia/Canberra", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Australia/Currie", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Tasmania", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Australia/Hobart", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Australia/Lindeman", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Victoria", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Australia/Melbourne", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Australia/NSW", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Australia/Queensland", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Australia/Sydney", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Australia/Tasmania", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Australia/Victoria", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("GMT+10:00", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/GMT-10", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("ChuukTime", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Chuuk Time", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Chuuk", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("ChamorroStandardTime", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Chamorro Standard Time", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("AEST", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Guam", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("PapuaNewGuineaTime", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Papua New Guinea Time", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Port_Moresby", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Saipan", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Truk", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Yap", "+10:00"); //No I18N
        timeZoneOffsetReturn.put("MacquarieIslandStandardTime", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("Macquarie Island Standard Time", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("Antarctica/Macquarie", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("MagadanTime", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("Magadan Time", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Magadan", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("SakhalinTime", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("Sakhalin Time", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Sakhalin", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("SrednekolymskTime", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("Srednekolymsk Time", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Srednekolymsk", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("GMT+11:00", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/GMT-11", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("BougainvilleStandardTime", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("Bougainville Standard Time", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Bougainville", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("VanuatuTime", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("Vanuatu Time", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Efate", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("SolomonIs.Time", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Guadalcanal", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("KosraeTime", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("Kosrae Time", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Kosrae", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("NorfolkTime", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("Norfolk Time", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Norfolk", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("NewCaledoniaTime", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("New Caledonia Time", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Noumea", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("PohnpeiTime", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("Pohnpei Time", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Pohnpei", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Ponape", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("SST", "+11:00"); //No I18N
        timeZoneOffsetReturn.put("NewZealandStandardTime", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("New Zealand Standard Time", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("Antarctica/McMurdo", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("Antarctica/South_Pole", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("AnadyrTime", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("Anadyr Time", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Anadyr", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("Petropavlovsk-KamchatskiTime", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("Asia/Kamchatka", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("GMT+12:00", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/GMT-12", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("MarshallIslandsTime", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("Marshall Islands Time", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("Kwajalein", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("NST", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("NZ", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Auckland", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("FijiTime", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("Fiji Time", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Fiji", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("TuvaluTime", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("Tuvalu Time", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Funafuti", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Kwajalein", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Majuro", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("NauruTime", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("Nauru Time", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Nauru", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("GilbertIs.Time", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Tarawa", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("WakeTime", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("Wake Time", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Wake", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("FutunaTime", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("Futuna Time", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Wallis", "+12:00"); //No I18N
        timeZoneOffsetReturn.put("GMT+13:00", "+13:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/GMT-13", "+13:00"); //No I18N
        timeZoneOffsetReturn.put("WestSamoaStandardTime", "+13:00"); //No I18N
        timeZoneOffsetReturn.put("West Samoa Standard Time", "+13:00"); //No I18N
        timeZoneOffsetReturn.put("MIT", "+13:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Apia", "+13:00"); //No I18N
        timeZoneOffsetReturn.put("PhoenixIs.Time", "+13:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Enderbury", "+13:00"); //No I18N
        timeZoneOffsetReturn.put("TokelauTime", "+13:00"); //No I18N
        timeZoneOffsetReturn.put("Tokelau Time", "+13:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Fakaofo", "+13:00"); //No I18N
        timeZoneOffsetReturn.put("TongaTime", "+13:00"); //No I18N
        timeZoneOffsetReturn.put("Tonga Time", "+13:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Tongatapu", "+13:00"); //No I18N
        timeZoneOffsetReturn.put("GMT+14:00", "+14:00"); //No I18N
        timeZoneOffsetReturn.put("Etc/GMT-14", "+14:00"); //No I18N
        timeZoneOffsetReturn.put("LineIs.Time", "+14:00"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Kiritimati", "+14:00"); //No I18N
        timeZoneOffsetReturn.put("MarquesasTime", "-09:30"); //No I18N
        timeZoneOffsetReturn.put("Marquesas Time", "-09:30"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Marquesas", "-09:30"); //No I18N
        timeZoneOffsetReturn.put("Newfoundland Daylight Time", "-02:30"); //No I18N
        timeZoneOffsetReturn.put("America/St_Johns", "-03:30"); //No I18N
        timeZoneOffsetReturn.put("CNT", "-03:30"); //No I18N
        timeZoneOffsetReturn.put("Canada/Newfoundland", "-03:30"); //No I18N
        timeZoneOffsetReturn.put("Iran Daylight Time", "+04:30"); //No I18N
        timeZoneOffsetReturn.put("Asia/Tehran", "+04:30"); //No I18N
        timeZoneOffsetReturn.put("Iran", "+03:30"); //No I18N
        timeZoneOffsetReturn.put("AfghanistanTime", "+04:30"); //No I18N
        timeZoneOffsetReturn.put("Afghanistan Time", "+04:30"); //No I18N
        timeZoneOffsetReturn.put("Asia/Kabul", "+04:30"); //No I18N
        timeZoneOffsetReturn.put("IndiaStandardTime", "+05:30"); //No I18N
        timeZoneOffsetReturn.put("India Standard Time", "+05:30"); //No I18N
        timeZoneOffsetReturn.put("Indian Standard Time", "+05:30"); //No I18N
        timeZoneOffsetReturn.put("Indian Time", "+05:30"); //No I18N
        timeZoneOffsetReturn.put("Asia/Calcutta", "+05:30"); //No I18N
        timeZoneOffsetReturn.put("Asia/Colombo", "+05:30"); //No I18N
        timeZoneOffsetReturn.put("Asia/Kolkata", "+05:30"); //No I18N
        timeZoneOffsetReturn.put("IST", "+05:30"); //No I18N
        timeZoneOffsetReturn.put("NepalTime", "+05:45"); //No I18N
        timeZoneOffsetReturn.put("Nepal Time", "+05:45"); //No I18N
        timeZoneOffsetReturn.put("Asia/Kathmandu", "+05:45"); //No I18N
        timeZoneOffsetReturn.put("Asia/Katmandu", "+05:45"); //No I18N
        timeZoneOffsetReturn.put("MyanmarTime", "+06:30"); //No I18N
        timeZoneOffsetReturn.put("Myanmar Time", "+06:30"); //No I18N
        timeZoneOffsetReturn.put("Asia/Rangoon", "+06:30"); //No I18N
        timeZoneOffsetReturn.put("Asia/Yangon", "+06:30"); //No I18N
        timeZoneOffsetReturn.put("CocosIslandsTime", "+06:30"); //No I18N
        timeZoneOffsetReturn.put("Cocos Islands Time", "+06:30"); //No I18N
        timeZoneOffsetReturn.put("Indian/Cocos", "+06:30"); //No I18N
        timeZoneOffsetReturn.put("AustralianCentralWesternStandardTime", "+08:45"); //No I18N
        timeZoneOffsetReturn.put("Australian Central Western Standard Time", "+08:45"); //No I18N
        timeZoneOffsetReturn.put("Australia/Eucla", "+08:45"); //No I18N
        timeZoneOffsetReturn.put("AustralianCentralStandardTime", "+09:30"); //No I18N
        timeZoneOffsetReturn.put("Australian Central Standard Time", "+09:30"); //No I18N
        timeZoneOffsetReturn.put("NorthernTerritory", "+09:30"); //No I18N
        timeZoneOffsetReturn.put("ACT", "+09:30"); //No I18N
        timeZoneOffsetReturn.put("SouthAustralia", "+09:30"); //No I18N
        timeZoneOffsetReturn.put("Australia/Adelaide", "+09:30"); //No I18N
        timeZoneOffsetReturn.put("SouthAustralia/NewSouthWales", "+09:30"); //No I18N
        timeZoneOffsetReturn.put("Australia/Broken_Hill", "+09:30"); //No I18N
        timeZoneOffsetReturn.put("Australia/Darwin", "+09:30"); //No I18N
        timeZoneOffsetReturn.put("Australia/North", "+09:30"); //No I18N
        timeZoneOffsetReturn.put("Australia/South", "+09:30"); //No I18N
        timeZoneOffsetReturn.put("Australia/Yancowinna", "+09:30"); //No I18N
        timeZoneOffsetReturn.put("LordHoweStandardTime", "+10:30"); //No I18N
        timeZoneOffsetReturn.put("Lord Howe Standard Time", "+10:30"); //No I18N
        timeZoneOffsetReturn.put("Australia/LHI", "+10:30"); //No I18N
        timeZoneOffsetReturn.put("Australia/Lord_Howe", "+10:30"); //No I18N
        timeZoneOffsetReturn.put("ChathamStandardTime", "+12:45"); //No I18N
        timeZoneOffsetReturn.put("Chatham Standard Time", "+12:45"); //No I18N
        timeZoneOffsetReturn.put("NZ-CHAT", "+12:45"); //No I18N
        timeZoneOffsetReturn.put("Pacific/Chatham", "+12:45"); //No I18N

        dayLightOffsetReturn.put("-12:00", "-11:00"); //No I18N
        dayLightOffsetReturn.put("-11:00", "-10:00"); //No I18N
        dayLightOffsetReturn.put("-10:00", "-09:00"); //No I18N
        dayLightOffsetReturn.put("-09:00", "-08:00"); //No I18N
        dayLightOffsetReturn.put("-08:00", "-07:00"); //No I18N
        dayLightOffsetReturn.put("-07:00", "-06:00"); //No I18N
        dayLightOffsetReturn.put("-06:00", "-05:00"); //No I18N
        dayLightOffsetReturn.put("-05:00", "-04:00"); //No I18N
        dayLightOffsetReturn.put("-04:00", "-03:00"); //No I18N
        dayLightOffsetReturn.put("-03:00", "-02:00"); //No I18N
        dayLightOffsetReturn.put("-02:00", "-01:00"); //No I18N
        dayLightOffsetReturn.put("-01:00", "+00:00"); //No I18N
        dayLightOffsetReturn.put("+00:00", "+01:00"); //No I18N
        dayLightOffsetReturn.put("+01:00", "+02:00"); //No I18N
        dayLightOffsetReturn.put("+02:00", "+03:00"); //No I18N
        dayLightOffsetReturn.put("+03:00", "+04:00"); //No I18N
        dayLightOffsetReturn.put("+04:00", "+05:00"); //No I18N
        dayLightOffsetReturn.put("+05:00", "+06:00"); //No I18N
        dayLightOffsetReturn.put("+06:00", "+07:00"); //No I18N
        dayLightOffsetReturn.put("+07:00", "+08:00"); //No I18N
        dayLightOffsetReturn.put("+08:00", "+09:00"); //No I18N
        dayLightOffsetReturn.put("+09:00", "+10:00"); //No I18N
        dayLightOffsetReturn.put("+10:00", "+11:00"); //No I18N
        dayLightOffsetReturn.put("+11:00", "+12:00"); //No I18N
        dayLightOffsetReturn.put("+12:00", "+13:00"); //No I18N
        dayLightOffsetReturn.put("+13:00", "+14:00"); //No I18N
        dayLightOffsetReturn.put("+14:00", "+14:00"); //No I18N
        dayLightOffsetReturn.put("-09:30", "-08:30"); //No I18N
        dayLightOffsetReturn.put("-02:30", "-01:30"); //No I18N
        dayLightOffsetReturn.put("+02:30", "+03:30"); //No I18N
        dayLightOffsetReturn.put("+03:30", "+04:30"); //No I18N
        dayLightOffsetReturn.put("+04:30", "+05:30"); //No I18N
        dayLightOffsetReturn.put("+05:30", "+06:30"); //No I18N
        dayLightOffsetReturn.put("+05:45", "+06:45"); //No I18N
        dayLightOffsetReturn.put("+06:30", "+07:30"); //No I18N
        dayLightOffsetReturn.put("+08:45", "+09:45"); //No I18N
        dayLightOffsetReturn.put("+09:30", "+10:30"); //No I18N
        dayLightOffsetReturn.put("+10:30", "+11:30"); //No I18N
        dayLightOffsetReturn.put("+12:45", "+13:45"); //No I18N
    }

    public static String timeZoneDetector(String dateText, Date referenceDate) {
        String timezoneFinal = null;
        String timezoneOffset = null;
        for (String timezone : timeZoneList) {
            if (isContain(dateText, timezone)) {
                timezoneFinal = timezone;
            }
        }

        if (timezoneFinal != null) {
            boolean daylightRefDateStart = TimeZone.getTimeZone(timezoneFinal)
                    .inDaylightTime(new Date(referenceDate.getTime()));
            timezoneOffset = timeZoneOffsetReturn.get(timezoneFinal);
            if (daylightRefDateStart) {
                timezoneOffset = dayLightOffsetReturn.get(timezoneOffset);
                LOGGER.info("TimeZoneExtractor :: TimeZone is in Daylight");
            }
        }
        return timezoneOffset;
    }

    private static boolean isContain(String dateText, String timezone) {
        String pattern = "\\b" + timezone + "\\b";  //No I18N
        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(dateText);
        return m.find();
    }

    public static DateTimeOffsetReturn referenceDateExtractor(Date referenceDate, HawkingConfiguration configuration, String parsedText) {
        TimeZone userTimeZone;
        String timeZoneOffSet = timeZoneDetector(parsedText, referenceDate);
        try {
            if (timeZoneOffSet != null) {
                isTimeZonePresent = true;
                userTimeZone = TimeZone.getTimeZone("GMT" + timeZoneOffSet); //No I18N
                zonedDateFormat.setTimeZone(userTimeZone);
                return new DateTimeOffsetReturn(dateFormat.parse(zonedDateFormat.format(referenceDate)), timeZoneOffSet);

            } else if (!configuration.getTimeZone().equals("")) {
                isTimeZonePresent = false;
                userTimeZone = TimeZone.getTimeZone(configuration.getTimeZone());
                zonedDateFormat.setTimeZone(userTimeZone);
                timeZoneOffSet = OffsetDateTime.now(userTimeZone.toZoneId()).getOffset().toString();
                return new DateTimeOffsetReturn(dateFormat.parse(zonedDateFormat.format(referenceDate)), timeZoneOffSet.equals("Z") ? "+00:00" : timeZoneOffSet);

            } else {
                isTimeZonePresent = false;
                userTimeZone = TimeZone.getDefault();
                zonedDateFormat.setTimeZone(userTimeZone);
                OffsetDateTime odt = OffsetDateTime.now(ZoneId.systemDefault());
                ZoneOffset zoneOffset = odt.getOffset();
                return new DateTimeOffsetReturn(dateFormat.parse(zonedDateFormat.format(referenceDate)), zoneOffset.toString());
            }
        } catch (Exception e) {
            LOGGER.info("TimeZoneExtractor :: Exception in Hawking :: Unable to parse Date time component");
            return new DateTimeOffsetReturn(referenceDate, null);
        }
    }

    public static Date offsetDateConverter(Long date, String offSet) {
        String formattedDate = dateFormat.format(new Date(date));
        formattedDate = formattedDate + offSet;
        try {
            return zonedDateFormat2.parse(formattedDate);

        } catch (ParseException e) {
            LOGGER.info("TimeZoneExtractor :: Exception in Hawking :: Unable to parse Date time component");
            return null;
        }
    }

    public static String dateFormatter(Long date) {
        return dateFormat.format(date);
    }
}
