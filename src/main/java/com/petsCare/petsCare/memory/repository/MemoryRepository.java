package com.petsCare.petsCare.memory.repository;

import com.petsCare.petsCare.memory.dto.form.MemoryDetailForm;
import com.petsCare.petsCare.memory.dto.form.MemorySimpleForm;
import com.petsCare.petsCare.user.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemoryRepository {
	Page<MemorySimpleForm> findSimpleFormByPet(UserDto userDto, Long petId, Pageable pageable);

	MemoryDetailForm findMemoryDetailById(UserDto userDto, Long memoryId);
}
