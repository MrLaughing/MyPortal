package com.zai360.portal.test.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
/**
 * 处理查询结果
 * @author report
 *
 */
public class ResultUtil {
	/**
	 * 处理数据库查询结果List
	 * @param list
	 * @return
	 */
	public static List<HashMap<String,Object>> convertList(List<HashMap<String,Object>> list){
		Iterator<HashMap<String, Object>> it = list.iterator();
		while (it.hasNext()) {
			HashMap<String, Object> map = it.next();
			/**************处理手机号部分显示****************/
			Object phone = map.get("手机号");
			if (phone != null) {
				String phoneno= phone.toString();
				if(phoneno.length()==11){
					String phoneheader = phoneno.substring(0, 3);
					String phonefooter = phoneno.substring(7, 11);
					String newphone = phoneheader + "****" + phonefooter;
					map.put("手机号", newphone);
				}
			}
			Object receiver = map.get("收货人");
			if (receiver != null) {
				String receiveman= receiver.toString();
				if(receiveman.length()==11){
					String phoneheader = receiveman.substring(0, 3);
					String phonefooter = receiveman.substring(7, 11);
					String newphone = phoneheader + "****" + phonefooter;
					map.put("收货人", newphone);
				}
			}
			Object stoper = map.get("暂停服务操作人");
			if (stoper != null) {
				String stopman= stoper.toString();
				if(stopman.length()==11){
					String phoneheader = stopman.substring(0, 3);
					String phonefooter = stopman.substring(7, 11);
					String newphone = phoneheader + "****" + phonefooter;
					map.put("暂停服务操作人", newphone);
				}
			}
			Object name = map.get("姓名");
			if (name != null) {
				String nameno= name.toString();
				if(nameno.length()==11){
					String phoneheader = nameno.substring(0, 3);
					String phonefooter = nameno.substring(7, 11);
					String newphone = phoneheader + "****" + phonefooter;
					map.put("姓名", newphone);
				}
			}
		}
		return list;
		
	}
}
