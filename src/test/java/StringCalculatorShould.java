import me.zaretmir.calculator.StringCalculator;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StringCalculatorShould {

    /*
     * TO-DO LIST:
     * "" -> "0"
     * "2.2" -> "2.2"
     * "2.2,3" -> "5.2"
     */

    @Test
    public void return_zero_when_input_is_empty_string() {
        StringCalculator calculator = new StringCalculator();

        assertThat(calculator.add("")).isEqualTo("0");
    }

    @Test
    public void return_same_number_when_input_is_a_single_number() {
        StringCalculator calculator = new StringCalculator();

        assertThat(calculator.add("0")).isEqualTo("0.0");
        assertThat(calculator.add("5")).isEqualTo("5.0");
        assertThat(calculator.add("2.3")).isEqualTo("2.3");
    }

    @Test
    public void return_sum_when_input_values_are_comma_separated() {
        StringCalculator calculator = new StringCalculator();

        assertThat(calculator.add("1,2")).isEqualTo("3.0");
        assertThat(calculator.add("0,2")).isEqualTo("2.0");
        assertThat(calculator.add("1.3,11")).isEqualTo("12.3");
        assertThat(calculator.add("1.3,11,0,13.78")).isEqualTo("26.08");
    }

    @Test
    public void return_sum_when_input_values_are_newline_and_comma_separated() {
        StringCalculator calculator = new StringCalculator();

        assertThat(calculator.add("1\n2")).isEqualTo("3.0");
        assertThat(calculator.add("0\n2")).isEqualTo("2.0");
        assertThat(calculator.add("1.3\n11")).isEqualTo("12.3");
        assertThat(calculator.add("1.3,11\n0,13.78")).isEqualTo("26.08");
    }

    @Test
    public void throw_error_when_using_consecutive_separators() {
        StringCalculator calculator = new StringCalculator();

        assertThatThrownBy(() -> calculator.add("1,\n2"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Number expected but '\\n' found at position 2");
        assertThatThrownBy(() -> calculator.add("1,2.7,\n2"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Number expected but '\\n' found at position 6");
    }

    @Test
    public void throw_error_when_ending_character_is_comma() {
        StringCalculator calculator = new StringCalculator();

        assertThatThrownBy(() -> calculator.add("1,2,"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Number expected but EOF found");
    }

    @Test
    public void allow_custom_separator() {
        StringCalculator calculator = new StringCalculator();

        assertThat(calculator.add("//;\n1;2")).isEqualTo("3.0");
        assertThat(calculator.add("//|\n1|2|3")).isEqualTo("6.0");
        assertThat(calculator.add("//sep\n2sep3")).isEqualTo("5.0");
    }

/*
    @Test
    public void throw_error_when_not_using_specified_custom_separator() {
        StringCalculator calculator = new StringCalculator();

        assertThatThrownBy(() -> calculator.add("//|\\n1|2,3"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("'|' expected but ',' found at position 3.");
    }*/
/*
    @Test
    public void throw_error_when_input_contains_negative_numbers() {
        StringCalculator calculator = new StringCalculator();

        assertThatThrownBy(() -> calculator.add("2,-4,-5"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Negative not allowed : -4.0, -5.0");
    }
    */
}
