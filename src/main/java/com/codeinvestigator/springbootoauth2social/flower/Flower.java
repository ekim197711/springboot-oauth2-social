package com.codeinvestigator.springbootoauth2social.flower;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flower {
    private String name;
    private Integer smell;
    private BigDecimal price;
}
