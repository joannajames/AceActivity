package Utilities;

import exceptions.Exception.NonPositiveValueException;
import exceptions.Exception.NonAppropriateValueException;

public class ValidationUtil {

    public static void checkPositive(float value, String valueName) throws NonPositiveValueException {
        if (value <= 0) {
            throw new NonPositiveValueException(valueName + " must be greater than 0. Provided: " + value);
        }
    }

    public static void checkPositive(int value, String valueName) throws NonPositiveValueException {
        if (value <= 0) {
            throw new NonPositiveValueException(valueName + " must be greater than 0. Provided: " + value);
        }
    }
    public static void checkSessionTime(int value, String valueName) throws NonAppropriateValueException {
        if (value <= 9) {
            throw new NonPositiveValueException(valueName + " must be greater than 9. Provided: " + value);
        }
    }
    
    public static void checkElevation(int value, String valueName) throws NonAppropriateValueException {
        if (value!=1 && value!=2 && value!= 3) {
            throw new NonPositiveValueException(valueName + " must be 1, 2 or 3. Provided: " + value);
        }
    }
}
