package com.petsCare.petsCare.weight.repository;

import com.petsCare.petsCare.pet.entity.Pet;
import com.petsCare.petsCare.pet.entity.PetBreed;
import com.petsCare.petsCare.pet.entity.PetType;
import com.petsCare.petsCare.user.entity.User;
import com.petsCare.petsCare.weight.entity.Weight;
import com.petsCare.petsCare.pet.repository.PetBreedRepository;
import com.petsCare.petsCare.pet.repository.PetRepository;
import com.petsCare.petsCare.pet.repository.PetTypeRepository;
import com.petsCare.petsCare.user.repository.UserRepository;
import com.petsCare.petsCare.weight.repository.WeightRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class WeightRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PetRepository petRepository;

    @Autowired
    WeightRepository weightRepository;

    @Autowired
    PetTypeRepository petTypeRepository;

    @Autowired
    PetBreedRepository petBreedRepository;

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
        petRepository.save(pet);

        //when
        Weight weight = new Weight(new BigDecimal(9.28), LocalDateTime.now(), pet);
        weightRepository.save(weight);
        Weight findWeight = weightRepository.findById(weight.getId()).get();

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
        petRepository.save(pet);
        Weight weight1 = new Weight(new BigDecimal(9.28), LocalDateTime.now(), pet);
        Weight weight2 = new Weight(new BigDecimal(9.21), LocalDateTime.now(), pet);
        Weight weight3 = new Weight(new BigDecimal(9.35), LocalDateTime.now(), pet);
        Weight weight4 = new Weight(new BigDecimal(9.25), LocalDateTime.now(), pet);
        weightRepository.save(weight1);
        weightRepository.save(weight2);
        weightRepository.save(weight3);
        weightRepository.save(weight4);

        //when
        List<Weight> findWeightsByPet = weightRepository.findByPet(pet);

        //then
        assertThat(findWeightsByPet.size()).isEqualTo(pet.getWeights().size());
    }
}