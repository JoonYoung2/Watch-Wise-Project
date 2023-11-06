package com.watch.project.service.member;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.watch.project.dto.MovieInfoDTO;
import com.watch.project.dto.PeopleInfoDetailDTO;
import com.watch.project.repository.MovieInfoRepository;
import com.watch.project.repository.PeopleInfoRepository;

@Service
public class MemberInfoService {
	@Autowired
	private MovieInfoRepository movieInfoRepo;
	@Autowired
	private PeopleInfoRepository peopleInfoRepo;
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

}
