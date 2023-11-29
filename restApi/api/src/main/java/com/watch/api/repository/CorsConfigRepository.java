package com.watch.api.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CorsConfigRepository {
	
	public String[] getAllowedOrigins();
	
}
