package com.petsCare.petsCare.memory.controller;

import com.petsCare.petsCare.memory.dto.form.MemoryForm;
import com.petsCare.petsCare.memory.service.MemoryService;
import com.petsCare.petsCare.oAuth2.dto.CustomOAuth2User;
import com.petsCare.petsCare.pet.service.PetService;
import com.petsCare.petsCare.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/memories")
@RequiredArgsConstructor
public class MemoryController {

	private final MemoryService memoryService;
	private final PetService petService;

	@GetMapping("/make")
	public String make(Model model, @AuthenticationPrincipal CustomOAuth2User oAuth2User) {
		model.addAttribute("petIdAndNameForms", petService.showPets(oAuth2User.getUserDto().getId()));
		model.addAttribute("memoryForm", new MemoryForm());

		return "/memory/addMemoryForm";
	}

	@PostMapping("/make")
	public String make(@Validated MemoryForm memoryForm, BindingResult bindingResult,
					   @AuthenticationPrincipal CustomOAuth2User oAuth2User, Model model) {
		UserDto userDto = oAuth2User.getUserDto();

		if (bindingResult.hasErrors()) {
			model.addAttribute("petIdAndNameForms", petService.showPets(userDto.getId()));
			return "/memory/addMemoryForm";
		}

		memoryService.make(memoryForm, userDto);

		return "redirect:/";
	}
}
