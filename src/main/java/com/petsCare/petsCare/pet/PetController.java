package com.petsCare.petsCare.pet;

import com.petsCare.petsCare.oAuth2.dto.CustomOAuth2User;
import com.petsCare.petsCare.pet.dto.form.*;
import com.petsCare.petsCare.pet.dto.validation.PetLeaveGroup;
import com.petsCare.petsCare.pet.entity.PetGender;
import com.petsCare.petsCare.pet.service.PetBreedService;
import com.petsCare.petsCare.pet.service.PetService;
import com.petsCare.petsCare.pet.service.PetTypeService;
import com.petsCare.petsCare.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pets")
public class PetController {

	private final PetService petService;
	private final PetTypeService petTypeService;
	private final PetBreedService petBreedService;

	@ModelAttribute("petGenders")
	public PetGender[] petGenders() {
		return PetGender.values();
	}

	@GetMapping("/adopt")
	public String adopt(Model model) {
		model.addAttribute("petTypeIdAndNameForms", petTypeService.showPetTypes());
		model.addAttribute("petAdoptForm", new PetAdoptForm());

		return "/pet/petAdoptForm";
	}

	@GetMapping("/petBreeds/{petTypeId}")
	@ResponseBody
	public List<PetBreedIdAndName> petBreeds(@PathVariable Long petTypeId) {
		return petBreedService.showPetBreeds(petTypeId);
	}

	@PostMapping("/adopt")
	public String adopt(@Validated PetAdoptForm petAdoptForm, BindingResult bindingResult,
						@AuthenticationPrincipal CustomOAuth2User oAuth2User, Model model) {
		UserDto userDto = oAuth2User.getUserDto();

		if (bindingResult.hasErrors()) {
			model.addAttribute("petTypeIdAndNameForms", petTypeService.showPetTypes());
			return "/pet/petAdoptForm";
		}

		petService.adopt(petAdoptForm, userDto);

		return "redirect:/";
	}

	@GetMapping
	public String pets(Model model, @AuthenticationPrincipal CustomOAuth2User oAuth2User) {
		model.addAttribute("petsForms", petService.showPets(oAuth2User.getUserDto()));

		return "/pet/pets";
	}

	@GetMapping("/{petId}")
	public String pet(Model model, @PathVariable Long petId) {
		model.addAttribute("petDetailForm", petService.showPetDetail(petId));

		return "/pet/pet";
	}

	@PostMapping("/{petId}")
	public String pet(@Validated(PetLeaveGroup.class) PetDetailForm petDetailForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "/pet/pet";
		}

		petService.leave(petDetailForm);

		return "redirect:/pets/" + petDetailForm.getId();
	}
}
