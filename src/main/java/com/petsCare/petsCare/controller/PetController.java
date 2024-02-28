package com.petsCare.petsCare.controller;

import com.petsCare.petsCare.dto.oauth2.CustomOAuth2User;
import com.petsCare.petsCare.entity.pet.PetGender;
import com.petsCare.petsCare.form.pet.PetAdoptForm;
import com.petsCare.petsCare.service.pet.PetService;
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
	public String adopt(@Validated PetAdoptForm petAdoptForm, BindingResult bindingResult, @AuthenticationPrincipal CustomOAuth2User oAuth2User) {
		if (bindingResult.hasErrors()) {
			return "/pet/petAdoptForm";
		}

		petService.adopt(petAdoptForm, oAuth2User.getUser());

		return "redirect:/";
	}
}
