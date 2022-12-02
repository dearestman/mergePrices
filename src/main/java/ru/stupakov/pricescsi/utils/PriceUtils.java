package ru.stupakov.pricescsi.utils;

import lombok.experimental.UtilityClass;
import ru.stupakov.pricescsi.models.Price;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author Stupakov D. L.
 **/
public interface PriceUtils {

    List<Price> findByProductCodeAndNumberAndDepart(List<Price> prices, String productCode, Integer number, Integer depart);

    List<Price> findByDateBeginAfterAndDateEndBeforeOrderByDateBeginDesc(List<Price> prices, LocalDateTime dateBegin, LocalDateTime dateEnd);

    void save(Price price, Map<String, List<Price>> priceGroupByProductCodeAndNumberAndDepart);

    void saveAll(List<Price> newPrices, Map<String, List<Price>> priceGroupByProductCodeAndNumberAndDepart);

    List<Price> convertToPriceList(Map<String, List<Price>> priceGroupByProductCodeAndNumberAndDepart);
}
