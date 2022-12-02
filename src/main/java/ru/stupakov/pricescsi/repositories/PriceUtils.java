package ru.stupakov.mergeprices.repositories;

import org.springframework.stereotype.Repository;
import ru.stupakov.mergeprices.models.Price;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Stupakov D. L.
 **/
public interface PriceRepository {

    List<Price> findByProductCodeAndNumberAndDepart(List<Price> prices, String productCode, Integer number, Integer depart);

    List<Price> findByDateBeginAfterAndDateEndBeforeOrderByDateBeginDesc(List<Price> prices, LocalDateTime dateBegin, LocalDateTime dateEnd);

    void save(Price price);

    void saveAll(List<Price> newPrices);

    List<Price> findAll();
}
