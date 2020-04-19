package com.vitily.order.service.impl;

import club.yourbatis.hi.base.Item;
import club.yourbatis.hi.base.field.CompareField;
import club.yourbatis.hi.base.field.OrderField;
import club.yourbatis.hi.base.field.SelectField;
import club.yourbatis.hi.base.meta.FieldWithValue;
import com.vitily.common.util.PageInfo;
import club.yourbatis.hi.base.param.FieldItem;
import club.yourbatis.hi.base.param.ParamItem;
import club.yourbatis.hi.base.param.ValueItem;
import club.yourbatis.hi.wrapper.delete.DeleteWrapper;
import club.yourbatis.hi.wrapper.factory.PropertyConditionWrapper;
import club.yourbatis.hi.wrapper.query.CountWrapper;
import club.yourbatis.hi.wrapper.query.SelectWrapper;
import club.yourbatis.hi.wrapper.update.UpdateWrapper;
import com.vitily.basicservice.impl.BasicServiceImpl;
import com.vitily.common.util.JSONUtil;
import com.vitily.order.mapper.OrderFormMapper;
import com.vitily.order.module.entity.TbOrderDetail;
import com.vitily.order.module.entity.TbOrderForm;
import com.vitily.order.module.query.TsOrderForm;
import com.vitily.order.module.view.TvOrderForm;
import com.vitily.order.service.OrderFormService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class OrderFormServiceImpl extends BasicServiceImpl<TbOrderForm, TvOrderForm,OrderFormMapper> implements OrderFormService {

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
				new DeleteWrapper<>(new PropertyConditionWrapper()).where(x->x.eq(TsOrderForm.Fields.payWayId.name(),444).eq(
						FieldWithValue.withOriginalValue("id",333)

				))
		)+"------------------------------");
//		//delete
//		log.info("------------------------"+mapper.delete(
//				DeleteWrapper.build().where(x->x.e().eq(TsOrderForm.Fields.payWayId,222).eq(
//						FieldWithValue.withOriginalValue("id",1001)
//
//				))
//		)+"------------------------------");
//		//delete
//		log.info("------------------------"+mapper.delete(
//				new DeleteWrapper<>(new StringConditionWrapper()).where(x->x.e().eq(TsOrderForm.Fields.payWayId,999).eq(
//						FieldWithValue.withOriginalValue("id",888)
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
		SelectWrapper queryWrapper =
				new SelectWrapper<>(new PropertyConditionWrapper())
//						.select("payWayId pid")
//						.select("payState sd")
//						.select("userName    ")
//						.select("   dealStatus    ")
//						.select("   orderDate   ")
						.select(SelectField.valueOf("(select count(0) from Tb_cms_news) counts",true))
//						.select0(
//								SelectField.valueOf("e", TsOrderForm.Fields.area)
//						)
						.leftJoin(TbOrderDetail.class,"d", x->
										x.f()
										.eq(
												FieldItem.valueOf(CompareField.valueOf("e.id")),
												FieldItem.valueOf(CompareField.valueOf("d.orderId"))
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
						.orderBy( OrderField.valueOf("deliveryId"),OrderField.valueOf(TsOrderForm.Fields.deliveryId.name()))
						.orderBy(TsOrderForm.Fields.deliveryId.name())
						.orderBy(OrderField.valueOf("e.updateDate")
								, OrderField.valueOf("e.sendDate")
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
//				new CountWrapper<>(new StringConditionWrapper())
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
			CountWrapper.build().where(x->
					x.eq("e.payWayId",333333))
		))+"------------------------------");

		log.info("------------------------"+JSONUtil.toJSONString(mapper.selectList(
				queryWrapper
		))+"------------------------------");
		log.info("------------------------"+JSONUtil.toJSONString(mapper.selectCount(
				new CountWrapper(queryWrapper.getWhere(),queryWrapper)

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
		UpdateWrapper wrapper = UpdateWrapper.build()
				.set("e.payState",88)
//				.set(TsOrderForm.Fields.memberId.name(),30)
//				.set(FieldWithValue.withOriginalValue("e.payState",188))
//				.set(
//						FieldWithValue.withOriginalValue("e.phone",11),
//						FieldWithValue.withParamValue(TsOrderForm.Fields.payWayId,22),
//						FieldWithValue.withParamValue("e.payState",33),
//						FieldWithValue.withParamValue(TsOrderForm.Fields.deliveryId,44),
//						FieldWithValue.valueOf("e.phone",FieldItem.valueOf("e.id"))
//				)
				.set(TsOrderForm.Fields.amountPay.name(),88.88)

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
//				.where(x->
//						x.or(k->
//								k.eq(FieldValue.valueOf("e.id",1874544423L))
//										.eq(FieldValue.valueOf("e.orderNo",1874544423L)))
//				)
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
				mapper.selectOne(SelectWrapper.build()
					.select("e.id,e.payWayName")

						.select(SelectField.valueOf("x", TsOrderForm.Fields.deliveryId))
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
				mapper.selectCount(new CountWrapper<>(new PropertyConditionWrapper())
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
//								.eq(FieldWithValue.withOriginalValue("x.productId",34))
//								.eq(FieldWithValue.withParamValue("e.id",88))
//								.eq(FieldWithValue.valueOf("e.payState",FieldItem.valueOf("e.orderNo")))
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
				mapper.selectCount(new CountWrapper<>(new PropertyConditionWrapper())
						.where(x->
								x.eq("e.payState",2))
				)
		)+"------------------------------");
		log.info("------------------------"+ JSONUtil.toJSONString(
				mapper.selectOne(SelectWrapper.build()
						.select(
								SelectField.valueOf("e", TsOrderForm.Fields.deliveryId)
						)
						.where(x->x
								.eq(TsOrderForm.Fields.memberId.name(),3))
				)
		)+"------------------------------");
		log.info("------------------------"+ JSONUtil.toJSONString(
				mapper.selectList(SelectWrapper.build()
						.select(
								SelectField.valueOf("e", TsOrderForm.Fields.deliveryId)
						)
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