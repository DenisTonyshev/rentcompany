package telran.cars.dto;

import java.time.LocalDate;

public class DataForGetRentRecords {

    public LocalDate from;
    public LocalDate to;

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    public DataForGetRentRecords() {
    }
}
