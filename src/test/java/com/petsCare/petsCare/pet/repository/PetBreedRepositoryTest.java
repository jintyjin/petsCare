package com.petsCare.petsCare.pet.repository;

import com.petsCare.petsCare.pet.entity.PetBreed;
import com.petsCare.petsCare.pet.entity.PetType;
import com.petsCare.petsCare.pet.repository.PetBreedRepository;
import com.petsCare.petsCare.pet.repository.PetTypeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class PetBreedRepositoryTest {

	@Autowired
	PetBreedRepository petBreedRepository;

	@Autowired
	PetTypeRepository petTypeRepository;

	@Test
	@DisplayName("저장 및 불러오기 테스트")
	void saveAndFind() {
		//given
		PetType dogType = new PetType("강아지");
		PetType catType = new PetType("고양이");

		petTypeRepository.save(dogType);
		petTypeRepository.save(catType);

		PetBreed dog1 = new PetBreed("닥스훈트", dogType);
		PetBreed dog2 = new PetBreed("진돗개", dogType);
		PetBreed tiger = new PetBreed("호랑이", catType);

		//when
		petBreedRepository.save(dog1);
		petBreedRepository.save(dog2);
		petBreedRepository.save(tiger);

		List<PetBreed> dogs = petBreedRepository.findByPetTypeId(dogType.getId());
		List<PetBreed> cats = petBreedRepository.findByPetTypeId(catType.getId());

		//then
		assertThat(dogs.size()).isEqualTo(2);
		assertThat(cats.size()).isEqualTo(1);
	}
}