package ru.stupakov.pricescsi.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stupakov.pricescsi.models.Price;
import ru.stupakov.pricescsi.utils.PriceUtilsImpl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Stupakov D. L.
 **/
@Service
@RequiredArgsConstructor
public class PriceService {

    private final PriceUtilsImpl priceUtils;

    public List<Price> mergePrice(List<Price> oldPrice, List<Price> newPrice){

        Map<String, List<Price>> priceGroupByProductCodeAndNumberAndDepart = new TreeMap<>();

        priceUtils.saveAll(oldPrice, priceGroupByProductCodeAndNumberAndDepart);
        priceUtils.saveAll(newPrice, priceGroupByProductCodeAndNumberAndDepart);
        priceUtils.mergePricesInList(priceGroupByProductCodeAndNumberAndDepart);

        return priceUtils.convertToPriceList(priceGroupByProductCodeAndNumberAndDepart);

    }
}
