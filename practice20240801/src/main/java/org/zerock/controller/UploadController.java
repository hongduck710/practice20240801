package org.zerock.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.http.HttpHeaders;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;

import org.zerock.domain.AttachFileDTO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
public class UploadController {
	
	/* 파일 다운로드 */
	@GetMapping(value = "/download", produces= MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName) {
		log.info("다운로드 파일(download file): " + fileName);
		Resource resource = new FileSystemResource("C:\\zzz\\upload\\" + fileName);
		if(resource.exists() == false) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		log.info("리소스(resource): " + resource);
		
		String resourceName = resource.getFilename();
		
		HttpHeaders headers = new HttpHeaders();
		
		try {
			String downloadName = null;
			
			if(userAgent.contains("Trident")) {
				log.info("IE 브라우저(IE browser)");
				downloadName = URLEncoder.encode(resourceName, "UTF-8").replaceAll("\\", " ");
			} else if(userAgent.contains("Edge")) {
				log.info("엣지 브라우저(Edge browser)");
				downloadName = URLEncoder.encode(resourceName, "UTF-8");
				log.info("엣지 이름(Edge name): " + downloadName);
			} else {
				log.info("크롬 브라우저(Chrome browser)");
				downloadName = new String(resourceName.getBytes("UTF-8"), "ISO-8859-1");
			} // if else 닫음
			
			headers.add("Content-Disposition", "attachment; filename=" + downloadName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	} // downloadFile 닫음
	
	/* 섬네일 데이터 전송 */
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName) { /* getFile은 문자열로 파일의 경로가 포함된 fileName을 파라미터로 받고 byte[]를 전송함 */
		log.info("파일이름(fileName): " + fileName);
		File file = new File("C:\\zzz\\upload\\" + fileName);
		log.info("파일(file): " + file);
		
		ResponseEntity<byte[]> result = null;
		
		try {
			HttpHeaders header = new HttpHeaders();
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	} // getFile 닫음
	
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
