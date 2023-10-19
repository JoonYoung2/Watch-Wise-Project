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
import com.watch.api.service.DocidResearchApiService;
import com.watch.api.service.MovieInfoApiService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class MovieDocidResearchScheduled {
	private final DocidResearchApiService service;
	
//	@Scheduled(fixedDelay = 1000)
//	@Scheduled(cron = "30 19 22 * * *")
	public void nanDocidResearch() throws UnsupportedEncodingException {
		int cnt = 0;
		log.info("MovieDocidResearchScheduled - nanDocidResearch() 실행");
		
		List<MovieInfoDTO> list = service.getDocidNan();
		log.info("{}", list.size());
		for(int i = list.size()-1; i >= 0; --i) {
			String movieId = list.get(i).getMovieId();
			String movieNm = list.get(i).getMovieNm();
			String openDt = list.get(i).getOpenDt();
			String prdtYear = list.get(i).getPrdtYear();
			int tmp = cnt;
			cnt += service.docidResearch(movieId, movieNm, openDt, prdtYear);
			if(tmp != cnt) {
				log.info("{} 영화 docidUpdate 갱신에 성공!", movieNm);
				log.info("{} 업뎃 성공", cnt );
			}else {
				log.info("docidUpdate 갱신 실패!");
				log.info("{} 업뎃 성공", cnt );
			}
		}
		
		log.info("갱신 성공 횟수 : {}", cnt);
	}
}
