package com.petsCare.petsCare.pet;

import com.petsCare.petsCare.pet.entity.PetGender;
import com.petsCare.petsCare.pet.dto.form.PetAdoptForm;
import com.petsCare.petsCare.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pets")
public class PetController {

	private final PetService petService;
	private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

	@ModelAttribute("petGenders")
	public PetGender[] petGenders() {
		return PetGender.values();
	}

	@GetMapping("/adopt")
	public String adopt(Model model) {
		model.addAttribute("petAdoptForm", new PetAdoptForm());

		return "/pet/petAdoptForm";
	}

	@PostMapping("/adopt")
	public String adopt(@Validated PetAdoptForm petAdoptForm, BindingResult bindingResult, @AuthenticationPrincipal UserDto userDto) {
		if (bindingResult.hasErrors()) {
			return "/pet/petAdoptForm";
		}

		petService.adopt(petAdoptForm, userDto);

		return "redirect:/";
	}
  
	@GetMapping
	public String pets(Model model, @AuthenticationPrincipal UserDto userDto) {
		model.addAttribute("petsForms", petService.showPets(userDto));

		return "/pet/pets";
	}
}
