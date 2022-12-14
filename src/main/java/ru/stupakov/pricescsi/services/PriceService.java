package ru.stupakov.pricescsi.services;

import lombok.RequiredArgsConstructor;
import ru.stupakov.pricescsi.models.Price;
import ru.stupakov.pricescsi.utils.PriceUtilsImpl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Stupakov D. L.
 **/
@RequiredArgsConstructor
public class PriceService {

    private final PriceUtilsImpl priceUtils;

    public List<Price> mergePrice(List<Price> oldPrice, List<Price> newPrice){

        Map<String, List<Price>> priceGroupByProductCodeAndNumberAndDepart = new TreeMap<>();
        //Добавляем в Map группировку по Product code, number, depart, так как про сортировку ничего не было написано,
        // а в примере есть сортировка, то использую TreeMap
        priceUtils.saveAll(oldPrice, priceGroupByProductCodeAndNumberAndDepart);
        priceUtils.saveAll(newPrice, priceGroupByProductCodeAndNumberAndDepart);

        priceUtils.mergePricesInList(priceGroupByProductCodeAndNumberAndDepart);

        return priceUtils.convertToPriceList(priceGroupByProductCodeAndNumberAndDepart);

    }
}
