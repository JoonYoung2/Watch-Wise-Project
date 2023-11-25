package com.watch.project.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.watch.project.dto.WishListDTO;

@Mapper
public interface WishListRepository {

	int insertMovieInfoWishList(WishListDTO dto);

	WishListDTO getWishListDtoById(String id);

	int deleteMovieFromWishList(String id);

	Integer getAmountOfWishedMovies(String userEmail);

	List<WishListDTO> getwishListDtoByEmail(String userEmail);

}
