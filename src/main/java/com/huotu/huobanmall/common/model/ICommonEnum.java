package com.huotu.huobanmall.common.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.huotu.huobanmall.config.EnumJsonSerializer;


/**
 * 常规枚举类
 * 对app来说 这些信息都会以name,value为key的json展示
 */
@JsonSerialize(converter = EnumJsonSerializer.class)
public interface ICommonEnum {
	int getValue();

	String getName();

}


