package com.petsCare.petsCare.user.repository;

import com.petsCare.petsCare.user.entity.DeletedUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeletedUserRepository extends JpaRepository<DeletedUser, Long> {

    Optional<DeletedUser> findByLoginId(String loginId);
}
