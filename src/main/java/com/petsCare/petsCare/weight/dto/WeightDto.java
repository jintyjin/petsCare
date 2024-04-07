package com.petsCare.petsCare.weight.dto;

import com.petsCare.petsCare.weight.entity.Weight;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class WeightDto {

    @NotNull
    private Long weightId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate date;

    @DecimalMin(value = "0.00", inclusive = false)
    @Digits(integer=2, fraction=2)
    private BigDecimal weight;

    public WeightDto(Weight weight) {
        this.weightId = weight.getId();
        this.date = weight.getDate();
        this.weight = weight.getWeight();
    }
}
