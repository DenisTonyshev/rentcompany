package telran.cars.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import telran.cars.service.IRentCompany;
import telran.cars.service.*;
import telran.cars.utils.Persistable;

import javax.annotation.*;

@Configuration
public class CarServiceConfiguration {

    @Value("${pathFile:data}")
    private String fileName;

    @Autowired
    IRentCompany company;

    @Bean
    IRentCompany company() {
        return new RentCompanyEmbedded().restoreFromFile(fileName);
    }

    @PreDestroy
    public void saveToFile() {
        if (company instanceof RentCompanyEmbedded) {
            ((RentCompanyEmbedded) company).saveToFile(fileName);
        }
    }

}
