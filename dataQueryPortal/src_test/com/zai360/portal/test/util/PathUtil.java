package com.zai360.portal.test.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.zai360.portal.test.vo.Path;
/**
 * 存储左侧下拉栏信息
 * @author report
 *
 */
public class PathUtil {
	public static List<Path> PathCollection() {
		List<Path> list = new ArrayList<Path>();
//		Subject currentUser = SecurityUtils.getSubject();
//		currentUser.isPermitted("admin:report_comment"); //通过这个条件来生成不同的List条目
		list.add(new Path("appxdxx.jsp", "app下单信息", QueryEnum.APPXX.getId()));
		list.add(new Path("yhxx.jsp", "查询用户信息", QueryEnum.YHXX.getId()));
		list.add(new Path("dtxx.jsp", "单个地推信息", QueryEnum.DTXX.getId()));
		list.add(new Path("hsje.jsp", "每日回收金额", QueryEnum.HSJE.getId()));
		list.add(new Path("zcyh.jsp", "某时段注册用户数", QueryEnum.ZCYH.getId()));
		list.add(new Path("noOpenid.jsp", "注册无openID用户", QueryEnum.NOID.getId()));
		list.add(new Path("hsxx.jsp", "回收任务信息", QueryEnum.HSXX.getId()));
		list.add(new Path("tgrs.jsp", "基站推广人数", QueryEnum.TGRS.getId()));
		list.add(new Path("scsm.jsp", "基站首次上门数", QueryEnum.SCSM.getId()));
		list.add(new Path("dtzc.jsp", "地推注册明细", QueryEnum.DTZC.getId()));
		list.add(new Path("scyh.jsp", "未购买过商品用户", QueryEnum.SCYH.getId()));
		list.add(new Path("dddc.jsp", "订单查询", QueryEnum.DDDC.getId()));
		list.add(new Path("yxyh.jsp", "营销用户数", QueryEnum.YXYH.getId()));
		list.add(new Path("tyyh.jsp", "停用用户数", QueryEnum.TYYH.getId()));
		list.add(new Path("cfdz.jsp", "重复地址用户信息", QueryEnum.CFDZ.getId()));
		list.add(new Path("wyhq.jsp", "未发放优惠券用户", QueryEnum.WYHQ.getId()));
		list.add(new Path("wssj.jsp", "未完善时间用户", QueryEnum.WSSJ.getId()));
		list.add(new Path("ccrs.jsp", "各基站出差人数", QueryEnum.CCRS.getId()));
		list.add(new Path("scxd.jsp", "首次下单信息", QueryEnum.SCXD.getId()));
		list.add(new Path("xdmx.jsp", "首次下单商品明细", QueryEnum.XDMX.getId()));
		list.add(new Path("zcfw.jsp", "新用户服务设置", QueryEnum.ZCFW.getId()));
		list.add(new Path("dtfw.jsp", "地推用户服务设置", QueryEnum.DTFW.getId()));
		list.add(new Path("zhye.jsp", "所有账户总余额", QueryEnum.ZHYE.getId()));
		list.add(new Path("fwrq.jsp", "地推推荐服务日期", QueryEnum.FWRQ.getId()));
		list.add(new Path("scsy.jsp", "商城首次使用数", QueryEnum.SCSY.getId()));
		list.add(new Path("zxyy.jsp", "客户注销原因", QueryEnum.ZXYY.getId()));
		list.add(new Path("smwy.jsp", "首次上门违约用户", QueryEnum.SMWY.getId()));
		list.add(new Path("hssj.jsp", "回收手机任务信息", QueryEnum.HSSJ.getId()));
		list.add(new Path("tsgl.jsp", "用户投诉管理", QueryEnum.TSGL.getId()));
		list.add(new Path("smkh.jsp", "上门客户数", QueryEnum.SMKH.getId()));
		list.add(new Path("dhgw.jsp", "电话购物信息", QueryEnum.DHGW.getId()));		
		return list;
	}
	public static List<Path> ceshiList() {
		List<Path> list = new ArrayList<Path>();
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.isPermitted("admin:report_findappxdxx"); //通过这个条件来生成不同的List条目
		if(currentUser.isPermitted("admin:report_findappxdxx")){
			list.add(new Path("appxdxx.jsp", "app下单信息", QueryEnum.APPXX.getId()));
		}
		if(currentUser.isPermitted("admin:report_findyhxx")){
			list.add(new Path("yhxx.jsp", "查询用户信息", QueryEnum.YHXX.getId()));
		}
		if(currentUser.isPermitted("admin:report_finddtxx")){
			list.add(new Path("dtxx.jsp", "单个地推信息", QueryEnum.DTXX.getId()));
		}
		list.add(new Path("hsje.jsp", "每日回收金额", QueryEnum.HSJE.getId()));
		list.add(new Path("zcyh.jsp", "某时段注册用户数", QueryEnum.ZCYH.getId()));
		list.add(new Path("noOpenid.jsp", "注册无openID用户", QueryEnum.NOID.getId()));
		list.add(new Path("hsxx.jsp", "回收任务信息", QueryEnum.HSXX.getId()));
		list.add(new Path("tgrs.jsp", "基站推广人数", QueryEnum.TGRS.getId()));
		list.add(new Path("scsm.jsp", "基站首次上门数", QueryEnum.SCSM.getId()));
		list.add(new Path("dtzc.jsp", "地推注册明细", QueryEnum.DTZC.getId()));
		list.add(new Path("scyh.jsp", "未购买过商品用户", QueryEnum.SCYH.getId()));
		list.add(new Path("dddc.jsp", "订单查询", QueryEnum.DDDC.getId()));
		list.add(new Path("yxyh.jsp", "营销用户数", QueryEnum.YXYH.getId()));
		list.add(new Path("tyyh.jsp", "停用用户数", QueryEnum.TYYH.getId()));
		list.add(new Path("cfdz.jsp", "重复地址用户信息", QueryEnum.CFDZ.getId()));
		list.add(new Path("wyhq.jsp", "未发放优惠券用户", QueryEnum.WYHQ.getId()));
		list.add(new Path("wssj.jsp", "未完善时间用户", QueryEnum.WSSJ.getId()));
		list.add(new Path("ccrs.jsp", "各基站出差人数", QueryEnum.CCRS.getId()));
		list.add(new Path("scxd.jsp", "首次下单信息", QueryEnum.SCXD.getId()));
		list.add(new Path("xdmx.jsp", "首次下单商品明细", QueryEnum.XDMX.getId()));
		list.add(new Path("zcfw.jsp", "新用户服务设置", QueryEnum.ZCFW.getId()));
		list.add(new Path("dtfw.jsp", "地推用户服务设置", QueryEnum.DTFW.getId()));
		list.add(new Path("zhye.jsp", "所有账户总余额", QueryEnum.ZHYE.getId()));
		list.add(new Path("fwrq.jsp", "地推推荐服务日期", QueryEnum.FWRQ.getId()));
		list.add(new Path("scsy.jsp", "商城首次使用数", QueryEnum.SCSY.getId()));
		list.add(new Path("zxyy.jsp", "客户注销原因", QueryEnum.ZXYY.getId()));
		list.add(new Path("smwy.jsp", "首次上门违约用户", QueryEnum.SMWY.getId()));
		list.add(new Path("hssj.jsp", "回收手机任务信息", QueryEnum.HSSJ.getId()));
		list.add(new Path("tsgl.jsp", "用户投诉管理", QueryEnum.TSGL.getId()));
		list.add(new Path("smkh.jsp", "上门客户数", QueryEnum.SMKH.getId()));
		list.add(new Path("dhgw.jsp", "电话购物信息", QueryEnum.DHGW.getId()));		
		return list;
	}
}
