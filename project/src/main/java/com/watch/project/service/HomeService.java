package com.watch.project.service;

import org.springframework.stereotype.Service;

import com.watch.project.dto.HomeDTO;
import com.watch.project.repository.HomeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HomeService {
	private final HomeRepository repo;
	public HomeDTO getId() {
		
		return repo.getId();
	}
	
}
