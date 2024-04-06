package com.petsCare.petsCare.weight.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class WeightForm {

	@NotNull
	private final Long id;

	private final String petName;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private final LocalDate date;

	@DecimalMin(value = "0.00", inclusive = false)
	@Digits(integer=2, fraction=2)
	private final BigDecimal weight;

	private final List<WeightDto> weightDtoList;
}
