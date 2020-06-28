package com.mybatishelper.demo.common.module;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mybatishelper.core.annotation.Column;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity<T extends BaseEntity> implements Serializable {
    @Column(value = "`id`",primaryKey = true)
    private Long id;
    @Column("`create_date`")
    private Date createDate;
    @Column("`update_date`")
    private Date updateDate;
    @JsonIgnore
    @Column("`deltag`")
    private Boolean deltag;
}
