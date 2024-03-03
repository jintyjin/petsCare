package com.petsCare.petsCare.pet.repository;

import com.petsCare.petsCare.pet.entity.Pet;
import com.petsCare.petsCare.pet.entity.PetBreed;
import com.petsCare.petsCare.pet.entity.PetType;
import com.petsCare.petsCare.pet.repository.PetBreedRepository;
import com.petsCare.petsCare.pet.repository.PetRepository;
import com.petsCare.petsCare.pet.repository.PetTypeRepository;
import com.petsCare.petsCare.user.entity.User;
import com.petsCare.petsCare.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class PetRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PetRepository petRepository;

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
        petRepository.save(pet1);
        petRepository.save(pet2);
        petRepository.save(pet3);

        //when
        List<Pet> findPetsByUser1 = petRepository.findByUserId(user1.getId());
        List<Pet> findPetsByUser2 = petRepository.findByUserId(user2.getId());

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
        petRepository.save(pet1);
        petRepository.save(pet2);
        petRepository.save(pet3);

        //when
        List<Pet> findPets = petRepository.findByUserId(user.getId());

        //then
        assertThat(findPets.size()).isEqualTo(3);
    }
}