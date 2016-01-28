package com.zai360.portal.test.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.env.WebEnvironment;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.util.WebUtils;

import com.zai360.portal.test.vo.Path;

/**
 * 存储左侧下拉栏信息
 * 
 * @author report
 *
 */
public class PathUtil {
	/**
	 * 回收人员权限
	 * 
	 * @return
	 */
	public static List<Path> huishouList() {
		List<Path> huishouList = new ArrayList<Path>();
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser.isPermitted("admin:report_findccrs")){
			huishouList.add(new Path("ccrs.jsp", "各基站出差人数", QueryEnum.CCRS.getId()));
		}
		if(currentUser.isPermitted("admin:report_findsmwy")){
			huishouList.add(new Path("smwy.jsp", "首次上门违约用户", QueryEnum.SMWY.getId()));
		}
		return huishouList;
	}

	/**
	 * 客服人员权限
	 * 
	 * @return
	 */
	public static List<Path> kefuList() {
		List<Path> kefuList = new ArrayList<Path>();
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser.isPermitted("admin:report_findtyyh")){
			kefuList.add(new Path("tyyh.jsp", "停用用户数", QueryEnum.TYYH.getId()));
		}
		if(currentUser.isPermitted("admin:report_finddhgw")){
			kefuList.add(new Path("dhgw.jsp", "电话购物信息", QueryEnum.DHGW.getId()));
		}
		return kefuList;
	}

	/**
	 * 商城人员权限
	 * 
	 * @return
	 */
	public static List<Path> shangchengList() {
		List<Path> shangchengList = new ArrayList<Path>();
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser.isPermitted("admin:report_findappxdxx")){
			shangchengList.add(new Path("appxdxx.jsp", "app下单信息", QueryEnum.APPXX.getId()));
			
		}
		if(currentUser.isPermitted("admin:report_findwyhq")){
			shangchengList.add(new Path("wyhq.jsp", "未发放优惠券用户", QueryEnum.WYHQ.getId()));
		}
		return shangchengList;
	}

	/**
	 * 市场人员权限
	 * 
	 * @return
	 */
	public static List<Path> shichangList() {
		List<Path> shichangList = new ArrayList<Path>();
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser.isPermitted("admin:report_findtgrs")){
			shichangList.add(new Path("tgrs.jsp", "基站推广人数", QueryEnum.TGRS.getId()));
		}
		return shichangList;
	}

	/**
	 * 推广人员权限
	 * 
	 * @return
	 */
	public static List<Path> tuiguangList() {
		List<Path> tuiguangList = new ArrayList<Path>();
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser.isPermitted("admin:report_finddtzc")){
			tuiguangList.add(new Path("dtzc.jsp", "地推注册明细", QueryEnum.DTZC
			.getId()));
		}
		if(currentUser.isPermitted("admin:report_findfwrq")){
			tuiguangList.add(new Path("fwrq.jsp", "地推推荐服务日期", QueryEnum.FWRQ
			.getId()));
		}
		return tuiguangList;
	}

	/**
	 * 交互查询权限
	 * 
	 * @return
	 */
	public static List<Path> jiaohuList() {
		List<Path> jiaohuList = new ArrayList<Path>();
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.isPermitted("admin:report_findyhxx")) {
			jiaohuList.add(new Path("yhxx.jsp", "查询用户信息", QueryEnum.YHXX
					.getId()));
		}
		if (currentUser.isPermitted("admin:report_findcfdz")) {
			jiaohuList.add(new Path("cfdz.jsp", "重复地址用户信息", QueryEnum.CFDZ
					.getId()));
		}
		if (currentUser.isPermitted("admin:report_findzxyy")) {
			jiaohuList.add(new Path("zxyy.jsp", "客户注销原因", QueryEnum.ZXYY
					.getId()));
		}
		return jiaohuList;
	}
	public static List<Path> PathCollection() {
		List<Path> list = new ArrayList<Path>();
		Subject currentSubject = SecurityUtils.getSubject();
		boolean flag = currentSubject.hasRole("");
		currentSubject.isPermitted("");
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
}
