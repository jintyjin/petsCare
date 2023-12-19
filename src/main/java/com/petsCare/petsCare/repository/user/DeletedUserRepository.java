package com.petsCare.petsCare.repository.user;

import com.petsCare.petsCare.entity.user.DeletedUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeletedUserRepository extends JpaRepository<DeletedUser, Long> {

    Optional<DeletedUser> findByLoginId(String loginId);
}
