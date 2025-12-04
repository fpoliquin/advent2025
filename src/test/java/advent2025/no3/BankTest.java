package advent2025.no3;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class BankTest {

    @ParameterizedTest
    @CsvSource({
            "987654321111111, 987654321111",
            "811111111111119, 811111111119",
            "234234234234278, 434234234278",
            "818181911112111, 888911112111"
    })
    public void ShouldFindTwoHighestDigits(String digits, long expected) {
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

        assertThat(res).isEqualTo(3121910778619L);
    }
}
