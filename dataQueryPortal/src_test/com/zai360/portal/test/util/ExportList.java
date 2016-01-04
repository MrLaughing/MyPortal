package com.zai360.portal.test.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;


/**
 * list<T>为object数据
 * @author report
 *
 * @param <T>
 */
public class ExportList<T> {
	 
	public void export(List<T> list,String[] columns,String filename,String[] titles, int length,String sheetName,Integer[] widths) throws Exception{  
		/**
		 * 获取list<T>中T的属性和值
		 */
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		List<T> data=new ArrayList<T>();
		String[] properties =new String[length];
		if(!list.isEmpty()){
//		Iterator<T> it = list.iterator();  
			Object[] obj;	
			obj=list.toArray();
			Field[] fields=obj[0].getClass().getDeclaredFields();
			for (short i = 0; i < fields.length; i++) {  		  
				Field field = fields[i];  
				String fieldName = field.getName();  
				properties[i]=fieldName;
			}  	
			for(int i=0;i<obj.length;i++){
				data.add((T) obj[i]);
			}
	//		while (it.hasNext()) {  
	//			T t =  (T) it.next();  
	//			data.add(t);
	//			Field[] fields = t.getClass().getDeclaredFields();  
	//			for (short i = 0; i < fields.length; i++) {  		  
	//				Field field = fields[i];  
	//				String fieldName = field.getName();  
	//				properties[i]=fieldName;
	//			}  	
	//		}
			ExportExcel exportExcel=new ExportExcel(properties, titles, data, columns);
			exportExcel.sheetName=sheetName;
			exportExcel.widths=widths;
			exportExcel.doExportExcel(request, response, filename);
		}else{
			data=null;
			ExportExcel exportExcel=new ExportExcel(properties, titles, data, columns);
			exportExcel.sheetName=sheetName;
			exportExcel.widths=widths;
			exportExcel.doExportExcel(request, response, filename);
		}
	}
}
