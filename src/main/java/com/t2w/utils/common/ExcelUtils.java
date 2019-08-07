package com.t2w.utils.common;

import com.t2w.utils.common.domain.ExcelVersion;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-08-05 14:00
 * @name com.t2w.utils.common.ExcelUtils.java
 * @see describing 操作 Excel 工具类 <br/>
 * 需要导入依赖【org.apache.poi.poi,org.apache.poi.poi-ooxml-schemas,org.apache.poi.poi-ooxml】
 */
public class ExcelUtils {

    /**
     * @param is      Excel 的字节输入流
     * @param version Excel 版本
     * @return org.apache.poi.ss.usermodel.Workbook Excel 工作簿对象
     * @date 2019-08-06 19:41
     * @see describing 获取 Excel 工作簿对象
     */
    public static Workbook getWorkbook(InputStream is, ExcelVersion version) {
        Workbook workbook = null;
        try {
            switch (version) {
                case EXCEL2003L:
                    workbook = new HSSFWorkbook(is);
                    break;
                case EXCEL2007U:
                    workbook = new XSSFWorkbook(is);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    /**
     * @param workbook Excel 工作薄对象
     * @return java.util.List<org.apache.poi.ss.usermodel.Sheet>   Excel 中所有的 Sheet 集合
     * @date 2019-08-06 19:43
     * @see describing  获取 Excel 中所有的 Sheet 集合
     */
    public static List<Sheet> getSheets(Workbook workbook) {
        if (workbook == null)
            return null;
        List<Sheet> sheets = new ArrayList<Sheet>();
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            sheets.add(workbook.getSheetAt(i));
        }
        return sheets;
    }

    /**
     * @param sheet Excel 中的单个 Sheet 对象
     * @return java.util.List<org.apache.poi.ss.usermodel.Row> 单个 Sheet 中所有行的集合
     * @date 2019-08-06 19:45
     * @see describing 获取 Excel 中单个 Sheet 中的所有行
     */
    public static List<Row> getRows(Sheet sheet) {
        if (sheet == null)
            return null;
        List<Row> rows = new ArrayList<Row>();
        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
            rows.add(sheet.getRow(i));
        }
        return rows;
    }

    /**
     * @param row Excel 中单行对象
     * @return java.util.List<org.apache.poi.ss.usermodel.Cell> 单行中所有单元格的集合
     * @date 2019-08-06 19:47
     * @see describing 获取 Excel 中单个 Row 中的所有单元格
     */
    public static List<Cell> getCells(Row row) {
        if (row == null)
            return null;
        List<Cell> cells = new ArrayList<Cell>();
        for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
            cells.add(row.getCell(i));
        }
        return cells;
    }

    /**
     * @param cell   单元格对象
     * @param object 需要写入的内容对象
     * @date 2019-08-06 19:49
     * @see describing 向 Excel 单元格中写入内容
     */
    public static void setCellValue(Cell cell, Object object) {
        if (object instanceof String) {
            String value = (String) object;
            cell.setCellValue(value);
        } else if (object instanceof Byte || object instanceof Short || object instanceof Integer
                || object instanceof Long || object instanceof Float || object instanceof Double) {
            Double value = Double.valueOf(object.toString());
            cell.setCellValue(value.doubleValue());
        } else if (object instanceof Boolean) {
            Boolean value = (Boolean) object;
            cell.setCellValue(value.booleanValue());
        } else if (object instanceof Date) {
            Date date = (Date) object;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            String value = sdf.format(date);
            cell.setCellValue(value);
        } else {
            if (object == null)
                cell.setCellValue("null");
            else {
                String value = object.toString();
                cell.setCellValue(value);
            }
        }
    }

    /**
     * @param workbook Excel 工作簿对象
     * @return org.apache.poi.ss.usermodel.CellStyle 单元格样式
     * @date 2019-08-06 20:16
     * @see describing 生成一个 Excel 默认的单元格样式
     */
    public static CellStyle getCellStyle(Workbook workbook) {
        short fontSize = 11; // 单元格中字体的大小
        return getCellStyle(workbook, "微软雅黑", fontSize, false);
    }

    /**
     * @param workbook   Excel 工作簿对象
     * @param fontName   字体的名称，如： 宋体 、 黑体 、 Arial 等
     * @param fontSize   字体的大小
     * @param isCentered 是否居中
     * @return org.apache.poi.ss.usermodel.CellStyle 单元格样式
     * @date 2019-08-06 20:17
     * @see describing 根据指定属性生成一个 Excel 单元格样式
     */
    public static CellStyle getCellStyle(Workbook workbook, String fontName
            , int fontSize, boolean isCentered) {
        //设计单元格风格
        CellStyle style = workbook.createCellStyle();
        //设置字体
        Font font = workbook.createFont();
        font.setFontName(fontName);                                 // 设置字体
        font.setFontHeightInPoints((short) fontSize);             // 单元格中字体的大小
        style.setFont(font);
        if (isCentered) {
            style.setAlignment(CellStyle.ALIGN_CENTER);             // 水平居中
            style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);  // 垂直居中
        }
        //设置边框样式
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        //设置边框颜色
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        return style;
    }

    /**
     * @param row   需要设置内容的行对象
     * @param cells 需要设置内容的数据集合
     * @date 2019-08-06 20:20
     * @see describing 向 Excel 中的指定行填入内容
     */
    public static void setRow(Row row, List<Object> cells) {
        for (int index = 0; index < cells.size(); index++) {
            Cell cell = row.createCell(index);
            CellStyle cellStyle = getCellStyle(row.getSheet().getWorkbook());
            //cellStyle.setWrapText(true);        //设置自动换行
            cell.setCellStyle(cellStyle);
            setCellValue(cell, cells.get(index));
            row.getSheet().autoSizeColumn(index);
        }
    }

    /**
     * @param sheet 需要设置内容的 Sheet 对象
     * @param rows  需要设置内容的数据集合
     * @date 2019-08-06 20:22
     * @see describing 向 Excel 中的指定 sheet 填入内容，默认重 0 行开始设置内容
     */
    public static void setSheet(Sheet sheet, List<List<Object>> rows) {
        setSheet(sheet, rows, 0);
    }

    /**
     * @param sheet    需要设置内容的 Sheet 对象
     * @param rows     需要设置内容的数据集合
     * @param startRow 设置内容的起始行数
     * @date 2019-08-06 20:23
     * @see describing 向 Excel 中的指定 sheet 中的 startRow 行填入内容
     */
    public static void setSheet(Sheet sheet, List<List<Object>> rows, int startRow) {
        for (int index = 0; index < rows.size(); index++, startRow++) {
            Row row = sheet.createRow(startRow);
            setRow(row, rows.get(index));
        }
    }

    /**
     * @param workbook 需要设置内容的工作簿对象
     * @param data     需要设置内容的数据集合
     * @date 2019-08-06 20:27
     * @see describing 向 Excel 中填入内容
     */
    public static void setWorkbookData(Workbook workbook, List<List<List<Object>>> data) {
        setWorkbookData(workbook, data, 0);
    }

    /**
     * @param workbook 需要设置内容的工作簿对象
     * @param data     需要设置内容的数据集合
     * @param startRow 设置内容的起始行数
     * @date 2019-08-06 20:29
     * @see describing 向 Excel 中填入内容，每个 Sheet 都从 startRow 行填入内容
     */
    public static void setWorkbookData(Workbook workbook, List<List<List<Object>>> data, int startRow) {
        for (int index = 0; index < data.size(); index++) {
            Sheet sheet = workbook.createSheet();
            setSheet(sheet, data.get(index), startRow);
        }
    }

    /**
     * @param workbook   需要设置内容的工作薄对象
     * @param sheetNames 需要设置 Sheet 名字的集合
     * @date 2019-08-06 20:32
     * @see describing 为 Excel 中的所有 sheet 页设置名字
     */
    public static void setSheetNames(Workbook workbook, List<String> sheetNames) {
        for (int index = 0; index < sheetNames.size(); index++)
            workbook.setSheetName(index, sheetNames.get(index));
    }

    /**
     * @param sheet 需要设置标题的 Sheet 对象
     * @param heads 标题内容的集合
     * @date 2019-08-06 20:35
     * @see describing 设置单个 Sheet 页的标题行
     */
    public static void setSheetHeads(Sheet sheet, List<String> heads) {
        CellStyle cellStyle = getCellStyle(sheet.getWorkbook(), "黑体", (short) 12, true);
        setSheetHeads(sheet, heads, cellStyle);
    }

    /**
     * @param sheet     需要设置标题的 Sheet 对象
     * @param heads     标题内容的集合
     * @param cellStyle 标题的单元格样式
     * @date 2019-08-06 20:38
     * @see describing 为 Excel 设置单个 Sheet 页的标题行
     */
    public static void setSheetHeads(Sheet sheet, List<String> heads, CellStyle cellStyle) {
        Row row = sheet.createRow(0);
        for (int index = 0; index < heads.size(); index++) {
            Cell cell = row.createCell(index);
            cell.setCellStyle(cellStyle);
            setCellValue(cell, heads.get(index));
            sheet.autoSizeColumn(index);
        }
    }

    /**
     * @param workbook 需要设置标题的工作簿对象
     * @param heads    标题内容的集合
     * @date 2019-08-06 20:39
     * @see describing 为 Excel 设置多个 Sheet 页的标题行
     */
    public static void setWorkbookHeads(Workbook workbook, List<List<String>> heads) {
        for (int index = 0; index < heads.size(); index++) {
            Sheet sheet = workbook.getSheetAt(index);
            setSheetHeads(sheet, heads.get(index));
        }
    }


}
