package com.petsCare.petsCare.pet;

import com.petsCare.petsCare.memory.exception.MemoryMakeException;
import com.petsCare.petsCare.oAuth2.dto.CustomOAuth2User;
import com.petsCare.petsCare.pet.dto.form.*;
import com.petsCare.petsCare.pet.dto.validation.PetLeaveGroup;
import com.petsCare.petsCare.pet.entity.PetGender;
import com.petsCare.petsCare.pet.exception.*;
import com.petsCare.petsCare.pet.service.PetBreedService;
import com.petsCare.petsCare.pet.service.PetService;
import com.petsCare.petsCare.pet.service.PetTypeService;
import com.petsCare.petsCare.user.dto.UserDto;
import com.petsCare.petsCare.user.exception.UserCanNotFindException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pets")
@Slf4j
public class PetController {

	private final PetService petService;
	private final PetTypeService petTypeService;
	private final PetBreedService petBreedService;
	private final MessageSource messageSource;

	@GetMapping("/adopt")
	public String adopt(Model model) {
		model.addAttribute("petTypeIdAndNameForms", petTypeService.showPetTypes());
		model.addAttribute("petAdoptForm", new PetAdoptForm());
		model.addAttribute("petGenders", PetGender.values());

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
			settingAdoptData(model);
			return "/pet/petAdoptForm";
		}

		try {
			petService.adopt(petAdoptForm, userDto);
		} catch (UserCanNotFindException e) {
			bindingResult.rejectValue("petName", "user.canNotFind", messageSource.getMessage(e.getMessage(), null, Locale.KOREAN));
			settingAdoptData(model);
			return "/pet/petAdoptForm";
		} catch (PetBreedCanNotFindException e) {
			bindingResult.rejectValue("breedId", "breed.canNotFind", messageSource.getMessage(e.getMessage(), null, Locale.KOREAN));
			settingAdoptData(model);
			return "/pet/petAdoptForm";
		} catch (PetBirthCanNotAfterTodayException e) {
			bindingResult.rejectValue("petBirth", "breed.birthCanNotAfterToday", messageSource.getMessage(e.getMessage(), null, Locale.KOREAN));
			settingAdoptData(model);
			return "/pet/petAdoptForm";
		} catch (MemoryMakeException e) {
			bindingResult.rejectValue("thumbnail", "thumbnail.error", messageSource.getMessage(e.getMessage(), null, Locale.KOREAN));
			settingAdoptData(model);
			return "/pet/petAdoptForm";
		}

		return "redirect:/";
	}

	@GetMapping("/{petId}")
	public String pet(Model model, @PathVariable Long petId) {
		model.addAttribute("petDetailForm", petService.showPetDetail(petId));

		return "/pet/pet";
	}

	@PostMapping("/{petId}")
	public String leave(@Validated(PetLeaveGroup.class) PetDetailForm petDetailForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "/pet/pet";
		}

		try {
			petService.leave(petDetailForm);
		} catch (PetCanNotFindException e) {
			bindingResult.rejectValue("leaveTime", "pet.canNotFind", messageSource.getMessage(e.getMessage(), null, Locale.KOREAN));
			return "/pet/pet";
		} catch (PetLeaveCanNotBeforeBirthException e) {
			bindingResult.rejectValue("leaveTime", "pet.leaveCanNotBeforeBirth", messageSource.getMessage(e.getMessage(), null, Locale.KOREAN));
			return "/pet/pet";
		} catch (PetLeaveCanNotAfterTodayException e) {
			bindingResult.rejectValue("leaveTime", "pet.leaveCanNotAfterToday", messageSource.getMessage(e.getMessage(), null, Locale.KOREAN));
			return "/pet/pet";
		}

		return "redirect:/pets/" + petDetailForm.getId();
	}

	@GetMapping
	public String pets(Model model, @AuthenticationPrincipal CustomOAuth2User oAuth2User) {
		model.addAttribute("petsForms", petService.showPets(oAuth2User.getUserDto()));

		return "/pet/pets";
	}

	private void settingAdoptData(Model model) {
		model.addAttribute("petTypeIdAndNameForms", petTypeService.showPetTypes());
		model.addAttribute("petGenders", PetGender.values());
	}
}
