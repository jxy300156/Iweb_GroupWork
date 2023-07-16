package com.iweb.inter;

import com.iweb.pojo.Bill;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author jxy
 * @date
 */
public interface ExcelExport {
    File exportExcel(List<Bill> exportBills, Class<?> type, String writePath, int pageSize) throws IOException;
}
