package com.rsjava.specificationsubquery.person.service;

import com.rsjava.specificationsubquery.person.PersonRepository;
import com.rsjava.specificationsubquery.person.exception.PersonNotFoundException;
import com.rsjava.specificationsubquery.person.mapper.PersonMapper;
import com.rsjava.specificationsubquery.person.model.PersonEntity;
import com.rsjava.specificationsubquery.person.request.PersonRequest;
import com.rsjava.specificationsubquery.person.response.PersonResponse;
import com.rsjava.specificationsubquery.utils.PredicatesBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.rsjava.specificationsubquery.person.mapper.PersonMapper.mapToEntity;
import static com.rsjava.specificationsubquery.person.mapper.PersonMapper.mapToResponse;


@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    @Transactional
    public List<PersonResponse> getPeople(String uuid,
                                          String name,
                                          String surname,
                                          LocalDate birthdayFrom,
                                          LocalDate birthdayTo
    ) {
        Specification<PersonEntity> specification = getPersonEntityQuery(uuid, name, surname, birthdayFrom, birthdayTo);

        return personRepository.findAll(specification)
                .stream()
                .map(PersonMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PersonResponse> getPeopleByCarModel(String carModel) {

        return personRepository.findDistinctAllByCars_Model(carModel)
                .stream()
                .map(PersonMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public PersonResponse getPerson(String uuid) {
        PersonEntity personEntity = personRepository.findByUuid(uuid).orElseThrow(() -> new PersonNotFoundException(uuid));

        return mapToResponse(personEntity);
    }

    @Transactional
    public String savePerson(PersonRequest request) {
        log.debug("Save person request with params: {}", request);

        return personRepository.save(mapToEntity(request)).getUuid();
    }

    @Transactional
    public void deletePerson(String uuid) {
        personRepository.deleteByUuid(uuid);
    }

    private Specification<PersonEntity> getPersonEntityQuery(String uuid,
                                                             String name,
                                                             String surname,
                                                             LocalDate birthdayFrom,
                                                             LocalDate birthdayTo) {
        return (root, query, criteriaBuilder) ->
                new PredicatesBuilder<>(root, criteriaBuilder)
                        .caseInsensitiveLike(PersonEntity.Fields.uuid, uuid)
                        .caseInsensitiveLike(PersonEntity.Fields.name, name)
                        .caseInsensitiveLike(PersonEntity.Fields.surname, surname)
                        .greaterThanOrEqualTo(root.get(PersonEntity.Fields.birthday), birthdayFrom)
                        .lessThanOrEqualTo(root.get(PersonEntity.Fields.birthday), birthdayTo)
                        .build();
    }
}
