package com.iweb;

import com.iweb.inter.impl.ExcelExportImpl;
import com.iweb.inter.impl.ExcelImportImpl;
import com.iweb.pojo.Bill;
import com.iweb.util.ExcelUtil;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jxy
 * @date
 */
public class Test {

    public static void main(String[] args) throws Exception {
        ExcelUtil eu = new ExcelUtil();
        ExcelImportImpl excelImport = new ExcelImportImpl();
        ExcelExportImpl excelExport = new ExcelExportImpl();
        eu.doExport(excelExport);
        eu.doImport(excelImport);
    }
}
