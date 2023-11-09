package com.watch.api.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.watch.api.dto.LiveSearchDTO;
import com.watch.api.repository.LiveSearchRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LiveSearchService {
	private final LiveSearchRepository repo;
	
	public void liveSearchUpdate() {
		String date = getDate();
		int hour = Integer.parseInt(date.substring(11, 13));
		int liveSize = 10;
		
		/*
		 * 00시~1시59분까지는 update 중단
		 */
		if(hour == 0 || hour == 1) {
			repo.deleteLiveSearchData();
			return;
		}
		
		List<LiveSearchDTO> getLiveSearchDataList = repo.getLiveSearchDataList();
		List<LiveSearchDTO> getNewLiveSearchDataList = repo.getNewLiveSearchDataList(date.substring(0, 10)); 
		repo.deleteLiveSearchData();
		repo.deleteLiveSearch();
		for(int i = 0; i < getNewLiveSearchDataList.size(); ++i) {
			repo.insertLiveSearchData(getNewLiveSearchDataList.get(i));
		}
		
		
		if(getNewLiveSearchDataList.size() > liveSize) {
			int forSize = liveSize;
			insertLiveSearch(liveSize, getLiveSearchDataList, getNewLiveSearchDataList, forSize);
		}else {
			int forSize = getNewLiveSearchDataList.size();
			insertLiveSearch(liveSize, getLiveSearchDataList, getNewLiveSearchDataList, forSize);
		}
		
	}

	private void insertLiveSearch(int liveSize, List<LiveSearchDTO> getLiveSearchDataList,
			List<LiveSearchDTO> getNewLiveSearchDataList, int forSize) {
		int check = 0;
		for(int i = 0; i < forSize; ++i) {
			String content = getNewLiveSearchDataList.get(i).getContent();
			if(getLiveSearchDataList.size() != 0) {
				if(getLiveSearchDataList.size() > liveSize) {
					for(int j = 0; j < liveSize; ++j) {
						if(getLiveSearchDataList.get(j).getContent().equals(content)) {
							if(j != i) { 
								getNewLiveSearchDataList.get(i).setState("" + (j - i));
								check++;
							}else {
								getNewLiveSearchDataList.get(i).setState("0");
								check++;
							}
						}
					}						
				}else {
					for(int j = 0; j < getLiveSearchDataList.size(); ++j) {
						if(getLiveSearchDataList.get(j).getContent().equals(content)) {
							if(j != i) {
								getNewLiveSearchDataList.get(i).setState("" + (j - i));
								check++;
							}else {
								getNewLiveSearchDataList.get(i).setState("0");
								check++;
							}
						}
					}
				}
			}else {
				getNewLiveSearchDataList.get(i).setState("0");
				check++;
			}
			
			if(check == 0) {
				getNewLiveSearchDataList.get(i).setState("new");
			}
			check = 0;
			repo.insertLiveSearch(getNewLiveSearchDataList.get(i));
		}
	}

	private String getDate() {
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(currentDate);
	}
}
