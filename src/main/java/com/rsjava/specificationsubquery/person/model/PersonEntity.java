package com.rsjava.specificationsubquery.person.model;

import com.rsjava.specificationsubquery.car.model.CarEntity;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "person")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldNameConstants
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Include
    private String uuid;
    private String name;
    private String surname;
    private LocalDate birthday;
    @Setter(AccessLevel.NONE)
    private LocalDateTime creationDateTime;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Set<CarEntity> cars = new HashSet<>();

    public PersonEntity(String name,
                        String surname,
                        LocalDate birthday) {
        this.uuid = UUID.randomUUID().toString();
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.creationDateTime = LocalDateTime.now();
    }
}

