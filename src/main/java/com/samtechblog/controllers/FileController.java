package com.samtechblog.controllers;

import com.samtechblog.payloads.FileResponse;
import com.samtechblog.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {
    //service instance
    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    String path;

    @PostMapping("/upload")
    public ResponseEntity<FileResponse> uploadFile(@RequestParam("image") MultipartFile image) {
        String fileName=null;
        try
        {
            fileName = this.fileService.uploadImage(path, image);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>(new FileResponse(null, "Image is not Uploaded due to some internal error."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new FileResponse(fileName, "Image is Successfully Uploaded."), HttpStatus.OK);
    }
}
