package com.senior.hotel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senior.hotel.entity.HospedeValor;
import com.senior.hotel.repository.HospedeValorRepository;
import com.senior.hotel.service.HospedeValorService;

@Service
public class HospedeValorServiceImpl implements HospedeValorService {
	
	@Autowired
	private HospedeValorRepository hospedeValorRepository;
	
	@Override
	public List<HospedeValor> findGuestValues(Long id) {
		return hospedeValorRepository.findGuestValues(id);
	}

}
