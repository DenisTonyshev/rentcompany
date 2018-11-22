package telran.cars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

@SpringBootApplication
@ManagedResource
public class MainRentCompanyAppl {

    static ConfigurableApplicationContext contex;
    @ManagedOperation
    public static void stop() {
        contex.close();
    }

    public static void main(String[] args) {
        contex = SpringApplication.run(MainRentCompanyAppl.class, args);
    }

}
