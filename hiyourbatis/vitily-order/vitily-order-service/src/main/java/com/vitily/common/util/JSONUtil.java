package com.vitily.common.util;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.extern.slf4j.Slf4j;

/**
 * creator : whh-lether
 * date    : 2018/12/4 17:28
 * desc    :
 **/
@Slf4j
public class JSONUtil {
    private JSONUtil(){
        throw new AssertionError();
    }
    @JsonFilter("commonFilter")
    private static interface CommonFilterMixIn{}
    public static void wrapLongToStringObjectMapper(ObjectMapper mapper){
        SimpleModule module = new SimpleModule();
        module.addSerializer(Long.class, ToStringSerializer.instance);
        module.addSerializer(Long.TYPE, ToStringSerializer.instance);
        mapper.registerModule(module);
    }
    public static String toJSONString(Object object, String[] properties){
        String result = null;
        try{
            ObjectMapper mapper = new ObjectMapper();
            FilterProvider filterProvider = new SimpleFilterProvider().addFilter("commonFilter", SimpleBeanPropertyFilter.filterOutAllExcept(properties));
            mapper.setFilterProvider(filterProvider);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.addMixIn(Object.class,CommonFilterMixIn.class);
            wrapLongToStringObjectMapper(mapper);
            result = mapper.writeValueAsString(object);
        }catch (Exception ex){
            log.warn(ex.getMessage(),ex);
        }
        return result;
    }
    public static String toJSONString(Object object){
        String result = null;
        try{
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            wrapLongToStringObjectMapper(mapper);
            result =mapper.writeValueAsString(object);
        }catch (Exception ex){
            log.warn(ex.getMessage(),ex);
        }
        return result;
    }
    public static <T> T parseObject(String jsonStr,Class<T> clazz){
        T object = null;
        try{
            object = new ObjectMapper().readValue(jsonStr,clazz);
        }catch (Exception ex){
            log.warn(ex.getMessage(),ex);
        }
        return object;
    }
}
