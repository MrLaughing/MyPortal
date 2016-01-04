package com.zai360.portal.test.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

public class SqlUtil {
	public static StringBuffer getSql(QueryEnum query) {
		String id = query.getId();
		if (QueryEnum.APPXX.getId().equals(id)) {
			return getAppxxSql();
		} else if (QueryEnum.YHXX.getId().equals(id)) {
			return getYhxxSql();
		} else if (QueryEnum.DTXX.getId().equals(id)) {
			return getDtxxSql();
		} else if (QueryEnum.HSJE.getId().equals(id)) {
			return getHsjeSql();
		} else if (QueryEnum.ZCYH.getId().equals(id)) {
			return getZcyhSql();
		} else if (QueryEnum.NOID.getId().equals(id)) {
			return getNoOpenidSql();
		} else if (QueryEnum.HSXX.getId().equals(id)) {
			return getHsxxSql();
		} else if (QueryEnum.TGRS.getId().equals(id)) {
			return getTgrsSql();
		} else if (QueryEnum.SCSM.getId().equals(id)) {
			return getScsmSql();
		} else if (QueryEnum.DTZC.getId().equals(id)) {
			return getDtzcSql();
		} else if (QueryEnum.SCYH.getId().equals(id)) {
			return getScyhSql();
		} else if (QueryEnum.DDDC.getId().equals(id)) {
			return getDddcSql();
		} else if (QueryEnum.YXYH.getId().equals(id)) {
			return getYxyhSql();
		} else if (QueryEnum.TYYH.getId().equals(id)) {
			return getTyyhSql();
		} else if (QueryEnum.CFDZ.getId().equals(id)) {
			return getCfdzSql();
		} else if (QueryEnum.WYHQ.getId().equals(id)) {
			return getWyhqSql();
		} else if (QueryEnum.WSSJ.getId().equals(id)) {
			return getWssjSql();
		} else if (QueryEnum.CCRS.getId().equals(id)) {
			return getCcrsSql();
		} else if (QueryEnum.SCXD.getId().equals(id)) {
			return getScxdSql();
		} else if (QueryEnum.XDMX.getId().equals(id)) {
			return getXdmxSql();
		} else if (QueryEnum.ZCFW.getId().equals(id)) {
			return getZcfwSql();
		} else if (QueryEnum.DTFW.getId().equals(id)) {
			return getDtfwSql();
		} else if (QueryEnum.ZHYE.getId().equals(id)) {
			return getZhyeSql();
		} else if (QueryEnum.FWRQ.getId().equals(id)) {
			return getFwrqSql();
		} else if (QueryEnum.SCSY.getId().equals(id)) {
			return getScsySql();
		} else if (QueryEnum.ZXYY.getId().equals(id)) {
			return getZxyySql();
		} else if (QueryEnum.SMWY.getId().equals(id)) {
			return getSmwySql();
		} else if (QueryEnum.HSSJ.getId().equals(id)) {
			return getHssjSql();
		} else if (QueryEnum.TSGL.getId().equals(id)) {
			return getTsglSql();
		} else if (QueryEnum.SMKH.getId().equals(id)) {
			return getSmkhSql(query);
		} else if (QueryEnum.DHGW.getId().equals(id)) {
			return getDhgwSql();
		} else {
			return null;
		}
	}


	/**********************************/
	/**
	 * 电话购物信息查询SQL
	 * @return
	 */
	private static StringBuffer getDhgwSql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		String createDate_min = request.getParameter("createDate_min");
		String createDate_max = request.getParameter("createDate_max");
		sql.append("SELECT c.phoneno AS '手机号', s.name AS '所属基站', c.registertime AS '注册时间', "
				+ " c.recycletimes AS '回收次数', c.ordertimes AS '商城使用次数', COUNT(1) AS '通过电话购物成功下单次数', "
				+ " SUM(o.amount_paid) AS '通过电话购物下单总金额' FROM polan.customer c, polan.syorganization s, "
				+ " polan.xx_order o, polan.xx_order_way_log l WHERE o.order_status = 2 AND c.syorganization_id = s.id "
				+ " AND c.phoneno = o.phone AND o.sn = l.order_sn AND l.type = 2 AND o.create_date BETWEEN '"+createDate_min+"' "
				+ " AND '"+createDate_max+" 23:59:59' GROUP BY c.phoneno ");
		return sql;
	}
	/**
	 * 上门客户数查询SQL
	 * 
	 * @return
	 */
	private static StringBuffer getSmkhSql(QueryEnum query) {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(1) AS '上门客户数' ");
		//根据query.getlength拼接sql
		for(int i=1;i<query.getLength();i++){//1为最初始长度
				sql.append(", SUM(CASE WHEN a.recycletimes = "+((i-1)+1)
						+" THEN 1 ELSE 0 END) AS '"+((i-1)+1)+"次上门客户数' ");
		}
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

	/**
	 * 用户投诉管理查询SQL
	 * 
	 * @return
	 */
	private static StringBuffer getTsglSql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		String complainTime_min = request.getParameter("complainTime_min");
		String complainTime_max = request.getParameter("complainTime_max");
		sql.append("SELECT b.`phoneno` AS '手机号' , CASE a.`COMPLAINTTYPE` WHEN 0 THEN '投诉' "
				+ " WHEN 1 THEN '建议' ELSE '未知' END AS '类型', a.`COMPLAINTIME` AS '投诉时间', "
				+ " a.`CONTENT` AS '投诉内容', a.`HANDLETIME` AS '处理时间', a.`HANDLERESUTL` AS '处理结果', "
				+ " CASE a.`STATUS` WHEN 0 THEN '未解决' WHEN 1 THEN '已解决' ELSE '未知' END AS '状态', "
				+ " a.`OPERATOR` AS '操作人', c.`NAME` AS '所属基站' FROM complaint a, customer b, "
				+ " syorganization c WHERE a.`CUSTOMERID` = b.`id` AND b.`syorganization_id` = c.`ID` "
				+ " AND a.COMPLAINTIME >= '"
				+ complainTime_min
				+ " 00:00:00' AND a.COMPLAINTIME <= '"
				+ complainTime_max
				+ " 23:59:59'");
		return sql;
	}

	/**
	 * 回收二手智能机台数查询 回收手机任务信息
	 * 
	 * @return
	 */
	private static StringBuffer getHssjSql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		String recycleTaskDate_min = request
				.getParameter("recycleTaskDate_min");// 回收手机时间段
		String recycleTaskDate_max = request
				.getParameter("recycleTaskDate_max");
		sql.append("SELECT s.`NAME` AS '基站', m.`REAL_QUANTITY` AS '回收二手智能机数量', "
				+ " m.`PHONENO` AS '手机号', CASE WHEN m.`STATUS` = 0 THEN '未处理' "
				+ " WHEN m.`STATUS` = 1 THEN '发出' WHEN m.`STATUS` = 2 THEN '收到' WHEN m.`STATUS` = 3 "
				+ " THEN '退回' WHEN m.`STATUS` = 4 THEN '已完成' END AS '订单状态', "
				+ " t.`CREATEDATETIME` AS '创建日期', m.`CREATEDATETIME` AS '完成时间' FROM "
				+ " mobile_recycle_task m, TASK t, syorganization s WHERE m.`TASK` = t.`ID` "
				+ " AND m.`SYORGANIZATION_ID` = s.`ID` AND t.`CREATEDATETIME` BETWEEN '"
				+ recycleTaskDate_min
				+ "'"
				+ " AND '"
				+ recycleTaskDate_max
				+ " 23:59:59' AND m.`CREATEDATETIME` BETWEEN '"
				+ recycleTaskDate_min
				+ "' AND '"
				+ recycleTaskDate_max
				+ " 23:59:59' " + "ORDER BY s.code,t.CREATEDATETIME ");
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
		String orgcode = request.getParameter("orgcode");// 基站CODE
		String realReceveiTime_min = request
				.getParameter("realReceveiTime_min");// 注销时间段
		String realReceveiTime_max = request
				.getParameter("realReceveiTime_max");
		sql.append("SELECT c.`phoneno` AS '手机号', c.`address` AS '客户地址', a.`comment` AS '原因', "
				+ " s.`NAME` AS '所属基站' FROM TASK a, customer c, syorganization s "
				+ " WHERE a.`CUSTOMERID` = c.`id` AND s.`ID` = a.`syorganization_id` "
				+ " AND a.`STATUS` = 5 AND a.`TYPE` = 0 AND a.`realReceveiTime` >= '"
				+ realReceveiTime_min
				+ "' AND a.`realReceveiTime` <= '"
				+ realReceveiTime_max
				+ " 23:59:59' AND c.`FIRSTRECYCLESUCCESSDATE` IS NULL ");
		if (orgcode != "") {
			sql.append(" AND s.`CODE`=" + orgcode);
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
		String orgcode = request.getParameter("orgcode");// 基站CODE
		String updatedatetime_min = request.getParameter("updatedatetime_min");// 注销时间段
		String updatedatetime_max = request.getParameter("updatedatetime_max");
		sql.append("SELECT a.`phoneno` AS '手机号', a.`address` '客户地址', "
				+ " a.`updatedatetime` AS '注销时间', CASE WHEN a.`reason` IS NULL "
				+ " THEN a.comment ELSE a.reason END AS '注销原因', "
				+ " s.`NAME` AS '所属基站' FROM customer a, syorganization s WHERE a.`status` = 0 "
				+ " AND a.`syorganization_id` = s.`id` AND a.`updatedatetime` >= '"
				+ updatedatetime_min + "' AND a.updatedatetime <= '"
				+ updatedatetime_max + " 23:59:59' ");
		if (orgcode != "") {
			sql.append(" AND s.`CODE`=" + orgcode);
		}
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
		String firstordersuccessdate_min = request
				.getParameter("firstordersuccessdate_min");// 首次下单成功时间
		String firstordersuccessdate_max = request
				.getParameter("firstordersuccessdate_max");
		sql.append("SELECT s.`NAME` AS '地推人员', COUNT(1) AS '推荐用户商城首次使用数' "
				+ " FROM customer c, syuser s, recycleman r WHERE c.`refereecode` = s.`LOGINNAME` "
				+ " AND s.`ID` = r.`SYUSERID` AND s.`USERTYPE` = 2 AND r.`TIMETYPE` = 1"
				+ " AND c.`FIRSTORDERSUCCESSDATE` IS NOT NULL AND c.`FIRSTORDERSUCCESSDATE` >= '"
				+ firstordersuccessdate_min
				+ "' AND c.`FIRSTORDERSUCCESSDATE` <= '"
				+ firstordersuccessdate_max
				+ " 23:59:59' GROUP BY c.`refereecode` ORDER BY c.`refereecode`");
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
		String loginname = request.getParameter("loginname");// 地推人员工号
		String registertime_min = request.getParameter("registertime_min");// 注册时间
		String registertime_max = request.getParameter("registertime_max");
		sql.append("SELECT SUM(CASE WHEN c.`recycleday` = 1 THEN 1 ELSE 0 END) AS '周一', "
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
		if (loginname != "") {
			sql.append(" AND s.`LOGINNAME` = " + loginname);
		}
		sql.append(" AND c.`registertime` >= '" + registertime_min
				+ " ' AND c.`registertime` " + " <= '" + registertime_max
				+ " 23:59:59' GROUP BY c.`syorganization_id` "
				+ " ORDER BY org.`CODE` ");
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
		sql.append("SELECT (SUM(a.`credit`)-SUM(a.`debit`)) AS '账户总余额' FROM xx_deposit a "
				+ "WHERE a.`create_date` <= '" + createDate + " 23:59:59' ");
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
		sql.append("SELECT (SELECT s.`NAME` FROM syorganization s WHERE s.`ID` = t.`syorganization_id`) AS '基站名称', "
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
				+ " GROUP BY t.`USERID`, DATE_FORMAT(t.`realReceveiTime`, '%Y-%m-%d') ORDER BY '基站名称' ");
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
		String orderReceiveTime_min = request
				.getParameter("orderReceiveTime_min");
		String orderReceiveTime_max = request
				.getParameter("orderReceiveTime_max");
		sql.append("SELECT t.`ID` AS '任务ID', s.`NAME` AS '再生侠', t.`ORDERRECEIVEDATE` "
				+ " AS '订单接受日期',( CASE WHEN t.`TYPE` = 0 THEN '上门回收' "
				+ " WHEN t.`TYPE` = 1 THEN '附带商品' WHEN t.`TYPE` = 2 THEN '召唤服务' "
				+ " WHEN t.`TYPE` = 3 THEN '临时任务 ' WHEN t.`TYPE` = 4 THEN '回收手机 ' "
				+ " WHEN t.`TYPE` = 5 THEN '回收家电 ' END ) AS '回收类型', "
				+ " t.`RECYCLETOTAL` AS '回收总金额',t.`RECYCLEBAG` AS '回收袋子金额', "
				+ " t.`RECYCLEBOTTLE` AS '回收瓶子金额',t.`RECYCLECLOTHES` AS '回收衣服金额', "
				+ " t.`RECYCLEELECTRONIC` AS '回收电子金额',t.`RECYCLEPAPER` AS '回收废纸金额' "
				+ " FROM task t , syuser s  WHERE  t.`USERID`=s.`ID` AND t.status = 4  "
				+ " AND t.`ORDERRECEIVEDATE` BETWEEN '"
				+ orderReceiveTime_min
				+ "' AND '" + orderReceiveTime_max + " 23:59:59'");
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
		String createDate_min = request.getParameter("createDate_min");// 首次下单日期
		String createDate_max = request.getParameter("createDate_max");
		sql.append("SELECT t.`sn` AS '订单编号', t.`phone` AS '手机号', t.`create_date` AS '下单时间', (SELECT"
				+ " SUM(i1.`quantity` * i1.`price`) + t.freight + t.`fee` - "
				+ " t.`promotion_discount` - t.`coupon_discount` + t.`offset_amount` + "
				+ " t.`tax`  FROM xx_order_item i1 WHERE i1.`orders` = t.`id`) "
				+ " AS '订单总金额', t.`account_paid` AS '账户抵用金额', m.`balance` "
				+ " AS '当前余额', p.`sn` AS '商品编号', p.`name` AS '商品名称',"
				+ " i.`sale_price` AS '商品原价', i.`price` AS '商品价格', i.`quantity` AS '数量' "
				+ " FROM xx_order t, xx_member m, xx_product p, xx_order_item i "
				+ " WHERE t.`create_date` >= '"
				+ createDate_min
				+ "' AND t.`create_date` <= '"
				+ createDate_max
				+ " 23:59:59' AND t.`id` = i.`orders` "
				+ " AND i.`product` = p.`id` AND ( ( t.`order_status` = '2' "
				+ " AND t.`shipping_status` = '2') OR t.`order_status` = '1')"
				+ " AND t.`member` = m.`id` AND NOT EXISTS (SELECT 1 FROM xx_order o1"
				+ " WHERE o1.`member` = t.`member` AND ( ( t.`order_status` = '2'"
				+ " AND t.`shipping_status` = '2') OR t.`order_status` = '1') "
				+ " AND o1.`create_date` < t.`create_date`) ORDER BY t.`sn`, p.`sn`");
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
		String completeTime_min = request.getParameter("completeTime_min");// 订单完成日期_min
		String completeTime_max = request.getParameter("completeTime_max");// 订单完成日期_max
		sql.append("SELECT DISTINCT t.`sn` AS '订单编号', (SELECT ( (SELECT "
				+ " SUM(item.`price` * item.`quantity`) FROM xx_order_item item "
				+ " WHERE item.`orders` = o2.`id`) + o2.`fee` + o2.freight + o2.`tax` + "
				+ " o2.`offset_amount` - o2.`promotion_discount` - o2.`coupon_discount` ) "
				+ " AS price  FROM xx_order o2  WHERE o2.`sn` = t.`sn`) AS '订单总金额',"
				+ " t.`consignee` AS '收货人', t.`phone` AS '手机号', t.`address` AS '地址',"
				+ " s.`NAME` AS '所属基站', l.`create_date` AS '完成时间' FROM xx_order t,"
				+ " xx_order_log l, xx_member m, syorganization s WHERE t.`member` = m.`id` "
				+ " AND l.`orders` = t.`id` AND m.`syorganization_id`=s.`ID` AND l.`type` "
				+ " = 7 AND l.`create_date` BETWEEN '"
				+ completeTime_min
				+ "' AND '"
				+ completeTime_max
				+ "' AND t.`create_date` >= '"
				+ createDate
				+ " 23:59:59' AND (SELECT"
				+ " SUM(item.`price` * item.`quantity`) FROM xx_order_item item"
				+ " WHERE item.`orders` = t.`id`) > 0 AND NOT EXISTS (SELECT 1 FROM xx_order o1 "
				+ " WHERE t.`member` = o1.`member` AND o1.`create_date` < t.`create_date` AND "
				+ " (SELECT SUM(item.`price` * item.`quantity`) FROM xx_order_item item "
				+ " WHERE item.`orders` = o1.`id`) > 0 AND o1.`order_status` = 2) ORDER BY t.`sn` ");
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
		sql.append("SELECT t.`sn` AS '订单编号',  p.`sn` AS '商品编号',  p.`name` AS '商品名称',"
				+ "  i.`quantity` AS '订单数量',  p.`price` AS '商品价格',  (SELECT "
				+ " SUM(i1.`quantity` * i1.`price`) + t.freight + t.`fee` - t.`promotion_discount` - "
				+ " t.`coupon_discount` + t.`offset_amount` + t.`tax` FROM  xx_order_item i1"
				+ " WHERE i1.`orders` = t.`id`) AS '总金额',"
				+ " t.`amount_paid` AS '已付金额',  (SELECT  SUM(pay.`amount`)  FROM  xx_payment pay "
				+ " WHERE pay.`orders` = t.`id`  AND pay.`method` = 0 "
				+ " AND pay.`status` = 1) AS '在线支付',  t.`account_paid` AS '账户抵用',  t.`consignee` AS '收货人',"
				+ " t.`phone` AS '手机号',  t.`station_name` AS '基站',  t.`address` AS '地址',  t.`zip_code` AS '邮编',"
				+ " CASE  WHEN t.`is_invoice` = b'0'   THEN '否'   WHEN t.`is_invoice` = b'1' "
				+ " THEN '是' END AS '是否开具发票',  t.`invoice_title` AS '发票抬头',  t.`payment_method_name` AS '支付方式',"
				+ " t.`shipping_method_name` AS '配送方式', CASE WHEN t.`order_status`=0 THEN '未确认'"
				+ " WHEN t.`order_status`= 1 THEN '已确认' WHEN t.`order_status`= 2 THEN '已完成'"
				+ " WHEN t.`order_status`= 3 THEN '已取消'  END AS '订单状态',  CASE WHEN t.`payment_status`=0 THEN '未支付'"
				+ " WHEN t.`payment_status`= 1 THEN '部分支付'  WHEN t.`payment_status`= 2 THEN '已支付'"
				+ " WHEN t.`payment_status`= 3 THEN '部分退款'  WHEN t.`payment_status`= 4 THEN '已退款'  END AS '支付状态',"
				+ " CASE WHEN t.`shipping_status`=0 THEN '未发货' WHEN t.`shipping_status`= 1 THEN '部分发货'"
				+ " WHEN t.`shipping_status`= 2 THEN '已发货'  WHEN t.`shipping_status`= 3 THEN '部分退货'"
				+ "  WHEN t.`shipping_status`= 4 THEN '已退货' END AS '配送状态',  t.`estimated_delivery_date` AS '确认收货',"
				+ " (SELECT    l.`create_date`  FROM   xx_order_log l  WHERE l.`orders` = t.`id` "
				+ " AND l.`type` = 0 LIMIT 1) AS '创建订单',  (SELECT l.`create_date`   FROM   xx_order_log l "
				+ " WHERE l.`orders` = t.`id`  AND l.`type` = 2 LIMIT 1) AS '确认订单',  (SELECT   l.`create_date`  FROM"
				+ " xx_order_log l  WHERE l.`orders` = t.`id` AND l.`type` = 5 LIMIT 1) AS '配送订单',  (SELECT "
				+ " l.`create_date`  FROM    xx_order_log l  WHERE l.`orders` = t.`id`   AND l.`type` = 7 LIMIT 1) AS '完成订单',"
				+ " (SELECT  l.`create_date`  FROM   xx_order_log l  WHERE l.`orders` = t.`id` "
				+ " AND l.`type` = 8 LIMIT 1) AS '取消订单' FROM  xx_order t, xx_product p, xx_order_item i WHERE t.`id` = i.`orders` "
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
		sql.append("select t.sn as '订单',");
		sql.append(" (select sum(it.quantity*it.price) + t.freight -t.promotion_discount - t.coupon_discount from xx_order_item it where it.orders=t.id) as '总金额',");
		sql.append(" t.phone as '手机号', "
				+ " CASE WHEN t.`source_type`=0 THEN 'iOS' "
				+ " WHEN t.`source_type`=1 THEN 'Android' "
				+ " WHEN t.`source_type`=2 THEN '微信' " + " END AS '客户端' ");
		String create_date_min = request.getParameter("create_date_min");
		String create_date_max = request.getParameter("create_date_max");
		sql.append("from xx_order t where t.create_date between '"
				+ create_date_min + "' and '" + create_date_max + " 23:59:59' ");
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
		String orgCode = request.getParameter("orgCode");
		String registertime_min = request.getParameter("registertime_min");
		String registertime_max = request.getParameter("registertime_max");
		sql.append("SELECT  c.phoneno AS '手机号', CASE c.recyclePeriod WHEN 2 THEN "
				+ " '隔周' WHEN 3 THEN '每三周' WHEN 4 THEN '每四周' ELSE '周期未维护' END "
				+ " AS '服务周期', CASE c.`recycleday` WHEN 1 THEN '周一' WHEN 2 THEN '周二' "
				+ " WHEN 3 THEN '周三' WHEN 4 THEN '周四' WHEN 5 THEN '周五' WHEN 6 THEN "
				+ " '周六' WHEN 7 THEN '周日' ELSE '未知' END AS '服务日期', "
				+ " c.`recycletimequantum` AS '服务时间段', c.`registertime` AS '注册时间', "
				+ " CASE WHEN s.`NAME` IS NULL THEN c.refereecode ELSE s.name END AS '推荐人', "
				+ " CASE c.status WHEN 0 THEN '暂停服务' WHEN 1 THEN '正常服务' WHEN -1 THEN "
				+ " '出差' WHEN 2 THEN '我不住这儿' WHEN 3 THEN '信息不完善' WHEN 4 THEN '即将开通' "
				+ " WHEN 5 THEN '黑户' ELSE '状态未维护' END AS '用户状态', CASE c.userType "
				+ " WHEN 0 THEN 'iOS注册' WHEN 1 THEN 'Android注册' WHEN 2 THEN '微信注册' "
				+ " WHEN 3 THEN '网上注册' WHEN 4 THEN '后台注册' WHEN 10 THEN '地推注册' "
				+ " ELSE '未知' END AS '用户来源', c.recycleTimes AS '回收次数', c.orderTimes "
				+ " AS '商城使用次数', org.`NAME` AS '所属基站', c.`address` AS '用户地址', "
				+ " CASE WHEN EXISTS(SELECT 1 FROM TASK t WHERE t.customerid = c.id AND "
				+ " DATE_FORMAT(t.createdatetime, '%Y-%m-%d') = DATE_FORMAT(c.registertime, '%Y-%m-%d')) "
				+ " THEN '是' ELSE '否' END AS '注册当天是否有临时任务', CASE WHEN c.status = -1 "
				+ " THEN '是' ELSE '否' END AS '是否开启了出差' FROM customer c, syorganization org, "
				+ " syuser s WHERE c.`syorganization_id` = org.`ID` AND c.`refereecode` = "
				+ " s.`LOGINNAME` AND c.`refereecode` IS NOT NULL ");
		if (orgCode != "") {
			sql.append(" AND org.`CODE`= '" + orgCode + "' ");
		}
		if (registertime_min != "" && registertime_max != "") {
			sql.append(" AND c.`registertime` >= '" + registertime_min
					+ " 00:00:00' AND c.`registertime` <= '" + registertime_max
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
		String orgCode = request.getParameter("orgCode");
		String registertime_min = request.getParameter("registertime_min");
		String registertime_max = request.getParameter("registertime_max");
		sql.append("SELECT c.phoneno AS '手机号', CASE c.recyclePeriod WHEN 2 THEN '隔周' "
				+ " WHEN 3 THEN '每三周' WHEN 4 THEN '每四周' ELSE '周期未维护' END AS '服务周期', "
				+ " CASE c.recycleday WHEN 1 THEN '周一' WHEN 2 THEN '周二' WHEN 3 THEN '周三' "
				+ " WHEN 4 THEN '周四' WHEN 5 THEN '周五' WHEN 6 THEN '周六' WHEN 7 THEN '周日' "
				+ " ELSE '日期未维护' END AS '服务日期', c.recycleTimeQuantum AS '服务时间段', "
				+ " c.registerTime AS '注册时间', s.`NAME` AS '推荐人', CASE c.status WHEN 0 "
				+ " THEN '暂停服务' WHEN 1 THEN '正常服务' WHEN -1 THEN '出差' WHEN 2 THEN '我不住这儿' "
				+ " WHEN 3 THEN '信息不完善' WHEN 4 THEN '即将开通' WHEN 5 THEN '黑户' ELSE '状态未维护' "
				+ " END AS '用户状态', CASE c.userType WHEN 0 THEN 'iOS注册' WHEN 1 THEN 'Android注册' "
				+ " WHEN 2 THEN '微信注册' WHEN 3 THEN '网上注册' WHEN 4 THEN '后台注册' WHEN 10 THEN "
				+ " '地推注册' ELSE '未知' END AS '用户来源', c.recycleTimes AS '回收次数', "
				+ " c.orderTimes AS '商城使用次数', org.name AS '所属基站' , c.address AS '用户地址', "
				+ " CASE WHEN EXISTS(SELECT 1 FROM TASK t WHERE t.customerid = c.id AND "
				+ " DATE_FORMAT(t.createdatetime, '%Y-%m-%d') = DATE_FORMAT(c.registertime, '%Y-%m-%d')) "
				+ " THEN '是' ELSE '否' END AS '注册当天是否有临时任务', CASE WHEN c.status = -1 "
				+ " THEN '是' ELSE '否' END AS '是否开启了出差' FROM Customer c LEFT JOIN syorganization org "
				+ " ON c.syorganization_id = org.id  LEFT JOIN syuser s ON c.`refereecode` = s.`LOGINNAME` "
				+ " WHERE 1 = 1 ");
		if (orgCode != "") {
			sql.append(" AND org.`CODE`= '" + orgCode + "' ");
		}
		if (registertime_min != "" && registertime_max != "") {
			sql.append(" AND c.`registertime` >= '" + registertime_min
					+ " 00:00:00' AND c.`registertime` <= '" + registertime_max
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
		String createDateTime_min = request.getParameter("createDateTime_min");
		String createDateTime_max = request.getParameter("createDateTime_max");
		sql.append("SELECT DATE_FORMAT( c.`CREATEDATETIME`, '%Y-%m-%d') AS '日期',"
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
				+ createDateTime_min
				+ "' AND C.`CREATEDATETIME` <= '"
				+ createDateTime_max
				+ " 23:59:59' "
				+ " AND t.`phoneno` = c.`PHONENO` AND s.`ID` = "
				+ " t.`syorganization_id`  AND c.`OPERATION` IN ( '开启出差模式', "
				+ " '微信设置出差模式' ) GROUP BY DATE_FORMAT(c.`CREATEDATETIME`, '%Y-%m-%d'),"
				+ " s.`CODE`");
		return sql;
	}

	/**
	 * 拼接未完善回收时间用户查询SQL
	 * 
	 * @return
	 */
	private static StringBuffer getWssjSql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT c.`phoneno` AS '手机号',c.`address` AS '地址',s.`NAME` AS '基站名称', "
				+ " c.`recycletimes` AS '回收次数',c.`refereecode` AS '再生侠编号',m.`balance` AS '余额' "
				+ " FROM customer c,syorganization s,xx_member m WHERE c.`builingcode` IS NOT NULL "
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
		String credit = request.getParameter("credit");// 最低金额
		String type = request.getParameter("type");// 类型
		String create_date = request.getParameter("create_date");// 选定日期
		String code = request.getParameter("code");// 优惠券编码,以逗号,分隔
		sql.append("SELECT DISTINCT  m.`mobile` AS '手机号' , cu.`name` AS '用户名'"
				+ " FROM xx_deposit d, xx_member m, customer cu WHERE d.`member` = m.`id` "
				+ " AND d.`credit` >= "
				+ credit
				+ " AND d.`type` = "
				+ type
				+ " "
				+ " AND d.`create_date` > '"
				+ create_date
				+ "' "
				+ " AND cu.`phoneno` = m.`mobile` AND NOT EXISTS (SELECT 1 "
				+ " FROM xx_coupon_code c, xx_coupon cc WHERE c.`coupon` = cc.`id` "
				+ " AND cc.`code` IN (" + code
				+ ") AND d.`member` = c.`member`)");
		return sql;
	}

	/**
	 * 拼接重复地址用户信息查询SQL
	 * 
	 * @return
	 */
	private static StringBuffer getCfdzSql() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT c.`phoneno` AS '手机号', c.`address` AS '地址', cc.`NAME` AS '小区名称', "
				+ " c.`communitycode` AS '小区编号', bb.`NAME` AS '楼宇', c.`builingcode` AS '楼宇号', "
				+ " c.`housenumber` AS '门牌号', c.`ordertimes` AS '商城使用次数', "
				+ " c.`recycletimes` AS '上门回收次数', c.`refereecode` AS '推荐人', "
				+ " c.`registertime` AS '注册时间',  t.numbers AS '重复数量' "
				+ " FROM customer c,  (SELECT b.`address` AS address,   COUNT(b.`address`) AS numbers "
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
		String registertime = request.getParameter("registertime");
		sql.append("SELECT c.`phoneno` AS '手机号',  c.`refereecode` AS '推广人员工号', "
				+ " c.`registertime` AS '注册时间', (SELECT MAX(t.`CREATEDATETIME`) "
				+ " FROM task t  WHERE t.`CUSTOMERID` = c.`id`) AS '预约上门时间',"
				+ " (SELECT MAX(s.`NAME`) FROM task t, syuser s WHERE t.`USERID` = s.`ID` "
				+ " AND t.`CUSTOMERID` = c.`id` AND t.`STATUS` = 5 AND t.`TYPE` <> 1) AS '服务再生侠', "
				+ " (SELECT MAX(t.`comment`) FROM task t WHERE t.`CUSTOMERID` = c.`id` "
				+ " AND t.`STATUS` = 5 AND t.`TYPE` <> 1) AS '违约原因', s.`NAME` AS '所属基站', "
				+ " (SELECT COUNT(1) FROM task t WHERE t.`STATUS` = 5 AND t.`TYPE` <> 1 "
				+ " AND t.`CUSTOMERID` = c.`id`) AS '回收次数', c.`address` AS '用户地址',"
				+ " (SELECT MAX(t.`OPERATOR`) FROM customer_log t WHERE t.`OPERATION` = '用户暂停服务' "
				+ " AND t.`PHONENO` = c.`phoneno`) AS '暂停服务操作人', c.`recycletimes` AS '成功回收次数' "
				+ " FROM  customer c LEFT JOIN syorganization s ON c.`syorganization_id` = s.`ID` "
				+ " WHERE c.`registertime` >= '"
				+ registertime
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
		sql.append("SELECT DISTINCT c.`phoneno` AS '手机号', "
				+ " m.`balance` AS '当前余额', " + " c.`address` AS '地址', "
				+ " c.`recycletimes` AS '回收次数', "
				+ " c.`registertime` AS '注册时间' "
				+ " FROM  xx_member m, customer c "
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
		String syorganizationCode = request.getParameter("syorganizationCode");
		String customerregistertime_min = request
				.getParameter("customerregistertime_min");
		String customerregistertime_max = request
				.getParameter("customerregistertime_max");
		sql.append("SELECT  c.`phoneno` AS '手机号', c.`registertime` AS '注册时间', "
				+ " CASE WHEN s.`NAME` IS NOT NULL THEN s.name ELSE 'n/a' END AS '地推姓名', "
				+ " s.`LOGINNAME` AS '地推工号',  sy.`NAME` AS '基站名称', "
				+ " c.`address` AS '地址', "
				+ " CASE c.status WHEN 1 THEN '正常服务' WHEN -1 THEN '出差' END AS '状态' "
				+ " FROM syorganization sy, "
				+ " customer c LEFT JOIN syuser s ON c.`refereecode` = s.`LOGINNAME` WHERE ");
		if (syorganizationCode != "") {
			sql.append("sy.`CODE`='" + syorganizationCode + "' AND");
		}
		sql.append(" c.`syorganization_id` = sy.`id` "
				+ " AND c.`registertime` BETWEEN '" + customerregistertime_min
				+ "' " + " AND '" + customerregistertime_max
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
		String firstRecycleSuccessDate_min = request
				.getParameter("firstRecycleSuccessDate_min");
		String firstRecycleSuccessDate_max = request
				.getParameter("firstRecycleSuccessDate_max");
		sql.append("SELECT COUNT(DISTINCT(c.`address`)) AS '首次上门数',sy.`NAME` AS '基站' "
				+ " FROM syuser s,customer c, syorganization sy "
				+ " WHERE 1 = 1 AND c.refereecode = s.loginname  "
				+ " AND c.FIRSTRECYCLESUCCESSDATE BETWEEN '"
				+ firstRecycleSuccessDate_min
				+ "' AND '"
				+ firstRecycleSuccessDate_max + " 23:59:59' ");
		String refereecode = request.getParameter("refereecode");
		if (!refereecode.equals("")) {
			sql.append(" AND c.`refereecode` = '" + refereecode + "' ");
		}
		sql.append("AND sy.`ID`=c.`syorganization_id`"
				+ " GROUP  BY c.`syorganization_id`");
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
		String registertime_min = request.getParameter("registertime_min");
		String registertime_max = request.getParameter("registertime_max");
		sql.append("SELECT COUNT(DISTINCT c.`address`) AS '推广人数', "
				+ " s.`NAME` AS '基站' " + " FROM customer c , syorganization s "
				+ " WHERE  c.`registertime` BETWEEN '" + registertime_min
				+ "'  " + " AND '" + registertime_max
				+ " 23:59:59' AND c.`status` <> 0 "
				+ " AND s.`ID`=c.`syorganization_id` ");
		String refereecode = request.getParameter("refereecode");
		if (!refereecode.equals("")) {
			sql.append(" AND c.`refereecode` = '" + refereecode + "' ");
		} else {
			sql.append("AND c.`refereecode` <> 0 ");
		}
		sql.append(" GROUP BY c.`syorganization_id` ");
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
		String registertime_min = request.getParameter("registertime_min"); // 时间点_min
		String registertime_max = request.getParameter("registertime_max");// 时间点_max
		sql.append("SELECT  c.`phoneno` AS '手机号', c.`address` AS '地址', "
				+ " c.`name` AS '姓名', c.`registertime` AS '注册时间', "
				+ " (SELECT s.`NAME` FROM syuser s "
				+ " WHERE s.`LOGINNAME` =c.`refereecode`) AS '推荐人'FROM "
				+ " customer c WHERE c.`registertime` BETWEEN '"
				+ registertime_min
				+ "' "
				+ " AND '"
				+ registertime_max
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
		sql.append("SELECT ");
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
		sql.append("SELECT c.`phoneno` AS '手机号', "
				+ " t.id AS '任务ID', "
				+ " (SELECT SUM(d.`TOTALPRICE`) / 100 FROM  task_detail d "
				+ " WHERE d.`TASK_ID` = t.`ID`) AS '回收金额', "
				+ " t.`realReceveiTime` AS '完成时间', "
				+ "	s.`NAME` AS '再生侠' "
				+ " FROM task t, customer c, syuser s "
				+ " WHERE  c.`id`=t.`CUSTOMERID` AND t.`USERID` = s.`ID` AND t.`STATUS` = 4 " // 已经提交
				+ " AND t.`TYPE` <> 1 " // 附带商品 ??
				+ " AND t.`CREATEDATETIME` BETWEEN '" + createDateTime
				+ "' AND '" + createDateTime + " 23:59:59' "
				+ " AND (SELECT SUM(d.`TOTALPRICE`) / 100 " + " FROM"
				+ "  task_detail d " + " WHERE d.`TASK_ID` = t.`ID`) > 0  "
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
		String firstrecyclesuccess_date_min = request
				.getParameter("firstrecyclesuccess_date_min");// 获取时间段_min
		String firstrecyclesuccess_date_max = request
				.getParameter("firstrecyclesuccess_date_max");// 获取时间段_max
		sql.append("SELECT c.phoneno AS '手机号', "
				+ " c.registertime AS '注册时间', "
				+ " SUM(CASE WHEN o.order_status <> 3 THEN 1 ELSE 0 END) AS '下单', "
				+ " SUM(CASE WHEN o.order_status = 2 THEN 1 ELSE 0 END) AS '完成订单数' "
				+ " FROM customer c, xx_member t LEFT JOIN xx_order o ON t.id = o.member "
				+ " WHERE c.phoneno = t.mobile " + " AND c.refereecode = "
				+ refereecode + " AND c.FIRSTRECYCLESUCCESSDATE BETWEEN '"
				+ firstrecyclesuccess_date_min + "' " + " AND '"
				+ firstrecyclesuccess_date_max + "' " + " GROUP BY t.id ");
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
		String registertime_min = request.getParameter("registertime_min");
		String registertime_max = request.getParameter("registertime_max");
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT c.phoneno AS '手机号', "
				+ " CASE WHEN c.`status` = -1 THEN '差旅' "
				+ " WHEN c.`status` = 0 THEN '暂停用户' "
				+ " WHEN c.`status` = 1 THEN '正常用户' "
				+ " WHEN c.`status` = 2 THEN '我不住这儿' "
				+ " ELSE '其它' END AS '用户状态', "
				+ " (SELECT l.CREATEDATETIME FROM customer_log l WHERE l.PHONENO = c.PHONENO AND l.OPERATION = '暂停账户' ORDER BY l.CREATEDATETIME DESC LIMIT 1) AS '暂停服务时间', "
				+ " m.balance AS '账户余额', "
				+ " c.registertime AS '注册时间', "
				+ " c.recycletimes AS '上门回收次数', "
				+ " c.ordertimes AS '商城使用次数', "
				+ " (SELECT COUNT(1) FROM task t WHERE t.STATUS = 5 AND t.CUSTOMERID = c.id ) AS '违约次数', "
				+ " (SELECT COUNT(1) FROM task t WHERE t.STATUS = 6 AND t.CUSTOMERID = c.id ) AS '商品拒收次数', "
				+ " (SELECT MIN(t.ORDERRECEIVEDATE) FROM task t WHERE t.STATUS = 4 AND t.CUSTOMERID = c.id) AS '首次上门服务时间', "
				+ " (SELECT MIN(t.create_date) FROM xx_order t WHERE t.member = m.id AND (t.order_status = '1' OR "
				+ " t.order_status = '2')) AS '首次商城下单时间', (SELECT MAX(t.ORDERRECEIVEDATE) FROM task t "
				+ " WHERE t.STATUS = 4 AND t.CUSTOMERID = c.id) AS '最近上门服务时间', (SELECT MAX(t.create_date) "
				+ " FROM xx_order t WHERE t.member = m.id ) AS '最近商城下单时间', (SELECT SUM((SELECT "
				+ " SUM(t1.`price`*t1.`quantity`) FROM xx_order_item t1 WHERE t1.`orders` = t.`id`)-t.`promotion_discount`) FROM "
				+ " xx_order t WHERE (t.`order_status` = 1  OR (t.`order_status` = 2 AND t.`payment_status` = 2)) AND "
				+ " t.`member` = m.`id`) AS '商城订单总金额' , "
				+ " (SELECT COUNT(1) FROM task t WHERE t.`CREATEDATETIME` BETWEEN '2014-09-01' AND '2014-10-01' AND t.`STATUS` = 4 AND t.`CUSTOMERID` = c.`id`) AS '2014年9月成功上门任务数', "
				+ " (SELECT COUNT(1) FROM task t WHERE t.`CREATEDATETIME` BETWEEN '2014-10-01' AND '2014-11-01' AND t.`STATUS` = 4 AND t.`CUSTOMERID` = c.`id`) AS '2014年10月成功上门任务数', "
				+ " (SELECT COUNT(1) FROM task t WHERE t.`CREATEDATETIME` BETWEEN '2014-11-01' AND '2014-12-01' AND t.`STATUS` = 4 AND t.`CUSTOMERID` = c.`id`) AS '2014年11月成功上门任务数', "
				+ " (SELECT COUNT(1) FROM task t WHERE t.`CREATEDATETIME` BETWEEN '2014-12-01' AND '2015-01-01' AND t.`STATUS` = 4 AND t.`CUSTOMERID` = c.`id`) AS '2014年12月成功上门任务数', "
				+ " (SELECT COUNT(1) FROM task t WHERE t.`CREATEDATETIME` BETWEEN '2015-01-01' AND '2015-02-01' AND t.`STATUS` = 4 AND t.`CUSTOMERID` = c.`id`) AS '2015年1月成功上门任务数', "
				+ " (SELECT COUNT(1) FROM task t WHERE t.`CREATEDATETIME` BETWEEN '2015-02-01' AND '2015-03-01' AND t.`STATUS` = 4 AND t.`CUSTOMERID` = c.`id`) AS '2015年2月成功上门任务数', "
				+ " (SELECT COUNT(1) FROM task t WHERE t.`CREATEDATETIME` BETWEEN '2015-03-01' AND '2015-04-01' AND t.`STATUS` = 4 AND t.`CUSTOMERID` = c.`id`) AS '2015年3月成功上门任务数', "
				+ " (SELECT COUNT(1) FROM task t WHERE t.`CREATEDATETIME` BETWEEN '2015-04-01' AND '2015-05-01' AND t.`STATUS` = 4 AND t.`CUSTOMERID` = c.`id`) AS '2015年4月成功上门任务数', "
				+ " (SELECT COUNT(1) FROM task t WHERE t.`CREATEDATETIME` BETWEEN '2015-05-01' AND '2015-06-01' AND t.`STATUS` = 4 AND t.`CUSTOMERID` = c.`id`) AS '2015年5月成功上门任务数', "
				+ " (SELECT COUNT(1) FROM task t WHERE t.`CREATEDATETIME` BETWEEN '2015-06-01' AND '2015-07-01' AND t.`STATUS` = 4 AND t.`CUSTOMERID` = c.`id`) AS '2015年6月成功上门任务数', "
				+ " (SELECT COUNT(1) FROM task t WHERE t.`CREATEDATETIME` BETWEEN '2015-07-01' AND '2015-08-01' AND t.`STATUS` = 4 AND t.`CUSTOMERID` = c.`id`) AS '2015年7月成功上门任务数', "
				+ " (SELECT COUNT(1) FROM task t WHERE t.`CREATEDATETIME` BETWEEN '2015-08-01' AND '2015-09-01' AND t.`STATUS` = 4 AND t.`CUSTOMERID` = c.`id`) AS '2015年8月成功上门任务数', "
				+ " (SELECT COUNT(1) FROM task t WHERE t.`CREATEDATETIME` BETWEEN '2015-09-01' AND '2015-10-01' AND t.`STATUS` = 4 AND t.`CUSTOMERID` = c.`id`) AS '2015年9月成功上门任务数', "
				+ " (SELECT COUNT(1) FROM task t WHERE t.`CREATEDATETIME` BETWEEN '2015-10-01' AND '2015-11-01' AND t.`STATUS` = 4 AND t.`CUSTOMERID` = c.`id`) AS '2015年10月成功上门任务数', "
				+ " (SELECT COUNT(1) FROM task t WHERE t.`CREATEDATETIME` BETWEEN '2015-11-01' AND '2015-12-01' AND t.`STATUS` = 4 AND t.`CUSTOMERID` = c.`id`) AS '2015年11月成功上门任务数' "
				+ " FROM customer c, xx_member m WHERE c.phoneno = m.mobile ");
		if (phoneno != "") {
			sql.append(" AND c.phoneno='" + phoneno + "' ");
		}
		if (registertime_min != "" && registertime_max != "") {
			sql.append(" AND c.registertime BETWEEN '" + registertime_min
					+ "' AND '" + registertime_max + " 23:59:59' ");
		}
		sql.append(" ORDER BY c.registertime");
		return sql;
	}
}
