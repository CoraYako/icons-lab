package com.icons.util;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Pattern;

public class ApiUtils {
    public static final String CONTINENT_BASE_URL = "/api/v1/continents";

    public static final String COUNTRY_BASE_URL = "/api/v1/countries";

    public static final String ICON_BASE_URL = "/api/v1/icons";

    public static final String URI_RESOURCE = "/{id}";

    public static final Date VALID_TOKEN_TIME = new Date(System.currentTimeMillis() + 1000 * 60 * 60);
    public static final DateTimeFormatter OF_PATTERN = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    public static final int ELEMENTS_PER_PAGE = 10;
    public static final String REGEX_PATTERN = "/(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}/";
    private static final Pattern UUID_REGEX_PATTERN = Pattern
            .compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    public static boolean isNotValidUUID(String uuid) {
        return !UUID_REGEX_PATTERN.matcher(uuid).matches();
    }

    public static boolean isASC(String order) {
        return order.compareToIgnoreCase("ASC") == 0;
    }
}
