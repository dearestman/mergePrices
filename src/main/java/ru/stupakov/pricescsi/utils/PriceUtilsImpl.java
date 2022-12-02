package ru.stupakov.pricescsi.utils;

import org.springframework.stereotype.Component;
import ru.stupakov.pricescsi.models.Price;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Stupakov D. L.
 **/
@Component
public class PriceUtilsImpl implements PriceUtils {
    @Override
    public List<Price> findByProductCodeAndNumberAndDepart(List<Price> prices, String productCode, Integer number, Integer depart) {
        return prices.stream()
                .filter(price -> price.getProductCode().equals(productCode)
                        &&price.getNumber().equals(number)
                        &&price.getDepart().equals(depart))
                .collect(Collectors.toList());
    }

    @Override
    public List<Price> findByDateBeginAfterAndDateEndBeforeOrderByDateBeginDesc(List<Price> prices, LocalDateTime dateBegin, LocalDateTime dateEnd) {
        return prices.stream()
                .filter(price -> price.getDateBegin().isAfter(dateBegin.minusNanos(1))
                        &&price.getDateEnd().isBefore(dateBegin.plusNanos(1)))
                .sorted(Comparator.comparing(Price::getDateBegin).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public void save(Price price, Map<String, List<Price>> priceGroupByProductCodeAndNumberAndDepart) {
        priceGroupByProductCodeAndNumberAndDepart.computeIfAbsent(
                convertToMapKeyPrice(price),
                k -> new ArrayList<>()
        );
        priceGroupByProductCodeAndNumberAndDepart.get(
                convertToMapKeyPrice(price)).add(price);
    }

    @Override
    public void saveAll(List<Price> newPrices, Map<String, List<Price>> priceGroupByProductCodeAndNumberAndDepart) {
        newPrices.forEach(price -> save(price, priceGroupByProductCodeAndNumberAndDepart));
    }

    @Override
    public List<Price> convertToPriceList(Map<String, List<Price>> priceGroupByProductCodeAndNumberAndDepart) {
        List<Price> prices = new ArrayList<>();
        priceGroupByProductCodeAndNumberAndDepart.forEach((s, mapPrices) -> {
            prices.addAll(mapPrices.stream()
                    .sorted(Comparator.comparing(Price::getDateBegin))
                    .collect(Collectors.toList()));
        });

        return prices;
    }

    private String convertToMapKeyPrice(Price price){
        return " " + price.getProductCode() +
                " " + price.getNumber() +
                " " + price.getDepart();
    }

    public void mergePricesInList(Map<String, List<Price>> priceGroupByProductCodeAndNumberAndDepart) {
        priceGroupByProductCodeAndNumberAndDepart.forEach((k, v) -> {
            List<Price> resultList = new ArrayList<>();
            for (int i = 1; i < v.size(); i++) {
                Price firstPrice = v.get(i - 1);
                Price secondPrice = v.get(i);
                if (firstPrice.getValue().equals(secondPrice.getValue())
                        && firstPrice.getDateEnd().plusNanos(1).isBefore(secondPrice.getDateEnd().minusNanos(1))
                        && firstPrice.getDateEnd().minusNanos(1).isAfter((secondPrice.getDateBegin().plusNanos(1)))) {
                    secondPrice.setDateBegin(
                            (firstPrice.getDateBegin().isBefore(secondPrice.getDateBegin())
                                    ? firstPrice.getDateBegin()
                                    : secondPrice.getDateBegin())
                    );
                    firstPrice.setDateEnd(
                            (firstPrice.getDateEnd().isAfter(secondPrice.getDateEnd())
                                    ? firstPrice.getDateEnd()
                                    : secondPrice.getDateEnd())
                    );
                    resultList.add(secondPrice);

                } else if (firstPrice.getDateEnd().minusNanos(1).isAfter(secondPrice.getDateEnd().plusNanos(1))
                        && firstPrice.getDateEnd().minusNanos(1).isAfter((secondPrice.getDateBegin().plusNanos(1)))) {
                    Price newPrice = new Price(firstPrice);
                    newPrice.setDateBegin(secondPrice.getDateEnd());
                    firstPrice.setDateEnd(secondPrice.getDateBegin());
                    resultList.addAll(Arrays.asList(firstPrice, secondPrice, newPrice));

                } else if (firstPrice.getDateEnd().plusNanos(1).isBefore(secondPrice.getDateEnd().minusNanos(1))
                        && firstPrice.getDateEnd().minusNanos(1).isAfter((secondPrice.getDateBegin().plusNanos(1)))) {

                    firstPrice.setDateEnd(secondPrice.getDateBegin());
                    resultList.addAll(Arrays.asList(firstPrice, secondPrice));
                } else
                    resultList.addAll(v);

            }
            priceGroupByProductCodeAndNumberAndDepart.put(k, resultList);
        });
    }
}
