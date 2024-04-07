package com.petsCare.petsCare.weight;

import com.petsCare.petsCare.weight.dto.WeightDto;
import com.petsCare.petsCare.weight.dto.WeightForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/weights")
@Slf4j
public class WeightController {

	private final WeightService weightService;

	@GetMapping("/{petId}")
	public String weights(@PathVariable Long petId, Model model) {
		model.addAttribute("weightForm", weightService.findPetWeights(petId));

		return "/weight/weights";
	}

	@PostMapping("/measure")
	public String measure(@Validated WeightForm weightForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "/weight/weights";
		}

		weightService.measure(weightForm);

		return "redirect:/weights/" + weightForm.getPetId();
	}

	@GetMapping("/delete")
	public String deleteWeight(Long id, Long petId) {
		weightService.deleteWeight(id);

		return "redirect:/weights/" + petId;
	}
}
