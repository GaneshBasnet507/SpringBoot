package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.FileUploadExcel;
import com.example.demo.service.IExcelDataService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
@RestController
@RequestMapping("api/upload")
@Controller
public class FileUploadController {

    @Autowired
    FileUploadExcel fileUploadExcel;
    @Autowired
    //excellService
    IExcelDataService excelService;

    @Autowired
    UserRepository userRepository;
    @PostMapping("/uploadFile")
        public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
       try {
           fileUploadExcel.uploadFile(file);
           // Process the Excel file and save data to the database
           List<User> excelData = excelService.getExcelDataAsList();
           int noOfRecords = excelService.saveExcelData(excelData);
           redirectAttributes.addFlashAttribute("message", "You have successfully uploaded");
       }

       catch (Exception e) {
           redirectAttributes.addFlashAttribute("message", "Error uploading file: " + e.getMessage());
       }

        return "Successful upload files!";

    }
}
