package com.petsCare.petsCare.memory.repository;

import com.petsCare.petsCare.memory.dto.form.*;
import com.petsCare.petsCare.user.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemoryRepository {
	Page<MemorySimpleForm> findSimpleFormByPet(UserDto userDto, Long petId, Pageable pageable);

	MemoryDetailForm findMemoryDetailById(UserDto userDto, Long memoryId);

	List<MemoryWalkResponseForm> findMemoryWalkFormByPet(UserDto userDto, MemoryWalkRequestForm memoryWalkRequestForm);

	MemoryWalkInfoResponse findMemoryWalkInfoByMemory(UserDto userDto, Long memoryId);
}
