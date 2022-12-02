package ru.stupakov.pricescsi.utils;

import ru.stupakov.pricescsi.models.Price;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Stupakov D. L.
 **/
public class PriceUtilsImpl implements PriceUtils {

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

    /**
     * Метод нужен для преобразования сгруппированных данных bp Map в List с результатом
     * @param priceGroupByProductCodeAndNumberAndDepart - сгруппированные данные в Map, ключ (product code + " " + number + " " + depart
     * @return
     */
    @Override
    public List<Price> convertToPriceList(Map<String, List<Price>> priceGroupByProductCodeAndNumberAndDepart) {
        List<Price> prices = new ArrayList<>();
        priceGroupByProductCodeAndNumberAndDepart.forEach((s, mapPrices) -> prices.addAll(mapPrices.stream()
                    .sorted(Comparator.comparing(Price::getDateBegin))
                    .collect(Collectors.toList()))
        );
        return prices;
    }


    private String convertToMapKeyPrice(Price price){
        return " " + price.getProductCode() +
                " " + price.getNumber() +
                " " + price.getDepart();
    }

    /**
     * Метод проходится по группировке цен, и в зависимости от ситуации между двумя ценами:
     * 1. Если значения цены равны и периоды как-то пересикаются, то они объеденяются
     * 2. Если значения цены не равны и 1 период происходит полностью внутри другого, то создается доп цена пример: 50	60	50
     * 3. Если значения цены не равны и 1 период только пересекается, с другим периодом, тогда меняется их начало и конец. Пример: 20 40
     * @param priceGroupByProductCodeAndNumberAndDepart
     */
    public void mergePricesInList(Map<String, List<Price>> priceGroupByProductCodeAndNumberAndDepart) {
        priceGroupByProductCodeAndNumberAndDepart.forEach((k, v) -> {
            List<Price> resultList = new ArrayList<>();
            for (int i = 1; i < v.size(); i++) {
                Price firstPrice = v.get(i - 1);
                Price secondPrice = v.get(i);
                if (firstPrice.checkSameValuesToMerge(secondPrice)) {
                    resultList.add(mergeSameValues(firstPrice,secondPrice));
                } else if (firstPrice.checkNotSameValuesNeedToAddNew(secondPrice)) {
                    resultList.addAll(mergeSameValuesAndAddNewPrice(firstPrice,secondPrice));
                } else if (firstPrice.checkNotSameValuesNotNeedToAddNew(secondPrice)) {
                    resultList.addAll(mergeNotSameValuesAndNotAddNewPrice(firstPrice, secondPrice));
                } else
                    resultList.addAll(v);
            }
            priceGroupByProductCodeAndNumberAndDepart.put(k, resultList);
        });
    }


    private Price mergeSameValues(Price firstPrice, Price secondPrice){
        //Если нужно совместить цены с одинаковым значением, берет максимальное из dateBegin и dateEnd из 2-ух цен
        secondPrice.setDateBegin(
                (firstPrice.getDateBegin().isBefore(secondPrice.getDateBegin())
                        ? firstPrice.getDateBegin()
                        : secondPrice.getDateBegin())
        );
        secondPrice.setDateEnd(
                (firstPrice.getDateEnd().isAfter(secondPrice.getDateEnd())
                        ? firstPrice.getDateEnd()
                        : secondPrice.getDateEnd())
        );
        return secondPrice;
    }

    private List<Price> mergeSameValuesAndAddNewPrice(Price firstPrice, Price secondPrice){
        Price newPrice = new Price(firstPrice);
        newPrice.setDateBegin(secondPrice.getDateEnd());
        firstPrice.setDateEnd(secondPrice.getDateBegin());

        return Arrays.asList(firstPrice, secondPrice, newPrice);
    }

    private List<Price> mergeNotSameValuesAndNotAddNewPrice(Price firstPrice, Price secondPrice){
        firstPrice.setDateEnd(secondPrice.getDateBegin());
        return Arrays.asList(firstPrice, secondPrice);
    }
}
