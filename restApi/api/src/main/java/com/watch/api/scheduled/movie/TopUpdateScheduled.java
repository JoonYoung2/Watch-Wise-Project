package com.watch.api.scheduled.movie;

import java.io.UnsupportedEncodingException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.watch.api.repository.MovieInfoApiRepository;
import com.watch.api.service.MovieInfoApiService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class TopUpdateScheduled {
	private final MovieDailyUpdateScheduled dailyUpdate;
	private final MovieWeeklyUpdateScheduled weeklyUpdate;
	
	@Scheduled(cron = "10 21 19 * * *")
	public void A() {
		char[] x = {'가','나','ㄷ','ㄹ','ㅁ','ㅂ','ㅅ','ㅇ','ㅈ','ㅊ','ㅌ','ㅍ','ㅎ'};
		for(int i = 0; i < x.length; ++i) {
			log.info("x => {}", (int)x[i]);
		}
	}
	
	@Scheduled(cron = "40 1 15 * * *")
	public void allUpdateProcess() throws UnsupportedEncodingException {
		dailyUpdate.allFindAndInsert();
		dailyUpdate.DailyMovieInsert();
		weeklyUpdate.WeeklyMovieInsert0();
		weeklyUpdate.WeeklyMovieInsert1();
		weeklyUpdate.WeeklyMovieInsert2();
	}
}
