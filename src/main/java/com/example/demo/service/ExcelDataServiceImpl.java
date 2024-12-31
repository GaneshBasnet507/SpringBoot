package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public class ExcelDataServiceImpl implements IExcelDataService {
    @Value("${app.upload.file:${user.home}}")
    public String EXCEL_FILE_PATH;

    @Autowired
    UserRepository userRepository;
    Workbook workbook;

    @Override
    public List<User> getExcelDataAsList() {
        List<String> list = new ArrayList<String>();
        //Create a DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter = new DataFormatter();

        // Create the Workbook
        try {
            workbook = WorkbookFactory.create(new File(EXCEL_FILE_PATH));
        } catch (EncryptedDocumentException | IOException e) {
            e.printStackTrace();
        }
        // Retrieving the number of sheets in the Workbook
        System.out.println("-------Workbook has '" + workbook.getNumberOfSheets() + "' Sheets-----");
        //Getting the sheet at zero index
        Sheet sheet = workbook.getSheetAt(0);

        // Getting number of columns in the Sheet
        int noOfColumns = sheet.getRow(0).getLastCellNum();
        System.out.println("-------Sheet has '" + noOfColumns + "' columns------");
        // Skipping the header row (index 0) and processing the rest of the rows
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {  // Start from 1 to skip header
            Row row = sheet.getRow(rowIndex);

            // Loop through each cell in the row
            for (int colIndex = 0; colIndex < noOfColumns; colIndex++) {
                Cell cell = row.getCell(colIndex);
                String cellValue = dataFormatter.formatCellValue(cell);
                list.add(cellValue);
            }
        }
        // filling excel data and creating list as List<User>
        List<User> userList = createList(list, noOfColumns);
        // Closing the workbook
        try {
            workbook.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return userList;
    }

    private List<User> createList(List<String> excelData, int noOfColumns) {
        ArrayList<User> userList = new ArrayList<User>();

        // Start iterating from the first row of data (after the header row)
        for (int i = 0; i < excelData.size(); i += noOfColumns) {
            User user = new User();


            user.setFullName(excelData.get(i));
            user.setUserName(excelData.get(i + 1));
            user.setAddress(excelData.get(i + 2));
            user.setPhoneNo(excelData.get(i + 3));
            user.setEmail(excelData.get(i + 4));
            user.setPassword(excelData.get(i + 5));
            user.setRole(excelData.get(i + 6));

            userList.add(user);
        }
        return userList;
    }

    @Override
    public int saveExcelData(List<User> users) {
        if (users != null && !users.isEmpty()) {
            users = userRepository.saveAll(users);
            System.out.println("Saved " + users.size() + " users to the database.");
            return users.size();
        }
        return 0;
    }
}
