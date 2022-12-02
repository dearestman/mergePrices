package ru.stupakov.pricescsi;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.stupakov.pricescsi.models.Price;
import ru.stupakov.pricescsi.services.PriceService;
import ru.stupakov.pricescsi.utils.PriceDateFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Stupakov D. L.
 **/
@SpringBootTest
class MergePriceTest {

    @Autowired
    private PriceService priceService;


    @Test
    void testMergeWithSameValue(){
        ArrayList<Price> oldPrice = new ArrayList<>(Arrays.asList(
                new Price(
                        new Price(
                                "122856",
                                1,
                                1,
                                PriceDateFormatter.formatUserDateToLocalDateTime("01.01.2013 00:00:00"),
                                PriceDateFormatter.formatUserDateToLocalDateTime("31.01.2013 23:59:59"),
                                11000L
                        ))
                )
        );

        ArrayList<Price> newPrice = new ArrayList<>(Collections.singletonList(
                new Price(
                        new Price(
                                "122856",
                                1,
                                1,
                                PriceDateFormatter.formatUserDateToLocalDateTime("04.01.2013 00:00:00"),
                                PriceDateFormatter.formatUserDateToLocalDateTime("31.02.2013 23:59:59"),
                                11000L
                        ))
        ));

        ArrayList<Price> resultPrice = new ArrayList<>(Collections.singletonList(
                new Price(
                        new Price(
                                "122856",
                                1,
                                1,
                                PriceDateFormatter.formatUserDateToLocalDateTime("01.01.2013 00:00:00"),
                                PriceDateFormatter.formatUserDateToLocalDateTime("31.02.2013 23:59:59"),
                                11000L
                        ))
        ));

        assertEquals(resultPrice, priceService.mergePrice(oldPrice, newPrice));
    }


    @Test
    void mainTestByTask() {
        ArrayList<Price> oldPrice = new ArrayList<>();
        oldPrice.add(
                new Price(
                        "122856",
                        1,
                        1,
                        PriceDateFormatter.formatUserDateToLocalDateTime("01.01.2013 00:00:00"),
                        PriceDateFormatter.formatUserDateToLocalDateTime("31.01.2013 23:59:59"),
                        11000L
                ));

        oldPrice.add(
                new Price(
                        "122856",
                        2,
                        1,
                        PriceDateFormatter.formatUserDateToLocalDateTime("10.01.2013 00:00:00"),
                        PriceDateFormatter.formatUserDateToLocalDateTime("20.01.2013 23:59:59"),
                        99000L
                ));

        oldPrice.add(
                new Price(
                        "6654",
                        1,
                        2,
                        PriceDateFormatter.formatUserDateToLocalDateTime("01.01.2013 00:00:00"),
                        PriceDateFormatter.formatUserDateToLocalDateTime("31.01.2013 00:00:00"),
                        5000L
                ));

        ArrayList<Price> newPrice = new ArrayList<>();
        newPrice.add(
                new Price(
                        "122856",
                        1,
                        1,
                        PriceDateFormatter.formatUserDateToLocalDateTime("20.01.2013 00:00:00"),
                        PriceDateFormatter.formatUserDateToLocalDateTime("20.02.2013 23:59:59"),
                        11000L
                ));

        newPrice.add(
                new Price(
                        "122856",
                        2,
                        1,
                        PriceDateFormatter.formatUserDateToLocalDateTime("15.01.2013 00:00:00"),
                        PriceDateFormatter.formatUserDateToLocalDateTime("25.01.2013 23:59:59"),
                        92000L
                ));

        newPrice.add(
                new Price(
                        "6654",
                        1,
                        2,
                        PriceDateFormatter.formatUserDateToLocalDateTime("12.01.2013 00:00:00"),
                        PriceDateFormatter.formatUserDateToLocalDateTime("13.01.2013 00:00:00"),
                        4000L
                ));

        ArrayList<Price> resultPrice = new ArrayList<>();
        resultPrice.add(
                new Price(
                        "122856",
                        1,
                        1,
                        PriceDateFormatter.formatUserDateToLocalDateTime("01.01.2013 00:00:00"),
                        PriceDateFormatter.formatUserDateToLocalDateTime("20.02.2013 23:59:59"),
                        11000L
                ));

        resultPrice.add(
                new Price(
                        "122856",
                        2,
                        1,
                        PriceDateFormatter.formatUserDateToLocalDateTime("10.01.2013 00:00:00"),
                        PriceDateFormatter.formatUserDateToLocalDateTime("15.01.2013 00:00:00"),
                        99000L
                ));

        resultPrice.add(
                new Price(
                        "122856",
                        2,
                        1,
                        PriceDateFormatter.formatUserDateToLocalDateTime("15.01.2013 00:00:00"),
                        PriceDateFormatter.formatUserDateToLocalDateTime("25.01.2013 23:59:59"),
                        92000L
                ));

        resultPrice.add(
                new Price(
                        "6654",
                        1,
                        2,
                        PriceDateFormatter.formatUserDateToLocalDateTime("01.01.2013 00:00:00"),
                        PriceDateFormatter.formatUserDateToLocalDateTime("12.01.2013 00:00:00"),
                        5000L
                ));

        resultPrice.add(
                new Price(
                        "6654",
                        1,
                        2,
                        PriceDateFormatter.formatUserDateToLocalDateTime("12.01.2013 00:00:00"),
                        PriceDateFormatter.formatUserDateToLocalDateTime("13.01.2013 00:00:00"),
                        4000L
                ));

        resultPrice.add(
                new Price(
                        "6654",
                        1,
                        2,
                        PriceDateFormatter.formatUserDateToLocalDateTime("13.01.2013 00:00:00"),
                        PriceDateFormatter.formatUserDateToLocalDateTime("31.01.2013 00:00:00"),
                        5000L
                ));

        assertEquals(resultPrice, priceService.mergePrice(oldPrice, newPrice));
//        assertEquals(resultPrice, priceService.mergePrice(oldPrice, newPrice));
    }
}