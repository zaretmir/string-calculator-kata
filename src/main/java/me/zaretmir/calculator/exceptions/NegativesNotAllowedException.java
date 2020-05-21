package me.zaretmir.calculator.exceptions;

import java.util.List;

public class NegativesNotAllowedException extends Exception {

    private List<Double> invalidValues;

    public NegativesNotAllowedException(List<Double> invalidValues) {

        super("Negative not allowed : " + invalidValues.toString()
                .replace("[", "")
                .replace("]",""));
    }
}
