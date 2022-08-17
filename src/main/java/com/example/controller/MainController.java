package com.example.controller;

import com.example.test2b1.pojo.MyCell;
import com.example.test2b1.service.WorkWithExcelService;
import com.example.test2b1.service.helpers.ExcelHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MainController {
    private final WorkWithExcelService workWithExcelService;

    @Value("${message}")
    private static String message = "";

    @Value("${data}")
    private static Map<Integer, List<MyCell>> data;

    @Value("${files}")
    private static List<String> files;

    @GetMapping(value = { "/main"})
    public ModelAndView index() {
        String mess = "Загрузите файл";
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("mess", mess);
        message="qwerqwert";
        modelAndView.addObject(
                "message", message
        );
        return modelAndView;
    }

    @PostMapping("/import")
    //public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
    public ModelAndView uploadFile(@RequestParam("file") MultipartFile file) {
        ModelAndView mav = new ModelAndView();
        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                workWithExcelService.save(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                StringBuilder resultStringBuilder = new StringBuilder();
                try (BufferedReader br
                             = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        resultStringBuilder.append(line).append("\n");
                    }
                }
                files.add(file.getOriginalFilename());
                //model.addAttribute("files", files);
                mav.addObject("files", files);
                data = workWithExcelService.show(resultStringBuilder.toString());
                mav.addObject("data", data);
                //session.setAttribute("data", data);
                // return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                //return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }
        message = "Please upload an excel file!";
        return mav;
        //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping(value = {"/savePage"})
    public String savePage(String url) throws Exception {
        workWithExcelService.saveInFile(url);
        return "index";
    }

    @GetMapping(value = {"/saveFileJSon"})
    public String saveInJSON(@RequestParam("fil") MultipartFile file) {
        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                workWithExcelService.saveInJSon(file);
            } catch (Exception e) {
                message = "Could not save the file: " + file.getOriginalFilename() + "!";
            }
        }
        message = "Please upload an excel file!";
        return "index";
    }

/*
    @GetMapping("/show")
    public ResponseEntity<List<Record>> getAllRecords() {
        try {
            List<Record> records = workWithExcelService.getAllRecords();
            if (records.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(records, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
*/
}
