package com.petsCare.petsCare.weight.repository;

import com.petsCare.petsCare.pet.entity.Pet;
import com.petsCare.petsCare.pet.entity.PetBreed;
import com.petsCare.petsCare.pet.entity.PetType;
import com.petsCare.petsCare.user.entity.User;
import com.petsCare.petsCare.weight.entity.Weight;
import com.petsCare.petsCare.pet.repository.PetBreedRepository;
import com.petsCare.petsCare.pet.repository.JpaPetRepository;
import com.petsCare.petsCare.pet.repository.PetTypeRepository;
import com.petsCare.petsCare.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class JpaWeightRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
	JpaPetRepository jpaPetRepository;

    @Autowired
    JpaWeightRepository jpaWeightRepository;

    @Autowired
    PetTypeRepository petTypeRepository;

    @Autowired
    PetBreedRepository petBreedRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void 체중_등록() {
        //given
        User user = User.builder()
                .provider("naver")
                .loginId("naver_jinjin")
                .username("JJ")
                .profileImage("jj.png")
                .role("ROLE_USER")
                .build();
        userRepository.save(user);
        Pet pet = new Pet("이복댕", null, new PetBreed("닥스훈트", new PetType("강아지")), 1, LocalDate.of(2014, 7, 31), user);
        jpaPetRepository.save(pet);

        //when
        Weight weight = new Weight(new BigDecimal(9.28), LocalDate.now(), pet);
        jpaWeightRepository.save(weight);
        Weight findWeight = jpaWeightRepository.findById(weight.getId()).get();

        //then
        assertThat(weight.getId()).isEqualTo(findWeight.getId());
    }

    @Test
    void 반려동물_체중_가져오기() {
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
        Pet pet = new Pet("이복댕", null, petBreed, 1, LocalDate.of(2014, 7, 31), user);
        jpaPetRepository.save(pet);
        Weight weight1 = new Weight(new BigDecimal(9.28), LocalDate.now(), pet);
        Weight weight2 = new Weight(new BigDecimal(9.21), LocalDate.now(), pet);
        Weight weight3 = new Weight(new BigDecimal(9.35), LocalDate.now(), pet);
        Weight weight4 = new Weight(new BigDecimal(9.25), LocalDate.now(), pet);
        jpaWeightRepository.save(weight1);
        jpaWeightRepository.save(weight2);
        jpaWeightRepository.save(weight3);
        jpaWeightRepository.save(weight4);
        Long petId = pet.getId();

        entityManager.flush();
        entityManager.clear();

        //when
        Pet findPet = jpaWeightRepository.findPetForWeights(petId);

        //then
        assertThat(findPet.getWeights().size()).isEqualTo(4);
    }
}