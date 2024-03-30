package org.backend.bankwebapplication.dto.rates;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.time.LocalDate;
import java.util.List;


public record DailyCursResponse(
        @JacksonXmlProperty(localName = "Date", isAttribute = true) @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy") LocalDate date,
        @JacksonXmlProperty(localName = "Valute") @JacksonXmlElementWrapper(useWrapping = false) List<ValuteResponse> valutes) {

}