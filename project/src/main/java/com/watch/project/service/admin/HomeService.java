package com.watch.project.service.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.watch.project.dto.admin.LiveSearchDTO;
import com.watch.project.repository.admin.AdminHomeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service("adminHomeSerivce")
@RequiredArgsConstructor
@Slf4j
public class HomeService {
	
	private final AdminHomeRepository repo;
	
	public List<LiveSearchDTO> getLiveSearchDataList(){
		String date = getDate().substring(0, 10);
		return repo.getLiveSearchDataList(date);
	}
	
	private String getDate() {
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(currentDate);
	}
}
