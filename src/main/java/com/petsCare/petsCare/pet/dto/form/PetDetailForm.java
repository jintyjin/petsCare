package com.petsCare.petsCare.pet.dto.form;

import com.petsCare.petsCare.memory.entity.MemoryType;
import com.petsCare.petsCare.pet.dto.validation.PetLeaveGroup;
import com.petsCare.petsCare.pet.entity.Pet;
import com.petsCare.petsCare.pet.entity.PetGender;
import com.petsCare.petsCare.pet.entity.PetStatus;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PetDetailForm {

    private Long id;

    private String name;

    private String gender;

    private String breed;

    private String path;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate bornTime;

    @NotNull(groups = {PetLeaveGroup.class})
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate leaveTime;

    private PetStatus petStatus;

    private MemoryType type;

    @QueryProjection
    public PetDetailForm(Pet pet, MemoryType type) {
        this.id = pet.getId();
        this.name = pet.getPetName();
        this.gender = PetGender.getLabel(pet.getPetGender()).toString();
        this.breed = pet.getPetBreed().getBreed();
        this.path = pet.getProfile();
        this.bornTime = pet.getTogetherTime().getBornTime();
        this.leaveTime = pet.getTogetherTime().getLeaveTime();
        this.petStatus = pet.getPetStatus();
        this.type = type;
    }
}
