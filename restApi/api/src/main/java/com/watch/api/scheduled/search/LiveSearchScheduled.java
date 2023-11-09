package com.watch.api.scheduled.search;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.watch.api.service.LiveSearchService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j 
public class LiveSearchScheduled {
	private final LiveSearchService service;
	
	/*
	 * 0시~2시까지 2시간동안은 실시간 검색 update가 진행되지 않고 이전 기록은 그대로 남긴다.
	 * 2시가 되면 당일날부터의 실시간 검색을 탐색하여 나타내어준다.
	 * 30분에 한번씩 업데이트가 된다.
	 */
	@Scheduled(fixedDelay = 60000 * 30) 
	public void liveSearchUpdate() {
		service.liveSearchUpdate();
	}
}
