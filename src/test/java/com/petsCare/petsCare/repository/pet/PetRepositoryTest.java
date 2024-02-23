package com.petsCare.petsCare.repository.pet;

import com.petsCare.petsCare.entity.pet.Pet;
import com.petsCare.petsCare.entity.pet.PetStatus;
import com.petsCare.petsCare.entity.user.User;
import com.petsCare.petsCare.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class PetRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PetRepository petRepository;

    @Test
    @Transactional
    void 펫_등록() {
        //given
        User user1 = User.builder()
                .provider("naver")
                .loginId("naver_test1")
                .nickName("테스트1")
                .profileImage("jj.png")
                .role("ROLE_USER")
                .build();
        User user2 = User.builder()
                .provider("naver")
                .loginId("naver_test2")
                .nickName("테스트2")
                .profileImage("jj.png")
                .role("ROLE_USER")
                .build();
        userRepository.save(user1);
        userRepository.save(user2);
        Pet pet1 = new Pet("이복댕1", null, 1, LocalDate.of(2014, 7, 31), user1);
        Pet pet2 = new Pet("이복댕2", null, 1, LocalDate.of(2014, 7, 31), user2);
        Pet pet3 = new Pet("이복댕3", null, 1, LocalDate.of(2014, 7, 31), user2);
        petRepository.save(pet1);
        petRepository.save(pet2);
        petRepository.save(pet3);

        //when
        List<Pet> findPetsByUser1 = petRepository.findByUser(user1);
        List<Pet> findPetsByUser2 = petRepository.findByUser(user2);

        //then
        assertThat(findPetsByUser1.size()).isEqualTo(user1.getPets().size());
        assertThat(findPetsByUser2.size()).isEqualTo(user2.getPets().size());
    }

    @Test
    @Transactional
    void 펫_가져오기() {
        //given
        User user = User.builder()
                .provider("naver")
                .loginId("naver_jinjin")
                .nickName("JJ")
                .profileImage("jj.png")
                .role("ROLE_USER")
                .build();
        userRepository.save(user);
        Pet pet1 = new Pet("이복댕1", null, 1, LocalDate.of(2014, 7, 31), user);
        Pet pet2 = new Pet("이복댕2", null, 1, LocalDate.of(2014, 7, 31), user);
        Pet pet3 = new Pet("이복댕3", null, 1, LocalDate.of(2014, 7, 31), user);
        petRepository.save(pet1);
        petRepository.save(pet2);
        petRepository.save(pet3);

        //when
        List<Pet> findPets = petRepository.findByUser(user);

        //then
        assertThat(findPets.size()).isEqualTo(3);
    }
}