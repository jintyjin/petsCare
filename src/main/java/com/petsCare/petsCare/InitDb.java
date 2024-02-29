package com.petsCare.petsCare;

import com.petsCare.petsCare.pet.entity.PetBreed;
import com.petsCare.petsCare.pet.entity.PetType;
import com.petsCare.petsCare.user.entity.User;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
    }
    
    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        private final BCryptPasswordEncoder passwordEncoder;

        public void dbInit1() {
            User user = User.builder()
                    .provider("naver")
                    .loginId("testMember123")
                    .username("에세이르123")
                    .profileImage("jj.png")
                    .role("ROLE_USER")
                    .build();
            em.persist(user);
            PetType petType = new PetType("강아지");
            em.persist(petType);
            PetBreed petBreed = new PetBreed("닥스훈트", petType);
            em.persist(petBreed);
        }
    }
}
