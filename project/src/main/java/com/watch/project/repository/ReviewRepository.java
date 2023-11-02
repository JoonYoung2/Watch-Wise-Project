package com.watch.project.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.watch.project.dto.CommentLikedUsersDTO;
import com.watch.project.dto.MovieReviewDTO;
import com.watch.project.dto.userInfo.ReviewListDTO;

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

	int increaseLikeCountForComment(MovieReviewDTO dto);

	int decreaseLikeCountForComment(MovieReviewDTO dto);

	int insertLikedUserInfo(CommentLikedUsersDTO commentDto);

	int deleteLikedUserInfo(CommentLikedUsersDTO commentDto);

	CommentLikedUsersDTO selectLikedComment(String commentLikedUsersDtoId);

	int selectCommentLikes(MovieReviewDTO dto);

	float getAverageRating(String movieId);

	List<MovieReviewDTO> selectReviewListByEmail(String userEmail);

	List<String> selectCommentIdByEmail(String userEmail);

	String getMovieIdById(String id);



//	int deleteFromMovieReview(String userEmail);////////////////////////////////////////////////////////////////

}
