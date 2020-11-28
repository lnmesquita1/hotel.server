package com.senior.hotel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.senior.hotel.entity.HospedeValor;

public interface HospedeValorRepository extends JpaRepository<HospedeValor, Long> {

	@Query("SELECT hv FROM HospedeValor hv WHERE hv.hospede.id = :id")
	List<HospedeValor> findGuestValues(Long id);
}
