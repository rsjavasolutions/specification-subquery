package com.rsjava.specificationsubquery.car;

import com.rsjava.specificationsubquery.car.request.CarRequest;
import com.rsjava.specificationsubquery.car.response.CarResponse;
import com.rsjava.specificationsubquery.car.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public CarResponse getCar(@PathVariable String uuid) {
        return carService.getCar(uuid);
    }

    @GetMapping("models")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getCarModels() {
        return carService.getCarModels();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CarResponse> getCars(@RequestParam(value = "uuid", required = false) String uuid,
                                     @RequestParam(value = "brand", required = false) String brand,
                                     @RequestParam(value = "model", required = false) String model,
                                     @RequestParam(value = "yearFrom", required = false) Integer yearFrom,
                                     @RequestParam(value = "yearTo", required = false) Integer yearTo,
                                     @RequestParam(value = "priceFrom", required = false) BigDecimal priceFrom,
                                     @RequestParam(value = "priceTo", required = false) BigDecimal priceTo
    ) {
        return carService.getCars(uuid, brand, model, yearFrom, yearTo, priceFrom, priceTo);
    }

    @PostMapping("people/{personUuid}")
    @ResponseStatus(HttpStatus.CREATED)
    public String saveCar(@PathVariable String personUuid,
                          @RequestBody @Valid CarRequest request) {
        return carService.saveCar(personUuid, request);
    }

    @PutMapping("{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public CarResponse updateCar(@PathVariable String uuid,
                                 @RequestBody CarRequest request) {
        return carService.updateCar(uuid, request);
    }

    @DeleteMapping("{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCar(@PathVariable String uuid) {
        carService.deleteCar(uuid);
    }
}
