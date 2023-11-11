package christmas.menu.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MenuAndCountTest {

    @Nested
    @DisplayName("금액_계산_테스트")
    class 금액_계산_테스트{
        @ParameterizedTest(name = "{0}(이)가 {1}개 있으면 {2} 원이 된다.")
        @CsvSource(value = {
                "바비큐립, 5, 270_000",
                "크리스마스파스타, 13, 325_000"
        })
        void 금액_테스트(String menuName, int amount, int expectedPrice){
            Menu menu = Menu.from(menuName);
            MenuAndCount menuAndCount = new MenuAndCount(menu, amount);
            Assertions.assertThat(menuAndCount.getTotalPrice())
                    .isEqualTo(expectedPrice);
        }
    }
}