package ru.stupakov.pricescsi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.stupakov.pricescsi.utils.formatters.PriceDateFormatter;

import java.time.LocalDateTime;

/**
 * @author Stupakov D. L.
 **/
@Data
@AllArgsConstructor
public class Price {
    private String productCode;
    private Integer number;
    private Integer depart;
    private LocalDateTime dateBegin;
    private LocalDateTime dateEnd;
    private Long value;

    public Price(Price price) {
        this.productCode = price.getProductCode();
        this.number = price.getNumber();
        this.depart = price.getDepart();
        this.dateBegin = price.getDateBegin();
        this.dateEnd = price.getDateEnd();
        this.value = price.getValue();
    }

    public String priceToCSV(){
        return new StringBuilder()
                .append(productCode).append(",")
                .append(number).append(",")
                .append(depart).append(",")
                .append(PriceDateFormatter.formatLocalDateTimeToUserDate(dateBegin)).append(",")
                .append(PriceDateFormatter.formatLocalDateTimeToUserDate(dateEnd)).append(",")
                .append(value)
                .append("\n").toString();
    }

    public boolean checkSameValuesToMerge(Price secondPrice){
        return this.value.equals(secondPrice.getValue())
                && this.dateEnd.plusNanos(1).isBefore(secondPrice.getDateEnd().minusNanos(1))
                && this.dateEnd.minusNanos(1).isAfter((secondPrice.getDateBegin().plusNanos(1)));
    }

    public boolean checkNotSameValuesNeedToAddNew(Price secondPrice){
        return this.dateEnd.minusNanos(1).isAfter(secondPrice.getDateEnd().plusNanos(1))
                && this.dateEnd.minusNanos(1).isAfter((secondPrice.getDateBegin().plusNanos(1)));
    }

    public boolean checkNotSameValuesNotNeedToAddNew(Price secondPrice){
        return this.dateEnd.plusNanos(1).isBefore(secondPrice.getDateEnd().minusNanos(1))
                && this.dateEnd.minusNanos(1).isAfter((secondPrice.getDateBegin().plusNanos(1)));
    }


};
