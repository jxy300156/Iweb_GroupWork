package com.iweb.inter.impl;

import com.iweb.anno.Excel;
import com.iweb.inter.ExcelImport;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jxy
 * @date
 */
public class ExcelImportImpl implements ExcelImport {

    @Override
    public boolean importExcel(File excelFile, Class<?> type) throws FileNotFoundException {
        if(!excelFile.exists()){
            throw new FileNotFoundException("找不到对应文件，请检查！");
        }
        try (InputStream inputStream = new FileInputStream(excelFile)) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            List<Object> dataList = new ArrayList<>();
            Field[] fields = type.getDeclaredFields();
            for (Row row : sheet) {
                if(row.getRowNum()==0){
                    continue;
                }
                Object data = type.newInstance();
                int cellCount = 0;
                for (Field field : fields) {
                    Excel annotation = field.getAnnotation(Excel.class);
                    if (annotation != null) {
                        field.setAccessible(true);
                        Cell cell = row.getCell(cellCount);
                        Object value = getCurrentValue(cell, field.getType());
                        field.set(data, value);
                        cellCount++;
                    }
                }
                dataList.add(data);
            }
            workbook.close();
            return true;
        } catch (IOException | ReflectiveOperationException e) {
            e.printStackTrace();
        }

        return false;
    }
    private Object getCurrentValue(Cell cell, Class<?> type) {
        if (cell == null) {
            return null;
        }

        CellType cellType = cell.getCellType();
        if (cellType == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cellType == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue();
            } else {
                if (type == Integer.class || type == int.class) {
                    return (int) cell.getNumericCellValue();
                } else if (type == Long.class || type == long.class) {
                    return (long) cell.getNumericCellValue();
                } else if (type == BigDecimal.class) {
                    return new BigDecimal(cell.getNumericCellValue());
                } else {
                    return cell.getNumericCellValue();
                }
            }
        }

        return null;
    }
}
