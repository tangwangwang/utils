package com.t2w.utils.common.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-08-05 14:05
 * @name com.t2w.utils.common.domain.ExcelVersion.java
 * @see describing
 */
@Getter
public enum ExcelVersion {
    /** Excel 2003 及其以下 */
    EXCEL2003L(".xls"),
    /** Excel 2007 及其以上 */
    EXCEL2007U(".xlsx");
    /** 版本后缀名 */
    private String name;

    ExcelVersion(String name) {
        this.name = name;
    }

}
