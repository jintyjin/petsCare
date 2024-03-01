package com.petsCare.petsCare.memory.entity;

import com.petsCare.petsCare.pet.entity.Pet;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Memory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "memory_id")
	private Long id;

	@Embedded
	private UploadFile uploadFile;

	@Embedded
	private Gps gps;

	@Embedded
	private ManageTime manageTime;

	@Enumerated(value = EnumType.STRING)
	private MemoryStatus memoryStatus;

	@Enumerated(value = EnumType.STRING)
	private MemoryType memoryType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pet_id")
	private Pet pet;

	public Memory(UploadFile uploadFile, Gps gps, ManageTime manageTime, Pet pet) {
		this.uploadFile = uploadFile;
		this.gps = gps;
		this.manageTime = manageTime;
		this.memoryStatus = MemoryStatus.NORMAL;
		this.pet = pet;
	}
}
