package br.com.multitela.quiz.servidor.util;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Created by arthurpereira on 03/04/17.
 */
public class ExcelUtil {

    public static void formatExcel(Object doc) {
        XSSFWorkbook workbook = (XSSFWorkbook) doc;
        XSSFSheet sheet = workbook.getSheetAt(0);

        XSSFRow header = sheet.getRow(0);
        int colCount = header.getPhysicalNumberOfCells();
        int rowCount = sheet.getPhysicalNumberOfRows();


        XSSFCellStyle intStyle = workbook.createCellStyle();
        intStyle.setDataFormat((short) 1);

        XSSFCellStyle decimalStyle = workbook.createCellStyle();
        decimalStyle.setDataFormat((short) 2);

        XSSFCellStyle currencyStyle = workbook.createCellStyle();
        currencyStyle.setDataFormat((short) 5);

        for(int rowInd = 1; rowInd < rowCount; rowInd++) {
            XSSFRow row = sheet.getRow(rowInd);
            for(int cellInd = 1; cellInd < colCount; cellInd++) {
                XSSFCell cell = row.getCell(cellInd);

                String stringValue = cell.getStringCellValue();

                if(stringValue.matches("(?s)\\d*$")) {
                    int intValue = Integer.valueOf(stringValue);

                    cell.setCellType(XSSFCell.CELL_TYPE_BLANK);
                    cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                    cell.setCellStyle(intStyle);
                    cell.setCellValue(intValue);
                } else {
                    cell.setCellValue(stringValue);
                }
            }
        }

    }

}
