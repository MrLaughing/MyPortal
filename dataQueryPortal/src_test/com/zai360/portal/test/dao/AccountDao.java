package com.zai360.portal.test.dao;

import java.util.HashMap;
import java.util.List;

import com.zai360.portal.test.commons.BaseDao;
import com.zai360.portal.test.vo.Admin;
import com.zai360.portal.test.vo.Authority;
import com.zai360.portal.test.vo.Role;
/**
 * 
 * @author report
 *
 */
public interface AccountDao extends BaseDao{
	/**
	 * 通过用户名获取账户对象
	 * @param loginname
	 * @return
	 */
	Admin findAdmin(String username);
	/**
	 * 通过用户名获取角色
	 * @param syroleId
	 * @return
	 */
	List<Role> findRole(String username);
	/**
	 * 通过用户名获取账户对象
	 * @param loginname
	 * @return
	 */
	Role findRoleByRoleName(String name);
	/**
	 * 通过角色名获取权限对象
	 * @param name
	 * @return
	 */
	List<Authority> findAuthority(String name);
	/**
	 * 通过权限名获取权限对象
	 * @param name
	 * @return
	 */
	Authority findrealAuthority(String name);
	
	/**
	 * 查询总用户数
	 * @param sql
	 * @return
	 */
	int findTotal(StringBuffer sql);
	/**
	 * 根据页码和记录数分页查询
	 * @return
	 */
	List<HashMap<String,Object>> getList(String mappermethod, StringBuffer sql);
	/**
	 * 用户注册--添加用户
	 * @param sql
	 */
	void insertAccount(StringBuffer sql);
	/**
	 * 删除用户
	 * @param sql
	 */
	void deleteAccount(StringBuffer sql);
	/**
	 * 更新用户信息
	 * @param sql
	 */
	void updateAccount(StringBuffer sql);
	/**
	 * 添加角色
	 * @param sql
	 */
	void addRole(StringBuffer sql);
	/**
	 * 通过基本角色id获取同权限的其他额外角色id
	 * @param sql
	 * @return
	 */
	List<String> findAuthorityById(StringBuffer sql);
	/**
	 * 删除用户原有角色
	 * @param sql
	 */
	void deleteRole(StringBuffer sql);
	/**
	 * 重新添加用户角色
	 * @param sql
	 */
	void insertRole(StringBuffer sql);
	/**
	 * 删除角色原有权限
	 * @param sql
	 */
	void deleteAuthority(StringBuffer sql);
	/**
	 * 重新添加角色权限
	 * @param sql
	 */
	void insertAuthority(StringBuffer sql);
	/**
	 * （真！）更新角色信息
	 * @param sql
	 */
	void realUpdateRole(StringBuffer sql);
	/**
	 * 查询出所有角色
	 * @param sql
	 * @return
	 */
	List<HashMap<String,Object>> findallRoles(StringBuffer sql);
	/**
	 * 删除（真）用户
	 * @param sql
	 */
	void deleteRealRole(StringBuffer sql);
	/**
	 * 更新权限信息
	 * @param sql
	 */
	void updaterealAuthority(StringBuffer sql);
	/**
	 * 添加单条权限
	 * @param sql
	 */
	void insertrealAuthority(StringBuffer sql);
	/**
	 * 查询出所有权限
	 * @param sql
	 * @return
	 */
	List<HashMap<String,Object>> findallAuthorities(StringBuffer sql);
}
