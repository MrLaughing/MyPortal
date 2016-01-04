package com.zai360.portal.test.service.impl;

import java.io.Serializable;

import com.zai360.portal.test.commons.BaseDao;
import com.zai360.portal.test.service.BaseService;

public  abstract class BaseServiceImpl<T,PK extends Serializable> implements BaseService<T,PK> {
	
	private BaseDao<T, PK> baseDao;

	@Override
	public void insert(T t) {
		this.baseDao.insert(t);
	}

	@Override
	public void update(T t) {
		this.baseDao.update(t);
	}

	@Override
	public void delete(T t) {
		this.baseDao.delete(t);
	}

	@Override
	public void deleteById(PK id) {
		this.baseDao.deleteById(id);
	}

	@Override
	public T selectById(PK id) {
		return this.baseDao.selectById(id);
	}

	public BaseDao<T, PK> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao<T, PK> baseDao) {
		this.baseDao = baseDao;
	}
	
}
