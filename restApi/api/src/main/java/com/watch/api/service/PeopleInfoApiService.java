package com.watch.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.watch.api.dto.PeopleInfoDTO;
import com.watch.api.dto.PeopleInfoDetailDTO;
import com.watch.api.repository.PeopleInfoApiRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PeopleInfoApiService {
	private final PeopleInfoApiRepository repo;
	
	public void peopleInfoSave(PeopleInfoDTO dto) {
		repo.savePeopleInfo(dto);
	}
	
	public int getPeopleInfoById(int peopleId) {
		PeopleInfoDTO dto = repo.getPeopleInfoById(peopleId);
		if(dto == null) {
			return 0;
		}else
			return 1;
	}
	
	public List<PeopleInfoDTO> getPeopleInfoAll(){
		return repo.getPeopleInfoAll();
	}

	public void savePeopleInfoDetail(PeopleInfoDetailDTO dto) {
		repo.savePeopleInfoDetail(dto);
	}
	
	public List<PeopleInfoDetailDTO> getAllPeopleInfoDetail(){
		return repo.getAllPeopleInfoDetail();
	}
	
	public void updateProfileUrlByPeopleId(PeopleInfoDetailDTO dto) {
		repo.updateProfileUrlByPeopleId(dto);
	}

	public int checkMovieExsist(String query) {
		return repo.checkMovieExsist(query);
	}

	public void updateProfileUrlNanFromNull() {
		repo.updateProfileUrlNanFromNull();
	}

	public PeopleInfoDetailDTO getPeopleInfoDetailById(int peopleId) {
		return repo.getPeopleInfoDetailById(peopleId);
	}
	
}
