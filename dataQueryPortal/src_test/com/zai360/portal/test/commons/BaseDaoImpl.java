package com.zai360.portal.test.commons;

import java.io.Serializable;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseDaoImpl<T, PK extends Serializable> extends
		SqlSessionDaoSupport implements BaseDao<T, PK> {
	/**
	 * 实体类类型
	 */
	protected Class<T> entityClass;

	/*@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		Type type = getClass().getGenericSuperclass();
		//判断this.getClass是不是BaseDaoImpl的子类 或者 实现类 或者自身
		
//		if(this instanceof BaseDaoImpl && !getClass().equals(BaseDaoImpl.class)){
//			Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
//			entityClass = (Class<T>) parameterizedType[0];
//		}
		if(BaseDaoImpl.class.isAssignableFrom(this.getClass())&&!this.getClass().equals(BaseDaoImpl.class)){
			Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
			entityClass = (Class<T>) parameterizedType[0];
		}
	}*/

	@Autowired
	@Override
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	/**
	 * 添加对象t
	 */
	@Override
	public void insert(T t) {
		getSqlSession().insert(entityClass.getName() + ".insert", t);
	}

	/**
	 * 修改对象t
	 */
	@Override
	public void update(T t) {
		getSqlSession()
				.update(entityClass.getName() + ".updateByPrimaryKey", t);
	}

	/**
	 * 删除对象t
	 */
	@Override
	public void delete(T t) {
	}

	/**
	 * 根据主键id删除对象t
	 */
	@Override
	public void deleteById(PK id) {
		getSqlSession().delete(entityClass.getName() + ".deleteByPrimaryKey",
				id);
	}

	/**
	 * 查询对象t
	 */
	@Override
	public T selectById(PK id) {
		T t = getSqlSession().selectOne(
				entityClass.getName() + ".selectByPrimaryKey", id);
		return t;
	}
}
