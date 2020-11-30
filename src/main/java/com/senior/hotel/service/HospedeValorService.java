package com.senior.hotel.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.senior.hotel.entity.HospedeValor;

@Component
public interface HospedeValorService {

	List<HospedeValor> findGuestValues(Long id);
}
