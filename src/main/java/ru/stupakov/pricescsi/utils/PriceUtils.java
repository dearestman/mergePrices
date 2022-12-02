package ru.stupakov.pricescsi.utils;

import ru.stupakov.pricescsi.models.Price;

import java.util.List;
import java.util.Map;

/**
 * @author Stupakov D. L.
 **/
public interface PriceUtils {

    void save(Price price, Map<String, List<Price>> priceGroupByProductCodeAndNumberAndDepart);

    void saveAll(List<Price> newPrices, Map<String, List<Price>> priceGroupByProductCodeAndNumberAndDepart);

    List<Price> convertToPriceList(Map<String, List<Price>> priceGroupByProductCodeAndNumberAndDepart);
}
