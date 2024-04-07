package com.petsCare.petsCare.weight.dto;

import com.petsCare.petsCare.pet.entity.Pet;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data
@NoArgsConstructor
public class WeightForm {

	@NotNull
	private Long petId;

	@NotNull
	private String petName;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private LocalDate date;

	@DecimalMin(value = "0.00", inclusive = false)
	@Digits(integer=2, fraction=2)
	private BigDecimal weight;

	private List<WeightDto> weightDtoList = new ArrayList<>();

	public WeightForm(Pet pet) {
		this.petId = pet.getId();
		this.petName = pet.getPetName();
		this.date = LocalDate.now();
		this.weight = BigDecimal.ZERO;
		this.weightDtoList = pet.getWeights().stream().map(weight -> new WeightDto(weight))
				.sorted(Comparator.comparing(WeightDto::getDate)).toList();
	}
}
