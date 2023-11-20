package com.watch.project.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.watch.project.controller.HomeController;
import com.watch.project.dto.memberInfo.MyScoredListDTO;
import com.watch.project.dto.CommentLikedUsersDTO;
import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.MovieReviewDTO;
import com.watch.project.dto.admin.BlackListDTO;
import com.watch.project.dto.memberInfo.LikedCommentListDTO;
import com.watch.project.dto.memberInfo.ReviewListDTO;
import com.watch.project.repository.MovieInfoRepository;
import com.watch.project.repository.ReviewRepository;
import com.watch.project.repository.admin.AdminMemberRepository;
import com.watch.project.service.admin.MemberService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class ReviewService {
	@Autowired
	private ReviewRepository repo;
	@Autowired
	private HttpSession session;
	@Autowired
	private MovieInfoRepository movieInfoRepo;
	@Autowired 
	private AdminMemberRepository adminMemberRepo;


	public String insertOrUpdateScore(String movieId, float rating) {
		String msg = "";
		if(rating == 0.5){
			msg = "최악이에요";
		}else if(rating == 1.0){
			msg = "싫어요";
		}else if(rating == 1.5){
			msg = "재미없어요";
		}else if(rating == 2.0){
			msg = "별로예요";
		}else if(rating == 2.5){
			msg = "부족해요";
		}else if(rating == 3.0){
			msg = "보통이에요";
		}else if(rating == 3.5){
			msg = "볼만해요";
		}else if(rating == 4.0){
			msg = "재미있어요";
		}else if(rating == 4.5){
			msg = "훌륭해요";
		}else if(rating == 5.0){
			msg = "최고예요";
		}
		String pk = movieId+(String)session.getAttribute("userEmail");
		MovieReviewDTO result = repo.getInfoByPk(pk);
		if(result != null) {//이미 평가를 했던 사람
			result.setReviewScore(rating);
			int updateResult = repo.updateScore(result);
			if(updateResult != 1) {
				msg = "오류가 발생했습니다. 다시 시도해주세요.";
			}
		}else { //처음으로 평가하는 사람
			MovieReviewDTO rookie = new MovieReviewDTO();
			rookie.setId(pk);
			rookie.setMovieId(movieId);
			rookie.setUserEmail((String)session.getAttribute("userEmail"));
			rookie.setReviewScore(rating);
			rookie.setReviewComment("nan");
//			rookie.setReviewCommentDate("nan");

			int insertResult = repo.insertScore(rookie);
			if(insertResult != 1) {
				msg = "오류가 발생했습니다. 다시 시도해주세요.";
			}
		}
		return msg;
	}

	public String insertComment(MovieReviewDTO dto) {//movieId, reviewComment
		String msg = "저장되었습니다.";
		if(dto.getReviewComment() == null || dto.getReviewComment().equals("")) {
			msg ="코멘트를 입력해주세요.";
			return msg;
		}
		String pk = dto.getMovieId()+(String)session.getAttribute("userEmail"); //movie_review 테이블의 id
		float existance = checkScore(pk);
		
		 //comment가 nan인지 판별
		String commentNanCh = checkCommentNan(pk);//null 이다, catch에 걸린다, 아예 없으니 insert/ nan이다, 잘 나온다 update 
		
				
		String dateStr = getDate();
		
		dto.setId(pk);
		dto.setUserEmail((String)session.getAttribute("userEmail"));
		dto.setReviewScore(existance);
		dto.setReviewCommentDate(dateStr);
		
		if(existance > 0 ||commentNanCh != null) {//평점을 먼저한 사람 => 이미 데이터 행이 있음. / update
			int updateResult = repo.updateForComment(dto);
			if(updateResult != 1) {
				msg = "오류가 발생했습니다. 다시 시도해주세요.";
			}
		} else {//코멘트를 먼저하는 사람 => 데이터 행 없음. insert
			int storageResult = repo.insertForComment(dto);
			if(storageResult != 1) {
				msg = "오류가 발생했습니다. 다시 시도해주세요.";
			}
		}
		return msg;
	}

	private String checkCommentNan(String pk) {
		String result = null;
		try {
			MovieReviewDTO comment = repo.getComment(pk); //null 이다, catch에 걸린다, 아예 없으니 insert/ nan이다, 잘 나온다 update 
			result = comment.getReviewComment();
		}catch(Exception e){
			System.out.println("오류발생");
		}
		return result;
	}

	private String getDate() {
		Date currentDate = new Date(); //Fri Oct 27 21:35:40 KST 2023
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// Date를 ISO 8601 형식의 문자열로 변환
		String dateStr = dateFormat.format(currentDate);//2023-10-27T21:35:40Z
		return dateStr;
	}

	private float checkScore(String id) {
		float reviewScore = 0;
		try {
			reviewScore = repo.getStoredScore(id);	
			System.out.println("try문 내부의 reviewScore : "+reviewScore);
		}catch(Exception e){
			System.out.println("에러 발생");
		}
			return reviewScore;
	}

	public List<MovieReviewDTO> getEveryCommentForThisMovie(String movieId) {
		List<MovieReviewDTO> comments = repo.selectComments(movieId);
		for(MovieReviewDTO comment : comments) {
			String CommentIdPlusUserEmail = comment.getId() + (String)session.getAttribute("userEmail");
			
			CommentLikedUsersDTO likedComment = repo.selectLikedComment(CommentIdPlusUserEmail);
			if(likedComment != null) {
				comment.setIsLiked(1); //좋아한 코멘트
			}else {
				comment.setIsLiked(0); //좋아하지 않은 코멘트
			}
			
			BlackListDTO blackListDto = adminMemberRepo.checkIfReported(CommentIdPlusUserEmail);
			if(blackListDto != null) {
				comment.setIsReported(1); //신고한 코멘트
			}else {
				comment.setIsReported(0); //신고하지 않은 코멘트
			}
		}
		return comments;
	}

	public MovieReviewDTO getComment(String movieId) {
		String pkId = movieId + (String)session.getAttribute("userEmail");
		MovieReviewDTO reviewComment = repo.getComment(pkId);
		return reviewComment;
	}

	public String deleteComment(String id) {
		String msg = "해당 코멘트가 삭제되었습니다.";
		int deleteResult = repo.deleteComment(id);
		repo.deleteCommentLike(id);
		if(deleteResult != 1) {
			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
			msg = "오류가 발생했습니다. 다시 시도해주세요.";
		}
		return msg;
	}

	public String modifyComment(MovieReviewDTO dto) {
		String msg = "코멘트 수정이 완료되었습니다.";
		String id = dto.getMovieId()+(String)session.getAttribute("userEmail");
		String dateStr = getDate();
		
		dto.setId(id);
		dto.setUserEmail((String)session.getAttribute("userEmail"));
		dto.setReviewCommentDate(dateStr);

		int updateResult = repo.updateForComment(dto);
		if(updateResult != 1) {
			msg = "오류가 발생했습니다. 다시 시도해주세요.";
		}
		return msg;
	}

	public int increaseLikeCountForComment(MovieReviewDTO dto, CommentLikedUsersDTO commentDto) {//id, movieId, userEmail
		String msg="";
		int updateResult = repo.increaseLikeCountForComment(dto);
		int insertResult = repo.insertLikedUserInfo(commentDto);
		int commentLikeCount = repo.selectCommentLikes(dto);
		if(updateResult != 1||insertResult !=1) {
			msg = "오류가 발생했습니다. 다시 시도해주세요.";
		}
		return commentLikeCount;
	}

	public int decreaseLikeCountForComment(MovieReviewDTO dto, CommentLikedUsersDTO commentDto) {//id, movieId, userEmail
		String msg="";
		int updateResult = repo.decreaseLikeCountForComment(dto);
		int deleteResult = repo.deleteLikedUserInfo(commentDto);
		int commentLikeCount = repo.selectCommentLikes(dto);
		if(updateResult != 1 || deleteResult != 1) {
			msg = "오류가 발생했습니다. 다시 시도해주세요.";
		}
		return commentLikeCount;
	}

	public float getAverageRating(String movieId) {
		float averageRating = 0.0f;
		try {
			 averageRating = repo.getAverageRating(movieId);			
		}catch(NullPointerException e) {
			
		}catch(BindingException e) {
			
		}
		return averageRating;
	}

	public List<ReviewListDTO> getReviewList(String userEmail) {
		List<MovieReviewDTO> reviewInfoList = repo.selectReviewListByEmail(userEmail);//해당 유저의 모든 리뷰 조회
		List<ReviewListDTO> reviewList = new ArrayList<>();
		for(MovieReviewDTO movie : reviewInfoList) {
			CommentLikedUsersDTO likedComment = new CommentLikedUsersDTO();
			Map<String, String> box = new HashMap<>();
			box.put("userEmail", userEmail);
			box.put("id", movie.getId());
			try{
				likedComment = repo.chIfLikedComment(box);
			}catch(Exception e) {
				likedComment = null;
			}
			MovieInfoDTO movieInfo = movieInfoRepo.getMovieInfoById(movie.getMovieId()); //리뷰의 영화 모든 정보 조회
			String[] mainPosterList = movieInfo.getPosterUrl().split("\\|");
			String mainPoster = mainPosterList[0];
			ReviewListDTO tempDto = new ReviewListDTO();
			tempDto.setId(movie.getId());
			tempDto.setMovieId(movie.getMovieId());
			tempDto.setUserEmail(movie.getUserEmail());
			tempDto.setReviewScore(movie.getReviewScore());
			tempDto.setReviewComment(movie.getReviewComment());
			tempDto.setReviewCommentDate(movie.getReviewCommentDate());
			tempDto.setReviewCommentLikes(movie.getReviewCommentLikes());
			tempDto.setMovieNm(movieInfo.getMovieNm());
			tempDto.setMovieNmEn(movieInfo.getMovieNmEn());
			tempDto.setGenreNm(movieInfo.getGenreNm());
			if(likedComment != null) {
				tempDto.setLiked(1);
			}else {
				tempDto.setLiked(0);
			}
			tempDto.setPosterUrl(mainPoster);
			reviewList.add(tempDto);
		}
		return reviewList;
	}

	public List<LikedCommentListDTO> getLikedCommentList(String userEmail) {
		List<String> commentIdList = repo.selectCommentIdByEmail(userEmail);//로그인한 유저가 좋아요한 코멘트들의 아이디
		List<LikedCommentListDTO> FinalDtoList = new ArrayList<>();
		List<String> movieIdList = new ArrayList<>();
		Map<Integer, String> map = new HashMap<>();
		
		int cnt = 0;
		for(String id : commentIdList) {
			log.info("id => {}",id);
			if(map.containsValue(id) == false) {
					map.put(cnt++, id);					
			}
			String movieId = repo.getMovieIdById(id);
			if(!(movieIdList.contains(movieId))) {
				movieIdList.add(movieId);
			}
		}
		
		log.info("{}", commentIdList.size());
		log.info("{}", movieIdList.size());
		log.info("{}",map.size());
		
		for(String movieId : movieIdList) {
			MovieInfoDTO movieInfo = movieInfoRepo.getMovieInfoById(movieId);
			String posterUrl = movieInfo.getPosterUrl().split("\\|")[0];
			movieInfo.setPosterUrl(posterUrl);
			List<MovieReviewDTO> likedCommentsInfo = new ArrayList<>();
			for(int i = 0; i < map.size(); ++i) {
				Map<String, String> map2 = new HashMap<>();
				map2.put("id", map.get(i));
				map2.put("movieId", movieId);
				List<MovieReviewDTO> list = repo.getCommentByMovieId(map2);//영화 하나 당 달린 코멘트들 리스트
				if(list.size() == 1) {
					likedCommentsInfo.add(list.get(0));
				}
			}
			LikedCommentListDTO finalTempDto = LikedCommentListDTO.builder()
					.movieReviewDto(likedCommentsInfo)
					.movieInfoDto(movieInfo)
					.build();
			
			log.info("review service final DTO inside => {}", movieInfo.getMovieNm());

			FinalDtoList.add(finalTempDto);
		}
		log.info("review service final DTO => {}", FinalDtoList);

		return FinalDtoList;
	}

	public String updateNewComment(MovieReviewDTO dto) {
		String msg = "코멘트 수정이 완료되었습니다.";
		String dateStr = getDate();
		dto.setReviewCommentDate(dateStr);
		System.out.println("dto.getReviewComment()" + dto.getReviewComment() );
		System.out.println("dto.getreviewCommentDate()" + dto.getReviewCommentDate() );
		System.out.println("dto.getid()" + dto.getId() );
		int result = repo.updateForComment(dto);
		
		if(result != 1) {
			msg = "오류가 발생했습니다. 다시 시도해주세요.";
			System.out.println("result가 1이 아님. 오류 발생.");
		}
		return msg;
	}

//	public List<MyScoredListDTO> getMyScoredMovieList(String userEmail) {
//		List<MyScoredListDTO> finalList = new ArrayList<>();
//		List<MovieReviewDTO> reviewDtoList = repo.getMoveReviewDTOWhereScoreIsNotZero(userEmail);
//		for( MovieReviewDTO reviewDto : reviewDtoList ) {
//			String movieId = reviewDto.getMovieId();
//			MovieInfoDTO movieInfoDto = movieInfoRepo.getMovieInfoById(movieId);
//			MyScoredListDTO tempDto = MyScoredListDTO.builder()
//					.movieReviewDto(reviewDto)
//					.movieInfoDto(movieInfoDto).build();
//			finalList.add(tempDto);		
//		}
//		return finalList;
//	}

	public List<MyScoredListDTO> getScoreFive(String userEmail) { //스코어가 5.0인 애들
		List<MyScoredListDTO> finalList = new ArrayList<>();
		List<MovieReviewDTO> listFive = repo.getMoveReviewDtoWhereScoreIsFive(userEmail);
		for( MovieReviewDTO movieReviewFive : listFive ) {
			String movieId = movieReviewFive.getMovieId();
			MovieInfoDTO movieInfoDto = movieInfoRepo.getMovieInfoById(movieId);
			String posters[] = movieInfoDto.getPosterUrl().split("\\|");
			movieInfoDto.setPosterUrl(posters[0]);
			MyScoredListDTO tempDto = MyScoredListDTO.builder()
					.movieReviewDto(movieReviewFive)
					.movieInfoDto(movieInfoDto).build();
			finalList.add(tempDto);
		}
		return finalList;
	}

	public List<MyScoredListDTO> getScoreFourPointFive(String userEmail) { //스코어가 4.5인 애들
		List<MyScoredListDTO> finalList = new ArrayList<>();
		List<MovieReviewDTO> listFourPointFive = repo.getMoveReviewDtoWhereScoreIsFourPointFive(userEmail);
		for( MovieReviewDTO movieReviewFourPointFive : listFourPointFive ) {
			String movieId = movieReviewFourPointFive.getMovieId();
			MovieInfoDTO movieInfoDto = movieInfoRepo.getMovieInfoById(movieId);
			String posters[] = movieInfoDto.getPosterUrl().split("\\|");
			movieInfoDto.setPosterUrl(posters[0]);
			MyScoredListDTO tempDto = MyScoredListDTO.builder()
					.movieReviewDto(movieReviewFourPointFive)
					.movieInfoDto(movieInfoDto).build();
			finalList.add(tempDto);
		}
		return finalList;
	}

	public List<MyScoredListDTO> getScoreFour(String userEmail) { //스코어가 4.0인 애들
		List<MyScoredListDTO> finalList = new ArrayList<>();
		List<MovieReviewDTO> listFour = repo.getMoveReviewDtoWhereScoreIsFour(userEmail);
		for( MovieReviewDTO movieReviewFour : listFour ) {
			String movieId = movieReviewFour.getMovieId();
			MovieInfoDTO movieInfoDto = movieInfoRepo.getMovieInfoById(movieId);
			String posters[] = movieInfoDto.getPosterUrl().split("\\|");
			movieInfoDto.setPosterUrl(posters[0]);
			MyScoredListDTO tempDto = MyScoredListDTO.builder()
					.movieReviewDto(movieReviewFour)
					.movieInfoDto(movieInfoDto).build();
			finalList.add(tempDto);
		}
		return finalList;
	}

	public List<MyScoredListDTO> getScoreThreePointFive(String userEmail) { //스코어가 3.5인 애들
		List<MyScoredListDTO> finalList = new ArrayList<>();
		List<MovieReviewDTO> listThreePointFive = repo.getMoveReviewDtoWhereScoreIsThreePointFive(userEmail);
		for( MovieReviewDTO movieReviewThreePointFive : listThreePointFive ) {
			String movieId = movieReviewThreePointFive.getMovieId();
			MovieInfoDTO movieInfoDto = movieInfoRepo.getMovieInfoById(movieId);
			String posters[] = movieInfoDto.getPosterUrl().split("\\|");
			movieInfoDto.setPosterUrl(posters[0]);
			MyScoredListDTO tempDto = MyScoredListDTO.builder()
					.movieReviewDto(movieReviewThreePointFive)
					.movieInfoDto(movieInfoDto).build();
			finalList.add(tempDto);
		}
		return finalList;
	}

	public List<MyScoredListDTO> getScoreThree(String userEmail) { //스코어가 3.0인 애들
		List<MyScoredListDTO> finalList = new ArrayList<>();
		List<MovieReviewDTO> listThree = repo.getMoveReviewDtoWhereScoreIsThree(userEmail);
		for( MovieReviewDTO movieReviewThree : listThree ) {
			String movieId = movieReviewThree.getMovieId();
			MovieInfoDTO movieInfoDto = movieInfoRepo.getMovieInfoById(movieId);
			String posters[] = movieInfoDto.getPosterUrl().split("\\|");
			movieInfoDto.setPosterUrl(posters[0]);
			MyScoredListDTO tempDto = MyScoredListDTO.builder()
					.movieReviewDto(movieReviewThree)
					.movieInfoDto(movieInfoDto).build();
			finalList.add(tempDto);
		}
		return finalList;
	}

	public List<MyScoredListDTO> getScoreTwoPointFive(String userEmail) { //스코어가 2.5인 애들
		List<MyScoredListDTO> finalList = new ArrayList<>();
		List<MovieReviewDTO> listTwoPointFive = repo.getMoveReviewDtoWhereScoreIsTwoPointFive(userEmail);
		for( MovieReviewDTO movieReviewTwoPointFive : listTwoPointFive ) {
			String movieId = movieReviewTwoPointFive.getMovieId();
			MovieInfoDTO movieInfoDto = movieInfoRepo.getMovieInfoById(movieId);
			String posters[] = movieInfoDto.getPosterUrl().split("\\|");
			movieInfoDto.setPosterUrl(posters[0]);
			MyScoredListDTO tempDto = MyScoredListDTO.builder()
					.movieReviewDto(movieReviewTwoPointFive)
					.movieInfoDto(movieInfoDto).build();
			finalList.add(tempDto);
		}
		return finalList;
	}

	public List<MyScoredListDTO> getScoreTwo(String userEmail) { //스코어가 2.0인 애들
		List<MyScoredListDTO> finalList = new ArrayList<>();
		List<MovieReviewDTO> listTwo = repo.getMoveReviewDtoWhereScoreIsTwo(userEmail);
		for( MovieReviewDTO movieReviewTwo : listTwo ) {
			String movieId = movieReviewTwo.getMovieId();
			MovieInfoDTO movieInfoDto = movieInfoRepo.getMovieInfoById(movieId);
			String posters[] = movieInfoDto.getPosterUrl().split("\\|");
			movieInfoDto.setPosterUrl(posters[0]);
			MyScoredListDTO tempDto = MyScoredListDTO.builder()
					.movieReviewDto(movieReviewTwo)
					.movieInfoDto(movieInfoDto).build();
			finalList.add(tempDto);
		}
		return finalList;
	}

	public List<MyScoredListDTO> getScoreOnePointFive(String userEmail) { //스코어가 1.5인 애들
		List<MyScoredListDTO> finalList = new ArrayList<>();
		List<MovieReviewDTO> listOnePointFive = repo.getMoveReviewDtoWhereScoreIsOnePointFive(userEmail);
		for( MovieReviewDTO movieReviewOnePointFive : listOnePointFive ) {
			String movieId = movieReviewOnePointFive.getMovieId();
			MovieInfoDTO movieInfoDto = movieInfoRepo.getMovieInfoById(movieId);
			String posters[] = movieInfoDto.getPosterUrl().split("\\|");
			movieInfoDto.setPosterUrl(posters[0]);
			MyScoredListDTO tempDto = MyScoredListDTO.builder()
					.movieReviewDto(movieReviewOnePointFive)
					.movieInfoDto(movieInfoDto).build();
			finalList.add(tempDto);
		}
		return finalList;
	}

	public List<MyScoredListDTO> getScoreOne(String userEmail) { //스코어가 1.0인 애들
		List<MyScoredListDTO> finalList = new ArrayList<>();
		List<MovieReviewDTO> listOne = repo.getMoveReviewDtoWhereScoreIsOne(userEmail);
		for( MovieReviewDTO movieReviewOne : listOne ) {
			String movieId = movieReviewOne.getMovieId();
			MovieInfoDTO movieInfoDto = movieInfoRepo.getMovieInfoById(movieId);
			String posters[] = movieInfoDto.getPosterUrl().split("\\|");
			movieInfoDto.setPosterUrl(posters[0]);
			MyScoredListDTO tempDto = MyScoredListDTO.builder()
					.movieReviewDto(movieReviewOne)
					.movieInfoDto(movieInfoDto).build();
			finalList.add(tempDto);
		}
		return finalList;
	}

	public List<MyScoredListDTO> getScoreZeroPointFive(String userEmail) { //스코어가 0.5인 애들
		List<MyScoredListDTO> finalList = new ArrayList<>();
		List<MovieReviewDTO> listZeroPointFive = repo.getMoveReviewDtoWhereScoreIsZeroPointFive(userEmail);
		for( MovieReviewDTO movieReviewZeroPointFive : listZeroPointFive ) {
			String movieId = movieReviewZeroPointFive.getMovieId();
			MovieInfoDTO movieInfoDto = movieInfoRepo.getMovieInfoById(movieId);
			String posters[] = movieInfoDto.getPosterUrl().split("\\|");
			movieInfoDto.setPosterUrl(posters[0]);
			MyScoredListDTO tempDto = MyScoredListDTO.builder()
					.movieReviewDto(movieReviewZeroPointFive)
					.movieInfoDto(movieInfoDto).build();
			finalList.add(tempDto);
		}
		return finalList;
	}

	public Map<String, List<MyScoredListDTO>> getScoredMovieListByScore(String userEmail) {
		
		List<MyScoredListDTO> listFive = getScoreFive(userEmail); //스코어 5.0인 애들
		List<MyScoredListDTO> listFourPointFive = getScoreFourPointFive(userEmail); //스코어 4.5인 애들
		List<MyScoredListDTO> listFour = getScoreFour(userEmail); //스코어 4.0인 애들
		List<MyScoredListDTO> listThreePointFive = getScoreThreePointFive(userEmail); //스코어 3.5인 애들
		List<MyScoredListDTO> listThree = getScoreThree(userEmail); //스코어 3.0인 애들
		List<MyScoredListDTO> listTwoPointFive = getScoreTwoPointFive(userEmail); //스코어 2.5인 애들
		List<MyScoredListDTO> listTwo = getScoreTwo(userEmail); //스코어 2.0인 애들
		List<MyScoredListDTO> listOnePointFive = getScoreOnePointFive(userEmail); //스코어 1.5인 애들
		List<MyScoredListDTO> listOne = getScoreOne(userEmail); //스코어 1.0인 애들
		List<MyScoredListDTO> listZeroPointFive = getScoreZeroPointFive(userEmail); //스코어 0.5인 애들  143
		
	    Map<String, List<MyScoredListDTO>> scoreLists = new LinkedHashMap<>();
	    
	    scoreLists.put("listFive", listFive); //5.0
	    scoreLists.put("listFourPointFive", listFourPointFive); //4.5
	    scoreLists.put("listFour", listFour); //4.0
	    scoreLists.put("listThreePointFive", listThreePointFive); //3.5
	    scoreLists.put("listThree", listThree); //3.0
	    scoreLists.put("listTwoPointFive", listTwoPointFive); //2.5
	    scoreLists.put("listTwo", listTwo); //2.0
	    scoreLists.put("listOnePointFive", listOnePointFive); //1.5
	    scoreLists.put("listOne", listOne); //1.0
	    scoreLists.put("listZeroPointFive", listZeroPointFive); //0.5
	    
	    return scoreLists;
	}

}
