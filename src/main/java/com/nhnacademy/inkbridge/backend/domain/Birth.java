package com.nhnacademy.inkbridge.backend.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import lombok.Getter;

@Getter
public class Birth {

	private final LocalDate value;

	public Birth(LocalDate value) {
		this.value = value;
	}

	public static Birth from(String value){
		return new Birth(new BirthDateParser().parse(value));
	}

	private static class BirthDateParser{

		private static final List<DateTimeFormatter> FORMATTERS = List.of(
			DateTimeFormatter.ofPattern("yyyyMMdd"),
			DateTimeFormatter.ofPattern("yyyy-MM-dd"),
			DateTimeFormatter.ofPattern("yyyy.MM.dd")
		);

		public LocalDate parse(String date) {
			for(DateTimeFormatter formatter : FORMATTERS) {
				try {
					return LocalDate.parse(date, formatter);
				}catch (DateTimeParseException ignored) {}
			}
			throw new BusinessException(ErrorMessage.DATE_FORMAT_NOT_SUPPORTED);
		}
	}
}
