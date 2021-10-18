package com.rsjava.specificationsubquery.person.mapper;

import com.rsjava.specificationsubquery.car.mapper.CarMapper;
import com.rsjava.specificationsubquery.person.model.PersonEntity;
import com.rsjava.specificationsubquery.person.request.PersonRequest;
import com.rsjava.specificationsubquery.person.response.PersonResponse;

import java.util.stream.Collectors;


public class PersonMapper {

    public static PersonEntity mapToEntity(PersonRequest request) {
        return new PersonEntity(
                request.getName(),
                request.getSurname(),
                request.getBirthday()
        );
    }

    public static PersonResponse mapToResponse(PersonEntity entity) {
        return new PersonResponse(
                entity.getUuid(),
                entity.getId(),
                entity.getName(),
                entity.getSurname(),
                entity.getBirthday(),
                entity.getCreationDateTime(),
                entity.getCars()
                        .stream()
                        .map(CarMapper::mapToResponse)
                        .collect(Collectors.toSet())
        );
    }
}
