package ru.stupakov.pricescsi.utils.files;

import ru.stupakov.pricescsi.models.Price;
import ru.stupakov.pricescsi.utils.formatters.PriceDateFormatter;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author Stupakov D. L.
 **/
public class FileUtilsImpl implements FileUtils {


    @Override
    public void readPrice(List<Price> oldPrice, BufferedReader bufferedReader) throws IOException {
        int countPrice = Integer.parseInt(bufferedReader.readLine());
        for (int i = 0; i < countPrice; i++) {
            String price = bufferedReader.readLine();
            oldPrice.add(new Price(
                    price.split(",")[0],
                    Integer.parseInt(price.split(",")[1]),
                    Integer.parseInt(price.split(",")[2]),
                    PriceDateFormatter.formatUserDateToLocalDateTime(price.split(",")[3]),
                    PriceDateFormatter.formatUserDateToLocalDateTime(price.split(",")[4]),
                    Long.parseLong(price.split(",")[5])
            ));
        }
    }


    @Override
    public void write(String string) throws IOException {
        FileWriter fileWriter = new FileWriter("output.txt");
        fileWriter.flush();
        fileWriter.write(string);
        fileWriter.close();

    }


}
