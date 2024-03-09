package org.backend.bankwebapplication.dto.rates;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class DoubleDeserializer extends JsonDeserializer<Double> {

    @Override
    public Double deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JacksonException {
        String doubleString = parser.getText();
        if (doubleString.contains(",")) {
            doubleString = doubleString.replace(',', '.');
        }
        return Double.valueOf(doubleString);
    }
}