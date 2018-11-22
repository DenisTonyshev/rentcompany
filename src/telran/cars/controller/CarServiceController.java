package telran.cars.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.web.bind.annotation.*;
import telran.cars.dto.*;
import telran.cars.service.IRentCompany;
import telran.cars.service.RentCompanyEmbedded;
import telran.cars.utils.Persistable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static telran.cars.dto.RentCompanyConstants.*;

@RestController
@ManagedResource

public class CarServiceController {

    @Autowired
    IRentCompany rentCompany;

    @GetMapping(value = GET_MODEL + "/{" + MODEL_NAME + "}")
    public Model getModel(@PathVariable(MODEL_NAME) String modelName) {
        return rentCompany.getModel(modelName);
    }

    @PostMapping(value = ADD_MODEL)
    public CarsReturnCode addModel(@RequestBody Model model) {
        return rentCompany.addModel(model);
    }

    @GetMapping(value = GET_CAR + "/{" + CAR_NUMBER + "}")
    public Car getCar(@PathVariable(CAR_NUMBER) String carNumber) {
        return rentCompany.getCar(carNumber);
    }

    @PostMapping(value = ADD_CAR)
    public CarsReturnCode addCar(@RequestBody Car car) {
        return rentCompany.addCar(car);
    }

    @GetMapping(value = GET_DRIVER + "/{" + LICENSE_ID + "}")
    public Driver getDriver(@PathVariable(LICENSE_ID) long licenseId) {
        return rentCompany.getDriver(licenseId);
    }

    @PostMapping(value = ADD_DRIVER)
    public CarsReturnCode addDriver(@RequestBody Driver driver) {
        return rentCompany.addDriver(driver);
    }

    @GetMapping(value = GET_ALL_MODELS)
    public List<String> getAllModels() {
        return rentCompany.getAllModelNames();
    }

    @GetMapping(value = GET_ALL_CARS)
    public List<Car> getAllCars() {
        return rentCompany.getAllCars().collect(Collectors.toList());
    }

    @GetMapping(value = GET_ALL_DRIVERS)
    public List<Driver> getAllDrivers() {
        return rentCompany.getAllDrivers().collect(Collectors.toList());
    }

    @PostMapping(value = RENT_CAR)
    public CarsReturnCode rentCar(@RequestBody DatesForRent car) {
        return rentCompany.rentCar(car.getCarNumber(), car.getDriverId(), car.getRentDate(), car.getDays());
    }

    @PostMapping(value = RETURN_CAR)
    public CarsReturnCode returnCar(@RequestBody DatesForReturn car) {
        return rentCompany.returnCar(car.getCarNumber(), car.getReturnDate(), car.getGasTankPercent(), car.getDamages());
    }

    @GetMapping(value = GET_CAR_DRIVERS + "/{" + CAR_NUMBER + "}")
    public List<Driver> getCarDrivers(@PathVariable(CAR_NUMBER) String carNumber) {
        return rentCompany.getCarDrivers(carNumber);
    }

    @GetMapping(value = GET_DRIVER_CARS + "/{" + LICENSE_ID + "}")
    public List<Car> getDriverCars(@PathVariable(LICENSE_ID) long licenseId) {
        return rentCompany.getDriverCars(licenseId);
    }

    @GetMapping(value = GET_ALL_RECORDS)
    public List<RentRecord> getDriverCars() {
        return rentCompany.getAllRecords().collect(Collectors.toList());
    }

    @GetMapping(value = GET_MODEL_PROFIT + "/{" + MODEL_NAME + "}")
    public double getModelProfit(@PathVariable(MODEL_NAME) String modelName) {
        return rentCompany.getModelProfit(modelName);
    }

    @GetMapping(value = GET_MOST_PROFITABLE_MODEL_NAMES)
    public List<String> getMostProfitableModelNames() {
        return rentCompany.getMostProfitModelNames();
    }

    @GetMapping(value = GET_MOST_POPULAR_MODEL_NAMES)
    public List<String> getMostPopularModelNames() {
        return rentCompany.getMostPopularModelNames();
    }

    @PostMapping(value = CLEAR)
    public List<Car> clear(@RequestBody ClearFactor car) {
        return rentCompany.clear(car.getCurrentDate(), car.getDays());
    }

    @DeleteMapping(value = REMOVE_CAR + "/{" + CAR_NUMBER + "}")
    public CarsReturnCode removeCar(@PathVariable(CAR_NUMBER) String carNumber) {
        return rentCompany.removeCar(carNumber);
    }

    @PostMapping(value = GET_RETURNED_RECORDS_BY_DATES)
    public List<RentRecord> getRentRecords(@RequestBody DataForGetRentRecords rentRecords) {
        return rentCompany.getReturnedRecordsByDates(rentRecords.from, rentRecords.to).collect(Collectors.toList());
    }

    @PreDestroy
    void save() {
        if (rentCompany instanceof Persistable)
            ((Persistable) rentCompany).saveToFile(this.fileName);
    }

    @Value("${fileName:data}")
    private String fileName;
    @Value("${finePercent:15}")
    private int finePercent;
    @Value("${gasPrice:10}")
    private int gasPrice;

    @PostConstruct
    void set() {
        setGasPrice(gasPrice);
        setFinePercent(finePercent);
    }

    @ManagedAttribute
    public int getFinePercent() {
        return finePercent;
    }

    @ManagedAttribute
    public void setFinePercent(int finePercent) {
        this.finePercent = finePercent;
        rentCompany.setFinePercent(finePercent);
    }

    @ManagedAttribute
    public int getGasPrice() {
        return gasPrice;
    }

    @ManagedAttribute
    public void setGasPrice(int gasPrice) {
        this.gasPrice = gasPrice;
        rentCompany.setGasPrice(gasPrice);
    }
}
