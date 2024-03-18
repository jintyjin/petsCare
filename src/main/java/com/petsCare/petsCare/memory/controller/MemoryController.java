package com.petsCare.petsCare.memory.controller;

import com.petsCare.petsCare.memory.dto.form.MemoryMakeForm;
import com.petsCare.petsCare.memory.dto.form.MemorySimpleForm;
import com.petsCare.petsCare.memory.service.MemoryService;
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
@RequestMapping("/memory")
@RequiredArgsConstructor
public class MemoryController {

	private final MemoryService memoryService;

	@GetMapping("/make")
	public String make(Model model) {
		model.addAttribute("memorySimpleForm", new MemorySimpleForm());

		return "/memory/memoryMakeForm";
	}

	@PostMapping("/make")
	public String make(@Validated MemoryMakeForm memoryMakeForm, BindingResult bindingResult,
					   @AuthenticationPrincipal UserDto userDto) {
		if (bindingResult.hasErrors()) {
			return "/memory/memoryMakeForm";
		}

		memoryService.make(memoryMakeForm, userDto);

		return "redirect:/";
	}
}
