package ru.stupakov.pricescsi.models;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Stupakov D. L.
 **/
@Data
public class Price {
    private String productCode;
    private Integer number;
    private Integer depart;
    private LocalDateTime dateBegin;
    private LocalDateTime dateEnd;
    private Long value;


}
