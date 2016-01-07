package com.zai360.portal.test.util;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
/**
 * （可处理合并单元格）
 * 应不处理单元格！
 * @author report
 * @date 2015年12月30日
 */
public class ReadExcel {
	/**
	 * 读取excel文件
	 * @param wb
	 * @param sheetIndex
	 *            sheet页下标：从0开始
	 * @param startReadLine
	 *            开始读取的行:从0开始
	 * @param tailLine
	 *            去除最后读取的行
	 */
	public Map<String, Object> readExcelData(Workbook wb, int sheetIndex,
			int startReadLine, int tailLine) {
		Sheet sheet = wb.getSheetAt(sheetIndex);
		int rowNum = sheet.getLastRowNum() - tailLine + 1;// 得到总行数
		int colNum = sheet.getRow(0).getPhysicalNumberOfCells();// 依表头得到总列数
		String[] colType = new String[colNum];// 存放表头类型
		String[][] allVal = new String[rowNum][colNum];// 存放所有单元格值
		Map<String, Object> result = new HashMap<String, Object>();// 将结果存入map集合中以调用
		result.put("colType", colType);
		result.put("allVal", allVal);
		Row row = null;
		for (int i = startReadLine; i < sheet.getLastRowNum() - tailLine + 1; i++) {
			row = sheet.getRow(i);
			 for(Cell c : row) {//只获取不为空的单元格！
				boolean isMerge = isMergedRegion(sheet, i, c.getColumnIndex());
				// 判断是否具有合并单元格
				if (isMerge) {
					String rs = getMergedRegionValue(sheet, row.getRowNum(),
							c.getColumnIndex(), colType);
					allVal[i][c.getColumnIndex()] = rs;// 存放单元格值
				} else {
					allVal[i][c.getColumnIndex()] = getCellValue(c, colType);// 存放单元格值
				}
			}
		}
		/*更改colType类型*/
		for (int i = 0; i < colType.length; i++) {
			/* 更改数值类型 */
			if (colType[i] == "numeric") {
				String beginstrmax = "";
				String endstrmax = "";
				for (int j = 1; j < allVal.length; j++) {
					if (allVal[j][i] != null) {
						if (allVal[j][i].contains(".")) {// 若小数decimal or double
							int loc = allVal[j][i].indexOf(".");
							if (beginstrmax.length() < allVal[j][i].substring(
									0, loc).length()) {
								beginstrmax = allVal[j][i].substring(0, loc);
							}
							if (endstrmax.length() < allVal[j][i].substring(
									loc + 1).length()) {
								endstrmax = allVal[j][i].substring(loc + 1);
							}
						} else if (beginstrmax.length() < allVal[j][i].length()) {// 若整数int or bigint
							beginstrmax = allVal[j][i];
						}
					}else{
						allVal[j][i] = "";
					}
				}
				if (endstrmax.length() > 2) {
					colType[i] = "decimal(" + (beginstrmax.length()+endstrmax.length()) + ","
							+ endstrmax.length() + ")";//decimal(6,2);总长度6位，小数点前6位，小数点后2位
				} else if (endstrmax.length() <= 2 && endstrmax.length() > 0) {
					colType[i] = "double(" + (beginstrmax.length()+endstrmax.length()) + ","
							+ endstrmax.length() + ")";//double(6,2):总长度6位，小数点前4位，小数点后2位
				} else if (beginstrmax.length() >= 11) {
					colType[i] = "bigint("+beginstrmax.length()+")";
				} else if (beginstrmax.length() < 11) {
					colType[i] = "int("+beginstrmax.length()+")";
				}
			}
			/* 更改varchar(20)类型 */
			if (colType[i] == "varchar(20)") {
				int strlen = 0;
				for (int j = 1; j < allVal.length; j++) {
					if (allVal[j][i] != null) {
						if (strlen < allVal[j][i].length()) {
							strlen = allVal[j][i].length();
						}
					}else{
						allVal[j][i] = "";
					}
				}
				if(strlen== 0){
					strlen=20;
				}
				colType[i] = "varchar(" + strlen + ")";
			}
			/* 更改datetime类型,不需要修改 */
			if (colType[i] == "datetime") {
				for (int j = 1; j < allVal.length; j++) {
					if (allVal[j][i] == null) {
						allVal[j][i] = "0000-00-00 00:00:00";
					}
				}
			}
			/* 更改bit(1)类型,不需要修改 */
		}
		return result;
	}

	/**
	 * 获取合并单元格的值
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public String getMergedRegionValue(Sheet sheet, int row, int column,
			String[] colType) {
		int sheetMergeCount = sheet.getNumMergedRegions();

		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();

			if (row >= firstRow && row <= lastRow) {

				if (column >= firstColumn && column <= lastColumn) {
					Row fRow = sheet.getRow(firstRow);
					Cell fCell = fRow.getCell(firstColumn);
					return getCellValue(fCell, colType);
				}
			}
		}

		return null;
	}

	/**
	 * 判断合并了行
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public boolean isMergedRow(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row == firstRow && row == lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断指定的单元格是否是合并单元格
	 * 
	 * @param sheet
	 * @param row
	 *            行下标
	 * @param column
	 *            列下标
	 * @return
	 */
	private boolean isMergedRegion(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();// 合并的单元格个数
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断sheet页中是否含有合并单元格
	 * 
	 * @param sheet
	 * @return
	 */
	public boolean hasMerged(Sheet sheet) {
		return sheet.getNumMergedRegions() > 0 ? true : false;
	}

	/**
	 * 合并单元格
	 * 
	 * @param sheet
	 * @param firstRow
	 *            开始行
	 * @param lastRow
	 *            结束行
	 * @param firstCol
	 *            开始列
	 * @param lastCol
	 *            结束列
	 */
	public void mergeRegion(Sheet sheet, int firstRow, int lastRow,
			int firstCol, int lastCol) {
		sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol,
				lastCol));
	}

	/**
	 * 获取单元格的值
	 * 
	 * @param cell
	 * @param colType
	 *            存表头类型
	 * @return
	 */
	public String getCellValue(Cell cell, String[] colType) {
		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			colType[cell.getColumnIndex()] = "varchar(20)";
			return cell.getStringCellValue();

		} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			colType[cell.getColumnIndex()] = "bit(1)";
			return String.valueOf(cell.getBooleanCellValue());

		} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
			colType[cell.getColumnIndex()] = "formula";
			return cell.getCellFormula();

		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			if (HSSFDateUtil.isCellDateFormatted(cell)) {// 判断单元格是否属于日期格式
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				colType[cell.getColumnIndex()] = "datetime";
				return sdf.format(cell.getDateCellValue());
			} else {
				colType[cell.getColumnIndex()] = "numeric";// ?怎么判断金额double，手机号？
			}
			NumberFormat nf = NumberFormat.getInstance();// 取消科学计数法
			//整数部分最少为1位 
			nf.setMinimumIntegerDigits(1);
			//小数点后最多为10位 
			nf.setMaximumFractionDigits(10);
			nf.setGroupingUsed(false);// true时的格式：1,234,567,890
			return nf.format(cell.getNumericCellValue());

		}
		return "";
	}
}
