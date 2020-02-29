package com.vitily.common.service.impl;

import club.yourbatis.hi.base.Page;
import club.yourbatis.hi.base.Primary;
import club.yourbatis.hi.base.meta.PageInfo;
import club.yourbatis.hi.wrapper.ICountWrapper;
import club.yourbatis.hi.wrapper.IDeleteWrapper;
import club.yourbatis.hi.wrapper.ISelectorWrapper;
import club.yourbatis.hi.wrapper.IUpdateWrapper;
import club.yourbatis.hi.wrapper.query.CountWrapper;
import club.yourbatis.hi.wrapper.query.SelectWrapper;
import com.vitily.common.mapper.CommonBasicMapper;
import com.vitily.common.module.BaseEntity;
import com.vitily.common.module.QueryInfo;
import com.vitily.common.module.TvPageList;
import com.vitily.common.service.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public abstract class BasicServiceImpl<T extends BaseEntity<T>, V extends T,M extends CommonBasicMapper<T,V> >
		implements BasicService<T,V> {
	@Autowired(required = false)
	protected M mapper;

	protected void beforeInsert(T entity){}
	protected void afterInsert(T entity){}
	@Override
	@Transactional
	public int insert(T entity){
		beforeInsert(entity);
		Date now=new Date();
		entity.setCreateDate(now);
		entity.setDeltag(false);
		int res = insertSelective(entity);
		if(res > 0) {
			afterInsert(entity);
		}
		return res;
	}
	@Override
	@Transactional
	public int insertSelective(T entity){
		return mapper.insertSelective(entity);
	}
	@Override
	@Transactional
	public int deleteByPrimaryKey(Primary primary){
		return mapper.deleteByPrimaryKey(primary);
	}
	@Override
	@Transactional
	public int delete(IDeleteWrapper wrapper){
		return mapper.delete(wrapper);
	}
	@Override
	public V selectItemByPrimaryKey(Primary primary){
		return mapper.selectItemByPrimaryKeyV(primary);
	}
	@Override
	public V selectOne(ISelectorWrapper wrapper){
		return mapper.selectOneV(wrapper);
	}
	@Override
	public List<T> selectList(ISelectorWrapper wrapper){
		return mapper.selectList(wrapper);
	}
	@Override
	public List<V> selectListV(ISelectorWrapper wrapper){
		return mapper.selectListV(wrapper);
	}
	@Override
	public int selectCount(ICountWrapper wrapper){
		return mapper.selectCount(wrapper);
	}
	protected void beforeUpdate(T entity){}
	protected void afterUpdate(T entity){}
	@Override
	@Transactional
	public int updateByPrimary(T entity){
		beforeUpdate(entity);
		entity.setUpdateDate(new Date());
		int res = updateSelectiveByPrimaryKey(entity);
		if(res > 0) {
			afterUpdate(entity);
		}
		return res;
	}
	@Override
	@Transactional
	public int updateSelectiveByPrimaryKey(T entity){
		return mapper.updateSelectiveByPrimaryKey(entity);
	}
	@Override
	@Transactional
	public int updateSelectItem(IUpdateWrapper wrapper){
		return mapper.updateSelectItem(wrapper);
	}

	@Override
	public TvPageList<V> selectPageList(SelectWrapper wrapper){
		Page page = wrapper.getPage();
		if(page == null){
			throw new RuntimeException("page entity can not be null !");
		}
		TvPageList<V> pageList = new TvPageList<>();
		PageInfo pageInfo = PageInfo.valueOf(page.getPageIndex(),page.getPageSize());
		pageList.setPageInfo(pageInfo);
		pageList.setList(mapper.selectListV(wrapper));
		pageInfo.setRecordCount(mapper.selectCount(new CountWrapper(wrapper.getWhere(),wrapper)));
		return pageList;
	}

	//from xml
	@Override
	public List<V> getListByBean(QueryInfo<T> query) {
		return mapper.getListByBean(query);
	}

	@Override
	public int selectCount(QueryInfo<T> query) {
		return mapper.getCountPaging(query);
	}

	@Override
	public TvPageList<V> selectPageList(QueryInfo<T> query) {
		if(query.getPageInfo() == null){
			throw new RuntimeException("page entity can not be null !");
		}
		TvPageList<V> pageList = new TvPageList<>();
		Page page = query.getPageInfo();
		PageInfo pageInfo = PageInfo.valueOf(page.getPageIndex(),page.getPageSize());
		pageInfo.setRecordCount(mapper.getCountPaging(query));
		pageList.setPageInfo(pageInfo);
		pageList.setList(mapper.getListByBean(query));
		return pageList;
	}
}
