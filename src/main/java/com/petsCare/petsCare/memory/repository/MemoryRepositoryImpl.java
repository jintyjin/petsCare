package com.petsCare.petsCare.memory.repository;

import com.petsCare.petsCare.memory.dto.form.MemoryDetailForm;
import com.petsCare.petsCare.memory.dto.form.MemorySimpleForm;
import com.petsCare.petsCare.memory.dto.form.QMemoryDetailForm;
import com.petsCare.petsCare.memory.dto.form.QMemorySimpleForm;
import com.petsCare.petsCare.pet.entity.QPet;
import com.petsCare.petsCare.user.dto.UserDto;
import com.petsCare.petsCare.user.entity.QUser;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.petsCare.petsCare.memory.entity.QMemory.*;
import static com.petsCare.petsCare.pet.entity.QPet.*;
import static com.petsCare.petsCare.user.entity.QUser.*;

public class MemoryRepositoryImpl implements MemoryRepository {

	private final JPAQueryFactory jpaQueryFactory;

	public MemoryRepositoryImpl(EntityManager em) {
		this.jpaQueryFactory = new JPAQueryFactory(em);
	}

	public Page<MemorySimpleForm> findSimpleFormByPet(UserDto userDto, Long petId, Pageable pageable) {
		List<MemorySimpleForm> content = jpaQueryFactory
				.select(new QMemorySimpleForm(
						memory.id, memory.memoryType, memory.uploadFile.saveFileName
				))
				.from(memory)
				.where(
						petIdEq(petId),
						memory.pet.user.id.eq(userDto.getId())
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

	public MemoryDetailForm findMemoryDetailById(UserDto userDto, Long memoryId) {
		return jpaQueryFactory
				.select(new QMemoryDetailForm(memory))
				.from(memory)
				.where(
						memory.id.eq(memoryId),
						memory.pet.user.id.eq(userDto.getId())
				)
				.fetchOne();
	}

	private BooleanExpression petIdEq(Long petId) {
		return petId == null ? null : memory.pet.id.eq(petId);
	}
}
