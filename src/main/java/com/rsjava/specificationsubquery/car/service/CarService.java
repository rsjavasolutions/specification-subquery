package com.rsjava.specificationsubquery.car.service;


import com.rsjava.specificationsubquery.car.CarRepository;
import com.rsjava.specificationsubquery.car.exception.CarNotFoundException;
import com.rsjava.specificationsubquery.car.mapper.CarMapper;
import com.rsjava.specificationsubquery.car.model.CarEntity;
import com.rsjava.specificationsubquery.car.model.CarModelOnly;
import com.rsjava.specificationsubquery.car.request.CarRequest;
import com.rsjava.specificationsubquery.car.response.CarResponse;
import com.rsjava.specificationsubquery.person.PersonRepository;
import com.rsjava.specificationsubquery.person.exception.PersonNotFoundException;
import com.rsjava.specificationsubquery.person.model.PersonEntity;
import com.rsjava.specificationsubquery.utils.PredicatesBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.rsjava.specificationsubquery.car.mapper.CarMapper.mapToEntity;
import static com.rsjava.specificationsubquery.car.mapper.CarMapper.mapToResponse;


@Slf4j
@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final PersonRepository personRepository;

    @Transactional
    public List<CarResponse> getCars(String uuid,
                                     String brand,
                                     String model,
                                     Integer yearFrom,
                                     Integer yearTo,
                                     BigDecimal priceFrom,
                                     BigDecimal priceTo
    ) {
        Specification<CarEntity> specification = getCarEntityQuery(uuid, brand, model, yearFrom, yearTo, priceFrom, priceTo);

        return carRepository.findAll(specification)
                .stream()
                .map(CarMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<String> getCarModels() {
        return carRepository.findAllProjectedBy(CarModelOnly.class)
                .stream()
                .map(CarModelOnly::getModel)
                .collect(Collectors.toList());
    }


    @Transactional
    public CarResponse getCar(String uuid) {
        CarEntity carEntity = carRepository.findByUuid(uuid).orElseThrow(() -> new CarNotFoundException(uuid));
        return mapToResponse(carEntity);
    }

    @Transactional
    public String saveCar(String personUuid, CarRequest request) {
        log.debug("Save car request with params: {}", request);

        PersonEntity personEntity = personRepository.findByUuid(personUuid).orElseThrow(() -> new PersonNotFoundException(personUuid));
        CarEntity carEntity = mapToEntity(request);
        personEntity.getCars().add(carEntity);

        return carRepository.save(carEntity).getUuid();
    }

    @Transactional
    public CarResponse updateCar(String uuid, CarRequest request) {
        CarEntity carEntity = carRepository.findByUuid(uuid).orElseThrow(() -> new CarNotFoundException(uuid));

        carEntity.setBrand(request.getBrand());
        carEntity.setModel(request.getModel());

        return mapToResponse(carEntity);
    }

    @Transactional
    public void deleteCar(String uuid) {
        carRepository.deleteByUuid(uuid);
    }

    private Specification<CarEntity> getCarEntityQuery(String uuid,
                                                       String brand,
                                                       String model,
                                                       Integer yearFrom,
                                                       Integer yearTo,
                                                       BigDecimal priceFrom,
                                                       BigDecimal priceTo) {
        return (root, query, criteriaBuilder) ->
                new PredicatesBuilder<>(root, criteriaBuilder)
                        .caseInsensitiveLike(CarEntity.Fields.uuid, uuid)
                        .caseInsensitiveLike(CarEntity.Fields.brand, brand)
                        .caseInsensitiveLike(CarEntity.Fields.model, model)
                        .greaterThanOrEqualTo(root.get(CarEntity.Fields.year), yearFrom)
                        .lessThanOrEqualTo(root.get(CarEntity.Fields.year), yearTo)
                        .greaterThanOrEqualTo(root.get(CarEntity.Fields.price), priceFrom)
                        .lessThanOrEqualTo(root.get(CarEntity.Fields.price), priceTo)
                        .build();
    }
}
