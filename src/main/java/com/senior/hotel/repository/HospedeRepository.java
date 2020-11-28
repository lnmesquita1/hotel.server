package com.senior.hotel.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.senior.hotel.entity.Hospede;

public interface HospedeRepository extends JpaRepository<Hospede, Long> {

	@Query("SELECT distinct ch.hospede FROM CheckIn ch WHERE :data < ch.dataEntrada or :data > ch.dataSaida")
	List<Hospede> findGuestsWithCheckInAndLeftHotel(LocalDate data);
	
	@Query("SELECT ch.hospede FROM CheckIn ch WHERE :data between ch.dataEntrada and ch.dataSaida")
	List<Hospede> findGuestsHosted(LocalDate data);
}
