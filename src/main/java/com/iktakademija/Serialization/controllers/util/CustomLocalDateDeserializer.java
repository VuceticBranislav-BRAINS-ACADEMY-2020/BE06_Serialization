package com.iktakademija.Serialization.controllers.util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class CustomLocalDateDeserializer extends StdDeserializer<LocalDate> {

	private static final long serialVersionUID = 3278515083694064664L;
	private static  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
	
	public CustomLocalDateDeserializer() {
		this(null);
	}

	public CustomLocalDateDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public LocalDate deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException {
		String date = p.getText();
		return LocalDate.parse(date, formatter);
	}
}
