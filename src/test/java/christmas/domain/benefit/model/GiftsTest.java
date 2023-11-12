package christmas.domain.benefit.model;

import christmas.domain.benefit.constance.BenefitConst;
import christmas.domain.benefit.model.gift.Gifts;
import christmas.domain.menu.model.Menu;
import christmas.domain.menu.model.MenuAndCount;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GiftsTest {

    @Test
    @DisplayName("Benefit.getDescription()을 호출하면 \"증정 이벤트\"가 출력된다.")
    void getDescription_test() {
        Gifts gifts = new Gifts(List.of());
        Assertions.assertThat(gifts.getDescription())
                .isEqualTo(BenefitConst.GIFT_DESCRIPTION);
    }

    @Nested
    @DisplayName("금액 계산 테스트")
    class 금액_계산_테스트 {
        static Stream<Arguments> ListAndTotalPrice() {
            return Stream.of(
                    Arguments.of(
                            List.of(
                                    new MenuAndCount(Menu.CHRISTMAS_PASTA, 3), //75_000
                                    new MenuAndCount(Menu.SEAFOOD_PASTA, 5)),//175_000
                            250_000
                    ),
                    Arguments.of(
                            List.of(
                                    new MenuAndCount(Menu.BARBCUE_RIBS, 2), //108_000
                                    new MenuAndCount(Menu.CHOCOLATE_CAKE, 1),//15_000
                                    new MenuAndCount(Menu.MUSHROOM_SOUP, 3) //18_000
                            ), 141_000
                    ),
                    Arguments.of(
                            List.of(new MenuAndCount(Menu.CHAMPAGNE, 1)),//175_000
                            25_000
                    )
            );
        }

        @ParameterizedTest(name = "총 합계 금액 {1} 원이 정상적으로 구해진다.")
        @DisplayName("Benefit.getPrice()를 호출하면 올바른 혜택 금액을 반환한다.")
        @MethodSource("ListAndTotalPrice")
        void getPrice_테스트(List<MenuAndCount> orderList, int expectedPrice) {
            Gifts gifts = new Gifts(orderList);
            Assertions.assertThat(gifts.getPrice())
                    .isEqualTo(expectedPrice);
        }
    }

    @Nested
    @DisplayName("포함 항목 반환 테스트")
    class getGiftsTest {

        @ParameterizedTest
        @MethodSource("orderList")
        void 생성될_때_가지고_있던_항목을_정확하게_반환한다(List<MenuAndCount> orderList) {
            Gifts gifts = new Gifts(orderList);

            List<MenuAndCount> giftList = gifts.getGifts();
            Assertions.assertThat(giftList)
                    .containsExactly(orderList.toArray(MenuAndCount[]::new));
        }

        static Stream<List<MenuAndCount>> orderList() {
            return Stream.of(
                    List.of(
                            new MenuAndCount(Menu.CHRISTMAS_PASTA, 3),
                            new MenuAndCount(Menu.SEAFOOD_PASTA, 5)
                    ),
                    List.of(
                            new MenuAndCount(Menu.BARBCUE_RIBS, 2), //108_000
                            new MenuAndCount(Menu.CHOCOLATE_CAKE, 1),//15_000
                            new MenuAndCount(Menu.MUSHROOM_SOUP, 3) //18_000
                    ),
                    List.of(new MenuAndCount(Menu.CHAMPAGNE, 1))
            );
        }
    }
}