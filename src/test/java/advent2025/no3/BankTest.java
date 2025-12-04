package advent2025.no3;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class BankTest {

    @ParameterizedTest
    @CsvSource({
            "12, 12",
            "121, 21",
            "987654321111111, 98",
            "811111111111119, 89",
            "234234234234278, 78",
            "818181911112111, 92"
    })
    public void ShouldFindTwoHighestDigits(String digits, int expected) {
        var bank = new Bank(digits);

        var res = bank.findLargestPossibleJoltage();

        assertThat(res).isEqualTo(expected);
    }

    @Test
    public void ShouldSumAllLines() {
        var bank = new Bank("""
                987654321111111
                811111111111119
                234234234234278
                818181911112111
                """);

        var res = bank.findLargestPossibleJoltage();

        assertThat(res).isEqualTo(357);
    }
}
