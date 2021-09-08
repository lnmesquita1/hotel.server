package com.senior.hotel.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senior.hotel.entity.Hospede;
import com.senior.hotel.entity.HospedeValor;
import com.senior.hotel.response.Response;
import com.senior.hotel.service.HospedeService;
import com.senior.hotel.service.HospedeValorService;

@RestController
@RequestMapping("/api/hospede")
@CrossOrigin(origins = "*")
public class HospedeController {
	
	@Autowired
	private HospedeService hospedeService;
	
	@Autowired
	private HospedeValorService hospedeValorService;

	@PostMapping()
    public ResponseEntity<Response<Hospede>> createHospede(@RequestBody Hospede hospede) {
		Response<Hospede> response = new Response<Hospede>();
		try {
			Hospede persisted = hospedeService.save(hospede);
			response.setData(persisted);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
    }
	
	@GetMapping(value="left")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<List<Map<String, Object>>>> findGuestsWithCheckInAndLeftHotel() {
		Response<List<Map<String, Object>>> response = new Response<List<Map<String, Object>>>();
		List<Hospede> guests = hospedeService.findGuestsWithCheckInAndLeftHotel(LocalDateTime.now());
		
		List<Map<String, Object>> retorno = this.createValueGuests(guests);
		
		response.setData(retorno);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value="all")
	public ResponseEntity<Response<List<Map<String, Object>>>> findAll() {
		Response<List<Map<String, Object>>> response = new Response<List<Map<String, Object>>>();
		List<Hospede> guests = hospedeService.findAll();
		List<Map<String, Object>> retorno = this.createValueGuests(guests);
		response.setData(retorno);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value="hosted")
	public ResponseEntity<Response<List<Map<String, Object>>>> findGuestsHosted() {
		Response<List<Map<String, Object>>> response = new Response<List<Map<String, Object>>>();
		List<Hospede> guests = hospedeService.findGuestsHosted(LocalDateTime.now());
		
		List<Map<String, Object>> retorno = this.createValueGuests(guests);
		
		response.setData(retorno);
		return ResponseEntity.ok(response);
	}
	
	private List<Map<String, Object>> createValueGuests(List<Hospede> guests) {
		List<Map<String, Object>> retorno = new ArrayList<>();
		guests.stream().forEach(guest -> {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("hospede", guest);
			List<HospedeValor> hospedeValor = hospedeValorService.findGuestValues(guest.getId());
			if (!hospedeValor.isEmpty()) {
				Double total = hospedeValor.stream().mapToDouble(i -> i.getValor()).sum();
				Double lastAccommodation = hospedeValor.get(hospedeValor.size()-1).getValor();
				item.put("total", total);
				item.put("last", lastAccommodation);
			}
			
			retorno.add(item);
		});
		return retorno;
	}
}
