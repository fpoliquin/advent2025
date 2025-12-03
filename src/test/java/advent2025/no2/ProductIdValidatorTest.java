package advent2025.no2;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ProductIdValidatorTest {

    public static final String PROVIDED_EXAMPLE = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224," +
            "1698522-1698528,446443-446449,38593856-38593862,565653-565659," +
            "824824821-824824827,2121212118-2121212124";

    @ParameterizedTest
    @CsvSource({
            "1, true",
            "2345, true",
            "55, false",
            "6464, false",
            "123123, false"
    })
    public void ShouldNotAcceptSequencesOfDigitsRepeatedTwice(long number, boolean accepted) {
        var validator = new ProductIdValidator();

        var res = validator.isValid(number);

        assertThat(res).isEqualTo(accepted);
    }

    @Test
    public void ShouldReturnEmptyListIfAllValid() {
        var validator = new ProductIdValidator();
        var range = "1-2";

        var res = validator.findInvalidNumbers(range);

        assertThat(res).isEmpty();
    }

    @Test
    public void ShouldReturnInvalidNumberInRange() {
        var validator = new ProductIdValidator();
        var range = "10-12";

        var res = validator.findInvalidNumbers(range);

        assertThat(res).isEqualTo(List.of(11L));
    }

    @Test
    public void ShouldReturnAllInvalidNumbersInRange() {
        var validator = new ProductIdValidator();
        var range = "10-23";

        var res = validator.findInvalidNumbers(range);

        assertThat(res).isEqualTo(List.of(11L, 22L));
    }

    @Test
    public void ShouldReturnAllInvalidNumbersMultipleRanges() {
        var validator = new ProductIdValidator();
        var range = "10-12,33-33";

        var res = validator.findInvalidNumbers(range);

        assertThat(res).isEqualTo(List.of(11L, 33L));
    }

    @Test
    public void ShouldTestProvidedExample() {
        var validator = new ProductIdValidator();

        var res = validator.findInvalidNumbers(PROVIDED_EXAMPLE);

        assertThat(res).isEqualTo(List.of(11L, 22L, 99L, 1010L, 1188511885L, 222222L, 446446L, 38593859L));
    }

    @Test
    public void ShouldSumAllInvalidNumbers() {
        var validator = new ProductIdValidator();

        var res = validator.findInvalidNumbersSum(PROVIDED_EXAMPLE);

        assertThat(res).isEqualTo(1227775554L);
    }

}
