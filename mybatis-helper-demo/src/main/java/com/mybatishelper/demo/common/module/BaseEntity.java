package com.mybatishelper.demo.common.module;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mybatishelper.core.annotation.Column;
import com.mybatishelper.core.annotation.Primary;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity<T extends BaseEntity> implements Serializable {
	@Primary
	private Long id;
	private Date createDate;
	private Date updateDate;
	@JsonIgnore
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
