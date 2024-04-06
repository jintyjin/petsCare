package com.petsCare.petsCare.weight.dto;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class WeightDto {

    @NotNull
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate date;

    @DecimalMin(value = "0.00", inclusive = false)
    @Digits(integer=2, fraction=2)
    private BigDecimal weight;

    @QueryProjection
    public WeightDto(Long id, LocalDate date, BigDecimal weight) {
        this.id = id;
        this.date = date;
        this.weight = weight;
    }
}
