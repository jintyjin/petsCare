package com.petsCare.petsCare.pet.repository;

import com.petsCare.petsCare.pet.dto.form.PetDetailForm;
import com.petsCare.petsCare.pet.entity.Pet;
import com.petsCare.petsCare.pet.entity.PetBreed;
import com.petsCare.petsCare.pet.entity.PetGender;
import com.petsCare.petsCare.pet.entity.PetType;
import com.petsCare.petsCare.user.entity.User;
import com.petsCare.petsCare.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class JpaPetRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JpaPetRepository jpaPetRepository;

    @Autowired
    PetTypeRepository petTypeRepository;

    @Autowired
    PetBreedRepository petBreedRepository;

    @Test
    void 펫_등록() {
        //given
        User user1 = User.builder()
                .provider("naver")
                .loginId("naver_test1")
                .username("테스트1")
                .profileImage("jj.png")
                .role("ROLE_USER")
                .build();
        User user2 = User.builder()
                .provider("naver")
                .loginId("naver_test2")
                .username("테스트2")
                .profileImage("jj.png")
                .role("ROLE_USER")
                .build();
        userRepository.save(user1);
        userRepository.save(user2);
        PetType petType = new PetType("강아지");
        petTypeRepository.save(petType);
        PetBreed petBreed = new PetBreed("닥스훈트", petType);
        petBreedRepository.save(petBreed);
        Pet pet1 = new Pet("이복댕1", null, petBreed, 1, LocalDate.of(2014, 7, 31), user1);
        Pet pet2 = new Pet("이복댕2", null, petBreed, 1, LocalDate.of(2014, 7, 31), user2);
        Pet pet3 = new Pet("이복댕3", null, petBreed, 1, LocalDate.of(2014, 7, 31), user2);
        jpaPetRepository.save(pet1);
        jpaPetRepository.save(pet2);
        jpaPetRepository.save(pet3);

        //when
        List<Pet> findPetsByUser1 = jpaPetRepository.findByUserId(user1.getId());
        List<Pet> findPetsByUser2 = jpaPetRepository.findByUserId(user2.getId());

        //then
        assertThat(findPetsByUser1.size()).isEqualTo(user1.getPets().size());
        assertThat(findPetsByUser2.size()).isEqualTo(user2.getPets().size());
    }

    @Test
    void 펫_가져오기() {
        //given
        User user = User.builder()
                .provider("naver")
                .loginId("naver_jinjin")
                .username("JJ")
                .profileImage("jj.png")
                .role("ROLE_USER")
                .build();
        userRepository.save(user);
        PetType petType = new PetType("강아지");
        petTypeRepository.save(petType);
        PetBreed petBreed = new PetBreed("닥스훈트", petType);
        petBreedRepository.save(petBreed);
        Pet pet1 = new Pet("이복댕1", null, petBreed, 1, LocalDate.of(2014, 7, 31), user);
        Pet pet2 = new Pet("이복댕2", null, petBreed, 1, LocalDate.of(2014, 7, 31), user);
        Pet pet3 = new Pet("이복댕3", null, petBreed, 1, LocalDate.of(2014, 7, 31), user);
        jpaPetRepository.save(pet1);
        jpaPetRepository.save(pet2);
        jpaPetRepository.save(pet3);

        //when
        List<Pet> findPets = jpaPetRepository.findByUserId(user.getId());

        //then
        assertThat(findPets.size()).isEqualTo(3);
        for (Pet pet : findPets) {
            System.out.println("pet.getPetGender() = " + pet.getPetGender());
            System.out.println("PetGender.getLabel(pet.getPetGender()) = " + PetGender.getLabel(pet.getPetGender()));
        }
    }

    @Test
    void 펫_상세정보_가져오기() {
        //given
        User user = User.builder()
                .provider("naver")
                .loginId("naver_jinjin")
                .username("JJ")
                .profileImage("jj.png")
                .role("ROLE_USER")
                .build();
        userRepository.save(user);
        PetType petType = new PetType("강아지");
        petTypeRepository.save(petType);
        PetBreed petBreed = new PetBreed("닥스훈트", petType);
        petBreedRepository.save(petBreed);
        Pet pet = new Pet("이복댕1", null, petBreed, 1, LocalDate.of(2014, 7, 31), user);
        jpaPetRepository.save(pet);

        //when
        PetDetailForm petDetailForm = jpaPetRepository.showPetDetail(pet.getId());

        //then
        assertThat(PetGender.getLabel(pet.getPetGender()).toString()).isEqualTo(petDetailForm.getGender());
        assertThat(pet.getId()).isEqualTo(petDetailForm.getId());
        assertThat(pet.getPetBreed().getBreed()).isEqualTo(petDetailForm.getBreed());
    }
}