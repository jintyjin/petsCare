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

    @Column(name = "pet_profile")
    private String profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "breed_id")
    private PetBreed petBreed;

    @Column(name = "pet_gender")
    private int petGender;

    @Column(name = "pet_status")
    @Enumerated(EnumType.STRING)
    private PetStatus petStatus;

    @Embedded
    private TogetherTime togetherTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    List<Memory> memories = new ArrayList<>();

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    List<Weight> weights = new ArrayList<>();

    @Builder
    public Pet(String petName, String profile, PetBreed petBreed, int petGender, LocalDate bornTime, User user) {
        this.petName = petName;
        this.profile = profile;
        this.petBreed = petBreed;
        this.petGender = petGender;
        this.togetherTime = new TogetherTime(bornTime);
        this.petStatus = PetStatus.NORMAL;
        this.user = user;
    }

    public void makeMemory(Memory memory) {
        this.memories.add(memory);
    }

    public void measurement(Weight weight) {
        this.weights.add(weight);
    }

    public void changeProfile(String profile) {
        this.profile = profile;
    }

    public void leave(LocalDate leaveTime) {
        this.togetherTime.leave(leaveTime);
        this.petStatus = PetStatus.LEAVE;
    }
}
