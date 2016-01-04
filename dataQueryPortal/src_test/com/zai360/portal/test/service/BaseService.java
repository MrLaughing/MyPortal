package com.zai360.portal.test.service;

import java.io.Serializable;



public interface BaseService<T,PK extends Serializable>{
	/**
	 * 添加数据t
	 * @param t
	 */
	public void insert(T t);
	/**
	 * 修改数据t
	 * @param t
	 */
	public void update(T t);
	/**
	 * 将表中数据t删除
	 * @param t
	 */
	public void delete(T t);
	/**
	 * 根据id删除表中数据
	 * @param id
	 */
	public void deleteById(PK id);
	/**
	 * 根据id查找数据
	 * @param id
	 * @return 返回数据t
	 */
	public T selectById(PK id);
}
