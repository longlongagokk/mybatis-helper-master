package com.mybatishelper.demo.basicservice;

import com.mybatishelper.core.base.Primary;
import com.mybatishelper.core.base.meta.PageList;
import com.mybatishelper.core.wrapper.*;
import com.mybatishelper.core.wrapper.factory.PropertyConditionWrapper;
import com.mybatishelper.core.wrapper.query.SelectWrapper;
import com.mybatishelper.demo.common.module.BaseEntity;
import com.mybatishelper.demo.common.module.QueryInfo;
import com.mybatishelper.demo.common.module.TvPageList;

import java.util.List;

@SuppressWarnings("unused")
public interface BasicService<T extends BaseEntity<T>,V> {
	int insert(T req);
	int insertSelective(T entity);
	int deleteByPrimaryKey(Primary primary);
	int delete(IDeleteWrapper wrapper);
	V selectItemByPrimaryKey(Primary primary);
	V selectOne(ISelectorWrapper wrapper);
	List<T> selectList(ISelectorWrapper wrapper);
	List<V> selectListV(ISelectorWrapper wrapper);
	int selectCount(IQueryWrapper wrapper);
	int updateByPrimary(T req);
	int updateSelectiveByPrimaryKey(T entity);
	int updateSelectItem(IUpdateWrapper wrapper);


	PageList<T> selectPageList(ISelectorWrapper selectWrapper);
	PageList<V> selectPageListV(ISelectorWrapper selectWrapper);

	default SelectWrapper<PropertyConditionWrapper> getCommonQueryWrapper(){return SelectWrapper.build().selectMain(true);}

	//from xml
	List<V> getListByBean(QueryInfo<T> query);
	int selectCount(QueryInfo<T> query);
	TvPageList<V> selectPageList(QueryInfo<T> query);

}
