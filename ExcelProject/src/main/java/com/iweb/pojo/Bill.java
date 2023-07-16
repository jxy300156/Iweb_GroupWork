package com.iweb.pojo;

import com.iweb.anno.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author jxy
 * @date
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    @Excel (name="账单编号" )
    private Long id;

    @Excel (name="账单金额")
    private BigDecimal money ;

    @Excel (name="账单发生时间")
    private Date createTime;

    @Excel (name="账单创建人")
    private String createUser;

    @Excel(ignore=true)
    private int version;

}
