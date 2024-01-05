package com.petsCare.petsCare;

import com.petsCare.petsCare.entity.user.User;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

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
            User user = new User("testMember123", passwordEncoder.encode("testMember!23"), "에세이르123");
            em.persist(user);
        }
    }
}
