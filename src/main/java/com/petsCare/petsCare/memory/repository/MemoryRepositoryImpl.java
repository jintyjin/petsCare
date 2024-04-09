package com.petsCare.petsCare.memory.repository;

import com.petsCare.petsCare.memory.dto.form.*;
import com.petsCare.petsCare.memory.entity.MemoryType;
import com.petsCare.petsCare.pet.entity.QPet;
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
import static com.petsCare.petsCare.pet.entity.QPet.*;

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
	public List<MemoryWalkResponseForm> findMemoryWalkFormByPet(UserDto userDto, MemoryWalkRequestForm memoryWalkRequestForm) {
		return jpaQueryFactory
				.select(new QMemoryWalkResponseForm(
						memory.id, memory.uploadFile.saveFileName, memory.gps.latitude, memory.gps.longitude, memory.manageTime.imageTime,
						memory.imageSize.width, memory.imageSize.height, memory.memoryType
				))
				.from(memory)
				.where(
						userIdEq(userDto.getId()),
						petIdEq(memoryWalkRequestForm.getPetId()),
						memory.gps.latitude.isNotNull(),
						memory.gps.longitude.isNotNull(),
						memory.memoryType.eq(MemoryType.IMAGE),
						imageTimeBetween(memoryWalkRequestForm)
				)
				.fetch();
	}

	private BooleanExpression userIdEq(Long userId) {
		return userId == null ? null : memory.pet.user.id.eq(userId);
	}
	private BooleanExpression petIdEq(Long petId) {
		return petId == null ? null : memory.pet.id.eq(petId);
	}

	private BooleanExpression imageTimeBetween(MemoryWalkRequestForm memoryWalkRequestForm) {
		return memory.manageTime.imageTime.between(memoryWalkRequestForm.getStartTime().atStartOfDay(), memoryWalkRequestForm.getEndTime().atTime(LocalTime.MAX));
	}
}
