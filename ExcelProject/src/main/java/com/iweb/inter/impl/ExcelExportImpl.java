package com.iweb.inter.impl;

import com.iweb.anno.Excel;
import com.iweb.inter.ExcelExport;
import com.iweb.pojo.Bill;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jxy
 * @date
 */
public class ExcelExportImpl implements ExcelExport {
    @Override
    public File exportExcel(List<Bill> exportBills, Class<?> type, String writePath, int pageSize) throws IOException {
        File file = new File(writePath);

        try(FileOutputStream fos = new FileOutputStream(file)) {
            Workbook workbook = createWorkBook(writePath);
                Sheet sheet = workbook.createSheet();
                Row row1 = sheet.createRow(0);
                setFirstRowCells(row1);
                int rowCount = 1;
                int sheetCount = 1;

                for (Bill bill : exportBills) {
                    Row row = sheet.createRow(rowCount);
                    Field[] fields = type.getDeclaredFields();
                    int columnCount = 0;
                    for (Field field : fields) {
                        Excel e = field.getAnnotation(Excel.class);
                        if (e != null) {
                            field.setAccessible(true);
                            Object value = field.get(bill);
                            Cell cell = row.createCell(columnCount);
                            setCurrentValue(cell, value);
                            columnCount++;
                        }
                    }
                    rowCount++;
                    if (rowCount >= pageSize) {
                        rowCount = 1;
                        sheet = workbook.createSheet();
                        row1 = sheet.createRow(0);
                        sheetCount++;
                    }
                }
            workbook.write(fos);
            workbook.close();
        }  catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }
    /**
     * 通过配置文件获取文件路径，根据文件路径后缀名不同创建工作簿
     * @param filePath 提供文件路径，用于创建适应.xls或.xlsx后缀的工作簿
     * @return 根据后缀名返回工作簿类型信息，如果后缀名不符合则
     */
    private static Workbook createWorkBook(String filePath) throws Exception {
        if(filePath.endsWith(".xls")){
            return new HSSFWorkbook();
        }else if(filePath.endsWith(".xlsx")){
            return new XSSFWorkbook();
        }else {
            throw new IllegalArgumentException("文件后缀名不符合，请查看您的配置文件");
        }
    }

    /**
     * 使用instanceof判断value的类型，为每一个单元格写入对应的value值
     * @param cell 对应每一个需要写入数据的单元格，将其作为参数传入
     * @param value 从每一个Bill对象中获取的字段的值，将其作为参数传入
     */
    private static void setCurrentValue(Cell cell, Object value) {
        if(value==null){
            cell.setCellValue("");
        }else if(value instanceof Long){
            cell.setCellValue((Long)value);
        }else if(value instanceof Integer){
            cell.setCellValue((Integer)value);
        }else if(value instanceof BigDecimal){
            cell.setCellValue(((BigDecimal)value).doubleValue());
        }else if(value instanceof Date){
            DataFormat date = cell.getSheet().getWorkbook().createDataFormat();
            CellStyle dateStyle = cell.getSheet().getWorkbook().createCellStyle();
            dateStyle.setDataFormat(date.getFormat("yyyy/MM/dd"));
            cell.setCellValue((Date)value);
            cell.setCellStyle(dateStyle);
        }else if(value instanceof String){
            cell.setCellValue((String)value);
        }
    }
    /**
     * 为Excel文件表中的第一行创建对应的单元格，通过getTags()方法获取每个单元格的
     * 值，并传入
     * @param row1 表中的第一行，将其作为参数传入
     * @throws Exception 抛出异常
     */
    private static void setFirstRowCells(Row row1) throws Exception {
        List<String> tags = getTags();
        Cell cell1 = row1.createCell(0);
        Cell cell2 = row1.createCell(1);
        Cell cell3 = row1.createCell(2);
        Cell cell4 = row1.createCell(3);
        Cell cell5 = row1.createCell(4);
        cell1.setCellValue(tags.get(0));
        cell2.setCellValue(tags.get(1));
        cell3.setCellValue(tags.get(2));
        cell4.setCellValue(tags.get(3));
        cell5.setCellValue(tags.get(4));
    }
    /**
     * 获取注解中的name属性内容，将其作为第一行的Cells的值写入每一个cell中
     * @return 返回一个String类型的集合
     * @throws Exception 抛出异常
     */
    private static List<String> getTags() throws Exception{
        List<String> tags = new ArrayList<>();
        Class c = Class.forName("com.iweb.pojo.Bill");
        Field[] fields = c.getDeclaredFields();
        for (Field f:fields) {
            Excel e = f.getAnnotation(Excel.class);
            tags.add(e.name());
        }
        tags.add("");
        return tags;
    }
}
