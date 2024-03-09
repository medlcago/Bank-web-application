package org.backend.bankwebapplication.dto.rates;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyCurs {

    @JacksonXmlProperty(localName = "Date", isAttribute = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date date;

    @JacksonXmlProperty(localName = "name", isAttribute = true)
    private String name;

    @JacksonXmlProperty(localName = "Valute")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Valute> valutes;


    @Override
    public String toString() {
        return "DailyCurs{" +
                "date=" + date +
                ", name='" + name + '\'' +
                ", valutes=" + valutes +
                '}';
    }
}