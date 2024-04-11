package com.petsCare.petsCare.memory.repository;

import com.petsCare.petsCare.memory.dto.form.*;
import com.petsCare.petsCare.memory.entity.MemoryType;
import com.petsCare.petsCare.user.dto.UserDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalTime;
import java.util.List;

import static com.petsCare.petsCare.memory.entity.QMemory.*;

public class MemoryRepositoryImpl implements MemoryRepository {

	private final JPAQueryFactory jpaQueryFactory;

	public MemoryRepositoryImpl(EntityManager em) {
		this.jpaQueryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Page<MemorySimpleForm> findSimpleFormByPet(UserDto userDto, Long petId, Pageable pageable) {
		List<MemorySimpleForm> content = jpaQueryFactory
				.select(new QMemorySimpleForm(
						memory.id, memory.memoryType, memory.uploadFile.saveFileName
				))
				.from(memory)
				.where(
						petIdEq(petId),
						userIdEq(userDto.getId())
				)
				.orderBy(
						memory.manageTime.imageTime.desc(),
						memory.id.desc()
				)
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();

		JPAQuery<Long> countQuery = jpaQueryFactory
				.select(memory.count())
				.from(memory)
				.where(
						petIdEq(petId)
				);

		return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
	}

	@Override
	public MemoryDetailForm findMemoryDetailById(UserDto userDto, Long memoryId) {
		return jpaQueryFactory
				.select(new QMemoryDetailForm(memory))
				.from(memory)
				.where(
						memory.id.eq(memoryId),
						userIdEq(userDto.getId())
				)
				.fetchOne();
	}

	@Override
	public List<MemoryWalkAbstractResponse> findMemoryWalkFormByPet(UserDto userDto, MemoryWalkAbstractRequest memoryWalkAbstractRequest) {
		return jpaQueryFactory
				.select(new QMemoryWalkAbstractResponse(memory))
				.from(memory)
				.where(
						userIdEq(userDto.getId()),
						petIdEq(memoryWalkAbstractRequest.getPetId()),
						memory.gps.latitude.isNotNull(),
						memory.gps.longitude.isNotNull(),
						memory.memoryType.eq(MemoryType.IMAGE),
						imageTimeBetween(memoryWalkAbstractRequest)
				)
				.fetch();
	}

	@Override
	public MemoryWalkInfoResponse findMemoryWalkInfoByMemory(UserDto userDto, Long memoryId) {
		return jpaQueryFactory
				.select(new QMemoryWalkInfoResponse(
						memory
				))
				.from(memory)
				.where(
						userIdEq(userDto.getId()),
						memoryIdEq(memoryId)
				)
				.fetchOne();
	}

	private BooleanExpression userIdEq(Long userId) {
		return userId == null ? null : memory.pet.user.id.eq(userId);
	}

	private BooleanExpression petIdEq(Long petId) {
		return petId == null ? null : memory.pet.id.eq(petId);
	}

	private BooleanExpression memoryIdEq(Long memoryId) {
		return memoryId == null ? null : memory.id.eq(memoryId);
	}

	private BooleanExpression imageTimeBetween(MemoryWalkAbstractRequest memoryWalkAbstractRequest) {
		return memory.manageTime.imageTime.between(memoryWalkAbstractRequest.getStartTime().atStartOfDay(), memoryWalkAbstractRequest.getEndTime().atTime(LocalTime.MAX));
	}
}
