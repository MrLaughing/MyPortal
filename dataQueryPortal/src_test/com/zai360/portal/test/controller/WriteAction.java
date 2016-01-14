package com.zai360.portal.test.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.zai360.portal.test.service.WriteService;
import com.zai360.portal.test.util.ReadExcel;
/**
 * excel写入DB的Action
 * @author Laughing_Lz
 * @date 2016年1月4日
 */
@Controller
public class WriteAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private File excel;
	private String excelContentType;
	private String excelFileName;
	@Autowired
	private WriteService writeService;
	public String write() throws IOException, FileUploadException,
			InvalidFormatException {
		InputStream is = new FileInputStream(excel);
		Workbook wb = null;
		wb = WorkbookFactory.create(is);
		ReadExcel excel = new ReadExcel();
		Map<String, Object> result = excel.readExcelData(wb, 0, 0, 0);// 读取excel数据，包括类型，及各单元格值
		String[] colType = (String[]) result.get("colType");
		String[][] allVal = (String[][]) result.get("allVal");
		/* 1、判断表是否已存在 */
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA='polan' AND TABLE_NAME='"
				+ excelFileName.substring(0, excelFileName.indexOf("."))
				+ "' ;");
		String havetable = this.writeService.havetable(sql);
		if (havetable == null) {
			/* 2、创建表 */
			this.writeService.createTable(createSql(colType, allVal,
					excelFileName));
			/* 3、遍历插入行 */
			for (int i = 1; i < allVal.length; i++) {
				this.writeService.insertRow(insertsql(colType, allVal,
						excelFileName, i));
			}
//			ResultUtil result2=new ResultUtil(true, "", allVal);
//			WriteJson.writeResult(result2);
			return "success";
		} else {
			HttpServletRequest request  = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			request.setAttribute("msg", "该表已存在，请勿再次创建！");
			try {
				request.getRequestDispatcher("/write/writeExcel.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//跳转至原页面并输出警告信息
		}
		
		return null;
	}

	/**
	 * 创建表sql
	 * 
	 * @param colType
	 * @param allVal
	 * @return
	 */
	public static StringBuffer createSql(String[] colType, String[][] allVal,
			String excelFileName) {
		StringBuffer sql = new StringBuffer();
		sql.append("CREATE TABLE "
				+ excelFileName.substring(0, excelFileName.indexOf(".")) + "(");
		for (int i = 0; i < colType.length; i++) {
			if (i == colType.length - 1) {// 最后一行
				sql.append(allVal[0][i] + " " + colType[i] + ")");
			} else {
				sql.append(allVal[0][i] + " " + colType[i] + ",");
			}
		}
		return sql;
	}

	/**
	 * 插入行sql
	 * 
	 * @param colType
	 * @param allVal
	 * @param excelFileName
	 * @param row
	 *            当前行
	 * @return
	 */
	public static StringBuffer insertsql(String[] colType, String[][] allVal,
			String excelFileName, int row) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO "
				+ excelFileName.substring(0, excelFileName.indexOf("."))
				+ " VALUES(");
		for (int i = 0; i < colType.length; i++) {
			if (i == colType.length - 1) {
				sql.append("'" + allVal[row][i] + "')");
			} else {
				sql.append("'" + allVal[row][i] + "',");
			}
		}
		return sql;
	}

	/************************/
	public String getExcelContentType() {
		return excelContentType;
	}
	public void setExcelContentType(String excelContentType) {
		this.excelContentType = excelContentType;
	}
	public String getExcelFileName() {
		return excelFileName;
	}
	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}
	public File getExcel() {
		return excel;
	}
	public void setExcel(File excel) {
		this.excel = excel;
	}
}
