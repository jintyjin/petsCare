package com.petsCare.petsCare.memory.service;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import com.petsCare.petsCare.memory.dto.form.*;
import com.petsCare.petsCare.memory.entity.*;
import com.petsCare.petsCare.memory.exception.MemoryException;
import com.petsCare.petsCare.memory.repository.JpaMemoryRepository;
import com.petsCare.petsCare.pet.entity.Pet;
import com.petsCare.petsCare.pet.exception.PetException;
import com.petsCare.petsCare.pet.repository.JpaPetRepository;
import com.petsCare.petsCare.user.dto.UserDto;
import com.petsCare.petsCare.websocket.WebSocketService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemoryService {

	private final WebSocketService webSocketService;
	private final JpaPetRepository jpaPetRepository;
	private final JpaMemoryRepository memoryRepository;

	@Value("${file.dir}")
	private String fileDir;

	@Transactional
	public void make(MemoryForm memoryForm, UserDto userDto) {
		Pet pet = jpaPetRepository.findById(memoryForm.getPetId())
				.orElseThrow(() -> PetException.PET_CAN_NOT_FIND_EXCEPTION);

		List<MultipartFile> files = memoryForm.getFiles();

		String loginId = userDto.getLoginId();

		saveMemory(files, loginId, pet);
	}

	public String saveMemory(List<MultipartFile> files, String loginId, Pet pet) {
		LocalDateTime today = LocalDateTime.now();

		String path = makeFolders(loginId, today);

		List<String> filePathList = new ArrayList<>();

		String thumbnail = "";

		try {
			int count = 1;
			int totalCount = files.size();

			for (MultipartFile file : files) {
				String saveFileName = UUID.randomUUID().toString().replaceAll("-", "_") + "." + file.getContentType().split("/")[1];

				String filePath = path + "/" + saveFileName;
				filePathList.add(filePath);

				String savePath = saveImage(filePath, file);

				saveFileName = new StringBuilder(filePath).substring(fileDir.length());

				thumbnail = saveFileName;

				saveMemoryData(today, file, saveFileName, savePath, pet);

				webSocketService.makeNotice("/adopt/notice" ,count, totalCount);

				count++;
			}
		} catch (Exception e) {
			deleteMemory(filePathList);
			webSocketService.exceptionNotice("/adopt/notice");
			e.printStackTrace();
			throw MemoryException.MEMORY_MAKE_EXCEPTION;
		}

		return thumbnail;
	}

	public List<MemorySimpleForm> reminisce(UserDto userDto, @Nullable Long petId, Pageable pageable) {
		return memoryRepository.findSimpleFormByPet(userDto, petId, pageable)
				.getContent();
	}

	public MemoryDetailForm showMemoryDetail(UserDto userDto, Long memoryId) {
		return memoryRepository.findMemoryDetailById(userDto, memoryId);
	}

	public List<MemoryWalkAbstractResponse> showMemoryWalk(UserDto userDto, MemoryWalkAbstractRequest memoryWalkAbstractRequest) {
		return memoryRepository.findMemoryWalkFormByPet(userDto, memoryWalkAbstractRequest);
	}

	public MemoryWalkInfoResponse showMemoryWalkInfo(UserDto userDto, Long memoryId) {
		return memoryRepository.findMemoryWalkInfoByMemory(userDto, memoryId);
	}

	private void deleteMemory(List<String> filePathList) {
		for (String filePath : filePathList) {
			File file = new File(filePath);
			if (file.exists()) {
				file.delete();
			}
		}
	}

	private void saveMemoryData(LocalDateTime today, MultipartFile file, String saveFileName, String savePath, Pet pet) throws ImageProcessingException, IOException {
		UploadFile uploadFile = new UploadFile(file.getOriginalFilename(), saveFileName);
		File saveFile = new File(savePath);
		Gps gps = extractGps(saveFile);
		ManageTime manageTime = new ManageTime(extractLocalDateTime(saveFile, today));
		MemoryType memoryType = MemoryType.valueOf(file.getContentType().split("/")[0].toUpperCase());
		ImageSize imageSize = extractImageSize(saveFile, memoryType);
		Memory memory = new Memory(uploadFile, gps, manageTime, imageSize, memoryType, pet);
		memoryRepository.save(memory);
	}

	private String makeFolders(String loginId, LocalDateTime today) {
		String[] paths = {fileDir, loginId, "/" + today.format(DateTimeFormatter.ofPattern("yyyyMMdd"))};

		StringBuilder path = new StringBuilder();

		for (String p : paths) {
			path.append(p);
			File file = new File(path.toString());
			if (!file.exists()) {
				file.mkdir();
			}
		}

		return path.toString();
	}

	private String saveImage(String path, MultipartFile file) throws IOException {
		file.transferTo(new File(path));

		return path;
	}

	private Gps extractGps(File file) throws ImageProcessingException, IOException {
		Metadata metadata = ImageMetadataReader.readMetadata(file);

		GpsDirectory directory = metadata.getFirstDirectoryOfType(GpsDirectory.class);

		if (checkNull(directory)) {
			return null;
		}

		GeoLocation geoLocation = directory.getGeoLocation();
		return new Gps(new BigDecimal(geoLocation.getLatitude()), new BigDecimal(geoLocation.getLongitude()));
	}

	public LocalDateTime extractLocalDateTime(File file, LocalDateTime today) throws ImageProcessingException, IOException {
		Metadata metadata = ImageMetadataReader.readMetadata(file);

		ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

		if (checkNull(directory)) {
			return today;
		}

		Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);

		if (checkNull(date)) {
			return today;
		}

		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	public ImageSize extractImageSize(File file, MemoryType memoryType) throws IOException {
		ImageSize imageSize = null;

		if (memoryType == MemoryType.IMAGE) {
			BufferedImage bi = ImageIO.read(file);
			imageSize = new ImageSize(bi.getWidth(), bi.getHeight());
		}

		return imageSize;
	}

	private boolean checkNull(Object object) {
		if (object == null) {
			return true;
		}
		return false;
	}
}
