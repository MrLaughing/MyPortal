package com.zai360.portal.test.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

/**
 * 用户相关SQL
 * 
 * @author report
 *
 */
public class Sql4Account {
	/**
	 * 登录后更新用户的登录IP和登录时间
	 * 
	 * @return
	 */
	public static StringBuffer updateAdmin(String username, String login_ip,
			String login_date) {
		HttpServletRequest request = ServletActionContext.getRequest();

		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE xx_admin_report a SET a.`login_ip`='" + login_ip
				+ "',a.`login_date`='" + login_date + "' WHERE a.`username`='"
				+ username + "';");
		return sql;
	}
	/**
	 * 修改密码
	 * 
	 * @return
	 */
	public static StringBuffer updateAdminpwd(String username, String newpwd) {
		HttpServletRequest request = ServletActionContext.getRequest();

		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE xx_admin_report a SET a.`password`='" + newpwd
				+ "' WHERE a.`username`='"+ username + "';");
		return sql;
	}
	/**
	 * 查询用户条目数
	 * 
	 * @return
	 */
	public static StringBuffer findAccountsTotal() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		String department = request.getParameter("department");
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM xx_admin_report a ");
		if (username == "" && department == "") {

		} else if (username != "" && department == "") {
			sql.append(" WHERE a.`username`='" + username + "' ");
		} else if (username == "" && department != "") {
			sql.append(" WHERE a.`department`='" + department + "' ");
		} else if (username != "" && department != "") {
			sql.append(" WHERE a.`department`='" + department
					+ "' AND a.`username`='" + username + "' ");
		}
		return sql;
	}

	/**
	 * 按条件查询用户
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public static StringBuffer findAccounts(int pageNumber, int pageSize) {
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		String department = request.getParameter("department");
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM xx_admin_report a ");
		if (username == "" && department == "") {

		} else if (username != "" && department == "") {
			sql.append(" WHERE a.`username`='" + username + "' ");
		} else if (username == "" && department != "") {
			sql.append(" WHERE a.`department`='" + department + "' ");
		} else if (username != "" && department != "") {
			sql.append(" WHERE a.`department`='" + department
					+ "' AND a.`username`='" + username + "' ");
		}
		sql.append(" LIMIT " + (pageNumber - 1) * pageSize + "," + pageSize);
		return sql;
	}
	/**
	 * 用户注册--添加用户SQL
	 * @return
	 */
	public static StringBuffer insertAccount() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		String name = request.getParameter("name");
		String department = request.getParameter("department");
		String email = request.getParameter("email");
		String password = Md5Util.toMd5(request.getParameter("password"));// 密码需要MD5加密
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");																	// 字母小写
		String create_date = sdf.format(new Date());
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO xx_admin_report(username,name,email,department,password,create_date) VALUES('"
				+ username+ "','"+ name+ "','"+ email+ "','"+ department+ "','"+password+"','"+create_date+"')");
		return sql;
	}

	/**
	 * 更新用户信息
	 * 
	 * @return
	 */
	public static StringBuffer updateAccount() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("username");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String department = request.getParameter("department");
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String modify_date = sdf.format(new Date());
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE xx_admin_report a SET a.`name`='" + name
				+ "',a.`department`='" + department + "',a.`email`='" + email
				+ "',a.`modify_date`='"+modify_date+"' WHERE a.`username`='" + username + "';");
		return sql;
	}
	public static StringBuffer deleteAccount(String username){
		StringBuffer sql=new StringBuffer();
		sql.append("DELETE FROM xx_admin_report WHERE username='"+username+"'");
		return sql;
		
	}
	/**
	 * 查询角色条目数
	 * 
	 * @return
	 */
	public static StringBuffer findRolesTotal() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String type = request.getParameter("type");
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM xx_role_report r ");
		if (!"".equals(type)&&type!=null) {
			sql.append(" WHERE r.`type`='"+type+"' ");
		}
		return sql;
	}

	/**
	 * 按条件查询角色
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public static StringBuffer findRoles(int pageNumber, int pageSize) {
		HttpServletRequest request = ServletActionContext.getRequest();
		String type = request.getParameter("type");
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM xx_role_report r ");
		if (!"".equals(type)&&type!=null) {
			sql.append(" WHERE r.`type`='"+type+"' ");
		}
		sql.append(" LIMIT " + (pageNumber - 1) * pageSize + "," + pageSize);
		return sql;
	}
	/**
	 * 添加角色
	 * @return
	 */
	public static StringBuffer addRole(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String description = request.getParameter("description");
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String create_date = sdf.format(new Date());
		String modify_date = sdf.format(new Date());
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO xx_role_report(create_date,modify_date,"
				+ "description,NAME,TYPE) VALUES('"+create_date+"','"+modify_date
				+"','"+description+"','"+name+"','"+type+"')");
		return sql;
	}
	/**
	 * （真！）更新用户信息
	 * @return
	 */
	public static StringBuffer realUpdateRole(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String name = request.getParameter("name");
		String type =request.getParameter("type");
		String description=request.getParameter("description");
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String modify_date = sdf.format(new Date());
		StringBuffer sql =new StringBuffer();
		sql.append("UPDATE xx_role_report r SET r.`description`='"+description
				+"', r.`type`='"+type+"', r.`modify_date`='"+modify_date+"' WHERE r.`name`='"+name+"' ");
		return sql;
	}
	/**
	 * 获取所有角色
	 * @return
	 */
	public static StringBuffer findallRoles(){
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT * FROM xx_role_report r ");
		return sql;
	}
	
	/**
	 * 删除用户原有角色
	 * 
	 * @return
	 */
	public static StringBuffer deleteRole(Long id) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM xx_admin_role_report WHERE admins='" + id + "'");
		return sql;
	}

	/**
	 * 重新添加用户角色
	 * 
	 * @return
	 */
	public static StringBuffer insertRole(Long id) {
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] roles = request.getParameterValues("roles");
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO xx_admin_role_report(admins,roles) VALUES");
		StringBuffer role_str = new StringBuffer();
		if (roles != null) {
			if (roles.length > 0 & roles[0] != "") {
				for (int i = 0; i < roles.length; i++) {
					if (i == roles.length - 1) {
						role_str.append("(" + id + "," + roles[i] + ")");
					} else {
						role_str.append("(" + id + "," + roles[i] + "),");
					}
				}
				sql.append(role_str);
			} else {
				return null;// 不添加任何角色 则跳转页面
			}
		} else {
			return null;// 不添加任何角色 则跳转页面
		}
		return sql;
	}

	/**
	 * 查询角色权限条目数
	 * 
	 * @return
	 */
	public static StringBuffer findAuthoritiesTotal() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM xx_role_report r LEFT JOIN xx_role_authority_report ra "
				+ " ON r.`id` = ra.`role` ");
		return sql;
	}

	/**
	 * 查询角色权限信息
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public static StringBuffer findAuthorities(int pageNumber, int pageSize) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT r.`name` AS '角色', r.`description` AS '角色描述', ra.`authorities` "
				+ " AS '权限' FROM xx_role_report r LEFT JOIN xx_role_authority_report ra ON r.`id` = ra.`role`");
		sql.append(" LIMIT " + (pageNumber - 1) * pageSize + "," + pageSize);
		return sql;
	}

	/**
	 * 删除角色原有权限
	 * 
	 * @return
	 */
	public static StringBuffer deleteAuthority(Long id) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM xx_role_authority_report WHERE role='" + id
				+ "' AND authorities LIKE 'admin:report_find%';");
		return sql;
	}

	/**
	 * 重新添加角色权限
	 * 
	 * @return
	 */
	public static StringBuffer insertAuthority(Long id) {
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] authorities = request.getParameterValues("authorities");
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO xx_role_authority_report(role,authorities) VALUES");
		StringBuffer authority_str = new StringBuffer();
		if (authorities != null) {
			if (authorities.length > 0 & authorities[0] != "") {
				for (int i = 0; i < authorities.length; i++) {
					if (i == authorities.length - 1) {
						authority_str.append("(" + id + ",'" + authorities[i]
								+ "')");
					} else {
						authority_str.append("(" + id + ",'" + authorities[i]
								+ "'),");
					}
				}
				sql.append(authority_str);
			} else {
				return null;// 不添加任何角色 则跳转页面
			}
		} else {
			return null;// 不添加任何角色 则跳转页面
		}
		return sql;
	}

	/**
	 * 通过基本角色id获取同权限的其他额外角色id SQL
	 * 
	 * @param id
	 * @return
	 */
	public static StringBuffer findAuthorityById(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ra.`role` FROM xx_role_authority_report ra ,xx_role_report r WHERE ra.`authorities` "
				+ " IN (SELECT ra.`authorities` FROM xx_role_authority_report ra WHERE "
				+ " ra.`role`='"
				+ id
				+ "') AND ra.`role`=r.`id` AND r.`type`='额外角色'");
		return sql;
	}
}
