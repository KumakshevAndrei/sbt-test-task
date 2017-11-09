package sbt.test.utils;

import java.util.regex.Pattern;

/**
 * Created by Home on 09.11.2017.
 */
public enum LimitType {

    FROM("\\s*" +
            "(от|" +
            "(нижний|минимальный)\\s+предел\\s+(цены|суммы|стоимости)?|" +
            "(нижняя|минимальная)\\s+(цена|сумма|стоимость)?|" +
            "минимум\\s+(цены|суммы|стоимости)?)" +
            "\\s*",
            ".//input[contains(@class, 'input__control') and @id = 'glf-pricefrom-var']"),
    TO("\\s*" +
            "(до|" +
            "(верхний|максимальный)\\s+предел\\s+(цены|суммы|стоимости)?|" +
            "(верхняя|максимальная)\\s+(цена|сумма|стоимость)?|" +
            "максимум\\s+(цены|суммы|стоимости)?)" +
            "\\s*",
            ".//input[contains(@class, 'input__control') and @id = 'glf-priceto-var']");

    private Pattern displayingNamePattern;
    private String xpath;

    LimitType(String displayingNameRegex, String xpath) {
        this.displayingNamePattern = Pattern.compile(displayingNameRegex);
        this.xpath = xpath;
    }

    public static LimitType getByDisplayingName(String displayingName) {
        if (FROM.displayingNamePattern.matcher(displayingName).matches()) {
            return FROM;
        } else if (TO.displayingNamePattern.matcher(displayingName).matches()) {
            return TO;
        } else return null;

    }

    public String getXpath() {
        return xpath;
    }
}
