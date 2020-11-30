package com.senior.hotel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senior.hotel.entity.CheckIn;
import com.senior.hotel.repository.CheckInRepository;
import com.senior.hotel.service.CheckInService;

@Service
public class CheckInServiceImpl implements CheckInService {

	@Autowired
	private CheckInRepository checkInRepository;
	
	@Override
	public CheckIn save(CheckIn checkIn) {
		return checkInRepository.save(checkIn);
	}
}
