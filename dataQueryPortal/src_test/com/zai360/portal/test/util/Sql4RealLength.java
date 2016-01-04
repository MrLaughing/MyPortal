package com.zai360.portal.test.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

/**
 * 获取查询sql生成的真实列数
 * 
 * @author report
 *
 */
public class Sql4RealLength {
	public static boolean ifDynamic(QueryEnum query) {
		String id = query.getId();
		if (QueryEnum.SMKH.getId().equals(id)) {
			return true;
		} else if (QueryEnum.SMKH.getId().equals(id)) {
			return true;
		} else {
			return false;
		}
	}

	public static StringBuffer getRealLength(QueryEnum query) {
		String id = query.getId();
		if (QueryEnum.SMKH.getId().equals(id)) {
			return getSmkhRealSql();
		}
		return null;
	}

	private static StringBuffer getSmkhRealSql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();		
		sql.append("SELECT CASE WHEN MAX(a.recycletimes) IS NULL THEN '1' ELSE MAX(a.recycletimes)+1 END AS '最大回收次数+1' ");
		String orderReceiveDate_min = request
				.getParameter("orderReceiveDate_min");
		String orderReceiveDate_max = request
				.getParameter("orderReceiveDate_max");
		String city=request.getParameter("city");
		sql.append(" FROM (SELECT COUNT(1) AS recycletimes, t.`CUSTOMERID` AS customerid FROM TASK t, "
				+ " syorganization s WHERE t.`syorganization_id`=s.`ID` ");
		if("010".equals(city)){
			sql.append(" AND s.`tree_path` LIKE '%"+"048a99f2-aef4-47ae-89c0-7230d73ff429"+"%' ");
		}else if("021".equals(city)){
			sql.append(" AND s.`tree_path` LIKE '%"+"99d685f2-a131-4df9-9046-1d02a858e169"+"%' ");
		}
		sql.append(" AND t.`ORDERRECEIVEDATE` >= '"+orderReceiveDate_min+"' AND t.`ORDERRECEIVEDATE` <= '"
				+orderReceiveDate_max+" 23:59:59' AND t.`STATUS` = 4 ");
		String[] taskType=request.getParameterValues("taskType"); 
		StringBuffer taskType_str = new StringBuffer();
		if (taskType != null) {
			if (taskType.length > 0 & taskType[0] != "") {
				for (int i = 0; i < taskType.length; i++) {
					if (i == taskType.length - 1) {
						taskType_str.append(taskType[i]);
					} else {
						taskType_str.append(taskType[i] + ",");
					}
				}
				sql.append(" AND t.`TYPE` IN ("+taskType_str+") ");
			}
		}
		sql.append(" GROUP BY t.`CUSTOMERID`)  AS a ");
		return sql;
	}
}
