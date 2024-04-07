package com.petsCare.petsCare.weight.repository;

import com.petsCare.petsCare.pet.entity.Pet;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import static com.petsCare.petsCare.pet.entity.QPet.*;
import static com.petsCare.petsCare.weight.entity.QWeight.*;

public class WeightRepositoryImpl implements WeightRepository {

	private final JPAQueryFactory jpaQueryFactory;

	public WeightRepositoryImpl(EntityManager entityManager) {
		this.jpaQueryFactory = new JPAQueryFactory(entityManager);
	}

	@Override
	public Pet findPetForWeights(Long petId) {
		return jpaQueryFactory
				.select(pet)
				.from(pet)
				.leftJoin(pet.weights, weight1)
				.fetchJoin()
				.where(
						pet.id.eq(petId)
				)
				.fetchOne();
	}
}
