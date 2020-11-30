package com.senior.hotel.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.senior.hotel.entity.Hospede;

public interface HospedeRepository extends JpaRepository<Hospede, Long> {

	@Query("SELECT distinct ho FROM Hospede ho LEFT JOIN CheckIn ch ON (ho.id = ch.hospede.id) "
			+ "WHERE ch.dataSaida is not null and :data < ch.dataEntrada or :data > ch.dataSaida order by ho")
	List<Hospede> findGuestsWithCheckInAndLeftHotel(LocalDateTime data);
	
	@Query("SELECT distinct ho FROM Hospede ho LEFT JOIN CheckIn ch ON (ho.id = ch.hospede.id) "
			+ "WHERE :data between ch.dataEntrada and ch.dataSaida order by ho")
	List<Hospede> findGuestsHosted(LocalDateTime data);
}
