package com.petsCare.petsCare.repository.pet;

import com.petsCare.petsCare.entity.pet.PetBreed;
import com.petsCare.petsCare.entity.pet.PetType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

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

		List<PetBreed> dogs = petBreedRepository.findByPetTypeType(dogType.getType());
		List<PetBreed> cats = petBreedRepository.findByPetTypeType(catType.getType());

		//then
		assertThat(dogs.size()).isEqualTo(2);
		assertThat(cats.size()).isEqualTo(1);
	}
}