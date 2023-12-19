package com.petsCare.petsCare.repository.user;

import com.petsCare.petsCare.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginId(String loginId);

    Optional<User> findByNickName(String nickName);
}
