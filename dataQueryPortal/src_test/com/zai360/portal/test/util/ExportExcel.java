package com.zai360.portal.test.util;

/**============================================================
 * 版权：再生活科技 版权所有 (c) 2011 - 2020
 * 包： com.northwestwind.export.excel
 * 修改记录：
 * 日期                作者           内容
 * =============================================================
 * 2014年9月19日       pss        
 * ============================================================*/



import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
//import com.northwestwind.model.common.constants.BaseConstants;
//import com.northwestwind.util.base.DateUtil;

//import com.northwestwind.util.base.FormatUtil;

/**
 * <p>Excel导出操作</p>
 *
 * <p>Copyright: 版权所有 (c) 2011 - 2020<br>
 * Company: 再生活科技</p>
 *
 * @author  pss
 * @version 3.0!
 * @date    2014年9月19日
 */
public class ExportExcel extends AbstractExportExcel{
	
	/** 默认日期格式配比 */
	protected static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/** 文件名称 */
	protected String filename;
	
	/** 表名称 */
	protected String sheetName;
	
	/** 属性 */
	protected String[] properties;

	/** 标题 */
	protected String[] titles;

	/** 列宽 */
	protected Integer[] widths;

	/** 类型转换 */
	protected Converter[] converters;

	/** 数据 */
	protected Collection<?> data;

	/** 附加内容 */
	protected String[] contents;
	
	/** 列属性值*/
	protected String[] columns;
	
	/**
	 * @param filename
	 *            文件名称
	 * @param sheetName
	 *            表名称
	 * @param properties
	 *            属性
	 * @param titles
	 *            标题
	 * @param widths
	 *            列宽
	 * @param converters
	 *            类型转换
	 * @param data
	 *            数据
	 * @param contents
	 *            附加内容
	 * @param columns
	 *            列
	 */
	public ExportExcel(String filename, String sheetName, String[] properties, String[] titles, Integer[] widths, Converter[] converters, Collection<?> data, String[] contents, String[] columns){
		this.filename = filename;
		this.sheetName = sheetName;
		this.properties = properties;
		this.titles = titles;
		this.widths = widths;
		this.converters = converters;
		this.data = data;
		this.contents = contents;
		this.columns = columns;
	}
	
	/**
	 * @param properties
	 *            属性
	 * @param titles
	 *            标题
	 * @param data
	 *            数据
	 * @param contents
	 *            附加内容
	 * @param columns
	 *            列
	 */
	public ExportExcel(String[] properties, String[] titles, Collection<?> data, String[] contents, String[] columns) {
		this.properties = properties;
		this.titles = titles;
		this.data = data;
		this.contents = contents;
		this.columns = columns;
	}

	/**
	 * @param properties
	 *            属性
	 * @param titles
	 *            标题
	 * @param data
	 *            数据
	 * @param columns
	 *            列
	 */
	public ExportExcel(String[] properties, String[] titles, Collection<?> data, String[] columns) {
		this.properties = properties;
		this.titles = titles;
		this.data = data;
		this.columns = columns;
	}

	/**
	 * @param properties
	 *            属性
	 * @param data
	 *            数据
	 * @param columns
	 *            列
	 */
	public ExportExcel(String[] properties, Collection<?> data, String[] columns) {
		this.properties = properties;
		this.data = data;
		this.columns = columns;
	}

	@Override
	protected void buildExcelDocument(HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HSSFSheet sheet;
		if (StringUtils.isNotBlank(sheetName)) {
			sheet = workbook.createSheet(sheetName);
		} else {
			sheet = workbook.createSheet();
		}
		int rowNumber = 0;
		rowNumber = buildDocumentTitle(workbook, sheet, rowNumber, titles, widths);
		if(data != null){
			HSSFCellStyle dateCellStyle = StyleUtil.setDateCellStyle(workbook);//设置单元格日期时间格式
			if(columns != null && columns.length > 0){
				buildDocumentDataNonProperties(sheet, rowNumber,dateCellStyle);
			} else {
				buildDocumentDataProperties(sheet, rowNumber,dateCellStyle);
			}
			rowNumber += data.size();
		}

		if (contents != null && contents.length > 0) {
			rowNumber++;
			for (String content : contents) {
				HSSFRow row = sheet.createRow(rowNumber);
				HSSFCell cell = row.createCell(0);
				HSSFCellStyle cellStyle = workbook.createCellStyle();
				HSSFFont font = workbook.createFont();
				font.setColor(HSSFColor.GREY_50_PERCENT.index);
				cellStyle.setFont(font);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(content);
				rowNumber++;
			}
		}
	}

	/**
	 * 构建数据--未包含属性值时 columns!=null
	 * 
	 * @param sheet
	 * @param rowNumber
	 * @param rowNumber void
	 */
	@SuppressWarnings("rawtypes")
	protected void buildDocumentDataNonProperties(HSSFSheet sheet, int rowNumber,HSSFCellStyle dateCellStyle) {
		for (Object item : data) {
			HashMap result = (HashMap) item;
			HSSFRow row = sheet.createRow(rowNumber);
			for (int i = 0; i < columns.length; i++) {
				HSSFCell cell = row.createCell(i);
				Object cellValue = result.get(columns[i]);
				if(cellValue != null){
					if(converters != null && converters[i] != null && converters[i] instanceof DoubleConverter){
//						cell.setCellValue(Double.parseDouble(FormatUtil.FormatMoney((double)cellValue)));
						//待修改
						cell.setCellValue(Double.parseDouble((String) cellValue));						
					} else if(cellValue instanceof BigDecimal){
						cell.setCellValue(((BigDecimal) cellValue).doubleValue());
					}
					else if (cellValue instanceof Number){
						setNumericCell(cell, (Number) cellValue);
					} else if (cellValue instanceof Boolean){
						setBooleanCell(cell, (Boolean) cellValue);
					}
					else if (cellValue instanceof Date) {
                        cell.setCellValue((Date)cellValue);
                        cell.setCellStyle(dateCellStyle);//存入日期格式，读取时发现从属于number?!
					}
					else {
						setStringCell(cell, (String) cellValue);
					}
				} else {
					setBlankCell(cell);
				}
			}
			rowNumber++;
		}
	}
	
	/**
	 * 构建数据--包含属性值时
	 * 
	 * @param sheet
	 * @param rowNumber void
	 * @throws Exception 
	 */
	protected void buildDocumentDataProperties(HSSFSheet sheet, int rowNumber,HSSFCellStyle dateCellStyle ) throws Exception {
		
		for (Object item : data) {
			HSSFRow row = sheet.createRow(rowNumber);
			for (int i = 0; i < properties.length; i++) {
				HSSFCell cell = row.createCell(i);
				if (converters != null && converters.length > i && converters[i] != null) {
					Class<?> clazz = PropertyUtils.getPropertyType(item, properties[i]);
					ConvertUtils.register(converters[i], clazz);
					String property = BeanUtils.getProperty(item, properties[i]);
					cell.setCellValue(property);
					ConvertUtils.deregister(clazz);
					if(clazz.equals(BigDecimal.class)){
						double propertyValue = 0.0;
						if(StringUtils.isNotBlank(property)){
							property = FormatMoney(new BigDecimal(property));
							propertyValue = Double.valueOf(property).doubleValue();
						}
						cell.setCellValue(propertyValue);
					}
				} else {
					Class<?> clazz = PropertyUtils.getPropertyType(item, properties[i]);
					String property = BeanUtils.getProperty(item, properties[i]);
					//判断日期是否为空
					if(clazz.equals(Date.class)&property!=null){
						SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
						Date propertyDate = sdf.parse(property);//String转为Date存入excel
						cell.setCellStyle(dateCellStyle);//含日期时间格式
						cell.setCellValue(propertyDate);
					}else if("false".equalsIgnoreCase(property)){
						cell.setCellValue("否");
					} else if ("true".equalsIgnoreCase(property)){
						cell.setCellValue("是");
					} else {
						cell.setCellValue(property);
					}
				}
				/**
				 * 设置列宽
				 */
				if (rowNumber == 0 || rowNumber == 1) {
					if (widths != null && widths.length > i && widths[i] != null) {
						sheet.setColumnWidth(i, widths[i] * 32);
					} else {
						sheet.setColumnWidth(i, 80*32);
					}
				}
			}
			rowNumber++;
		
		}
	}

	/**
	 * 获取文件名称
	 * 
	 * @return 文件名称
	 */
	public String getFileName() {
		return filename;
	}

	/**
	 * 设置文件名称
	 * 
	 * @param filename
	 *            文件名称
	 */
	public void setFileName(String filename) {
		this.filename = filename;
	}

	/**
	 * 获取表名称
	 * 
	 * @return 表名称
	 */
	public String getSheetName() {
		return sheetName;
	}

	/**
	 * 设置表名称
	 * 
	 * @param sheetName
	 *            表名称
	 */
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	/**
	 * 获取属性
	 * 
	 * @return 属性
	 */
	public String[] getProperties() {
		return properties;
	}

	/**
	 * 设置属性
	 * 
	 * @param properties
	 *            属性
	 */
	public void setProperties(String[] properties) {
		this.properties = properties;
	}

	/**
	 * 获取标题
	 * 
	 * @return 标题
	 */
	public String[] getTitles() {
		return titles;
	}

	/**
	 * 设置标题
	 * 
	 * @param titles
	 *            标题
	 */
	public void setTitles(String[] titles) {
		this.titles = titles;
	}

	/**
	 * 获取列宽
	 * 
	 * @return 列宽
	 */
	public Integer[] getWidths() {
		return widths;
	}

	/**
	 * 设置列宽
	 * 
	 * @param widths
	 *            列宽
	 */
	public void setWidths(Integer[] widths) {
		this.widths = widths;
	}

	/**
	 * 获取类型转换
	 * 
	 * @return 类型转换
	 */
	public Converter[] getConverters() {
		return converters;
	}

	/**
	 * 设置类型转换
	 * 
	 * @param converters
	 *            类型转换
	 */
	public void setConverters(Converter[] converters) {
		this.converters = converters;
	}

	/**
	 * 获取数据
	 * 
	 * @return 数据
	 */
	public Collection<?> getData() {
		return data;
	}

	/**
	 * 设置数据
	 * 
	 * @param data
	 *            数据
	 */
	public void setData(Collection<?> data) {
		this.data = data;
	}
	/**
	 * 获取附加内容
	 * 
	 * @return 附加内容
	 */
	public String[] getContents() {
		return contents;
	}

	/**
	 * 设置附加内容
	 * 
	 * @param contents
	 *            附加内容
	 */
	public void setContents(String[] contents) {
		this.contents = contents;
	}

	/**
	 * 格式化金额
	 * 
	 * @param aPrice
	 * @return String
	 */
	public static String FormatMoney(BigDecimal aPrice) {
		BigDecimal price = aPrice;
		price.setScale(2, BigDecimal.ROUND_HALF_UP);
		return String.format("%.2f", price.floatValue());
	}
	
}
