package com.rsjava.specificationsubquery.person.response;

import com.rsjava.specificationsubquery.car.response.CarResponse;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PersonResponse {
    @EqualsAndHashCode.Include
    private final String uuid;
    private final long id;
    private final String name;
    private final String surname;
    private final LocalDate birthday;
    private final LocalDateTime creationDateTime;
    private final Set<CarResponse> cars;
}
