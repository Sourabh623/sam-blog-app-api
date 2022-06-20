package com.samtechblog.services.impl;

import com.samtechblog.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        //file name
        String fileName = file.getOriginalFilename();
        System.out.println("file name is "+fileName);
        System.out.println("file path is "+path);

        //random uuid generate
        String Uid = UUID.randomUUID().toString();
        String fileName1 = Uid.concat(fileName.substring(fileName.lastIndexOf(".")));
        System.out.println("new file name is "+fileName1);

        //full path of the file
        String fullFilePath = path+ File.separator+fileName1;
        System.out.println("new and full file path is "+fullFilePath);

        //folder created if it is not available
        File file1 = new File(path);
        if(!file1.exists()) file1.mkdir();

        //file copy in images/ directory
        Files.copy(file.getInputStream(), Paths.get(fullFilePath));

        return fileName1;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {

        //full path of the file
        String fullFilePath = path+File.separator+fileName;
        return new FileInputStream(fullFilePath);
    }
}
