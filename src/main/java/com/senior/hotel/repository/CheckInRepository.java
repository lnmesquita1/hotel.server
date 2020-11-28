package com.senior.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.senior.hotel.entity.CheckIn;

public interface CheckInRepository extends JpaRepository<CheckIn, Long> {

}
