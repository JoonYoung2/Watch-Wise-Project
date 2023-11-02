package com.watch.project.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public class HabitRecordResponseDTO {
	private List<HabitRecordResponseDTO> habitRecord;
//	public static HabitRecordResponseDTO fromEntity(Habit entity) {
//		HabitRecordResponseDTO dto = new HabitRecordResponseDTO();
//		
//		dto.habitRecord = entity.getHabitRecords().stram().
//				map(habitRecords -> HabitRecordResponseDTO.fromEntity(habitRecords))
//				.collect(Collectors.toList());
//		return dto;
//	}
}
