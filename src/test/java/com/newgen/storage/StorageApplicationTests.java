package com.newgen.storage;

import com.newgen.storage.entity.StorageFile;
import com.newgen.storage.service.StorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.time.Instant;
import java.util.Date;
import java.util.Scanner;

@SpringBootTest
class StorageApplicationTests {

	@Autowired
	StorageService storageService;

	@Test
	void contextLoads() {

		StorageFile file=new StorageFile();
		file.setFileType("txt");
		file.setId("one");
		file.setName("one");
		file.setUserId("first");
		file.setSavedDate(new Date());
		InputStream ios=new ByteArrayInputStream("hello one this is me ".getBytes());


		 var rs=storageService.saveFileData(file,ios);
		System.out.println(" the result is " + rs );

	}

}
