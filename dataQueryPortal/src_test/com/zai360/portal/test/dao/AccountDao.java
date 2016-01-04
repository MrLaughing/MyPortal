package com.zai360.portal.test.dao;

import java.util.List;

import com.zai360.portal.test.commons.BaseDao;
import com.zai360.portal.test.vo.Admin;
import com.zai360.portal.test.vo.Role;
import com.zai360.portal.test.vo.Role_authority;
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
	 * 通过角色名获取权限
	 * @param syresourceId
	 * @return
	 */
	List<Role_authority> findAuthority(String name);
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
	List getList(String mappermethod, StringBuffer sql);
	/**
	 * 更新用户信息
	 * @param sql
	 */
	void updateAccount(StringBuffer sql);
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
}
