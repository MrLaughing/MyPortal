package com.zai360.portal.test.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
/**
 * 查询结果总条数
 * @author report
 *
 */
public class Sql4CountUtil {

	public static StringBuffer getSql(QueryEnum query) {
		String id=query.getId();
		if(QueryEnum.APPXX.getId().equals(id)){
			return getAppxxSql();
		}else if(QueryEnum.YHXX.getId().equals(id)){
			return getYhxxSql();
		}else if(QueryEnum.DTXX.getId().equals(id)){
			return getDtxxSql();
		}else if(QueryEnum.HSJE.getId().equals(id)){
			return getHsjeSql();
		}else if(QueryEnum.ZCYH.getId().equals(id)){
			return getZcyhSql();
		}else if(QueryEnum.NOID.getId().equals(id)){
			return getNoOpenidSql();
		}else if(QueryEnum.HSXX.getId().equals(id)){
			return getHsxxSql();
		}else if(QueryEnum.TGRS.getId().equals(id)){
			return getTgrsSql();
		}else if(QueryEnum.SCSM.getId().equals(id)){
			return getScsmSql();
		}else if(QueryEnum.DTZC.getId().equals(id)){
			return getDtzcSql();
		}else if(QueryEnum.SCYH.getId().equals(id)){
			return getScyhSql();
		}else if(QueryEnum.DDDC.getId().equals(id)){
			return getDddcSql();
		}else if(QueryEnum.YXYH.getId().equals(id)){
			return getYxyhSql();
		}else if(QueryEnum.TYYH.getId().equals(id)){
			return getTyyhSql();
		}else if(QueryEnum.CFDZ.getId().equals(id)){
			return getCfdzSql();
		}else if(QueryEnum.WYHQ.getId().equals(id)){
			return getWyhqSql();
		}else if(QueryEnum.WSSJ.getId().equals(id)){
			return getWssjSql();
		}else if(QueryEnum.CCRS.getId().equals(id)){
			return getCcrsSql();
		}else if(QueryEnum.SCXD.getId().equals(id)){
			return getScxdSql();
		}else if(QueryEnum.XDMX.getId().equals(id)){
			return getXdmxSql();
		}else if(QueryEnum.ZCFW.getId().equals(id)){
			return getZcfwSql();
		}else if(QueryEnum.DTFW.getId().equals(id)){
			return getDtfwSql();
		}else if(QueryEnum.ZHYE.getId().equals(id)){
			return getZhyeSql();
		}else if(QueryEnum.FWRQ.getId().equals(id)){
			return getFwrqSql();
		}else if(QueryEnum.SCSY.getId().equals(id)){
			return getScsySql();
		}else if(QueryEnum.ZXYY.getId().equals(id)){
			return getZxyySql();
		}else if(QueryEnum.SMWY.getId().equals(id)){
			return getSmwySql();
		}else if(QueryEnum.HSSJ.getId().equals(id)){
			return getHssjSql();
		}else if(QueryEnum.TSGL.getId().equals(id)){
			return getTsglSql();
		}else if(QueryEnum.SMKH.getId().equals(id)){
			return getSmkhSql();
		}else if(QueryEnum.DHGW.getId().equals(id)){
			return getDhgwSql();
		}else{
			return null;
		}
	}

	/**********************************/
	/**
	 * 电话购物信息
	 * @return
	 */
	private static StringBuffer getDhgwSql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		String datetime_min = request.getParameter("datetime_min");
		String datetime_max = request.getParameter("datetime_max");
		sql.append("SELECT COUNT(*) FROM (SELECT c.phoneno AS '手机号', s.name AS '所属基站', c.registertime AS '注册时间', "
				+ " c.recycletimes AS '回收次数', c.ordertimes AS '商城使用次数', COUNT(1) AS '通过电话购物成功下单次数', "
				+ " SUM(o.amount_paid) AS '通过电话购物下单总金额' FROM polan.customer c, polan.syorganization s, "
				+ " polan.xx_order o, polan.xx_order_way_log l WHERE o.order_status = 2 AND c.syorganization_id = s.id "
				+ " AND c.phoneno = o.phone AND o.sn = l.order_sn AND l.type = 2 AND o.create_date BETWEEN '"+datetime_min+"' "
				+ " AND '"+datetime_max+" 23:59:59' GROUP BY c.phoneno) a ");
		return sql;
	}

	/**
	 * 上门客户数查询SQL
	 * @return
	 */
	private static StringBuffer getSmkhSql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM (SELECT COUNT(1) AS '上门客户数' ");
		String datetime_min = request
				.getParameter("datetime_min");
		String datetime_max = request
				.getParameter("datetime_max");
		String city=request.getParameter("city");
		sql.append(" FROM (SELECT COUNT(1) AS recycletimes, t.`CUSTOMERID` AS customerid FROM TASK t, "
				+ " syorganization s WHERE t.`syorganization_id`=s.`ID` ");
		if("010".equals(city)){
			sql.append(" AND s.`tree_path` LIKE '%"+"048a99f2-aef4-47ae-89c0-7230d73ff429"+"%' ");
		}else if("021".equals(city)){
			sql.append(" AND s.`tree_path` LIKE '%"+"99d685f2-a131-4df9-9046-1d02a858e169"+"%' ");
		}
		sql.append(" AND t.`ORDERRECEIVEDATE` >= '"+datetime_min+"' AND t.`ORDERRECEIVEDATE` <= '"
				+datetime_max+" 23:59:59' AND t.`STATUS` = 4 ");
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
		sql.append(" GROUP BY t.`CUSTOMERID`)  AS a) a ");
		return sql;
	}
	/**
	 * 用户投诉管理查询SQL
	 * @return
	 */
	private static StringBuffer getTsglSql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		String datetime_min = request
				.getParameter("datetime_min");
		String datetime_max = request
				.getParameter("datetime_max");
		sql.append("SELECT COUNT(*) FROM complaint a, customer b, "
				+ " syorganization c WHERE a.`CUSTOMERID` = b.`id` AND b.`syorganization_id` = c.`ID` "
				+ " AND a.COMPLAINTIME >= '"+datetime_min+" 00:00:00' AND a.COMPLAINTIME <= '"
				+datetime_max+" 23:59:59'");
		return sql;
	}
	/**
	 * 回收二手智能机台数查询
	 * 回收手机任务信息
	 * @return
	 */
	private static StringBuffer getHssjSql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		String datetime_min = request
				.getParameter("datetime_min");// 回收手机时间段
		String datetime_max = request
				.getParameter("datetime_max");
		sql.append("SELECT COUNT(*) FROM (SELECT s.`NAME` AS '基站', m.`REAL_QUANTITY` AS '回收二手智能机数量', "
				+ " m.`PHONENO` AS '手机号', CASE WHEN m.`STATUS` = 0 THEN '未处理' "
				+ " WHEN m.`STATUS` = 1 THEN '发出' WHEN m.`STATUS` = 2 THEN '收到' WHEN m.`STATUS` = 3 "
				+ " THEN '退回' WHEN m.`STATUS` = 4 THEN '已完成' END AS '订单状态', "
				+ " t.`CREATEDATETIME` AS '创建日期', m.`CREATEDATETIME` AS '完成时间' FROM "
				+ " mobile_recycle_task m, TASK t, syorganization s WHERE m.`TASK` = t.`ID` "
				+ " AND m.`SYORGANIZATION_ID` = s.`ID` AND t.`CREATEDATETIME` BETWEEN '"
				+ datetime_min + "'" + " AND '" + datetime_max
				+ " 23:59:59' AND m.`CREATEDATETIME` BETWEEN '"
				+ datetime_min + "' AND '" + datetime_max
				+ " 23:59:59' " + "ORDER BY s.code,t.CREATEDATETIME) a ");
		return sql;
	}
	/**
	 * 首次上门违约客户信息查询SQL
	 * 
	 * @return
	 */
	private static StringBuffer getSmwySql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		String sycode = request.getParameter("sycode");// 基站CODE
		String datetime_min = request
				.getParameter("datetime_min");// 注销时间段
		String datetime_max = request
				.getParameter("datetime_max");
		sql.append("SELECT COUNT(*) FROM TASK a, customer c, syorganization s "
				+ " WHERE a.`CUSTOMERID` = c.`id` AND s.`ID` = a.`syorganization_id` "
				+ " AND a.`STATUS` = 5 AND a.`TYPE` = 0 AND a.`realReceveiTime` >= '"
				+ datetime_min
				+ "' AND a.`realReceveiTime` <= '"
				+ datetime_max
				+ " 23:59:59' AND c.`FIRSTRECYCLESUCCESSDATE` IS NULL ");
		if (sycode != "") {
			sql.append(" AND s.`CODE`=" + sycode);
		}
		sql.append(" ORDER BY s.`NAME` ");
		return sql;
	}

	/**
	 * 客户注销原因查询SQL
	 * 
	 * @return
	 */
	private static StringBuffer getZxyySql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		String sycode = request.getParameter("sycode");// 基站CODE
		String datetime_min = request.getParameter("datetime_min");// 注销时间段
		String datetime_max = request.getParameter("datetime_max");
		sql.append("SELECT COUNT(*) FROM (SELECT a.`phoneno` AS '手机号', a.`address` '客户地址', "
				+ " a.`updatedatetime` AS '注销时间', CASE WHEN a.`reason` IS NULL "
				+ " THEN a.comment ELSE a.reason END AS '注销原因', "
				+ " s.`NAME` AS '所属基站' FROM customer a, syorganization s WHERE a.`status` = 0 "
				+ " AND a.`syorganization_id` = s.`id` AND a.`updatedatetime` >= '"
				+ datetime_min + "' AND a.updatedatetime <= '"
				+ datetime_max + " 23:59:59' ");
		if (sycode != "") {
			sql.append(" AND s.`CODE`=" + sycode);
		}
		sql.append(") a");
		return sql;
	}

	/**
	 * 地推人员推荐用户商城首次使用数查询SQL
	 * 
	 * @return
	 */
	private static StringBuffer getScsySql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		String datetime_min = request
				.getParameter("datetime_min");// 首次下单成功时间
		String datetime_max = request
				.getParameter("datetime_max");
		sql.append("SELECT COUNT(*) FROM (SELECT s.`NAME` AS '地推人员', COUNT(1) AS '推荐用户商城首次使用数' "
				+ " FROM customer c, syuser s, recycleman r WHERE c.`refereecode` = s.`LOGINNAME` "
				+ " AND s.`ID` = r.`SYUSERID` AND s.`USERTYPE` = 2 AND r.`TIMETYPE` = 1"
				+ " AND c.`FIRSTORDERSUCCESSDATE` IS NOT NULL AND c.`FIRSTORDERSUCCESSDATE` >= '"
				+ datetime_min
				+ "' AND c.`FIRSTORDERSUCCESSDATE` <= '"
				+ datetime_max
				+ " 23:59:59' GROUP BY c.`refereecode` ORDER BY c.`refereecode`) a");
		return sql;
	}

	/**
	 * 地推人员推荐的每周服务日期查询SQL
	 * 
	 * @return
	 */
	private static StringBuffer getFwrqSql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		String refereecode = request.getParameter("refereecode");// 地推人员工号
		String datetime_min = request.getParameter("datetime_min");// 注册时间
		String datetime_max = request.getParameter("datetime_max");
		sql.append("SELECT COUNT(*) FROM (SELECT SUM(CASE WHEN c.`recycleday` = 1 THEN 1 ELSE 0 END) AS '周一', "
				+ " SUM(CASE WHEN c.`recycleday` = 2 THEN 1 ELSE 0 END) AS '周二', "
				+ " SUM(CASE WHEN c.`recycleday` = 3 THEN 1 ELSE 0 END) AS '周三', "
				+ " SUM(CASE WHEN c.`recycleday` = 4 THEN 1 ELSE 0 END) AS '周四', "
				+ " SUM(CASE WHEN c.`recycleday` = 5 THEN 1 ELSE 0 END) AS '周五', "
				+ " SUM(CASE WHEN c.`recycleday` = 6 THEN 1 ELSE 0 END) AS '周六', "
				+ " SUM(CASE WHEN c.`recycleday` = 7 THEN 1 ELSE 0 END) AS '周日', "
				+ " org.`NAME` AS '基站' FROM customer c, syorganization org, syuser s, "
				+ " recycleman r WHERE c.`refereecode` = s.`LOGINNAME` AND "
				+ " c.`syorganization_id` = org.`ID` AND s.`ID` = r.`SYUSERID` "
				+ " AND s.`USERTYPE` = 2 AND r.`TIMETYPE` = 1 ");
		if (refereecode != "") {
			sql.append(" AND s.`LOGINNAME` = " + refereecode);
		}
		sql.append(" AND c.`registertime` >= '" + datetime_min
				+ " ' AND c.`registertime` " + " <= '" + datetime_max
				+ " 23:59:59' GROUP BY c.`syorganization_id` "
				+ " ORDER BY org.`CODE`) a ");
		return sql;
	}

	/**
	 * 拼接所有用户账户总余额SQL
	 * 
	 * @return
	 */
	private static StringBuffer getZhyeSql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		String createDate = request.getParameter("createDate");
		sql.append("SELECT COUNT(*) FROM (SELECT (SUM(a.`credit`)-SUM(a.`debit`)) AS '账户总余额' FROM xx_deposit a "
				+ "WHERE a.`create_date` <= '" + createDate + " 23:59:59') a ");
		return sql;
	}

	/**
	 * 拼接商城营销用户数SQL
	 * 
	 * @return
	 */
	private static StringBuffer getYxyhSql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		String realReceveiTime = request.getParameter("realReceveiTime");
		sql.append("SELECT COUNT(*) FROM (SELECT (SELECT s.`NAME` FROM syorganization s WHERE s.`ID` = t.`syorganization_id`) AS '基站名称', "
				+ " (SELECT s.`LOGINNAME` FROM syuser s WHERE s.`ID` = t.`USERID`) AS '再生侠编号', "
				+ " (SELECT s.`NAME` FROM syuser s WHERE s.`ID` = t.`USERID`) AS '再生侠名称', "
				+ " COUNT(t.`ID`) AS '营销客户数' FROM task t, xx_member m, customer c  WHERE t.`CUSTOMERID` = c.`id` "
				+ " AND m.`mobile` = c.`phoneno` AND t.`STATUS` = 4 AND t.`TYPE` IN (0, 2) "
				+ " AND t.`realReceveiTime` BETWEEN '"
				+ realReceveiTime
				+ "' AND '"
				+ realReceveiTime
				+ " 23:59:59' "
				+ " AND EXISTS (SELECT 1 FROM xx_order ox WHERE ox.`order_status` IN (1, 2) AND ox.`member` = m.`id` "
				+ " AND ox.`create_date` BETWEEN '"
				+ realReceveiTime
				+ "' AND '"
				+ realReceveiTime
				+ " 23:59:59') "
				+ " GROUP BY t.`USERID`, DATE_FORMAT(t.`realReceveiTime`, '%Y-%m-%d') ORDER BY '基站名称') a ");
		return sql;
	}

	/**
	 * 拼接回收任务信息查询SQL
	 * 
	 * @return 返回：选定条件后的回收任务信息
	 */
	private static StringBuffer getHsxxSql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		String datetime_min = request
				.getParameter("datetime_min");
		String datetime_max = request
				.getParameter("datetime_max");
		sql.append("SELECT COUNT(*) FROM task t , syuser s  WHERE  t.`USERID`=s.`ID` AND t.status = 4  "
				+ " AND t.`ORDERRECEIVEDATE` BETWEEN '"
				+ datetime_min
				+ "' AND '" + datetime_max + " 23:59:59'");
		return sql;
	}

	/**
	 * 拼接首次下单账户及商品信息明细查询SQL
	 * 
	 * @return
	 */
	private static StringBuffer getXdmxSql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		String datetime_min = request.getParameter("datetime_min");// 首次下单日期
		String datetime_max = request.getParameter("datetime_max");
		sql.append("SELECT COUNT(*) FROM (SELECT t.`sn` AS '订单编号', t.`phone` AS '手机号', t.`create_date` AS '下单时间', (SELECT"
				+ " SUM(i1.`quantity` * i1.`price`) + t.freight + t.`fee` - "
				+ " t.`promotion_discount` - t.`coupon_discount` + t.`offset_amount` + "
				+ " t.`tax`  FROM xx_order_item i1 WHERE i1.`orders` = t.`id`) "
				+ " AS '订单总金额', t.`account_paid` AS '账户抵用金额', m.`balance` "
				+ " AS '当前余额', p.`sn` AS '商品编号', p.`name` AS '商品名称',"
				+ " i.`sale_price` AS '商品原价', i.`price` AS '商品价格', i.`quantity` AS '数量' "
				+ " FROM xx_order t, xx_member m, xx_product p, xx_order_item i "
				+ " WHERE t.`create_date` >= '"
				+ datetime_min
				+ "' AND t.`create_date` <= '"
				+ datetime_max
				+ " 23:59:59' AND t.`id` = i.`orders` "
				+ " AND i.`product` = p.`id` AND ( ( t.`order_status` = '2' "
				+ " AND t.`shipping_status` = '2') OR t.`order_status` = '1')"
				+ " AND t.`member` = m.`id` AND NOT EXISTS (SELECT 1 FROM xx_order o1"
				+ " WHERE o1.`member` = t.`member` AND ( ( t.`order_status` = '2'"
				+ " AND t.`shipping_status` = '2') OR t.`order_status` = '1') "
				+ " AND o1.`create_date` < t.`create_date`) ORDER BY t.`sn`, p.`sn`) a");
		return sql;
	}

	/**
	 * 拼接首次下单信息查询SQL
	 * 
	 * @return
	 */
	private static StringBuffer getScxdSql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		String createDate = request.getParameter("createDate");// 下单截止日期
		String datetime_min = request.getParameter("datetime_min");// 订单完成日期_min
		String datetime_max = request.getParameter("datetime_max");// 订单完成日期_max
		sql.append("SELECT COUNT(*) FROM (SELECT DISTINCT t.`sn` AS '订单编号', (SELECT ( (SELECT "
				+ " SUM(item.`price` * item.`quantity`) FROM xx_order_item item "
				+ " WHERE item.`orders` = o2.`id`) + o2.`fee` + o2.freight + o2.`tax` + "
				+ " o2.`offset_amount` - o2.`promotion_discount` - o2.`coupon_discount` ) "
				+ " AS price  FROM xx_order o2  WHERE o2.`sn` = t.`sn`) AS '订单总金额',"
				+ " t.`consignee` AS '收货人', t.`phone` AS '手机号', t.`address` AS '地址',"
				+ " s.`NAME` AS '所属基站', l.`create_date` AS '完成时间' FROM xx_order t,"
				+ " xx_order_log l, xx_member m, syorganization s WHERE t.`member` = m.`id` "
				+ " AND l.`orders` = t.`id` AND m.`syorganization_id`=s.`ID` AND l.`type` "
				+ " = 7 AND l.`create_date` BETWEEN '"
				+ datetime_min
				+ "' AND '"
				+ datetime_max
				+ "' AND t.`create_date` >= '"
				+ createDate
				+ " 23:59:59' AND (SELECT"
				+ " SUM(item.`price` * item.`quantity`) FROM xx_order_item item"
				+ " WHERE item.`orders` = t.`id`) > 0 AND NOT EXISTS (SELECT 1 FROM xx_order o1 "
				+ " WHERE t.`member` = o1.`member` AND o1.`create_date` < t.`create_date` AND "
				+ " (SELECT SUM(item.`price` * item.`quantity`) FROM xx_order_item item "
				+ " WHERE item.`orders` = o1.`id`) > 0 AND o1.`order_status` = 2) ORDER BY t.`sn`) a ");
		return sql;
	}

	/**
	 * 订单导出SQL
	 * 
	 * @return
	 */
	private static StringBuffer getDddcSql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		String orderSN = request.getParameter("orderSN");// 查询订单编号
		sql.append("SELECT COUNT(*) FROM  xx_order t, xx_product p, xx_order_item i WHERE t.`id` = i.`orders` "
				+ " AND i.`product` = p.`id` ");
		if (!"".equals(orderSN)) {
			sql.append(" AND t.`sn` = " + orderSN);
		}
		sql.append(" ORDER BY t.`sn`, p.`sn` ");
		return sql;
	}

	/**
	 * 拼接app下单信息查询SQL
	 * 
	 * @return 查询语句
	 */
	private static StringBuffer getAppxxSql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		sql.append("select COUNT(*) ");
		String datetime_min = request.getParameter("datetime_min");
		String datetime_max = request.getParameter("datetime_max");
		sql.append(" from xx_order t where t.create_date between '"
				+ datetime_min + "' and '" + datetime_max + " 23:59:59' ");
		String[] order_status = request.getParameterValues("order_status");
		String[] source_type = request.getParameterValues("source_type");
		StringBuffer status_str = new StringBuffer();
		if (order_status != null) {
			if (order_status.length > 0 & order_status[0] != "") {
				for (int i = 0; i < order_status.length; i++) {
					if (i == order_status.length - 1) {
						status_str.append(order_status[i]);
					} else {
						status_str.append(order_status[i] + ",");
					}
				}
				sql.append(" and t.order_status IN (" + status_str + ") ");
			}
		}
		StringBuffer type_str = new StringBuffer();
		if (source_type != null) {
			if (source_type.length > 0 & source_type[0] != "") {
				for (int i = 0; i < source_type.length; i++) {
					if (i == source_type.length - 1) {
						type_str.append(source_type[i]);
					} else {
						type_str.append(source_type[i] + ",");
					}
				}
				sql.append(" and t.source_type in (" + type_str + ") ");
			}
		}
		sql.append(" AND NOT EXISTS(SELECT 1 FROM xx_order_way_log l WHERE t.sn = l.order_sn AND l.type = 2)");
		return sql;
	}

	/**
	 * 拼接地推注册用户服务信息设置查询SQL
	 * 
	 * @return
	 */
	private static StringBuffer getDtfwSql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		String sycode = request.getParameter("sycode");
		String datetime_min = request.getParameter("datetime_min");
		String datetime_max = request.getParameter("datetime_max");
		sql.append("SELECT COUNT(*) FROM customer c, syorganization org, "
				+ " syuser s WHERE c.`syorganization_id` = org.`ID` AND c.`refereecode` = "
				+ " s.`LOGINNAME` AND c.`refereecode` IS NOT NULL ");
		if (sycode != "") {
			sql.append(" AND org.`CODE`= '" + sycode + "' ");
		}
		if (datetime_min != "" && datetime_max != "") {
			sql.append(" AND c.`registertime` >= '" + datetime_min
					+ " 00:00:00' AND c.`registertime` <= '" + datetime_max
					+ " 23:59:59' ");
		}
		sql.append(" ORDER BY c.registerTime DESC ");
		return sql;
	}

	/**
	 * 拼接新注册用户服务信息设置查询SQL
	 * 
	 * @return
	 */
	private static StringBuffer getZcfwSql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		String sycode = request.getParameter("sycode");
		String datetime_min = request.getParameter("datetime_min");
		String datetime_max = request.getParameter("datetime_max");
		sql.append("SELECT COUNT(*) FROM Customer c LEFT JOIN syorganization org "
				+ " ON c.syorganization_id = org.id  LEFT JOIN syuser s ON c.`refereecode` = s.`LOGINNAME` "
				+ " WHERE 1 = 1 ");
		if (sycode != "") {
			sql.append(" AND org.`CODE`= '" + sycode + "' ");
		}
		if (datetime_min != "" && datetime_max != "") {
			sql.append(" AND c.`registertime` >= '" + datetime_min
					+ " 00:00:00' AND c.`registertime` <= '" + datetime_max
					+ " 23:59:59' ");
		}
		sql.append(" ORDER BY c.registerTime DESC ");
		return sql;
	}

	/**
	 * 拼接各基站分时段出差人数查询SQL
	 * 
	 * @return
	 */
	private static StringBuffer getCcrsSql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		String datetime_min = request.getParameter("datetime_min");
		String datetime_max = request.getParameter("datetime_max");
		sql.append("SELECT COUNT(*) FROM (SELECT DATE_FORMAT( c.`CREATEDATETIME`, '%Y-%m-%d') AS '日期',"
				+ " s.name AS '基站名称', SUM( CASE WHEN c.`CREATEDATETIME` > "
				+ " DATE_FORMAT( c.`CREATEDATETIME`, '%Y-%m-%d 00:00:00') AND "
				+ " c.`CREATEDATETIME` < DATE_FORMAT( c.`CREATEDATETIME`, '%Y-%m-%d "
				+ " 17:00:00') THEN 1 ELSE 0 END ) AS '凌晨0点-下午5点', SUM( CASE"
				+ " WHEN c.`CREATEDATETIME` > DATE_FORMAT( c.`CREATEDATETIME`, "
				+ " '%Y-%m-%d 17:00:00') AND c.`CREATEDATETIME` <= DATE_FORMAT( "
				+ " DATE_ADD( c.`CREATEDATETIME`, INTERVAL '1 0:0:1' DAY_SECOND ), "
				+ " '%Y-%m-%d 00:00:00' ) THEN 1 ELSE 0 END ) AS '下午5点-凌晨0点' FROM "
				+ " customer_log c, syorganization s, customer t WHERE c.`CREATEDATETIME` "
				+ " >= '"
				+ datetime_min
				+ "' AND C.`CREATEDATETIME` <= '"
				+ datetime_max
				+ " 23:59:59' "
				+ " AND t.`phoneno` = c.`PHONENO` AND s.`ID` = "
				+ " t.`syorganization_id`  AND c.`OPERATION` IN ( '开启出差模式', "
				+ " '微信设置出差模式' ) GROUP BY DATE_FORMAT(c.`CREATEDATETIME`, '%Y-%m-%d'),"
				+ " s.`CODE`) a ");
		return sql;
	}

	/**
	 * 拼接未完善回收时间用户查询SQL
	 * 
	 * @return
	 */
	private static StringBuffer getWssjSql() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM customer c,syorganization s,xx_member m WHERE c.`builingcode` IS NOT NULL "
				+ " AND c.`communitycode` IS NOT NULL  AND c.`housenumber` IS NOT NULL "
				+ " AND c.`unit` IS NOT NULL AND (c.`recycleday` IS NULL OR c.`recycleperiod` IS NULL "
				+ " OR c.`recycletimequantum` IS NULL) AND c.`status` IN (1,-1) "
				+ " AND c.`syorganization_id` = s.`ID` AND m.`mobile` = c.`phoneno` ");
		return sql;
	}

	/**
	 * 拼接未发放优惠券用户查询SQL
	 * 
	 * @return
	 */
	private static StringBuffer getWyhqSql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		String deposit_credit = request.getParameter("deposit_credit");// 最低金额
		String deposit_type = request.getParameter("deposit_type");// 类型
		String datetime_begin = request.getParameter("datetime_begin");// 选定日期
		String coupon_code = request.getParameter("coupon_code");// 优惠券编码,以逗号,分隔
		sql.append("SELECT COUNT(*) FROM (SELECT DISTINCT  m.`mobile` AS '手机号' , cu.`name` AS '用户名'"
				+ " FROM xx_deposit d, xx_member m, customer cu WHERE d.`member` = m.`id` "
				+ " AND d.`credit` >= "
				+ deposit_credit
				+ " AND d.`type` = "
				+ deposit_type
				+ " "
				+ " AND d.`create_date` > '"
				+ datetime_begin
				+ "' "
				+ " AND cu.`phoneno` = m.`mobile` AND NOT EXISTS (SELECT 1 "
				+ " FROM xx_coupon_code c, xx_coupon cc WHERE c.`coupon` = cc.`id` "
				+ " AND cc.`code` IN (" + coupon_code
				+ ") AND d.`member` = c.`member`)) a");
		return sql;
	}

	/**
	 * 拼接重复地址用户信息查询SQL
	 * 
	 * @return
	 */
	private static StringBuffer getCfdzSql() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM customer c,  (SELECT b.`address` AS address,   COUNT(b.`address`) AS numbers "
				+ " FROM  customer b  WHERE b.`status` = 1 AND b.`communitycode` IS NOT NULL "
				+ " AND b.`builingcode` IS NOT NULL  AND b.`housenumber` IS NOT NULL "
				+ " AND b.`recycleday` IS NOT NULL  AND b.`recycleperiod` IS NOT NULL "
				+ " AND b.`recycletimequantum` IS NOT NULL   GROUP BY b.`address` "
				+ " HAVING COUNT(b.`address`) > 1) t,  community cc,building bb "
				+ " WHERE c.`address` = t.address  AND cc.`CODE` = c.`communitycode` AND bb.`CODE` = c.`builingcode` "
				+ " AND c.`status` = 1 ORDER BY c.`address`, c.`registertime` ");
		return sql;
	}

	/**
	 * 拼接停用用户信息SQL
	 * 
	 * @return
	 */
	private static StringBuffer getTyyhSql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		String datetime_begin = request.getParameter("datetime_begin");
		if(datetime_begin==null){
			throw new NullPointerException("datetime_begin参数缺失");
		}
		sql.append("SELECT COUNT(*) FROM  customer c LEFT JOIN syorganization s ON c.`syorganization_id` = s.`ID` "
				+ " WHERE c.`registertime` >= '"
				+ datetime_begin
				+ "' AND c.`status` = 0 ");
		return sql;
	}

	/**
	 * 拼接未购买过商品的商城用户信息SQL
	 * 
	 * @return
	 */
	private static StringBuffer getScyhSql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		String balance_min = request.getParameter("balance_min");
		sql.append("SELECT DISTINCT COUNT(*) FROM  xx_member m, customer c "
				+ " WHERE m.`mobile` = c.`phoneno` "
				+ " AND (c.`status` = 1 OR c.`status` = - 1) "
				+ " AND m.`balance` > " + balance_min
				+ " AND NOT EXISTS (SELECT 1 FROM xx_order t "
				+ " WHERE t.`member` = m.id)");
		return sql;
	}

	/**
	 * 拼接地推注册明细查询SQL
	 * 
	 * @return
	 */
	private static StringBuffer getDtzcSql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		String sycode = request.getParameter("sycode");
		String datetime_min = request
				.getParameter("datetime_min");
		String datetime_max = request
				.getParameter("datetime_max");
		sql.append("SELECT  COUNT(*) FROM syorganization sy, "
				+ " customer c LEFT JOIN syuser s ON c.`refereecode` = s.`LOGINNAME` WHERE ");
		if (sycode != "") {
			sql.append("sy.`CODE`='" + sycode + "' AND");
		}
		sql.append(" c.`syorganization_id` = sy.`id` "
				+ " AND c.`registertime` BETWEEN '" + datetime_min
				+ "' " + " AND '" + datetime_max
				+ " 23:59:59' ORDER BY s.`LOGINNAME` ");
		return sql;
	}

	/**
	 * 拼接再生侠各基站首次上门数
	 * 
	 * @return
	 */
	private static StringBuffer getScsmSql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		String datetime_min = request
				.getParameter("datetime_min");
		String datetime_max = request
				.getParameter("datetime_max");
		sql.append("SELECT COUNT(*) FROM (SELECT COUNT(DISTINCT(c.`address`)) AS '首次上门数',sy.`NAME` AS '基站' "
				+ " FROM syuser s,customer c, syorganization sy "
				+ " WHERE 1 = 1 AND c.refereecode = s.loginname  "
				+ " AND c.FIRSTRECYCLESUCCESSDATE BETWEEN '"
				+ datetime_min
				+ "' AND '"
				+ datetime_max + " 23:59:59' ");
		String refereecode = request.getParameter("refereecode");
		if (!refereecode.equals("")) {
			sql.append(" AND c.`refereecode` = '" + refereecode + "' ");
		}
		sql.append("AND sy.`ID`=c.`syorganization_id`"
				+ " GROUP  BY c.`syorganization_id`) a ");
		return sql;
	}

	/**
	 * 拼接再生侠各基站推广人数查询SQL
	 * 
	 * @return 返回推广人数和基站名称
	 */
	private static StringBuffer getTgrsSql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		String datetime_min = request.getParameter("datetime_min");
		String datetime_max = request.getParameter("datetime_max");
		sql.append("SELECT COUNT(*) FROM (SELECT COUNT(DISTINCT c.`address`) AS '推广人数', "
				+ " s.`NAME` AS '基站' " + " FROM customer c , syorganization s "
				+ " WHERE  c.`registertime` BETWEEN '" + datetime_min
				+ "'  " + " AND '" + datetime_max
				+ " 23:59:59' AND c.`status` <> 0 "
				+ " AND s.`ID`=c.`syorganization_id` ");
		String refereecode = request.getParameter("refereecode");
		if (!refereecode.equals("")) {
			sql.append(" AND c.`refereecode` = '" + refereecode + "' ");
		} else {
			sql.append("AND c.`refereecode` <> 0 ");
		}
		sql.append(" GROUP BY c.`syorganization_id`) a ");
		return sql;
	}

	/**
	 * 拼接查询地推注册但无openID
	 * 
	 * @return 返回：自起始日期后的地推注册但无openID的用户信息
	 */
	private static StringBuffer getNoOpenidSql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		String datetime_min = request.getParameter("datetime_min"); // 时间点_min
		String datetime_max = request.getParameter("datetime_max");// 时间点_max
		sql.append("SELECT COUNT(*) FROM customer c WHERE c.`registertime` BETWEEN '"
				+ datetime_min+ "' AND '"+ datetime_max
				+ " 23:59:59' AND NOT EXISTS (SELECT 1 "
				+ " FROM  xx_member_wechat_openid t WHERE t.`phoneno` = c.`phoneno` "
				+ " AND t.`modify_date` > c.`registertime`) ");
		return sql;
	}

	/**
	 * 拼接某时段注册用户数查询SQL
	 * 
	 * @return 返回：选定时段的注册用户数
	 */
	private static StringBuffer getZcyhSql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		String time = request.getParameter("time");
		sql.append("SELECT COUNT(*) FROM (SELECT ");
		if ("0".equals(time)) {
			sql.append(" DATE_FORMAT(c.`registertime`, '%Y') AS '时间段',");
		} else if ("1".equals(time)) {
			sql.append(" DATE_FORMAT(c.`registertime`, '%Y-%m') AS '时间段',");
		} else if ("2".equals(time)) {
			sql.append(" DATE_FORMAT(c.`registertime`, '%Y-%m-%d') AS '时间段',");
		} else if ("3".equals(time)) {
			sql.append(" DATE_FORMAT(c.`registertime`, '%Y-%m-%d,%H') AS '时间段',");
		}
		sql.append(" COUNT(c.`id`) AS '注册人数'  FROM customer c ");
		if ("0".equals(time)) {
			sql.append(" GROUP BY DATE_FORMAT(c.`registertime`, '%Y') ");
		} else if ("1".equals(time)) {
			sql.append(" GROUP BY DATE_FORMAT(c.`registertime`, '%Y-%m') ");
		} else if ("2".equals(time)) {
			sql.append(" GROUP BY DATE_FORMAT(c.`registertime`, '%Y-%m-%d') ");
		} else if ("3".equals(time)) {
			sql.append(" GROUP BY DATE_FORMAT(c.`registertime`, '%Y-%m-%d,%H') ");
		}
		sql.append(") a");
		return sql;
	}

	/**
	 * 拼接每日用户回收金额查询SQL
	 * 
	 * @return 返回:每日各用户回收总金额
	 */
	private static StringBuffer getHsjeSql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		String createDateTime = request.getParameter("createDateTime"); // 选择日期
		sql.append("SELECT COUNT(*) FROM task t, customer c, syuser s "
				+ " WHERE  c.`id`=t.`CUSTOMERID` AND t.`USERID` = s.`ID` AND t.`STATUS` = 4 " // 已经提交
				+ " AND t.`TYPE` <> 1 " // 附带商品 ??
				+ " AND t.`CREATEDATETIME` BETWEEN '"
				+ createDateTime
				+ "' AND '"
				+ createDateTime
				+ " 23:59:59' "
				+ " AND (SELECT SUM(d.`TOTALPRICE`) / 100 "
				+ " FROM"
				+ "  task_detail d "
				+ " WHERE d.`TASK_ID` = t.`ID`) > 0  "
				+ " ORDER BY t.`CREATEDATETIME` ");
		return sql;
	}

	/**
	 * 拼接地推信息查询SQL
	 * 
	 * @return 返回：该期间首次上门的用户的手机号，注册时间，商城使用信息(下单数_不包括取消的订单、完成订单的数量)
	 */
	private static StringBuffer getDtxxSql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		String refereecode = request.getParameter("refereecode");// 获取员工号
		String datetime_min = request
				.getParameter("datetime_min");// 获取时间段_min
		String datetime_max = request
				.getParameter("datetime_max");// 获取时间段_max
		sql.append("SELECT COUNT(*) FROM customer c, xx_member t LEFT JOIN xx_order o ON t.id = o.member "
				+ " WHERE c.phoneno = t.mobile " + " AND c.refereecode = "
				+ refereecode + " AND c.FIRSTRECYCLESUCCESSDATE BETWEEN '"
				+ datetime_min + "' " + " AND '"
				+ datetime_max + "' ");
		return sql;
	}

	/**
	 * 拼接用户信息查询SQL
	 * 
	 * @return 查询语句
	 */
	private static StringBuffer getYhxxSql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String phoneno = request.getParameter("phoneno");
		String datetime_min = request.getParameter("datetime_min");
		String datetime_max = request.getParameter("datetime_max");
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM customer c, xx_member m WHERE c.phoneno = m.mobile ");
		if (phoneno != "") {
			sql.append(" AND c.phoneno='" + phoneno + "' ");
		}
		if (datetime_min != "" && datetime_max != "") {
			sql.append(" AND c.registertime BETWEEN '" + datetime_min
					+ "' AND '" + datetime_max + " 23:59:59' ");
		}
		sql.append(" ORDER BY c.registertime");
		return sql;
	}
}
