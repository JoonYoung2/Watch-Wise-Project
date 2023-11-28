package com.watch.api.controller.ajax;

import java.io.UnsupportedEncodingException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.watch.api.scheduled.search.LiveSearchScheduled;
import com.watch.api.scheduled.update.AllUpdateScheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UpdateController {
	private final AllUpdateScheduled update;
	private final LiveSearchScheduled liveSearchUpdate;
	
	@GetMapping("/allUpdate")
	public String allUpdate() throws UnsupportedEncodingException {
		update.allUpdateProcess();
		return "완료";
	}
	
	@GetMapping("/liveSearchUpdate")
	public String liveSearchUpdate() {
		liveSearchUpdate.liveSearchUpdate();
		return "완료";
	}
	
}
