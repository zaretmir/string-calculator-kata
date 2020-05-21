package me.zaretmir.calculator;


import java.util.*;
import java.util.stream.Collectors;

public class StringCalculator {

    public String add(String expression) {
        if (expression.isEmpty()) {
            return "0";
        }

        validateSyntax(expression);
        validateNonNegativeNumbersIn(expression);
        return toString(calculateSum(parseNumbers(expression)));
    }

    private String toString(double result) {
        return Double.toString(result);
    }

    private void validateSyntax(String expression) {

        if (expression.endsWith(",")) {
            throw new IllegalArgumentException("Number expected but EOF found");
        }

        boolean inputHasConsecutiveSeparators = expression.contains(",\n");
        if (inputHasConsecutiveSeparators) {
            int invalidSeparatorPosition = expression.indexOf(",\n") + 1;
            throw new IllegalArgumentException("Number expected but '\\n' found at position " + invalidSeparatorPosition);
        }
    }

    private List<Double> parseNumbers(String expression) {

        List<String> inputValues = extractNumbersFrom(expression);
        return inputValues.stream()
                .mapToDouble(Double::parseDouble)
                .boxed().collect(Collectors.toList());
    }

    private Double calculateSum(List<Double> inputNumbers) {
        return inputNumbers.stream().reduce(0D, Double::sum);
    }

    private void validateNonNegativeNumbersIn(String expression) {
        List<Double> numbers = parseNumbers(expression);
        List<Double> negativeNumbers = numbers.stream().filter(number -> number < 0).collect(Collectors.toList());
        boolean hasNegativeNumbers = !negativeNumbers.isEmpty();

        if (hasNegativeNumbers) {
            throw new IllegalArgumentException("Negative not allowed : " +
                    negativeNumbers.toString()
                            .replace("[", "")
                            .replace("]",""));
        }
    }

    private List<String> extractNumbersFrom(String expression) {
        String customSeparator = "//";
        boolean hasCustomSeparator = expression.startsWith(customSeparator);
        if (hasCustomSeparator) {
            return extractNumbersByCustomSeparatorFrom(expression);
        }

        return Arrays.asList(expression.split("[,\n]"));
    }

    private List<String> extractNumbersByCustomSeparatorFrom(String expression) {
        String[] separatorAndNumbers = expression.split("\n", 2);
        String separator = separatorAndNumbers[0]
                .replace("//", "")
                .replace("\n", "");
        if (separator.equals("|")) {
            separator = "\\|";
        }
        String inputNumbers = separatorAndNumbers[1];
        return Arrays.asList(inputNumbers.split(separator));
    }


}
