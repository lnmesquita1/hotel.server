package com.senior.hotel.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.senior.hotel.entity.Hospede;

@Component
public interface HospedeService {
	
	List<Hospede> findGuestsWithCheckInAndLeftHotel(LocalDateTime data);
	
	List<Hospede> findGuestsHosted(LocalDateTime data);
	
	List<Hospede> findAll();
	
	Hospede save(Hospede hospede);

}
