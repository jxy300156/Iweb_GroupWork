package com.iweb.util;

import com.iweb.inter.ExcelImport;
import com.iweb.inter.impl.ExcelExportImpl;
import com.iweb.inter.impl.ExcelImportImpl;
import com.iweb.pojo.Bill;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author jxy
 * @date
 */
public class ExcelUtil {
    private String getExcelFilePath() throws Exception {
        File configProperties = new File("D:\\idea_work_space\\ExcelProject\\src" +
                "\\main\\java\\com\\iweb\\config.properties");
        Properties config = new Properties();
        config.load(new FileInputStream(configProperties));
        return (String)config.get("excelPath");
    }
    private List<Bill> getBillList(){
        List<Bill> exportBills = new ArrayList<>();
        Long id = 1001L;
        BigDecimal money = new BigDecimal(15889000.458);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2012);
        calendar.set(Calendar.MONTH, Calendar.JULY);
        calendar.set(Calendar.DAY_OF_MONTH, 12);
        Date createTime = calendar.getTime();
        String createUser = "Bob";
        int version = 1;
        Bill bill = new Bill(id,money,createTime,createUser,version);
        exportBills.add(bill);
        exportBills.add(bill);
        exportBills.add(bill);
        return exportBills;
    }
       public void doExport(ExcelExportImpl excelExport) throws Exception {
        List<Bill> exportBills = getBillList();
        String filePath = getExcelFilePath();
        int pageSize = 10000;
        File file = excelExport.exportExcel(exportBills,Bill.class,filePath,pageSize);
        System.out.println("Excel导出成功，文件路径为:"+file.getAbsolutePath());
    }
    public void doImport(ExcelImportImpl excelImport) throws Exception {
        String filePath = getExcelFilePath();
        File file = new File(filePath);
        boolean isImported = excelImport.importExcel(file,Bill.class);
        if(isImported){
            System.out.println("Excel导入成功");
        }else {
            System.out.println("Excel导入失败");
        }
    }
}
