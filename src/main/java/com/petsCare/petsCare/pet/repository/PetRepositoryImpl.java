package com.petsCare.petsCare.pet.repository;

import com.petsCare.petsCare.memory.entity.QMemory;
import com.petsCare.petsCare.pet.dto.form.PetsForm;
import com.petsCare.petsCare.pet.dto.form.QPetsForm;
import com.petsCare.petsCare.pet.entity.QPet;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.petsCare.petsCare.memory.entity.QMemory.*;
import static com.petsCare.petsCare.pet.entity.QPet.*;

public class PetRepositoryImpl implements PetRepository {

	private final JPAQueryFactory jpaQueryFactory;

	public PetRepositoryImpl(EntityManager entityManager) {
		this.jpaQueryFactory = new JPAQueryFactory(entityManager);
	}

	@Override
	public List<PetsForm> showPets(Long userId) {
		return jpaQueryFactory
				.select(new QPetsForm(
						pet.id, pet.petName, pet.profile, memory.memoryType
						))
				.from(pet)
				.where(pet.user.id.eq(userId))
				.leftJoin(memory)
				.on(pet.profile.eq(memory.uploadFile.saveFileName))
				.fetch();
	}
}
