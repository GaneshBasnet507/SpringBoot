package com.example.demo.service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface  FileUploadExcel {
    public void uploadFile(MultipartFile file);
}
