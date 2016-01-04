package com.zai360.portal.test.util;
/**
 * 拼接动态query
 * @author report
 *
 */
public class DynamicQuery {
	public static QueryEnum getDynamicQuery(QueryEnum query) {
		String id = query.getId();
		if (QueryEnum.SMKH.getId().equals(id)) {
			return getSmkhQuery(query);
		}
		return null;
	}
	
	private static QueryEnum getSmkhQuery(QueryEnum query){
		String[] newcolumns=new String[query.getLength()];//重新定义长度
		String[] newtitles=new String[query.getLength()];
		Integer[] newwidth=new Integer[query.getLength()];
		for(int i=0;i<query.getLength();i++){
			if(i<1){
				newcolumns[i]=query.columns[i];
				newtitles[i]=query.titles[i];
				newwidth[i]=query.widths[i];
			}else{
				newcolumns[i]=i+"次上门客户数";
				newtitles[i]=i+"次上门客户数";
				newwidth[i]=120;
			}
		}
		query.setColumns(newcolumns);
		query.setTitles(newtitles);
		query.setWidths(newwidth);
		return query;
	}
}
