package ru.stupakov.pricescsi.services;

import ru.stupakov.pricescsi.models.Price;
import ru.stupakov.pricescsi.utils.PriceUtilsImpl;
import ru.stupakov.pricescsi.utils.files.FileUtilsImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stupakov D. L.
 **/
public class AppService {

    public static void startApp() throws IOException {
        FileUtilsImpl fileUtils = new FileUtilsImpl();
        PriceService priceService = new PriceService(new PriceUtilsImpl());


        List<Price> oldPrice = new ArrayList<>();
        List<Price> newPrice = new ArrayList<>();

        File file = new File("input.txt");
        if (!file.exists()) {
            System.out.println("Файл не найден!");
            return;
        }
        BufferedReader bufferedReader =
                new BufferedReader(
                        new FileReader("input.txt"));

        fileUtils.readPrice(oldPrice, bufferedReader);
        fileUtils.readPrice(newPrice, bufferedReader);

        StringBuilder result = new StringBuilder();
        priceService.mergePrice(oldPrice,newPrice).forEach(price -> result.append(price.priceToCSV()));
        fileUtils.write(result.toString());


    }
}
