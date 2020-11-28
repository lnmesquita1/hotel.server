package com.senior.hotel.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senior.hotel.entity.CheckIn;
import com.senior.hotel.entity.HospedeValor;
import com.senior.hotel.repository.CheckInRepository;
import com.senior.hotel.repository.HospedeValorRepository;
import com.senior.hotel.response.Response;

@RestController
@RequestMapping("/api/checkin")
@CrossOrigin(origins = "*")
public class CheckInController {
	
	@Autowired
	private CheckInRepository checkInRepository;
	
	@Autowired
	private HospedeValorRepository hospedeValorRepository;

	@PostMapping()
    public ResponseEntity<Response<CheckIn>> createCheckin(@RequestBody CheckIn checkIn) {
		Response<CheckIn> response = new Response<CheckIn>();
		try {
			HospedeValor hospedeValor = new HospedeValor();
			hospedeValor.setHospede(checkIn.getHospede());
			hospedeValor.setValor(getAccommodationValue(checkIn));
			hospedeValorRepository.save(hospedeValor);
			
			CheckIn persisted = checkInRepository.save(checkIn);
			response.setData(persisted);
			
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
    }
	
	public boolean isWeekend(LocalDate data) {
	    DayOfWeek d = data.getDayOfWeek();
	    return d == DayOfWeek.SATURDAY || d == DayOfWeek.SUNDAY;
	}
	
	private Double getAccommodationValue(CheckIn checkIn) {
		Double accomodationValue = 0.0;
		
		LocalDate data = checkIn.getDataEntrada();
		while(data.isBefore(checkIn.getDataSaida())) {
			Double dailyValue = isWeekend(data) ? 150.00 : 120.00;
			Double dailyIncrease = checkIn.isAdicionalVeiculo() ? (isWeekend(data) ? 20.00 : 15.00) : 0.0;
			dailyValue += dailyIncrease;
			accomodationValue += dailyValue;
			data = data.plusDays(1);
		}
		return accomodationValue;
	}
	
}
