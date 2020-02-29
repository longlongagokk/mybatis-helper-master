package com.vitily.common.module;

import club.yourbatis.hi.annotation.Column;
import club.yourbatis.hi.annotation.PrimaryKey;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity<T extends BaseEntity> implements Serializable {
	@PrimaryKey()
	@Column("`id`")
	private Long id;
	@Column("`create_date`")
	private Date createDate;
	@Column("`update_date`")
	private Date updateDate;
	@JsonIgnore
	@Column("`deltag`")
	private Boolean deltag;
	public Long getId() {
		return id;
	}

	public T setId(Long id) {
		this.id = id;
		return (T)this;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public T setCreateDate(Date createDate) {
		this.createDate = createDate;
		return (T)this;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public T setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
		return (T)this;
	}

	public Boolean getDeltag() {
		return deltag;
	}

	public T setDeltag(Boolean deltag) {
		this.deltag = deltag;
		return (T)this;
	}
}
