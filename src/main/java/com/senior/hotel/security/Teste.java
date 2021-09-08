package com.senior.hotel.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Teste {

	public static void main(String[] args) {
		BCryptPasswordEncoder f = new BCryptPasswordEncoder();
		System.out.println(f.encode("123"));

	}

}
