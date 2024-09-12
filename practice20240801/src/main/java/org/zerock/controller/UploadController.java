package org.zerock.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.AttachFileDTO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
public class UploadController {
	
	/* 이미지 파일 여부 체크 */
	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			return contentType.startsWith("image");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	} // checkImageType 닫음
	
	/*날짜별로  폴더 만들기 위한 메소드*/
	private String getFolder () {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-", File.separator);
	} // getFolder 닫음
	
	@PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_VALUE) //수정
	@ResponseBody // @ResponseBody 추가
	public ResponseEntity<List<AttachFileDTO>> // void지우고 ResponseEntity타입으로 수정
	uploadAjaxPost(MultipartFile[] uploadFile) {
		log.info("업데이트 AJAX 포스트(update ajax post)....");
		
		List<AttachFileDTO> list = new ArrayList<>(); // 추가
		String uploadFolder = "C:\\zzz\\upload";
		
		String uploadFolderPath = getFolder(); // 추가 
		// make folder --------------------
		File uploadPath = new File(uploadFolder, uploadFolderPath); // getFolder()를 uploadFolderPath로 수정
		log.info("업로드 패스(upload path): " + uploadPath);
		
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		} // make yyyy/MM/dd folder
		
		for (MultipartFile multipartFile : uploadFile) {
			log.info("--------------------------");
			log.info("업로드 파일 이름(upload file name): " + multipartFile.getOriginalFilename());
			log.info("업로드 파일 사이즈(upload file size): " + multipartFile.getSize());
			
			AttachFileDTO attachDTO = new AttachFileDTO(); // 추가
			
			String uploadFileName = multipartFile.getOriginalFilename();
			
			// IE has file path
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
			log.info("파일 이름만(only file name): " + uploadFileName);
			attachDTO.setFileName(uploadFileName); // 20240912.: 작일(昨日)에 해당 코드가 누락되어서 json에서 fileName이 null인 상태로 전송됨
			
			// 파일 이름에 uuid 붙임 
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			try {
				File saveFile = new File(uploadPath, uploadFileName); // try catch문 위에 있던 saveFIle을 try안으로 넣음
				multipartFile.transferTo(saveFile);
				
				attachDTO.setUuid(uuid.toString()); // 추가
				attachDTO.setUploadPath(uploadFolderPath); // 추가
				
				// check imagr type file
				if(checkImageType(saveFile)) {
					
					attachDTO.setImage(true); // 추가
					
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
					thumbnail.close();
				} // if문 닫음
				
				// add to List
				list.add(attachDTO); // 추가
				
			} catch(Exception e){
				log.error(e.getMessage());
			} // catch 닫음
			
		} // for 닫음
		return new ResponseEntity<>(list, HttpStatus.OK);		
	} // uploadAjaxPost닫음
		
/*		
	@PostMapping("/uploadAjaxAction")
	public void uploadAjaxPost(MultipartFile[] uploadFile) {
		log.info("업데이트 AJAX 포스트(update ajax post)....");
		
		String uploadFolder = "C:\\zzz\\upload";
		
		// make folder --------------------
		File uploadPath = new File(uploadFolder, getFolder());
		log.info("업로드 패스(upload path): " + uploadPath);
		
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		} // make yyyy/MM/dd folder
		
		for (MultipartFile multipartFile : uploadFile) {
			log.info("--------------------------");
			log.info("업로드 파일 이름(upload file name): " + multipartFile.getOriginalFilename());
			log.info("업로드 파일 사이즈(upload file size): " + multipartFile.getSize());
			
			String uploadFileName = multipartFile.getOriginalFilename();
			
			// IE has file path
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
			log.info("파일 이름만(only file name): " + uploadFileName);
			
			// 파일 이름에 uuid 붙임 
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			try {
				File saveFile = new File(uploadPath, uploadFileName); // try catch문 위에 있던 saveFIle을 try안으로 넣음
				multipartFile.transferTo(saveFile);
				
				// check imagr type file
				if(checkImageType(saveFile)) {
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
					thumbnail.close();
				} // if문 닫음
				
			} catch(Exception e){
				log.error(e.getMessage());
			} // catch 닫음
			
		} // for 닫음
				
	} // uploadAjaxPost닫음
*/	
	@GetMapping("/uploadAjax")
	public void uploadAjax () {
		log.info("업로드 AJAX페이지(upload ajax)");
	}
	
	@GetMapping("/uploadForm")
	public void uploadForm() {
		log.info("업로드폼(upload form)");
	}
	
	@PostMapping("/uploadFormAction")
	public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
		
		String uploadFolder = "C:\\zzz\\upload";
		
		for (MultipartFile multipartFile : uploadFile) {
			log.info("---------------------------------");
			log.info("업로드 파일 이름(Upload File Name): " + multipartFile.getOriginalFilename());
			log.info("업로드 파일 사이즈(Upload File Size): " + multipartFile.getSize());
			
			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
			try {
				multipartFile.transferTo(saveFile);
			} catch (Exception e) {
				log.error(e.getMessage());
			} // catch 닫음
			
		} // for 닫음
	} // uploadFormPost 닫음
	

	
}
