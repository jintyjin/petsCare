package com.petsCare.petsCare.pet.entity;

import com.petsCare.petsCare.memory.entity.Memory;
import com.petsCare.petsCare.user.entity.User;
import com.petsCare.petsCare.weight.entity.Weight;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pet {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long id;

    @Column(name = "pet_name")
    private String petName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "breed_id")
    private PetBreed petBreed;

    @Column(name = "pet_gender")
    private int petGender;

    @Column(name = "pet_birth")
    private LocalDate petBirth;

    @Column(name = "pet_status")
    @Enumerated(EnumType.STRING)
    private PetStatus petStatus;

    @Column(name = "pet_leave_date")
    private LocalDate petLeaveDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "pet")
    List<Memory> memories = new ArrayList<>();

    @OneToMany(mappedBy = "pet")
    List<Weight> weights = new ArrayList<>();

    @Builder
    public Pet(String petName, PetBreed petBreed, int petGender, LocalDate petBirth, User user) {
        this.petName = petName;
        this.petBreed = petBreed;
        this.petGender = petGender;
        this.petBirth = petBirth;
        this.petStatus = PetStatus.NORMAL;
        this.user = user;
        this.user.getPets().add(this);
        this.petBreed.getPet().add(this);
    }

    public void makeMemory(Memory memory) {
        this.memories.add(memory);
    }

    public void leave(LocalDate petLeaveDate) {
        this.petLeaveDate = petLeaveDate;
        this.petStatus = PetStatus.LEAVE;
    }
}
