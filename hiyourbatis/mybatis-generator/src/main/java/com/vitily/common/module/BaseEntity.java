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
}
