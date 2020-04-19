package com.vitily.basicservice.impl;

import club.yourbatis.hi.base.Page;
import club.yourbatis.hi.base.Primary;
import club.yourbatis.hi.base.meta.PageList;
import club.yourbatis.hi.wrapper.ICountWrapper;
import club.yourbatis.hi.wrapper.IDeleteWrapper;
import club.yourbatis.hi.wrapper.ISelectorWrapper;
import club.yourbatis.hi.wrapper.IUpdateWrapper;
import club.yourbatis.hi.wrapper.query.SelectWrapper;
import com.vitily.basicmapper.CommonBasicMapper;
import com.vitily.basicservice.BasicService;
import com.vitily.common.mapper.StaticBoundMapper;
import com.vitily.common.module.BaseEntity;
import com.vitily.common.module.QueryInfo;
import com.vitily.common.module.TvPageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public abstract class BasicServiceImpl<T extends BaseEntity<T>, V extends T,M extends CommonBasicMapper<T,V> >
		implements BasicService<T,V> {
	@Autowired(required = false)
	protected M mapper;
	@Autowired(required = true)
	protected StaticBoundMapper staticBoundMapper;

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
		return staticBoundMapper.insertSelective(entity);
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
		return staticBoundMapper.updateSelectiveByPrimaryKey(entity);
	}
	@Override
	@Transactional
	public int updateSelectItem(IUpdateWrapper wrapper){
		return mapper.updateSelectItem(wrapper);
	}

	@Override
	public TvPageList<T> selectPageList(SelectWrapper wrapper){
		Page page = wrapper.getPage();
		if(page == null){
			throw new RuntimeException("page entity can not be null !");
		}
		PageList<T> oriPageList = mapper.selectPageList(wrapper);
		TvPageList<T> targetPageList = new TvPageList<>();
		targetPageList.setList(oriPageList.getList());
		targetPageList.setRecordCount(oriPageList.getCount());
		return targetPageList;
	}

	@Override
	public TvPageList<V> selectPageListV(SelectWrapper wrapper){
		Page page = wrapper.getPage();
		if(page == null){
			throw new RuntimeException("page entity can not be null !");
		}
		PageList<V> oriPageList = mapper.selectPageListV(wrapper);
		TvPageList<V> targetPageList = new TvPageList<>();
		targetPageList.setList(oriPageList.getList());
		targetPageList.setRecordCount(oriPageList.getCount());
		return targetPageList;
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
		pageList.setRecordCount(mapper.getCountPaging(query));
		pageList.setList(mapper.getListByBean(query));
		return pageList;
	}
}
