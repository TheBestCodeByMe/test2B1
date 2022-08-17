package com.example.test2b1.service.helpers;

import com.example.test2b1.dto.AllInformation;
import com.example.test2b1.entity.Bank;
import com.example.test2b1.entity.File;
import com.example.test2b1.entity.Record;
import com.example.test2b1.pojo.MyCell;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.IntStream;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    /*static String[] HEADERs = {"NameBank", "Currency", "nameFIle", "dateSince",
            "dateTo", "fillingTime", "balanceAccNumber", "codeBalAcc", " DescriptionCl",
            "activeAmountInc", "passiveAmountInc", "activeAmountOut", "passiveAmountOut",
            "debitAmount", "creditAmount"};*/

    static String SHEET = "All informations";

    // проверка на excel-формат
    public static boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    // выгрузка из excel в список записей с учетом названия банка, файла и так далее
    public static List<Record> excelToRecords(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();
            List<Record> records = new ArrayList<Record>();
            int rowNumber = 0;
            boolean flag = true;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                AllInformation allInformation = new AllInformation();
                File file = new File();
                com.example.test2b1.entity.Record record = new com.example.test2b1.entity.Record();
                int cellIdx = 0;
                Cell currentCell = cellsInRow.next();

                if (flag) {
                    file.getBankId().setName(currentCell.getStringCellValue());
                    currentRow = rows.next();
                    cellsInRow = currentRow.iterator();
                    currentCell = cellsInRow.next();

                    file.setName(currentCell.getStringCellValue());
                    currentRow = rows.next();
                    cellsInRow = currentRow.iterator();
                    currentCell = cellsInRow.next();

                    String period = currentCell.getStringCellValue();
                    String[] strings = period.split(" ");
                    DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                    file.setDateSince(formatter.parse(strings[4]));
                    file.setDateTo(formatter.parse(strings[6]));
                    currentRow = rows.next();
                    cellsInRow = currentRow.iterator();
                    currentCell = cellsInRow.next();

                    file.setFillingTime(currentCell.getLocalDateTimeCellValue().toLocalDate());
                    while (cellsInRow.hasNext()) {
                        if (!currentCell.getStringCellValue().equals("")) {
                            file.getCurrencyId().setName(currentCell.getStringCellValue());
                        }
                    }
                    flag = false;

                    rows.next();
                    rows.next();
                    rows.next();
                }

                while (cellsInRow.hasNext()) {
                    currentCell = cellsInRow.next();
                    String className = "";
                    switch (cellIdx) {
                        case 0:
                            if (currentCell.getCellType() == CellType.STRING) {
                                if (currentCell.getStringCellValue().equals("ПО КЛАССУ")) {
                                    record.getBalanceAccount().setBalanceAccNumb(currentCell.getStringCellValue());
                                } else {
                                    className = currentCell.getStringCellValue();
                                }
                            } else {
                                record.getBalanceAccount().getClassId().setCode((int) currentCell.getNumericCellValue());
                                record.getBalanceAccount().getClassId().setDescription(className);
                            }
                            break;
                        case 1:
                            record.getIncomingBalance().setActiveAmount(BigDecimal.valueOf(currentCell.getNumericCellValue()));
                            break;
                        case 2:
                            record.getIncomingBalance().setPassiveAmount(BigDecimal.valueOf(currentCell.getNumericCellValue()));
                            break;
                        case 3:
                            record.getTurnover().setDebitAmount(BigDecimal.valueOf(currentCell.getNumericCellValue()));
                            break;
                        case 4:
                            record.getTurnover().setCreditAmount(BigDecimal.valueOf(currentCell.getNumericCellValue()));
                            break;
                        case 5:
                            record.getOutgoingBalance().setActiveAmount(BigDecimal.valueOf(currentCell.getNumericCellValue()));
                            break;
                        case 6:
                            record.getOutgoingBalance().setPassiveAmount(BigDecimal.valueOf(currentCell.getNumericCellValue()));
                            break;
                        default:
                            record.setFileId(file);
                            break;
                    }
                    cellIdx++;
                }
                records.add(record);
            }
            workbook.close();
            return records;
        } catch (IOException | ParseException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    // чтение excel-файла с учётом стилей ячеек
    public Map<Integer, List<MyCell>> readExcel(String fileLocation) throws IOException {

        Map<Integer, List<MyCell>> data = new HashMap<>();
        FileInputStream fis = new FileInputStream(new java.io.File(fileLocation));

        if (fileLocation.endsWith(".xls")) {
            data = readHSSFWorkbook(fis);
        } else if (fileLocation.endsWith(".xlsx")) {
            data = readXSSFWorkbook(fis);
        }

        int maxNrCols = data.values().stream()
                .mapToInt(List::size)
                .max()
                .orElse(0);

        data.values().stream()
                .filter(ls -> ls.size() < maxNrCols)
                .forEach(ls -> {
                    IntStream.range(ls.size(), maxNrCols)
                            .forEach(i -> ls.add(new MyCell("")));
                });

        return data;
    }

    // чтение данных ячеек
    private String readCellContent(Cell cell) {
        String content;
        switch (cell.getCellTypeEnum()) {
            case STRING:
                content = cell.getStringCellValue();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    content = cell.getDateCellValue() + "";
                } else {
                    content = cell.getNumericCellValue() + "";
                }
                break;
            case BOOLEAN:
                content = cell.getBooleanCellValue() + "";
                break;
            case FORMULA:
                content = cell.getCellFormula() + "";
                break;
            default:
                content = "";
        }
        return content;
    }

    // чтение стилей ячеек
    private Map<Integer, List<MyCell>> readHSSFWorkbook(FileInputStream fis) throws IOException {
        Map<Integer, List<MyCell>> data = new HashMap<>();
        try (HSSFWorkbook workbook = new HSSFWorkbook(fis)) {

            HSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
                HSSFRow row = sheet.getRow(i);
                data.put(i, new ArrayList<>());
                if (row != null) {
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        HSSFCell cell = row.getCell(j);
                        if (cell != null) {
                            HSSFCellStyle cellStyle = cell.getCellStyle();

                            MyCell myCell = new MyCell();

                            HSSFColor bgColor = cellStyle.getFillForegroundColorColor();
                            if (bgColor != null) {
                                short[] rgbColor = bgColor.getTriplet();
                                myCell.setBgColor("rgb(" + rgbColor[0] + "," + rgbColor[1] + "," + rgbColor[2] + ")");
                            }
                            HSSFFont font = cell.getCellStyle()
                                    .getFont(workbook);
                            myCell.setTextSize(font.getFontHeightInPoints() + "");
                            if (font.getBold()) {
                                myCell.setTextWeight("bold");
                            }
                            HSSFColor textColor = font.getHSSFColor(workbook);
                            if (textColor != null) {
                                short[] rgbColor = textColor.getTriplet();
                                myCell.setTextColor("rgb(" + rgbColor[0] + "," + rgbColor[1] + "," + rgbColor[2] + ")");
                            }
                            myCell.setContent(readCellContent(cell));
                            data.get(i)
                                    .add(myCell);
                        } else {
                            data.get(i)
                                    .add(new MyCell(""));
                        }
                    }
                }
            }
        }
        return data;
    }

    private Map<Integer, List<MyCell>> readXSSFWorkbook(FileInputStream fis) throws IOException {
        XSSFWorkbook workbook = null;
        Map<Integer, List<MyCell>> data = new HashMap<>();
        try {

            workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
                XSSFRow row = sheet.getRow(i);
                data.put(i, new ArrayList<>());
                if (row != null) {
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        XSSFCell cell = row.getCell(j);
                        if (cell != null) {
                            XSSFCellStyle cellStyle = cell.getCellStyle();

                            MyCell myCell = new MyCell();
                            XSSFColor bgColor = cellStyle.getFillForegroundColorColor();
                            if (bgColor != null) {
                                byte[] rgbColor = bgColor.getRGB();
                                myCell.setBgColor("rgb(" + (rgbColor[0] < 0 ? (rgbColor[0] + 0xff) : rgbColor[0]) + "," + (rgbColor[1] < 0 ? (rgbColor[1] + 0xff) : rgbColor[1]) + "," + (rgbColor[2] < 0 ? (rgbColor[2] + 0xff) : rgbColor[2]) + ")");
                            }
                            XSSFFont font = cellStyle.getFont();
                            myCell.setTextSize(font.getFontHeightInPoints() + "");
                            if (font.getBold()) {
                                myCell.setTextWeight("bold");
                            }
                            XSSFColor textColor = font.getXSSFColor();
                            if (textColor != null) {
                                byte[] rgbColor = textColor.getRGB();
                                myCell.setTextColor("rgb(" + (rgbColor[0] < 0 ? (rgbColor[0] + 0xff) : rgbColor[0]) + "," + (rgbColor[1] < 0 ? (rgbColor[1] + 0xff) : rgbColor[1]) + "," + (rgbColor[2] < 0 ? (rgbColor[2] + 0xff) : rgbColor[2]) + ")");
                            }
                            myCell.setContent(readCellContent(cell));
                            data.get(i)
                                    .add(myCell);
                        } else {
                            data.get(i)
                                    .add(new MyCell(""));
                        }
                    }
                }
            }
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
        return data;
    }
}
