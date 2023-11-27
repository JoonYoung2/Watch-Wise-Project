package com.watch.api.scheduled.update;

import java.io.UnsupportedEncodingException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.watch.api.scheduled.movie.MovieDailyUpdateScheduled;
import com.watch.api.scheduled.movie.MovieWeeklyUpdateScheduled;
import com.watch.api.scheduled.people.PeopleInfoUpdateScheduled;
import com.watch.api.scheduled.people.PeopleProfileUpdateScheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AllUpdateScheduled {
	private final MovieDailyUpdateScheduled dailyUpdate;
	private final MovieWeeklyUpdateScheduled weeklyUpdate;
	private final PeopleInfoUpdateScheduled peopleInfoUpdate;
	private final PeopleProfileUpdateScheduled peopleProfileUpdate;
	
	@Scheduled(cron = "0 0 6 * * *")
	public void allUpdateProcess() throws UnsupportedEncodingException {
		dailyUpdate.allFindAndInsert();
		dailyUpdate.DailyMovieInsert();
		weeklyUpdate.WeeklyMovieInsert0();
		weeklyUpdate.WeeklyMovieInsert1(); 
		weeklyUpdate.WeeklyMovieInsert2();
		peopleInfoUpdate.peopleAllSearchAndSave();
		peopleInfoUpdate.peopleDetailSearchAndSave();
		peopleProfileUpdate.peopleDetailSearchAndSave2();
	}
}
