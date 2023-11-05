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
	
<<<<<<< HEAD
	@Scheduled(cron = "10 21 19 * * *")
	public void A() {
		char[] x = {'가','나','ㄷ','ㄹ','ㅁ','ㅂ','ㅅ','ㅇ','ㅈ','ㅊ','ㅌ','ㅍ','ㅎ'};
		for(int i = 0; i < x.length; ++i) {
			log.info("x => {}", (int)x[i]);
		}
	}
	
	@Scheduled(cron = "40 1 15 * * *")
=======
	@Scheduled(cron = "0 3 12 * * *")
>>>>>>> 433374b6f7dea9bf25ed1b9eb2a42168757d9bca
	public void allUpdateProcess() throws UnsupportedEncodingException {
		dailyUpdate.allFindAndInsert();
		dailyUpdate.DailyMovieInsert();
		weeklyUpdate.WeeklyMovieInsert0();
		weeklyUpdate.WeeklyMovieInsert1();
		weeklyUpdate.WeeklyMovieInsert2();
	}
}
