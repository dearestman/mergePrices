package ru.stupakov.pricescsi.models;

import lombok.AllArgsConstructor;
import lombok.Data;

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
}
