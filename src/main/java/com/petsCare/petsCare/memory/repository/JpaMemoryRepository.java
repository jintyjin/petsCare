package com.petsCare.petsCare.memory.repository;

import com.petsCare.petsCare.memory.entity.Memory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemoryRepository extends JpaRepository<Memory, Long>, MemoryRepository {
}
