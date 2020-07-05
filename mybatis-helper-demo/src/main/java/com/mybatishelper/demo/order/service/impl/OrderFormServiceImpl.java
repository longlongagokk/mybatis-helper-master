package com.mybatishelper.demo.order.service.impl;

import com.mybatishelper.core.base.Item;
import com.mybatishelper.core.base.meta.ItemPar;
import com.mybatishelper.core.base.meta.SelectInfo;
import com.mybatishelper.core.base.meta.SortInfo;
import com.mybatishelper.core.base.meta.UpdateInfo;
import com.mybatishelper.core.base.param.FieldItem;
import com.mybatishelper.core.base.param.ParamItem;
import com.mybatishelper.core.base.param.ValueItem;
import com.mybatishelper.core.wrapper.factory.SqlWrapperFactory;
import com.mybatishelper.core.wrapper.delete.DeleteWrapper;
import com.mybatishelper.core.wrapper.factory.PropertyConditionWrapper;
import com.mybatishelper.core.wrapper.query.QueryWrapper;
import com.mybatishelper.core.wrapper.query.SelectWrapper;
import com.mybatishelper.core.wrapper.update.UpdateWrapper;
import com.mybatishelper.demo.basicservice.impl.BasicServiceImpl;
import com.mybatishelper.demo.common.util.JSONUtil;
import com.mybatishelper.demo.common.util.PageInfo;
import com.mybatishelper.demo.order.mapper.OrderFormMapper;
import com.mybatishelper.demo.order.module.entity.TbOrderDetail;
import com.mybatishelper.demo.order.module.entity.TbOrderForm;
import com.mybatishelper.demo.order.module.query.TsOrderForm;
import com.mybatishelper.demo.order.module.view.TvOrderForm;
import com.mybatishelper.demo.order.service.OrderFormService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class OrderFormServiceImpl extends BasicServiceImpl<TbOrderForm, TvOrderForm, OrderFormMapper> implements OrderFormService {

	/**
	 * nested
	 * @param id
	 * @return
	 */
	@Transactional()
	@Override
	public TvOrderForm selectOne(Long id){
		if(id.longValue() == 0L){
			return t0(id);
		}
		if(id.longValue() == 1L){
			return update(id);
		}
		if(id.longValue() == 2L){
			return list0(id);
		}
		return page0(id);
	}
	private TvOrderForm t0(Long id){


//		log.info("------------------------insertOfAll------------------------------");
//		//insertOfAll
//		TbOrderForm orderForm = new TbOrderForm();
//		//orderForm.setMemberId(123L);
//		//orderForm.setOrderNo("absfwsfdwef");
//		log.info("------------------------"+mapper.insert(orderForm)+"------------------------------");
//
//		log.info("------------------------insertOfNotNull------------------------------");
//		//insertOfNotNull
//		//orderForm.setMemberId(456L);
//		//orderForm.setOrderNo("ddfea");
//		orderForm.setAmountPaid(BigDecimal.valueOf(100));
//		log.info("------------------------"+mapper.insertSelective(orderForm)+"------------------------------");
//
//		log.info("------------------------deleteByPrimaryKey------------------------------");
//		//deleteByPrimaryKey
//		log.info("------------------------"+mapper.deleteByPrimaryKey(SimplePrimary.valueOf(30857))+"------------------------------");
//
		//delete
		log.info("------------------------"+mapper.delete(
				new DeleteWrapper<>(new PropertyConditionWrapper()).where(x->x
						.eq(TsOrderForm.Fields.payWayId.name(),444)
						.eq(ItemPar.withFieldParam("e.id",3)

				))
		)+"------------------------------");
//		//delete
//		log.info("------------------------"+mapper.delete(
//				DeleteWrapper.build().where(x->x.e().eq(TsOrderForm.Fields.payWayId,222).eq(
//
//				))
//		)+"------------------------------");
//		//delete
//		log.info("------------------------"+mapper.delete(
//				new DeleteWrapper<>(new StringConditionWrapper()).where(x->x.e().eq(TsOrderForm.Fields.payWayId,999).eq(
//
//				))
//		)+"------------------------------");
//		log.info("------------------------delete------------------------------");
//		//delete
//		log.info("------------------------"+mapper.delete(
//				new DeleteWrapper<>(new StringConditionWrapper()).where(e->e
//								//.between(CompareAlias.valueOf(TsOrderForm.Fields.memberId),1000,2000)
//								.e()
//								.or(x->x
//										.eq(TsOrderForm.Fields.deliveryId,3)
//										.eq(TsOrderForm.Fields.deliveryId,31))
//								.eq(TsOrderForm.Fields.postCode,"3430")
//								.eq(TsOrderForm.Fields.deliveryId,1)
//								.eq(TsOrderForm.Fields.deliveryId,2)
//								.eq(TsOrderForm.Fields.deliveryId,3)
//								.eq(TsOrderForm.Fields.deliveryId,4)
//								.or(a->a.eq(TsOrderForm.Fields.deliveryId,5)
//										.and(b->b.eq(TsOrderForm.Fields.orderNo,"abc")
//												.or(c->c.eq(TsOrderForm.Fields.orderNo,"'-- delete from tb_adm undo_log ' -- ")
//														.eq(TsOrderForm.Fields.orderNo,"kk"))
//										)
//										.eq(TsOrderForm.Fields.memberId,10)
//										.and(b->b.eq(TsOrderForm.Fields.orderNo,"abc")
//												.or(c->c.eq(TsOrderForm.Fields.orderNo,"'-- delete from tb_adm undo_log ' -- ")
//														.eq(TsOrderForm.Fields.orderNo,"kk"))
//										)
//								)
//						)
//		)+"------------------------------");
//
//		log.info("------------------------selectItemByPrimaryKey--all------------------------------");
//		//selectItemByPrimaryKey--all
//		log.info("------------------------"+JSONUtil.toJSONString(mapper.selectItemByPrimaryKey(IdWrapper.valueOf(6)))+"------------------------------");
//
//		log.info("------------------------selectItemByPrimaryKey--selectItem------------------------------");
//		//selectItemByPrimaryKey--selectItem
//		log.info("------------------------"+JSONUtil.toJSONString(mapper.selectItemByPrimaryKey(IdWrapper.valueOf(6,TsOrderForm.Fields.deliveryId,TsOrderForm.Fields.memberId)))+"------------------------------");

		log.info("------------------------selectOne--all------------------------------");
		//selectOne--all
		SelectWrapper<PropertyConditionWrapper> queryWrapper =
				new SelectWrapper<>(new PropertyConditionWrapper())
//						.select("payWayId pid")
//						.select("payState sd")
//						.select("userName    ")
//						.select("   dealStatus    ")
//						.select("   orderDate   ")
						.select(SelectInfo.withOriginal("(select count(0) from tb_order_form) counts"))
//						.select0(
//								SelectField.valueOf("e", TsOrderForm.Fields.area)
//						)
						.leftJoin(TbOrderDetail.class,"d", x->
										x.f()
										.eq(
												FieldItem.valueOf("e.id"),
												FieldItem.valueOf("d.orderId")
										).eq(
												FieldItem.valueOf("e.id"),
												FieldItem.valueOf("e.payState")
										)
//												.or(a->a.eq(FieldItem.valueOf("e.id"), ParamItem.valueOf(10L))
//														.and(b->b.eq(FieldItem.valueOf("e.id"), ValueItem.valueOf(20L))
//																.or(c->c.eq(FieldItem.valueOf("e.id"), ParamItem.valueOf(2222))
//																		.eq(FieldItem.valueOf("e.id"), ParamItem.valueOf(33333))
//																)
//																.eq(FieldItem.valueOf("e.id"), ValueItem.valueOf(50L))
//																.and(bs->bs.eq(FieldItem.valueOf("e.id"), ValueItem.valueOf(30L))
//																		.or(c->c.eq(FieldItem.valueOf("e.id"), ValueItem.valueOf(30L))
//																				.eq(FieldItem.valueOf("e.id"), ValueItem.valueOf(30L)))
//																)
//														)
//												)
//								.eqc(
//										CompareAlias.valueOf("d.orderId"),
//										CompareAlias.valueOf("e.id")
//								)
//								.eqc(
//										CompareAlias.valueOf("d.orderId"),
//										CompareAlias.valueOf("e.id")
//								)
//								.eq(CompareAlias.valueOf(TsOrderForm.Fields.id,"e"),300L)
//								.or(k->k.eq(CompareAlias.valueOf(TsOrderForm.Fields.id,"e"),100L)
//										.eq(CompareAlias.valueOf(TsOrderForm.Fields.id,"e"),200L)
//										.eq(CompareAlias.valueOf(TsOrderForm.Fields.id,"e"),300L)
//										.and(z->z.eq(CompareAlias.valueOf(TsOrderForm.Fields.id,"e"),11)
//												.eq(CompareAlias.valueOf(TsOrderForm.Fields.id,"e"),22)
//												.eq(CompareAlias.valueOf(TsOrderForm.Fields.id,"e"),33)
//										)
//								)
						)

//				.rightJoin(ClassAssociateTableInfo.valueOf(TbOrderDetail.class,"x"),x->
//						x.eqc(FieldField.valueOf("d.orderId","d.id"))
//								.eqc(CompareAlias.valueOf(TsOrderDetail.Fields.orderId,"d"),
//                                        CompareAlias.valueOf(TsOrderForm.Fields.id,"e")
//								)
//								.eq(CompareAlias.valueOf(TsOrderForm.Fields.id,"e"),300L)
//								.or(k->k.eq(CompareAlias.valueOf(TsOrderForm.Fields.id,"e"),100L)
//										.eq(CompareAlias.valueOf(TsOrderForm.Fields.id,"e"),200L)
//										.eq(CompareAlias.valueOf(TsOrderForm.Fields.id,"e"),300L)
//										.and(z->z.eq(CompareAlias.valueOf(TsOrderForm.Fields.id,"e"),11)
//												.eq(CompareAlias.valueOf(TsOrderForm.Fields.id,"e"),22)
//												.eq(CompareAlias.valueOf(TsOrderForm.Fields.id,"e"),33)
//										)
//								)
//				)
						.orderBy("e.id desc,e.payWayName asc")
						.orderBy("e.area")
						.orderBy(SortInfo.withField("deliveryId"),SortInfo.withField(TsOrderForm.Fields.deliveryId.name()))
						.orderBy(TsOrderForm.Fields.deliveryId.name())
						.orderBy(SortInfo.withField("e.updateDate")
								, SortInfo.withField("e.sendDate")
						)
						.page(PageInfo.valueOf(30))
						.where(x->x
								.eq(TsOrderForm.Fields.amountPaid.name(),1)
								.d().eq("e.orderNo","123")
								.eq("e.orderNo","456")
								.or(m->m.eq("e.id",889)
										.eq("e.payState",3)
										.eq(TsOrderForm.Fields.memberId.name(),345)
								)
								.eq(TsOrderForm.Fields.address.name(),2)
								.eq(TsOrderForm.Fields.deliveryId.name(),3)
								.eq(TsOrderForm.Fields.deliveryId.name(),4)
								.or(a->a.eq(TsOrderForm.Fields.deliveryId.name(),5)
										.and(b->b.eq(TsOrderForm.Fields.orderNo.name(),"abc")
												.or(c->c.eq(TsOrderForm.Fields.orderNo.name(),"'-- delete from tb_adm undo_log ' -- ")
														.eq(TsOrderForm.Fields.orderNo.name(),"kk"))
										)
										.eq(TsOrderForm.Fields.memberId.name(),10)
										.and(b->b.eq(TsOrderForm.Fields.orderNo.name(),"abc")
												.or(c->c.eq(TsOrderForm.Fields.orderNo.name(),"'-- delete from tb_adm undo_log ' -- ")
														.eq(TsOrderForm.Fields.orderNo.name(),"kk"))
										)
								)
						)
				;
//		log.info("------------------------"+JSONUtil.toJSONString(mapper.selectCount(
//				new QueryWrapper<>(new StringConditionWrapper())
//						.join(ClassAssociateTableInfo.valueOf(TbOrderDetail.class,"d"),
//								d->
//										d.eq("d.id",700)
//								)
//				.where(x->
//						x.eq("e.id",123)
//						.e()
//						.eq(TsOrderForm.Fields.orderNo,"23")
//						.f()
//						.eq(ParamItem.valueOf(3000),FieldItem.valueOf("e.payState"))
//						.or(y->y.e()
//										.eq(TsOrderForm.Fields.orderNo,"8888")
//										.eq(TsOrderForm.Fields.orderNo,"999")
//								)
//						)
//
//		))+"------------------------------");
//
		//queryWrapper.getWhere();queryWrapper.getJoins()
		log.info("------------------------"+JSONUtil.toJSONString(mapper.selectCount(
			SqlWrapperFactory.prop4Query().where(x->
					x.eq("e.payWayId",333333))
		))+"------------------------------");

		log.info("------------------------"+JSONUtil.toJSONString(mapper.selectList(
				queryWrapper
		))+"------------------------------");
		QueryWrapper queryWrapper1 = new QueryWrapper<>(queryWrapper.getWhere(),queryWrapper);
		log.info("------------------------"+JSONUtil.toJSONString(mapper.selectCount(
				queryWrapper1

		))+"------------------------------");




		log.info("------------------------selectOne--selectSome------------------------------");
		//selectOne--selectSome
		//log.info("------------------------"+ JSONUtil.toJSONString(mapper.selectOne(new OldQueryWrapper<TbOrderForm>().select(TsOrderForm.Fields.deliveryId,TsOrderForm.Fields.memberId).ge(TsOrderForm.Fields.memberId,8892))) +"------------------------------");

		return new TvOrderForm();
	}
	private TvOrderForm update(Long id){


//		log.info("------------------------updateByPrimary------------------------------");
//		//updateByPrimary
		TbOrderForm orderForm = new TbOrderForm();
		orderForm.setMemberId(123L);
		orderForm.setOrderNo("absfwsfdwef");
		orderForm.setId(12345L);
		log.info("------------------------"+staticBoundMapper.updateByPrimary(new TbOrderForm())+"------------------------------");
//
//		log.info("------------------------updateSelectiveByPrimaryKey------------------------------");
//		//updateSelectiveByPrimaryKey
		orderForm.setMemberId(456L);
		orderForm.setOrderNo("ddfea");
		orderForm.setAmountPaid(BigDecimal.valueOf(100));
		orderForm.setId(98L);
		log.info("------------------------"+staticBoundMapper.updateSelectiveByPrimaryKey(orderForm)+"------------------------------");

		log.info("------------------------updateSelectItem------------------------------");
//		Collection<ValueItem> items = new ArrayList<>();
		UpdateWrapper wrapper = SqlWrapperFactory.prop4Update()
				.set("e.payState",88)
//				.set(TsOrderForm.Fields.memberId.name(),30)
//				.set(
//				)
				.set(TsOrderForm.Fields.amountPay.name(),88.88)

				.set(UpdateInfo.withColumnParam("e.order_no",666))
				.set(UpdateInfo.withColumnField("e.order_no","e.orderNo"))
				.set(UpdateInfo.withColumnValue("e.order_no","777"))

				.where(x->
						x
						.eq("e.deliveryId",1)
						.eq("e.id",2)
						.eq(TsOrderForm.Fields.leaveMessage.name(),"343")
//						.eq(TsOrderForm.Fields.postCode,3)
//						.eq(TsOrderForm.Fields.id,4)
//						.eq(TsOrderForm.Fields.payState,5)
//						.eq(TsOrderForm.Fields.deliveryId,6)
//						.eq(TsOrderForm.Fields.payWayId,7)
//						.eq(TsOrderForm.Fields.deliveryId,8)
//						.or(a->a.eq(TsOrderForm.Fields.deliveryId,9)
//								.and(b->b.eq(TsOrderForm.Fields.orderNo,"abc")
//										.or(c->c.eq(TsOrderForm.Fields.orderNo,"'-- delete from tb_adm undo_log ' -- ")
//												.eq(TsOrderForm.Fields.orderNo,"kk"))
//								)
//								.eq(TsOrderForm.Fields.memberId,10)
//								.and(b->b.eq(TsOrderForm.Fields.orderNo,"abc")
//										.or(c->c.eq(TsOrderForm.Fields.orderNo,"'-- delete from tb_adm undo_log ' -- ")
//												.eq(TsOrderForm.Fields.orderNo,"kk"))
//								)
//						)
				)
				.leftJoin(TbOrderDetail.class,"x",x->
						x.eq("x.orderId",200)
				)
				.where(x->x.f().eq(ValueItem.valueOf(20),ValueItem.valueOf(30))
				)
				;
		log.info("------------------------"+mapper.updateSelectItem(wrapper

//						.eq(TsOrderForm.Fields.memberId,3)
//						.update(Elements.valueOf(TsOrderForm.Fields.deliveryId,34))
//						.eq(TsOrderForm.Fields.memberId,3)
//						.update(Elements.valueOf(TsOrderForm.Fields.deliveryId,34))
//						.update(Elements.valueOf(TsOrderForm.Fields.deliveryId,34))
//						.eq(TsOrderForm.Fields.memberId,3)

		)+"------------------------------");

//		  mapper.selectOne(new QueryWrapper<TbOrderForm>()
//				.eq(TsOrderForm.Fields.memberId,3)
//				.select(TsOrderForm.Fields.deliveryId,TsOrderForm.Fields.orderNo)
//                  .select("id,orderNo,deliveryId")
//				.or(a->a.ge(TsOrderForm.Fields.deliveryId,20)
//						.and(b->b.eq(TsOrderForm.Fields.orderNo,"abc")
//								.or(c->c.eq(TsOrderForm.Fields.orderNo,"'-- delete from tb_adm undo_log ' -- ")
//										.eq(TsOrderForm.Fields.orderNo,"kk"))
//						)
//						.lt(TsOrderForm.Fields.memberId,10)
//				)
//				.eq(TsOrderForm.Fields.deliveryId,22)
//				.between(TsOrderForm.Fields.memberId,1000,2000)
//				.or(x->x.eq(TsOrderForm.Fields.deliveryId,3))//没什么用
//				.orderBy(OrderBy.valueOf(Order.ASC,TsOrderForm.Fields.memberId,TsOrderForm.Fields.deliveryId))
//				.orderBy(OrderBy.valueOf(Order.ASC,TsOrderForm.Fields.memberId,TsOrderForm.Fields.deliveryId))
//				.orderBy(OrderBy.valueOf(Order.DESC,TsOrderForm.Fields.memberId,TsOrderForm.Fields.deliveryId))
//		);
		return new TvOrderForm();
	}
	private TvOrderForm list0(Long id){

		log.info("------------------------selectList------------------------------");
		log.info("------------------------"+ JSONUtil.toJSONString(
				mapper.selectOne(SqlWrapperFactory.prop4Select()
					.select("e.id,e.payWayName")

						.select(SelectInfo.withField("x.deliveryId"))
						.leftJoin(TbOrderForm.class,"x",x->x.eq("x.id",8543))
//						.innerJoin(ClassAssociateTableInfo.valueOf(TbOrderForm.class,"x"),
//								)
				)
		)+"------------------------------");
		List<Integer> ids = new ArrayList<>();
		ids.add(1);
		List<Item> iids = new ArrayList<>();
		iids.add(FieldItem.valueOf("e.orderNo"));
		iids.add(ParamItem.valueOf("e.orderNo"));
		iids.add(ValueItem.valueOf("100"));
		log.info("------------------------"+
				mapper.selectCount(new QueryWrapper<>(new PropertyConditionWrapper())
						.leftJoin(TbOrderDetail.class,"x",
								x->x.eq("x.id",3)
								)
						.where(x->
								x
								.f()
										.notIn(
												FieldItem.valueOf("e.id"),
												iids
										)
										.notIn(
												ValueItem.valueOf("888"),
												iids
										)
										.notIn(
												ParamItem.valueOf("e.id"),
												iids
										)
								.d()
										.in("e.id",ids)
//										.between("x.productId",100,200)
//										.e()
//										.between(TsOrderForm.Fields.orderNo,999,888)
										.f()
										.between(
												FieldItem.valueOf("x.id"),
												FieldItem.valueOf("x.id"),
												FieldItem.valueOf("x.id")
												)
										.between(
												ValueItem.valueOf("800"),
												ParamItem.valueOf("666"),
												ParamItem.valueOf("777")
										)
										.between(
												ParamItem.valueOf("x.id"),
												ValueItem.valueOf("666"),
												ParamItem.valueOf("777")
										)
//										.d()
//								.isNull("e.payState")
//								.or(k->k
//										.e().notNull(TsOrderForm.Fields.memberId)
//										.f()
//											.isNull(ParamItem.valueOf("'200k'"))
//											.isNull(FieldItem.valueOf("e.memberId"))
//											.isNull(ValueItem.valueOf("888"))
//											.eq(FieldItem.valueOf("e.payState"),FieldItem.valueOf("e.orderNo"))
//										.eq(ParamItem.valueOf("777"),ValueItem.valueOf("999"))
//										.eq(ParamItem.valueOf("777"),FieldItem.valueOf("e.orderNo"))
//										.eq(ParamItem.valueOf("777"),ParamItem.valueOf("e.orderNo"))
//								)

								)

		)+"------------------------------");
		log.info("------------------------"+ JSONUtil.toJSONString(
				mapper.selectCount(new QueryWrapper<>(new PropertyConditionWrapper())
						.where(x->
								x.eq("e.payState",2))
				)
		)+"------------------------------");
		log.info("------------------------"+ JSONUtil.toJSONString(
				mapper.selectOne(SqlWrapperFactory.prop4Select()
						.select(SelectInfo.withField("e.deliveryId"))
						.where(x->x
								.eq(TsOrderForm.Fields.memberId.name(),3))
				)
		)+"------------------------------");
		log.info("------------------------"+ JSONUtil.toJSONString(
				mapper.selectList(SqlWrapperFactory.prop4Select()
						.select(SelectInfo.withField("e.deliveryId"))
						.where(x->x
								.eq(TsOrderForm.Fields.memberId.name(),3))
				)
		)+"------------------------------");
//
//		 mapper.selectOne(new OldQueryWrapper<TbOrderForm>()
//				.eq(TsOrderForm.Fields.memberId,3)
//				.select(TsOrderForm.Fields.deliveryId,TsOrderForm.Fields.orderNo)
//				.or(a->a.ge(TsOrderForm.Fields.deliveryId,20)
//						.and(b->b.eq(TsOrderForm.Fields.orderNo,"abc")
//								.or(c->c.eq(TsOrderForm.Fields.orderNo,"'-- delete from tb_adm undo_log ' -- ")
//										.eq(TsOrderForm.Fields.orderNo,"kk"))
//						)
//						.lt(TsOrderForm.Fields.memberId,10)
//				)
//				.eq(TsOrderForm.Fields.deliveryId,22)
//				.between(TsOrderForm.Fields.memberId,1000,2000)
//				.or(x->x.eq(TsOrderForm.Fields.deliveryId,3))//没什么用
//				.orderBy(OrderBy.valueOf(Order.ASC,TsOrderForm.Fields.memberId,TsOrderForm.Fields.deliveryId))
//				.orderBy(OrderBy.valueOf(Order.ASC,TsOrderForm.Fields.memberId,TsOrderForm.Fields.deliveryId))
//				.orderBy(OrderBy.valueOf(Order.DESC,TsOrderForm.Fields.memberId,TsOrderForm.Fields.deliveryId))
//		);
		return new TvOrderForm();
	}
	private TvOrderForm page0(Long id){
//
//		log.info("------------------------selectPageList------------------------------");
//		log.info("------------------------"+ JSONUtil.toJSONString(mapper.selectCount(new OldQueryWrapper<TbOrderForm>()
//				.ge(TsOrderForm.Fields.memberId,3)
//		))+"------------------------------");
//		mapper.selectOne(new OldQueryWrapper<TbOrderForm>()
//				.gt(TsOrderForm.Fields.memberId,3)
//				.select(TsOrderForm.Fields.memberId,TsOrderForm.Fields.orderNo)
//				.between(TsOrderForm.Fields.memberId,1000,20000)
//				.orderBy(OrderBy.valueOf(Order.ASC,TsOrderForm.Fields.memberId,TsOrderForm.Fields.deliveryId))
//		);
		return null;
	}
}