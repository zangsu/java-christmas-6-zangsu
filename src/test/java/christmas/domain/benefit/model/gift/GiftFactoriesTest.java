package christmas.domain.benefit.model.gift;

import christmas.domain.benefit.constance.BenefitConst;
import christmas.domain.benefit.model.Benefit;
import christmas.domain.date.model.PromotionDay;
import christmas.domain.menu.model.Menu;
import christmas.domain.menu.model.MenuAndCount;
import christmas.domain.menu.model.collection.OrderSheet;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class GiftFactoriesTest {

    PromotionDay promotionDay = PromotionDay.from(24);

    @Nested
    @DisplayName("샴페인 증정 이벤트 테스트")
    class 샴페인_증정_이벤트 {

        static List<OrderSheet> successOrderSheet() {
            return List.of(
                    new OrderSheet(List.of(
                            new MenuAndCount(Menu.T_BONE_STEAK, 1),
                            new MenuAndCount(Menu.BARBCUE_RIBS, 1),
                            new MenuAndCount(Menu.CHOCOLATE_CAKE, 2),
                            new MenuAndCount(Menu.ZERO_COKE, 1)
                    )),
                    new OrderSheet(List.of(
                            new MenuAndCount(Menu.T_BONE_STEAK, 3)
                    )),
                    new OrderSheet(List.of(
                            new MenuAndCount(Menu.T_BONE_STEAK, 1),
                            new MenuAndCount(Menu.SEAFOOD_PASTA, 1),
                            new MenuAndCount(Menu.CHRISTMAS_PASTA, 1),
                            new MenuAndCount(Menu.ICE_CREAM, 1)
                    ))
            );
        }

        static List<OrderSheet> failOrderSheet() {
            return List.of(
                    new OrderSheet(List.of(
                            new MenuAndCount(Menu.T_BONE_STEAK, 1)
                    )),
                    new OrderSheet(List.of(
                            new MenuAndCount(Menu.BARBCUE_RIBS, 1),
                            new MenuAndCount(Menu.SEAFOOD_PASTA, 1)
                    ))
            );
        }

        @ParameterizedTest(name = "120,000원 이상 주문하면 샴페인이 증정된다.")
        @MethodSource("successOrderSheet")
        @DisplayName("샴페인 증정 테스트")
        void 샴페인_증정_테스트(OrderSheet orderSheet) {
            List<Benefit> benefits = GiftFactories.of(promotionDay, orderSheet);

            Assertions.assertThat(orderSheet.getTotalPrice())
                    .isGreaterThanOrEqualTo(BenefitConst.MIN_PRICE_FOR_CHAMPAGNE);
            benefits.stream()
                    .map(benefit -> (Gifts) benefit)
                    .map(Gifts::getGifts)
                    .forEach(gifts -> Assertions.assertThat(gifts)
                            .contains(new MenuAndCount(Menu.CHAMPAGNE, 1)));
        }

        @ParameterizedTest(name = "120,000원 미만 주문하면 샴페인이 증정되지 않는다.")
        @MethodSource("failOrderSheet")
        @DisplayName("샴페인 미증정 테스트")
        void 샴페인_미증정_테스트(OrderSheet orderSheet) {
            List<Benefit> benefits = GiftFactories.of(promotionDay, orderSheet);

            Assertions.assertThat(orderSheet.getTotalPrice())
                    .isLessThan(BenefitConst.MIN_PRICE_FOR_CHAMPAGNE);
            benefits.stream()
                    .map(benefit -> (Gifts) benefit)
                    .map(Gifts::getGifts)
                    .forEach(gifts -> Assertions.assertThat(gifts)
                            .doesNotContain(new MenuAndCount(Menu.CHAMPAGNE, 1)));
        }

    }
}