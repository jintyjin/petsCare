package com.petsCare.petsCare.controller;

import com.petsCare.petsCare.entity.pet.PetGender;
import com.petsCare.petsCare.form.pet.PetAdoptForm;
import com.petsCare.petsCare.service.pet.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
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

	@ModelAttribute("petGenders")
	public PetGender[] petGenders() {
		return PetGender.values();
	}

	@GetMapping("/adopt")
	public String adopt(Model model, Authentication authentication) {
		String loginId = authentication.getName();
		model.addAttribute("petAdoptForm", new PetAdoptForm(loginId));

		return "/pet/petAdoptForm";
	}

	@PostMapping("/adopt")
	public String adopt(@Validated PetAdoptForm petAdoptForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "/pet/petAdoptForm";
		}

		petService.adopt(petAdoptForm);

		return "redirect:/";
	}
}
