package com.example.test2b1.service;

import com.example.test2b1.entity.Record;
import com.example.test2b1.pojo.MyCell;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface WorkWithExcelService {
    void save(MultipartFile file);
    List<Record> getAllRecords();
    Map<Integer, List<MyCell>> show(String fileLocation) throws IOException;
    void saveInFile(String url) throws Exception;
    void saveInJSon(MultipartFile file) throws IOException;
}
