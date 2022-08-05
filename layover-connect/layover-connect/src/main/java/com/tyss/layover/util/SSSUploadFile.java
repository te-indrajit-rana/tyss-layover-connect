package com.tyss.layover.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.tyss.layover.exception.FailedToUploadException;

@Component
public class SSSUploadFile {

	/**
	 * This is the end point url for amazon s3 bucket
	 */
	@Value("${amazon-properties.endpoint-url}")
	private String endpointUrl;
	/**
	 * This is the bucketName for amazon s3 bucket
	 */
	@Value("${amazon-properties.bucket-name}")
	public String bucketName;

	/**
	 * This enables automatic dependency injection of AmazonS3 interface. This
	 * object is used by methods in the SimpleProductServiceImplementation to call
	 * the respective methods.
	 */
	@Autowired
	private AmazonS3 s3client;

	public String uploadFile(MultipartFile multipartFile) {
		String fileUrl = "";
		try {
			File file = convertMultiPartFiletoFile(multipartFile);
			String fileName = generateFileName(multipartFile);
			fileUrl = uploadFileTos3bucketConfig(file, fileName, multipartFile.getOriginalFilename());
		} catch (Exception e) {
			throw new FailedToUploadException("Failed to upload.");
		}
		return fileUrl;
	}

	private File convertMultiPartFiletoFile(MultipartFile file) throws IllegalStateException, IOException {
		File convFile = new File(System.getProperty("java.io.tmpdir") + File.separator + file.getOriginalFilename());
		file.transferTo(convFile);
		return convFile;
	}

	private String generateFileName(MultipartFile multiPart) {
		String fileName = multiPart.getOriginalFilename();
		if (fileName != null) {
			return UUID.randomUUID()+ File.separator + fileName.replace(" ", "_");
		} else {
			throw new FailedToUploadException("File name is empty.");
		}

	}

	public String uploadFileTos3bucketConfig(File file, String fileName, String originalName) {
		String filePath = "img/" + fileName;
		s3client.putObject(
				new PutObjectRequest(bucketName, filePath, file).withCannedAcl(CannedAccessControlList.PublicRead));
		String url = s3client.getUrl(bucketName, filePath).toString();
		Path path = Paths.get((System.getProperty("java.io.tmpdir") + File.separator + originalName));
		try {
			Files.delete(path);
		} catch (IOException e) {
			return url;
		}
		return url;
	}

	public void deleteS3Folder(String folderPath) {
		if (folderPath.length() > 1) {
			String path = folderPath.replace("https://layover-connect-s3.s3.ap-south-1.amazonaws.com/", "");
			for (S3ObjectSummary file : s3client.listObjects(bucketName, path).getObjectSummaries()) {
				s3client.deleteObject(bucketName, file.getKey());
			}
		}
	}

}
