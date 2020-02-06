package com.vitily.common.service;

import club.yourbatis.hi.base.Primary;
import club.yourbatis.hi.wrapper.ICountWrapper;
import club.yourbatis.hi.wrapper.IDeleteWrapper;
import club.yourbatis.hi.wrapper.ISelectorWrapper;
import club.yourbatis.hi.wrapper.IUpdateWrapper;
import club.yourbatis.hi.wrapper.factory.StringConditionWrapper;
import club.yourbatis.hi.wrapper.query.SelectWrapper;
import com.vitily.common.module.BaseEntity;
import com.vitily.common.module.QueryInfo;
import com.vitily.common.module.TvPageList;

import java.util.List;

@SuppressWarnings("unused")
public interface BasicService<T extends BaseEntity<T>,Q,V> {
	int insert(Q req);
	int insertSelective(T entity);
	int deleteByPrimaryKey(Primary primary);
	int delete(IDeleteWrapper wrapper);
	V selectItemByPrimaryKey(Primary primary);
	V selectOne(ISelectorWrapper wrapper);
	List<T> selectList(ISelectorWrapper wrapper);
	List<V> selectListV(ISelectorWrapper wrapper);
	int selectCount(ICountWrapper wrapper);
	int updateByPrimary(Q req);
	int updateSelectiveByPrimaryKey(T entity);
	int updateSelectItem(IUpdateWrapper wrapper);


	TvPageList<V> selectPageList(SelectWrapper selectWrapper);

	default SelectWrapper<String,Object, StringConditionWrapper> getCommonQueryWrapper(){return SelectWrapper.build().selectMain(true);}

	//from xml
	List<V> getListByBean(QueryInfo<T> query);
	int selectCount(QueryInfo<T> query);
	TvPageList<V> selectPageList(QueryInfo<T> query);

}

