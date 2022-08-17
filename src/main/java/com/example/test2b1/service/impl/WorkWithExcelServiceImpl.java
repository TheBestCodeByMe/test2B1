package com.example.test2b1.service.impl;

import com.example.test2b1.entity.Record;
import com.example.test2b1.pojo.MyCell;
import com.example.test2b1.repo.RecordRepository;
import com.example.test2b1.service.WorkWithExcelService;
import com.example.test2b1.service.helpers.ExcelHelper;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WorkWithExcelServiceImpl implements WorkWithExcelService {
    private final RecordRepository recordRepository;

    @Override
    public void save(MultipartFile file) {
        try {
            List<Record> records = ExcelHelper.excelToRecords(file.getInputStream());
            recordRepository.saveAll(records);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    @Override
    public Map<Integer, List<MyCell>> show(String fileLocation) throws IOException {
        ExcelHelper excelHelper = new ExcelHelper();
        return excelHelper.readExcel(fileLocation);
    }

    @Override
    public List<Record> getAllRecords() {
        return recordRepository.findAll();
    }

    @Override
    public void saveInFile(String uRL) throws Exception {
        OutputStream out = new FileOutputStream("d:/test.html");

        URL url = new URL(uRL);
        URLConnection conn = url.openConnection();
        conn.connect();
        InputStream is = conn.getInputStream();

        copy(is, out);
        is.close();
        out.close();
    }

    private void copy(InputStream from, OutputStream to) throws IOException {
        byte[] buffer = new byte[4096];
        while (true) {
            int numBytes = from.read(buffer);
            if (numBytes == -1) {
                break;
            }
            to.write(buffer, 0, numBytes);
        }
    }

    @Override
    public void saveInJSon(MultipartFile file) throws IOException {
        List<Record> records = ExcelHelper.excelToRecords(file.getInputStream());
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
            objectWriter.writeValue(new File("D:\\" + records.get(0).getFileId().getBankId().getName() + records.get(0).getFileId().getName() + ".json"), records);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
