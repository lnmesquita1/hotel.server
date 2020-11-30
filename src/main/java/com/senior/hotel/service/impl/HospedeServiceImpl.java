package com.senior.hotel.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senior.hotel.entity.Hospede;
import com.senior.hotel.repository.HospedeRepository;
import com.senior.hotel.service.HospedeService;

@Service
public class HospedeServiceImpl implements HospedeService {
	
	@Autowired
	private HospedeRepository hospedeRepository;
	
	public List<Hospede> findGuestsWithCheckInAndLeftHotel(LocalDateTime data) {
		return hospedeRepository.findGuestsWithCheckInAndLeftHotel(data);
	}
	
	public List<Hospede> findGuestsHosted(LocalDateTime data) {
		return hospedeRepository.findGuestsHosted(data);
	}
	
	public List<Hospede> findAll() {
		return hospedeRepository.findAll();
	}
	
	public Hospede save(Hospede hospede) {
		return hospedeRepository.save(hospede);
	}

}
