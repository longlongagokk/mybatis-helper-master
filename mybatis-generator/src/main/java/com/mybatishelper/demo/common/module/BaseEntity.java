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
}
