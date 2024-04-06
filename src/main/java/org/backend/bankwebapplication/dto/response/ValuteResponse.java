package org.backend.bankwebapplication.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.backend.bankwebapplication.utils.deserializers.DoubleDeserializer;


@JsonIgnoreProperties(ignoreUnknown = true)
public record ValuteResponse(@JacksonXmlProperty(localName = "ID", isAttribute = true) String id,
                             @JacksonXmlProperty(localName = "NumCode") String numCode,
                             @JacksonXmlProperty(localName = "CharCode") String charCode,
                             @JacksonXmlProperty(localName = "Nominal") int nominal,
                             @JacksonXmlProperty(localName = "Name") String name,
                             @JacksonXmlProperty(localName = "Value") @JsonDeserialize(using = DoubleDeserializer.class) Double value) {

}