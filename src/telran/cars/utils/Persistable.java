package telran.cars.utils;

import java.io.Serializable;

public interface Persistable extends Serializable {
    void saveToFile(String fileName);
}
