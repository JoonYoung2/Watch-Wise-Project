package com.watch.project.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.watch.project.dto.MovieReviewDTO;

@Mapper
public interface ReviewRepository {

	MovieReviewDTO getInfoByPk(String pk);

	int updateScore(MovieReviewDTO result);

	int insertScore(MovieReviewDTO rookie);

	float getStoredScore(String id);

	int insertForComment(MovieReviewDTO dto);

	List<MovieReviewDTO> selectComments(String movieId);

	int updateForComment(MovieReviewDTO dto);

	MovieReviewDTO getComment(String pkId);

	int deleteComment(String id);

}
