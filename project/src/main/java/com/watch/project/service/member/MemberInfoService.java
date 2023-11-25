package com.watch.project.service.member;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.PeopleInfoDetailDTO;
import com.watch.project.dto.WishListDTO;
import com.watch.project.repository.MovieInfoRepository;
import com.watch.project.repository.PeopleInfoRepository;
import com.watch.project.repository.WishListRepository;

@Service
public class MemberInfoService {
	@Autowired
	private MovieInfoRepository movieInfoRepo;
	@Autowired
	private PeopleInfoRepository peopleInfoRepo;
	@Autowired 
	private WishListRepository wishListRepo;
	@Autowired
	private HttpSession session;
	
	public List<MovieInfoDTO> getLikedMovieList(String userEmail) {
		List<MovieInfoDTO> likedMovieInfoList = new ArrayList<>();
		List<String> likedMovieIdList = movieInfoRepo.getLikedMovieIdByEmail(userEmail);
		for(String likedMovieId : likedMovieIdList) {
			MovieInfoDTO movieInfoDto = movieInfoRepo.getMovieInfoById(likedMovieId);
			String[] posterUrlList = movieInfoDto.getPosterUrl().split("\\|");
			String mainPosterUrl = posterUrlList[0];
			movieInfoDto.setPosterUrl(mainPosterUrl);
			likedMovieInfoList.add(movieInfoDto);
		}
		return likedMovieInfoList;
	}
	public List<PeopleInfoDetailDTO> getLikedActorList(String userEmail) {
		List<PeopleInfoDetailDTO> likedActorList = new ArrayList<>();
		List<Integer> likedActorIdList = peopleInfoRepo.getLikedActorIdByEmail(userEmail);
		for(Integer likedActorId : likedActorIdList) {
			PeopleInfoDetailDTO likedActorDto = peopleInfoRepo.getPeopleInfoDetailById(likedActorId);
			likedActorList.add(likedActorDto);
		}
		return likedActorList;
	}
	public String addMovieIntoWishList(String movieId) {
		MovieInfoDTO movieInfo = movieInfoRepo.getMovieInfoById(movieId);
		String msg = "\"" + movieInfo.getMovieNm()+"\"가 보고 싶은 영화 목록에 추가되었습니다.";
		WishListDTO dto = new WishListDTO();
		String email = (String)session.getAttribute("userEmail");
		//날짜 데이터
		Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(currentDate);
        //영화 정보 가져오기
        //포스터 메인 자르기
		String[] posterUrlList = movieInfo.getPosterUrl().split("\\|");
		String mainPosterUrl = posterUrlList[0];
        dto.setId(movieId+email);
        dto.setUserEmail(email);
        dto.setMovieId(movieId);
        dto.setMovieNm(movieInfo.getMovieNm());
        dto.setMoviePoster(mainPosterUrl);
        dto.setAddedDate(formattedDate);
		int result = wishListRepo.insertMovieInfoWishList(dto);
		if(result==0) {
			msg = "오류가 발생했습니다. 다시 시도해주세요.";
		}
		return msg;
	}
	public int checkWished(String movieId) {
		int result = 0;
		String id = movieId + (String)session.getAttribute("userEmail");
		try {
			WishListDTO dto = wishListRepo.getWishListDtoById(id);
			if(dto!=null) {
				result = 1;
			}else {
				result = 0;
			}
		}catch(NullPointerException e){
			result = 0;
		}
		return result;
	}
	public String deleteMovieFromWishList(String movieId) {
		MovieInfoDTO movieInfo = movieInfoRepo.getMovieInfoById(movieId);
		String msg = "\"" + movieInfo.getMovieNm()+"\"가 보고 싶은 영화 목록에서 삭제되었습니다.";
		String id = movieId + (String)session.getAttribute("userEmail");
		int result = wishListRepo.deleteMovieFromWishList(id);
		if(result==0) {
			msg = "오류가 발생했습니다. 다시 시도해주세요.";
		}
		return msg;
	}
	public List<WishListDTO> getWishList(String userEmail) {
		List<WishListDTO> dto = wishListRepo.getwishListDtoByEmail(userEmail);
		return dto;
	}

}
