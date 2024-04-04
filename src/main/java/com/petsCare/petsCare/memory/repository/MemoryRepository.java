package com.petsCare.petsCare.memory.repository;

import com.petsCare.petsCare.memory.dto.form.MemoryDetailForm;
import com.petsCare.petsCare.memory.dto.form.MemorySimpleForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemoryRepository {
	Page<MemorySimpleForm> findSimpleFormByPet(Long petId, Pageable pageable);

	MemoryDetailForm findMemoryDetailById(Long memoryId);
}
