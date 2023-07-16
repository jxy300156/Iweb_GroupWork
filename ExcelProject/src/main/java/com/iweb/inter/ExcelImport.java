package com.iweb.inter;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author jxy
 * @date
 */
public interface ExcelImport {

   boolean importExcel(File excelFile, Class<?> type) throws FileNotFoundException;
}
