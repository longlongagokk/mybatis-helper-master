package com.vitily.common.service.impl;

import club.yourbatis.hi.base.Page;
import club.yourbatis.hi.base.Primary;
import club.yourbatis.hi.wrapper.ICountWrapper;
import club.yourbatis.hi.wrapper.IDeleteWrapper;
import club.yourbatis.hi.wrapper.ISelectorWrapper;
import club.yourbatis.hi.wrapper.IUpdateWrapper;
import club.yourbatis.hi.wrapper.query.SelectWrapper;
import com.vitily.common.mapper.CommonBasicMapper;
import com.vitily.common.module.BaseEntity;
import com.vitily.common.module.BaseRequest;
import com.vitily.common.module.QueryInfo;
import com.vitily.common.module.TvPageList;
import com.vitily.common.service.BasicService;
import club.yourbatis.hi.base.meta.PageInfo;
import club.yourbatis.hi.mapper.BasicMapper;
import club.yourbatis.hi.wrapper.query.CountWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public abstract class BasicServiceImpl<T extends BaseEntity<T>,Q extends BaseRequest<T,Q>, V extends T,M extends BasicMapper<T,V> & CommonBasicMapper<T,V> >
		implements BasicService<T,Q,V> {
	@Autowired(required = false)
	protected M mapper;

	protected void beforeInsert(Q req){}
	protected void afterInsert(Q req){}
	@Override
	@Transactional
	public int insert(Q req){
		beforeInsert(req);
		Date now=new Date();
		req.getEntity().setCreateDate(now);
		req.getEntity().setDeltag(false);
		int res = insertSelective(req.getEntity());
		if(res > 0) {
			afterInsert(req);
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
		return mapper.selectItemByPrimaryKey(primary);
	}
	@Override
	public V selectOne(ISelectorWrapper wrapper){
		return mapper.selectOne(wrapper);
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
	protected void beforeUpdate(Q req){}
	protected void afterUpdate(Q req){}
	@Override
	@Transactional
	public int updateByPrimary(Q req){
		beforeUpdate(req);
		req.getEntity().setUpdateDate(new Date());
		int res = updateSelectiveByPrimaryKey(req.getEntity());
		if(res > 0) {
			afterUpdate(req);
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
		if(wrapper.getPage() == null){
			throw new RuntimeException("page entity can not be null !");
		}
		TvPageList<V> pageList = new TvPageList<>();
		Page page = wrapper.getPage();
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
