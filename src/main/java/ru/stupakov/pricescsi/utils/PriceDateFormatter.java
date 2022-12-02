package ru.stupakov.pricescsi.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Stupakov D. L.
 **/
public class PriceDateFormatter {
    public static LocalDateTime formatUserDateToLocalDateTime(String userDateFormat){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return LocalDateTime.parse(userDateFormat, formatter);
    };
}
