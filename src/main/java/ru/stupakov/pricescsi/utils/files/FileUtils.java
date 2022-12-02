package ru.stupakov.pricescsi.utils.files;

import ru.stupakov.pricescsi.models.Price;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 * @author Stupakov D. L.
 **/
public interface FileUtils {
    void readPrice(List<Price> oldPrice, BufferedReader bufferedReader) throws IOException;

    void write(String string) throws IOException;

}
