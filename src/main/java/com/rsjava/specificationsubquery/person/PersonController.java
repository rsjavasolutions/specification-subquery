package com.rsjava.specificationsubquery.person;

import com.rsjava.specificationsubquery.person.request.PersonRequest;
import com.rsjava.specificationsubquery.person.response.PersonResponse;
import com.rsjava.specificationsubquery.person.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("people")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping("{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public PersonResponse getPerson(@PathVariable String uuid) {
        return personService.getPerson(uuid);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PersonResponse> getPeople(@RequestParam(value = "uuid", required = false) String uuid,
                                          @RequestParam(value = "name", required = false) String name,
                                          @RequestParam(value = "surname", required = false) String surname,
                                          @RequestParam(value = "birthdayFrom", required = false) LocalDate birthdayFrom,
                                          @RequestParam(value = "birthdayTo", required = false) LocalDate birthdayTo

    ) {
        return personService.getPeople(uuid, name, surname, birthdayFrom, birthdayTo);
    }



    @GetMapping("cars")
    @ResponseStatus(HttpStatus.OK)
    public List<PersonResponse> getPeopleByCarModel(@RequestParam(value = "carModel", required = false) String carModel) {
        return personService.getPeopleByCarModel(carModel);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String savePerson(@RequestBody @Valid PersonRequest request) {
        return personService.savePerson(request);
    }


    @DeleteMapping("{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable String uuid) {
        personService.deletePerson(uuid);
    }
}
