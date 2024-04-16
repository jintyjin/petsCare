package com.petsCare.petsCare.memory.controller;

import com.petsCare.petsCare.memory.dto.form.*;
import com.petsCare.petsCare.memory.exception.MemoryMakeException;
import com.petsCare.petsCare.memory.service.MemoryService;
import com.petsCare.petsCare.oAuth2.dto.CustomOAuth2User;
import com.petsCare.petsCare.pet.dto.form.PetIdAndNameForm;
import com.petsCare.petsCare.pet.exception.PetCanNotFindException;
import com.petsCare.petsCare.pet.service.PetService;
import com.petsCare.petsCare.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/memories")
@RequiredArgsConstructor
@Slf4j
public class MemoryController {

	private final MemoryService memoryService;
	private final PetService petService;
	private final MessageSource messageSource;

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

		try {
			memoryService.make(memoryForm, userDto);
		} catch (PetCanNotFindException e) {
			bindingResult.rejectValue("petId", "pet.canNotFind", messageSource.getMessage(e.getMessage(), null, Locale.KOREAN));
			return "/memory/addMemoryForm";
		} catch (MemoryMakeException e) {
			bindingResult.rejectValue("files", "files.error", messageSource.getMessage(e.getMessage(), null, Locale.KOREAN));
			return "/memory/addMemoryForm";
		}

		return "redirect:/";
	}

	@GetMapping("/memory")
	public String memory(@AuthenticationPrincipal CustomOAuth2User oAuth2User, Model model) {
		model.addAttribute("petsForms", petService.showPets(oAuth2User.getUserDto()));

		return "/memory/memory";
	}

	@GetMapping("/{petId}")
	public String memories(@AuthenticationPrincipal CustomOAuth2User oAuth2User,
						   @PathVariable Long petId, Model model, Pageable pageable) {
		model.addAttribute("reminiscences", memoryService.reminisce(oAuth2User.getUserDto(), petId, pageable));
		model.addAttribute("petId", petId);
		model.addAttribute("pageSize", pageable.getPageSize());

		return "/memory/memories";
	}

	@GetMapping("/api/{petId}")
	@ResponseBody
	public List<MemorySimpleForm> memories(@AuthenticationPrincipal CustomOAuth2User oAuth2User, Pageable pageable,
										   @PathVariable Long petId) {

		return memoryService.reminisce(oAuth2User.getUserDto(), petId, pageable);
	}

	@GetMapping("/detail/{memoryId}")
	public String memoryDetail(@AuthenticationPrincipal CustomOAuth2User oAuth2User,
							   @PathVariable Long memoryId, Model model) {
		model.addAttribute("memoryDetailForm", memoryService.showMemoryDetail(oAuth2User.getUserDto(), memoryId));

		return "/memory/detail";
	}

	@GetMapping("/walk/{petId}")
	public String memoryWalk(@AuthenticationPrincipal CustomOAuth2User oAuth2User, @PathVariable Long petId, Model model) {
		List<MemoryWalkAbstractResponse> memoryWalkResponseForms = memoryService.showMemoryWalk(oAuth2User.getUserDto(), new MemoryWalkRequestForm(petId));
		log.info("memoryWalkResponseForms size = {}", memoryWalkResponseForms.size());
		model.addAttribute("memoryWalkResponseForms", memoryWalkResponseForms);
		model.addAttribute("petIdAndNameForm", new PetIdAndNameForm(petService.findPet(petId)));

		return "/memory/walk";
	}

	@PostMapping("/walk")
	@ResponseBody
	public List<MemoryWalkAbstractResponse> memoryWalk(@AuthenticationPrincipal CustomOAuth2User oAuth2User, @RequestBody MemoryWalkRequest memoryWalkRequest) {
		List<MemoryWalkAbstractResponse> memoryWalkAbstractResponses = memoryService.showMemoryWalk(oAuth2User.getUserDto(), memoryWalkRequest);

		return memoryWalkAbstractResponses;
	}

	@GetMapping("/walk/info/{memoryId}")
	@ResponseBody
	public MemoryWalkInfoResponse memoryWalkInfo(@AuthenticationPrincipal CustomOAuth2User oAuth2User, @PathVariable Long memoryId) {
		return memoryService.showMemoryWalkInfo(oAuth2User.getUserDto(), memoryId);
	}
}
