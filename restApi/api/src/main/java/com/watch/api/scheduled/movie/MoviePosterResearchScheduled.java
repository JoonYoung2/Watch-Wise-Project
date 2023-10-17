package com.watch.api.scheduled.movie;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.watch.api.dto.MovieInfoDTO;
import com.watch.api.repository.MovieInfoApiRepository;
import com.watch.api.service.MovieInfoApiService;
import com.watch.api.service.PosterResearchApiService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class MoviePosterResearchScheduled {
	private final PosterResearchApiService service;
	
//	@Scheduled(fixedDelay = 1000)
//	@Scheduled(cron = "25 7 13 * * *")
	public void nanPosterResearch() throws UnsupportedEncodingException {
		int cnt = 0;
		log.info("MoviePosterResearch - nanPosterResearch() 실행");
		
		List<MovieInfoDTO> list = service.getPosterNan();
		log.info("{}", list.size());
		for(int i = 0; i < list.size(); ++i) {
			String movieId = list.get(i).getMovieId();
			String movieNm = list.get(i).getMovieNm();
			String openDt = list.get(i).getOpenDt();
			int tmp = cnt;
			cnt += service.posterResearch(movieId, movieNm, openDt);
			if(tmp != cnt) {
				log.info("{} 영화 posterUpdate 갱신에 성공!", movieNm);
				log.info("{} 업뎃 성공", cnt );
			}else {
				log.info("posterUpdate 갱신 실패!");
				log.info("{} 업뎃 성공", cnt );
			}
		}
		
		log.info("갱신 성공 횟수 : {}", cnt);
	}
	
//	@Scheduled(fixedDelay = 1000)
//	@Scheduled(cron = "40 47 18 * * *")
	public void nanPosterResearch2() throws UnsupportedEncodingException {
		int cnt = 0;
		log.info("MoviePosterResearch - nanPosterResearch2() 실행");
		
		List<MovieInfoDTO> list = service.getPosterNan();
		log.info("{}", list.size());
		for(int i = 0; i < list.size(); ++i) {
			String movieId = list.get(i).getMovieId();
			String movieNm = list.get(i).getMovieNm();
			String openDt = list.get(i).getOpenDt();
			String prdtYear = list.get(i).getPrdtYear();
			int tmp = cnt;
			cnt += service.posterResearch(movieId, movieNm, openDt, prdtYear);
			if(tmp != cnt) {
				log.info("{} 영화 posterUpdate 갱신에 성공!", movieNm);
				log.info("{} 업뎃 성공", cnt );
			}else {
				log.info("posterUpdate 갱신 실패!");
				log.info("{} 업뎃 성공", cnt );
			}
		}
		
		log.info("갱신 성공 횟수 : {}", cnt);
	}
}
