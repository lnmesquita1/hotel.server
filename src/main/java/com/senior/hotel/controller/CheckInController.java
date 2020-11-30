package com.senior.hotel.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senior.hotel.entity.CheckIn;
import com.senior.hotel.entity.HospedeValor;
import com.senior.hotel.repository.HospedeValorRepository;
import com.senior.hotel.response.Response;
import com.senior.hotel.service.CheckInService;

@RestController
@RequestMapping("/api/checkin")
@CrossOrigin(origins = "*")
public class CheckInController {
	
	@Autowired
	private CheckInService checkInService;
	
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
			
			checkIn.setDataEntrada(checkIn.getDataEntrada().minusHours(3));
			checkIn.setDataSaida(checkIn.getDataSaida().minusHours(3));
			CheckIn persisted = checkInService.save(checkIn);
			response.setData(persisted);
			
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
    }
	
	private Double getAccommodationValue(CheckIn checkIn) {
		Double accomodationValue = 0.0;
		LocalDateTime localDateTimeSaida = checkIn.getDataSaida().minusHours(3);
		LocalDate localDateEntrada = checkIn.getDataEntrada().minusHours(3).toLocalDate();
		LocalDate localDateSaida = checkIn.getDataSaida().minusHours(3).toLocalDate();
		
		LocalDate data = localDateEntrada;
		while(data.isBefore(localDateSaida)) {
			accomodationValue += getDailyValue(data);
			accomodationValue += getDailyIncrease(data, checkIn.isAdicionalVeiculo());
			data = data.plusDays(1);
		}
		LocalDateTime dateAt1630 = localDateTimeSaida.toLocalDate().atTime(16, 30);
		if (localDateTimeSaida.isAfter(dateAt1630)) {
			accomodationValue += getDailyValue(localDateSaida);
			accomodationValue += getDailyIncrease(localDateSaida, checkIn.isAdicionalVeiculo());
		}
		return accomodationValue;
	}
	
	private Double getDailyValue(LocalDate data) {
		return isWeekend(data) ? 150.00 : 120.00;
	}
	
	private Double getDailyIncrease(LocalDate data, boolean adicionalVeiculo) {
		return adicionalVeiculo ? (isWeekend(data) ? 20.00 : 15.00) : 0.0;
	}
	
	private boolean isWeekend(LocalDate data) {
	    DayOfWeek d = data.getDayOfWeek();
	    return d == DayOfWeek.SATURDAY || d == DayOfWeek.SUNDAY;
	}
	
}
