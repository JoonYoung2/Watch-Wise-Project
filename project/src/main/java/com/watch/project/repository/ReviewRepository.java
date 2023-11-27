package com.watch.project.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.watch.project.dto.CommentLikedUsersDTO;
import com.watch.project.dto.MovieReviewDTO;
import com.watch.project.dto.memberInfo.ReviewListDTO;

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

	List<MovieReviewDTO> getCommentByMovieId(Map<String, String> map);

	Integer getAmountOfScoredOnes(String userEmail);

	Integer getAmountOfComments(String userEmail);

	Integer getAmountOfLikedComments(String userEmail);

	int deleteCommentLike(String id);

	CommentLikedUsersDTO chIfLikedComment(Map<String, String> box);

//	List<MovieReviewDTO> getMoveReviewDTOWhereScoreIsNotZero(String userEmail);

	List<MovieReviewDTO> getMoveReviewDtoWhereScoreIsFive(String userEmail);

	List<MovieReviewDTO> getMoveReviewDtoWhereScoreIsFourPointFive(String userEmail);

	List<MovieReviewDTO> getMoveReviewDtoWhereScoreIsFour(String userEmail);

	List<MovieReviewDTO> getMoveReviewDtoWhereScoreIsThreePointFive(String userEmail);

	List<MovieReviewDTO> getMoveReviewDtoWhereScoreIsThree(String userEmail);

	List<MovieReviewDTO> getMoveReviewDtoWhereScoreIsTwoPointFive(String userEmail);

	List<MovieReviewDTO> getMoveReviewDtoWhereScoreIsTwo(String userEmail);

	List<MovieReviewDTO> getMoveReviewDtoWhereScoreIsOnePointFive(String userEmail);

	List<MovieReviewDTO> getMoveReviewDtoWhereScoreIsOne(String userEmail);

	List<MovieReviewDTO> getMoveReviewDtoWhereScoreIsZeroPointFive(String userEmail);

	String getUserNmByEmail(String userEmail);

	int deleteReportedDatas(String id);

	MovieReviewDTO getCommentWithoutNanById(String pkId);

//	int updateNewComment(MovieReviewDTO dto);

//	int deleteFromMovieReview(String userEmail);////////////////////////////////////////////////////////////////
}
