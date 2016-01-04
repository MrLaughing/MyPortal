package com.zai360.portal.test.util;
/**
 * 存放关于导出excel相关属性
 * @author report
 *
 */
public enum QueryEnum {
	APPXX("1", "common.findappxdxx",
			new String[] { "订单", "总金额", "客户端", "手机号" }, "appxdxx.xls",
			new String[] { "订单", "总金额", "客户端", "手机号" }, 4, "app下单信息表",
			new Integer[] { 160, 100, 100, 120 }), 
	DTXX("3", "common.finddtxx",
			new String[] { "手机号", "注册时间", "下单数", "完成订单数" }, "dtxx.xls",
			new String[] { "手机号", "注册时间", "下单数", "完成订单数" }, 4, "地推信息表",
			new Integer[] { 100, 160, 80, 100 }), 
	DDDC("12","common.finddddc",
			new String[] { "订单编号", "商品编号", "商品名称", "订单数量", "商品价格",
					"总金额", "已付金额", "在线支付", "账户抵用", "收货人", "手机号", "基站", "地址", "邮编",
					"是否开具发票", "发票抬头", "支付方式", "配送方式", "订单状态", "支付状态", "配送状态",
					"确认收货", "创建订单", "确认订单", "配送订单", "完成订单", "取消订单" }, "dddc.xls",
			new String[] { "订单编号", "商品编号", "商品名称", "订单数量", "商品价格",
					"总金额", "已付金额", "在线支付", "账户抵用", "收货人", "手机号", "基站", "地址", "邮编",
					"是否开具发票", "发票抬头", "支付方式", "配送方式", "订单状态", "支付状态", "配送状态",
					"确认收货", "创建订单", "确认订单", "配送订单", "完成订单", "取消订单" }, 27, "订单信息",
			new Integer[] { 120, 100, 360, 80, 80, 80, 80, 80, 80,
					100, 100, 120, 360, 100, 120, 220, 150, 150, 100, 100, 100,
					200, 200, 200, 200, 200, 200 }), 
	CFDZ("15", "common.findcfdz",
			new String[] { "手机号", "地址", "小区名称", "小区编号", "楼宇", "楼宇号", "门牌号",
					"商城使用次数", "上门回收次数", "推荐人", "注册时间", "重复数量" }, "cfdz.xls",
			new String[] { "手机号", "地址", "小区名称", "小区编号", "楼宇", "楼宇号", "门牌号",
					"商城使用次数", "上门回收次数", "推荐人", "注册时间", "重复数量" }, 12,
			"重复地址用户信息表", 
			new Integer[] { 100, 360, 200, 120, 160, 160, 120,
					120, 120, 120, 160, 120 }),
	CCRS("18","common.findccrs", 
			new String[] { "日期", "基站名称", "凌晨0点-下午5点", "下午5点-凌晨0点" }, "ccrs.xls", 
			new String[] { "日期", "基站名称", "凌晨0点-下午5点", "下午5点-凌晨0点" }, 4, "各基站出差人数表", 
			new Integer[] { 100, 100, 120, 120 }), 
	DTFW("22", "common.finddtfw",
			new String[] { "手机号", "服务周期", "服务日期", "服务时间段", "注册时间",
					"推荐人", "用户状态", "用户来源", "回收次数", "商城使用次数", "所属基站", "用户地址",
					"注册当天是否有临时任务", "是否开启了出差" }, "dtfw.xls",
			new String[] { "手机号", "服务周期", "服务日期", "服务时间段", "注册时间",
					"推荐人", "用户状态", "用户来源", "回收次数", "商城使用次数", "所属基站", "用户地址",
					"注册当天是否有临时任务", "是否开启了出差" }, 14, "地推用户服务信息表",
			new Integer[] { 100, 120, 120, 120, 160, 100, 100,
					120, 100, 120, 120, 360, 160, 120 }), 
	DTZC("10", "common.finddtzc",
			new String[] { "手机号", "注册时间", "地推姓名", "地推工号", "基站名称",
					"地址", "状态" }, "dtzc.xls",
			new String[] { "手机号", "注册时间", "地推姓名", "地推工号", "基站名称",
					"地址", "状态" }, 7, "地推注册明细表",
			new Integer[] { 100, 160, 100, 80, 120, 360, 90 }), 
	FWRQ("24", "common.findfwrq",
			new String[] { "周一", "周二", "周三", "周四", "周五", "周六", "周日",
			"基站" }, "fwrq.xls",
			new String[] { "周一", "周二", "周三", "周四", "周五", "周六", "周日",
			"基站" }, 8, "每周服务日期表",
			new Integer[] { 80, 80, 80, 80, 80, 80, 80, 120 }), 
	HSJE("4", "common.findhsje",
			new String[] { "手机号", "任务ID", "回收金额", "完成时间", "再生侠" }, "hsje.xls",
			new String[] { "手机号", "任务ID", "回收金额", "完成时间", "再生侠" }, 5, "每日回收金额表",
			new Integer[] { 120, 260, 100, 160, 120 }), 
	HSXX("7", "common.findhsxx",
			new String[] { "任务ID", "再生侠", "订单接受日期","回收类型", "回收总金额", "回收袋子金额",
					"回收瓶子金额", "回收衣服金额", "回收电子金额", "回收废纸金额" }, "hsxx.xls",
			new String[] { "任务ID", "再生侠", "订单接受日期","回收类型", "回收总金额", "回收袋子金额",
					"回收瓶子金额", "回收衣服金额", "回收电子金额", "回收废纸金额" }, 10, "回收任务信息表",
			new Integer[] { 260, 120, 160, 100, 100, 120, 120, 120, 120, 120 }), 
	NOID("6", "common.findnoOpenid",
			new String[] { "手机号", "地址", "姓名", "注册时间", "推荐人" }, "noOpenid.xls",
			new String[] { "手机号", "地址", "姓名", "注册时间", "推荐人" }, 5, "注册但无openid的用户表",
			new Integer[] { 100, 360, 80, 160, 80 }), 
	SCSM("9", "common.findscsm",
			new String[] { "首次上门数", "基站" }, "scsm.xls",
			new String[] { "首次上门数", "基站" }, 2, "各基站首次上门数表",
			new Integer[] { 100, 100 }), 
	SCSY("25", "common.findscsy",
			new String[] { "地推人员", "推荐用户商城首次使用数" }, "scsy.xls",
			new String[] { "地推人员", "推荐用户商城首次使用数" }, 2, "地推推荐用户商城首次使用数表",
			new Integer[] { 120, 200 }), 
	SCXD("19", "common.findscxd",
			new String[] { "订单编号", "订单总金额", "收货人", "手机号", "地址",
					"所属基站", "完成时间" }, "scxd.xls",
			new String[] { "订单编号", "订单总金额", "收货人", "手机号", "地址",
					"所属基站", "完成时间" }, 7, "首次下单信息",
			new Integer[] { 120, 100, 120, 120, 360, 100, 160 }), 
	SCYH("11", "common.findscyh",
			new String[] { "手机号", "当前余额", "地址", "回收次数", "注册时间" }, "scyh.xls",
			new String[] { "手机号", "当前余额", "地址", "回收次数", "注册时间" }, 5, "未购买过商品的用户信息表",
			new Integer[] { 100, 100, 360, 80, 160 }), 
	SMWY("27", "common.findsmwy",
			new String[] { "手机号", "客户地址", "原因", "所属基站" }, "smwy.xls",
			new String[] { "手机号", "客户地址", "原因", "所属基站" }, 4, "首次上门违约用户信息表",
			new Integer[] { 110, 360, 360, 120 }), 
	TGRS("8", "common.findtgrs",
			new String[] { "推广人数", "基站" }, "tgrs.xls",
			new String[] { "推广人数", "基站" }, 2, "各基站推广人数表",
			new Integer[] { 100, 100 }), 
	TYYH("14", "common.findtyyh",
			new String[] { "手机号", "推广人员工号", "注册时间", "预约上门时间",
					"服务再生侠", "违约原因", "所属基站", "回收次数", "用户地址", "暂停服务操作人", "成功回收次数" }, "tyyh.xls",
			new String[] { "手机号", "推广人员工号", "注册时间", "预约上门时间",
					"服务再生侠", "违约原因", "所属基站", "回收次数", "用户地址", "暂停服务操作人", "成功回收次数" }, 11, "停用用户信息表",
			new Integer[] { 100, 120, 150, 150, 100, 360, 150, 80,
					360, 130, 120 }), 
	WSSJ("17", "common.findwssj",
			new String[] { "手机号", "地址", "基站名称", "回收次数", "再生侠编号", "余额" }, "wssj.xls",
			new String[] { "手机号", "地址", "基站名称", "回收次数", "再生侠编号", "余额" }, 6, "未完善回收时间用户表",
			new Integer[] { 120, 360, 160, 80, 120, 120 }), 
	WYHQ("16", "common.findwyhq",
			new String[] { "手机号", "用户名", }, "wyhq.xls",
			new String[] { "手机号", "用户名", }, 2, "未发放优惠券用户表",
			new Integer[] { 120, 80 }), 
	XDMX("20", "common.findxdmx",
			new String[] { "订单编号", "手机号", "下单时间", "订单总金额", "账户抵用金额",
					"当前余额", "商品编号", "商品名称", "商品原价", "商品价格", "数量" }, "xdmx.xls",
			new String[] { "订单编号", "手机号", "下单时间", "订单总金额", "账户抵用金额",
					"当前余额", "商品编号", "商品名称", "商品原价", "商品价格", "数量" }, 11, "首次下单明细",
			new Integer[] { 120, 100, 160, 120, 120, 100, 100, 360,
					100, 100, 80 }), 			
	YHXX("2", "common.findyhxx",
			new String[] { "手机号", "用户状态", "暂停服务时间", "账户余额", "注册时间",
					"上门回收次数", "商城使用次数", "违约次数", "商品拒收次数", "首次上门服务时间", "首次商城下单时间",
					"最近上门服务时间", "最近商城下单时间", "商城订单总金额", "2014年9月成功上门任务数",
					"2014年10月成功上门任务数", "2014年11月成功上门任务数", "2014年12月成功上门任务数",
					"2015年01月成功上门任务数", "2015年02月成功上门任务数", "2015年03月成功上门任务数",
					"2015年04月成功上门任务数", "2015年05月成功上门任务数", "2015年06月成功上门任务数",
					"2015年07月成功上门任务数", "2015年08月成功上门任务数", "2015年09月成功上门任务数",
					"2015年10月成功上门任务数", "2015年11月成功上门任务数", }, "yhxx.xls",
			new String[] { "手机号", "用户状态", "暂停服务时间", "账户余额", "注册时间",
					"上门回收次数", "商城使用次数", "违约次数", "商品拒收次数", "首次上门服务时间", "首次商城下单时间",
					"最近上门服务时间", "最近商城下单时间", "商城订单总金额", "2014年9月成功上门任务数",
					"2014年10月成功上门任务数", "2014年11月成功上门任务数", "2014年12月成功上门任务数",
					"2015年01月成功上门任务数", "2015年02月成功上门任务数", "2015年03月成功上门任务数",
					"2015年04月成功上门任务数", "2015年05月成功上门任务数", "2015年06月成功上门任务数",
					"2015年07月成功上门任务数", "2015年08月成功上门任务数", "2015年09月成功上门任务数",
					"2015年10月成功上门任务数", "2015年11月成功上门任务数", }, 29, "用户信息表",
			new Integer[] { 100, 160, 160, 100, 160, 120, 120, 100,
					120, 160, 160, 160, 160, 120, 200, 200, 200, 200, 200, 200,
					200, 200, 200, 200, 200, 200, 200, 200, 200, }),
	YXYH("13", "common.findyxyh",
			new String[] { "基站名称", "再生侠编号", "再生侠名称", "营销客户数" }, "yxyh.xls",
			new String[] { "基站名称", "再生侠编号", "再生侠名称", "营销客户数" }, 4, "营销用户数表",
			new Integer[] { 120, 100, 100, 100 }),
	ZCFW("21", "common.findzcfw",
			new String[] { "手机号", "服务周期", "服务日期", "服务时间段", "注册时间", "推荐人",
					"用户状态", "用户来源", "回收次数", "商城使用次数", "所属基站", "用户地址",
					"注册当天是否有临时任务", "是否开启了出差" }, "zcfw.xls",
			new String[] { "手机号", "服务周期", "服务日期", "服务时间段", "注册时间", "推荐人",
					"用户状态", "用户来源", "回收次数", "商城使用次数", "所属基站", "用户地址",
					"注册当天是否有临时任务", "是否开启了出差" }, 14, "新注册用户服务信息表",
			new Integer[] { 100, 120, 120, 120, 160, 100, 100,
					120, 100, 120, 120, 360, 160, 120 }),
	ZCYH("5", "common.findzcyh",
			new String[] { "时间段", "注册人数" }, "zcyh.xls",
			new String[] { "时间段", "注册人数" }, 2, "注册用户数",
			new Integer[] { 120, 100 }),
	ZHYE("23", "common.findzhye",
			new String[] { "账户总余额" }, "zhye.xls",
			new String[] { "账户总余额" }, 1, "账户总余额表",
			new Integer[] { 120 }),
	ZXYY("26", "common.findzxyy",
			new String[] { "手机号", "客户地址", "注销时间", "注销原因", "所属基站" }, "zxyy.xls",
			new String[] { "手机号", "客户地址", "注销时间", "注销原因", "所属基站" }, 5, "客户注销原因表",
			new Integer[] { 120, 360, 160, 360, 120 }),
	HSSJ("28","common.findhssj",
			new String[]{"基站","回收二手智能机数量","手机号","订单状态","创建日期","完成时间"},"hssj.xls",
			new String[]{"基站","回收二手智能机数量","手机号","订单状态","创建日期","完成时间"},6,"回收二手手机数量",
			new Integer[] { 120, 150, 120, 120, 160, 160}),
	TSGL("29","common.findtsgl",
			new String[]{"手机号","类型","投诉时间","投诉内容","处理时间","处理结果","状态","操作人","所属基站"},"tsgl.xls",
			new String[]{"手机号","类型","投诉时间","投诉内容","处理时间","处理结果","状态","操作人","所属基站"},9,"用户投诉管理",
			new Integer[] { 120, 80, 160, 360, 160, 360, 80, 100, 100}),
	SMKH("30","common.findsmkh",
			new String[]{"上门客户数"},"smkh.xls",
			new String[]{"上门客户数"},1,"上门客户数",
			new Integer[] { 100}),
	DHGW("31","common.finddhgw",
			new String[]{"手机号","所属基站","注册时间","回收次数","商城使用次数","通过电话购物成功下单次数",
			"通过电话购物下单总金额"},"dhgw.xls",
			new String[]{"手机号","所属基站","注册时间","回收次数","商城使用次数","通过电话购物成功下单次数",
			"通过电话购物下单总金额"},7,"电话购物信息",
			new Integer[] { 120, 120, 160, 100, 100, 220, 220});
	

	/************************************/
	String id;//每个查询的唯一ID标识
	String mappermethod;//mapper中对应的selectname
	String[] columns;//sql查询结果对应的列名
	String filename;//excel名称
	String[] titles;//excel中标题行
	int length;//长度
	String sheetName;//表名
	Integer[] widths;//列宽

	private QueryEnum(String id, String mappermethod, String[] columns,
			String filename, String[] titles, int length, String sheetName,
			Integer[] widths) {
		this.id = id;
		this.mappermethod = mappermethod;
		this.columns = columns;
		this.filename = filename;
		this.titles = titles;
		this.length = length;
		this.sheetName = sheetName;
		this.widths = widths;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMappermethod() {
		return mappermethod;
	}

	public void setMappermethod(String mappermethod) {
		this.mappermethod = mappermethod;
	}

	public String[] getColumns() {
		return columns;
	}

	public void setColumns(String[] columns) {
		this.columns = columns;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String[] getTitles() {
		return titles;
	}

	public void setTitles(String[] titles) {
		this.titles = titles;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public Integer[] getWidths() {
		return widths;
	}

	public void setWidths(Integer[] widths) {
		this.widths = widths;
	}

}
