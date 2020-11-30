package com.senior.hotel.service;

import org.springframework.stereotype.Component;

import com.senior.hotel.entity.CheckIn;

@Component
public interface CheckInService {

	CheckIn save(CheckIn checkIn);
	
}
