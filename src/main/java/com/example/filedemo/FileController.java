package com.example.filedemo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
//import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.java.Log;



@RestController
@RequestMapping("/file")
public class FileController {
	private static final Logger LOG = LoggerFactory.getLogger(FileController.class);
	@Autowired
	FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/upload")
	public ResponseEntity<FileResponse> fileUpload(@RequestParam("image") MultipartFile image) {
		LOG.debug("image.."+image);
		String fileName = null;
		try {
		fileName = fileService.uploadImage(path, image);}
		catch(IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(new FileResponse(null , " Image is not uploaded"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(new FileResponse(fileName , " Image is uploaded successfully"),HttpStatus.OK);
		
	}
	
	@GetMapping()
	public String hello(){
		LOG.debug("image..");
		return "hello";
	}
	
	@GetMapping(value = "/image/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getImage(@PathVariable String name) throws IOException {
		byte[] image = fileService.getimage(path, name);

	    return image;
	}
	
	@GetMapping(value = "/get/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getfromDb(@PathVariable String name) throws IOException {
		
		byte[] image = fileService.getfromDb( name);

	    return image;
	}
	
	
	
}
