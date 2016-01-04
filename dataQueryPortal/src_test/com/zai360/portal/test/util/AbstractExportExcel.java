package com.zai360.portal.test.util;

/**============================================================
 * 版权：再生活科技 版权所有 (c) 2011 - 2020
 * 包： com.northwestwind.export.excel
 * 修改记录：
 * 日期                作者           内容
 * =============================================================
 * 2014年9月19日       pss        
 * ============================================================*/



import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * <p>导出数据</p>
 *
 * <p>Copyright: 版权所有 (c) 2011 - 2020<br>
 * Company: 再生活科技</p>
 *
 * @author  pss
 * @version 3.0!
 * @date    2014年9月19日
 */
public abstract class AbstractExportExcel {
	
	/** The content type for an Excel response */
	private static final String CONTENT_TYPE = "application/vnd.ms-excel";

	/**
	 * Convenient method to set a String as text content in a cell.
	 * @param cell the cell in which the text must be put
	 * @param text the text to put in the cell
	 */
	protected void setStringCell(HSSFCell cell, String text) {
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(text);
	}
	
	/**
	 * Convenient method to set a numeric as numeric content in a cell.
	 * @param cell the cell in which the text must be put
	 * @param text the text to put in the cell
	 */
	protected void setNumericCell(HSSFCell cell, Number numeric) {
		if(numeric == null){
			setBlankCell(cell);
		} else {
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			if (numeric instanceof Integer) {
				cell.setCellValue(numeric.intValue());
			} else if (numeric instanceof Long) {
				cell.setCellValue(numeric.longValue());
			} else if (numeric instanceof Short) {
				cell.setCellValue(numeric.shortValue());
			} else if (numeric instanceof Double) {
				cell.setCellValue(numeric.doubleValue());
			} else {
				cell.setCellValue(numeric.toString());
			}
		}
	}
	
	/**
	 * Convenient method to set a boolean as boolean content in a cell.
	 * @param cell the cell in which the text must be put
	 * @param text the text to put in the cell
	 */
	protected void setBooleanCell(HSSFCell cell, boolean boolCell) {
		cell.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
		cell.setCellValue(boolCell);
	}
	
	/**
	 * Convenient method to set a blank.
	 * 
	 * @param cell void
	 */
	protected void setBlankCell(HSSFCell cell) {
		cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
//		cell.setCellValue("");
	}
	
	/**
	 * 生成导出excel文件
	 * 
	 * @param workbook  the Excel workbook to complete
	 * @param request
	 * @param response
	 * @throws Exception void
	 */
	protected abstract void buildExcelDocument(HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception;
	
	/**
	 * 执行导出excel操作
	 * 
	 * @param request
	 * @param response
	 * @param filename  导出文件名称
	 * @throws Exception void
	 */
	public final void doExportExcel(HttpServletRequest request,
			HttpServletResponse response, String filename) throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		buildExcelDocument(workbook, request, response);
		
		// Set the content type.
		response.setContentType(CONTENT_TYPE);
		response.setContentType("application/msexcel");//??
		if (StringUtils.isNotEmpty(filename)) {
			response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
		} else {
			response.setHeader("Content-disposition", "attachment");
		}

		// Flush byte array to servlet output stream.
		ServletOutputStream out = response.getOutputStream();
		workbook.write(out);
		out.flush();
		out.close();//
	}

	/***
	 * 构建文件标题
	 * 
	 * @param workbook   the Excel workbook to complete
	 * @param sheet		 the Excel sheet to complete
	 * @param rowNumber  sheet row
	 * @param titles     标题
	 * @param widths     列宽度
	 * @return void
	 */
	protected int buildDocumentTitle(HSSFWorkbook workbook, HSSFSheet sheet, int rowNumber, String[] titles, Integer[] widths){
		if (titles != null && titles.length > 0) {
			HSSFRow header = sheet.createRow(rowNumber);
			header.setHeight((short) 400);
			HSSFCellStyle cellStyle = workbook.createCellStyle();//可重用
			for (int i = 0; i < titles.length; i++) {
				HSSFCell cell = header.createCell(i);
				cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
				cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
				HSSFFont font = workbook.createFont();
				font.setFontHeightInPoints((short) 11);
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				cellStyle.setFont(font);
				cell.setCellStyle(cellStyle);
				if (titles.length > i && titles[i] != null) {
					cell.setCellValue(titles[i]);
				}
				
				//设置宽度
				if (widths != null && widths.length > i && widths[i] != null) {
					sheet.setColumnWidth(i, widths[i]*32);
				} else {
					sheet.autoSizeColumn(i);
				}
			}
			rowNumber++;
		}
		
		return rowNumber;
	}
}
