package com.zai360.portal.test.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/**
 * 单元格格式工具类
 * @author report
 *
 */
public class StyleUtil {
	/**
	 * 设置日期时间单元格格式yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static HSSFCellStyle setDateCellStyle(HSSFWorkbook workbook){
		HSSFCellStyle dateCellStyle = workbook.createCellStyle();//dateCellStyle
		HSSFDataFormat dateFormat= workbook.createDataFormat();
		dateCellStyle.setDataFormat(dateFormat.getFormat("yyyy-MM-dd HH:mm:ss"));//设置Date日期Format
		return dateCellStyle;
	}
	/**
	 * 设置日期单元格格式(不含时间)
	 * @return
	 */
	public static HSSFCellStyle setDateNoCellStyle(HSSFWorkbook workbook){
		HSSFCellStyle dateCellStyle = workbook.createCellStyle();//dateCellStyle
		HSSFDataFormat dateFormat= workbook.createDataFormat();
		dateCellStyle.setDataFormat(dateFormat.getFormat("yyyy-MM-dd"));//设置Date日期Format
		return dateCellStyle;
	}
}
