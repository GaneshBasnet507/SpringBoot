package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;

public interface IExcelDataService {
    List<User> getExcelDataAsList();
    int saveExcelData(List<User> users);
}
