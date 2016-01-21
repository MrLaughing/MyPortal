package com.zai360.portal.test.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.zai360.portal.test.commons.BaseDaoImpl;
import com.zai360.portal.test.dao.AccountDao;
import com.zai360.portal.test.util.SQLAdapter;
import com.zai360.portal.test.vo.Admin;
import com.zai360.portal.test.vo.Role;
import com.zai360.portal.test.vo.Role_authority;
@Repository
public class AccountDaoImpl extends BaseDaoImpl implements
		AccountDao {

	@Override
	public Admin findAdmin(String username) {
		Admin admin = this.getSqlSession().selectOne("account.findAdmin",
				username);
		return admin;
	}

	@Override
	public List<Role> findRole(String username) {
		List<Role> list= this.getSqlSession().selectList("account.findRole",
				username);
		return list;
	}

	@Override
	public List<Role_authority> findAuthority(String name) {
		List<Role_authority> authority = this.getSqlSession().selectList("account.findAuthority", name);
		return authority;
	}

	@Override
	public List<HashMap<String,Object>> getList(String mappermethod, StringBuffer sql) {
		List<HashMap<String,Object>> list=this.getSqlSession().selectList(mappermethod,new SQLAdapter(sql.toString()));
		return list;
	}

	@Override
	public int findTotal(StringBuffer sql) {
		int total=this.getSqlSession().selectOne("account.findTotal", new SQLAdapter(sql.toString()));
		return total;
	}

	@Override
	public void updateAccount(StringBuffer sql) {
		this.getSqlSession().update("account.updateAccount",new SQLAdapter(sql.toString()));
	}

	@Override
	public void insertRole(StringBuffer sql) {
		this.getSqlSession().insert("account.insertRole", new SQLAdapter(sql.toString()));
	}

	@Override
	public void deleteRole(StringBuffer sql) {
		this.getSqlSession().delete("account.deleteRole", new SQLAdapter(sql.toString()));
	}

	@Override
	public Role findRoleByRoleName(String name) {
		return this.getSqlSession().selectOne("account.findRoleByRoleName", name);
	}

	@Override
	public void deleteAuthority(StringBuffer sql) {
		this.getSqlSession().delete("account.deleteAuthority", new SQLAdapter(sql.toString()));
	}

	@Override
	public void insertAuthority(StringBuffer sql) {
		this.getSqlSession().insert("account.insertAuthority", new SQLAdapter(sql.toString()));
	}

	@Override
	public List<String> findAuthorityById(StringBuffer sql) {
		return this.getSqlSession().selectList("account.findAuthorityById", new SQLAdapter(sql.toString()));
	}

	@Override
	public void insertAccount(StringBuffer sql) {
		this.getSqlSession().insert("account.insertAccount", new SQLAdapter(sql.toString()));
	}

	@Override
	public void deleteAccount(StringBuffer sql) {
		this.getSqlSession().delete("account.deleteAccount", new SQLAdapter(sql.toString()));
	}
}
