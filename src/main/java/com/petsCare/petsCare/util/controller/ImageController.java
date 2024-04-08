package com.petsCare.petsCare.util.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;

@Controller
@Slf4j
public class ImageController {

	@Value("${file.dir}")
	private String fileDir;

	@ResponseBody
	@GetMapping("/images/{loginId}/{folder}/{filename}")
	public Resource showImage(@PathVariable String loginId, @PathVariable String folder,
							  @PathVariable String filename) throws MalformedURLException {
		return new UrlResource("file:" + fileDir + loginId + "/" + folder + "/" + filename);
	}
}
