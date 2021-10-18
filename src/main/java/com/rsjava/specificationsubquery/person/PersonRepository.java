package com.rsjava.specificationsubquery.person;

import com.rsjava.specificationsubquery.person.model.PersonEntity;
import com.rsjava.specificationsubquery.person.response.PersonResponse;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    Set<PersonEntity> findAll(Specification<PersonEntity> specification);

    Optional<PersonEntity> findByUuid(String uuid);

    void deleteByUuid(String uuid);

    List<PersonEntity> findDistinctAllByCars_Model(String carModel);
}
