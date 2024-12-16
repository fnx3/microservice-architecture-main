package ru.otus.userapp.repository;


import javax.persistence.AttributeConverter;
import java.util.UUID;

public class UuidConverter implements AttributeConverter<UUID, String> {
    @Override
    public String convertToDatabaseColumn(UUID attribute) {
        return attribute.toString();
    }

    @Override
    public UUID convertToEntityAttribute(String dbData) {
        return UUID.fromString(dbData);
    }
}