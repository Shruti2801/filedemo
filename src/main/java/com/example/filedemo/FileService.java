package com.example.filedemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

	@Autowired
	PhotoRepo photoRepo;
	private static final Logger LOG = LoggerFactory.getLogger(FileService.class);
	
	
	public String uploadImage(String path, MultipartFile file) throws IOException {
		LOG.debug("path...{}   file...{}",path,file);
		//file name
		String name = file.getOriginalFilename();
		
		//full paths
//		String filePath = path + File.separator + name;
		
//		LOG.debug("filePAth...."+filePath);
		//create folder if not created 
//		File f = new File(path);
//		if(!f.exists()) {
//			f.mkdir();
//		}
	
		//file copy
//		Files.copy(file.getInputStream(), Paths.get(filePath));
		LOG.debug("name... "+name);
		
		
		Photo photo = new Photo();
		
		photo.setCategoryPhoto(IOUtils.toByteArray(file.getInputStream()));
		photo.setName(name);
		
		photoRepo.save(photo);
		
		return name;
		
	}

	

	public byte[] getimage(String path, String name) throws IOException {
		
		String filePath = path +  name;
		LOG.debug("filepath... "+filePath);
		InputStream in = new FileInputStream(filePath);
//		Path filePathf = Paths.get(filePath);
//		InputStream in = Files.newInputStream(filePathf, StandardOpenOption.READ);
		LOG.debug("input.."+ in);
		return IOUtils.toByteArray(in);
//			    return in.readAllBytes();
	}



	public byte[] getfromDb(String name) {
		Photo photo = photoRepo.findByName(name);
		byte[] bs = photo.getCategoryPhoto();
		LOG.debug("bs.."+bs.toString());
		return bs;
	}
	
	
	

}
